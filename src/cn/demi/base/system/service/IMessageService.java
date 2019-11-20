package cn.demi.base.system.service;

import java.util.List;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.MessageVo;
/**
 * <strong>Create on : 2016年11月2日 下午3:23:11 </strong> <br>
 * <strong>Description : 邮件service </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IMessageService extends IBaseService<MessageVo> {
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午3:23:22 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param user 登陆人
	 * @param position position
	 * @param q 查询
	 * @return List
	 * @throws GlobalException
	 */
	public List<MessageVo> list(String user,int position, String q) throws GlobalException;
	
	/**
	 * 获取未读邮件数
	 * @param user
	 * @return
	 * @throws GlobalException
	 */
	public int countInbox(String user) throws GlobalException;
	
	/**
	 * 获取各类别数量
	 * @return
	 * @throws GlobalException
	 */
	public MessageVo count4Category() throws GlobalException;
	
}
