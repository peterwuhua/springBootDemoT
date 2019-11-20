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
import cn.core.framework.utils.code.annotation.Module;

/**
 * 方案点位持久对象
 * @author QuJunLong
 *
 */
@Entity(name = "bus_scheme_point")
@Table(name = "bus_scheme_point")
@Module(value = "bus.schemePoint")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SchemePoint extends Po<SchemePoint>{
	
 
	private static final long serialVersionUID = -6447109463092537967L;
	public String[] PROPERTY_TO_MAP = {"id","sort","isDel"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	private Scheme scheme;//
	private String projectId;
	private String sampTypeId;//样品类别
	private String sampTypeName;
	private String sampName;//样品
	 
	private int pc;//采样频率
	private String pcUnit;
	//检测点位
	private String pointName;//检测点
	private String pointCode;
	private String pointType;//点位类型 水 气 声 固
	private String xz;
	private String packing;
	private String deal;
	//检测项目
	private String itemId;
	private String itemName;
	
	private String room;//车间
	private String cyType;//采样方式
	private int cyHours;//采样时长
	private float fxPrice;
	private String remark;
	@ManyToOne
	@JoinColumn(name = "scheme_id")
	public Scheme getScheme() {
		return scheme;
	}
	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}
	@Column(length =32)
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Column(length =32)
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	@Column(length =32)
	public String getPointCode() {
		return pointCode;
	}
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}
	@Column(length =8)
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	@Column(length =1000)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(length =1000)
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Column(length =32)
	public String getPcUnit() {
		return pcUnit;
	}
	public void setPcUnit(String pcUnit) {
		this.pcUnit = pcUnit;
	}
	@Column(length =320)
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length =320)
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	@Column(length =32)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length =32)
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length =128)
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	@Column(length =64)
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	@Column(length =64)
	public String getDeal() {
		return deal;
	}
	public void setDeal(String deal) {
		this.deal = deal;
	}
	public float getFxPrice() {
		return fxPrice;
	}
	public void setFxPrice(float fxPrice) {
		this.fxPrice = fxPrice;
	}
	@Column(length =64)
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	@Column(length =32)
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
//	public static void main(String[] args) {
//		CreateCodeUtils.autoCreateCode(SchemePoint.class, true, ActionType.JSP);
//	}
}
