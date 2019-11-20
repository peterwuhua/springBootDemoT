package cn.demi.init.ti.vo;

import cn.core.framework.common.vo.Vo;

public class TaskInitVo extends Vo<TaskInitVo> {
	/**
	 * 检测项目
	 */
	private String itemId;
	private String itemName;
	private String ename;
	/**
	 * 方法
	 */
	private String methodId;
	private String methodName;
	/**
	 *仪器
	 */
	private String appId;
	private String appName;
	//部门
	private String orgId;
	private String orgName;
	//录入人
	private String userId;
	private String userName;
	//采样流量
	private String ll;
	//采样时长
	private String hours;
	/**
	 * 所需样品
	 */	
	private String sampName;
	private String jzId;//采样介质
	private String jzName;
	/**
	 * 运储条件
	 */	
	private String conditions;
	//能力范围
	private String isMay;
	private String gdj;
	private String remark;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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

	public String getLl() {
		return ll;
	}

	public void setLl(String ll) {
		this.ll = ll;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getSampName() {
		return sampName;
	}

	public void setSampName(String sampName) {
		this.sampName = sampName;
	}

	 

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getIsMay() {
		return isMay;
	}

	public void setIsMay(String isMay) {
		this.isMay = isMay;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getJzId() {
		return jzId;
	}

	public void setJzId(String jzId) {
		this.jzId = jzId;
	}

	public String getJzName() {
		return jzName;
	}

	public void setJzName(String jzName) {
		this.jzName = jzName;
	}

	public String getGdj() {
		return gdj;
	}

	public void setGdj(String gdj) {
		this.gdj = gdj;
	}
}

