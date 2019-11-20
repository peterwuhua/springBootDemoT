package cn.demi.base.system.dao;

import java.util.List;
import java.util.Set;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.RoleFun;
/**
 * Create on : 2016年11月3日 上午10:53:59  <br>
 * Description : 角色-功能 DAO <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public interface IRoleFunDao extends IBaseDao<RoleFun> {
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:50:27 </strong> <br>
	 * <strong>Description : 清空当前角具备的功能列表 </strong> <br>
	 * @param roleId 角色Id
	 * @throws GlobalException
	 */
	public void deleteByRoleId(String roleId) throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:51:39 </strong> <br>
	 * <strong>Description : 得到当前角具备的功能列表 </strong> <br>
	 * @param roleId 角色Id
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleFun> listByRoleId(String... roleId) throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:51:39 </strong> <br>
	 * <strong>Description : 得到当前角具备的功能列表 </strong> <br>
	 * @param roleId 角色Id
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleFun> getFunctionList(String... roleId)
			throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:52:47 </strong> <br>
	 * <strong>Description :获取当前角色组具备的功能权限（取所有角色具备功能的并集） </strong> <br>
	 * @param pfunctionId
	 * @param roleId 角色Id
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleFun> list(String pfunctionId, String... roleId)
			throws GlobalException;

	/**
	 * <strong>Description :获取角色id集合</strong> <br>
	 * @param funCode 功能编号
	 * @param wfCode 流程编号
	 * @return List
	 * @throws GlobalException
	 */
	public Set<String> listRole(String funCode, String wfCode)throws GlobalException;
}
