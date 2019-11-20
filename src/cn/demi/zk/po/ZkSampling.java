package cn.demi.zk.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

/**
 *质控 检测对象信息
 * @author QuJunLong
 */
@Entity(name = "zk_sampling")
@Table(name = "zk_sampling")
@Module(value = "zk.sampling")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ZkSampling extends Po<ZkSampling>{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","task","assDate","type","userNames","appNames","sampCode","sampDate","isBack"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 任务
	 */
	private ZkTask task;
	/**
	 * 考核日期
	 */
	private String assDate;
	/**
	 * 考核方式
	 */
	private String type;
	/**
	 * 被考核人员
	 */
	private String userIds;
	private String userNames;
	/**
	 * 仪器
	 */
	private String appIds;
	private String appNames;
	
	/**
	 * 标准物质
	 */
	private String standIds;
	private String standNames;
	  
	/**
	 *样品编号
	 */
	private String sampCode;
	/**
	 *样品配置日期
	 */
	private String sampDate;
	/**
	 *体积
	 */
	private String tj;
	/**
	 *配置方法及过程
	 */
	private String ffgc;
	/**
	 *配置浓度
	 */
	private String pnd;
	/**
	 *测定浓度
	 */
	private String cnd;
	/**
	 *回收率
	 */
	private String hsl;
	/**
	 *精密度
	 */
	private String jmd;
	/**
	 *配置人
	 */
	private String muserId;
	private String muserName;
	/**
	 *检测人
	 */
	private String cuserId;
	private String cuserName;
	/**
	 *实验室测试项目
	 */
	private String itemIds;
	/**
	 *实验室测试项目
	 */
	private String itemNames;
	/**
	 * 检测科室
	 */
	private String orgId;
	/**
	 * 检测科室
	 */
	private String orgName;
	/**
	 * 要求完成日期
	 */
	private String compDate;
	/**
	 * 备注
	 */
	private String remark;
	
    private int isBack;  
      
	@Column(length = 32)
	public String getSampCode() {
		return sampCode;
	}
	public void setSampCode(String sampCode) {
		this.sampCode = sampCode;
	}
	@Column(length = 20)
	public String getSampDate() {
		return sampDate;
	}
	public void setSampDate(String sampDate) {
		this.sampDate = sampDate;
	}
	 
	@Column(length = 1000)
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	@Column(length = 1000)
	public String getItemNames() {
		return itemNames;
	}
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@ManyToOne
	@JoinColumn(name = "task_id")
	public ZkTask getTask() {
		return task;
	}
	public void setTask(ZkTask task) {
		this.task = task;
	}
	@Column(length = 20)
	public String getAssDate() {
		return assDate;
	}
	public void setAssDate(String assDate) {
		this.assDate = assDate;
	}
	@Column(length = 320)
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	@Column(length = 320)
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	@Column(length = 320)
	public String getAppIds() {
		return appIds;
	}
	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}
	@Column(length = 320)
	public String getAppNames() {
		return appNames;
	}
	public void setAppNames(String appNames) {
		this.appNames = appNames;
	}
	@Column(length = 320)
	public String getStandIds() {
		return standIds;
	}
	public void setStandIds(String standIds) {
		this.standIds = standIds;
	}
	@Column(length = 320)
	public String getStandNames() {
		return standNames;
	}
	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}
	@Column(length =32)
	public String getTj() {
		return tj;
	}
	public void setTj(String tj) {
		this.tj = tj;
	}
	@Column(length = 64)
	public String getFfgc() {
		return ffgc;
	}
	public void setFfgc(String ffgc) {
		this.ffgc = ffgc;
	}
	@Column(length = 32)
	public String getPnd() {
		return pnd;
	}
	public void setPnd(String pnd) {
		this.pnd = pnd;
	}
	@Column(length = 32)
	public String getCnd() {
		return cnd;
	}
	public void setCnd(String cnd) {
		this.cnd = cnd;
	}
	@Column(length = 32)
	public String getHsl() {
		return hsl;
	}
	public void setHsl(String hsl) {
		this.hsl = hsl;
	}
	@Column(length = 32)
	public String getJmd() {
		return jmd;
	}
	public void setJmd(String jmd) {
		this.jmd = jmd;
	}
	@Column(length = 32)
	public String getMuserId() {
		return muserId;
	}
	public void setMuserId(String muserId) {
		this.muserId = muserId;
	}
	@Column(length = 32)
	public String getMuserName() {
		return muserName;
	}
	public void setMuserName(String muserName) {
		this.muserName = muserName;
	}
	@Column(length = 32)
	public String getCuserId() {
		return cuserId;
	}
	public void setCuserId(String cuserId) {
		this.cuserId = cuserId;
	}
	@Column(length = 32)
	public String getCuserName() {
		return cuserName;
	}
	public void setCuserName(String cuserName) {
		this.cuserName = cuserName;
	}
	@Column(length = 2)
	public int getIsBack() {
		return isBack;
	}
	public void setIsBack(int isBack) {
		this.isBack = isBack;
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
	@Column(length = 20)
	public String getCompDate() {
		return compDate;
	}
	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(ZkSampling.class, true, ActionType.JSP);
	}
}
