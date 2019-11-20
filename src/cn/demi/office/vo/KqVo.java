package cn.demi.office.vo;

import javax.persistence.Column;

import cn.core.framework.common.vo.Vo;

public class KqVo extends Vo<KqVo> {
	private String no;//编号
	private String type; //请假类型(出勤、加班、事假、婚假、哺乳假、公差、旷工、公休、调休)
	private String orgName; //一级部门
	private String orgId; //一级部门id
	private String deptId;//二级部门
	private String deptName;//部门
	private String userId;
	private String userName;//人
	private String date;//申请时间
	private String startTime;//开始时间
	private String endTime;//截止时间
	private int hours;//时长
	private String content;//描述
	private String remark;//备注
	private String jober; //职位代理人
	private String joberId; //职位代理人id
	
	private String status;//状态
	private String fstatus; //已保存，审核中，已通过，已销假
	private String busId;//业务id 外勤从采样安排来的采样安排任务
	//一级审核信息
	private String auditId;
	private String auditName;//审核人
	private String auditDate;//审核日期
	private String auditResult;//审核状态
	private String auditMsg;//审核备注
	//二级审核信息
	private String shId;
	private String shName;//审核人
	private String shDate;//审核日期
	private String shResult;//审核状态
	private String shMsg;//审核备注
	//三级审核信息
	private String spId;
	private String spName;//审批人
	private String spDate;//审批日期
	private String spResult;//审批状态
	private String spMsg;//审批备注
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(length = 20)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public String getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	public String getAuditMsg() {
		return auditMsg;
	}
	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
	public String getShId() {
		return shId;
	}
	public void setShId(String shId) {
		this.shId = shId;
	}
	public String getShName() {
		return shName;
	}
	public void setShName(String shName) {
		this.shName = shName;
	}
	public String getShDate() {
		return shDate;
	}
	public void setShDate(String shDate) {
		this.shDate = shDate;
	}
	public String getShResult() {
		return shResult;
	}
	public void setShResult(String shResult) {
		this.shResult = shResult;
	}
	public String getShMsg() {
		return shMsg;
	}
	public void setShMsg(String shMsg) {
		this.shMsg = shMsg;
	}
	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public String getSpDate() {
		return spDate;
	}
	public void setSpDate(String spDate) {
		this.spDate = spDate;
	}
	public String getSpResult() {
		return spResult;
	}
	public void setSpResult(String spResult) {
		this.spResult = spResult;
	}
	public String getSpMsg() {
		return spMsg;
	}
	public void setSpMsg(String spMsg) {
		this.spMsg = spMsg;
	}
	public String getJober() {
		return jober;
	}
	public void setJober(String jober) {
		this.jober = jober;
	}
	public String getJoberId() {
		return joberId;
	}
	public void setJoberId(String joberId) {
		this.joberId = joberId;
	}
}

