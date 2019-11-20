package cn.demi.bus.pjt.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.vo.ProgressVo;

public class ProjectVo extends Vo<ProjectVo> {
	
	
	private ProjectHtBaseVo pjtHtBaseVo; //合同基础vo
	
	private String no;//项目编号
	private String htNo;//合同编号
	//客户信息
	private CustVo custVo;
	private InvoiceVo invoiceVo;
	//样品信息
	private String sampType;//大类
	private String sampTypeId;//样品类别（多个）
	private String sampTypeName;//样品名称
	private String sampName;
	private String sampNum;
	private String sampStatus; //样品状态
	private String sampRQ; //样品容器
	private String sampTJ; //样品体积
	private String otherInfo; //其它信息
	private String sampBh; //样品编号
		
	//点位信息
	private String pointName;
	private String pointNum;
	//项目信息
	private String itemIds;
	private String itemNames;
	private String itemDesc; //检验项目相关说明 
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
	//技术评审   是/否
	private String jsps;
	//是否加急
	private String jj;
	private float htPrice;//合同费用
	private String htPrice_;//合同费用中文
	private float pay;//已收金额
	
	private String fb;//是否分包
	private float fbPrice;//分包费用
	private int fbSampNum;//分包样品数
	private String fbItemId;//分包项目
	private String fbItemName;
	
	private String userId;//立项人信息
	private String userName;
	private String sdate;//立项日期
	private String sdate_;//立项日期中文
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
	private String psId;//评审人
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
	private ProgressVo progressVo;//流程
	private String status;//状态
	private String isBack;//退回状态
	 
	private String htSt;//合同状态    未生成，未签订，已签订，执行中，已完成
	private String paySt;//收费状态    未收费，未完结，已完结
	private String htName;//合同名称
	private String htPath;//合同文件路径
	private String htTemp;//合同模板
	private String htTime;//签订日期
	
	private SurveyVo surveyVo;//现场踏勘
	
	private List<FilesVo> fileList;
	private List<SchemeVo> schemeList;
	private SchemeVo schemeVo;//自送样 下一步vo
	private List<SchemePointVo> pointList;
	private List<ProjectFbVo> fbList;//分包单位集合
	private List<CustRoomVo> roomList;//岗位集合
	private List<CustPointVo> potList;//采样点/检测对象
	private List<CustWorkVo> workList;//写实调查表集合
	private List<CustMaterialVo> materialList;//物料集合
	
	/**
	 * 物料单 
	 */
	private List<TypeExVo> materialTypeList;//物料类型集合(物料单)
	private String wlPath;//物料单文件路径
	private String wlName;//物料单文件名称
	private String dwPath;//点位表文件路径
	private String dwName;//点位表文件名称
	private String dcPath;//调查表文件路径
	private String dcName;//调查表文件名称
	
	/**
	 * 调查表
	 */
	private CustWorkVo custWorkVo;
	
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
	
	
	public String getOrgId1() {
		return orgId1;
	}
	public void setOrgId1(String orgId1) {
		this.orgId1 = orgId1;
	}
	public String getOrgName1() {
		return orgName1;
	}
	public void setOrgName1(String orgName1) {
		this.orgName1 = orgName1;
	}
	public String getOrgId2() {
		return orgId2;
	}
	public void setOrgId2(String orgId2) {
		this.orgId2 = orgId2;
	}
	public String getOrgName2() {
		return orgName2;
	}
	public void setOrgName2(String orgName2) {
		this.orgName2 = orgName2;
	}
	public String getOrgId3() {
		return orgId3;
	}
	public void setOrgId3(String orgId3) {
		this.orgId3 = orgId3;
	}
	public String getOrgName3() {
		return orgName3;
	}
	public void setOrgName3(String orgName3) {
		this.orgName3 = orgName3;
	}
	public String getOrgId4() {
		return orgId4;
	}
	public void setOrgId4(String orgId4) {
		this.orgId4 = orgId4;
	}
	public String getOrgName4() {
		return orgName4;
	}
	public void setOrgName4(String orgName4) {
		this.orgName4 = orgName4;
	}
	public String getOrgId5() {
		return orgId5;
	}
	public void setOrgId5(String orgId5) {
		this.orgId5 = orgId5;
	}
	public String getOrgName5() {
		return orgName5;
	}
	public void setOrgName5(String orgName5) {
		this.orgName5 = orgName5;
	}
	public String getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}
	public String getOrgNames() {
		return orgNames;
	}
	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}
	public CustWorkVo getCustWorkVo() {
		return custWorkVo;
	}
	public void setCustWorkVo(CustWorkVo custWorkVo) {
		this.custWorkVo = custWorkVo;
	}
	public List<TypeExVo> getMaterialTypeList() {
		return materialTypeList;
	}
	public void setMaterialTypeList(List<TypeExVo> materialTypeList) {
		this.materialTypeList = materialTypeList;
	}
	public String getWlPath() {
		return wlPath;
	}
	public void setWlPath(String wlPath) {
		this.wlPath = wlPath;
	}
	public String getWlName() {
		return wlName;
	}
	public void setWlName(String wlName) {
		this.wlName = wlName;
	}
	public String getDwPath() {
		return dwPath;
	}
	public void setDwPath(String dwPath) {
		this.dwPath = dwPath;
	}
	public String getDwName() {
		return dwName;
	}
	public void setDwName(String dwName) {
		this.dwName = dwName;
	}
	public String getDcPath() {
		return dcPath;
	}
	public void setDcPath(String dcPath) {
		this.dcPath = dcPath;
	}
	public String getDcName() {
		return dcName;
	}
	public void setDcName(String dcName) {
		this.dcName = dcName;
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public CustVo getCustVo() {
		return custVo;
	}
	public void setCustVo(CustVo custVo) {
		this.custVo = custVo;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public String getCycleUnit() {
		return cycleUnit;
	}
	public void setCycleUnit(String cycleUnit) {
		this.cycleUnit = cycleUnit;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public String getPcUnit() {
		return pcUnit;
	}
	public void setPcUnit(String pcUnit) {
		this.pcUnit = pcUnit;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public int getReportNum() {
		return reportNum;
	}
	public void setReportNum(int reportNum) {
		this.reportNum = reportNum;
	}
	public String getReportWay() {
		return reportWay;
	}
	public void setReportWay(String reportWay) {
		this.reportWay = reportWay;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getPj() {
		return pj;
	}
	public void setPj(String pj) {
		this.pj = pj;
	}
	public String getStandNames() {
		return standNames;
	}
	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}
	public String getStandIds() {
		return standIds;
	}
	public void setStandIds(String standIds) {
		this.standIds = standIds;
	}
	public String getFbzff() {
		return fbzff;
	}
	public void setFbzff(String fbzff) {
		this.fbzff = fbzff;
	}
	public String getZsy() {
		return zsy;
	}
	public void setZsy(String zsy) {
		this.zsy = zsy;
	}
	public String getXctk() {
		return xctk;
	}
	public void setXctk(String xctk) {
		this.xctk = xctk;
	}
	public String getJsps() {
		return jsps;
	}
	public void setJsps(String jsps) {
		this.jsps = jsps;
	}
	public String getJj() {
		return jj;
	}
	public void setJj(String jj) {
		this.jj = jj;
	}
	public float getHtPrice() {
		return htPrice;
	}
	public void setHtPrice(float htPrice) {
		this.htPrice = htPrice;
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
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getZpUserId() {
		return zpUserId;
	}
	public void setZpUserId(String zpUserId) {
		this.zpUserId = zpUserId;
	}
	public String getZpUserName() {
		return zpUserName;
	}
	public void setZpUserName(String zpUserName) {
		this.zpUserName = zpUserName;
	}
	public String getZpDate() {
		return zpDate;
	}
	public void setZpDate(String zpDate) {
		this.zpDate = zpDate;
	}
	public String getZpMsg() {
		return zpMsg;
	}
	public void setZpMsg(String zpMsg) {
		this.zpMsg = zpMsg;
	}
	public String getTkUserId() {
		return tkUserId;
	}
	public void setTkUserId(String tkUserId) {
		this.tkUserId = tkUserId;
	}
	public String getTkUserName() {
		return tkUserName;
	}
	public void setTkUserName(String tkUserName) {
		this.tkUserName = tkUserName;
	}
	public String getTkDate() {
		return tkDate;
	}
	public void setTkDate(String tkDate) {
		this.tkDate = tkDate;
	}
	public String getTkMsg() {
		return tkMsg;
	}
	public void setTkMsg(String tkMsg) {
		this.tkMsg = tkMsg;
	}
	public String getFaUserId() {
		return faUserId;
	}
	public void setFaUserId(String faUserId) {
		this.faUserId = faUserId;
	}
	public String getFaUserName() {
		return faUserName;
	}
	public void setFaUserName(String faUserName) {
		this.faUserName = faUserName;
	}
	public String getFaDate() {
		return faDate;
	}
	public void setFaDate(String faDate) {
		this.faDate = faDate;
	}
	public String getFaMsg() {
		return faMsg;
	}
	public void setFaMsg(String faMsg) {
		this.faMsg = faMsg;
	}
	public String getPsId() {
		return psId;
	}
	public void setPsId(String psId) {
		this.psId = psId;
	}
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
	public String getPsResult() {
		return psResult;
	}
	public void setPsResult(String psResult) {
		this.psResult = psResult;
	}
	public String getPsDate() {
		return psDate;
	}
	public void setPsDate(String psDate) {
		this.psDate = psDate;
	}
	public String getPsMsg() {
		return psMsg;
	}
	public void setPsMsg(String psMsg) {
		this.psMsg = psMsg;
	}
	public String getQdId() {
		return qdId;
	}
	public void setQdId(String qdId) {
		this.qdId = qdId;
	}
	public String getQdName() {
		return qdName;
	}
	public void setQdName(String qdName) {
		this.qdName = qdName;
	}
	public String getQdDate() {
		return qdDate;
	}
	public void setQdDate(String qdDate) {
		this.qdDate = qdDate;
	}
	public String getQdMsg() {
		return qdMsg;
	}
	public void setQdMsg(String qdMsg) {
		this.qdMsg = qdMsg;
	}
	public int getTaskTotal() {
		return taskTotal;
	}
	public void setTaskTotal(int taskTotal) {
		this.taskTotal = taskTotal;
	}
	public int getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}
	public ProgressVo getProgressVo() {
		return progressVo;
	}
	public void setProgressVo(ProgressVo progressVo) {
		this.progressVo = progressVo;
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
	public String getHtSt() {
		return htSt;
	}
	public void setHtSt(String htSt) {
		this.htSt = htSt;
	}
	public String getPaySt() {
		return paySt;
	}
	public void setPaySt(String paySt) {
		this.paySt = paySt;
	}
	public String getHtName() {
		return htName;
	}
	public void setHtName(String htName) {
		this.htName = htName;
	}
	public String getHtPath() {
		return htPath;
	}
	public void setHtPath(String htPath) {
		this.htPath = htPath;
	}
	public String getHtTemp() {
		return htTemp;
	}
	public void setHtTemp(String htTemp) {
		this.htTemp = htTemp;
	}
	public String getHtTime() {
		return htTime;
	}
	public void setHtTime(String htTime) {
		this.htTime = htTime;
	}
	public SurveyVo getSurveyVo() {
		return surveyVo;
	}
	public void setSurveyVo(SurveyVo surveyVo) {
		this.surveyVo = surveyVo;
	}
	public List<FilesVo> getFileList() {
		return fileList;
	}
	public void setFileList(List<FilesVo> fileList) {
		this.fileList = fileList;
	}
	public List<SchemeVo> getSchemeList() {
		return schemeList;
	}
	public void setSchemeList(List<SchemeVo> schemeList) {
		this.schemeList = schemeList;
	}
	public InvoiceVo getInvoiceVo() {
		return invoiceVo;
	}
	public void setInvoiceVo(InvoiceVo invoiceVo) {
		this.invoiceVo = invoiceVo;
	}
	public float getPay() {
		return pay;
	}
	public void setPay(float pay) {
		this.pay = pay;
	}
	public SchemeVo getSchemeVo() {
		return schemeVo;
	}
	public void setSchemeVo(SchemeVo schemeVo) {
		this.schemeVo = schemeVo;
	}
	public List<SchemePointVo> getPointList() {
		return pointList;
	}
	public void setPointList(List<SchemePointVo> pointList) {
		this.pointList = pointList;
	}
	public List<ProjectFbVo> getFbList() {
		return fbList;
	}
	public void setFbList(List<ProjectFbVo> fbList) {
		this.fbList = fbList;
	}
	public String getFb() {
		return fb;
	}
	public void setFb(String fb) {
		this.fb = fb;
	}
	public float getFbPrice() {
		return fbPrice;
	}
	public void setFbPrice(float fbPrice) {
		this.fbPrice = fbPrice;
	}
	public int getFbSampNum() {
		return fbSampNum;
	}
	public void setFbSampNum(int fbSampNum) {
		this.fbSampNum = fbSampNum;
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
	public String getSalerName() {
		return salerName;
	}
	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}
	public String getSalerId() {
		return salerId;
	}
	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}
	public String getHtPrice_() {
		return htPrice_;
	}
	public void setHtPrice_(String htPrice_) {
		this.htPrice_ = htPrice_;
	}
	public String getSdate_() {
		return sdate_;
	}
	public void setSdate_(String sdate_) {
		this.sdate_ = sdate_;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getPointNum() {
		return pointNum;
	}
	public void setPointNum(String pointNum) {
		this.pointNum = pointNum;
	}
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	public String getHtNo() {
		return htNo;
	}
	public void setHtNo(String htNo) {
		this.htNo = htNo;
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
	public String getSampNum() {
		return sampNum;
	}
	public void setSampNum(String sampNum) {
		this.sampNum = sampNum;
	}
	public String getPzId() {
		return pzId;
	}
	public void setPzId(String pzId) {
		this.pzId = pzId;
	}
	public String getPzName() {
		return pzName;
	}
	public void setPzName(String pzName) {
		this.pzName = pzName;
	}
	public String getPsCt() {
		return psCt;
	}
	public void setPsCt(String psCt) {
		this.psCt = psCt;
	}
	public ProjectHtBaseVo getPjtHtBaseVo() {
		return pjtHtBaseVo;
	}
	public void setPjtHtBaseVo(ProjectHtBaseVo pjtHtBaseVo) {
		this.pjtHtBaseVo = pjtHtBaseVo;
	}
	public String getSampStatus() {
		return sampStatus;
	}
	public void setSampStatus(String sampStatus) {
		this.sampStatus = sampStatus;
	}
	public String getSampRQ() {
		return sampRQ;
	}
	public void setSampRQ(String sampRQ) {
		this.sampRQ = sampRQ;
	}
	public String getSampTJ() {
		return sampTJ;
	}
	public void setSampTJ(String sampTJ) {
		this.sampTJ = sampTJ;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getSampBh() {
		return sampBh;
	}
	public void setSampBh(String sampBh) {
		this.sampBh = sampBh;
	}
	public List<CustRoomVo> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<CustRoomVo> roomList) {
		this.roomList = roomList;
	}
	public List<CustWorkVo> getWorkList() {
		return workList;
	}
	public void setWorkList(List<CustWorkVo> workList) {
		this.workList = workList;
	}
	public List<CustMaterialVo> getMaterialList() {
		return materialList;
	}
	public void setMaterialList(List<CustMaterialVo> materialList) {
		this.materialList = materialList;
	}
	public List<CustPointVo> getPotList() {
		return potList;
	}
	public void setPotList(List<CustPointVo> potList) {
		this.potList = potList;
	}
	public String getPsFilePath() {
		return psFilePath;
	}
	public void setPsFilePath(String psFilePath) {
		this.psFilePath = psFilePath;
	}
	public String getPsFileName() {
		return psFileName;
	}
	public void setPsFileName(String psFileName) {
		this.psFileName = psFileName;
	}
	public String getDqPsId() {
		return dqPsId;
	}
	public void setDqPsId(String dqPsId) {
		this.dqPsId = dqPsId;
	}
	public String getDqPsName() {
		return dqPsName;
	}
	public void setDqPsName(String dqPsName) {
		this.dqPsName = dqPsName;
	}
	
	
	
	
}

