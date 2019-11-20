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
import cn.demi.qlt.service.INsRecordService;
import cn.demi.qlt.vo.NsRecordVo;

@Controller("qlt.nsRecordAction")
@RequestMapping("/qlt/nsRecord")
public class NsRecordAction extends BaseAction<NsRecordVo> {
	final String VIEW_PATH = "/qlt/ns/record/ns_record";
	@Autowired private INsRecordService nsRecordService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<NsRecordVo> baseService() {
		return nsRecordService;
	}
	@Override
	public ModelAndView edit(NsRecordVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=nsRecordService.findById(v.getId());
			if(StrUtils.isBlankOrNull(v.getDate())) {
				v.setDate(DateUtils.getCurrDateStr());
				v.setUserId(getCurrent().getAccountId());
				v.setUserName(getCurrent().getUserName());
			}
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新内审记录",module="内审记录")
	public Status updateData(NsRecordVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	public GridVo gridData(GridVo gridVo, NsRecordVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_M_00.getStatus());
		v.setUserId(getCurrent().getAccountId());
		return super.gridData(gridVo, v);
	}
	@Override
	public GridVo gridDatad(GridVo gridVo, NsRecordVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_M_00.getStatus());
		return super.gridDatad(gridVo, v);
	}
	
	
}