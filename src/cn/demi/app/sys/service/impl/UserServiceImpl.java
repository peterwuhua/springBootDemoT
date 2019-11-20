package cn.demi.app.sys.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.sys.service.AppUserService;
import cn.demi.app.sys.vo.AppUser;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.UserInfoVo;
import cn.demi.app.sys.vo.UserOutVo;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IAccountRoleDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.dao.IUserDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.po.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("app.userService")
public class UserServiceImpl implements AppUserService {

	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private IAccountRoleDao accountRoleDao;

	@Override
	public ObjVo checkPerm(String token) throws GlobalException {
		ObjVo userVo = null;
		try {
			String decode = new String(StrUtils.disencrypt(token));
			String id = decode.split("-")[0];
			Account account = accountDao.findById(id);
			long time = Long.valueOf(decode.split("-")[1]);
			long ctime = DateUtils.getCurrDate().getTime();
			if (null != account /* && (ctime - time) <= ConstantApp.time */) {
				userVo = new ObjVo();
				// token = account.getId() + "-" + DateUtils.getCurrDate().getTime();
				// token = StrUtils.encrypt(token);
				userVo.setToken(token);
				userVo.setUserId(account.getId());

			}
			userVo.setName(account.getUser().getName());
			userVo.setDepId(account.getOrg().getId());
			userVo.setRealUserId(account.getUser().getId());
			Org org = orgDao.findOrg(userVo.getDepId());
			userVo.setOrgId(org.getId());
			userVo.setOrgName(account.getOrg().getName());
			String roleIds = accountRoleDao.getRoleIds(account.getId());// 角色Ids
			userVo.setRoleIds(roleIds);
			return userVo;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public UserOutVo login(String name, String password) throws GlobalException {
		UserOutVo userVo = null;
		Account account = accountDao.find(name);
		if (null == account) {
			return null;
		} else if (!StringUtils.equalsIgnoreCase(StrUtils.md5(password), account.getPassword())) {
			return null;
		} else if (String.valueOf(Po.N).equals(account.getIsUse())) {
			return null;
		} else {
			userVo = new UserOutVo();
			userVo.setId(account.getId());
			User user = account.getUser();
			userVo.setUserName(user.getName());
			userVo.setDeptName(account.getOrg().getName());
			userVo.setDuty(user.getDuty());
			if (!StrUtils.isBlankOrNull(user.getAvatar())) {
				String SERVER_BASE = (String) ApplicationUtils.getValue("config.server.base");
				File f = new File(SERVER_BASE + "static/upload/" + user.getAvatar());
				if (f.exists() && f.isFile()) {
					userVo.setAvatar("static/upload/" + user.getAvatar());
				} else {
					userVo.setAvatar("static/upload/avatar/profile-pic.jpg");
				}
			} else {
				userVo.setAvatar("static/upload/avatar/profile-pic.jpg");
			}
			String token = account.getId() + "-" + DateUtils.getCurrDate().getTime();
			token = StrUtils.encrypt(token);
			userVo.setToken(token);
		}
		return userVo;
	}

	@Override
	public UserInfoVo findUser(String userId) throws GlobalException {
		Account account = accountDao.findById(userId);
		UserInfoVo userVo = new UserInfoVo();
		BeanUtils.copyProperties(account.getUser(), userVo);

		// 用户头像
		if (!StrUtils.isBlankOrNull(account.getUser().getAvatar())) {
			String SERVER_BASE = (String) ApplicationUtils.getValue("config.server.base");
			File f = new File(SERVER_BASE + "static/upload/" + account.getUser().getAvatar());
			if (f.exists() && f.isFile()) {
				userVo.setAvatar("static/upload/" + account.getUser().getAvatar());
			} else {
				userVo.setAvatar("static/upload/avatar/profile-pic.jpg");
			}
		} else {
			userVo.setAvatar("static/upload/avatar/profile-pic.jpg");
		}
		userVo.setId(userId);
		return userVo;
	}

	@Override
	public boolean updateUser(UserInfoVo infoVo, String userId) throws GlobalException {
		try {
			Account account = accountDao.findById(userId);
			User user = account.getUser();
			user.setEmail(infoVo.getEmail());
			user.setMobile(infoVo.getMobile());
			user.setAddress(infoVo.getAddress());
			user.setUrgentUser(infoVo.getUrgentUser());
			user.setUrgentPhone(infoVo.getUrgentPhone());
			user.setPolitical(infoVo.getPolitical());
			user.setEducation(infoVo.getEducation());
			user.setGraduationSchool(infoVo.getGraduationSchool());
			user.setProfession(infoVo.getProfession());
			userDao.update(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String resetPwd(String accountId, String oldPwd, String newPwd) throws GlobalException {
		Account account = accountDao.findById(accountId);
		if (null == account) {
			return "用户不存在";
		} else if (!StringUtils.equalsIgnoreCase(StrUtils.md5(oldPwd), account.getPassword())) {
			return "旧密码输入错误";
		} else {
			account.setPassword(StrUtils.md5(newPwd));
			accountDao.update(account);
			return null;
		}
	}

	@Override
	public void updateAvatar(String id, String path) throws GlobalException {
		Account account = accountDao.findById(id);
		User user = account.getUser();
		user.setAvatar(path);
		userDao.update(user);
	}

	@Override
	public JSONArray userList() throws GlobalException {
		JSONArray arr = new JSONArray();
		List<Account> userList = accountDao.list();
		// Set<String> orgName = new HashSet<>();
		for (Account user : userList) {
			/*
			 * Org org = user.getOrg(); if (!orgName.contains(org.getCode())) {
			 * orgName.add(org.getCode()); JSONObject obj = new JSONObject(); obj.put("id",
			 * org.getId()); obj.put("pId", ""); obj.put("name", org.getName());
			 * obj.put("open", "true"); obj.put("nocheck", "true"); obj.put("type",
			 * "group"); arr.add(obj); }
			 */
			JSONObject obj = new JSONObject();
			obj.put("id", user.getId());
			obj.put("pId", user.getOrg().getId());
			obj.put("name", user.getUser().getName());
			obj.put("open", "true");
			obj.put("checked", "false");
			obj.put("type", "user");
			obj.put("mobile", user.getUser().getMobile());
			obj.put("avatar", "static/upload/" + user.getUser().getAvatar());
			arr.add(obj);
		}
		return arr;
	}

	@Override
	public List<AppUser> AppuserList(ObjVo v, String name) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + userDao.getEntityName(User.class) + " WHERE isDel=" + Po.N);
		hql.append(" and name like '%" + name + "%' ");
		hql.append(" ORDER BY sort asc");
		Query query = userDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<User> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppUser> outList = new ArrayList<>();
		for (User user : list) {
			AppUser appUser = new AppUser();
			appUser.setId(user.getId());
			appUser.setNo(user.getNo());
			appUser.setName(user.getName());
			appUser.setOrgName(user.getOrgName());
			appUser.setDuty(user.getDuty());
			appUser.setSex(user.getSex());
			appUser.setMobile(user.getMobile());
			appUser.setEmail(user.getEmail());
			outList.add(appUser);
		}
		return outList;
	}

}
