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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhuozhengsoft.pageoffice.FileSaver;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.export.ExportUtils;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.pjt.service.IImService;
import cn.demi.bus.pjt.vo.ImVo;
import cn.demi.bus.task.service.ITaskApService;
import cn.demi.bus.task.vo.SampAppVo;
import cn.demi.bus.task.vo.TaskBaseVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.init.sp.vo.PcUnit;
import cn.demi.init.std.service.IItemMethodService;
import cn.demi.init.std.service.ISampSourceService;
import cn.demi.init.std.vo.SampSourceVo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 采样安排
 * 
 * @author QuJunLong
 */
@Controller("bus.taskApAction")
@RequestMapping("/bus/taskAp")
public class TaskApAction extends BaseAction<TaskVo> {

	final String VIEW_PATH = "/bus/task/ap/task_ap";
	public static final String SERVER_EXPORT_DIR = ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", "/");
	@Autowired
	private ITaskApService taskApService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IImService imService;
	@Autowired
	private ISampSourceService sampSourceService;
	@Autowired
	private IItemMethodService itemMethodService;
	@Autowired 
	private IProgressLogService progressLogService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<TaskVo> baseService() {
		return taskApService;
	}

	@Override
	public ModelAndView edit(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = taskApService.find(v.getId());
		}
		//若为退回项目，获取退回日志记录
		if(v.getIsBack().equals(Constants.Y)) {
			ProgressLogVo logVo=progressLogService.findBack(v.getId());
			mav.addObject("logVo", logVo);
		}
		if (null == v.getCarList() || v.getCarList().size() == 0) {
			v.setCarList(taskApService.findDefinedCar());
		}
		mav.addObject("pcUnitList", PcUnit.getRwList());
		// 负责人
		List<AccountVo> fzList = accountService.listByIds(v.getCyId());
		mav.addObject("fzList", fzList);
		mav.addObject("pcUnitList", PcUnit.getRwList());
		// 未来7天采样安排
		Map<String, Object> panMap = taskApService.listApPlan(v.getId());
		mav.addObject("panMap", panMap);
		mav.addObject(VO, v);
		if(v.getSampType().equals(EnumBus.SAMP_TYPE_GW)||v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			mav.setViewName(getViewPath()+"_zw_edit");//职卫 登记单
		}else {
			mav.setViewName(getViewPath() + "_edit");
		}
		return mav;
	}

	@Override
	public ModelAndView show(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = taskApService.findById(v.getId());
		}
		mav.addObject(VO, v);
		if(v.getSampType().equals(EnumBus.SAMP_TYPE_GW)||v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			mav.setViewName(getViewPath()+"_zw_show");//职卫 登记单
		}else {
			mav.setViewName(getViewPath() + "_show");
		}
		return mav;
	}

	@RequestMapping(value = "edited.do")
	public ModelAndView edited(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			v = taskApService.findById(v.getId());
		}
		mav.addObject("pcUnitList", PcUnit.getRwList());
		// 负责人
		List<AccountVo> fzList = accountService.listByIds(v.getCyId());
		mav.addObject("fzList", fzList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit_ed");
		return mav;
	}

	@Override
	@Log(operation = Log.Operation.UPDATE, content = "更新信息", module = "采样安排")
	public Status updateData(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskApService.update(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * 重置样品
	 * 根据采样日期  或 点位批次单位的变化
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "update4Samp.do")
	@Log(operation = Log.Operation.UPDATE, content = "重置样品信息", module = "采样安排")
	public Status update4Samp(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskApService.update4Samp(v);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * 重置样品
	 * 将已生成的样品信息根据采样日期点信息等全部重置
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "update4SampAll.do")
	@Log(operation = Log.Operation.UPDATE, content = "重置样品信息", module = "采样安排")
	public Status update4SampAll(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			boolean f=taskApService.update4SampAll(v);
			if(f) {
				status = new Status("重置成功", Status.STATUS_SUCCESS);
			}else {
				status = new Status("重置失败，请检查采样日期和点位频次", Status.STATUS_ERROR);
			}
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "重置失败", e);
			status = new Status("重置失败", Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value = "updateBack.do")
	@Log(operation = Log.Operation.UPDATE, content = "退回任务", module = "采样安排")
	public Status updateBack(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskApService.updateBack(v);
			status = new Status("退回成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "退回失败", e);
			status = new Status("退回失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "updateYb.do")
	@Log(operation = Log.Operation.UPDATE, content = "更新已办信息", module = "采样安排")
	public Status updateYb(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskApService.updateYb(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "ajaxGetSampCode.do")
	public String ajaxGetSampCode(TaskVo v) throws GlobalException {
		return taskApService.findSampCode(v.getId(), v.getIds());
	}
	 
	/************* 批量 **************/
	@RequestMapping(value = "editBatch.do")
	public ModelAndView editBatch(TaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v = taskApService.find4Batch(v.getIds());
		v.setApDate(DateUtils.getCurrDateTimeStr());
		v.setApName(getCurrent().getUserName());
		v.setApId(getCurrent().getAccountId());
		if (null == v.getCarList() || v.getCarList().size() == 0) {
			v.setCarList(taskApService.findDefinedCar());
		}
		// 负责人
		List<AccountVo> fzList = accountService.listByIds(v.getCyId());
		mav.addObject("fzList", fzList);
		mav.addObject("pcUnitList", PcUnit.getRwList());
		// 未来7天采样安排
		Map<String, Object> panMap = taskApService.listApPlan(v.getId());
		mav.addObject("panMap", panMap);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit_batch");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "updateBatch.do")
	@Log(operation = Log.Operation.UPDATE, content = "批量更新信息", module = "采样安排")
	public Status updateBatch(TaskVo v, RedirectAttributes attr) throws GlobalException {
		try {
			taskApService.updateBatch(v);
			status = new Status("更新成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "更新失败", e);
			status = new Status("更新失败", Status.STATUS_ERROR);
		}
		return status;
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
		if (id.contains("cyjh")) {
			id = id.split("cyjh")[0];
		}
		v = taskApService.findById(id);
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
	 * @throws IOException
	 * @throws ParseException
	 * @Title: editWord @Description: 样品采样计划表在线编辑 @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         ModelAndView @throws
	 */
	@RequestMapping(value = "editWord.do")
	@Log(operation = Log.Operation.UPDATE, content = "生成样品采样计划表", module = "采样安排")
	public ModelAndView editWord(TaskVo v) throws GlobalException, IOException, ParseException {
		ModelAndView mav = new ModelAndView();
		v = taskApService.findById(v.getId());
		List<ImVo> imvosList = imService.list4Method(v.getId());
		if (!StrUtils.isBlankOrNull(v.getCyStandIds())) {
			List<SampSourceVo> ssList = sampSourceService.listByIds(v.getCyStandIds());
			v.setSsList(ssList);
		}
		List<TaskPointVo> taskPoints = itemMethodService.getEnvAndDesc(v.getTpList());
//		List<SampAppVo> sampApps = v.getAppList();
//		if (sampApps.size() > 0) {
//			String appNames = "";
//			int i = 0;
//			for (SampAppVo svo : sampApps) {
//				i++;
//				appNames += svo.getAppName();
//				if (i == sampApps.size() - 1) {
//					appNames += ",";
//				}
//			}
//			v.setAppNames(appNames);// 采样仪器
//		}
		TaskBaseVo taskBaseVo = new TaskBaseVo();
		taskBaseVo.setYear(DateUtils.getYear());
		taskBaseVo.setMonth(DateUtils.getMonth());
		taskBaseVo.setDay(DateUtils.getDay());

		v.setTaskBaseVo(taskBaseVo);
		v.setTpList(taskPoints);
		v.setImList(imvosList);
		v.setCyDate(DateUtils.getChineseDate(v.getCyDate()));// 日期转化成中文年月日格式
		v = createWordRepFile(v, "YP-CY-JH.ftl");// 生成样品采样计划表

		mav.addObject(VO, v);
		mav.addObject("tempPath", v.getCyjhPath());
		mav.addObject("id", v.getId() + "cyjh");
		mav.setViewName(getViewPath() + "_file_open");
		return mav;
	}

	public TaskVo createWordRepFile(TaskVo v, String source) throws IOException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(VO, v);
		String reportPath = UPLOAD_DIR + Constants.FILE_TYPE_TASK + File.separator + v.getId();
		String fileName = v.getNo() + "_jhd.doc";// 生成的文件存放路径
		String targetPath = reportPath + File.separator + fileName;// 生成的文件存放路径
		v.setCyjhName(fileName);
		v.setCyjhPath(targetPath.replace("\\", "/"));
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