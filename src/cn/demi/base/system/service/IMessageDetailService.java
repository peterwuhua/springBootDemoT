package cn.demi.base.system.service;

import java.util.List;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.MessageDetailVo;
/**
 * Create on : 2016年11月3日 上午10:55:16  <br>
 * Description : 邮件service  <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public interface IMessageDetailService extends IBaseService<MessageDetailVo> {
	/**
	 * Create on : Dave Yu 2016年11月3日 上午10:59:49 <br>
	 * Description :  <br>
	 * @param user 登陆人
	 * @param position position
	 * @param q 查询
	 * @return List
	 * @throws GlobalException
	 */
	public List<MessageDetailVo> list(String user,Integer position, String q) throws GlobalException;
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午3:22:23 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param ids ids
	 * @param position position
	 * @throws GlobalException
	 */
	public void updatePosition(String ids,int position) throws GlobalException;
}
