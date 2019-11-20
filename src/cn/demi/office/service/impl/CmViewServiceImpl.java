package cn.demi.office.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.ICmDao;
import cn.demi.office.po.Cm;
import cn.demi.office.service.ICmViewService;
import cn.demi.office.vo.CmVo;

@Service("office.cmViewService")
public class CmViewServiceImpl extends BaseServiceImpl<CmVo,Cm> implements
		ICmViewService {
	@Autowired
	private ICmDao cmDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	
	@Override
	public CmVo findById(String id) throws GlobalException {
		Cm po = cmDao.findById(id);
		return po2Vo(po);
	}
	@Override
	public void update(CmVo v) throws GlobalException {
		Cm p = cmDao.findById(v.getId());
		if(p.getViewEdIds()==null) {
			p.setViewEdIds(getCurrent().getAccountId());
			p.setViewEdNames(getCurrent().getUserName());
		}else {
			p.setViewEdIds(p.getViewEdIds()+","+getCurrent().getAccountId());
			p.setViewEdNames(p.getViewEdNames()+","+getCurrent().getUserName());
		}
		cmDao.update(p);
		
		Progress pg=progressDao.findByBusId(p.getId());
		String userId[]=pg.getUserId().split(",");
		String userName[]=pg.getUserName().split(",");
		List<String> newIds=new LinkedList<String>();
		List<String> newNames=new LinkedList<String>();
		int n=0;
		for (String id : userId) {
			if(id.trim().length()>0 && !id.equals(getCurrent().getAccountId())) {
				newIds.add(id);
				newNames.add(userName[n]);
			}
			n++;
		}
		if(newIds.size()>0) {
			progressDao.update(pg.getId(), EunmTask.GS_CS.getStatus(), String.join(",", newIds), String.join(",", newNames));
			progressLogDao.add(p.getId(), p.getId(), EunmTask.GS_CS.getStatus(), v.getIsCommit(), p.getRemark(), p.getOrgId(), p.getOrgName(), p.getUserId(), p.getUserName());
		}else {
			progressDao.delete(pg);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CmVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("fstatus", QueryCondition.EQ, Cm.ST_2));
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ, EunmTask.GS_CS.getStatus()));
		pageResult.addCondition(new QueryCondition("viewIds like '%"+getCurrent().getAccountId()+"%' or userIds like '%"+getCurrent().getAccountId()+"%' or userId ='"+getCurrent().getAccountId()+"'"));
		pageResult = cmDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Cm>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, CmVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status", QueryCondition.NE, Cm.ST_2));
		pageResult.addCondition(new QueryCondition("viewEdIds", QueryCondition.AK, getCurrent().getAccountId()));
		pageResult = cmDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Cm>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}

