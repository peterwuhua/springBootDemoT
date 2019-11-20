package cn.demi.cus.customer.vo;
/**
 * 客户简要信息
 * @author QuJunLong
 *
 */
public class CustVo {
	/**
	 * 客户编号
	 */
	private String id;
	/**
	 * 客户编号
	 */
	private String code;
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 客户属性
	 */
	private String cusType;
	/**
	 * 客户地址
	 */
	private String address;
	/**
	 * 客户电话
	 */
	private String telephone;
	/**
	 * 客户电话
	 */
	private String phone;
	/**
	 * 客户邮箱
	 */
	private String email;
	/**
	 * 传真
	 */
	private String fax;
	private String zip;

	private String attribute;// 单位性质
	private String product;// 主要产品
	/**
	 * 联系人
	 */
	private String person;
	private String custCode;//纳税人识别号
	private String custAddress;//注册地址
	private String custTel;//注册电话
	private String custBank;//开户银行
	private String custAccount;//开户账号

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getCustTel() {
		return custTel;
	}
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	public String getCustBank() {
		return custBank;
	}
	public void setCustBank(String custBank) {
		this.custBank = custBank;
	}
	public String getCustAccount() {
		return custAccount;
	}
	public void setCustAccount(String custAccount) {
		this.custAccount = custAccount;
	}
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
}
