package cn.demi.app.res.vo;

import cn.demi.res.po.Appara;

public class AppApparaout {

	private Appara appara;
	// 出库人
	private String useId;
	private String useName;
	// 出库时间
	private String useTime;
	// 仪器状态
	private String appStatus;
	// 仪器状态
	private String status;
	//留言
	private String remark;

	public Appara getAppara() {
		return appara;
	}

	public void setAppara(Appara appara) {
		this.appara = appara;
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
	
}
