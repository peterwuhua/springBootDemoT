package cn.demi.im.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.im.service.IGroupService;
import cn.demi.im.vo.GroupVo;

@Controller("im.groupAction")
@RequestMapping("/im/group")
public class GroupAction extends BaseAction<GroupVo> {
	final String VIEW_PATH = "/im/group/group";
	@Autowired private IGroupService groupService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<GroupVo> baseService() {
		return groupService;
	}
}