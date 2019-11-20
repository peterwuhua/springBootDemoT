package cn.demi.bus.pjt.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class SchemePointVo extends Vo<SchemePointVo> {
	
	private SchemeVo schemeVo;//
	private String projectId;
	private String sampTypeId;//样品类别
	private String sampTypeName;
	private String sampName;
	private int pc;//采样频率
	private String pcUnit;
	
	//检测点位
	private String pointName;
	private String pointCode;
	private String pointType;//点位类型 水 气 声 固
	private String xz;
	private String packing;
	private String deal;
	//检测项目
	private String itemId;
	private String itemName;
	private float fxPrice;
	
	private String room;//车间
	private String cyType;//采样方式
	private int cyHours;//采样时长
	//检测项目和方法关系表id
	private String imId;
	private String remark;
	private List<ImVo> imList;//项目方法关系集合
	public SchemeVo getSchemeVo() {
		return schemeVo;
	}
	public void setSchemeVo(SchemeVo schemeVo) {
		this.schemeVo = schemeVo;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getImId() {
		return imId;
	}
	public void setImId(String imId) {
		this.imId = imId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
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
	public List<ImVo> getImList() {
		return imList;
	}
	public void setImList(List<ImVo> imList) {
		this.imList = imList;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
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
}

