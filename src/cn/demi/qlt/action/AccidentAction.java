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
import cn.demi.qlt.po.Accident;
import cn.demi.qlt.service.IAccidentService;
import cn.demi.qlt.vo.AccidentVo;

@Controller("qlt.accidentAction")
@RequestMapping("/qlt/accident")
public class AccidentAction extends BaseAction<AccidentVo> {
	final String VIEW_PATH = "/qlt/accident/accident";
	@Autowired private IAccidentService accidentService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<AccidentVo> baseService() {
		return accidentService;
	}
	
	@Override
	public ModelAndView edit(AccidentVo v) throws GlobalException {
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
	@Log(operation=Log.Operation.ADD,content="新增事故信息",module="事故管理")
	public Status addData(AccidentVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="更新事故信息",module="事故管理")
	public Status updateData(AccidentVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="删除事故信息",module="事故管理")
	public ModelAndView delete(AccidentVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}
	
	@Override
	public ModelAndView gridPage(AccidentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	public GridVo gridData(GridVo gridVo, AccidentVo v) throws GlobalException {
		return super.gridData(gridVo, v);
	}
	//事故处理
	@RequestMapping(value ="gridPage4Audit.do")
	public ModelAndView gridPage4Audit(AccidentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_audit_page");
		return mav;
	}
	//事故处理
	@ResponseBody
	@RequestMapping(value ="gridData4Audit.do")
	public GridVo gridData4Audit(GridVo gridVo, AccidentVo v) throws GlobalException {
		v.setStatus(Accident.ST_1);
		return super.gridData(gridVo, v);
	}
	@RequestMapping(value ="edit4Audit.do")
	public ModelAndView edit4Audit(AccidentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=baseService().findById(v.getId());
			v.setAuditId(getCurrent().getAccountId());
			v.setAuditName(getCurrent().getUserName());
			v.setAuditDate(DateUtils.getCurrDateStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_audit_edit");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value ="update4Audit.do")
	@Log(operation=Log.Operation.ADD,content="更新事故信息",module="事故管理")
	public Status update4Audit(AccidentVo v, RedirectAttributes attr) throws GlobalException {
		try {
			accidentService.update4Audit(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	
	//事故查询
	@RequestMapping(value ="gridPage4Query.do")
	public ModelAndView gridPage4Query(AccidentVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_query_page");
		return mav;
	}
	//事故查询
	@ResponseBody
	@RequestMapping(value ="gridData4Query.do")
	public GridVo gridData4Query(GridVo gridVo, AccidentVo v) throws GlobalException {
		return accidentService.gridData4Query(gridVo,v);
	}
}