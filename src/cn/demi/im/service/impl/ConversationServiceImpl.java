package cn.demi.im.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.OrgVo;
import cn.demi.base.system.vo.UserVo;
import cn.demi.im.dao.IConversationDao;
import cn.demi.im.po.Conversation;
import cn.demi.im.service.IConversationService;
import cn.demi.im.vo.ConversationVo;
import cn.demi.im.webscoket.ChatAnnotation;
import net.sf.json.JSONObject;
/**
 * Create on : 2016年12月13日 下午1:04:09  <br>
 * Description :  即时通讯会话serviceImpl<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Service("im.conversationService")
public class ConversationServiceImpl extends BaseServiceImpl<ConversationVo,Conversation> implements
		IConversationService {
	@Autowired private IConversationDao conversationDao;
	@Autowired private IAccountDao accountDao;
	@Autowired private IOrgDao orgDao;
	
	/***
	 * Create on : Paddy Zhang 2016年12月13日 下午1:02:14 <br>
	 * Description :  获取好友列表集合<br>
	 * @return
	 */
	@Override
	public Map<String, Object> listFriend() {
		Map<String, Object> map =new   HashMap<String, Object>();
		map.put("code",0);
		map.put("msg","");
		Map<String, Object> data = new HashMap<String, Object>();
		
		Current current = getCurrent();
		Account account = accountDao.findById(current.getAccountId());
		AccountVo accountVo = new AccountVo();
		accountVo.toVo(account, accountVo);
		accountVo.setStatus(StrUtils.isBlankOrNull(accountVo.getStatus())?"online":accountVo.getStatus());
		Map<String, Object> mine = getMember(accountVo);
		data.put("mine",mine);
		
		List<Map<String, Object>> friends = listGroup();
		List<Map<String, Object>> groups = listGp();
		data.put("friend",friends);//好友列表
		data.put("group",groups);//群组列表
		map.put("data",data);
		return map;
	}
	/**
	 * Description : 获取群集合 <br>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> listGp() {
		List<Map<String, Object>> listGroup = new ArrayList<Map<String, Object>>();//分组集合
		List<QueryCondition> queryConditionsList = new ArrayList<QueryCondition>();
		queryConditionsList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		queryConditionsList.add(new QueryCondition("parent.id ='"+ getCurrent().getOrgId()+"' or id='"+ getCurrent().getOrgId()+"'"));
		List<OrderCondition> orderConditions = new ArrayList<OrderCondition>();
		orderConditions.add(new OrderCondition("sort", OrderCondition.ORDER_ASC));
		
		Org org=orgDao.findById(getCurrent().getDepId());
		
		Map<String, Object> group = new HashedMap();
		group.put("groupname",org.getName());//分组名称
		group.put("id",org.getId());//分组名称
		group.put("avatar","/static/upload/avatar/logo_mini.png");//分组顺序
		listGroup.add(group);
		if(org.getLevel()==2) {
			Map<String, Object> pgroup = new HashedMap();
			pgroup.put("groupname",org.getParent().getName());//分组名称
			pgroup.put("id",org.getParent().getId());//分组名称
			pgroup.put("avatar","/static/upload/avatar/logo_mini.png");//分组顺序
			listGroup.add(pgroup);
		}
		
		return listGroup;
	}
	
	/**
	 * Create on : Paddy Zhang 2016年12月24日 下午9:38:12 <br>
	 * Description : 获取好友分组集合 <br>
	 * @return
	 */
	private List<Map<String, Object>> listGroup() {
		List<Map<String, Object>> listGroup = new ArrayList<Map<String, Object>>();//分组集合
		Map<String, String> linestatus = ChatAnnotation.getLinestatus();//好友在线状态
		
		List<QueryCondition> queryConditionsList = new ArrayList<QueryCondition>();
		queryConditionsList.add(new QueryCondition("org.isDel", QueryCondition.EQ, Po.N));
		List<OrderCondition> orderConditions = new ArrayList<OrderCondition>();
		orderConditions.add(new OrderCondition("org.sort", OrderCondition.ORDER_ASC));
		orderConditions.add(new OrderCondition("sort", OrderCondition.ORDER_ASC));
		List<Account> listAccount = accountDao.list(queryConditionsList,orderConditions);
		
		Map<String, Map<String, Object>> groups = new LinkedHashMap<>();//保存所有分组
		Map<String, List<Map<String, Object>>> listFriends = new LinkedHashMap<>();//保存每个分组下的好友集合
		
		List<Map<String, Object>> listFriend = null;//单个分组下的好友集合
		for(int i=0; i<listAccount.size();i++){
			Account account = listAccount.get(i);
			AccountVo accountVo = new AccountVo();
			accountVo.toVo(account, accountVo);
			OrgVo orgVo = accountVo.getOrgVo();
			if(orgVo.getLevel()>1) {
				orgVo=orgVo.getParentVo();
			}
			Map<String, Object> group = null;
			Integer onlineCount = 0;//每个分组下在线人数
			if (!groups.containsKey(orgVo.getId())) {//检测分组是否存在
				group = new LinkedHashMap<>();//分组存在创建分组
				String groupName=orgVo.getName();
				group.put("groupname",groupName);//分组名称
				group.put("id",orgVo.getId().trim());//分组名称
				group.put("online",groups.size()+1);//分组顺序
				listFriend = new ArrayList<Map<String, Object>>();//分组下好友集合
				groups.put(orgVo.getId(),group);//保存分组
				listFriends.put(orgVo.getId(),listFriend);//保存好友集合
			}else {
				group = groups.get(orgVo.getId());//分组存在时取出分组
				listFriend = listFriends.get(orgVo.getId());//取出分组下的好友的集合
				onlineCount =Integer.valueOf((String) group.get("onlineCount"));
			}
			if(linestatus.containsKey(accountVo.getId().trim())){//检测好友是否在线，设置好友在线状态
				//accountVo.setStatus("hide".equals(linestatus.get(accountVo.getId()))?"offline":"online");
				if("hide".equals(linestatus.get(accountVo.getId()))){
					accountVo.setStatus("offline");
				}else {
					accountVo.setStatus("online");
					//在线人数加一
					onlineCount++;
					//System.out.println(onlineCount);
				}
			}else {
				if(getCurrent().getAccountId().equals(accountVo.getId())){
					//accountVo.setStatus("hide".equals(linestatus.get(accountVo.getId()))?"offline":"online");
					if("hide".equals(linestatus.get(accountVo.getId()))){
						accountVo.setStatus("offline");
					}else {
						accountVo.setStatus("online");
						onlineCount++;
					}
				}else {
					accountVo.setStatus("offline");
				}
			}
			group.put("onlineCount",Integer.toString(onlineCount));
			listFriend.add(getMember(accountVo));
			group.put("list", listFriend);
		}
		listGroup.addAll(groups.values());
		return listGroup;
	}
	/***
	 * Create on : Paddy Zhang 2016年12月9日 下午6:21:33 <br>
	 * Description : 获取即时通讯好友分组及分组下的所有好友，已改为用优化后的方法listGroup（）<br>
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<Map<String, Object>> ListFriends() {
		//好友分组集合
		List<Map<String, Object>> listGroupFriend = new ArrayList<Map<String, Object>>(); 
		Map<String, String> linestatus = ChatAnnotation.getLinestatus();//好友在线状态
		List<Org> listOrg = orgDao.list();
		for (int i=0;i<listOrg.size();i++) {
			Org org = listOrg.get(i);
			//每一个分组
			Map<String, Object> gruopFriend = new HashMap<String, Object>();
			
			//查询组织下的账户
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			String orgId = org.getId();
			queryConditions.add(new QueryCondition("org.id", QueryCondition.EQ,orgId));
			List<Account> listAccount = accountDao.list(queryConditions);
			//分组下的所有人员
			List<Map<String, Object>> listFriend = new ArrayList<Map<String, Object>>();
			if(null != listAccount && listAccount.size()>0){
				OrgVo orgVo = new OrgVo();
				orgVo.toVo(org, orgVo);
				gruopFriend.put("groupname",orgVo.getName());//分组名称
				gruopFriend.put("id",orgVo.getId().trim());//分组名称
				gruopFriend.put("online",i);//分组顺序
				Map<String, Object> member = new HashMap<String, Object>();
				for (Account account : listAccount) {
					AccountVo accountVo = new  AccountVo();
					accountVo.toVo(account,accountVo);
					if(linestatus.containsKey(accountVo.getId().trim())){
						accountVo.setStatus("hide".equals(linestatus.get(accountVo.getId()))?"offline":"online");
					}else {
						if(getCurrent().getAccountId().equals(accountVo.getId())){
							accountVo.setStatus("hide".equals(linestatus.get(accountVo.getId()))?"offline":"online");
						}else {
							accountVo.setStatus("offline");
						}
					}
					member= getMember(accountVo);
					listFriend.add(member);
				}
				gruopFriend.put("list",listFriend);//分组下好友集合
				listGroupFriend.add(gruopFriend);
			}
		}
		return listGroupFriend;
	}
	/**
	 * Create on : Paddy Zhang 2016年12月13日 下午1:01:29 <br>
	 * Description :  获取单个好友的信息<br>
	 * @param accountVo
	 * @return
	 */
	private Map<String, Object> getMember(AccountVo accountVo) {
		Map<String, Object> mine = new HashMap<String, Object>();
		UserVo userVo = accountVo.getUserVo();
		String username= "";
		if(null != userVo){
			username = userVo.getName()+"【"+accountVo.getUserVo().getDuty()+"】";
			if(StrUtils.isNotBlankOrNull(userVo.getAvatar())){
				mine.put("avatar","/static/upload/"+userVo.getAvatar());//头像
			}else{
				mine.put("avatar","/static/upload/avatar/default.jpg");//头像
			}
		}else{
			mine.put("avatar","/static/upload/avatar/default.jpg");//头像
			username = accountVo.getLoginName();
		}
		mine.put("username",username);//用户名
		mine.put("id",accountVo.getId().trim());//id
		mine.put("status",StrUtils.isBlankOrNull(accountVo.getStatus())?"offline":accountVo.getStatus());//在线状态
		mine.put("sign",accountVo.getSignText());//个性签名
		return mine;
	}
	/**
	 * Create on : Paddy Zhang 2016年12月13日 下午1:02:59 <br>
	 * Description :  保存即时通讯单个回话对象<br>
	 * @param mineId 发信账户id
	 * @param toId 收信人账户id
	 * @return
	 * @throws GlobalException
	 */
	@Override
	public ConversationVo addConversation(String mineId, String toId,ConversationVo vo) throws GlobalException {
		Conversation conversation = null;
		ConversationVo conversationVo = null;
		Current current = (null==vo?null:null==vo.getCurrent()?null:vo.getCurrent());
		try {
			if(StrUtils.isNotBlankOrNull(mineId) || StrUtils.isNotBlankOrNull(toId)){
				conversation = conversationDao.findByMineAndToId(mineId,toId);
				if(null == conversation){
					Account sender = accountDao.findById(mineId);
					Account receive = accountDao.findById(toId);
					conversation = new Conversation();
					conversation.setSender(sender);
					conversation.setReceive(receive);
					if(null != current){
						conversation.setLastUpdUser(current.getKey());
						conversation.setCreateUser(current.getKey());
					}
					conversationDao.addData(conversation);
				}
				conversationVo = po2Vo(conversation);
			}else {
				throw new GlobalException("参数异常");
			}
		} catch (Exception e) {
			throw new GlobalException("保存即时通信会话出错", e);
		}
		return conversationVo;
	}
	@Override
	public void imSendMsg(String accountId,String message) throws GlobalException {
		if(StrUtils.isBlankOrNull(accountId))
			throw new GlobalException("无效参数");
		//mine
		Current current = getCurrent();
		Account mineAccount =  accountDao.findById(current.getAccountId());
		AccountVo mineAccountVo = new AccountVo();
		mineAccountVo = mineAccountVo.toVo(mineAccount,mineAccountVo);
		//to
		Account toAccount = accountDao.findById(accountId);
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
