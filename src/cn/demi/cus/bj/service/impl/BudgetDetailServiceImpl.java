package cn.demi.cus.bj.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.cus.bj.po.BudgetDetail;
import cn.demi.cus.bj.service.IBudgetDetailService;
import cn.demi.cus.bj.vo.BudgetDetailVo;

@Service("cus.budgetDetailService")
public class BudgetDetailServiceImpl extends BaseServiceImpl<BudgetDetailVo,BudgetDetail> implements
		IBudgetDetailService {
}
