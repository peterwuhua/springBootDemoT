package cn.demi.res.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 分包单位管理
 * @author QuJunLong
 *
 */
@Entity(name = "res_fb")
@Table(name = "res_fb")
@Module(value = "res.fb")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fb extends Po<Fb>{

 
	private static final long serialVersionUID = 3486710772440199006L;
	public String[] PROPERTY_TO_MAP= {"id","sort","name","code","type","buildDate","grade","areaPath",
			"address","person","phone","email","remark","fax","zip"};
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 单位名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private String type;
	 
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
	 * 传真
	 */
	private String fax;
	/**
	 * 邮编
	 */
	private String zip;
 
	/**
	 * 付款方式
	 */
	private String payWay;
	private String zz;//资质
	private String startDate;//资质有效期
	private String endDate;
	
	private String userId;//负责人
	private String userName;
	/**
	 * 备注
	 */
	private String remark;
	
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
	@Column(length=16)
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
	@Column(length = 32)
	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getZip() {
		return zip;
	}
	@Column(length = 32)
	public void setZip(String zip) {
		this.zip = zip;
	}
	@Column(length = 32)	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 128)	
	public String getZz() {
		return zz;
	}
	public void setZz(String zz) {
		this.zz = zz;
	}
	@Column(length = 20)	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Column(length = 20)
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Column(length = 32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 32)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Fb.class, true, ActionType.JSP);
	}
}
