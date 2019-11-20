package cn.demi.bus.sample.vo;

import cn.core.framework.common.vo.Vo;

public class SampContainerVo extends Vo<SampContainerVo> {
	/**
	 * 任务编号
	 */
	private String no;
	/**
	 * 任务Id
	 */
	private String taskId;
	/**
	 * 点位Id
	 */
	private String pointId;
	/**
	 * 样品Id
	 * 自送样需要
	 */
	private String sampId;
	/**
	 * 容器
	 */
	private String containerId;
	/**
	 * 容器
	 */
	private String container;
	/**
	 * 数量
	 */
	private int num;
	/**
	 * 备注
	 */
	private String remark;
	public String getSampId() {
		return sampId;
	}
	public void setSampId(String sampId) {
		this.sampId = sampId;
	}
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getContainerId() {
		return containerId;
	}
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	
}

