package cn.demi.bi.task.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.service.IOrgService;
import cn.demi.base.system.vo.OrgVo;
import cn.demi.bi.task.service.IReportService;
import cn.demi.bus.report.vo.ReportVo;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;

/**
 * 报告统计 view层
 * @author QuJunLong
 *
 */
@Controller("bi.reportAction")
@RequestMapping("/bi/report")
public class ReportAction extends BaseAction<ReportVo> {

	final String VIEW_PATH = "/bi/report/report";
	@Autowired 
	private IReportService reportService;
	@Autowired 
	private IOrgService orgService;
	@Autowired 
	private ISampTypeService sampTypeService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<ReportVo> baseService() {
		return reportService;
	}

	@Override
	public ModelAndView show(ReportVo v) throws GlobalException {
		return super.show(v);
	}
	
	@Override
	public ModelAndView gridPage(ReportVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		List<EnumBus> taskTypeList=EnumBus.getALLList();
		mav.addObject("busList", taskTypeList);
		List<OrgVo> orgList = orgService.listByPid(orgService.findRoot().getId());
		mav.addObject("orgList", orgList);
		List<SampTypeVo> sampTypeList = sampTypeService.listByPid(sampTypeService.findRoot().getId());
		if(null!=sampTypeList) {
			for (SampTypeVo sampTypeVo : sampTypeList) {
				List<SampTypeVo> subList=sampTypeService.listByPid(sampTypeVo.getId());
				sampTypeVo.setList(subList);
			}
		}
		mav.addObject("sampTypeList", sampTypeList);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
}
