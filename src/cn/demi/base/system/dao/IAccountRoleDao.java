package cn.demi.base.system.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.AccountRole;
/**
 * Create on : 2016年11月3日 上午10:48:03  <br>
 * Description : 户-角色 DAO  <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public interface IAccountRoleDao extends IBaseDao<AccountRole> {
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:38:39 </strong> <br>
	 * <strong>Description : 清楚当前账户具备的角色信息 </strong> <br>
	 * @param accountId 账户id
	 * @throws GlobalException
	 */
	public void deleteByAccountId(String accountId) throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:38:39 </strong> <br>
	 * <strong>Description : 清楚当前账户具备的角色信息 </strong> <br>
	 * @param accountId 账户id
	 * @throws GlobalException
	 */
	public String getRoleIds(String accountId) throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:42:41 </strong> <br>
	 * <strong>Description : 得到当前账户具备的角色信息 </strong> <br>
	 * @param accountId 账户id
	 * @return List
	 * @throws GlobalException
	 */
	public List<AccountRole> listByAccountId(String accountId)
			throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:45:00 </strong> <br>
	 * <strong>Description : 得到当前角色具备的账户信息</strong> <br>
	 * @param roleId 账户id
	 * @return List
	 * @throws GlobalException
	 */
	public List<AccountRole> listByRoleId(String roleId) throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:45:52 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param dep dep
	 * @param roleCode roleCode
	 * @param orgCode orgCode
	 * @return List
	 * @throws GlobalException
	 */
	public List<AccountRole> list(String dep, String roleCode, String orgCode)
			throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年5月24日 下午4:16:35 <br>
	 * Description : 根据accountId删除账户所有角色且 如果当前用为不是suadmin 不删除 管理员和超级管理员角色  <br>
	 * @param accountId
	 * @throws GlobalException
	 */
	public void deleteExceptAdmin(String accountId) throws GlobalException;

}
