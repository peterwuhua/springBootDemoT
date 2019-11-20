package cn.demi.office.action;

import java.util.List;

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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.office.service.IKqShService;
import cn.demi.office.vo.KqVo;
/**
 * 考勤 二级 审核
 * @author QuJunLong
 *
 */
@Controller("office.kqShAction")
@RequestMapping("/office/kqSh")
public class KqShAction extends BaseAction<KqVo> {
	final String VIEW_PATH = "/office/kq_sh/kq_sh";
	@Autowired 
	private IKqShService kqShService;	
	@Autowired 
	private IProgressLogService progressLogService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<KqVo> baseService() {
		return kqShService;
	}
	@Override
	public ModelAndView edit(KqVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=kqShService.findById(v.getId());
			v.setShDate(DateUtils.getCurrDateTimeStr());
			v.setShId(getCurrent().getAccountId());
			v.setShName(getCurrent().getUserName());
		}
		mav.addObject(VO, v);
		List<ProgressLogVo> logList=progressLogService.findList(null, v.getId());
		mav.addObject("logList", logList);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(KqVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = kqShService.findById(v.getId());
			List<ProgressLogVo> logList=progressLogService.findList(null, v.getId());
			mav.addObject("logList", logList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value="update2Data.do")
	@Log(operation=Log.Operation.UPDATE,content="考勤审核",module="考勤审核")
	public Status update2Data(KqVo v, RedirectAttributes attr) throws GlobalException {
		try {
			kqShService.update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
}