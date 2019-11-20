package cn.demi.app.sys.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.vo.AppUser;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.UserInfoVo;
import cn.demi.app.sys.vo.UserOutVo;
import net.sf.json.JSONArray;

@Transactional
public interface AppUserService {

	/**
	 * 用户登陆
	 *
	 * @param name 用户名
	 * @param pwd  密码
	 * @return
	 * @throws GlobalException
	 */
	public UserOutVo login(String name, String pwd) throws GlobalException;

	/**
	 * 获取用户信息
	 *
	 * @param userId
	 * @return
	 * @throws GlobalException
	 */
	public UserInfoVo findUser(String userId) throws GlobalException;

	/**
	 * 修改用户信息
	 *
	 * @param infoVo
	 * @param userId
	 * @return
	 * @throws GlobalException
	 */
	public boolean updateUser(UserInfoVo infoVo, String userId) throws GlobalException;

	/**
	 * 修改密码
	 *
	 * @param accountId
	 * @param oldPwd
	 * @param newPwd
	 * @throws GlobalException
	 */
	public String resetPwd(String accountId, String oldPwd, String newPwd) throws GlobalException;

	/**
	 * 修改头像
	 *
	 * @param id
	 * @param path
	 * @throws GlobalException
	 */
	public void updateAvatar(String id, String path) throws GlobalException;

	/**
	 * 检查权限 若权限有效，则返回最新token,否则返回null
	 *
	 * @param token
	 * @return
	 * @throws GlobalException
	 */
	public ObjVo checkPerm(String token) throws GlobalException;

	/**
	 * 向IM提供组和用户信息
	 *
	 * @return
	 * @throws GlobalException
	 */
	public JSONArray userList() throws GlobalException;

	/**
	 * 人员档案
	 */
	public List<AppUser> AppuserList(ObjVo v,String name) throws GlobalException;

}
