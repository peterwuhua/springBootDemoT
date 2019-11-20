package cn.demi.bus.file.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.file.po.Archive;

public interface IArchiveDao extends IBaseDao<Archive> {

	/**
	 * Description :  <br>
	 * @param path
	 * @return
	 * @throws GlobalException
	 */
	public List<Archive> listChildByPath(String path) throws GlobalException;
	/**
	 * Description : 根据名字查询文件数据 <br>
	 * @param name
	 * @return List
	 * @throws GlobalException
	 */
	public List<Archive> findByAll(String name) throws GlobalException;
	/**
	 * Description : 根据文件夹Id查询该文件夹下的文件信息 <br>
	 * @return 
	 * @throws GlobalException
	 */
	public List<Archive> listByTypeId(String id) throws GlobalException;
}

