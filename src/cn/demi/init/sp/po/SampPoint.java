package cn.demi.init.sp.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 采用点
 * @author QuJunLong
 *
 */
@Entity(name = "init_samp_point")
@Table(name = "init_samp_point")
@Module(value = "init.samp_point")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SampPoint  extends Po<SampPoint>{
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","code","name","sx","type","sampName","disinfect","sampTypeId","sampTypeName","orgName","itemName","itemId","pc","pcUnit","sbyq"};
 
	/**
	 *点位代码
	 */
	private String code;
	/**
	 *监测点名称
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
	private String csl;//取水量
	//所属部门
	private String orgId;
	private String orgName;
	private String itemId;
	private String itemName;
	private String sbyq;//上报要求
	private String remark;
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
 
	@Column(length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 64)
	public String getSampName() {
		return sampName;
	}
	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length = 64)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 32)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length = 32)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length = 32)
	public String getSampTypeId() {
		return sampTypeId;
	}

	public void setSampTypeId(String sampTypeId) {
		this.sampTypeId = sampTypeId;
	}
	@Column(length = 64)
	public String getSampTypeName() {
		return sampTypeName;
	}

	public void setSampTypeName(String sampTypeName) {
		this.sampTypeName = sampTypeName;
	}
	@Column(length = 32)
	public String getSampId() {
		return sampId;
	}

	public void setSampId(String sampId) {
		this.sampId = sampId;
	}
	@Column(length = 64)
	public String getSx() {
		return sx;
	}

	public void setSx(String sx) {
		this.sx = sx;
	}
	@Column(length = 64)
	public String getHl() {
		return hl;
	}

	public void setHl(String hl) {
		this.hl = hl;
	}
	@Column(length = 64)
	public String getSqdm() {
		return sqdm;
	}

	public void setSqdm(String sqdm) {
		this.sqdm = sqdm;
	}
	@Column(length = 64)
	public String getDxs() {
		return dxs;
	}

	public void setDxs(String dxs) {
		this.dxs = dxs;
	}
	@Column(length = 32)
	public String getGnq() {
		return gnq;
	}

	public void setGnq(String gnq) {
		this.gnq = gnq;
	}
	@Column(length = 32)
	public String getSd() {
		return sd;
	}

	public void setSd(String sd) {
		this.sd = sd;
	}
	@Column(length = 32)
	public String getSp() {
		return sp;
	}
	public void setSp(String sp) {
		this.sp = sp;
	}
	@Column(length = 32)
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 128)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length = 1000)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(length = 1000)
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
	@Column(length = 32)
	public String getPcUnit() {
		return pcUnit;
	}
	public void setPcUnit(String pcUnit) {
		this.pcUnit = pcUnit;
	}
	@Column(length = 128)
	public String getSbyq() {
		return sbyq;
	}
	public void setSbyq(String sbyq) {
		this.sbyq = sbyq;
	}
	@Column(length = 32)
	public String getSxCode() {
		return sxCode;
	}

	public void setSxCode(String sxCode) {
		this.sxCode = sxCode;
	}
	@Column(length = 32)
	public String getJjdmjb() {
		return jjdmjb;
	}

	public void setJjdmjb(String jjdmjb) {
		this.jjdmjb = jjdmjb;
	}
	@Column(length = 32)
	public String getWgdmjb() {
		return wgdmjb;
	}

	public void setWgdmjb(String wgdmjb) {
		this.wgdmjb = wgdmjb;
	}
	@Column(length = 32)
	public String getYgdmjb() {
		return ygdmjb;
	}

	public void setYgdmjb(String ygdmjb) {
		this.ygdmjb = ygdmjb;
	}
	@Column(length = 32)
	public String getHlCode() {
		return hlCode;
	}

	public void setHlCode(String hlCode) {
		this.hlCode = hlCode;
	}
	@Column(length = 32)
	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	@Column(length = 32)
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	@Column(length = 32)
	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}
	@Column(length = 32)
	public String getCsl() {
		return csl;
	}

	public void setCsl(String csl) {
		this.csl = csl;
	}
	
}
