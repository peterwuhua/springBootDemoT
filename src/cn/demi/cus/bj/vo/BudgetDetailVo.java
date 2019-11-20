package cn.demi.cus.bj.vo;

import cn.core.framework.common.vo.Vo;

public class BudgetDetailVo extends Vo<BudgetDetailVo> {

	private BudgetVo budgetVo;
	//样品信息
	private String sampTypeId;//样品类别
	private String sampTypeName;
	private String sampName;
	
	private String itemIds;
	private String itemNames;
	private float cyPrice;//采样费用
	private float fxPrice;//分析费用
	public BudgetVo getBudgetVo() {
		return budgetVo;
	}
	public void setBudgetVo(BudgetVo budgetVo) {
		this.budgetVo = budgetVo;
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
	public float getCyPrice() {
		return cyPrice;
	}
	public void setCyPrice(float cyPrice) {
		this.cyPrice = cyPrice;
	}
	public float getFxPrice() {
		return fxPrice;
	}
	public void setFxPrice(float fxPrice) {
		this.fxPrice = fxPrice;
	}
}

