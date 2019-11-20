package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.dao.ISupplierDao;
import cn.demi.res.dao.ISupplierEvaluateDao;
import cn.demi.res.po.SupplierEvaluate;
import cn.demi.res.service.ISupplierEvaluateService;
import cn.demi.res.vo.SupplierEvaluateVo;

@Service("res.supplierEvaluateService")
public class SupplierEvaluateServiceImpl extends BaseServiceImpl<SupplierEvaluateVo,SupplierEvaluate> implements
		ISupplierEvaluateService {
	@Autowired
	private ISupplierEvaluateDao supplierEvaluateDao;
	@Autowired
	private ISupplierDao supplierDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, SupplierEvaluateVo v) throws GlobalException {
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
		conditions.add(new QueryCondition("supplier.id", QueryCondition.EQ,v.getSupplierVo().getId()));
		pageResult.addConditionList(conditions);
		pageResult = supplierEvaluateDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<SupplierEvaluate>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<SupplierEvaluateVo> supplierEvaluateListBySupId(String id) throws GlobalException {
		List<SupplierEvaluateVo> vList = new ArrayList<SupplierEvaluateVo>();
		List<SupplierEvaluate> pList = null;
		SupplierEvaluateVo vo = null;
		pList = supplierEvaluateDao.supplierEvaluateListBySupId(id);
		if(null != pList && pList.size() > 0){
			for(SupplierEvaluate po : pList){
				vo = new SupplierEvaluateVo();
				vo = super.po2Vo(po);
				vList.add(vo);
			}
		}
		return vList;
	}

	@Override
	public void add(SupplierEvaluateVo v) throws GlobalException {
		SupplierEvaluate p = vo2Po(v);
		p.setSupplier(supplierDao.findById(v.getSupplierVo().getId()));
		supplierEvaluateDao.add(p);
		v.setId(p.getId());
	}

	
	
}
