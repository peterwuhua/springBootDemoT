package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.dao.IConsOutInDao;
import cn.demi.res.dao.IConsumableDao;
import cn.demi.res.po.ConsOutIn;
import cn.demi.res.po.Consumable;
import cn.demi.res.po.RegOutIn;
import cn.demi.res.service.IConsOutInService;
import cn.demi.res.vo.ConsOutInVo;

@Service("res.consOutInService")
public class ConsOutInServiceImpl extends BaseServiceImpl<ConsOutInVo,ConsOutIn> implements
		IConsOutInService {
@Autowired private IConsumableDao consumableDao;
@Autowired private IConsOutInDao consOutInDao;
	@Override
	public void addOutDepot(ConsOutInVo v) throws GlobalException {
		Consumable consumable=consumableDao.findById(v.getConsumableVo().getId());
		Double newNum = consumable.getAmount() - v.getLeadingNum();
		consumable.setAmount(newNum);
		consumableDao.update(consumable);
		ConsOutIn p = vo2Po(v);
		p.setConsumable(consumable);
		p.setStatus(RegOutIn.out);
		p.setAmount(newNum);
		consOutInDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public void addInDepot(ConsOutInVo v) throws GlobalException {
		Consumable consumable=consumableDao.findById(v.getConsumableVo().getId());
		Double newNum = consumable.getAmount() + v.getInNum();
		consumable.setAmount(newNum);
		consumableDao.update(consumable);
		ConsOutIn p = vo2Po(v);
		p.setConsumable(consumable);
		p.setStatus(RegOutIn.in);
		p.setAmount(newNum);
		consOutInDao.add(p);
		v.setId(p.getId());
	}
	/*@SuppressWarnings("unchecked")
	@Override
	public GridVo outData(GridVo gridVo, RegOutInVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = regOutInDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<RegOutIn>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}*/
	@Override
	public PageResult getPageResult(GridVo gridVo, ConsOutInVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		pageResult.addConditionList(toQueryList(v));
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		if(v.getStatus().equals(RegOutIn.out)){
			queryList.add(new QueryCondition("status", QueryCondition.EQ,RegOutIn.out));
		}
		if(v.getStatus().equals(RegOutIn.in)){
			queryList.add(new QueryCondition("status", QueryCondition.EQ,RegOutIn.in));
		}
		pageResult.addConditionList(queryList);
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		return pageResult;
	}
	@Override
	public List<ConsOutInVo> getInList() throws GlobalException {
		List<QueryCondition> querylist = new ArrayList<QueryCondition>();
		querylist.add(new QueryCondition("status",QueryCondition.EQ, ConsOutIn.in));
		return toVoList(consOutInDao.list(querylist));
	}
	@Override
	public List<ConsOutInVo> getOutList() throws GlobalException {
		List<QueryCondition> querylist = new ArrayList<QueryCondition>();
		querylist.add(new QueryCondition("status",QueryCondition.EQ, ConsOutIn.out));
		return toVoList(consOutInDao.list(querylist));
	}
}
