package cn.demi.bus.task.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class TaskRoomVo extends Vo<TaskRoomVo> {
	
	private String taskId;
	private String custId;//客户信息
	private String name;
	private String remark;
	
	private List<TaskPointVo> pointList;

	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<TaskPointVo> getPointList() {
		return pointList;
	}
	public void setPointList(List<TaskPointVo> pointList) {
		this.pointList = pointList;
	}
}

