package cn.demi.bus.pjt.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhuozhengsoft.pageoffice.FileSaver;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.pjt.service.ICustMaterialService;
import cn.demi.bus.pjt.service.ICustPointService;
import cn.demi.bus.pjt.service.ICustWorkService;
import cn.demi.bus.pjt.service.IProjectPsService;
import cn.demi.bus.pjt.service.ISurveyService;
import cn.demi.bus.pjt.vo.CustMaterialVo;
import cn.demi.bus.pjt.vo.CustPointVo;
import cn.demi.bus.pjt.vo.CustWorkVo;
import cn.demi.bus.pjt.vo.ProjectVo;
import cn.demi.bus.pjt.vo.SurveyVo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 合同评审
 * 
 * @author QuJunLong
 *
 */
@Controller("bus.projectPsAction")
@RequestMapping("/bus/projectPs")
public class ProjectPsAction extends BaseAction<ProjectVo> {
	final String VIEW_PATH = "/bus/project/ps/project_ps";
	public static final String SERVER_EXPORT_DIR = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");
	@Autowired
	private IProjectPsService projectPsService;
	@Autowired
	private IProgressLogService progressLogService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private ISurveyService surveyService;
	@Autowired
	private ICustPointService custPointService;
	@Autowired
	private ICustMaterialService custMaterialService;
	@Autowired
	private ICustWorkService custWorkService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<ProjectVo> baseService() {
		return projectPsService;
	}

	@Override
	public ModelAndView edit(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = projectPsService.findById(v.getId());
			if (StrUtils.isBlankOrNull(v.getPsDate())) {
				v.setPsDate(DateUtils.getCurrDateTimeStr());
			}
			if (StrUtils.isBlankOrNull(v.getDqPsId())) {
				v.setDqPsId(getCurrent().getAccountId());
				v.setDqPsName(getCurrent().getUserName());
			}
			List<FilesVo> fileList = filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		}
		if (v.getSampType().equals(EnumBus.SAMP_TYPE_ZW) || v.getSampType().equals(EnumBus.SAMP_TYPE_GW)) {
			// 检测点信息
			List<CustPointVo> potList = custPointService.listByProjectId(v.getId());
			mav.addObject("potList", potList);
			// 物料集合
			List<CustMaterialVo> mtList = custMaterialService.listByProjectId(v.getId());
			mav.addObject("mtList", mtList);
			// 写实调查集合
			List<CustWorkVo> workList = custWorkService.listByProjectId(v.getId());
			mav.addObject("workList", workList);
		} else {
			SurveyVo surveyVo = surveyService.findByProjectId(v.getId());
			mav.addObject("surveyVo", surveyVo);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	@Override
	public ModelAndView show(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = projectPsService.findById(v.getId());
			ProgressLogVo logVo = progressLogService.find(v.getId(), EunmTask.PROJECT_PS.getStatus());
			mav.addObject("logVo", logVo);
		}
		if (v.getSampType().equals(EnumBus.SAMP_TYPE_ZW) || v.getSampType().equals(EnumBus.SAMP_TYPE_GW)) {
			// 检测点信息
			List<CustPointVo> potList = custPointService.listByProjectId(v.getId());
			mav.addObject("potList", potList);
			// 物料集合
			List<CustMaterialVo> mtList = custMaterialService.listByProjectId(v.getId());
			mav.addObject("mtList", mtList);
			// 写实调查集合
			List<CustWorkVo> workList = custWorkService.listByProjectId(v.getId());
			mav.addObject("workList", workList);
		} else {
			SurveyVo surveyVo = surveyService.findByProjectId(v.getId());
			mav.addObject("surveyVo", surveyVo);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_show");
		return mav;
	}

	@Override
	@Log(operation = Log.Operation.UPDATE, content = "更新方案信息", module = "合同评审")
	public Status updateData(ProjectVo v, RedirectAttributes attr) throws GlobalException {
		try {
			projectPsService.update(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "update2Stop.do")
	@Log(operation = Log.Operation.UPDATE, content = "终止项目", module = "合同评审")
	public Status update2Stop(ProjectVo v, RedirectAttributes attr) throws GlobalException {
		try {
			projectPsService.update2Stop(v);
			status = new Status("终止成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("终止失败", e);
			status = new Status("终止失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "gridData.do")
	public GridVo gridData(GridVo gridVo, ProjectVo v) throws GlobalException {
		v.setStatus(EunmTask.PROJECT_PS.getStatus());
		return projectPsService.gridData(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ProjectVo v) throws GlobalException {
		v.setStatus(EunmTask.PROJECT_PS.getStatus());
		return projectPsService.gridDatad(gridVo, v);
	}

	/**
	 * Description : 在线保存报告 <br>
	 * 
	 * @param v
	 *            ReportVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxSaveFile.do")
	public void ajaxSaveFile(ProjectVo v) throws GlobalException {
		v = projectPsService.findById(v.getId());
		FileSaver fs = new FileSaver(request, response);
		String targetFile = SERVER_BASE + v.getPsFilePath().replace("/", "\\");
		fs.saveToFile(targetFile);
		fs.close();
	}

	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @Title: editWord @Description: 合同评审记录表在线编辑 @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         ModelAndView @throws
	 */
	@RequestMapping(value = "editWord.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新合同评审内容", module = "合同评审")
	public ModelAndView editWord(ProjectVo v) throws GlobalException, IOException, ParseException {
		ModelAndView mav = new ModelAndView();
		v = projectPsService.find4File(v.getId());
		if (StrUtils.isBlankOrNull(v.getPsFilePath())) {
			v = createWordRepFile(v, "HT-PS.ftl");// 生成合同评审文件
			projectPsService.update2PsFile(v);
		}
		mav.addObject(VO, v);
		mav.addObject("id", v.getId());
		mav.addObject("tempPath", v.getPsFilePath());
		mav.setViewName(getViewPath() + "_file_open");
		return mav;
	}

	@RequestMapping(value = "showWord.do")
	public ModelAndView showWord(ProjectVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = projectPsService.findById(v.getId());
		mav.addObject("tempPath", v.getPsFilePath());
		mav.setViewName(getViewPath() + "_file_show");
		return mav;
	}

	public ProjectVo createWordRepFile(ProjectVo v, String source) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String[] psDates = v.getPsDate().split(" ");
		String psdate = psDates[0];
		v.setPsDate(psdate);
		dataMap.put(VO, v);
		// String reportPath
		// =UPLOAD_DIR+File.separator+Constants.FILE_TYPE_PROJECT+File.separator+v.getId();
		String reportPath = UPLOAD_DIR + Constants.FILE_TYPE_PROJECT + File.separator + v.getId();
		String fileName = v.getNo() + "_PS.doc";// 生成的文件存放路径
		String targetPath = reportPath + File.separator + fileName;// 生成的文件存放路径
		v.setPsFileName(fileName);
		v.setPsFilePath(targetPath.replace("\\", "/"));
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