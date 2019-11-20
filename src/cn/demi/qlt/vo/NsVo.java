package cn.demi.qlt.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.pg.vo.ProgressVo;

public class NsVo extends Vo<NsVo> {
	private String no;
	private String title;//标题
	private String deptId;//待审部门
	private String deptName;
	private String year;//年
	private String months;//月份
	private String purpose;  //审核目的
	private String ranges;    //审核范围
	private String stand;    //审核依据
	private String auditType;//审核方式
	private String address;  //审核地点
	private String content;//审核情况概述
	private String result;//内审结论
	
	private String headId;//审核组长
	private String headName;
	private String nsId;//内审人员
	private String nsName;

	private String orgId;//部门
	private String orgName;
	private String userId;//编制人
	private String userName;
	private String date;
	
	private String auditId;//批准人员
	private String auditName;
	private String auditDate;
	
	private String fileName;//报告名称及路径
	private String filePath;

	private String sumId;//汇总人
	private String sumName;
	private String sumDate;//汇总日期
	
	private String cdId;//报告存档人
	private String cdName;
	private String cdDate;//报告存档日期
	
	private String fileAddress;//报告存档地点
	private String remark;
	private String isBack;
	private String status;//当前状态
	private ProgressVo pgVo;//流程
	private List<NsDetailVo> detailList;
	private List<NsRecordVo> recordList;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getRanges() {
		return ranges;
	}
	public void setRanges(String ranges) {
		this.ranges = ranges;
	}
	public String getStand() {
		return stand;
	}
	public void setStand(String stand) {
		this.stand = stand;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	public String getNsId() {
		return nsId;
	}
	public void setNsId(String nsId) {
		this.nsId = nsId;
	}
	public String getNsName() {
		return nsName;
	}
	public void setNsName(String nsName) {
		this.nsName = nsName;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
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
	public String getCdId() {
		return cdId;
	}
	public void setCdId(String cdId) {
		this.cdId = cdId;
	}
	public String getCdName() {
		return cdName;
	}
	public void setCdName(String cdName) {
		this.cdName = cdName;
	}
	public String getCdDate() {
		return cdDate;
	}
	public void setCdDate(String cdDate) {
		this.cdDate = cdDate;
	}
	public String getFileAddress() {
		return fileAddress;
	}
	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ProgressVo getPgVo() {
		return pgVo;
	}
	public void setPgVo(ProgressVo pgVo) {
		this.pgVo = pgVo;
	}
	public List<NsDetailVo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<NsDetailVo> detailList) {
		this.detailList = detailList;
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
	public String getSumId() {
		return sumId;
	}
	public void setSumId(String sumId) {
		this.sumId = sumId;
	}
	public String getSumName() {
		return sumName;
	}
	public void setSumName(String sumName) {
		this.sumName = sumName;
	}
	public String getSumDate() {
		return sumDate;
	}
	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<NsRecordVo> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<NsRecordVo> recordList) {
		this.recordList = recordList;
	}
}

