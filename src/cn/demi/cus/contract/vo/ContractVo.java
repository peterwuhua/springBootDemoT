package cn.demi.cus.contract.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.cus.customer.vo.CustomerVo;

/**
 * Create on : 2016年11月15日 下午2:51:09 <br>
 * Description : 合同信息Vo <br>
 * 
 * @version v 1.0.0 <br>
 * @author Danlee Li<br>
 */
public class ContractVo extends Vo<ContractVo> {

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
	private float payPrice;// 最终优惠价格
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
	private CustomerVo customerVo;
	/**
	 * 上传文件路径
	 */
	private String path;
	/**
	 * 上传文件真实路径
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

	private String flag;// 合同列表1，执行中2，已完结3 ，快到到期4，已超期 { 合同（销售跟踪）状态}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdime() {
		return edime;
	}

	public void setEdime(String edime) {
		this.edime = edime;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getContractSum() {
		return contractSum;
	}

	public void setContractSum(String contractSum) {
		this.contractSum = contractSum;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public float getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(float payPrice) {
		this.payPrice = payPrice;
	}

	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}

	public String getSalerId() {
		return salerId;
	}

	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
