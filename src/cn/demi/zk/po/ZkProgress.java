package cn.demi.zk.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 进度信息
 * @author QuJunLong
 *
 */
@Entity(name = "zk_progress")
@Table(name = "zk_progress")
@Module(value = "zk.progress")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ZkProgress extends Po<ZkProgress>{

 
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","busType","org","user","status","msg","date"};

	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 任务Id
	 */
	private String taskId;
	/**
	 * 进度类型
	 */
	private String busType;
	/**
	 * 部门
	 */
	private String org;
	/**
	 * 处理人
	 */
	private String user;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 备注
	 */
	private String msg;
	
 
	@Column(length = 32)
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	@Column(length = 32)
	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	@Column(length = 32)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 64)
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Column(length = 20)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	 
	@Column(length = 320)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Column(length = 64)
	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}
}
