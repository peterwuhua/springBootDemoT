package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;


public class SmsVo extends Vo<SmsVo>{
	/**
	 * 备注
	 */
	private String remark;//备注
	/**
	 * 收件人
	 */
	private String receiver; //收件人
	/**
	 * 主信息
	 */
	private String content;//主信息
	/**
	 * 状态
	 */
	private String status;//状态
	/**
	 * 业务模块
	 */
	private String busType;//业务模块
	/**
	 * 业务信息
	 */
	private String busInfo;//业务信息
	/**
	 * 业务ID
	 */
	private String busId;//业务ID
	/**
	 * 状态
	 */
	private String result;//状态
	/**
	 * 发送时间
	 */
	private String sendTime;//发送时间
	public String getBusInfo() {
		return busInfo;
	}
	public void setBusInfo(String busInfo) {
		this.busInfo = busInfo;
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
