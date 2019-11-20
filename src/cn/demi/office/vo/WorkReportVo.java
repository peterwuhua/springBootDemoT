package cn.demi.office.vo;

import cn.core.framework.common.vo.Vo;

/**
 * 
 * @ClassName:  WorkReportVo   
 * @Description:工作简报   
 * @author: 吴华 
 * @date:   2019年3月2日 下午12:00:45       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
public class WorkReportVo extends Vo<WorkReportVo> {
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
    
    
	public String getViewDate() {
		return viewDate;
	}
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}
	public String getFkContent() {
		return fkContent;
	}
	public void setFkContent(String fkContent) {
		this.fkContent = fkContent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
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
	public String getWpStatus() {
		return wpStatus;
	}
	public void setWpStatus(String wpStatus) {
		this.wpStatus = wpStatus;
	}
    
    
    
    
}

