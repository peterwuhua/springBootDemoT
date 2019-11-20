package cn.demi.bus.pg.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class ProgressVo extends Vo<ProgressVo> {
	/**
	 * 业务Id
	 */
	private String busId;
	/**
	 * 进度类型
	 */
	private String busType;
	/**
	 * 进度类型
	 * task/item
	 */
	private String type;
	/**
	 * 部门
	 */
	private String orgName;
	/**
	 * 部门
	 */
	private String orgId;
	/**
	 * 处理人
	 */
	private String userName;
	/**
	 * 处理人
	 */
	private String userId;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 样式
	 */
	private String classes;
	/**
	 * 备注
	 */
	private String msg;
	private List<ProgressVo> progressList;
	
	private List<ProgressLogVo> logList;
	
	private List<ProgressVo> itList;//项目进度
	
	private List<ProgressVo> reportList;//报告进度
 
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<ProgressVo> getProgressList() {
		return progressList;
	}
	public void setProgressList(List<ProgressVo> progressList) {
		this.progressList = progressList;
	}
	public List<ProgressLogVo> getLogList() {
		return logList;
	}
	public void setLogList(List<ProgressLogVo> logList) {
		this.logList = logList;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<ProgressVo> getItList() {
		return itList;
	}
	public void setItList(List<ProgressVo> itList) {
		this.itList = itList;
	}
	public List<ProgressVo> getReportList() {
		return reportList;
	}
	public void setReportList(List<ProgressVo> reportList) {
		this.reportList = reportList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}

