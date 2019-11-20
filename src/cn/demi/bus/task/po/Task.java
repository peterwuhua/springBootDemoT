package cn.demi.bus.task.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pjt.po.Cust;
/**
 * 任务信息
 * 
 * @author QuJunLong
 */
@Entity(name = "bus_task")
@Table(name = "bus_task")
@Module(value = "bus.task")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task extends Po<Task> {
	
	public static final String ST_N="-1";//无上报数据
	public static final String ST_0="0";//未上报
	public static final String ST_1="1";//上报中
	public static final String ST_2="2";//已上报
	
	private static final long serialVersionUID = 1L;
	public String[] PROPERTY_TO_MAP = { "id", "no", "isDel", "progress", "status", "taskType", "cust", "price", "finishDate", "isBack", "orgName", "sumUser",
			"itemNames", "date", "invoice", "apName", "apDate", "cyName", "cyDate", "cyTime", "cyEndDate", "xdDate", "xdUser", "sumDate", "sumUser",
			"sampTypeName", "sampName", "sampNum", "userName", "source", "reciveDate", "pointNames", "jj", "projectId", "schemeId", "sampType", "zbDate",
			"zbName","timNum","timTal","pj","reciveMsg","remark" ,"cyAppIds" ,"cyAppNames"};
	public String[] IGNORE_PROPERTY_TO_PO = { "id", "createTime", "lastUpdTime", "version", "no", "progress", "status", "cust", "isBack", "orgId", "orgName",
			"projectId", "schemeId", "sampType" };

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}

	
	private String projectId;// 项目Id
	private String schemeId;// 方案Id
	private String no;// 任务编号
	private String reportNo;// 报告编号
	private String taskType;// 测试类型
	private Cust cust;// 客户信息
	private String pj;// 是否判定
	private String standIds;// 判定依据
	private String standNames;
	private float price;// 检测费用
	private String reportWay;// 取报告方式
	private String finishDate;// 要求完成时间 出报告日期
	private int reportNum;// 报告数量
	private String sampTypeId;// 样品类型
	private String sampTypeName;
	private String sampType;// 样品大类
	private String sampName;// 样品名称
	private int zq;// 周期
	private String source;// 样品来源
	private String taskSource;// 任务来源 ，采送样单位
	private int sampNum;// 样品总数
	private String dealRequest;// 样品处理要求
	private String saveRequest;// 样品保存条件

	private String jj;// 加急
	private String itemNames;
	private String pointNames;

	private String jcmd;// 监测目的
	private String jcct;// 监测内容
	private String testResult;// 检测结果
	private String result;// 结论
	private String zk;// 是否显示质控数据到报告

	private String fb;// 是否分包
	private String fbUnit;// 分包单位
	private String fbItemId;// 分包项目
	private String fbItemName;

	private String date;// 登记日期
	private String userId;// 登记人
	private String userName;
	private String orgId;// 受理部门
	private String orgName;

	private String apId;// 采样安排人
	private String apName;
	private String apDate;// 安排日期
	private String apMsg;// 意见
	private String cyStandIds;//采样依据
	private String cyStandNames;
	
	private String zbId;// 准备人
	private String zbName;
	private String zbDate;// 准备日期
	private String zbMsg;// 意见

	private String fzId;// 负责人
	private String fzName;
	private String cyId;// 采/送样人
	private String cyName;
	private String cyDate;// 开始采样日期
	private String cyEndDate;// 采样截至日期

	private String cyUserId;// 采样填报人
	private String cyUserName;
	private String cyTime;// 填报日期
	private String cyMsg;// 备注

	private String reciveDate;// 样品交接日期
	private String reciveUserId;// 样品接收人
	private String reciveUser;
	private String reciveMsg;
	
	private String xdDate;// 下达
	private String xdUser;
	private String xdUserId;
	private String xdMsg;

	private String sumDate;// 审核日期
	private String sumUser;// 负责人
	private String sumUserId;
	private String sumMsg;
	/**
	 * 其它说明
	 */
	private String remark;
	/**
	 * 委托书
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
	private String timTal;//总数
	private String timNum;//已完成数

	private String cyAppIds;//采样设备ids
	private String cyAppNames;//采样设备名称
	
	
//	private int cyDay;//采样天数
//	@Column(length = 16)
//	public int getCyDay() {
//		return cyDay;
//	}
//
//	public void setCyDay(int cyDay) {
//		this.cyDay = cyDay;
//	}

	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}

	public void setCust(Cust cust) {
		this.cust = cust;
	}

	@Column(length = 32)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(length = 32)
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Column(length = 20)
	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	@Column(length = 320)
	public String getItemNames() {
		return itemNames;
	}

	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}

	@Column(length = 320)
	public String getStandIds() {
		return standIds;
	}

	public void setStandIds(String standIds) {
		this.standIds = standIds;
	}

	@Column(length = 320)
	public String getStandNames() {
		return standNames;
	}
	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}
	@Column(length = 128)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@ManyToOne
	@JoinColumn(name = "progress_id")
	public Progress getProgress() {
		return progress;
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}

	@Column(length = 8)
	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	@Column(length = 32)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(length = 32)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(length = 32)
	public String getReportWay() {
		return reportWay;
	}

	public void setReportWay(String reportWay) {
		this.reportWay = reportWay;
	}

	@Column(length = 16)
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Column(length = 64)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(length = 128)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(length = 20)
	public String getSumDate() {
		return sumDate;
	}

	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}

	@Column(length = 32)
	public String getSumUser() {
		return sumUser;
	}

	public void setSumUser(String sumUser) {
		this.sumUser = sumUser;
	}

	@Column(length = 32)
	public String getSumUserId() {
		return sumUserId;
	}

	public void setSumUserId(String sumUserId) {
		this.sumUserId = sumUserId;
	}

	@Column(length = 1000)
	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	@Column(length = 64)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(length = 20)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(length = 32)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length = 256)
	public String getSumMsg() {
		return sumMsg;
	}

	public void setSumMsg(String sumMsg) {
		this.sumMsg = sumMsg;
	}

	@Column(length = 2)
	public int getReportNum() {
		return reportNum;
	}

	public void setReportNum(int reportNum) {
		this.reportNum = reportNum;
	}

	@Column(length = 320)
	public String getSampTypeId() {
		return sampTypeId;
	}

	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}

	@Column(length = 320)
	public String getSampTypeName() {
		return sampTypeName;
	}

	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}

	@Column(length = 320)
	public String getSampName() {
		return sampName;
	}

	public void setSampName(String sampName) {
		this.sampName = sampName;
	}

	@Column(length = 4)
	public int getZq() {
		return zq;
	}

	public void setZq(int zq) {
		this.zq = zq;
	}

	@Column(length = 64)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(length = 4)
	public int getSampNum() {
		return sampNum;
	}

	public void setSampNum(int sampNum) {
		this.sampNum = sampNum;
	}

	@Column(length = 32)
	public String getApId() {
		return apId;
	}

	public void setApId(String apId) {
		this.apId = apId;
	}

	@Column(length = 32)
	public String getApName() {
		return apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}

	@Column(length = 20)
	public String getApDate() {
		return apDate;
	}

	public void setApDate(String apDate) {
		this.apDate = apDate;
	}

	@Column(length = 256)
	public String getApMsg() {
		return apMsg;
	}

	public void setApMsg(String apMsg) {
		this.apMsg = apMsg;
	}

	@Column(length = 320)
	public String getCyId() {
		return cyId;
	}

	public void setCyId(String cyId) {
		this.cyId = cyId;
	}

	@Column(length = 320)
	public String getCyName() {
		return cyName;
	}

	public void setCyName(String cyName) {
		this.cyName = cyName;
	}

	@Column(length = 20)
	public String getCyDate() {
		return cyDate;
	}

	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}

	@Column(length = 20)
	public String getCyEndDate() {
		return cyEndDate;
	}

	public void setCyEndDate(String cyEndDate) {
		this.cyEndDate = cyEndDate;
	}

	@Column(length = 256)
	public String getCyMsg() {
		return cyMsg;
	}

	public void setCyMsg(String cyMsg) {
		this.cyMsg = cyMsg;
	}

	@Column(length = 20)
	public String getXdDate() {
		return xdDate;
	}

	public void setXdDate(String xdDate) {
		this.xdDate = xdDate;
	}

	@Column(length = 32)
	public String getXdUser() {
		return xdUser;
	}

	public void setXdUser(String xdUser) {
		this.xdUser = xdUser;
	}

	@Column(length = 32)
	public String getXdUserId() {
		return xdUserId;
	}

	public void setXdUserId(String xdUserId) {
		this.xdUserId = xdUserId;
	}

	@Column(length = 256)
	public String getXdMsg() {
		return xdMsg;
	}

	public void setXdMsg(String xdMsg) {
		this.xdMsg = xdMsg;
	}

	@Column(length = 20)
	public String getReciveDate() {
		return reciveDate;
	}

	public void setReciveDate(String reciveDate) {
		this.reciveDate = reciveDate;
	}

	@Column(length = 32)
	public String getReciveUserId() {
		return reciveUserId;
	}

	public void setReciveUserId(String reciveUserId) {
		this.reciveUserId = reciveUserId;
	}

	@Column(length = 32)
	public String getReciveUser() {
		return reciveUser;
	}

	public void setReciveUser(String reciveUser) {
		this.reciveUser = reciveUser;
	}

	@Column(length = 4)
	public String getPj() {
		return pj;
	}

	public void setPj(String pj) {
		this.pj = pj;
	}

	@Column(length = 128)
	public String getPointNames() {
		return pointNames;
	}

	public void setPointNames(String pointNames) {
		this.pointNames = pointNames;
	}

	@Column(length = 32)
	public String getCyUserId() {
		return cyUserId;
	}

	public void setCyUserId(String cyUserId) {
		this.cyUserId = cyUserId;
	}

	@Column(length = 32)
	public String getCyUserName() {
		return cyUserName;
	}

	public void setCyUserName(String cyUserName) {
		this.cyUserName = cyUserName;
	}

	@Column(length = 20)
	public String getCyTime() {
		return cyTime;
	}

	public void setCyTime(String cyTime) {
		this.cyTime = cyTime;
	}

	@Column(length = 64)
	public String getTaskSource() {
		return taskSource;
	}

	public void setTaskSource(String taskSource) {
		this.taskSource = taskSource;
	}

	@Column(length = 32)
	public String getFzId() {
		return fzId;
	}

	public void setFzId(String fzId) {
		this.fzId = fzId;
	}

	@Column(length = 32)
	public String getFzName() {
		return fzName;
	}

	public void setFzName(String fzName) {
		this.fzName = fzName;
	}

	@Column(length = 64)
	public String getDealRequest() {
		return dealRequest;
	}

	public void setDealRequest(String dealRequest) {
		this.dealRequest = dealRequest;
	}

	@Column(length = 64)
	public String getSaveRequest() {
		return saveRequest;
	}

	public void setSaveRequest(String saveRequest) {
		this.saveRequest = saveRequest;
	}

	@Column(length = 4)
	public String getJj() {
		return jj;
	}

	public void setJj(String jj) {
		this.jj = jj;
	}

	@Column(length = 128)
	public String getJcmd() {
		return jcmd;
	}

	public void setJcmd(String jcmd) {
		this.jcmd = jcmd;
	}
 
	@Column(length = 4)
	public String getZk() {
		return zk;
	}

	public void setZk(String zk) {
		this.zk = zk;
	}

	@Column(length = 1000)
	public String getJcct() {
		return jcct;
	}

	public void setJcct(String jcct) {
		this.jcct = jcct;
	}

	@Column(length = 4)
	public String getFb() {
		return fb;
	}

	public void setFb(String fb) {
		this.fb = fb;
	}

	@Column(length = 64)
	public String getFbUnit() {
		return fbUnit;
	}

	public void setFbUnit(String fbUnit) {
		this.fbUnit = fbUnit;
	}

	@Column(length = 320)
	public String getFbItemId() {
		return fbItemId;
	}

	public void setFbItemId(String fbItemId) {
		this.fbItemId = fbItemId;
	}

	@Column(length = 320)
	public String getFbItemName() {
		return fbItemName;
	}

	public void setFbItemName(String fbItemName) {
		this.fbItemName = fbItemName;
	}

	@Column(length = 32)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(length = 32)
	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	@Column(length = 32)
	public String getSampType() {
		return sampType;
	}

	public void setSampType(String sampType) {
		this.sampType = sampType;
	}

	@Column(length = 32)
	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	@Column(length = 32)
	public String getZbId() {
		return zbId;
	}

	public void setZbId(String zbId) {
		this.zbId = zbId;
	}

	@Column(length = 32)
	public String getZbName() {
		return zbName;
	}

	public void setZbName(String zbName) {
		this.zbName = zbName;
	}

	@Column(length = 20)
	public String getZbDate() {
		return zbDate;
	}

	public void setZbDate(String zbDate) {
		this.zbDate = zbDate;
	}
	@Column(length = 256)
	public String getZbMsg() {
		return zbMsg;
	}
	public void setZbMsg(String zbMsg) {
		this.zbMsg = zbMsg;
	}
	@Column(length = 1000)
	public String getCyStandIds() {
		return cyStandIds;
	}
	public void setCyStandIds(String cyStandIds) {
		this.cyStandIds = cyStandIds;
	}
	@Column(length = 1000)
	public String getCyStandNames() {
		return cyStandNames;
	}
	public void setCyStandNames(String cyStandNames) {
		this.cyStandNames = cyStandNames;
	}
	@Column(length = 2)
	public String getXcSt() {
		return xcSt;
	}
	public void setXcSt(String xcSt) {
		this.xcSt = xcSt;
	}
	@Column(length = 4)
	public String getTimTal() {
		return timTal;
	}
	public void setTimTal(String timTal) {
		this.timTal = timTal;
	}
	@Column(length = 4)
	public String getTimNum() {
		return timNum;
	}
	public void setTimNum(String timNum) {
		this.timNum = timNum;
	}
	@Column(length = 256)
	public String getReciveMsg() {
		return reciveMsg;
	}
	public void setReciveMsg(String reciveMsg) {
		this.reciveMsg = reciveMsg;
	}
	@Column(length = 1000)
	public String getCyAppIds() {
		return cyAppIds;
	}

	public void setCyAppIds(String cyAppIds) {
		this.cyAppIds = cyAppIds;
	}
	@Column(length = 1000)
	public String getCyAppNames() {
		return cyAppNames;
	}

	public void setCyAppNames(String cyAppNames) {
		this.cyAppNames = cyAppNames;
	}
	
}
