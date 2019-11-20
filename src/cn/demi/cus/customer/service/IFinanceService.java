package cn.demi.cus.customer.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.FinanceVo;
/**
 * Create on : 2016年11月15日 下午2:55:11  <br>
 * Description : 账户信息service <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Transactional
public interface IFinanceService extends IBaseService<FinanceVo> {
	
	/**
	 * Create on : Danlee Li 2016年11月16日 下午2:42:56 <br>
	 * Description :根据客户ID获取JSON分页对象  <br>
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Tab(GridVo gridVo,FinanceVo v) throws GlobalException;
}
