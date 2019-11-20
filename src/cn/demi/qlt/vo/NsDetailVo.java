package cn.demi.qlt.vo;

import cn.core.framework.common.vo.Vo;

public class NsDetailVo extends Vo<NsDetailVo> {
	private NsVo nsVo;//计划
	private String itemId;//要素Id
	private String code;//要素条款
    private String name;//要素内容
	private String months; //月份
	private String headId; //负责人
	private String headName; 
	private String xzId; //协助人
	private String xzName;
	public NsVo getNsVo() {
		return nsVo;
	}
	public void setNsVo(NsVo nsVo) {
		this.nsVo = nsVo;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	public String getXzId() {
		return xzId;
	}
	public void setXzId(String xzId) {
		this.xzId = xzId;
	}
	public String getXzName() {
		return xzName;
	}
	public void setXzName(String xzName) {
		this.xzName = xzName;
	} 
}

