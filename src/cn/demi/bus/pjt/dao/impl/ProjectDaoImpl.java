package cn.demi.bus.pjt.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.pjt.dao.IProjectDao;
import cn.demi.bus.pjt.po.Project;

@Repository("bus.projectDao")
public class ProjectDaoImpl extends BaseDaoImpl<Project> implements IProjectDao {

	@Override
	public Project findByNo(String no) {
		String hql="FROM  "+getEntityName(Project.class)+" WHERE isDel='"+Po.N+"' AND no='"+no+"' ";
		return (Project) getEntityManager().createQuery(hql).setFirstResult(0).setMaxResults(1).getSingleResult();
	}
}
