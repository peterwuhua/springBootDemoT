package cn.demi.im.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.im.po.GroupMember;
import cn.demi.im.service.IGroupMemberService;
import cn.demi.im.vo.GroupMemberVo;
/**
 * Create on : 2016年12月15日 下午8:50:29  <br>
 * Description : 即时通讯群组列表serviceImpl<br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Service("im.group.memberService")
public class GroupMemberServiceImpl extends BaseServiceImpl<GroupMemberVo,GroupMember> implements
		IGroupMemberService {
 
	@Autowired private IAccountDao accountDao;
	@Autowired private IOrgDao orgDao;
	@Override
	public Map<String, Object> listMember(GroupMemberVo v) throws GlobalException {
		Map<String, Object> map =new   HashMap<String, Object>();
		map.put("code","0");
		map.put("msg","");
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> owner = new HashMap<String, Object>();
		Account own=accountDao.find("suadmin");
		owner.put("username", own.getUser().getName()+"【"+own.getLoginName()+"】");
		owner.put("id", own.getId());
		if(StrUtils.isNotBlankOrNull(own.getUser().getAvatar())){
			owner.put("avatar","/static/upload/"+own.getUser().getAvatar());//头像
		}else{
			owner.put("avatar","/static/upload/avatar/default.jpg");//头像
		}
		owner.put("sign",own.getSignText());//个性签名
		data.put("owner",owner);
		//查询组织下的账户
		List<Map<String, Object>> listFriend = new ArrayList<Map<String, Object>>();
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		List<String> orgIds=orgDao.listChild(v.getId());
		orgIds.add(v.getId());
		queryConditions.add(new QueryCondition("org.id in('"+String.join("','", orgIds)+"')"));
		List<Account> listAccount = accountDao.list(queryConditions);
		if(null != listAccount && listAccount.size()>0){
			for (Account account : listAccount) {
				Map<String, Object> mine = new HashMap<String, Object>();
				String username= "";
				if(null != account.getUser()){
					username = account.getUser().getName()+"【"+account.getLoginName()+"】";
					if(StrUtils.isNotBlankOrNull(account.getUser().getAvatar())){
						mine.put("avatar","/static/upload/"+account.getUser().getAvatar());//头像
					}else{
						mine.put("avatar","/static/upload/avatar/default.jpg");//头像
					}
				}else{
					mine.put("avatar","/static/upload/avatar/default.jpg");//头像
					username = account.getLoginName();
				}
				mine.put("username",username);//用户名
				mine.put("id",account.getId().trim());//id
				mine.put("status",StrUtils.isBlankOrNull(account.getStatus())?"offline":account.getStatus());//在线状态
				mine.put("sign",account.getSignText());//个性签名
				listFriend.add(mine);
			}
		}
		data.put("list",listFriend);
		map.put("data",data);
		return map;
	}
}
