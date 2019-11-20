package cn.demi.bus.sample.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.pjt.vo.CustVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.vo.TestResultVo;

public class SamplingVo extends Vo<SamplingVo> {
	
	private TaskVo taskVo;// 任务
	private CustVo custVo;//客户信息
	private TaskPointVo pointVo;// 任务点信息
	private SampCydVo cydVo;//采样单
	private String p;//批次
	private String code;//项目代码
	private SampRecordVo recordVo;//
	private String sampTypeId;//样品名称
	private String sampTypeName;//样品名称
	private String sampType;//样品大类
	private String sampName;//样品名称
	private String sampCode;//编号
	private String pointName;//点位名称
	private String pointCode;//点位代码
	private String dealRequest;//样品处理要求
	private String saveRequest;//样品保存条件
	private String cyType;//采样方式
	private int cyHours;//采样时长
	private String workHours;//接触时间 h/d
	private String workPc;//接触频次
	private String xz;// 样品性状
	private String tjj;//添加剂
	private String sampStatus;//样品状态
	private String num;//数量、采样体积(气)  距声源距离(声)
	private String tj;//标准体积
	private String fcType;//粉尘种类  测量部位    体力劳动强度
	private String v1;//采样前重量
	private String v2;//采样后
	private String itemIds;//测试项目
	private String itemNames;
	private String cyDate;//采样日期
	private String cyTime;//采样时间
	private String cyEndTime;//采样时间
	private float saveHour;//保存时间
	private float pyHour;//培养时间
	
	private String containerId;//采样容器
	private String container;
	private String dealUser;//余样处理人
	private String dealDate;//处理日期
	private String dealRemark;//处理备注
	
	//部门
	private String orgId;
	private String orgName;
	private String deptId;
	private String deptName;
	private String fxDeptId;
	private String fxDeptName;
	private String reciveDate;
	private String reciveUserId;
	private String reciveUser;
	
	private String remark;//备注
	private String type;//样品类型 普通样0，虚拟样-1
	private String zkType;//样品类型 普通样 0，质控样 1 2 3 4
	private String ccfl;//是否采测分离
	private String saveAddress;//存放位置
	private String ly;//是否留样
	private String lyDate;//留样时间
	private String bcqx;//保存期限
	private String lyReason;//留样原因
	
	private String fileName;//委托单
	private String filePath;
    private String status;//样品状态：已接收、已出库、已归还、已处理
	
    private String cyTimeStr;//采样时间 中文 年月日时分-年月日时分
    private List<TestResultVo> itList;
	private List<SampContainerVo>  containerList;//采样容器
	
	private String zy; //(昼/夜)
	private String cyDatestr;//采样时间（xx月xx日xx时）
	
	
	
	public String getCyDatestr() {
		return cyDatestr;
	}
	public void setCyDatestr(String cyDatestr) {
		this.cyDatestr = cyDatestr;
	}
	public String getZy() {
		return zy;
	}
	public void setZy(String zy) {
		this.zy = zy;
	}
	public String getWorkPc() {
		return workPc;
	}
	public void setWorkPc(String workPc) {
		this.workPc = workPc;
	}
	public TaskVo getTaskVo() {
		return taskVo;
	}
	public void setTaskVo(TaskVo taskVo) {
		this.taskVo = taskVo;
	}
	public CustVo getCustVo() {
		return custVo;
	}
	public void setCustVo(CustVo custVo) {
		this.custVo = custVo;
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
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getPointCode() {
		return pointCode;
	}
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
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
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
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
	public String getSampCode() {
		return sampCode;
	}
	public void setSampCode(String sampCode) {
		this.sampCode = sampCode;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getDealUser() {
		return dealUser;
	}
	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public String getDealRemark() {
		return dealRemark;
	}
	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
	}
//	public String getUseId() {
//		return useId;
//	}
//	public void setUseId(String useId) {
//		this.useId = useId;
//	}
//	public String getUseName() {
//		return useName;
//	}
//	public void setUseName(String useName) {
//		this.useName = useName;
//	}
//	public String getUseDate() {
//		return useDate;
//	}
//	public void setUseDate(String useDate) {
//		this.useDate = useDate;
//	}
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSaveAddress() {
		return saveAddress;
	}
	public void setSaveAddress(String saveAddress) {
		this.saveAddress = saveAddress;
	}
	public String getLy() {
		return ly;
	}
	public void setLy(String ly) {
		this.ly = ly;
	}
	public String getLyDate() {
		return lyDate;
	}
	public void setLyDate(String lyDate) {
		this.lyDate = lyDate;
	}
	public String getBcqx() {
		return bcqx;
	}
	public void setBcqx(String bcqx) {
		this.bcqx = bcqx;
	}
	public String getLyReason() {
		return lyReason;
	}
	public void setLyReason(String lyReason) {
		this.lyReason = lyReason;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TestResultVo> getItList() {
		return itList;
	}
	public void setItList(List<TestResultVo> itList) {
		this.itList = itList;
	}
	public String getCyTime() {
		return cyTime;
	}
	public void setCyTime(String cyTime) {
		this.cyTime = cyTime;
	}
	public TaskPointVo getPointVo() {
		return pointVo;
	}
	public void setPointVo(TaskPointVo pointVo) {
		this.pointVo = pointVo;
	}
	public String getContainerId() {
		return containerId;
	}
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public String getTjj() {
		return tjj;
	}
	public void setTjj(String tjj) {
		this.tjj = tjj;
	}
	public SampRecordVo getRecordVo() {
		return recordVo;
	}
	public void setRecordVo(SampRecordVo recordVo) {
		this.recordVo = recordVo;
	}
	public String getCyEndTime() {
		return cyEndTime;
	}
	public void setCyEndTime(String cyEndTime) {
		this.cyEndTime = cyEndTime;
	}
	public String getCcfl() {
		return ccfl;
	}
	public void setCcfl(String ccfl) {
		this.ccfl = ccfl;
	}
	public List<SampContainerVo> getContainerList() {
		return containerList;
	}
	public void setContainerList(List<SampContainerVo> containerList) {
		this.containerList = containerList;
	}
	public float getSaveHour() {
		return saveHour;
	}
	public void setSaveHour(float saveHour) {
		this.saveHour = saveHour;
	}
	public float getPyHour() {
		return pyHour;
	}
	public void setPyHour(float pyHour) {
		this.pyHour = pyHour;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFxDeptId() {
		return fxDeptId;
	}
	public void setFxDeptId(String fxDeptId) {
		this.fxDeptId = fxDeptId;
	}
	public String getFxDeptName() {
		return fxDeptName;
	}
	public void setFxDeptName(String fxDeptName) {
		this.fxDeptName = fxDeptName;
	}
	public String getV1() {
		return v1;
	}
	public void setV1(String v1) {
		this.v1 = v1;
	}
	public String getV2() {
		return v2;
	}
	public void setV2(String v2) {
		this.v2 = v2;
	}
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}
	public String getCyTimeStr() {
		return cyTimeStr;
	}
	public void setCyTimeStr(String cyTimeStr) {
		this.cyTimeStr = cyTimeStr;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	public String getSampStatus() {
		return sampStatus;
	}
	public void setSampStatus(String sampStatus) {
		this.sampStatus = sampStatus;
	}
	public String getZkType() {
		return zkType;
	}
	public void setZkType(String zkType) {
		this.zkType = zkType;
	}
	public String getCyType() {
		return cyType;
	}
	public void setCyType(String cyType) {
		this.cyType = cyType;
	}
	public int getCyHours() {
		return cyHours;
	}
	public void setCyHours(int cyHours) {
		this.cyHours = cyHours;
	}
	public SampCydVo getCydVo() {
		return cydVo;
	}
	public void setCydVo(SampCydVo cydVo) {
		this.cydVo = cydVo;
	}
	public String getTj() {
		return tj;
	}
	public void setTj(String tj) {
		this.tj = tj;
	}
	public String getFcType() {
		return fcType;
	}
	public void setFcType(String fcType) {
		this.fcType = fcType;
	}
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	
}

