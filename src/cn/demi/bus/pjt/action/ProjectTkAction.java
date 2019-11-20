package cn.demi.bus.pjt.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pjt.po.Project;
import cn.demi.bus.pjt.service.ICustMaterialService;
import cn.demi.bus.pjt.service.ICustPointService;
import cn.demi.bus.pjt.service.ICustRoomService;
import cn.demi.bus.pjt.service.ICustWorkService;
import cn.demi.bus.pjt.service.IProjectService;
import cn.demi.bus.pjt.service.IProjectTkService;
import cn.demi.bus.pjt.service.IProjectZpService;
import cn.demi.bus.pjt.vo.AuVo;
import cn.demi.bus.pjt.vo.CustMaterialVo;
import cn.demi.bus.pjt.vo.CustPointVo;
import cn.demi.bus.pjt.vo.CustRoomVo;
import cn.demi.bus.pjt.vo.CustWorkVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SurveyVo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 现场踏勘
 * 
 * @author QuJunLong
 *
 */
@Controller("bus.projectTkAction")
@RequestMapping("/bus/projectTk")
public class ProjectTkAction extends BaseAction<ProjectVo> {
	public static final String SERVER_EXPORT_DIR = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");
	final String VIEW_PATH = "/bus/project/tk/project_tk";
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IProjectTkService projectTkService;
	@Autowired
	private IProjectZpService projectZpService;
	@Autowired
	private ICustRoomService custRoomService;
	@Autowired
	private ICustPointService custPointService;
	@Autowired
	private ICustMaterialService custMaterialService;
	@Autowired
	private ICustWorkService custWorkService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private ICodeService codeService;

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
			List<FilesVo> fileList = filesService.listByBusId(v.getId(), Constants.FILE_TYPE_PROJECT_TK);
			if (v.getSampType().equals(EnumBus.SAMP_TYPE_ZW) || v.getSampType().equals(EnumBus.SAMP_TYPE_GW)) {
				v = projectTkService.find(v.getId());
				v.setFileList(fileList);
				mav.addObject(VO, v);
				mav.addObject("wlList", codeService.listByCode("wl-type"));
				mav.setViewName(getViewPath() + "_zw_edit");
			} else {
				SurveyVo vo = projectTkService.findByProjectId(v.getId());
				v.setFileList(fileList);
				vo.setProjectVo(v);
				mav.addObject(VO, vo);
				mav.setViewName(getViewPath() + "_edit");
			}
		}
		return mav;
	}

	@RequestMapping(value = "show4Surve.do")
	public ModelAndView show4Surve(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (!StrUtils.isBlankOrNull(v.getId())) {
			v = projectService.findById(v.getId());
			List<FilesVo> fileList = filesService.listByBusId(v.getId(), Constants.FILE_TYPE_PROJECT_TK);
			if (v.getSampType().equals(EnumBus.SAMP_TYPE_ZW) || v.getSampType().equals(EnumBus.SAMP_TYPE_GW)) {
				v = projectTkService.findById(v.getId());
				v.setFileList(fileList);
				mav.addObject(VO, v);
				mav.setViewName(getViewPath() + "_zw_show");
			} else {
				SurveyVo vo = projectTkService.findByProjectId(v.getId());
				v.setFileList(fileList);
				vo.setProjectVo(v);
				mav.addObject(VO, vo);
				mav.setViewName(getViewPath() + "_show");
			}
		}
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "update4Survey.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新踏勘信息", module = "现场踏勘")
	public Status update4Survey(SurveyVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file)
			throws GlobalException {
		try {
			projectTkService.update4Survey(v);
			// 文件上传
			List<FilesVo> fileList = uploads(file, v.getId(), Constants.FILE_TYPE_PROJECT_TK);
			filesService.saveFiles(fileList);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
	}

	// 职卫 保存
	@ResponseBody
	@RequestMapping(value = "update4Data.do")
	@Log(operation = Log.Operation.UPDATE, content = "保存踏勘信息", module = "现场踏勘")
	public Status update4Data(ProjectVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file)
			throws GlobalException {
		try {
			projectTkService.update(v);
			// 文件上传
			List<FilesVo> fileList = uploads(file, v.getId(), Constants.FILE_TYPE_PROJECT_TK);
			filesService.saveFiles(fileList);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
	}

	// 任务转移
	@RequestMapping(value = "edit4Zy.do")
	public ModelAndView edit4Zy(SurveyVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getProjectVo().getId())) {
			String projectId = v.getProjectVo().getId();
			v = projectTkService.findByProjectId(v.getProjectVo().getId());
			v.getProjectVo().setId(projectId);
		}
		mav.addObject(VO, v);
		List<AuVo> auList = projectZpService.listAu(v.getProjectVo().getSampType());
		mav.addObject("userList", auList);
		mav.setViewName(getViewPath() + "_edit_zy");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "update4Zy.do")
	@Log(operation = Log.Operation.UPDATE, content = "踏勘任务转移", module = "现场踏勘")
	public Status update4Zy(SurveyVo v, RedirectAttributes attr) throws GlobalException {
		try {
			projectTkService.update4Zy(v);
			status = new Status("转移成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("转移失败", Status.STATUS_ERROR);
		}
		return status;
	}

	// 动态增加 车间 点位 物料 写实 记录
	@ResponseBody
	@RequestMapping(value = "addRoom.do")
	@Log(operation = Log.Operation.ADD, content = "增加车间信息", module = "现场踏勘")
	public Status addRoom(CustRoomVo v, RedirectAttributes attr) throws GlobalException {
		try {
			custRoomService.add(v);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "addPoint.do")
	@Log(operation = Log.Operation.ADD, content = "增加检测点信息", module = "现场踏勘")
	public Status addPoint(CustPointVo v, RedirectAttributes attr) throws GlobalException {
		try {
			custPointService.add(v);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "addMaterial.do")
	@Log(operation = Log.Operation.ADD, content = "增加物料信息", module = "现场踏勘")
	public Status addMaterial(CustMaterialVo v, RedirectAttributes attr) throws GlobalException {
		try {
			custMaterialService.add(v);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "addWork.do")
	@Log(operation = Log.Operation.ADD, content = "增加写实调查信息", module = "现场踏勘")
	public Status addWork(CustWorkVo v, RedirectAttributes attr) throws GlobalException {
		try {
			custWorkService.add(v);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "deleteRoom.do")
	@Log(operation = Log.Operation.DELETE, content = "删除车间信息", module = "现场踏勘")
	public Status deleteRoom(CustRoomVo v, RedirectAttributes attr) throws GlobalException {
		try {
			custRoomService.delete(v.getId());
			status = new Status("删除成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败", e);
			status = new Status("删除失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "deletePoint.do")
	@Log(operation = Log.Operation.DELETE, content = "删除检测点信息", module = "现场踏勘")
	public Status deletePoint(CustPointVo v, RedirectAttributes attr) throws GlobalException {
		try {
			custPointService.delete(v.getId());
			status = new Status("删除成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败", e);
			status = new Status("删除失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "deleteMaterial.do")
	@Log(operation = Log.Operation.DELETE, content = "删除物料信息", module = "现场踏勘")
	public Status deleteMaterial(CustMaterialVo v, RedirectAttributes attr) throws GlobalException {
		try {
			custMaterialService.delete(v.getId());
			status = new Status("删除成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败", e);
			status = new Status("删除失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "deleteWork.do")
	@Log(operation = Log.Operation.DELETE, content = "删除车间信息", module = "现场踏勘")
	public Status deleteWork(CustWorkVo v, RedirectAttributes attr) throws GlobalException {
		try {
			custWorkService.delete(v.getId());
			status = new Status("删除成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败", e);
			status = new Status("删除失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@RequestMapping(value = "save4Survey.do")
	@Log(operation = Log.Operation.SAVE, content = "保存踏勘信息", module = "现场踏勘")
	public ModelAndView save4Survey(SurveyVo v, RedirectAttributes attr) throws GlobalException {
		try {
			projectTkService.update4Survey(v);
			status = new Status("保存成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("保存失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		// mav.addObject("id", v.getId());
		mav.addObject("id", v.getProjectVo().getId());
		mav.setViewName("redirect:edit4Survey.do");
		return mav;
	}

	/**
	 * 
	 * @Title: showWl @Description: 物料单 @param: @param
	 *         v @param: @return @param: @throws GlobalException @param: @throws
	 *         IOException @return: ModelAndView @throws
	 */
	@RequestMapping(value = "showWl.do")
	public ModelAndView showWl(ProjectVo v) throws GlobalException, IOException {
		ModelAndView mav = new ModelAndView();
		v = projectTkService.findById4Word(v.getId(), Project.WLD);
		if (StrUtils.isBlankOrNull(v.getWlPath())) {
			v = createWordRepFile(v, "XC-TK-WL.ftl", Project.WLD);// 生成报告文件
			projectTkService.update4Word(v);
		}
		mav.addObject("tempPath", v.getWlPath());
		mav.setViewName(getViewPath() + "_file_show");
		return mav;
	}

	/**
	 * 
	 * @Title: showDC @Description: 调查表 @param: @param
	 *         v @param: @return @param: @throws GlobalException @param: @throws
	 *         IOException @return: ModelAndView @throws
	 */
	@RequestMapping(value = "showDC.do")
	public ModelAndView showDC(ProjectVo v) throws GlobalException, IOException {
		ModelAndView mav = new ModelAndView();
		v = projectTkService.findById4Word(v.getId(), Project.DCB);
		if (StrUtils.isBlankOrNull(v.getDcPath())) {
			v = createWordRepFile(v, "XC-TK-DC.ftl", Project.DCB);// 生成报告文件
			projectTkService.update4Word(v);
		}
		mav.addObject("tempPath", v.getDcPath());
		mav.setViewName(getViewPath() + "_file_show");
		return mav;
	}

	/**
	 * 
	 * @Title: showDW @Description: 点位表 @param: @param
	 *         v @param: @return @param: @throws GlobalException @param: @throws
	 *         IOException @return: ModelAndView @throws
	 */
	@RequestMapping(value = "showDW.do")
	public ModelAndView showDW(ProjectVo v) throws GlobalException, IOException {
		ModelAndView mav = new ModelAndView();
		v = projectTkService.findById4Word(v.getId(), Project.DWB);
		if (StrUtils.isBlankOrNull(v.getDwPath())) {
			v = createWordRepFile(v, "XC-TK-DW.ftl", Project.DWB);// 生成报告文件
			projectTkService.update4Word(v);
		}
		mav.addObject("tempPath", v.getDwPath());
		mav.setViewName(getViewPath() + "_file_show");
		return mav;
	}

	public ProjectVo createWordRepFile(ProjectVo v, String source, String type) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String reportPath = UPLOAD_DIR + Constants.FILE_TYPE_PROJECT + File.separator + v.getId();
		String fileName = v.getId();
		String targetPath = reportPath + File.separator;
		if (type.equals(Project.WLD)) {
			fileName = fileName + "_wld.doc";// 生成的文件存放路径
			targetPath = targetPath + fileName;// 生成的文件存放路径
			v.setWlName(fileName);
			v.setWlPath(targetPath.replace("\\", "/"));
		} else if (type.equals(Project.DCB)) {
			fileName = fileName + "_dcb.doc";// 生成的文件存放路径
			targetPath = targetPath + fileName;// 生成的文件存放路径
			v.setDcName(fileName);
			v.setDcPath(targetPath.replace("\\", "/"));
		} else if (type.equals(Project.DWB)) {
			fileName = fileName + "_dwb.doc";// 生成的文件存放路径
			targetPath = targetPath + fileName;// 生成的文件存放路径
			v.setDwName(fileName);
			v.setDwPath(targetPath.replace("\\", "/"));
		}

		String filePath = SERVER_BASE + reportPath;// 生成的文件存放路径
		File file = new File(filePath);
		if (!file.exists())
			file.mkdirs();
		OutputStream os = null;
		OutputStreamWriter osw = null;

		File docFile = new File(SERVER_BASE + targetPath);
		if (!docFile.exists()) {
			docFile.createNewFile();
		} else {
			docFile.delete();
			docFile.createNewFile();
		}
		try {
			Configuration cfg = new Configuration();
			cfg.setDefaultEncoding("utf-8");
			cfg.setDirectoryForTemplateLoading(new File(SERVER_BASE + SERVER_EXPORT_DIR));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(source, "utf-8");

			os = new FileOutputStream(SERVER_BASE + targetPath);
			osw = new OutputStreamWriter(os);
			Writer docout = new BufferedWriter(osw);
			temp.process(dataMap, docout);
		} catch (Exception e) {
			Logger.getLogger(ExportUtils.class).error("生成文件出错", e);
		} finally {
			if (null != osw) {
				osw.flush();
				osw.close();
			}
			if (null != os) {
				os.flush();
				os.close();
			}
		}
		return v;
	}

}