package cn.demi.cus.contract.po;

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
import cn.demi.cus.customer.po.Customer;

/**
 * Create on : 2016年11月15日 下午2:49:26  <br>
 * Description :合同实体对象   <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Entity(name = "cus_contract")
@Table(name = "cus_contract")
@Module(value = "cus.contract")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contract extends Po<Contract>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","code","name","sdate","edime","num","discount","contractSum","customer","payType","payWay","contacts","saler","status"};
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","status","customer","orgId","orgName","leadId","leadName"};
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	/**
	 * 合同编号
	 */
	private String code;
	/**
	 * 合同名称
	 */
	private String name;
	/**
	 * 开始日期
	 */
	private String sdate;
	/**
	 * 结束日期
	 */
	private String edime;
	/**
	 * 样品数量
	 */
	private String num;
	/**
	 * 优惠折扣
	 */
	private String discount;
	/**
	 * 合同金额
	 */
	private String contractSum;
	private String payPrice;//最终优惠价格
	/**
	 * 付款类型
	 */
	private String payType;
	/**
	 * 付款方式
	 */
	private String payWay;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 客户信息
	 */
	private Customer customer;
	/**
	 * 上传文件存储路径
	 */
	private String path;
	/**
	 * 上传文件真实名称
	 */
	private String trueName;
	/**
	 * 合同执行状态
	 */
	private String status;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系人电话
	 */
	private String contactPhone;
	/**
	 * 客户经理
	 */
	private String saler;
	private String salerId;
	/**
	 * 领导
	 */
	private String leadId;
	private String leadName;
	/**
	 * 部门
	 */
	private String orgId;
	private String orgName;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Lob
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=32)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=16)
	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	@Column(length=16)
	public String getEdime() {
		return edime;
	}

	public void setEdime(String edime) {
		this.edime = edime;
	}
	@Column(length=16)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	@Column(length=16)
	public String getContractSum() {
		return contractSum;
	}
	public void setContractSum(String contractSum) {
		this.contractSum = contractSum;
	}

	@Column(length=16)
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	@Column(length=32)
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	@Column(length=32)
	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	@Column(length=64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length=128)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	@Column(length=64)
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	
	@Column(length=32)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(length=32)
	public String getContacts() {
		return contacts;
	}


	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	@Column(length=32)
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	@Column(length=11)
	public String getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}
	@Column(length=32)
	public String getSaler() {
		return saler;
	}
	public void setSaler(String saler) {
		this.saler = saler;
	}
	@Column(length=32)
	public String getSalerId() {
		return salerId;
	}

	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}
	@Column(length=32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length=32)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length=32)
	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	@Column(length=32)
	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}
	@Column(length=256)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

}
