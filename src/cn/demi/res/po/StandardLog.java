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

@Entity(name = "res_standard_log")
@Table(name = "res_standard_log")
@Module(value = "res.standard.log")
public class StandardLog extends Po<StandardLog> {
	private static final long serialVersionUID = 1L;
	private Standard standard;
	private String no;// 编号
	private String cas;// CAS号
	private String cerNo;// 证书批号
	private String name;// 标准品中文名称
	private String ename;// 标准品英文名称
	private Double safeAmount;// 警戒数量
	private Double amount;// 实际数量
	private String unit; // 规格型号
	private String content; // 含量
	private String supplier;// 供应商
	private String supplierId;// 供应商
	private String producer;// 生产厂家
	private String mfg;// 生产日期
	private String exp;// 有效期
	private String remark;// 备注
	private String state;// 状态
	private String price;

	private Double lastAmount;// 修改前数量
	private String cuser;// 修改人
	private String date;// 修改时间
	private String purDate; // 购置日期
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
	@JoinColumn(name = "standard_id")
	public Standard getStandard() {
		return standard;
	}

	public void setStandard(Standard standard) {
		this.standard = standard;
	}
	@Column(length = 64)
	public String getCas() {
		return cas;
	}

	public void setCas(String cas) {
		this.cas = cas;
	}
	@Column(length = 64)
	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	@Column(length = 64)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(length = 64)
	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
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
	@Column(length = 64)
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	@Column(length = 16)
	public Double getSafeAmount() {
		return safeAmount;
	}

	public void setSafeAmount(Double safeAmount) {
		this.safeAmount = safeAmount;
	}
	@Column(length = 16)
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
	@Column(length = 32)
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
	@Column(length = 20)
	public String getPurDate() {
		return purDate;
	}

	public void setPurDate(String purDate) {
		this.purDate = purDate;
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
