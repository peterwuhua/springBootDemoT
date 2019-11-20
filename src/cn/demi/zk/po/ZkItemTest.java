package cn.demi.zk.po;

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

/**
 * 项目检测信息
 * @author QuJunLong
 */
@Entity(name = "zk_item_test")
@Table(name = "zk_item_test")
@Module(value = "zk.itemTest")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ZkItemTest extends Po<ZkItemTest>{
	private static final long serialVersionUID = 1L;
	
	public static final int STOP=-1;//终止状态
	//判定结果	
	public static final String RESULT_YES="合格";
	public static final String RESULT_NO="不合格";
	
	public static final String STATUS_0="0";
	public static final String STATUS_1="1";
	
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","isDel","progressLog","orgName","task","sampling","itemId","itemName","compDate","value","testTime","testMan","checkMan","checkTime","isBack","lastUpdTime","checkMsg"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 任务
	 */
	private ZkTask task;
	/**
	 *样品信息
	 */
	private ZkSampling sampling;
	/**
	 * 评价标准与项目关系表Id
	 */
	private String psId;
	/**
	 * 检测项目
	 */
	private String itemId;
	private String itemName;
	/**
	 * 检测结果
	 */
	private String value;
	/**
	 * 回收率
	 */
	private String hsl;
	/**
	 * 判定结果
	 * 合格 不合格
	 */
	private String result;
	/**
	 * 计量单位
	 */
	private String unit;
	/**
	 * 测定结果相对差值
	 */
	private String limitLine;
	/**
	 * 给定标准值
	 */
	private String limited;
	
	/**
	 * 检测物质
	 */
	private String standId;
	/**
	 * 标准物质名称
	 */
	private String standName;
	/**
	 * 检测方法
	 */
	private String methodId;
	private String methodName;
	private String standCode;
	private String chapter;
	/**
	 * 测试仪器
	 */
	private String appId;
	private String appName;
	/**
	 *仪器状态
	 */
	private String appStat;
	/**
	 * 开始时间
	 */
	private String appStartTime;
	/**
	 * 结束时间
	 */
	private String appEndTime;
	/**
	 * 温度
	 */
	private String wd;
	/**
	 * 湿度
	 */
	private String sd;
	/**
	 * 要求完成日期
	 */
	private String compDate;
	/**
	 * 检测科室
	 */
	private String orgId;
	/**
	 * 检测科室
	 */
	private String orgName;
	/**
	 * 检测人
	 */
	private String testMan;
	private String testManId;
	/**
	 * 检测时间
	 */
	private String testTime;
	/**
	 * 校对人
	 */
	private String checkMan;
	private String checkManId;
	/**
	 * 校对时间
	 */
	private String checkTime;
	 /**
	  * 校对意见
	  */
	private String checkMsg;
	/**
	 * 检测状态
	 */
	private String status;
	 /**
     * 原始记录文件路径
     */
    private String filePath;
    /**
     * 原始记录文件名称
     */
    private String fileName;
    
    private String remark;
    
    private int isBack;
 
	/**
	 * 当前查询数据对于的日志记录
	 * map时用，不存入数据库
	 */
	private ZkProgressLog progressLog;
    
	@ManyToOne
	@JoinColumn(name = "samp_id")
	public ZkSampling getSampling() {
		return sampling;
	}
	public void setSampling(ZkSampling sampling) {
		this.sampling = sampling;
	}
	@Column(length = 32)
	public String getPsId() {
		return psId;
	}
	public void setPsId(String psId) {
		this.psId = psId;
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
	@Column(length = 32)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(length = 32)
	public String getLimitLine() {
		return limitLine;
	}
	public void setLimitLine(String limitLine) {
		this.limitLine = limitLine;
	}
	 
	@Column(length = 64)
	public String getStandName() {
		return standName;
	}
	public void setStandName(String standName) {
		this.standName = standName;
	}
	@Column(length = 32)
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	@Column(length = 64)
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
	@Column(length = 100)
	public String getTestMan() {
		return testMan;
	}
	public void setTestMan(String testMan) {
		this.testMan = testMan;
	}
	@Column(length = 20)
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
	@Column(length = 20)
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	@Column(length = 64)
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
	@Transient
	public ZkProgressLog getProgressLog() {
		return progressLog;
	}
	public void setProgressLog(ZkProgressLog progressLog) {
		this.progressLog = progressLog;
	}
	@Column(length = 32)
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	@Column(length = 64)
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@ManyToOne
	@JoinColumn(name = "task_id")
	public ZkTask getTask() {
		return task;
	}
	public void setTask(ZkTask task) {
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
	@Column(length = 32)
	public String getLimited() {
		return limited;
	}
	public void setLimited(String limited) {
		this.limited = limited;
	}
	@Column(length = 2)
	public int getIsBack() {
		return isBack;
	}
	public void setIsBack(int isBack) {
		this.isBack = isBack;
	}
	@Column(length = 32)
	public String getHsl() {
		return hsl;
	}
	public void setHsl(String hsl) {
		this.hsl = hsl;
	}
	@Column(length = 32)
	public String getStandId() {
		return standId;
	}
	public void setStandId(String standId) {
		this.standId = standId;
	}
	@Column(length = 32)
	public String getWd() {
		return wd;
	}
	public void setWd(String wd) {
		this.wd = wd;
	}
	@Column(length = 32)
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	@Column(length = 32)
	public String getAppStat() {
		return appStat;
	}
	public void setAppStat(String appStat) {
		this.appStat = appStat;
	}
	@Column(length = 20)
	public String getAppStartTime() {
		return appStartTime;
	}
	public void setAppStartTime(String appStartTime) {
		this.appStartTime = appStartTime;
	}
	@Column(length = 20)
	public String getAppEndTime() {
		return appEndTime;
	}
	public void setAppEndTime(String appEndTime) {
		this.appEndTime = appEndTime;
	}
	@Column(length = 64)
	public String getStandCode() {
		return standCode;
	}
	public void setStandCode(String standCode) {
		this.standCode = standCode;
	}
	@Column(length = 32)
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ZkItemTest.class, true, ActionType.JSP);
	}
}
