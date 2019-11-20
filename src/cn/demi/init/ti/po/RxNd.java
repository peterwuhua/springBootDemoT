package cn.demi.init.ti.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.init.std.po.Item;

/**
 * 容许浓度
 * @author QuJunLong
 */
@Entity(name = "init_rx_nd")
@Table(name = "init_rx_nd")
@Module(value = "init.rxNd")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RxNd  extends Po<RxNd>{

 
	private static final long serialVersionUID = -9066953108283601838L;
	public String[] PROPERTY_TO_MAP = {"id","sort","item","name","ename","cas","mac","pctwa","pcstel","remark","cxbs"};
	
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

	private Item item;//项目
	private String name;//中文名     粉尘类型或化学物质
	private String ename;//英文名
	private String cas;//化学文摘号
	private String mac;//MAC
	private String pctwa;//PC-TWA
	private String pcstel;//PC-STEL
	/**
	 *  凡是只有PC-TWA的，就有超限倍数
	 *  PC-TWA＜1       3    
	 *  1≤PC-TWA＜100   2.5
	 *  10≤PC-TWA＜100  2.0
	 *  PC-TWA≥100     1.5
	 */
	private String cxbs;//最大超限倍数     
	private String remark;//备注

	@ManyToOne
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	@Column(length = 128)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 128)
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	@Column(length = 32)
	public String getCas() {
		return cas;
	}
	public void setCas(String cas) {
		this.cas = cas;
	}
	@Column(length = 32)
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	@Column(length = 32)
	public String getPctwa() {
		return pctwa;
	}
	public void setPctwa(String pctwa) {
		this.pctwa = pctwa;
	}
	@Column(length = 32)
	public String getPcstel() {
		return pcstel;
	}
	public void setPcstel(String pcstel) {
		this.pcstel = pcstel;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getCxbs() {
		return cxbs;
	}
	public void setCxbs(String cxbs) {
		this.cxbs = cxbs;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(RxNd.class, true, ActionType.JSP);
	}
	
}
