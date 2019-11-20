package cn.demi.office.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.IKqDao;
import cn.demi.office.po.Kq;
import cn.demi.office.service.IKqService;
import cn.demi.office.vo.KqVo;

@Service("office.kqService")
public class KqServiceImpl extends BaseServiceImpl<KqVo,Kq> implements
		IKqService {
	@Autowired
	private IKqDao kqDao;
	@Autowired
	private IProgressDao progressDao;
	@Override
	public KqVo findById(String id) throws GlobalException {
		return super.findById(id);
	}
	/**
	 * 生成编号
	 */
	public String createCode(){
		String flag=DateUtils.getYear()+DateUtils.getMonth()+DateUtils.getDay();
		String hql="SELECT max(no) FROM "+kqDao.getEntityName(Kq.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
		String ls=(String) kqDao.query(hql).getSingleResult();
		String no=flag;
		if(ls==null) {
			no+="0001";
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
				no+="000"+sort;
			}else if(sort<100) {
				no+="00"+sort;
			}else if(sort<1000) {
				no+="0"+sort;
			}else {
				no+=sort;
			}
		}
		return no;
	}
	@Override
	public void add(KqVo v) throws GlobalException {
		Kq po=new Kq();
		po=po.toPo(v, po);
		po.setType(v.getType());
		po.setNo(createCode());
		po.setDeptId(getCurrent().getDepId());
		po.setDeptName(getCurrent().getDepName());
		po.setOrgId(getCurrent().getOrgId());
		po.setOrgName(getCurrent().getOrgName());
		po.setUserId(v.getUserId());
		po.setUserName(v.getUserName());
		po.setFstatus(Kq.ST_0);
		kqDao.add(po);
		v.setId(po.getId());
		Progress pg=progressDao.add(po.getId(), EunmTask.QJ_SQ.getStatus(),po.getOrgId(),po.getOrgName(),po.getUserId(),po.getUserName());
		po.setStatus(pg.getStatus());
		commit(po, v);
		kqDao.update(po);
	}
	@Override
	public void update(KqVo v) throws GlobalException {
		Kq po=kqDao.findById(v.getId());
		po=po.toPo(v, po);
		po.setFstatus(Kq.ST_0);
		commit(po, v);
		kqDao.update(po);
	}
	public void commit(Kq po,KqVo v){
		if (null!=v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
			Progress pg=progressDao.findByBusId(po.getId());
			pg=progressDao.update(pg.getId(),EunmTask.QJ_AD.getStatus(),po.getOrgId(),po.getOrgName(), null, null);
			po.setStatus(pg.getStatus());
			po.setFstatus(Kq.ST_1);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, KqVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("createTime", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("userId", QueryCondition.EQ, getCurrent().getUserId()));
		pageResult = kqDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Kq>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Query(GridVo gridVo, KqVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("createTime", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		if(!StrUtils.isBlankOrNull(v.getNo())) {
			pageResult.addCondition(new QueryCondition("no", QueryCondition.AK,v.getNo()));
		}
		if(!StrUtils.isBlankOrNull(v.getUserName())) {
			pageResult.addCondition(new QueryCondition("userName", QueryCondition.AK,v.getUserName()));
		}
		if(!StrUtils.isBlankOrNull(v.getType())) {
			pageResult.addCondition(new QueryCondition("type", QueryCondition.EQ,v.getType()));
		}
		if(!StrUtils.isBlankOrNull(v.getStartDate())) {
			pageResult.addCondition(new QueryCondition("date>='"+v.getStartDate()+" 00:00:00' or endTime>='"+v.getStartDate()+" 00:00:00'"));
		}
		if(!StrUtils.isBlankOrNull(v.getEndDate())) {
			pageResult.addCondition(new QueryCondition("date<='"+v.getEndDate()+" 23:59:59' or endTime<='"+v.getEndDate()+" 23:59:59'"));
		}
		if (!getCurrent().getRoleNames().contains("总经理") && !getCurrent().getRoleNames().contains("副总") && !getCurrent().getRoleNames().contains("管理员")) {
			pageResult.addCondition(new QueryCondition("deptId='" + getCurrent().getOrgId() + "' "));
		}
		
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ, Kq.ST_2));
		pageResult = kqDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Kq>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(KqVo v) throws GlobalException {
		return super.toQueryList(v);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Xj(GridVo gridVo, KqVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("date", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status", QueryCondition.EQ,EunmTask.QJ_XJ.getStatus()));
		pageResult.addCondition(new QueryCondition("userId", QueryCondition.EQ, getCurrent().getUserId()));
		pageResult = kqDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Kq>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public void update4Xj(KqVo v) throws GlobalException {
		Kq po=kqDao.findById(v.getId());
		po.setFstatus(Kq.ST_3);
		po.setStatus(EunmTask.TASK_END.getStatus());
		kqDao.update(po);
		progressDao.deleteByBusId(po.getId());
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Show(GridVo gridVo, KqVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("date", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("endTime>'"+DateUtils.getCurrDateTimeStr().substring(0, 13)+"点'"));
		pageResult.addCondition(new QueryCondition("status<>'"+EunmTask.TASK_END.getStatus()+"' and status<>'"+EunmTask.QJ_SQ.getStatus()+"'"));
		pageResult = kqDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Kq>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
