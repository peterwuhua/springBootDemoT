package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;

/**
 * Create on : 2016年11月15日 下午2:52:10  <br>
 * Description : 生产单位Vo   <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public class ProduceVo extends Vo<ProduceVo> {
	
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 客户行业
	 */
	private String industry;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 县
	 */
	private String county;
	/**
	 * 区域
	 */
	private String areaPath;
	/**
	 * 区域Id
	 */
	private String areaId;
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
	 * 联系人
	 */
	private String person;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 客户信息
	 */
	private CustomerVo cusVo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAreaPath() {
		return areaPath;
	}
	public void setAreaPath(String areaPath) {
		this.areaPath = areaPath;
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
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public CustomerVo getCusVo() {
		return cusVo;
	}
	public void setCusVo(CustomerVo cusVo) {
		this.cusVo = cusVo;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	
}

