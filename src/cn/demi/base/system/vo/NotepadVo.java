package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class NotepadVo extends Vo<NotepadVo> {
	/**
	 * 状态 0未完成 1是完成
	 */
	private String state;// 状态 0未完成 1是完成
	/**
	 * 标题
	 */
	private String title;// 标题
	/**
	 * 更新内容
	 */
	private String context;// 更新内容
	/**
	 * 便签所属人
	 */
	private AccountVo accountVo;// 便签所属人
	/**
	 * 时间
	 */
	private String ftime;// 时间

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public AccountVo getAccountVo() {
		return accountVo;
	}

	public void setAccountVo(AccountVo accountVo) {
		this.accountVo = accountVo;
	}

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}
}
