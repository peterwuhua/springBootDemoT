package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IFunctionDao;
import cn.demi.base.system.dao.IPermissionDao;
import cn.demi.base.system.dao.IRoleDao;
import cn.demi.base.system.dao.IRolePermDao;
import cn.demi.base.system.po.Function;
import cn.demi.base.system.po.Permission;
import cn.demi.base.system.po.Role;
import cn.demi.base.system.po.RolePerm;
import cn.demi.base.system.service.IRolePermService;
import cn.demi.base.system.vo.RolePermVo;

@Service("sys.role_permService")
public class RolePermServiceImpl extends BaseServiceImpl<RolePermVo,RolePerm> implements
		IRolePermService {
	@Autowired
	private IRolePermDao rolePermDao ;
	@Autowired
	private IPermissionDao permissionDao ;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IFunctionDao functionDao;
	@Override
	public List<QueryCondition> toQueryList(RolePermVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("role.isDel", QueryCondition.EQ,Po.N));
		queryConditions.add(new QueryCondition("permission.isDel", QueryCondition.EQ,Po.N));
		queryConditions.add(new QueryCondition("permission.function.isDel", QueryCondition.EQ,Po.N));
		
		if (null != v.getRoleVo() && StrUtils.isNotBlankOrNull(v.getRoleVo().getId())) {
			queryConditions.add(new QueryCondition("role.id", QueryCondition.EQ,v.getRoleVo().getId()));
		}
		if (null != v.getPermissionVo() && StrUtils.isNotBlankOrNull(v.getPermissionVo().getFunId())) {
			queryConditions.add(new QueryCondition("permission.function.id", QueryCondition.EQ,v.getPermissionVo().getFunId()));
		}
		return queryConditions;
	}
	@Override
	public void saveRolePerm(String roleId, String funId, String permIds) throws GlobalException {
		if(StrUtils.isBlankOrNull(roleId) || StrUtils.isBlankOrNull(funId)){
			throw  new GlobalException("角色ID(roleId)/功能ID(funId)  无效");
		}
		try {
			Collection<RolePerm> list =  rolePermDao.listByRoleAndFunId(funId,roleId);
			if(list.size()>0){
				rolePermDao.deleteAll(rolePermDao.listByRoleAndFunId(funId,roleId));
			}
		} catch (Exception e) {
			log.info("saveRolePerm-删除失败");
			throw	new GlobalException("删除功能下所有权限失败", e);
		}
		Role role = roleDao.findById(roleId);
		Function function = functionDao.findById(funId);
		if (null != role || null != function) {
			List<Permission> functionList = permissionDao.listByIds(permIds);
			for (Permission permission : functionList) {
				RolePerm rolePerm = new RolePerm();
				permission.setFunction(function);
				rolePerm.setRole(role);
				rolePerm.setPermission(permission);
				rolePermDao.add(rolePerm);
			}
		}
	}
	@Override
	public List<RolePermVo> listByRoleAndFunId(String funId ,String roleId) throws GlobalException {
		List<RolePerm> pList = rolePermDao.listByRoleAndFunId(funId,roleId);
		return toVoList(pList);
	}
	
	@Override
	public List<RolePermVo> listByRoleId(String roleId) throws GlobalException {
		List<RolePerm> pList = rolePermDao.listByRoleId(roleId);
		return toVoList(pList);
	}
	@Override
	public Set<String> listPerm(String roleIds) throws GlobalException {
		String jpql="FROM "+rolePermDao.getEntityName(RolePerm.class)+" WHERE isDel='"+Po.N+"' AND role.id in('"+roleIds.replace(",", "','")+"') ";
		List<RolePerm> lst=rolePermDao.list(jpql);
		Set<String> pset=new HashSet<>();
		if(null!=lst) {
			for (RolePerm rolePerm : lst) {
				pset.add(rolePerm.getPermission().getCode());
			}
		}
		return pset;
	}
}
