package cn.demi.cus.bj.po;

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
 * 报价管理
 * @author QuJunLong
 */
@Entity(name = "cus_budget")
@Table(name = "cus_budget")
@Module(value = "cus.budget")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Budget extends Po<Budget>{
	
	public static final String ST_0 = "未提交";
	public static final String ST_1 = "待确认";
	public static final String ST_2 = "已确认";
	public static final String ST_3 = "已完成";
	
	private static final long serialVersionUID = -5633969031599526490L;
	public String[] PROPERTY_TO_MAP = {"id","sort","isDel","no","custId","custName","custUser","custAddress","custMobile","custEmail","custZip","price","discount","htPrice","bdate","yxq","back","status","taskType","pc","pcUnit","sampTypeId","sampTypeName","taskEnv"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","lastUpdTime","version","orgId","orgName","no","status","back"};
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	private String no;
	//客户信息
	private String custId;
	private String custName;
	private String custUser;
	private String custAddress;
	private String custMobile;
	private String custEmail;
	private String custFax;
	private String custZip;
	private String taskEnv; //监测场景
	private String taskType;//监测类别
	private int pc;//项目频次
	private String pcUnit;//项目频次单位
	//部门
	private String orgId;
	private String orgName;
	
	private String buserId;//报价人
	private String buserName;
	private String bdate;//报价日期
	private String yxq;//有效期

	private String jj;
	private float jjPrice;//加急费
	private float price;//费用总额
	private float discount;//折扣
	private float payPrice;//最终优惠价格
	private float jtPrice;//交通费
	private float bgPrice;//报告费
	private float otherPrice;//税费等其他费用
	private float htPrice;//合同费用
	
	//样品信息
	private String sampTypeId;//样品类别
	private String sampTypeName;
	private String sampName;
	
	private String fileName;//文件
	private String filePath;//文件路径
	
	private String auditId;//审核人
	private String auditName;
	private String auditDate;
	
	private String status;//状态（频次执行状态  2已确认/ 1待确认/ 0未确认）
	private String back;
	private String remark;
	
	@Column(length = 32)
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	@Column(length = 64)
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	@Column(length = 32)
	public String getCustUser() {
		return custUser;
	}
	public void setCustUser(String custUser) {
		this.custUser = custUser;
	}
	@Column(length = 128)
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	@Column(length = 16)
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	@Column(length = 64)
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	@Column(length = 16)
	public String getCustFax() {
		return custFax;
	}
	public void setCustFax(String custFax) {
		this.custFax = custFax;
	}
	@Column(length = 1000)
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length = 1000)
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length = 32)
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
	@Column(length = 16)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 32)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length = 16)
	public String getCustZip() {
		return custZip;
	}
	public void setCustZip(String custZip) {
		this.custZip = custZip;
	}
	@Column(length = 8)
	public String getJj() {
		return jj;
	}
	public void setJj(String jj) {
		this.jj = jj;
	}
	@Column(length = 16)
	public float getJjPrice() {
		return jjPrice;
	}
	public void setJjPrice(float jjPrice) {
		this.jjPrice = jjPrice;
	}
	@Column(length = 16)
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Column(length = 8)
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	@Column(length = 16)
	public float getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(float payPrice) {
		this.payPrice = payPrice;
	}
	@Column(length = 16)
	public float getOtherPrice() {
		return otherPrice;
	}
	public void setOtherPrice(float otherPrice) {
		this.otherPrice = otherPrice;
	}
	@Column(length = 16)
	public float getHtPrice() {
		return htPrice;
	}
	public void setHtPrice(float htPrice) {
		this.htPrice = htPrice;
	}
	@Column(length = 16)
	public float getJtPrice() {
		return jtPrice;
	}
	public void setJtPrice(float jtPrice) {
		this.jtPrice = jtPrice;
	}
	@Column(length = 16)
	public float getBgPrice() {
		return bgPrice;
	}
	public void setBgPrice(float bgPrice) {
		this.bgPrice = bgPrice;
	}
	@Column(length = 32)
	public String getBuserId() {
		return buserId;
	}
	public void setBuserId(String buserId) {
		this.buserId = buserId;
	}
	@Column(length = 32)
	public String getBuserName() {
		return buserName;
	}
	public void setBuserName(String buserName) {
		this.buserName = buserName;
	}
	@Column(length = 20)
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	@Column(length = 32)
	public String getYxq() {
		return yxq;
	}
	public void setYxq(String yxq) {
		this.yxq = yxq;
	}
	@Column(length = 128)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length = 64)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(length = 128)
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(length = 32)	
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	@Column(length = 32)
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	@Column(length = 20)
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	@Column(length = 2)
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	@Column(length = 16)
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	@Column(length = 4)
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	@Column(length = 16)
	public String getPcUnit() {
		return pcUnit;
	}
	public void setPcUnit(String pcUnit) {
		this.pcUnit = pcUnit;
	}
	
	
	
	@Column(length = 32)
	public String getTaskEnv() {
		return taskEnv;
	}
	public void setTaskEnv(String taskEnv) {
		this.taskEnv = taskEnv;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(Budget.class, true, ActionType.JSP);
	}
}
