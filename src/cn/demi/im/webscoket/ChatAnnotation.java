package cn.demi.im.webscoket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.web.context.ContextLoader;

import cn.core.framework.exception.GlobalException;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.CollectionUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.UserVo;
import cn.demi.im.po.MessageContent;
import cn.demi.im.service.IConversationService;
import cn.demi.im.service.IMessageContentService;
import cn.demi.im.vo.ConversationVo;
import cn.demi.im.vo.MessageContentVo;
import net.sf.json.JSONObject;

/**
 * Create on : 2016年12月15日 下午8:54:43  <br>
 * Description : 即时通讯webscoket 服务端 <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
// 定义一个WebSocket服务端 
@ServerEndpoint(value="/websocket/{accountId}/{url}",configurator=GetHttpSessionConfigurator.class)
public class ChatAnnotation{
	private IConversationService 
	conversationService = (IConversationService) ContextLoader .getCurrentWebApplicationContext().getBean("im.conversationService");
	private IMessageContentService 
	messageContentService = (IMessageContentService) ContextLoader .getCurrentWebApplicationContext().getBean("im.messageContentService");
	private IAccountService
	accountService = (IAccountService) ContextLoader .getCurrentWebApplicationContext().getBean("sys.accountService");
	
	public Logger log = Logger.getLogger(this.getClass());
	
	//private static final String GUEST_PREFIX = "Guest";
	private static final AtomicInteger connectionIds = new AtomicInteger(0);
	private static final Set<ChatAnnotation> connections = new CopyOnWriteArraySet<ChatAnnotation>();//通讯服务端在线用户实例  Set集合
	private static final Map<String,ChatAnnotation> connectionMap = new ConcurrentHashMap<String,ChatAnnotation>();//通讯服务端在线用户实例 Map集合
	private static final Map<String, Object> conversation = new ConcurrentHashMap<String, Object>();//当前登陆用户 会话 Map
	public static final Map<String, Session> clients = new ConcurrentHashMap<String, Session>();//在线用户 socket session Map集合
	public static final Map<String, String> lineStatus = new ConcurrentHashMap<String, String>();//登陆后显示状态：在线/隐身
	public static final Map<String, HttpSession> httpSession = new ConcurrentHashMap<String, HttpSession>();//在线用户 http session Map集合
	//private final String nickname;
	private Session session;

	public ChatAnnotation() {
		//nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
		//System.out.println(nickname);
	}
	public static Map<String, Session> getClients() {
		return clients;
	}
	public static Map<String, String> getLinestatus() {
		return lineStatus;
	}
	public static Map<String, ChatAnnotation> getConnectionMap() {
		return connectionMap;
	}
	
	/**
	 * <strong>Create on : 2016年11月2日 下午4:12:56 </strong> <br>
	 * <strong>Description : 连接创建时调用的方法</strong> <br>
	 * @param session session
	 * @throws GlobalException 
	 */
	@OnOpen
	public void onOpen(@PathParam("accountId") String accountId,Session session,EndpointConfig config) throws GlobalException {
		//获取httpssion
		HttpSession htSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		httpSession.put(accountId.trim(),htSession);
		this.session = session;
		clients.put(accountId.trim(), session);//用户唯一标识
		Map<String, Object> online = new HashMap<String, Object>();//在线状态
		online.put("id",accountId.trim());
		try {
			//登录时查看登录人保存的在线状态  hide隐身   online 在线    offline 头像置灰
			AccountVo accountVo = accountService.findById(accountId.trim());
			String status = accountVo.getStatus();
			lineStatus.put(accountId.trim(),StrUtils.isBlankOrNull(status)?"online":status);
			if(Account.hide.equals(status)){
				online.put("status", "offline");
			}else {
				online.put("status", "online");
			}
		}catch (Exception e) {
			log.info("查询好友失败");
		}
		online.put("type","status");
		String msg = JSONObject.fromObject(online).toString();
		broadcasts(msg);//给所有登录的好友群发在线状态
		connections.add(this);
		connectionMap.put(accountId.trim(),this);
		sendOffLineMsg(accountId);
		//String message = String.format("* %s %s", nickname, "has joined.");
		//System.out.println("信息：" + message);
		//System.out.println(SecurityUtils.getSubject()+"~~~~~~~~~~~~~");
		//broadcast(message);
	}
	
	//发送离线消息
	private void sendOffLineMsg(String accountId) throws GlobalException {
		HttpSession  hSession = httpSession.get(accountId.trim());
		Current current = (Current) hSession.getAttribute(CurrentUtils.TOCKET_COOKIE_KEY);
		List<MessageContentVo> contentVos =  messageContentService.listOffLine(accountId.trim());
		if (CollectionUtils.isEmpty(contentVos)) return ;
		for (MessageContentVo messageVo : contentVos) {
			ConversationVo converVo = messageVo.getConversationVo();
			if(null == converVo) continue;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id",converVo.getSenderVo().getId());
			map.put("content",messageVo.getContent());
			map.put("mine",true);
			map.put("type","friend");
			UserVo userVo = converVo.getSenderVo().getUserVo();
			String username= "";
			if(null != userVo){
				username = userVo.getName()+"【"+converVo.getSenderVo().getLoginName()+"】";
				if(StrUtils.isNotBlankOrNull(userVo.getAvatar())){
					map.put("avatar","/static/upload/"+userVo.getAvatar());//头像
				}else{
					map.put("avatar","/static/upload/avatar/default.jpg");//头像
				}
			}else{
				map.put("avatar","/static/upload/avatar/default.jpg");//头像
				username = converVo.getSenderVo().getLoginName();
			}
			map.put("username",username);
			messageVo.setStatus(MessageContent.TRUE);
			messageVo.setCurrent(current);
			messageContentService.update4Statuas(messageVo);
			broadcast(accountId.trim(),JSONObject.fromObject(map).toString());
		}
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午4:13:23 </strong> <br>
	 * <strong>Description : 连接关闭时调用的方法 </strong> <br>
	 */
	@OnClose
	public void onClose(@PathParam("accountId") String accountId) {
		connections.remove(this);
		clients.remove(accountId.trim());
		lineStatus.remove(accountId.trim());
		for (Entry<String, Object> entry : conversation.entrySet()){
			if(entry.getKey().contains(accountId))
				conversation.remove(entry.getKey());
		}
		conversation.remove(accountId.trim());
		httpSession.remove(accountId.trim());
		connectionIds.getAndDecrement();//连接数减一
		//更新好友列表好友在线状态
		Map<String, Object> online = new HashMap<String, Object>();
		online.put("id",accountId.trim());
		online.put("status", "offline");
		online.put("type","status");
		String msg = JSONObject.fromObject(online).toString();
		broadcasts(msg);
		//String message = String.format("* %s %s", nickname, "has disconnected.");
		//broadcast(message);
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午4:13:35 </strong> <br>
	 * <strong>Description : 传输信息过程中调用的方法</strong> <br>
	 * @param message message
	 */
	@OnMessage
	public void OnMessage(String  data,Session session,@PathParam("accountId") String accountId) {
		HttpSession  hSession = httpSession.get(accountId.trim());
		Current current = (Current) hSession.getAttribute(CurrentUtils.TOCKET_COOKIE_KEY);
		try{
			JSONObject  jsonObject=JSONObject .fromObject(data);
			String type = jsonObject.getString("type");
			if("friend".equals(type)){
				incoming(jsonObject,current);//好友会发发送消息
			}else if ("status".equals(type)) {// 好友在线状态切换   切换后给所有在线用户发送在线状
				changeStatus(accountId, current, jsonObject);				
			}else if ("sign".equals(type)) {//签名的修改
				changeSign(accountId, current, jsonObject);
			}else if ("group".equals(type)) {
				// TODO 群组会话
			}
		}catch(Exception e){
			log.info("onmessage error");
			e.printStackTrace();
		}
	}
	public void changeSign(String accountId, Current current, JSONObject jsonObject) throws GlobalException {
		AccountVo accountVo = accountService.findById(accountId.trim());
		accountVo.setSignText(jsonObject.getString("data"));
		accountVo.setCurrent(current);
		accountService.save(accountVo);
	}
	private void changeStatus(String accountId, Current current, JSONObject jsonObject) throws GlobalException {
		//数据库保存用户在线状态以便下次登录时使用
		AccountVo accountVo = accountService.findById(accountId.trim());
		accountVo.setCurrent(current);
		accountVo.setStatus(jsonObject.getString("data"));
		accountService.save(accountVo);
		//给在线的其他用户发送在线状态
		Map<String, Object> online = new HashMap<String, Object>();
		online.put("id", accountId.trim());
		online.put("status",jsonObject.getString("data").equals("hide")?"offline":"online");
		online.put("type","status");
		String msg = JSONObject.fromObject(online).toString();
		broadcasts(msg);
	}

	public void incoming(JSONObject jsonObject,Current current) throws GlobalException {
		JSONObject jsondata = jsonObject.getJSONObject("data");
		JSONObject mine = jsondata.getJSONObject("mine");
		JSONObject to = jsondata.getJSONObject("to");
		mine.put("type", to.getString("type"));
		if(!conversation.containsKey(mine.getString("id").trim()+"_"+to.getString("id"))){//保存会话记录
			addConver(current, mine, to);
		}
		if(conversation.containsKey(mine.getString("id").trim()+"_"+to.getString("id"))){//保存消息记录
			addMessage(current, mine, to);
		}
		if(clients.containsKey(to.getString("id").trim())&& "online".equals(lineStatus.get(mine.get("id")).trim())){//好友是否在线
			broadcast(to.getString("id"),mine.toString());//发送消息
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("system", true);
			map.put("id",to.getString("id").trim());
			map.put("type", to.getString("type"));
			map.put("username","系统消息");
			map.put("avatar","/static/upload/avatar/default.jpg");//头像
			broadcast(mine.getString("id"),JSONObject.fromObject(map).toString());
		}
	}
	public void addMessage(Current current, JSONObject mine, JSONObject to) throws GlobalException {
		MessageContentVo messageContentVo = new MessageContentVo();
		messageContentVo.setContent(mine.getString("content"));
		messageContentVo.setConversationVo((ConversationVo)conversation.get(mine.getString("id").trim()+"_"+to.getString("id")));
		messageContentVo.setType(to.getString("type"));
		messageContentVo.setSenderTime(String.valueOf(System.currentTimeMillis()));
		messageContentVo.setCurrent(current);
		if(clients.containsKey(to.getString("id").trim()) && "online".equals(lineStatus.get(mine.get("id")).trim())){//好友是否在线
			messageContentVo.setStatus(MessageContent.TRUE);
		}else {
			messageContentVo.setStatus(MessageContent. FALSE);
		}
		messageContentService.add(messageContentVo);
	}
	public void addConver(Current current, JSONObject mine, JSONObject to) throws GlobalException {
		ConversationVo conversationVo=new ConversationVo();
		conversationVo.setCurrent(current);
		ConversationVo  conVo = conversationService.addConversation(mine.getString("id"),to.getString("id"),conversationVo);
		conversation.put(mine.getString("id").trim()+"_"+to.getString("id").trim(),conVo);
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午4:13:51 </strong> <br>
	 * <strong>Description : 发生错误时调用的方法 </strong> <br>
	 * @param t t
	 * @throws Throwable
	 */
	@OnError
	public void onError(Throwable t) throws Throwable {
		//System.out.println("Chat Error: " + t.toString());
	}

	/**
	 * <strong>Create on : 2016年11月2日 下午4:14:07 </strong> <br>
	 * <strong>Description : 通过connections，对所有其他用户推送信息的方法 </strong> <br>
	 * @param msg msg
	 */
	public static void broadcasts(String msg) {
		//broadcast("msg:" + msg);
		for (ChatAnnotation client : connections) {
			try {
				synchronized (client) {
					client.session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				//System.out.println("Chat Error: Failed to send message to client");
				connections.remove(client);
				try {
					client.session.close();
				} catch (IOException e1) {
					// Ignore
				}
//				String message = String.format("* %s %s", client.nickname,
//						"has been disconnected.");
				//System.out.println("xiaoxi:" + message);

			}
		}
	}
	
	/** 
     * 将数据传回客户端 
     * 异步的方式 
     * @param myWebsocket 
     * @param string 
     */  
    public static void broadcast(String myWebsocket, String message) {  
        if (clients.containsKey(myWebsocket)) {  
            clients.get(myWebsocket).getAsyncRemote().sendText(message);
        } else {  
            throw new NullPointerException("[" + myWebsocket +"]Connection does not exist");  
        }  
    }  
}
