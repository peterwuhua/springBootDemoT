package cn.demi.bus.pjt.po;
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
import cn.demi.bus.pg.po.Progress;
/**
 * 立项申请
 * @author QuJunLong
 */
@Entity(name = "bus_project")
@Table(name = "bus_project")
@Module(value = "bus.project")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project extends Po<Project>{
	
	public static final String ZQ_DAY="日";
	public static final String ZQ_MONTH="月";
	public static final String ZQ_QUARTER="季";
	public static final String ZQ_YEAR="年";
	
	public static final String PC_C="次";
	public static final String PC_M="次/月";
	public static final String PC_Y="次/年";

	public static final String WLD="物料单";
	public static final String DWB="点位表";
	public static final String DCB="调查表";
	
	private static final long serialVersionUID = 1L;
	
	public String[] PROPERTY_TO_MAP = {"id","sort","isDel","cust","xctk","sampTypeName","sampType","sampName","taskType","progress"
			,"rdate","finishDate","orgName","userName","sdate","status","htNo","no","lastUpdTime","zsy","pj","tkDate","htSt","paySt",
			"salerName","htPrice","pay","jj","zpUserName","zpDate","faDate","faUserName","isBack","psName","psDate","qdName","qdDate","tkUserName","wlPath","wlName","dwPath","dwName","dcPath","dcName","invoice",
			"orgId1","orgName1","orgId2","orgName2","orgId3","orgName3","orgId4","orgName4","orgId5","orgName5","orgIds","orgNames","dqPsId","dqPsName"};
	
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","orgId","orgName","no","progress","status","taskTotal","taskNum","htSt","paySt"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	private String no;//项目编号
	private String htNo;//合同编号
	//客户信息
	private Cust cust;
	private Invoice invoice;
	//样品信息
	private String sampType;//样品大类
	private String sampTypeId;//样品类别（多个）
	private String sampTypeName;//样品名称
	private String sampName;
	private String sampNum;
	//点位信息
	private String pointName;
	private String pointNum;
	//项目信息
	private String itemIds;
	private String itemNames;
	//任务信息
	private String taskType;//监测类别
	private int cycle;//每次项目周期
	private String cycleUnit;//项目周期单位
	private int pc;//项目频次
	private String pcUnit;//项目频次单位
	private String rdate;//立项日期
	private String finishDate;//拟完成日期
	private int reportNum;//报告份数
	private String reportWay;//取报告方式
	private String info;//委托方提供资料
	
	private String pj;//评价   是/否
	private String standNames;//评价依据
	private String standIds;//评价依据
	//是否同意使用非标准方法 是/否
	private String fbzff;
	//样品自送   是/否
	private String zsy;
	//现场踏勘   是/否
	private String xctk;
	//合同评审   是/否
	private String jsps;
	//是否加急
	private String jj;
	private float htPrice;//合同费用
	private float pay;//已收金额
	

	
	private String fb;//是否分包
	private float fbPrice;//分包费用
	private int fbSampNum;//分包样品数
	private String fbItemId;//分包项目
	private String fbItemName;
	
	private String userId;//立项人信息
	private String userName;
	private String sdate;//立项日期
	private String orgId;//部门
	private String orgName;
	private String remark;
	//任务指派信息
	private String zpUserId;
	private String zpUserName;
	private String zpDate;
	private String zpMsg;
	//踏勘人信息
	private String tkUserId;
	private String tkUserName;
	private String tkDate;
	private String tkMsg;
	//方案编制
	private String faUserId;//方案 制定人
	private String faUserName;
	private String faDate;//制定日期
	private String faMsg;
	//合同评审
	private String psId;
	private String psName;
	private String dqPsId;//当前评审人
	private String dqPsName;
	private String pzId;//批准人
	private String pzName;
	private String psCt;//评审内容
	private String psResult;//技术评审结果
	private String psDate;
	private String psMsg;//技术评审内容
	private String psFileName;//评审记录文件名称
	private String psFilePath;//评审记录文件路径
	//合同签订 销售
	private String qdId;
	private String qdName; 
	private String qdDate;
	private String qdMsg;
	
	private String salerName;//销售
	private String salerId;
	//流程信息
	private int taskTotal;//任务总数
	private int taskNum;//当前进度数
	
	private Progress progress;//流程
	private String status;//状态
	private String isBack;//退回状态
	 
	private String htSt;//合同状态    未生成，未签订，已签订，执行中，已完成
	private String paySt;//收费状态    未收费，未完结，已完结
	private String htName;//合同名称
	private String htPath;//合同文件路径
	private String htTemp;//合同模板
	private String htTime;//签订日期
	
	private String wlPath;//物料单文件路径
	private String wlName;//物料单文件名称
	private String dwPath;//点位表文件路径
	private String dwName;//点位表文件名称
	private String dcPath;//调查表文件路径
	private String dcName;//调查表文件名称
	
	/**
	 * 合同评审
	 */
	private String orgId1;
	private String orgName1;
	private String orgId2;
	private String orgName2;
	private String orgId3;
	private String orgName3;
	private String orgId4;
	private String orgName4;
	private String orgId5;
	private String orgName5;
	
	private String orgIds;
	private String orgNames;
	
	
	
	@Column(length = 32)
	public String getOrgId1() {
		return orgId1;
	}
	public void setOrgId1(String orgId1) {
		this.orgId1 = orgId1;
	}
	@Column(length = 32)
	public String getOrgName1() {
		return orgName1;
	}
	public void setOrgName1(String orgName1) {
		this.orgName1 = orgName1;
	}
	@Column(length = 32)
	public String getOrgId2() {
		return orgId2;
	}
	public void setOrgId2(String orgId2) {
		this.orgId2 = orgId2;
	}
	@Column(length = 32)
	public String getOrgName2() {
		return orgName2;
	}
	public void setOrgName2(String orgName2) {
		this.orgName2 = orgName2;
	}
	@Column(length = 32)
	public String getOrgId3() {
		return orgId3;
	}
	public void setOrgId3(String orgId3) {
		this.orgId3 = orgId3;
	}
	@Column(length = 32)
	public String getOrgName3() {
		return orgName3;
	}
	public void setOrgName3(String orgName3) {
		this.orgName3 = orgName3;
	}
	@Column(length = 32)
	public String getOrgId4() {
		return orgId4;
	}
	public void setOrgId4(String orgId4) {
		this.orgId4 = orgId4;
	}
	@Column(length = 32)
	public String getOrgName4() {
		return orgName4;
	}
	public void setOrgName4(String orgName4) {
		this.orgName4 = orgName4;
	}
	@Column(length = 32)
	public String getOrgId5() {
		return orgId5;
	}
	public void setOrgId5(String orgId5) {
		this.orgId5 = orgId5;
	}
	@Column(length = 32)
	public String getOrgName5() {
		return orgName5;
	}
	public void setOrgName5(String orgName5) {
		this.orgName5 = orgName5;
	}
	@Column(length = 256)
	public String getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}
	@Column(length = 256)
	public String getOrgNames() {
		return orgNames;
	}
	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}
	@Column(length = 128)
	public String getDwPath() {
		return dwPath;
	}
	public void setDwPath(String dwPath) {
		this.dwPath = dwPath;
	}
	@Column(length = 64)
	public String getDwName() {
		return dwName;
	}
	public void setDwName(String dwName) {
		this.dwName = dwName;
	}
	@Column(length = 128)
	public String getDcPath() {
		return dcPath;
	}
	public void setDcPath(String dcPath) {
		this.dcPath = dcPath;
	}
	@Column(length = 64)
	public String getDcName() {
		return dcName;
	}
	public void setDcName(String dcName) {
		this.dcName = dcName;
	}
	@Column(length = 128)
	public String getWlPath() {
		return wlPath;
	}
	public void setWlPath(String wlPath) {
		this.wlPath = wlPath;
	}
	@Column(length = 64)
	public String getWlName() {
		return wlName;
	}
	public void setWlName(String wlName) {
		this.wlName = wlName;
	}
	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}
	public void setCust(Cust cust) {
		this.cust = cust;
	}
	@ManyToOne
	@JoinColumn(name = "invoice_id")
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	@Column(length = 16)
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType= sampType;
	}
	@Column(length = 16)
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	@Column(length = 8)
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	@Column(length = 8)
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	@Column(length = 20)
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	@Column(length = 20)
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	@Column(length = 4)
	public int getReportNum() {
		return reportNum;
	}
	public void setReportNum(int reportNum) {
		this.reportNum = reportNum;
	}
	@Column(length = 64)
	public String getReportWay() {
		return reportWay;
	}
	public void setReportWay(String reportWay) {
		this.reportWay = reportWay;
	}
	@Column(length = 64)
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	@Column(length = 8)
	public String getFbzff() {
		return fbzff;
	}
	public void setFbzff(String fbzff) {
		this.fbzff = fbzff;
	}
	@Column(length = 8)
	public String getPj() {
		return pj;
	}
	public void setPj(String pj) {
		this.pj = pj;
	}
	@Column(length = 8)
	public String getZsy() {
		return zsy;
	}
	public void setZsy(String zsy) {
		this.zsy = zsy;
	}
	@Column(length = 8)
	public String getXctk() {
		return xctk;
	}
	public void setXctk(String xctk) {
		this.xctk = xctk;
	}
	@Column(length = 8)
	public String getJsps() {
		return jsps;
	}
	public void setJsps(String jsps) {
		this.jsps = jsps;
	}
	@Column(length = 320)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 320)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 20)
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
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
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
 
	@Column(length = 16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(length = 32)
	public String getCycleUnit() {
		return cycleUnit;
	}
	public void setCycleUnit(String cycleUnit) {
		this.cycleUnit = cycleUnit;
	}
	@Column(length = 32)
	public String getPcUnit() {
		return pcUnit;
	}
	public void setPcUnit(String pcUnit) {
		this.pcUnit = pcUnit;
	}
 
	@Column(length = 32)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length = 8)
	public String getJj() {
		return jj;
	}
	public void setJj(String jj) {
		this.jj = jj;
	}
	@Column(length = 4)
	public int getTaskTotal() {
		return taskTotal;
	}
	public void setTaskTotal(int taskTotal) {
		this.taskTotal = taskTotal;
	}
	@Column(length = 4)
	public int getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}
	@Column(length = 320)
	public String getTkUserId() {
		return tkUserId;
	}
	public void setTkUserId(String tkUserId) {
		this.tkUserId = tkUserId;
	}
	@Column(length = 320)
	public String getTkUserName() {
		return tkUserName;
	}
	public void setTkUserName(String tkUserName) {
		this.tkUserName = tkUserName;
	}
	@Column(length = 20)
	public String getTkDate() {
		return tkDate;
	}
	public void setTkDate(String tkDate) {
		this.tkDate = tkDate;
	}
	@Column(length = 1000)
	public String getStandNames() {
		return standNames;
	}
	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}
	@Column(length = 1000)
	public String getStandIds() {
		return standIds;
	}
	public void setStandIds(String standIds) {
		this.standIds = standIds;
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
	@Column(length = 128)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length = 11)
	public float getPaye() {
		return pay;
	}
	public void setPaye(float pay) {
		this.pay = pay;
	}
	@Column(length = 64)
	public String getHtName() {
		return htName;
	}
	public void setHtName(String htName) {
		this.htName = htName;
	}
	@Column(length = 128)
	public String getHtPath() {
		return htPath;
	}
	public void setHtPath(String htPath) {
		this.htPath = htPath;
	}
	@Column(length = 64)
	public String getHtTemp() {
		return htTemp;
	}
	public void setHtTemp(String htTemp) {
		this.htTemp = htTemp;
	}
	@ManyToOne
	@JoinColumn(name = "progress_id")
	public Progress getProgress() {
		return progress;
	}
	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	@Column(length = 16)
	public String getHtSt() {
		return htSt;
	}
	public void setHtSt(String htSt) {
		this.htSt = htSt;
	}
	@Column(length = 16)
	public String getPaySt() {
		return paySt;
	}
	public void setPaySt(String paySt) {
		this.paySt = paySt;
	}
	@Column(length = 16)
	public float getHtPrice() {
		return htPrice;
	}
	public void setHtPrice(float htPrice) {
		this.htPrice = htPrice;
	}
	@Column(length = 20)
	public String getHtTime() {
		return htTime;
	}
	public void setHtTime(String htTime) {
		this.htTime = htTime;
	}
	@Column(length = 32)
	public String getZpUserId() {
		return zpUserId;
	}
	public void setZpUserId(String zpUserId) {
		this.zpUserId = zpUserId;
	}
	@Column(length = 32)
	public String getZpUserName() {
		return zpUserName;
	}
	public void setZpUserName(String zpUserName) {
		this.zpUserName = zpUserName;
	}
	@Column(length = 20)
	public String getZpDate() {
		return zpDate;
	}
	public void setZpDate(String zpDate) {
		this.zpDate = zpDate;
	}
	@Column(length = 128)
	public String getZpMsg() {
		return zpMsg;
	}
	public void setZpMsg(String zpMsg) {
		this.zpMsg = zpMsg;
	}
	@Column(length = 128)
	public String getTkMsg() {
		return tkMsg;
	}
	public void setTkMsg(String tkMsg) {
		this.tkMsg = tkMsg;
	}
	@Column(length = 32)
	public String getFaUserId() {
		return faUserId;
	}
	public void setFaUserId(String faUserId) {
		this.faUserId = faUserId;
	}
	@Column(length = 16)
	public String getFaUserName() {
		return faUserName;
	}
	public void setFaUserName(String faUserName) {
		this.faUserName = faUserName;
	}
	@Column(length = 20)
	public String getFaDate() {
		return faDate;
	}
	public void setFaDate(String faDate) {
		this.faDate = faDate;
	}
	@Column(length = 128)
	public String getFaMsg() {
		return faMsg;
	}
	public void setFaMsg(String faMsg) {
		this.faMsg = faMsg;
	}
	@Column(length = 320)
	public String getPsId() {
		return psId;
	}
	public void setPsId(String psId) {
		this.psId = psId;
	}
	@Column(length = 320)
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
	@Column(length = 1000)
	public String getPsResult() {
		return psResult;
	}
	public void setPsResult(String psResult) {
		this.psResult = psResult;
	}
	@Column(length = 20)
	public String getPsDate() {
		return psDate;
	}
	public void setPsDate(String psDate) {
		this.psDate = psDate;
	}
	@Column(length = 128)
	public String getPsMsg() {
		return psMsg;
	}
	public void setPsMsg(String psMsg) {
		this.psMsg = psMsg;
	}
	@Column(length = 32)
	public String getQdId() {
		return qdId;
	}
	public void setQdId(String qdId) {
		this.qdId = qdId;
	}
	@Column(length = 32)
	public String getQdName() {
		return qdName;
	}
	public void setQdName(String qdName) {
		this.qdName = qdName;
	}
	@Column(length = 20)
	public String getQdDate() {
		return qdDate;
	}
	public void setQdDate(String qdDate) {
		this.qdDate = qdDate;
	}
	@Column(length = 128)
	public String getQdMsg() {
		return qdMsg;
	}
	public void setQdMsg(String qdMsg) {
		this.qdMsg = qdMsg;
	}
	@Column(length = 2)
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	@Column(length = 8)
	public String getFb() {
		return fb;
	}
	public void setFb(String fb) {
		this.fb = fb;
	}
	@Column(length = 11)
	public float getFbPrice() {
		return fbPrice;
	}
	public void setFbPrice(float fbPrice) {
		this.fbPrice = fbPrice;
	}
	@Column(length = 11)
	public int getFbSampNum() {
		return fbSampNum;
	}
	public void setFbSampNum(int fbSampNum) {
		this.fbSampNum = fbSampNum;
	}
	@Column(length = 1000)
	public String getFbItemId() {
		return fbItemId;
	}
	public void setFbItemId(String fbItemId) {
		this.fbItemId = fbItemId;
	}
	@Column(length = 1000)
	public String getFbItemName() {
		return fbItemName;
	}
	public void setFbItemName(String fbItemName) {
		this.fbItemName = fbItemName;
	}
	public float getPay() {
		return pay;
	}
	public void setPay(float pay) {
		this.pay = pay;
	}
	@Column(length = 32)
	public String getSalerName() {
		return salerName;
	}
	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}
	@Column(length = 32)
	public String getSalerId() {
		return salerId;
	}
	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}
	@Column(length = 1000)
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	@Column(length = 2)
	public String getPointNum() {
		return pointNum;
	}
	public void setPointNum(String pointNum) {
		this.pointNum = pointNum;
	}
	@Column(length = 1000)
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	@Column(length = 1000)
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	@Column(length = 32)
	public String getHtNo() {
		return htNo;
	}
	public void setHtNo(String htNo) {
		this.htNo = htNo;
	}
	@Column(length = 16)
	public String getSampNum() {
		return sampNum;
	}
	public void setSampNum(String sampNum) {
		this.sampNum = sampNum;
	}
	@Column(length = 32)
	public String getPzId() {
		return pzId;
	}
	public void setPzId(String pzId) {
		this.pzId = pzId;
	}
	@Column(length = 32)
	public String getPzName() {
		return pzName;
	}
	public void setPzName(String pzName) {
		this.pzName = pzName;
	}
	@Column(length = 256)
	public String getPsCt() {
		return psCt;
	}
	public void setPsCt(String psCt) {
		this.psCt = psCt;
	}
	@Column(length = 64)	
	public String getPsFileName() {
		return psFileName;
	}
	public void setPsFileName(String psFileName) {
		this.psFileName = psFileName;
	}
	@Column(length = 256)
	public String getPsFilePath() {
		return psFilePath;
	}
	public void setPsFilePath(String psFilePath) {
		this.psFilePath = psFilePath;
	}
	@Column(length = 32)
	public String getDqPsId() {
		return dqPsId;
	}
	public void setDqPsId(String dqPsId) {
		this.dqPsId = dqPsId;
	}
	@Column(length = 32)
	public String getDqPsName() {
		return dqPsName;
	}
	public void setDqPsName(String dqPsName) {
		this.dqPsName = dqPsName;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Project.class, true, ActionType.JSP);
	}
}
