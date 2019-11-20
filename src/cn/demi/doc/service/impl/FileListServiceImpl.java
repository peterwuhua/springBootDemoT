package cn.demi.doc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.po.Role;
import cn.demi.doc.dao.ICategoryDao;
import cn.demi.doc.dao.IDocumentDao;
import cn.demi.doc.dao.IRoleDocumentDao;
import cn.demi.doc.po.Document;
import cn.demi.doc.po.RoleDocument;
import cn.demi.doc.service.IFileListService;
import cn.demi.doc.vo.DocumentVo;
/**
 * Create on : 2016年11月24日 下午4:49:36  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Service("doc.filelistService")
public class FileListServiceImpl extends BaseServiceImpl<DocumentVo,Document> implements IFileListService {

//	@Autowired private IDocumentDao documentDao;
	@Autowired private IRoleDocumentDao roleDocumentDao;
	@Autowired private ICategoryDao categoryDao;
	@Autowired private IDocumentDao documentDao;
	
	@Override
	public List<QueryCondition> toQueryList(DocumentVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("category.isDel", QueryCondition.EQ,Po.N));
		String pid = v.getPid();
		if(StrUtils.isBlankOrNull(pid)) 
			pid = categoryDao.findRoot().getId();
		queryConditions.add(new QueryCondition("category.id", QueryCondition.EQ, null == pid ? "--" : pid));
		return queryConditions;
	}
	
	@Override
	public Map<String, Object> po2map(Document p) throws GlobalException {
		Map<String, Object> map =  super.po2map(p);
		List<RoleDocument> list =  roleDocumentDao.listRoleDocument(p.getId(),CurrentUtils.getCurrent().getRoleIds()); 
		map.put("isPer",!CollectionUtils.isEmpty(list));
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, DocumentVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		pageResult.addConditionList(toQueryList(v));
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult = documentDao.getPageResult(pageResult);
		List<DocumentVo> documentVoList = toVoList((List<Document>) pageResult.getResultList());
		gridVo.setDatas(documentVoList);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<DocumentVo> toVoList(List<Document> pList) throws GlobalException {
		List<DocumentVo> vList = new ArrayList<DocumentVo>();
		if(null==pList||pList.size()==0)
			return vList;
		
		for (Document document : pList) {
			String roleNames = "";
			String isPer ="";
			List<QueryCondition> conditions = new ArrayList<>();
			conditions.add(new QueryCondition("document.id",QueryCondition.EQ,document.getId()));
			conditions.add(new QueryCondition("isDel",QueryCondition.EQ,Po.N));
			 List<RoleDocument> roleDocuments = roleDocumentDao.list(conditions);
			 if(!CollectionUtils.isEmpty(roleDocuments)){
				 for (int i=0,j=roleDocuments.size();i<j;i++) {
					 if(i!=0) roleNames +=",";
					RoleDocument roleDocument = roleDocuments.get(i);
					Role role = roleDocument.getRole();
					roleNames += role.getName();
				}
				 isPer = "Y";
			 }else{
				 isPer = "N";
			 }
			DocumentVo documentVo = po2Vo(document);
			documentVo.setIsPer(isPer);
			documentVo.setRoleNames(roleNames);
			vList.add(documentVo);
		}
		return vList;
	}
	
	
}
