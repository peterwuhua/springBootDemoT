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
import cn.demi.cus.contract.service.IContractDetailService;
import cn.demi.cus.contract.vo.ContractDetailVo;
/**
 * Create on : 2017年1月9日 下午6:25:18  <br>
 * Description : 合同管理项目action <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Controller("cus.contract.detailAction")
@RequestMapping("/cus/contract/detail")
public class ContractDetailAction extends BaseAction<ContractDetailVo> {
	final String VIEW_PATH = "/cus/contract/contract_detail";
	@Autowired private IContractDetailService contractDetailService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ContractDetailVo> baseService() {
		return contractDetailService;
	}
	@Override
	public ModelAndView importExcel(ContractDetailVo v, String param, String type, MultipartFile file,
			RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = super.importExcel(v, param, type, file, attr);
		mav.addObject("contractVo.id", param);
		return mav;
	}

	@Override
	public ModelAndView add(ContractDetailVo v, RedirectAttributes arg1) throws GlobalException {
		ModelAndView mav = super.add(v, arg1);
		mav.addObject("contractVo.id", v.getContractVo().getId());
		return mav;
	}
	
	@Override
	public ModelAndView update(ContractDetailVo v, RedirectAttributes arg1) throws GlobalException {
		ModelAndView mav =  super.update(v, arg1);
		mav.addObject("contractVo.id", v.getContractVo().getId());
		return mav;
	}
	
	@Override
	public ModelAndView delete(ContractDetailVo v, RedirectAttributes arg1) throws GlobalException {
		ModelAndView mav =  super.delete(v, arg1);
		mav.addObject("contractVo.id", v.getContractVo().getId());
		return mav;
	}
	
	@Override
	public ModelAndView update2del(ContractDetailVo v, RedirectAttributes arg1) throws GlobalException {
		ModelAndView mav =  super.update2del(v, arg1);
		mav.addObject("contractVo.id", v.getContractVo().getId());
		return mav;
	}
}