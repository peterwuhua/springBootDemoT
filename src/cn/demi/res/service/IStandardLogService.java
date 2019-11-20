package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.StandardLogVo;

@Transactional
public interface IStandardLogService extends IBaseService<StandardLogVo> {
	
	public List<StandardLogVo> listByStandardId(String standardId) throws GlobalException ;
}
