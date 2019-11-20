package cn.demi.base.system.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.UserVo;
import cn.demi.im.webscoket.ChatAnnotation;
import net.sf.json.JSONObject;
/**
 * <strong>Create on : 2016年11月2日 下午5:05:23 </strong> <br>
 * <strong>Description : AccountDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.accountDao")
public class AccountDaoImpl extends BaseDaoImpl<Account> implements IAccountDao {

	@Override
	public String getKeyById(Serializable id) {
		Account a = findById(id);
		return a.getKey();
	}

	@Override
	public Account find(String loginName) {
		List<QueryCondition> queryConditionsList = new ArrayList<QueryCondition>();
		queryConditionsList.add(new QueryCondition("loginName",
				QueryCondition.EQ, loginName));
		queryConditionsList.add(new QueryCondition("isDel", QueryCondition.EQ,
				Po.N));
		return super.query0(queryConditionsList, null);
	}

	@Override
	public List<Account> listByUserIds(String... ids) {
		List<QueryCondition> queryCondition = new ArrayList<QueryCondition>();
		queryCondition.add(new QueryCondition("user.id in('"+toString(ids).replace(",", "','")+"')"));
		return super.list(queryCondition);
	}
	
	@Override
	public void imSendMsg(String accountId,String message) throws GlobalException {
		if(StrUtils.isBlankOrNull(accountId))
			throw new GlobalException("无效参数");
		//mine
		Current current = getCurrent();
		Account mineAccount =  super.findById(current.getAccountId());
		AccountVo mineAccountVo = new AccountVo();
		mineAccountVo = mineAccountVo.toVo(mineAccount,mineAccountVo);
		//to
		Account toAccount = super.findById(accountId);
		AccountVo toAccountVo = new AccountVo();
		toAccountVo = toAccountVo.toVo(toAccount,toAccountVo);
		
		Map<String, Session> clients = ChatAnnotation.getClients();//客户端
		Map<String, ChatAnnotation> connections = ChatAnnotation.getConnectionMap();//客户端
		ChatAnnotation annotation = connections.get(current.getAccountId().trim());
		Map<String, Object> data = getData(message, current, mineAccountVo, toAccountVo, clients);
		annotation.incoming(JSONObject.fromObject(data), current);
	}
	private Map<String, Object> getData(String message, Current current, AccountVo mineAccountVo,
			AccountVo toAccountVo,Map<String, Session> clients) {
		UserVo mineUserVo = mineAccountVo.getUserVo();
		UserVo toUserVo = toAccountVo.getUserVo();
		Map<String,Object> data = new HashMap<>();
		data.put("type", "friend");
		Map<String,Object> friendData = new HashMap<>();
		Map<String,Object> mine = new HashMap<>();
		Map<String,Object> to = new HashMap<>();
		if(StrUtils.isNotBlankOrNull(mineUserVo.getAvatar())){
			mine.put("avatar","/static/upload/"+mineUserVo.getAvatar());//头像
		}else{
			mine.put("avatar","/static/upload/avatar/default.jpg");//头像
		}
		mine.put("username",current.getKey());
		mine.put("id",mineAccountVo.getId());
		mine.put("mine","true");
		mine.put("content",message);
		
		to.put("sign",toAccountVo.getSignText());
		to.put("id",toAccountVo.getId());
		to.put("username",toUserVo.getName()+"【"+toAccountVo.getLoginName()+"】");
		if(clients.containsKey(toAccountVo.getId())){
			to.put("status", "online");
		}else {
			to.put("status", "offline");
		}
		to.put("name",toUserVo.getName()+"【"+toAccountVo.getLoginName()+"】");
		if(StrUtils.isNotBlankOrNull(toUserVo.getAvatar())){
			to.put("avatar","/static/upload/"+toUserVo.getAvatar());//头像
		}else{
			to.put("avatar","/static/upload/avatar/default.jpg");//头像
		}
		to.put("type", "friend");
		friendData.put("mine",mine);
		friendData.put("to",to);
		data.put("data",friendData);
		return data;
	}
	
}
