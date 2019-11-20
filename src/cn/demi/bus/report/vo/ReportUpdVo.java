package cn.demi.bus.report.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.pjt.vo.CustVo;
import cn.demi.bus.task.vo.TaskVo;

public class ReportUpdVo extends Vo<ReportUpdVo> {
	/**
	 * 报告
	 */
	private ReportVo reportVo;
	private TaskVo taskVo;
	private CustVo custVo;//客户信息
	/**
	 * 报告编号
	 */
	private String reportNo;
	/**
	 * 更新内容
	 */
	private String content;
	/**
	 * 更新人
	 */
	private String orgId;
	private String orgName;
	private String userId;
	private String userName;
	private String date;
	private String remark;
	/**
	 * 报告归档位置
	 */
	private String position;
	private String categoryId;//电子文档
	private String categoryName;//电子文档
	 /**
     * 报告路径
     */
    private String filePath;
    /**
     * 报告名称
     */
    private String fileName;
	 /**
     * 报告路径
     */
    private String pdfPath;
	 /**
     * 报告路径
     */
    private String pdfName;
    /**
     * 状态 0可改 1只读 
     */
    private String status;
    
	public ReportVo getReportVo() {
		return reportVo;
	}
	public void setReportVo(ReportVo reportVo) {
		this.reportVo = reportVo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public TaskVo getTaskVo() {
		return taskVo;
	}
	public void setTaskVo(TaskVo taskVo) {
		this.taskVo = taskVo;
	}
	public CustVo getCustVo() {
		return custVo;
	}
	public void setCustVo(CustVo custVo) {
		this.custVo = custVo;
	}
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}

