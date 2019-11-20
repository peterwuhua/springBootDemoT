package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.ConsumableLogVo;

@Transactional
public interface IConsumableLogService extends IBaseService<ConsumableLogVo> {
	
	public List<ConsumableLogVo> listByConsumableId(String consumableId) throws GlobalException ;
}
