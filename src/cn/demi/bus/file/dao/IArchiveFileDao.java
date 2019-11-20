package cn.demi.bus.file.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.file.po.ArchiveFile;

public interface IArchiveFileDao extends IBaseDao<ArchiveFile> {
	
	/**
	 * 根据档案id删除文件集
	 * @param archiveId
	 * @throws GlobalException
	 */
	public void upt4Del(String archiveId) throws GlobalException;
	/**
	 * 根据档案id获取文件集合
	 * @param id
	 * @return
	 */
	public List<ArchiveFile> listByArId(String id)throws GlobalException;
}

