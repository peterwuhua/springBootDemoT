package cn.demi.bus.pjt.vo;

import cn.core.framework.common.vo.Vo;
/**
 * 现场踏勘信息
 * @author QuJunLong
 *
 */
public class SurveyVo extends Vo<SurveyVo> {
	
	private ProjectVo projectVo;
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
	private String status;
    private String remark;
    
	public ProjectVo getProjectVo() {
		return projectVo;
	}
	public void setProjectVo(ProjectVo projectVo) {
		this.projectVo = projectVo;
	}
	public String getBuildUnit() {
		return buildUnit;
	}
	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public String getConstructUnit() {
		return constructUnit;
	}
	public void setConstructUnit(String constructUnit) {
		this.constructUnit = constructUnit;
	}
	public String getDesignCapacity() {
		return designCapacity;
	}
	public void setDesignCapacity(String designCapacity) {
		this.designCapacity = designCapacity;
	}
	public String getNowCapacity() {
		return nowCapacity;
	}
	public void setNowCapacity(String nowCapacity) {
		this.nowCapacity = nowCapacity;
	}
	public String getStaging() {
		return staging;
	}
	public void setStaging(String staging) {
		this.staging = staging;
	}
	public String getInspectDate() {
		return inspectDate;
	}
	public void setInspectDate(String inspectDate) {
		this.inspectDate = inspectDate;
	}
	public String getWasteWater() {
		return wasteWater;
	}
	public void setWasteWater(String wasteWater) {
		this.wasteWater = wasteWater;
	}
	public String getWasteMeasures() {
		return wasteMeasures;
	}
	public void setWasteMeasures(String wasteMeasures) {
		this.wasteMeasures = wasteMeasures;
	}
	public String getRain() {
		return rain;
	}
	public void setRain(String rain) {
		this.rain = rain;
	}
	public String getRainMeasures() {
		return rainMeasures;
	}
	public void setRainMeasures(String rainMeasures) {
		this.rainMeasures = rainMeasures;
	}
	public String getExhaustGas() {
		return exhaustGas;
	}
	public void setExhaustGas(String exhaustGas) {
		this.exhaustGas = exhaustGas;
	}
	public String getGasMeasures() {
		return GasMeasures;
	}
	public void setGasMeasures(String gasMeasures) {
		GasMeasures = gasMeasures;
	}
	public String getSolidWaste() {
		return solidWaste;
	}
	public void setSolidWaste(String solidWaste) {
		this.solidWaste = solidWaste;
	}
	public String getNoise() {
		return noise;
	}
	public void setNoise(String noise) {
		this.noise = noise;
	}
	public String getWasteCapacity() {
		return wasteCapacity;
	}
	public void setWasteCapacity(String wasteCapacity) {
		this.wasteCapacity = wasteCapacity;
	}
	public String getGasCapacity() {
		return gasCapacity;
	}
	public void setGasCapacity(String gasCapacity) {
		this.gasCapacity = gasCapacity;
	}
	public String getWasteDevice() {
		return wasteDevice;
	}
	public void setWasteDevice(String wasteDevice) {
		this.wasteDevice = wasteDevice;
	}
	public String getGasDevice() {
		return gasDevice;
	}
	public void setGasDevice(String gasDevice) {
		this.gasDevice = gasDevice;
	}
	public String getEmergencyPlan() {
		return emergencyPlan;
	}
	public void setEmergencyPlan(String emergencyPlan) {
		this.emergencyPlan = emergencyPlan;
	}
	public String getStandardiz() {
		return standardiz;
	}
	public void setStandardiz(String standardiz) {
		this.standardiz = standardiz;
	}
	public String getSolidAgreement() {
		return solidAgreement;
	}
	public void setSolidAgreement(String solidAgreement) {
		this.solidAgreement = solidAgreement;
	}
	public String getWasteAgreement() {
		return wasteAgreement;
	}
	public void setWasteAgreement(String wasteAgreement) {
		this.wasteAgreement = wasteAgreement;
	}
	public String getOtherConditor() {
		return otherConditor;
	}
	public void setOtherConditor(String otherConditor) {
		this.otherConditor = otherConditor;
	}
	public String getItemHpUnit() {
		return itemHpUnit;
	}
	public void setItemHpUnit(String itemHpUnit) {
		this.itemHpUnit = itemHpUnit;
	}
	public String getItemHpTime() {
		return itemHpTime;
	}
	public void setItemHpTime(String itemHpTime) {
		this.itemHpTime = itemHpTime;
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
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMainName() {
		return mainName;
	}
	public void setMainName(String mainName) {
		this.mainName = mainName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

