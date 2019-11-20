package cn.demi.cus.customer.vo;

import cn.core.framework.common.vo.Vo;

public class CustOkVo extends Vo<CustOkVo> {
	
	private CustomerVo customerVo;
	private int pf;//评分
	private String custUser;//调查人
	private String custMobile;//调查电话
	private String resean;//意见
	private String cdate;//调查时间
	private String userId;//录入人
	private String userName;
	private String orgId;
	private String orgName;
	private String remark;//备注
	public CustomerVo getCustomerVo() {
		return customerVo;
	}
	public void setCustomerVo(CustomerVo customerVo) {
		this.customerVo = customerVo;
	}
	public int getPf() {
		return pf;
	}
	public void setPf(int pf) {
		this.pf = pf;
	}
	public String getCustUser() {
		return custUser;
	}
	public void setCustUser(String custUser) {
		this.custUser = custUser;
	}
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	public String getResean() {
		return resean;
	}
	public void setResean(String resean) {
		this.resean = resean;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

