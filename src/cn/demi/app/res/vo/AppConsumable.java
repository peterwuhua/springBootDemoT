package cn.demi.app.res.vo;

import java.util.Map;

public class AppConsumable {
	private String id;
	private String no;// 编号
	private String name;// 耗材中文名称
	private String type;// 类型
	private String unit; // 单位
	private String model;// 型号
	private Double amount;// 实际数量
	private Double safeAmount;// 警戒数量
	private String exp;// 效期
	/***
	 * 仪器保管人	
	 */
	private String keeper;
	private String keepId;
	private String supplier;// 供应商
	private String supplierId;// 供应商
	private String mfg;// 生产日期
	private String price;
	
	
	private String amountstr;
	private String safeAmountstr;
	
	
	
	public AppConsumable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public AppConsumable(Map<String, String[]> map) {
		this.id = map.get("id") != null? map.get("id")[0]:null;
		this.no = map.get("no") != null? map.get("no")[0]:null;
		this.name = map.get("name") != null? map.get("name")[0]:null;
		this.type = map.get("type") != null? map.get("type")[0]:null;
		this.unit = map.get("unit") != null? map.get("unit")[0]:null;
		this.model = map.get("model") != null? map.get("model")[0]:null;		 
		this.exp = map.get("exp") != null? map.get("exp")[0]:null;
		this.keeper = map.get("keeper") != null? map.get("keeper")[0]:null;
		this.keepId = map.get("keepId") != null? map.get("keepId")[0]:null;
		 
		this.supplier = map.get("supplier") != null? map.get("supplier")[0]:null;
		this.supplierId = map.get("supplierId") != null? map.get("supplierId")[0]:null;		 
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
	public String getMfg() {
		return mfg;
	}
	public void setMfg(String mfg) {
		this.mfg = mfg;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	

}
