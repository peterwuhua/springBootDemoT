package cn.demi.res.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name = "res_maintenance")
@Table(name = "res_maintenance")
@Module(value="res.maintenance")
public class Maintenance extends Po<Maintenance> {
	private static final long serialVersionUID = 1L;
	private Appara appara; //仪器
	private String diagnosis; //故障描述
    private String maintenanceMethod; //维修方法
    private String maintenanceBegin; //维修开始时间
    private String maintenanceEnd; //维修结束时间
    private String maintenancePersonnel; //维修人
    private String maintenanceContent; //维修内容
    private String maintenancePrice; //维修金额
    private String status; //维修或保养状态
    private String remark; //备注
    /**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"appara","diagnosis","maintenanceMethod","maintenanceBegin","maintenanceEnd","maintenancePersonnel","maintenanceContent",
			"maintenancePrice","status","remark","id"};
	
    @ManyToOne
	@JoinColumn(name = "appara_id")
    public Appara getAppara() {
		return appara;
	}
	public void setAppara(Appara appara) {
		this.appara = appara;
	}
	@Column(length=512)
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
	@Column(length=512)
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
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Maintenance.class, false, ActionType.JSP);
	}
	@Transient
	@Override
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
}
