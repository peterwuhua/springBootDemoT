package cn.demi.cus.bj.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.bj.service.IBudgetViewService;
import cn.demi.cus.bj.vo.BudgetVo;
/**
 * 
 * @ClassName:  BudgetViewAction   
 * @Description:报价查看  
 * @author: 吴华 
 * @date:   2019年3月13日 上午9:43:29       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Controller("cus.budgetViewAction")
@RequestMapping("/cus/budgetView")
public class BudgetViewAction extends BaseAction<BudgetVo> {
	final String VIEW_PATH = "/cus/budget_view/budget_view";
	@Autowired private IBudgetViewService budgetViewService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<BudgetVo> baseService() {
		return budgetViewService;
	}

	@RequestMapping(value=SHOW)
	public ModelAndView show(BudgetVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = budgetViewService.findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	
	
	
	
	

}