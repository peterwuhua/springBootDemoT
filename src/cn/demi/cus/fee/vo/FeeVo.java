package cn.demi.cus.fee.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.cus.bill.vo.BillVo;

public class FeeVo extends Vo<FeeVo> {
	
	private String billno; //发票编号
	private BillVo billVo;
    private String fpId;

	
/**
 * 申请人
 */
	private String person;
	private String personId;
	

	
	private String payWay;//支付方式
	
	private double feeratio; // 收款比例
	private double receive;//应收款
	
	private double yfje;//预付款
	
	
	
	private String supportDate; //费用申请日期(开据发票的日期)
	private String sumDate; //审批日期
	private String sumUserName;//审批人
	private String sumRemark; //审批意见
	private String fdesc; // 备注

	
	private String fstatus; //收费状态
	
	private String htId;
	private String custId;
	private String pjtId;

	private String contractCode;
	private String customerName;
	

	
	

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public double getReceive() {
		return receive;
	}

	public void setReceive(double receive) {
		this.receive = receive;
	}


	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}


	public String getSupportDate() {
		return supportDate;
	}

	public void setSupportDate(String supportDate) {
		this.supportDate = supportDate;
	}

	public String getSumDate() {
		return sumDate;
	}

	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}

	public String getSumUserName() {
		return sumUserName;
	}

	public void setSumUserName(String sumUserName) {
		this.sumUserName = sumUserName;
	}

	public String getSumRemark() {
		return sumRemark;
	}

	public void setSumRemark(String sumRemark) {
		this.sumRemark = sumRemark;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getFpId() {
		return fpId;
	}

	public void setFpId(String fpId) {
		this.fpId = fpId;
	}

	public double getYfje() {
		return yfje;
	}

	public void setYfje(double yfje) {
		this.yfje = yfje;
	}

	public String getFdesc() {
		return fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}

	public String getFstatus() {
		return fstatus;
	}

	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}

	public String getHtId() {
		return htId;
	}

	public void setHtId(String htId) {
		this.htId = htId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getPjtId() {
		return pjtId;
	}

	public void setPjtId(String pjtId) {
		this.pjtId = pjtId;
	}

	public void setFeeratio(double feeratio) {
		this.feeratio = feeratio;
	}

	public BillVo getBillVo() {
		return billVo;
	}

	public void setBillVo(BillVo billVo) {
		this.billVo = billVo;
	}

	public double getFeeratio() {
		return feeratio;
	}

	
	
	
	
	
}

