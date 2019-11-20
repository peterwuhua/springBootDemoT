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
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.IWorkReportDao;
import cn.demi.office.po.WorkReport;
import cn.demi.office.service.IWorkReportService;
import cn.demi.office.vo.WorkReportVo;

@Service("office.workReportService")
public class WorkReportServiceImpl extends BaseServiceImpl<WorkReportVo,WorkReport> implements
		IWorkReportService {
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
		QueryCondition con = new QueryCondition(" personId = '"+accountId+"'  ");
		conditions.add(con);
		return conditions;
	}
	@Override
	public void add(WorkReportVo v) throws GlobalException {
		WorkReport p = vo2Po(v);
		workReportDao.add(p);
		v.setId(p.getId());
		commit(p,v);
	}
	@Override
	public void update(WorkReportVo v) throws GlobalException {
		WorkReport po =  workReportDao.findById(v.getId());
		valVersion(v.getVersion(), po.getVersion());
		po=po.toPo(v, po);
		workReportDao.update(po);
		commit(po,v);
	}
	public void commit(WorkReport p, WorkReportVo v) throws GlobalException {
		Progress pg = new Progress();
		pg.setBusId(p.getId());
		pg.setStatus("CY_01");
		pg.setUserId(p.getViewerId());
		pg.setUserName(p.getViewer());
		progressDao.save(pg);
    }
	
}
