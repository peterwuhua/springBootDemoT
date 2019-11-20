package cn.demi.res.vo;

import cn.core.framework.common.vo.Vo;

/** <strong>Create on : 2017年2月28日 下午1:37:21 </strong> <br>
 * <strong>Description : 标准品管理 </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
public class StanOutInVo extends Vo<StanOutInVo> {
	private StandardVo standardVo;
	private String no;// 编号
	private String cas;// CAS号
	private String cerNo;// 批号
	private String name;// 标准品中文名称
	private String ename;// 标准品英文名称
	private Double safeAmount;// 警戒数量
	private Double amount;// 实际数量
	private String unit; // 单位
	private String rule; // 规格型号
	private String content; // 含量
	private String user;// 保管人
	private String dep;// 保管科室
	private String supplier;// 供应商
	private String producer;// 生产厂家
	private String mfg;// 生产日期
	private String exp;// 有效期
	private String remark;// 备注
	private String state;// 状态
	private String price;
	private String flag;// 是否显示日志信息标志
	private String isSafe;// 是否超出警戒线
	private String saveCondition; // 保存条件
	private String effectiveDate; // 效期

	private String dateType; // 是否超期
	private String purDate; // 购置日期
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

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getSaveCondition() {
		return saveCondition;
	}

	public void setSaveCondition(String saveCondition) {
		this.saveCondition = saveCondition;
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

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
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

	public String getCas() {
		return cas;
	}

	public void setCas(String cas) {
		this.cas = cas;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getPurDate() {
		return purDate;
	}

	public void setPurDate(String purDate) {
		this.purDate = purDate;
	}

	public StandardVo getStandardVo() {
		return standardVo;
	}

	public void setStandardVo(StandardVo standardVo) {
		this.standardVo = standardVo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
