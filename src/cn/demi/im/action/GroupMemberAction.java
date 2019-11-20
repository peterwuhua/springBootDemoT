package cn.demi.im.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.im.service.IGroupMemberService;
import cn.demi.im.vo.GroupMemberVo;

@Controller("im.group.memberAction")
@RequestMapping("/im/group/member")
public class GroupMemberAction extends BaseAction<GroupMemberVo> {
	final String VIEW_PATH = "/im/group/member/groupMember";
	@Autowired private IGroupMemberService groupMemberService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<GroupMemberVo> baseService() {
		return groupMemberService;
	}
 
	@ResponseBody
	@RequestMapping(value ="listMember.do")
	public Map<String,Object> listMember(GroupMemberVo v) throws GlobalException {
		Map<String,Object> memberdMap = groupMemberService.listMember(v);
		return memberdMap;
	}
}