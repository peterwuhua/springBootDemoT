package cn.demi.doc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.doc.dao.IRoleDocumentDao;
import cn.demi.doc.po.RoleDocument;
/**
 * Create on : 2016年11月24日 下午4:45:23  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Repository("doc.roleDocumentDao")
public class RoleDocumentDaoImpl extends BaseDaoImpl<RoleDocument> implements IRoleDocumentDao {
	
	@Override
	public List<RoleDocument> listByRoleIds(String... roleId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("role.id IN ('"
				+ toString(roleId).replace(",", "','") + "')"));
		return super.list(queryList);
	}

	@Override
	public List<RoleDocument> listByCategoryIds(String... categoryIds) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("category.id IN ('"
				+ toString(categoryIds).replace(",", "','") + "')"));
		return super.list(queryList);
	}

	@Override
	public List<RoleDocument> listByDocumentIds(String... documentIds) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("document.id IN ('"
				+ toString(documentIds).replace(",", "','") + "')"));
		return super.list(queryList);
	}

	@Override
	public String getRoleIdsByFileId(String... documentIds) throws GlobalException {
		StringBuffer jpql = new StringBuffer("SELECT GROUP_CONCAT( (p.role.id)) FROM "
				+ getEntityName(RoleDocument.class)+ " p WHERE p.document.id IN ('"+ toString(documentIds).replace(",", "','") + "')") ;
		return (String) query(jpql.toString()).getSingleResult();
	}

	@Override
	public String getRoleIdsByDirId(String... dirIds) throws GlobalException {
		StringBuffer jpql = new StringBuffer("SELECT GROUP_CONCAT( DISTINCT(p.role.id)) FROM "
				+ getEntityClazz()+ " p WHERE p.document.category.id IN ('"+ toString(dirIds).replace(",", "','") + "')") ;
		return (String) query(jpql.toString()).getSingleResult();
	}

	@Override
	public List<RoleDocument> listRoleDocument(String categoryIds) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND  category.id= '"+ toString(categoryIds)+"' ");
		// jpql.append(" OR document.id in ( select id from "+getEntityName(Document.class) +" where category.id= '"+ toString(categoryIds)+"'))");// hql语句默认连接inner join 所以写这方法
		return list(jpql.toString());
	}

	@Override
	public void updateRoleDocumentPath(String prePath, String path) throws GlobalException {
		List<RoleDocument> roleDocumentList = listChildByPath(prePath);
		for(RoleDocument roleDocument:roleDocumentList){
			roleDocument.setPath(roleDocument.getPath().replace(prePath, path));
			super.update(roleDocument);
		}
		
	}

	@Override
	public List<RoleDocument> listChildByPath(String path) throws GlobalException {
		if (StrUtils.isEmpty(path))
			throw new GlobalException("无效的path " + path);

		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND path like '"+path+"%'");
		return list(jpql.toString());
	}
	
	@Override
	public List<RoleDocument> listRoleDocument(String id,String roleIds) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
			queryList.add(new QueryCondition("role.id IN ('"+ toString(roleIds).replace(",", "','") + "')"));
		if(!StrUtils.isBlankOrNull(id))
			queryList.add(new QueryCondition("document.id IN ('"+ toString(id).replace(",", "','") + "')"));
		return super.list(queryList);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		String jpql = " select " + pageType + " from v_doc_role_document where is_del = 0 ";
		jpql += " group by " + pageType;
		return getEntityManager().createNativeQuery(jpql).getResultList();
	}

	@Override
	public List<RoleDocument> listByPerDirId(String id) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("perDirId = '"+ id + "'"));
		return super.list(queryList);
	}

	@Override
	public List<RoleDocument> listByRoleIdsAndPid(String pid, String roleId) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("role.id = '"+ roleId + "'"));
		queryList.add(new QueryCondition("category.id = '"+ pid + "'"));
		queryList.add(new QueryCondition("perDirId IS NULL"));
		return super.list(queryList);
	}
}
