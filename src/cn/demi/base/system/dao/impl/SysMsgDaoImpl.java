package cn.demi.base.system.dao.impl;

import org.springframework.stereotype.Repository;
import cn.demi.base.system.dao.ISysMsgDao;
import cn.demi.base.system.po.SysMsg;
import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.DateUtils;

@Repository("sys.msgDao")
public class SysMsgDaoImpl extends BaseDaoImpl<SysMsg> implements ISysMsgDao {

	@Override
	public void sendMsg(String busId, String busType, String title, String content, String reciveId, String reciveName, String type) {
		SysMsg msg=new SysMsg();
		msg.setBusId(busId);
		msg.setBusType(busType);
		msg.setTitle(title);
		msg.setContent(content);
		msg.setReceiver(reciveName);
		msg.setReceiverId(reciveId);
		msg.setReceiverType(type);
		msg.setSendId(getCurrent().getAccountId());
		msg.setSendName(getCurrent().getUserName());
		msg.setSendTime(DateUtils.getCurrDateTimeStr());
		msg.setStatus(String.valueOf(Po.N));
		super.add(msg);
	}
}
