package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.CollectionUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.service.IOrgService;
import cn.demi.base.system.vo.OrgTreeVo;
import cn.demi.base.system.vo.OrgVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:12:28 </strong> <br>
 * <strong>Description : OrgServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.orgService")
public class OrgServiceImpl extends BaseServiceImpl<OrgVo, Org> implements
		IOrgService {

	@Autowired
	private IOrgDao orgDao;

	@Override
	public void add(OrgVo v) throws GlobalException {
		Org po = vo2Po(v);
		if(null != v.getParentVo() && StrUtils.isNotBlankOrNull(v.getParentVo().getId())){
			Org org = orgDao.findById(v.getParentVo().getId());
			po.setParent(org);
			if(null!=org) {
				v.setPid(org.getId());
			}
			po.setLevel(org.getLevel()+1);
		}else {
			po.setParent(null);
			po.setLevel(0);
		}
		orgDao.add(po);
		v.setId(po.getId());
	}

	@Override
	public void update(OrgVo v) throws GlobalException {
		Org org = orgDao.findById(v.getId());
		String oldPath = org.getPath();
		String oldName = org.getName();
		org.setName(v.getName());
		org.setCode(v.getCode());
		org.setType(v.getType());
		org.setSort(v.getSort());
		org.setDescribtion(v.getDescribtion());
		if(!v.getParentVo().getId().equals(org.getParent().getId())){
			Org parent = orgDao.findById(v.getParentVo().getId());
			org.setParent(parent);
		}
		String newPath = "";
		if(null != org.getParent()){
			org.setLevel(org.getParent().getLevel()+1);
			newPath=org.getPath()+"/"+v.getName();
		}else {
			newPath=v.getName();
		}
		if(!oldName.equals(v.getName())){
			orgDao.updatePath(oldPath,newPath);//修改path
		}
		if(!oldPath.equals(newPath)) {
			orgDao.updatePath(oldPath,newPath);//修改path
		}
		orgDao.update(org);
		v.setId(org.getId());
		if(null!=org.getParent()) {
			v.setPid(org.getParent().getId());
		}
	}
	@Override
	public void update4Grid(OrgVo v) throws GlobalException {
		Org org  = orgDao.findById(v.getId());
		org.setCode(v.getCode());
		org.setName(v.getName());
		org.setSort(v.getSort());
		orgDao.update(org);
		v.setId(org.getId());
	}
	
	@Override
	public List<QueryCondition> toQueryList(OrgVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		String pid = v.getPid();
		if(StrUtils.isBlankOrNull(pid)) 
			pid = findRoot().getId();
		queryConditions.add(new QueryCondition("parent.id", "=", pid));
		return queryConditions;
	}
	@Override
	public OrgVo po2Vo(Org p) throws GlobalException {
		OrgVo vo = super.po2Vo(p);
		if (null != p.getParent())
			vo.setPid(p.getParent().getId());
		return vo;
	}

	@Override
	public void update2del(String... ids) throws GlobalException {
		for(Org p:orgDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			orgDao.update(p);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("path", QueryCondition.RK,p.getPath()));
			List<Org> listChildNode= orgDao.list(queryConditions);
			for (Org org : listChildNode) {
				org.setIsDel(Po.Y);
				orgDao.update(org);
			}
		}
		
	}

	@Override
	public List<OrgVo> listByParentCode(String code) throws GlobalException{
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("parent.code",QueryCondition.EQ,code));
		conditions.add(new QueryCondition("parent.isDel",QueryCondition.EQ,Po.N));
		return super.list(conditions);
	}

	@Override
	public OrgVo listByCode(String code) throws GlobalException {
		if(null == orgDao.findByCode(code)) return null;
		return po2Vo(orgDao.findByCode(code));
	}
	@Override
	public List<OrgVo> treeList() throws GlobalException {
		List<OrgVo> list = new ArrayList<OrgVo>();
		StringBuffer jpql = new StringBuffer("FROM "+orgDao.getEntityName(Org.class)+"  WHERE isDel= "+Po.N+" AND parent is null");
		List<Org> pList=orgDao.list(jpql.toString());
		for (Org org : pList) {
			OrgVo v=new OrgVo();
			v=v.toVo(org);
			list.add(v);
			appendSubsetList(list, v.getId(),v.getId());
		}
		return list;
	}
	private List<OrgVo> appendSubsetList(List<OrgVo> allList,String pid,String currentTreeNodeId) throws GlobalException {
		List<OrgVo> tempList = listByPid(pid);
					
		if(!CollectionUtils.isEmpty(tempList)){
			for(OrgVo v:tempList){
				// 过滤当前树节点以及当前节点的子节点
				if(StringUtils.isNotBlank(currentTreeNodeId) && StringUtils.equalsIgnoreCase(currentTreeNodeId, v.getId())){
					continue;
				}
				allList.add(v);
				appendSubsetList(allList, v.getId(),currentTreeNodeId);
			}
		}
		return allList;
	}
	@Override
	public List<OrgVo> lists(String pId) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM "
				+ orgDao.getEntityName(Org.class) + " WHERE isDel= "+Po.N+"");
		if(pId==null) {
			jpql.append(" AND "+orgDao.getParentName()+" is null");
		}else {
			jpql.append(" AND "+orgDao.getParentName()+"='"+pId+"'");
		}
		jpql.append(" ORDER BY sort ASC");
		return toVoList(orgDao.list(jpql.toString()));
	}
	@Override
	public List<OrgVo> listChild(String pid) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM "
				+ orgDao.getEntityName(Org.class) + " WHERE isDel= "+Po.N+"");
		if(!StrUtils.isBlankOrNull(pid)) {
			List<String> ids=orgDao.listChild(pid);
			if(!pid.equals(orgDao.findRoot().getId())) {
				ids.add(pid);
			}
			jpql.append(" AND ("+orgDao.getParentName()+" in('"+String.join("','", ids)+"') or id in('"+String.join("','", ids)+"'))");
		}
		jpql.append(" ORDER BY code ASC");
		return toVoList(orgDao.list(jpql.toString()));
	}
	@Override
	public List<OrgVo> listChild(String pid,String type) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM "+ orgDao.getEntityName(Org.class) + " WHERE isDel= "+Po.N+"");
		if(!StrUtils.isBlankOrNull(pid)) {
			List<String> ids=orgDao.listChild(pid);
			if(!pid.equals(orgDao.findRoot().getId())) {
				ids.add(pid);
			}
			jpql.append(" AND ("+orgDao.getParentName()+" in('"+String.join("','", ids)+"') or id in('"+String.join("','", ids)+"'))");
		}
		jpql.append(" AND type='"+type+"'");
		jpql.append(" ORDER BY code ASC");
		return toVoList(orgDao.list(jpql.toString()));
	}
	@Override
	public OrgTreeVo find2JsonTree() throws GlobalException {
		Org p=orgDao.findRoot();
		OrgTreeVo treeVo=new OrgTreeVo();
		treeVo.setId(p.getId());
		treeVo.setName(p.getName());
		treeVo.setLeave(0);
		treeVo.setClassName("middle-level");
		treeVo.setChildren(subList(p.getId(),1));
		if(null!=treeVo.getChildren()) {
			treeVo.setTitle("部门（"+treeVo.getChildren().size()+"）");
		}
		return treeVo;
	}
	private List<OrgTreeVo> subList(String pid,int leave) throws GlobalException {
		List<Org> tempList = orgDao.listByPid(pid);
		List<OrgTreeVo> TreeList=null;
		if(!CollectionUtils.isEmpty(tempList)){
			TreeList=new ArrayList<OrgTreeVo>();
			for(Org org:tempList){
				OrgTreeVo treeVo=new OrgTreeVo();  
				treeVo.setId(org.getId());
				treeVo.setName(org.getName());
				treeVo.setLeave(leave);
				treeVo.setChildren(subList(org.getId(),leave+1));
				if(treeVo.getChildren()!=null) {
					int size=treeVo.getChildren().size();
					if(leave==1) {
						treeVo.setTitle("部门（"+size+"）");
						treeVo.setClassName("product-dept");
					}else if(leave==2) {
						treeVo.setTitle("团队（"+size+"）");
						treeVo.setClassName("pipeline1");
					}else if(leave==3) {
						treeVo.setTitle("班（"+size+"）");
						treeVo.setClassName("frontend1");
					}else if(leave==4) {
						treeVo.setTitle("组（"+size+"）");
						treeVo.setClassName("frontend1");
					}
				}else {
					if(leave==1) {
						treeVo.setClassName("product-dept");
					}else if(leave==2) {
						treeVo.setClassName("pipeline1");
					}else if(leave==3) {
						treeVo.setClassName("frontend1");
					}else if(leave==4) {
						treeVo.setClassName("frontend1");
					}
				}
				TreeList.add(treeVo);
			}
		}
		return TreeList;
	}

	@Override
	public List<OrgVo> lists(String pid, String type) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM "
				+ orgDao.getEntityName(Org.class) + " WHERE isDel= "+Po.N+"");
		if(pid==null) {
			jpql.append(" AND "+orgDao.getParentName()+" is null");
		}else {
			jpql.append(" AND "+orgDao.getParentName()+"='"+pid+"'");
		}
		jpql.append(" AND type='"+type+"'");
		jpql.append(" ORDER BY sort ASC");
		return toVoList(orgDao.list(jpql.toString()));
	}

	@Override
	public List<OrgVo> listByType(String type) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM "
				+ orgDao.getEntityName(Org.class) + " WHERE isDel= "+Po.N+"");
		jpql.append(" AND "+orgDao.getParentName()+" is not null");
		jpql.append(" AND type='"+type+"'");
		jpql.append(" ORDER BY sort ASC");
		return toVoList(orgDao.list(jpql.toString()));
	}
}
