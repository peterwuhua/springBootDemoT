package cn.demi.bus.report.po;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.task.po.Task;
/**
 * 报告更新
 * @author QuJunLong
 */
@Entity(name = "bus_report_upd")
@Table(name = "bus_report_upd")
@Module(value = "bus.reportUpd")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReportUpd extends Po<ReportUpd>{
	private static final long serialVersionUID = 1L;
 
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","report","task","cust","reportNo","content","orgName","userName","date","remark","status"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 报告
	 */
	private Report report;
	private Task task;
	private Cust cust;//客户信息
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
	@ManyToOne
	@JoinColumn(name = "report_id")
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	@ManyToOne
	@JoinColumn(name = "task_id")	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}
	public void setCust(Cust cust) {
		this.cust = cust;
	}
	@Column(length = 32)
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
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
	@Column(length = 1000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 256)
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Column(length = 256)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length = 128)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length = 256)
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	@Column(length = 128)
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	@Column(length = 4)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 32)
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	@Column(length = 256)
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
