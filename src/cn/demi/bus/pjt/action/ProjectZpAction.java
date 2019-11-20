package cn.demi.bus.pjt.action;

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
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.pjt.service.IProjectZpService;
import cn.demi.bus.pjt.vo.AuVo;
import cn.demi.bus.pjt.vo.ProjectVo;
 
/**
 * 任务指派
 * @author QuJunLong
 *
 */
@Controller("bus.projectZpAction")
@RequestMapping("/bus/projectZp")
public class ProjectZpAction extends BaseAction<ProjectVo> {
	final String VIEW_PATH = "/bus/project/zp/project_zp";
	@Autowired 
	private IProjectZpService projectZpService;
	@Autowired
	private IFilesService filesService;
	@Autowired 
	private IProgressLogService progressLogService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<ProjectVo> baseService() {
		return projectZpService;
	}
	@Override
	public ModelAndView edit(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=projectZpService.findById(v.getId());
			List<FilesVo> fileList=filesService.listByBusId(v.getId());
			v.setFileList(fileList);
//			if(StrUtils.isBlankOrNull(v.getTkUserId())) {
				//默认踏勘人员:取当前工作量最少的两个人
//				List<AuVo> auList=projectZpService.listAu();
//				if(auList.size()>=2) {
//					v.setTkUserId(auList.get(0).getId()+","+auList.get(1).getId());
//					v.setTkUserName(auList.get(0).getName()+","+auList.get(1).getName());
//				}else if(auList.size()>=1) {
//					v.setTkUserId(auList.get(0).getId());
//					v.setTkUserName(auList.get(0).getName());
//				}
//			}
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	
	@Override
	public ModelAndView show(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=projectZpService.findById(v.getId());
			List<FilesVo> fileList=filesService.listByBusId(v.getId());
			v.setFileList(fileList);
			ProgressLogVo logVo=progressLogService.find(v.getId(), EunmTask.PROJECT_ZP.getStatus());
			mav.addObject("logVo", logVo);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="updateData.do")
	@Log(operation=Log.Operation.UPDATE,content="更新指派信息",module="任务指派")
	public Status updateData(ProjectVo v, RedirectAttributes attr) throws GlobalException {
		try {
			projectZpService.update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	@ResponseBody
	@RequestMapping(value = "listAu.do")
	public List<AuVo> listAu(ProjectVo v) throws GlobalException {
		ProjectVo projectVo=projectZpService.findById(v.getId());
		return projectZpService.listAu(projectVo.getSampType());
	}

}