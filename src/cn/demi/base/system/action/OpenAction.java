package cn.demi.base.system.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.SmsVo;
/**
 * <strong>Create on : Dave Yu 2016年11月2日 下午2:27:27 </strong> <br>
 * <strong>Description : 岗位action </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.openAction")
@RequestMapping("sys/open")
public class OpenAction extends BaseAction<SmsVo>{
	final String VIEW_PATH = "/sys/open/open";
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<SmsVo> baseService() {
		return null;
	}
	
	/**
	 * <strong>Create on :Carson Liu 2016年11月2日 上午10:19:34 </strong> <br>
	 * <strong>Description : 全局通用返回，根据页面请求直接返回对应JSP</strong> <br>
	 * @param v 通用action返回
	 * @param act 请求action 例如 edit.do 则返回 edit.jsp  
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value="open.do")
	public ModelAndView act(@RequestParam("tempPath") String tempPath,@RequestParam("type") String type) throws GlobalException {
		ModelAndView mav = new ModelAndView(getViewPath()+"_"+type);
		mav.addObject("tempPath", tempPath);
		mav.addObject("user",getCurrent().getKey());
		return mav;
	}
	
}
