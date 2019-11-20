package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IRolePermDao;
import cn.demi.base.system.po.RolePerm;
/**
 * Create on : 2016年11月22日 下午2:25:17  <br>
 * Description :  RolePermDaoImpl<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Repository("sys.role_permDao")
public class RolePermDaoImpl extends BaseDaoImpl<RolePerm> implements IRolePermDao {

	@Override
	public List<RolePerm> listByRoleAndFunId(String funId, String roleId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		List<RolePerm> rolePermList = null;
		if(StrUtils.isNotBlankOrNull(funId)&&StrUtils.isNotBlankOrNull(roleId)){
			queryList.add(new QueryCondition("permission.function.id",QueryCondition.EQ,funId));
			queryList.add(new QueryCondition("role.id", QueryCondition.EQ, roleId));
			rolePermList =  super.list(queryList);
		}
		return rolePermList;
	}

	@Override
	public List<RolePerm> listByRoleId(String roleId) {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		List<RolePerm> rolePermList = null;
		queryList.add(new QueryCondition("role.id", QueryCondition.EQ, roleId));
		rolePermList =  super.list(queryList);
		return rolePermList;
	}

	
	
}
