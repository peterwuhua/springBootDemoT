package cn.demi.bus.report.action;

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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.report.service.IReportUpdService;
import cn.demi.bus.report.vo.ReportUpdVo;
/**
 * 报告返工
 * @author QuJunLong
 */
@Controller("bus.reportUpdAction")
@RequestMapping("/bus/reportUpd")
public class ReportUpdAction extends BaseAction<ReportUpdVo> {
	final String VIEW_PATH = "/bus/report/upd/report_upd";
	@Autowired 
	private IReportUpdService reportUpdService;
 
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ReportUpdVo> baseService() {
		return reportUpdService;
	}
	@Override
	public ModelAndView edit(ReportUpdVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=reportUpdService.findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="新增返工信息",module="报告返工")
	public Status addData(ReportUpdVo v, RedirectAttributes attr) throws GlobalException {
		try {
			reportUpdService.addReportUpd(v.getReportVo().getId());
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新报告信息",module="报告返工")
	public Status updateData(ReportUpdVo v, RedirectAttributes attr) throws GlobalException {
		try {
			reportUpdService.update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="删除返工信息",module="报告返工")
	public ModelAndView update2del(ReportUpdVo v, RedirectAttributes attr) throws GlobalException {
		try {
			reportUpdService.update2del(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, ReportUpdVo v) throws GlobalException {
		return reportUpdService.gridData(gridVo, v);
	}
	@Override
	public ModelAndView show(ReportUpdVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = reportUpdService.findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	/**
	 * Description : 修改报告  <br>
	 * @param v
	 * @param tempPath
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value={"editReport.do"})
	public ModelAndView editReport(ReportUpdVo v) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		v = reportUpdService.findById(v.getId());
		mav.addObject("id",v.getId());
		mav.addObject("tempPath",v.getFilePath());
		mav.setViewName(getViewPath()+"_file_open");
		return mav;
	}
	@RequestMapping(value={"showReport.do"})
	public ModelAndView showReport(ReportUpdVo v) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		v = reportUpdService.findById(v.getId());
		mav.addObject("tempPath",v.getFilePath());
		mav.setViewName(getViewPath()+"_file_show");
		return mav;
	}
	@RequestMapping(value={"showReport4Pdf.do"})
	public ModelAndView showReport4Pdf(ReportUpdVo v) throws GlobalException {
		ModelAndView mav =  new ModelAndView();
		v = reportUpdService.findById(v.getId());
		mav.addObject("tempPath",v.getPdfPath());
		mav.setViewName(getViewPath()+"_pdf_show");
		return mav;
	}
	/**
	 * Description : 在线保存报告  <br>
	 * @param v ReportVo
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value={"ajaxSaveFile.do"})
	public void ajaxSaveFile(ReportUpdVo v) throws GlobalException {
		ReportUpdVo reportVo = reportUpdService.findById(v.getId());
		FileSaver fs = new FileSaver(request,response);
		String targetFile = SERVER_BASE+reportVo.getFilePath().replace("/", "\\");
		if(fs.getFileExtName().equals(".pdf")) {
			targetFile=SERVER_BASE+reportVo.getPdfPath().replace("/", "\\");
		}
		fs.saveToFile(targetFile);
		fs.close();
 
	}
}