package cn.demi.base.system.vo;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.core.framework.common.vo.Export;
import cn.core.framework.common.vo.Vo;
import cn.core.framework.utils.ChineseFCUtil;

public class UserVo extends Vo<UserVo> {
	public static final String SYS_LOGO = "sys-user-logo";
	public static final String USER_AUTH_XSJL = "XSJL";//销售经理
	public static final String USER_AUTH_XSRY = "XSRY";//销售人员
	/**
	 * 用户名称
	 */
	@Export(label="用户名称")
	private String name;// 用户名称
	/**
	 * 拼音首字母
	 */
	private String cname;// 拼音首字母
	/**
	 * 头像
	 */
	private String avatar;// 头像
	/**
	 * 用户性别
	 */
	@Export(label="用户性别")
	private String sex;// 用户性别
	/**
	 * 用户生日
	 */
	private String birthday;// 用户生日
	/**
	 * 手机
	 */
	@Export(label="手机")
	private String mobile;// 手机
	/**
	 * 电话
	 */
	private String telephone;// 电话
	private String subTel;//短号
	/**
	 * 邮箱
	 */
	@Export(label="邮箱")
	private String email;// 邮箱
	/**
	 * 住址
	 */
	@Export(label="住址")
	private String address;// 住址
	/**
	 * 民族
	 */
	@Export(label="民族")
	private String nation; // 民族
	/**
	 * 到岗日期
	 */
	@Export(label="到岗日期")
	private String workDate;// 到岗日期
	/**
	 * 学历
	 */
	@Export(label="学历")
	private String education;// 学历
	/**
	 * 专业
	 */
	@Export(label="专业")
	private String profession;// 专业
	/**
	 * 岗位
	 */
	@Export(label="岗位")
	private String duty;// 岗位
	/**
	 * 职务
	 */
	private String job;//职务
	/**
	 * 职称
	 */
	@Export(label="职称")
	private String techTitle;// 职称
	/**
	 * 编号
	 */
	@Export(label="编号")
	private String no;// 编号
	/**
	 * 备注
	 */
	private String remark;// 备注
	/**
	 * 状态
	 */
	private String status;// 状态
	/**
	 * 证件类型
	 */
	private String credentialsType;// 证件类型
	/**
	 * 证件编号
	 */
	private String credentialsNo;// 证件编号
	/**
	 * 头像
	 */
	private String photo;// 头像
	/**
	 * 附件 
	 */
	private MultipartFile file;// 附件
	//附件
	private List<FilesVo> fileList;
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
	 * 工作满一年时间
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
	//上岗证有效期
	private String cardStartDate;
	private String cardEndDate;
	//所在公司
	private String orgId;
	private String orgName;
	
	private String theme;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.cname = ChineseFCUtil.cn2py(name);
		this.name = name;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getTechTitle() {
		return techTitle;
	}

	public void setTechTitle(String techTitle) {
		this.techTitle = techTitle;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCredentialsType() {
		return credentialsType;
	}

	public void setCredentialsType(String credentialsType) {
		this.credentialsType = credentialsType;
	}

	public String getCredentialsNo() {
		return credentialsNo;
	}

	public void setCredentialsNo(String credentialsNo) {
		this.credentialsNo = credentialsNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

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

	public String getResidenceType() {
		return residenceType;
	}

	public void setResidenceType(String residenceType) {
		this.residenceType = residenceType;
	}

	public String getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(String graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getGraduationSchool() {
		return graduationSchool;
	}

	public void setGraduationSchool(String graduationSchool) {
		this.graduationSchool = graduationSchool;
	}

	public String getRegularDate() {
		return regularDate;
	}

	public void setRegularDate(String regularDate) {
		this.regularDate = regularDate;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public String getPostSubsidy() {
		return postSubsidy;
	}

	public void setPostSubsidy(String postSubsidy) {
		this.postSubsidy = postSubsidy;
	}

	public String getMajorSkill() {
		return majorSkill;
	}

	public void setMajorSkill(String majorSkill) {
		this.majorSkill = majorSkill;
	}

	public String getConDeadline() {
		return conDeadline;
	}

	public void setConDeadline(String conDeadline) {
		this.conDeadline = conDeadline;
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
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getOriginAddress() {
		return originAddress;
	}

	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getUrgentUser() {
		return urgentUser;
	}

	public void setUrgentUser(String urgentUser) {
		this.urgentUser = urgentUser;
	}

	public String getUrgentPhone() {
		return urgentPhone;
	}

	public void setUrgentPhone(String urgentPhone) {
		this.urgentPhone = urgentPhone;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getBlankAccount() {
		return blankAccount;
	}

	public void setBlankAccount(String blankAccount) {
		this.blankAccount = blankAccount;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	public List<FilesVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	public String getCardStartDate() {
		return cardStartDate;
	}

	public void setCardStartDate(String cardStartDate) {
		this.cardStartDate = cardStartDate;
	}

	public String getCardEndDate() {
		return cardEndDate;
	}

	public void setCardEndDate(String cardEndDate) {
		this.cardEndDate = cardEndDate;
	}
	
	public String getSubTel() {
		return subTel;
	}

	public void setSubTel(String subTel) {
		this.subTel = subTel;
	}

	public static void main(String[] args) {
		Map<String, String> map = new UserVo().getExportMap(UserVo.class,"");
		for (String key : map.keySet()) {
			System.out.println(key+" = "+map.get(key));
		}
	}
}