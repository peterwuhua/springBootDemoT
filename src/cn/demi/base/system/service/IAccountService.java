package cn.demi.base.system.service;

import java.util.List;
import java.util.Set;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.UserVo;
import cn.demi.bus.task.vo.CyrVo;

/**
 * Create on : 2016年11月3日 上午11:01:43  <br>
 * Description : 账户service  <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public interface IAccountService extends IBaseService<AccountVo> {
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午1:44:08 </strong> <br>
	 * <strong>Description : 获得当前用户所拥有的账户信息</strong> <br>
	 * @param userId 用户id
	 * @return list
	 * @throws GlobalException
	 */
	public List<AccountVo> listByUserId(String userId) throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午1:47:17 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param dep dep
	 * @param roleCode roleCode
	 * @param orgCode orgCode
	 * @return List
	 * @throws GlobalException
	 */
	public List<AccountVo> list(String dep, String roleCode, String orgCode)
			throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午1:50:12 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param loginName 登录名
	 * @param password 密码
	 * @return AccountVo
	 * @throws GlobalException
	 */
	public AccountVo login(String loginName, String password)
			throws GlobalException;
	
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午1:50:12 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param loginName 登录名
	 * @return AccountVo
	 * @throws GlobalException
	 */
	public AccountVo find(String loginName)
			throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午1:50:12 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param loginName 登录名
	 * @return Set
	 * @throws GlobalException
	 */
	public Set<String> getRoleSet(String loginName) throws  GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午1:50:12 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param loginName 登录名
	 * @return Set
	 * @throws GlobalException
	 */
	public Set<String> getFunctionSet(String loginName) throws  GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午1:51:34 </strong> <br>
	 * <strong>Description : 修改密码</strong> <br>
	 * @param accountId 用户账户id
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return boolean
	 * @throws GlobalException 旧密码错误
	 */
	public boolean updatePwd(String accountId, String oldPwd, String newPwd)
			throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午1:53:48 </strong> <br>
	 * <strong>Description : 账户信息选择</strong> <br>
	 * @param dep 部门
	 * @param roleCode 角色编码
	 * @param orgCode 组织
	 * @param dutyCode 岗位
	 * @param preFix 前缀
	 * @return List
	 * @throws GlobalException
	 */
	public List<AccountVo> list(String dep,String roleCode,String orgCode,String dutyCode,String preFix) throws  GlobalException;

	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午1:40:03 </strong> <br>
	 * <strong>Description :修改密码 </strong> <br>
	 * @param accountId accountId
	 * @param oldPwd 原密码
	 * @param newPwd 新密码
	 * @throws GlobalException
	 */
	public void resetPwd(String accountId, String oldPwd, String newPwd) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2016年12月14日 下午6:31:04 <br>
	 * Description :  更新用户启用状态<br>
	 * @param ids 选择的用户Id
	 * @param isUse　账户启用状态
	 * @throws GlobalException 
	 */
	public void updateAccountIsuse(String ids, String isUse) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2016年12月23日 上午8:50:36 <br>
	 * Description : 客户服务添加账户   （无人员,组织给默认根节点组织（）,角色可有可无） <br>
	 * @param loginName
	 * @param passWord
	 * @throws GlobalException
	 */
	public void saveCssAccount(AccountVo v) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年3月3日 下午3:29:14 <br>
	 * Description : 根据组织编码查询账户信息  <br>
	 * @param v
	 * @return 
	 * @throws GlobalException
	 */
	public List<AccountVo> listByOrgCode(String  code) throws GlobalException;
	
    /**
     * Create on : Danlee Li 2017年3月7日 上午10:22:20 <br>
     * Description : 通过组织和角色查找当前组织或者角色下的信息 <br>
     * @param orgCode 组织
     * @param role 角色
     * @return
     * @throws GlobalException
     */
    public List<UserVo> listUser(String orgCode,String role)throws GlobalException;
    
    public void synAccount()throws GlobalException;
    
    public List<CyrVo> listByUsers(List<AccountVo> users) throws GlobalException;
}
