package cn.demi.qlt.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.qlt.service.IVisitService;
import cn.demi.qlt.vo.VisitVo;

@Controller("qlt.visitAction")
@RequestMapping("/qlt/visit")
public class VisitAction extends BaseAction<VisitVo> {
	final String VIEW_PATH = "/qlt/visit/visit";
	@Autowired 
	private IVisitService visitService;	
	 
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<VisitVo> baseService() {
		return visitService;
	}
	@Override
	public ModelAndView edit(VisitVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=visitService.findById(v.getId());
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
	@Log(operation=Log.Operation.ADD,content="新增回访信息",module="客户回访管理")
	public Status addData(VisitVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新回访信息",module="客户回访管理")
	public Status updateData(VisitVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.DELETE,content="删除回访信息",module="客户回访管理")
	public ModelAndView delete(VisitVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}
	/**
	 * 访问page页
	 * data数据来源cust
	 */
	@Override
	public ModelAndView gridPage(VisitVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	public ModelAndView gridPaged(VisitVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_ed_page");
		return mav;
	}
}