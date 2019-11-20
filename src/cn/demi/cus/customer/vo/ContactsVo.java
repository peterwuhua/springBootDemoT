package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;
/**
 * Create on : 2016年11月15日 下午2:50:58  <br>
 * Description : 联系人Vo  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public class ContactsVo extends Vo<ContactsVo> {
	/**
	 * 联系人名称 
	 */
	private String name;
	/**
	 * 客户地址
	 */
	private String address;
	/**
	 * 客户电话
	 */
	private String phone;
	/**
	 * 客户邮箱
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
	 * 客户信息
	 */
	private CustomerVo customerVo;
	
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
	public CustomerVo getCustomerVo() {
		return customerVo;
	}
	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
}

