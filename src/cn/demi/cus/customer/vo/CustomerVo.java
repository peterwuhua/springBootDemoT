package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;

/**
 * Create on : 2016年11月15日 下午2:51:38  <br>
 * Description : 客户信息Vo <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
public class CustomerVo extends Vo<CustomerVo> {
	
	private String auth; //权限

	/**
	 * 客户类型
	 */
	private String cusCates; 
	
	private String auditStatus; //审批状态 0:审批中 1:审批通过
	
	/**
	 * 审核字段
	 */
	private String sumUserName; //审核人
	private String sumDate; //审核日期
	private String sumRemark; //审核意见 
	
	
	
	
    /**
     * 跟进状态
     */
	private String gjStatus;//跟进状态  0:未签合同 1:已签合同
	
	
	/**
	 * 客户编号
	 */
	private String code;
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 属性
	 */
	private String cusType;
	/**
	 * 客户行业
	 */
	private String industry;
	/**
	 * 客户建立时间
	 */
	private String buildDate;
	/**
	 * 客户级别
	 */
	private String grade;
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
	 * 销售
	 */
	private String saler;
	private String salerId;
	
	/**
	 * 销售经理
	 */
	private String leader;
	private String leaderId;
	
	/**
	 * 客户地址
	 */
	private String address;
	/**
	 * 电话
	 */
	private String telephone;// 电话
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
	/**
	 * 联系人
	 */
	private String person;
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
	 * 客户账户信息
	 */
	private AccountVo accountVo;
	
	//发票信息
	private String custCode;//纳税人识别号
	private String custAddress;//注册地址
	private String custTel;//注册电话
	private String custBank;//开户银行
	private String custAccount;//开户账号
	private String custFaRen;//法人
	private String custCard;//营业执照
	private String jgCode;//机构代码
	//周期性客户
	private Integer cycle;//周期（月）
	private String lastTestDate;//上次检测日期
	private String testDate;//检测日期
	private String status;//回访状态 0一般情况，1继续跟踪，2回访完成
	
	private String fstatus;//客户状态（-1:审核不通过、 0:最初状态、1:业务员自己新增的 、 2:经理审核通过 、3:经理指派）
	
	
	private String supportDate;//提交日期
	
	
	private String sydm;//水源代码
	private String qsl;//月取水量
	
	
	
	
	
	public String getSydm() {
		return sydm;
	}
	public void setSydm(String sydm) {
		this.sydm = sydm;
	}
	public String getQsl() {
		return qsl;
	}
	public void setQsl(String qsl) {
		this.qsl = qsl;
	}
	public String getSupportDate() {
		return supportDate;
	}
	public void setSupportDate(String supportDate) {
		this.supportDate = supportDate;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
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
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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
	public String getSaler() {
		return saler;
	}
	public void setSaler(String saler) {
		this.saler = saler;
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
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public AccountVo getAccountVo() {
		return accountVo;
	}
	public void setAccountVo(AccountVo accountVo) {
		this.accountVo = accountVo;
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
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
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
	public Integer getCycle() {
		return cycle;
	}
	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}
	public String getLastTestDate() {
		return lastTestDate;
	}
	public void setLastTestDate(String lastTestDate) {
		this.lastTestDate = lastTestDate;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public String getSalerId() {
		return salerId;
	}
	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCustFaRen() {
		return custFaRen;
	}
	public void setCustFaRen(String custFaRen) {
		this.custFaRen = custFaRen;
	}
	public String getCustCard() {
		return custCard;
	}
	public void setCustCard(String custCard) {
		this.custCard = custCard;
	}
	public String getJgCode() {
		return jgCode;
	}
	public void setJgCode(String jgCode) {
		this.jgCode = jgCode;
	}
	public String getGjStatus() {
		return gjStatus;
	}
	public void setGjStatus(String gjStatus) {
		this.gjStatus = gjStatus;
	}
	public String getCusCates() {
		return cusCates;
	}
	public void setCusCates(String cusCates) {
		this.cusCates = cusCates;
	}
	public String getSumUserName() {
		return sumUserName;
	}
	public void setSumUserName(String sumUserName) {
		this.sumUserName = sumUserName;
	}
	public String getSumDate() {
		return sumDate;
	}
	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}
	public String getSumRemark() {
		return sumRemark;
	}
	public void setSumRemark(String sumRemark) {
		this.sumRemark = sumRemark;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}


	
	
	
	
}

