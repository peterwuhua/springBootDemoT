package cn.demi.cus.contract.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.contract.dao.IContractExecuteDao;
import cn.demi.cus.contract.po.ContractExecute;
/**
 * Create on : 2017年1月9日 下午6:30:56  <br>
 * Description : 合同执行情况daoImpl <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Repository("cus.contract.executeDao")
public class ContractExecuteDaoImpl extends BaseDaoImpl<ContractExecute> implements IContractExecuteDao {

	@Override
	public List<ContractExecute> listByconId(String conId) throws GlobalException {
		List<QueryCondition> querylist = new ArrayList<QueryCondition>();
		querylist.add(new QueryCondition("contract.id", QueryCondition.EQ, conId));
		return super.list(querylist);
	}
}
