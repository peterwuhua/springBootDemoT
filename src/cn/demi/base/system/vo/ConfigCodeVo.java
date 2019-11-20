package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class ConfigCodeVo extends Vo<ConfigCodeVo> {
	/**
	 * 类型
	 */
	private ConfigTypeVo configTypeVo;// 类型
	/**
	 * key
	 */
	private String key;// key
	/**
	 * 值
	 */
	private String value;// 值
	/**
	 * 状态：是否启用 Y:启用 N：不启用
	 */
	private String isUsed;// 状态：是否启用 Y:启用 N：不启用
	/**
	 * 说明
	 */
	private String describtion;// 说明

	public ConfigTypeVo getConfigTypeVo() {
		return configTypeVo;
	}

	public void setConfigTypeVo(ConfigTypeVo configTypeVo) {
		this.configTypeVo = configTypeVo;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

}
