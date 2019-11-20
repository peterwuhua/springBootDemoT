package cn.demi.cus.bj.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pjt.po.Project;
import cn.demi.cus.bj.service.IBudgetService;
import cn.demi.cus.bj.vo.BudgetVo;

@Controller("cus.budgetAction")
@RequestMapping("/cus/budget")
public class BudgetAction extends BaseAction<BudgetVo> {
	final String VIEW_PATH = "/cus/budget/budget";
	@Autowired
	private IBudgetService budgetService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<BudgetVo> baseService() {
		return budgetService;
	}

	@Override
	public ModelAndView edit(BudgetVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = budgetService.findById(v.getId());
		} else {
			v.setBdate(DateUtils.getCurrDateStr());
			v.setYxq(DateUtils.getNextDate(DateUtils.getCurrDateStr(), 7));
			v.setBuserId(getCurrent().getAccountId());
			v.setBuserName(getCurrent().getUserName());
			v.setPc(1);
			v.setPcUnit(Project.PC_C);
			v.setDiscount(1);
		}

		// mav.addObject("taskTypeList", EnumBus.getBusList());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@Override
	public ModelAndView show(BudgetVo v) throws GlobalException {
		return super.show(v);
	}

	@Override
	@Log(operation = Log.Operation.ADD, content = "新增报价单", module = "报价申请")
	public Status addData(BudgetVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}

	@Override
	@Log(operation = Log.Operation.UPDATE, content = "更新报价信息", module = "报价申请")
	public Status updateData(BudgetVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}

	@Override
	@Log(operation = Log.Operation.SAVE, content = "保存报价信息", module = "报价申请")
	public ModelAndView save(BudgetVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}

	@ResponseBody
	@RequestMapping(value = "gridData4Lx.do")
	public GridVo gridData4Lx(GridVo gridVo, BudgetVo v) throws GlobalException {
		return budgetService.gridData4Lx(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "gridData4Query.do")
	public GridVo gridData4Query(GridVo gridVo, BudgetVo v) throws GlobalException {
		return budgetService.gridData4Query(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "ajaxGetTaskEnv.do")
	public Map<String, Object> ajaxGetTaskEnv(BudgetVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<>();
		map.put("taskEnvList", EnumBus.getAllEnvs());
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "ajaxGetTaskType.do")
	public Map<String, Object> ajaxGetTaskType(BudgetVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<>();
		map.put("taskTypeList", EnumBus.getBusNameList(v.getTaskEnv()));
		return map;
	}

}