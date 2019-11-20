package cn.demi.bus.task.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.init.car.vo.CarUseVo;

@Transactional
public interface ITaskApService extends IBaseService<TaskVo> {
	
	/**
	 * 生成最新样品编号
	 * @param id 样品Id
	 * @param itemIds 项目Id
	 * @return
	 * @throws GlobalException
	 */
	public String findSampCode(String id,String itemIds) throws GlobalException;

	/**
	 * 获取未来3天默认空闲的车辆
	 * @return
	 * @throws GlobalException
	 */
	public List<CarUseVo> findDefinedCar() throws GlobalException;
	/**
	 * 更新已办信息
	 */
	public void updateYb(TaskVo v) throws GlobalException;
	/**
	 * 重置样品 信息
	 * 根据日期 点位
	 */
	public void update4Samp(TaskVo v) throws GlobalException;
	/**
	 * 重置样品 信息
	 * 强行重置
	 */
	public boolean update4SampAll(TaskVo v) throws GlobalException;
	/**
	 * 退回信息
	 */
	public void updateBack(TaskVo v) throws GlobalException;
	/**
	 * 安排编辑
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public TaskVo find(String id) throws GlobalException;
	/**
	 * 未来一周采样安排
	 * @return
	 * @throws GlobalException
	 */
	public Map<String, Object> listApPlan(String taskId) throws GlobalException;
	
	/**
	 * 安排编辑
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public TaskVo find4Batch(String id) throws GlobalException;
	
	
	/**
	 * 批量更新信息
	 */
	public void updateBatch(TaskVo v) throws GlobalException;
}
