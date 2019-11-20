package cn.demi.bus.test.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.report.vo.RtabRoomVo;
import cn.demi.bus.report.vo.RtabRowVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.doc.vo.DocumentVo;
import cn.demi.init.std.vo.MethodVo;
import cn.demi.init.std.vo.PstandItemVo;
import cn.demi.res.vo.ApparaVo;

public class TaskItemVo extends Vo<TaskItemVo> {
	/**
	 * 任务
	 */
	private TaskVo taskVo; 
 
	/**
	 * 检测项目
	 */
	private String itemId;
	private String itemName;
	private String itemType;//项目分类： 粉尘类型，测试项目的限值分类等
	private String sampTypeId;
	private String sampTypeName;
	private String sampNum;
	private String sampName;
	private String xz;//性状
	/**
	 * 类别分类  环境  水 气  声
	 * 数据录入时根据此字段来决定显示那些列
	 * 
	 */
	private String st;
	/**
	 * 计量单位
	 */
	private String unit;
	private String slUnit;//速率单位
	private float price;//检测费用
	/**
	 * 项目类型
	 * 0实验室项目
	 * 1现场项目
	 * 2重测项目
	 */
	private String type;
	//评价标准
	private String standId;
	private String standName;
	/**
	 * 环境  限值
	 * 职卫限值类型
	 * mac       a
	 * twa stel  b
	 * taw lmt   c
	 */
	private String limited;
	private String pitId;//限制关系Id
	//职业 标准限值
	private String mac;//最大容许浓度
	private String twa;//时间加权容许浓度
	private String stel;//短时间接触容许浓度
	private String lmt;//超限倍数
	/**
	 * 检测方法
	 */
	private String methodId;
	private String methodName;
	/**
	 * 检出限
	 */
	private String limitLine;
	/**
	 * 测试仪器
	 */
	private String appId;
	private String appName;
	/**
	 * 标准物质
	 */
	private String stId;
	private String stName;
	/**
	 * 温度
	 */
	private String wd;
	/**
	 * 湿度
	 */
	private String sd;
	/**
	 * 检测截止时间
	 */
	private String compDate;
	/**
	 * 要求上报时间
	 */
	private String sbDate;
	/**
	 * 检测部门
	 */
	private String orgId;
	private String orgName;
	private String deptId;// 检测科室
	private String deptName;
	//分配信息
    private String assignUser;
    private String assignUserId;
    private String assignDate;
    private String assignMsg;
	/**
	 * 检测人
	 */
	private String testMan;
	private String testManId;
	private String testTime;//检测时间
	private String testEndTime;//声 检测
	private String testMsg;
	/**
	 * 校对人
	 */
	private String checkMan;
	private String checkManId;
	private String checkTime;//校对时间
	private String checkMsg;//校对意见
    /**
     * 进度
     */
	private Progress progress;
	/**
	 * 检测状态
	 */
	private String status;
    private String isBack;
	 /**
     * 原始记录文件路径
     */
    private String filePath;
    private String fileName;
    private String temp;
    private String remark;
    //距离1天，显示红色；距离三天，显示蓝色；其他不加标识
    private String color;
    
    private List<TestItemVo> itemList;
	private List<DocumentVo> tempList;//原始记录单模板集合
	private List<FilesVo> fileList;//附件
	private List<MethodVo> methodList;
	private List<ApparaVo> appList;
	private List<TaskItemVo> timList;
	//职卫 报告文件
	private List<RtabRowVo> dateList;// 日期块
	private List<RtabRoomVo> roomList;//车间结合
	private String[] headAry;//项目头部数组
	private PstandItemVo pitVo;//限值关系
	private String cydTemp;//采样单模板
	
	private String pzSt;//溶液配置记录单状态（1，已填报 ，0：未填报）
	
	
	public String getPzSt() {
		return pzSt;
	}

	public void setPzSt(String pzSt) {
		this.pzSt = pzSt;
	}

	public TaskVo getTaskVo() {
		return taskVo;
	}

	public void setTaskVo(TaskVo taskVo) {
		this.taskVo = taskVo;
	}
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSlUnit() {
		return slUnit;
	}

	public void setSlUnit(String slUnit) {
		this.slUnit = slUnit;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	public String getLimited() {
		return limited;
	}

	public void setLimited(String limited) {
		this.limited = limited;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
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

	public String getLmt() {
		return lmt;
	}

	public void setLmt(String lmt) {
		this.lmt = lmt;
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
	public String getLimitLine() {
		return limitLine;
	}

	public void setLimitLine(String limitLine) {
		this.limitLine = limitLine;
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

	public String getStId() {
		return stId;
	}

	public void setStId(String stId) {
		this.stId = stId;
	}

	public String getStName() {
		return stName;
	}

	public void setStName(String stName) {
		this.stName = stName;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getSd() {
		return sd;
	}

	public void setSd(String sd) {
		this.sd = sd;
	}

	public String getCompDate() {
		return compDate;
	}

	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}

	public String getSbDate() {
		return sbDate;
	}

	public void setSbDate(String sbDate) {
		this.sbDate = sbDate;
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAssignUser() {
		return assignUser;
	}

	public void setAssignUser(String assignUser) {
		this.assignUser = assignUser;
	}

	public String getAssignUserId() {
		return assignUserId;
	}

	public void setAssignUserId(String assignUserId) {
		this.assignUserId = assignUserId;
	}

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	public String getAssignMsg() {
		return assignMsg;
	}

	public void setAssignMsg(String assignMsg) {
		this.assignMsg = assignMsg;
	}

	public String getTestMan() {
		return testMan;
	}

	public void setTestMan(String testMan) {
		this.testMan = testMan;
	}

	public String getTestManId() {
		return testManId;
	}

	public void setTestManId(String testManId) {
		this.testManId = testManId;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getTestEndTime() {
		return testEndTime;
	}

	public void setTestEndTime(String testEndTime) {
		this.testEndTime = testEndTime;
	}

	public String getTestMsg() {
		return testMsg;
	}

	public void setTestMsg(String testMsg) {
		this.testMsg = testMsg;
	}

	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}

	public String getCheckManId() {
		return checkManId;
	}

	public void setCheckManId(String checkManId) {
		this.checkManId = checkManId;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckMsg() {
		return checkMsg;
	}

	public void setCheckMsg(String checkMsg) {
		this.checkMsg = checkMsg;
	}

	public Progress getProgress() {
		return progress;
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
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

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<TestItemVo> getItemList() {
		return itemList;
	}

	public void setItemList(List<TestItemVo> itemList) {
		this.itemList = itemList;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<DocumentVo> getTempList() {
		return tempList;
	}

	public void setTempList(List<DocumentVo> tempList) {
		this.tempList = tempList;
	}

	public List<FilesVo> getFileList() {
		return fileList;
	}

	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}

	public List<MethodVo> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<MethodVo> methodList) {
		this.methodList = methodList;
	}

	public List<ApparaVo> getAppList() {
		return appList;
	}

	public void setAppList(List<ApparaVo> appList) {
		this.appList = appList;
	}

	public List<TaskItemVo> getTimList() {
		return timList;
	}

	public void setTimList(List<TaskItemVo> timList) {
		this.timList = timList;
	}

	public String getSampNum() {
		return sampNum;
	}

	public void setSampNum(String sampNum) {
		this.sampNum = sampNum;
	}

	public String getSampName() {
		return sampName;
	}

	public void setSampName(String sampName) {
		this.sampName = sampName;
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

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}

	public List<RtabRowVo> getDateList() {
		return dateList;
	}

	public void setDateList(List<RtabRowVo> dateList) {
		this.dateList = dateList;
	}

	public String[] getHeadAry() {
		return headAry;
	}

	public void setHeadAry(String[] headAry) {
		this.headAry = headAry;
	}

	public String getPitId() {
		return pitId;
	}

	public void setPitId(String pitId) {
		this.pitId = pitId;
	}

	public PstandItemVo getPitVo() {
		return pitVo;
	}
	public void setPitVo(PstandItemVo pitVo) {
		this.pitVo = pitVo;
	}

	public String getCydTemp() {
		return cydTemp;
	}

	public void setCydTemp(String cydTemp) {
		this.cydTemp = cydTemp;
	}

	public List<RtabRoomVo> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<RtabRoomVo> roomList) {
		this.roomList = roomList;
	}
	
	
	
}

