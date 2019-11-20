package cn.demi.bus.task.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.bus.task.po.TaskRoom;
import cn.demi.bus.task.service.ITaskRoomService;
import cn.demi.bus.task.vo.TaskRoomVo;

@Service("bus.taskRoomService")
public class TaskRoomServiceImpl extends BaseServiceImpl<TaskRoomVo,TaskRoom> implements
		ITaskRoomService {
}
