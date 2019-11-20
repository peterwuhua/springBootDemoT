package cn.demi.bi.res.po;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name = "bi_consumable")
@Module(value = "bi.consumable")
@Table(name = "bi_consumable")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Consumable extends Po<Consumable> {
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	private String no;// 编号
	/**
	 * 耗材中文名称
	 */
	private String name;// 耗材中文名称
	/**
	 * 警戒数量
	 */
	private String safeAmount;// 警戒数量
	/**
	 * 实际数量
	 */
	private String amount;// 实际数量
	/**
	 * 单位
	 */
	private String unit; // 单位
	/**
	 * 型号
	 */
	private String model;// 型号
	/**
	 * 类型
	 */
	private String type;// 类型
	/**
	 * 保管人
	 */
	private String user;// 保管人
	/**
	 * 保管科室
	 */
	private String dep;// 保管科室
	/**
	 * 供应商
	 */
	private String supplier;// 供应商
	/**
	 * 生产日期
	 */
	private String mfg;// 生产日期
	/**
	 * 效期
	 */
	private String exp;// 效期
	/**
	 * 备注
	 */
	private String remark;// 备注
	/**
	 * 状态
	 */
	private String state;// 状态
	/**
	 * 参考价格
	 */
	private String price;

	private String[] PROPERTY_TO_MAP = { "id", "sort", "code", "no", "name", "safeAmount", "amount", "unit", "model",
			"type", "user", "dep", "rule", "user", "dep", "supplier", "mfg", "exp", "remark", "state", "price" };

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

	public String getSafeAmount() {
		return safeAmount;
	}

	public void setSafeAmount(String safeAmount) {
		this.safeAmount = safeAmount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	@Lob
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

	@Transient
	@Override
	public String[] getPropertyToMap() {

		return PROPERTY_TO_MAP;
	}

	public static void main(String[] args) {

		CreateCodeUtils.autoCreateCode(Consumable.class, true, ActionType.JSP);
	}
}