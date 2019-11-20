package cn.demi.cus.sale.vo;

import cn.core.framework.common.vo.Vo;
/**
 * 
 * @ClassName:  CustContactVo   
 * @Description:客户关怀vo  
 * @author: 吴华 
 * @date:   2019年1月28日 上午10:02:51       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
public class CustContactVo extends Vo<CustContactVo> {
	
	
	
	
	/**
	 * 客户id
	 */
	private String customerId;
	
	/**
	 * 客户名称
	 */
	private String customer;
	
	/**
	 * 销售人员id
	 */
	private String salerId;
	
	/**
	 * 销售人员
	 */
	private String saler;
	
	/**
	 * 领导id
	 */
	private String leaderId;

	/**
	 * 领导
	 */
	private String leader;
	
	/**
	 * 联系人名称
	 */
	private String contactName;
	
	/**
	 * 联系人id
	 */
	private String contactId;
	
	/**
	 * 联系人出生年月
	 */
	private String birthDate;
	
	/**
	 * 联系人电话
	 */
	private String phone;
	
	/**
	 * 职位
	 */
	private String duty;
	
	/**
	 * 备注
	 */
	private String notes;
	
	/**
	 * 部门
	 */
	private String departMent;
	
	
	

	public String getDepartMent() {
		return departMent;
	}

	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
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

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	} 
	
	
	
	
	
}

