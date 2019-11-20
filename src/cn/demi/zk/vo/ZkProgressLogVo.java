package cn.demi.zk.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class ZkProgressLogVo extends Vo<ZkProgressLogVo> {
	/**
	 * 任务Id
	 */
	private String taskId;
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
	 * 记录日期
	 */
	private String logDate;
	/**
	 * 样式
	 */
	private String classes;
	private List<ZkProgressLogVo> logList;
	 
	private List<ZkProgressLogVo> progressList;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public List<ZkProgressLogVo> getLogList() {
		return logList;
	}
	public void setLogList(List<ZkProgressLogVo> logList) {
		this.logList = logList;
	}
	public List<ZkProgressLogVo> getProgressList() {
		return progressList;
	}
	public void setProgressList(List<ZkProgressLogVo> progressList) {
		this.progressList = progressList;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	
}

