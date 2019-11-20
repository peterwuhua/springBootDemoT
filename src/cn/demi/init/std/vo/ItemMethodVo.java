package cn.demi.init.std.vo;

import cn.core.framework.common.vo.Vo;

public class ItemMethodVo extends Vo<ItemMethodVo> {
 
	private ItemVo itemVo;
	private MethodVo methodVo;
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
	
	public ItemVo getItemVo() {
		return itemVo;
	}
	public void setItemVo(ItemVo itemVo) {
		this.itemVo = itemVo;
	}
	public MethodVo getMethodVo() {
		return methodVo;
	}
	public void setMethodVo(MethodVo methodVo) {
		this.methodVo = methodVo;
	}
	public String getCyll() {
		return cyll;
	}
	public void setCyll(String cyll) {
		this.cyll = cyll;
	}
	public String getCysc() {
		return cysc;
	}
	public void setCysc(String cysc) {
		this.cysc = cysc;
	}
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
	public String getCctj() {
		return cctj;
	}
	public void setCctj(String cctj) {
		this.cctj = cctj;
	}
	public String getCyAppId() {
		return cyAppId;
	}
	public void setCyAppId(String cyAppId) {
		this.cyAppId = cyAppId;
	}
	public String getCyAppName() {
		return cyAppName;
	}
	public void setCyAppName(String cyAppName) {
		this.cyAppName = cyAppName;
	}
	public String getCtId() {
		return ctId;
	}
	public void setCtId(String ctId) {
		this.ctId = ctId;
	}
	public String getCtName() {
		return ctName;
	}
	public void setCtName(String ctName) {
		this.ctName = ctName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

