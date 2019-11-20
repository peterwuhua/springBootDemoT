package cn.demi.bus.report.po;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
/**
 * 报告 项目数据 结果表
 * @author QuJunLong
 */
@Entity(name = "bus_report_detail_result")
@Table(name = "bus_report_detail_result")
@Module(value = "bus.reportDetailResult")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReportDetailResult extends Po<ReportDetailResult>{
 
	private static final long serialVersionUID = -3829821839338251798L;
	public String[] PROPERTY_TO_MAP = {"id","sort","reportId","detailId","itemId","itemName"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
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
	private String sampName;
	private String sampCode;
	private String p;//批次
	/**
	 * 检测结果
	 */
	private String value;//气   实测结果
	private String value2;//气  计算/仪器
	private String sl;//速率
 
	@Column(length = 128)
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Column(length = 1000)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Column(length = 32)
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	@Column(length = 32)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(length = 32)
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	@Column(length = 32)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length = 32)
	public String getSampCode() {
		return sampCode;
	}
	public void setSampCode(String sampCode) {
		this.sampCode = sampCode;
	}
	@Column(length = 20)
	public String getCyTime() {
		return cyTime;
	}
	public void setCyTime(String cyTime) {
		this.cyTime = cyTime;
	}
	@Column(length = 32)
	public String getSamplingId() {
		return samplingId;
	}
	public void setSamplingId(String samplingId) {
		this.samplingId = samplingId;
	}
	@Column(length = 32)
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	@Column(length = 16)
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	@Column(length = 32)
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
}
