package cn.demi.qlt.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pg.vo.ProgressVo;
import cn.demi.qlt.service.IProgressService;

@Controller("qlt.progressAction")
@RequestMapping("/qlt/progress")
public class ProgressAction extends BaseAction<ProgressVo> {
	final String VIEW_PATH = "/qlt/progress/progress";
	@Autowired 
	private IProgressService progressService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ProgressVo> baseService() {
		return progressService;
	}
	@Override
	public ModelAndView show(ProgressVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		ProgressVo vo = progressService.findById(v);
		mav.addObject(VO, vo);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
}