package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IMessageDao;
import cn.demi.base.system.po.Message;
/**
 * <strong>Create on : 2016年11月2日 下午5:06:42 </strong> <br>
 * <strong>Description : MessageDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements IMessageDao {

	@Override
	public List<Message> list(String user, int position, String subject)
			throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ,Po.N));
		queryList.add(new QueryCondition("position", QueryCondition.EQ,position));
		queryList.add(new QueryCondition("sender.id", QueryCondition.EQ,user));
		if (!StrUtils.isBlankOrNull(subject)) {
			queryList.add(new QueryCondition("subject", QueryCondition.LK,subject));
		}
		//queryList.add(new QueryCondition("reciver.id", QueryCondition.EQ,user));
		return super.list(queryList, null);
	}
}
