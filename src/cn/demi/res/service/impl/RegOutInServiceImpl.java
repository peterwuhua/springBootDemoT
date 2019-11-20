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
import cn.demi.res.dao.IReagentDao;
import cn.demi.res.dao.IRegOutInDao;
import cn.demi.res.po.Reagent;
import cn.demi.res.po.RegOutIn;
import cn.demi.res.service.IRegOutInService;
import cn.demi.res.vo.RegOutInVo;

@Service("res.regOutInService")
public class RegOutInServiceImpl extends BaseServiceImpl<RegOutInVo,RegOutIn> implements
		IRegOutInService {
@Autowired private IReagentDao reagentDao;
@Autowired private IRegOutInDao regOutInDao;
	@Override
	public void addOutDepot(RegOutInVo v) throws GlobalException {
		Reagent reagent=reagentDao.findById(v.getReagentVo().getId());
		Double newNum = reagent.getAmount() - v.getLeadingNum();
		reagent.setAmount(newNum);
		reagentDao.update(reagent);
		RegOutIn p = vo2Po(v);
		p.setReagent(reagent);
		p.setStatus(RegOutIn.out);
		p.setAmount(newNum);
		regOutInDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public void addInDepot(RegOutInVo v) throws GlobalException {
		Reagent reagent=reagentDao.findById(v.getReagentVo().getId());
		Double newNum = reagent.getAmount() + v.getInNum();
		reagent.setAmount(newNum);
		reagentDao.update(reagent);
		RegOutIn p = vo2Po(v);
		p.setReagent(reagent);
		p.setStatus(RegOutIn.in);
		p.setAmount(newNum);
		regOutInDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public PageResult getPageResult(GridVo gridVo, RegOutInVo v) throws GlobalException {
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
	public List<RegOutInVo> getOutList() throws GlobalException {
		List<QueryCondition> querylist = new ArrayList<QueryCondition>();
		querylist.add(new QueryCondition("status",QueryCondition.EQ, RegOutIn.out));
		return toVoList(regOutInDao.list(querylist));
	}
	@Override
	public List<RegOutInVo> getInList() throws GlobalException {
		List<QueryCondition> querylist = new ArrayList<QueryCondition>();
		querylist.add(new QueryCondition("status",QueryCondition.EQ, RegOutIn.in));
		return toVoList(regOutInDao.list(querylist));
	}
}
