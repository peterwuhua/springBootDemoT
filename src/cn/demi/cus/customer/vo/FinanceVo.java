package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;

/**
 * Create on : 2016年11月15日 下午2:51:47  <br>
 * Description : 账户信息Vo<br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public class FinanceVo extends Vo<FinanceVo> {

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
	 * 客户表
	 */
	private CustomerVo customerVo;
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
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}*/
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
	/*public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}*/
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public CustomerVo getCustomerVo() {
		return customerVo;
	}
	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
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
	
}

