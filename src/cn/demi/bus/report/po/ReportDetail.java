package cn.demi.bus.report.po;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.test.po.TaskItem;
/**
 *报告项目信息
 * @author QuJunLong
 */
@Entity(name = "bus_report_detail")
@Table(name = "bus_report_detail")
@Module(value = "bus.reportDetail")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReportDetail extends Po<ReportDetail>{
	private static final long serialVersionUID = 1L;
 
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","reportId","pointId","itemId","itemName","testTime","testMan"};
	
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
	 * 点位Id
	 */
	private String pointId;
	private String pointName;
	private String room;//车间
	private String workHours;//接触时间
	private String address;//测点位置
	private String type;//水 气 声
	/**
	 *样品Id
	 */
	private String samplingId;
	private String cyDate;
	private String cyTime;
	private int cyHours;
	private String sampTypeId;
	private String sampTypeName;
	private String sampName;
	private String sampCode;
	private String p;//批次
	private String xz;//性状
	private String stype;//0普通样品 1 现场平行样 2 室内平行样 3 全程序空白样 4 加标回收样 5密码样
	private ReportDetail parent;
 
	private TaskItem tim;//项目任务
	private String itemName;
	private String itemId;
	private int level;//级别
	/**
	 * 检出限
	 */
	private String limitLine;
	/**
	 * 限值
	 */
	private String limited;
	/**
	 * 检测结果
	 */
	private String value;//气   实测结果
	private String value2;//气  计算/仪器
	private String sl;//速率
	private String unit;
	private String unit2;
	/**
	 * 判定结果
	 * 合格 不合格
	 */
	private String result;
	/**
	 * PC-TWA
	 * 时间加权平均容器浓度
	 * 以时间加权规定的8小时，40小时平均容器浓度
	 */
	private String twa;
	/**
	 * PC-TWA
	 * 在PC-TWA前提下，容许短时间（15min）接触浓度
	 */
	private String stel;
	/**
	 * 最高容许浓度
	 */
	private String mac;
	/**
	 * 超限倍数
	 */
	private String lmt;
	/**
	 * 检测方法
	 */
	private String methodId;
	private String methodName;
	//评价标准
	private String standId;
	private String standName;
	/**
	 * 测试仪器
	 */
	private String appId;
	private String appName;
	/**
	 * 检测人
	 */
	private String testMan;
	private String testTime;
	private String orgId;
	private String orgName;

	@ManyToOne
	@JoinColumn(name = "tim_id")
	public TaskItem getTim() {
		return tim;
	}
	public void setTim(TaskItem tim) {
		this.tim = tim;
	}
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Column(length = 320)
	public String getTestMan() {
		return testMan;
	}
	public void setTestMan(String testMan) {
		this.testMan = testMan;
	}
	@Column(length = 64)
	public String getTestTime() {
		return testTime;
	}
	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}
	@Column(length = 32)
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	@ManyToOne
	@JoinColumn(name = "parent_id")
	public ReportDetail getParent() {
		return parent;
	}
	public void setParent(ReportDetail parent) {
		this.parent = parent;
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
	@Column(length = 32)
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	@Column(length = 32)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(length = 4)
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Column(length = 128)
	public String getLimitLine() {
		return limitLine;
	}
	public void setLimitLine(String limitLine) {
		this.limitLine = limitLine;
	}
	@Column(length = 64)
	public String getLimited() {
		return limited;
	}
	public void setLimited(String limited) {
		this.limited = limited;
	}
	@Column(length = 32)
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	@Column(length = 160)
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	@Column(length = 320)
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	@Column(length = 160)
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	@Column(length = 320)
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@Column(length = 64)
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
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
	@Column(length = 16)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 32)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(length = 32)
	public String getUnit2() {
		return unit2;
	}
	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}
	@Column(length = 20)
	public String getCyTime() {
		return cyTime;
	}
	public void setCyTime(String cyTime) {
		this.cyTime = cyTime;
	}
	@Column(length = 64)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length = 32)
	public String getSamplingId() {
		return samplingId;
	}
	public void setSamplingId(String samplingId) {
		this.samplingId = samplingId;
	}
	@Column(length = 32)
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length = 32)
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	@Column(length = 16)
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
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
	@Column(length = 64)
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	@Column(length = 320)
	public String getStandId() {
		return standId;
	}
	public void setStandId(String standId) {
		this.standId = standId;
	}
	@Column(length = 640)
	public String getStandName() {
		return standName;
	}
	public void setStandName(String standName) {
		this.standName = standName;
	}
	@Column(length = 20)
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}
	@Column(length = 128)
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	@Column(length = 32)
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	@Column(length = 32)
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	@Column(length = 32)
	public String getTwa() {
		return twa;
	}
	public void setTwa(String twa) {
		this.twa = twa;
	}
	@Column(length = 32)
	public String getStel() {
		return stel;
	}
	public void setStel(String stel) {
		this.stel = stel;
	}
	@Column(length = 32)
	public String getLmt() {
		return lmt;
	}
	public void setLmt(String lmt) {
		this.lmt = lmt;
	}
	public int getCyHours() {
		return cyHours;
	}
	public void setCyHours(int cyHours) {
		this.cyHours = cyHours;
	}
}
