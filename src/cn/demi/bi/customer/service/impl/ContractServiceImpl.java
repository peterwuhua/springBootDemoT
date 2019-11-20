package cn.demi.bi.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bi.customer.service.IContractService;
import cn.demi.cus.contract.dao.IContractDao;
import cn.demi.cus.contract.po.Contract;
import cn.demi.cus.contract.vo.ContractVo;

@Service("bi.contractService")
public class ContractServiceImpl extends BaseServiceImpl<ContractVo, Contract> implements IContractService {
	@Autowired
	private IContractDao contractDao;

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ContractVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = contractDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Contract>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	 
	@Override
	public List<QueryCondition> toQueryList(ContractVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(StrUtils.isNotBlankOrNull(v.getSaler())) {
			queryConditions.add(new QueryCondition("saler IN ('"+v.getSaler().replace(",", "','")+"')"));
		}
		if(StrUtils.isNotBlankOrNull(v.getCode())) {
			queryConditions.add(new QueryCondition("code like '%"+v.getCode() +"%' "));
		}
		if(!StrUtils.isNull(v.getCustomerVo())&&StrUtils.isNotBlankOrNull(v.getCustomerVo().getName())) {
			queryConditions.add(new QueryCondition("customer.name like '%"+v.getCustomerVo().getName() +"%' "));
		}
		return queryConditions;
	}
 
}
