package cn.demi.cus.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.VisitVo;
import cn.demi.cus.sale.vo.SaleContactsVo;
import cn.demi.ext.common.vo.TxVo;

@Transactional
public interface IVisitService extends IBaseService<VisitVo> {

	
	public GridVo gridData4Visit(GridVo gridVo,VisitVo v) throws GlobalException;
	
	
	/**
	 * 
	 * @Title: list4Tx   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param list
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: List<TxVo>      
	 * @throws
	 */
	public List<TxVo> list4Tx(List<TxVo> list) throws GlobalException;
	
	
	public List<VisitVo> listByIds(String salerId ,String custId)  throws GlobalException;
	
	
	public void updateVisitByGZId(SaleContactsVo v) throws GlobalException;
	
	
	public void saveVisitsByLoginUser(String auth) throws GlobalException;
	
	
	
	
}
