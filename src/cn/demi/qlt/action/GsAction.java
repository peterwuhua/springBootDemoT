package cn.demi.qlt.action;

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
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.service.IGsService;
import cn.demi.qlt.vo.GsVo;

@Controller("qlt.gsAction")
@RequestMapping("/qlt/gs")
public class GsAction extends BaseAction<GsVo> {
	final String VIEW_PATH = "/qlt/gs/plan/gs";
	@Autowired private IGsService gsService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<GsVo> baseService() {
		return gsService;
	}
	@Override
	public ModelAndView edit(GsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=gsService.findById(v.getId());
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
	@Log(operation=Log.Operation.ADD,content="新增信息",module="管理评审")
	public Status addData(GsVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="管理评审")
	public Status updateData(GsVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存信息",module="管理评审")
	public ModelAndView save(GsVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="删除信息",module="管理评审")
	public ModelAndView update2del(GsVo v, RedirectAttributes attr) throws GlobalException {
		return super.update2del(v, attr);
	}
	
	@ResponseBody
	@RequestMapping(value = "gridData4Record.do")
	public GridVo gridData4Record(GridVo gridVo, GsVo v) throws GlobalException {
		v.setStatus(QltEunm.GS_20.getStatus());
		return gsService.gridData4Record(gridVo,v);
	}
}