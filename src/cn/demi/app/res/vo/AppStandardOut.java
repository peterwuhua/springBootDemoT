package cn.demi.app.res.vo;

public class AppStandardOut {
	private String id;
	private String no;// 编号
	private String name;// 标准品中文名称
	private String rule; // 规格型号
	private String exp;// 有效期
	private String leadingPerson;// 出库领用人
	private String leadingDate;// 出库领取时间
	private Double leadingNum;// 出库领取数量
	private String inPerson;// 入库操作人
	private String inDate;// 入库时间
	private Double inNum;// 入库数量
	private String remark;// 备注
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
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
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
	
}
