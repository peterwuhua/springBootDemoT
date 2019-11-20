package cn.demi.init.st.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class SampTypeVo extends Vo<SampTypeVo> {
	
	private List<SampTypeVo> children;
	private boolean hasChild;
	private SampTypeVo parentVo;
	/**
	 * 分类编号
	 */
	private String typeNo;
 
	/**
	 * 水容器开头字母
	 */
	private String packCodeType;
	/**
	 * 样品种类字母
	 */
	private String type;
	/**
	 * 是否启用
	 */
	private String isUsed;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 名称
	 */
	private String name;
	
	public SampTypeVo getParentVo() {
		return parentVo;
	}
	public void setParentVo(SampTypeVo parentVo) {
		this.parentVo = parentVo;
	}
	public String getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}
	public String getPackCodeType() {
		return packCodeType;
	}
	public void setPackCodeType(String packCodeType) {
		this.packCodeType = packCodeType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SampTypeVo> getChildren() {
		return children;
	}
	public void setChildren(List<SampTypeVo> children) {
		this.children = children;
	}
	public boolean isHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
}

