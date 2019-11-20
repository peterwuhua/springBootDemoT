package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class RoleVo extends Vo<RoleVo> {
	/**
	 * 角色名称
	 */
	private String name;// 角色名称
	/**
	 * 是否可用
	 */
	private String isUsed;// 是否可用
	/**
	 * 说明
	 */
	private String describtion;// 说明
	/**
	 * 角色编码
	 */
	private String code; // 角色编码
	/**
	 * functionIds
	 */
	private String functionIds;
	
	private String funNames;
	/**
	 * 首页功能
	 */
	private FunctionVo mainFunVo; // 首页功能

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public FunctionVo getMainFunVo() {
		return mainFunVo;
	}

	public void setMainFunVo(FunctionVo mainFunVo) {
		this.mainFunVo = mainFunVo;
	}

	public String getFunNames() {
		return funNames;
	}

	public void setFunNames(String funNames) {
		this.funNames = funNames;
	}
	

}