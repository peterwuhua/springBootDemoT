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
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.po.Files;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.report.po.Report;
import cn.demi.bus.report.service.IReportSignService;
import cn.demi.bus.report.vo.ReportVo;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.pfm.dao.ISRecordDao;

@Service("bus.reportSignService")
public class ReportSignServiceImpl extends BaseServiceImpl<ReportVo,Report> implements
		IReportSignService {

	@Autowired
	private IReportDao reportDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private ISRecordDao sRecordDao;
	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private IFilesDao filesDao;
	@Override
	public ReportVo findById(String id) throws GlobalException {
		Report po = reportDao.findById(id);
		ReportVo vo=new ReportVo();
		vo=vo.toVo(po);
		List<FilesVo> fileList = new ArrayList<FilesVo>();
		List<TaskItem> tiList=taskItemDao.listByTaskId(vo.getTaskVo().getId());
		if(null!=tiList) {
			for (TaskItem taskItem : tiList) {
				// 附件
				String hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + taskItem.getId() + "'  ";
				List<Files> fList = filesDao.list(hql);
				if (null != fList) {
					for (Files f : fList) {
						FilesVo fVo = new FilesVo();
						fVo = fVo.toVo(f);
						fileList.add(fVo);
					}
				}
			}
		}
		// 附件
		String hql = "FROM " + filesDao.getEntityName(Files.class) + " WHERE isDel='" + Po.N + "' AND busId='" + vo.getTaskVo().getId() + "' ";
		List<Files> fList = filesDao.list(hql);
		if (null != fList) {
			for (Files f : fList) {
				FilesVo fVo = new FilesVo();
				fVo = fVo.toVo(f);
				fileList.add(fVo);
			}
		}
		vo.setFileList(fileList);
		return vo;
	}
	 @Override
	public void update(ReportVo v) throws GlobalException {
		 Report po= reportDao.findById(v.getId());
		 po.setSignDate(v.getSignDate());
		 po.setSignUserId(v.getSignUserId());
		 po.setSignUser(v.getSignUser());
		 po.setSignMsg(v.getSignMsg());
		 reportDao.update(po);
		 Progress pg=po.getProgress();
		 if(v.getIsCommit().equals(EunmTask.PASS_Y)) {
			pg=progressDao.update(pg.getId(),EunmTask.TASK_FF.getStatus(),null,null);
		}else if(v.getIsCommit().equals(EunmTask.PASS_N)) {
			pg=progressDao.update(pg.getId(),EunmTask.TASK_BZ.getStatus(), po.getMakeUserId(),po.getMakeUser());
			po.setIsBack(Constants.Y);
		}
		 po.setStatus(pg.getStatus());
		 reportDao.update(po);
		 progressLogDao.add(po.getTask().getId(),po.getTask().getId(),EunmTask.TASK_PZ.getStatus(),v.getIsCommit(),v.getSignMsg());
		 Task task=po.getTask();
		task.setStatus(po.getStatus());
		taskDao.update(task);	
		sRecordDao.insert(po.getTask().getNo(),po.getAuditDate(),Constants.KH_RW_BGQF);
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
		pageResult.addCondition(new QueryCondition("status='"+EunmTask.TASK_PZ.getStatus()+"' " ));
		pageResult.addCondition(new QueryCondition("signUserId like '"+CurrentUtils.getCurrent().getAccountId()+"%' or signUserId is null or signUserId='' "));
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
		hql.append(" AND log.status='"+EunmTask.TASK_PZ.getStatus()+"' AND log.userId like '"+CurrentUtils.getCurrent().getAccountId()+"' ");
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
	@Override
	public void update4Pdf(ReportVo v) throws GlobalException {
		Report po = reportDao.findById(v.getId());
		po.setPdfName(v.getPdfName());
		po.setPdfPath(v.getPdfPath());
		reportDao.update(po);
	}
}
