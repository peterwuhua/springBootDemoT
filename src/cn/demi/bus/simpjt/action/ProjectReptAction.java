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
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IAccountRoleService;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.simpjt.service.IProjectReptService;
import cn.demi.bus.simpjt.vo.ProjectVo;

/**
 * 报告编制
 * 
 */
@Controller("bus.simProjectReptAction")
@RequestMapping("/bus/sim/project/rept")
public class ProjectReptAction extends BaseAction<ProjectVo> {
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	public static final String REPORT_PATH = ApplicationUtils.getValue("config.upload.samp.report.report").toString().replace("\\", File.separator);
	public static final String SERVER_EXPORT_DIR = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");

	final String VIEW_PATH = "/bus/sim/report/bz/report";

	@Autowired
	private IProjectReptService projectReptService;
	@Autowired
	private IProgressLogService progressLogService;
	@Autowired
	private IAccountRoleService accountRoleService;
	@Autowired
	private IFilesService filesService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<ProjectVo> baseService() {
		return projectReptService;
	}

	@Override
	public ModelAndView edit(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = projectReptService.find(v.getId());
		}
		// 若为退回项目，获取退回日志记录
		if (v.getIsBack().equals(Constants.Y)) {
			ProgressLogVo logVo = progressLogService.findBack(v.getId());
			mav.addObject("logVo", logVo);
		}
		List<AccountVo> accountList = accountRoleService.listAccount(Constants.ROLE_REPORT_AUDIT);
		mav.addObject("userList", accountList);
		mav.addObject("user",getCurrent().getUserName()+"【"+getCurrent().getLoginName()+"】");
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@Override
	public ModelAndView show(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = baseService().findById(v.getId());
		}
		// 文件上传
		List<FilesVo> fileList = filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		ProgressLogVo logVo = progressLogService.find(v.getId(), EunmProject.PROJECT_BZ.getStatus());
		mav.addObject("logVo", logVo);
		mav.setViewName(getViewPath() + "_show");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value="update4Data.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新报告信息", module = "报告编制")
	public Status update4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file) throws GlobalException {
		try {
			projectReptService.update(v);
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
	@RequestMapping(value="save4Data.do")
	@Log(operation=Log.Operation.SAVE,content="保存报告编制信息",module="报告编制")
	public ModelAndView save4Data(ProjectVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException {
		try {
			projectReptService.save(v);
			//文件上传
			List<FilesVo> fileList=uploads(file, v.getId(),Constants.FILE_TYPE_PROJECT);
			filesService.saveFiles(fileList);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.error("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}
	
	
	@Override
	@ResponseBody
	@RequestMapping(value = "gridData.do")
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		return projectReptService.gridData(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException {
		return projectReptService.gridDatad(gridVo, v);
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