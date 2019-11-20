package cn.demi.bus.simpjt.action;

import java.util.List;

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
import cn.demi.bus.simpjt.service.IProjectReptGdService;
import cn.demi.bus.simpjt.vo.ProjectVo;
/**
 * 
 * @ClassName:  ProjectReptGdAction   
 * @Description:报告归档   
 * @author: 吴华 
 * @date:   2019年6月15日 上午11:05:26       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Controller("bus.simProjectReptGdAction")
@RequestMapping("/bus/sim/reportReptGd")
public class ProjectReptGdAction extends BaseAction<ProjectVo> {
	final String VIEW_PATH = "/bus/sim/report/gd/report_file";
	@Autowired private IProjectReptGdService projectReptGdService;
	@Autowired 
	private IProgressLogService progressLogService;
	@Autowired
	private IFilesService filesService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ProjectVo> baseService() {
		return projectReptGdService;
	}
	@Override
	public ModelAndView edit(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			List<FilesVo> fileList = filesService.listByBusId(v.getId());
			v=projectReptGdService.findById(v.getId(),fileList);
		}
		v.setFileDate(DateUtils.getCurrDateTimeStr());
		v.setFileUser(CurrentUtils.getCurrent().getUserName());
		v.setFileUserId(CurrentUtils.getCurrent().getAccountId());
		
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新报告信息",module="报告归档")
	public Status updateData(ProjectVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		return projectReptGdService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException {
		return projectReptGdService.gridDatad(gridVo, v);
	}
	
	@Override
	public ModelAndView show(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			List<FilesVo> fileList = filesService.listByBusId(v.getId());
			v = projectReptGdService.findById(v.getId(),fileList);
		}
		mav.addObject(VO, v);
		ProgressLogVo logVo=progressLogService.find(v.getId(), EunmTask.TASK_GD.getStatus());
		mav.addObject("logVo", logVo);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
}