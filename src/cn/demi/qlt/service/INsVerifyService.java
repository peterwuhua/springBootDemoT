package cn.demi.qlt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.vo.NsRecordVo;

@Transactional
public interface INsVerifyService extends IBaseService<NsRecordVo> {
	/**
	 * 已办查看 详情
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public NsRecordVo find4Show(String id) throws GlobalException;
}
