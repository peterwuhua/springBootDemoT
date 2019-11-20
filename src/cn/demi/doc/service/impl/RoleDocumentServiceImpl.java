package cn.demi.doc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.demi.doc.dao.IRoleDocumentDao;
import cn.demi.doc.po.RoleDocument;
import cn.demi.doc.service.IRoleDocumentService;
import cn.demi.doc.vo.RoleDocumentVo;
/**
 * Create on : 2016年11月24日 下午4:49:54  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Service("doc.roleDocumentService")
public class RoleDocumentServiceImpl extends BaseServiceImpl<RoleDocumentVo,RoleDocument> implements IRoleDocumentService {

	@Autowired private IRoleDocumentDao roleDocumentDao;

	@Override
	public List<RoleDocumentVo> listByDocumentIds(String... documentIds) throws GlobalException {
		List<RoleDocument> pList = roleDocumentDao.listByDocumentIds(documentIds);
		return  toVoList(pList);
	}
	
	@Override
	public List<RoleDocumentVo> listByCategoryIds(String... categoryIds) throws GlobalException {
		List<RoleDocument> pList = roleDocumentDao.listByCategoryIds(categoryIds);
		return  toVoList(pList);
	}


	@Override
	public List<RoleDocumentVo> listByRoleIds(String... roleIds) throws GlobalException {
		return toVoList(roleDocumentDao.listByRoleIds(roleIds));
	}

	@Override
	public String getRoleIdsByFileId(String... documentIds) throws GlobalException {
		return roleDocumentDao.getRoleIdsByFileId(documentIds);
	}

	@Override
	public String getRoleIdsByDirId(String... dirIds) throws GlobalException {
		return roleDocumentDao.getRoleIdsByDirId(dirIds);
	}
	
	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		pageType = pageType.replaceAll("[A-Z]", "_$0").toLowerCase(); //字符大写转“——”加小写
		List<String> poList = roleDocumentDao.selectByPageType(pageType);
		return poList;
	}
	
	@Override
	public List<RoleDocumentVo> list(RoleDocumentVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(null != v.getStartDate() && StringUtils.isNotBlank(v.getStartDate())){
			queryList.add(new QueryCondition("perTime >= '"+v.getStartDate()+"'"));
			if(null != v.getEndDate() && StringUtils.isNotBlank(v.getEndDate())){
				queryList.add(new QueryCondition("perTime <= '"+v.getEndDate()+"'"));
			}else{
				queryList.add(new QueryCondition("perTime <= '"+DateUtils.getCurrDateStr()+"'"));
			}
		}
		return toVoList(roleDocumentDao.list(queryList));
	}

	@Override
	public List<RoleDocumentVo> listByPerDirId(String id) throws GlobalException {
		return  toVoList(roleDocumentDao.listByPerDirId(id));
	}
}
