package cn.demi.app.res.vo;

import java.util.Map;

public class AppStandard {
	private String id;
	private String no;// 编号
	private String name;// 标准品中文名称
	private String rule; // 规格型号
	private String content; // 含量
	private Double amount;// 实际数量
	private Double safeAmount;// 警戒数量
	private String exp;// 有效期
	/***
	 * 仪器保管人	
	 */
	private String keeper;
	private String keepId;
	
	private String cas;// CAS号
	private String supplier;// 供应商
	private String supplierId;// 供应商
	private String cerNo;// 证书批号
	private String producer;// 生产厂家
	private String saveCondition; // 保存条件
	private String price;
	private String remark;// 备注
	private String mfg;
	private String ename;
	private String amountstr;
	private String safeAmountstr;
	
	
	public AppStandard() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppStandard(Map<String, String[]> map) {
		this.id = map.get("id") != null? map.get("id")[0]:null;
		this.no = map.get("no") != null? map.get("no")[0]:null;
		this.name = map.get("name") != null? map.get("name")[0]:null;
		this.rule = map.get("rule") != null? map.get("rule")[0]:null;
		this.content = map.get("content") != null? map.get("content")[0]:null;	 
		this.exp = map.get("exp") != null? map.get("exp")[0]:null;
		this.keeper = map.get("keeper") != null? map.get("keeper")[0]:null;
		this.keepId = map.get("keepId") != null? map.get("keepId")[0]:null;
		this.cas = map.get("cas") != null? map.get("cas")[0]:null;
		this.supplier = map.get("supplier") != null? map.get("supplier")[0]:null;
		this.supplierId = map.get("supplierId") != null? map.get("supplierId")[0]:null;
		this.cerNo = map.get("cerNo") != null? map.get("cerNo")[0]:null;
		this.producer = map.get("producer") != null? map.get("producer")[0]:null;
		this.saveCondition = map.get("saveCondition") != null? map.get("saveCondition")[0]:null;		
		this.price = map.get("price") != null? map.get("price")[0]:null;
		this.remark = map.get("remark") != null? map.get("remark")[0]:null;
		this.mfg = map.get("mfg") != null? map.get("mfg")[0]:null;
		this.ename = map.get("ename") != null? map.get("ename")[0]:null;
	 
		this.amountstr = map.get("amountstr") != null? map.get("price")[0]:null;
		this.safeAmountstr = map.get("safeAmountstr") != null? map.get("safeAmountstr")[0]:null;

	}
	
	public String getMfg() {
		return mfg;
	}
	public void setMfg(String mfg) {
		this.mfg = mfg;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getAmountstr() {
		return amountstr;
	}
	public void setAmountstr(String amountstr) {
		this.amountstr = amountstr;
	}
	public String getSafeAmountstr() {
		return safeAmountstr;
	}
	public void setSafeAmountstr(String safeAmountstr) {
		this.safeAmountstr = safeAmountstr;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getSafeAmount() {
		return safeAmount;
	}
	public void setSafeAmount(Double safeAmount) {
		this.safeAmount = safeAmount;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getKeeper() {
		return keeper;
	}
	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}
	public String getKeepId() {
		return keepId;
	}
	public void setKeepId(String keepId) {
		this.keepId = keepId;
	}
	public String getCas() {
		return cas;
	}
	public void setCas(String cas) {
		this.cas = cas;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getSaveCondition() {
		return saveCondition;
	}
	public void setSaveCondition(String saveCondition) {
		this.saveCondition = saveCondition;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
