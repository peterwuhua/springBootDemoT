package cn.demi.bus.task.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.task.vo.TaskVo;

@Transactional
public interface ITaskXdService extends IBaseService<TaskVo> {
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,TaskVo v) throws GlobalException;
	/**
	 * 下达数据获取
	 * @param ids
	 * @return
	 * @throws GlobalException
	 */
	public TaskVo find(String ids)throws GlobalException;
}
