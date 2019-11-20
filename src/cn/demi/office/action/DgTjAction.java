package cn.demi.office.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

import cn.demi.office.vo.DgTjVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.demi.office.service.IDgTjService;
import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;

@Controller("office.dgTjAction")
@RequestMapping("/office/dgTj")
public class DgTjAction extends BaseAction<DgTjVo> {
	final String VIEW_PATH = "/office/dg_tj/dg_tj";
	@Autowired private IDgTjService dgTjService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<DgTjVo> baseService() {
		return dgTjService;
	}
	@Override
	public ModelAndView gridPage(DgTjVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(v.getStartDate()==null) {
			v.setStartDate(DateUtils.getCurrDateStr());
			v.setEndDate(DateUtils.getCurrDateStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	public GridVo gridData(GridVo gridVo, DgTjVo v) throws GlobalException {
		return dgTjService.gridData(gridVo,v);
	}
}