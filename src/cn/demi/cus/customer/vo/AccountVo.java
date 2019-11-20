package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;

public class AccountVo extends Vo<AccountVo> {
	
	/**
	 * 用户信息Id
	 */
	private CustomerVo customerVo;
	/**
	 * 登陆名称
	 */
	private String loginName;
	/**
	 * 登陆的密码
	 */
	private String password;
	/**
	 * 是否使用
	 */
	private String isUse;
	
	private String roleIds;
	
	
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public CustomerVo getCustomerVo() {
		return customerVo;
	}
	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
	}
	public String getLoginName() {
		return loginName;
	}
	
	
}

