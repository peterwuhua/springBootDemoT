package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class ExportxVo extends Vo<ExportxVo> {
	/**
	 * 业务模块
	 */
	private String busType;//业务模块
	/**
	 * 名称
	 */
	private String name;//名称
	/**
	 * 类型
	 */
	private String code;//类型
	/**
	 * 值
	 */
	private String content;//值
	/**
	 * 说明
	 */
	private String describtion;//说明

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
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

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

}