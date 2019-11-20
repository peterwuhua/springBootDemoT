package cn.demi.init.std.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class ItemUserVo extends Vo<ItemUserVo> {
	//项目Id
	private String itemId;
	//部门
	private String orgId;
	private String orgName;
	//默认检测人
	private String userId;
	private String userName;
	//检测人
	private String userIds;
	private String userNames;
	
	//检测方法
	private String methodIds;
	private String methodNames;
	
	private List<ItemMethodVo> methodList;
	
	public String getItemId() {
		return itemId;
	}
	public String getOrgId() {
		return orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserIds() {
		return userIds;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public List<ItemMethodVo> getMethodList() {
		return methodList;
	}
	public void setMethodList(List<ItemMethodVo> methodList) {
		this.methodList = methodList;
	}
	public String getMethodIds() {
		return methodIds;
	}
	public String getMethodNames() {
		return methodNames;
	}
	public void setMethodIds(String methodIds) {
		this.methodIds = methodIds;
	}
	public void setMethodNames(String methodNames) {
		this.methodNames = methodNames;
	}
	
}

