package cn.demi.init.ti.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.init.std.vo.ItemVo;
/**
 * 容许浓度
 * @author QuJunLong
 */
public class RxNdVo extends Vo<RxNdVo> {
	
	private ItemVo itemVo;//项目
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
	public ItemVo getItemVo() {
		return itemVo;
	}
	public void setItemVo(ItemVo itemVo) {
		this.itemVo = itemVo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getCas() {
		return cas;
	}
	public void setCas(String cas) {
		this.cas = cas;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getPctwa() {
		return pctwa;
	}
	public void setPctwa(String pctwa) {
		this.pctwa = pctwa;
	}
	public String getPcstel() {
		return pcstel;
	}
	public void setPcstel(String pcstel) {
		this.pcstel = pcstel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCxbs() {
		return cxbs;
	}
	public void setCxbs(String cxbs) {
		this.cxbs = cxbs;
	}
}

