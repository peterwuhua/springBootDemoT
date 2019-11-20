package cn.demi.bus.pjt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 开票信息
 * @author QuJunLong
 *
 */
@Entity(name = "bus_invoice")
@Table(name = "bus_invoice")
@Module(value = "bus.invoice")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Invoice extends Po<Invoice>{
	
	private static final long serialVersionUID = 6834802335343777182L;
	public String[] PROPERTY_TO_MAP = {"id","no","custName","custCode","custTel","custBank","custAccount","type",
			"taskType","price","num","hcj","user","tel","cwUser","pay","payDate","payWay","yfkMax","yfkMin","sskMax","sskMin","payRatio"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private String no;//项目编号
	private String custName;
	private String custCode;//纳税人识别号   个人身份证
	private String custAddress;//注册地址
	private String custTel;//注册电话
	private String custBank;//开户银行
	private String custAccount;//开户账号
	private String type;//发票类型
	private String taskType;//测试类型
	private String num;//件数
	
	private String user;//联系人
	private String tel;//电话
	private String remark;//备注
	
	private String cwUser;//收款人
	private String cwUserId;
	private String cwDate;//财务确认日期
	
	private float jjPrice;//加急费
	private float price;//分析费用+采样费用总额
	private float discount;//折扣
	private float yhPrice;//优惠价格
	private float jtPrice;//交通费
	private float bgPrice;//报告费
	private float otherPrice;//税费等其他费用
	private float totalPrice;//合同总额费用（优惠前）
	private float htPrice;//合同费用（优惠后）
	
	private float pay;//收款金额
	private float cash;//现金/转账额
	private String payDate;//收款日期
	private String payWay;//付款方式（转账，支付宝,现金,其他,现金+代金券）
	private String kp;//是否开票

	private float  yfkMin; //预付款
	private float  sskMin; //实收款
	private double payRatio; //合同付款比例
	
	 
	@Column(length = 11)
	public float getYfkMin() {
		return yfkMin;
	}
	public void setYfkMin(float yfkMin) {
		this.yfkMin = yfkMin;
	}
	@Column(length = 11)
	public float getSskMin() {
		return sskMin;
	}
	public void setSskMin(float sskMin) {
		this.sskMin = sskMin;
	}
	@Column(length = 32)
	public double getPayRatio() {
		return payRatio;
	}
	public void setPayRatio(double payRatio) {
		this.payRatio = payRatio;
	}
	@Column(length = 64)
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	@Column(length = 32)
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	@Column(length = 128)
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	@Column(length = 32)
	public String getCustTel() {
		return custTel;
	}
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	@Column(length = 64)
	public String getCustBank() {
		return custBank;
	}
	public void setCustBank(String custBank) {
		this.custBank = custBank;
	}
	@Column(length = 32)
	public String getCustAccount() {
		return custAccount;
	}
	public void setCustAccount(String custAccount) {
		this.custAccount = custAccount;
	}
	@Column(length = 32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 32)
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Column(length = 16)
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@Column(length = 32)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Column(length = 32)
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getCwUser() {
		return cwUser;
	}
	public void setCwUser(String cwUser) {
		this.cwUser = cwUser;
	}
	@Column(length = 32)
	public String getCwUserId() {
		return cwUserId;
	}
	public void setCwUserId(String cwUserId) {
		this.cwUserId = cwUserId;
	}
	@Column(length = 32)
	public float getPay() {
		return pay;
	}
	public void setPay(float pay) {
		this.pay = pay;
	}
	@Column(length = 32)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length = 4)
	public String getKp() {
		return kp;
	}
	public void setKp(String kp) {
		this.kp = kp;
	}
	@Column(length = 20)
	public String getCwDate() {
		return cwDate;
	}
	public void setCwDate(String cwDate) {
		this.cwDate = cwDate;
	}
	public float getJjPrice() {
		return jjPrice;
	}
	public void setJjPrice(float jjPrice) {
		this.jjPrice = jjPrice;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public float getOtherPrice() {
		return otherPrice;
	}
	public void setOtherPrice(float otherPrice) {
		this.otherPrice = otherPrice;
	}
	public float getHtPrice() {
		return htPrice;
	}
	public void setHtPrice(float htPrice) {
		this.htPrice = htPrice;
	}
	public float getJtPrice() {
		return jtPrice;
	}
	public void setJtPrice(float jtPrice) {
		this.jtPrice = jtPrice;
	}
	public float getBgPrice() {
		return bgPrice;
	}
	public void setBgPrice(float bgPrice) {
		this.bgPrice = bgPrice;
	}
	public float getYhPrice() {
		return yhPrice;
	}
	public void setYhPrice(float yhPrice) {
		this.yhPrice = yhPrice;
	}
	
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
	public float getCash() {
		return cash;
	}
	public void setCash(float cash) {
		this.cash = cash;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Invoice.class, true, ActionType.JSP);
	}
}
