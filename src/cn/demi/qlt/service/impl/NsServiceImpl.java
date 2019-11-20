package cn.demi.qlt.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.po.Account;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.dao.INsDao;
import cn.demi.qlt.dao.INsDetailDao;
import cn.demi.qlt.dao.INsItemDao;
import cn.demi.qlt.dao.INsRecordDao;
import cn.demi.qlt.dao.INsRecordDetailDao;
import cn.demi.qlt.po.Ns;
import cn.demi.qlt.po.NsDetail;
import cn.demi.qlt.po.NsItem;
import cn.demi.qlt.po.NsRecord;
import cn.demi.qlt.po.NsRecordDetail;
import cn.demi.qlt.service.INsService;
import cn.demi.qlt.vo.NsDetailVo;
import cn.demi.qlt.vo.NsVo;

@Service("qlt.nsService")
public class NsServiceImpl extends BaseServiceImpl<NsVo,Ns> implements
		INsService {
	@Autowired
	private INsDao nsDao;
	@Autowired
	private INsDetailDao nsDetailDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private INsRecordDao nsRecordDao;
	@Autowired
	private INsRecordDetailDao nsRecordDetailDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private INsItemDao nsItemDao;
	@Override
	public NsVo findById(String id) throws GlobalException {
		Ns po = nsDao.findById(id);
		NsVo vo=po2Vo(po);
		List<NsDetail> dtList=nsDetailDao.listByNsId(po.getId());
		List<NsDetailVo> dtVoList=new ArrayList<NsDetailVo>();
		if(null!=dtList&&dtList.size()>0) {
			for (NsDetail dt : dtList) {
				NsDetailVo dtVo=new NsDetailVo();
				dtVo=dtVo.toVo(dt);
				dtVoList.add(dtVo);
			}
		}
		vo.setDetailList(dtVoList);
		return vo;
	}
	@Override
	public void add(NsVo v) throws GlobalException {
		Ns p = vo2Po(v);
		p.setOrgId(getCurrent().getOrgId());
		p.setOrgName(getCurrent().getOrgName());
		p.setIsBack(QltEunm.N);
		p.setNo(createCode());
		nsDao.add(p);
		v.setId(p.getId());
		//添加流程实例
		Progress pg=progressDao.add(p.getId(),QltEunm.QLT_00.getStatus(),p.getOrgId(),p.getOrgName(), p.getUserId(),p.getUserName());
		p.setPg(pg);
		p.setStatus(pg.getStatus());
		updateItem(p,v);
		nsDao.update(p);
		commit(p, v);
	}
	@Override
	public void update(NsVo v) throws GlobalException {
		Ns p = nsDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		updateItem(p,v);
		if(StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(createCode());
		}
		nsDao.update(p);
		commit(p, v);
	}
	/**
	 * 生成编号
	 */
	public String createCode(){
		String flag=DateUtils.getYear()+DateUtils.getMonth();
		String hql="SELECT max(no) FROM "+nsDao.getEntityName(Ns.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
		String ls=(String) nsDao.query(hql).getSingleResult();
		String no=flag;
		if(ls==null) {
			no+="01";
		}else {
			ls=ls.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort=0;
			}
			sort++;
			if(sort<10) {
				no+="0"+sort;
			}else {
				no+=sort;
			}
		}
		return no;
	}
	//更新要素
	public void updateItem(Ns p,NsVo v) {
		List<NsDetailVo> dtList=v.getDetailList();
		List<Integer> monthList=new ArrayList<Integer>();
		if(null!=dtList) {
			for (NsDetailVo dtVo : dtList) {
				if(null!=dtVo&&!StrUtils.isBlankOrNull(dtVo.getId())) {
					NsDetail detail=nsDetailDao.findById(dtVo.getId());
					detail=detail.toPo(dtVo, detail);
					nsDetailDao.update(detail);
					if(!StrUtils.isBlankOrNull(dtVo.getMonths())) {
						String months[]=dtVo.getMonths().split(",");
						for (String m : months) {
							if(m.trim().length()>0) {
								int x=Integer.valueOf(m.trim());
								if(!monthList.contains(x)) {
									monthList.add(x);
								}
							}
						}
					}
				}
			}
			Collections.sort(monthList);
			String str="";
			for (Integer n : monthList) {
				str+=n+",";
			}
			if(monthList.size()>0) {
				str=str.substring(0,str.length()-1);
			}
			p.setMonths(str);
		}
	}
	public void commit(Ns p,NsVo v){
		if(null!=v.getIsCommit() && v.getIsCommit().equals(QltEunm.PASS_Y)) {
			p.setIsBack(Constants.N);
			Progress pg=progressDao.update(p.getPg().getId(),QltEunm.getNext(p.getPg().getStatus(), v.getIsCommit()));
			p.setStatus(pg.getStatus());
			nsDao.update(p);
			progressLogDao.add(p.getId(), p.getId(),QltEunm.QLT_00.getStatus(),v.getIsCommit(),v.getRemark());
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
	
	@Override
	public void update4Audit(NsVo v) throws GlobalException {
		Ns p = nsDao.findById(v.getId());
		p.setAuditId(v.getAuditId());
		p.setAuditName(v.getAuditName());
		p.setAuditDate(v.getAuditDate());
		if(StrUtils.isBlankOrNull(v.getRemark())) {
			if(!StrUtils.isBlankOrNull(p.getRemark())) {
				p.setRemark(p.getRemark()+"\r\n"+v.getRemark());
			}else {
				p.setRemark(v.getRemark());
			}
		}
		Progress pg=null;
		if(null!=v.getIsCommit() && v.getIsCommit().equals(QltEunm.PASS_Y)) {
			pg=progressDao.update(p.getPg().getId(),QltEunm.getNext(p.getPg().getStatus(), v.getIsCommit()));
			initRecord(p);
		}else {
			p.setIsBack(Constants.Y);
			pg=progressDao.update(p.getPg().getId(),QltEunm.getNext(p.getPg().getStatus(), v.getIsCommit()),p.getUserId(),p.getUserName());
		}
		p.setStatus(pg.getStatus());
		nsDao.update(p);
		progressLogDao.add(p.getId(), p.getId(),QltEunm.QLT_10.getStatus(),v.getIsCommit(),v.getRemark());
	}
	//初始话月度记录单
	public void initRecord(Ns ns) {
		String months[]=ns.getMonths().split(",");
		for (String month : months) {
			int m=Integer.valueOf(month);
			String jpql="FROM "+nsDetailDao.getEntityName(NsDetail.class)+" WHERE isDel='"+Po.N+"' AND ns.id='"+ns.getId()+"'";
			jpql+=" AND (months='"+m+"' OR months like '"+m+",%' OR months like '%,"+m+"' OR months like '%,"+m+",%')";
			List<NsDetail> dtList=nsDetailDao.list(jpql);
			if(null!=dtList) {
				List<String> item=new ArrayList<String>();
				Set<String> fzId=new HashSet<String>();
				Set<String> fzName=new HashSet<String>();
				List<String> xzId=new ArrayList<String>();
				List<String> xzName=new ArrayList<String>();
				for (NsDetail dt : dtList) {
					item.add(dt.getName());
					if(!StrUtils.isBlankOrNull(dt.getHeadId())) {
						fzId.add(dt.getHeadId());
						fzName.add(dt.getHeadName());
					}
					if(!StrUtils.isBlankOrNull(dt.getXzId())) {
						List<Account> atList=accountDao.listByIds(dt.getXzId());
						if(atList!=null) {
							for (Account at : atList) {
								if(!xzId.contains(at.getId())) {
									xzId.add(at.getId());
									xzName.add(at.getUser().getName());
								}
							}
						}
					}
				}
				//初始化月度记录单
				NsRecord r=nsRecordDao.findByNsId(ns.getId(),m);
				if(r==null) {
					r=new NsRecord();
					r.setNs(ns);
					r.setYear(ns.getYear());
					r.setMonth(m);
					r.setItem(String.join(",", item));
					r.setHeadId(String.join(",", fzId));
					r.setHeadName(String.join(",", fzName));
					r.setXzId(String.join(",", xzId));
					r.setXzName(String.join(",", xzName));
					r.setIsBack(QltEunm.N);
					nsRecordDao.add(r);
					//添加流程实例
					Progress pg=progressDao.add(r.getId(),QltEunm.QLT_M_00.getStatus(),ns.getOrgId(),ns.getOrgName(), r.getHeadId()+","+r.getXzId(),r.getHeadName()+","+r.getXzName());
					r.setPg(pg);
					r.setStatus(pg.getStatus());
					nsRecordDao.update(r);
					//初始化月度记录单  要素 详情
					for (NsDetail dt : dtList) {
						NsItem ys=nsItemDao.findById(dt.getItemId());
						NsRecordDetail rdt=new NsRecordDetail();
						rdt.setNsId(ns.getId());
						rdt.setRecordId(r.getId());
						rdt.setParent(null);
						rdt.setItemId(ys.getId());
						rdt.setCode(dt.getCode());
						rdt.setName(ys.getName());
						rdt.setLevel(ys.getLevel());
						rdt.setMonth(m);
						rdt.setSort(ys.getSort());
						nsRecordDetailDao.add(rdt);
						//初始化要素内容
						List<NsItem> ctList=nsItemDao.listByItemId(dt.getItemId());
						if(null!=ctList) {
							int x=1;
							for (NsItem ct : ctList) {
								NsRecordDetail srdt=new NsRecordDetail();
								srdt.setNsId(ns.getId());
								srdt.setRecordId(r.getId());
								srdt.setParent(rdt);
								srdt.setItemId(ct.getId());
								srdt.setCode(dt.getCode()+"."+x);
								srdt.setName(ct.getName());
								srdt.setLevel(ct.getLevel());
								srdt.setMonth(m);
								srdt.setSort(ct.getSort());
								nsRecordDetailDao.add(srdt);
								//初始话重点
								List<NsItem> zdList=nsItemDao.listByItemId(srdt.getItemId());
								if(null!=zdList) {
									int y=1;
									for (NsItem zd : zdList) {
										NsRecordDetail trdt=new NsRecordDetail();
										trdt.setNsId(ns.getId());
										trdt.setRecordId(r.getId());
										trdt.setParent(srdt);
										trdt.setItemId(zd.getId());
										trdt.setCode(dt.getCode()+"."+x+"."+y);
										trdt.setName(zd.getName());
										trdt.setLevel(zd.getLevel());
										trdt.setMonth(m);
										trdt.setSort(zd.getSort());
										nsRecordDetailDao.add(trdt);
										y++;
									}
								}
								x++;
							}
						}
					}
				}
			}
		}
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
