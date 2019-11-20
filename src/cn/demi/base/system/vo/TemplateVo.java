package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class TemplateVo extends Vo<TemplateVo> {
	/**
	 * 标记模板类别（导入模板/导出模板）
	 */
	private String type;//标记模板类别（导入模板/导出模板）
	/**
	 * 模板名称
	 */
	private String name;//模板名称
	/**
	 * 模板编码
	 */
	private String code;//模板编码
	/**
	 * 负责人
	 */
	private String user;//负责人
	/**
	 * 版本号
	 */
	private String versionNo;//版本号
	/**
	 * 说明
	 */
	private String describtion;//说明
	/**
	 * 业务模块
	 */
	private String busType;//业务模块
	/**
	 * 存储路径
	 */
	private String path;//存储路径
	 
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
