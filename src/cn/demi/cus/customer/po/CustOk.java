package cn.demi.cus.customer.po;

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

/**
 * 客户满意度调查表
 * @author QuJunLong
 *
 */
@Entity(name = "cus_cust_ok")
@Table(name = "cus_cust_ok")
@Module(value = "cus.custOk")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustOk extends Po<CustOk>{
	
	private static final long serialVersionUID = 6439994348822614513L;
	public String[] PROPERTY_TO_MAP= {"id","sort","customer","pf","custUser","custMobile","resean","userName","cdate"};
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","userId","userName","orgId","orgName"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	private Customer customer;
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
	
	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Column(length=4)
	public int getPf() {
		return pf;
	}
	public void setPf(int pf) {
		this.pf = pf;
	}
	@Column(length=32)
	public String getCustUser() {
		return custUser;
	}
	public void setCustUser(String custUser) {
		this.custUser = custUser;
	}
	@Column(length=16)
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	@Column(length=128)
	public String getResean() {
		return resean;
	}
	public void setResean(String resean) {
		this.resean = resean;
	}
	@Column(length=32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length=32)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length=32)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length=20)
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(CustOk.class, true, ActionType.JSP);
	}
}
