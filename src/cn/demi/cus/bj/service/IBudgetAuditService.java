package cn.demi.cus.bj.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.cus.bj.vo.BudgetVo;

@Transactional
public interface IBudgetAuditService extends IBaseService<BudgetVo> {
	
}
