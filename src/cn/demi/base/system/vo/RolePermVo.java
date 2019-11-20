package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class RolePermVo extends Vo<RolePermVo> {
	/**
	 * role
	 */
	private RoleVo roleVo;
	/**
	 * permission
	 */
	private PermissionVo permissionVo;
	public RoleVo getRoleVo() {
		return roleVo;
	}
	public void setRoleVo(RoleVo roleVo) {
		this.roleVo = roleVo;
	}
	public PermissionVo getPermissionVo() {
		return permissionVo;
	}
	public void setPermissionVo(PermissionVo permissionVo) {
		this.permissionVo = permissionVo;
	}
	
	
}

