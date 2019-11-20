package cn.demi.base.system.service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.RolePermVo;

@Transactional
public interface IRolePermService extends IBaseService<RolePermVo> {
	/**
	 * Create on : Paddy Zhang 2016年11月21日 下午6:42:58 <br>
	 * Description : 给角色下的某个功能赋权<br>
	 * 
	 * @param roleId
	 * @param functionId
	 * @param permIds
	 * @throws GlobalException
	 */
	public void saveRolePerm(String roleId, String functionId, String permIds) throws GlobalException;

	/**
	 * Create on : Paddy Zhang 2016年11月22日 上午10:28:09 <br>
	 * Description : 查询角色》功能》拥有的权限 <br>
	 * @param funId
	 * @param funId
	 * @return
	 * @throws GlobalException
	 */
	public List<RolePermVo> listByRoleAndFunId(String funId, String roleId) throws GlobalException;
	
	/**
	 * Create on : Paddy Zhang 2016年11月22日 上午10:28:09 <br>
	 * Description : 查询角色》功能》拥有的权限 <br>
	 * @param funId
	 * @return
	 * @throws GlobalException
	 */
	public List<RolePermVo> listByRoleId(String roleId) throws GlobalException;
	
	public Set<String> listPerm(String roleIds) throws GlobalException;


}
