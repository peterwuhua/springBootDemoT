package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.vo.NsVo;

@Transactional
public interface INsService extends IBaseService<NsVo> {
	
	/**
	 * 计划审核
	 * @param v
	 * @throws GlobalException
	 */
	public void update4Audit(NsVo v) throws GlobalException;
}
