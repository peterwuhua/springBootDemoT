package cn.demi.res.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.res.vo.MaintenanceVo;

@Transactional
public interface IMaintenanceService extends IBaseService<MaintenanceVo> {
	
}
