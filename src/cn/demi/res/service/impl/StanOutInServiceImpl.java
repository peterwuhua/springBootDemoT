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
import cn.demi.res.dao.IStanOutInDao;
import cn.demi.res.dao.IStandardDao;
import cn.demi.res.po.RegOutIn;
import cn.demi.res.po.StanOutIn;
import cn.demi.res.po.Standard;
import cn.demi.res.service.IStanOutInService;
import cn.demi.res.vo.StanOutInVo;

@Service("res.stanOutInService")
public class StanOutInServiceImpl extends BaseServiceImpl<StanOutInVo,StanOutIn> implements
		IStanOutInService {
@Autowired private IStandardDao standardDao;
@Autowired private IStanOutInDao stanOutInDao;
	@Override
	public void addOutDepot(StanOutInVo v) throws GlobalException {
		Standard standard=standardDao.findById(v.getStandardVo().getId());
		Double newNum = standard.getAmount() - v.getLeadingNum();
		standard.setAmount(newNum);
		standardDao.update(standard);
		StanOutIn p = vo2Po(v);
		p.setStandard(standard);
		p.setStatus(RegOutIn.out);
		p.setAmount(newNum);
		stanOutInDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public void addInDepot(StanOutInVo v) throws GlobalException {
		//Standard standard=standardDao.findById(v.getStandardVo().getId());
		v.setName(v.getName().trim());
		v.setNo(v.getNo().trim());
		Standard standard=standardDao.find(v.getName(), v.getNo());
		if(standard==null) {
			standard=new Standard();
			standard=standard.toPo(v, standard);
		}
		Double newNum = standard.getAmount() + v.getInNum();
		standard.setAmount(newNum);
		standardDao.saveOrUpdate(standard);
		StanOutIn p = vo2Po(v);
		p.setStandard(standard);
		p.setStatus(RegOutIn.in);
		p.setAmount(newNum);
		stanOutInDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public PageResult getPageResult(GridVo gridVo, StanOutInVo v) throws GlobalException {
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
	public List<StanOutInVo> getInList() throws GlobalException {
		List<QueryCondition> querylist = new ArrayList<QueryCondition>();
		querylist.add(new QueryCondition("status",QueryCondition.EQ, RegOutIn.in));
		return toVoList(stanOutInDao.list(querylist));
	}
	@Override
	public List<StanOutInVo> getOutList() throws GlobalException {
		List<QueryCondition> querylist = new ArrayList<QueryCondition>();
		querylist.add(new QueryCondition("status",QueryCondition.EQ, RegOutIn.out));
		return toVoList(stanOutInDao.list(querylist));
	}
}
