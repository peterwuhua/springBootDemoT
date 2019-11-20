package cn.demi.im.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.openIM.OpenIM;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.UserVo;
import cn.demi.im.dao.IConversationDao;
import cn.demi.im.dao.IMessageContentDao;
import cn.demi.im.po.Conversation;
import cn.demi.im.po.MessageContent;
import cn.demi.im.service.IMessageContentService;
import cn.demi.im.vo.ConversationVo;
import cn.demi.im.vo.MessageContentVo;
/**
 * Create on : 2016年12月13日 下午5:30:04  <br>
 * Description : im消息记录  messageContentServiceImpl<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Service("im.messageContentService")
public class MessageContentServiceImpl extends BaseServiceImpl<MessageContentVo,MessageContent> implements
		IMessageContentService {
	@Autowired private IConversationDao conversationDao;
	@Autowired private IMessageContentDao messageContentDao;
	@Override
	public MessageContent vo2Po(MessageContentVo v) throws GlobalException {
		MessageContent messageContent= super.vo2Po(v);
		if(StrUtils.isNotBlankOrNull(v.getConversationVo().getId())){
			Conversation conversation=conversationDao.findById(v.getConversationVo().getId());
			messageContent.setConversation(conversation);
		}
		return messageContent;
	}
	@Override
	public Map<String, Object> getchatMessage(String mineId, String toId) throws GlobalException {
		Map<String, Object> message = new HashMap<String, Object>();
		try {
			List<MessageContent> contentList= messageContentDao.listByMineAndToId(mineId, toId);
			message.put("code","0");
			message.put("msg","");
			List<Map<String,Object>> listcontent = new ArrayList<Map<String,Object>>();
			for (MessageContent messageContent : contentList) {
				Map<String, Object> content = new HashMap<String, Object>();
				MessageContentVo vo = new MessageContentVo();
				vo.toVo(messageContent,vo);
				ConversationVo converVo = vo.getConversationVo();
				AccountVo senderVo = converVo.getSenderVo();
				UserVo senderUserVo = senderVo.getUserVo();
				content.put("username",senderVo.getLoginName()+"【"+senderUserVo.getName()+"】");
				content.put("id",senderVo.getId());
				if(StrUtils.isNotBlankOrNull(senderUserVo.getAvatar())){
					content.put("avatar","/static/upload/"+senderUserVo.getAvatar());//头像
				}else{
					content.put("avatar","/static/upload/avatar/default.jpg");//头像
				}
				content.put("timestamp",Long.valueOf(vo.getSenderTime()));
				content.put("content",vo.getContent());
				listcontent.add(content);
			}
			message.put("data",listcontent);

		} catch (Exception e) {
			message.put("code","1");
			message.put("msg","获取消息记录失败");
			message.put("data","");
			e.printStackTrace();
			throw new GlobalException("获取消息记录失败/或暂无消息", e);
		}
		return message;
	}
	@Override
	public PageResult pageResult(MessageContentVo v, PageResult pageResult) throws GlobalException {
		List<QueryCondition> queryConditionsList = new ArrayList<QueryCondition>();
		StringBuffer jpql = new StringBuffer();
		jpql.append("(conversation.sender.id='"+v.getMineId()+"' AND  conversation.receive.id='"+v.getToId()+"')");
		jpql.append(" OR (conversation.sender.id='"+v.getToId()+"' AND  conversation.receive.id='"+v.getMineId()+"')");
		queryConditionsList.add(new QueryCondition(jpql.toString()));
		List<OrderCondition> orderConditionsList = new ArrayList<OrderCondition>();
		orderConditionsList.add(new OrderCondition("senderTime", OrderCondition.ORDER_ASC));
		pageResult.setQueryList(queryConditionsList);
		pageResult.setOrderColumn("senderTime");
		pageResult.setOrder(OrderCondition.ORDER_DESC);
		return super.pageResult(v, pageResult);
	}
	@Override
	public Map<String, Object> getChatContent(MessageContentVo v, List<MessageContentVo> resultList) throws GlobalException {
		Map<String, Object> message = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> listcontent = new ArrayList<Map<String,Object>>();
			
			for (MessageContentVo vo : resultList) {
				Map<String, Object> content = new HashMap<String, Object>();
				ConversationVo converVo = vo.getConversationVo();
				AccountVo senderVo = converVo.getSenderVo();
				UserVo senderUserVo = senderVo.getUserVo();
				String username = "";
				if(null != senderUserVo){
					username = senderVo.getLoginName()+"【"+senderUserVo.getName()+"】";
					if(StrUtils.isNotBlankOrNull(senderUserVo.getAvatar())){
						content.put("avatar","/static/upload/"+senderUserVo.getAvatar());//头像
					}else{
						content.put("avatar","/static/upload/avatar/default.jpg");//头像
					}
				}else{
					content.put("avatar","/static/upload/avatar/default.jpg");//头像
					username = senderVo.getLoginName();
				}
				content.put("username",username);
				content.put("id",senderVo.getId());         
				content.put("timestamp",Long.valueOf(vo.getSenderTime()));
				content.put("content",vo.getContent());
				listcontent.add(content);
			}
			message.put("data",listcontent);
		} catch (Exception e) {
			message.put("code","1");
			message.put("msg","获取消息记录失败");
			message.put("data","");
			e.printStackTrace();
			throw new GlobalException("获取消息记录失败/或暂无消息", e);
		}
		return message;
	}
	@Override
	public void add(MessageContentVo v) throws GlobalException {
		MessageContent p = vo2Po(v);
		Current current = v.getCurrent();
		if(null != current){
			p.setCreateUser(current.getKey());
			p.setLastUpdUser(current.getKey());
		}
		messageContentDao.add(p);
		v.setId(p.getId());
		
		try {
			OpenIM.pushMsg(p.getConversation().getSender().getLoginName(), p.getConversation().getReceive().getLoginName(), p.getContent());
		} catch (Exception e) {
			 log.error("消息通信异常！", e);
		}
	}
	@Override
	public boolean addData4IM(MessageContentVo v) throws GlobalException {
		MessageContent mct=messageContentDao.findByMineAndToId(v.getMineId(), v.getToId(),v.getContent(), v.getSenderTime());
		if(mct!=null) {
			return false;
		}else {
			mct = vo2Po(v);
			Current current = v.getCurrent();
			if(null != current){
				mct.setCreateUser(current.getKey());
				mct.setLastUpdUser(current.getKey());
			}
			if(!StrUtils.isBlankOrNull(v.getSenderTime())) {
				mct.setSenderTime(v.getSenderTime()+"000");
			}
			messageContentDao.addData(mct);
			v.setId(mct.getId());
			return true;
		}
	}
	@Override
	public void update4Statuas(MessageContentVo v) throws GlobalException {
		MessageContent p = messageContentDao.findById(v.getId());
		p.setStatus(v.getStatus());
		Current current = v.getCurrent();
		if(MessageContent.TRUE.equals(v.getStatus())){
			p.setReceiveTime(String.valueOf(System.currentTimeMillis()));
		}
		if(null != current){
			p.setCreateUser(current.getKey());
		}
		messageContentDao.update(p);
	}
	@Override
	public List<MessageContentVo> listOffLine(String id) throws GlobalException {
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("conversation.receive.id",QueryCondition.EQ,id));
		conditions.add(new QueryCondition("status",QueryCondition.EQ,MessageContent.FALSE));
		return super.list(conditions);
	}
	
	
}
