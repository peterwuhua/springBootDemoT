package cn.demi.base.system.service;

import org.springframework.web.multipart.MultipartFile;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.EmailCfgVo;
import cn.demi.base.system.vo.EmailVo;
/**
 * <strong>Create on : 2016年11月2日 下午3:20:35 </strong> <br>
 * <strong>Description : 邮件service </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IEmailService extends IBaseService<EmailVo> {
	/**
	 * Create on : Paddy Zhang 2017年4月17日 下午1:10:18 <br>
	 * Description :发送邮件，并保存本地  <br>
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public String saveMail(EmailVo v,MultipartFile[] files) throws GlobalException;
	
	public EmailCfgVo find(String userId) throws GlobalException;
	
	public void updateCfg(EmailCfgVo v) throws GlobalException;
}
