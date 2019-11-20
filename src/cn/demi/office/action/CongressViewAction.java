package cn.demi.office.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.service.ICongressViewService;
import cn.demi.office.vo.CongressVo;

/**
 * 
 * @ClassName:  congressViewAction   
 * @Description:会务查阅Action  
 * @author: 吴华 
 * @date:   2019年3月5日 下午6:02:11       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Controller("office.congressViewAction")
@RequestMapping("/office/congressView")
public class CongressViewAction extends BaseAction<CongressVo> {
	final String VIEW_PATH = "/office/congress/congress_view";
	@Autowired private ICongressViewService congressViewService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<CongressVo> baseService() {
		return congressViewService;
	}
	/**
	 * 
	 * <p>Title: gridData</p>   
	 * <p>Description: 会务查阅列表</p>   
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.action.BaseAction#gridData(cn.core.framework.common.page.GridVo, cn.core.framework.common.vo.Vo)
	 */
	@ResponseBody
	@RequestMapping(value = {GRID_DATA})
	public GridVo gridData(GridVo gridVo,CongressVo v) throws GlobalException{
		return congressViewService.gridData(gridVo,v);
	}
	@Override
	public ModelAndView show(CongressVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
			congressViewService.updateStatus(v);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
}