package cn.demi.bus.sample.po;

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
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;

/**
 * 样品信息
 * @author QuJunLong
 */
@Entity(name = "bus_sampling")
@Table(name = "bus_sampling")
@Module(value = "bus.sampling")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sampling extends Po<Sampling>{
	private static final long serialVersionUID = 1L;

	public static final String SAMP_TYPE_XN="-1";//虚拟样
	public static final String SAMP_TYPE_PT="0";
	public static final String SAMP_TYPE_PX_X="1";//现场平行样
	public static final String SAMP_TYPE_PX_S="2";//室内平行样
	public static final String SAMP_TYPE_KB="3";//全程序空白样
	public static final String SAMP_TYPE_JB="4";//加标回收样
	
	public static final String ST_00="SAMP_00";//库存中
	public static final String ST_10="SAMP_10";//已留样
	public static final String ST_30="SAMP_30";//已处理
	
	
	public static final String ST_20="SAMP_20";//待出库
	public static final String ST_21="SAMP_21";//已出库
	public static final String ST_22="SAMP_22";//待归还
	public static final String ST_23="SAMP_23";//已归还
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","task","cust","sampName","sampCode","reciveUser","reciveDate"
			,"lyDate","bcqx","compDate","assignDate","testTime","checkTime","isBack","deptName","sampTypeName"
			,"saveAddress","useName","useDate","dealUser","dealDate","dealRemark","dealRequest","pointName","pointCode","status","ldqd","workPc"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private Task task;// 任务
	private Cust cust;//客户信息
	private TaskPoint point;//监测点位
	private SampCyd cyd;//采样单
	private String p;//批次
	private String code;//项目代码
	private SampRecord record;//分析记录
	private String sampTypeId;//样品类别
	private String sampTypeName;
	private String sampType;//样品大类
	private String sampName;//样品名称
	private String pointName;//点位名称
	private String pointCode;//点位代码
	private String dealRequest;//样品处理要求
	private String saveRequest;//样品保存条件
	private String cyType;//采样方式
	private int cyHours;//采样时长
	private String xz;// 样品性状
	private String tjj;//添加剂   时长(气)
	private String sampStatus;//样品状态
	private String num;//数量、  采样体积(气)  距声源距离(声)
	private String tj;//标准体积
	private String workHours;//接触时间 h/d
	private String workPc;//接触频次
	private String fcType;//粉尘种类  测量部位
	private String v1;//采样前重量
	private String v2;//采样后
	private String itemIds;//测试项目
	private String itemNames;
	private String cyDate;//采样日期
	private String cyTime;//采样时间
	private String cyEndTime;//采样时间
	private float saveHour;//保存时间
	private float pyHour;//培养时间
	private String sampCode;
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
	private String type;//样品类型 普通样 0，虚拟样-1
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

    @Column(length =32)
    public String getWorkPc() {
		return workPc;
	}
	public void setWorkPc(String workPc) {
		this.workPc = workPc;
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
	@JoinColumn(name = "cyd_id")
	public SampCyd getCyd() {
		return cyd;
	}
	public void setCyd(SampCyd cyd) {
		this.cyd = cyd;
	}
	@ManyToOne
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	@ManyToOne
	@JoinColumn(name = "point_id")
	public TaskPoint getPoint() {
		return point;
	}
	public void setPoint(TaskPoint point) {
		this.point = point;
	}
	@ManyToOne
	@JoinColumn(name = "record_id")
	public SampRecord getRecord() {
		return record;
	}
	public void setRecord(SampRecord record) {
		this.record = record;
	}
	@Column(length = 32)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length = 32)
	public String getSampCode() {
		return sampCode;
	}
	public void setSampCode(String sampCode) {
		this.sampCode = sampCode;
	}
	@Column(length = 32)
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
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
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 8)
	public String getLy() {
		return ly;
	}
	public void setLy(String ly) {
		this.ly = ly;
	}
	@Column(length =128)
	public String getSaveAddress() {
		return saveAddress;
	}
	public void setSaveAddress(String saveAddress) {
		this.saveAddress = saveAddress;
	}
 
	@Column(length = 20)
	public String getLyDate() {
		return lyDate;
	}
	public void setLyDate(String lyDate) {
		this.lyDate = lyDate;
	}
	@Column(length = 20)
	public String getBcqx() {
		return bcqx;
	}
	public void setBcqx(String bcqx) {
		this.bcqx = bcqx;
	}
	@Column(length = 128)
	public String getLyReason() {
		return lyReason;
	}
	public void setLyReason(String lyReason) {
		this.lyReason = lyReason;
	}
	 
	@Column(length = 64)
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@Column(length = 64)
	public String getDealRequest() {
		return dealRequest;
	}
	public void setDealRequest(String dealRequest) {
		this.dealRequest = dealRequest;
	}
	@Column(length = 32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 64)
	public String getSaveRequest() {
		return saveRequest;
	}
	public void setSaveRequest(String saveRequest) {
		this.saveRequest = saveRequest;
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
//	@Column(length = 32)
//	public String getUseId() {
//		return useId;
//	}
//	public void setUseId(String useId) {
//		this.useId = useId;
//	}
//	@Column(length = 32)
//	public String getUseName() {
//		return useName;
//	}
//	public void setUseName(String useName) {
//		this.useName = useName;
//	}
//	@Column(length = 20)
//	public String getUseDate() {
//		return useDate;
//	}
//	public void setUseDate(String useDate) {
//		this.useDate = useDate;
//	}
	@Column(length = 32)
	public String getDealUser() {
		return dealUser;
	}
	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}
	@Column(length = 20)
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	@Lob
	public String getDealRemark() {
		return dealRemark;
	}
	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
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
	@Column(length = 64)
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	@Column(length = 32)
	public String getPointCode() {
		return pointCode;
	}
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}
	@Column(length = 20)
	public String getCyTime() {
		return cyTime;
	}
	public void setCyTime(String cyTime) {
		this.cyTime = cyTime;
	}
	@Column(length = 320)
	public String getContainerId() {
		return containerId;
	}
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	@Column(length = 320)
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	@Column(length = 16)
	public String getTjj() {
		return tjj;
	}
	public void setTjj(String tjj) {
		this.tjj = tjj;
	}
	@Column(length = 20)
	public String getCyEndTime() {
		return cyEndTime;
	}
	public void setCyEndTime(String cyEndTime) {
		this.cyEndTime = cyEndTime;
	}
	@Column(length = 4)
	public String getCcfl() {
		return ccfl;
	}
	public void setCcfl(String ccfl) {
		this.ccfl = ccfl;
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
	@Column(length = 16)
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	@Column(length = 16)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length = 32)
	public String getFxDeptId() {
		return fxDeptId;
	}
	public void setFxDeptId(String fxDeptId) {
		this.fxDeptId = fxDeptId;
	}
	@Column(length = 32)
	public String getFxDeptName() {
		return fxDeptName;
	}
	public void setFxDeptName(String fxDeptName) {
		this.fxDeptName = fxDeptName;
	}
	@Column(length = 32)
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	@Column(length = 64)
	public String getSampStatus() {
		return sampStatus;
	}
	public void setSampStatus(String sampStatus) {
		this.sampStatus = sampStatus;
	}
	@Column(length = 16)
	public String getZkType() {
		return zkType;
	}
	public void setZkType(String zkType) {
		this.zkType = zkType;
	}
	@Column(length = 2)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 32)
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
	@Column(length = 32)
	public String getTj() {
		return tj;
	}
	public void setTj(String tj) {
		this.tj = tj;
	}
	@Column(length = 32)
	public String getFcType() {
		return fcType;
	}
	public void setFcType(String fcType) {
		this.fcType = fcType;
	}
	@Column(length = 32)
	public String getV1() {
		return v1;
	}
	public void setV1(String v1) {
		this.v1 = v1;
	}
	@Column(length = 32)
	public String getV2() {
		return v2;
	}
	public void setV2(String v2) {
		this.v2 = v2;
	}
	@Column(length = 32)
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	@Column(length = 20)
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}
	
//	public static void main(String[] args) {
//		CreateCodeUtils.autoCreateCode(Sampling.class, true, ActionType.JSP);
//	}
}
