package cn.demi.im.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.im.dao.IConversationDao;
import cn.demi.im.po.Conversation;
/**
 * Create on : 2016年12月13日 下午5:28:53  <br>
 * Description : 会话 conversationDaoImpl<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Repository("im.conversationDao")
public class ConversationDaoImpl extends BaseDaoImpl<Conversation> implements IConversationDao {
	@Override
	public Conversation findByMineAndToId(String mineId, String toId) {
		String hql="FROM "+getEntityName(Conversation.class)+" WHERE isDel='"+Po.N+"' AND sender.id='"+mineId.trim()+"' AND receive.id='"+toId.trim()+"'";
		List<Conversation> pList=super.list(hql);
		if(null!=pList&&pList.size()>0) {
			return pList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public void addData(Conversation po) {
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
