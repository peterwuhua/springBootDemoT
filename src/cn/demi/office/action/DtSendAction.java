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
import cn.demi.office.service.IDtSendService;
import cn.demi.office.vo.DtVo;
/**
 * 文档下发
 * @author QuJunLong
 *
 */
@Controller("office.dtSendAction")
@RequestMapping("/office/dtSend")
public class DtSendAction extends BaseAction<DtVo> {
	final String VIEW_PATH = "/office/dt_send/dt_send";
	@Autowired 
	private IDtSendService dtSendService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<DtVo> baseService() {
		return dtSendService;
	}
	@Override
	public ModelAndView edit(DtVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=dtSendService.findById(v.getId());
			v.setSendTime(DateUtils.getCurrDateTimeStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@RequestMapping(value="updateData.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="更新申请单",module="文件下发")
	public Status updateData(DtVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
}