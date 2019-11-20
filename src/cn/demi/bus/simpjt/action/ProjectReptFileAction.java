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
import cn.core.framework.constant.EunmProject;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.simpjt.service.IProjectReptFileService;
import cn.demi.bus.simpjt.vo.ProjectVo;

/**
 * 提交备案
 * 
 */
@Controller("bus.simProjectReptFileAction")
@RequestMapping("/bus/sim/project/reptFile")
public class ProjectReptFileAction extends BaseAction<ProjectVo> {
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	final String VIEW_PATH = "/bus/sim/report/file/report_file";
	@Autowired
	private IProjectReptFileService  projectReptFileService;
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
		return projectReptFileService;
	}

	@Override
	public ModelAndView edit(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = projectReptFileService.findById(v.getId());
		}
		v.setFileDate(DateUtils.getCurrDateTimeStr());
		v.setFileUser(CurrentUtils.getCurrent().getUserName());
		v.setFileUserId(CurrentUtils.getCurrent().getAccountId());
		mav.addObject("user", getCurrent().getUserName() + "【" + getCurrent().getLoginName() + "】");
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "update4Data.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新报告信息", module = "报告归档")
	public Status update4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file) throws GlobalException {
		try {
			projectReptFileService.update(v);
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

	@Override
	@ResponseBody
	@RequestMapping(value = "gridData.do")
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		return projectReptFileService.gridData(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException {
		return projectReptFileService.gridDatad(gridVo, v);
	}

	@Override
	public ModelAndView show(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = baseService().findById(v.getId());
		}
		mav.addObject(VO, v);
		ProgressLogVo logVo = progressLogService.find(v.getId(), EunmProject.PROJECT_BA.getStatus());
		mav.addObject("logVo", logVo);
		mav.setViewName(getViewPath() + "_show");
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