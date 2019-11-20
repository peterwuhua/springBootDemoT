package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.demi.base.system.dao.IPermissionDao;
import cn.demi.base.system.po.Permission;
/**
 * Create on : 2016年11月22日 下午3:03:39  <br>
 * Description :  PermissionDaoImpl<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Repository("sys.permissionDao")
public class PermissionDaoImpl extends BaseDaoImpl<Permission> implements IPermissionDao {

	@Override
	public Permission findByCode(String code) {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("code", QueryCondition.EQ,code));
		return super.query0(queryConditions, null);
		
	}
}
