package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.IPUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.openIM.OpenIM;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IAccountRoleDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.dao.IRoleDao;
import cn.demi.base.system.dao.IRoleFunDao;
import cn.demi.base.system.dao.IUserDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.AccountRole;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.po.Role;
import cn.demi.base.system.po.RoleFun;
import cn.demi.base.system.po.User;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.OrgVo;
import cn.demi.base.system.vo.UserVo;
import cn.demi.bus.task.vo.CyrVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * <strong>Create on : 2016年11月2日 下午5:10:51 </strong> <br>
 * <strong>Description : AccountServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.accountService")
public class AccountServiceImpl extends BaseServiceImpl<AccountVo, Account>
		implements IAccountService {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private IAccountRoleDao accountRoleDao;
	@Autowired
	private IRoleFunDao roleFunDao;

	@Override
	public List<AccountVo> listByUserId(String userId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("user.id", QueryCondition.EQ, userId));
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,Po.N));
		List<Account> atList=accountDao.list(queryList);
		List<AccountVo> atVolist=new ArrayList<AccountVo>();
		for (Account po : atList) {
			AccountVo vo=new AccountVo();
			vo=vo.toVo(po);
			vo.setDepVo(vo.getOrgVo());
			Org org=orgDao.findOrg(vo.getOrgVo().getId());
			OrgVo orgVo=new OrgVo();
			orgVo=orgVo.toVo(org);
			vo.setOrgVo(orgVo);
			atVolist.add(vo);
		}
		return atVolist;
	}

	@SuppressWarnings("static-access")
	@Override
	public void add(AccountVo v) throws GlobalException {
		Account po = vo2Po(v);

		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("loginName", QueryCondition.EQ,v.getLoginName()));
		List<Account> accounts = this.accountDao.list(queryConditions);
		if (accounts.size() > 0) {
			throw new GlobalException("账户已经存在:" + v.getLoginName());
		}

		User user = userDao.findById(v.getUserVo().getId());
		po.setUser(user);

		Org org = orgDao.findById(v.getOrgVo().getId());
		po.setOrg(org);
		 
		if(StrUtils.isBlankOrNull(v.getPassword())){
			po.setPassword(StrUtils.md5("111111"));
		}else{
			po.setPassword(StrUtils.md5(v.getPassword()));
		}
		accountDao.add(po);
		saveAccountRole(po, v.getRoleIds());
		v.setId(po.getId());
		//new OpenIM().addUser(po);
	}

	@SuppressWarnings("static-access")
	@Override
	public void update(AccountVo v) throws GlobalException {
		Account po = accountDao.findById(v.getId());
		String oldPwd = po.getPassword();
		BeanUtils.copyProperties(v, po, new String[] { "id","signature","password","roleIds"});
		if(null !=v.getOrgVo() && !po.getOrg().getId().equals(v.getOrgVo().getId())){
			Org org = orgDao.findById(v.getOrgVo().getId());
			po.setOrg(org);
		}
		
		if(StrUtils.isNotBlankOrNull(v.getSignature()) && !v.getSignature().equals(po.getSignature())){
			po.setSignature(v.getSignature());
		}
		//webscoket 保存会setCurrent
		Current  current= v.getCurrent();
		if(null != current){
			po.setCreateUser(current.getKey());
			po.setCreateUser(current.getKey());
		}
		/*if(!po.getUser().getId().equals(v.getUserVo().getId())){
			User user = userDao.findById(v.getUserVo().getId());
			po.setUser(user);
		}*/
		if(StrUtils.isBlankOrNull(v.getRoleIds())){
			saveAccountRole(po, v.getRoleIds());
		}else{
			if(!v.getRoleIds().equals(po.getRoleIds())){
				saveAccountRole(po, v.getRoleIds()); 
			}
		}
		po.setRoleIds(v.getRoleIds());
		String newPwd = v.getPassword();
		/**
		 * 密码不相等，证明有修改，更新密码
		 */
		if (!StrUtils.isBlankOrNull(newPwd)) {
			if (!newPwd.equals(oldPwd)) {
				newPwd = StrUtils.md5(newPwd);
				if (!newPwd.equals(oldPwd)) {
					po.setPassword(newPwd);
				}
			}
		}
		accountDao.update(po);
		//new OpenIM().updateUser(po);
	}

	@Override
	public AccountVo po2Vo(Account p) throws GlobalException {
		AccountVo vo = new AccountVo();
		try {
			vo = vo.toVo(p,vo,new String[]{"isDel","user","org","lastUpdTime","lastUpdUser"});
		} catch (Exception e) {
			throw new GlobalException("po2Vo 转换异常");
		}
		return vo;
	}
	//获取部门
	public OrgVo getOrgVo(Org org) throws GlobalException {
		OrgVo orgVo=null;
		if(org.getLevel()<=1) {
			orgVo=new OrgVo();
			orgVo=orgVo.toVo(org);
		}else {
			orgVo=getOrgVo(org.getParent());
		}
		return orgVo;
	};
	/**
	 * <strong>Description : 同步角色</strong> <br>
	 * @param account account
	 * @param roleIds roleIds
	 * @throws GlobalException
	 */
	@CacheEvict(value="authenticationCache",allEntries=true)
	private void saveAccountRole(Account account, String roleIds)
			throws GlobalException {
		/*if (StrUtils.isEmpty(roleIds))
			return;*/
		accountRoleDao.deleteExceptAdmin(account.getId());

		for (Role role : roleDao.listByIds(roleIds)) {
			AccountRole accountRole = new AccountRole();
			accountRole.setAccount(account);
			accountRole.setRole(role);
			accountRoleDao.add(accountRole);
		}
	}

	@Override
	public AccountVo find(String loginName)
			throws GlobalException {
		Account account = accountDao.find(loginName);
		if (null == account) return null; 
		AccountVo vo = new AccountVo();
		vo = vo.toVo(account,vo,new String[]{"isDel","user","org","lastUpdTime","lastUpdUser"});
		if(null != account.getUser()){
			UserVo userVo = new UserVo().toVo(account.getUser());
			vo.setUserVo(userVo);
		}
		if(null != account.getOrg()){
			OrgVo orgVo = new OrgVo().toVo(account.getOrg());
			vo.setDepVo(orgVo);
			vo.setOrgVo(getOrgVo(account.getOrg()));
		}
		
		List<AccountRole> arList=accountRoleDao.findByProperty("account.id", account.getId());
		if(null!=arList) {
			String ids="";
			String names="";
			for (AccountRole ar : arList) {
				if(!ids.contains(ar.getRole().getId())) {
					ids+=ar.getRole().getId()+",";
					names+=ar.getRole().getName()+",";
				}
			}
			if(ids.endsWith(",")) {
				ids=ids.substring(0, ids.length()-1);
				names=names.substring(0, names.length()-1);
			}
			vo.setRoleIds(ids);
			vo.setRoleNames(names);
		}
		return vo;
	}
	
	@Override
	public Set<String> getRoleSet(String loginName) throws GlobalException {
		Account account = accountDao.find(loginName);
		List<AccountRole> list = accountRoleDao.listByAccountId(account.getId());
		Set<String> set = new HashSet<String>();
		for(AccountRole accountRole:list){
			set.add(accountRole.getRole().getCode());
		}
		return set;
	}

	@Override
	public Set<String> getFunctionSet(String loginName) throws GlobalException {
		Account account = accountDao.find(loginName);
		List<RoleFun> list = roleFunDao.listByRoleId(accountRoleDao.getRoleIds(account.getId()));;
		Set<String> set = new HashSet<String>();
		for(RoleFun roleFun:list){
			set.add(roleFun.getFunction().getUrl());
		}
		return set;
	}
	
	@Override
	public AccountVo login(String loginName, String password)
			throws GlobalException {
		Account account = accountDao.find(loginName);
		
		if (null == account)
			throw new GlobalException("无此账号");
		if (!StringUtils.equalsIgnoreCase(password, account.getPassword())) 
			throw new GlobalException("密码不正确");
		if (String.valueOf(Po.N).equals(account.getIsUse() ))
			throw new GlobalException("账户未启用");
		if (IPUtils.isIpLimit()) {
			if (!IPUtils.checkIp(account.getIp()))
				throw new GlobalException("没有权限在本机登陆");
		}
		AccountVo vo = po2Vo(account);
		vo.setDepVo(vo.getOrgVo());
		Org org=orgDao.findOrg(vo.getOrgVo().getId());
		OrgVo orgVo=new OrgVo();
		orgVo=orgVo.toVo(org);
		vo.setOrgVo(orgVo);
		List<AccountRole> arList=accountRoleDao.findByProperty("account.id", account.getId());
		if(null!=arList) {
			String ids="";
			String names="";
			for (AccountRole ar : arList) {
				if(!ids.contains(ar.getRole().getId())) {
					ids+=ar.getRole().getId()+",";
					names+=ar.getRole().getName()+",";
				}
			}
			if(ids.endsWith(",")) {
				ids=ids.substring(0, ids.length()-1);
				names=names.substring(0, names.length()-1);
			}
			vo.setRoleIds(ids);
			vo.setRoleNames(names);
		}
		return vo;
	}

	@Override
	public List<AccountVo> list(String dep, String roleCode, String orgCode)
			throws GlobalException {
		List<AccountRole> accList = accountRoleDao.list(dep, roleCode, orgCode);
		List<AccountVo> accountList = null;
		if (null != accList) {
			accountList = new ArrayList<AccountVo>();
			for (AccountRole po : accList) {
				accountList.add(po2Vo(po.getAccount()));
			}
		}
		return accountList;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean updatePwd(String accountId, String oldPwd, String newPwd)
			throws GlobalException {
		Account account = this.accountDao.findById(accountId);
		// 去除空格
		oldPwd = StringUtils.trim(oldPwd);
		newPwd = StringUtils.trim(newPwd);

		// 原始密码是否正确
		if (StringUtils.equals(StrUtils.md5(oldPwd), account.getPassword())) {
			// 新密码和旧密码完全相同，不做修改
			if (StringUtils.equals(StrUtils.md5(newPwd), account.getPassword())) {

			}
			// 完成密码修改
			account.setPassword(StrUtils.md5(newPwd));
			this.accountDao.update(account);
			//new OpenIM().updateUser(account);
		} else {
			throw new GlobalException();
		}
		return true;

	}


	@Override
	public List<QueryCondition> toQueryList(AccountVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		String orgId = v.getOrgId();
		if(StrUtils.isNotBlankOrNull(orgId)) {
			List<String> ordIds=orgDao.listChild(orgId);
			ordIds.add(orgId);
			queryConditions.add(new QueryCondition("org.id in ('"+String.join("','", ordIds)+"')"));
			queryConditions.add(new QueryCondition("org.isDel", QueryCondition.EQ,Po.N));
		}	
		if (null != v.getUserVo() && !StrUtils.isBlankOrNull(v.getUserVo().getId())) {
			queryConditions.add(new QueryCondition("user.id", QueryCondition.EQ,v.getUserVo().getId()));
			queryConditions.add(new QueryCondition("user.isDel", QueryCondition.EQ,Po.N));
		}
		return queryConditions;
	}


	@Override
	public List<AccountVo> list(String dep, String roleCode, String orgCode,
			String dutyCode,String preFix) throws GlobalException {
		return null;
	}

	@Override
	public void resetPwd(String accountId, String oldPwd, String newPwd) throws GlobalException {
		Account account = this.accountDao.findById(accountId);
		
		// 去除空格
		oldPwd = StringUtils.trim(oldPwd);
		newPwd = StringUtils.trim(newPwd);
		
		// 原始密码是否正确
		if(StringUtils.equals(StrUtils.md5(oldPwd), account.getPassword())){
			// 新密码和旧密码完全相同，不做修改
			if(StringUtils.equals(StrUtils.md5(newPwd),account.getPassword()))
				return;
			
			// 完成密码修改
			account.setPassword(StrUtils.md5(newPwd));
			this.accountDao.update(account);
		}else{
			throw new GlobalException();
		}
		
	}

	@Override
	public void updateAccountIsuse(String ids, String isUse) throws GlobalException {
		try {
			List<Account> listAccount = accountDao.listByIds(ids);
			for (Account account : listAccount) {
				account.setIsUse(isUse);
				accountDao.update(account);
			}
		} catch (Exception e) {
			throw new GlobalException("保存账启用状态出错",e);
		}
		
	}
	@Override
	public void saveCssAccount(AccountVo v) throws GlobalException {
		if(StrUtils.isBlankOrNull(v.getLoginName()))
			throw new GlobalException("参数异常：    登录名不能为空");
		Account po = new Account();
		if(!StrUtils.isBlankOrNull(v.getId())){
			po = accountDao.findById(v.getId());
			String newPwd = v.getPassword();
			String oldPwd = po.getPassword();
			if (StrUtils.isNotBlankOrNull(newPwd)) {
				newPwd = StrUtils.md5(newPwd);
				if (!newPwd.equals(oldPwd)) {
					po.setPassword(newPwd);
				}
			}
		}else {
			//给默认组织
			Org org = orgDao.findRoot();
			po.setOrg(org);
			if(StrUtils.isBlankOrNull(v.getPassword())){
				po.setPassword(StrUtils.md5("111111"));
			}else{
				po.setPassword(StrUtils.md5(v.getPassword()));
			}
		}
		if(!v.getLoginName().equals(po.getLoginName())){
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("loginName", QueryCondition.EQ,
					v.getLoginName()));
			List<Account> accounts = this.accountDao.list(queryConditions);
			if (accounts.size() > 0) {
				throw new GlobalException("账户已经存在:" + v.getLoginName());
			}
		}
		String newRoleIds = v.getRoleIds();
		String oldRoleIds = po.getRoleIds();
		po = po.toPo(v, po, new String[]{"id","createTime","lastUpdTime","version","user","org","password","signature","org"});
		accountDao.saveOrUpdate(po);
		if(StrUtils.isBlankOrNull(newRoleIds)){
			saveAccountRole(po, newRoleIds);
		}else{
			if(!newRoleIds.equals(oldRoleIds)){
				saveAccountRole(po, newRoleIds); 
			}
		}
		v.setId(po.getId());
	}

	@Override
	public List<AccountVo> listByOrgCode(String code) throws GlobalException {
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("org.code", QueryCondition.EQ,code.trim()));
		conditions.add(new QueryCondition("org.isDel", QueryCondition.EQ,Po.N));
		return super.list(conditions);
	}
	@Override
	public List<UserVo> listUser(String orgCode, String role) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("role.code", QueryCondition.EQ, role));
		queryList.add(new QueryCondition("role.isDel", QueryCondition.EQ, Po.N));
		//queryList.add(new QueryCondition("account.org.code", QueryCondition.EQ, orgCode));
		queryList.add(new QueryCondition("account.user.isDel", QueryCondition.EQ, Po.N));
		List<AccountRole> list = accountRoleDao.list(queryList);
		List<UserVo> voList = new ArrayList<UserVo>();
		UserVo vo = null;
		for(AccountRole ar:list){
			 vo = new UserVo();
			 BeanUtils.copyProperties(ar.getAccount().getUser(), vo);
			 voList.add(vo);
		}
		return voList;
	}
	@SuppressWarnings("static-access")
	@Override
	public void update2del(String... ids) throws GlobalException {
		List<Account> pList=accountDao.listByIds(ids);
		for(Account p:pList){
			p.setIsDel(Po.Y);
			accountDao.update(p);
			//new OpenIM().deleteUser(p.getLoginName());
		}
	}

	@Override
	public void synAccount() throws GlobalException {
		JSONArray arry=OpenIM.getGroupList(getCurrent().getLoginName());
		try {
			for (Object object : arry) {
				JSONObject map=(JSONObject) object;
				long tribe_id=(long) map.get("tribe_id");
				JSONArray userArry=OpenIM.getGroupUsers(getCurrent().getLoginName(), tribe_id);
				for (Object user : userArry) {
					JSONObject userObj=(JSONObject) user;
					String uid=(String) userObj.get("uid");
					OpenIM.deleteUser(uid);//删除用户
				}
				OpenIM.delGroup(getCurrent().getLoginName(), tribe_id);//删除群
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String hql="FROM "+accountDao.getEntityName(Account.class)+" WHERE isDel='"+Po.N+"' ";
		List<Account> atList=accountDao.list(hql);
		for (Account at : atList) {
			//验证群是否存在
			Org org = at.getOrg();
			if(null!=org.getImId()) {
				boolean flag=OpenIM.getGroup(getCurrent().getLoginName(), org.getImId());
				if(!flag) {
					long imId=OpenIM.addGroup(getCurrent().getLoginName(),getOrgName(org));
					org.setImId(imId);
					orgDao.update(org);
				}
			}else {
				long imId=OpenIM.addGroup(getCurrent().getLoginName(),getOrgName(org));
				org.setImId(imId);
				orgDao.update(org);
			}
			//验证并更新用户
			if(!OpenIM.getUser(at.getLoginName())) {
				OpenIM.addUser(at);
			}
			OpenIM.addUser2Group(at.getLoginName(), org.getImId());
		}
	}
	public String getOrgName(Org org) {
		if(org.getLevel()==3) {
			return org.getParent().getParent().getName()+"-"+org.getParent().getName()+"-"+org.getName();
		}else if(org.getLevel()==2) {
			return org.getParent().getName()+"-"+org.getName();
		}else {
			return org.getName();
		}
	}
	
	@Override
	public List<CyrVo> listByUsers(List<AccountVo> users) throws GlobalException {
		List<CyrVo> cyrs = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			CyrVo cyr = new CyrVo();
			cyr.setId(users.get(i).getId()); 
			cyr.setName(users.get(i).getUserName());
			cyrs.add(cyr);
		}
		return cyrs;
	}
	
}
