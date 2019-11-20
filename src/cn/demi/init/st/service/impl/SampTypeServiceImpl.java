package cn.demi.init.st.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;

@Service("init.samp_typeService")
public class SampTypeServiceImpl extends BaseServiceImpl<SampTypeVo,SampType> implements
		ISampTypeService {
	
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Override
	public void add(SampTypeVo v) throws GlobalException {
		SampType po = vo2Po(v);
		if(null != v.getParentVo() && StrUtils.isNotBlankOrNull(v.getParentVo().getId())){
			SampType sampType = sampTypeDao.findById(v.getParentVo().getId());
			po.setParent(sampType);
			po.setLevel(sampType.getLevel()+1);
			v.setPid(sampType.getId());
		}
		sampTypeDao.add(po);
		v.setId(po.getId());
	}

	@Override
	public void update(SampTypeVo v) throws GlobalException {
		SampType sampType = sampTypeDao.findById(v.getId());
		String oldPath = sampType.getPath();
		String oldName = sampType.getName();
		BeanUtils.copyProperties(v, sampType, new String[] {"id","createTime","level","lastUpdTime","version"});
		if(!v.getParentVo().getId().equals(sampType.getParent().getId())){
			SampType parent = sampTypeDao.findById(v.getParentVo().getId());
			sampType.setParent(parent);
		}
		sampType.setLevel(sampType.getParent().getLevel()+1);
		String newPath = "";
		if(null != sampType.getParent()){
			newPath=sampType.getPath()+"/"+v.getName();
		}else {
			newPath=v.getName();
		}
		if(!oldName.equals(v.getName())){
			sampTypeDao.updatePath(oldPath,newPath);//修改path
		}
		sampTypeDao.update(sampType);
		v.setId(sampType.getId());
		v.setPid(sampType.getParent().getId());
	}

	@Override
	public List<QueryCondition> toQueryList(SampTypeVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		String pid = v.getPid();
		if(StrUtils.isBlankOrNull(pid)) 
			pid = findRoot().getId();
		queryConditions.add(new QueryCondition("parent.id", "=", pid));
		return queryConditions;
	}
	@Override
	public SampTypeVo po2Vo(SampType p) throws GlobalException {
		SampTypeVo vo = super.po2Vo(p);
		if (null != p.getParent())
			vo.setPid(p.getParent().getId());
		return vo;
	}

	@Override
	public void update2del(String... ids) throws GlobalException {
		for(SampType p:sampTypeDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			sampTypeDao.update(p);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("path", QueryCondition.RK,p.getPath()));
			List<SampType> listChildNode= sampTypeDao.list(queryConditions);
			for (SampType sampType : listChildNode) {
				sampType.setIsDel(Po.Y);
				sampTypeDao.update(sampType);
			}
		}
	}

	@Override
	public List<SampTypeVo> tree(String rootId) throws GlobalException{
		List<SampTypeVo> list=this.treeList(rootId);
		list=this.generateTree(list, rootId);
		return list;
	}
	public List<SampTypeVo> generateTree(List<SampTypeVo> list, String rootId) {
        List<SampTypeVo> lists = new ArrayList<>();
        for (SampTypeVo t : list) {
            if(null!=t.getParentVo()&&null!=t.getParentVo().getId()
            		&&t.getParentVo().getId().equals(rootId)){
                List<SampTypeVo> childrenList = generateTree(list, t.getId());
                t.setChildren(childrenList);
                lists.add(t);
            }
        }
        return lists;
    }

	@Override
	public List<SampTypeVo> list(SampTypeVo v) throws GlobalException {
		StringBuffer hql=new StringBuffer("FROM "+sampTypeDao.getEntityName(SampType.class)+" WHERE isDel='"+Po.N+"'");
		hql.append(" order by sort asc");
		List<SampType> pList=sampTypeDao.list(hql.toString());
		return toVoList(pList);
	}

	@Override
	public List<SampTypeVo> treeData4Type(String name) throws GlobalException {
		String ids=sampTypeDao.find4Type(name);
		StringBuffer hql=new StringBuffer("FROM "+sampTypeDao.getEntityName(SampType.class)+" WHERE isDel='"+Po.N+"'");
		hql.append(" AND id in('"+ids.replaceAll(",", "','")+"')");
		hql.append(" order by sort asc");
		List<SampType> pList=sampTypeDao.list(hql.toString());
		return toVoList(pList);
	}

	@Override
	public String find4Type(String type) throws GlobalException {
		return sampTypeDao.find4Type(type);
	}

	@Override
	public SampTypeVo findByName(String name) throws GlobalException {
		StringBuffer hql=new StringBuffer("FROM "+sampTypeDao.getEntityName(SampType.class)+" WHERE isDel='"+Po.N+"'");
		hql.append(" AND name like '"+name+"' order by sort asc");
		List<SampType> pList=sampTypeDao.list(hql.toString());
		if(null!=pList && pList.size()>0) {
			return po2Vo(pList.get(0));
		}else {
			return null;
		}
	}

	@Override
	public List<SampTypeVo> listData(SampTypeVo v) throws GlobalException {
		StringBuffer hql=new StringBuffer("FROM "+sampTypeDao.getEntityName(SampType.class)+" WHERE isDel='"+Po.N+"'");
		if(StrUtils.isNotBlankOrNull(v.getPid())) {
			hql.append(" AND parent.id in('"+v.getPid().replace(",", "','")+"')");
		}
		if(StrUtils.isNotBlankOrNull(v.getIds())) {
			hql.append(" AND id in('"+v.getIds().replace(",", "','")+"')");
		}
		hql.append(" order by parent.sort,sort asc");
		List<SampType> pList=sampTypeDao.list(hql.toString());
		return toVoList(pList);
	}
	@Override
	public List<SampTypeVo> treeList(String rootId) throws GlobalException {
		List<SampTypeVo> list = new ArrayList<SampTypeVo>();
		if(StrUtils.isBlank(rootId))
			throw new GlobalException("pid 不能为空");
		return getSubsetList(list,rootId);
	}
	private List<SampTypeVo> getSubsetList(List<SampTypeVo> allList,String rootId) throws GlobalException {
		SampTypeVo v = findById(rootId);
		allList.add(v);
		List<SampTypeVo> tempList = listByPid(rootId);
		if(null!=tempList&&tempList.size()>0) {
			v.setHasChild(true);
			 for (SampTypeVo t : tempList) {
		           getSubsetList(allList, t.getId());
		     }
		}else {
			v.setHasChild(false);
		}
        return allList;
	}

	@Override
	public SampTypeVo find4SampType(String id) throws GlobalException {
		SampType sampType=sampTypeDao.findById(id);
		SampType p=getParent(sampType);
		SampTypeVo vo=new SampTypeVo();
		vo=vo.toVo(p);
		return vo;
	}
	
	public SampType getParent(SampType p) {
		if(p.getLevel()>1) {
			return getParent(p.getParent());
		}else {
			return p;
		}
	}
}
