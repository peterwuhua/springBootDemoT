package cn.demi.base.system.vo;

import org.springframework.web.multipart.MultipartFile;

import cn.core.framework.common.vo.Vo;

public class UnitVo extends Vo<UnitVo> {
	public static final String SYS_LOGO = "sys-logo";
	/**
	 * 组织名称
	 */
	private String name;// 组织名称
	/**
	 * 组织地址
	 */
	private String address;// 组织地址
	/**
	 * 联系人
	 */
	private String linkMan;// 联系人
	/**
	 * 电话
	 */
	private String telephone;// 电话
	/**
	 * 邮编
	 */
	private String post;// 邮编
	/**
	 * 传真
	 */
	private String fax;// 传真
	/**
	 * logo
	 */
	private String logo;// logo
	/**
	 * 网站
	 */
	private String website;// 网站
	/**
	 * 电子邮箱
	 */
	private String email;// 电子邮箱
	/**
	 * 附件
	 */
	private MultipartFile file;// 附件

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}