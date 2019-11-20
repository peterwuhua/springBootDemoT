package cn.demi.office.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.service.IDtViewService;
import cn.demi.office.vo.DtVo;
/**
 * 文档下发
 * @author QuJunLong
 *
 */
@Controller("office.dtViewAction")
@RequestMapping("/office/dtView")
public class DtViewAction extends BaseAction<DtVo> {
	final String VIEW_PATH = "/office/dt_view/dt_view";
	@Autowired 
	private IDtViewService dtViewService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<DtVo> baseService() {
		return dtViewService;
	}
	@Override
	public ModelAndView edit(DtVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=dtViewService.findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
}