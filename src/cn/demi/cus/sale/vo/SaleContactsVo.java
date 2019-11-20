package cn.demi.cus.sale.vo;

import cn.core.framework.common.vo.Vo;

public class SaleContactsVo extends Vo<SaleContactsVo> {
	private String salerId; //销售id
	private String saler; //销售名称
	private String customerId; //客户id
	private String customerName; //客户名称
	
	private String leaderId; //领导id
	private String leaderName; //领导名称
	
	private String contactWay; //联系方式（phone）
	private String contactPerson; //联系人
	private String contactId; //联系人id
	private String duty; //职务
	private String department;//所在部门
	private String email;//email
	private String birthDate;//birthDate
	
	
	private String content; //内容
	private String gzDate; //跟踪日期
	private String address; //拜访地点
	
	
	
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSalerId() {
		return salerId;
	}
	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}
	public String getSaler() {
		return saler;
	}
	public void setSaler(String saler) {
		this.saler = saler;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getContactWay() {
		return contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGzDate() {
		return gzDate;
	}
	public void setGzDate(String gzDate) {
		this.gzDate = gzDate;
	}
	
	
}

