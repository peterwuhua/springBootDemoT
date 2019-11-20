package cn.demi.cus.bj.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.bj.vo.BudgetVo;

@Transactional
public interface IBudgetViewService extends IBaseService<BudgetVo> {
	
	/**
	 * 
	 * <p>Title: findById</p>   
	 * <p>Description: 根据报价id查询报价信息</p>   
	 * @param id
	 * @return
	 * @throws GlobalException   
	 * @see cn.core.framework.common.service.IBaseService#findById(java.lang.String)
	 */
	public BudgetVo findById(String id) throws GlobalException;
	
}
