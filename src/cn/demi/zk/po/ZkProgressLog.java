package cn.demi.zk.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 进度信息 历史记录
 * @author QuJunLong
 *
 */
@Entity(name = "zk_progress_log")
@Table(name = "zk_progress_log")
@Module(value = "zk.progressLog")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ZkProgressLog extends Po<ZkProgressLog>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","busType","org","user","status","msg","startDate","endDate"};
	
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
	 * 业务类型
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
	 * 任务节点状态码
	 */
	private String status;
	/**
	 * 审批码
	 */
	private String audit;
	/**
	 * 备注
	 */
	private String msg;
	/**
	 * 记录日期
	 */
	private String logDate;
	 
	
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
	@Column(length = 64)
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
	@Column(length = 32)
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	@Column(length = 20)
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ZkProgressLog.class, true, ActionType.JSP);
	}
}
