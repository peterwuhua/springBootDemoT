package cn.demi.base.system.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.IMessageDetailDao;
import cn.demi.base.system.po.MessageDetail;
import cn.demi.base.system.service.IMessageDetailService;
import cn.demi.base.system.vo.MessageDetailVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:11:53 </strong> <br>
 * <strong>Description : MessageDetailServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.messageDetailService")
public class MessageDetailServiceImpl extends BaseServiceImpl<MessageDetailVo, MessageDetail> implements
		IMessageDetailService {
	@Autowired IMessageDetailDao messageDetailDao;
	@Override
	public List<MessageDetailVo> list(String user,Integer position,  String subject)
			throws GlobalException {
		return toVoList(messageDetailDao.list(user,position, subject));
	}
	@Override
	public void updatePosition(String ids, int position) throws GlobalException {
		List<MessageDetail> messageDetailList = messageDetailDao.listByIds(ids);
		if(CollectionUtils.isEmpty(messageDetailList))
			return;
		for(MessageDetail p:messageDetailList){
			if (2==position) {
				//标记为已读
				p.setReadFlag(1);
			}else {
				p.setPosition(position);
			}
			messageDetailDao.update(p);
		}
	}
	//在用jqGrid需要添加条件的时候可以重写次方法
	/*@Override
	public List<QueryCondition> toQueryList(MessageDetailVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("position", QueryCondition.EQ,0));
		return queryList;
	}*/
	
}
