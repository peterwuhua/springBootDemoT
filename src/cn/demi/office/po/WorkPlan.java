package cn.demi.office.po;

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
 * 工作计划
 * @author QuJunLong
 *
 */
@Entity(name = "office_work_plan")
@Table(name = "office_work_plan")
@Module(value = "office.workPlan")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkPlan extends Po<WorkPlan>{

	//类型
	private String type;
	//标题
	private String title;
	//内容
	private String content;
	//用户
	private String userId;
	private String userName;
	//部门
	private String orgId;
	private String orgName;
	//紧急程度
	private String urgentLevel;
	private String startTime;
	private String endTime;
	private String dateStr;
	private static final long serialVersionUID = -5444576869469615071L;
	public String[] PROPERTY_TO_MAP= {"id","sort","orgName","title","type","userName","urgentLevel","startTime","endTime"};
	/**
	 * 对象转换为po需要例外的属性
	 */
	public String[] IGNORE_PROPERTY_TO_PO = {"id","createTime","lastUpdTime","version","orgId","orgName"};
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	@Override
	@Transient
	public String[] getIgnorePropertyToPo() {
		return IGNORE_PROPERTY_TO_PO;
	}
	@Column(length = 32)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 32)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length = 256)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 16)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
	@Column(length = 16)
	public String getUrgentLevel() {
		return urgentLevel;
	}
	public void setUrgentLevel(String urgentLevel) {
		this.urgentLevel = urgentLevel;
	}
	@Column(length = 20)
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(length = 20)
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(length = 1000)
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	/**
	 * 代码生成
	 * @param args
	 */
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(WorkPlan.class, false, ActionType.JSP);
	}
}
