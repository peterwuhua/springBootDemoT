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
import cn.demi.doc.po.Category;
import cn.demi.doc.po.Document;
import cn.demi.doc.po.RoleDocument;
import cn.demi.doc.service.IDirService;
import cn.demi.doc.vo.CategoryVo;
import cn.demi.doc.vo.RoleDocumentVo;
/**
 * Create on : 2016年11月24日 下午4:49:16  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Service("doc.dirService")
public class DirServiceImpl extends BaseServiceImpl<CategoryVo,Category> implements IDirService {

	@Autowired private IRoleDocumentDao roleDocumentDao;
	@Autowired private ICategoryDao categoryDao;
	@Autowired private IRoleDao roleDao;
	@Autowired private IDocumentDao documentDao;
	
	@Override
	public void save(RoleDocumentVo v) throws GlobalException {
		if(StrUtils.isBlankOrNull(v.getRoleIds())){
			roleDocumentDao.deleteAll(roleDocumentDao.listRoleDocument(v.getDirIds()));// 客户不想给角色赋权取消勾选删除赋权
			return;
		}
		
		roleDocumentDao.deleteAll(roleDocumentDao.listRoleDocument(v.getDirIds()));// 先删后赋
		Category tempCategory = categoryDao.findById(v.getDirIds());
		
		//文件夹组
		List<Category> categoryList = categoryDao.listChildByPath(tempCategory.getPath());
		//文件组
		List<Document> documentList = documentDao.listChildByPath(tempCategory.getPath());
		//角色组
		List<Role> roleList = roleDao.listByIds(v.getRoleIds());
		
		for (Role role : roleList) {
			//文件夹赋权
			for(Category category:categoryList){
				RoleDocument roleDocument = new RoleDocument();
				roleDocument.setRole(role);
				roleDocument.setCategory(category);
				roleDocument.setPath(category.getPath());
				roleDocument.setPerTime(DateUtils.getCurrDateStr());
				roleDocument.setPerDirId(category.getId());
				roleDocumentDao.add(roleDocument);
			}
			
			//文件赋权
			for(Document document:documentList){
				RoleDocument roleDocument = new RoleDocument();
				roleDocument.setRole(role);
				roleDocument.setDocument(document);
				roleDocument.setCategory(document.getCategory());
				roleDocument.setPath(document.getCategory().getPath()+"/"+document.getName());
				roleDocument.setPerTime(DateUtils.getCurrDateStr());
				roleDocumentDao.add(roleDocument);
			}
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
		for(Document document:documentList){
			document.setRoleNames(roleNames);
			documentDao.update(document);
		}
	}
	
	/**
	 * Create on : Dave Yu 2016年12月1日 下午7:09:44 <br>
	 * Description : (预留) 通过文件夹ID查询所有文件夹下的文件 Id<br>
	 * @param dirId
	 * @return String
	 * @throws GlobalException
	 */
	public String findDocumentIdBydirId(String dirId) throws GlobalException{
		 List<RoleDocument> pList = roleDocumentDao.listByCategoryIds(dirId);
		 List<RoleDocument> roleDocumentList = roleDocumentDao.listChildByPath( pList.get(0).getCategory().getPath());
		 String docIds = "";
		 for(int i = 0 , j = roleDocumentList.size(); i < j ; i++){
			 if(0 != i) docIds += ",";
			 if(null != roleDocumentList.get(i).getDocument()){
				 docIds += roleDocumentList.get(i).getDocument().getId();
			 }
		 }
		return docIds;
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

	
}
