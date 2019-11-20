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
 * 内审计划
 * @author QuJunLong
 *
 */
@Entity(name = "qlt_ns")
@Table(name = "qlt_ns")
@Module(value = "qlt.ns")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ns extends Po<Ns>{

	private static final long serialVersionUID = -9093327130670248755L;
	public String[] PROPERTY_TO_MAP= {"id","sort","no","title","orgName","year","months","headName","isBack","date","userName","status","pg"};
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version","no","orgId","orgName","isBack"};
	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}
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
	
	private String sumId;//汇总人
	private String sumName;
	private String sumDate;//汇总日期
	private String fileName;//报告名称及路径
	private String filePath;

	private String cdId;//报告存档人
	private String cdName;
	private String cdDate;//报告存档日期
	private String fileAddress;//报告存档地点
	private String remark;
	private String isBack;
	private String status;//当前状态
	private Progress pg;//流程
	
	@Column(length=64)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length=32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length=64)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length=128)
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Column(length=64)
	public String getRanges() {
		return ranges;
	}
	public void setRanges(String ranges) {
		this.ranges = ranges;
	}
	@Column(length=64)
	public String getStand() {
		return stand;
	}
	public void setStand(String stand) {
		this.stand = stand;
	}
	@Column(length=64)
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	@Column(length=64)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length=32)
	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}
	@Column(length=16)
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	@Column(length=320)
	public String getNsId() {
		return nsId;
	}
	public void setNsId(String nsId) {
		this.nsId = nsId;
	}
	@Column(length=320)
	public String getNsName() {
		return nsName;
	}
	public void setNsName(String nsName) {
		this.nsName = nsName;
	}
	@Column(length=32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length=16)
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
	@Column(length=16)
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
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=4)
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	@Column(length=128)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length=64)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length=32)
	public String getCdId() {
		return cdId;
	}
	public void setCdId(String cdId) {
		this.cdId = cdId;
	}
	@Column(length=16)
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
	public String getFileAddress() {
		return fileAddress;
	}
	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}
	@Column(length=32)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length=2)
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	@Column(length=64)
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	@Column(length=64)
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
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(length=64)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(length=32)
	public String getSumId() {
		return sumId;
	}
	public void setSumId(String sumId) {
		this.sumId = sumId;
	}
	@Column(length=32)
	public String getSumName() {
		return sumName;
	}
	public void setSumName(String sumName) {
		this.sumName = sumName;
	}
	@Column(length=20)
	public String getSumDate() {
		return sumDate;
	}
	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}
	@Column(length=256)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=64)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Ns.class, true, ActionType.JSP);
	}
}
