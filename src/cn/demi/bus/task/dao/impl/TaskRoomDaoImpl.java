package cn.demi.bus.task.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.bus.task.dao.ITaskRoomDao;
import cn.demi.bus.task.po.TaskRoom;

@Repository("bus.taskRoomDao")
public class TaskRoomDaoImpl extends BaseDaoImpl<TaskRoom> implements ITaskRoomDao {
}
