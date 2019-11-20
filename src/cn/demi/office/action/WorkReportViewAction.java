package cn.demi.office.action;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.po.WorkReport;
import cn.demi.office.service.IWorkReportViewService;
import cn.demi.office.vo.WorkReportVo;

@Controller("office.workReportViewAction")
@RequestMapping("/office/workReportView")
public class WorkReportViewAction extends BaseAction<WorkReportVo> {
	final String VIEW_PATH = "/office/workReportView/work_report_view";
	protected static final String DOWNLOAD_DIR = ApplicationUtils.getValue("config.server.base").toString().replace("\\", "/");
	@Autowired
	private IWorkReportViewService workReportViewService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<WorkReportVo> baseService() {
		return workReportViewService;
	}

	/**
	 * 
	 * <p>
	 * Title: edit
	 * </p>
	 * <p>
	 * Description: 查阅编辑页
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
			v = workReportViewService.findById(v.getId());// 跳转到编辑时查询汇报信息
		}
		v.setViewerId(getCurrent().getAccountId());
		v.setViewer(getCurrent().getUserName());
		v.setViewDate(DateUtils.getCurrDateStr());
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
	 * Description: 工作查阅列表
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
		return workReportViewService.gridData(gridVo, v);
	}

	/**
	 * 
	 * @Title: save4Data @Description: 保存方法 @param: @param v @param: @param
	 * attr @param: @param file @param: @return @param: @throws
	 * GlobalException @return: ModelAndView @throws
	 */
	@RequestMapping(value = "save4Data.do")
	@Log(operation = Log.Operation.SAVE, content = "保存工作查阅", module = "工作简报")
	public ModelAndView save4Data(WorkReportVo v, RedirectAttributes attr)
			throws GlobalException {
		try {
			v.setWpStatus(WorkReport.WORK_REPORT_STATUS_WTJ);
			workReportViewService.save(v);
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

	/**
	 * 
	 * @Title: update4Data   
	 * @Description: 修改提交方法
	 * @param: @param v
	 * @param: @param attr
	 * @param: @param file
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: Status      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="update4Data.do")
	@Log(operation=Log.Operation.UPDATE,content="提交修改的工作查阅",module="工作简报")
	public Status update4Data(WorkReportVo v, RedirectAttributes attr) throws GlobalException {
		try {
			workReportViewService.updateWorkReport(v); //修改保存工作查阅
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	
	/**
	 * 
	 * @Title: add4Data   
	 * @Description:新增提交方法  
	 * @param: @param v
	 * @param: @param attr
	 * @param: @param file
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: Status      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="add4Data.do")
	@Log(operation=Log.Operation.ADD,content="提交新增的工作查阅",module="工作简报")
	public Status add4Data(WorkReportVo v, RedirectAttributes attr) throws GlobalException {
		try {
			v.setWpStatus(WorkReport.WORK_REPORT_STATUS_YFK);//已反馈
			workReportViewService.addWorkReport(v); //新增保存工作查阅
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * 
	 * <p>Title: download</p>   
	 * <p>Description: 下载文件</p>   
	 * @param filePath
	 * @param trueName
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.action.BaseAction#download(java.lang.String, java.lang.String)
	 */
	@RequestMapping(value = DOWNLOAD)
	public ResponseEntity<byte[]> download(@RequestParam(value = "filePath", required = true) String filePath,
			@RequestParam(value = "trueName", required = true) String trueName) throws GlobalException {
		String ctxPath = DOWNLOAD_DIR + filePath;
		String downLoadPath = ctxPath.replace("/", File.separator);
		return down(trueName, downLoadPath);
	}

}