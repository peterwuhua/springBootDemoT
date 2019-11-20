package cn.demi.bus.file.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.file.po.ArchiveType;

public interface IArchiveTypeDao extends IBaseDao<ArchiveType> {
	
	/**
	 * 获取样品及子集id集合
	 * @param id
	 * @return
	 */
	public List<String> findAllSubids(String id);
	/**
	 * Description :  <br>
	 * @param path
	 * @return
	 */
	public List<ArchiveType> listChildByPath(String path) throws GlobalException;
	
	/**
	 * Description :  <br>
	 * @param prePath
	 * @param path
	 * @throws GlobalException
	 */
	public void updatePath(String prePath,String path) throws GlobalException;
	/**
	 * Description : 根据文件夹Id查询此文件夹下子文件夹信息 <br>
	 * @param id
	 * @return List
	 * @throws GlobalException
	 */
	public List<ArchiveType> findByPid(String id) throws GlobalException;
}

