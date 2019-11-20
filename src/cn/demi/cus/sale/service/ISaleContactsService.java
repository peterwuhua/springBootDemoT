package cn.demi.cus.sale.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.sale.vo.SaleContactsVo;

@Transactional
public interface ISaleContactsService extends IBaseService<SaleContactsVo> {
	
	public List<SaleContactsVo> listByIds(String cusId,String salerId) throws GlobalException;

	public List<SaleContactsVo> listBypubcId(String cusId) throws GlobalException;
	
	public List<SaleContactsVo> listBySalerId(String salerId) throws GlobalException;
	
	
}
