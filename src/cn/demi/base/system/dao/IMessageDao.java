package cn.demi.base.system.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.Message;
/**
 * <strong>Create on : 2016年11月2日 下午2:48:42 </strong> <br>
 * <strong>Description : 邮件DAO </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IMessageDao extends IBaseDao<Message>{
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午5:43:01 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param user user
	 * @param position position
	 * @param q q
	 * @return List
	 * @throws GlobalException
	 */
	public List<Message> list(String user,int position,String q) throws GlobalException;
}
