package cn.demi.init.car.po;

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
 * 车辆使用记录
 * @author QuJunLong
 */
@Entity(name = "init_car_use")
@Table(name = "init_car_use")
@Module(value = "init.carUse")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarUse extends Po<CarUse>{

	public static final String ST_BC="已保存";
	public static final String ST_SH="审核中";
	public static final String ST_TG="已通过";
	public static final String ST_JJ="已拒绝";
	
	private static final long serialVersionUID = 6769049404705999857L;
	public String[] PROPERTY_TO_MAP = {"id","sort","carId","code","name","rule","busId","busNo"
			,"userName","startTime","endTime","duration","status","auditName","auditDate","auditResult","date","no","destination","destRynum"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private String carId;
	private String code;//编号
	private String name;//名称
	private String rule;//型号
	private String no;//申请单
	private String busId;//业务Id
	private String busNo;//业务编号
	private String orgId;//使用人部门
	private String orgName;
	private String userId;//使用人
	private String userName;
	private String date;
	private String startTime;//使用日期
	private String endTime;
	private int duration;//时长
	private String content;//用途描述
	private String remark;
	private String status;//状态  保存，审核中，已通过，已拒绝
	
	private String auditId;//审核人
	private String auditName;
	private String auditDate;
	private String auditResult;//审核结果
	private String auditMsg;//审核原因
	
	
	private String destination;//去向
	private int destRynum; //出差人数
	
	
	
	@Column(length = 1000)
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	@Column(length = 32)
	public int getDestRynum() {
		return destRynum;
	}
	public void setDestRynum(int destRynum) {
		this.destRynum = destRynum;
	}
	@Column(length = 32)
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	@Column(length = 32)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length = 64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 64)
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	@Column(length = 32)
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	@Column(length = 32)
	public String getBusNo() {
		return busNo;
	}
	public void setBusNo(String busNo) {
		this.busNo = busNo;
	}
	@Column(length = 320)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 320)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 20)
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(length = 20)
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(length = 8)
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
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
	@Column(length = 32)
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
	@Column(length = 128)
	public String getAuditMsg() {
		return auditMsg;
	}
	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
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
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length = 20)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(CarUse.class, true, ActionType.JSP);
	}
}
