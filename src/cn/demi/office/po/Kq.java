package cn.demi.office.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 考勤管理
 * @author QuJunLong
 *
 */
@Entity(name = "office_kq")
@Table(name = "office_kq")
@Module(value = "office.kq")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Kq extends Po<Kq>{
 
	public static final String TP_QJ = "请假";
	public static final String TP_WQ = "外勤";
	
	public static final String ST_B = "已退回";
	public static final String ST_0 = "已保存";
	public static final String ST_1 = "待审核";
	public static final String ST_2 = "已通过";
	public static final String ST_3 = "已销假";
	
	private static final long serialVersionUID = -5253821618236763236L;
	private String no;//编号
	private String type; //请假类型(出勤、加班、事假、婚假、哺乳假、公差、旷工、公休、调休)
	private String orgName; //一级部门
	private String orgId; //一级部门id
	private String deptId;//二级部门
	private String deptName;//部门
	private String userId;
	private String userName;//人
	private String date;//申请时间
	private String startTime;//开始时间
	private String endTime;//截止时间
	private int hours;//时长
	private String content;//描述
	private String remark;//备注
	private String jober; //职位代理人
	private String joberId; //职位代理人id
	private String status;//状态
	private String fstatus; //已保存，审核中，已通过，已销假
	private String busId;//业务id 外勤从采样安排来的采样安排任务
	//一级审核信息
	private String auditId;
	private String auditName;//审核人
	private String auditDate;//审核日期
	private String auditResult;//审核状态
	private String auditMsg;//审核备注
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
	
	public String[] PROPERTY_TO_MAP= {"id","sort","no","deptName","type","userName","date","startTime","endTime","hours","content","remark","status","auditName","auditDate","auditResult"
			,"shName","shDate","shResult","spName","spDate","spResult","fstatus"};
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version","no","type","orgId","orgName","deptId","deptName","userId","userName"};
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
	
	@Column(length = 32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(length = 256)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	@Column(length = 20)
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	@Column(length = 32)
	public String getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	@Column(length = 64)
	public String getAuditMsg() {
		return auditMsg;
	}
	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}
	@Column(length = 32)
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	@Column(length = 20)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(length = 20)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length = 64)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length = 20)
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	@Column(length = 32)
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
	@Column(length = 32)
	public String getShId() {
		return shId;
	}
	public void setShId(String shId) {
		this.shId = shId;
	}
	@Column(length = 32)
	public String getShName() {
		return shName;
	}
	public void setShName(String shName) {
		this.shName = shName;
	}
	@Column(length = 20)
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
	@Column(length = 32)
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	@Column(length = 20)
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
	public String getJober() {
		return jober;
	}
	public void setJober(String jober) {
		this.jober = jober;
	}
	@Column(length = 32)
	public String getJoberId() {
		return joberId;
	}
	public void setJoberId(String joberId) {
		this.joberId = joberId;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Kq.class, false, ActionType.JSP);
	}
	
}
