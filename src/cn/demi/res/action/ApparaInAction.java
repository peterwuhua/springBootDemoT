package cn.demi.res.action;

import java.util.List;

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
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.res.po.ApparaIn;
import cn.demi.res.service.IApparaInService;
import cn.demi.res.vo.ApparaInVo;

@Controller("res.apparaInAction")
@RequestMapping("/res/apparaIn")
public class ApparaInAction extends BaseAction<ApparaInVo> {
	final String VIEW_PATH = "/res/appara_in/appara_in";
	@Autowired private IApparaInService apparaInService;
	@Autowired private IAccountService accountService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ApparaInVo> baseService() {
		return apparaInService;
	}
	@Override
	public ModelAndView edit(ApparaInVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=apparaInService.findById(v.getId());
			v.setUseTime(DateUtils.getCurrDateTimeStr());
			long time=DateUtils.getIntevalMinutes(v.getOutVo().getUseTime(), v.getUseTime());
			String dt="";
			if(time/(24*60)>0) {
				dt+=time/(24*60)+"天";
				time=time%(24*60);
			}
			if(time/60>0) {
				dt+=time/60+"小时";
				time=time%60;
			}
			if(time>0) {
				dt+=time+"分钟";
			}
			v.setDuration(dt);
			List<AccountVo> userList=accountService.listByIds(v.getCyIds());
			mav.addObject("userList", userList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(ApparaInVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=apparaInService.findById(v.getId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新入库信息",module="入库管理")
	public Status updateData(ApparaInVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	public ModelAndView gridPage(ApparaInVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v.setStatus(ApparaIn.ST_0);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	public ModelAndView gridPaged(ApparaInVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v.setStatus(ApparaIn.ST_1);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_ed_page");
		return mav;
	}
}