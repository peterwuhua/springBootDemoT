package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class AreaVo extends Vo<AreaVo> {
	/**
	 * 上级
	 */
	private AreaVo parentVo;// 上级
	/**
	 * 名称
	 */
	private String name;// 名称
	/**
	 * 编码
	 */
	private String code;// 编码
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

	public AreaVo getParentVo() {
		return parentVo;
	}

	public void setParentVo(AreaVo parentVo) {
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

}