package cn.demi.bus.pg.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;

@Controller("bus.progressLogAction")
@RequestMapping("/bus/progressLog")
public class ProgressLogAction extends BaseAction<ProgressLogVo> {
	final String VIEW_PATH = "/bus/progress_log/progress_log";
	@Autowired private IProgressLogService progressLogService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ProgressLogVo> baseService() {
		return progressLogService;
	}
}