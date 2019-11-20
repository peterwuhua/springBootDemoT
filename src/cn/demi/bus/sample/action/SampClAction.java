package cn.demi.bus.sample.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.sample.service.ISampClService;
import cn.demi.bus.sample.service.ISampUsedService;
import cn.demi.bus.sample.vo.SampUsedVo;
import cn.demi.bus.sample.vo.SamplingVo;
/**
 * 余样处理
 * @author QuJunLong
 */
@Controller("bus.sampClAction")
@RequestMapping("/bus/sampCl")
public class SampClAction extends BaseAction<SamplingVo> {
	final String VIEW_PATH = "/bus/samp/cl/samp_cl";
	@Autowired 
	private ISampClService sampClService;
	@Autowired 
	private ISampUsedService sampUsedService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<SamplingVo> baseService() {
		return sampClService;
	}
	@Override
	public ModelAndView edit(SamplingVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=sampClService.findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="余样处理")
	public Status updateData(SamplingVo v, RedirectAttributes attr) throws GlobalException {
		try {
			sampClService.update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	public ModelAndView show(SamplingVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
			List<SampUsedVo> suList=sampUsedService.listBySampId(v.getId());
			mav.addObject("suList", suList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
}