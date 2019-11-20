package cn.demi.bus.pjt.po;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 现场踏勘
 * @author QuJunLong
 */
@Entity(name = "bus_survey")
@Table(name = "bus_survey")
@Module(value = "bus.survey")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Survey extends Po<Survey>{

	private static final long serialVersionUID = 3095979671398723066L;

	public String[] PROPERTY_TO_MAP = {"id","sort","isDel","project","userName","tdate","createTime","lastUpdTime"};
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","project"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	private Project project;//项目信息
	private String buildUnit;//建设单位名称
	private String name;//项目名称
	private String productionDate; //工程开工建设及投产时间
    private String constructUnit; //环保设施设计及施工单位
    private String designCapacity; //项目设计生产能力
    private String nowCapacity; //项目目前生产能力
    private String staging; //整体或分期验监测
    private String inspectDate; //现场踏勘时间
    private String wasteWater; //废水总排口
    private String wasteMeasures;//废水治理措施
    private String rain; //雨水总排口
    private String rainMeasures;//雨水治理措施
    private String exhaustGas ; //废气总排口
    private String GasMeasures;//废气治理措施
    private String solidWaste ; //固体废物
    private String noise; //噪声
    private String wasteCapacity; //废水处理能力
    private String gasCapacity; //废气处理能力
    private String wasteDevice; //废水排口在线装置情况
    private String gasDevice; //废气排口在线装置情况
    private String emergencyPlan; //应急预案及事故应急池
    private String standardiz; //排污口设置及规范化情况
    private String solidAgreement; //固体废物处理协议签订情况
    private String wasteAgreement; //废水处理协议签订情况
    private String otherConditor; //其他相关情况
    private String itemHpUnit;//项目环评单位
    private String itemHpTime;//项目环评时间
    private String mainName;//主管部门
    
    private String userId;//踏勘人
    private String userName;
    private String tdate;//踏勘日期
    
    private String remark;
    
	@ManyToOne
	@JoinColumn(name = "project_id")
    public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@Column(length = 64)
	public String getBuildUnit() {
		return buildUnit;
	}
	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}
	@Column(length = 64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 20)
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	@Column(length = 64)
	public String getConstructUnit() {
		return constructUnit;
	}
	public void setConstructUnit(String constructUnit) {
		this.constructUnit = constructUnit;
	}
	@Column(length = 64)
	public String getDesignCapacity() {
		return designCapacity;
	}
	public void setDesignCapacity(String designCapacity) {
		this.designCapacity = designCapacity;
	}
	@Column(length = 64)
	public String getNowCapacity() {
		return nowCapacity;
	}
	public void setNowCapacity(String nowCapacity) {
		this.nowCapacity = nowCapacity;
	}
	@Column(length = 64)
	public String getStaging() {
		return staging;
	}
	public void setStaging(String staging) {
		this.staging = staging;
	}
	@Column(length = 20)
	public String getInspectDate() {
		return inspectDate;
	}
	public void setInspectDate(String inspectDate) {
		this.inspectDate = inspectDate;
	}
	@Column(length = 64)
	public String getWasteWater() {
		return wasteWater;
	}
	public void setWasteWater(String wasteWater) {
		this.wasteWater = wasteWater;
	}
	@Column(length = 64)
	public String getWasteMeasures() {
		return wasteMeasures;
	}
	public void setWasteMeasures(String wasteMeasures) {
		this.wasteMeasures = wasteMeasures;
	}
	@Column(length = 64)
	public String getRain() {
		return rain;
	}
	public void setRain(String rain) {
		this.rain = rain;
	}
	@Column(length = 64)
	public String getRainMeasures() {
		return rainMeasures;
	}
	public void setRainMeasures(String rainMeasures) {
		this.rainMeasures = rainMeasures;
	}
	@Column(length = 64)
	public String getExhaustGas() {
		return exhaustGas;
	}
	public void setExhaustGas(String exhaustGas) {
		this.exhaustGas = exhaustGas;
	}
	@Column(length = 64)
	public String getGasMeasures() {
		return GasMeasures;
	}
	public void setGasMeasures(String gasMeasures) {
		GasMeasures = gasMeasures;
	}
	@Column(length = 64)
	public String getSolidWaste() {
		return solidWaste;
	}
	public void setSolidWaste(String solidWaste) {
		this.solidWaste = solidWaste;
	}
	@Column(length = 64)
	public String getNoise() {
		return noise;
	}
	public void setNoise(String noise) {
		this.noise = noise;
	}
	@Column(length = 64)
	public String getWasteCapacity() {
		return wasteCapacity;
	}
	public void setWasteCapacity(String wasteCapacity) {
		this.wasteCapacity = wasteCapacity;
	}
	@Column(length = 64)
	public String getGasCapacity() {
		return gasCapacity;
	}
	public void setGasCapacity(String gasCapacity) {
		this.gasCapacity = gasCapacity;
	}
	@Column(length = 64)
	public String getWasteDevice() {
		return wasteDevice;
	}
	public void setWasteDevice(String wasteDevice) {
		this.wasteDevice = wasteDevice;
	}
	@Column(length = 64)
	public String getGasDevice() {
		return gasDevice;
	}
	public void setGasDevice(String gasDevice) {
		this.gasDevice = gasDevice;
	}
	@Column(length = 64)
	public String getEmergencyPlan() {
		return emergencyPlan;
	}
	public void setEmergencyPlan(String emergencyPlan) {
		this.emergencyPlan = emergencyPlan;
	}
	@Column(length = 64)
	public String getStandardiz() {
		return standardiz;
	}
	public void setStandardiz(String standardiz) {
		this.standardiz = standardiz;
	}
	@Column(length = 64)
	public String getSolidAgreement() {
		return solidAgreement;
	}
	public void setSolidAgreement(String solidAgreement) {
		this.solidAgreement = solidAgreement;
	}
	@Column(length = 64)
	public String getWasteAgreement() {
		return wasteAgreement;
	}
	public void setWasteAgreement(String wasteAgreement) {
		this.wasteAgreement = wasteAgreement;
	}
	@Column(length = 64)
	public String getOtherConditor() {
		return otherConditor;
	}
	public void setOtherConditor(String otherConditor) {
		this.otherConditor = otherConditor;
	}
	@Column(length = 64)
	public String getItemHpUnit() {
		return itemHpUnit;
	}
	public void setItemHpUnit(String itemHpUnit) {
		this.itemHpUnit = itemHpUnit;
	}
	@Column(length = 64)
	public String getItemHpTime() {
		return itemHpTime;
	}
	public void setItemHpTime(String itemHpTime) {
		this.itemHpTime = itemHpTime;
	}
	@Column(length = 64)
	public String getMainName() {
		return mainName;
	}
	public void setMainName(String mainName) {
		this.mainName = mainName;
	}
	@Column(length = 320)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 320)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 20)
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Survey.class, true, ActionType.JSP);
	}
}
