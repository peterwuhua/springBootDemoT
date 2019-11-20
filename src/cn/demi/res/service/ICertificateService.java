package cn.demi.res.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.CertificateVo;

@Transactional
public interface ICertificateService extends IBaseService<CertificateVo> {

	void removeFile(CertificateVo v) throws GlobalException;
	
}
