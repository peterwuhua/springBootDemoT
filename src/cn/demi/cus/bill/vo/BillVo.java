package cn.demi.cus.bill.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.cus.contract.vo.ContractVo;
import cn.demi.cus.customer.vo.CustomerVo;

public class BillVo extends Vo<BillVo> {
	private String billno;//发票编号
	
	private String contractCode;//合同编号
	
	private ContractVo contractVo; 
	
	
	private String itemno; //项目编号
	
	private ProjectVo projectVo;
	
	
	private CustomerVo customerVo; //客户
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
	
	private String fstatus; //发票状态（未审核，已审核,未开据，已开据）

	private String ftype; //发票类型
	
	
	private String htId;
	private String custId;
	private String pjtId;	
	
	
	
	
	private String customerId;
	private String contractId;
	private String projectId;
	
	private double fpje;//发票金额
	

	private double htje;//合同金额
	
	private String pstatus;//收费状态（）
	
	
	
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


	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public ProjectVo getProjectVo() {
		return projectVo;
	}

	public void setProjectVo(ProjectVo projectVo) {
		this.projectVo = projectVo;
	}

	public ContractVo getContractVo() {
		return contractVo;
	}

	public void setContractVo(ContractVo contractVo) {
		this.contractVo = contractVo;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}


	public CustomerVo getCustomerVo() {
		return customerVo;
	}

	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
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


	public String getFdesc() {
		return fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
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

	public String getFstatus() {
		return fstatus;
	}

	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
}

