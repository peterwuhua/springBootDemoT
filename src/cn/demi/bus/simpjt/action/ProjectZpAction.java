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
import cn.core.framework.constant.EunmProject;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.pjt.vo.AuVo;
import cn.demi.bus.simpjt.service.IProjectZpService;
import cn.demi.bus.simpjt.vo.ProjectVo;

/**
 * 任务指派
 * 
 * @author QuJunLong
 *
 */
@Controller("bus.simProjectZpAction")
@RequestMapping("/bus/sim/projectZp")
public class ProjectZpAction extends BaseAction<ProjectVo> {
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	final String VIEW_PATH = "/bus/sim/project/zp/project_zp";
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
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = projectZpService.findById(v.getId());
			List<FilesVo> fileList = filesService.listByBusId(v.getId());
			v.setFileList(fileList);
			if (StrUtils.isBlankOrNull(v.getZpUserId())) {
				v.setZpUserId(getCurrent().getAccountId());
				v.setZpUserName(getCurrent().getUserName());
				v.setZpDate(DateUtils.getCurrDateTimeStr());
			}
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
			v = projectZpService.findById(v.getId());
			List<FilesVo> fileList = filesService.listByBusId(v.getId());
			v.setFileList(fileList);
			ProgressLogVo logVo = progressLogService.find(v.getId(), EunmProject.PROJECT_ZP.getStatus());
			mav.addObject("logVo", logVo);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_show");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "update4Data.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新指派信息", module = "任务指派")
	public Status update4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file)
			throws GlobalException {
		try {
			projectZpService.update(v);
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

	@RequestMapping(value = "user_select.do")
	public ModelAndView user_select(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_user_select");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "listAu.do")
	public List<AuVo> listAu(ProjectVo v) throws GlobalException {
		return projectZpService.listAu(v.getItemType());
	}

}