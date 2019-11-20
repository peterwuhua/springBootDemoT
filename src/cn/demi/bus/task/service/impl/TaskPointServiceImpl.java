package cn.demi.bus.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.service.ITaskPointService;
import cn.demi.bus.task.vo.TaskPointVo;

@Service("bus.taskPointService")
public class TaskPointServiceImpl extends BaseServiceImpl<TaskPointVo,TaskPoint> implements
		ITaskPointService {
	@Autowired
	private ITaskPointDao taskPointDao;
	@Autowired
	private IImDao imDao;
	@Override
	public void delete(String... ids) throws GlobalException {
		List<TaskPoint> tpList=taskPointDao.listByIds(ids);
		for (TaskPoint tp : tpList) {
			imDao.deleteByBusId(tp.getId());
			taskPointDao.delete(tp);
		}
	}
}
