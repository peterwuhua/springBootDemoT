package cn.demi.bus.file.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.file.vo.ArchiveFileVo;

@Transactional
public interface IArchiveFileService extends IBaseService<ArchiveFileVo> {
	
	/**
	 * 更新文件
	 * @param v
	 * @throws GlobalException
	 */
	public void update4File(ArchiveFileVo v) throws GlobalException;
}
