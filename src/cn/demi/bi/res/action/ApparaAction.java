package cn.demi.bi.res.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bi.res.service.IApparaUsedService;
import cn.demi.res.vo.ApparaUsedVo;

/**
 * 关于仪器相关信息统计
 * @author QuJunLong
 *
 */
@Controller("bi.apparaAction")
@RequestMapping("/bi/appara")
public class ApparaAction extends BaseAction<ApparaUsedVo> {
	
	final String VIEW_PATH = "/bi/appara/appara";
	
	@Autowired
	private IApparaUsedService apparaUsedService;
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<ApparaUsedVo> baseService() {
		return apparaUsedService;
	}
 
	@RequestMapping(value="gridPage4Used.do")
	public ModelAndView gridPage4Used(ApparaUsedVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_used_page");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value ="gridData4Used.do")
	public GridVo gridData4Used(GridVo gridVo, ApparaUsedVo v) throws GlobalException {
		return apparaUsedService.gridData(gridVo,v);
	}
	
}