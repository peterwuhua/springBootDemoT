package cn.demi.res.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.demi.res.service.IReagentService;
import cn.demi.res.service.IRegOutInService;
import cn.demi.res.vo.ReagentVo;
import cn.demi.res.vo.RegOutInVo;

/** 
 * <strong>Description : 试剂action</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 */
@Controller("res.reagentAction")
@RequestMapping("/res/reagent")
public class ReagentAction extends BaseAction<ReagentVo> {
	final String VIEW_PATH = "/res/reagent/reagent";
	@Autowired private IReagentService reagentService;	
	@Autowired private IRegOutInService regOutInService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ReagentVo> baseService() {
		return reagentService;
	}
	
	/** <strong>Create on : Hans He 2017年5月24日 下午1:15:56 </strong> <br>
	 * <strong>Description :出库页面 </strong> <br>
	 * @param v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping("/outDepot")
	public ModelAndView outDepot(ReagentVo v) throws GlobalException{
		v = reagentService.findById(v.getId());
		RegOutInVo vo = new RegOutInVo();
		vo.setReagentVo(v);
		ModelAndView mv = new ModelAndView();
		mv.addObject(VO, vo);
		mv.setViewName(VIEW_PATH + "_outDepot_page");
		return mv;
	}
	/** <strong>Create on : Hans He 2017年5月24日 下午1:15:56 </strong> <br>
	 * <strong>Description :入库页面 </strong> <br>
	 * @param v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping("/inDepot")
	public ModelAndView inDepot(ReagentVo v) throws GlobalException{
		v = reagentService.findById(v.getId());
		RegOutInVo vo = new RegOutInVo();
		vo.setReagentVo(v);
		ModelAndView mv = new ModelAndView();
		mv.addObject(VO, vo);
		mv.setViewName(VIEW_PATH + "_inDepot_page");
		return mv;
	}
	/** <strong>Create on : Hans He 2017年5月24日 下午1:15:56 </strong> <br>
	 * <strong>Description :出库库提交 </strong> <br>
	 * @param v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping("/addOutDepot")
	public ModelAndView addOutIn(RegOutInVo v,RedirectAttributes attr) throws GlobalException{
		try {
			regOutInService.addOutDepot(v);
			status = new Status("操作成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"操作失败",e);
			status = new Status("操作失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/res/reagent/gridPage.do");
		return mv;
	}
	/** <strong>Create on : Hans He 2017年5月24日 下午1:15:56 </strong> <br>
	 * <strong>Description :入库提交 </strong> <br>
	 * @param v
	 * @return ModelAndView
	 * @throws GlobalException
	 */
	@RequestMapping("/addInDepot")
	public ModelAndView addInDepot(RegOutInVo v,RedirectAttributes attr) throws GlobalException{
		try {
			regOutInService.addInDepot(v);
			status = new Status("操作成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"操作失败",e);
			status = new Status("操作失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/res/reagent/gridPage.do");
		return mv;
	}
	@Override
	public ModelAndView gridPage(ReagentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增试剂信息",module="试剂管理")
	public Status addData(ReagentVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改试剂信息",module="试剂管理")
	public Status updateData(ReagentVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@ResponseBody
	@RequestMapping(value="copyData.do")
	@Log(operation=Log.Operation.ADD,content="复制信息",module="试剂管理")
	public Status copyData(ReagentVo v, RedirectAttributes attr) throws GlobalException {
		try {
			String targetId = reagentService.copy(v);
			status = new Status("copy成功",Status.STATUS_SUCCESS);
			status.setObject(targetId);
		} catch (GlobalException e) {
			log.info("copy失败",e);
			status = new Status("copy成功",Status.STATUS_ERROR);
		}
		return status;
	}
}
