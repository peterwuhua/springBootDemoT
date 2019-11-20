package cn.demi.cus.contract.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.contract.vo.ContractExecuteVo;
/**
 * Create on : 2017年1月9日 下午6:29:11  <br>
 * Description : 合同执行情况Iservice <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Transactional
public interface IContractExecuteService extends IBaseService<ContractExecuteVo> {
	
	/**
 	 * Create on : Danlee Li 2017年3月30日 下午2:11:12 <br>
 	 * Description : 根据主记录Id查找当前合同执行情况 <br>
 	 * @param gridVo GridVo
 	 * @param conId conId
 	 * @return
 	 * @throws GlobalException
 	 */
	public List<ContractExecuteVo> listByConId(String conId) throws GlobalException;
}
