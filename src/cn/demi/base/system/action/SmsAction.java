package cn.demi.base.system.action;

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
import cn.core.framework.utils.DateUtils;
import cn.demi.base.system.service.ISmsService;
import cn.demi.base.system.vo.SmsVo;
/**
 * <strong>Create on : Dave Yu 2016年11月2日 下午2:27:27 </strong> <br>
 * <strong>Description : 岗位action </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.smsAction")
@RequestMapping("sys/sms")
public class SmsAction extends BaseAction<SmsVo>{
	final String VIEW_PATH = "/sys/sms/sms";
	@Autowired private ISmsService smsService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<SmsVo> baseService(){
		return smsService;
	}
	
	/**	
	 * <strong>Create on : 2016年11月2日 上午10:25:21 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param v V
	 * @param attr RedirectAttributes
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value={"ajaxSaveSms.do","saveSms.do"})
	public @ResponseBody Status saveSms(SmsVo v) throws GlobalException {
		try {
			v.setSendTime(DateUtils.getCurrDateTimeStr());
			smsService.saveSms(v);
			status = new Status("短信发送成功",Status.STATUS_SUCCESS);
			status.setObject(v.getId());
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("短信发送失败，请在系统管理短信功能中尝试重新发送",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	public ModelAndView add(SmsVo v, RedirectAttributes attr) throws GlobalException {
		try {
			v.setSendTime(DateUtils.getCurrDateTimeStr());
			smsService.saveSms(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		ModelAndView mav = new ModelAndView();
		attr.addFlashAttribute(STATUS, status);
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	
}
