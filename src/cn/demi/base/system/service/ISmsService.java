package cn.demi.base.system.service;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.SmsVo;
/**
 * <strong>Create on : 2016年11月2日 下午3:27:26 </strong> <br>
 * <strong>Description : 短信service </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface ISmsService extends IBaseService<SmsVo> {
	/**
	 * Create on : Paddy Zhang 2017年4月21日 下午4:57:33 <br>
	 * Description : 发送短信 <br>
	 * @param v
	 * @throws GlobalException
	 */
	public void saveSms(SmsVo v) throws GlobalException;
}
