package cn.demi.bus.pjt.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.cus.customer.vo.ClientVo;
import cn.demi.cus.customer.vo.CustomerVo;

public class CustVo extends Vo<CustVo> {
	 
	//受检单位信息
	private ClientVo clientVo;// 客户
	private String custName;
	private String custJdr;//受检单位接待人/采样接待人
	private String custUser;
	private String custTel;// 联系电话
	private String custAddress;// 地址
	private String custEmail;// 邮箱
	private String custZip;// 邮编
	private String custFax;// 传真
	private String industry;//客户行业和代码
	private String attribute;// 单位性质
	private String product;// 主要产品
	private String tkJdr;//踏勘接待人
	//委托单位信息
	private CustomerVo customerVo;// 客户
	private String wtName;
	private String wtUser;
	private String wtTel;// 联系电话
	private String wtAddress;// 地址
	private String wtEmail;// 邮箱
	private String wtZip;// 邮编
	private String wtFax;// 传真
	private String wtIndustry;//客户行业和代码
	private String WtAttribute;// 单位性质
	//采样单位
	private String cyUnit;//采样单位
	private String cyUser;//采样人
	//测试单位信息
	private String unit;// 测试单位
	private String address;// 地址
	private String zip;// 邮编
	private String user;// 联系人
	private String url;// 联系人
	private String tel;// 电话
	
	public ClientVo getClientVo() {
		return clientVo;
	}
	public void setClientVo(ClientVo clientVo) {
		this.clientVo = clientVo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustUser() {
		return custUser;
	}
	public void setCustUser(String custUser) {
		this.custUser = custUser;
	}
	public String getCustTel() {
		return custTel;
	}
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public String getCustZip() {
		return custZip;
	}
	public void setCustZip(String custZip) {
		this.custZip = custZip;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCustFax() {
		return custFax;
	}
	public void setCustFax(String custFax) {
		this.custFax = custFax;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public CustomerVo getCustomerVo() {
		return customerVo;
	}
	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
	}
	public String getWtName() {
		return wtName;
	}
	public void setWtName(String wtName) {
		this.wtName = wtName;
	}
	public String getWtUser() {
		return wtUser;
	}
	public void setWtUser(String wtUser) {
		this.wtUser = wtUser;
	}
	public String getWtTel() {
		return wtTel;
	}
	public void setWtTel(String wtTel) {
		this.wtTel = wtTel;
	}
	public String getWtAddress() {
		return wtAddress;
	}
	public void setWtAddress(String wtAddress) {
		this.wtAddress = wtAddress;
	}
	public String getWtEmail() {
		return wtEmail;
	}
	public void setWtEmail(String wtEmail) {
		this.wtEmail = wtEmail;
	}
	public String getWtZip() {
		return wtZip;
	}
	public void setWtZip(String wtZip) {
		this.wtZip = wtZip;
	}
	public String getCyUnit() {
		return cyUnit;
	}
	public void setCyUnit(String cyUnit) {
		this.cyUnit = cyUnit;
	}
	public String getCyUser() {
		return cyUser;
	}
	public void setCyUser(String cyUser) {
		this.cyUser = cyUser;
	}
	public String getCustJdr() {
		return custJdr;
	}
	public void setCustJdr(String custJdr) {
		this.custJdr = custJdr;
	}
	public String getWtFax() {
		return wtFax;
	}
	public void setWtFax(String wtFax) {
		this.wtFax = wtFax;
	}
	public String getWtIndustry() {
		return wtIndustry;
	}
	public void setWtIndustry(String wtIndustry) {
		this.wtIndustry = wtIndustry;
	}
	public String getWtAttribute() {
		return WtAttribute;
	}
	public void setWtAttribute(String wtAttribute) {
		WtAttribute = wtAttribute;
	}
	public String getTkJdr() {
		return tkJdr;
	}
	public void setTkJdr(String tkJdr) {
		this.tkJdr = tkJdr;
	}
}

