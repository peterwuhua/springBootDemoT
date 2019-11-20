package cn.core.framework.utils.openIM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.OpenImUser;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimAppChatlogsGetRequest;
import com.taobao.api.request.OpenimImmsgPushRequest;
import com.taobao.api.request.OpenimImmsgPushRequest.ImMsg;
import com.taobao.api.request.OpenimTribeCreateRequest;
import com.taobao.api.request.OpenimTribeDismissRequest;
import com.taobao.api.request.OpenimTribeGetalltribesRequest;
import com.taobao.api.request.OpenimTribeGetmembersRequest;
import com.taobao.api.request.OpenimTribeGettribeinfoRequest;
import com.taobao.api.request.OpenimTribeJoinRequest;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersDeleteRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.request.OpenimUsersUpdateRequest;
import com.taobao.api.response.OpenimAppChatlogsGetResponse;
import com.taobao.api.response.OpenimImmsgPushResponse;
import com.taobao.api.response.OpenimTribeCreateResponse;
import com.taobao.api.response.OpenimTribeDismissResponse;
import com.taobao.api.response.OpenimTribeGetalltribesResponse;
import com.taobao.api.response.OpenimTribeGetmembersResponse;
import com.taobao.api.response.OpenimTribeGettribeinfoResponse;
import com.taobao.api.response.OpenimTribeJoinResponse;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersDeleteResponse;
import com.taobao.api.response.OpenimUsersGetResponse;
import com.taobao.api.response.OpenimUsersUpdateResponse;

import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.demi.base.system.po.Account;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OpenIM {
	
	public static Logger log = Logger.getLogger(OpenIM.class);
	public static final String appkey="24838392";
	public static final String secret="0dddc7222fa70bcba0805f4624dd1c93";
	public static final String url="http://gw.api.taobao.com/router/rest";
	static String SERVER_BASE = (String)ApplicationUtils.getValue("config.server.host");
	
	/**
	 * im 新增用户
	 * @param atVo
	 */
	public static void addUser(Account at) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();
		List<Userinfos> list2 = new ArrayList<Userinfos>();
		Userinfos obj3 = new Userinfos();
		list2.add(obj3);
		obj3.setNick(at.getUser().getName());
		obj3.setIconUrl(SERVER_BASE+"static/upload/"+at.getUser().getAvatar());
		obj3.setEmail(at.getUser().getEmail());
		obj3.setMobile(at.getUser().getMobile());
		obj3.setTaobaoid(at.getLoginName());
		obj3.setUserid(at.getLoginName());
		obj3.setPassword(at.getPassword());
		obj3.setRemark(at.getUser().getRemark());
		obj3.setExtra("{}");
		obj3.setCareer(at.getUser().getDuty());
		obj3.setVip("{}");
		obj3.setAddress(at.getUser().getAddress());
		obj3.setName(at.getUser().getName());
		obj3.setAge(0l);
		if(at.getUser().getSex()!=null&&at.getUser().getSex().equals("女")) {
			obj3.setGender("F");
		}else {
			obj3.setGender("M");
		}
		obj3.setWechat(null);
		obj3.setQq(null);
		obj3.setWeibo(null);
		req.setUserinfos(list2);
		try {
			OpenimUsersAddResponse rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("error_response")) {
				log.error("同步账户 "+at.getLoginName()+" 异常");
			}else if(null!=obj.get("openim_users_add_response")) {
				JSONObject msg = JSONObject.fromObject(obj.get("openim_users_add_response"));
				if(msg.get("uid_succ")!=null) {
					JSONObject mg = JSONObject.fromObject(msg.get("uid_succ"));
					if(mg.get("string")!=null) {
						log.info("同步用户成功");
					}
				}else if(msg.get("fail_msg")!=null) {
					JSONObject mg = JSONObject.fromObject(msg.get("fail_msg"));
					if(mg.get("string")!=null) {
						log.error(msg.get("同步账户异常"+"fail_msg").toString());
					}
				}
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * im 更新用户
	 * @param atVo
	 */
	public static void updateUser(Account at) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimUsersUpdateRequest req = new OpenimUsersUpdateRequest();
		List<Userinfos> list2 = new ArrayList<Userinfos>();
		Userinfos obj3 = new Userinfos();
		list2.add(obj3);
		obj3.setNick(at.getUser().getName());
		obj3.setIconUrl(SERVER_BASE+"static/upload/"+at.getUser().getAvatar());
		obj3.setEmail(at.getUser().getEmail());
		obj3.setMobile(at.getUser().getMobile());
		obj3.setTaobaoid(at.getLoginName());
		obj3.setUserid(at.getLoginName());
		obj3.setPassword(at.getPassword());
		obj3.setRemark(at.getUser().getRemark());
		obj3.setExtra("{}");
		obj3.setCareer(at.getUser().getDuty());
		obj3.setVip("{}");
		obj3.setAddress(at.getUser().getAddress());
		obj3.setName(at.getUser().getName());
		obj3.setAge(0l);
		if(at.getUser().getSex()!=null&&at.getUser().getSex().equals("女")) {
			obj3.setGender("F");
		}else {
			obj3.setGender("M");
		}
		obj3.setWechat(null);
		obj3.setQq(null);
		obj3.setWeibo(null);
		req.setUserinfos(list2);
		try {
			OpenimUsersUpdateResponse  rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("error_response")) {
				log.error("更新账户 "+at.getLoginName()+" 异常");
			}else if(null!=obj.get("openim_users_update_response")) {
				log.error("更新账户 "+at.getLoginName()+" 成功");
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * im 删除用户
	 * @param loginName
	 */
	public static void deleteUser(String loginName) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimUsersDeleteRequest  req = new OpenimUsersDeleteRequest ();
		req.setUserids(loginName);
		try {
			OpenimUsersDeleteResponse   rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("error_response")) {
				log.error("删除账户 "+loginName+" 异常");
			}else if(null!=obj.get("openim_users_delete_response")) {
				log.error("删除账户 "+loginName+" 成功");
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * im 查询用户
	 * @param loginName
	 * @return
	 */
	public static boolean getUser(String loginName) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimUsersGetRequest req = new OpenimUsersGetRequest();
		req.setUserids(loginName);
		OpenimUsersGetResponse rsp;
		try {
			rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("openim_users_get_response")) {
				JSONObject msg = JSONObject.fromObject(obj.get("openim_users_get_response"));
				JSONObject mg=JSONObject.fromObject(msg.get("userinfos"));
				if(null!=mg&&mg.get("userinfos")!=null) {
					JSONArray objx= (JSONArray) mg.get("userinfos");
					if(objx.size()>0) {
						return true;
					}else {
						return false;
					}
				}else {
					return false;
				}
			}else if(null!=obj.get("error_response")) {
				JSONObject msg = JSONObject.fromObject(obj.get("error_response"));
				Object mg=msg.get("sub_code");
				if(mg!=null&&mg.toString().equals("isp.service-error")) {
					return getUser(loginName);
				}else {
					return false;
				}
			}else {
				return getUser(loginName);
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	/**
	 * im 创建群
	 * @param loginName
	 */
	public static Long addGroup(String loginName,String groupName) {
		Long groupId=null;
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimTribeCreateRequest req = new OpenimTribeCreateRequest();
		OpenImUser obj1 = new OpenImUser();
		obj1.setUid(loginName);
		obj1.setTaobaoAccount(false);
		obj1.setAppKey(appkey);
		req.setUser(obj1);
		req.setTribeName(groupName);
		req.setNotice(groupName);
		req.setTribeType(0L);
		try {
			OpenimTribeCreateResponse rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("error_response")) {
				log.error("创建群 "+groupName+" 异常");
			}else if(null!=obj.get("openim_tribe_create_response")) {
				log.error("创建群 "+groupName+" 成功");
				JSONObject msg = JSONObject.fromObject(obj.get("openim_tribe_create_response"));
				groupId=(long) JSONObject.fromObject(msg.get("tribe_info")).get("tribe_id");
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
		}
		return groupId;
	}
	/**
	 * im 群增加成员
	 * @param loginName
	 */
	public static void addUser2Group(String loginName,long groupId) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimTribeJoinRequest   req = new OpenimTribeJoinRequest();
		OpenImUser obj1 = new OpenImUser();
		obj1.setUid(loginName);
		obj1.setTaobaoAccount(false);
		obj1.setAppKey(appkey);
		req.setUser(obj1);
		req.setTribeId(groupId);
		try {
			OpenimTribeJoinResponse  rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("error_response")) {
				log.error("群成员 "+loginName+" 加入异常");
			}else if(null!=obj.get("openim_tribe_join_response")) {
				log.error("群成员 "+loginName+" 加入成功");
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * im 查询群
	 * @param loginName
	 * @return
	 */
	public static boolean getGroup(String loginName,long groupId) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimTribeGettribeinfoRequest  req = new OpenimTribeGettribeinfoRequest();
		OpenImUser obj1 = new OpenImUser();
		obj1.setUid(loginName);
		obj1.setTaobaoAccount(false);
		obj1.setAppKey(appkey);
		req.setUser(obj1);
		req.setTribeId(groupId);
		OpenimTribeGettribeinfoResponse rsp;
		try {
			rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("openim_tribe_gettribeinfo_response")) {
				JSONObject msg = JSONObject.fromObject(obj.get("openim_tribe_gettribeinfo_response"));
				JSONObject mg=JSONObject.fromObject(msg.get("tribe_info"));
				if(null!=mg) {
					if(mg.get("tribe_id")!=null) {
						return true;
					}else {
						return false;
					}
				}else {
					return false;
				}
			}else if(null!=obj.get("error_response")) {
				JSONObject msg = JSONObject.fromObject(obj.get("error_response"));
				Object mg=msg.get("sub_code");
				if(mg!=null&&mg.toString().equals("isp.service-error")) {
					return getGroup(loginName,groupId);
				}else {
					return false;
				}
			}else {
				return getGroup(loginName,groupId);
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	/**
	 * im 查询群集合
	 * @param loginName
	 * @return
	 */
	public static JSONArray getGroupList(String loginName) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimTribeGetalltribesRequest  req = new OpenimTribeGetalltribesRequest();
		OpenImUser obj1 = new OpenImUser();
		obj1.setUid(loginName);
		obj1.setTaobaoAccount(false);
		obj1.setAppKey(appkey);
		req.setUser(obj1);
		req.setTribeTypes("0,1");
		OpenimTribeGetalltribesResponse  rsp;
		try {
			rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("openim_tribe_getalltribes_response")) {
				JSONObject msg = JSONObject.fromObject(obj.get("openim_tribe_getalltribes_response"));
				JSONObject mg=JSONObject.fromObject(msg.get("tribe_info_list"));
				if(null!=mg) {
					JSONArray objx= (JSONArray) mg.get("tribe_info");
					return objx;
				}else {
					return null;
				}
			}else {
				return null;
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * im 查询群成员集合
	 * @param loginName
	 * @return
	 */
	public static JSONArray getGroupUsers(String loginName,long groupId) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimTribeGetmembersRequest   req = new OpenimTribeGetmembersRequest ();
		OpenImUser obj1 = new OpenImUser();
		obj1.setUid(loginName);
		obj1.setTaobaoAccount(false);
		obj1.setAppKey(appkey);
		req.setUser(obj1);
		req.setTribeId(groupId);
		OpenimTribeGetmembersResponse rsp;
		try {
			rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("openim_tribe_getmembers_response")) {
				JSONObject msg = JSONObject.fromObject(obj.get("openim_tribe_getmembers_response"));
				JSONObject mg=JSONObject.fromObject(msg.get("tribe_user_list"));
				if(null!=mg) {
					JSONArray objx= (JSONArray) mg.get("tribe_user");
					return objx;
				}else {
					return null;
				}
			}else {
				return null;
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * im 删除群
	 * @param loginName
	 * @return
	 */
	public static boolean delGroup(String loginName,long groupId) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimTribeDismissRequest req = new OpenimTribeDismissRequest ();
		OpenImUser obj1 = new OpenImUser();
		obj1.setUid(loginName);
		obj1.setTaobaoAccount(false);
		obj1.setAppKey(appkey);
		req.setUser(obj1);
		req.setTribeId(groupId);
		OpenimTribeDismissResponse  rsp;
		try {
			rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("openim_tribe_dismiss_response")) {
				JSONObject msg = JSONObject.fromObject(obj.get("openim_tribe_dismiss_response"));
				Object mg=msg.get("tribe_code");
				if(null!=mg&&Integer.valueOf(mg.toString())==0) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	public static void pushMsg(String fromUser,String toUser,String msg) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimImmsgPushRequest req = new OpenimImmsgPushRequest();
		ImMsg obj1 = new ImMsg();
		obj1.setFromUser(fromUser);
		obj1.setToUsers(Arrays.asList(toUser));
		obj1.setMsgType(0L);
		obj1.setContext(msg);
		obj1.setToAppkey(appkey);
		obj1.setFromTaobao(0L);
		req.setImmsg(obj1);
		try {
			OpenimImmsgPushResponse rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("error_response")) {
				String mg = obj.get("sub_msg").toString();
				log.error(""+fromUser+"向"+toUser+"推送消息异常；异常原因："+mg);
			}else {
				log.info(""+fromUser+"向"+toUser+"推送消息成功");
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
		}
	}
	public static JSONArray getMsg(Long startTime,Long endTime) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenimAppChatlogsGetRequest req = new OpenimAppChatlogsGetRequest();
		req.setBeg(startTime);
		req.setEnd(endTime);
		req.setCount(100L);
		OpenimAppChatlogsGetResponse rsp =null;
		try {
			rsp = client.execute(req);
			JSONObject obj = JSONObject.fromObject(rsp.getBody());
			if(null!=obj.get("openim_app_chatlogs_get_response")) {
				try {
					JSONObject msg=obj.getJSONObject("openim_app_chatlogs_get_response").getJSONObject("result").getJSONObject("messages");
					if(msg.containsKey("es_message")) {
						JSONArray msgAyy =msg.getJSONArray("es_message");
						return msgAyy; 
					}else {
						return null; 
					}
				} catch (Exception e) {
					log.error("解析消息异常:"+obj.toString());
				}
			}else {
				JSONObject errMsg = JSONObject.fromObject(obj.get("error_response"));
				if(null!=errMsg) {
					log.error("获取消息异常:"+errMsg.toString());
				}else {
					log.error("获取消息异常");
				}
			}
		} catch (ApiException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(getUser("test"));
		//initUser();
	}
}
