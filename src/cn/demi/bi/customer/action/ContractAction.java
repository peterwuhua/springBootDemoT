package cn.demi.bi.customer.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bi.customer.service.IContractService;
import cn.demi.cus.contract.vo.ContractVo;

/**
 * <strong>合同 统计分析 action </strong> <br>
 */
@Controller("bi.ContractAction")
@RequestMapping("bi/contract")
public class ContractAction extends BaseAction<ContractVo> {
	
	final String VIEW_PATH = "/bi/contract/contract";
	@Autowired
	private IContractService contractService;

	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}

	@Override
	public IBaseService<ContractVo> baseService() {
		return contractService;
	}
	
	@Override
	public ModelAndView gridPage(ContractVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	public GridVo gridData(GridVo gridVo, ContractVo v) throws GlobalException {
		return baseService().gridData(gridVo,v);
	}
	 
	
}
