package cn.demi.bus.simpjt.action;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.simpjt.service.IProjectService;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Controller("bus.simProjectAction")
@RequestMapping("/bus/sim/project")
public class ProjectAction extends BaseAction<ProjectVo> {
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	final String VIEW_PATH = "/bus/sim/project/project";
	@Autowired
	private IProjectService projectService;
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
		// 退回日志
		if (null != v.getIsBack() && v.getIsBack().equals(Constants.Y)) {
			ProgressLogVo logVo = progressLogService.findBack(v.getId());
			mav.addObject("logVo", logVo);
		}
		mav.addObject("user", getCurrent().getUserName() + "【" + getCurrent().getLoginName() + "】");
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
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
		mav.setViewName(getViewPath() + "_show");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "add4Data.do")
	@Log(operation = Log.Operation.ADD, content = "新增项目", module = "项目立项")
	public Status add4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file) throws GlobalException {
		try {
			projectService.add(v);
			// 文件上传
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
	public Status update4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file)
			throws GlobalException {
		try {
			projectService.update(v);
			// 文件上传
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
	public ModelAndView save4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file)
			throws GlobalException {
		try {
			projectService.save(v);
			// 文件上传
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


	// 更新已办 第一页
	@ResponseBody
	@RequestMapping(value = "updated.do")
	@Log(operation = Log.Operation.UPDATE, content = "修改项目", module = "项目立项")
	public Status updated(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file) throws GlobalException {
		try {
			projectService.updated(v);
			// 文件上传
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
	@RequestMapping(value = { GRID_DATAD })
	public GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException {
		return baseService().gridDatad(gridVo, v);
	}

	/**
	 * 
	 * <p>
	 * Title: download
	 * </p>
	 * <p>
	 * Description: 文件下载
	 * </p>
	 * 
	 * @param filePath
	 * @param trueName
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#download(java.lang.String,
	 *      java.lang.String)
	 */
	@RequestMapping(value = DOWNLOAD)
	public ResponseEntity<byte[]> download(@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		String ctxPath = DOWNLOAD_DIR + filePath;
		String downLoadPath = ctxPath.replace("/", File.separator);
		return down(trueName, downLoadPath);
	}

}