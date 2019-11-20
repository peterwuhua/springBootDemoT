package cn.demi.cus.sale.po;

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
 * @ClassName:  CustContact   
 * @Description:客户关怀
 * @author: 吴华 
 * @date:   2019年1月28日 上午9:14:14       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Entity(name = "cus_cust_contact")
@Table(name = "cus_cust_contact")
@Module(value = "cus.custContact")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustContact extends Po<CustContact>{

	public String[] PROPERTY_TO_MAP= {"id","sort","customerId","customer","salerId","saler","leaderId","leader","contactName","contactId","birthDate","phone","duty","notes","departMent"};
	public String[] IGNORE_PROPERTY_TO_PO= {"createTime","lastUpdTime","version","status"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	
	
	/**
	 * 公司id
	 */
	private String customerId;
	
	/**
	 * 公司名称
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
	 * 部门
	 */
	private String departMent;
	
	/**
	 * 备注
	 */
	private String notes;

	
	@Column(length=64)
	public String getDepartMent() {
		return departMent;
	}
	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}
	@Column(length=32)
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	@Column(length=64)
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	@Column(length=32)
	public String getSalerId() {
		return salerId;
	}
	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}
	@Column(length=64)
	public String getSaler() {
		return saler;
	}
	public void setSaler(String saler) {
		this.saler = saler;
	}
	@Column(length=32)
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	@Column(length=64)
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	@Column(length=64)
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	@Column(length=32)
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	@Column(length=64)
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	@Column(length=16)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(length=32)
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	@Column(length=1000)
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(CustContact.class, false, ActionType.JSP);
	}
	
	
	
}
