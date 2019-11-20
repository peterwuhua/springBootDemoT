package cn.demi.app.offfice.vo;

import java.util.List;

/**
 * 请假申请详情
 * 
 * @author user
 *
 */
public class AppQjSqEdit {
	private String id;
	private String type;
	private String deptName;
	private String deptId;
	private String userName;
	private String userId;
	private String date;
	private String startTime;
	private String endTime;
	private String hours;
	private String jober;
	private String joberId;
	private String content;
	private String isCommit;
	private List<AppQjLogList> appQjSqLogList;

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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

	public String getJoberId() {
		return joberId;
	}

	public void setJoberId(String joberId) {
		this.joberId = joberId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsCommit() {
		return isCommit;
	}

	public void setIsCommit(String isCommit) {
		this.isCommit = isCommit;
	}

	public List<AppQjLogList> getAppQjSqLogList() {
		return appQjSqLogList;
	}

	public void setAppQjSqLogList(List<AppQjLogList> appQjSqLogList) {
		this.appQjSqLogList = appQjSqLogList;
	}

}
