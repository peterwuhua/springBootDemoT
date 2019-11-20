package cn.demi.qlt.po;

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
 * 监督抽查
 * @author QuJunLong
 *
 */
@Entity(name = "qlt_check")
@Table(name = "qlt_check")
@Module(value = "qlt.check")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Check  extends Po<Check>{

 
	private static final long serialVersionUID = -4661292479458437089L;
	public String[] PROPERTY_TO_MAP= {"id","sort","orgName","user","date","userName","result","no","phone"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version","no"};
	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}
	private String no;
	private String orgId;//单位
	private String orgName;
	
	private String stand;//标准
	private String appId;//仪器
	private String appName;
	private String appCode;//编号
	
	private String deptId;//科室
	private String deptName;
	
	private String user;//联系人
	private String phone;//电话
	private String content;//抽查内容
	private String result;//结果
	private String pj;//评价
	
	private String userId;//抽查人
	private String userName;
	private String date;//抽查日期
	
	private String jdId;//监督人
	private String jdName;
	private String remark;//备注

	@Column(length=320)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length=320)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=256)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=32)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Column(length=32)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length=32)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(length=32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length=64)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length=20)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(length=128)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Column(length=128)
	public String getStand() {
		return stand;
	}
	public void setStand(String stand) {
		this.stand = stand;
	}
	@Column(length=32)
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	@Column(length=128)
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@Column(length=32)
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	@Column(length=32)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(length=32)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(length=128)
	public String getPj() {
		return pj;
	}
	public void setPj(String pj) {
		this.pj = pj;
	}
	@Column(length=320)
	public String getJdId() {
		return jdId;
	}
	public void setJdId(String jdId) {
		this.jdId = jdId;
	}
	@Column(length=320)
	public String getJdName() {
		return jdName;
	}
	public void setJdName(String jdName) {
		this.jdName = jdName;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Check.class, true, ActionType.JSP);
	}
}
