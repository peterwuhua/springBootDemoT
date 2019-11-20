package cn.demi.bus.test.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import cn.demi.bus.test.vo.StantSolutMxVo;
import cn.core.framework.common.service.IBaseService;
import cn.demi.bus.test.service.IStantSolutMxService;
import cn.core.framework.common.action.BaseAction;

@Controller("bus.stantSolutMxAction")
@RequestMapping("/bus/stantSolutMx")
public class StantSolutMxAction extends BaseAction<StantSolutMxVo> {
	final String VIEW_PATH = "/bus/stantSolutMx/stantSolutMx";
	@Autowired private IStantSolutMxService stantSolutMxService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<StantSolutMxVo> baseService() {
		return stantSolutMxService;
	}
}