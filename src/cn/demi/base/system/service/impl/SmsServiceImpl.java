package cn.demi.base.system.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.message.MessageUtils;
import cn.demi.base.system.po.Sms;
import cn.demi.base.system.service.ISmsService;
import cn.demi.base.system.vo.SmsVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:12:52 </strong> <br>
 * <strong>Description : SmsServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.smsService")
public class SmsServiceImpl extends BaseServiceImpl<SmsVo, Sms> implements
		ISmsService {
	@Override
	public void saveSms(SmsVo v) throws GlobalException {
		try {
			super.save(v);
			String result = MessageUtils.sendSms(v.getContent(),v.getReceiver());
			if(result.contains("发送失败"))throw new GlobalException("短信发送失败，请尝试重新发送");
		} catch (Exception e) {
			throw new GlobalException("短信发送失败，请在短信功能中尝试重新发送");
		}
	}
	
}
