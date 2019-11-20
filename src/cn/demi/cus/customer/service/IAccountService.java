package cn.demi.cus.customer.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.AccountVo;

@Transactional
public interface IAccountService extends IBaseService<AccountVo> {
	/**
	 * Create on : Danlee Li 2017年4月6日 上午11:14:53 <br>
	 * Description : 根据登录名查找登录信息 <br>
	 * @param loginName
	 * @return
	 * @throws GlobalException
	 */
	public AccountVo find(String loginName)throws GlobalException;
	
}
