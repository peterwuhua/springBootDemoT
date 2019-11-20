package cn.demi.res.po;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/** <strong>Create on : 2017年2月28日 下午1:30:31 </strong> <br>
 * <strong>Description :供应商管理表 </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Hans He</strong><br>
 */
@Entity(name = "res_supplier")
@Table(name = "res_supplier")
@Module(value = "res.supplier")
public class Supplier extends Po<Supplier> {
	private static final long serialVersionUID = 1L;
	private String no;//供应商编号
	private String name;//供应商名称
	private String companytype;//公司类型
	private String foundingTime;//成立时间
	private String productType;//供应产品类型
	private String relationBuildTime;//关系建立时间
	private String linkman;//联系人
	private String linkmanTel;//联系电话
	private String email;//邮箱
	private String fax;//传真
	private String postcode;//邮编
	private String address;//公司地址
	private String division;//级别
	private String review;//审核
	private String filePath;//附件地址
	private String fileName;//附件名称
	private String remark;//备注
	
	private String accountName;//账户名称
	private String bankName;//开户行
	private String bankNumber;//帐号
	private String productDetails;//供应产品详情
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","no","name","companytype","foundingTime","productType","relationBuildTime","linkman","linkmanTel","email","fax","postcode","address","division","review","fileUrl",
			"trueName","remark","accountName","bankName","bankNumber","productDetails"};


	public String getNo() {
		return no;
	}


	public void setNo(String no) {
		this.no = no;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCompanytype() {
		return companytype;
	}


	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}


	public String getFoundingTime() {
		return foundingTime;
	}


	public void setFoundingTime(String foundingTime) {
		this.foundingTime = foundingTime;
	}


	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	public String getRelationBuildTime() {
		return relationBuildTime;
	}


	public void setRelationBuildTime(String relationBuildTime) {
		this.relationBuildTime = relationBuildTime;
	}


	public String getLinkman() {
		return linkman;
	}


	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}


	public String getLinkmanTel() {
		return linkmanTel;
	}


	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFax() {
		return fax;
	}


	public void setFax(String fax) {
		this.fax = fax;
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getDivision() {
		return division;
	}


	public void setDivision(String division) {
		this.division = division;
	}


	public String getReview() {
		return review;
	}


	public void setReview(String review) {
		this.review = review;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Lob
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getBankNumber() {
		return bankNumber;
	}


	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}


	public String getProductDetails() {
		return productDetails;
	}


	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	@Transient
	@Override
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

}
