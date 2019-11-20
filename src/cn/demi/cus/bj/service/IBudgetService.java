package cn.demi.cus.bj.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.bj.vo.BudgetVo;

@Transactional
public interface IBudgetService extends IBaseService<BudgetVo> {
	
	/**
	 * 立项时选择报价列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Lx(GridVo gridVo, BudgetVo v) throws GlobalException;
	/**
	 * 报价列表 查询
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Query(GridVo gridVo, BudgetVo v) throws GlobalException;
	/**
	 * 获取客户 报价集合
	 * @param custId
	 * @return
	 * @throws GlobalException
	 */
	public List<BudgetVo> list4Cust(String custId) throws GlobalException;
}
