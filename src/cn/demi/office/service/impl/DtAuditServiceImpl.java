package cn.demi.office.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.doc.dao.IDocumentDao;
import cn.demi.doc.po.Document;
import cn.demi.office.dao.IDtDao;
import cn.demi.office.po.Dt;
import cn.demi.office.service.IDtAuditService;
import cn.demi.office.vo.DtVo;

@Service("office.dtAuditService")
public class DtAuditServiceImpl extends BaseServiceImpl<DtVo,Dt> implements
		IDtAuditService {
	@Autowired
	private IDtDao dtDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired 
	private IDocumentDao documentDao;
	@Override
	public DtVo findById(String id) throws GlobalException {
		Dt po = dtDao.findById(id);
		DtVo vo=po2Vo(po);
		return vo;
	}
	
	@Override
	public void update(DtVo v) throws GlobalException {
		Dt p = dtDao.findById(v.getId());
		p.setAuditCt(v.getAuditCt());
		p.setAuditId(v.getAuditId());
		p.setAuditName(v.getAuditName());
		p.setAuditTime(v.getAuditTime());
		p.setSendId(v.getSendId());
		p.setSendName(v.getSendName());
		Progress pg=progressDao.findByBusId(p.getId());
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			p.setAuditResult("通过");
			progressDao.update(pg.getId(), EunmTask.DT_XF.getStatus(),null,null,p.getSendId(), p.getSendName());
			uptFile(p);
		}else if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_N)) {
			p.setAuditResult("不通过");
			progressDao.update(pg.getId(), EunmTask.DT_SQ.getStatus(),p.getOrgId(),p.getOrgName(),p.getUserId(), p.getUserName());
		}
		p.setStatus(pg.getStatus());
		progressLogDao.add(p.getId(), p.getId(), EunmTask.DT_QF.getStatus(), v.getIsCommit(), p.getAuditCt());
		dtDao.update(p);
	}
	//同步文件到文档管理下
	public void uptFile(Dt p) {
		Document doc=new Document();
		doc.setCategory(p.getCategory());
		doc.setType(p.getType());
		doc.setTitle(p.getTitle());
		doc.setName(p.getTitle());
		doc.setTime(DateUtils.getCurrDateTimeStr());
		doc.setPath(p.getPath());
		doc.setRelativePath(p.getFilePath());
		doc.setIsAllVisible(Constants.F);
		doc.setOriginalSize(p.getOriginalSize());
		doc.setSize(p.getSize());
		doc.setSign(null);
		doc.setState("现行");
		documentDao.add(doc);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, DtVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ,EunmTask.DT_QF.getStatus()));
		pageResult.addCondition(new QueryCondition("auditId", QueryCondition.EQ, getCurrent().getAccountId()));
		pageResult = dtDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Dt>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, DtVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("auditTime is not null"));
		pageResult.addCondition(new QueryCondition("auditId", QueryCondition.EQ, getCurrent().getAccountId()));
		pageResult = dtDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Dt>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<?, Object>> poList2mapList(List<Dt> list) throws GlobalException {
		List tempList = new ArrayList();
		for(Dt p:list){
			Map<String, Object> map=po2map(p);
			map.put("status", EunmTask.getName(map.get("status").toString()));
			tempList.add(map);
		}
		return tempList;
	}
}
