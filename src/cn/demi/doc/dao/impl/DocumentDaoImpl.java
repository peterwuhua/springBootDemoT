package cn.demi.doc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.doc.dao.IDocumentDao;
import cn.demi.doc.po.Document;
/**
 * Create on : 2016年11月24日 下午4:45:08  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Repository("doc.documentDao")
public class DocumentDaoImpl extends BaseDaoImpl<Document> implements IDocumentDao {
	@Override
	public List<Document> listChildByPath(String path) throws GlobalException {
		if (StrUtils.isEmpty(path))
			throw new GlobalException("无效的path " + path);

		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND path like '"+path+"%'");
		return list(jpql.toString());
	}

	@Override
	public void updateDocumentPath(String prePath,String path) throws GlobalException{
		List<Document> docList = listChildByPath(prePath);
		for(Document doc:docList){
			doc.setPath(doc.getPath().replace(prePath, path));
			super.update(doc);
		}
		
	}

	@Override
	public List<Document> findBereplaced(String id) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE replaceId = '"+id+"'");
		return list(jpql.toString());
	}

	@Override
	public List<Document> findByAll(String name) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND isAllVisible = '是'");
		jpql.append(" AND (name like '%"+name+"%'");
		jpql.append(" OR size like '%"+name+"%'");
		jpql.append(" OR title like '%"+name+"%'");
		jpql.append(" OR sign like '%"+name+"%'");
		jpql.append(" OR type like '%"+name+"%'");
		jpql.append(" OR describtion like '%"+name+"%'");
		jpql.append(" OR category.name like '%"+name+"%'");
		jpql.append(" OR category.code like '%"+name+"%'");
		jpql.append(" OR state like '%"+name+"%')");
		return list(jpql.toString());
	}

	@Override
	public List<Document> listDocumentByCId(String id) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND category.id ='"+id+"'");
		return list(jpql.toString());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		String jpql = " select " + pageType + " from v_doc_document where is_del = 0 ";
		jpql += " group by " + pageType;
		return getEntityManager().createNativeQuery(jpql).getResultList();
	}
}
