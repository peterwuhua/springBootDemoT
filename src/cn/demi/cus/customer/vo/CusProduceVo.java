package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;

/**
 * Create on : 2016年11月15日 下午2:51:28  <br>
 * Description : 生产单位客户关联表Vo   <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public class CusProduceVo extends Vo<CusProduceVo> {
	/**
	 * 生产单位
	 */
	private ProduceVo produceVo;
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
	public ProduceVo getProduceVo() {
		return produceVo;
	}
	public void setProduceVo(ProduceVo produceVo) {
		this.produceVo = produceVo;
	}
	
	
	
}

