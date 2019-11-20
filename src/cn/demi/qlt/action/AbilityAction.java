package cn.demi.qlt.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.qlt.po.Ability;
import cn.demi.qlt.service.IAbilityService;
import cn.demi.qlt.vo.AbilityVo;

@Controller("qlt.abilityAction")
@RequestMapping("/qlt/ability")
public class AbilityAction extends BaseAction<AbilityVo> {
	final String VIEW_PATH = "/qlt/ability/ability";
	@Autowired private IAbilityService abilityService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<AbilityVo> baseService() {
		return abilityService;
	}
	@Override
	public ModelAndView edit(AbilityVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=baseService().findById(v.getId());
		}else {
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
			v.setDate(DateUtils.getCurrDateStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	
	@Override
	@Log(operation=Log.Operation.ADD,content="新增比对验证",module="比对验证")
	public Status addData(AbilityVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="更新比对验证",module="比对验证")
	public Status updateData(AbilityVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="删除比对验证",module="比对验证")
	public ModelAndView delete(AbilityVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}
	
	@Override
	public ModelAndView gridPage(AbilityVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	public GridVo gridData(GridVo gridVo, AbilityVo v) throws GlobalException {
		return super.gridData(gridVo, v);
	}
//	//比对审核
//	@RequestMapping(value ="gridPage4Audit.do")
//	public ModelAndView gridPage4Audit(AbilityVo v) throws GlobalException {
//		ModelAndView mav = new ModelAndView();
//		mav.addObject(VO, v);
//		mav.setViewName(getViewPath()+"_audit_page");
//		return mav;
//	}
//	//比对审核
//	@ResponseBody
//	@RequestMapping(value ="gridData4Audit.do")
//	public GridVo gridData4Audit(GridVo gridVo, AbilityVo v) throws GlobalException {
//		v.setStatus(Ability.ST_1);
//		return super.gridData(gridVo, v);
//	}
//	@RequestMapping(value ="edit4Audit.do")
//	public ModelAndView edit4Audit(AbilityVo v) throws GlobalException {
//		ModelAndView mav = new ModelAndView();
//		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
//			v=baseService().findById(v.getId());
//		}
//		mav.addObject(VO, v);
//		mav.setViewName(getViewPath()+"_audit_edit");
//		return mav;
//	}
//	@ResponseBody
//	@RequestMapping(value ="update4Audit.do")
//	@Log(operation=Log.Operation.ADD,content="更新比对验证",module="比对验证")
//	public Status update4Audit(AbilityVo v, RedirectAttributes attr) throws GlobalException {
//		try {
//			abilityService.update4Audit(v);
//			status = new Status("更新成功",Status.STATUS_SUCCESS);
//		}catch (GlobalException e) {
//			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
//			status = new Status("更新失败",Status.STATUS_ERROR);
//		}
//		return status;
//	}
	//记录
	@RequestMapping(value ="gridPage4Record.do")
	public ModelAndView gridPage4Record(AbilityVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_record_page");
		return mav;
	}
	//结果记录
	@ResponseBody
	@RequestMapping(value ="gridData4Record.do")
	public GridVo gridData4Record(GridVo gridVo, AbilityVo v) throws GlobalException {
		v.setStatus(Ability.ST_1);
		return super.gridData(gridVo, v);
	}
	@RequestMapping(value ="edit4Record.do")
	public ModelAndView edit4Record(AbilityVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=baseService().findById(v.getId());
			v.setJlId(getCurrent().getAccountId());
			v.setJlName(getCurrent().getUserName());
			v.setJlDate(DateUtils.getCurrDateStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_record_edit");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value ="update4Record.do")
	@Log(operation=Log.Operation.ADD,content="更新比对验证",module="比对验证")
	public Status update4Record(AbilityVo v, RedirectAttributes attr) throws GlobalException {
		try {
			abilityService.update4Record(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	//比对查询
	@RequestMapping(value ="gridPage4Query.do")
	public ModelAndView gridPage4Query(AbilityVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_query_page");
		return mav;
	}
	//查询
	@ResponseBody
	@RequestMapping(value ="gridData4Query.do")
	public GridVo gridData4Query(GridVo gridVo, AbilityVo v) throws GlobalException {
		return abilityService.gridData4Query(gridVo,v);
	}
}