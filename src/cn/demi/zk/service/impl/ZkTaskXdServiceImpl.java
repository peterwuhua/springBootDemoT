package cn.demi.zk.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmZkTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.po.Account;
import cn.demi.zk.dao.IZkProgressLogDao;
import cn.demi.zk.dao.IZkSamplingDao;
import cn.demi.zk.dao.IZkTaskDao;
import cn.demi.zk.po.ZkProgressLog;
import cn.demi.zk.po.ZkSampling;
import cn.demi.zk.po.ZkTask;
import cn.demi.zk.service.IZkTaskXdService;
import cn.demi.zk.vo.ZkSamplingVo;
import cn.demi.zk.vo.ZkTaskVo;

@Service("zk.taskXdService")
public class ZkTaskXdServiceImpl extends BaseServiceImpl<ZkTaskVo,ZkTask> implements
		IZkTaskXdService {
	@Autowired
	private IZkTaskDao zkTaskDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private IZkSamplingDao zkSamplingDao;
	@Autowired
	private IZkProgressLogDao progressLogDao;
	@Override
	public void update(ZkTaskVo v) throws GlobalException {
		ZkTask p = zkTaskDao.findById(v.getId());
		List<ZkSamplingVo> sampList=v.getSampList();
		for (ZkSamplingVo sampVo : sampList) {
			ZkSampling samp=zkSamplingDao.findById(sampVo.getId());
			samp.setOrgId(sampVo.getOrgId());
			samp.setOrgName(sampVo.getOrgName());
			samp.setUserIds(sampVo.getUserIds());
			samp.setUserNames(sampVo.getUserNames());
			samp.setCompDate(sampVo.getCompDate());
			zkSamplingDao.update(samp);
		}
		if(null!=v.getIsCommit()&&v.getIsCommit().equals(Constants.PASS_Y)) {
			p.setStatus(EunmZkTask.TASK_40.getStatus());
			zkTaskDao.update(p);
			progressLogDao.add(p.getId(),EunmZkTask.TASK_30.getStatus(),"下达",v.getRemark());
		}
	}
	@Override
	public ZkTaskVo findById(String id) throws GlobalException {
		ZkTask po= zkTaskDao.findById(id);
		ZkTaskVo vo=po2Vo(po);
		List<ZkSampling> sampList=zkSamplingDao.findByProperty("task.id", po.getId());
		List<ZkSamplingVo> sampVoList= new ArrayList<ZkSamplingVo>();
		if(null!=sampList) {
			for (ZkSampling samp : sampList) {
				ZkSamplingVo sampVo=new ZkSamplingVo();
				sampVo=sampVo.toVo(samp);
				if(StrUtils.isBlankOrNull(samp.getOrgId())) {
					Account act=accountDao.findById(samp.getUserIds());
					samp.setOrgId(act.getOrg().getId());
					samp.setOrgName(act.getOrg().getName());
				}
				sampVoList.add(sampVo);
			}
		}
		vo.setSampList(sampVoList);
		return vo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = zkTaskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ZkTask>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	 
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("FROM "+zkTaskDao.getEntityName(ZkTask.class)+" t,"+zkTaskDao.getEntityName(ZkProgressLog.class)+" log where log.taskId=t.id and t.isDel !="+Po.Y);
		if(QueryConditionList!=null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='"+v.getStatus()+"' ");
		hql.append(" ORDER BY t."+pageResult.getOrderColumn()+" "+pageResult.getOrder()+"");
		Query query=zkTaskDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Object[]> list = zkTaskDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		List<ZkTask> taskList=new ArrayList<ZkTask>();
		if(list!=null) {
			for (Object[] objects : list) {
				try {
					Object obj[]=objects;
					ZkTask t=(ZkTask) obj[0];
					ZkProgressLog log=(ZkProgressLog) obj[1];
					t.setProgressLog(log);
					taskList.add(t);
				} catch (Exception e) {
				}
			}
		}
		gridVo.setDatas(poList2mapList(taskList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	 
	@Override
	public List<QueryCondition> toQueryList(ZkTaskVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("status='"+v.getStatus()+"'"));
		return queryList;
	}
}
