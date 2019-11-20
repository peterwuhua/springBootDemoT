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
import cn.demi.qlt.dao.INsRecordDao;
import cn.demi.qlt.dao.INsRecordDetailDao;
import cn.demi.qlt.po.NsRecord;
import cn.demi.qlt.po.NsRecordDetail;
import cn.demi.qlt.service.INsCrtService;
import cn.demi.qlt.vo.NsRecordDetailVo;
import cn.demi.qlt.vo.NsRecordVo;

@Service("qlt.nsCrtService")
public class NsCrtServiceImpl extends BaseServiceImpl<NsRecordVo,NsRecord> implements
		INsCrtService {
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private INsRecordDao nsRecordDao;
	@Autowired
	private INsRecordDetailDao nsRecordDetailDao;
	
	@Override
	public NsRecordVo findById(String id) throws GlobalException {
		NsRecord po  = nsRecordDao.findById(id);
		NsRecordVo vo=po2Vo(po);
		vo.setDetailList(getDtList(po.getId(),null));
		return vo;
	}
	public List<NsRecordDetailVo> getDtList(String rId,String pId) throws GlobalException{
		List<NsRecordDetailVo> dtVoList=null;
		List<NsRecordDetail> dtList=nsRecordDetailDao.listByPid(rId,pId);
		if(null!=dtList &&dtList.size()>0) {
			dtVoList=new ArrayList<NsRecordDetailVo>();
			for (NsRecordDetail dt : dtList) {
				if(null!=dt.getResult()&&!dt.getResult().equals(QltEunm.ITEM_BFH)) {
					continue;
				}
				NsRecordDetailVo dtVo=new NsRecordDetailVo();
				dtVo=dtVo.toVo(dt);
				dtVo.setSubList(getDtList(rId,dt.getId()));
				if(StrUtils.isBlankOrNull(dtVo.getResult()) && dtVo.getSubList()!=null && dtVo.getSubList().size()>0 || !StrUtils.isBlankOrNull(dtVo.getResult())) {
					int count=0;
					if(null!=dtVo.getSubList()) {
						for(NsRecordDetailVo subVo :dtVo.getSubList()) {
							count+=subVo.getCount();
						}
					}else {
						count=1;
					}
					dtVo.setCount(count);
					dtVoList.add(dtVo);
				}
			}
		}
		return dtVoList;
	}
	@Override
	public NsRecordVo find4Show(String id) throws GlobalException {
		NsRecord po  = nsRecordDao.findById(id);
		NsRecordVo vo=po2Vo(po);
		vo.setDetailList(getDtList4Show(po.getId(),null));
		return vo;
	}
	public List<NsRecordDetailVo> getDtList4Show(String rId,String pId) throws GlobalException{
		List<NsRecordDetailVo> dtVoList=null;
		List<NsRecordDetail> dtList=nsRecordDetailDao.listByPid(rId,pId);
		if(null!=dtList &&dtList.size()>0) {
			dtVoList=new ArrayList<NsRecordDetailVo>();
			for (NsRecordDetail dt : dtList) {
				if(null!=dt.getResult()&&StrUtils.isBlankOrNull(dt.getRectification())) {
					continue;
				}
				NsRecordDetailVo dtVo=new NsRecordDetailVo();
				dtVo=dtVo.toVo(dt);
				dtVo.setSubList(getDtList4Show(rId,dt.getId()));
				if(StrUtils.isBlankOrNull(dtVo.getResult()) && dtVo.getSubList()!=null && dtVo.getSubList().size()>0 || !StrUtils.isBlankOrNull(dtVo.getResult())) {
					int count=0;
					if(null!=dtVo.getSubList()) {
						for(NsRecordDetailVo subVo :dtVo.getSubList()) {
							count+=subVo.getCount();
						}
					}else {
						count=1;
					}
					dtVo.setCount(count);
					dtVoList.add(dtVo);
				}
			}
		}
		return dtVoList;
	}
	@Override
	public void update(NsRecordVo v) throws GlobalException {
		NsRecord p = nsRecordDao.findById(v.getId());
		p.setRemark(v.getRemark());
		p.setGzDate(v.getGzDate());
		p.setGzId(v.getGzId());
		p.setGzName(v.getGzName());
		nsRecordDao.update(p);
		saveDetail(p, v);
		if(null!=v.getIsCommit() && v.getIsCommit().equals(QltEunm.PASS_Y)) {
			Progress pg=progressDao.update(p.getPg().getId(),QltEunm.QLT_M_20.getStatus(),p.getGzId(),p.getGzName());
			p.setStatus(pg.getStatus());
			nsRecordDao.update(p);
			progressLogDao.add(p.getNs().getId(), p.getId(),QltEunm.QLT_M_10.getStatus(),v.getIsCommit(),v.getRemark());
		}
	}
	public void saveDetail(NsRecord p,NsRecordVo v) {
		List<NsRecordDetailVo> dtVoList=v.getDetailList();
		for (NsRecordDetailVo rdtVo : dtVoList) {
			NsRecordDetail rdt=nsRecordDetailDao.findById(rdtVo.getId());
			rdt.setRectification(rdtVo.getRectification());
			nsRecordDetailDao.update(rdt);
			List<NsRecordDetailVo> subList=rdtVo.getSubList();
			if(null!=subList) {
				for (NsRecordDetailVo sdtVo : subList) {
					NsRecordDetail sdt=nsRecordDetailDao.findById(sdtVo.getId());
					sdt.setRectification(sdtVo.getRectification());
					nsRecordDetailDao.update(sdt);
					List<NsRecordDetailVo> thdList=sdtVo.getSubList();
					if(null!=thdList) {
						for (NsRecordDetailVo tdtVo : thdList) {
							NsRecordDetail tdt=nsRecordDetailDao.findById(tdtVo.getId());
							tdt.setRectification(tdtVo.getRectification());
							nsRecordDetailDao.update(tdt);
						}
					}
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, NsRecordVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='"+v.getStatus()+"'"));
		pageResult.addCondition(new QueryCondition("gzId like '%"+getCurrent().getAccountId()+"%' or gzId is null or gzId=''"));
		pageResult = nsRecordDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<NsRecord>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, NsRecordVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("SELECT distinct t FROM "+nsRecordDao.getEntityName(NsRecord.class)+" t,"+nsRecordDao.getEntityName(ProgressLog.class)+" log where log.busId=t.id and t.isDel !="+Po.Y);
		if(QueryConditionList!=null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='"+v.getStatus()+"' AND log.userId like '%"+CurrentUtils.getCurrent().getAccountId()+"%' ");
		hql.append(" ORDER BY t."+pageResult.getOrderColumn()+" "+pageResult.getOrder()+"");
		pageResult =nsRecordDao.getPageResult(hql.toString(), pageResult);
		gridVo.setDatas(poList2mapList((List<NsRecord>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<?, Object>> poList2mapList(List<NsRecord> list) throws GlobalException {
		List tempList = new ArrayList();
		for(NsRecord p:list){
			Map<String, Object> map = po2map(p);
			String status = String.valueOf(map.get("status"));
			map.put("status",QltEunm.getName(status));
			tempList.add(map);
		}
		return tempList;
	}
}
