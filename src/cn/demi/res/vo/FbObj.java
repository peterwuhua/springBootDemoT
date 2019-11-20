package cn.demi.res.vo;

public class FbObj{
	/**
	 * 单位名称
	 */
	private String id;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 单位名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	 
	private String userName;
	/**
	 * 备注
	 */
	private String remark;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}

