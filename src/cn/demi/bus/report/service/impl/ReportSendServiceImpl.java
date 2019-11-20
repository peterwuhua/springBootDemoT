package cn.demi.bus.report.service.impl;

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
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.report.po.Report;
import cn.demi.bus.report.service.IReportSendService;
import cn.demi.bus.report.vo.ReportVo;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.po.Task;

@Service("bus.reportSendService")
public class ReportSendServiceImpl extends BaseServiceImpl<ReportVo,Report> implements
		IReportSendService {

	@Autowired
	private IReportDao reportDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ITaskDao taskDao;
	
	@Override
	public ReportVo findById(String id) throws GlobalException {
		Report po = reportDao.findById(id);
		ReportVo vo=new ReportVo();
		vo=vo.toVo(po);
		return vo;
	}
	
	@Override
	public void update(ReportVo v) throws GlobalException {
		 Report po= reportDao.findById(v.getId());
		 po.setSendDate(v.getSendDate());
		 po.setSendUser(v.getSendUser());
		 po.setSendUserId(v.getSendUserId());
		 po.setSendType(v.getSendType());
		 po.setToUser(v.getToUser());
		 po.setDemo(v.getDemo());
		 po.setSendMsg(v.getSendMsg());
		 if(v.getIsCommit().equals(EunmTask.PASS_Y)) {
			String msg="编号："+po.getReportNo()+" 备注："+v.getSendMsg();
			 //插入任务进度日志
			Progress pg=po.getProgress();
			progressLogDao.add(po.getTask().getId(),po.getTask().getId(),EunmTask.TASK_FF.getStatus(),v.getIsCommit(),msg);
			pg=progressDao.update(pg.getId(),EunmTask.TASK_GD.getStatus());
			po.setStatus(pg.getStatus());
		 }
		 reportDao.update(po);
		 Task task=po.getTask();
		task.setStatus(po.getStatus());
		taskDao.update(task);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ReportVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='"+EunmTask.TASK_FF.getStatus()+"' " ));
		//pageResult.addCondition(new QueryCondition("orgId like '"+CurrentUtils.getCurrent().getOrgId()+"%' "));
		pageResult = reportDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Report>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(ReportVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		//queryList.add(new QueryCondition("type='" + Po.N + "' "));
		return queryList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ReportVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("SELECT distinct t FROM "+reportDao.getEntityName(Report.class)+" t,"+reportDao.getEntityName(ProgressLog.class)+" log where log.busId=t.task.id and t.isDel !="+Po.Y);
		if(QueryConditionList!=null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='"+EunmTask.TASK_FF.getStatus()+"' AND log.userId like '"+CurrentUtils.getCurrent().getAccountId()+"' ");
		hql.append(" ORDER BY t."+pageResult.getOrderColumn()+" "+pageResult.getOrder()+"");
		Query query=reportDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Report> taskList = reportDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		gridVo.setDatas(poList2mapList(taskList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
