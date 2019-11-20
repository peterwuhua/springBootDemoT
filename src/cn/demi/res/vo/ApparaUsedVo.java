package cn.demi.res.vo;

import cn.core.framework.common.vo.Vo;

public class ApparaUsedVo extends Vo<ApparaUsedVo>{
	
	
	private ApparaVo apparaVo;
	private String busId;
	/**
	 *仪器使用状态
	 */
	private String appStat;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 时长
	 */
	private String duration;
	/**
	 * 使用人部门
	 */
	private String orgId;
	private String orgName;
	/**
	 * 使用人
	 */
	private String userId;
	private String userName;
	//用途
	private String purpose;
	//备注
	private String remark;
	public ApparaVo getApparaVo() {
		return apparaVo;
	}
	public void setApparaVo(ApparaVo apparaVo) {
		this.apparaVo = apparaVo;
	}
	public String getAppStat() {
		return appStat;
	}
	public void setAppStat(String appStat) {
		this.appStat = appStat;
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	
}
