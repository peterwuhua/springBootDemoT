package cn.demi.bus.test.action;

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
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.task.service.ITaskService;
import cn.demi.bus.test.service.IItemCheckService;
import cn.demi.bus.test.vo.TaskItemVo;

/**
 * 数据审核（实验中心）
 * @author QuJunLong
 *
 */
@Controller("bus.itemCheckAction")
@RequestMapping("/bus/itemCheck")
public class ItemCheckAction extends BaseAction<TaskItemVo> {
	final String VIEW_PATH = "/bus/item/check/item_check";
	@Autowired 
	private IItemCheckService itemCheckService;	
	@Autowired 
	private IProgressLogService progressLogService;
	@Autowired
	private IFilesService filesService;
	@Autowired 
	private ITaskService taskService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TaskItemVo> baseService() {
		return itemCheckService;
	}
	@Override
	public ModelAndView edit(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<TaskItemVo> itemList=null;
		if(null!=v && !StrUtils.isBlankOrNull(v.getIds())){
			itemList=itemCheckService.listByIds(v.getIds());
		}
		mav.addObject("itemList", itemList);
		
		v.setCheckMan(CurrentUtils.getCurrent().getUserName());
		v.setCheckManId(CurrentUtils.getCurrent().getAccountId());
		v.setCheckTime(DateUtils.getCurrDateStr());//复核时间改成复核日期
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, TaskItemVo v) throws GlobalException {
		return itemCheckService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, TaskItemVo v) throws GlobalException {
		return itemCheckService.gridDatad(gridVo, v);
	}
	@Override
	public ModelAndView show(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=itemCheckService.findById(v.getId());
			List<FilesVo> fileList=filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		}
		mav.addObject(VO, v);
		ProgressLogVo logVo=progressLogService.find(v.getId(), EunmTask.ITEM_JY.getStatus());
		mav.addObject("logVo", logVo);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新复核结果",module="数据复核")
	public Status updateData(TaskItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			taskService.update2St(null, v.getIds());
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
}