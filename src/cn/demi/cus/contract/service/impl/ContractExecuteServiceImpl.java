package cn.demi.cus.contract.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.contract.dao.IContractDao;
import cn.demi.cus.contract.dao.IContractExecuteDao;
import cn.demi.cus.contract.po.Contract;
import cn.demi.cus.contract.po.ContractExecute;
import cn.demi.cus.contract.service.IContractExecuteService;
import cn.demi.cus.contract.vo.ContractExecuteVo;
import cn.demi.cus.contract.vo.ContractVo;
/**
 * Create on : 2017年1月9日 下午2:40:53  <br>
 * Description :  合同执行情况serviceImpl<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Service("cus.contract.executeService")
public class ContractExecuteServiceImpl extends BaseServiceImpl<ContractExecuteVo,ContractExecute> implements
		IContractExecuteService {
	
	@Autowired private IContractDao contractDao;
	@Autowired private IContractExecuteDao contractExecuteDao;
	@Override
	public void update(ContractExecuteVo v) throws GlobalException {
		ContractExecute p = contractExecuteDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		if(null != v.getContractVo()){
			Contract contract =contractDao.findById(v.getContractVo().getId());
			if(null == contract)
				throw new GlobalException("参数异常");
			p.setContract(contract);
		}
		contractExecuteDao.update(p);
		v.setId(p.getId());
	}
	@Override
	public void add(ContractExecuteVo v) throws GlobalException {
		ContractExecute p = vo2Po(v);
		if(null != v.getContractVo()){
			Contract contract =contractDao.findById(v.getContractVo().getId());
			if(null == contract)
				throw new GlobalException("参数异常");
			p.setContract(contract);
		}
		contractExecuteDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public void data2Vo(String[] values, ContractExecuteVo v, String param) throws GlobalException {
		if(StrUtils.isBlankOrNull(param))
			throw new GlobalException("参数异常");
		ContractVo contractVo = new ContractVo();
		contractVo.setId(param);
		v.setContractVo(contractVo);
		v.setTaskNo(values[1]);
		v.setSampName(values[2]);
		v.setSampCount(values[3]);
		v.setSendSampDate(values[4]);
		v.setSendSampPerson(values[5]);
	}
	@Override
	public List<QueryCondition> toQueryList(ContractExecuteVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("contract.id", QueryCondition.EQ, v.getContractVo().getId()));
		return queryList;
	}
	
	@Override
	public List<ContractExecuteVo> list(ContractExecuteVo v) throws GlobalException {
		if(null!=v.getContractVo()){
			return toVoList(contractExecuteDao.listByconId(v.getContractVo().getId()));
		}else{
			return super.list(v);
		}
	}
	@Override
	public List<ContractExecuteVo> listByConId(String conId) throws GlobalException {
		return toVoList(contractExecuteDao.listByconId(conId));
	}
}
