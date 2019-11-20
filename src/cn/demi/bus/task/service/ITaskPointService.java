package cn.demi.bus.task.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.bus.task.vo.TaskPointVo;

@Transactional
public interface ITaskPointService extends IBaseService<TaskPointVo> {
	
}
