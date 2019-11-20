package cn.core.framework.task;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import cn.core.framework.exception.GlobalException;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.current.CurrentUtils;
import cn.core.framework.utils.openIM.OpenIM;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.im.service.IConversationService;
import cn.demi.im.service.IMessageContentService;
import cn.demi.im.vo.ConversationVo;
import cn.demi.im.vo.MessageContentVo;
import cn.demi.im.webscoket.ChatAnnotation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 获取openIM消息 定时器
 * 每隔5秒
 * @author QuJunLong
 *
 */
@Component("ScheduleIM")
public class ScheduleIM {
	
	public static Long time=null;
	public Logger log = Logger.getLogger(this.getClass());
	
	private IConversationService  conversationService = (IConversationService) ContextLoader .getCurrentWebApplicationContext().getBean("im.conversationService");
	private IMessageContentService messageContentService = (IMessageContentService) ContextLoader .getCurrentWebApplicationContext().getBean("im.messageContentService");
	private IAccountService accountService = (IAccountService) ContextLoader .getCurrentWebApplicationContext().getBean("sys.accountService");
	
	@Scheduled(fixedDelay=5000)//每隔5秒执行一次
    public void job() throws GlobalException {
		Map<String, HttpSession> httpSessions=ChatAnnotation.httpSession;
		Map<String,Current> loginUser=new HashMap<String,Current>();
		for (HttpSession session : httpSessions.values()) {
			Current current = (Current) session.getAttribute(CurrentUtils.TOCKET_COOKIE_KEY);
			loginUser.put(current.getLoginName(),current);
		}
		if(time==null) {
			time=DateUtils.getCurrDate().getTime()/1000;
		}
		Long endTime=DateUtils.getCurrDate().getTime()/1000;
		JSONArray msgAyy=OpenIM.getMsg(time, endTime);
		if(null!=msgAyy) {
			for (Object object : msgAyy) {
				try {
					JSONObject obj=JSONObject.fromObject(object);
					String to_id=obj.getJSONObject("to_id").getString("uid");//接收人
					String from_id=obj.getJSONObject("from_id").getString("uid");//发送人
					String content="未识别消息，请手机端查看！";//发送内容
					JSONObject ct=obj.getJSONObject("content");
					if(ct.containsKey("roaming_message_item")) {
						JSONArray cts=ct.getJSONArray("roaming_message_item");
						for (Object msgObj : cts) {
							JSONObject msg=JSONObject.fromObject(msgObj);
							String type=msg.getString("type");
							if(null!=type&&type.equals("T")) {
								content=msg.getString("value");
								break;
							}
						}
					}
					String sendTime=obj.getString("time");//发送时间
					boolean online=false;
					if(loginUser.containsKey(to_id)) {
						online=true;
					}
					AccountVo to=accountService.find(to_id);
					if(null!=loginUser.get(from_id)&&to!=null) {
						Current current=loginUser.get(from_id);
						incoming(current, current.getAccountId(), to.getId(),sendTime,content, String.valueOf(online));
					}else if(to!=null){
						AccountVo from=accountService.find(from_id);
						if(null!=from) {
							incoming(null, from.getId(), to.getId(), sendTime,content, String.valueOf(online));
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
		}
		time=endTime;
    }
	
	public void incoming(Current current,String fromId,String toId,String time,String content,String status) throws GlobalException {
		ConversationVo conversationVo=new ConversationVo();
		conversationVo.setCurrent(current);
		conversationVo=conversationService.addConversation(fromId, toId, conversationVo);
		boolean isNew=addMessage(current,conversationVo,fromId, toId,time,content,status);
		if(isNew) {
			if("true".equals(status)){//好友是否在线
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id",fromId);
				map.put("content",content);
				map.put("mine",true);
				map.put("type","friend");
				AccountVo from=accountService.findById(fromId);
				String username= "";
				if(null != from&&null!=from.getUserVo()){
					username = from.getUserVo().getName()+"【"+from.getLoginName()+"】";
					if(StrUtils.isNotBlankOrNull(from.getUserVo().getAvatar())){
						map.put("avatar","/static/upload/"+from.getUserVo().getAvatar());//头像
					}else{
						map.put("avatar","/static/upload/avatar/default.jpg");//头像
					}
				}else{
					map.put("avatar","/static/upload/avatar/default.jpg");//头像
					username = from.getLoginName();
				}
				map.put("username",username);
				ChatAnnotation.broadcast(toId,JSONObject.fromObject(map).toString());//发送消息
			}else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("system", true);
				map.put("id",toId);
				map.put("type", "friend");
				map.put("username","系统消息");
				map.put("avatar","/static/upload/avatar/default.jpg");//头像
				ChatAnnotation.broadcast(fromId,JSONObject.fromObject(map).toString());
			}
		}
	}
	public boolean addMessage(Current current,ConversationVo conversationVo,String mine, String to,String time,String content,String status) throws GlobalException {
		MessageContentVo messageContentVo = new MessageContentVo();
		messageContentVo.setContent(content);
		messageContentVo.setConversationVo(conversationVo);
		messageContentVo.setType("friend");
		messageContentVo.setSenderTime(time);
		messageContentVo.setCurrent(current);
		messageContentVo.setStatus(status);
		messageContentVo.setMineId(mine);
		messageContentVo.setToId(to);
		return messageContentService.addData4IM(messageContentVo);
	}
}
