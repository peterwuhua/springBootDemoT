package cn.demi.im.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.im.vo.MessageContentVo;
/**
 * Create on : 2016年12月13日 下午5:32:35  <br>
 * Description : IM消息记录  IMessageContentService<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Transactional
public interface IMessageContentService extends IBaseService<MessageContentVo> {
	/**
	 * Create on : Paddy Zhang 2016年12月13日 下午5:32:40 <br>
	 * Description : 查询消息记录 <br>
	 * @param mineId 发送人ID
	 * @param toId 收信人ID
	 * @return map (消息记录详细信息)
	 * @throws GlobalException 
	 */
	public Map<String, Object> getchatMessage(String mineId, String toId) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2016年12月14日 上午11:30:03 <br>
	 * Description : 拼装消息记录的Map <br>
	 * @param v
	 * @param resultList
	 * @return
	 * @throws GlobalException
	 */
	public Map<String, Object> getChatContent(MessageContentVo v, List<MessageContentVo> resultList)throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年3月10日 下午4:28:07 <br>
	 * Description : 获取离线消息集合  <br>
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public List<MessageContentVo> listOffLine(String id) throws GlobalException;
	/**
	 * Create on : Paddy Zhang 2017年3月10日 下午5:31:46 <br>
	 * Description : 改变消息是否发送成功  <br>
	 * @param id
	 * @param status
	 * @throws GlobalException
	 */
	public void update4Statuas(MessageContentVo v) throws GlobalException;
	/**
	 * IM定时任务 保存消息
	 * @param v
	 * @throws GlobalException
	 */
	boolean addData4IM(MessageContentVo v) throws GlobalException;
}
