package cn.demi.app.sys.vo;

import java.util.Map;

public class UserInfoVo {

	private String id;
    private String no;// 编号
    private String name;// 用户名称
    private String sex;// 用户性别
    private String orgName;// 部门
    private String duty;// 职务
    private String techTitle;// 职称
    private String mobile;// 手机
    private String email;// 邮箱
    private String political;// 政治面貌
    private String education;// 文化程度
    private String graduationSchool;//毕业学校
    private String profession;// 专业
    private String address;// 现住址
    private String urgentUser;//紧急联系人
    private String urgentPhone;//联系人 手机
    private String credentialsNo;// 证件编号
    private String job;//职务
    private String avatar;

    //构造函数
    public UserInfoVo() {
        super();
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserInfoVo(Map<String, String[]> map) {
		this.id = map.get("id") != null ? map.get("id")[0] : null;
        this.no = map.get("no") != null ? map.get("no")[0] : null;
        this.name = map.get("name") != null ? map.get("name")[0] : null;
        this.sex = map.get("sex") != null ? map.get("sex")[0] : null;
        this.orgName = map.get("orgName") != null ? map.get("orgName")[0] : null;
        this.duty = map.get("duty") != null ? map.get("duty")[0] : null;
        this.techTitle = map.get("techTitle") != null ? map.get("techTitle")[0] : null;
        this.mobile = map.get("mobile") != null ? map.get("mobile")[0] : null;
        this.email = map.get("email") != null ? map.get("email")[0] : null;
        this.political = map.get("political") != null ? map.get("political")[0] : null;
        this.education = map.get("education") != null ? map.get("education")[0] : null;
        this.graduationSchool = map.get("graduationSchool") != null ? map.get("graduationSchool")[0] : null;
        this.profession = map.get("profession") != null ? map.get("profession")[0] : null;
        this.address = map.get("address") != null ? map.get("address")[0] : null;
        this.urgentUser = map.get("urgentUser") != null ? map.get("urgentUser")[0] : null;
        this.urgentPhone = map.get("urgentPhone") != null ? map.get("urgentPhone")[0] : null;
        this.credentialsNo = map.get("credentialsNo") != null ? map.get("credentialsNo")[0] : null;
        this.job = map.get("job") != null ? map.get("job")[0] : null;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGraduationSchool() {
        return graduationSchool;
    }

    public void setGraduationSchool(String graduationSchool) {
        this.graduationSchool = graduationSchool;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCredentialsNo() {
        return credentialsNo;
    }

    public void setCredentialsNo(String credentialsNo) {
        this.credentialsNo = credentialsNo;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
