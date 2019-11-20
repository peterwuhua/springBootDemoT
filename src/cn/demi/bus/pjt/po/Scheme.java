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

/**
 * 方案持久对象
 * @author QuJunLong
 */
@Entity(name = "bus_scheme")
@Table(name = "bus_scheme")
@Module(value = "bus.scheme")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Scheme extends Po<Scheme>{

 
	public static final String ST_WZX = "未执行";
	public static final String ST_ZXZ = "执行中";
	public static final String ST_YZX = "已执行";
	
	private static final long serialVersionUID = 7031153337790141620L;
	public String[] PROPERTY_TO_MAP = {"id","sort","isDel","project","startDate","cyDay"};
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","project","status","num"};
	
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
	private Project project; 
	private String cyDate;//采/送样日期
	private String cyEndDate;//采送样结束日期
	private String startDate;//开始日期
	private String endDate;//结束日期
	private String cyUserName;//送样人员
	//样品信息
	private String sampTypeId;//样品类型名称
	private String sampTypeName;
	private String sampName;//样品名称
	private int sampNum;//样品数量
	private String itemNames;//检测项目
	private String pointNames;//检测点位
	private String dealRequest;//样品处理要求
	private String saveRequest;//样品保存条件
	
	private float fxPrice;//检测费用=每个点位（项目费用总额*频次）的合计
	private float cyPrice;//采样费用
	private String status;//当前状态：未执行、执行中、 已执行
	private String remark;
	private int num;//方案序号
	
	private int cyDay ;//采样天数
	
	
	@Column(length = 16)
	public int getCyDay() {
		return cyDay;
	}
	public void setCyDay(int cyDay) {
		this.cyDay = cyDay;
	}
	@ManyToOne
	@JoinColumn(name = "project_id")
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@Column(length = 20)
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@Column(length = 20)
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Column(length = 128)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public float getFxPrice() {
		return fxPrice;
	}
	public void setFxPrice(float fxPrice) {
		this.fxPrice = fxPrice;
	}
	@Column(length = 16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public float getCyPrice() {
		return cyPrice;
	}
	public void setCyPrice(float cyPrice) {
		this.cyPrice = cyPrice;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	@Column(length = 64)
	public String getCyUserName() {
		return cyUserName;
	}
	public void setCyUserName(String cyUserName) {
		this.cyUserName = cyUserName;
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
	public int getSampNum() {
		return sampNum;
	}
	public void setSampNum(int sampNum) {
		this.sampNum = sampNum;
	}
	@Column(length = 1000)
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	@Column(length = 1000)
	public String getPointNames() {
		return pointNames;
	}
	public void setPointNames(String pointNames) {
		this.pointNames = pointNames;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Scheme.class, true, ActionType.JSP);
	}
}
