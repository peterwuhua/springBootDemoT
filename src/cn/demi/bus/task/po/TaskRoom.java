package cn.demi.bus.task.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 职卫
 *车间或岗位
 * @author QuJunLong
 *
 */
@Entity(name = "bus_task_room")
@Table(name = "bus_task_room")
@Module(value = "bus.taskRoom")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskRoom extends Po<TaskRoom>{
	
	private static final long serialVersionUID = 7998153638582744154L;
	public String[] PROPERTY_TO_MAP = {"id","sort"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 业务Id
	 */
	private String taskId;
	private String custId;//客户信息
	private String name;
	private String remark;
	@Column(length = 32)
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	@Column(length = 128)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(TaskRoom.class, true, ActionType.JSP);
	}
}
