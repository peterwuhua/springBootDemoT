package cn.demi.init.ti.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * 检测配置
 * @author QuJunLong
 */
@Entity(name = "init_task_init")
@Table(name = "init_task_init")
@Module(value = "init.task_init")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskInit  extends Po<TaskInit>{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","itemName","ename","methodName","appName","orgName","ll","hours","jzName","isMay","conditions"};
	
	public String[] IGNORE_PROPERTY_TO_PO= {"id","createTime","orgId","orgName","userName","userId"};
	@Override
	@Transient
	public String[] getIgnorePropertyToPo(){
		return IGNORE_PROPERTY_TO_PO;
	}
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	 
	/**
	 * 检测项目
	 */
	private String itemId;
	private String itemName;
	private String ename;
	/**
	 * 方法
	 */
	private String methodId;
	private String methodName;
	/**
	 *仪器
	 */
	private String appId;
	private String appName;
	//部门
	private String orgId;
	private String orgName;
	//录入人
	private String userId;
	private String userName;
	//采样流量
	private String ll;
	//采样时长
	private String hours;
	/**
	 * 所需样品
	 */	
	private String sampName;
	
	private String jzId;//采样介质
	private String jzName;
	/**
	 * 运储条件
	 */	
	private String conditions;
	//能力范围
	private String isMay;
	//固定剂
	private String gdj;
	private String remark;
	 
	@Column(length = 32)
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(length = 64)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	@Column(length = 32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 32)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 32)
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	@Column(length = 320)
	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	@Column(length = 320)
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	@Column(length = 320)
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	@Column(length = 320)
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	@Column(length = 16)
	public String getLl() {
		return ll;
	}

	public void setLl(String ll) {
		this.ll = ll;
	}
	@Column(length = 16)
	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}
	@Column(length = 64)
	public String getSampName() {
		return sampName;
	}

	public void setSampName(String sampName) {
		this.sampName = sampName;
	}
	@Column(length = 64)
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	@Column(length = 16)
	public String getIsMay() {
		return isMay;
	}
	public void setIsMay(String isMay) {
		this.isMay = isMay;
	}
	@Column(length = 320)
	public String getJzId() {
		return jzId;
	}
	public void setJzId(String jzId) {
		this.jzId = jzId;
	}
	@Column(length = 320)
	public String getJzName() {
		return jzName;
	}
	public void setJzName(String jzName) {
		this.jzName = jzName;
	}
	@Column(length = 64)
	public String getGdj() {
		return gdj;
	}
	public void setGdj(String gdj) {
		this.gdj = gdj;
	}

//	public static void main(String[] args) {
//		CreateCodeUtils.autoCreateCode(TaskInit.class, true, ActionType.JSP);
//	}
}
