package cn.demi.office.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 
 * @ClassName:  QJ   
 * @Description: 请假   
 * @author: 吴华 
 * @date:   2019年3月11日 上午10:06:22       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Entity(name = "office_qj")
@Table(name = "office_qj")
@Module(value = "office.qj")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Qjgl extends Po<Qjgl>{

	private static final long serialVersionUID = 1L;

	
	public String[] PROPERTY_TO_MAP= {"id","sort","no","deptId","deptName","type","person","personId","supportDate","beginTime","endTime","remark","sumDay","jober","joberId","sumDate","sumUserName","sumUserId","sumRemark","fstatus","xjshow"};
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version"};
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
	private String no; //编号（方便查询）
	private String deptName; //部门
	private String deptId; //部门id
	private String type; //请假类型(出勤、加班、事假、婚假、哺乳假、公差、旷工、公休、调休)
	private String person; //请假申请人
	private String personId; //请假申请人id
	private String supportDate; //请假申请日期
	private String beginTime; //开始时间
	private String endTime; //结束时间
	private String remark;//请假事由
	private double sumDay;//共计天数（取两位小数）  
	private String jober; //职位代理人
	private String joberId; //职位代理人id
	
	/**
	 * 审批
	 */
	private String sumDate; //审批日期
	private String sumUserName;//审批人
	private String sumUserId;//审批人id
	private String sumRemark; //审批意见
	
	
	
	/**
	 * 销假
	 */
	private String xjshow; //是否销假（未销假、已销假）
	
	private String fstatus; //请假状态
	
	
	
	@Column(length = 32)
	public String getXjshow() {
		return xjshow;
	}
	public void setXjshow(String xjshow) {
		this.xjshow = xjshow;
	}
	@Column(length = 20)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length = 32)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(length = 32)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(length = 32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 128)
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	@Column(length = 32)
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	@Column(length = 32)
	public String getSupportDate() {
		return supportDate;
	}
	public void setSupportDate(String supportDate) {
		this.supportDate = supportDate;
	}
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
	@Column(length = 1000)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public double getSumDay() {
		return sumDay;
	}
	public void setSumDay(double sumDay) {
		this.sumDay = sumDay;
	}
	@Column(length = 256)
	public String getJober() {
		return jober;
	}
	public void setJober(String jober) {
		this.jober = jober;
	}
	@Column(length = 512)
	public String getJoberId() {
		return joberId;
	}
	public void setJoberId(String joberId) {
		this.joberId = joberId;
	}
	@Column(length = 32)
	public String getSumDate() {
		return sumDate;
	}
	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}
	@Column(length = 64)
	public String getSumUserName() {
		return sumUserName;
	}
	public void setSumUserName(String sumUserName) {
		this.sumUserName = sumUserName;
	}
	@Column(length = 32)
	public String getSumUserId() {
		return sumUserId;
	}
	public void setSumUserId(String sumUserId) {
		this.sumUserId = sumUserId;
	}
	@Column(length = 1000)
	public String getSumRemark() {
		return sumRemark;
	}
	public void setSumRemark(String sumRemark) {
		this.sumRemark = sumRemark;
	}
	@Column(length = 32)
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Qjgl.class, true, ActionType.JSP);
	}
	
	
}
