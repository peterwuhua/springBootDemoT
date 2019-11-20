package cn.demi.zk.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.init.std.vo.MethodVo;
import cn.demi.res.vo.ApparaVo;

public class ZkItemTestVo extends Vo<ZkItemTestVo> {
	/**
	 * 任务
	 */
	private ZkTaskVo taskVo;
	/**
	 *样品信息
	 */
	private ZkSamplingVo samplingVo;
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
	 * 检测标准编号
	 */
	private String standId;
	/**
	 * 检测标准名称
	 */
	private String standName;
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
	 * 检测方法
	 */
	private String methodId;
	private String methodName;
	private String standCode;
	private String chapter;

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
	private ZkProgressLogVo progressLogVo;
	
	private List<MethodVo> methodList;
	
	private List<ZkItemTestVo> itemList;
	private List<ZkItemTestVo> ItemVoList;
	//附件
	private List<FilesVo> fileList;
	private List<ApparaVo> appList;
	
	public ZkTaskVo getTaskVo() {
		return taskVo;
	}

	public void setTaskVo(ZkTaskVo taskVo) {
		this.taskVo = taskVo;
	}

	public ZkSamplingVo getSamplingVo() {
		return samplingVo;
	}

	public void setSamplingVo(ZkSamplingVo samplingVo) {
		this.samplingVo = samplingVo;
	}

	public String getPsId() {
		return psId;
	}

	public void setPsId(String psId) {
		this.psId = psId;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHsl() {
		return hsl;
	}

	public void setHsl(String hsl) {
		this.hsl = hsl;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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
 
	public String getStandName() {
		return standName;
	}

	public void setStandName(String standName) {
		this.standName = standName;
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

	public String getCompDate() {
		return compDate;
	}

	public void setCompDate(String compDate) {
		this.compDate = compDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsBack() {
		return isBack;
	}

	public void setIsBack(int isBack) {
		this.isBack = isBack;
	}

	public ZkProgressLogVo getProgressLogVo() {
		return progressLogVo;
	}

	public void setProgressLogVo(ZkProgressLogVo progressLogVo) {
		this.progressLogVo = progressLogVo;
	}

	public List<MethodVo> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<MethodVo> methodList) {
		this.methodList = methodList;
	}

	public List<ZkItemTestVo> getItemList() {
		return itemList;
	}

	public void setItemList(List<ZkItemTestVo> itemList) {
		this.itemList = itemList;
	}

	public String getStandId() {
		return standId;
	}

	public void setStandId(String standId) {
		this.standId = standId;
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

	public String getAppStat() {
		return appStat;
	}

	public void setAppStat(String appStat) {
		this.appStat = appStat;
	}

	public String getAppStartTime() {
		return appStartTime;
	}

	public void setAppStartTime(String appStartTime) {
		this.appStartTime = appStartTime;
	}

	public String getAppEndTime() {
		return appEndTime;
	}

	public void setAppEndTime(String appEndTime) {
		this.appEndTime = appEndTime;
	}

	public List<ApparaVo> getAppList() {
		return appList;
	}

	public void setAppList(List<ApparaVo> appList) {
		this.appList = appList;
	}

	public List<ZkItemTestVo> getItemVoList() {
		return ItemVoList;
	}

	public void setItemVoList(List<ZkItemTestVo> itemVoList) {
		ItemVoList = itemVoList;
	}

	public String getStandCode() {
		return standCode;
	}

	public void setStandCode(String standCode) {
		this.standCode = standCode;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public List<FilesVo> getFileList() {
		return fileList;
	}

	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	
}

