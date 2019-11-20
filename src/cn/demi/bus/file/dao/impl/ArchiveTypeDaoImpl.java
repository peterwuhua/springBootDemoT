package cn.demi.bus.file.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.file.dao.IArchiveTypeDao;
import cn.demi.bus.file.po.ArchiveType;

@Repository("bus.archiveTypeDao")
public class ArchiveTypeDaoImpl extends BaseDaoImpl<ArchiveType> implements IArchiveTypeDao {

	@Override
	public List<ArchiveType> listChildByPath(String path) throws GlobalException {
		if (StrUtils.isEmpty(path))
			throw new GlobalException("无效的path " + path);

		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND path like '"+path+"%'");
		return list(jpql.toString());
	}

	@Override
	public void updatePath(String prePath, String path) throws GlobalException {
		List<ArchiveType> categoryList = listChildByPath(prePath);
		for(ArchiveType category:categoryList){
			String oldPath=category.getPath();
			oldPath=oldPath.replace(prePath, path);
			category.setPath(oldPath);
			super.update(category);
		}
	}

	@Override
	public List<ArchiveType> findByPid(String id) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND parent.id = '"+id+"'");
		return list(jpql.toString());
	}
	@Override
	public List<String> findAllSubids(String id) {
		String hql="FROM "+getEntityName(ArchiveType.class)+" WHERE isDel='"+Po.N+"' AND parent.id='"+id+"'";
		List<ArchiveType> list = super.list(hql);
		Set<String> idSet=new HashSet<String>();
		if(null!=list) {
			for (ArchiveType st : list) {
				idSet.add(st.getId());
				List<String> sub=allSub(st.getId());
				if(null!=sub) {
					idSet.addAll(sub);
				}
			}
		}
		List<String> idList=new ArrayList<>();
		idList.add(id);
		idList.addAll(idSet);
		return idList;
	}
	public List<String> allSub(String pid){
		String hql="FROM "+getEntityName(ArchiveType.class)+" WHERE isDel='"+Po.N+"' AND parent.id='"+pid+"'";
		List<ArchiveType> list = super.list(hql);
		List<String> ids=null;
		if(null!=list&&list.size()>0) {
			ids=new ArrayList<String>();
			for (ArchiveType st : list) {
				ids.add(st.getId());
				List<String> sub=allSub(st.getId());
				if(null!=sub) {
					ids.addAll(sub);
				}
			}
		}
		return ids;
	}
}
