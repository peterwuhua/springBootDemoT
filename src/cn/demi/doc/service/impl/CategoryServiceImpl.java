package cn.demi.doc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.doc.dao.ICategoryDao;
import cn.demi.doc.dao.IDocumentDao;
import cn.demi.doc.dao.IRoleDocumentDao;
import cn.demi.doc.po.Category;
import cn.demi.doc.po.Document;
import cn.demi.doc.po.RoleDocument;
import cn.demi.doc.service.ICategoryService;
import cn.demi.doc.vo.CategoryVo;
/**
 * Create on : 2016年11月24日 下午4:49:00  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Service("doc.categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<CategoryVo, Category>implements ICategoryService {

	@Autowired private ICategoryDao categoryDao;
	@Autowired private IDocumentDao documentDao;
	@Autowired private IRoleDocumentDao roleDocumentDao;
	
	@Override
	public void add(CategoryVo v) throws GlobalException {
		Category po = vo2Po(v);
		Category category = categoryDao.findById(v.getParentVo().getId());
		po.setParent(category);
		po.setTime(DateUtils.getCurrDateTimeStr());
		categoryDao.add(po);
		v.setId(po.getId());
	}
	
	@Override
	public void update(CategoryVo v) throws GlobalException {
		Category category = categoryDao.findById(v.getId());
		String prePath = category.getPath();
		String preName = category.getName();
		BeanUtils.copyProperties(v, category, new String[] { "id", "sort" });
		if (!v.getParentVo().getId().equals(category.getParent().getId())) {
			Category parent = categoryDao.findById(v.getParentVo().getId());
			category.setParent(parent);
		}
		category.setTime(DateUtils.getCurrDateTimeStr());
		if(!StrUtils.isNull(v.getSort())) {
			category.setSort(v.getSort());
		}
		categoryDao.update(category);
		
		if (!preName.equals(category.getName())) {
			documentDao.updateDocumentPath(prePath, category.getPath());// 修改path
			categoryDao.updateCategoryPath(prePath, category.getPath());// 修改path
			roleDocumentDao.updateRoleDocumentPath(prePath, category.getPath());// 修改path
		}
	}
	
	@Override
	public void update4Grid(CategoryVo v) throws GlobalException {
		Category category = categoryDao.findById(v.getId());
		category.setCode(v.getCode());
		category.setName(v.getName());
		category.setSort(v.getSort());
		categoryDao.update(category);
	}
	
	@Override
	public List<QueryCondition> toQueryList(CategoryVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("isDel", QueryCondition.EQ,Po.N));
		String pid = v.getPid();
		if(StrUtils.isBlankOrNull(pid)) 
			pid = findRoot().getId();
		queryConditions.add(new QueryCondition("parent.id", QueryCondition.EQ, null == pid ? "--" : pid));
		return queryConditions;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CategoryVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = categoryDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Category>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<CategoryVo> list() throws GlobalException {
		StringBuffer jpql = new StringBuffer("FROM "+ categoryDao.getEntityName(Category.class) + " WHERE isDel= "+Po.N);
		jpql.append(" ORDER BY sort ASC");
		List<Category> list=categoryDao.list(jpql.toString());
		return toVoList(list);
	}
	@Override
	public Map<String, Object> po2map(Category p) throws GlobalException {
		Map<String, Object> map =  super.po2map(p);
		List<Document> docList  = documentDao.listDocumentByCId(p.getId());
		List<Category> cateList  = categoryDao.findByPid(p.getId());
		map.put("isCate",!CollectionUtils.isEmpty(cateList));
		map.put("isDoc",!CollectionUtils.isEmpty(docList));
		return map;
	}
	
	@Override
	public void update2del(String... ids) throws GlobalException {
		for (Category p : categoryDao.listByIds(ids)) {
			p.setIsDel(Po.Y);
			categoryDao.update(p);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("path", QueryCondition.RK,p.getPath()));
			// 假删文件夹下子级文件夹
			List<Category> listChildDir = categoryDao.list(queryConditions);
			for (Category category : listChildDir) {
				category.setIsDel(Po.Y);
				categoryDao.update(category);
			}
			// 假删文件
			List<Document> listChildDoc = documentDao.list(queryConditions);
			for (Document document : listChildDoc) {
				document.setIsDel(Po.Y);
				documentDao.update(document);
			}
			// 真删授权文件信息
			List<RoleDocument> listChildFile = roleDocumentDao.list(queryConditions);
			roleDocumentDao.deleteAll(listChildFile);
		}
	}
	
	@Override
	public void data2Vo(String[] values, CategoryVo v, String param) throws GlobalException {
		v.setCode(values[1]);
		v.setName(values[2]);
		
		if(StrUtils.isNotBlankOrNull(values[3])){
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("name", QueryCondition.EQ,values[3]));
			List<Category> categoryList = categoryDao.list(queryConditions);
			if(!CollectionUtils.isEmpty(categoryList)){
				v.setParentVo(po2Vo(categoryList.get(0)));
			}else{
				throw new GlobalException("无效的文件夹名 " + values[3]);
			}
		}
		
		v.setDescribtion(values[4]);
	}

	@Override
	public boolean isExistName(String name,String id) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("name", QueryCondition.EQ,name));
		queryConditions.add(new QueryCondition("id", QueryCondition.NE,id));
		List<Category> pList = categoryDao.list(queryConditions);
		if(null!=pList && pList.size()>0){
			return true;
		}else{
			return false;
		}
	}
}
