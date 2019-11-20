package cn.demi.bus.task.action;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
import cn.demi.base.system.po.Org;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.service.IOrgService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.base.system.vo.OrgVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.sample.service.ISamplingService;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.bus.task.service.ITaskJjService;
import cn.demi.bus.task.vo.TaskBaseVo;
import cn.demi.bus.task.vo.TaskVo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 样品交接
 * 
 * @author QuJunLong
 */
@Controller("bus.taskJjAction")
@RequestMapping("/bus/taskJj")
public class TaskJjAction extends BaseAction<TaskVo> {

	public static final String TEMP_PATH = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");
	public static final String FILE_PATH = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	final String VIEW_PATH = "/bus/task/jj/task_jj";
	@Autowired
	private ITaskJjService taskJjService;
	@Autowired
	private IProgressLogService progressLogService;
	@Autowired
	private ISamplingService samplingService;
	// @Autowired
	// private ISampTypeService sampTypeService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private ICodeService codeService;
	@Autowired
	private IOrgService orgService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<TaskVo> baseService() {
		return taskJjService;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "gridData.do")
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskJjService.gridData(gridVo, v);
	}

	@Override
	public ModelAndView edit(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskJjService.find(v.getId());
		v.setReciveDate(DateUtils.getCurrDateTimeStr());
		v.setReciveUser(getCurrent().getUserName());
		v.setReciveUserId(getCurrent().getAccountId());
		
		List<FilesVo> fjList = filesService.listByBusId(v.getId());
		v.setFileList(fjList);
		// 样品保存条件
		List<String> srList = codeService.listByCode("save-request");
		mav.addObject("srList", srList);
		// 保存地址
		List<String> saddrList = codeService.listByCode("save-address");
		mav.addObject("saddrList", saddrList);
		List<OrgVo> orgList = orgService.listByType(Org.TYPE_JC);
		mav.addObject("orgList", orgList);
		mav.addObject(VO, v);
		if (v.getSource().equals(Constants.SAMP_XC)) {
			mav.setViewName(getViewPath() + "_edit");
		} else {
			mav.setViewName(getViewPath() + "_sy_edit");
		}
		return mav;
	}

	@Override
	public ModelAndView show(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskJjService.findById(v.getId());
		
		List<FilesVo> fileList = filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		ProgressLogVo logVo = progressLogService.find(v.getId(), EunmTask.TASK_JJ.getStatus());
		mav.addObject("logVo", logVo);
		mav.setViewName(getViewPath() + "_show");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskJjService.gridDatad(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "ajaxCheckCode.do")
	public Status ajaxCheckCode(String sampCode, RedirectAttributes attr) throws GlobalException {
		if (StrUtils.isBlankOrNull(sampCode)) {
			status = new Status("参数异常,验证编号失败", Status.STATUS_ERROR);
			status.setObject("");
			attr.addFlashAttribute(STATUS, status);
			return status;
		}
		try {
			if (taskJjService.checkCode(sampCode)) {
				status = new Status("编号重复", Status.STATUS_SUCCESS);
			}
			status = new Status("验证通过", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			status = new Status("验证失败", Status.STATUS_ERROR);
			status.setObject("");
		}
		attr.addFlashAttribute(STATUS, status);
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "update4Jj.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新交接信息", module = "样品交接")
	public Status update4Jj(TaskVo v, RedirectAttributes attr, @RequestParam(value = "files", required = false) MultipartFile[] files) throws GlobalException {
		try {
			baseService().update(v);
			//文件上传
			List<FilesVo> fileList = uploads(files, v.getId(), Constants.FILE_TYPE_TASK_JJ);
			filesService.saveFiles(fileList);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "updateBack.do")
	@Log(operation = Log.Operation.UPDATE, content = "退回交接信息", module = "样品交接")
	public Status updateBack(TaskVo v, RedirectAttributes attr, @RequestParam(value = "files", required = false) MultipartFile[] files) throws GlobalException {
		try {
			taskJjService.updateBack(v);
			// 文件上传
			List<FilesVo> fileList = uploads(files, v.getId(), Constants.FILE_TYPE_TASK_JJ);
			filesService.saveFiles(fileList);
			status = new Status("退回成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "退回失败", e);
			status = new Status("退回失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "ajaxGetSampList.do")
	public List<SamplingVo> ajaxGetSampList(String id) throws GlobalException {
		List<SamplingVo> sampList = samplingService.listByTaskId(id);
		return sampList;
	}

	/**
	 * 打印样品编号
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value = "print.do")
	public ModelAndView print(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskJjService.findById(v.getId());
		List<SamplingVo> sampList = samplingService.listByTaskId(v.getId());
		for (SamplingVo samplingVo : sampList) {
			samplingVo.getTaskVo().setCyDate(samplingVo.getTaskVo().getCyDate().substring(0, 10));
			samplingVo.setItemNames(samplingVo.getItemNames().replace(",", "、"));
		}
		mav.addObject("sampList", sampList);
		mav.addObject(VO, v);
		if (v.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			mav.setViewName(getViewPath() + "_print");
		} else {
			mav.setViewName(getViewPath() + "_zw_print");
		}
		return mav;
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
	public void ajaxSaveFile(TaskVo v) throws GlobalException {
		String id = v.getId();
		if (id.contains("ypjj")) {
			id = id.split("ypjj")[0];
		}
		v = taskJjService.findById(id);
		FileSaver fs = new FileSaver(request, response);
		String reportPath = UPLOAD_DIR + Constants.FILE_TYPE_TASK + File.separator + v.getId();
		String fileName = v.getNo() + ".doc";// 生成的文件存放路径
		String targetPath = reportPath + File.separator + fileName;// 生成的文件存放路径
		String targetFile = SERVER_BASE + targetPath.replace("/", "\\");
		fs.saveToFile(targetFile);
		fs.close();
	}

	/**
	 * 
	 * @Title: editWord @Description: 生成样品交接单 @param: @param
	 *         v @param: @return @param: @throws GlobalException @param: @throws
	 *         IOException @param: @throws ParseException @return:
	 *         ModelAndView @throws
	 */
	@RequestMapping(value = "editWord.do")
	@Log(operation = Log.Operation.UPDATE, content = "生成样品交接单", module = "样品交接")
	public ModelAndView editWord(TaskVo v) throws GlobalException, IOException, ParseException {
		ModelAndView mav = new ModelAndView();
		v = taskJjService.findById(v.getId());
		TaskBaseVo taskBaseVo = new TaskBaseVo();
		taskBaseVo.setYear(DateUtils.getYear());
		taskBaseVo.setMonth(DateUtils.getMonth());
		taskBaseVo.setDay(DateUtils.getDay());
		v.setTaskBaseVo(taskBaseVo);
		List<SamplingVo> sampList = samplingService.listSampsByTaskId(v.getId());
		v.setSampList(sampList);
		if (StrUtils.isBlankOrNull(v.getCyDate())) {
			v.setCyDate("");
		} else {
			v.setCyDate(DateUtils.getChineseDate(v.getCyDate()));// 日期转化成中文的年月日
		}
		if (StrUtils.isBlankOrNull(v.getCyEndDate())) {
			v.setCyDate("");
		} else {
			v.setCyEndDate(DateUtils.getChineseDate(v.getCyEndDate()));// 日期转化成中文的年月日
		}

		v = createWordRepFile(v, "YP-JJ-BD.ftl");// 生成样品采样计划表

		mav.addObject(VO, v);
		mav.addObject("tempPath", v.getYpjjPath());
		mav.addObject("id", v.getId() + "ypjj");
		mav.setViewName(getViewPath() + "_file_open");
		return mav;
	}

	public TaskVo createWordRepFile(TaskVo v, String source) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String reportPath = UPLOAD_DIR + Constants.FILE_TYPE_TASK + File.separator + v.getId();
		String fileName = v.getNo() + "_jjd.doc";// 生成的文件存放路径
		String targetPath = reportPath + File.separator + fileName;// 生成的文件存放路径
		v.setYpjjName(fileName);
		v.setYpjjPath(targetPath.replace("\\", "/"));
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
			cfg.setDirectoryForTemplateLoading(new File(SERVER_BASE + TEMP_PATH));
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
	
	
	@RequestMapping(value = DOWNLOAD)
	public ResponseEntity<byte[]> download(
			@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		String ctxPath = FILE_PATH + filePath;
		String downLoadPath = ctxPath.replace("/", File.separator);  
        return down(trueName, downLoadPath);
	}

}