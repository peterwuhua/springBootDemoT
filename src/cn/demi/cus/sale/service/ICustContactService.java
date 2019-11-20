package cn.demi.cus.sale.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.ContactsVo;
import cn.demi.cus.sale.vo.CustContactVo;

@Transactional
public interface ICustContactService extends IBaseService<CustContactVo> {
	
	public GridVo query3dsCustBirth(GridVo gridVo,CustContactVo v) throws GlobalException;

	public String[] listByContactIds(String ids) throws GlobalException;
	
	
	public void update4Contacts(List<CustContactVo> vos,ContactsVo v) throws GlobalException;
	
}
