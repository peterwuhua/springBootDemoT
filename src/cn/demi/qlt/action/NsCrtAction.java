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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.service.INsCrtService;
import cn.demi.qlt.vo.NsRecordVo;
/**
 * 纠正措施
 * @author QuJunLong
 *
 */
@Controller("qlt.nsCrtAction")
@RequestMapping("/qlt/nsCrt")
public class NsCrtAction extends BaseAction<NsRecordVo> {
	final String VIEW_PATH = "/qlt/ns/crt/ns_crt";
	@Autowired private INsCrtService nsCrtService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<NsRecordVo> baseService() {
		return nsCrtService;
	}
	@Override
	public ModelAndView edit(NsRecordVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=nsCrtService.findById(v.getId());
			if(StrUtils.isBlankOrNull(v.getGzDate())) {
				v.setGzDate(DateUtils.getCurrDateStr());
			}
			if(StrUtils.isBlankOrNull(v.getGzId())) {
				v.setGzId(getCurrent().getAccountId());
				v.setGzName(getCurrent().getUserName());
			}
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(NsRecordVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = nsCrtService.find4Show(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新纠正措施",module="纠正措施")
	public Status updateData(NsRecordVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	public GridVo gridData(GridVo gridVo, NsRecordVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_M_10.getStatus());
		return super.gridData(gridVo, v);
	}
	@Override
	public GridVo gridDatad(GridVo gridVo, NsRecordVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_M_10.getStatus());
		return super.gridDatad(gridVo, v);
	}
	
	
}