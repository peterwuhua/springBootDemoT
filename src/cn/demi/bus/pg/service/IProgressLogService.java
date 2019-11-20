package cn.demi.bus.pg.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pg.vo.ProgressLogVo;

@Transactional
public interface IProgressLogService extends IBaseService<ProgressLogVo> {
	
	public List<ProgressLogVo> findList(String taskId,String busId)throws GlobalException;
	
	public ProgressLogVo findBack(String busId)throws GlobalException;
	
	public ProgressLogVo find(String busId,String status)throws GlobalException;
}
