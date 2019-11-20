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
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.service.ISampCkService;
import cn.demi.bus.sample.service.ISamplingService;
import cn.demi.bus.sample.vo.SampUsedVo;
import cn.demi.bus.sample.vo.SamplingVo;
/**
 * 样品出库
 * @author QuJunLong
 *
 */
@Controller("bus.sampCkAction")
@RequestMapping("/bus/sampCk")
public class SampCkAction extends BaseAction<SampUsedVo> {
	final String VIEW_PATH = "/bus/samp/ck/samp_ck";
	@Autowired 
	private ISampCkService sampCkService;	
	@Autowired 
	private ISamplingService samplingService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<SampUsedVo> baseService() {
		return sampCkService;
	}
	@Override
	public ModelAndView edit(SampUsedVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=sampCkService.findById(v.getId());
		}else if(null!=v.getSamplingVo()&&StrUtils.isNotBlankOrNull(v.getSamplingVo().getId())) {
			SamplingVo sampVo=samplingService.findById(v.getSamplingVo().getId());
			v.setSamplingVo(sampVo);
			v.setSampName(sampVo.getSampName());
			v.setSampCode(sampVo.getSampCode());
			v.setUseDate(DateUtils.getCurrDateTimeStr());
			v.setStatus(Sampling.ST_21);
			v.setGh(Constants.F);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="样品出库")
	public Status updateData(SampUsedVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("操作成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"操作失败",e);
			status = new Status("操作失败",Status.STATUS_ERROR);
		}
		return status;
	}
}