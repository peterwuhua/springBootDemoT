package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class AccountVo extends Vo<AccountVo> {
	public static final String SYS_SIGNATURE = "sys-signature";
	/**
	 * 用户信息Id
	 */
	private UserVo userVo;// 用户信息Id
	/**
	 * 组织
	 */
	private OrgVo orgVo;// 组织
	/**
	 * 部门
	 */
	private OrgVo depVo;// 系统配置 部门
	/**
	 * 登陆名称
	 */
	private String loginName;// 登陆名称
	/**
	 * 登陆名称
	 */
	private String userName;//用户名称
	/**
	 * 登陆的密码
	 */
	private String password;// 登陆的密码
	/**
	 * 签章
	 */
	private String signature;// 签章

	/**
	 * 角色id
	 */
	private String roleIds;
	/**
	 * 角色id
	 */
	private String roleNames;
	/**
	 * 角色id
	 */
	private String roleCode;
	/**
	 * 组织id
	 */
	private String orgId;
	/**
	 * 电子签章
	 */
//	private MultipartFile file;
	/**
	 * 在线状态
	 */
	private String status;
	/**
	 *即时通讯个性签名
	 */
	private String signText;
	/**
	 * 账户是否启用
	 */
	private String isUse;
	/**
	 * ip
	 */
	private String ip;
	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public OrgVo getOrgVo() {
		return orgVo;
	}

	public void setOrgVo(OrgVo orgVo) {
		this.orgVo = orgVo;
	}

//	public MultipartFile getFile() {
//		return file;
//	}
//
//	public void setFile(MultipartFile file) {
//		this.file = file;
//	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public OrgVo getDepVo() {
		return depVo;
	}

	public void setDepVo(OrgVo depVo) {
		this.depVo = depVo;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSignText() {
		return signText;
	}

	public void setSignText(String signText) {
		this.signText = signText;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		if(null!=getUserVo()) {
			userName=getUserVo().getName();
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	
}
