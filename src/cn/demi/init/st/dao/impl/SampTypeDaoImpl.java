package cn.demi.init.st.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;

@Repository("init.samp_typeDao")
public class SampTypeDaoImpl extends BaseDaoImpl<SampType> implements ISampTypeDao {

	@Override
	public void updatePath(String oldPath, String newPath) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("isDel", QueryCondition.EQ, Po.N));
		queryList.add(new QueryCondition("path", QueryCondition.RK,oldPath));
		List<SampType> listOrg = super.list(queryList);
		for (SampType sampType : listOrg) {
			sampType.setPath(sampType.getPath().replace(oldPath,newPath));
			super.update(sampType);
		}
		
	}

	@Override
	public String findAllIds(String id) {
		SampType type=super.findById(id);
		id=findIds(type.getParent(), id);
		return id;
	}
	
	public String findIds(SampType type,String id) {
		if(null!=type&&type.getLevel()>0) {
			id+=","+type.getId();
			id=findIds(type.getParent(), id);
		}
		return id;
	}

	@Override
	public String find4Type(String name) {
		String hql="FROM "+getEntityName(SampType.class)+" WHERE isDel='"+Po.N+"' AND name like '%"+name+"%'";
		List<SampType> listOrg = super.list(hql);
		Set<String> idSet=new HashSet<String>();
		if(null!=listOrg) {
			for (SampType st : listOrg) {
				idSet.add(st.getId());
				List<String> sub=allSub(st.getId());
				if(null!=sub) {
					idSet.addAll(sub);
				}
			}
		}
		return String.join(",", idSet);
	}
	
	public List<String> allSub(String pid){
		String hql="FROM "+getEntityName(SampType.class)+" WHERE isDel='"+Po.N+"' AND parent.id='"+pid+"'";
		List<SampType> listOrg = super.list(hql);
		List<String> ids=null;
		if(null!=listOrg&&listOrg.size()>0) {
			ids=new ArrayList<String>();
			for (SampType st : listOrg) {
				ids.add(st.getId());
				List<String> sub=allSub(st.getId());
				if(null!=sub) {
					ids.addAll(sub);
				}
			}
		}
		return ids;
	}

	@Override
	public List<SampType> listByType(String ids, String type) {
		String hql="FROM "+getEntityName(SampType.class)+" WHERE isDel='"+Po.N+"' AND id in('"+ids.replace(",", "','")+"') AND type like '"+type+"'";
		return super.list(hql);
	}

	@Override
	public List<String> findAllPids(String id) {
		List<String> plist=new ArrayList<String>();
		SampType type=super.findById(id);
		plist.add(type.getId());
		plist.addAll(findPIds(type.getParent()));
		return plist;
	}
	public List<String> findPIds(SampType type) {
		List<String> list=new ArrayList<String>();
		if(null!=type&&type.getLevel()>0) {
			list.add(type.getId());
			list.addAll(findPIds(type.getParent()));
		}
		return list;
	}

	@Override
	public List<String> findAllSubids(String id) {
		String hql="FROM "+getEntityName(SampType.class)+" WHERE isDel='"+Po.N+"' AND parent.id='"+id+"'";
		List<SampType> listOrg = super.list(hql);
		Set<String> idSet=new HashSet<String>();
		if(null!=listOrg) {
			for (SampType st : listOrg) {
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

	@Override
	public SampType findByName(String name) {
		String hql="FROM "+getEntityName(SampType.class)+" WHERE isDel='"+Po.N+"' AND name like '"+name+"'";
		List<SampType> list = super.list(hql);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else{
			return null;
		}
	}
}
