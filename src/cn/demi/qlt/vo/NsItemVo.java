package cn.demi.qlt.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class NsItemVo extends Vo<NsItemVo> {
	
	private NsItemVo itemVo;
	private String code;//条款
    private String name;//内容
	private int level; //级别
	private List<NsItemVo> subList;
	
	public NsItemVo getItemVo() {
		return itemVo;
	}
	public void setItemVo(NsItemVo itemVo) {
		this.itemVo = itemVo;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public List<NsItemVo> getSubList() {
		return subList;
	}
	public void setSubList(List<NsItemVo> subList) {
		this.subList = subList;
	}
	
}

