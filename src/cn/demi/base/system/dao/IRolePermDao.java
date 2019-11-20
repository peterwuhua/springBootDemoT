package cn.demi.base.system.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.base.system.po.RolePerm;

public interface IRolePermDao extends IBaseDao<RolePerm> {
	/**
	 * Create on : Paddy Zhang 2016年11月22日 上午8:59:25 <br>
	 * Description : 通过角色 和 功能 查询 roleperm<br>
	 * @param funId
	 * @param functionId
	 * @return
	 */
	public List<RolePerm> listByRoleAndFunId(String funId, String roleId);
	/**
	 * Create on : Paddy Zhang 2016年11月22日 上午8:59:25 <br>
	 * Description : 通过角色 和 功能 查询 roleperm<br>
	 * @param roleId
	 * @return
	 */
	public List<RolePerm> listByRoleId(String roleId);


}

