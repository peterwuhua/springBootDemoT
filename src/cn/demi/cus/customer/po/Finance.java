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
 * Create on : 2016年11月15日 下午2:50:11  <br>
 * Description : 账户信息  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Entity(name = "cus_finance")
@Table(name = "cus_finance")
@Module(value = "cus.finance")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Finance extends Po<Finance>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","name","address","bank","accountNum","bankNum","settlementNum","customer","registerNum"};
	/**
	 * 单位名称
	 */
	private String name;
	/**
	 * 注册名称
	 */
	//private String regName;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 纳税人识别号
	 */
	//private String no;
	/**
	 * 发票抬头
	 */
	//private String title;
	/**
	 * 开户银行
	 */
	private String bank;
	/**
	 * 账号
	 */
	private String accountNum;
	/**
	 * 行号
	 */
	private String bankNum;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 同城结算号
	 */
	private String settlementNum;
	/**
	 * 税务登记号
	 */
	private String registerNum;
	/**
	 * 邮编
	 */
	private String zipCode;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 联系人
	 */
	private String contacts;
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

	@Column(length=64)
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	/*@Column(length=64)
	public String getRegName() {
		return regName;
	}


	public void setRegName(String regName) {
		this.regName = regName;
	}*/

	@Column(length=128)
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

	/*@Column(length=32)
	public String getNo() {
		return no;
	}


	public void setNo(String no) {
		this.no = no;
	}

	@Column(length=32)
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}*/

	@Column(length=32)
	public String getBank() {
		return bank;
	}


	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(length=32)
	public String getBankNum() {
		return bankNum;
	}


	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	@Lob
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public String getAccountNum() {
		return accountNum;
	}


	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}


	public String getSettlementNum() {
		return settlementNum;
	}


	public void setSettlementNum(String settlementNum) {
		this.settlementNum = settlementNum;
	}


	public String getRegisterNum() {
		return registerNum;
	}


	public void setRegisterNum(String registerNum) {
		this.registerNum = registerNum;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getContacts() {
		return contacts;
	}


	public void setContacts(String contacts) {
		this.contacts = contacts;
	}


	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}


}
