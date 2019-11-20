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
import cn.demi.doc.dao.IDocumentDao;
import cn.demi.doc.dao.IRoleDocumentDao;
import cn.demi.doc.po.Document;
import cn.demi.doc.po.RoleDocument;
import cn.demi.doc.service.IRoleService;
import cn.demi.doc.vo.RoleDocumentVo;
/**
 * Create on : 2016年11月24日 下午4:50:02  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Service("doc.roleService")
public class RoleServiceImpl extends BaseServiceImpl<RoleDocumentVo,RoleDocument> implements IRoleService {

	@Autowired private IRoleDocumentDao roleDocumentDao;
	@Autowired private IDocumentDao documentDao;
	@Autowired private IRoleDao roleDao;

	@Override
	public List<RoleDocumentVo> listByDocumentIds(String... documentIds) throws GlobalException {
		List<RoleDocument> pList = roleDocumentDao.listByDocumentIds(documentIds);
		return  toVoList(pList);
	}

	@Override
	public void save(RoleDocumentVo v) throws GlobalException {
		if(StrUtils.isBlankOrNull(v.getFileIds())){
			roleDocumentDao.deleteAll(roleDocumentDao.listByRoleIdsAndPid(v.getPid(),v.getRoleIds()));
			return;
		}
		roleDocumentDao.deleteAll(roleDocumentDao.listByRoleIdsAndPid(v.getPid(),v.getRoleIds()));
		List<Document> documentList = documentDao.listByIds(v.getFileIds());
		List<Role> roleList = roleDao.listByIds(v.getRoleIds());
		for (Role role : roleList) {
			for (Document document : documentList) {
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
		for (Document document : documentList) {
			document.setRoleNames(roleNames);
			documentDao.update(document);
		}
	}

	@Override
	public List<RoleDocumentVo> listByRoleIds(String... roleIds) throws GlobalException {
		return toVoList(roleDocumentDao.listByRoleIds(roleIds));
	}
	
	
}
