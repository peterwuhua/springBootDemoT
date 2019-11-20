package cn.demi.base.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.message.MessageUtils;
import cn.demi.base.system.dao.ICfgDao;
import cn.demi.base.system.po.Cfg;
import cn.demi.base.system.po.Email;
import cn.demi.base.system.service.IEmailService;
import cn.demi.base.system.vo.EmailCfgVo;
import cn.demi.base.system.vo.EmailVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:11:36 </strong> <br>
 * <strong>Description : EmailServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.emailService")
public class EmailServiceImpl extends BaseServiceImpl<EmailVo, Email> implements
		IEmailService {
	@Autowired
	private ICfgDao cfgDao;
	
	@Override
	public String saveMail(EmailVo v,MultipartFile[] file) throws GlobalException {
		String result = "";
		try {
			if(StrUtils.isBlankOrNull(v.getReceiver())) throw new  GlobalException("参数异常");
			String[] receivers = v.getReceiver().replaceAll("，", ",").split(",");
			for (String receiver : receivers) {
				v.setReceiver(receiver);
				super.save(v);
				Cfg cfg=cfgDao.find(getCurrent().getUserId(), Cfg.TYPE_EMAIL);
				if(null!=cfg) {
					result = MessageUtils.sendMail(cfg.getD1(), cfg.getD2(), cfg.getD3(), cfg.getD4(), v.getReceiver(),v.getSubject(),v.getContent(), file);
				}else {
					result = MessageUtils.sendMail4Customer(v.getReceiver(),v.getSubject(),v.getContent(), file);
				}
			}
			if("false".equals(result)){
				throw new  GlobalException("邮件发送失败,请尝试在系统管理中重新发送");
			}
		} catch (Exception e) {
			log.warn(e);
			throw new  GlobalException("邮件发送失败,请尝试在系统管理中重新发送");
		}
		return result;
	}

	@Override
	public EmailCfgVo find(String userId) throws GlobalException {
		Cfg cfg=cfgDao.find(userId, Cfg.TYPE_EMAIL);
		EmailCfgVo cfgVo=new EmailCfgVo();
		if(cfg!=null) {
			cfgVo.setHost(cfg.getD1());
			cfgVo.setFromMail(cfg.getD2());
			cfgVo.setPwd(cfg.getD3());
			cfgVo.setPort(cfg.getD4());
			cfgVo.setUserId(cfg.getUserId());
		}else {
			cfgVo=new EmailCfgVo();
			cfgVo.setUserId(userId);
		}
		return cfgVo;
	}

	@Override
	public void updateCfg(EmailCfgVo v) throws GlobalException {
		if(null!=v && !StrUtils.isBlankOrNull(v.getFromMail())) {
			Cfg cfg=cfgDao.find(v.getUserId(), Cfg.TYPE_EMAIL);
			if(null!=cfg) {
				cfg.setD1(v.getHost());
				cfg.setD2(v.getFromMail());
				cfg.setD3(v.getPwd());
				cfg.setD4(v.getPort());
				cfgDao.update(cfg);
			}else {
				cfg=new Cfg();
				cfg.setUserId(v.getUserId());
				cfg.setType(Cfg.TYPE_EMAIL);
				cfg.setD1(v.getHost());
				cfg.setD2(v.getFromMail());
				cfg.setD3(v.getPwd());
				cfg.setD4(v.getPort());
				cfgDao.add(cfg);
			}
		}
	}
	
}
