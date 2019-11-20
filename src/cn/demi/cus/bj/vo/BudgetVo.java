package cn.demi.cus.bj.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class BudgetVo extends Vo<BudgetVo> {
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
	
	private String status;//状态（频次执行状态  1已确认/ 0未确认）
	private String back;
	private String remark;
	
	
	
	
	
	public String getTaskEnv() {
		return taskEnv;
	}
	public void setTaskEnv(String taskEnv) {
		this.taskEnv = taskEnv;
	}
	private List<BudgetDetailVo> detailList;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustUser() {
		return custUser;
	}
	public void setCustUser(String custUser) {
		this.custUser = custUser;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public String getCustFax() {
		return custFax;
	}
	public void setCustFax(String custFax) {
		this.custFax = custFax;
	}
	public String getCustZip() {
		return custZip;
	}
	public void setCustZip(String custZip) {
		this.custZip = custZip;
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
	public String getBuserId() {
		return buserId;
	}
	public void setBuserId(String buserId) {
		this.buserId = buserId;
	}
	public String getBuserName() {
		return buserName;
	}
	public void setBuserName(String buserName) {
		this.buserName = buserName;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	public String getYxq() {
		return yxq;
	}
	public void setYxq(String yxq) {
		this.yxq = yxq;
	}
	public String getJj() {
		return jj;
	}
	public void setJj(String jj) {
		this.jj = jj;
	}
	public float getJjPrice() {
		return jjPrice;
	}
	public void setJjPrice(float jjPrice) {
		this.jjPrice = jjPrice;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public float getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(float payPrice) {
		this.payPrice = payPrice;
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
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<BudgetDetailVo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<BudgetDetailVo> detailList) {
		this.detailList = detailList;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getAuditName() {
		return auditName;
	}
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public String getPcUnit() {
		return pcUnit;
	}
	public void setPcUnit(String pcUnit) {
		this.pcUnit = pcUnit;
	}
}

