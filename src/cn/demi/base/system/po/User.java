package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.ChineseFCUtil;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name = "sys_user")
@Table(name = "sys_user")
@Module(value = "sys.user")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends Po<User> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","no", "name", "cname", "sex", "birthday", "mobile", "telephone", "email","orgName",
			"address", "nation", "workDate", "education", "profession", "duty", "techTitle", "credentialsType",
			"credentialsNo" ,"remark","status","photo","residenceType","graduationDate","graduationSchool","regularDate","workYear","postSubsidy","majorSkill","conDeadline"};
	/**
	 * 编号
	 */
	private String no;// 编号
	/**
	 * 用户名称
	 */
	private String name;// 用户名称
	/**
	 * 拼音首字母
	 */
	private String cname;// 拼音首字母
	/**
	 * 用户性别
	 */
	private String sex;// 用户性别
	/**
	 * 用户生日
	 */
	private String birthday;// 用户生日
	/**
	 * 手机
	 */
	private String mobile;// 手机
	/**
	 * 电话
	 */
	private String telephone;// 电话
	private String subTel;//短号
	/**
	 * 邮箱
	 */
	private String email;// 邮箱
	/**
	 * 住址
	 */
	private String address;// 住址
	/**
	 * 民族
	 */
	private String nation; // 民族
	/**
	 * 籍贯
	 */
	private String origin; // 籍贯
	/**
	 * 户籍地址
	 */
	private String originAddress; // 户籍地址
	/**
	 * 政治面貌
	 */
	private String political;// 政治面貌
	/**
	 * 婚姻状况
	 */
	private String marriage;//婚姻状况
	/**
	 *紧急联系人
	 */
	private String urgentUser;//紧急联系人
	/**
	 *紧急联系人 手机
	 */
	private String urgentPhone;//紧急联系人 手机
	/**
	 *用工性质
	 */
	private String workType;//用工性质
	/**
	 *银行账号
	 */
	private String blankAccount;//银行账号
	/**
	 * 到岗日期
	 */
	private String workDate;// 到岗日期
	/**
	 * 学历
	 */
	private String education;// 学历
	/**
	 * 专业
	 */
	private String profession;// 专业
	/**
	 * 岗位
	 */
	private String duty;//岗位
	/**
	 * 职务
	 */
	private String job;//职务
	/**
	 * 职称
	 */
	private String techTitle;// 职称
	/**
	 * 证件类型
	 */
	private String credentialsType;// 证件类型
	/**
	 * 证件编号
	 */
	private String credentialsNo;// 证件编号
	/**
	 * 备注
	 */
	private String remark;// 备注
	/**
	 * 状态
	 */
	private String status;// 状态
	/**
	 * 头像
	 */
	private String photo;// 头像
	/**
	 * 头像
	 */
	private String avatar;//头像
	/**
	 * 个性签名
	 */
	private String sign;
	/**
	 * 户口性质
	 */
	private String residenceType;
	/**
	 * 毕业时间
	 */
	private String graduationDate;
	/**
	 * 毕业学校
	 */
	private String graduationSchool;
	/**
	 * 转正时间
	 */
	private String regularDate;
	/**
	 * 工作年限
	 * 从事技术年限
	 */
	private String workYear;
	/**
	 * 岗位津贴
	 */
	private String postSubsidy;
	/**
	 *专业技术 
	 */
	private String majorSkill;
	/**
	 * 合同期限contractDeadline
	 */
	private String conDeadline;
	//上岗证有效期
	private String cardStartDate;
	private String cardEndDate;
	//所在部门
	private String orgId;
	private String orgName;
	private String theme;
	@Column(length=64)
	public String getCredentialsType() {
		return credentialsType;
	}

	public void setCredentialsType(String credentialsType) {
		this.credentialsType = credentialsType;
	}
	@Column(length=64)
	public String getCredentialsNo() {
		return credentialsNo;
	}

	public void setCredentialsNo(String credentialsNo) {
		this.credentialsNo = credentialsNo;
	}
	@Column(length=64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length=64)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(length=64)
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	@Column(length=64)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(length=64)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(length=64)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(length=64)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length=64)
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	@Column(length=64)
	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	@Column(length=64)
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	@Column(length=64)
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	@Column(length=64)
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}
	@Column(length=64)
	public String getTechTitle() {
		return techTitle;
	}

	public void setTechTitle(String techTitle) {
		this.techTitle = techTitle;
	}
	@Column(length=64)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	@Lob
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=64)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length=64)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Column(length=64)
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	@Column(length=64)
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	@Column(length=64)
	public String getResidenceType() {
		return residenceType;
	}
	
	public void setResidenceType(String residenceType) {
		this.residenceType = residenceType;
	}
	@Column(length=64)
	public String getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(String graduationDate) {
		this.graduationDate = graduationDate;
	}
	@Column(length=64)
	public String getGraduationSchool() {
		return graduationSchool;
	}

	public void setGraduationSchool(String graduationSchool) {
		this.graduationSchool = graduationSchool;
	}
	@Column(length=64)
	public String getRegularDate() {
		return regularDate;
	}

	public void setRegularDate(String regularDate) {
		this.regularDate = regularDate;
	}
	@Column(length=64)
	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}
	@Column(length=64) 
	public String getPostSubsidy() {
		return postSubsidy;
	}

	public void setPostSubsidy(String postSubsidy) {
		this.postSubsidy = postSubsidy;
	}
	@Column(length=64)
	public String getMajorSkill() {
		return majorSkill;
	}

	public void setMajorSkill(String majorSkill) {
		this.majorSkill = majorSkill;
	}
	@Column(length=64)
	public String getConDeadline() {
		return conDeadline;
	}

	public void setConDeadline(String conDeadline) {
		this.conDeadline = conDeadline;
	}
	@Column(length=320)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length=640)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length=32)
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	@Column(length=64)	
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	@Column(length=128)
	public String getOriginAddress() {
		return originAddress;
	}
	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}
	@Column(length=32)
	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}
	@Column(length=16)
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	@Column(length=32)
	public String getUrgentUser() {
		return urgentUser;
	}

	public void setUrgentUser(String urgentUser) {
		this.urgentUser = urgentUser;
	}
	@Column(length=16)
	public String getUrgentPhone() {
		return urgentPhone;
	}
	public void setUrgentPhone(String urgentPhone) {
		this.urgentPhone = urgentPhone;
	}
	@Column(length=16)
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	@Column(length=32)
	public String getBlankAccount() {
		return blankAccount;
	}
	public void setBlankAccount(String blankAccount) {
		this.blankAccount = blankAccount;
	}
	
	public String getCardStartDate() {
		return cardStartDate;
	}
	@Column(length=20)
	public void setCardStartDate(String cardStartDate) {
		this.cardStartDate = cardStartDate;
	}
	public String getCardEndDate() {
		return cardEndDate;
	}
	@Column(length=20)
	public void setCardEndDate(String cardEndDate) {
		this.cardEndDate = cardEndDate;
	}

	@Override
	@Transient
	public void onAdd(){
		super.onAdd();
		if(null!=name) this.setCname(ChineseFCUtil.cn2py(name));
	}
	
	@Override
	@Transient
	public void onUpdate(){
		super.onUpdate();
		if(null!=name) this.setCname(ChineseFCUtil.cn2py(name));
	}
	@Column(length=32)
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	@Column(length=32)
	public String getSubTel() {
		return subTel;
	}

	public void setSubTel(String subTel) {
		this.subTel = subTel;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
}