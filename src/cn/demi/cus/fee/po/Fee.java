package cn.demi.cus.fee.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.cus.bill.po.Bill;

@Entity(name = "cus_fee")
@Table(name = "cus_fee")
@Module(value = "cus.fee")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fee extends Po<Fee> {

	private static final long serialVersionUID = 1L;
	public String[] PROPERTY_TO_MAP = { "id", "sort","billno","bill","fpId", "person","personId","person","feeratio", "receive","yfje","fdesc","supportDate","sumDate","sumUserName","sumRemark","fstatus","htId","custId","pjtId" ,"payWay","contractCode","customerName"};
	public String[] IGNORE_PROPERTY_TO_PO = { "id", "bill","createTime", "lastUpdTime", "version", "status" };

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}

	private String billno; //发票编号
	private Bill bill;
    private String fpId;

	private String contractCode;
	private String customerName;
    
    
	
/**
 * 申请人
 */
	private String person;
	private String personId;
	
private String payWay;
	
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
	
	
	
	
	
	
	@Column(length=32)
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	@Column(length=64)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(length=10)
	public double getReceive() {
		return receive;
	}

	public void setReceive(double receive) {
		this.receive = receive;
	}


	@Column(length=64)
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
	@Column(length=32)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(length=64)
	public String getSupportDate() {
		return supportDate;
	}

	public void setSupportDate(String supportDate) {
		this.supportDate = supportDate;
	}
	@Column(length=64)
	public String getSumDate() {
		return sumDate;
	}

	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}
	@Column(length=64)
	public String getSumUserName() {
		return sumUserName;
	}

	public void setSumUserName(String sumUserName) {
		this.sumUserName = sumUserName;
	}
	@Lob
	public String getSumRemark() {
		return sumRemark;
	}

	public void setSumRemark(String sumRemark) {
		this.sumRemark = sumRemark;
	}

	

	@Column(length = 10)
	public double getFeeratio() {
		return feeratio;
	}

	public void setFeeratio(double feeratio) {
		this.feeratio = feeratio;
	}


	@Column(length = 500)
	public String getFdesc() {
		return fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}

	@ManyToOne
	@JoinColumn(name = "bill_id")
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}



	@Column(length = 32)
	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}
	@Column(length = 32)
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
	@Column(length = 64)
	public String getFstatus() {
		return fstatus;
	}

	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
	@Column(length = 32)
	public String getHtId() {
		return htId;
	}

	public void setHtId(String htId) {
		this.htId = htId;
	}
	@Column(length = 32)
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	@Column(length = 32)
	public String getPjtId() {
		return pjtId;
	}

	public void setPjtId(String pjtId) {
		this.pjtId = pjtId;
	}

	
	
	@Column(length=32)
	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Fee.class, true, ActionType.JSP);
	}

}
