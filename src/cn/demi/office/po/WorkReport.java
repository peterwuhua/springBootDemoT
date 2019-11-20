package cn.demi.office.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;


/**
 * 
 * @ClassName:  WorkReport   
 * @Description:工作简报   
 * @author: 吴华 
 * @date:   2019年3月2日 上午11:39:45       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Entity(name = "office_work_report")
@Table(name = "office_work_report")
@Module(value = "office.workReport")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkReport extends Po<WorkReport>{

	private static final long serialVersionUID = 1L;
    public static final String WORK_REPORT_STATUS_WTJ = "未提交";
    public static final String WORK_REPORT_STATUS_WFK = "未反馈";
    public static final String WORK_REPORT_STATUS_YFK = "已反馈";
	
	public String[] PROPERTY_TO_MAP= {"id","sort","name","depart","reportDate","person","personId","viewer","viewerId","filename","filepath","content","remark","wpStatus","fkContent","viewDate"};
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}
	
	private String name; //工作名称
	private String depart; //汇报部门
	private String reportDate; //汇报日期
	private String person; //汇报人
	private String personId; //汇报人id
	private String viewer; //查阅人
	private String viewerId; //查阅人id
	private String viewDate; //查阅日期
	private String filename; //上传附件名称
    private String filepath; //上传附件路径
    private String content; //工作内容
    private String remark; //备注
    private String wpStatus; //工作汇报状态
    private String fkContent; //反馈内容
    
    @Column(length=32)
    public String getViewDate() {
		return viewDate;
	}
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}
	@Column(length=1000)
    public String getFkContent() {
		return fkContent;
	}
	public void setFkContent(String fkContent) {
		this.fkContent = fkContent;
	}
	@Column(length=128)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=32)
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	@Column(length=32)
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	@Column(length=32)
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	@Column(length=1000)
	public String getViewer() {
		return viewer;
	}
	public void setViewer(String viewer) {
		this.viewer = viewer;
	}
	@Column(length=64)
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Column(length=128)
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	@Column(length=1000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=1000)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=32)
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	@Column(length=1000)
	public String getViewerId() {
		return viewerId;
	}
	public void setViewerId(String viewerId) {
		this.viewerId = viewerId;
	}
	@Column(length=32)
	public String getWpStatus() {
		return wpStatus;
	}
	public void setWpStatus(String wpStatus) {
		this.wpStatus = wpStatus;
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(WorkReport.class, true, ActionType.JSP);
	}
    
    
	
}
