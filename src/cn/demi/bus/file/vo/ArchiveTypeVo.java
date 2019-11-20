package cn.demi.bus.file.vo;

import cn.core.framework.common.vo.Vo;

public class ArchiveTypeVo extends Vo<ArchiveTypeVo> {
	
	/**
	 * 上级文档类型
	 */
	private ArchiveTypeVo parentVo;
	/**
	 * 文档类型名称
	 */
	private String name;
	/**
	 * 文档类型编码
	 */
	private String code;
	/**
	 * 创建时间
	 */
	private String time;
	/**
	 * 文档类型说明
	 */
	private String describtion;// 说明
	
	private boolean hasChild;
	public ArchiveTypeVo getParentVo() {
		return parentVo;
	}
	public void setParentVo(ArchiveTypeVo parentVo) {
		this.parentVo = parentVo;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	public boolean isHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
}

