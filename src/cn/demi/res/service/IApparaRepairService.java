package cn.demi.res.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.res.vo.ApparaRepairVo;

@Transactional
public interface IApparaRepairService extends IBaseService<ApparaRepairVo> {
	
}
