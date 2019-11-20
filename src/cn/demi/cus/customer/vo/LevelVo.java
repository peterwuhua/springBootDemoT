package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;
/**
 * Create on : 2016年11月15日 下午2:51:57  <br>
 * Description : 客户等级Vo<br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public class LevelVo extends Vo<LevelVo> {
	
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 说明
	 */
	private String remark;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

