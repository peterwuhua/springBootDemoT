package cn.demi.bus.test.po;

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
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.task.po.Task;

/**
 * 子任务-项目流程
 * @author QuJunLong
 */
@Entity(name = "bus_task_item")
@Table(name = "bus_task_item")
@Module(value = "bus.taskItem")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskItem extends Po<TaskItem>{

	private static final long serialVersionUID = -4958119360262705894L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","createUser","isDel","progress","cust","orgName","task","itemId","itemName","compDate","testMan","checkMan"
			,"checkTime","isBack","assignUser","assignDate","unit","type","status","sbDate","sampNum","sampName","testTime","pzSt"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	//判定结果	
	public static final String RESULT_YES="合格";
	public static final String RESULT_NO="不合格";

	//项目类型
	public static final String ITEM_TYPE_PT="0";//普通项目
	public static final String ITEM_TYPE_XC="1";//现场项目
	public static final String ITEM_TYPE_CC="2";//重测项目 复测项目
	
	/**
	 * 任务
	 */
	private Task task; 
	/**
	 * 检测项目
	 */
	private String itemId;
	private String itemName;
	private String itemType;//项目分类： 粉尘类型，测试项目的限值分类等
	private String sampTypeId;
	private String sampTypeName;
	private int sampNum;
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
	 * 限值
	 */
	private String limited;
	private String pitId;//限值关系Id
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
    
    private String pzSt;//溶液配置记录单状态（1，已填报 ，0：未填报）
    
    
    @Column(length = 32)
	public String getPzSt() {
		return pzSt;
	}
	public void setPzSt(String pzSt) {
		this.pzSt = pzSt;
	}
	@Column(length = 32)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(length = 32)
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Column(length = 32)
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
	@Column(length = 320)
	public String getLimitLine() {
		return limitLine;
	}
	public void setLimitLine(String limitLine) {
		this.limitLine = limitLine;
	}
	@Column(length = 320)
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	@Column(length = 640)
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	@Column(length = 20)
	public String getCompDate() {
		return compDate;
	}
	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	public String getCheckMan() {
		return checkMan;
	}
	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}
	@Column(length = 64)
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	@Column(length = 128)
	public String getCheckMsg() {
		return checkMsg;
	}
	public void setCheckMsg(String checkMsg) {
		this.checkMsg = checkMsg;
	}
	@Column(length = 32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 256)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length = 100)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length = 320)
	public String getTestManId() {
		return testManId;
	}
	public void setTestManId(String testManId) {
		this.testManId = testManId;
	}
	@Column(length = 32)
	public String getCheckManId() {
		return checkManId;
	}
	public void setCheckManId(String checkManId) {
		this.checkManId = checkManId;
	}
	@ManyToOne
	@JoinColumn(name = "progress_id")
	public Progress getProgress() {
		return progress;
	}
	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	@Column(length = 320)
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	@Column(length = 640)
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@ManyToOne
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	@Column(length = 32)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 320)
	public String getLimited() {
		return limited;
	}
	public void setLimited(String limited) {
		this.limited = limited;
	}
	@Column(length = 32)
	public String getSlUnit() {
		return slUnit;
	}
	public void setSlUnit(String slUnit) {
		this.slUnit = slUnit;
	}
	@Column(length = 128)
	public String getAssignMsg() {
		return assignMsg;
	}
	public void setAssignMsg(String assignMsg) {
		this.assignMsg = assignMsg;
	}
	@Column(length = 64)
	public String getTestEndTime() {
		return testEndTime;
	}
	public void setTestEndTime(String testEndTime) {
		this.testEndTime = testEndTime;
	}
	@Column(length = 128)
	public String getTestMsg() {
		return testMsg;
	}
	public void setTestMsg(String testMsg) {
		this.testMsg = testMsg;
	}
	@Column(length = 128)
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	@Column(length = 320)
	public String getStId() {
		return stId;
	}
	public void setStId(String stId) {
		this.stId = stId;
	}
	@Column(length = 640)
	public String getStName() {
		return stName;
	}
	public void setStName(String stName) {
		this.stName = stName;
	}

	@Column(length = 20)
	public String getSbDate() {
		return sbDate;
	}
	public void setSbDate(String sbDate) {
		this.sbDate = sbDate;
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
	@Column(length = 16)
	public String getWd() {
		return wd;
	}
	public void setWd(String wd) {
		this.wd = wd;
	}
	@Column(length = 8)
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	@Column(length = 32)
	public String getAssignUser() {
		return assignUser;
	}
	public void setAssignUser(String assignUser) {
		this.assignUser = assignUser;
	}
	@Column(length = 32)
	public String getAssignUserId() {
		return assignUserId;
	}
	public void setAssignUserId(String assignUserId) {
		this.assignUserId = assignUserId;
	}
	@Column(length = 20)
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	@Column(length = 16)
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Column(length = 32)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(length = 32)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(length = 2)
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
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
	@Column(length = 32)
	public int getSampNum() {
		return sampNum;
	}
	public void setSampNum(int sampNum) {
		this.sampNum = sampNum;
	}
	@Column(length = 128)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length = 32)
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length = 128)
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	@Column(length = 32)
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	@Column(length = 32)
	public String getSt() {
		return st;
	}
	public void setSt(String st) {
		this.st = st;
	}
	@Column(length = 1000)
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	@Column(length = 32)
	public String getPitId() {
		return pitId;
	}
	public void setPitId(String pitId) {
		this.pitId = pitId;
	}
	
//	
//	public static void main(String[] args) {
//		CreateCodeUtils.autoCreateCode(TaskItem.class, true, ActionType.JSP);
//	}
}
