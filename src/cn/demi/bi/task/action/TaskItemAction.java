package cn.demi.bi.task.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.demi.bi.task.service.ITaskItemService;
import cn.demi.bi.task.vo.ObjVo;
import cn.demi.bus.test.vo.TaskItemVo;

/**
 * 项目统计 view 层
 * @author QuJunLong
 *
 */
@Controller("bi.taskItemAction")
@RequestMapping("/bi/taskItem")
public class TaskItemAction extends BaseAction<TaskItemVo> {
	final String VIEW_PATH = "/bi/task_item/task_item";
	
	@Autowired 
	private ITaskItemService testItemService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TaskItemVo> baseService() {
		return testItemService;
	}
	@ResponseBody
	@RequestMapping(value ="chartValue.do")
	public Map<String, Object> chartValue(TaskItemVo v) throws GlobalException {
		Map<String, Object> map = testItemService.chartValue(v);
		return map;
	}
	//首页统计 项目趋势
	@ResponseBody
	@RequestMapping(value ="chartNum.do")
	public Map<String, Object> chartNum(TaskItemVo v) throws GlobalException {
		Map<String, Object> map = testItemService.chartNum(v);
		return map;
	}
	//人员工作量统计
	@RequestMapping(value ="list4Uw.do")
	public ModelAndView list4Uw(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<ObjVo> list= testItemService.list4Uw(v);
		mav.addObject("itlist", list);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_user_page");
		return mav;
	}
	@Override
	public ModelAndView gridPage(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		List<EnumBus> taskTypeList=EnumBus.getALLList();
		mav.addObject("busList", taskTypeList);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
}