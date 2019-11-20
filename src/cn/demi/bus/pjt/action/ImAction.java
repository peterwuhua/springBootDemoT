package cn.demi.bus.pjt.action;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.service.IImService;
import cn.demi.bus.pjt.vo.ImVo;

/**
 * 项目方法关系
 * @author QuJunLong
 */
@Controller("bus.imAction")
@RequestMapping("/bus/im")
public class ImAction extends BaseAction<ImVo> {
	final String VIEW_PATH = "/bus/item_method/im";
	@Autowired 
	private IImService imService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ImVo> baseService() {
		return imService;
	}
	
	 
	@Override
	public ModelAndView show(ImVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=imService.findById(v.getId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="list4Im.do")
	public ModelAndView list4Im(ImVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<ImVo> imList=imService.list4Im(v.getBusId());
		mav.addObject("list", imList);
		mav.setViewName(getViewPath()+"_list");
		return mav;
	}
}