package cn.demi.doc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.doc.dao.ICategoryDao;
import cn.demi.doc.po.Category;
/**
 * Create on : 2016年11月24日 下午4:44:51  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Repository("doc.categoryDao")
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements ICategoryDao {

	@Override
	public List<Category> listChildByPath(String path) throws GlobalException {
		if (StrUtils.isEmpty(path))
			throw new GlobalException("无效的path " + path);

		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND path like '"+path+"%'");
		return list(jpql.toString());
	}
	
	@Override
	public void updateCategoryPath(String prePath,String path) throws GlobalException {
		List<Category> categoryList = listChildByPath(prePath);
		for(Category category:categoryList){
			category.setPath(path);
			super.update(category);
		}
		
	}

	@Override
	public List<Category> findByPid(String id) throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND pid = '"+id+"'");
		return list(jpql.toString());
	}
}
