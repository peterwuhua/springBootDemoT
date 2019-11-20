package cn.demi.zk.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.zk.dao.IZkTaskDao;
import cn.demi.zk.po.ZkTask;

@Repository("zk.taskDao")
public class ZkTaskDaoImpl extends BaseDaoImpl<ZkTask> implements IZkTaskDao {
}
