package cn.demi.res.action;

import java.util.List;

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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IAccountService;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.res.po.ApparaOut;
import cn.demi.res.service.IApparaOutService;
import cn.demi.res.vo.ApparaOutVo;
import cn.demi.res.vo.ApparaVo;

@Controller("res.apparaOutAction")
@RequestMapping("/res/apparaOut")
public class ApparaOutAction extends BaseAction<ApparaOutVo> {
	final String VIEW_PATH = "/res/appara_out/appara_out";
	@Autowired private IApparaOutService apparaOutService;	
	@Autowired private IAccountService accountService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ApparaOutVo> baseService() {
		return apparaOutService;
	}
	@Override
	public ModelAndView edit(ApparaOutVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=apparaOutService.findById(v.getId());
			v.setUseTime(DateUtils.getCurrDateTimeStr());
			List<AccountVo> userList=accountService.listByIds(v.getCyIds());
			mav.addObject("userList", userList);
			List<ApparaVo> appList=apparaOutService.listApp(v);
			mav.addObject("appList", appList);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(ApparaOutVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v=apparaOutService.findById(v.getId());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新出库信息",module="出库管理")
	public Status updateData(ApparaOutVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增出库信息",module="出库管理")
	public Status addData(ApparaOutVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@ResponseBody
	@RequestMapping(value ="listData.do")
	public List<ApparaOutVo> listData(ApparaOutVo v) throws GlobalException {
		return apparaOutService.listData(v);
	}
	@Override
	public ModelAndView gridPage(ApparaOutVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v.setStatus(ApparaOut.ST_0);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	public ModelAndView gridPaged(ApparaOutVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		v.setStatus(ApparaOut.ST_1);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_ed_page");
		return mav;
	}
	@Override
	@RequestMapping(value="update2del.do")
	public ModelAndView update2del(ApparaOutVo v, RedirectAttributes attr) throws GlobalException {
		try {
			apparaOutService.update2del(v.getIds());
			status = new Status("取消成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("取消失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
}