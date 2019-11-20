package cn.demi.office.vo;

import cn.core.framework.common.vo.Vo;

public class OfficeSuppliesDetailVo extends Vo<OfficeSuppliesDetailVo> {
	private String bgname;//办公用品名称
	private Integer sl;//数量
	private String dw;//单位名称
	private String remark;//备注
	private String pid;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getBgname() {
		return bgname;
	}
	public void setBgname(String bgname) {
		this.bgname = bgname;
	}
	public Integer getSl() {
		return sl;
	}
	public void setSl(Integer sl) {
		this.sl = sl;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}

