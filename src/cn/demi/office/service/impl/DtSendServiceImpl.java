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
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.IDtDao;
import cn.demi.office.po.Dt;
import cn.demi.office.service.IDtSendService;
import cn.demi.office.vo.DtVo;

@Service("office.dtSendService")
public class DtSendServiceImpl extends BaseServiceImpl<DtVo,Dt> implements
		IDtSendService {
	@Autowired
	private IDtDao dtDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Override
	public DtVo findById(String id) throws GlobalException {
		Dt po = dtDao.findById(id);
		DtVo vo=po2Vo(po);
		return vo;
	}
	
	@Override
	public void update(DtVo v) throws GlobalException {
		Dt p = dtDao.findById(v.getId());
		p.setSendCt(v.getSendCt());
		p.setSendId(v.getSendId());
		p.setSendName(v.getSendName());
		p.setSendTime(v.getSendTime());
		
		p.setUserIds(v.getUserIds());
		p.setUserNames(v.getUserNames());
		dtDao.update(p);
		Progress pg=progressDao.findByBusId(p.getId());
		pg = progressDao.update(pg.getId(), EunmTask.DT_CK.getStatus(),null,null,p.getUserIds(), p.getUserNames());
		p.setStatus(pg.getStatus());
		progressLogDao.add(p.getId(), p.getId(), EunmTask.DT_XF.getStatus(), v.getIsCommit(), p.getSendCt());
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, DtVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ,EunmTask.DT_XF.getStatus()));
		pageResult.addCondition(new QueryCondition("sendId", QueryCondition.EQ, getCurrent().getAccountId()));
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
		pageResult.addCondition(new QueryCondition("sendTime is not null"));
		pageResult.addCondition(new QueryCondition("sendId", QueryCondition.EQ, getCurrent().getAccountId()));
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
