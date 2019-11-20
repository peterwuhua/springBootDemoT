package cn.demi.res.vo;

import cn.core.framework.common.vo.Vo;

public class MaintenanceVo extends Vo<MaintenanceVo> {
	private ApparaVo apparaVo; //仪器
	private String diagnosis; //故障描述
    private String maintenanceMethod; //维修方法
    private String maintenanceBegin; //维修开始时间
    private String maintenanceEnd; //维修结束时间
    private String maintenancePersonnel; //维修人
    private String maintenanceContent; //维修内容
    private String maintenancePrice; //维修金额
    private String status; //维修或保养状态
    private String remark; //备注
    
	public ApparaVo getApparaVo() {
		return apparaVo;
	}
	public void setApparaVo(ApparaVo apparaVo) {
		this.apparaVo = apparaVo;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getMaintenanceMethod() {
		return maintenanceMethod;
	}
	public void setMaintenanceMethod(String maintenanceMethod) {
		this.maintenanceMethod = maintenanceMethod;
	}
	public String getMaintenanceBegin() {
		return maintenanceBegin;
	}
	public void setMaintenanceBegin(String maintenanceBegin) {
		this.maintenanceBegin = maintenanceBegin;
	}
	public String getMaintenanceEnd() {
		return maintenanceEnd;
	}
	public void setMaintenanceEnd(String maintenanceEnd) {
		this.maintenanceEnd = maintenanceEnd;
	}
	public String getMaintenancePersonnel() {
		return maintenancePersonnel;
	}
	public void setMaintenancePersonnel(String maintenancePersonnel) {
		this.maintenancePersonnel = maintenancePersonnel;
	}
	public String getMaintenanceContent() {
		return maintenanceContent;
	}
	public void setMaintenanceContent(String maintenanceContent) {
		this.maintenanceContent = maintenanceContent;
	}
	public String getMaintenancePrice() {
		return maintenancePrice;
	}
	public void setMaintenancePrice(String maintenancePrice) {
		this.maintenancePrice = maintenancePrice;
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

