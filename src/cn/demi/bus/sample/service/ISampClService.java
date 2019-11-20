package cn.demi.bus.sample.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.sample.vo.SamplingVo;

@Transactional
public interface ISampClService extends IBaseService<SamplingVo> {
	
	 /**
	  * 获取任务样品信息
	  * @param taskId
	  * @return
	  * @throws GlobalException
	  */
	public List<SamplingVo> listByTaskId(String taskId) throws GlobalException;
	/**
	 * 获取任务样品信息
	 * @param taskId
	 * @param type 样品种类：普通样，质控样
	 * @return
	 * @throws GlobalException
	 */
	public List<SamplingVo> listByTaskId(String taskId,String type) throws GlobalException;
}
