package cn.demi.bi.task.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.service.IOrgService;
import cn.demi.base.system.vo.OrgVo;
import cn.demi.bi.task.service.ITaskService;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;

/**
 * 任务统计 view层
 * @author QuJunLong
 *
 */
@Controller("bi.taskAction")
@RequestMapping("/bi/task")
public class TaskAction extends BaseAction<TaskVo> {
	final String VIEW_PATH = "/bi/task/task";
	@Autowired 
	private ITaskService taskService;
	@Autowired 
	private IOrgService orgService;
	@Autowired 
	private ISampTypeService sampTypeService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TaskVo> baseService() {
		return taskService;
	}
	@Override
	public ModelAndView gridPage(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		List<EnumBus> taskTypeList=EnumBus.getALLList();
		mav.addObject("busList", taskTypeList);
		List<OrgVo> orgList = orgService.listByPid(orgService.findRoot().getId());
		mav.addObject("orgList", orgList);
		List<SampTypeVo> sampTypeList = sampTypeService.listByPid(sampTypeService.findRoot().getId());
		if(null!=sampTypeList) {
			for (SampTypeVo sampTypeVo : sampTypeList) {
				List<SampTypeVo> subList=sampTypeService.listByPid(sampTypeVo.getId());
				sampTypeVo.setList(subList);
			}
		}
		mav.addObject("sampTypeList", sampTypeList);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@RequestMapping(value ="gridPage4Fy.do")
	public ModelAndView gridPage4Fy(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		List<EnumBus> taskTypeList=EnumBus.getALLList();
		mav.addObject("busList", taskTypeList);
		List<OrgVo> orgList = orgService.listByPid(orgService.findRoot().getId());
		mav.addObject("orgList", orgList);
		List<SampTypeVo> sampTypeList = sampTypeService.listByPid(sampTypeService.findRoot().getId());
		if(null!=sampTypeList) {
			for (SampTypeVo sampTypeVo : sampTypeList) {
				List<SampTypeVo> subList=sampTypeService.listByPid(sampTypeVo.getId());
				sampTypeVo.setList(subList);
			}
		}
		mav.addObject("sampTypeList", sampTypeList);
		mav.setViewName(getViewPath()+"_fy_page");
		return mav;
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskService.gridData(gridVo, v);
	}
 
	@ResponseBody
	@RequestMapping(value ="charts.do")
	public int[] chart(TaskVo v) throws GlobalException {
		return taskService.chart(v);
	}
	@RequestMapping(value ="chartFy.do")
	public ModelAndView chartFy(TaskVo v) throws GlobalException {
		Map<String, Object> map=taskService.chartFy(v);
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.addObject("map", JSON.toJSON(map));
		mav.setViewName(getViewPath()+"_chart_fy");
		return mav;
	}
	/**
	 * 首页 任务统计 图标
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value ="chartPie.do")
	public Map<String, Object> chartPie(TaskVo v) throws GlobalException {
		Map<String, Object> map = taskService.chartPie(v);
		return map;
	}
}