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
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.sample.service.ISampContainerService;
import cn.demi.bus.sample.vo.SampContainerVo;
import cn.demi.bus.task.service.ITaskZbService;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.init.std.service.IItemMethodService;
/**
 * 采样准备
 * @author QuJunLong
 */
@Controller("bus.taskZbAction")
@RequestMapping("/bus/taskZb")
public class TaskZbAction extends BaseAction<TaskVo> {
	
	final String VIEW_PATH = "/bus/task/zb/task_zb";
	
	@Autowired 
	private ITaskZbService taskZbService;
	@Autowired 
	private ISampContainerService sampContainerService;
	@Autowired 
	private IItemMethodService itemMethodService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TaskVo> baseService() {
		return taskZbService;
	}
	@Override
	public ModelAndView edit(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=taskZbService.findById(v.getId());
			mav.addObject("sopList", taskZbService.list4Sop(v));
		}
		v.setZbDate(DateUtils.getCurrDateTimeStr());
		v.setZbId(getCurrent().getAccountId());
		v.setZbName(getCurrent().getUserName());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = taskZbService.findById(v.getId());
			mav.addObject("sopList", taskZbService.list4Sop(v));
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="采样准备")
	public Status updateData(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskZbService.update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value = "updateBack.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="采样准备")
	public Status updateBack(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskZbService.updateBack(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value = "printSop.do")
	public ModelAndView printSop(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = taskZbService.findById(v.getId());
			mav.addObject("sopList", taskZbService.list4Sop(v));
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_sop_print");
		return mav;
	}
	@RequestMapping(value = "printCt.do")
	public ModelAndView printCt(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			List<SampContainerVo> ctlist=sampContainerService.listByTaskId(v.getId());
			mav.addObject("ctlist", ctlist);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_ct_print");
		return mav;
	}
}