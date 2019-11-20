package cn.demi.office.action;

import org.apache.commons.lang3.StringUtils;
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
import cn.demi.office.service.IDgAuditService;
import cn.demi.office.vo.DgVo;
/**
 * 打卡 审核
 * @author QuJunLong
 *
 */
@Controller("office.dgAuditAction")
@RequestMapping("/office/dgAudit")
public class DgAuditAction extends BaseAction<DgVo> {
	final String VIEW_PATH = "/office/dg_audit/dg_audit";
	@Autowired 
	private IDgAuditService dgAuditService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<DgVo> baseService() {
		return dgAuditService;
	}
	@Override
	public ModelAndView edit(DgVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=dgAuditService.findById(v.getId());
		}
		v.setAuditDate(DateUtils.getCurrDateTimeStr());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	 
	@ResponseBody
	@RequestMapping(value="update2Data.do")
	@Log(operation=Log.Operation.UPDATE,content="补卡审核",module="补卡审核")
	public Status update2Data(DgVo v, RedirectAttributes attr) throws GlobalException {
		try {
			dgAuditService.update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
}