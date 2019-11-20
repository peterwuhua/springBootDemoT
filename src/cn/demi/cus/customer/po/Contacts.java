package cn.demi.cus.customer.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
/**
 * Create on : 2016年11月15日 下午2:49:15  <br>
 * Description : 联系人实体对象  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Entity(name = "cus_contacts")
@Table(name = "cus_contacts")
@Module(value = "cus.contacts")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contacts extends Po<Contacts>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","name","address","phone","email","depart","duty","remark","customer","birthDate"};

	/**
	 * 联系人名称 
	 */
	private String name;
	/**
	 * 联系人地址
	 */
	private String address;
	/**
	 * 联系人电话
	 */
	private String phone;
	/**
	 * 联系人邮箱
	 */
	private String email;
	/**
	 * 部门
	 */
	private String depart;
	/**
	 * 职务
	 */
	private String duty;
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 出生年月
	 */
	private String birthDate;
	/**
	 * 客户表
	 */
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Column(length=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length=255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length=16)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(length=100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Lob
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	
	@Column(length=64)
	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}


}
