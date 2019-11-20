package cn.demi.bus.pjt.action;
import org.apache.commons.lang3.StringUtils;
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
import cn.demi.bus.pjt.service.ISurveyService;
import cn.demi.bus.pjt.vo.SurveyVo;

/**
 * 现场踏勘 view层
 * @author QuJunLong
 */
@Controller("bus.surveyAction")
@RequestMapping("/bus/survey")
public class SurveyAction extends BaseAction<SurveyVo> {
	final String VIEW_PATH = "/bus/survey/survey";
	@Autowired 
	private ISurveyService surveyService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<SurveyVo> baseService() {
		return surveyService;
	}
	
	@Override
	public ModelAndView edit(SurveyVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=surveyService.findById(v.getId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(SurveyVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=surveyService.findById(v.getId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存踏勘信息",module="现场踏勘")
	public ModelAndView save(SurveyVo v, RedirectAttributes attr) throws GlobalException {
		try {
			surveyService.save(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", v.getId());
		mav.addObject("projectVo.id", v.getProjectVo().getId());
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}
	@ResponseBody
	@RequestMapping(value="updateData.do")
	@Log(operation=Log.Operation.UPDATE,content="更新踏勘信息",module="现场踏勘")
	public Status updateData(SurveyVo v, RedirectAttributes attr) throws GlobalException {
		try {
			surveyService.update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	@ResponseBody
	@RequestMapping(value ="gridData4Tk.do")
	public GridVo gridData4Tk(GridVo gridVo, SurveyVo v) throws GlobalException {
		v.setStatus(EunmTask.PROJECT_TK.getStatus());
		return surveyService.gridData(gridVo,v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, SurveyVo v) throws GlobalException {
		v.setStatus(EunmTask.PROJECT_TK.getStatus());
		return surveyService.gridDatad(gridVo,v);
	}
}