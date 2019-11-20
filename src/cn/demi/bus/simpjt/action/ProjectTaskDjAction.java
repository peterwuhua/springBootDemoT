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
import cn.demi.bus.simpjt.service.IProjectTaskDjService;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Controller("bus.simProjectTaskDjAction")
@RequestMapping("/bus/sim/project/taskdj")
public class ProjectTaskDjAction extends BaseAction<ProjectVo> {
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	final String VIEW_PATH = "/bus/sim/task/dj/task";
	@Autowired
	private IProjectTaskDjService projectTaskDjService;
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
		return projectTaskDjService;
	}

	@Override
	public ModelAndView edit(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = projectTaskDjService.findById(v.getId());
			// 若为退回项目，获取退回日志记录
			if (v.getIsBack().equals(Constants.Y)) {
				ProgressLogVo logVo = progressLogService.findBack(v.getId());
				mav.addObject("logVo", logVo);
			}
			List<FilesVo> fileList = filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		}
		// 任务类型
		mav.addObject("user", getCurrent().getUserName() + "【" + getCurrent().getLoginName() + "】");
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@Override
	public ModelAndView show(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = projectTaskDjService.findById(v.getId());
		List<FilesVo> fileList = filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_show");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "update4Data.do")
	@Log(operation = Log.Operation.UPDATE, content = "修改项目", module = "任务登记")
	public ModelAndView update4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file)
			throws GlobalException {
		try {
			projectTaskDjService.update(v);
			// 文件上传
			List<FilesVo> fileList = uploads(file, v.getId(), Constants.FILE_TYPE_PROJECT);
			filesService.saveFiles(fileList);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}

	@RequestMapping(value = "save4Data.do")
	@Log(operation = Log.Operation.SAVE, content = "保存项目", module = "任务登记")
	public ModelAndView save4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file)
			throws GlobalException {
		try {
			projectTaskDjService.save(v);
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
	@Log(operation = Log.Operation.DELETE, content = "删除项目", module = "任务登记")
	public ModelAndView delete(ProjectVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}

	@ResponseBody
	@RequestMapping(value = "update2Stop.do")
	@Log(operation = Log.Operation.UPDATE, content = "终止项目", module = "任务登记")
	public Status update2Stop(ProjectVo v, RedirectAttributes attr) throws GlobalException {
		try {
			projectTaskDjService.update2Stop(v);
			status = new Status("终止成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("终止失败", e);
			status = new Status("终止失败", Status.STATUS_ERROR);
		}
		return status;
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