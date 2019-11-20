package cn.demi.bi.task.service;


import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bi.task.vo.ObjVo;
import cn.demi.bus.test.vo.TaskItemVo;

@Transactional
public interface ITaskItemService extends IBaseService<TaskItemVo> {
	 
	/**
	 * 检测数量分析图表数据源
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public Map<String, Object> chartNum(TaskItemVo v) throws GlobalException;
	/**
	 * 检测结果分析图表数据源
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public Map<String, Object> chartValue(TaskItemVo v) throws GlobalException;
	/**
	 * 人员工作量
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<ObjVo> list4Uw(TaskItemVo v) throws GlobalException;
}
