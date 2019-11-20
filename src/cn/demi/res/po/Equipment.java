package cn.demi.res.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 仪器设备持久对象
 * @author QuJunLong
 */
@Entity(name = "res_equipment")
@Table(name = "res_equipment")
@Module(value="res.equipment")
public class Equipment extends Po<Equipment> {
	
	public static final String ST_0="正常";//仪器状态
	public static final String ST_1="异常";//仪器状态
	public static final String ST_2="停用";//仪器状态
	public static final String ST_3="报废";//仪器状态
	
	private static final long serialVersionUID = 1L;
	/**
	 * 仪器分类
	 */
	private String type;
	/***
	 * 仪器名称	
	 */
	private String name;
	/***
	 * 仪器编号	
	 */
	private String no;
	/***
	 *出厂编号	
	 */
	private String code;
	/***
	 * 规格/型号	
	 */
	private String spec;
	/***
	 * 生产国别	
	 */
	private String mcountry;
	/**
	 * 供应商	supplier
	 */
	private String supplier;
	private String supplierId;// 供应商
	/*** 
	 * 制造厂商	
	 */
	private String producer;
	private String producerPhone;//生产厂家电话
	/***
	 * 价格(元)	
	 */
	private double price;
	/***
	 * 购置日期	
	 */
	private String purTime;
	/***
	 * 放置地点	
	 */
	private String address;
	/***
	 * 状态	
	 */
	private String status;
	/***
	 * 仪器保管人	
	 */
	private String keeper;
	private String keepId;
	/***
	 * 仪器保管科室
	 */
	private String deptId;
	private String deptName;
	/***
	 * 部门信息
	 */
	private String orgId;
	private String orgName;
	//强检
	private String qj;
	/***
	 * 是否验收	
	 */
	private String isCheck;
	/***
	 * 有效期	
	 */
	private String effectDate;
	/***
	 * 创建人	
	 */
	private String createUserId;
	/***
	 *下次检定日期
	 */
	private String verificationDate;
	 
	/***
	 *上次检定日期
	 */
	private String lastverificationDate;
	/***
	 * 检定周期
	 */
	private String verificationCycle ;
	/***
	 * 检定单位	
	 */
	private String verificationUnit;
	/***
	 * 检定证书
	 */
	private String verificationNo;
	/***
	 * 检定结果	
	 */
	private String verificationResult;
	/***
	 * 下次校准日期
	 */
	private String calibrationDate;
	/***
	 * 上次校准日期
	 */
	private String lastcalibrationDate;
	/***
	 * 校准周期
	 */
	private String calibrationCycle;
	/***
	 * 测量范围	
	 */
	private String measureRange;
	/***
	 * 出厂日期
	 */
	private String productDate;
	 

	/***
	 * 准确度
	 */
	private String accuracy;
	/***
	 * 溯源单位
	 */
	private String sourceUnit;
	/***
	 * 备注
	 */
	private String remark;
	private String logo;//仪器logo
	 
	public String[] PROPERTY_TO_MAP= {"id","type","name","no","code","mcountry","supplier","spec","producer","price","purTime","address","status","keeper","keeperId",
	"isCheck","createUserId","verificationDate","calibrationDate","inspectDate","verificationCycle","calibrationCycle","inspectCycle","measureRange","productDate",
	"verificationUnit","verificationResult","accuracy","lastverificationDate","lastcalibrationDate","lastinspectDate","sourceUnit","deptName","orgName"};
	
	
	@Column(length = 64)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 64)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length = 64)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length = 64)
	public String getMcountry() {
		return mcountry;
	}
	public void setMcountry(String mcountry) {
		this.mcountry = mcountry;
	}
	@Column(length = 64)
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	@Column(length = 64)
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	@Column(length = 64)
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	@Column(length = 16)
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Column(length = 20)
	public String getLastverificationDate() {
		return lastverificationDate;
	}
	public void setLastverificationDate(String lastverificationDate) {
		this.lastverificationDate = lastverificationDate;
	}
	@Column(length = 20)
	public String getPurTime() {
		return purTime;
	}
	public void setPurTime(String purTime) {
		this.purTime = purTime;
	}
	@Column(length = 64)
	public String getKeeper() {
		return keeper;
	}
	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}
	@Column(length = 32)
	public String getKeepId() {
		return keepId;
	}
	public void setKeepId(String keepId) {
		this.keepId = keepId;
	}

	@Column(length = 32)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(length = 64)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(length = 64)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length = 64)
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	@Column(length = 20)
	public String getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
	@Column(length = 64)
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@Column(length = 20)
	public String getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}
	@Column(length = 20)
	public String getVerificationCycle() {
		return verificationCycle;
	}
	public void setVerificationCycle(String verificationCycle) {
		this.verificationCycle = verificationCycle;
	}
	@Column(length = 64)
	public String getMeasureRange() {
		return measureRange;
	}
	public void setMeasureRange(String measureRange) {
		this.measureRange = measureRange;
	}
	@Column(length = 20)
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	@Column(length = 64)
	public String getVerificationUnit() {
		return verificationUnit;
	}
	public void setVerificationUnit(String verificationUnit) {
		this.verificationUnit = verificationUnit;
	}
	@Column(length = 64)
	public String getVerificationResult() {
		return verificationResult;
	}
	public void setVerificationResult(String verificationResult) {
		this.verificationResult = verificationResult;
	}
	@Column(length = 64)
	public String getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	@Column(length = 64)
	public String getSourceUnit() {
		return sourceUnit;
	}
	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit = sourceUnit;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 256)
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@Column(length = 64)
	public String getProducerPhone() {
		return producerPhone;
	}
	public void setProducerPhone(String producerPhone) {
		this.producerPhone = producerPhone;
	}
	@Column(length = 64)
	public String getCalibrationDate() {
		return calibrationDate;
	}
	public void setCalibrationDate(String calibrationDate) {
		this.calibrationDate = calibrationDate;
	}
	@Column(length = 64)
	public String getLastcalibrationDate() {
		return lastcalibrationDate;
	}
	public void setLastcalibrationDate(String lastcalibrationDate) {
		this.lastcalibrationDate = lastcalibrationDate;
	}
	@Column(length = 16)
	public String getCalibrationCycle() {
		return calibrationCycle;
	}
	public void setCalibrationCycle(String calibrationCycle) {
		this.calibrationCycle = calibrationCycle;
	}
	@Column(length = 32)
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	@Column(length = 128)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length = 32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 32)
	public String getQj() {
		return qj;
	}
	public void setQj(String qj) {
		this.qj = qj;
	}
	@Column(length = 32)
	public String getVerificationNo() {
		return verificationNo;
	}
	public void setVerificationNo(String verificationNo) {
		this.verificationNo = verificationNo;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Equipment.class, false, ActionType.JSP);
	}
	@Transient
	@Override
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
}
