package cn.demi.bus.task.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.common.page.PageResult;
import cn.demi.bus.task.po.Task;
/**
 * 任务持久层接口
 * @author QuJunLong
 *
 */
public interface ITaskDao extends IBaseDao<Task> {
	/**
	 * sql分页对象
	 * @param sql
	 * @param pageResult
	 * @return
	 */
	public PageResult queryBySql(String sql,PageResult pageResult);
	/**
	 * 更新任务进度
	 * @param id
	 */
	void updateSt(String id);
}

