package cn.demi.base.system.dao;

import java.io.Serializable;
import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.po.Account;

/**
 * <strong>Create on : 2016年11月2日 下午2:33:01 </strong> <br>
 * <strong>Description : 账户DAO</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IAccountDao extends IBaseDao<Account> {
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:33:21 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param id id
	 * @return String
	 */
	public String getKeyById(Serializable id);
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:33:21 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param String loginName
	 * @return String
	 */
	public Account find(String loginName);
	/**
	 * Create on : Paddy Zhang 2017年1月20日 下午1:05:03 <br>
	 * Description : 根据用户ID获取账户信息 <br>
	 * @param ids
	 * @return 
	 */
	public List<Account> listByUserIds(String... ids);
	/**Create on : Paddy Zhang 2017年3月17日 上午10:21:48 <br>
	 * Description : 发送消息  <br>
	 * @param accountId 账户ID
	 * @param message 发送的消息
	 * @throws GlobalException
	 */
	public void imSendMsg(String accountId, String message) throws GlobalException;
}
