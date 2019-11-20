package cn.demi.zk.vo;

import java.util.List;
import java.util.Map;

import cn.core.framework.common.vo.Vo;

public class ZkSamplingVo extends Vo<ZkSamplingVo> {
	/**
	 * 任务
	 */
	private ZkTaskVo taskVo;
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
	private Map<String,ZkItemTestVo> itemMap;	
	private List<ZkItemTestVo> itemList;

	public ZkTaskVo getTaskVo() {
		return taskVo;
	}

	public void setTaskVo(ZkTaskVo taskVo) {
		this.taskVo = taskVo;
	}

	public String getAssDate() {
		return assDate;
	}

	public void setAssDate(String assDate) {
		this.assDate = assDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getAppIds() {
		return appIds;
	}

	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}

	public String getAppNames() {
		return appNames;
	}

	public void setAppNames(String appNames) {
		this.appNames = appNames;
	}

	public String getStandIds() {
		return standIds;
	}

	public void setStandIds(String standIds) {
		this.standIds = standIds;
	}

	public String getStandNames() {
		return standNames;
	}

	public void setStandNames(String standNames) {
		this.standNames = standNames;
	}

	public String getSampCode() {
		return sampCode;
	}

	public void setSampCode(String sampCode) {
		this.sampCode = sampCode;
	}

	public String getSampDate() {
		return sampDate;
	}

	public void setSampDate(String sampDate) {
		this.sampDate = sampDate;
	}

	public String getTj() {
		return tj;
	}

	public void setTj(String tj) {
		this.tj = tj;
	}

	public String getFfgc() {
		return ffgc;
	}

	public void setFfgc(String ffgc) {
		this.ffgc = ffgc;
	}

	public String getPnd() {
		return pnd;
	}

	public void setPnd(String pnd) {
		this.pnd = pnd;
	}

	public String getCnd() {
		return cnd;
	}

	public void setCnd(String cnd) {
		this.cnd = cnd;
	}

	public String getHsl() {
		return hsl;
	}

	public void setHsl(String hsl) {
		this.hsl = hsl;
	}

	public String getJmd() {
		return jmd;
	}

	public void setJmd(String jmd) {
		this.jmd = jmd;
	}

	public String getMuserId() {
		return muserId;
	}

	public void setMuserId(String muserId) {
		this.muserId = muserId;
	}

	public String getMuserName() {
		return muserName;
	}

	public void setMuserName(String muserName) {
		this.muserName = muserName;
	}

	public String getCuserId() {
		return cuserId;
	}

	public void setCuserId(String cuserId) {
		this.cuserId = cuserId;
	}

	public String getCuserName() {
		return cuserName;
	}

	public void setCuserName(String cuserName) {
		this.cuserName = cuserName;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsBack() {
		return isBack;
	}

	public void setIsBack(int isBack) {
		this.isBack = isBack;
	}

	public List<ZkItemTestVo> getItemList() {
		return itemList;
	}

	public void setItemList(List<ZkItemTestVo> itemList) {
		this.itemList = itemList;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCompDate() {
		return compDate;
	}

	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}

	public Map<String, ZkItemTestVo> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, ZkItemTestVo> itemMap) {
		this.itemMap = itemMap;
	}
	
}

