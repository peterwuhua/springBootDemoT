package cn.demi.base.system.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.MessageDetail;
/**
 * <strong>Create on : 2016年11月2日 下午2:49:00 </strong> <br>
 * <strong>Description : 邮件DAO </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IMessageDetailDao extends IBaseDao<MessageDetail>{
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午5:43:38 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param messageId messageId
	 * @return List
	 */
	public List<MessageDetail> listByMessageId(String messageId);
	public List<MessageDetail> list(String user,Integer position,String q) throws GlobalException;
}
