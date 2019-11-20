package cn.demi.cus.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.ContactsVo;
/**
 * Create on : 2016年11月15日 下午2:53:26  <br>
 * Description : 联系人service <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Transactional
public interface IContactsService extends IBaseService<ContactsVo> {
	
	/**
	 * Description :根据客户ID获取JSON分页对象  <br>
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridData4Tab(GridVo gridVo,ContactsVo v) throws GlobalException;
	
	public List<ContactsVo> listByCustId(String custId)throws GlobalException;
	
	
	public List<ContactsVo> listByCustName(String custName)throws GlobalException;
}
