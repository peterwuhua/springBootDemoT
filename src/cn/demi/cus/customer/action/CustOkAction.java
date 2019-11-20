package cn.demi.cus.customer.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.cus.customer.service.ICustOkService;
import cn.demi.cus.customer.vo.CustOkVo;

@Controller("cus.custOkAction")
@RequestMapping("/cus/custOk")
public class CustOkAction extends BaseAction<CustOkVo> {
	final String VIEW_PATH = "/cus/cust_ok/cust_ok";
	@Autowired private ICustOkService custOkService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<CustOkVo> baseService() {
		return custOkService;
	}
}