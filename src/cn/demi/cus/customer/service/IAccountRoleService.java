package cn.demi.cus.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.customer.vo.AccountRoleVo;

@Transactional
public interface IAccountRoleService extends IBaseService<AccountRoleVo> {
	/**
	 * Create on : Danlee Li 2016年12月12日 下午3:36:28 <br>
	 * Description :  根据客户账户查找账户角色信息<br>
	 * @param accountId
	 * @return
	 * @throws GlobalException
	 */
	public List<AccountRoleVo> listByAccountId(String accountId)
			throws GlobalException;
}
