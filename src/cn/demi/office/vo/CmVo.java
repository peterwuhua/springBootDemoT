package cn.demi.office.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.FilesVo;

public class CmVo extends Vo<CmVo> {
	private String code;//编号
	private String price;//成本
	private String hours;//工时
	private String userIds;//人员
	private String userNames;//人员
	private String sdate;//申请日期
	private String type;//类别
	private String content;//内容
	
	private String orgId;
	private String orgName;//子公司
	private String deptId;
	private String deptName;//部门
	

	private String userId;//申请人
	private String userName;//申请人
	private String status;//状态()
	private String fstatus;//
	private String remark;//备注
	
	/**
	 * 三级审核（办公用品）
	 */
	//一级审核信息
	private String auditId;// 
	private String auditName;// 审批人
	private String auditTime;// 审批日期
	private String auditResult;//审核状态
	private String auditCt;// 审批备注
	//二级审核信息
	private String shId;
	private String shName;//审核人
	private String shDate;//审核日期
	private String shResult;//审核状态
	private String shMsg;//审核备注
	//三级审核信息
	private String spId;
	private String spName;//审批人
	private String spDate;//审批日期
	private String spResult;//审批状态
	private String spMsg;//审批备注
	
	/**
	 * 三级审核(文件呈报、传阅)
	 */
	private String auditIds;// 审批人(多选)(主管)
	private String auditNames;// 审批人(多选)
	private String auditIds_sec;// 审批人(多选)（分管副总）
	private String auditNames_sec;// 审批人(多选)
	private String auditIds_thd;// 审批人(多选)（总经理）
	private String auditNames_thd;// 审批人(多选)
	private int auditLevel;//审核层级
	
	private String viewIds;//抄送对象
	private String viewNames;//抄送对象
	
	private String viewEdIds;//抄送对象 已查看
	private String viewEdNames;//抄送对象 已查看

	private String beginTime;//开始时间
	private String endTime;//结束时间
	
	private String fileName;
	private String filePath;
	private List<FilesVo> fileList;
	
	
	public String getAuditIds() {
		return auditIds;
	}
	public void setAuditIds(String auditIds) {
		this.auditIds = auditIds;
	}
	public String getAuditNames() {
		return auditNames;
	}
	public void setAuditNames(String auditNames) {
		this.auditNames = auditNames;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getViewIds() {
		return viewIds;
	}
	public void setViewIds(String viewIds) {
		this.viewIds = viewIds;
	}
	public String getViewNames() {
		return viewNames;
	}
	public void setViewNames(String viewNames) {
		this.viewNames = viewNames;
	}
	public String getViewEdIds() {
		return viewEdIds;
	}
	public void setViewEdIds(String viewEdIds) {
		this.viewEdIds = viewEdIds;
	}
	public String getViewEdNames() {
		return viewEdNames;
	}
	public void setViewEdNames(String viewEdNames) {
		this.viewEdNames = viewEdNames;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditCt() {
		return auditCt;
	}
	public void setAuditCt(String auditCt) {
		this.auditCt = auditCt;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
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
	public List<FilesVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	public int getAuditLevel() {
		return auditLevel;
	}
	public void setAuditLevel(int auditLevel) {
		this.auditLevel = auditLevel;
	}
	public String getAuditIds_sec() {
		return auditIds_sec;
	}
	public void setAuditIds_sec(String auditIds_sec) {
		this.auditIds_sec = auditIds_sec;
	}
	public String getAuditNames_sec() {
		return auditNames_sec;
	}
	public void setAuditNames_sec(String auditNames_sec) {
		this.auditNames_sec = auditNames_sec;
	}
	public String getAuditIds_thd() {
		return auditIds_thd;
	}
	public void setAuditIds_thd(String auditIds_thd) {
		this.auditIds_thd = auditIds_thd;
	}
	public String getAuditNames_thd() {
		return auditNames_thd;
	}
	public void setAuditNames_thd(String auditNames_thd) {
		this.auditNames_thd = auditNames_thd;
	}
	public String getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	public String getShId() {
		return shId;
	}
	public void setShId(String shId) {
		this.shId = shId;
	}
	public String getShName() {
		return shName;
	}
	public void setShName(String shName) {
		this.shName = shName;
	}
	public String getShDate() {
		return shDate;
	}
	public void setShDate(String shDate) {
		this.shDate = shDate;
	}
	public String getShResult() {
		return shResult;
	}
	public void setShResult(String shResult) {
		this.shResult = shResult;
	}
	public String getShMsg() {
		return shMsg;
	}
	public void setShMsg(String shMsg) {
		this.shMsg = shMsg;
	}
	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public String getSpDate() {
		return spDate;
	}
	public void setSpDate(String spDate) {
		this.spDate = spDate;
	}
	public String getSpResult() {
		return spResult;
	}
	public void setSpResult(String spResult) {
		this.spResult = spResult;
	}
	public String getSpMsg() {
		return spMsg;
	}
	public void setSpMsg(String spMsg) {
		this.spMsg = spMsg;
	}
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
	
	
}

