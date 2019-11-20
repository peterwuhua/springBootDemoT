package cn.demi.cus.bj.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
import cn.demi.cus.bj.dao.IBudgetDao;
import cn.demi.cus.bj.dao.IBudgetDetailDao;
import cn.demi.cus.bj.po.Budget;
import cn.demi.cus.bj.po.BudgetDetail;
import cn.demi.cus.bj.service.IBudgetService;
import cn.demi.cus.bj.vo.BudgetDetailVo;
import cn.demi.cus.bj.vo.BudgetVo;

@Service("cus.budgetService")
public class BudgetServiceImpl extends BaseServiceImpl<BudgetVo,Budget> implements
		IBudgetService {
	
	@Autowired
	private IBudgetDao budgetDao;
	@Autowired
	private IBudgetDetailDao budgetDetailDao;
	
	@Override
	public BudgetVo findById(String id) throws GlobalException {
		Budget po  = budgetDao.findById(id);
		BudgetVo vo=new BudgetVo();
		vo=vo.toVo(po);
		List<BudgetDetail> detailList=budgetDetailDao.findByProperty("budget.id", po.getId());
		List<BudgetDetailVo> detailVoList=new ArrayList<BudgetDetailVo>();
		if(null!=detailList) {
			for (BudgetDetail detail : detailList) {
				BudgetDetailVo detialVo=new BudgetDetailVo();
				detialVo=detialVo.toVo(detail);
				detailVoList.add(detialVo);
			}
		}
		vo.setDetailList(detailVoList);
		return vo;
	}
	@Override
	public void update(BudgetVo v) throws GlobalException {
		Budget p = budgetDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		setDetail(v, p);
		if(null!=v.getIsCommit()&&v.getIsCommit().equals(Constants.PASS_Y)) {
			p.setStatus(Budget.ST_1);
			p.setBack(Constants.N);
		}
		budgetDao.update(p);
	}
	@Override
	public void add(BudgetVo v) throws GlobalException {
		Budget p = vo2Po(v);
		p.setOrgId(getCurrent().getOrgId());
		p.setOrgName(getCurrent().getOrgName());
		p.setNo(createCode());
		p.setBack(Constants.N);
		if(null!=v.getIsCommit()&&v.getIsCommit().equals(Constants.PASS_Y)) {
			p.setStatus(Budget.ST_1);
		}else {
			p.setStatus(Budget.ST_0);
			
		}
		p.setPayPrice(p.getPrice()*p.getDiscount());
		budgetDao.add(p);
		v.setId(p.getId());
		setDetail(v, p);
	}
	@Override
	public void delete(String... ids) throws GlobalException {
		List<Budget> pList=budgetDao.listByIds(ids);
		for (Budget p : pList) {
			budgetDetailDao.deleteByBudId(p.getId());
			budgetDao.delete(p);
		}
	}
	public void setDetail(BudgetVo v,Budget p) {
		Set<String> sampNameSet=new HashSet<String>();
		List<String> sampTypeIdList=new LinkedList<String>();
		List<String> sampTypeNameList=new LinkedList<String>();
		budgetDetailDao.deleteByBudId(p.getId());
		if(null!=v.getDetailList()) {
			for (BudgetDetailVo detailVo : v.getDetailList()) {
				BudgetDetail detail=new BudgetDetail();
				detail=detail.toPo(detailVo, detail);
				detail.setBudget(p);
				budgetDetailDao.add(detail);
				sampNameSet.add(detail.getSampName());
				if(!sampTypeIdList.contains(detail.getSampTypeId())) {
					sampTypeIdList.add(detail.getSampTypeId());
					sampTypeNameList.add(detail.getSampTypeName());
				}
			}
		}
		p.setSampName(String.join(",", sampNameSet));
		p.setSampTypeId(String.join(",", sampTypeIdList));
		p.setSampTypeName(String.join(",", sampTypeNameList));
	}
	/**
	 * 生成项目编号
	 * @param taskType
	 * @param type
	 * @return
	 */
	public String createCode(){
		String flag=DateUtils.getYear()+DateUtils.getMonth();
		String hql="SELECT max(no) FROM "+budgetDao.getEntityName(Budget.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
		String ls=(String) budgetDao.query(hql).getSingleResult();
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
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, BudgetVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='"+Budget.ST_0+"' "));
		pageResult = budgetDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Budget>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, BudgetVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status!='"+Budget.ST_0+"' "));
		pageResult.addCondition(new QueryCondition("buserId='"+getCurrent().getAccountId()+"' "));
		pageResult = budgetDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Budget>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Lx(GridVo gridVo, BudgetVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='"+Budget.ST_2+"' "));
		pageResult.addCondition(new QueryCondition("orgId='"+getCurrent().getOrgId()+"' "));
		pageResult.addCondition(new QueryCondition("yxq>='"+DateUtils.getCurrDateStr()+"' "));
		pageResult = budgetDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Budget>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Query(GridVo gridVo, BudgetVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status!='"+Budget.ST_0+"' "));
		pageResult.addCondition(new QueryCondition("orgId='"+getCurrent().getOrgId()+"' "));
		pageResult = budgetDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Budget>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<BudgetVo> list4Cust(String custId) throws GlobalException {
		String jpql="FROM "+budgetDao.getEntityName(Budget.class)+" WHERE isDel='"+Po.N+"' AND custId='"+custId+"' ORDER BY bdate DESC";
		List<Budget> bgList=budgetDao.list(jpql);
		return toVoList(bgList);
	}
}
