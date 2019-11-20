package cn.demi.cus.customer.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.CustomerVo;
/**
 * 
 * @ClassName:  IPubCustomerService   
 * @Description:公共池客户业务层  
 * @author: 吴华 
 * @date:   2019年1月22日 上午10:15:16       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Transactional
public interface IPubCustomerService extends IBaseService<CustomerVo>{

	/**
	 * <strong>Description : gridData </strong> <br>
	 * @param gridVo gridVo
	 * @param v v
	 * @return GridVo
	 * @throws GlobalException
	 */
	public GridVo gridData(GridVo gridVo,CustomerVo v) throws GlobalException;
	
	
}
