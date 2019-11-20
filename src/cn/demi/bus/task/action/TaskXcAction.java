package cn.demi.bus.task.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import cn.core.framework.constant.EnumCyd;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.sample.service.ISampCydService;
import cn.demi.bus.sample.vo.SampCydVo;
import cn.demi.bus.task.service.ITaskXcService;
import cn.demi.bus.task.vo.CyrVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.service.ITaskItemService;
import cn.demi.bus.test.service.ITestItemService;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.bus.test.vo.TestItemVo;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;
import cn.demi.init.std.service.ISampSourceService;
import cn.demi.res.service.IApparaService;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * 现场采样
 * 
 * @author QuJunLong
 */
@Controller("bus.taskXcAction")
@RequestMapping("/bus/taskXc")
public class TaskXcAction extends BaseAction<TaskVo> {

	public static final String EXPORT_PATH = ApplicationUtils.getValue("config.upload.samp.task").toString().replace("\\", File.separator);
	public static final String TEMP_PATH = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");

	final String VIEW_PATH = "/bus/task/xc/task_xc";
	@Autowired
	private ITaskXcService taskXcService;
	// @Autowired
	// private ISamplingService samplingService;
	@Autowired
	private IProgressLogService progressLogService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private ICodeService codeService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ISampSourceService sampSourceService;
	@Autowired
	private ITestItemService testItemService;
	@Autowired
	private ITaskItemService taskItemService;
	@Autowired
	private ISampCydService sampCydService;
	@Autowired
	private ISampTypeService sampTypeService;
	@Autowired
	private IApparaService apparaService;
	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<TaskVo> baseService() {
		return taskXcService;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "gridData.do")
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		return taskXcService.gridData(gridVo, v);
	}

	@Override
	public ModelAndView edit(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskXcService.find(v.getId());
		v.setCyUserId(getCurrent().getAccountId());
		v.setCyUserName(getCurrent().getUserName());
		v.setCyTime(DateUtils.getCurrDateTimeStr());
		// 附件
		List<FilesVo> fileList = filesService.listByBusId(v.getId(), Constants.FILE_TYPE_SAMP);
		v.setFileList(fileList);
		mav.addObject(VO, v);
		if (v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			// 点位现场检测情况
			mav.addObject("cydEnum", EnumCyd.getList4Zw());
			mav.setViewName(getViewPath() + "_zw_edit");// 职卫 登记单
		} else {
			List<SampTypeVo> sampList = null;
			if (StrUtils.isNotBlankOrNull(v.getSampTypeId())) {
				sampList = sampTypeService.listByIds(v.getSampTypeId());
			}
			mav.addObject("sampList", sampList);
			mav.addObject("cydEnum", EnumCyd.getList4Hj());
			mav.setViewName(getViewPath() + "_edit");
		}
		return mav;
	}

	@Override
	public ModelAndView show(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskXcService.find(v.getId());
		// 附件
		List<FilesVo> fileList = filesService.listByBusId(v.getId(), Constants.FILE_TYPE_SAMP);
		v.setFileList(fileList);
		ProgressLogVo logVo = progressLogService.find(v.getId(), EunmTask.TASK_XC.getStatus());
		mav.addObject("logVo", logVo);
		mav.addObject(VO, v);
		if (v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			mav.setViewName(getViewPath() + "_zw_show");// 职卫 登记单
		} else {
			mav.setViewName(getViewPath() + "_show");
		}
		return mav;
	}

	/**
	 * Description : 根据评价标准自动判定结果 <br>
	 * 
	 * @param v
	 *            ReportVo
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "checkValue.do")
	public Map<String, Object> checkValue(TestItemVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean b = testItemService.checkValue(v);
		if (!b) {
			map.put("type", Status.STATUS_ERROR);
			return map;
		}
		map.put("type", Status.STATUS_SUCCESS);
		return map;
	}

	/**
	 * 更新现场信息
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "update4Data.do")
	@Log(operation = Log.Operation.UPDATE, content = "保存现场信息", module = "现场采样")
	public Status update4Data(TaskVo v, RedirectAttributes attr, @RequestParam(value = "files", required = false) MultipartFile[] files)
			throws GlobalException {
		try {
			taskXcService.update(v);
			// 文件上传
			List<FilesVo> fileList = uploads(files, v.getId(), Constants.FILE_TYPE_SAMP);
			filesService.saveFiles(fileList);
			status = new Status("保存成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("保存失败", Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * 更新现场信息
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "updateBack.do")
	@Log(operation = Log.Operation.UPDATE, content = "退回", module = "现场采样")
	public Status updateBack(TaskVo v, RedirectAttributes attr)throws GlobalException {
		try {
			taskXcService.updateBack(v);
			status = new Status("退回成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			status = new Status("退回失败", Status.STATUS_ERROR);
		}
		return status;
	}
	
	@ResponseBody
	@RequestMapping(value = "delPoint.do")
	@Log(operation = Log.Operation.DELETE, content = "删除点位", module = "现场采样")
	public Status delPoint(String ids, RedirectAttributes attr)throws GlobalException {
		try {
			taskXcService.deletePoint(ids);
			status = new Status("退回成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			status = new Status("退回失败", Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value = "addPoint.do")
	@Log(operation = Log.Operation.DELETE, content = "删除点位", module = "现场采样")
	public Status addPoint(String id, RedirectAttributes attr)throws GlobalException {
		try {
			taskXcService.addPoint(id);
			status = new Status("添加成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			status = new Status("添加失败", Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value = "uptPoint.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新点位", module = "现场采样")
	public Status uptPoint(TaskPointVo v, RedirectAttributes attr)throws GlobalException {
		try {
			taskXcService.uptPoint(v);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
	}
	// 打印
	@RequestMapping(value = "print.do")
	public ModelAndView print(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskXcService.findById(v.getId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_print");
		return mav;
	}

	/***** 选择采样单模板打印 ***********/
	// 选择采样单模板页面
	@RequestMapping(value = "selectCyd.do")
	public ModelAndView selectCyd(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		SampCydVo vo = sampCydService.findById(v.getId());
		mav.addObject(VO, vo);
		mav.setViewName(getViewPath() + "_cyd");
		return mav;
	}

	// 编辑采样单文件
	@RequestMapping(value = "editCydFile.do")
	public ModelAndView editCydFile(SampCydVo v) throws GlobalException, IOException {
		ModelAndView mav = new ModelAndView();
		SampCydVo vo = sampCydService.find4File(v.getId());
		if (StrUtils.isBlankOrNull(vo.getFilePath())) {
			vo = createFile(vo, v.getTemp());// 生成采样单
			vo.setTemp(v.getTemp());
			sampCydService.updateFile(vo);
		}
		File f = new File(SERVER_BASE + vo.getFilePath());
		if (!f.exists() || !f.isFile()) {
			vo = createFile(vo, v.getTemp());// 生成采样单
			vo.setTemp(v.getTemp());
			sampCydService.updateFile(vo);
		}
		mav.addObject("tempPath", vo.getFilePath());
		mav.addObject("id", vo.getId());
		mav.setViewName(getViewPath() + "_file_open");
		return mav;
	}

	// 生成采样单文件
	@RequestMapping(value = "createCydFile.do")
	public ModelAndView createCydFile(SampCydVo v) throws GlobalException, IOException {
		ModelAndView mav = new ModelAndView();
		SampCydVo vo = sampCydService.find4File(v.getId());
		vo = createFile(vo, v.getTemp());// 生成采样单
		vo.setTemp(v.getTemp());
		sampCydService.updateFile(vo);
		mav.addObject("tempPath", vo.getFilePath());
		mav.addObject("id", vo.getId());
		mav.setViewName(getViewPath() + "_file_open");
		return mav;
	}

	// 生成word 采样单文件
	public SampCydVo createFile(SampCydVo v, String tempId) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String reportPath = EXPORT_PATH + File.separator + v.getTaskVo().getId();
		String targetPath = reportPath + File.separator + v.getId() + ".doc";// 生成的文件存放路径
		String filePath = SERVER_BASE + reportPath;// 生成的文件存放路径
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
			Template temp = cfg.getTemplate(tempId + ".ftl", "utf-8");
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
		v.setFilePath(targetPath.replace("\\", "/"));
		v.setFileName(v.getPointName() + "_采样单.doc");
		return v;
	}

	// 生成xls 采样单文件
	public SampCydVo createXlsFile(SampCydVo v, String temp) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String source = SERVER_BASE + File.separator + temp + ".xls";// 模板文件
		String subPath = EXPORT_PATH + File.separator + v.getTaskVo().getId();
		String fileName = v.getId() + ".xls";// 生成的文件存放路径
		String targetPath = subPath + File.separator + fileName;// 生成的文件存放路径
		String filePath = SERVER_BASE + subPath;// 生成的文件存放路径
		if (StrUtils.isNotBlankOrNull(v.getFilePath())) {
			String oldFilePath = SERVER_BASE + File.separator + v.getFilePath();
			File oldFile = new File(oldFilePath);
			if (oldFile.exists())
				oldFile.delete();
		}
		File file = new File(filePath);
		if (!file.exists())
			file.mkdirs();
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(new FileInputStream(source));
			Workbook workbook = transformer.transformXLS(is, dataMap);
			os = new BufferedOutputStream(new FileOutputStream(SERVER_BASE + targetPath));
			workbook.write(os);
		} catch (Exception e) {
			Logger.getLogger(ExportUtils.class).error("生成文件出错", e);
		} finally {
			if (null != is) {
				is.close();
			}
			if (null != os) {
				os.flush();
				os.close();
			}
		}
		v.setFilePath(targetPath.replace("\\", "/"));
		v.setFileName(v.getPointName() + "_采样单.xls");
		return v;
	}

	/**
	 * Description : 在线保存文件 <br>
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxSaveFile.do")
	public void ajaxSaveFile(SampCydVo v) throws GlobalException {
		FileSaver fs = new FileSaver(request, response);
		SampCydVo vo = sampCydService.find4File(v.getId());
		String targetFile = SERVER_BASE + vo.getFilePath().replace("/", "\\");
		fs.saveToFile(targetFile);
		fs.close();
	}

	/*** 环境 采样单 **********/
	@RequestMapping(value = "editCyd.do")
	public ModelAndView editCyd(SampCydVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = sampCydService.find4Cyd(v.getId());
		// 检测方法
		mav.addObject("cyMethodList", sampSourceService.listByIds(v.getTaskVo().getCyStandIds()));
		// 采样设备
		mav.addObject("appList",apparaService.listByIds(v.getTaskVo().getCyAppIds()));
		// 分析人 校核人
		List<AccountVo> userList = accountService.listByIds(v.getTaskVo().getCyId());
		mav.addObject("userList", userList);

		// 采样人
		List<CyrVo> cyrList = accountService.listByUsers(userList);
		mav.addObject("cyrList", cyrList);

		
		
		// 天气
		List<String> tqList = codeService.listByCode("xc-tq");
		mav.addObject("tqList", tqList);
		// 风向
		List<String> fxList = codeService.listByCode("xc-fx");
		mav.addObject("fxList", fxList);
		// 排放类型
		List<String> runTypeList = codeService.listByCode("xc-runType");
		mav.addObject("runTypeList", runTypeList);
		// 样品保存条件
		List<String> srList = codeService.listByCode("save-request");
		mav.addObject("srList", srList);
		// 功能区
		List<String> gnqList = codeService.listByCode("xc-gnq");
		mav.addObject("gnqList", gnqList);
		if (!StrUtils.isBlankOrNull(v.getType())) {
			mav.setViewName("/bus/task/xc/cyd/" + v.getType() + "_edit");
		} else {
			mav.setViewName("/bus/task/xc/cyd/cyd_edit");
		}
		mav.addObject(VO, v);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "updateCyd.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新采样单", module = "现场采样")
	public Status updateCyd(SampCydVo v) throws GlobalException {
		try {
			sampCydService.updateCyd(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@RequestMapping(value = "showCyd.do")
	public ModelAndView showCyd(SampCydVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = sampCydService.find4Cyd(v.getId());
		if (!StrUtils.isBlankOrNull(v.getType())) {
			mav.setViewName("/bus/task/xc/cyd/" + v.getType() + "_show");
		} else {
			mav.setViewName("/bus/task/xc/cyd/cyd_show");
		}
		mav.addObject(VO, v);
		return mav;
	}

	/***** 现场项目数据录入 查看等 ***********************/
	// 现场项目结果查看
	@RequestMapping(value = "show4Item.do")
	public ModelAndView show4Item(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (!StrUtils.isBlankOrNull(v.getId())) {
			v = taskXcService.findById(v.getId());
			List<TaskItemVo> timList = testItemService.list4Xc(v.getId());
			mav.addObject("timList", timList);
		}
		mav.addObject(VO, v);
		if (v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			mav.setViewName(getViewPath() + "_zw_show_item");
		} else {
			mav.setViewName(getViewPath() + "_show_item");
		}

		return mav;
	}

	// 现场项目结果录入
	@RequestMapping(value = "edit4Item.do")
	public ModelAndView edit4Item(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (!StrUtils.isBlankOrNull(v.getId())) {
			v = taskXcService.findById(v.getId());
			taskXcService.checkItem(v.getId());
			List<TaskItemVo> timList = testItemService.list4Xc(v.getId());
			mav.addObject("timList", timList);
			// 采样设备
			mav.addObject("appList", taskXcService.getAppList(v.getId()));
			// 分析人 采样人 校核人
			List<AccountVo> userList = accountService.listByIds(v.getCyId());
			mav.addObject("userList", userList);
		}
		mav.addObject(VO, v);
		if (v.getSampType().equals(EnumBus.SAMP_TYPE_GW) || v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			mav.setViewName(getViewPath() + "_zw_edit_item");
		} else {
			mav.setViewName(getViewPath() + "_edit_item");
		}
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "update4Item.do")
	@Log(operation = Log.Operation.UPDATE, content = "现场项目录入", module = "现场采样")
	public Status update4Item(TaskVo v) throws GlobalException {
		try {
			testItemService.update4Xc(v);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "update4Limited.do")
	@Log(operation = Log.Operation.UPDATE, content = "现场数据录入更新限值", module = "现场采样")
	public Status update4Limited(TaskItemVo v) throws GlobalException {
		try {
			taskItemService.update4Limited(v);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**** 职业卫生公共卫生 ******/
	/**
	 * 职业卫生采样单 编辑
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value = "editCyd4Zw.do")
	public ModelAndView editCyd4Zw(SampCydVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = sampCydService.find4Cyd(v.getId());
		// 采样方法
		mav.addObject("cyMethodList", sampSourceService.listByIds(v.getTaskVo().getCyStandIds()));
		// 采样设备
		mav.addObject("appList",apparaService.listByIds(v.getTaskVo().getCyAppIds()));
		// 分析人 采样人 校核人
		List<AccountVo> userList = accountService.listByIds(v.getTaskVo().getCyId());
		mav.addObject("userList", userList);
		// 天气
		List<String> tqList = codeService.listByCode("xc-tq");
		mav.addObject("tqList", tqList);
		// 风向
		List<String> fxList = codeService.listByCode("xc-fx");
		mav.addObject("fxList", fxList);

		if (v.getType().equals("cyd_wl_jg")) {// 查询激光辐射类型列表
			List<String> jgList = codeService.listByCode("xc-jglx");
			mav.addObject("jgTypeList", jgList);
		} else if (v.getType().equals("cyd_wl_cp")) {// 查询超高频辐射类型
			List<String> jgList = codeService.listByCode("xc-cgpfs");
			mav.addObject("jgTypeList", jgList);
		} else if (v.getType().equals("cyd_wl_cg")) {// 查询照度类型
			List<String> jgList = codeService.listByCode("xc-zdlx");
			mav.addObject("jgTypeList", jgList);
		}
		if (!StrUtils.isBlankOrNull(v.getType())) {
			mav.setViewName("/bus/task/xc/cyd_zw/" + v.getType() + "_edit");
		} else {
			mav.setViewName("/bus/task/xc/cyd_zw/cyd_edit");
		}
		mav.addObject(VO, v);
		return mav;
	}

	/**
	 * 更新职业卫生 采样单
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "updateCyd4Zw.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新采样单", module = "现场采样")
	public Status updateCyd4Zw(SampCydVo v) throws GlobalException {
		try {
			sampCydService.updateCyd(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * 职业卫生采样单查看
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value = "showCyd4Zw.do")
	public ModelAndView showCyd4Zw(SampCydVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = sampCydService.find4Cyd(v.getId());
		if (!StrUtils.isBlankOrNull(v.getType())) {
			mav.setViewName("/bus/task/xc/cyd_zw/" + v.getType() + "_show");
		} else {
			mav.setViewName("/bus/task/xc/cyd_zw/cyd_show");
		}
		mav.addObject(VO, v);
		return mav;
	}

	/*** 采样单操作 ***/
	// 合并采样单
	@ResponseBody
	@RequestMapping(value = "update4Hb.do")
	@Log(operation = Log.Operation.UPDATE, content = "合并采样单", module = "现场采样")
	public Status update4Hb(String ids) throws GlobalException {
		try {
			taskXcService.update4Hb(ids);
			status = new Status("合并成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "合并失败", e);
			status = new Status("合并失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "update4Cf.do")
	@Log(operation = Log.Operation.UPDATE, content = "拆分采样单", module = "现场采样")
	public Status update4Cf(String id) throws GlobalException {
		try {
			taskXcService.update4Cf(id);
			status = new Status("拆分成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "拆分失败", e);
			status = new Status("拆分失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * 重置所有采样单
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "initAllCyd.do")
	@Log(operation = Log.Operation.UPDATE, content = "重置所有采样单", module = "现场采样")
	public Status initAllCyd(TaskVo v) throws GlobalException {
		try {
			boolean f = taskXcService.initAllCyd(v);
			if (f) {
				status = new Status("重置成功", Status.STATUS_SUCCESS);
			} else {
				status = new Status("重置失败", Status.STATUS_ERROR);
			}
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "重置失败", e);
			status = new Status("重置失败", Status.STATUS_ERROR);
		}
		return status;
	}
}