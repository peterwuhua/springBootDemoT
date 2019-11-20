package cn.demi.cus.bill.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.bill.vo.BillVo;

@Transactional
public interface IBillService extends IBaseService<BillVo> {
	void updateStatus4KJ(String ids) throws GlobalException;
	
	public GridVo gridData(GridVo gridVo, BillVo v) throws GlobalException;
	public GridVo gridData4Waudit(GridVo gridVo, BillVo v) throws GlobalException;
	
	public GridVo gridData4Wview(GridVo gridVo, BillVo v) throws GlobalException;

	public GridVo gridData4audit(GridVo gridVo, BillVo v) throws GlobalException;
	
	public GridVo gridData4view(GridVo gridVo, BillVo v) throws GlobalException; 
	
	public BillVo findBillById(String id) throws GlobalException; 
	
}
