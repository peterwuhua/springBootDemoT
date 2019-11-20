package cn.demi.bus.report.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.file.vo.ArchiveFileVo;
import cn.demi.bus.pg.vo.ProgressVo;
import cn.demi.bus.pjt.vo.CustMaterialVo;
import cn.demi.bus.pjt.vo.CustVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.vo.TaskItemVo;

public class ReportVo extends Vo<ReportVo> {
	
	private TaskVo taskVo;
	private CustVo custVo;//客户信息
	private ProgressVo progressVo;
	private String reportNo;
	private String sampTypeId;//样品类型
	private String sampTypeName;
	private String sampType;
	private String sampName;//样品名称
	private String taskType;//测试类型
	private String reportDate;//报告日期
	private String remark;
	private String orgId;//受理部门
	private String orgName;
	private String testResult;//监测目的
	private String standIds;//检测依据
	private String standNames;
	private String result;//结论
	private String jhsm;//检测计划和程序说明
	private String jcct;//检测内容
	private String jssm;//解释和说明
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
	 
	private String position;//报告归档位置
	private String categoryId;//电子文档
	private String categoryName;//电子文档
	private String finishDate;//要求完成时间
	private String status;
    private String filePath;//报告路径
    private String fileName;// 报告名称
    private String pdfPath;//报告路径
    private String pdfName;//
    /**
     * 定期项目报告
     */
    private String filePath2;
    private String fileName2;
    private String pdfPath2;
    private String pdfName2;
	private String template;//报告模板
	private String isBack;
	
	private String reportDateStr;//报告日期 中文格式
	private String cyDate;//采样日期
	private String testDate;//分析日期
	private String testEndDate;//分析日期
	private String cyEndDate;//采样日期
	private String wd;//气温
	private String sd;//湿度
	/**
	 * 详情集合
	 */
	private List<ReportDetailVo> detailList;
	private List<ReportDetailVo> zdetailList;
	private int itemNum;
	//附件
	private List<FilesVo> fileList;
	private List<ArchiveFileVo> archList;
	//生成报告文件字段
	private int page;
	private List<RsampVo> ffList;//分析方法
	private List<RappVo> appList;//仪器信息集合

	private RtabVo qtab;//监测结果页面集合  气
	private RtabVo qwtab;//监测结果页面集合  无组织 气
	private RtabVo qxtab;//监测结果页面集合  无组织 气像
	private List<RtabVo> wtabList;//监测结果页面集合  水
	private List<RtabVo> stabList;//监测结果页面集合  声
	private List<RtabVo> ttabList;//监测结果页面集合  土
	private String appIds;//设备集合
	private List<TaskItemVo> timList;
	private List<CustMaterialVo> mtList;//原辅料信息
	private List<RtabDataVo> dataList;//可用于行循环的数据块
	private String testRange;//检测范围
	private List<RtabRoomVo> roomList;//现场情况 定期报告
	
	private String fslx;//放射类型（空气比释动能率，剂量当量率）
	
	
	
	public String getFslx() {
		return fslx;
	}
	public void setFslx(String fslx) {
		this.fslx = fslx;
	}
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	 
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMakeUser() {
		return makeUser;
	}
	public void setMakeUser(String makeUser) {
		this.makeUser = makeUser;
	}
	public String getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public String getSignUser() {
		return signUser;
	}
	public void setSignUser(String signUser) {
		this.signUser = signUser;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ReportDetailVo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<ReportDetailVo> detailList) {
		this.detailList = detailList;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getMakeUserId() {
		return makeUserId;
	}
	public void setMakeUserId(String makeUserId) {
		this.makeUserId = makeUserId;
	}
	public String getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	public String getSignUserId() {
		return signUserId;
	}
	public void setSignUserId(String signUserId) {
		this.signUserId = signUserId;
	}
	public String getFileUser() {
		return fileUser;
	}
	public void setFileUser(String fileUser) {
		this.fileUser = fileUser;
	}
	public String getFileUserId() {
		return fileUserId;
	}
	public void setFileUserId(String fileUserId) {
		this.fileUserId = fileUserId;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	public List<FilesVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	public TaskVo getTaskVo() {
		return taskVo;
	}
	public void setTaskVo(TaskVo taskVo) {
		this.taskVo = taskVo;
	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public ProgressVo getProgressVo() {
		return progressVo;
	}
	public void setProgressVo(ProgressVo progressVo) {
		this.progressVo = progressVo;
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
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public CustVo getCustVo() {
		return custVo;
	}
	public void setCustVo(CustVo custVo) {
		this.custVo = custVo;
	}
	public String getAuditMsg() {
		return auditMsg;
	}
	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	 
 
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public String getTestResult() {
		return testResult;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public String getStandIds() {
		return standIds;
	}
	public void setStandIds(String standIds) {
		this.standIds = standIds;
	}
	public String getStandNames() {
		return standNames;
	}
	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReportDateStr() {
		return reportDateStr;
	}
	public void setReportDateStr(String reportDateStr) {
		this.reportDateStr = reportDateStr;
	}
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public List<RsampVo> getFfList() {
		return ffList;
	}
	public void setFfList(List<RsampVo> ffList) {
		this.ffList = ffList;
	}
//	public List<RItemVo> getXcList() {
//		return xcList;
//	}
//	public void setXcList(List<RItemVo> xcList) {
//		this.xcList = xcList;
//	}
//	public List<RsampVo> getFxList() {
//		return fxList;
//	}
//	public void setFxList(List<RsampVo> fxList) {
//		this.fxList = fxList;
//	}
//	public List<RItemVo> getZsList() {
//		return zsList;
//	}
//	public void setZsList(List<RItemVo> zsList) {
//		this.zsList = zsList;
//	}
	public List<RappVo> getAppList() {
		return appList;
	}
	public void setAppList(List<RappVo> appList) {
		this.appList = appList;
	}
	public List<ReportDetailVo> getZdetailList() {
		return zdetailList;
	}
	public void setZdetailList(List<ReportDetailVo> zdetailList) {
		this.zdetailList = zdetailList;
	}
	public String getReptUser() {
		return reptUser;
	}
	public void setReptUser(String reptUser) {
		this.reptUser = reptUser;
	}
	public String getReptUserId() {
		return reptUserId;
	}
	public void setReptUserId(String reptUserId) {
		this.reptUserId = reptUserId;
	}
	public String getReptDate() {
		return reptDate;
	}
	public void setReptDate(String reptDate) {
		this.reptDate = reptDate;
	}
	public String getReptMsg() {
		return reptMsg;
	}
	public void setReptMsg(String reptMsg) {
		this.reptMsg = reptMsg;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	public String getJhsm() {
		return jhsm;
	}
	public void setJhsm(String jhsm) {
		this.jhsm = jhsm;
	}
	public String getJcct() {
		return jcct;
	}
	public void setJcct(String jcct) {
		this.jcct = jcct;
	}
	public String getJssm() {
		return jssm;
	}
	public void setJssm(String jssm) {
		this.jssm = jssm;
	}
	public String getTestEndDate() {
		return testEndDate;
	}
	public void setTestEndDate(String testEndDate) {
		this.testEndDate = testEndDate;
	}
	public String getAppIds() {
		return appIds;
	}
	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}
	public RtabVo getQtab() {
		return qtab;
	}
	public void setQtab(RtabVo qtab) {
		this.qtab = qtab;
	}
	public RtabVo getQwtab() {
		return qwtab;
	}
	public void setQwtab(RtabVo qwtab) {
		this.qwtab = qwtab;
	}
	public RtabVo getQxtab() {
		return qxtab;
	}
	public void setQxtab(RtabVo qxtab) {
		this.qxtab = qxtab;
	}
	public List<RtabVo> getWtabList() {
		return wtabList;
	}
	public void setWtabList(List<RtabVo> wtabList) {
		this.wtabList = wtabList;
	}
	public List<RtabVo> getStabList() {
		return stabList;
	}
	public void setStabList(List<RtabVo> stabList) {
		this.stabList = stabList;
	}
	public List<RtabVo> getTtabList() {
		return ttabList;
	}
	public void setTtabList(List<RtabVo> ttabList) {
		this.ttabList = ttabList;
	}
	public int getPage() {
		if(page==0)page=1;
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getJchj() {
		return jchj;
	}
	public void setJchj(String jchj) {
		this.jchj = jchj;
	}
	public String getCyEndDate() {
		return cyEndDate;
	}
	public void setCyEndDate(String cyEndDate) {
		this.cyEndDate = cyEndDate;
	}
	public List<TaskItemVo> getTimList() {
		return timList;
	}
	public void setTimList(List<TaskItemVo> timList) {
		this.timList = timList;
	}
	public String getFilePath2() {
		return filePath2;
	}
	public void setFilePath2(String filePath2) {
		this.filePath2 = filePath2;
	}
	public String getFileName2() {
		return fileName2;
	}
	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}
	public String getPdfPath2() {
		return pdfPath2;
	}
	public void setPdfPath2(String pdfPath2) {
		this.pdfPath2 = pdfPath2;
	}
	public String getPdfName2() {
		return pdfName2;
	}
	public void setPdfName2(String pdfName2) {
		this.pdfName2 = pdfName2;
	}
	public String getWd() {
		return wd;
	}
	public void setWd(String wd) {
		this.wd = wd;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public List<CustMaterialVo> getMtList() {
		return mtList;
	}
	public void setMtList(List<CustMaterialVo> mtList) {
		this.mtList = mtList;
	}
	public List<RtabDataVo> getDataList() {
		return dataList;
	}
	public void setDataList(List<RtabDataVo> dataList) {
		this.dataList = dataList;
	}
	public String getTestRange() {
		return testRange;
	}
	public void setTestRange(String testRange) {
		this.testRange = testRange;
	}
	public List<RtabRoomVo> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<RtabRoomVo> roomList) {
		this.roomList = roomList;
	}
	public String getSendMsg() {
		return sendMsg;
	}
	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}
	public String getFileMsg() {
		return fileMsg;
	}
	public void setFileMsg(String fileMsg) {
		this.fileMsg = fileMsg;
	}
	public List<ArchiveFileVo> getArchList() {
		return archList;
	}
	public void setArchList(List<ArchiveFileVo> archList) {
		this.archList = archList;
	}
	public String getFpUser() {
		return fpUser;
	}
	public void setFpUser(String fpUser) {
		this.fpUser = fpUser;
	}
	public String getFpUserId() {
		return fpUserId;
	}
	public void setFpUserId(String fpUserId) {
		this.fpUserId = fpUserId;
	}
	public String getFpDate() {
		return fpDate;
	}
	public void setFpDate(String fpDate) {
		this.fpDate = fpDate;
	}
	
	
	
}

