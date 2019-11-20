package cn.demi.base.system.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.base.system.po.Files;

public interface IFilesDao extends IBaseDao<Files> {
	
	/**
	 * 根据业务Id获取数据集合
	 * @param busId
	 * @return
	 */
	List<Files> listByBusid(String busId);
}

