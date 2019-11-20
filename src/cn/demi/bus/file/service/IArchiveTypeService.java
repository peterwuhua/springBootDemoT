package cn.demi.bus.file.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.file.vo.ArchiveTypeVo;

@Transactional
public interface IArchiveTypeService extends IBaseService<ArchiveTypeVo> {
	
	/**
	 * Description : 查询文件名是否存在 <br>
	 * @param name
	 * @return boolean
	 * @throws GlobalException
	 */
	public boolean isExistName(String name,String id) throws GlobalException;
}
