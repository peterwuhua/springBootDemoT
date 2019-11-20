package cn.demi.base.system.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;
@Entity(name="sys_unit")
@Table(name="sys_unit")
@Module(value="sys.unit")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Unit extends Po<Unit>  {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","address","linkMan","telephone","post","fax","logo","website","email"};
	/**
	 * 名称
	 */
	private String name;//名称
	/**
	 * 地址
	 */
	private String address;//地址
	/**
	 * 联系人
	 */
	private String linkMan;//联系人
	/**
	 * 电话
	 */
	private String telephone;//电话
	/**
	 * 邮编
	 */
	private String post;//邮编
	/**
	 * 传真
	 */
	private String fax;//传真
	/**
	 * logo
	 */
	private String logo;//logo
	/**
	 * 网站
	 */
	private String website;//网站
	/**
	 * 电子邮箱
	 */
	private String email;//电子邮箱
	@Column(length=64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=64)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length=64)
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	@Column(length=64)
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(length=64)
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	@Column(length=64)
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column(length=64)
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@Column(length=64)
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	@Column(length=64)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
}