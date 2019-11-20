package cn.demi.bus.test.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.test.vo.TaskItemVo;

@Transactional
public interface IItemCheckService extends IBaseService<TaskItemVo> {
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,TaskItemVo v) throws GlobalException;
}
