package cn.demi.qlt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.pg.po.Progress;

/**
 * 内审记录
 * @author QuJunLong
 *
 */
@Entity(name = "qlt_ns_record")
@Table(name = "qlt_ns_record")
@Module(value = "qlt.nsRecord")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NsRecord extends Po<NsRecord>{

	private static final long serialVersionUID = -7844988463000815126L;
	public String[] PROPERTY_TO_MAP= {"id","sort","ns","year","month","item","headName","userName","date","isBack","status","pg"};
	private Ns ns;//计划
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
	private String gzDate;  //跟踪日期
	
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
	private Progress pg;//流程
	
	@ManyToOne
	@JoinColumn(name = "ns_id")
	public Ns getNs() {
		return ns;
	}
	public void setNs(Ns ns) {
		this.ns = ns;
	}
	@Column(length=2)
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	@Column(length=256)
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	@Column(length=320)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length=320)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=20)
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(length=256)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=256)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=4)
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	@Column(length=320)
	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}
	@Column(length=160)
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	@Column(length=320)
	public String getXzId() {
		return xzId;
	}
	public void setXzId(String xzId) {
		this.xzId = xzId;
	}
	@Column(length=320)
	public String getXzName() {
		return xzName;
	}
	public void setXzName(String xzName) {
		this.xzName = xzName;
	}
	@Column(length=64)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@ManyToOne
	@JoinColumn(name = "pg_id")
	public Progress getPg() {
		return pg;
	}
	public void setPg(Progress pg) {
		this.pg = pg;
	}
	@Column(length=4)
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	@Column(length=320)
	public String getGzId() {
		return gzId;
	}
	public void setGzId(String gzId) {
		this.gzId = gzId;
	}
	@Column(length=320)
	public String getGzName() {
		return gzName;
	}
	public void setGzName(String gzName) {
		this.gzName = gzName;
	}
	@Column(length=20)
	public String getGzDate() {
		return gzDate;
	}
	public void setGzDate(String gzDate) {
		this.gzDate = gzDate;
	}
	@Column(length=32)
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	@Column(length=16)
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	@Column(length=20)
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	@Column(length=128)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length=64)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length=320)
	public String getYzId() {
		return yzId;
	}
	public void setYzId(String yzId) {
		this.yzId = yzId;
	}
	@Column(length=320)
	public String getYzName() {
		return yzName;
	}
	public void setYzName(String yzName) {
		this.yzName = yzName;
	}
	@Column(length=20)
	public String getYzDate() {
		return yzDate;
	}
	public void setYzDate(String yzDate) {
		this.yzDate = yzDate;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(NsRecord.class, true, ActionType.JSP);
	}
}
