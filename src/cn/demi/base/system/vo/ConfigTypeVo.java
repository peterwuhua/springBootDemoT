package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class ConfigTypeVo extends Vo<ConfigTypeVo> {
	/**
	 * 业务模块
	 */
	private String busInfo;// 业务模块
	/**
	 * 配置级别的类型名称
	 */
	private String name; // 配置级别的类型名称
	/**
	 * code码
	 */
	private String code;// code码
	/**
	 * 说明
	 */
	private String describtion;// 说明

	public String getBusInfo() {
		return busInfo;
	}

	public void setBusInfo(String busInfo) {
		this.busInfo = busInfo;
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

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

}
