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

@Entity(name = "res_reagent_log")
@Table(name = "res_reagent_log")
@Module(value = "res.reagent.log")
public class ReagentLog extends Po<ReagentLog> {
	private static final long serialVersionUID = 1L;
	private Reagent reagent;
	private String name;// 试剂中文名称
	private String ename;// 试剂英文名称
	private String sname;// 试剂俗称
	private String grade;// 试剂级别
	private String type;// 试剂类型
	private Double safeAmount;// 警戒数量
	private Double amount;// 实际数量
	private String no;// 编号
	private String unit; // 单位
	private String supplier;// 供应商
	private String supplierId;// 供应商
	private String mfg;// 生产日期
	private String exp;// 效期
	private String remark;// 备注
	private String state;// 状态
	private String price;// 价格
	private String bnum;// 批号
	private String purity;// 纯度
	private String saveCode;// 保存条件及要求
	private String purpose;// 用途

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
	@JoinColumn(name = "reagent_id")
	public Reagent getReagent() {
		return reagent;
	}

	public void setReagent(Reagent reagent) {
		this.reagent = reagent;
	}
	@Column(length = 64)
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Column(length = 64)
	public String getBnum() {
		return bnum;
	}

	public void setBnum(String bnum) {
		this.bnum = bnum;
	}
	@Column(length = 64)
	public String getPurity() {
		return purity;
	}

	public void setPurity(String purity) {
		this.purity = purity;
	}
	@Column(length = 64)
	public String getSaveCode() {
		return saveCode;
	}

	public void setSaveCode(String saveCode) {
		this.saveCode = saveCode;
	}
	@Column(length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 64)
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	@Column(length = 64)
	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
	@Column(length = 64)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	@Column(length = 32)
	public Double getSafeAmount() {
		return safeAmount;
	}

	public void setSafeAmount(Double safeAmount) {
		this.safeAmount = safeAmount;
	}
	@Column(length = 64)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Column(length = 64)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 64)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	@Column(length = 64)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(length = 64)
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	@Column(length = 20)
	public String getMfg() {
		return mfg;
	}

	public void setMfg(String mfg) {
		this.mfg = mfg;
	}
	@Column(length = 20)
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
