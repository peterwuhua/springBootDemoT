package cn.demi.bi.res.service;


import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.res.vo.EquipmentUsedVo;

@Transactional
public interface IEquipmentUsedService extends IBaseService<EquipmentUsedVo> {
	 
}
