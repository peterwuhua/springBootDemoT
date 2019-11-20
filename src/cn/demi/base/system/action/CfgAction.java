package cn.demi.base.system.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.base.system.service.ICfgService;
import cn.demi.base.system.vo.CfgVo;

@Controller("sys.cfgAction")
@RequestMapping("/sys/cfg")
public class CfgAction extends BaseAction<CfgVo> {
	final String VIEW_PATH = "/sys/cfg/cfg";
	@Autowired
	private ICfgService cfgService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<CfgVo> baseService() {
		return cfgService;
	}
}