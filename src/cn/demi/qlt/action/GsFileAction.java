package cn.demi.qlt.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.service.IGsFileService;
import cn.demi.qlt.vo.GsVo;

@Controller("qlt.gsFileAction")
@RequestMapping("/qlt/gsFile")
public class GsFileAction extends BaseAction<GsVo> {
	final String VIEW_PATH = "/qlt/gs/file/gs_file";
	
	@Autowired 
	private IGsFileService gsFileService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<GsVo> baseService() {
		return gsFileService;
	}
	@Override
	public ModelAndView edit(GsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
		}
		if(StrUtils.isBlankOrNull(v.getCdDate())) {
			v.setCdDate(DateUtils.getCurrDateStr());
			v.setCdId(getCurrent().getAccountId());
			v.setCdName(getCurrent().getUserName());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新信息",module="管理评审")
	public Status updateData(GsVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	public GridVo gridData(GridVo gridVo, GsVo v) throws GlobalException {
		v.setStatus(QltEunm.GS_30.getStatus());
		return baseService().gridData(gridVo,v);
	}
	@Override
	public GridVo gridDatad(GridVo gridVo, GsVo v) throws GlobalException {
		v.setStatus(QltEunm.GS_30.getStatus());
		return baseService().gridDatad(gridVo,v);
	}
}