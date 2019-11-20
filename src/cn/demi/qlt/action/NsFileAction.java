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
import cn.demi.qlt.constant.QltEunm;
import cn.demi.qlt.service.INsFileService;
import cn.demi.qlt.vo.NsVo;

@Controller("qlt.nsFileAction")
@RequestMapping("/qlt/nsFile")
public class NsFileAction extends BaseAction<NsVo> {
	final String VIEW_PATH = "/qlt/ns/file/ns_file";
	
	@Autowired private INsFileService nsFileService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<NsVo> baseService() {
		return nsFileService;
	}
	@Override
	public ModelAndView edit(NsVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=nsFileService.findById(v.getId());
			v.setCdDate(DateUtils.getCurrDateStr());
			v.setCdId(getCurrent().getAccountId());
			v.setCdName(getCurrent().getUserName());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新存档信息",module="记录存档")
	public Status updateData(NsVo v, RedirectAttributes attr) throws GlobalException {
		try {
			nsFileService.update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
			status.setObject(v.getId());
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	public GridVo gridData(GridVo gridVo, NsVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_30.getStatus());
		return nsFileService.gridData(gridVo,v);
	}
	@Override
	public GridVo gridDatad(GridVo gridVo, NsVo v) throws GlobalException {
		v.setStatus(QltEunm.QLT_30.getStatus());
		return nsFileService.gridDatad(gridVo,v);
	}
}