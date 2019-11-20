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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.simpjt.service.IProjectService;
import cn.demi.bus.simpjt.service.IProjectTkService;
import cn.demi.bus.simpjt.vo.ProjectVo;

/**
 * 现场踏勘
 * 
 * 
 *
 */
@Controller("bus.simProjectTkAction")
@RequestMapping("/bus/sim/projectTk")
public class ProjectTkAction extends BaseAction<ProjectVo> {
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	public static final String SERVER_EXPORT_DIR = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");
	final String VIEW_PATH = "/bus/sim/project/tk/project_tk";
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IProjectTkService projectTkService;
	@Autowired
	private IFilesService filesService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<ProjectVo> baseService() {
		return projectTkService;
	}

	@RequestMapping(value = "edit4Survey.do")
	public ModelAndView edit4Survey(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = projectService.findById(v.getId());
			List<FilesVo> fileList = filesService.listByBusId(v.getId(), Constants.FILE_TYPE_PROJECT);
			v.setFileList(fileList);
			if (StrUtils.isBlankOrNull(v.getTkDate())) {
				v.setTkDate(DateUtils.getCurrDateTimeStr());
			}
			mav.addObject("user", getCurrent().getUserName() + "【" + getCurrent().getLoginName() + "】");
			mav.addObject(VO, v);
			mav.setViewName(getViewPath() + "_edit");
		}
		return mav;
	}

	@RequestMapping(value = "show4Surve.do")
	public ModelAndView show4Surve(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (!StrUtils.isBlankOrNull(v.getId())) {
			v = projectService.findById(v.getId());
			List<FilesVo> fileList = filesService.listByBusId(v.getId(), Constants.FILE_TYPE_PROJECT);
			v.setFileList(fileList);
			mav.addObject(VO, v);
			mav.setViewName(getViewPath() + "_show");
		}
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "update4Survey.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新踏勘信息", module = "现场踏勘")
	public Status update4Survey(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file)
			throws GlobalException {
		try {
			projectTkService.update4Survey(v);
			// 文件上传
			List<FilesVo> fileList = uploads(file, v.getId(), Constants.FILE_TYPE_PROJECT);
			filesService.saveFiles(fileList);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@RequestMapping(value = "save4Survey.do")
	@Log(operation = Log.Operation.SAVE, content = "保存踏勘信息", module = "现场踏勘")
	public ModelAndView save4Survey(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file)
			throws GlobalException {
		try {
			projectTkService.update4Survey(v);
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
		mav.setViewName("redirect:edit4Survey.do");
		return mav;
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