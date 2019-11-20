package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class MailVo extends Vo<MailVo> {
	/**
	 * 主题
	 */
	private String subject; // 主题
	/**
	 * 电子邮件-EMAIL 短信-SMS
	 */
	private String type; // 电子邮件-EMAIL 短信-SMS
	/**
	 * 备注
	 */
	private String remark; // 备注
	/**
	 * 收件人
	 */
	private String receiver; // 收件人
	/**
	 * 主信息
	 */
	private String content;// 主信息
	/**
	 * 状态
	 */
	private String status;// 状态
	/**
	 * 状态
	 */
	private String result;// 状态
	/**
	 * 发送时间
	 */
	private String sendTime;// 发送时间
	/**
	 * 业务模块
	 */
	private String busType;// 业务模块
	/**
	 * 业务模块
	 */
	private String busInfo;// 业务模块
	/**
	 * 业务ID
	 */
	private String busId;// 业务ID

	public MailVo() {
		super();
	}

	public MailVo(String subject, String type, String remark, String receiver,
			String content, String status, String result, String sendTime,
			String busType, String busInfo, String busId) {
		super();
		this.subject = subject;
		this.type = type;
		this.remark = remark;
		this.receiver = receiver;
		this.content = content;
		this.status = status;
		this.result = result;
		this.sendTime = sendTime;
		this.busType = busType;
		this.busInfo = busInfo;
		this.busId = busId;
	}

	public String getSubject() {
		return subject;
	}

	public String getBusInfo() {
		return busInfo;
	}

	public void setBusInfo(String busInfo) {
		this.busInfo = busInfo;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
