package cn.demi.init.ti.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.demi.base.system.service.IOrgService;
import cn.demi.base.system.vo.OrgVo;
import cn.demi.init.ti.service.ITaskInitService;
import cn.demi.init.ti.vo.TaskInitVo;

/**
 * 检测配置
 * @author admin
 */
@Controller("init.task_initAction")
@RequestMapping("/init/taskInit")
public class TaskInitAction extends BaseAction<TaskInitVo> {
	final String VIEW_PATH = "/init/task_init/task_init";
	@Autowired 
	private ITaskInitService taskInitService;	
	@Autowired
	private IOrgService orgService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TaskInitVo> baseService() {
		return taskInitService;
	}
	@Override
	public ModelAndView edit(TaskInitVo vo)throws GlobalException {
		ModelAndView mav = super.edit(vo);
		List<EnumBus> busList=EnumBus.getALLList();
		mav.addObject("busList", busList);
		List<OrgVo> orgList = orgService.lists(orgService.findRoot().getId());
		mav.addObject("orgList", orgList);
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存初始化信息",module="任务初始化")
	public ModelAndView save(TaskInitVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	
	@Override
	@Log(operation=Log.Operation.ADD,content="新增任务初始化",module="任务初始化")
	public Status addData(TaskInitVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改任务初始化",module="任务初始化")
	public Status updateData(TaskInitVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	
	@Override
	@Log(operation=Log.Operation.DELETE,content="删除任务初始化",module="任务初始化")
	public ModelAndView delete(TaskInitVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}
	
	
}