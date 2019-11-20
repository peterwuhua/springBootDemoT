package cn.demi.bus.pg.vo;

import cn.core.framework.common.vo.Vo;

public class ProgressLogVo extends Vo<ProgressLogVo> {
	/**
	 * 任务Id
	 */
	private String taskId;
	/**
	 * 业务Id
	 */
	private String busId;
	/**
	 * 业务类型
	 */
	private String busType;
	/**
	 * 部门
	 */
	private String orgId;
	/**
	 * 部门
	 */
	private String orgName;
	/**
	 * 处理人
	 */
	private String userId;
	/**
	 * 处理人
	 */
	private String userName;
	/**
	 * 审批码
	 */
	private String audit;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 备注
	 */
	private String msg;
	/**
	 * 记录时间
	 */
	private String logTime;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
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
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	
}

