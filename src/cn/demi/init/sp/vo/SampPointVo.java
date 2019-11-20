package cn.demi.init.sp.vo;

import cn.core.framework.common.vo.Vo;

public class SampPointVo extends Vo<SampPointVo> {
	
	/**
	 *点位代码
	 */
	private String code;
	/**
	 *采样点名称
	 */
	private String name;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 样品类别
	 */
	private String sampTypeId;	
	/**
	 * 样品类别
	 */
	private String sampTypeName;
	/**
	 *样品名称
	 */
	private String sampId;
	private String sampName;
	/**
	 *类型
	 */
	private String type;
	/**
	 *市县
	 */
	private String sx;
	private String sxCode;
	
	private String jjdmjb;//交接断面管辖级别
	private String wgdmjb;//网管断面级别
	private String ygdmjb;//域管断面级别
	
	/**
	 *河流
	 */
	private String hl;
	private String hlCode;
	private String sqdm;//水期代码
	private String dxs;//地下水层名称
	private String gnq;//功能区
	private String sd;//采样深度
	private String sp;//水平点代码
	private String cz;//垂直点代码
	private String lng;//经度
	private String lat;//纬度
	private Integer pc;//频次
	private String pcUnit;
	private String lx;//流向
	private String csl;//年采水量
	//所属部门
	private String orgId;
	private String orgName;
	private String itemId;
	private String itemName;
	private String remark;
	private String sbyq;//上报要求
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSampTypeId() {
		return sampTypeId;
	}
	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	public String getSampTypeName() {
		return sampTypeName;
	}
	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	public String getSampId() {
		return sampId;
	}
	public void setSampId(String sampId) {
		this.sampId = sampId;
	}
	public String getSx() {
		return sx;
	}
	public void setSx(String sx) {
		this.sx = sx;
	}
	public String getHl() {
		return hl;
	}
	public void setHl(String hl) {
		this.hl = hl;
	}
	public String getSqdm() {
		return sqdm;
	}
	public void setSqdm(String sqdm) {
		this.sqdm = sqdm;
	}
	public String getDxs() {
		return dxs;
	}
	public void setDxs(String dxs) {
		this.dxs = dxs;
	}
	public String getGnq() {
		return gnq;
	}
	public void setGnq(String gnq) {
		this.gnq = gnq;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public String getSp() {
		return sp;
	}
	public void setSp(String sp) {
		this.sp = sp;
	}
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getPc() {
		return pc;
	}
	public void setPc(Integer pc) {
		this.pc = pc;
	}
	public String getPcUnit() {
		return pcUnit;
	}
	public void setPcUnit(String pcUnit) {
		this.pcUnit = pcUnit;
	}
	public String getSbyq() {
		return sbyq;
	}
	public void setSbyq(String sbyq) {
		this.sbyq = sbyq;
	}
	public String getSxCode() {
		return sxCode;
	}
	public void setSxCode(String sxCode) {
		this.sxCode = sxCode;
	}
	public String getJjdmjb() {
		return jjdmjb;
	}
	public void setJjdmjb(String jjdmjb) {
		this.jjdmjb = jjdmjb;
	}
	public String getWgdmjb() {
		return wgdmjb;
	}
	public void setWgdmjb(String wgdmjb) {
		this.wgdmjb = wgdmjb;
	}
	public String getYgdmjb() {
		return ygdmjb;
	}
	public void setYgdmjb(String ygdmjb) {
		this.ygdmjb = ygdmjb;
	}
	public String getHlCode() {
		return hlCode;
	}
	public void setHlCode(String hlCode) {
		this.hlCode = hlCode;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public String getCsl() {
		return csl;
	}
	public void setCsl(String csl) {
		this.csl = csl;
	}
	
}

