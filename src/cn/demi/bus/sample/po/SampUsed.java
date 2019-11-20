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
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.task.po.Task;

/**
 * 样品使用信息
 * @author QuJunLong
 */
@Entity(name = "bus_samp_used")
@Table(name = "bus_samp_used")
@Module(value = "bus.sampUsed")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SampUsed extends Po<SampUsed>{
	
	private static final long serialVersionUID = 7747521842415998473L;
	public static final String TYPE_OUT="out";
	public static final String TYPE_IN="in";
  
	public String[] PROPERTY_TO_MAP = {"id","sort","task","cust","sampling","sampName","sampCode","deptName","sampTypeName"
			,"useName","num","useDate","pointName","pointCode","status","remark","content"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private Task task;// 任务
	private Cust cust;//客户信息
	private Sampling sampling;//样品
	private String sampTypeId;//样品名称
	private String sampTypeName;//样品名称
	private String sampId;//样品名称
	private String sampName;//样品名称
	private String sampCode;
	private String pointName;//点位名称
	private String pointCode;//点位代码
	private String num;//数量、  采样体积(气)  距声源距离(声)
	private String itemIds;//测试项目
	private String itemNames; 
	 
	private String useId;//领样人
	private String useName;
	private String useDate;//领样日期
	private String content;//内容
	//部门
	private String orgId;
	private String orgName;
	private String deptId;
	private String deptName;
	
	private String remark;//备注
	private String type;//out 出库 in 入库
    private String status;//样品状态：已出库、待归还、已归还
    private String gh;//是 否
	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}
	public void setCust(Cust cust) {
		this.cust = cust;
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
	@JoinColumn(name = "sampling_id")
	public Sampling getSampling() {
		return sampling;
	}
	public void setSampling(Sampling sampling) {
		this.sampling = sampling;
	}
	@Column(length = 32)
	public String getSampId() {
		return sampId;
	}
	public void setSampId(String sampId) {
		this.sampId = sampId;
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
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 8)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 64)
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@Column(length = 32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	@Column(length = 32)
	public String getUseId() {
		return useId;
	}
	public void setUseId(String useId) {
		this.useId = useId;
	}
	@Column(length = 32)
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	@Column(length = 20)
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	@Column(length = 32)
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length = 32)
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
	@Column(length = 256)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length = 8)
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(SampUsed.class, true, ActionType.JSP);
	}
}
