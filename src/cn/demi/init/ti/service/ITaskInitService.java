package cn.demi.init.ti.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.ti.vo.TaskInitVo;

@Transactional
public interface ITaskInitService extends IBaseService<TaskInitVo> {
	
	/**
	 * 根据项目ids获取量化集合
	 * 采样安排 使用
	 * @param ids
	 * @return
	 * @throws GlobalException
	 */
	List<TaskInitVo> listByItemIds(String ids)throws GlobalException;
 
}
