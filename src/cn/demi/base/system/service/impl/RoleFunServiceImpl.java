package cn.demi.base.system.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.CollectionUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IFunctionDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.dao.IRoleDao;
import cn.demi.base.system.dao.IRoleFunDao;
import cn.demi.base.system.po.Function;
import cn.demi.base.system.po.Role;
import cn.demi.base.system.po.RoleFun;
import cn.demi.base.system.service.IRoleFunService;
import cn.demi.base.system.vo.FunctionVo;
import cn.demi.base.system.vo.RoleFunVo;
import cn.demi.base.system.vo.RoleVo;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
/**
 * <strong>Create on : 2016年11月2日 下午5:12:37 </strong> <br>
 * <strong>Description : RoleFunServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.roleFunService")
public class RoleFunServiceImpl extends BaseServiceImpl<RoleFunVo, RoleFun>
		implements IRoleFunService {
	@Autowired
	private IFunctionDao functionDao;
	@Autowired
	private IRoleFunDao roleFunDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IOrgDao orgDao;
	@Override
	public List<RoleFunVo> listByRoleId(String roleId) throws GlobalException {
		return toVoList(roleFunDao.listByRoleId(roleId));
	}

	@Override
	public List<RoleFunVo> listByRoleIds(String... roleIds)
			throws GlobalException {
		return toVoList(roleFunDao.listByRoleId(roleIds));
	}
	
	@Override
	public Set<String> getFunctionAuth(String... roleId)
			throws GlobalException{
		List<RoleFun> roleFunctionList = roleFunDao.listByRoleId(roleId);
		Set<String> set = new HashSet<String>();
		for (RoleFun roleFun : roleFunctionList) {
			set.add(roleFun.getFunction().getCode());
		}
		return set;
	}

	@Override
	public void save(String roleId, String functionIds) throws GlobalException {
		roleFunDao.deleteAll(roleFunDao.listByRoleId(roleId));
		List<Function> functionList = functionDao.listByIds(functionIds);
		Role role = roleDao.findById(roleId);
		List<String> funNames=new ArrayList<String>();
		for (Function function : functionList) {
			RoleFun roleFun = new RoleFun();
			roleFun.setFunction(function);
			roleFun.setRole(role);
			roleFunDao.add(roleFun);
			if(!function.getId().equals("0")&&!StrUtils.isBlankOrNull(function.getUrl())) {
				funNames.add(function.getName());
			}
		}
		role.setFunNames(String.join(",", funNames));
	}

	@Override
	public List<RoleFunVo> getMenuList(String pfunctionId, String... roleIds)
			throws GlobalException {
		List<RoleFun> pList = roleFunDao.list(pfunctionId, roleIds);
		List<RoleFunVo> vList = null;

		if (CollectionUtils.isEmpty(pList)) {
			return vList;
		} else {
			vList = new ArrayList<RoleFunVo>();
			for (RoleFun rf : pList) {
				RoleFunVo v = new RoleFunVo();
				v.setRoleVo(new RoleVo().toVo(rf.getRole()));
				v.setFunctionVo(new FunctionVo().toVo(rf.getFunction()));
				v.setList(getMenuList(rf.getFunction().getId(), roleIds));

				vList.add(v);
			}
			return vList;
		}
	}
	
	@Override
	public List<RoleFunVo> getRoleFunctionList(String... roleId)
			throws GlobalException{
		List<RoleFunVo> vList = new ArrayList<RoleFunVo>();
		
		List<RoleFun> pList = roleFunDao.getFunctionList(roleId);
		if(null==pList||pList.size()==0)
			return vList;
		
		for (RoleFun rf : pList) {
			RoleFunVo v = new RoleFunVo();
			v.setRoleVo(new RoleVo().toVo(rf.getRole()));
			v.setFunctionVo(new FunctionVo().toVo(rf.getFunction()));

			List<RoleFunVo> roleFunList =getMenuList(rf.getFunction().getId(), roleId);
			if(CollectionUtils.isEmpty(roleFunList))//判断功能下是否还有子功能
				vList.add(v);//功能
		}
		return vList;
	}

	@Override
	public List<RoleFunVo> list(RoleFunVo v) throws GlobalException {
		List<QueryCondition> queryConditions = this.toQueryList(v);
		List<OrderCondition> orderConditionsList = new ArrayList<>();
		orderConditionsList.add(new OrderCondition("function.sort", "asc"));
		return toVoList(roleFunDao.list(queryConditions, orderConditionsList));
	}

	@Override
	public List<QueryCondition> toQueryList(RoleFunVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("function.isDel", QueryCondition.EQ,Po.N));
		queryConditions.add(new QueryCondition("role.isDel", QueryCondition.EQ,Po.N));
		if (null != v.getRoleVo() && !StrUtils.isBlankOrNull(v.getRoleVo().getId())) {
			queryConditions.add(new QueryCondition("role.id", QueryCondition.EQ,v.getRoleVo().getId()));
		}
		return queryConditions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleFunVo> getRoleFunList() throws GlobalException {
		String roleIds=getCurrent().getRoleIds();
		String orgId=getCurrent().getOrgId();
		List<String> orgIds=orgDao.listChild(orgId);
		orgIds.add(orgId);
		String userId=getCurrent().getAccountId();
		List<RoleFunVo> vList = new ArrayList<RoleFunVo>();
		
		StringBuffer hql=new StringBuffer("select fun.name, fun.url, count(fun.id) from "+tablePrefix+roleFunDao.getEntityName(Function.class)+" fun join "+tablePrefix+roleFunDao.getEntityName(Progress.class)+" pg on fun.wf_code=pg.status and pg.is_del='"+Po.N+"' ");
		hql.append(" where fun.is_del='"+Po.N+"' ");
		hql.append(" and (pg.user_id like '%"+userId+"%' or pg.user_id is null and pg.org_id in ('"+String.join("','", orgIds)+"') or pg.org_id like '%"+orgId+"%' or pg.user_id is null and pg.org_id is null)");
		hql.append(" and fun.id  in (select fun_id from "+tablePrefix+roleFunDao.getEntityName(RoleFun.class)+" where role_id in ('"+ roleIds.replace(",", "','") + "'))");
		hql.append(" group by fun.id order by fun.sort asc");
		 
		List<Object[]> pList = roleFunDao.queryBySql(hql.toString());
		if(null!=pList) {
			int n=0;
			for (Object[] obj : pList) {
				RoleFunVo v = new RoleFunVo();
				FunctionVo fun=new FunctionVo();
				String url=String.valueOf(obj[1]);
				fun.setUrl(url);
				fun.setName(String.valueOf(obj[0]));
				fun.setCount(Integer.parseInt(obj[2].toString()));
				v.setFunctionVo(fun);
				vList.add(v);
				n+=fun.getCount();
			}
			getCurrent().setDb(n);
		}
		
		hql=new StringBuffer("select count(log.id) from "+tablePrefix+roleFunDao.getEntityName(ProgressLog.class)+" log where log.is_del='"+Po.N+"' ");
		hql.append(" and log.user_id like '"+userId+"%'");
		BigInteger n = (BigInteger) roleFunDao.queryBySql(hql.toString()).get(0);
		getCurrent().setYb(n.intValue());
		getCurrent().setZs(getCurrent().getDb()+getCurrent().getYb());
		return vList;
	}

	@Override
	public List<FunctionVo> list4First() throws GlobalException{
		List<RoleFun> pList = roleFunDao.list(functionDao.findRoot().getId(), getCurrent().getRoleIds());
		List<FunctionVo> vList = null;

		if (CollectionUtils.isEmpty(pList)) {
			return vList;
		} else {
			vList = new ArrayList<FunctionVo>();
			for (RoleFun rf : pList) {
				FunctionVo f = new FunctionVo();
				f=f.toVo(rf.getFunction());
				f.setUrl("main.do?sysId="+f.getId());
				vList.add(f);
			}
			return vList;
		}
	}

}
