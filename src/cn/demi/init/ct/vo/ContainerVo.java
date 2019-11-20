package cn.demi.init.ct.vo;

import cn.core.framework.common.vo.Vo;

public class ContainerVo extends Vo<ContainerVo> {
	
	/**
	 * 容器编号
	 */
	private String code;
	/**
	 * 容器名称
	 */
	private String name;
	/**
	 * 容器单位
	 */
	private String unit;
	/**
	 *规格
	 */
	private String maxVal;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getMaxVal() {
		return maxVal;
	}
	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}
	
}

