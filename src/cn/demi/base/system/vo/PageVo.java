package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class PageVo extends Vo<PageVo> {
	/**
	 * 页面编码
	 */
	private String code;// 页面编码
	/**
	 * 页面名称
	 */
	private String name;// 页面名称
	/**
	 * 页面描述
	 */
	private String describtion;// 页面描述
	/**
	 * 页面级别
	 */
	private String grade;// 页面级别
	/**
	 * 页面路径
	 */
	private String path;// 页面路径

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

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
