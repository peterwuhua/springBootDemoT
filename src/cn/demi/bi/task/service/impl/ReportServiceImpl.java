package cn.demi.bi.task.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
import cn.demi.bi.task.service.IReportService;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.report.po.Report;
import cn.demi.bus.report.vo.ReportVo;
/**
 * 报告统计 业务逻辑层
 */
@Service("bi.reportService")
public class ReportServiceImpl extends BaseServiceImpl<ReportVo,Report> implements
		IReportService {
	
 
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private IReportDao reportDao;
	/**
	 * 获取任务详细信息
	 * 包含项目、样品信息
	 */
	@Override
	public ReportVo findById(String id) throws GlobalException {
		Report task=reportDao.findById(id);
		ReportVo vo=po2Vo(task);
		return vo;
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
		String currDeptId = CurrentUtils.getCurrent().getDepId();
		Org org = orgDao.findOrg(currDeptId);

		if (org.getName().contains("环境")) {
			pageResult.addCondition(new QueryCondition("samp_type like '%环境%'"));
		} else if (org.getName().contains("职业卫生")) {
			pageResult.addCondition(new QueryCondition("samp_type like '%职业卫生%'"));
		}
		
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
		List<QueryCondition> QueryConditionList = new ArrayList<QueryCondition>();
		if(null!=v.getTaskVo()) {
			if(!StrUtils.isBlankOrNull(v.getTaskVo().getNo())) {
				QueryConditionList.add(new QueryCondition("task.no", QueryCondition.AK, v.getTaskVo().getNo()));
			}
		}
		if(!StrUtils.isBlankOrNull(v.getReportNo())) {
			QueryConditionList.add(new QueryCondition("reportNo", QueryCondition.AK, v.getReportNo()));
		}
		if(!StrUtils.isBlankOrNull(v.getSampName())) {
			QueryConditionList.add(new QueryCondition("sampName", QueryCondition.AK, v.getSampName()));
		}
		if(null!=v.getCustVo()&&!StrUtils.isBlankOrNull(v.getCustVo().getCustName())) {
			QueryConditionList.add(new QueryCondition("cust.custName", QueryCondition.AK, v.getCustVo().getCustName()));
		}
		if(!StrUtils.isBlankOrNull(v.getTaskType())) {
			QueryConditionList.add(new QueryCondition("taskType", QueryCondition.AK, v.getTaskType()));
		}
		if(!StrUtils.isBlankOrNull(v.getStatus())) {
			QueryConditionList.add(new QueryCondition("status", QueryCondition.EQ, v.getStatus()));
		}
		if(!StrUtils.isBlankOrNull(v.getOrgName())) {
			QueryConditionList.add(new QueryCondition("orgName", QueryCondition.AK, v.getOrgName()));
		}
		if(!StrUtils.isBlankOrNull(v.getStartDate())) {
			QueryConditionList.add(new QueryCondition("reportDate", QueryCondition.GE, v.getStartDate()+" 00:00:00"));
		}
		if(!StrUtils.isBlankOrNull(v.getEndDate())) {
			QueryConditionList.add(new QueryCondition("reportDate", QueryCondition.LE, v.getEndDate()+" 23:59:59"));
		}
		if(!StrUtils.isBlankOrNull(v.getOrgId())) {
			List<String> ids=orgDao.listChild(v.getOrgId());
			ids.add(v.getOrgId());
			QueryConditionList.add(new QueryCondition("orgId in('"+String.join("','", ids)+"')"));
		}
		return QueryConditionList;
	}
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<?, Object>> poList2mapList(List<Report> list) throws GlobalException{
		List tempList = new ArrayList();
		for(Report p:list){
			Map<String, Object> map=po2map(p);
			map.put("status", EunmTask.getName(map.get("status").toString()));
			tempList.add(map);
		}
		return tempList;
	}
}
