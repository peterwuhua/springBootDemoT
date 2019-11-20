package cn.demi.cus.customer.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 拜访管理
 * @author QuJunLong
 *
 */
@Entity(name = "cus_visit")
@Table(name = "cus_visit")
@Module(value = "cus.visit")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Visit extends Po<Visit>{
	 
	private static final long serialVersionUID = -8633628352212658051L;
	public String[] PROPERTY_TO_MAP= {"id","sort","customer","user","userId","vdate","status","userName","result","salerId","saler","contactId","contractId","contractName","leaderId","leader"};
	public String[] IGNORE_PROPERTY_TO_PO= {"id","customer","createTime","lastUpdTime","version"};
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
	private String user;//拜访客户
	private String userId; //客户id
	private String userName;//客户名称
	private String salerId; //销售人员id
	private String saler; //销售人员
	private String leaderId; //领导id
	private String leader; //领导
	
	private String vdate;
	private String status;
	private String result;
	private String contactId; //客户跟踪id
	private String contractId; //合同id
	private String contractName; //合同名称
	
	
	
	@Column(length=32)
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	@Column(length=64)
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	@Column(length=64)
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	@Column(length=32)
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	@Column(length=32)
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	@Column(length=64)
	public String getSaler() {
		return saler;
	}
	public void setSaler(String saler) {
		this.saler = saler;
	}
	@Column(length=32)
	public String getSalerId() {
		return salerId;
	}
	public void setSalerId(String salerId) {
		this.salerId = salerId;
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
	public String getVdate() {
		return vdate;
	}
	public void setVdate(String vdate) {
		this.vdate = vdate;
	}
	@Column(length=16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length=128)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@ManyToOne
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@Column(length=32)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Visit.class, true, ActionType.JSP);
	}
}
