package cn.demi.qlt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.dao.INsDao;
import cn.demi.qlt.dao.INsRecordDao;
import cn.demi.qlt.po.Ns;
import cn.demi.qlt.po.NsRecord;
import cn.demi.qlt.service.INsFileService;
import cn.demi.qlt.vo.NsVo;

@Service("qlt.nsFileService")
public class NsFileServiceImpl extends BaseServiceImpl<NsVo,Ns> implements
		INsFileService {
	@Autowired
	private INsDao nsDao;
	@Autowired
	private INsRecordDao nsRecordDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Override
	public NsVo findById(String id) throws GlobalException {
		Ns po = nsDao.findById(id);
		NsVo vo=po2Vo(po);
		return vo;
	}
	 
	@Override
	public void update(NsVo v) throws GlobalException {
		Ns p = nsDao.findById(v.getId());
		p.setCdId(v.getCdId());
		p.setCdName(v.getCdName());
		p.setCdDate(v.getCdDate());
		p.setFileAddress(v.getFileAddress());
		Progress pg=progressDao.update(p.getPg().getId(),QltEunm.getNext(p.getPg().getStatus(), v.getIsCommit()));
		p.setStatus(pg.getStatus());
		p.setPg(null);
		nsDao.update(p);
		progressDao.delete(pg);
		progressLogDao.add(p.getId(), p.getId(),QltEunm.QLT_30.getStatus(),QltEunm.TASK_END.getStatus(),v.getRemark());
		List<NsRecord> rdList=nsRecordDao.findByNsId(p.getId());
		for (NsRecord rd : rdList) {
			Progress rpg=rd.getPg();
			if(null!=rpg) {
				rd.setPg(null);
				nsRecordDao.update(rd);
				progressDao.delete(rpg);
				progressLogDao.add(p.getId(), rd.getId(),QltEunm.QLT_M_40.getStatus(),QltEunm.TASK_END.getStatus(),"月度内审完成");
			}
		}
	}
	 
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, NsVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='"+v.getStatus()+"'"));
		if(!StrUtils.isBlankOrNull(v.getOrgId())) {
			pageResult.addCondition(new QueryCondition("orgId='"+v.getOrgId()+"'"));
		}
		if(!StrUtils.isBlankOrNull(v.getUserId())) {
			pageResult.addCondition(new QueryCondition("userId='"+v.getUserId()+"'"));
		}
		pageResult = nsDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Ns>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, NsVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("SELECT distinct t FROM "+nsDao.getEntityName(Ns.class)+" t,"+nsDao.getEntityName(ProgressLog.class)+" log where log.busId=t.id and t.isDel !="+Po.Y);
		if(QueryConditionList!=null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='"+v.getStatus()+"' AND log.userId like '%"+CurrentUtils.getCurrent().getAccountId()+"%' ");
		hql.append(" ORDER BY t."+pageResult.getOrderColumn()+" "+pageResult.getOrder()+"");
		pageResult =nsDao.getPageResult(hql.toString(), pageResult);
		gridVo.setDatas(poList2mapList((List<Ns>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<?, Object>> poList2mapList(List<Ns> list) throws GlobalException {
		List tempList = new ArrayList();
		for(Ns p:list){
			Map<String, Object> map = po2map(p);
			String status = String.valueOf(map.get("status"));
			map.put("status",QltEunm.getName(status));
			tempList.add(map);
		}
		return tempList;
	}
}
