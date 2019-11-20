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
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.po.Account;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.report.po.Report;
import cn.demi.bus.report.service.IReportReptService;
import cn.demi.bus.report.vo.ReportVo;
import cn.demi.pfm.dao.ISRecordDao;

@Service("bus.reportReptService")
public class ReportReptServiceImpl extends BaseServiceImpl<ReportVo,Report> implements
		IReportReptService {

	@Autowired
	private IReportDao reportDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ISRecordDao sRecordDao;
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
		 po.setReptDate(v.getReptDate());
		 po.setReptUser(v.getReptUser());
		 po.setReptUserId(v.getReptUserId());
		 po.setReptMsg(v.getReptMsg());
		 if(!StrUtils.isBlankOrNull(v.getAuditUserId())) {
			Account at=accountDao.findById(v.getAuditUserId());
			po.setAuditUser(at.getUser().getName());
			po.setAuditUserId(at.getId());
		 }
		 Progress pg=po.getProgress();
		 if(v.getIsCommit().equals(EunmTask.PASS_Y)) {
			 if(!StrUtils.isBlankOrNull(po.getAuditUserId())) {
				pg=progressDao.update(pg.getId(),EunmTask.TASK_SH.getStatus(),null,null,po.getAuditUserId(),po.getAuditUser());
			 }else {
				 pg=progressDao.update(pg.getId(),EunmTask.TASK_SH.getStatus(),null,null,null,null);
			 }
		}else if(v.getIsCommit().equals(EunmTask.PASS_N)) {
			pg=progressDao.update(pg.getId(),EunmTask.TASK_BZ.getStatus(), po.getMakeUserId(),po.getMakeUser());
			po.setIsBack(Constants.Y);
		}
		po.setStatus(pg.getStatus());
		reportDao.update(po);
		progressLogDao.add(po.getId(),po.getId(),EunmTask.TASK_FH.getStatus(),v.getIsCommit(),v.getReptMsg());
		sRecordDao.insert(po.getReportNo(),po.getMakeDate(),Constants.KH_RW_BGFH);
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
		pageResult.addCondition(new QueryCondition("status='"+EunmTask.TASK_FH.getStatus()+"' " ));
		pageResult.addCondition(new QueryCondition("reptUserId like '"+CurrentUtils.getCurrent().getAccountId()+"%' or reptUserId is null or reptUserId='' "));
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
		//queryList.add(new QueryCondition("type='" + Po.Y + "' "));
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
		StringBuffer hql=new StringBuffer("SELECT distinct t FROM "+reportDao.getEntityName(Report.class)+" t,"+reportDao.getEntityName(ProgressLog.class)+" log where log.busId=t.id and t.isDel !="+Po.Y);
		if(QueryConditionList!=null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='"+EunmTask.TASK_FH.getStatus()+"' AND log.userId like '"+CurrentUtils.getCurrent().getAccountId()+"' ");
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
