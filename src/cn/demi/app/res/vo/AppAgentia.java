package cn.demi.app.res.vo;

import java.util.Map;

public class AppAgentia {
	private String id;
	private String no;// 编号
	private String name;// 试剂中文名称
	private String type;// 试剂类型
	private String purity;// 纯度
	private String grade;// 试剂级别
	private Double amount;// 实际数量
	private Double safeAmount;// 警戒数量
	private String exp;// 效期	
	/**
	 * 仪器保管人	
	 */
	private String keeper;
	private String keepId;
	
	
	private String ename;// 试剂英文名称
	private String sname;// 试剂俗称
	private String unit; // 单位
	private String supplier;// 供应商
	private String supplierId;// 供应商
	private String bnum;// 批号
	private String saveCode;// 保存条件及要求
	private String purpose;// 用途Leading person
	private String mfg;// 生产日期
	private String price;//价格
	private String amountstr;
	private String safeAmountstr;
	
	
	
	
	public AppAgentia() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AppAgentia(Map<String, String[]> map) {
		this.id = map.get("id") != null? map.get("id")[0]:null;
		this.no = map.get("no") != null? map.get("no")[0]:null;
		this.name = map.get("name") != null? map.get("name")[0]:null;
		this.type = map.get("type") != null? map.get("type")[0]:null;
		this.purity = map.get("purity") != null? map.get("purity")[0]:null;
		this.grade = map.get("grade") != null? map.get("grade")[0]:null;		 
		this.exp = map.get("exp") != null? map.get("exp")[0]:null;
		this.keeper = map.get("keeper") != null? map.get("keeper")[0]:null;
		this.keepId = map.get("keepId") != null? map.get("keepId")[0]:null;
		this.ename = map.get("ename") != null? map.get("ename")[0]:null;
		this.sname = map.get("sname") != null? map.get("sname")[0]:null;
		this.unit = map.get("unit") != null? map.get("unit")[0]:null;
		this.supplier = map.get("supplier") != null? map.get("supplier")[0]:null;
		this.supplierId = map.get("supplierId") != null? map.get("supplierId")[0]:null;
		this.bnum = map.get("bnum") != null? map.get("bnum")[0]:null;
		this.saveCode = map.get("saveCode") != null? map.get("saveCode")[0]:null;
		this.purpose = map.get("purpose") != null? map.get("purpose")[0]:null;
		this.mfg = map.get("mfg") != null? map.get("mfg")[0]:null;
		this.price = map.get("price") != null? map.get("price")[0]:null;
		
	 
		this.amountstr = map.get("amountstr") != null? map.get("price")[0]:null;
		this.safeAmountstr = map.get("safeAmountstr") != null? map.get("safeAmountstr")[0]:null;
		
		
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


	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPurity() {
		return purity;
	}
	public void setPurity(String purity) {
		this.purity = purity;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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
	public String getMfg() {
		return mfg;
	}
	public void setMfg(String mfg) {
		this.mfg = mfg;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getBnum() {
		return bnum;
	}
	public void setBnum(String bnum) {
		this.bnum = bnum;
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
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getPrice() {
		return price;
	} 
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
