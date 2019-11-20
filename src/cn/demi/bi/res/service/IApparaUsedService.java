package cn.demi.bi.res.service;


import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.res.vo.ApparaUsedVo;

@Transactional
public interface IApparaUsedService extends IBaseService<ApparaUsedVo> {
	 
}
