package cn.demi.doc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
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
import cn.demi.doc.service.IDocumentService;
import cn.demi.doc.vo.CategoryVo;
import cn.demi.doc.vo.DocumentVo;
/**
 * Create on : 2016年11月24日 下午4:49:27  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Service("doc.documentService")
public class DocumentServiceImpl extends BaseServiceImpl<DocumentVo,Document> implements IDocumentService {
	@Autowired private IDocumentDao documentDao;
	@Autowired private ICategoryDao categoryDao;
	@Autowired private IRoleDocumentDao roleDocumentDao;
	
	@Override
	public void add(DocumentVo v) throws GlobalException {
		Document po = vo2Po(v);
		if(null != v.getCategoryVo() && !v.getCategoryVo().getId().equals(po.getCategory().getId())){
			Category category = categoryDao.findById(v.getCategoryVo().getId());
			po.setCategory(category);
		}
		if(null != po.getCategory() && !StrUtils.isBlankOrNull(po.getName()) && !StrUtils.isBlankOrNull(po.getCategory().getPath())){
			po.setPath(po.getCategory().getPath()+"/"+po.getName());
		}
		po.setTime(DateUtils.getCurrDateTimeStr());
		documentDao.add(po);
		v.setId(po.getId());
	}
	
	
	@Override
	public void update(DocumentVo v) throws GlobalException {
		Document po = documentDao.findById(v.getId());
		BeanUtils.copyProperties(v, po, new String[] { "id" ,"sort","type","size"});
		if(null != v.getCategoryVo() && !v.getCategoryVo().getId().equals(po.getCategory().getId())){
			Category category = categoryDao.findById(v.getCategoryVo().getId());
			po.setCategory(category);
		}
		if(StrUtils.isNotBlankOrNull(v.getType())){
			po.setType(v.getType());
		}
		if(StrUtils.isNotBlankOrNull(v.getSize())){
			po.setSize(v.getSize());
		}
		if(null != po.getCategory() && !StrUtils.isBlankOrNull(po.getName()) && !StrUtils.isBlankOrNull(po.getCategory().getPath()) && !StrUtils.isBlankOrNull(v.getType())){
			po.setPath(po.getCategory().getPath()+"/"+po.getName()+v.getType());
		}
		if(null!=v.getSort()) {
			po.setSort(v.getSort());
		}
		documentDao.update(po);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Tab(GridVo gridVo, DocumentVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		pageResult.addConditionList(toQueryListDoc(v));
		
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult = documentDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Document>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, DocumentVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = documentDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Document>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(DocumentVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("state", QueryCondition.EQ,"现行"));
		queryConditions.add(new QueryCondition("category.isDel", QueryCondition.EQ,Po.N));
		String dirId = v.getDirIds();
		if(StrUtils.isBlankOrNull(dirId)) {
			dirId = categoryDao.findRoot().getId();
		}
		queryConditions.add(new QueryCondition("category.id", QueryCondition.EQ, dirId));
		return queryConditions;
	}
	
	/**
	 * Create on : Dave Yu 2016年12月1日 上午9:42:50 <br>
	 * Description : 按角色赋权二级页面点击树返回值方法  <br>
	 * @param v
	 * @return List
	 * @throws GlobalException
	 */
	public List<QueryCondition> toQueryListDoc(DocumentVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("category.isDel", QueryCondition.EQ,Po.N));
		String pid = v.getPid();
		if(StrUtils.isBlankOrNull(pid)) 
			pid = categoryDao.findRoot().getId();
		queryConditions.add(new QueryCondition("category.id", QueryCondition.EQ, null == pid ? "--" : pid));
		return queryConditions;
	}

	@Override
	public String getReplaceTheName(String id) throws GlobalException {
		String replaceName = null;
		if(!StrUtils.isBlankOrNull(id)){
			Document document = documentDao.findById(id);
			replaceName = document.getName();
		}
		return replaceName;
	}

	@Override
	public List<DocumentVo> getBereplaced(String id) throws GlobalException {
		List<Document>  pList = documentDao.findBereplaced(id);
		return toVoList(pList);
	}

	@Override
	public List<DocumentVo> listByDocId(String id) throws GlobalException {
		Document doc = documentDao.findById(id);
		List<Document> pList = new ArrayList<Document>();
		pList.add(doc);
		if(StrUtils.isNotBlankOrNull(doc.getReplaceId()))
			pList=findReplaceDocument(pList,doc.getReplaceId());
		return toVoList(pList);
	}
	/**
	 * Create on : Dave Yu 2016年12月1日 下午3:06:07 <br>
	 * Description : 递归取出更新文件  <br>
	 * @param pList
	 * @param id
	 * @return
	 */
	public List<Document> findReplaceDocument(List<Document> pList,String id){
			Document document = documentDao.findById(id);
			pList.add(document);
			if(StrUtils.isNotBlankOrNull(document.getReplaceId())){
				return findReplaceDocument(pList,document.getReplaceId());
			}else{
				return pList;
			}
	}
	
	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		pageType = pageType.replaceAll("[A-Z]", "_$0").toLowerCase(); //字符大写转“——”加小写
		List<String> poList = documentDao.selectByPageType(pageType);
		return poList;
	}
	
	@Override
	public List<DocumentVo> list(DocumentVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null != v.getType() && StringUtils.isNotBlank(v.getType()))
			queryList.add(new QueryCondition(" type IN ('"+v.getType().replace(",", "','")+"')"));
		return toVoList(documentDao.list(queryList));
	}
	
	@Override
	public void update2del(String... ids) throws GlobalException {
		for (Document p : documentDao.listByIds(ids)) {
			p.setIsDel(Po.Y);
			documentDao.update(p);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("document.id", QueryCondition.EQ, p.getId()));
			List<RoleDocument> listChildFile = roleDocumentDao.list(queryConditions);
			for (RoleDocument roleDocument : listChildFile) {
				roleDocumentDao.delete(roleDocument);
			}
		}
	}
	
	@Override
	public void data2Vo(String[] values, DocumentVo v, String param) throws GlobalException {
		v.setName(values[1]);
		v.setTitle(values[2]);
		
		if(StrUtils.isNotBlankOrNull(values[3])){
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("category.name", QueryCondition.EQ,values[3]));
			List<Category> categoryList = categoryDao.list(queryConditions);
			if(!CollectionUtils.isEmpty(categoryList)){
				CategoryVo categoryVo = new CategoryVo();
				v.setCategoryVo(categoryVo.toVo(categoryList.get(0)));
			}else{
				throw new GlobalException("无效的所属文件夹名 " + values[3]);
			}
		}
		
		v.setSign(values[4]);
		v.setIsAllVisible(values[5]);
		v.setDescribtion(values[6]);
		v.setState("现行");
	}
	@Override
	public void addUpdateFile(DocumentVo v) throws GlobalException{
		
		Document po = vo2Po(v);
		
		if(StrUtils.isNotBlankOrNull(v.getType())){
			po.setType(v.getType());
		}
		if(StrUtils.isNotBlankOrNull(v.getSize())){
			po.setSize(v.getSize());
		}
		
		if(null != v.getCategoryVo() && !v.getCategoryVo().getId().equals(po.getCategory().getId())){
			Category category = categoryDao.findById(v.getCategoryVo().getId());
			po.setCategory(category);
		}
		if(null != po.getCategory() && !StrUtils.isBlankOrNull(po.getName()) && !StrUtils.isBlankOrNull(po.getCategory().getPath())){
			po.setPath(po.getCategory().getPath()+"/"+po.getName());
		}
		po.setTime(DateUtils.getCurrDateTimeStr());
		documentDao.add(po);
		if("是".equals(v.getIsSync())){
			List<QueryCondition> conditions = new ArrayList<>();
			conditions.add(new QueryCondition("document.id",QueryCondition.EQ,v.getReplaceId()));
			conditions.add(new QueryCondition("isDel",QueryCondition.EQ,Po.N));
			List<RoleDocument> roleDocList = roleDocumentDao.list(conditions);
			for (RoleDocument roleDocument : roleDocList) {
				RoleDocument roleDoc = new RoleDocument();
				roleDoc.setRole(roleDocument.getRole());
				roleDoc.setDocument(po);
				roleDoc.setCategory(po.getCategory());
				roleDoc.setPath(po.getCategory().getPath()+"/"+po.getName());
				roleDoc.setPerTime(DateUtils.getCurrDateStr());
				roleDocumentDao.add(roleDoc);
			}
		}
		v.setId(po.getId());
	}


	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Select(GridVo gridVo, DocumentVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("state", QueryCondition.EQ,"现行"));
		queryConditions.add(new QueryCondition("category.isDel", QueryCondition.EQ,Po.N));
		if(null!=v.getCategoryVo()) {
			if(StrUtils.isNotBlankOrNull(v.getCategoryVo().getCode())) {
				queryConditions.add(new QueryCondition("category.code", QueryCondition.EQ, v.getCategoryVo().getCode()));
			}
		}
		if(StrUtils.isNotBlankOrNull(v.getSign())) {
			queryConditions.add(new QueryCondition("sign", QueryCondition.EQ,v.getSign()));
		}
		pageResult.addConditionList(queryConditions);
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult = documentDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Document>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
