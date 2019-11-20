package cn.demi.bus.report.vo;

import cn.core.framework.common.vo.Vo;

public class ReportDetailResultVo extends Vo<ReportDetailResultVo> {
	/**
	 * 报告Id
	 */
	private String reportId;
	/**
	 * 项目id
	 */
	private String detailId; 
	private String itemName;
	private String itemId;
	/**
	 *样品Id
	 */
	private String samplingId;
	private String cyTime;
	private String sampTypeId;
	private String sampTypeName;
	private String sampName;
	private String sampCode;
	private String p;//批次
	/**
	 * 检测结果
	 */
	private String value;//气   实测结果
	private String value2;//气  计算/仪器
	private String sl;//速率
	
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getSamplingId() {
		return samplingId;
	}
	public void setSamplingId(String samplingId) {
		this.samplingId = samplingId;
	}
	public String getCyTime() {
		return cyTime;
	}
	public void setCyTime(String cyTime) {
		this.cyTime = cyTime;
	}
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public String getSampCode() {
		return sampCode;
	}
	public void setSampCode(String sampCode) {
		this.sampCode = sampCode;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
}

