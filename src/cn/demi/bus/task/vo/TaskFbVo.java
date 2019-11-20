package cn.demi.bus.task.vo;

import cn.core.framework.common.vo.Vo;
import cn.demi.res.vo.SupplierVo;

public class TaskFbVo extends Vo<TaskFbVo> {
	
	private TaskVo taskVo;
	private SupplierVo fbVo;
	private String fbUser;//联系人
	private String fbMobile;//联系电话
	private int num;//分包数量
	private String itemIds;//分包项目
	private String itemNames;//分包项目
	private float price;//分包费用
	private String remark;//备注
	 
	public SupplierVo getFbVo() {
		return fbVo;
	}
	public void setFbVo(SupplierVo fbVo) {
		this.fbVo = fbVo;
	}
	public String getFbUser() {
		return fbUser;
	}
	public void setFbUser(String fbUser) {
		this.fbUser = fbUser;
	}
	public String getFbMobile() {
		return fbMobile;
	}
	public void setFbMobile(String fbMobile) {
		this.fbMobile = fbMobile;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TaskVo getTaskVo() {
		return taskVo;
	}
	public void setTaskVo(TaskVo taskVo) {
		this.taskVo = taskVo;
	}
}

