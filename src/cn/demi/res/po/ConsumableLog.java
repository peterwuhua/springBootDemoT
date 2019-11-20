package cn.demi.res.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name = "res_consumable_log")
@Table(name = "res_consumable_log")
@Module(value = "res.consumable.log")
public class ConsumableLog extends Po<ConsumableLog> {
	private static final long serialVersionUID = 1L;
	private Consumable consumable;
	private String no;// 编号
	private String name;// 耗材中文名称
	private Double safeAmount;// 警戒数量
	private Double amount;// 实际数量
	private String unit; // 单位
	private String model;// 型号
	private String type;// 类型
	private String supplier;// 供应商
	private String supplierId;// 供应商
	private String mfg;// 生产日期
	private String exp;// 效期
	private String remark;// 备注
	private String state;// 状态
	private String price;

	private Double lastAmount;// 修改前数量
	private String cuser;// 修改人
	private String date;// 修改时间
	private String leadingPerson;// 领用人
	private String rkNum;//入库数量
	/***
	 * 仪器保管人	
	 */
	private String keeper;
	private String keepId;
	/***
	 * 仪器保管科室
	 */
	private String deptId;
	private String deptName;
	/***
	 * 部门信息
	 */
	private String orgId;
	private String orgName;
	public final String[] IGNORE_PROPERTY= {"id","createTime","lastUpdTime","isDel","version"};

	@Column(length = 16)
	public String getRkNum() {
		return rkNum;
	}

	public void setRkNum(String rkNum) {
		this.rkNum = rkNum;
	}

	@ManyToOne
	@JoinColumn(name = "consumable_id")
	public Consumable getConsumable() {
		return consumable;
	}

	public void setConsumable(Consumable consumable) {
		this.consumable = consumable;
	}

	@Column(length = 64)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	@Column(length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 32)
	public Double getSafeAmount() {
		return safeAmount;
	}

	public void setSafeAmount(Double safeAmount) {
		this.safeAmount = safeAmount;
	}
	@Column(length = 32)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Column(length = 64)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(length = 64)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	@Column(length = 64)
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	@Column(length = 64)
	public String getMfg() {
		return mfg;
	}

	public void setMfg(String mfg) {
		this.mfg = mfg;
	}
	@Column(length = 64)
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
	@Column(length = 64)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(length = 16)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	@Column(length = 64)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 64)
	public String getLeadingPerson() {
		return leadingPerson;
	}

	public void setLeadingPerson(String leadingPerson) {
		this.leadingPerson = leadingPerson;
	}
	@Column(length = 64)
	public String getKeeper() {
		return keeper;
	}
	public void setKeeper(String keeper) {
		this.keeper = keeper;
	}
	@Column(length = 32)
	public String getKeepId() {
		return keepId;
	}
	public void setKeepId(String keepId) {
		this.keepId = keepId;
	}

	@Column(length = 32)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(length = 64)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(length = 64)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length = 32)
	public Double getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(Double lastAmount) {
		this.lastAmount = lastAmount;
	}
	@Column(length = 64)
	public String getCuser() {
		return cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}
	@Column(length = 20)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	@Column(length = 32)
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	@Transient
	@Override
	public String[] getPropertyToMap() {
		return null;
	}
}
