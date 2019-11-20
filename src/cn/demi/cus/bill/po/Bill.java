package cn.demi.cus.bill.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.pjt.po.Project;
import cn.demi.cus.contract.po.Contract;
import cn.demi.cus.customer.po.Customer;

@Entity(name = "cus_bill")
@Table(name = "cus_bill")
@Module(value = "cus.bill")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bill extends Po<Bill>{

	private static final long serialVersionUID = 1L;
	public String[] PROPERTY_TO_MAP = { "id", "sort","billno" ,"contract","contractCode","itemno","project","customer","person","personId","supportDate","sumDate","sumUserName","sumRemark","fstatus","ftype","fdesc","htId","custId","pjtId","fpje","htje","pstatus"};
	public String[] IGNORE_PROPERTY_TO_PO = { "id","customer" ,"contract","project","createTime", "lastUpdTime", "version", "status" };

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
	
	private String htId;
	private String custId;
	private String pjtId;
	
	private String billno;//发票编号
	
	private Contract contract;
	
	private String contractCode;//合同编号
	
	private String itemno; //项目编号
	
	private Project project;
	private Customer customer; //客户
	private String custName;
	private String custCode;
	private String address;
	private String telephone;
	private String custBank;
	private String custAccount;
    private String person; //申请人
    private String personId;
	
    private String fdesc;//备注
    
	private String supportDate; //开票日期
	
	private String sumDate; //审批日期
	private String sumUserName;//审批人
	private String sumRemark; //审批意见
	
	private String fstatus; //发票状态
	
	private String ftype; //发票类型（增值税普通发票，增值税专用发票）
	
	private double fpje;//发票金额
	
	private double htje;//合同金额
	
	private String pstatus;//收费状态（）
	
	
	@Column(length = 64)
	public String getPstatus() {
		return pstatus;
	}

	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}

	public double getHtje() {
		return htje;
	}

	public void setHtje(double htje) {
		this.htje = htje;
	}

	public double getFpje() {
		return fpje;
	}

	public void setFpje(double fpje) {
		this.fpje = fpje;
	}

	@Column(length = 32)
	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	@Column(length = 64)
	public String getFstatus() {
		return fstatus;
	}

	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}

	@Column(length = 500)
	public String getFdesc() {
		return fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}


	@Column(length = 64)
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
	@Column(length = 32)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	@Column(length = 64)
	public String getSumDate() {
		return sumDate;
	}

	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}
	@Column(length = 64)
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

	@Column(length = 32)
	public String getSupportDate() {
		return supportDate;
	}

	public void setSupportDate(String supportDate) {
		this.supportDate = supportDate;
	}

	@Column(length = 32)
	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}
	
	@Column(length = 32)
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	@Column(length = 32)
	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}


	@ManyToOne
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@ManyToOne
	@JoinColumn(name = "contract_id")
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	@ManyToOne
	@JoinColumn(name = "project_id")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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
	@Column(length = 128)
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	@Column(length = 64)
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	@Column(length = 128)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length = 64)
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(length = 128)
	public String getCustBank() {
		return custBank;
	}
	public void setCustBank(String custBank) {
		this.custBank = custBank;
	}
	@Column(length = 64)
	public String getCustAccount() {
		return custAccount;
	}
	public void setCustAccount(String custAccount) {
		this.custAccount = custAccount;
	}
	
}
