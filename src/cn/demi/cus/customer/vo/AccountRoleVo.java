package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.RoleVo;

public class AccountRoleVo extends Vo<AccountRoleVo> {
	
	/**
	 * account
	 */
	private AccountVo accountVo;
	/**
	 * role
	 */
	private RoleVo roleVo;
	
	public AccountVo getAccountVo() {
		return accountVo;
	}
	public void setAccountVo(AccountVo accountVo) {
		this.accountVo = accountVo;
	}
	public RoleVo getRoleVo() {
		return roleVo;
	}
	public void setRoleVo(RoleVo roleVo) {
		this.roleVo = roleVo;
	}
	
	
}

