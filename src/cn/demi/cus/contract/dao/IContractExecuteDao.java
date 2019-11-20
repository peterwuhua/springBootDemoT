package cn.demi.cus.contract.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.contract.po.ContractExecute;
/**
 * Create on : 2017年1月9日 下午6:31:47  <br>
 * Description : 合同执行情况daoImpl <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public interface IContractExecuteDao extends IBaseDao<ContractExecute> {
	/**
	 * Create on : Danlee Li 2017年3月13日 上午11:08:41 <br>
	 * Description : 通过合同查找合同执行情况 <br>
	 * @param conId
	 * @return
	 * @throws GlobalException
	 */
	public List<ContractExecute> listByconId(String conId) throws GlobalException;
	
}

