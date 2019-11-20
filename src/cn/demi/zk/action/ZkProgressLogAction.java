package cn.demi.zk.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.zk.service.IZkProgressLogService;
import cn.demi.zk.vo.ZkProgressLogVo;

@Controller("zk.progressLogAction")
@RequestMapping("/zk/progress")
public class ZkProgressLogAction extends BaseAction<ZkProgressLogVo> {
	final String VIEW_PATH = "/zk/progress/progress";
	@Autowired private IZkProgressLogService zkProgressLogService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ZkProgressLogVo> baseService() {
		return zkProgressLogService;
	}
	@Override
	public ModelAndView show(ZkProgressLogVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		ZkProgressLogVo vo = zkProgressLogService.findById(v);
		mav.addObject(VO, vo);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
}