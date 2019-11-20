package cn.demi.base.system.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.User;
/**
 * <strong>Create on : 2016年11月2日 下午2:55:19 </strong> <br>
 * <strong>Description : 用户信息DAO </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IUserDao extends IBaseDao<User> {
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午5:45:15 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @return List
	 * @throws GlobalException
	 */
	public List<String> getPreFixCnameList() throws GlobalException;
}
