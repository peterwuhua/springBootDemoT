package cn.demi.bus.pjt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 岗位 检测点/检测对象
 * @author QuJunLong
 *
 */
@Entity(name = "bus_cust_point")
@Table(name = "bus_cust_point")
@Module(value = "bus.custPoint")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustPoint extends Po<CustPoint>{
	
	private static final long serialVersionUID = -3806747849550956581L;
	public String[] PROPERTY_TO_MAP = { "id", "isDel","sort"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private Cust cust;//客户信息
	private CustRoom room;//岗位
	private String projectId;//项目
	private String name;//名称
	private String sampTypeId;//样品类别
	private String sampTypeName;
	private int workTal;//作业人数 总数
	private int workNum;//作业人数  数/班
	private String productName;//生产设备
	private int productTal;//生产设备 总数
	private int productNum;//生产设备 开启数
	
	private String itemIds;//检测项目
	private String itemNames;
	private String workHours;//接触时间 h/d
	
	private String fhName;//职业病防护设施 名称
	private int fhTal;//职业病防护设施 总数
	private int fhNum;//职业病防护设施 开启数
	
	private String others;//个体防护用品及佩戴情况
	private String works;//工作内容
	
	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}
	public void setCust(Cust cust) {
		this.cust = cust;
	}
	@ManyToOne
	@JoinColumn(name = "room_id")
	public CustRoom getRoom() {
		return room;
	}
	public void setRoom(CustRoom room) {
		this.room = room;
	}
	@Column(length =32)
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Column(length =128)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWorkTal() {
		return workTal;
	}
	public void setWorkTal(int workTal) {
		this.workTal = workTal;
	}
	public int getWorkNum() {
		return workNum;
	}
	public void setWorkNum(int workNum) {
		this.workNum = workNum;
	}
	@Column(length =128)
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductTal() {
		return productTal;
	}
	public void setProductTal(int productTal) {
		this.productTal = productTal;
	}
	public int getProductNum() {
		return productNum;
	}
	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	@Column(length =1000)
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	@Column(length =1000)
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	@Column(length =128)
	public String getFhName() {
		return fhName;
	}
	public void setFhName(String fhName) {
		this.fhName = fhName;
	}
	public int getFhTal() {
		return fhTal;
	}
	public void setFhTal(int fhTal) {
		this.fhTal = fhTal;
	}
	public int getFhNum() {
		return fhNum;
	}
	public void setFhNum(int fhNum) {
		this.fhNum = fhNum;
	}
	@Column(length =256)
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	@Column(length =256)
	public String getWorks() {
		return works;
	}
	public void setWorks(String works) {
		this.works = works;
	}
	@Column(length =320)
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length =640)
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(CustPoint.class, true, ActionType.JSP);
	}
	
}
