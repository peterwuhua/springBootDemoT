package cn.demi.doc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IRoleDao;
import cn.demi.base.system.po.Role;
import cn.demi.doc.dao.ICategoryDao;
import cn.demi.doc.dao.IDocumentDao;
import cn.demi.doc.dao.IRoleDocumentDao;
import cn.demi.doc.po.Document;
import cn.demi.doc.po.RoleDocument;
import cn.demi.doc.service.IFileService;
import cn.demi.doc.vo.DocumentVo;
/**
 * Create on : 2016年11月24日 下午4:49:46  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Service("doc.fileService")
public class FileServiceImpl extends BaseServiceImpl<DocumentVo,Document> implements IFileService {

	@Autowired private IRoleDocumentDao roleDocumentDao;
	@Autowired private IDocumentDao documentDao;
	@Autowired private IRoleDao roleDao;
	@Autowired private ICategoryDao categoryDao;

	@Override
	public void save(DocumentVo v) throws GlobalException {
		if(StrUtils.isBlankOrNull(v.getRoleIds())
				||StrUtils.isBlankOrNull(v.getId())){
			roleDocumentDao.deleteAll(roleDocumentDao.listByDocumentIds(v.getId()));
			return;
		}
		
		roleDocumentDao.deleteAll(roleDocumentDao.listByDocumentIds(v.getId()));
		Document document= documentDao.findById(v.getId());
		List<Role> roleList = roleDao.listByIds(v.getRoleIds());
		for (Role role : roleList) {
			RoleDocument roleDocument = new RoleDocument();
			roleDocument.setRole(role);
			roleDocument.setDocument(document);
			roleDocument.setCategory(document.getCategory());
			roleDocument.setPath(document.getCategory().getPath()+"/"+document.getName());
			roleDocument.setPerTime(DateUtils.getCurrDateStr());
			roleDocumentDao.add(roleDocument);
		}
		String roleNames = "";
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("document.id",QueryCondition.EQ,v.getId()));
		conditions.add(new QueryCondition("isDel",QueryCondition.EQ,Po.N));
		List<RoleDocument> roleDocuments = roleDocumentDao.list(conditions);
		if(!CollectionUtils.isEmpty(roleDocuments)){
			 for (int i=0,j=roleDocuments.size();i<j;i++) {
				 if(i!=0) roleNames +=",";
				RoleDocument roleDocument = roleDocuments.get(i);
				Role role = roleDocument.getRole();
				roleNames += role.getName();
			}
		}
		Document p = documentDao.findById(v.getId());
		p.setRoleNames(roleNames);
		documentDao.update(p);
	}
	
	@Override
	public List<QueryCondition> toQueryList(DocumentVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("category.isDel", QueryCondition.EQ,Po.N));
		String dirId = v.getDirIds();
		if(StrUtils.isBlankOrNull(dirId)) 
			dirId = categoryDao.findRoot().getId();
		queryConditions.add(new QueryCondition("category.id", QueryCondition.EQ, dirId));
		return queryConditions;
	}
	
	
}
