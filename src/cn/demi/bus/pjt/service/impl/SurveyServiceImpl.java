package cn.demi.bus.pjt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.IProjectDao;
import cn.demi.bus.pjt.dao.ISurveyDao;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.pjt.po.Survey;
import cn.demi.bus.pjt.service.ISurveyService;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SurveyVo;
/**
 * 现场踏勘业务逻辑层
 * @author QuJunLong
 */
@Service("bus.surveyService")
public class SurveyServiceImpl extends BaseServiceImpl<SurveyVo,Survey> implements
		ISurveyService {
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ISurveyDao surveyDao;
	@Override
	public SurveyVo findById(String id) throws GlobalException {
		Survey po=surveyDao.findById(id);
		SurveyVo vo=po2Vo(po);
		return vo;
	}
	@Override
	public void update(SurveyVo vo) throws GlobalException {
		Survey po=surveyDao.findById(vo.getId());
		po.toPo(vo, po);
		surveyDao.update(po);
		Project p=projectDao.findById(vo.getProjectVo().getId());
		if(null!=vo.getIsCommit()&&vo.getIsCommit().equals(EunmTask.PASS_Y)) {
			Progress pg=progressDao.update(p.getProgress().getId(), EunmTask.PROJECT_BZ.getStatus(),null, null, null,null);
			p.setStatus(pg.getStatus());
			projectDao.update(p);
			//添加日志
			progressLogDao.add(p.getId(), p.getId(), EunmTask.PROJECT_TK.getStatus(), EunmTask.PASS_Y,vo.getRemark());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, SurveyVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("userId like '%"+getCurrent().getAccountId()+"%'"));
		pageResult.addCondition(new QueryCondition("status like '"+EunmTask.PROJECT_TK.getStatus()+"'"));
		pageResult = surveyDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Survey>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, SurveyVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		pageResult.addConditionList(toQueryList(v));
		
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("createTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("SELECT distinct t FROM "+surveyDao.getEntityName(Survey.class)+" t,"+progressLogDao.getEntityName(ProgressLog.class)+" log where log.busId=t.project.id and t.isDel !="+Po.Y);
		if(QueryConditionList!=null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='"+EunmTask.PROJECT_TK.getStatus()+"' AND log.userId like '%"+CurrentUtils.getCurrent().getAccountId()+"%' ");
		hql.append(" ORDER BY t."+pageResult.getOrderColumn()+" "+pageResult.getOrder()+"");
		Query query=surveyDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Survey> pjList = surveyDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		gridVo.setDatas(poList2mapList(pjList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	public List<QueryCondition> toQueryList(ProjectVo v) throws GlobalException {
		return null;
	}
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<?, Object>> poList2mapList(List<Survey> list) throws GlobalException{
		List tempList = new ArrayList();
		for(Survey p:list){
			Map<String, Object> map=po2map(p);
			if(null!=map.get("tdate")) {
				String tdate=String.valueOf(map.get("tdate"));
				long n=DateUtils.getIntevalDays(DateUtils.getCurrDateStr(), tdate);
				if(n<0) {
					map.put("color", "red");
				}else if(n<3) {
					map.put("color", "blue");
				}
			}
			tempList.add(map);
		}
		return tempList;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<?, Object>> poList2mapList1(List<Project> list) throws GlobalException {
		List tempList = new ArrayList();
		for(Project p:list){
			tempList.add(p.toMapProperty(p, p.getPropertyToMap()));
		}
		return tempList;
	}
	@Override
	public SurveyVo findByProjectId(String projectId) throws GlobalException {
		Survey su=surveyDao.findByProjectId(projectId);
		SurveyVo suVo=new SurveyVo();
		suVo=suVo.toVo(su);
		return suVo;
	}
	
}
