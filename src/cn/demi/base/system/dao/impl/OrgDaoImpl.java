package cn.demi.base.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
/**
 * <strong>Create on : 2016年11月2日 下午5:07:10 </strong> <br>
 * <strong>Description : OrgDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.orgDao")
public class OrgDaoImpl extends BaseDaoImpl<Org> implements IOrgDao {

	@Override
	public Org findByCode(String code) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		queryList.add(new QueryCondition("code", QueryCondition.EQ, code));
		return super.query0(queryList, null);
	}
	@Override
	public void updatePath(String oldPath, String newPath) throws GlobalException{
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		queryList.add(new QueryCondition("path", QueryCondition.RK,oldPath));
		List<Org> listOrg = super.list(queryList);
		for (Org org : listOrg) {
			org.setPath(org.getPath().replace(oldPath,newPath));
			super.update(org);
		}
	}
	@Override
	public List<String> listChild(String pid) throws GlobalException {
		List<Org> list=super.listByPid(pid);
		List<String> subList=new ArrayList<String>();
		if(null!=list) {
			for (Org org : list) {
				subList.add(org.getId());
				subList.addAll(listChild(org.getId()));
			}
		}
		return subList;
	}
	@Override
	public Org findOrg(String orgId) throws GlobalException {
		Org org=super.findById(orgId);
		if(null!=org.getParent()&&!org.getParent().getId().equals(super.findRoot().getId())) {
			org=findOrg(org.getParent().getId());
		}
		return org;
	}
	@Override
	public Org findByName(String name) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		queryList.add(new QueryCondition("name", QueryCondition.AK, name));
		String hql="FROM "+getEntityName(Org.class)+" WHERE isDel="+Po.N+" AND name like '%"+name+"%' order by level asc ";
		List<Org> list=list(hql);
		if(null!=list &&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
}
