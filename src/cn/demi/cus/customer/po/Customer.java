package cn.demi.cus.customer.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * Create on : 2016年11月15日 下午2:50:00  <br>
 * Description :公共池客户实体对象  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Entity(name = "cus_customer")
@Table(name = "cus_customer")
@Module(value = "cus.customer")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer extends Po<Customer>{

	public static final String ST_F1="-1";
	public static final String ST_0="0";
	public static final String ST_1="1";
	public static final String ST_2="2";
	public static final String ST_3="3";
	public static final String ST_4="4";
	public static final String ST_5="5";
	
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","name","code","gjStatus","cusType","industry","buildDate","grade","areaPath","saler","salerId","leader","leaderId",
			"address","person","phone","telephone","email","payType","payWay","remark","fax","zip","status","lastTestDate","custFaRen","custCode","jgCode","custCard","sumUserName","sumDate","sumRemark","cusCates","fstatus","auditStatus","supportDate","sydm","qsl","auth"};
	
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","status"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	
	
	private String auditStatus; //审批状态 { 审批中 ，审批通过}
	
	
	/**
	 * 审核字段
	 */
	private String sumUserName; //审核人
	private String sumDate; //审核日期
	private String sumRemark; //审核意见 
	
	
    /**
     * 跟进状态
     */
	private String gjStatus;//跟进状态
	
	private String cusCates;//客户类型  {近期服务客户,中期服务客户,长期服务客户,大客户}
	
	
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
	 * 提交日期
	 */
	private String supportDate;
	
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
	 * 联系人
	 */
	private String person;
	/**
	 * 传真
	 */
	private String fax;
	
	private String zip;
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
	 * 是否同步至客户服务系统
	 */
	private int isSysn=N;
	
	//发票信息
	private String custCode;//纳税人识别号   个人身份证
	private String custAddress;//注册地址
	private String custTel;//注册电话
	private String custBank;//开户银行
	private String custAccount;//开户账号
	private String custFaRen;//法人
 
	private String custCard;//营业执照
	private String jgCode;//机构代码
	private Integer cycle;//周期（月）
	private String lastTestDate;//上次检测日期
	private String testDate;//检测日期
	private String status;//回访状态 
	
	private String fstatus;//客户状态（-1:审核不通过、 0:最初状态、1:业务员自己新增的 、 2:经理审核通过 、3、经理指派）
	
	private String sydm;//水源代码
	private String qsl;//月取水量
	
	
	private String auth;//权限
	
	
	@Column(length = 10)
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	@Column(length = 32)
	public String getSydm() {
		return sydm;
	}
	public void setSydm(String sydm) {
		this.sydm = sydm;
	}
	@Column(length = 32)
	public String getQsl() {
		return qsl;
	}
	public void setQsl(String qsl) {
		this.qsl = qsl;
	}
	@Column(length=32)
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	@Column(length=32)
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	@Column(length=8)
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}

	@Column(length=64)
	public String getSumUserName() {
		return sumUserName;
	}
	public void setSumUserName(String sumUserName) {
		this.sumUserName = sumUserName;
	}
	@Column(length=64)
	public String getSumDate() {
		return sumDate;
	}
	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}
	@Lob
	public String getSumRemark() {
		return sumRemark;
	}
	public void setSumRemark(String sumRemark) {
		this.sumRemark = sumRemark;
	}
	@Column(length=32)
	public String getCusCates() {
		return cusCates;
	}
	public void setCusCates(String cusCates) {
		this.cusCates = cusCates;
	}
	@Column(length=8)
	public String getGjStatus() {
		return gjStatus;
	}
	public void setGjStatus(String gjStatus) {
		this.gjStatus = gjStatus;
	}
	@Column(length=32)
	public int getIsSysn() {
		return isSysn;
	}

	public void setIsSysn(int isSysn) {
		this.isSysn = isSysn;
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
	@Column(length=32)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=128)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length=128)
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	@Column(length=20)
	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}
	@Column(length=100)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	@Column(length=100)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	@Column(length=100)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@Column(length=100)
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	@Column(length=128)
	public String getAreaPath() {
		return areaPath;
	}

	public void setAreaPath(String areaPath) {
		this.areaPath = areaPath;
	}
	@Column(length=32)
	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}
	@Column(length=128)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length=16)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(length=32)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length=32)
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
	@Lob
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 64)
	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	@Column(length = 64)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	@Column(length = 32)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column(length = 64)
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	@Column(length = 64)
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	@Column(length = 128)
	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	@Column(length = 16)
	public String getCustTel() {
		return custTel;
	}

	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	@Column(length = 64)
	public String getCustBank() {
		return custBank;
	}

	public void setCustBank(String custBank) {
		this.custBank = custBank;
	}
	@Column(length = 64)
	public String getCustAccount() {
		return custAccount;
	}

	public void setCustAccount(String custAccount) {
		this.custAccount = custAccount;
	}
	@Column(length = 20)
	public String getLastTestDate() {
		return lastTestDate;
	}

	public void setLastTestDate(String lastTestDate) {
		this.lastTestDate = lastTestDate;
	}
	@Column(length = 20)
	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	@Column(length = 4)
	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}
	@Column(length = 32)
	public String getSalerId() {
		return salerId;
	}
	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}
	@Column(length = 8)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 16)
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(length = 32)
	public String getCustFaRen() {
		return custFaRen;
	}
	public void setCustFaRen(String custFaRen) {
		this.custFaRen = custFaRen;
	}
	@Column(length = 32)
	public String getCustCard() {
		return custCard;
	}
	public void setCustCard(String custCard) {
		this.custCard = custCard;
	}
	@Column(length = 32)
	public String getJgCode() {
		return jgCode;
	}
	public void setJgCode(String jgCode) {
		this.jgCode = jgCode;
	}
	@Column(length = 8)
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	@Column(length=64)
	public String getSupportDate() {
		return supportDate;
	}
	public void setSupportDate(String supportDate) {
		this.supportDate = supportDate;
	}
	
	
	
	
}
