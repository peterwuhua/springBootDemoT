package cn.demi.app.sys.vo;

import cn.core.framework.common.vo.Vo;

public class AppBillingInfoVo  {
	private String id;
	
	// 单位名称
	private String companyName;
	// 税号
	private String tfn;
	// 单位地址
	private String companyAddress;
	// 电话号码
	private String tel;
	// 开户银行
	private String depositBank;
	// 银行账号
	private String depositNum;
	//人员id
	private String userId;
	//人员姓名
	private String userName;
	private String isDel;
	
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTfn() {
		return tfn;
	}
	public void setTfn(String tfn) {
		this.tfn = tfn;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDepositBank() {
		return depositBank;
	}
	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}
	public String getDepositNum() {
		return depositNum;
	}
	public void setDepositNum(String depositNum) {
		this.depositNum = depositNum;
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
	
	
}

