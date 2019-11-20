package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class AccountRoleVo extends Vo<AccountRoleVo> {
	/**
	 * accountVo
	 */
	private AccountVo accountVo;
	/**
	 * roleVo
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
