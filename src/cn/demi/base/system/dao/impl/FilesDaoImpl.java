package cn.demi.base.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.po.Files;

@Repository("sys.filesDao")
public class FilesDaoImpl extends BaseDaoImpl<Files> implements IFilesDao {

	@Override
	public List<Files> listByBusid(String busId) {
		String hql="FROM "+getEntityName(Files.class)+" WHERE isDel='"+Po.N+"' AND busId ='"+busId+"' ORDER BY sort asc";
		return super.list(hql);
	}
}
