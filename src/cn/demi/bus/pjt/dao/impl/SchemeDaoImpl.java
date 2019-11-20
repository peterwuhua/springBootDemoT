package cn.demi.bus.pjt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.po.Scheme;
/**
 * 方案 数据持久层
 * @author QuJunLong
 */
@Repository("bus.schemeDao")
public class SchemeDaoImpl extends BaseDaoImpl<Scheme> implements ISchemeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Scheme> listByPjId(String pjId) {
		String hql="FROM  "+getEntityName(Scheme.class)+" WHERE project.id='"+pjId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@Override
	public Scheme find4Zsy(String pjId) {
		String hql="FROM  "+getEntityName(Scheme.class)+" WHERE project.id='"+pjId+"' order by sort asc";
		@SuppressWarnings("unchecked")
		List<Scheme> schemeList=getEntityManager().createQuery(hql).getResultList();
		if(null!=schemeList&&schemeList.size()>0) {
			return schemeList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public void deleteByProjectId(String pjId) {
		String hql="DELETE FROM  "+getEntityName(Scheme.class)+" WHERE project.id='"+pjId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}
}
