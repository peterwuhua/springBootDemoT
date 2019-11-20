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

@Entity(name = "bi_standard")
@Module(value = "bi.standard")
@Table(name = "bi_standard")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Standard extends Po<Standard> {
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	private String no;// 编号
	/**
	 * CAS号
	 */
	private String cas;// CAS号
	/**
	 * 证书批号
	 */
	private String cerNo;// 证书批号
	/**
	 * 标准品中文名称
	 */
	private String name;// 标准品中文名称
	/**
	 * 标准品英文名称
	 */
	private String ename;// 标准品英文名称
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
	 * 规格型号
	 */
	private String rule; // 规格型号
	/**
	 * 含量
	 */
	private String content; // 含量
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
	 * 生产厂家
	 */
	private String producer;// 生产厂家
	/**
	 * 生产日期
	 */
	private String mfg;// 生产日期
	/**
	 * 有效期
	 */
	private String exp;// 有效期
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
	/**
	 * 保存条件
	 */
	private String saveCondition; // 保存条件
	/**
	 * 购置日期
	 */
	private String purDate; // 购置日期

	private String[] PROPERTY_TO_MAP = { "id", "sort", "code", "no", "cas", "cerNo", "name", "ename", "safeAmount",
			"amount", "unit", "rule", "content", "user", "dep", "supplier", "producer", "mfg", "exp", "remark", "state",
			"price", "saveCondition", "purDate" };

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
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

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
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

	public String getSaveCondition() {
		return saveCondition;
	}

	public void setSaveCondition(String saveCondition) {
		this.saveCondition = saveCondition;
	}

	public String getPurDate() {
		return purDate;
	}

	public void setPurDate(String purDate) {
		this.purDate = purDate;
	}

	@Transient
	@Override
	public String[] getPropertyToMap() {

		return PROPERTY_TO_MAP;
	}

	public static void main(String[] args) {

		CreateCodeUtils.autoCreateCode(Standard.class, true, ActionType.JSP);
	}

}