package cn.demi.bus.file.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class ArchiveVo extends Vo<ArchiveVo> {
	/**
	 * 任务编号
	 * 档案编号
	 */
	private String code;
	/**
	 * 档案标题
	 */
	private String title;
	/**
	 * 归档时间
	 */
	private String time;
	private String userId;//归档人
	private String userName;
	/**
	 * 档案数量
	 */
	private int num;
	/**
	 * 文档说明
	 */
	private String describtion;
	/**
	 * 文档所属类型
	 */
	private ArchiveTypeVo archiveTypeVo;
	
	private String dirIds;
	/**
	 * 节点路径
	 */
	private String path;
	private List<ArchiveFileVo> fileList;
	

	private String auditId;//审批人id
	private String auditName;//审批人
	private String auditTime;//审批时间
	private String auditCt;//审批意见
    private String confirmId;//确认人id	
    private String confirm;//确认人	
    private String confirmCt;//确认意见
    private String confirmTime;//确认时间
	private String viewer;//查阅人
	private String viewerId;//查阅人id
	private String viewCt;//查阅情况
	private String viewTime;//查阅时间
	private String status;//状态
	
	private String orgId;
	private String orgName;//子公司
	private String deptId;
	private String deptName;//部门
	
	
	
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
	public String getViewTime() {
		return viewTime;
	}
	public void setViewTime(String viewTime) {
		this.viewTime = viewTime;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConfirmId() {
		return confirmId;
	}
	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public String getConfirmCt() {
		return confirmCt;
	}
	public void setConfirmCt(String confirmCt) {
		this.confirmCt = confirmCt;
	}
	public String getViewCt() {
		return viewCt;
	}
	public void setViewCt(String viewCt) {
		this.viewCt = viewCt;
	}
	public String getViewer() {
		return viewer;
	}
	public void setViewer(String viewer) {
		this.viewer = viewer;
	}
	public String getViewerId() {
		return viewerId;
	}
	public void setViewerId(String viewerId) {
		this.viewerId = viewerId;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	public ArchiveTypeVo getArchiveTypeVo() {
		return archiveTypeVo;
	}
	public void setArchiveTypeVo(ArchiveTypeVo archiveTypeVo) {
		this.archiveTypeVo = archiveTypeVo;
	}
	public List<ArchiveFileVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<ArchiveFileVo> fileList) {
		this.fileList = fileList;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDirIds() {
		return dirIds;
	}
	public void setDirIds(String dirIds) {
		this.dirIds = dirIds;
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
	
}

