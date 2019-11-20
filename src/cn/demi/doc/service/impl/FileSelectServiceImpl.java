package cn.demi.doc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.doc.dao.IDocumentDao;
import cn.demi.doc.dao.IRoleDocumentDao;
import cn.demi.doc.po.Document;
import cn.demi.doc.po.RoleDocument;
import cn.demi.doc.service.IFileSelectService;
import cn.demi.doc.vo.DocumentVo;
/**
 * Create on : 2016年11月24日 下午4:49:46  <br>
 * Description : 接口实现类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
@Service("doc.fileselectService")
public class FileSelectServiceImpl extends BaseServiceImpl<DocumentVo,Document> implements IFileSelectService {
	@Autowired private IDocumentDao documentDao;
	@Autowired private IRoleDocumentDao roleDocumentDao;

	@Override
	public List<DocumentVo> findByAll(String name) throws GlobalException {
		Map<String, Document> map = new HashMap<String, Document>();
		List<Document> pList = documentDao.findByAll(name);
		// 如果集合不为空  开始比对 遍历
		if(!CollectionUtils.isEmpty(pList)){
			//拼id查询已授权对象
			String ids = "";
			for (int i = 0, j = pList.size(); i < j; i++) {
				if (0 != i)
					ids += ",";
				ids += pList.get(i).getId();
			}
			List<RoleDocument> list = roleDocumentDao.listRoleDocument(ids, CurrentUtils.getCurrent().getRoleIds());
			// 第二次拼已授权id为下面循环做对比
			for (int i = 0, j = list.size(); i < j; i++) {
				if (0 != i)
					ids += ",";
				ids += list.get(i).getId();
				map.put(list.get(i).getDocument().getId(), null);
			}
			//有授权文件时循环
			if (!CollectionUtils.isEmpty(list)) {
				List<DocumentVo> docVoList = new ArrayList<DocumentVo>();
				for (Document docPo : pList) {
					DocumentVo documentVo = po2Vo(docPo);
					if (!map.containsKey(documentVo.getId())) {
						documentVo.setIsPer("N");
					} else {
						documentVo.setIsPer("Y");
					}
					docVoList.add(documentVo);
				}
				return docVoList;
			} else { // 无授权文件时直接返回
				return toDocumentVoList(pList);
			}
		}else{ // 无文件信息时 返回null
			return null;
		}
		
	}
	
	public List<DocumentVo> toDocumentVoList(List<Document> pList) throws GlobalException {
		List<DocumentVo> vList = new ArrayList<DocumentVo>();
		if (null == pList || pList.size() == 0)
			return vList;

		for (Document p : pList) {
			DocumentVo v = po2Vo(p);
			v.setIsPer("N");
			vList.add(v);
		}
		return vList;
	}
}
