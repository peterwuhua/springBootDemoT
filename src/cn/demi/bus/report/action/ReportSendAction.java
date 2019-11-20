package cn.demi.bus.report.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.bus.report.service.IReportSendService;
import cn.demi.bus.report.vo.ReportVo;
/**
 * 报告签发
 * @author QuJunLong
 */
@Controller("bus.reportSendAction")
@RequestMapping("/bus/reportSend")
public class ReportSendAction extends BaseAction<ReportVo> {
	final String VIEW_PATH = "/bus/report/send/report_send";
	@Autowired private IReportSendService reportSendService;
	@Autowired 
	private IProgressLogService progressLogService;
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ReportVo> baseService() {
		return reportSendService;
	}
	@Override
	public ModelAndView edit(ReportVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=reportSendService.findById(v.getId());
		}
		v.setSendDate(DateUtils.getCurrDateTimeStr());
		v.setToUser(v.getTaskVo().getCustVo().getCustUser());
		v.setSendType(v.getTaskVo().getReportWay());
		v.setSendUser(CurrentUtils.getCurrent().getUserName());
		v.setSendUserId(CurrentUtils.getCurrent().getAccountId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新报告信息",module="报告发放")
	public Status updateData(ReportVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, ReportVo v) throws GlobalException {
		return reportSendService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ReportVo v) throws GlobalException {
		return reportSendService.gridDatad(gridVo, v);
	}
	@Override
	public ModelAndView show(ReportVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
		}
		mav.addObject(VO, v);
		ProgressLogVo logVo=progressLogService.find(v.getId(), EunmTask.TASK_FF.getStatus());
		mav.addObject("logVo", logVo);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
}