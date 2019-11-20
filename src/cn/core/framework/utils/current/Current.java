package cn.core.framework.utils.current;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.demi.base.system.vo.FunctionVo;

public final class Current implements Serializable {
	private static final long serialVersionUID = 1L;
	//登陆Key
	private String key;
	
	private String userName;
	private String userId;

	private String accountId;
	private String loginName;
	private String loginTime;
	//公司信息
	private String orgName;
	private String orgId;
	private String orgCode;
	//部门信息
	private String depId;
	private String depName;
	private String depCode;
	//岗位
	private String duty;
	//角色
	private String roleIds;
	private String roleNames;
	
	private String avatar;//头像
	private String isUse;//账户是否可用
	private String theme;//主题
	
	private List<FunctionVo> sysList;//子系统
	private String sysId;//子系统
	private String sysName;//子系统
	private String portal;//首页
	
	private int db;//代办总数
	private int yb;//已办总数
	private int zs;//工作总数
	private int yj;//邮件
	private int rz;//日志
	private int tz;//通知
	public void setKey(String key) {
		this.key = key;
	}

	private Map<String, Object> map;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getKey() {
		key = userName + "【" + loginName + "】";
		return key;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public int getDb() {
		return db;
	}

	public void setDb(int db) {
		this.db = db;
	}

	public int getYb() {
		return yb;
	}

	public void setYb(int yb) {
		this.yb = yb;
	}

	public int getZs() {
		return zs;
	}

	public void setZs(int zs) {
		this.zs = zs;
	}

	public int getYj() {
		return yj;
	}

	public void setYj(int yj) {
		this.yj = yj;
	}

	public int getRz() {
		return rz;
	}

	public void setRz(int rz) {
		this.rz = rz;
	}

	public int getTz() {
		return tz;
	}

	public void setTz(int tz) {
		this.tz = tz;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public List<FunctionVo> getSysList() {
		return sysList;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysList(List<FunctionVo> sysList) {
		this.sysList = sysList;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
}
