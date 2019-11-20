package cn.demi.cus.customer.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.cus.customer.service.IAccountRoleService;
import cn.demi.cus.customer.vo.AccountRoleVo;

@Controller("cus.account_roleAction")
@RequestMapping("/cus/account_role")
public class AccountRoleAction extends BaseAction<AccountRoleVo> {
	final String VIEW_PATH = "/cus/account_role/accountRole";
	@Autowired private IAccountRoleService accountRoleService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<AccountRoleVo> baseService() {
		return accountRoleService;
	}
}