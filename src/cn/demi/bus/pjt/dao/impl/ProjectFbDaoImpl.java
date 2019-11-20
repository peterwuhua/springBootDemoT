package cn.demi.bus.pjt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.pjt.dao.IProjectFbDao;
import cn.demi.bus.pjt.po.ProjectFb;

@Repository("projectFbDao")
public class ProjectFbDaoImpl extends BaseDaoImpl<ProjectFb> implements IProjectFbDao {
	
	@Override
	public void deleteByProjectId(String pjId) {
		String hql="DELETE FROM  "+getEntityName(ProjectFb.class)+" WHERE project.id='"+pjId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectFb> listByProjectId(String pjId) {
		String hql="FROM  "+getEntityName(ProjectFb.class)+" WHERE isDel='"+Po.N+"' AND project.id='"+pjId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}
}
