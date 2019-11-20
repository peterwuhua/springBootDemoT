package cn.demi.bus.pjt.vo;

import cn.core.framework.common.vo.Vo;

public class CustMaterialVo extends Vo<CustMaterialVo> {
	private CustVo custVo;//客户信息
	private CustRoomVo roomVo;//岗位
	private String projectId;//项目
	private String type;//类型     原铺材料、产品、副产品、中间产品
	private String name;//物料名称
	private String cts;//主要成分及含量
	private String xz;//性状
	private String yl;//用量 或产量
	private String utime;//使用/生产时间
	private String useType;//接触方式说明
	public CustVo getCustVo() {
		return custVo;
	}
	public void setCustVo(CustVo custVo) {
		this.custVo = custVo;
	}
	public CustRoomVo getRoomVo() {
		return roomVo;
	}
	public void setRoomVo(CustRoomVo roomVo) {
		this.roomVo = roomVo;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCts() {
		return cts;
	}
	public void setCts(String cts) {
		this.cts = cts;
	}
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	public String getYl() {
		return yl;
	}
	public void setYl(String yl) {
		this.yl = yl;
	}
	public String getUtime() {
		return utime;
	}
	public void setUtime(String utime) {
		this.utime = utime;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
}

