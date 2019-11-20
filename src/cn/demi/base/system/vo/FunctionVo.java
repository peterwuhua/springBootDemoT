package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class FunctionVo extends Vo<FunctionVo> {
	/**
	 * 功能名称
	 */
	private String name;// 功能名称
	/**
	 * 功能编码
	 */
	private String code; // 功能编码
	/**
	 * 流程编码
	 */
	private String wfCode; // 流程编码
	/**
	 * 功能路径
	 */
	private String url;// 功能路径
	/**
	 * imageUrl
	 */
	private String imageUrl;
	/**
	 * 父功能
	 */
	private FunctionVo parentVo;// 父功能
	/**
	 * 功能类型
	 */
	private String type;// 功能类型
	/**
	 * 说明
	 */
	private String describtion;// 说明

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public FunctionVo getParentVo() {
		return parentVo;
	}

	public void setParentVo(FunctionVo parentVo) {
		this.parentVo = parentVo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWfCode() {
		return wfCode;
	}

	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}

}