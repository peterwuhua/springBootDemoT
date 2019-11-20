package cn.demi.bus.pg.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 进度信息 历史记录
 * @author QuJunLong
 *
 */
@Entity(name = "bus_progress_log")
@Table(name = "bus_progress_log")
@Module(value = "bus.progressLog")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProgressLog extends Po<ProgressLog>{

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
	 * 业务Id
	 */
	private String busId;
	/**
	 * 业务类型
	 */
	private String busType;
	/**
	 * 部门
	 */
	private String orgId;
	private String orgName;
	/**
	 * 处理人
	 */
	private String userId;
	private String userName;
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
	 * 记录时间
	 */
	private String logTime;
	 
	
	@Column(length = 32)
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	@Column(length = 32)
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
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
	@Column(length = 256)
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	 
	@Column(length = 32)
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	@Column(length = 20)
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	@Column(length = 32)
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@Column(length = 32)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@Column(length = 320)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 320)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
//	public static void main(String[] args) {
//		CreateCodeUtils.autoCreateCode(ProgressLog.class, true, ActionType.JSP);
//	}
}
