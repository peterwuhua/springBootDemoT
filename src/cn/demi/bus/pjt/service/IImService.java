package cn.demi.bus.pjt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.ImVo;

@Transactional
public interface IImService extends IBaseService<ImVo> {
 
	 
	/**
	 * 获取项目方法集合
	 * @param busId 业务Id
	 */
	List<ImVo> list4Im(String busId) throws GlobalException;
	
	 
	/**
	 * 获取任务的方法集合
	 * @param busId 业务Id
	 */
	List<ImVo> list4Method(String busId) throws GlobalException;
}
