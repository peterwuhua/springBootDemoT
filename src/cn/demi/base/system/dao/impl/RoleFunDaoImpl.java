package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IRoleFunDao;
import cn.demi.base.system.po.RoleFun;

/**
 * <strong>Create on : 2016年11月2日 下午5:07:30 </strong> <br>
 * <strong>Description : 功能和角色的关联</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.roleFunDao")
public class RoleFunDaoImpl extends BaseDaoImpl<RoleFun> implements IRoleFunDao {

	@Override
	public void deleteByRoleId(String roleId) throws GlobalException {
		super.deleteAll(listByRoleId(roleId));
	}

	@Override
	public List<RoleFun> listByRoleId(String... roleId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("role.id IN ('"
				+ toString(roleId).replace(",", "','") + "')"));
		return super.list(queryList);
	}
	
	@Override
	public List<RoleFun> getFunctionList(String... roleId)
			throws GlobalException{
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND role.id IN ('"+ toString(roleId).replace(",", "','") + "')");
		jpql.append(" AND LENGTH(function.url) >5");
		jpql.append(" GROUP BY function ");
		jpql.append(" ORDER BY function.sort ASC ");
		return list(jpql.toString());
	}

	@Override
	public List<RoleFun> list(String pfunctionId, String... roleId)
			throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND role.id IN ('"+ toString(roleId).replace(",", "','") + "')");
		jpql.append(" AND role.isDel ='"+Po.N+"'");
		jpql.append(" AND function.parent.id ='" + pfunctionId+ "' ");
		jpql.append(" AND function.isDel ='" +Po.N+ "' ");
		jpql.append(" GROUP BY function ");
		jpql.append(" ORDER BY function.sort ASC ");
		return list(jpql.toString());
		
	}
	@Override
	public Set<String> listRole(String funCode, String wfCode) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE role.isDel= "+Po.N+" ");
		if(!StrUtils.isBlankOrNull(funCode)) {
			jpql.append(" AND function.code ='" + funCode+ "' ");
		}
		if(!StrUtils.isBlankOrNull(wfCode)) {
			jpql.append(" AND function.wfCode ='" + wfCode+ "' ");
		}
		List<RoleFun> rfList=list(jpql.toString());
		Set<String> rolelist=new HashSet<>();
		if(null!=rfList) {
			for (RoleFun rf : rfList) {
				rolelist.add(rf.getRole().getId());
			}
		}
		return rolelist;
	}

}
