package cn.demi.cus.contract.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.contract.po.ContractDetail;
/**
 * Create on : 2017年1月9日 下午6:31:16  <br>
 * Description : 合同项目详情Idao <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public interface IContractDetailDao extends IBaseDao<ContractDetail> {
	/**
	 * Create on : Danlee Li 2017年3月13日 上午11:08:41 <br>
	 * Description : 通过合同查找客户详情 <br>
	 * @param conId
	 * @return
	 * @throws GlobalException
	 */
	public List<ContractDetail> listByconId(String conId) throws GlobalException;
	
}

