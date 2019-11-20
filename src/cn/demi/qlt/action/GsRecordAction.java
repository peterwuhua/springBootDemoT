package cn.demi.qlt.action;

import org.apache.commons.lang3.StringUtils;
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
import cn.demi.qlt.service.IGsRecordService;
import cn.demi.qlt.service.IGsService;
import cn.demi.qlt.vo.GsRecordVo;
import cn.demi.qlt.vo.GsVo;

@Controller("qlt.gsRecordAction")
@RequestMapping("/qlt/gsRecord")
public class GsRecordAction extends BaseAction<GsRecordVo> {
	final String VIEW_PATH = "/qlt/gs/record/gs_record";
	@Autowired private IGsRecordService gsRecordService;	
	@Autowired private IGsService gsService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<GsRecordVo> baseService() {
		return gsRecordService;
	}
	@Override
	public ModelAndView edit(GsRecordVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=gsRecordService.findById(v.getId());
		}else {
			v.setUserId(getCurrent().getAccountId());
			v.setUserName(getCurrent().getUserName());
			v.setDate(DateUtils.getCurrDateStr());
		}
		if(null!=v && v.getGsVo()!=null && !StrUtils.isBlankOrNull(v.getGsVo().getId())){
			GsVo gs=gsService.findById(v.getGsVo().getId());
			v.setGsVo(gs);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增信息",module="管理评审记录")
	public Status addData(GsRecordVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@RequestMapping(value="deleteOne.do")
	@ResponseBody
	@Log(operation=Log.Operation.UPDATE,content="删除信息",module="管理评审记录")
	public Status deleteOne(GsRecordVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update2del(v.getId());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	public GridVo gridData(GridVo gridVo, GsRecordVo v) throws GlobalException {
		return baseService().gridData(gridVo,v);
	}
}