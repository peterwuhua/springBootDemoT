package cn.demi.bus.pjt.vo;

import cn.core.framework.common.vo.Vo;

public class InvoiceVo extends Vo<InvoiceVo> {
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
	private String totalPriceMax;//合同经费大写
	private float htPrice;//合同费用（优惠后）
	private float pjPrice;//评价费用
	private float yfPrice;//预付费用
	private String yfPriceMax;//预付费用大写
	private float syPrice;//剩余应付费用
	private String syPriceMax;//剩余应付费用大写
	private String htPriceZn;//合同费用（中文）
	
	private float pay;//收款金额
	private float cash;//现金/转账额
	private String payDate;//收款日期
	private String payWay;//付款方式（转账，支付宝,现金,其他,现金+代金券）
	private String kp;//是否开票
	
	
	private String yfkMax; //预付款大写
	private float  yfkMin; //预付款
	private String sskMax; //实收款大写
	private float  sskMin; //实收款
	private double payRatio; //合同付款比例
	private String yfRatio; //预付款比例
	private String leftRatio; //剩余款比例
	private String yfkMinZn; //预付款
	private String sskMinZn; //实收款
	
	
	
	public String getYfkMinZn() {
		return yfkMinZn;
	}
	public void setYfkMinZn(String yfkMinZn) {
		this.yfkMinZn = yfkMinZn;
	}
	public String getSskMinZn() {
		return sskMinZn;
	}
	public void setSskMinZn(String sskMinZn) {
		this.sskMinZn = sskMinZn;
	}
	public String getYfRatio() {
		return yfRatio;
	}
	public void setYfRatio(String yfRatio) {
		this.yfRatio = yfRatio;
	}
	public String getLeftRatio() {
		return leftRatio;
	}
	public void setLeftRatio(String leftRatio) {
		this.leftRatio = leftRatio;
	}
	public String getYfkMax() {
		return yfkMax;
	}
	public void setYfkMax(String yfkMax) {
		this.yfkMax = yfkMax;
	}
	public float getYfkMin() {
		return yfkMin;
	}
	public void setYfkMin(float yfkMin) {
		this.yfkMin = yfkMin;
	}
	public String getSskMax() {
		return sskMax;
	}
	public void setSskMax(String sskMax) {
		this.sskMax = sskMax;
	}
	public float getSskMin() {
		return sskMin;
	}
	public void setSskMin(float sskMin) {
		this.sskMin = sskMin;
	}
	public double getPayRatio() {
		return payRatio;
	}
	public void setPayRatio(double payRatio) {
		this.payRatio = payRatio;
	}
	public String getHtPriceZn() {
		return htPriceZn;
	}
	public void setHtPriceZn(String htPriceZn) {
		this.htPriceZn = htPriceZn;
	}
	public float getSyPrice() {
		return syPrice;
	}
	public void setSyPrice(float syPrice) {
		this.syPrice = syPrice;
	}
	public String getSyPriceMax() {
		return syPriceMax;
	}
	public void setSyPriceMax(String syPriceMax) {
		this.syPriceMax = syPriceMax;
	}
	public String getYfPriceMax() {
		return yfPriceMax;
	}
	public void setYfPriceMax(String yfPriceMax) {
		this.yfPriceMax = yfPriceMax;
	}
	public float getYfPrice() {
		return yfPrice;
	}
	public void setYfPrice(float yfPrice) {
		this.yfPrice = yfPrice;
	}
	public float getPjPrice() {
		return pjPrice;
	}
	public void setPjPrice(float pjPrice) {
		this.pjPrice = pjPrice;
	}
	public String getTotalPriceMax() {
		return totalPriceMax;
	}
	public void setTotalPriceMax(String totalPriceMax) {
		this.totalPriceMax = totalPriceMax;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getCustTel() {
		return custTel;
	}
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	public String getCustBank() {
		return custBank;
	}
	public void setCustBank(String custBank) {
		this.custBank = custBank;
	}
	public String getCustAccount() {
		return custAccount;
	}
	public void setCustAccount(String custAccount) {
		this.custAccount = custAccount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCwUser() {
		return cwUser;
	}
	public void setCwUser(String cwUser) {
		this.cwUser = cwUser;
	}
	public String getCwUserId() {
		return cwUserId;
	}
	public void setCwUserId(String cwUserId) {
		this.cwUserId = cwUserId;
	}
	public float getPay() {
		return pay;
	}
	public void setPay(float pay) {
		this.pay = pay;
	}
	public String getKp() {
		return kp;
	}
	public void setKp(String kp) {
		this.kp = kp;
	}
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
	public float getYhPrice() {
		return yhPrice;
	}
	public void setYhPrice(float yhPrice) {
		this.yhPrice = yhPrice;
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
	
	
	
	
}

