package cn.demi.base.system.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.FilesVo;

@Transactional
public interface IFilesService extends IBaseService<FilesVo> {
	
	public List<FilesVo> listByBusId(String busId) throws GlobalException;
	/**
	 * 获取原始记录单文件
	 * @param busId
	 * @return
	 * @throws GlobalException
	 */
	public List<FilesVo> listTemp(String busId) throws GlobalException;
	
	public List<FilesVo> listByBusId(String busId,String busType) throws GlobalException;
	/**
	 * 根据上传人Id获取集合
	 * @param busId
	 * @param busType
	 * @return
	 * @throws GlobalException
	 */
	public List<FilesVo> listByUserId(String busId,String userId) throws GlobalException;
	
	public void saveFiles(List<FilesVo> filesList) throws GlobalException;
	
	public void deleteByBusId(String busId) throws GlobalException;
	
	public void deleteById(String id) throws GlobalException;
}
