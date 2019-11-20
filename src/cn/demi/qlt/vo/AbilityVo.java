package cn.demi.qlt.vo;

import cn.core.framework.common.vo.Vo;

public class AbilityVo extends Vo<AbilityVo> {
	private String no;
	private String title;//计划名称
	private String orgId;//部门
	private String orgName;
	private String user;//验证联系人
	private String phone;//电话
	private String udate;//实施日期
	private String type;//类型
	private float price;//预算经费
	private String content;//比对/验证内容及要求
	private String status;//状态
	private String remark;//备注
	
	private String userId;//计划人
	private String userName;
	private String date;//计划日期
	
	private String auditId;//批准人
	private String auditName;
	private String auditDate;//处理日期
	
	private String result;//验证比对结果
	
	private String jlId;//记录人
	private String jlName;
	private String jlDate;//记录日期
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUdate() {
		return udate;
	}
	public void setUdate(String udate) {
		this.udate = udate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getJlId() {
		return jlId;
	}
	public void setJlId(String jlId) {
		this.jlId = jlId;
	}
	public String getJlName() {
		return jlName;
	}
	public void setJlName(String jlName) {
		this.jlName = jlName;
	}
	public String getJlDate() {
		return jlDate;
	}
	public void setJlDate(String jlDate) {
		this.jlDate = jlDate;
	}
	
}

