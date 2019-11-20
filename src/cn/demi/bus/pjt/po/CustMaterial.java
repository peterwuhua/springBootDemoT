package cn.demi.bus.pjt.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 岗位 物料
 * @author QuJunLong
 *
 */
@Entity(name = "bus_cust_material")
@Table(name = "bus_cust_material")
@Module(value = "bus.custMaterial")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustMaterial extends Po<CustMaterial>{
	
	private static final long serialVersionUID = 7927144856926940987L;
	public String[] PROPERTY_TO_MAP = { "id", "isDel","sort"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	private Cust cust;//客户信息
	private CustRoom room;//岗位
	private String projectId;//项目
	private String type;//类型     原铺材料、产品、副产品、中间产品
	private String name;//物料名称
	private String cts;//主要成分及含量
	private String xz;//性状
	private String yl;//用量 或产量
	private String utime;//使用/生产时间
	private String useType;//接触方式说明
	
	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}
	public void setCust(Cust cust) {
		this.cust = cust;
	}
	@ManyToOne
	@JoinColumn(name = "room_id")
	public CustRoom getRoom() {
		return room;
	}
	public void setRoom(CustRoom room) {
		this.room = room;
	}
	@Column(length =32)
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Column(length =32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length =128)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length =256)
	public String getCts() {
		return cts;
	}
	public void setCts(String cts) {
		this.cts = cts;
	}
	@Column(length =128)
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	@Column(length =64)
	public String getYl() {
		return yl;
	}
	public void setYl(String yl) {
		this.yl = yl;
	}
	@Column(length =20)
	public String getUtime() {
		return utime;
	}
	public void setUtime(String utime) {
		this.utime = utime;
	}
	@Column(length =256)
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(CustMaterial.class, true, ActionType.JSP);
	}
	
}
