package cn.demi.bus.pjt.vo;

import cn.core.framework.common.vo.Vo;

public class CustPointVo extends Vo<CustPointVo> {
	private CustVo custVo;//客户信息
	private CustRoomVo roomVo;//岗位
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
	private String imId;//关系表
	private String workHours;//接触时间 h/d
	
	private String fhName;//职业病防护设施 名称
	private int fhTal;//职业病防护设施 总数
	private int fhNum;//职业病防护设施 开启数
	
	private String others;//个体防护用品及佩戴情况
	private String works;//工作内容
	public CustVo getCustVo() {
		return custVo;
	}
	public void setCustVo(CustVo custVo) {
		this.custVo = custVo;
	}
	public CustRoomVo getRoomVo() {
		return roomVo;
	}
	public void setRoomVo(CustRoomVo roomVo) {
		this.roomVo = roomVo;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
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
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
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
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public String getWorks() {
		return works;
	}
	public void setWorks(String works) {
		this.works = works;
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
	public String getImId() {
		return imId;
	}
	public void setImId(String imId) {
		this.imId = imId;
	}
}

