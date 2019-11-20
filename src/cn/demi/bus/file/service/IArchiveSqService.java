package cn.demi.bus.file.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.file.vo.ArchiveVo;

@Transactional
public interface IArchiveSqService extends IBaseService<ArchiveVo> {
	/**
	 * 
	 * @Title: deleteArchive   
	 * @Description:删除归档申请信息以及它所有的归档文件  
	 * @param: @param id
	 * @param: @throws GlobalException      
	 * @return: void      
	 * @throws
	 */
	public void deleteArchive(String id) throws GlobalException;
}
