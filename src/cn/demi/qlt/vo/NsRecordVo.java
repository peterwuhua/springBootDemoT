package cn.demi.qlt.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.pg.vo.ProgressVo;

public class NsRecordVo extends Vo<NsRecordVo> {
	
	private NsVo nsVo;//计划
	private String year; //年份
	private int month; //月份
	private String item;//整个要素
	
	private String headId; //负责人
	private String headName; 
	private String xzId; //协助人
	private String xzName; 
	
	private String userId;//记录人
	private String userName;//记录人
	private String date;//记录日期
	
	private String content;//审核情况概述
	private String result;//内审结论
	private String remark;
	
	private String gzId; //跟踪人
	private String gzName; 
	private String gzDate;//跟踪日期
	
	private String yzId; //验证人
	private String yzName; 
	private String yzDate;//验证日期
	
	private String reportId;//报告人
	private String reportName;
	private String reportDate;
	private String fileName;//报告名称及路径
	private String filePath;
	
	private String isBack;
	private String status;//当前状态
	private ProgressVo pgVo;//流程
	private List<NsRecordDetailVo> detailList;
	 
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<NsRecordDetailVo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<NsRecordDetailVo> detailList) {
		this.detailList = detailList;
	}
	public NsVo getNsVo() {
		return nsVo;
	}
	public void setNsVo(NsVo nsVo) {
		this.nsVo = nsVo;
	}
	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	public String getXzId() {
		return xzId;
	}
	public void setXzId(String xzId) {
		this.xzId = xzId;
	}
	public String getXzName() {
		return xzName;
	}
	public void setXzName(String xzName) {
		this.xzName = xzName;
	}
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ProgressVo getPgVo() {
		return pgVo;
	}
	public void setPgVo(ProgressVo pgVo) {
		this.pgVo = pgVo;
	}
	public String getGzId() {
		return gzId;
	}
	public void setGzId(String gzId) {
		this.gzId = gzId;
	}
	public String getGzName() {
		return gzName;
	}
	public void setGzName(String gzName) {
		this.gzName = gzName;
	}
	public String getGzDate() {
		return gzDate;
	}
	public void setGzDate(String gzDate) {
		this.gzDate = gzDate;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
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
	public String getYzId() {
		return yzId;
	}
	public void setYzId(String yzId) {
		this.yzId = yzId;
	}
	public String getYzName() {
		return yzName;
	}
	public void setYzName(String yzName) {
		this.yzName = yzName;
	}
	public String getYzDate() {
		return yzDate;
	}
	public void setYzDate(String yzDate) {
		this.yzDate = yzDate;
	}
	
}

