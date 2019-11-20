package cn.demi.base.system.service;

import java.util.List;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.AccountRoleVo;
import cn.demi.base.system.vo.AccountVo;

/**
 * Create on : 2016年11月3日 上午10:54:44  <br>
 * Description :  账户-角色service <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public interface IAccountRoleService extends IBaseService<AccountRoleVo> {
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午3:18:03 </strong> <br>
	 * <strong>Description : 获得当前账户所具备的角色信息 </strong> <br>
	 * @param accountId 账户id
	 * @return List
	 * @throws GlobalException
	 */
	public List<AccountRoleVo> listByAccountId(String accountId)
			throws GlobalException;
	
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午3:19:13 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param roleId roleId
	 * @return List
	 * @throws GlobalException
	 */
	public List<AccountRoleVo> listByRoleId(String roleId)
			throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年3月7日 下午2:52:54 <br>
	 * Description :  <br>
	 * @param orgCode 组织编码
	 * @param roleCode 角色名称
	 * @return
	 * @throws GlobalException
	 */
	public List<AccountVo> listAccount(GridVo gridVo,String orgCode, String roleCode, String funCode) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年3月7日 下午2:52:54 <br>
	 * Description :  <br>
	 * @param orgCode 组织编码
	 * @param roleCode 角色名称
	 * @return
	 * @throws GlobalException
	 */
	public List<AccountVo> listAccount(GridVo gridVo,String roleCode) throws GlobalException;
	/**
	 * 获取用于某角色用户集合
	 * 权限范围内的集合
	 * @param roleCode 角色名称
	 * @return
	 * @throws GlobalException
	 */
	public List<AccountVo> listAccount(String roleCode) throws GlobalException;
	/**
	 * Description :  <br>
	 * @param orgCode 组织编码
	 * @param roleCode 角色名称
	 * @return
	 * @throws GlobalException
	 */
	public List<AccountVo> listAccount(String orgCode, String roleCode, String funCode) throws GlobalException;
	
	/**
	 * 
	 * @Title: listAccountByOrg   
	 * @Description: 根据报告审核人角色和项目类型所对应的部门（职业卫生还是环境）  
	 * @param: @param role
	 * @param: @param itemType
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: List<AccountVo>      
	 * @throws
	 */
	public List<AccountVo> listAccountByOrg(String role,String itemType) throws GlobalException;
	
	

}
