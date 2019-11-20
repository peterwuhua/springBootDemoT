package cn.demi.cus.contract.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.contract.dao.IContractDao;
import cn.demi.cus.contract.dao.IContractDetailDao;
import cn.demi.cus.contract.po.Contract;
import cn.demi.cus.contract.po.ContractDetail;
import cn.demi.cus.contract.service.IContractDetailService;
import cn.demi.cus.contract.vo.ContractDetailVo;
import cn.demi.cus.contract.vo.ContractVo;
/**
 * Create on : 2017年1月9日 下午6:28:08  <br>
 * Description : 合同项目详情serviceImpl <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Service("cus.contract.detailService")
public class ContractDetailServiceImpl extends BaseServiceImpl<ContractDetailVo,ContractDetail> implements
		IContractDetailService {
	@Autowired private IContractDao contractDao;
	@Autowired private IContractDetailDao contractDetailDao;
	@Override
	public List<QueryCondition> toQueryList(ContractDetailVo v) throws GlobalException {
		List<QueryCondition> conditions = new ArrayList<>();
		if(null != v.getContractVo())
			conditions.add(new QueryCondition("contract.id",QueryCondition.EQ,v.getContractVo().getId()));
		if(null != v.getContractVo() && StrUtils.isNotBlankOrNull(v.getContractVo().getIds()))
			conditions.add(new QueryCondition("contract.id in ('"+v.getContractVo().getIds().replace(",", "','")+"')" ));
		conditions.add(new QueryCondition("contract.isDel",QueryCondition.EQ,Po.N));
		return conditions;
	}

	@Override
	public void update(ContractDetailVo v) throws GlobalException {
		ContractDetail po = new ContractDetail();
		po = contractDetailDao.findById(v.getId());
		valVersion(v.getVersion(), po.getVersion());
		po.toPo(v, po);
		po = po.toPo(v, po);
		if(null != po.getContract()){
			Contract contract = contractDao.findById(v.getContractVo().getId());
			po.setContract(contract);
		}
		contractDetailDao.update(po);
		v.setId(po.getId());
	}

	@Override
	public void add(ContractDetailVo v) throws GlobalException {
		ContractDetail po = new ContractDetail();
		po = po.toPo(v, po);
		if(null != po.getContract()){
			Contract contract = contractDao.findById(v.getContractVo().getId());
			po.setContract(contract);
		}
		contractDetailDao.add(po);
		v.setId(po.getId());
	}

	@Override
	public void data2Vo(String[] values, ContractDetailVo v, String param) throws GlobalException {
		if(StrUtils.isBlankOrNull(param))
			throw new GlobalException("参数异常");
		ContractVo contractVo = new ContractVo();
		contractVo.setId(param);
		v.setContractVo(contractVo);
		v.setSampName(values[1]);
		v.setSampType(values[2]);
		v.setItem(values[3]);
		v.setStandard(values[4]);
		v.setBatch(values[5]);
		v.setPrice(values[6]);
	}

	
	@Override
	public List<ContractDetailVo> list(ContractDetailVo v) throws GlobalException {
		if(null!=v.getContractVo()){
			return toVoList(contractDetailDao.listByconId(v.getContractVo().getId()));
		}else{
			return super.list(v);
		}
	}

	@Override
	public List<ContractDetailVo> listByConId(String conId) throws GlobalException {
		List<ContractDetailVo> list = toVoList(contractDetailDao.listByconId(conId));
		return list;
	}
	
	
	
}


