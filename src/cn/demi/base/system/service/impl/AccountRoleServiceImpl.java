package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IAccountRoleDao;
import cn.demi.base.system.dao.IRoleFunDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.AccountRole;
import cn.demi.base.system.service.IAccountRoleService;
import cn.demi.base.system.vo.AccountRoleVo;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.RoleVo;
import cn.demi.base.system.vo.UserVo;

/**
 * <strong>Create on : 2016年11月2日 下午5:10:41 </strong> <br>
 * <strong>Description :AccountRoleServiceImpl </strong> <br>
 * 
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.accountRoleService")
public class AccountRoleServiceImpl extends BaseServiceImpl<AccountRoleVo, AccountRole> implements IAccountRoleService {
	@Autowired
	private IAccountRoleDao accountRoleDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private IRoleFunDao roleFunDao;

	@Override
	public List<AccountRoleVo> listByAccountId(String accountId) throws GlobalException {
		List<AccountRole> pList = accountRoleDao.listByAccountId(accountId);
		return toVoList(pList);
	}

	@Override
	public AccountRoleVo po2Vo(AccountRole p) throws GlobalException {
		AccountRoleVo vo = super.po2Vo(p);
		vo.setAccountVo(new AccountVo().toVo(p.getAccount()));
		vo.setRoleVo(new RoleVo().toVo(p.getRole()));
		return vo;
	}

	@Override
	public List<AccountRoleVo> listByRoleId(String roleId) throws GlobalException {
		List<AccountRole> pList = accountRoleDao.listByRoleId(roleId);
		return toVoList(pList);
	}

	@Override
	public List<QueryCondition> toQueryList(AccountRoleVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("account.isDel", QueryCondition.EQ, Po.N));
		if (null != v.getRoleVo() && !StrUtils.isBlankOrNull(v.getRoleVo().getId())) {
			queryConditions.add(new QueryCondition("role.id", QueryCondition.EQ, v.getRoleVo().getId()));
		}
		queryConditions.add(new QueryCondition("role.isDel", QueryCondition.EQ, Po.N));
		return queryConditions;
	}

	@Override
	public List<AccountRoleVo> list(AccountRoleVo v) throws GlobalException {
		List<QueryCondition> queryConditions = this.toQueryList(v);
		return toVoList(accountRoleDao.list(queryConditions));
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, AccountRoleVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());

		// pageResult.addOrder("account.user.name", "asc");//添加排序

		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		pageResult.addConditionList(toQueryList(v));

		PageBean pageBean = pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if (0 != gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);

		pageResult = accountRoleDao.getPageResult(pageResult);

		gridVo.setDatas(poList2mapList((List<AccountRole>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<AccountVo> listAccount(GridVo gridVo, String orgCode, String roleCode, String funCode) throws GlobalException {
		List<QueryCondition> conditions = getFilterRules(gridVo.getFilters());
		if (conditions == null)
			conditions = new ArrayList<>();
		conditions.add(new QueryCondition("account.org.code", QueryCondition.RK, orgCode));
		conditions.add(new QueryCondition("account.org.isDel", QueryCondition.EQ, Po.N));
		conditions.add(new QueryCondition("account.isDel", QueryCondition.EQ, Po.N));
		if (!StrUtils.isBlankOrNull(roleCode)) {
			conditions.add(new QueryCondition("role.code", QueryCondition.EQ, roleCode));
		}
		conditions.add(new QueryCondition("role.isDel", QueryCondition.EQ, Po.N));
		if (!StrUtils.isBlankOrNull(funCode)) {
			Set<String> roleId = roleFunDao.listRole(funCode, null);
			conditions.add(new QueryCondition("role.id in ('" + String.join("','", roleId) + "')"));
		}
		List<AccountRole> list = accountRoleDao.list(conditions);
		Set<Account> actSet = new HashSet<Account>();
		if (null != list) {
			for (AccountRole ar : list) {
				actSet.add(ar.getAccount());
			}
		}
		List<AccountVo> listVo = new ArrayList<AccountVo>();
		AccountVo vo = null;
		for (Account acct : actSet) {
			vo = new AccountVo();
			BeanUtils.copyProperties(acct, vo);
			UserVo userVo = new UserVo();
			BeanUtils.copyProperties(acct.getUser(), userVo);
			vo.setUserVo(userVo);
			listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public List<AccountVo> listAccount(GridVo gridVo, String roleCode) throws GlobalException {
		List<QueryCondition> conditions = getFilterRules(gridVo.getFilters());
		if (conditions == null)
			conditions = new ArrayList<>();
		conditions.add(new QueryCondition("account.isDel", QueryCondition.EQ, Po.N));
		if (!StrUtils.isBlankOrNull(roleCode)) {
			conditions.add(new QueryCondition("role.code", QueryCondition.EQ, roleCode));
		}
		conditions.add(new QueryCondition("role.isDel", QueryCondition.EQ, Po.N));
		List<AccountRole> list = accountRoleDao.list(conditions);
		Set<String> actSet = new HashSet<String>();
		if (null != list) {
			for (AccountRole ar : list) {
				Account at = ar.getAccount();
				actSet.add(at.getId());
			}
		}
		List<Account> atlist = accountDao.listByIds(String.join(",", actSet), "user.no", "asc");
		List<AccountVo> listVo = new ArrayList<AccountVo>();
		AccountVo vo = null;
		for (Account acct : atlist) {
			vo = new AccountVo();
			BeanUtils.copyProperties(acct, vo);
			UserVo userVo = new UserVo();
			BeanUtils.copyProperties(acct.getUser(), userVo);
			vo.setUserVo(userVo);
			listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public List<AccountVo> listAccount(String roleCode) throws GlobalException {
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("account.isDel", QueryCondition.EQ, Po.N));
		conditions.add(new QueryCondition("role.code", QueryCondition.EQ, roleCode));
		conditions.add(new QueryCondition("role.isDel", QueryCondition.EQ, Po.N));
		List<AccountRole> list = accountRoleDao.list(conditions);
		Set<Account> actSet = new HashSet<Account>();
		if (null != list) {
			for (AccountRole ar : list) {
				actSet.add(ar.getAccount());
			}
		}
		List<AccountVo> listVo = new ArrayList<AccountVo>();
		AccountVo vo = null;
		for (Account acct : actSet) {
			vo = new AccountVo();
			BeanUtils.copyProperties(acct, vo);
			UserVo userVo = new UserVo();
			BeanUtils.copyProperties(acct.getUser(), userVo);
			vo.setUserVo(userVo);
			listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public List<AccountVo> listAccount(String orgCode, String roleCode, String funCode) throws GlobalException {
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("account.isDel", QueryCondition.EQ, Po.N));
		if (!StrUtils.isBlankOrNull(orgCode)) {
			conditions.add(new QueryCondition("account.org.code", QueryCondition.RK, orgCode));
			conditions.add(new QueryCondition("account.org.isDel", QueryCondition.EQ, Po.N));
		}
		if (!StrUtils.isBlankOrNull(roleCode)) {
			conditions.add(new QueryCondition("role.code", QueryCondition.EQ, roleCode));
			conditions.add(new QueryCondition("role.isDel", QueryCondition.EQ, Po.N));
		}
		if (!StrUtils.isBlankOrNull(funCode)) {
			Set<String> roleId = roleFunDao.listRole(funCode, null);
			conditions.add(new QueryCondition("role.id in ('" + String.join("','", roleId) + "')"));
		}
		List<AccountRole> list = accountRoleDao.list(conditions);
		Set<Account> actSet = new HashSet<Account>();
		if (null != list) {
			for (AccountRole ar : list) {
				actSet.add(ar.getAccount());
			}
		}
		List<AccountVo> listVo = new ArrayList<AccountVo>();
		AccountVo vo = null;
		for (Account acct : actSet) {
			vo = new AccountVo();
			BeanUtils.copyProperties(acct, vo);
			UserVo userVo = new UserVo();
			BeanUtils.copyProperties(acct.getUser(), userVo);
			vo.setUserVo(userVo);
			listVo.add(vo);
		}
		return listVo;
	}

	@Override
	public List<AccountVo> listAccountByOrg(String roleCode, String itemType) throws GlobalException {
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("account.isDel", QueryCondition.EQ, Po.N));
		conditions.add(new QueryCondition("role.code", QueryCondition.EQ, roleCode));
		conditions.add(new QueryCondition("role.isDel", QueryCondition.EQ, Po.N));
		if (itemType.contains("环境咨询")||itemType.contains("环境")) {
			conditions.add(new QueryCondition(" account.org.name like '%环境%' "));
		} else if (itemType.contains("安全") || itemType.contains("职业卫生")) {
			conditions.add(new QueryCondition(" account.org.name like '%职业卫生%' "));
		}
		List<AccountRole> list = accountRoleDao.list(conditions);
		Set<Account> actSet = new HashSet<Account>();
		if (null != list) {
			for (AccountRole ar : list) {
				actSet.add(ar.getAccount());
			}
		}
		List<AccountVo> listVo = new ArrayList<AccountVo>();
		AccountVo vo = null;
		for (Account acct : actSet) {
			vo = new AccountVo();
			BeanUtils.copyProperties(acct, vo);
			UserVo userVo = new UserVo();
			BeanUtils.copyProperties(acct.getUser(), userVo);
			vo.setUserVo(userVo);
			listVo.add(vo);
		}
		return listVo;
	}

}
