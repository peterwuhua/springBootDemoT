package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.ReagentLogVo;

@Transactional
public interface IReagentLogService extends IBaseService<ReagentLogVo> {
	
	public List<ReagentLogVo> listByReagentId(String reagentId) throws GlobalException ;
}
