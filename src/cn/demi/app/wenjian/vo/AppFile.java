package cn.demi.app.wenjian.vo;

public class AppFile {
	private String id;
	private String code;// 文件编号
	private String title;// 文件名称
	private String source;// 文件来源
	private String dj;// 保密等级
	private String path;// 文件目录
	private String fileName;// 文件名称
	private String filePath;// 文件路径
	private String auditId;// 签发人
	private String auditName;// 签发人
	private String auditCt;// 审批意见
	private String date;// 登记日期
	private String result;// 落实情况
	private String content;// 描述
	private String remark;// 备注
	private String auditTime;// 签发时间
	private String sendTime;// 下发时间
	private String userId;// 申请人
	private String userName;// 申请人
	private String sendId;// 下发人
	private String sendName;// 下发人
	private String sendCt;

	private String userIds;// 指定人员
	private String userNames;// 指定人员
	private String status;// 状态
	private String statusChina;// 状态中文
	private String viewEdIds;// 抄送对象 已查看
	private String viewEdNames;// 抄送对象 已查看

	private String categoryId;
	private String categoryName;
private String categoryPath;

	public String getCategoryPath() {
	return categoryPath;
}

public void setCategoryPath(String categoryPath) {
	this.categoryPath = categoryPath;
}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	private String type;

	public String getSendCt() {
		return sendCt;
	}

	public void setSendCt(String sendCt) {
		this.sendCt = sendCt;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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

	public String getAuditCt() {
		return auditCt;
	}

	public void setAuditCt(String auditCt) {
		this.auditCt = auditCt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusChina() {
		return statusChina;
	}

	public void setStatusChina(String statusChina) {
		this.statusChina = statusChina;
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

	public String getAuditTime() {
		return auditTime;
	}

	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
