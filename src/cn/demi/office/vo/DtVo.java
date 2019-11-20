package cn.demi.office.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.doc.vo.CategoryVo;

public class DtVo extends Vo<DtVo> {
	
	private String code;//文件编号
	private String title;//文件名称
	private String source;//文件来源
	private String type;
	private CategoryVo categoryVo;//文档所属类型
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public CategoryVo getCategoryVo() {
		return categoryVo;
	}

	public void setCategoryVo(CategoryVo categoryVo) {
		this.categoryVo = categoryVo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditCt() {
		return auditCt;
	}

	public void setAuditCt(String auditCt) {
		this.auditCt = auditCt;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendCt() {
		return sendCt;
	}
	public void setSendCt(String sendCt) {
		this.sendCt = sendCt;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getViewEdIds() {
		return viewEdIds;
	}

	public void setViewEdIds(String viewEdIds) {
		this.viewEdIds = viewEdIds;
	}

	public String getViewEdNames() {
		return viewEdNames;
	}

	public void setViewEdNames(String viewEdNames) {
		this.viewEdNames = viewEdNames;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}

