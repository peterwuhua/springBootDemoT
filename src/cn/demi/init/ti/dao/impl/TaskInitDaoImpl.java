package cn.demi.init.ti.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.init.ti.dao.ITaskInitDao;
import cn.demi.init.ti.po.TaskInit;

@Repository("init.task_initDao")
public class TaskInitDaoImpl extends BaseDaoImpl<TaskInit> implements ITaskInitDao {
}
