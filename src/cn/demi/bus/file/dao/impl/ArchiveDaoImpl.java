package cn.demi.bus.file.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.file.dao.IArchiveDao;
import cn.demi.bus.file.po.Archive;

@Repository("bus.archiveDao")
public class ArchiveDaoImpl extends BaseDaoImpl<Archive> implements IArchiveDao {

	@Override
	public List<Archive> listChildByPath(String path) throws GlobalException {
		if (StrUtils.isEmpty(path))
			throw new GlobalException("无效的path " + path);

		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND path like '"+path+"%'");
		return list(jpql.toString());
	}

	@Override
	public List<Archive> findByAll(String name) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND (title like '%"+name+"%'");
		jpql.append(" OR code like '%"+name+"%'");
		jpql.append(" OR describtion like '%"+name+"%'");
		jpql.append(" OR archiveType.name like '%"+name+"%'");
		jpql.append(" OR archiveType.code like '%"+name+"%' )");
		return list(jpql.toString());
	}

	@Override
	public List<Archive> listByTypeId(String id) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND archiveType.id ='"+id+"'");
		return list(jpql.toString());
	}
}
