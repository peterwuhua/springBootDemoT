package cn.demi.app.res.vo;

public class AppConsumableOut {
	private String id;
	private String no;// 编号
	private String name;// 耗材中文名称
	private String type;// 类型
	private String unit; // 单位
	private String model;// 型号
	private String inPerson;// 入库操作人
	private String inDate;// 入库时间
	private Double inNum;// 入库数量
	private String remark;// 备注
	private String leadingPerson;// 出库领用人
	private String leadingDate;// 出库领取时间
	private Double leadingNum;// 出库领取数量
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getInPerson() {
		return inPerson;
	}
	public void setInPerson(String inPerson) {
		this.inPerson = inPerson;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	public Double getInNum() {
		return inNum;
	}
	public void setInNum(Double inNum) {
		this.inNum = inNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLeadingPerson() {
		return leadingPerson;
	}
	public void setLeadingPerson(String leadingPerson) {
		this.leadingPerson = leadingPerson;
	}
	public String getLeadingDate() {
		return leadingDate;
	}
	public void setLeadingDate(String leadingDate) {
		this.leadingDate = leadingDate;
	}
	public Double getLeadingNum() {
		return leadingNum;
	}
	public void setLeadingNum(Double leadingNum) {
		this.leadingNum = leadingNum;
	}
	
}
