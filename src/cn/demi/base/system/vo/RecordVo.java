package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class RecordVo extends Vo<RecordVo> {
	/**
	 * 更新时间
	 */
	private String time;// 更新时间
	/**
	 * 更新人
	 */
	private String user;// 更新人
	/**
	 * 摘要
	 */
	private String title;// 摘要
	/**
	 * 更新内容
	 */
	private String context;// 更新内容
	/**
	 * 版本
	 */
	private String versionNum;// 版本

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

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

	public String getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

}
