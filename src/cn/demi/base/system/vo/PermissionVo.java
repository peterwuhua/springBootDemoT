package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class PermissionVo extends Vo<PermissionVo> {
	/**
	 * 权限名称
	 */
	private String name;// 角色名称
	/**
	 * 权限说明
	 */
	private String describtion;// 说明
	/**
	 * 权限编码
	 */
	private String code;
	/**
	 * 所属功能
	 */
	private FunctionVo functionVo;// 功能
	
	private String funId;//功能ID
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public FunctionVo getFunctionVo() {
		return functionVo;
	}
	public void setFunctionVo(FunctionVo functionVo) {
		this.functionVo = functionVo;
	}
	public String getFunId() {
		return funId;
	}
	public void setFunId(String funId) {
		this.funId = funId;
	}
	
	
}

