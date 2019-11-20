package cn.demi.base.system.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import cn.demi.base.system.vo.SysMsgVo;
import cn.core.framework.common.service.IBaseService;
import cn.demi.base.system.service.ISysMsgService;
import cn.core.framework.common.action.BaseAction;

@Controller("sys.msgAction")
@RequestMapping("/sys/msg")
public class SysMsgAction extends BaseAction<SysMsgVo> {
	final String VIEW_PATH = "/sys/msg/sysMsg";
	@Autowired
	private ISysMsgService sysMsgService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<SysMsgVo> baseService() {
		return sysMsgService;
	}
}