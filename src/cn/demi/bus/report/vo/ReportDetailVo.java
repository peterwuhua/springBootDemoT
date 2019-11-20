package cn.demi.bus.report.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.test.vo.TaskItemVo;

public class ReportDetailVo extends Vo<ReportDetailVo> {
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
	private String cyTime;
	private int cyHours;
	private String sampTypeId;
	private String sampTypeName;
	private String sampName;
	private String cyDate;
	private String sampCode;
	private String p;//批次
	private String xz;//性状
	private String stype;//0普通样品 1 现场平行样 2 室内平行样 3 全程序空白样 4 加标回收样 5密码样
	
	private ReportDetailVo parentVo;
	private TaskItemVo timVo;//项目任务
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
	private String value;
	private String value2;
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
	private String methodCode;
	//评价标准
	private String standId;
	private String standName;
	private String standCode;
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
	
	private List<ReportDetailVo> subList;
	private String[] resultArray;
	private List<ReportDetailResultVo> resultList;
	
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
 
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	 
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getTestMan() {
		return testMan;
	}
	public void setTestMan(String testMan) {
		this.testMan = testMan;
	}
	public String getTestTime() {
		return testTime;
	}
	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}
	public ReportDetailVo getParentVo() {
		return parentVo;
	}
	public void setParentVo(ReportDetailVo parentVo) {
		this.parentVo = parentVo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public List<ReportDetailVo> getSubList() {
		return subList;
	}
	public void setSubList(List<ReportDetailVo> subList) {
		this.subList = subList;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getLimitLine() {
		return limitLine;
	}
	public void setLimitLine(String limitLine) {
		this.limitLine = limitLine;
	}
	public String getLimited() {
		return limited;
	}
	public void setLimited(String limited) {
		this.limited = limited;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodCode() {
		return methodCode;
	}
	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCyTime() {
		return cyTime;
	}
	public void setCyTime(String cyTime) {
		this.cyTime = cyTime;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnit2() {
		return unit2;
	}
	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSamplingId() {
		return samplingId;
	}
	public void setSamplingId(String samplingId) {
		this.samplingId = samplingId;
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
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	public String getStandId() {
		return standId;
	}
	public void setStandId(String standId) {
		this.standId = standId;
	}
	public String getStandName() {
		return standName;
	}
	public void setStandName(String standName) {
		this.standName = standName;
	}
	public String getStandCode() {
		return standCode;
	}
	public void setStandCode(String standCode) {
		this.standCode = standCode;
	}
	public TaskItemVo getTimVo() {
		return timVo;
	}
	public void setTimVo(TaskItemVo timVo) {
		this.timVo = timVo;
	}
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}
	public List<ReportDetailResultVo> getResultList() {
		return resultList;
	}
	public void setResultList(List<ReportDetailResultVo> resultList) {
		this.resultList = resultList;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	public String getTwa() {
		return twa;
	}
	public void setTwa(String twa) {
		this.twa = twa;
	}
	public String getStel() {
		return stel;
	}
	public void setStel(String stel) {
		this.stel = stel;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getLmt() {
		return lmt;
	}
	public void setLmt(String lmt) {
		this.lmt = lmt;
	}
	public String[] getResultArray() {
		return resultArray;
	}
	public void setResultArray(String[] resultArray) {
		this.resultArray = resultArray;
	}
	public int getCyHours() {
		return cyHours;
	}
	public void setCyHours(int cyHours) {
		this.cyHours = cyHours;
	}
}

