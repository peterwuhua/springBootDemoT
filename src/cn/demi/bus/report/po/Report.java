package cn.demi.bus.report.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.task.po.Task;

/**
 * 报告信息
 * @author QuJunLong
 */
@Entity(name = "bus_report")
@Table(name = "bus_report")
@Module(value = "bus.report")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Report extends Po<Report>{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","no","task","reportNo","cust","reportDate","status","progress","finishDate","isBack","makeDate",
			"auditDate","signDate","sendDate","fileDate","taskType","sampTypeName","sampName","reptDate","reptUser","testDate","testEndDate","jcct","fslx","fpUser","fpUserId","fpDate"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 对象转换为po需要例外的属性
	 */
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version","reportNo","task"};
	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}
	/**
	 * 任务
	 */
	private Task task;
	private Cust cust;//客户信息
	private String taskType;//测试类型
	/**
	 * 报告编号
	 */
	private String reportNo;
	private String sampTypeId;//样品类型
	private String sampTypeName;
	private String sampType;
	private String sampName;//样品名称
	/**
	 *报告日期
	 */
	private String reportDate;
	private String testDate;//分析日期
	private String testEndDate;//分析日期
	private String cyDate;//采样日期
	private String cyEndDate;//采样日期
	
	/**
	 * 备注
	 */
	private String remark;
	
	private String orgId;//受理部门
	private String orgName;
	
	private String jhsm;//检测计划和程序说明
	private String jcct;//检测内容
	private String jssm;//解释和说明
	private String testResult;//监测目的
	private String standIds;//检测依据
	private String standNames;
	private String result;//结论
	private String jchj;//检测环境
	/**
	 * 分配人
	 */
	private String fpUser;
	private String fpUserId;
	private String fpDate;
	
	
	/**
	 * 编制人
	 */
	private String makeUser;
	private String makeUserId;
	private String makeDate;
	/**
	 * 复核人
	 */
	private String reptUser;
	private String reptUserId;
	private String reptDate;
	private String reptMsg;
	/**
	 * 审核人
	 */
	private String auditUser;
	private String auditUserId;
	private String auditDate;
	private String auditMsg;
	/**
	 * 签发人
	 */
	private String signUser;
	private String signUserId;
	private String signDate;
	private String signMsg;
	/**
	 * 发送人
	 */
	private String sendUser;
	private String sendUserId;
	private String sendDate;
	private String sendMsg;
	/**
	 * 归档人
	 */
	private String fileUser;
	private String fileUserId;
	private String fileDate;
	private String fileMsg;
	
	private String sendType;
	private String toUser;
	private String demo;
	/**
	 * 报告归档位置
	 */
	private String position;
	private String categoryId;//电子文档
	private String categoryName;//电子文档
	/**
	 *要求完成时间
	 */
	private String finishDate;
 
	private Progress progress;
	private String status;
	 /**
     * 报告
     */
    private String filePath;
    private String fileName;
    private String pdfPath;
    private String pdfName;
    /**
     * 定期汇总报告
     */
    private String filePath2;
    private String fileName2;
    private String pdfPath2;
    private String pdfName2;
	/**
	 * 报告模板
	 */
	private String template;
	
	private String isBack;
	
	private String fslx;//放射类型（空气比释动能率，剂量当量率）
	
	
	@Column(length = 256)
	public String getFslx() {
		return fslx;
	}
	public void setFslx(String fslx) {
		this.fslx = fslx;
	}
	@ManyToOne
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}
	public void setCust(Cust cust) {
		this.cust = cust;
	}
	@ManyToOne
	@JoinColumn(name = "progress_id")
	public Progress getProgress() {
		return progress;
	}
	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	@Column(length = 32)
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	@Column(length = 20)
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 64)
	public String getMakeUser() {
		return makeUser;
	}
	public void setMakeUser(String makeUser) {
		this.makeUser = makeUser;
	}
	@Column(length = 20)
	public String getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}
	@Column(length = 64)
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	@Column(length = 20)
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	@Column(length = 64)
	public String getSignUser() {
		return signUser;
	}
	public void setSignUser(String signUser) {
		this.signUser = signUser;
	}
	@Column(length = 20)
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	@Column(length = 20)
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	@Column(length = 256)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length = 128)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length = 64)
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	@Column(length = 32)
	public String getMakeUserId() {
		return makeUserId;
	}
	public void setMakeUserId(String makeUserId) {
		this.makeUserId = makeUserId;
	}
	@Column(length = 32)
	public String getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	@Column(length = 32)
	public String getSignUserId() {
		return signUserId;
	}
	public void setSignUserId(String signUserId) {
		this.signUserId = signUserId;
	}
	@Column(length = 32)
	public String getFileUser() {
		return fileUser;
	}
	public void setFileUser(String fileUser) {
		this.fileUser = fileUser;
	}
	@Column(length = 32)
	public String getFileUserId() {
		return fileUserId;
	}
	public void setFileUserId(String fileUserId) {
		this.fileUserId = fileUserId;
	}
	@Column(length = 20)
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	@Column(length = 256)
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Column(length = 8)
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	@Column(length = 32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 32)
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	@Column(length = 32)
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	@Column(length = 20)
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length = 32)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length = 32)
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	@Column(length = 32)
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	@Column(length = 256)
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	@Column(length = 128)
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	@Column(length = 64)
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	@Column(length = 2000)
	public String getAuditMsg() {
		return auditMsg;
	}
	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}
	@Column(length = 2000)
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	@Column(length = 32)
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	@Column(length = 32)
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	@Column(length = 128)
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Column(length = 320)
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length = 640)
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	@Column(length = 128)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length = 320)
	public String getStandIds() {
		return standIds;
	}
	public void setStandIds(String standIds) {
		this.standIds = standIds;
	}
	@Column(length = 320)
	public String getStandNames() {
		return standNames;
	}
	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}
	@Column(length = 1000)
	public String getTestResult() {
		return testResult;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	@Column(length = 1000)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Column(length = 32)
	public String getReptUser() {
		return reptUser;
	}
	public void setReptUser(String reptUser) {
		this.reptUser = reptUser;
	}
	@Column(length = 32)
	public String getReptUserId() {
		return reptUserId;
	}
	public void setReptUserId(String reptUserId) {
		this.reptUserId = reptUserId;
	}
	@Column(length = 20)
	public String getReptDate() {
		return reptDate;
	}
	public void setReptDate(String reptDate) {
		this.reptDate = reptDate;
	}
	@Column(length = 2000)
	public String getReptMsg() {
		return reptMsg;
	}
	public void setReptMsg(String reptMsg) {
		this.reptMsg = reptMsg;
	}
	@Column(length = 32)
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	@Column(length = 1000)
	public String getJhsm() {
		return jhsm;
	}
	public void setJhsm(String jhsm) {
		this.jhsm = jhsm;
	}
	@Column(length = 1000)
	public String getJcct() {
		return jcct;
	}
	public void setJcct(String jcct) {
		this.jcct = jcct;
	}
	@Column(length = 1000)
	public String getJssm() {
		return jssm;
	}
	public void setJssm(String jssm) {
		this.jssm = jssm;
	}
	@Column(length = 1000)
	public String getJchj() {
		return jchj;
	}
	public void setJchj(String jchj) {
		this.jchj = jchj;
	}
	@Column(length = 20)
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	@Column(length = 20)
	public String getTestEndDate() {
		return testEndDate;
	}
	public void setTestEndDate(String testEndDate) {
		this.testEndDate = testEndDate;
	}
	@Column(length = 20)
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}
	@Column(length = 20)
	public String getCyEndDate() {
		return cyEndDate;
	}
	public void setCyEndDate(String cyEndDate) {
		this.cyEndDate = cyEndDate;
	}
	@Column(length = 256)
	public String getFilePath2() {
		return filePath2;
	}
	public void setFilePath2(String filePath2) {
		this.filePath2 = filePath2;
	}
	@Column(length = 128)
	public String getFileName2() {
		return fileName2;
	}
	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}
	@Column(length = 256)
	public String getPdfPath2() {
		return pdfPath2;
	}
	public void setPdfPath2(String pdfPath2) {
		this.pdfPath2 = pdfPath2;
	}
	@Column(length = 128)
	public String getPdfName2() {
		return pdfName2;
	}
	public void setPdfName2(String pdfName2) {
		this.pdfName2 = pdfName2;
	}
	@Column(length = 256)
	public String getSendMsg() {
		return sendMsg;
	}
	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}
	@Column(length = 256)
	public String getFileMsg() {
		return fileMsg;
	}
	public void setFileMsg(String fileMsg) {
		this.fileMsg = fileMsg;
	}
	@Column(length = 64)
	public String getFpUser() {
		return fpUser;
	}
	public void setFpUser(String fpUser) {
		this.fpUser = fpUser;
	}
	@Column(length = 32)
	public String getFpUserId() {
		return fpUserId;
	}
	public void setFpUserId(String fpUserId) {
		this.fpUserId = fpUserId;
	}
	@Column(length = 20)
	public String getFpDate() {
		return fpDate;
	}
	public void setFpDate(String fpDate) {
		this.fpDate = fpDate;
	}
	
	
	
	
}
