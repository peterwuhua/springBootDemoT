package cn.demi.qlt.po;

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
 * 内审计划详情
 * @author QuJunLong
 *
 */
@Entity(name = "qlt_ns_detail")
@Table(name = "qlt_ns_detail")
@Module(value = "qlt.nsDetail")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NsDetail extends Po<NsDetail>{

	private static final long serialVersionUID = 5845524080644846697L;
	public String[] PROPERTY_TO_MAP= {"id","sort"};
	private Ns ns;//计划
	private String itemId;//要素Id
	private String code;//要素条款
    private String name;//要素内容
	private String months; //月份
	private String headId; //负责人
	private String headName; 
	private String xzId; //协助人
	private String xzName; 
	@ManyToOne
	@JoinColumn(name = "ns_id")
	public Ns getNs() {
		return ns;
	}
	public void setNs(Ns ns) {
		this.ns = ns;
	}
	@Column(length=32)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(length=32)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=256)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=64)
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	@Column(length=32)
	public String getHeadId() {
		return headId;
	}
	public void setHeadId(String headId) {
		this.headId = headId;
	}
	@Column(length=16)
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	@Column(length=320)
	public String getXzId() {
		return xzId;
	}
	public void setXzId(String xzId) {
		this.xzId = xzId;
	}
	@Column(length=320)
	public String getXzName() {
		return xzName;
	}
	public void setXzName(String xzName) {
		this.xzName = xzName;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(NsDetail.class, true, ActionType.JSP);
	}
}
