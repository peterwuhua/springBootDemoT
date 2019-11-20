package cn.demi.office.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 通用流程 申请、审批、抄送 包括请假申请、采购申请、报销申请等
 * 
 * @author QuJunLong
 *
 */
@Entity(name = "office_cm")
@Table(name = "office_cm")
@Module(value = "office.cm")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cm extends Po<Cm> {

	public static final String ST_0 = "已保存";// 状态
	public static final String ST_1 = "待审核";// 状态
	public static final String ST_2 = "通过";// 状态
	public static final String ST_3 = "不通过";// 状态

	public static final String TYPE_FILE = "文件";

	private static final long serialVersionUID = -5812492092794449895L;
	public String[] PROPERTY_TO_MAP = { "id", "sort", "orgName", "deptName", "type", "userName", "sdate", "content", "auditName", "viewNames", "status",
			"auditTime", "hours", "price", "userNames", "beginTime", "endTime", "code", "auditIds", "auditNames", "auditIds_sec", "auditNames_sec",
			"auditIds_thd", "auditNames_thd", "auditLevel", "auditResult", "shId", "shName", "shDate", "shResult", "shMsg", "spId", "spName", "spDate",
			"spResult", "spMsg", "fstatus" };
	public String[] IGNORE_PROPERTY_TO_PO = { "id", "createTime", "lastUpdTime", "version", "orgId", "orgName", "deptId", "deptName", "userId", "userName" };

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}

	private String code;// 编号
	private String type;// 类别
	private String content;// 内容
	private String price;// 成本
	private String hours;// 工时
	private String remark;// 备注
	private String sdate;// 申请日期
	private String userIds;// 人员
	private String userNames;// 人员

	private String orgId;
	private String orgName;// 子公司
	private String deptId;
	private String deptName;// 部门
	private String userId;// 申请人
	private String userName;// 申请人

	/**
	 * 三级审核（办公用品）
	 */
	// 一级审核信息
	private String auditId;// 审批人id
	private String auditName;// 审批人
	private String auditTime;// 审批日期
	private String auditResult;// 审核状态
	private String auditCt;// 审批意见

	// 二级审核信息
	private String shId;
	private String shName;// 审核人
	private String shDate;// 审核日期
	private String shResult;// 审核状态
	private String shMsg;// 审核备注
	// 三级审核信息
	private String spId;
	private String spName;// 审批人
	private String spDate;// 审批日期
	private String spResult;// 审批状态
	private String spMsg;// 审批备注

	/**
	 * 三级审核(文件)
	 */
	private String auditIds;// 审批人(多选)(主管)
	private String auditNames;// 审批人(多选)
	private String auditIds_sec;// 审批人(多选)（分管副总）
	private String auditNames_sec;// 审批人(多选)
	private String auditIds_thd;// 审批人(多选)（总经理）
	private String auditNames_thd;// 审批人(多选)
	private int auditLevel;// 审核层级

	private String viewIds;// 抄送对象
	private String viewNames;// 抄送对象

	private String viewEdIds;// 抄送对象 已查看
	private String viewEdNames;// 抄送对象 已查看

	private String status;// 状态（流程总状态）

	private String fstatus;

	private String beginTime;// 开始时间
	private String endTime;// 结束时间

	private String fileName;
	private String filePath;

	@Column(length = 32)
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	@Column(length = 32)
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(length = 32)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(length = 32)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length = 20)
	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	@Column(length = 32)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(length = 1000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 128)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(length = 32)
	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	@Column(length = 32)
	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	@Column(length = 320)
	public String getViewIds() {
		return viewIds;
	}

	public void setViewIds(String viewIds) {
		this.viewIds = viewIds;
	}

	@Column(length = 320)
	public String getViewNames() {
		return viewNames;
	}

	public void setViewNames(String viewNames) {
		this.viewNames = viewNames;
	}

	@Column(length = 320)
	public String getViewEdIds() {
		return viewEdIds;
	}

	public void setViewEdIds(String viewEdIds) {
		this.viewEdIds = viewEdIds;
	}

	@Column(length = 320)
	public String getViewEdNames() {
		return viewEdNames;
	}

	public void setViewEdNames(String viewEdNames) {
		this.viewEdNames = viewEdNames;
	}

	@Column(length = 16)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(length = 20)
	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	@Column(length = 128)
	public String getAuditCt() {
		return auditCt;
	}

	public void setAuditCt(String auditCt) {
		this.auditCt = auditCt;
	}

	@Column(length = 32)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(length = 32)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(length = 32)
	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	@Column(length = 320)
	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	@Column(length = 320)
	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	@Column(length = 128)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(length = 256)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(length = 320)
	public String getAuditIds() {
		return auditIds;
	}

	public void setAuditIds(String auditIds) {
		this.auditIds = auditIds;
	}

	@Column(length = 320)
	public String getAuditNames() {
		return auditNames;
	}

	public void setAuditNames(String auditNames) {
		this.auditNames = auditNames;
	}

	@Column(length = 10)
	public int getAuditLevel() {
		return auditLevel;
	}

	public void setAuditLevel(int auditLevel) {
		this.auditLevel = auditLevel;
	}

	@Column(length = 320)
	public String getAuditIds_sec() {
		return auditIds_sec;
	}

	public void setAuditIds_sec(String auditIds_sec) {
		this.auditIds_sec = auditIds_sec;
	}

	@Column(length = 320)
	public String getAuditNames_sec() {
		return auditNames_sec;
	}

	public void setAuditNames_sec(String auditNames_sec) {
		this.auditNames_sec = auditNames_sec;
	}

	@Column(length = 320)
	public String getAuditIds_thd() {
		return auditIds_thd;
	}

	public void setAuditIds_thd(String auditIds_thd) {
		this.auditIds_thd = auditIds_thd;
	}

	@Column(length = 320)
	public String getAuditNames_thd() {
		return auditNames_thd;
	}

	public void setAuditNames_thd(String auditNames_thd) {
		this.auditNames_thd = auditNames_thd;
	}

	@Column(length = 32)
	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	@Column(length = 32)
	public String getShId() {
		return shId;
	}

	public void setShId(String shId) {
		this.shId = shId;
	}

	@Column(length = 64)
	public String getShName() {
		return shName;
	}

	public void setShName(String shName) {
		this.shName = shName;
	}

	@Column(length = 64)
	public String getShDate() {
		return shDate;
	}

	public void setShDate(String shDate) {
		this.shDate = shDate;
	}

	@Column(length = 32)
	public String getShResult() {
		return shResult;
	}

	public void setShResult(String shResult) {
		this.shResult = shResult;
	}

	@Column(length = 128)
	public String getShMsg() {
		return shMsg;
	}

	public void setShMsg(String shMsg) {
		this.shMsg = shMsg;
	}

	@Column(length = 32)
	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	@Column(length = 64)
	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	@Column(length = 64)
	public String getSpDate() {
		return spDate;
	}

	public void setSpDate(String spDate) {
		this.spDate = spDate;
	}

	@Column(length = 32)
	public String getSpResult() {
		return spResult;
	}

	public void setSpResult(String spResult) {
		this.spResult = spResult;
	}

	@Column(length = 128)
	public String getSpMsg() {
		return spMsg;
	}

	public void setSpMsg(String spMsg) {
		this.spMsg = spMsg;
	}

	@Column(length = 32)
	public String getFstatus() {
		return fstatus;
	}

	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}

}
