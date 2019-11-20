package cn.demi.bi.task.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bi.task.service.IProjectService;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;

/**
 * 任务统计 view层
 * @author QuJunLong
 *
 */
@Controller("bi.projectAction")
@RequestMapping("/bi/project")
public class ProjectAction extends BaseAction<ProjectVo> {
	final String VIEW_PATH = "/bi/project/project";
	@Autowired 
	private IProjectService projectService;
	@Autowired 
	private ISampTypeService sampTypeService;
	  @Autowired
	  private IFilesService filesService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ProjectVo> baseService() {
		return projectService;
	}
	@Override
	public ModelAndView gridPage(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		List<String> taskTypeList=EnumBus.getAllEnvs();
		mav.addObject("busList", taskTypeList);
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
	  @Override
	  public ModelAndView show(ProjectVo v) throws GlobalException {
	    ModelAndView mav = new ModelAndView();
	    if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
	      v = projectService.findById(v.getId());
	      List<FilesVo> fileList = filesService.listByBusId(v.getId());
	      v.setFileList(fileList);
	    }
	    mav.addObject(VO, v);
	    if (v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
	      mav.setViewName(getViewPath() + "_zw_show");
	    } else {
	      mav.setViewName(getViewPath() + "_show");
	    }
	    return mav;
	  } 
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		return projectService.gridData(gridVo, v);
	}
}