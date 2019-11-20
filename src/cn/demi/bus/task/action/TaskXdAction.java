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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.service.IOrgService;
import cn.demi.base.system.vo.OrgVo;
import cn.demi.bus.task.service.ITaskService;
import cn.demi.bus.task.service.ITaskXdService;
import cn.demi.bus.task.vo.TaskVo;

/**
 * 任务下达
 * @author QuJunLong
 */
@Controller("bus.taskXdAction")
@RequestMapping("/bus/taskXd")
public class TaskXdAction extends BaseAction<TaskVo> {
	final String VIEW_PATH = "/bus/task/xd/task_xd";
	@Autowired 
	private ITaskXdService taskXdService;	
	@Autowired 
	private IOrgService orgService;
	@Autowired 
	private ITaskService taskService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TaskVo> baseService() {
		return taskXdService;
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskXdService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskXdService.gridDatad(gridVo, v);
	}
	@RequestMapping(value ="edit.do")
	public ModelAndView edit(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=taskXdService.find(v.getIds());
		v.setXdDate(DateUtils.getCurrDateTimeStr());
		v.setXdUser(getCurrent().getUserName());
		v.setXdUserId(getCurrent().getAccountId());
		List<OrgVo> orgList = orgService.listByType(Org.TYPE_JC);//检测室
		mav.addObject("orgList", orgList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	
	@RequestMapping(value ="show.do")
	public ModelAndView show(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=taskXdService.findById(v.getId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="任务分发")
	public Status updateData(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskXdService.update(v);
			taskService.update2St(v.getIds(),null);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
}