package cn.demi.bus.task.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.task.vo.TaskVo;

@Transactional
public interface ITaskJjService extends IBaseService<TaskVo> {
	
	/**
	 * 编辑页面数据源
	 */
	public TaskVo find(String id) throws GlobalException;
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,TaskVo v) throws GlobalException;
	/**
	 * 查询编号是否重复，若不重复，则返回false
	 * 重复 true
	 * @param sampCode
	 * @return
	 * @throws GlobalException
	 */
	public boolean checkCode(String sampCode) throws GlobalException;
	/**
	 * 更新协议文件
	 * @param id
	 * @param sort
	 * @return
	 * @throws GlobalException
	 */
	boolean update4File(TaskVo v) throws GlobalException;
	/**
	 * 退回样品
	 * @param v
	 * @throws GlobalException
	 */
	void updateBack(TaskVo v) throws GlobalException;
}
