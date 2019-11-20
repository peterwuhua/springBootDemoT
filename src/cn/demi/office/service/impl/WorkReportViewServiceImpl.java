package cn.demi.office.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.office.dao.IWorkReportDao;
import cn.demi.office.po.WorkReport;
import cn.demi.office.service.IWorkReportViewService;
import cn.demi.office.vo.WorkReportVo;

@Service("office.workReportViewService")
public class WorkReportViewServiceImpl extends BaseServiceImpl<WorkReportVo,WorkReport> implements
		IWorkReportViewService {
	@Autowired IWorkReportDao workReportDao;
	@Autowired IProgressDao progressDao;
	/**
	 * 
	 * @Title: gridData   
	 * @Description: 工作汇报列表页数据集(按照汇报日期倒序)
	 * @param: @param gridVo
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: GridVo      
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo,WorkReportVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			//先根据状态排序desc,然后在根据汇报日期排序desc
			pageResult.addOrder("wpStatus", OrderCondition.ORDER_DESC);
			pageResult.addOrder("reportDate", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = workReportDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<WorkReport>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	/**
	 * 
	 * <p>Title: toQueryList</p>   
	 * <p>Description: 查询条件方法</p>   
	 * @param v
	 * @return
	 * @throws GlobalException   
	 */
	@Override
	public List<QueryCondition> toQueryList(WorkReportVo v) throws GlobalException {
		List<QueryCondition> conditions = new ArrayList<QueryCondition>();
		String accountId = getCurrent().getAccountId();
		QueryCondition con = new QueryCondition(" viewerId like '%"+accountId+"%' ");
		QueryCondition con1 = new QueryCondition(" wpStatus in ('"+WorkReport.WORK_REPORT_STATUS_WFK+"','"+WorkReport.WORK_REPORT_STATUS_YFK+"'  )  ");
		conditions.add(con);
		conditions.add(con1);
		return conditions;
	}
	
	/**
	 * 
	 * <p>Title: addWorkReport</p>   
	 * <p>Description: 新增工作汇报</p>   
	 * @param v
	 * @throws GlobalException   
	 * @see cn.demi.office.service.IWorkReportViewService#addWorkReport(cn.demi.office.vo.WorkReportVo)
	 */
	@Override
	public void addWorkReport(WorkReportVo v) throws GlobalException {
		WorkReport po = vo2Po(v);
		workReportDao.add(po);
		deletePgById(po);
	}
	
	/**
	 * 
	 * <p>Title: updateWorkReport</p>   
	 * <p>Description: 修改工作汇报 </p>   
	 * @param v
	 * @throws GlobalException   
	 * @see cn.demi.office.service.IWorkReportViewService#updateWorkReport(cn.demi.office.vo.WorkReportVo)
	 */
	@Override
	public void updateWorkReport(WorkReportVo v) throws GlobalException {
		WorkReport po =workReportDao.findById(v.getId());
		valVersion(v.getVersion(), po.getVersion());
		po.toPo(v, po);
//		po.setFkContent(v.getFkContent());
		po.setWpStatus(WorkReport.WORK_REPORT_STATUS_YFK);
		workReportDao.update(po);
		deletePgById(po);
	}

	/**
	 * 
	 * @Title: deletePgById   
	 * @Description: 根据id删除pg   
	 * @param: @param po      
	 * @return: void      
	 * @throws
	 */
	private void deletePgById(WorkReport po)
	{
		progressDao.deleteByBusId(po.getId());
	}
	
	
	
}
