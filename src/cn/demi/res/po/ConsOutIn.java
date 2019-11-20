package cn.demi.res.po;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/** 
 * <strong>Description :耗材管理表 </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 */
@Entity(name = "res_consoutin")
@Table(name = "res_consoutin")
@Module(value = "res.consoutin")
public class ConsOutIn extends Po<ConsOutIn> {
	private static final long serialVersionUID = 1L;
	public static final String out = "出库";
	public static final String in = "入库";
	private String no;// 编号
	private String name;// 耗材中文名称
	private Double safeAmount;// 警戒数量
	private Double amount;// 实际数量
	private String unit; // 单位
	private String model;// 型号
	private String type;// 类型
	private String user;// 保管人
	private String dep;// 保管科室
	private String supplier;// 供应商
	private String mfg;// 生产日期
	private String exp;// 效期
	private String remark;// 备注
	private String state;// 状态
	private String price;
	private String leadingPerson;// 出库领用人
	private String leadingDate;// 出库领取时间
	private Double leadingNum;// 出库领取数量
	private String inPerson;// 入库操作人
	private String inDate;// 入库时间
	private Double inNum;// 入库数量
	private String status;//出入库标识 （出库\入库）
	private Consumable consumable;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"name","type","safeAmount","amount","no","unit","model","user","dep","supplier","mfg","exp","remark","state","price","id","consumable",
			"leadingDate","leadingNum","inPerson","inDate","inNum","status","leadingPerson"};
	
	
	@ManyToOne
	@JoinColumn(name = "consumable_id")
	public Consumable getConsumable() {
		return consumable;
	}

	public void setConsumable(Consumable consumable) {
		this.consumable = consumable;
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

	public Double getSafeAmount() {
		return safeAmount;
	}

	public void setSafeAmount(Double safeAmount) {
		this.safeAmount = safeAmount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLeadingPerson() {
		return leadingPerson;
	}

	public void setLeadingPerson(String leadingPerson) {
		this.leadingPerson = leadingPerson;
	}

	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ConsOutIn.class, false, ActionType.JSP);
	}
	
	public String getLeadingDate() {
		return leadingDate;
	}

	public void setLeadingDate(String leadingDate) {
		this.leadingDate = leadingDate;
	}


	public Double getLeadingNum() {
		return leadingNum;
	}

	public void setLeadingNum(Double leadingNum) {
		this.leadingNum = leadingNum;
	}

	public String getInPerson() {
		return inPerson;
	}

	public void setInPerson(String inPerson) {
		this.inPerson = inPerson;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public Double getInNum() {
		return inNum;
	}

	public void setInNum(Double inNum) {
		this.inNum = inNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	@Override
	public String[] getPropertyToMap() {
		// TODO Auto-generated method stub
		return PROPERTY_TO_MAP;
	}

}
