package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class AccountOrgVo extends Vo<AccountOrgVo> {
	/**
	 * accountVo
	 */
	private AccountVo accountVo;
	/**
	 * orgVo
	 */
	private OrgVo orgVo;

	public AccountVo getAccountVo() {
		return accountVo;
	}

	public void setAccountVo(AccountVo accountVo) {
		this.accountVo = accountVo;
	}

	public OrgVo getOrgVo() {
		return orgVo;
	}

	public void setOrgVo(OrgVo orgVo) {
		this.orgVo = orgVo;
	}

}
