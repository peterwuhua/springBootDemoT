package cn.demi.qlt.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.pg.po.Progress;

public class GsVo extends Vo<GsVo> {
	
	private String no;
	private String title;//标题
	private String purpose;  //审核目的
	private String auditType;//审核方式
	private String psDate;   //评审时间
	private String stand;    //审核依据
	private String address;  //审核地点
	private String deptId;//参与部门
	private String deptName;
	
	private String zcId;//主持人
	private String zcName;
	
	private String headId;//评审组长
	private String headName;

	private String psId;//评审人员
	private String psName;
	
	private String psct;//审核情况概述
	private String yq;//准备要求
	
	private String orgId;//部门
	private String orgName;
	private String userId;//编制人
	private String userName;
	private String date;
	
	private String auditId;//批准人员
	private String auditName;
	private String auditDate;
	
	private String content;//内容
	private String ryj;//意见
	private String remark;//备注
	
	private String ruserId;//记录人
	private String ruserName;
	private String rdate;//记录日期
	
	private String reportId;//报告人
	private String reportName;
	private String reportDate;//报告日期
	
	private String fileName;//报告名称及路径
	private String filePath;
	
	private String psyj;//评审组意见
	private String questions;//存在意见
	private String gjyj;//改进意见
	
	private String gjcs;//改进措施
	private String finishDate;//完成日期
	private String gjId;//跟踪人
	private String gjName;
	
	private String result;//结果
	private String yzDate;//验证日期
	private String yzId;//验证人
	private String yzName;
	
	private String zlyj;//质量负责人意见
	private String zlId;
	private String zlName;
	private String zlDate;
	
	private String zryj;//主任意见
	private String zrId;
	private String zrName;
	private String zrDate;
	
	private String cdId;//报告存档人
	private String cdName;
	private String cdDate;//报告存档日期
	private String cdAddress;//报告存档地点
	
	private String isBack;
	private String status;//当前状态
	private Progress pg;//流程
	private List<GsRecordVo> recordList;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getPsDate() {
		return psDate;
	}
	public void setPsDate(String psDate) {
		this.psDate = psDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getPsId() {
		return psId;
	}
	public void setPsId(String psId) {
		this.psId = psId;
	}
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
	public String getPsct() {
		return psct;
	}
	public void setPsct(String psct) {
		this.psct = psct;
	}
	public String getYq() {
		return yq;
	}
	public void setYq(String yq) {
		this.yq = yq;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRyj() {
		return ryj;
	}
	public void setRyj(String ryj) {
		this.ryj = ryj;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRuserId() {
		return ruserId;
	}
	public void setRuserId(String ruserId) {
		this.ruserId = ruserId;
	}
	public String getRuserName() {
		return ruserName;
	}
	public void setRuserName(String ruserName) {
		this.ruserName = ruserName;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getPsyj() {
		return psyj;
	}
	public void setPsyj(String psyj) {
		this.psyj = psyj;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
	}
	public String getGjyj() {
		return gjyj;
	}
	public void setGjyj(String gjyj) {
		this.gjyj = gjyj;
	}
	public String getGjcs() {
		return gjcs;
	}
	public void setGjcs(String gjcs) {
		this.gjcs = gjcs;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getGjId() {
		return gjId;
	}
	public void setGjId(String gjId) {
		this.gjId = gjId;
	}
	public String getGjName() {
		return gjName;
	}
	public void setGjName(String gjName) {
		this.gjName = gjName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getYzDate() {
		return yzDate;
	}
	public void setYzDate(String yzDate) {
		this.yzDate = yzDate;
	}
	public String getYzId() {
		return yzId;
	}
	public void setYzId(String yzId) {
		this.yzId = yzId;
	}
	public String getYzName() {
		return yzName;
	}
	public void setYzName(String yzName) {
		this.yzName = yzName;
	}
	public String getZlyj() {
		return zlyj;
	}
	public void setZlyj(String zlyj) {
		this.zlyj = zlyj;
	}
	public String getZlId() {
		return zlId;
	}
	public void setZlId(String zlId) {
		this.zlId = zlId;
	}
	public String getZlName() {
		return zlName;
	}
	public void setZlName(String zlName) {
		this.zlName = zlName;
	}
	public String getZlDate() {
		return zlDate;
	}
	public void setZlDate(String zlDate) {
		this.zlDate = zlDate;
	}
	public String getZryj() {
		return zryj;
	}
	public void setZryj(String zryj) {
		this.zryj = zryj;
	}
	public String getZrId() {
		return zrId;
	}
	public void setZrId(String zrId) {
		this.zrId = zrId;
	}
	public String getZrName() {
		return zrName;
	}
	public void setZrName(String zrName) {
		this.zrName = zrName;
	}
	public String getZrDate() {
		return zrDate;
	}
	public void setZrDate(String zrDate) {
		this.zrDate = zrDate;
	}
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Progress getPg() {
		return pg;
	}
	public void setPg(Progress pg) {
		this.pg = pg;
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
	public List<GsRecordVo> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<GsRecordVo> recordList) {
		this.recordList = recordList;
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
	public String getCdAddress() {
		return cdAddress;
	}
	public void setCdAddress(String cdAddress) {
		this.cdAddress = cdAddress;
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
	public String getStand() {
		return stand;
	}
	public void setStand(String stand) {
		this.stand = stand;
	}
	public String getZcId() {
		return zcId;
	}
	public void setZcId(String zcId) {
		this.zcId = zcId;
	}
	public String getZcName() {
		return zcName;
	}
	public void setZcName(String zcName) {
		this.zcName = zcName;
	}
}

