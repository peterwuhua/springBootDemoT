package cn.demi.res.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.demi.res.service.ITrainService;
import cn.demi.res.vo.TrainVo;

@Controller("res.trainAction")
@RequestMapping("/res/train")
public class TrainAction extends BaseAction<TrainVo> {
	final String VIEW_PATH = "/res/train/train";
	@Autowired private ITrainService trainService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TrainVo> baseService() {
		return trainService;
	}
	
	@RequestMapping(value="detail_edit.do")
	public ModelAndView detail_edit(TrainVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		
		v=trainService.findById(v.getId());
		
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_detail_edit");
		return mav;
	}
	
	/**
	 * 更新培训计划状态
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="update4Run.do")
	@Log(operation=Log.Operation.UPDATE,content="更新培训计划状态",module="培训管理")
	public ModelAndView update4Run(TrainVo v, RedirectAttributes attr) throws GlobalException {
		try {
			trainService.update4Run(v.getIds());
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	/**
	 * 更新培训计划状态
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="update4Comp.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新为完成",module="培训管理")
	public Status update4Comp(TrainVo v, RedirectAttributes attr) throws GlobalException {
		try {
			trainService.update4Comp(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * 更新培训对象信息
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value="updateDetail.do")
	@Log(operation=Log.Operation.UPDATE,content="更新培训对象信息",module="培训管理")
	public ModelAndView updateDetail(TrainVo v, RedirectAttributes attr) throws GlobalException {
		try {
			trainService.updateDetail(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
}