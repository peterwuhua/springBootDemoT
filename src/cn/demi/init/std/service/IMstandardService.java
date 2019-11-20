package cn.demi.init.std.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.std.vo.MstandardVo;

@Transactional
public interface IMstandardService extends IBaseService<MstandardVo> {
	
	public MstandardVo findByCode(String code) throws GlobalException;
}
