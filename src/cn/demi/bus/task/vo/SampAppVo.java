package cn.demi.bus.task.vo;

import cn.core.framework.common.vo.Vo;

public class SampAppVo extends Vo<SampAppVo> {
 
	/**
	 * 业务Id
	 */
	private String busId;
	/**
	 * 设备信息
	 */
	private String appId;
	private String appName;
	private String appCode;//编号
	/**
	 * 备注
	 */
	private String remark;
	 
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
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
}

