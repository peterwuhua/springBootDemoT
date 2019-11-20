package cn.demi.base.system.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.base.system.dao.IRoleDao;
import cn.demi.base.system.po.Role;
/**
 * <strong>Create on : 2016年11月2日 下午5:07:18 </strong> <br>
 * <strong>Description : RoleDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {
}
