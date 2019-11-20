package cn.demi.bus.sample.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.sample.vo.SamplingVo;

@Transactional
public interface ISamplingService extends IBaseService<SamplingVo> {
	
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
	/**
	 * 删除样品信息
	 * @param sampId
	 * @return
	 * @throws GlobalException
	 */
	boolean deleteSamp(String sampId) throws GlobalException;
	/**
	 * 根据Id复制样品信息
	 * @param sampId
	 * @return
	 * @throws GlobalException
	 */
	boolean copySamp(String sampId) throws GlobalException;
	/**
	 * 更新样品处理信息
	 * @param v
	 * @throws GlobalException
	 */
	public void update4Deal(SamplingVo v) throws GlobalException;
	
	
	/**
	 * 
	 * @Title: listSampsByTaskId   
	 * @Description:   
	 * @param: @param taskId
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: List<SamplingVo>      
	 * @throws
	 */
	public List<SamplingVo> listSampsByTaskId(String taskId) throws GlobalException;
	/**
	 * 新增样品
	 * @param pointId 点位ID
	 * @param type 质控类型 null或空为普通样
	 * @param ids 项目ids
	 * @return
	 * @throws GlobalException
	 */
	public boolean addSamp(String pointId,String type,String ids) throws GlobalException;
	/**
	 *删除样品
	 */
	public boolean delSamp(String pointId,String type) throws GlobalException;
	
}
