package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class SysMsgVo extends Vo<SysMsgVo> {
	/**
	 * 业务ID
	 */
	private String busId;//业务ID
	/**
	 * 业务模块
	 */
	private String busType;//业务模块
	/**
	 *标题
	 */
	private String title;
	/**
	 * 主信息
	 */
	private String content;
	/**
	 * 收件人
	 * 客户Id
	 * 账户Id
	 * 角色Id
	 * 部门Id
	 */
	private String receiver;
	private String receiverId;
	/**
	 * 收件人类型
	 * 客户cust
	 * 账户user
	 * 角色role
	 * 部门org
	 */
	private String receiverType; 
	/**
	 * 状态
	 * 0未读  1已读
	 */
	private String status;//状态
	/**
	 * 发送时间
	 */
	private String sendTime;//发送时间
	/**
	 * 发送人
	 */
	private String sendId;
	private String sendName;
	/**
	 * 备注
	 */
	private String remark;//备注
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

