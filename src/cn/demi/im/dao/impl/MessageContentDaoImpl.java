package cn.demi.im.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.QueryCondition;
import cn.demi.im.dao.IMessageContentDao;
import cn.demi.im.po.MessageContent;
/**
 * Create on : 2016年12月13日 下午5:29:24  <br>
 * Description :  messageContentDaoImpl<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Repository("im.messageContentDao")
public class MessageContentDaoImpl extends BaseDaoImpl<MessageContent> implements IMessageContentDao {

	@Override
	public List<MessageContent> listByMineAndToId(String mineId,String toId) {
		List<QueryCondition> queryConditionsList = new ArrayList<QueryCondition>();
		StringBuffer jpql = new StringBuffer();
		jpql.append("(conversation.sender.id='"+mineId+"' AND  conversation.receive.id='"+toId+"')");
		jpql.append(" OR (conversation.sender.id='"+toId+"' AND  conversation.receive.id='"+mineId+"')");
		queryConditionsList.add(new QueryCondition(jpql.toString()));
		List<OrderCondition> orderConditionsList = new ArrayList<OrderCondition>();
		orderConditionsList.add(new OrderCondition("senderTime", OrderCondition.ORDER_ASC));
		return super.list(queryConditionsList, orderConditionsList);
	}

	@Override
	public MessageContent findByMineAndToId(String mineId, String toId,String ct, String time) {
		List<QueryCondition> queryConditionsList = new ArrayList<QueryCondition>();
		queryConditionsList.add(new QueryCondition("conversation.sender.id='"+mineId+"'"));
		queryConditionsList.add(new QueryCondition("conversation.receive.id='"+toId+"'"));
		queryConditionsList.add(new QueryCondition("content like '"+ct+"'"));
		queryConditionsList.add(new QueryCondition("senderTime like '"+time+"%'"));
		List<MessageContent> pList=super.list(queryConditionsList, null);
		if(null!=pList&&pList.size()>0) {
			return pList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public void addData(MessageContent po) {
		if(po.getSort()==null) {
			po.setSort(getMaxSort()+1);
		}
		po.setCreateUser("lims【dataSync】");
		po.setLastUpdUser("lims【dataSync】");
		po.setCreateTime(System.currentTimeMillis());
		po.setLastUpdTime(System.currentTimeMillis());
		getEntityManager().persist(po);
	}
}
