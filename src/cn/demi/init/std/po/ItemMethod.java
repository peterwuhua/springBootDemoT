package cn.demi.init.std.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 项目方法关系对象
 * @author QuJunLong
 *
 */
@Entity(name = "init_item_method")
@Table(name = "init_item_method")
@Module(value = "init.item_method")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemMethod extends Po<ItemMethod>{

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","item","method","cyll","cysc","cytj","bcsj","cctj",
			"cyAppId","cyAppName","ctId","ctName","remark"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 项目
	 */
	private Item item;
 
	/**
	 * 方法
	 */
	private Method method;
	
	private String cyll;//采样流量
	private String cysc;//采样时长 
	private String cytj;//采样体积
	private float bcsj;//储存时间/h
	private String cctj;//储存条件
	private String cyAppId;//采样设备
	private String cyAppName;
	private String ctId;//采样容器
	private String ctName;
	private String remark;//备注
	@ManyToOne
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	@ManyToOne
	@JoinColumn(name = "method_id")
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	@Column(length = 64)
	public String getCyll() {
		return cyll;
	}
	public void setCyll(String cyll) {
		this.cyll = cyll;
	}
	@Column(length = 64)
	public String getCysc() {
		return cysc;
	}
	public void setCysc(String cysc) {
		this.cysc = cysc;
	}
	@Column(length = 64)
	public String getCytj() {
		return cytj;
	}
	public void setCytj(String cytj) {
		this.cytj = cytj;
	}
	public float getBcsj() {
		return bcsj;
	}
	public void setBcsj(float bcsj) {
		this.bcsj = bcsj;
	}
	@Column(length = 256)
	public String getCctj() {
		return cctj;
	}
	public void setCctj(String cctj) {
		this.cctj = cctj;
	}
	@Column(length = 320)
	public String getCyAppId() {
		return cyAppId;
	}
	public void setCyAppId(String cyAppId) {
		this.cyAppId = cyAppId;
	}
	@Column(length = 640)
	public String getCyAppName() {
		return cyAppName;
	}
	public void setCyAppName(String cyAppName) {
		this.cyAppName = cyAppName;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 320)
	public String getCtId() {
		return ctId;
	}
	public void setCtId(String ctId) {
		this.ctId = ctId;
	}
	@Column(length = 320)
	public String getCtName() {
		return ctName;
	}
	public void setCtName(String ctName) {
		this.ctName = ctName;
	}
	
}
