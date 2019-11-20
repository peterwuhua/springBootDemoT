package cn.demi.bus.task.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.vo.ProgressVo;
import cn.demi.bus.pjt.vo.CustVo;
import cn.demi.bus.pjt.vo.ImVo;
import cn.demi.bus.report.vo.ReportVo;
import cn.demi.bus.sample.vo.SampCydVo;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.bus.test.vo.TestItemVo;
import cn.demi.doc.vo.DocumentVo;
import cn.demi.init.car.vo.CarUseVo;
import cn.demi.init.std.vo.SampSourceVo;

public class TaskVo extends Vo<TaskVo> {
	private TaskBaseVo taskBaseVo;//样品采样计划单基本属性
	private String ypjjPath;//样品交接路径
	private String ypjjName;//样品交接名称
	private String appNames; //采样仪器
	private String cyjhPath;//采样计划单路径
	private String cyjhName;//采样计划单名称
	private String projectId;//项目Id
	private String schemeId;//方案Id
	private CustVo custVo;//客户信息
	private String no;//任务编号
	private String reportNo;//报告编号
	private String taskType;//测试类型
	private String pj;//是否判定
	private String standIds;//判定依据
	private String standNames;//采样方法
	private float price;//检测费用
	private String reportWay;//取报告方式
	private String finishDate;//要求完成时间  出报告日期
	private int reportNum;//报告数量
	private String sampTypeId;//样品类型
	private String sampTypeName;
	private String sampType;//样品大类
	private String sampName;//样品名称
	private int zq;//周期
	private String jj;//加急
	private String source;//样品来源，采送样单位
	private String taskSource;//任务来源 ，采送样单位
	private int sampNum;//样品总数
	private String dealRequest;//样品处理要求
	private String saveRequest;//样品保存条件
	private String itemNames;//检测项目
	private String itemIds;
	private String pointNames;
	private String jcmd;//监测目的
	private String jcct;//监测内容
	private String testResult;//检测结果
	private String result;//结论
	private String zk;//是否显示质控数据到报告
	private String date;//申请日期
	private String userId;//申请人
	private String userName;//申请人
	private String orgId;//受理部门
	private String orgName;
	
	private String fb;//是否分包
	private String fbUnit;//分包单位
	private String fbItemId;//分包项目
	private String fbItemName;
	
	private String apId;//采样安排人
	private String apName;
	private String apDate;//安排日期
	private String apMsg;//意见
	private String cyStandIds;//采样依据
	private String cyStandNames;
	
	private String fzId;//负责人
	private String fzName;
	private String cyId;//采样人
	private String cyName;
	private String cyDate;//开始采样日期
	private String cyEndDate;//采样截至日期
	private String cyUserId;//采样填报人
	private String cyUserName;
	private String cyTime;//填报日期
	private String cyMsg;//备注

	private String zbId;//准备人
	private String zbName;
	private String zbDate;//准备日期
	private String zbMsg;//意见
	
	private String reciveDate;//样品交接日期
	private String reciveUserId;//样品接收人
	private String reciveUser;
	private String reciveMsg;
	
	private String xdDate;//下达
	private String xdUser;
	private String xdUserId;
	private String xdMsg;
	
	private String sumDate;//审核日期
	private String sumUser;//负责人
	private String sumUserId;
	private String sumMsg;
	private String timTal;//总数
	private String timNum;//已完成数
	
	private String makeUser;//报告编制人（汇总指定人时用）
	private String makeUserId;
	
	/**
	 * 其它说明
	 */
	private String remark;
	 /**
     *委托书
     */
	private String fileName;
	private String filePath;
	/**
	 *现场分析数据上报状态
	 *-1 无上报数据
	 *0 未上报
	 *1 保存中
	 *2 已上报
	 */
	private String xcSt;
	
	private Progress progress;
	private String status;
	private String isBack;
	private ProgressVo progressVo;
	
	private ReportVo reportVo;
	private List<TaskItemVo> timList;
	private List<TestItemVo> itemList;
	private List<TestItemVo> zkList;
	private List<FilesVo> fileList;
	private List<DocumentVo> tempList;//原始记录单模板集合
	private List<TaskVo> taskList;
	private List<SamplingVo> sampList;//样品集合
	private List<SamplingVo> zsampList;//质控样集合
	private List<TaskPointVo> tpList;
	private List<SampAppVo> appList;//采样设备集合
	private List<CarUseVo> carList;
	private List<TaskFbVo> fbList;
	private List<ImVo> imList; //检测方法
	private List<SampSourceVo> ssList ; //采样依据
	private List<SampCydVo> cydList;//采样单集合
	
	private String customerId;//客户id
	
	private String cyAppIds;//采样设备ids
	private String cyAppNames;//采样设备名称
	
//	private int cyDay;//采样天数
//	
//	
//	public int getCyDay() {
//		return cyDay;
//	}
//	public void setCyDay(int cyDay) {
//		this.cyDay = cyDay;
//	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public List<SampSourceVo> getSsList() {
		return ssList;
	}
	public void setSsList(List<SampSourceVo> ssList) {
		this.ssList = ssList;
	}
	public List<ImVo> getImList() {
		return imList;
	}
	public void setImList(List<ImVo> imList) {
		this.imList = imList;
	}
	public TaskBaseVo getTaskBaseVo() {
		return taskBaseVo;
	}
	public void setTaskBaseVo(TaskBaseVo taskBaseVo) {
		this.taskBaseVo = taskBaseVo;
	}
	public String getAppNames() {
		return appNames;
	}
	public void setAppNames(String appNames) {
		this.appNames = appNames;
	}
	public String getCyjhPath() {
		return cyjhPath;
	}
	public void setCyjhPath(String cyjhPath) {
		this.cyjhPath = cyjhPath;
	}
	public String getCyjhName() {
		return cyjhName;
	}
	public void setCyjhName(String cyjhName) {
		this.cyjhName = cyjhName;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public CustVo getCustVo() {
		return custVo;
	}
	public void setCustVo(CustVo custVo) {
		this.custVo = custVo;
	}
	public String getStandIds() {
		return standIds;
	}
	public void setStandIds(String standIds) {
		this.standIds = standIds;
	}
	public String getStandNames() {
		return standNames;
	}
	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getReportWay() {
		return reportWay;
	}
	public void setReportWay(String reportWay) {
		this.reportWay = reportWay;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getTestResult() {
		return testResult;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	public String getSumDate() {
		return sumDate;
	}
	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}
	public String getSumUser() {
		return sumUser;
	}
	public void setSumUser(String sumUser) {
		this.sumUser = sumUser;
	}
	public String getSumUserId() {
		return sumUserId;
	}
	public void setSumUserId(String sumUserId) {
		this.sumUserId = sumUserId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public ProgressVo getProgressVo() {
		return progressVo;
	}
	public void setProgressVo(ProgressVo progressVo) {
		this.progressVo = progressVo;
	}
	public ReportVo getReportVo() {
		return reportVo;
	}
	public void setReportVo(ReportVo reportVo) {
		this.reportVo = reportVo;
	}
	public List<TestItemVo> getItemList() {
		return itemList;
	}
	public void setItemList(List<TestItemVo> itemList) {
		this.itemList = itemList;
	}
	public List<FilesVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String getSumMsg() {
		return sumMsg;
	}
	public void setSumMsg(String sumMsg) {
		this.sumMsg = sumMsg;
	}
	public List<TaskVo> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<TaskVo> taskList) {
		this.taskList = taskList;
	}
	public int getReportNum() {
		return reportNum;
	}
	public void setReportNum(int reportNum) {
		this.reportNum = reportNum;
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
	public int getZq() {
		return zq;
	}
	public void setZq(int zq) {
		this.zq = zq;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getSampNum() {
		return sampNum;
	}
	public void setSampNum(int sampNum) {
		this.sampNum = sampNum;
	}
	public String getApId() {
		return apId;
	}
	public void setApId(String apId) {
		this.apId = apId;
	}
	public String getApName() {
		return apName;
	}
	public void setApName(String apName) {
		this.apName = apName;
	}
	public String getApDate() {
		return apDate;
	}
	public void setApDate(String apDate) {
		this.apDate = apDate;
	}
	public String getApMsg() {
		return apMsg;
	}
	public void setApMsg(String apMsg) {
		this.apMsg = apMsg;
	}
	public String getCyId() {
		return cyId;
	}
	public void setCyId(String cyId) {
		this.cyId = cyId;
	}
	public String getCyName() {
		return cyName;
	}
	public void setCyName(String cyName) {
		this.cyName = cyName;
	}
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}
	public String getCyEndDate() {
		return cyEndDate;
	}
	public void setCyEndDate(String cyEndDate) {
		this.cyEndDate = cyEndDate;
	}
	public String getCyMsg() {
		return cyMsg;
	}
	public void setCyMsg(String cyMsg) {
		this.cyMsg = cyMsg;
	}
	public String getXdDate() {
		return xdDate;
	}
	public void setXdDate(String xdDate) {
		this.xdDate = xdDate;
	}
	public String getXdUser() {
		return xdUser;
	}
	public void setXdUser(String xdUser) {
		this.xdUser = xdUser;
	}
	public String getXdUserId() {
		return xdUserId;
	}
	public void setXdUserId(String xdUserId) {
		this.xdUserId = xdUserId;
	}
	public String getXdMsg() {
		return xdMsg;
	}
	public void setXdMsg(String xdMsg) {
		this.xdMsg = xdMsg;
	}
	public Progress getProgress() {
		return progress;
	}
	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	public String getReciveDate() {
		return reciveDate;
	}
	public void setReciveDate(String reciveDate) {
		this.reciveDate = reciveDate;
	}
	public String getReciveUserId() {
		return reciveUserId;
	}
	public void setReciveUserId(String reciveUserId) {
		this.reciveUserId = reciveUserId;
	}
	public String getReciveUser() {
		return reciveUser;
	}
	public void setReciveUser(String reciveUser) {
		this.reciveUser = reciveUser;
	}
	public List<SamplingVo> getSampList() {
		return sampList;
	}
	public void setSampList(List<SamplingVo> sampList) {
		this.sampList = sampList;
	}
	public List<TaskPointVo> getTpList() {
		return tpList;
	}
	public void setTpList(List<TaskPointVo> tpList) {
		this.tpList = tpList;
	}
	public String getPj() {
		return pj;
	}
	public void setPj(String pj) {
		this.pj = pj;
	}
	public String getPointNames() {
		return pointNames;
	}
	public void setPointNames(String pointNames) {
		this.pointNames = pointNames;
	}
	public List<SampAppVo> getAppList() {
		return appList;
	}
	public void setAppList(List<SampAppVo> appList) {
		this.appList = appList;
	}
	public List<CarUseVo> getCarList() {
		return carList;
	}
	public void setCarList(List<CarUseVo> carList) {
		this.carList = carList;
	}
	public String getCyUserId() {
		return cyUserId;
	}
	public void setCyUserId(String cyUserId) {
		this.cyUserId = cyUserId;
	}
	public String getCyUserName() {
		return cyUserName;
	}
	public void setCyUserName(String cyUserName) {
		this.cyUserName = cyUserName;
	}
	public String getCyTime() {
		return cyTime;
	}
	public void setCyTime(String cyTime) {
		this.cyTime = cyTime;
	}
	public List<DocumentVo> getTempList() {
		return tempList;
	}
	public void setTempList(List<DocumentVo> tempList) {
		this.tempList = tempList;
	}
	public String getDealRequest() {
		return dealRequest;
	}
	public void setDealRequest(String dealRequest) {
		this.dealRequest = dealRequest;
	}
	public String getSaveRequest() {
		return saveRequest;
	}
	public void setSaveRequest(String saveRequest) {
		this.saveRequest = saveRequest;
	}
	public String getTaskSource() {
		return taskSource;
	}
	public void setTaskSource(String taskSource) {
		this.taskSource = taskSource;
	}
	public String getFzId() {
		return fzId;
	}
	public void setFzId(String fzId) {
		this.fzId = fzId;
	}
	public String getFzName() {
		return fzName;
	}
	public void setFzName(String fzName) {
		this.fzName = fzName;
	}
	public String getJj() {
		return jj;
	}
	public void setJj(String jj) {
		this.jj = jj;
	}
	public String getJcmd() {
		return jcmd;
	}
	public void setJcmd(String jcmd) {
		this.jcmd = jcmd;
	}
	public List<TestItemVo> getZkList() {
		return zkList;
	}
	public void setZkList(List<TestItemVo> zkList) {
		this.zkList = zkList;
	}
	public String getZk() {
		return zk;
	}
	public void setZk(String zk) {
		this.zk = zk;
	}
	public String getJcct() {
		return jcct;
	}
	public void setJcct(String jcct) {
		this.jcct = jcct;
	}
	public List<SamplingVo> getZsampList() {
		return zsampList;
	}
	public void setZsampList(List<SamplingVo> zsampList) {
		this.zsampList = zsampList;
	}
	public String getFb() {
		return fb;
	}
	public void setFb(String fb) {
		this.fb = fb;
	}
	public String getFbUnit() {
		return fbUnit;
	}
	public void setFbUnit(String fbUnit) {
		this.fbUnit = fbUnit;
	}
	public String getFbItemId() {
		return fbItemId;
	}
	public void setFbItemId(String fbItemId) {
		this.fbItemId = fbItemId;
	}
	public String getFbItemName() {
		return fbItemName;
	}
	public void setFbItemName(String fbItemName) {
		this.fbItemName = fbItemName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	public List<TaskFbVo> getFbList() {
		return fbList;
	}
	public void setFbList(List<TaskFbVo> fbList) {
		this.fbList = fbList;
	}
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	public String getZbId() {
		return zbId;
	}
	public void setZbId(String zbId) {
		this.zbId = zbId;
	}
	public String getZbName() {
		return zbName;
	}
	public void setZbName(String zbName) {
		this.zbName = zbName;
	}
	public String getZbDate() {
		return zbDate;
	}
	public void setZbDate(String zbDate) {
		this.zbDate = zbDate;
	}
	public String getZbMsg() {
		return zbMsg;
	}
	public void setZbMsg(String zbMsg) {
		this.zbMsg = zbMsg;
	}
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	public String getCyStandIds() {
		return cyStandIds;
	}
	public void setCyStandIds(String cyStandIds) {
		this.cyStandIds = cyStandIds;
	}
	public String getCyStandNames() {
		return cyStandNames;
	}
	public void setCyStandNames(String cyStandNames) {
		this.cyStandNames = cyStandNames;
	}
	public String getYpjjPath() {
		return ypjjPath;
	}
	public void setYpjjPath(String ypjjPath) {
		this.ypjjPath = ypjjPath;
	}
	public String getYpjjName() {
		return ypjjName;
	}
	public void setYpjjName(String ypjjName) {
		this.ypjjName = ypjjName;
	}
	public List<SampCydVo> getCydList() {
		return cydList;
	}
	public void setCydList(List<SampCydVo> cydList) {
		this.cydList = cydList;
	}
	public List<TaskItemVo> getTimList() {
		return timList;
	}
	public void setTimList(List<TaskItemVo> timList) {
		this.timList = timList;
	}
	public String getXcSt() {
		return xcSt;
	}
	public void setXcSt(String xcSt) {
		this.xcSt = xcSt;
	}
	public String getTimTal() {
		return timTal;
	}
	public void setTimTal(String timTal) {
		this.timTal = timTal;
	}
	public String getTimNum() {
		return timNum;
	}
	public void setTimNum(String timNum) {
		this.timNum = timNum;
	}
	public String getReciveMsg() {
		return reciveMsg;
	}
	public void setReciveMsg(String reciveMsg) {
		this.reciveMsg = reciveMsg;
	}
	public String getMakeUser() {
		return makeUser;
	}
	public void setMakeUser(String makeUser) {
		this.makeUser = makeUser;
	}
	public String getMakeUserId() {
		return makeUserId;
	}
	public void setMakeUserId(String makeUserId) {
		this.makeUserId = makeUserId;
	}
	public String getCyAppIds() {
		return cyAppIds;
	}
	public void setCyAppIds(String cyAppIds) {
		this.cyAppIds = cyAppIds;
	}
	public String getCyAppNames() {
		return cyAppNames;
	}
	public void setCyAppNames(String cyAppNames) {
		this.cyAppNames = cyAppNames;
	}
	
	
	
}

