package cn.demi.base.system.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.demi.base.system.service.IEmailService;
import cn.demi.base.system.vo.EmailCfgVo;
import cn.demi.base.system.vo.EmailVo;
import net.sf.json.JSONObject;
/**
 * <strong>Create on : 2016年11月2日 下午2:11:38 </strong> <br>
 * <strong>Description :邮件action  </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.emailAction")
@RequestMapping("sys/email")
public class EmailAction extends BaseAction<EmailVo>{
	final String VIEW_PATH = "/sys/email/email";
	@Autowired private IEmailService emailService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<EmailVo> baseService(){
		return emailService;
	}
	
	/**	
	 * <strong>Create on : 2016年11月2日 上午10:25:21 </strong> <br>
	 * <strong>Description : 发送邮件 </strong> <br>
	 * @param v V
	 * @param attr RedirectAttributes
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping(value={"ajaxSaveMail.do","saveMail.do"})
	@Log(operation=Log.Operation.ADD,content="发送邮件",module="邮件管理")
	public @ResponseBody Status saveMail(EmailVo v,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException {
		try {
			v.setSendTime(DateUtils.getCurrDateTimeStr());
			String result=emailService.saveMail(v,file);
			if(null!=result && result.equals("true")) {
				status = new Status("发送成功",Status.STATUS_SUCCESS);
			}else {
				status = new Status("发送失败，请检查邮箱配置或收件人是否正确",Status.STATUS_ERROR);
			}
			status.setObject(v.getId());
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"发送失败",e);
			status = new Status("发送失败，请检查邮箱配置或收件人是否正确",Status.STATUS_ERROR);
		}
		return status;
	}
	
	@RequestMapping(value={"add4Data.do"})
	@ResponseBody
	@Log(operation=Log.Operation.ADD,content="发送邮件",module="邮件管理")
	public Status add4Data(EmailVo v, RedirectAttributes attr,@RequestParam(value="file", required=false)MultipartFile[] file) throws GlobalException {
		try {
			v.setSendTime(DateUtils.getCurrDateTimeStr());
			String result=emailService.saveMail(v,file);
			if(null!=result && result.equals("true")) {
				status = new Status("发送成功",Status.STATUS_SUCCESS);
			}else {
				status = new Status("发送失败，请检查邮箱配置或收件人是否正确",Status.STATUS_ERROR);
			}
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("发送失败，请检查邮箱配置或收件人是否正确",Status.STATUS_ERROR);
		}
		return status;
	}
	/**
	 * Create on : Paddy Zhang 2017年4月23日 下午7:09:46 <br>
	 * Description : 发送消息 <br>
	 * @param mailData 邮件json数据  '{"receiver":"receiver","subject":"subject","content":"content","busType":"busType","busInfo":"busInfo","remark":"remark"}';
	 * @param smsData 短信数据  '{"receiver":"receiver","content":"content","busType":"busType","busInfo":"busInfo","remark":"remark"}'
	 * @return
	 * @throws GlobalException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"sendMessage.do"})
	@Log(operation=Log.Operation.ADD,content="发送邮件",module="邮件管理")
	public  ModelAndView sendMessage(@RequestParam(value = "mailData", required = false)String mailData,
			@RequestParam(value = "smsData", required = false)String smsData) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		Map<String, String> sms = new HashMap<>();
		Map<String, String> mail = new HashMap<>();
		sms = JSONObject.fromObject(smsData);
		mail = JSONObject.fromObject(mailData);
		mav.addObject("smsData", sms);
		mav.addObject("mailData", mail);
		mav.setViewName(getViewPath()+"_message");
		return mav;
	}
	
	@RequestMapping(value={"editCfg.do"})
	public ModelAndView editCfg() throws GlobalException {
		ModelAndView mav = new ModelAndView();
		EmailCfgVo v=emailService.find(getCurrent().getUserId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_cfg_edit");
		return mav;
	}
	
	@RequestMapping(value="updateCfg.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新配置",module="邮件管理")
	public Status updateCfg(EmailCfgVo v) throws GlobalException {
		try {
			emailService.updateCfg(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
}
