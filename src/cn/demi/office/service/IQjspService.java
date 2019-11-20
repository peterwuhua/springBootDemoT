package cn.demi.office.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.office.vo.QjglVo;

@Transactional
public interface IQjspService extends IBaseService<QjglVo> {
	
	public void updateAuditInfo(QjglVo vo) throws GlobalException;
	
	
}
