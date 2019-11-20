package cn.demi.office.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.service.IWorkPlanService;
import cn.demi.office.vo.WorkPlanVo;

@Controller("office.workPlanAction")
@RequestMapping("/office/workPlan")
public class WorkPlanAction extends BaseAction<WorkPlanVo> {
	final String VIEW_PATH = "/office/work_plan/work_plan";
	@Autowired
	private IWorkPlanService workPlanService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<WorkPlanVo> baseService() {
		return workPlanService;
	}

	@ResponseBody
	@RequestMapping(value = "addPlan")
	@Log(operation = Log.Operation.ADD, content = "新增工作计划", module = "工作计划")
	public Status addPlan(WorkPlanVo v, RedirectAttributes attr) throws GlobalException {
		try {
			if (v.getId() != null && !("").equals(v.getId())) {
				workPlanService.update(v);
			} else {
				workPlanService.add(v);
			}
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "updatePlan")
	@Log(operation = Log.Operation.UPDATE, content = "修改工作计划", module = "工作计划")
	public Status updatePlan(WorkPlanVo v, RedirectAttributes attr) throws GlobalException {
		try {
			workPlanService.update(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@Override
	@Log(operation = Log.Operation.DELETE, content = "删除工作计划", module = "工作计划")
	public ModelAndView delete(WorkPlanVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}

	@Override
	@Log(operation = Log.Operation.UPDATE, content = "删除工作计划", module = "工作计划")
	public ModelAndView update2del(WorkPlanVo v, RedirectAttributes attr) throws GlobalException {
		return super.update2del(v, attr);
	}

	@Override
	public ModelAndView edit(WorkPlanVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = workPlanService.findById(v.getId());
		} else {
			if (!StrUtils.isBlankOrNull(v.getSelectdate())) {
				v.setStartDate(v.getSelectdate());
				v.setEndDate(v.getSelectdate());
			} else {
				v.setStartDate(DateUtils.getCurrDateStr());
				v.setEndDate(DateUtils.getCurrDateStr());
			}
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
			v.setStartTime(DateUtils.getCurrTimeStr().substring(0, 5));
			v.setEndTime("23:59");
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	/**
	 * 获取用户集合
	 * 
	 * @param
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "listPlan.do")
	public List<WorkPlanVo> listPlan(WorkPlanVo v) throws GlobalException {
		v.setUserId(getCurrent().getUserId());
		List<WorkPlanVo> planList = workPlanService.list(v);
		return planList;
	}

	/**
	 * 获取用户集合
	 * 
	 * @param
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "listDate.do")
	public List<String> listDate(WorkPlanVo v) throws GlobalException {
		v.setUserId(getCurrent().getUserId());
		List<String> planList = workPlanService.listDate(v);
		return planList;
	}
}