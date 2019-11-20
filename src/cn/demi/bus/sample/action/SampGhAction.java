package cn.demi.bus.sample.action;

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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.sample.service.ISampGhService;
import cn.demi.bus.sample.vo.SampUsedVo;
/**
 * 样品归还
 * @author QuJunLong
 *
 */
@Controller("bus.sampGhAction")
@RequestMapping("/bus/sampGh")
public class SampGhAction extends BaseAction<SampUsedVo> {
	final String VIEW_PATH = "/bus/samp/gh/samp_gh";
	@Autowired 
	private ISampGhService sampGhService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<SampUsedVo> baseService() {
		return sampGhService;
	}
	@Override
	public ModelAndView edit(SampUsedVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=sampGhService.findById(v.getId());
			v.setUseDate(DateUtils.getCurrDateTimeStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="样品归还")
	public Status updateData(SampUsedVo v, RedirectAttributes attr) throws GlobalException {
		try {
			sampGhService.update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
}