package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class NoticeVo extends Vo<NoticeVo> {
	/**
	 * 主题
	 */
	private String subject;//主题
	/**
	 * 部门id
	 */
	private String orgId;
	/**
	 * 部门
	 */
	private String orgName;
	/**
	 *发布人
	 */
	private String userId;
	/**
	 * 发布人
	 */
	private String userName;
	/**
	 * 主信息
	 */
	private String content;//主信息
	/**
	 * 状态
	 */
	private String status;//状态
	
	private String createUser;
	/**
	 * 发送时间
	 */
	private String sendTime;//发送时间
	/**
	 * 有效期
	 */
	private String endTime;//有效期
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCreateUser() {
		if(createUser!=null) {
			createUser=createUser.substring(0, createUser.indexOf("【"));
		}
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
}
