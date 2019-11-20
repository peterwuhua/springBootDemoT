package cn.demi.cus.bj.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.bj.service.IBudgetAuditService;
import cn.demi.cus.bj.vo.BudgetVo;

@Controller("cus.budgetAuditAction")
@RequestMapping("/cus/budgetAudit")
public class BudgetAuditAction extends BaseAction<BudgetVo> {
	final String VIEW_PATH = "/cus/budget_audit/budget_audit";
	@Autowired private IBudgetAuditService budgetAuditService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<BudgetVo> baseService() {
		return budgetAuditService;
	}
	@Override
	public ModelAndView edit(BudgetVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=budgetAuditService.findById(v.getId());
			v.setAuditDate(DateUtils.getCurrDateTimeStr());
			v.setAuditId(getCurrent().getAccountId());
			v.setAuditName(getCurrent().getUserName());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(BudgetVo v) throws GlobalException {
		return super.show(v);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新报价信息",module="报价确认")
	public Status updateData(BudgetVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
}