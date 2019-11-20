package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class LogVo extends Vo<LogVo> {
	/**
	 * 业务模块
	 */
	private String busInfo;// 业务模块
	/**
	 * 摘要
	 */
	private String subject;// 摘要
	/**
	 * 模块
	 */
	private String function;// 模块
	/**
	 * 内容
	 */
	private String content;// 内容
	/**
	 * 方法类型
	 */
	private String type;// 方法类型
	/**
	 * ip地址
	 */
	private String ip;// ip地址
	/**
	 * 操作时间
	 */
	private String time;// 操作时间
	/**
	 * 操作者
	 */
	private String user;// 操作者

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

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

	public String getBusInfo() {
		return busInfo;
	}

	public void setBusInfo(String busInfo) {
		this.busInfo = busInfo;
	}

}