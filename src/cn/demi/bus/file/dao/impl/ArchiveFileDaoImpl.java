package cn.demi.bus.file.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.file.dao.IArchiveFileDao;
import cn.demi.bus.file.po.ArchiveFile;

@Repository("bus.archiveFileDao")
public class ArchiveFileDaoImpl extends BaseDaoImpl<ArchiveFile> implements IArchiveFileDao {

	@Override
	public void upt4Del(String archiveId) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND archive.id= '"+archiveId+"'");
		List<ArchiveFile> fileList= list(jpql.toString());
		if(null!=fileList) {
			for (ArchiveFile archiveFile : fileList) {
				archiveFile.setIsDel(Po.Y);
				super.update(archiveFile);
			}
		}
	}

	@Override
	public List<ArchiveFile> listByArId(String id) throws GlobalException {
		if (StrUtils.isEmpty(id))
			throw new GlobalException("无效的档案Id " + id);

		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND archive.id = '"+id+"'");
		return list(jpql.toString());
	}
}
