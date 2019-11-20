package cn.demi.bus.sample.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.sample.vo.SampContainerVo;

@Transactional
public interface ISampContainerService extends IBaseService<SampContainerVo> {
	
	/**
	 * 获取任务容器清单
	 * @param taskId
	 * @return
	 */
	public List<SampContainerVo> listByTaskId(String taskId)throws GlobalException;
	
}
