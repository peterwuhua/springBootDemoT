package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class OrgVo extends Vo<OrgVo> {
	/**
	 * 上级组织
	 */
	private OrgVo parentVo;// 上级组织
	/**
	 * 组织名称
	 */
	private String name;// 组织名称
	/**
	 * 组织编码
	 */
	private String code;// 组织编码
	/**
	 * 组织编码
	 */
	private String type;//类别
	/**
	 * 说明
	 */
	private String describtion;// 说明

	/**
	 * 节点路径
	 */
	private String path;
	
	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public OrgVo getParentVo() {
		return parentVo;
	}

	public void setParentVo(OrgVo parentVo) {
		this.parentVo = parentVo;
	}

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}