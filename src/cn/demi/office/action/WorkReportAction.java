package cn.demi.office.action;

import java.io.File;

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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.po.WorkReport;
import cn.demi.office.service.IWorkReportService;
import cn.demi.office.vo.WorkReportVo;

@Controller("office.workReportAction")
@RequestMapping("/office/workReport")
public class WorkReportAction extends BaseAction<WorkReportVo> {
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	final String VIEW_PATH = "/office/workReport/work_report";
	@Autowired
	private IWorkReportService workReportService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<WorkReportVo> baseService() {
		return workReportService;
	}

	/**
	 * 
	 * <p>
	 * Title: edit
	 * </p>
	 * <p>
	 * Description: 汇报编辑页
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#edit(cn.core.framework.common.vo.Vo)
	 */
	@Override
	public ModelAndView edit(WorkReportVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (v != null && !StrUtils.isBlankOrNull(v.getId())) {
			v = workReportService.findById(v.getId());// 跳转到编辑时查询汇报信息
		} else {
			v.setPerson(getCurrent().getUserName());
			v.setPersonId(getCurrent().getAccountId());
			v.setReportDate(DateUtils.getCurrDateStr());
			v.setDepart(getCurrent().getDepName());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Title: gridPage
	 * </p>
	 * <p>
	 * Description: 工作汇报列表
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.action.BaseAction#gridPage(cn.core.framework.common.vo.Vo)
	 */
	@RequestMapping(value = GRID_PAGE)
	public ModelAndView gridPage(WorkReportVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_page");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = { GRID_DATA, GRID_DATA_JSON })
	public GridVo gridData(GridVo gridVo, WorkReportVo v) throws GlobalException {
		return workReportService.gridData(gridVo, v);
	}

	/**
	 * 
	 * @Title: save4Data @Description: 保存方法 @param: @param v @param: @param
	 *         attr @param: @param file @param: @return @param: @throws
	 *         GlobalException @return: ModelAndView @throws
	 */
	@RequestMapping(value = "save4Data.do")
	@Log(operation = Log.Operation.SAVE, content = "保存工作汇报", module = "工作简报")
	public ModelAndView save4Data(WorkReportVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile file)
			throws GlobalException {
		// 文件上传
		if (null != file && !file.isEmpty()) {
			v.setFilepath(getPath(v, file.getOriginalFilename()));
			upload(file, v.getFilepath());
		}
		try {
			v.setWpStatus(WorkReport.WORK_REPORT_STATUS_WTJ);
			workReportService.save(v);
			status = new Status("保存成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			status = new Status("保存失败", Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}

	/**
	 * 
	 * @Title: update4Data @Description: 修改提交方法 @param: @param v @param: @param
	 * attr @param: @param file @param: @return @param: @throws
	 * GlobalException @return: Status @throws
	 */
	@ResponseBody
	@RequestMapping(value = "update4Data.do")
	@Log(operation = Log.Operation.UPDATE, content = "提交修改的工作汇报", module = "工作简报")
	public Status update4Data(WorkReportVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile file)
			throws GlobalException {
		// 文件上传
		if (null != file && !file.isEmpty()) {
			v.setFilepath(getPath(v, file.getOriginalFilename()));
			upload(file, v.getFilepath());
		}
		try {
			v.setWpStatus(WorkReport.WORK_REPORT_STATUS_WFK);// 未反馈
			workReportService.update(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * 
	 * @Title: getPath @Description: 获取项目工作汇报文件上传路径 @param: @param v @param: @param
	 * fileName @param: @return @return: String @throws
	 */
	private String getPath(WorkReportVo v, String fileName) {
		String fileType = fileName;// .substring(fileName.lastIndexOf("."));
		String path = UPLOAD_DIR + Constants.FILE_TYPE_WORK_REPORT;
		path = path + File.separator + DateUtils.getCurrDateStr() + File.separator + fileType;
		v.setFilename(fileName);
		return path.replace("\\", "/");
	}

	/**
	 * 
	 * @Title: add4Data @Description:新增提交方法 @param: @param v @param: @param
	 * attr @param: @param file @param: @return @param: @throws
	 * GlobalException @return: Status @throws
	 */
	@ResponseBody
	@RequestMapping(value = "add4Data.do")
	@Log(operation = Log.Operation.ADD, content = "提交新增的工作汇报", module = "工作简报")
	public Status add4Data(WorkReportVo v, RedirectAttributes attr, @RequestParam(value = "file", required = false) MultipartFile file) throws GlobalException {
		// 文件上传
		if (null != file && !file.isEmpty()) {
			v.setFilepath(getPath(v, file.getOriginalFilename()));
			upload(file, v.getFilepath());
		}
		try {
			v.setWpStatus(WorkReport.WORK_REPORT_STATUS_WFK);// 未反馈
			workReportService.add(v);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

	/**
	 * 
	 * <p>
	 * Title: download
	 * </p>
	 * <p>
	 * Description: 查看页下载附件
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