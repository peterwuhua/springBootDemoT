package cn.demi.qlt.po;

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
import cn.demi.bus.pg.po.Progress;

/**
 * 管理评审
 * @author QuJunLong
 *
 */
@Entity(name = "qlt_gs")
@Table(name = "qlt_gs")
@Module(value = "qlt.gs")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gs extends Po<Gs>{
	
	private static final long serialVersionUID = -4944196723808587599L;
	public String[] PROPERTY_TO_MAP= {"id","sort","title","no","orgName","deptName","headName","isBack","psDate","userName","status","pg",
			"reportName","reportDate","cdName","cdDate"};
	
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version","no","orgId","orgName","isBack"};
	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private String no;
	private String title;//标题
	private String purpose;  //审核目的
	private String auditType;//审核方式
	private String psDate;   //评审时间
	private String address;  //审核地点
	private String stand;    //审核依据
	private String deptId;//参与部门
	private String deptName;
	
	private String zcId;//主持人
	private String zcName;
	
	private String headId;//评审组 长
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
	
	@Column(length=32)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length=64)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length=128)
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Column(length=32)
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	@Column(length=20)
	public String getPsDate() {
		return psDate;
	}
	public void setPsDate(String psDate) {
		this.psDate = psDate;
	}
	@Column(length=64)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length=32)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(length=32)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(length=32)
	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}
	@Column(length=32)
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	@Column(length=320)
	public String getPsId() {
		return psId;
	}
	public void setPsId(String psId) {
		this.psId = psId;
	}
	@Column(length=320)
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
	@Column(length=256)
	public String getPsct() {
		return psct;
	}
	public void setPsct(String psct) {
		this.psct = psct;
	}
	@Column(length=256)
	public String getYq() {
		return yq;
	}
	public void setYq(String yq) {
		this.yq = yq;
	}
	@Column(length=32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length=32)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=20)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(length=32)
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	@Column(length=32)
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	@Column(length=20)
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	@Column(length=256)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=256)
	public String getRyj() {
		return ryj;
	}
	public void setRyj(String ryj) {
		this.ryj = ryj;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=32)
	public String getRuserId() {
		return ruserId;
	}
	public void setRuserId(String ruserId) {
		this.ruserId = ruserId;
	}
	@Column(length=32)
	public String getRuserName() {
		return ruserName;
	}
	public void setRuserName(String ruserName) {
		this.ruserName = ruserName;
	}
	@Column(length=20)
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	@Column(length=32)
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	@Column(length=32)
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	@Column(length=20)
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	@Column(length=256)
	public String getPsyj() {
		return psyj;
	}
	public void setPsyj(String psyj) {
		this.psyj = psyj;
	}
	@Column(length=256)
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
	}
	@Column(length=256)
	public String getGjyj() {
		return gjyj;
	}
	public void setGjyj(String gjyj) {
		this.gjyj = gjyj;
	}
	@Column(length=256)
	public String getGjcs() {
		return gjcs;
	}
	public void setGjcs(String gjcs) {
		this.gjcs = gjcs;
	}
	@Column(length=20)
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	@Column(length=32)
	public String getGjId() {
		return gjId;
	}
	public void setGjId(String gjId) {
		this.gjId = gjId;
	}
	@Column(length=32)
	public String getGjName() {
		return gjName;
	}
	public void setGjName(String gjName) {
		this.gjName = gjName;
	}
	@Column(length=256)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Column(length=20)
	public String getYzDate() {
		return yzDate;
	}
	public void setYzDate(String yzDate) {
		this.yzDate = yzDate;
	}
	@Column(length=32)
	public String getYzId() {
		return yzId;
	}
	public void setYzId(String yzId) {
		this.yzId = yzId;
	}
	@Column(length=32)
	public String getYzName() {
		return yzName;
	}
	public void setYzName(String yzName) {
		this.yzName = yzName;
	}
	@Column(length=256)
	public String getZlyj() {
		return zlyj;
	}
	public void setZlyj(String zlyj) {
		this.zlyj = zlyj;
	}
	@Column(length=32)
	public String getZlId() {
		return zlId;
	}
	public void setZlId(String zlId) {
		this.zlId = zlId;
	}
	@Column(length=32)
	public String getZlName() {
		return zlName;
	}
	public void setZlName(String zlName) {
		this.zlName = zlName;
	}
	@Column(length=20)
	public String getZlDate() {
		return zlDate;
	}
	public void setZlDate(String zlDate) {
		this.zlDate = zlDate;
	}
	@Column(length=256)
	public String getZryj() {
		return zryj;
	}
	public void setZryj(String zryj) {
		this.zryj = zryj;
	}
	@Column(length=32)
	public String getZrId() {
		return zrId;
	}
	public void setZrId(String zrId) {
		this.zrId = zrId;
	}
	@Column(length=32)
	public String getZrName() {
		return zrName;
	}
	public void setZrName(String zrName) {
		this.zrName = zrName;
	}
	@Column(length=20)
	public String getZrDate() {
		return zrDate;
	}
	public void setZrDate(String zrDate) {
		this.zrDate = zrDate;
	}
	@Column(length=20)
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	@Column(length=32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@ManyToOne
	@JoinColumn(name = "pg_id")
	public Progress getPg() {
		return pg;
	}
	public void setPg(Progress pg) {
		this.pg = pg;
	}
	@Column(length=32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length=32)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length=32)
	public String getCdId() {
		return cdId;
	}
	public void setCdId(String cdId) {
		this.cdId = cdId;
	}
	@Column(length=32)
	public String getCdName() {
		return cdName;
	}
	public void setCdName(String cdName) {
		this.cdName = cdName;
	}
	@Column(length=20)
	public String getCdDate() {
		return cdDate;
	}
	public void setCdDate(String cdDate) {
		this.cdDate = cdDate;
	}
	@Column(length=128)
	public String getCdAddress() {
		return cdAddress;
	}
	public void setCdAddress(String cdAddress) {
		this.cdAddress = cdAddress;
	}
	@Column(length=128)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length=128)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length=128)
	public String getStand() {
		return stand;
	}
	public void setStand(String stand) {
		this.stand = stand;
	}
	@Column(length=32)
	public String getZcId() {
		return zcId;
	}
	public void setZcId(String zcId) {
		this.zcId = zcId;
	}
	@Column(length=32)
	public String getZcName() {
		return zcName;
	}
	public void setZcName(String zcName) {
		this.zcName = zcName;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Gs.class, true, ActionType.JSP);
	}
}
