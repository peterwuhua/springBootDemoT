package cn.demi.base.system.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.base.system.service.IAccountRoleService;
import cn.demi.base.system.vo.AccountRoleVo;

/**
 * 
 * Create on : 2016年11月19日 下午3:46:26  <br>
 * Description : 账户角色 <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Controller("sys.accountRoleAction")
@RequestMapping("sys/accountRole")
public class AccountRoleAction extends BaseAction<AccountRoleVo> {

	final String VIEW_PATH = "/sys/account_role/account_role";
	
	@Autowired
	private IAccountRoleService accountRoleService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<AccountRoleVo> baseService() {
		return accountRoleService;
	}
	
}
