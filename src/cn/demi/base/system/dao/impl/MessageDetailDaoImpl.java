package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IMessageDetailDao;
import cn.demi.base.system.po.MessageDetail;
/**
 * <strong>Create on : 2016年11月2日 下午5:06:49 </strong> <br>
 * <strong>Description : MessageDetailDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.messageDetailDao")
public class MessageDetailDaoImpl extends BaseDaoImpl<MessageDetail> implements IMessageDetailDao {

	@Override
	public List<MessageDetail> listByMessageId(String messageId) {
		if (StrUtils.isEmpty(messageId))
			return null;
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("message.id", QueryCondition.EQ,
				messageId));
		return super.list(queryList, null);
	}

	@Override
	public List<MessageDetail> list(String user,Integer position, String subject)
			throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,Po.N));
		//queryList.add(new QueryCondition("message.subject", QueryCondition.EQ,q));
		queryList.add(new QueryCondition("reciver.id", QueryCondition.EQ,user));
			queryList.add(new QueryCondition("position", QueryCondition.EQ,position));
		if (!StrUtils.isBlankOrNull(subject)) {
			queryList.add(new QueryCondition("message.subject", QueryCondition.AK,subject));
		}
		return super.list(queryList, null);
	}
}
