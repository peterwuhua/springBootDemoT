package cn.demi.cus.bj.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 报价详情
 * @author QuJunLong
 */
@Entity(name = "cus_budget_detail")
@Table(name = "cus_budget_detail")
@Module(value = "cus.budgetDetail")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BudgetDetail extends Po<BudgetDetail>{

 
	private static final long serialVersionUID = 2739175834442085426L;
	public String[] PROPERTY_TO_MAP = {"id","sort","isDel","no"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private Budget budget;
	
	//样品信息
	private String sampTypeId;//样品类别
	private String sampTypeName;
	private String sampName;
	
	private String itemIds;
	private String itemNames;
	private float cyPrice;//采样费用
	private float fxPrice;//分析费用
	
	@ManyToOne
	@JoinColumn(name = "budget_id")
	public Budget getBudget() {
		return budget;
	}
	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	@Column(length =32)
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length =32)
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	@Column(length =64)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length =1000)
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	@Column(length =1000)
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	@Column(length =16)
	public float getCyPrice() {
		return cyPrice;
	}
	public void setCyPrice(float cyPrice) {
		this.cyPrice = cyPrice;
	}
	@Column(length =16)
	public float getFxPrice() {
		return fxPrice;
	}
	public void setFxPrice(float fxPrice) {
		this.fxPrice = fxPrice;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(BudgetDetail.class, true, ActionType.JSP);
	}	
}
