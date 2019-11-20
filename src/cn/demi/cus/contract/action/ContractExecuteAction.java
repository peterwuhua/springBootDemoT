package cn.demi.cus.contract.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.cus.contract.service.IContractExecuteService;
import cn.demi.cus.contract.vo.ContractExecuteVo;
/**
 * Create on : 2017年1月9日 下午6:26:28  <br>
 * Description : 合同管理合同执行情况action <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Controller("cus.contract.executeAction")
@RequestMapping("/cus/contract/execute")
public class ContractExecuteAction extends BaseAction<ContractExecuteVo> {
	final String VIEW_PATH = "/cus/contract/contract_execute";
	@Autowired private IContractExecuteService contractExecuteService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ContractExecuteVo> baseService() {
		return contractExecuteService;
	}

	@Override
	public ModelAndView importExcel(ContractExecuteVo v, String param, String type, MultipartFile file,
			RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = super.importExcel(v, param, type, file, attr);
		mav.addObject("contractVo.id",param);
		return mav;
	}
	
	@Override
	public ModelAndView add(ContractExecuteVo v, RedirectAttributes arg1) throws GlobalException {
		ModelAndView mav = super.add(v, arg1);
		mav.addObject("contractVo.id", v.getContractVo().getId());
		return mav;
	}
	
	@Override
	public ModelAndView update(ContractExecuteVo v, RedirectAttributes arg1) throws GlobalException {
		ModelAndView mav =  super.update(v, arg1);
		mav.addObject("contractVo.id", v.getContractVo().getId());
		return mav;
	}
	@Override
	public ModelAndView delete(ContractExecuteVo v, RedirectAttributes arg1) throws GlobalException {
		ModelAndView mav =  super.delete(v, arg1);
		mav.addObject("contractVo.id", v.getContractVo().getId());
		return mav;
	}
	@Override
	public ModelAndView update2del(ContractExecuteVo arg0, RedirectAttributes arg1) throws GlobalException {
		ModelAndView mav =  super.update2del(arg0, arg1);
		mav.addObject("contractVo.id", arg0.getContractVo().getId());
		return mav;
	}
	
}