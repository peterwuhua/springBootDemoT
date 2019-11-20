package cn.demi.bus.task.action;

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
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IAccountRoleService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.task.service.ITaskService;
import cn.demi.bus.task.service.ITaskSumService;
import cn.demi.bus.task.vo.TaskVo;

/**
 * 数据审核（质量部）
 * @author QuJunLong
 */
@Controller("bus.taskSumAction")
@RequestMapping("/bus/taskSum")
public class TaskSumAction extends BaseAction<TaskVo> {
	final String VIEW_PATH = "/bus/task/sum/task_sum";
	@Autowired 
	private ITaskSumService taskSumService;	
	@Autowired 
	private IProgressLogService progressLogService;
	@Autowired 
	private ITaskService taskService;
	@Autowired
	private IAccountRoleService accountRoleService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TaskVo> baseService() {
		return taskSumService;
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskSumService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskSumService.gridDatad(gridVo, v);
	}
	public ModelAndView edit(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=taskSumService.findById(v.getId());
		v.setSumDate(DateUtils.getCurrDateTimeStr());
		v.setSumUser(getCurrent().getUserName());
		v.setSumUserId(getCurrent().getAccountId());
		if(StrUtils.isBlankOrNull(v.getZk())) {
			v.setZk(Constants.F);
		}
		List<AccountVo> accountList = accountRoleService.listAccountByOrg(Constants.ROLE_REPORT_MAKER, v.getSampType());
		//List<AccountVo> accountList = accountRoleService.listAccount(Constants.ROLE_REPORT_MAKER);  //.listAccount(getCurrent().getOrgCode(), Constants.ROLE_REPORT_MAKER, null);
		mav.addObject("userList", accountList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskSumService.findById(v.getId());
		mav.addObject(VO, v);
		ProgressLogVo logVo=progressLogService.find(v.getId(), EunmTask.TASK_HZ.getStatus());
		mav.addObject("logVo", logVo);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value ="updateBack.do")
	@Log(operation=Log.Operation.UPDATE,content="项目退回",module="数据审核")
	public Status updateBack(TaskVo v,RedirectAttributes attr) throws GlobalException {
		try {
			taskSumService.updateBack(v);
			taskService.update2St(null,v.getIds());
			status = new Status("退回成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"退回失败",e);
			status = new Status("退回失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新审核信息",module="数据审核")
	public Status updateData(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * 项目终止
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
//	@ResponseBody
//	@RequestMapping(value ="update2Stop.do")
//	@Log(operation=Log.Operation.UPDATE,content="终止项目",module="数据审核")
//	public Status update2Stop(TaskVo v, RedirectAttributes attr) throws GlobalException {
//		try {
//			taskSumService.update2Stop(v);
//			status = new Status("终止成功",Status.STATUS_SUCCESS);
//		} catch (GlobalException e) {
//			log.info("终止失败",e);
//			status = new Status("终止失败",Status.STATUS_ERROR);
//		}  
//		return status;
//	}
}