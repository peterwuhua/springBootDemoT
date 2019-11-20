package cn.demi.bus.pjt.vo;

import cn.core.framework.common.vo.Vo;

public class CustWorkVo extends Vo<CustWorkVo> {
	private CustVo custVo;	//客户信息
	private CustRoomVo roomVo;//岗位
	private String projectId;//项目
	private CustPointVo pointVo;//采样点
	private String startTime;//工作开始时间
	private String endTime;//工作 结束时间
	private String workNum;//接触时间
	private String itemIds;//检测项目
	private String itemNames;
	private String works;//工作内容
	private String user;//姓名
	private String duty;//岗位
	private String age;//工龄
	private String remark;//备注
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
	public CustPointVo getPointVo() {
		return pointVo;
	}
	public void setPointVo(CustPointVo pointVo) {
		this.pointVo = pointVo;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getWorkNum() {
		return workNum;
	}
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getWorks() {
		return works;
	}
	public void setWorks(String works) {
		this.works = works;
	}
	
}

