package cn.demi.bus.pjt.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.pjt.service.IProjectService;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.cus.customer.service.ICustomerService;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;

/**
 * 项目立项  view层
 *
 * @author QuJunLong
 */
@Controller("bus.projectAction")
@RequestMapping("/bus/project")
public class ProjectAction extends BaseAction<ProjectVo> {
  final String VIEW_PATH = "/bus/project/lx/project";
  @Autowired
  private IProjectService projectService;
  @Autowired
  private IProgressLogService progressLogService;
  @Autowired
  private IFilesService filesService;
  @Autowired
  private ISampTypeService sampTypeService;
  @Autowired
  private ICodeService codeService;
  @Autowired
  private ICustomerService customerService;

  @Override
  public String getViewPath() {
    return VIEW_PATH;
  }

  @Override
  public IBaseService<ProjectVo> baseService() {
    return projectService;
  }

  @Override
  public ModelAndView edit(ProjectVo v) throws GlobalException {
    ModelAndView mav = new ModelAndView();
    if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
      v = projectService.findById(v.getId());
      List<FilesVo> fileList = filesService.listByBusId(v.getId());
      v.setFileList(fileList);
    } else {
      v = projectService.find4New(v);
    }
    //退回日志
    if (null != v.getIsBack() && v.getIsBack().equals(Constants.Y)) {
      ProgressLogVo logVo = progressLogService.findBack(v.getId());
      mav.addObject("logVo", logVo);
    }
    mav.addObject("taskTypeList", EnumBus.getBusList(v.getSampType()));
    mav.addObject(VO, v);
    if (v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
      mav.setViewName(getViewPath() + "_zw_edit");
    } else {
      mav.setViewName(getViewPath() + "_edit");
    }
    return mav;
  }

  @RequestMapping(value = "next.do")
  public ModelAndView next(ProjectVo v) throws GlobalException {
    ModelAndView mav = new ModelAndView();
    v = projectService.findNext(v.getId());
    if (null != v.getIsBack() && v.getIsBack().equals(Constants.Y)) {
      ProgressLogVo logVo = progressLogService.findBack(v.getId());
      mav.addObject("logVo", logVo);
    }
    List<SampTypeVo> sampTypeList = sampTypeService.listByIds(v.getSampTypeId());
    mav.addObject("sampTypeList", sampTypeList);
    //样品保存条件
    List<String> srList = codeService.listByCode("save-request");
    mav.addObject("srList", srList);
    mav.addObject(VO, v);
    mav.setViewName(getViewPath() + "_next");
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

  @ResponseBody
  @RequestMapping(value = "add4Data.do")
  @Log(operation = Log.Operation.ADD, content = "新增项目", module = "项目立项")
  public Status add4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file) throws GlobalException {
    try {
      projectService.add(v);
      //文件上传
      List<FilesVo> fileList = uploads(file, v.getId(), Constants.FILE_TYPE_PROJECT);
      filesService.saveFiles(fileList);
      status = new Status("新增成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
      status = new Status("新增失败", Status.STATUS_ERROR);
    }
    return status;
  }

  @ResponseBody
  @RequestMapping(value = "update4Data.do")
  @Log(operation = Log.Operation.UPDATE, content = "修改项目", module = "项目立项")
  public Status update4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file) throws GlobalException {
    try {
      projectService.update(v);
      //文件上传
      List<FilesVo> fileList = uploads(file, v.getId(), Constants.FILE_TYPE_PROJECT);
      filesService.saveFiles(fileList);
      status = new Status("修改成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
      status = new Status("修改失败", Status.STATUS_ERROR);
    }
    return status;
  }

  @RequestMapping(value = "save4Data.do")
  @Log(operation = Log.Operation.SAVE, content = "保存项目", module = "项目立项")
  public ModelAndView save4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file) throws GlobalException {
    try {
      projectService.save(v);
      //文件上传
      List<FilesVo> fileList = uploads(file, v.getId(), Constants.FILE_TYPE_PROJECT);
      filesService.saveFiles(fileList);
      status = new Status("保存成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info("保存失败", e);
      status = new Status("保存失败", Status.STATUS_ERROR);
    }
    attr.addFlashAttribute(STATUS, status);
    ModelAndView mav = new ModelAndView();
    mav.addObject("id", v.getId());
    mav.setViewName(REDIRECT_2_EDIT);
    return mav;
  }

  @RequestMapping(value = "save2Next.do")
  @Log(operation = Log.Operation.SAVE, content = "保存项目并跳转下一页", module = "项目立项")
  public ModelAndView save2Next(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file) throws GlobalException {
    try {
      projectService.save(v);
      //文件上传
      List<FilesVo> fileList = uploads(file, v.getId(), Constants.FILE_TYPE_PROJECT);
      filesService.saveFiles(fileList);
      status = new Status("保存成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info("保存失败", e);
      status = new Status("保存失败", Status.STATUS_ERROR);
    }
    attr.addFlashAttribute(STATUS, status);
    ModelAndView mav = new ModelAndView();
    mav.addObject("id", v.getId());
    mav.setViewName("redirect:next.do");
    return mav;
  }

  @RequestMapping(value = "saveNext.do")
  @Log(operation = Log.Operation.SAVE, content = "保存下一页信息", module = "项目立项")
  public ModelAndView saveNext(ProjectVo v, RedirectAttributes attr) throws GlobalException {
    try {
      projectService.updateNext(v);
      status = new Status("保存成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info("保存失败", e);
      status = new Status("保存失败", Status.STATUS_ERROR);
    }
    attr.addFlashAttribute(STATUS, status);
    ModelAndView mav = new ModelAndView();
    mav.addObject("id", v.getId());
    mav.setViewName("redirect:next.do");
    return mav;
  }

  @ResponseBody
  @RequestMapping(value = "update4NextData.do")
  @Log(operation = Log.Operation.UPDATE, content = "修改下一页项目信息", module = "项目立项")
  public Status update4NextData(ProjectVo v, RedirectAttributes attr) throws GlobalException {
    try {
      projectService.updateNext(v);
      status = new Status("修改成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
      status = new Status("修改失败", Status.STATUS_ERROR);
    }
    return status;
  }

  @RequestMapping(value = "delete.do")
  @Log(operation = Log.Operation.DELETE, content = "删除项目", module = "项目立项")
  public ModelAndView delete(ProjectVo v, RedirectAttributes attr) throws GlobalException {
    return super.delete(v, attr);
  }

  @ResponseBody
  @RequestMapping(value = "update2Stop.do")
  @Log(operation = Log.Operation.UPDATE, content = "终止项目", module = "项目立项")
  public Status update2Stop(ProjectVo v, RedirectAttributes attr) throws GlobalException {
    try {
      projectService.update2Stop(v);
      status = new Status("终止成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info("终止失败", e);
      status = new Status("终止失败", Status.STATUS_ERROR);
    }
    return status;
  }

  /**
   * 开票申请获取合同信息
   *
   * @param gridVo
   * @param v
   * @return
   * @throws GlobalException
   */
  @ResponseBody
  @RequestMapping(value = "gridData4Kp.do")
  public GridVo gridData4Kp(GridVo gridVo, ProjectVo v) throws GlobalException {
    return projectService.gridData4Kp(gridVo, v);
  }

  //更新已办 打开页面
  @RequestMapping(value = "edited.do")
  public ModelAndView edited(ProjectVo v) throws GlobalException {
    ModelAndView mav = new ModelAndView();
    v = projectService.findById(v.getId());
    List<FilesVo> fileList = filesService.listByBusId(v.getId());
    v.setFileList(fileList);
    mav.addObject("taskTypeList", EnumBus.getBusList(v.getSampType()));
    mav.addObject(VO, v);
    if (v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
      mav.setViewName(getViewPath() + "_zw_edit_ed");
    } else {
      List<SampTypeVo> sampTypeList = sampTypeService.listByIds(v.getSampTypeId());
      mav.addObject("sampTypeList", sampTypeList);
      //样品保存条件
      List<String> srList = codeService.listByCode("save-request");
      mav.addObject("srList", srList);
      mav.setViewName(getViewPath() + "_edit_ed");
    }
    return mav;
  }

  //更新已办 第一页
  @ResponseBody
  @RequestMapping(value = "updated.do")
  @Log(operation = Log.Operation.UPDATE, content = "修改项目", module = "项目立项")
  public Status updated(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file) throws GlobalException {
    try {
      projectService.updated(v);
      //文件上传
      List<FilesVo> fileList = uploads(file, v.getId(), Constants.FILE_TYPE_PROJECT);
      filesService.saveFiles(fileList);
      status = new Status("修改成功", Status.STATUS_SUCCESS);
    } catch (GlobalException e) {
      log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
      status = new Status("修改失败", Status.STATUS_ERROR);
    }
    return status;
  }


  @ResponseBody
  @RequestMapping(value = {GRID_DATAD})
  public GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException {
    return baseService().gridDatad(gridVo, v);
  }

	/**
	 * 历史记录 复制弹窗使用
	 * 
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Old.do")
	public GridVo gridData4Old(GridVo gridVo, ProjectVo v) throws GlobalException {
		return projectService.gridData4Old(gridVo, v);
	}
	
	@ResponseBody
	@RequestMapping(value = "copyProject.do")
	@Log(operation = Log.Operation.ADD, content = "复制信息", module = "业务受理")
	public Status copyProject(ProjectVo v, RedirectAttributes attr) throws GlobalException {
		try {
			String targetId = projectService.copy(v);
			status = new Status("copy成功", Status.STATUS_SUCCESS);
			status.setObject(targetId);
		} catch (GlobalException e) {
			log.info("copy失败", e);
			status = new Status("copy成功", Status.STATUS_ERROR);
		}
		return status;
	}
	

}