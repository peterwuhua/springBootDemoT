package cn.demi.res.vo;

import cn.core.framework.common.vo.Vo;

/** 
 * <strong>Description :试剂管理 </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 */
public class RegOutInVo extends Vo<RegOutInVo> {
	private ReagentVo reagentVo;
	private String name;// 试剂中文名称
	private String ename;// 试剂英文名称
	private String sname;// 试剂俗称
	private String type;// 试剂类型
	private String grade;// 试剂级别
	private Double safeAmount;// 警戒数量
	private Double amount;// 实际数量
	private String no;// 编号
	private String unit; // 单位
	private String rule; // 规格型号
	private String user;// 保管人
	private String dep;// 保管科室
	private String supplier;// 供应商
	private String mfg;// 生产日期
	private String exp;// 效期
	private String remark;// 备注
	private String state;// 状态
	private String price;
	private String bnum;// 批号
	private String purity;// 纯度
	private String saveCode;// 保存条件及要求
	private String purpose;// 用途

	private String flag;// 是否显示日志信息标志
	private String isSafe;// 是否超出警戒线
	private String leadingPerson;// 出库领用人
	private String leadingDate;// 出库领取时间
	private Double leadingNum;// 出库领取数量
	private String inPerson;// 入库操作人
	private String inDate;// 入库时间
	private Double inNum;// 入库数量
	private String status;//出入库标识 （出库\入库）
	
	

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getBnum() {
		return bnum;
	}

	public void setBnum(String bnum) {
		this.bnum = bnum;
	}

	public String getPurity() {
		return purity;
	}

	public void setPurity(String purity) {
		this.purity = purity;
	}

	public String getSaveCode() {
		return saveCode;
	}

	public void setSaveCode(String saveCode) {
		this.saveCode = saveCode;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Double getSafeAmount() {
		return safeAmount;
	}

	public void setSafeAmount(Double safeAmount) {
		this.safeAmount = safeAmount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getMfg() {
		return mfg;
	}

	public void setMfg(String mfg) {
		this.mfg = mfg;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIsSafe() {
		return isSafe;
	}

	public void setIsSafe(String isSafe) {
		this.isSafe = isSafe;
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

	public ReagentVo getReagentVo() {
		return reagentVo;
	}

	public void setReagentVo(ReagentVo reagentVo) {
		this.reagentVo = reagentVo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
