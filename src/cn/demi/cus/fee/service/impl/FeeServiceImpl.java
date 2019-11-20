package cn.demi.cus.fee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.fee.dao.IFeeDao;
import cn.demi.cus.fee.po.Fee;
import cn.demi.cus.fee.service.IFeeService;
import cn.demi.cus.fee.vo.FeeVo;

@Service("cus.feeService")
public class FeeServiceImpl extends BaseServiceImpl<FeeVo,Fee> implements
		IFeeService {
	@Autowired
	private IFeeDao feeDao;
	
	
	@Override
	public List<QueryCondition> toQueryList(FeeVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if (!StrUtils.isBlankOrNull(v.getBillno())) {
			queryList.add(new QueryCondition("billno", QueryCondition.AK, v.getBillno()));
		}
		
		return queryList;
	}
	
	
	@Override
	public GridVo gridData(GridVo gridVo, FeeVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("billno");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		queryList.add(new QueryCondition(" fstatus =  '"+Constants.CHARGE_STATUS_WSF+"'"));
		pageResult.addConditionList(queryList);
		pageResult = feeDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Fee>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	
	@Override
	public GridVo gridDatad(GridVo gridVo, FeeVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("billno");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		queryList.add(new QueryCondition(" fstatus !=  '"+Constants.CHARGE_STATUS_WSF+"'" ));
		pageResult.addConditionList(queryList);
		pageResult = feeDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Fee>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	
}
