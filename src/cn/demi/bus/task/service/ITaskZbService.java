package cn.demi.bus.task.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.init.std.vo.ItemMethodVo;

@Transactional
public interface ITaskZbService extends IBaseService<TaskVo> {
	
	
	List<ItemMethodVo> list4Sop(TaskVo v) throws GlobalException;
	/**
	 * 退回
	 * @param v
	 * @throws GlobalException
	 */
	void updateBack(TaskVo v) throws GlobalException;
}
