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
 * 能力验证
 * @author QuJunLong
 *
 */
@Entity(name = "qlt_ability")
@Table(name = "qlt_ability")
@Module(value = "qlt.ability")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ability extends Po<Ability>{

	public static final String ST_0 = "0";//已保存
	public static final String ST_1 = "1";//已提交
	public static final String ST_2 = "2";//已完成
	private static final long serialVersionUID = -4661292479458437089L;
	public String[] PROPERTY_TO_MAP= {"id","sort","title","orgName","user","phone","date","udate","userName","result","no","status","type"};
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
	@Column(length=32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length=32)
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
	@Column(length=32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length=32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length=32)
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	@Column(length=32)
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	@Column(length=20)
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=64)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getUdate() {
		return udate;
	}
	public void setUdate(String udate) {
		this.udate = udate;
	}
	@Column(length=11)
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Column(length=320)
	public String getJlId() {
		return jlId;
	}
	public void setJlId(String jlId) {
		this.jlId = jlId;
	}
	@Column(length=320)
	public String getJlName() {
		return jlName;
	}
	public void setJlName(String jlName) {
		this.jlName = jlName;
	}
	@Column(length=20)
	public String getJlDate() {
		return jlDate;
	}
	public void setJlDate(String jlDate) {
		this.jlDate = jlDate;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Ability.class, true, ActionType.JSP);
	}
}
