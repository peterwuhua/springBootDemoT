package cn.demi.cus.customer.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.cus.customer.service.IAccountService;
import cn.demi.cus.customer.vo.AccountVo;

@Controller("cus.accountAction")
@RequestMapping("/cus/account")
public class AccountAction extends BaseAction<AccountVo> {
	final String VIEW_PATH = "/cus/account/account";
	@Autowired private IAccountService accountService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<AccountVo> baseService() {
		return accountService;
	}
}