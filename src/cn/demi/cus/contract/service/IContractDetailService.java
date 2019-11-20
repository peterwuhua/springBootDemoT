package cn.demi.cus.contract.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.contract.vo.ContractDetailVo;
/**
 * Create on : 2017年1月9日 下午6:28:51  <br>
 * Description :  合同项目详情Iservice<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Transactional
public interface IContractDetailService extends IBaseService<ContractDetailVo> {
 	/**
 	 * Create on : Danlee Li 2017年3月30日 下午2:11:12 <br>
 	 * Description : 根据主记录Id查找当前合同明细 <br>
 	 * @param gridVo GridVo
 	 * @param conId conId
 	 * @return
 	 * @throws GlobalException
 	 */
	public List<ContractDetailVo> listByConId(String conId) throws GlobalException;
}
