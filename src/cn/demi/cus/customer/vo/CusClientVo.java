package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;
/**
 * Create on : 2016年11月15日 下午2:51:19  <br>
 * Description : 受检单位客户关联表Vo <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public class CusClientVo extends Vo<CusClientVo> {
	
	/**
	 * 受检单位
	 */
	private ClientVo clientVo;
	/**
	 * 客户信息
	 */
	private CustomerVo customerVo;
	
	public CustomerVo getCustomerVo() {
		return customerVo;
	}
	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
	}
	public ClientVo getClientVo() {
		return clientVo;
	}
	public void setClientVo(ClientVo clientVo) {
		this.clientVo = clientVo;
	}
	
	
}

