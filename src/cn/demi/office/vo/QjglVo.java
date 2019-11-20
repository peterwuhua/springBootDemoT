package cn.demi.office.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class QjglVo extends Vo<QjglVo> {
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
	private double sumDay;//共计小时（取两位小数）  
	private String jober; //职位代理人
	private String joberId; //职位代理人id
	
	/**
	 * 审批
	 */
	private String sumDate; //审批日期
	private String sumUserName;//审批人
	private String sumUserId;//审批人id
	private String sumRemark; //审批意见
	
	private String fstatus; //请假状态
	
	
	/**
	 * 销假
	 */
	private String xjshow; //是否销假（1:已销假、0:未销假）

	
	private List<String> typeList; //类型列表
	
	
	
	public String getXjshow() {
		return xjshow;
	}

	public void setXjshow(String xjshow) {
		this.xjshow = xjshow;
	}

	public List<String> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getSupportDate() {
		return supportDate;
	}

	public void setSupportDate(String supportDate) {
		this.supportDate = supportDate;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getSumDay() {
		return sumDay;
	}

	public void setSumDay(double sumDay) {
		this.sumDay = sumDay;
	}

	public String getJober() {
		return jober;
	}

	public void setJober(String jober) {
		this.jober = jober;
	}

	public String getJoberId() {
		return joberId;
	}

	public void setJoberId(String joberId) {
		this.joberId = joberId;
	}

	public String getSumDate() {
		return sumDate;
	}

	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}

	public String getSumUserName() {
		return sumUserName;
	}

	public void setSumUserName(String sumUserName) {
		this.sumUserName = sumUserName;
	}

	public String getSumUserId() {
		return sumUserId;
	}

	public void setSumUserId(String sumUserId) {
		this.sumUserId = sumUserId;
	}

	public String getSumRemark() {
		return sumRemark;
	}

	public void setSumRemark(String sumRemark) {
		this.sumRemark = sumRemark;
	}

	public String getFstatus() {
		return fstatus;
	}

	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}

	
}

