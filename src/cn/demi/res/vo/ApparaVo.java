package cn.demi.res.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.base.system.vo.UserVo;


/** <strong>Create on : 2017年2月28日 下午1:35:33 </strong> <br>
 * <strong>Description :仪器设备主表VO</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
public class ApparaVo extends Vo<ApparaVo>{
	
	/**
	 * 仪器分类
	 */
	private String typeId;
	/***
	 * 检测器配置	
	 */
	private String testProperty;
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
	 * 生产国别	
	 */
	private String mcountry;
	/**
	 * 供应商	supplier
	 */
	private String supplier;
	private String supplierId;// 供应商
	/***
	 * 规格/型号	
	 */
	private String spec;
	/*** 
	 * 制造厂商	
	 */
	private String producer;
	//生产厂家电话
	private String producerPhone;
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
	private String ext01;
	/***
	 * 状态	
	 */
	private String state;
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
	 *检定日期
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
	 * 备注
	 */
	private String comment;
	/***
	 * 测量范围	
	 */
	private String measureRange;
	/***
	 * 出厂日期
	 */
	private String productDate;
	 
	/***
	 * 使用部门	
	 */
	private String userTeam;
	/***
	 * 检定单位	
	 */
	private String verificationUnit;
	/***
	 * 检定结果	
	 */
	private String verificationResult;
	/***
	 * 准确度	
	 */
	private String accuracy;
	/***
	 * 是否测量器具
	 */
	private String isMensuration;
	/***
	 * 溯源单位
	 */
	private String sourceUnit;
	//收费标准
	private float usePrice;
	//撤销时限
	private int cancelTime;
	private float duration;
	private UserVo userVo;
	//预约须知
	private String noteOrder;
	//样品准备须知
	private String noteSamp;
	//设备收费须知
	private String noteCharge;
	
	private String remark;
	//仪器logo
	private String logo;
	//仪器操作指导书
	private String fileId;
	private String fileName;
	private String filePath;
	private List<FilesVo> fileList;//附件
	
 	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTestProperty() {
		return testProperty;
	}
	public void setTestProperty(String testProperty) {
		this.testProperty = testProperty;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMcountry() {
		return mcountry;
	}
	public void setMcountry(String mcountry) {
		this.mcountry = mcountry;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getPurTime() {
		return purTime;
	}
	public void setPurTime(String purTime) {
		this.purTime = purTime;
	}
	public String getExt01() {
		return ext01;
	}
	public void setExt01(String ext01) {
		this.ext01 = ext01;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getKeeper() {
		return keeper;
	}
	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public String getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getVerificationDate() {
		return verificationDate;
	}
	public void setVerificationDate(String verificationDate) {
		this.verificationDate = verificationDate;
	}
	public String getVerificationCycle() {
		return verificationCycle;
	}
	public void setVerificationCycle(String verificationCycle) {
		this.verificationCycle = verificationCycle;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getMeasureRange() {
		return measureRange;
	}
	public void setMeasureRange(String measureRange) {
		this.measureRange = measureRange;
	}
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getUserTeam() {
		return userTeam;
	}
	public void setUserTeam(String userTeam) {
		this.userTeam = userTeam;
	}
	public String getVerificationUnit() {
		return verificationUnit;
	}
	public void setVerificationUnit(String verificationUnit) {
		this.verificationUnit = verificationUnit;
	}
	public String getVerificationResult() {
		return verificationResult;
	}
	public void setVerificationResult(String verificationResult) {
		this.verificationResult = verificationResult;
	}
	public String getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	public String getIsMensuration() {
		return isMensuration;
	}
	public void setIsMensuration(String isMensuration) {
		this.isMensuration = isMensuration;
	}
	public String getLastverificationDate() {
		return lastverificationDate;
	}
	public void setLastverificationDate(String lastverificationDate) {
		this.lastverificationDate = lastverificationDate;
	}
	public String getSourceUnit() {
		return sourceUnit;
	}
	public void setSourceUnit(String sourceUnit) {
		this.sourceUnit = sourceUnit;
	}
	public String getKeepId() {
		return keepId;
	}
	public void setKeepId(String keepId) {
		this.keepId = keepId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	public float getUsePrice() {
		return usePrice;
	}
	public void setUsePrice(float usePrice) {
		this.usePrice = usePrice;
	}
	public int getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(int cancelTime) {
		this.cancelTime = cancelTime;
	}
	public UserVo getUserVo() {
		return userVo;
	}
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
	public List<FilesVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	public float getDuration() {
		return duration;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	}
	public String getNoteOrder() {
		return noteOrder;
	}
	public void setNoteOrder(String noteOrder) {
		this.noteOrder = noteOrder;
	}
	public String getNoteSamp() {
		return noteSamp;
	}
	public void setNoteSamp(String noteSamp) {
		this.noteSamp = noteSamp;
	}
	public String getNoteCharge() {
		return noteCharge;
	}
	public void setNoteCharge(String noteCharge) {
		this.noteCharge = noteCharge;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getProducerPhone() {
		return producerPhone;
	}
	public void setProducerPhone(String producerPhone) {
		this.producerPhone = producerPhone;
	}
	public String getCalibrationDate() {
		return calibrationDate;
	}
	public void setCalibrationDate(String calibrationDate) {
		this.calibrationDate = calibrationDate;
	}
	public String getLastcalibrationDate() {
		return lastcalibrationDate;
	}
	public void setLastcalibrationDate(String lastcalibrationDate) {
		this.lastcalibrationDate = lastcalibrationDate;
	}
	public String getCalibrationCycle() {
		return calibrationCycle;
	}
	public void setCalibrationCycle(String calibrationCycle) {
		this.calibrationCycle = calibrationCycle;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}

