package cn.demi.bus.pjt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.cus.customer.po.Client;
import cn.demi.cus.customer.po.Customer;

/**
 * 任务客户关系
 * 
 * @author QuJunLong
 */
@Entity(name = "bus_cust")
@Table(name = "bus_cust")
@Module(value = "bus.cust")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cust extends Po<Cust> {
	private static final long serialVersionUID = 1L;

	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = { "id", "client", "custName", "isDel", "custUser", "custTel","custAddress", "custEmail", "custZip"
			,"attribute","industry","product","wtCust","wtName","wtUser","wtTel","wtAddress","wtEmail","wtZip","cyUnit","cyUser"};

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version","client","customer"};
	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}
	//受检单位信息
	private Client client;// 客户
	private String custName;
	private String custJdr;//受检单位接待人/采样接待人
	private String custUser;
	private String custTel;// 联系电话
	private String custAddress;// 地址、
	private String custEmail;// 邮箱
	private String custZip;// 邮编
	private String custFax;// 传真
	private String industry;//客户行业和代码
	private String attribute;// 单位性质
	private String product;// 主要产品
	private String tkJdr;//踏勘接待人
	//委托单位信息
	private Customer customer;// 客户
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

	@ManyToOne
	@JoinColumn(name = "client_id")
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	@Column(length = 64)
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(length = 32)
	public String getCustUser() {
		return custUser;
	}

	public void setCustUser(String custUser) {
		this.custUser = custUser;
	}

	@Column(length = 32)
	public String getCustTel() {
		return custTel;
	}

	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}

	@Column(length = 128)
	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	@Column(length = 64)
	public String getCustEmail() {
		return custEmail;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	@Column(length = 64)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(length = 128)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(length = 32)
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(length = 32)
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Column(length = 128)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(length = 32)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(length = 64)
	public String getCustZip() {
		return custZip;
	}

	public void setCustZip(String custZip) {
		this.custZip = custZip;
	}
	@Column(length = 64)
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	@Column(length = 64)
	public String getCustFax() {
		return custFax;
	}
	public void setCustFax(String custFax) {
		this.custFax = custFax;
	}
	@Column(length = 64)
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	@Column(length = 128)
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	@ManyToOne
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Column(length = 64)
	public String getWtName() {
		return wtName;
	}
	public void setWtName(String wtName) {
		this.wtName = wtName;
	}
	@Column(length = 32)
	public String getWtUser() {
		return wtUser;
	}
	public void setWtUser(String wtUser) {
		this.wtUser = wtUser;
	}
	@Column(length = 64)
	public String getWtTel() {
		return wtTel;
	}
	public void setWtTel(String wtTel) {
		this.wtTel = wtTel;
	}
	@Column(length = 128)
	public String getWtAddress() {
		return wtAddress;
	}
	public void setWtAddress(String wtAddress) {
		this.wtAddress = wtAddress;
	}
	@Column(length = 64)
	public String getWtEmail() {
		return wtEmail;
	}
	public void setWtEmail(String wtEmail) {
		this.wtEmail = wtEmail;
	}
	@Column(length = 32)
	public String getWtZip() {
		return wtZip;
	}
	public void setWtZip(String wtZip) {
		this.wtZip = wtZip;
	}
	@Column(length = 64)
	public String getCyUnit() {
		return cyUnit;
	}
	public void setCyUnit(String cyUnit) {
		this.cyUnit = cyUnit;
	}
	@Column(length = 32)
	public String getCyUser() {
		return cyUser;
	}
	public void setCyUser(String cyUser) {
		this.cyUser = cyUser;
	}
	@Column(length = 32)
	public String getCustJdr() {
		return custJdr;
	}
	public void setCustJdr(String custJdr) {
		this.custJdr = custJdr;
	}
	@Column(length = 32)
	public String getWtFax() {
		return wtFax;
	}
	public void setWtFax(String wtFax) {
		this.wtFax = wtFax;
	}
	@Column(length = 64)
	public String getWtIndustry() {
		return wtIndustry;
	}
	public void setWtIndustry(String wtIndustry) {
		this.wtIndustry = wtIndustry;
	}
	@Column(length = 64)
	public String getWtAttribute() {
		return WtAttribute;
	}

	public void setWtAttribute(String wtAttribute) {
		WtAttribute = wtAttribute;
	}
	@Column(length = 64)
	public String getTkJdr() {
		return tkJdr;
	}
	public void setTkJdr(String tkJdr) {
		this.tkJdr = tkJdr;
	}
}
