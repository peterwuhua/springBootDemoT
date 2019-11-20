package cn.demi.base.system.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IMessageDao;
import cn.demi.base.system.dao.IMessageDetailDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Message;
import cn.demi.base.system.po.MessageDetail;
import cn.demi.base.system.service.IMessageService;
import cn.demi.base.system.vo.MessageVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:12:08 </strong> <br>
 * <strong>Description : MessageServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.messageService")
public class MessageServiceImpl extends BaseServiceImpl<MessageVo, Message> implements
		IMessageService {
	@Autowired
	private IMessageDetailDao messageDetailDao;
	@Autowired
	private IMessageDao messageDao;
	@Autowired
	private IAccountDao accountDao;
	
	@Override
	public void save(MessageVo v) throws GlobalException {
		Message po = null;
		if(!StrUtils.isBlankOrNull(v.getId())){
			po = messageDao.findById(v.getId());
		}else{
			po = new Message();
		}
		po.setStatus(v.getStatus());
		po.setContent(v.getContent());
		po.setReciver(v.getReciver());
		po.setReciverIds(v.getReciverIds());
		po.setPosition(v.getPosition());
		po.setSubject(v.getSubject());
		po.setSendTime(DateUtils.getCurrDateTimeStr());
		po.setSender(accountDao.findById(getCurrent().getAccountId()));
		messageDao.saveOrUpdate(po);
		if("1".equals(String.valueOf(v.getPosition())))
			saveMessageDetail(v.getReciverIds(), po);
		v.setId(po.getId());
	}
	/**
	 * <strong>Create on : 2016年11月2日 下午3:36:17 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param reciverIds reciverIds
	 * @param message message
	 */
	private void saveMessageDetail(String reciverIds,Message message) {
		List<Account> accountList =  accountDao.listByIds(reciverIds);
		if(CollectionUtils.isEmpty(accountList))
			return;
		messageDetailDao.deleteAll(messageDetailDao.listByMessageId(message.getId()));
		for(Account a:accountList){
			MessageDetail po = new MessageDetail();
			po.setMessage(message);
			po.setReciver(a);
			po.setPosition(message.getPosition());
			messageDetailDao.add(po);
		}
	}

	@Override
	public List<MessageVo> list(String user, int position, String subject)
			throws GlobalException {
		return toVoList(messageDao.list(user, position, subject));
	}
	@Override
	public int countInbox(String user) throws GlobalException {
		String jpql="SELECT count(id) FROM "+messageDetailDao.getEntityName(MessageDetail.class)+" WHERE readFlag='"+Po.N+"'  AND reciver like '"+user+"'";
		Object ct=messageDetailDao.query(jpql).getSingleResult();
		int count=0;
		try {
			count=Integer.valueOf(ct.toString());
		} catch (NumberFormatException e) {
			count=0;
		}
		return count;
	}
	@Override
	public MessageVo count4Category() throws GlobalException {
		MessageVo vo =new MessageVo();
		String jpql="SELECT count(id) FROM "+messageDetailDao.getEntityName(MessageDetail.class)+" WHERE readFlag='"+Po.N+"'  AND reciver like '"+getCurrent().getAccountId()+"'";
		Object ct=messageDetailDao.query(jpql).getSingleResult();
		int count1=0;
		try {
			count1=Integer.valueOf(ct.toString());
		} catch (NumberFormatException e) {
			count1=0;
		}
		vo.setSj(count1);
		
		List<Message> list1=messageDao.list(getCurrent().getAccountId(),1, null);
		if(null!=list1) {
			vo.setFj(list1.size());
		}
		List<Message> list2=messageDao.list(getCurrent().getAccountId(),3, null);
		if(null!=list2) {
			vo.setZy(list2.size());
		}
		List<Message> list3=messageDao.list(getCurrent().getAccountId(),-2, null);
		if(null!=list3) {
			vo.setCg(list3.size());
		}
		List<Message> list4=messageDao.list(getCurrent().getAccountId(),-1, null);
		if(null!=list4) {
			vo.setLj(list4.size());
		}
		return vo;
	}
	
	
}
