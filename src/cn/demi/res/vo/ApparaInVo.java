package cn.demi.res.vo;

import cn.core.framework.common.vo.Vo;

public class ApparaInVo extends Vo<ApparaInVo> {
	//仪器信息
	private ApparaVo apparaVo;
	//借出信息
	private ApparaOutVo outVo;
	private String busId;
	//采样小组
	private String cyIds;
	private String cyNames;
	//入库人部门
	private String orgId;
	private String orgName;
	//登记人
	private String userId;
	private String userName;
	//入库人
	private String useId;
	private String useName;
	//入库时间
	private String useTime;
	/**
	 * 时长
	 */
	private String duration;
	//入库人手机
	private String mobile;
	//仪器状态
	private String appStatus;
	//状态
	private String status;
	//备注
	private String remark;
	public ApparaVo getApparaVo() {
		return apparaVo;
	}
	public void setApparaVo(ApparaVo apparaVo) {
		this.apparaVo = apparaVo;
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
	public String getUseId() {
		return useId;
	}
	public void setUseId(String useId) {
		this.useId = useId;
	}
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ApparaOutVo getOutVo() {
		return outVo;
	}
	public void setOutVo(ApparaOutVo outVo) {
		this.outVo = outVo;
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
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getCyIds() {
		return cyIds;
	}
	public void setCyIds(String cyIds) {
		this.cyIds = cyIds;
	}
	public String getCyNames() {
		return cyNames;
	}
	public void setCyNames(String cyNames) {
		this.cyNames = cyNames;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
}

