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
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.dao.IGsDao;
import cn.demi.qlt.dao.IGsRecordDao;
import cn.demi.qlt.po.Gs;
import cn.demi.qlt.po.GsRecord;
import cn.demi.qlt.service.IGsReportService;
import cn.demi.qlt.vo.GsRecordVo;
import cn.demi.qlt.vo.GsVo;

@Service("qlt.gsReportService")
public class GsReportServiceImpl extends BaseServiceImpl<GsVo,Gs> implements
		IGsReportService {
	
	@Autowired
	private IGsDao gsDao;
	@Autowired
	private IGsRecordDao gsRecordDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	
	@Override
	public GsVo findById(String id) throws GlobalException {
		Gs po  = gsDao.findById(id);
		GsVo vo=po2Vo(po);
		List<GsRecord> rdList=gsRecordDao.listByGsId(po.getId());
		List<GsRecordVo> recordList =new ArrayList<GsRecordVo>();
		if(null!=rdList) {
			for (GsRecord rd : rdList) {
				GsRecordVo rdVo=new GsRecordVo();
				rdVo=rdVo.toVo(rd);
				recordList.add(rdVo);
			}
		}
		vo.setRecordList(recordList);
		return vo;
	}
	@Override
	public void update(GsVo v) throws GlobalException {
		Gs p = gsDao.findById(v.getId());
		p.setReportDate(v.getReportDate());
		p.setReportId(v.getReportId());
		p.setReportName(v.getReportName());
		gsDao.update(p);
		if(null!=v.getIsCommit() && v.getIsCommit().equals(QltEunm.PASS_Y)) {
			p.setIsBack(Constants.N);
			Progress pg=progressDao.update(p.getPg().getId(),QltEunm.GS_30.getStatus());
			p.setStatus(pg.getStatus());
			gsDao.update(p);
			progressLogDao.add(p.getId(), p.getId(),QltEunm.GS_20.getStatus(),v.getIsCommit(),v.getRemark());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, GsVo v) throws GlobalException {
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
		pageResult = gsDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Gs>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, GsVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("SELECT distinct t FROM "+gsDao.getEntityName(Gs.class)+" t,"+gsDao.getEntityName(ProgressLog.class)+" log where log.busId=t.id and t.isDel !="+Po.Y);
		if(QueryConditionList!=null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='"+v.getStatus()+"' AND log.userId like '%"+CurrentUtils.getCurrent().getAccountId()+"%' ");
		hql.append(" ORDER BY t."+pageResult.getOrderColumn()+" "+pageResult.getOrder()+"");
		pageResult =gsDao.getPageResult(hql.toString(), pageResult);
		gridVo.setDatas(poList2mapList((List<Gs>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<?, Object>> poList2mapList(List<Gs> list) throws GlobalException {
		List tempList = new ArrayList();
		for(Gs p:list){
			Map<String, Object> map = po2map(p);
			String status = String.valueOf(map.get("status"));
			map.put("status",QltEunm.getName(status));
			tempList.add(map);
		}
		return tempList;
	}
	@Override
	public GsVo find4Report(String id) throws GlobalException {
		Gs po  = gsDao.findById(id);
		GsVo vo=po2Vo(po);
		return vo;
	}
	@Override
	public void update4Report(GsVo v) throws GlobalException {
		Gs po  = gsDao.findById(v.getId());
		po.setFileName(v.getFileName());
		po.setFilePath(v.getFilePath());
		gsDao.update(po);
	}
}
