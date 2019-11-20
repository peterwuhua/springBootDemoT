package cn.demi.cus.bj.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.bj.dao.IBudgetDao;
import cn.demi.cus.bj.dao.IBudgetDetailDao;
import cn.demi.cus.bj.po.Budget;
import cn.demi.cus.bj.po.BudgetDetail;
import cn.demi.cus.bj.service.IBudgetViewService;
import cn.demi.cus.bj.vo.BudgetDetailVo;
import cn.demi.cus.bj.vo.BudgetVo;

@Service("cus.budgetViewService")
public class BudgetViewServiceImpl extends BaseServiceImpl<BudgetVo, Budget> implements IBudgetViewService {

	@Autowired
	private IBudgetDao budgetDao;
	@Autowired
	private IBudgetDetailDao budgetDetailDao;

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, BudgetVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = budgetDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Budget>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public BudgetVo findById(String id) throws GlobalException {
		Budget po = budgetDao.findById(id);
		BudgetVo vo = new BudgetVo();
		vo = vo.toVo(po);
		List<BudgetDetail> detailList = budgetDetailDao.findByProperty("budget.id", po.getId());
		List<BudgetDetailVo> detailVoList = new ArrayList<BudgetDetailVo>();
		if (null != detailList) {
			for (BudgetDetail detail : detailList) {
				BudgetDetailVo detialVo = new BudgetDetailVo();
				detialVo = detialVo.toVo(detail);
				detailVoList.add(detialVo);
			}
		}
		vo.setDetailList(detailVoList);
		return vo;
	}

}
