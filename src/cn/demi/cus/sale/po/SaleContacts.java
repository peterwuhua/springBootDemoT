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
 * @ClassName: SaleContacts
 * @Description:客户跟踪
 * @author: 吴华
 * @date: 2019年1月29日 下午4:43:09
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 *
 */
@Entity(name = "cus_sale_contact")
@Table(name = "cus_sale_contact")
@Module(value = "cus.saleContact")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SaleContacts extends Po<SaleContacts> {

	public String[] PROPERTY_TO_MAP = { "id", "sort", "salerId", "saler", "leaderId", "leaderName", "content", "customerId", "customerName", "contactWay",
			"gzDate", "address", "contactPerson", "department", "duty", "contactId" };
	public String[] IGNORE_PROPERTY_TO_PO = { "id", "createTime", "lastUpdTime", "version", "status" };

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

	private String salerId; // 销售id
	private String saler; // 销售名称
	private String leaderId; // 领导id
	private String leaderName; // 领导名称
	private String customerId; // 客户id
	private String customerName; // 客户名称
	private String contactWay; // 联系方式
	private String contactPerson; // 联系人
	private String contactId; // 联系人id
	private String department;// 所在部门
	private String duty;// 职务
	private String email;// email
	private String birthDate;// birthDate
	private String content; // 内容
	private String gzDate; // 跟踪日期
	private String address; // 拜访地点

	@Column(length = 32)
	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	@Column(length = 128)
	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	@Column(length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 64)
	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	@Column(length = 32)
	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	@Column(length = 128)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(length = 32)
	public String getSalerId() {
		return salerId;
	}

	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}

	@Column(length = 128)
	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}

	@Column(length = 32)
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(length = 128)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(length = 32)
	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	@Column(length = 1000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 32)
	public String getGzDate() {
		return gzDate;
	}

	public void setGzDate(String gzDate) {
		this.gzDate = gzDate;
	}

	@Column(length = 32)
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(length = 64)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(length = 64)
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(SaleContacts.class, false, ActionType.JSP);
	}

}
