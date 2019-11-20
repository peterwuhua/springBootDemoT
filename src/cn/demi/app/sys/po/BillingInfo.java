package cn.demi.app.sys.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 工作计划
 * 
 * @author QuJunLong
 *
 */
@Entity(name = "office_Billing_info")
@Table(name = "office_Billing_info")
@Module(value = "office.billingInfo")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingInfo extends Po<BillingInfo> {

	private static final long serialVersionUID = 705430075926641642L;

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

	public String[] PROPERTY_TO_MAP = { "id", "companyName", "tfn", "companyAddress", "tel", "depositBank", "depositNum","userId","userName" };
	public String[] IGNORE_PROPERTY_TO_PO = { "id", "createTime", "lastUpdTime", "version", "orgId", "orgName" };

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
	 @Column(length = 256)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	 @Column(length = 80)
	public String getTfn() {
		return tfn;
	}

	public void setTfn(String tfn) {
		this.tfn = tfn;
	}
	 @Column(length = 256)
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	 @Column(length = 40)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	 @Column(length = 120)
	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}
	 @Column(length = 60)
	public String getDepositNum() {
		return depositNum;
	}

	public void setDepositNum(String depositNum) {
		this.depositNum = depositNum;
	}
	 @Column(length = 40)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	 @Column(length = 80)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 代码生成
	 * @param args
	 */
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(BillingInfo.class, false, ActionType.JSP);
	}
}
