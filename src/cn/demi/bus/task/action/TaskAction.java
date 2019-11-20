package cn.demi.bus.task.action;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.core.framework.utils.importx.ImportUtils;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.service.ITaskPointService;
import cn.demi.bus.task.service.ITaskService;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 任务登记
 * 
 * @author QuJunLong
 *
 */
@Controller("bus.taskAction")
@RequestMapping("/bus/task")
public class TaskAction extends BaseAction<TaskVo> {
	public static final String EXPORT_PATH = ApplicationUtils.getValue("config.upload.samp.task").toString().replace("\\", File.separator);
	public static final String TEMP_PATH = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");

	final String VIEW_PATH = "/bus/task/dj/task";
	@Autowired
	private ITaskService taskService;
	@Autowired
	private ITaskPointService taskPointService;
	@Autowired
	private IProgressLogService progressLogService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private ICodeService codeService;
	@Autowired
	private ISampTypeService sampTypeService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<TaskVo> baseService() {
		return taskService;
	}

	@RequestMapping(value = "gridPage4MySelect.do")
	public ModelAndView gridPage4MySelect(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_tab");
		return mav;
	}

	@Override
	public ModelAndView edit(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = taskService.findById(v.getId());
			// 若为退回项目，获取退回日志记录
			if (v.getIsBack().equals(Constants.Y)) {
				ProgressLogVo logVo = progressLogService.findBack(v.getId());
				mav.addObject("logVo", logVo);
			}
			List<FilesVo> fileList = filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		} else {// 初始化信息
			v = taskService.find4Dj(v);
		}
		// 任务类型
		mav.addObject("taskTypeList", EnumBus.getBusList(v.getSampType()));
		List<SampTypeVo> sampList = null;
		if (StrUtils.isNotBlankOrNull(v.getSampTypeId())) {
			sampList = sampTypeService.listByIds(v.getSampTypeId());
		}
		mav.addObject("sampList", sampList);
		// 样品保存条件
		List<String> srList = codeService.listByCode("save-request");
		mav.addObject("srList", srList);
		mav.addObject(VO, v);
		if ((v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) && !StrUtils.isBlankOrNull(v.getProjectId())) {
			mav.setViewName(getViewPath() + "_zw_edit_project");// 职业卫生 和公共卫生 从项目过来单子
		} else if (v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			mav.setViewName(getViewPath() + "_zw_edit");// 职卫 登记单
		} else if (!StrUtils.isBlankOrNull(v.getProjectId())) {
			mav.setViewName(getViewPath() + "_edit_project");// 环境 从项目过来单子
		} else {
			mav.setViewName(getViewPath() + "_edit");// 环境登记单
		}
		return mav;
	}

	@RequestMapping(value = "add4Data.do")
	@Log(operation = Log.Operation.ADD, content = "新增信息", module = "业务受理")
	public ModelAndView add4Data(TaskVo v, RedirectAttributes attr, @RequestParam(value = "files", required = false) MultipartFile[] files)
			throws GlobalException {
		try {
			taskService.add(v);
			// 文件上传
			List<FilesVo> fileList = uploads(files, v.getId(), Constants.FILE_TYPE_TASK);
			filesService.saveFiles(fileList);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}

	@RequestMapping(value = "update4Data.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新信息", module = "业务受理")
	public ModelAndView update4Data(TaskVo v, RedirectAttributes attr, @RequestParam(value = "files", required = false) MultipartFile[] files)
			throws GlobalException {
		try {
			taskService.update(v);
			// 文件上传
			List<FilesVo> fileList = uploads(files, v.getId(), Constants.FILE_TYPE_TASK);
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
	@Log(operation = Log.Operation.SAVE, content = "保存信息", module = "业务受理")
	public ModelAndView save4Data(TaskVo v, RedirectAttributes attr, @RequestParam(value = "files", required = false) MultipartFile[] files)
			throws GlobalException {
		try {
			taskService.save(v);
			// 文件上传
			List<FilesVo> fileList = uploads(files, v.getId(), Constants.FILE_TYPE_TASK);
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

	@ResponseBody
	@RequestMapping(value = "saveData.do")
	@Log(operation = Log.Operation.SAVE, content = "保存信息", module = "业务受理")
	public Status saveData(TaskVo v, RedirectAttributes attr, @RequestParam(value = "files", required = false) MultipartFile[] files) throws GlobalException {
		try {
			taskService.save(v);
			status = new Status("保存成功", Status.STATUS_SUCCESS);
			status.setObject(v.getId());
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("保存失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@Override
	@Log(operation = Log.Operation.UPDATE, content = "删除信息", module = "业务受理")
	public ModelAndView delete(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskService.delete(v.getIds().split(","));
			status = new Status("删除成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败", e);
			status = new Status("删除失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "deletePoint.do")
	@Log(operation = Log.Operation.DELETE, content = "删除点位信息", module = "业务受理")
	public Status deletePoint(TaskPointVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskPointService.delete(v.getId());
			status = new Status("删除成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败", e);
			status = new Status("删除失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * 终止流程
	 * 
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "update2Stop.do")
	@Log(operation = Log.Operation.UPDATE, content = "终止业务", module = "业务受理")
	public Status update2Stop(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskService.update2Stop(v);
			status = new Status("终止成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("终止失败", e);
			status = new Status("终止失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@Override
	public ModelAndView show(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskService.findById(v.getId());
		List<FilesVo> fileList = filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		mav.addObject(VO, v);
		if (v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			mav.setViewName(getViewPath() + "_zw_show");// 职卫 登记单
		} else {
			mav.setViewName(getViewPath() + "_show");
		}
		return mav;
	}

	// private String getPath(String title,String originalFileName){
	// String suffix =
	// originalFileName.substring(originalFileName.lastIndexOf("."));
	// String fileName = title+"_"+new Date().getTime();
	// String path =
	// UPLOAD_DIR+Constants.FILE_TYPE_TASK+File.separator+DateUtils.getCurrDateStr()+File.separator;
	// path = path+fileName + suffix;
	// return path.replace("\\", "/");
	// }

	/**
	 * 已提交更新信息页面
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value = "edited.do")
	public ModelAndView edited(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskService.findById(v.getId());
		List<FilesVo> fileList = filesService.listByBusId(v.getId());
		v.setFileList(fileList);
		// 任务来源来源公共代码
		mav.addObject("tsList", codeService.listByCode("task-source"));
		// 样品保存条件
		List<String> srList = codeService.listByCode("save-request");
		mav.addObject("srList", srList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit_ed");
		return mav;
	}

	/**
	 * 已提交更新
	 * 
	 * @param v
	 * @param attr
	 * @param files
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "update4Yb.do")
	@Log(operation = Log.Operation.UPDATE, content = "已办更新方法信息", module = "业务受理")
	public Status update4Yb(TaskVo v, RedirectAttributes attr, @RequestParam(value = "files", required = false) MultipartFile[] files) throws GlobalException {
		try {
			taskService.update4Yb(v);
			// 文件上传
			List<FilesVo> fileList = uploads(files, v.getId(), Constants.FILE_TYPE_TASK);
			filesService.saveFiles(fileList);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * 历史记录 复制弹窗使用
	 * 
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Old.do")
	public GridVo gridData4Old(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskService.gridData4Old(gridVo, v);
	}

	/**
	 * 例行 任务 为生成例行 成果表 提供数据源
	 * 
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "gridData4Lx.do")
	public GridVo gridData4Lx(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskService.gridData4Lx(gridVo, v);
	}

	@ResponseBody
	@RequestMapping(value = "copyTask.do")
	@Log(operation = Log.Operation.ADD, content = "复制信息", module = "业务受理")
	public Status copyTask(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			String targetId = taskService.copy(v);
			status = new Status("copy成功", Status.STATUS_SUCCESS);
			status.setObject(targetId);
		} catch (GlobalException e) {
			log.info("copy失败", e);
			status = new Status("copy成功", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * Description : 修改委托单 <br>
	 * 
	 * @throws GlobalException
	 */
	@RequestMapping(value = { "editRwd.do" })
	public ModelAndView editRwd(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		TaskVo taskVo = taskService.find4Rwd(v.getId());
		mav.addObject("id", taskVo.getId());
		mav.addObject("tempPath", taskVo.getFilePath());
		mav.setViewName(getViewPath() + "_file_open");
		return mav;
	}

	/**
	 * Description : 生成/更新 协议书 <br>
	 * 
	 * @throws GlobalException
	 * @throws IOException
	 */
	@RequestMapping(value = { "update4Rwd.do" })
	public ModelAndView update4Rwd(TaskVo v) throws GlobalException, IOException {
		ModelAndView mav = new ModelAndView();
		TaskVo taskVo = taskService.find4Rwd(v.getId());
		taskVo = createWordRepFile(taskVo);// 生成报告文件
		taskService.update4File(taskVo);
		mav.addObject("id", taskVo.getId());
		mav.addObject("tempPath", taskVo.getFilePath());
		mav.setViewName(getViewPath() + "_file_open");
		return mav;
	}

	/**
	 * Description : 查看任务单 <br>
	 * 
	 * @throws GlobalException
	 */
	@RequestMapping(value = { "showRwd.do" })
	public ModelAndView showRwd(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskService.find4Rwd(v.getId());
		mav.setViewName(getViewPath() + "_file_show");
		return mav;
	}

	/**
	 * 生成word方法
	 * 
	 * @throws IOException
	 */
	public TaskVo createWordRepFile(TaskVo v) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String temPath = EXPORT_PATH + File.separator + v.getId();
		String fileName = v.getId() + ".doc";// 生成的文件存放路径
		String targetPath = temPath + File.separator + fileName;// 生成的文件存放路径
		String filePath = SERVER_BASE + temPath;// 生成的文件存放路径
		File file = new File(filePath);
		if (!file.exists())
			file.mkdirs();
		OutputStream os = null;
		OutputStreamWriter osw = null;

		try {

			Configuration cfg = new Configuration();
			cfg.setDefaultEncoding("utf-8");
			cfg.setDirectoryForTemplateLoading(new File(SERVER_BASE + TEMP_PATH));
			cfg.setObjectWrapper(new DefaultObjectWrapper());

			Template temp = cfg.getTemplate("task-wtd.ftl", "utf-8");

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
		v.setFileName(fileName);
		v.setFilePath(targetPath.replace("\\", "/"));
		return v;
	}

	/**
	 * Description : 在线保存 <br>
	 * 
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = { "ajaxSaveFile.do" })
	public void ajaxSaveFile(TaskVo v) throws GlobalException {
		TaskVo taskVo = taskService.find4Rwd(v.getId());
		FileSaver fs = new FileSaver(request, response);
		String targetFile = SERVER_BASE + taskVo.getFilePath().replace("/", "\\");
		fs.saveToFile(targetFile);
		fs.close();
	}

	/******** 外部人员登记 **********/
	@ResponseBody
	@RequestMapping(value = "gridData4Select.do")
	public GridVo gridData4Select(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskService.gridData4Select(gridVo, v);
	}

	/******** 我的客户任务进度跟踪 ********/
	@ResponseBody
	@RequestMapping(value = "gridData4MySelect.do")
	public GridVo gridData4MySelect(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskService.gridData4MySelect(gridVo, v);
	}

	@RequestMapping(value = "edit4Other.do")
	public ModelAndView edit4Other(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = taskService.findById(v.getId());
			List<FilesVo> fileList = filesService.listByBusId(v.getId());
			v.setFileList(fileList);
		} else {// 初始化信息
			v = taskService.find4Dj(v);
		}
		// 任务类型
		mav.addObject("taskTypeList", EnumBus.getALLList());
		// 样品来源公共代码
		mav.addObject("sourceList", codeService.listByCode("task-source"));
		// 样品类型
		List<SampTypeVo> sampTypeList = sampTypeService.listByPid(sampTypeService.findRoot().getId());
		mav.addObject("sampTypeList", sampTypeList);
		// 样品名称
		List<SampTypeVo> sampList = null;
		if (StrUtils.isNotBlankOrNull(v.getSampTypeId())) {
			sampList = sampTypeService.listByPid(v.getSampTypeId());
		} else if (sampTypeList.size() > 0) {
			sampList = sampTypeService.listByPid(sampTypeList.get(0).getId());
		}
		mav.addObject("sampList", sampList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_other_edit");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "save4Other.do")
	public Status save4Other(TaskVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile[] file) throws GlobalException {
		try {
			taskService.save4Other(v);
			// 文件上传
			List<FilesVo> fileList = uploads(file, v.getId(), Constants.FILE_TYPE_TASK);
			filesService.saveFiles(fileList);
			status = new Status("保存成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败", e);
			status = new Status("保存失败", Status.STATUS_ERROR);
		}
		return status;
	}
	
	
	@RequestMapping(value="import2Page.do")
	public ModelAndView import2Page(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_import");
		return mav;
	}
	

	
}