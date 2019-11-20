package cn.demi.cus.customer.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
/**
 * Create on : 2016年11月15日 下午2:50:48  <br>
 * Description : 受检单位Vo <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public class ClientVo extends Vo<ClientVo> {
	
	/**
	 * 受检单位名称
	 */
	private String name;
	/**
	 * 受检单位行业
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
	 * 受检单位电话
	 */
	private String phone;
	/**
	 * 受检单位邮箱
	 */
	private String email;
	//传真
	private String fax;
	//邮编
	private String zip;
	private String attribute;// 单位性质
	private String product;// 主要产品
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
	private CustomerVo customerVo;
	private List<ClientPointVo> cpList;//检测点位信息
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
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public CustomerVo getCustomerVo() {
		return customerVo;
	}
	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
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
	public List<ClientPointVo> getCpList() {
		return cpList;
	}
	public void setCpList(List<ClientPointVo> cpList) {
		this.cpList = cpList;
	}
}

