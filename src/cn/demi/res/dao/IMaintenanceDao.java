package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.Maintenance;

public interface IMaintenanceDao extends IBaseDao<Maintenance> {

	List<Maintenance> apparaMaintenanceListByAppId(String id);
	
}

