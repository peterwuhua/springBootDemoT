package cn.demi.bi.task.service;


import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.task.vo.TaskVo;

@Transactional
public interface ITaskService extends IBaseService<TaskVo> {
	/**
	 * chart 任务统计图
	 * @param TaskVo
	 * @return
	 * @throws GlobalException
	 */
	public int[] chart(TaskVo v) throws GlobalException;
	/**
	 * chart 费用统计图
	 * @param TaskVo
	 * @return
	 * @throws GlobalException
	 */
	public Map<String, Object> chartFy(TaskVo v) throws GlobalException;
	/**
	 * chartPie首页饼图
	 * @param TaskVo
	 * @return
	 * @throws GlobalException
	 */
	public Map<String, Object> chartPie(TaskVo v) throws GlobalException;

}
