package cn.demi.office.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.doc.po.Category;
/**
 * 文件流转流程
 * 文件登记（综合室）->文件签发（站长室）->文件下发（由上级指定、下发当前人指定）
 * @author QuJunLong
 *
 */
@Entity(name = "office_dt")
@Table(name = "office_dt")
@Module(value = "office.dt")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dt extends Po<Dt>{

	private static final long serialVersionUID = 4192771014738106409L;
	public String[] PROPERTY_TO_MAP= {"id","sort","code","orgName","deptName","title","dj","userName","date","content","auditName","status","auditTime","auditResult","fileName","source","sendName","sendTime"};
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","orgId","orgName","deptId","deptName","userId","userName","fileName","filePath"};
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
	private String code;//文件编号
	private String title;//文件名称
	private String source;//文件来源
	private String type;
	private Category category;//文档所属类型
	private String path;//文件目录
	private String content;//描述
	private String dj;//保密等级
	private String remark;//备注
	private String fileName;//文件名称
	private String filePath;//文件路径
	private String size;
	private long originalSize;
	private String result;//落实情况
	private String orgId;
	private String orgName;//子公司
	private String deptId;
	private String deptName;//部门
	
	private String date;//登记日期
	private String userId;//申请人
	private String userName;//申请人
	
	private String auditId;//签发人
	private String auditName;//签发人
	private String auditTime;//签发时间
	private String auditCt;//审批意见
	private String auditResult;//审批结果
	
	private String sendId;//下发人
	private String sendName;//下发人
	private String sendTime;//下发时间
	private String sendCt;//下发意见
	
	private String userIds;//指定人员
	private String userNames;//指定人员
	
	private String viewEdIds;//抄送对象 已查看
	private String viewEdNames;//抄送对象 已查看
	
	private String status;//状态
	
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length = 32)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length = 32)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(length = 32)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(length = 128)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	@Column(length = 20)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(length = 1000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length = 128)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	@Column(length = 32)
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	@Column(length = 16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 20)
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	@Column(length = 128)
	public String getAuditCt() {
		return auditCt;
	}
	public void setAuditCt(String auditCt) {
		this.auditCt = auditCt;
	}
	@Column(length = 32)
	public String getAuditResult() {
		return auditResult;
	}
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}
	@Column(length = 32)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length = 1000)
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	@Column(length = 1000)
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	@Column(length = 64)
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@ManyToOne
	@JoinColumn(name = "category_id")
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Column(length = 128)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Column(length = 32)
	public String getDj() {
		return dj;
	}
	public void setDj(String dj) {
		this.dj = dj;
	}
	@Column(length = 64)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length = 128)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length = 32)
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	@Column(length = 32)
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	@Column(length = 20)
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	@Column(length = 128)
	public String getSendCt() {
		return sendCt;
	}
	public void setSendCt(String sendCt) {
		this.sendCt = sendCt;
	}
	@Column(length = 16)
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public long getOriginalSize() {
		return originalSize;
	}
	public void setOriginalSize(long originalSize) {
		this.originalSize = originalSize;
	}
	@Column(length = 16)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 1000)
	public String getViewEdIds() {
		return viewEdIds;
	}
	public void setViewEdIds(String viewEdIds) {
		this.viewEdIds = viewEdIds;
	}
	@Column(length = 1000)
	public String getViewEdNames() {
		return viewEdNames;
	}
	public void setViewEdNames(String viewEdNames) {
		this.viewEdNames = viewEdNames;
	}
	@Column(length = 1000)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Dt.class, false, ActionType.JSP);
	}
}
