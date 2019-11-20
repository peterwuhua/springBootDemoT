package cn.demi.base.system.service;

import java.util.List;
import java.util.Set;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.FunctionVo;
import cn.demi.base.system.vo.RoleFunVo;
/**
 * Create on : 2016年11月3日 上午11:00:38  <br>
 * Description : 角色-功能service <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public interface IRoleFunService extends IBaseService<RoleFunVo> {
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午3:24:49 </strong> <br>
	 * <strong>Description : 获取当前角色具备的功能列表</strong> <br>
	 * @param roleId 角色Id
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleFunVo> listByRoleId(String roleId) throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午3:25:15 </strong> <br>
	 * <strong>Description : 获取当前角色具备的功能列表 </strong> <br>
	 * @param roleId 角色Id
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleFunVo> listByRoleIds(String... roleId)
			throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午3:25:55 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param pfunctionId pfunctionId
	 * @param roleId roleId
	 * @return List
	 * @throws GlobalException
	 */
	public Set<String> getFunctionAuth(String... roleId)
			throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午3:25:55 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param pfunctionId pfunctionId
	 * @param roleId roleId
	 * @return List
	 * @throws GlobalException
	 */
	public List<RoleFunVo> getMenuList(String pfunctionId, String... roleId)
			throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月24日 下午2:49:15 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param roleId
	 * @return
	 * @throws GlobalException
	 */
	public List<RoleFunVo> getRoleFunctionList(String... roleId)
			throws GlobalException;
	/**
	 * 获取代办事项
	 * @param roleIds
	 * @param orgId
	 * @param userId
	 * @return
	 * @throws GlobalException
	 */
	public List<RoleFunVo> getRoleFunList()throws GlobalException;
	/**
	 * 获取第一级菜单
	 * @return
	 */
	public List<FunctionVo> list4First()throws GlobalException;
	
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午3:26:26 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param roleId roleId
	 * @param functionIds functionIds
	 * @throws GlobalException
	 */
	public void save(String roleId, String functionIds) throws GlobalException;

}
