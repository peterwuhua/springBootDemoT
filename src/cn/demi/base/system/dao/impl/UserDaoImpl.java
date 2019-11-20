package cn.demi.base.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.IUserDao;
import cn.demi.base.system.po.User;
/**
 * <strong>Create on : 2016年11月2日 下午5:08:06 </strong> <br>
 * <strong>Description : UserDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	@Override
	public List<String> getPreFixCnameList() throws GlobalException {
		StringBuffer jpql = new StringBuffer(
				"SELECT DISTINCT(substring(cname,1,1)) AS c FROM "
						+ getEntityName(User.class) + " WHERE isDel= " + Po.N
						+ " ORDER BY c ASC");
		@SuppressWarnings("unchecked")
		List<String> l = super.query(jpql.toString()).getResultList();
		return l;
	}
}
