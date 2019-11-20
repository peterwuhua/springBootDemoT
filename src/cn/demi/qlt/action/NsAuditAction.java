package cn.demi.qlt.action;

import org.apache.commons.lang3.StringUtils;
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
import cn.demi.bus.pg.service.IProgressLogService;
import cn.demi.bus.pg.vo.ProgressLogVo;
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.service.INsService;
import cn.demi.qlt.vo.NsVo;
/**
 * 内审计划 审核
 * @author QuJunLong
 *
 */
@Controller("qlt.nsAuditAction")
@RequestMapping("/qlt/nsAudit")
public class NsAuditAction extends BaseAction<NsVo> {
	final String VIEW_PATH = "/qlt/ns/audit/ns_audit";
	
	@Autowired 
	private INsService nsService;	
	@Autowired 
	private IProgressLogService progressLogService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<NsVo> baseService() {
		return nsService;
	}
	@Override
	public ModelAndView edit(NsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=nsService.findById(v.getId());
			v.setAuditId(getCurrent().getAccountId());
			v.setAuditName(getCurrent().getUserName());
			v.setAuditDate(DateUtils.getCurrDateStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView show(NsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
			ProgressLogVo logVo=progressLogService.find(v.getId(), QltEunm.QLT_10.getStatus());
			mav.addObject("logVo", logVo);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新内审计划",module="内审计划审核")
	public Status updateData(NsVo v, RedirectAttributes attr) throws GlobalException {
		try {
			nsService.update4Audit(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	public GridVo gridData(GridVo gridVo, NsVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_10.getStatus());
		v.setOrgId(getCurrent().getOrgId());
		return nsService.gridData(gridVo,v);
	}
	@Override
	public GridVo gridDatad(GridVo gridVo, NsVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_10.getStatus());
		return nsService.gridDatad(gridVo,v);
	}
}