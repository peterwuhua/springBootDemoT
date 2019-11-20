package cn.demi.res.service.impl;

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
import cn.core.framework.exception.GlobalException;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.dao.IMaintenanceDao;
import cn.demi.res.po.Maintenance;
import cn.demi.res.service.IMaintenanceService;
import cn.demi.res.vo.MaintenanceVo;

@Service("res.maintenanceService")
public class MaintenanceServiceImpl extends BaseServiceImpl<MaintenanceVo,Maintenance> implements
		IMaintenanceService {
	
	@Autowired
	private IMaintenanceDao maintenanceDao;
	@Autowired
	private IApparaDao apparaDao;
	
	@Override
	public void add(MaintenanceVo v) throws GlobalException {
		Maintenance p = vo2Po(v);
		p.setAppara(apparaDao.findById(v.getApparaVo().getId()));
		maintenanceDao.add(p);
		v.setId(p.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, MaintenanceVo v) throws GlobalException {
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
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("appara.id", QueryCondition.EQ,v.getApparaVo().getId()));
		pageResult.addConditionList(conditions);
		pageResult.addOrder("create_time", OrderCondition.ORDER_DESC);
		pageResult = maintenanceDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Maintenance>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
