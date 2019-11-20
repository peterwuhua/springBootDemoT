package cn.demi.app.offfice.vo;

import java.util.List;

/**
 * 请假审核 详情
 * 
 * @author user
 *
 */
public class AppQjAuditEdit {
	private String id;
	private String type;
	private String deptName;
	private String userName;
	private String date;
	private String startTime;
	private String endTime;
	private String hours;
	private String jober;
	private String content;
	private String auditMsg;
	private String auditName;
	private String auditId;
	private String auditDate;
	private String isCommit;
	private List<AppQjLogList> qjLogList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getJober() {
		return jober;
	}

	public void setJober(String jober) {
		this.jober = jober;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	

	public String getIsCommit() {
		return isCommit;
	}

	public void setIsCommit(String isCommit) {
		this.isCommit = isCommit;
	}

	public List<AppQjLogList> getQjLogList() {
		return qjLogList;
	}

	public void setQjLogList(List<AppQjLogList> qjLogList) {
		this.qjLogList = qjLogList;
	}

}
