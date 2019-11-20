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
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.qlt.service.IComplainService;
import cn.demi.qlt.vo.ComplainVo;

@Controller("qlt.complainAction")
@RequestMapping("/qlt/complain")
public class ComplainAction extends BaseAction<ComplainVo> {
	
	final String VIEW_PATH = "/qlt/complain/complain";
	
	@Autowired 
	private IComplainService complainService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ComplainVo> baseService() {
		return complainService;
	}
	@Override
	public ModelAndView edit(ComplainVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
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
	@Log(operation=Log.Operation.ADD,content="新增投诉信息",module="业务投诉")
	public Status addData(ComplainVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改投诉信息",module="业务投诉")
	public Status updateData(ComplainVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="删除投诉信息",module="业务投诉")
	public ModelAndView update2del(ComplainVo v, RedirectAttributes attr) throws GlobalException {
		return super.update2del(v, attr);
	}
}