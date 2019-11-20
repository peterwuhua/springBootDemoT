package cn.demi.bus.sample.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.sample.vo.SampCydVo;

@Transactional
public interface ISampCydService extends IBaseService<SampCydVo> {
	
	/**
	 * 采样单 现场采样
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public SampCydVo find4Cyd(String id) throws GlobalException;

	
	
	
	/**
	 * 采样单 现场采样
	 * 文件
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public SampCydVo find4File(String id) throws GlobalException;
	/**
	 * 更新采样单
	 * @param v
	 * @throws GlobalException
	 */
	public void updateCyd(SampCydVo v) throws GlobalException;
	/**
	 * 更新采样单文件
	 * @param v
	 * @throws GlobalException
	 */
	public void updateFile(SampCydVo v) throws GlobalException;
 
}
