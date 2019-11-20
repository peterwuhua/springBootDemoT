package cn.demi.bus.sample.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.sample.service.ISampUsedService;
import cn.demi.bus.sample.service.ISamplingService;
import cn.demi.bus.sample.vo.SampUsedVo;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.init.st.service.ISampTypeService;
/**
 * 样品清单
 * @author QuJunLong
 */
@Controller("bus.samplingAction")
@RequestMapping("/bus/sampling")
public class SamplingAction extends BaseAction<SamplingVo> {
	final String VIEW_PATH = "/bus/samp/sampling/sampling";
	@Autowired 
	private ISamplingService samplingService;	
	@Autowired 
	private ISampTypeService sampTypeService;
	@Autowired 
	private ISampUsedService sampUsedService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<SamplingVo> baseService() {
		return samplingService;
	}
	@Override
	public ModelAndView edit(SamplingVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=samplingService.findById(v.getId());
			//表单类型
			String type=sampTypeService.findById(v.getSampTypeId()).getType();
			mav.addObject("type", type);
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@RequestMapping(value = "edit4Deal.do")
	public ModelAndView edit4Deal(SamplingVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getIds())){
			List<SamplingVo> sampList=samplingService.listByIds(v.getIds());
			mav.addObject("sampList", sampList);
		}
		v.setDealDate(DateUtils.getCurrDateStr());
		v.setDealRequest(Constants.SAMP_ZQ);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit_deal");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "updateData.do")
	@Log(operation=Log.Operation.UPDATE,content="更新留样信息",module="样品信息")
	public Status updateData(SamplingVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value = "update4Deal.do")
	@Log(operation=Log.Operation.UPDATE,content="更新样品处理信息",module="样品信息")
	public Status update4Deal(SamplingVo v, RedirectAttributes attr) throws GlobalException {
		try {
			samplingService.update4Deal(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	public ModelAndView show(SamplingVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v = baseService().findById(v.getId());
			//表单类型
			String type=sampTypeService.findById(v.getSampTypeId()).getType();
			mav.addObject("type", type);
		}
		List<SampUsedVo> suList=sampUsedService.listBySampId(v.getId());
		mav.addObject("suList", suList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value = "deleteSamp.do")
	@Log(operation=Log.Operation.DELETE,content="删除样品信息",module="样品信息")
	public Status deleteSamp(String id, String sort) throws GlobalException {
		try {
			samplingService.deleteSamp(id);
			 status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.warn(e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value = "copySamp.do")
	@Log(operation=Log.Operation.ADD,content="复制样品信息",module="样品信息")
	public Status copySamp(String id, String sort) throws GlobalException {
		try {
			samplingService.copySamp(id);
			 status = new Status("复制成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.warn(e);
			status = new Status("复制失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value = "addSamp.do")
	@Log(operation = Log.Operation.ADD, content = "添加样品信息", module = "样品管理")
	public Status addSamp(@RequestParam(value = "pointId") String pointId, @RequestParam(value = "type") String type,@RequestParam(value = "ids",required=false) String ids, RedirectAttributes attr)
			throws GlobalException {
		try {
			boolean f = samplingService.addSamp(pointId, type,ids);
			if (f) {
				status = new Status("新增样品成功", Status.STATUS_SUCCESS);
			} else {
				status = new Status("新增样品失败,若为空白样，请检查是否已存在", Status.STATUS_ERROR);
			}
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@ResponseBody
	@RequestMapping(value = "delSamp.do")
	@Log(operation = Log.Operation.DELETE, content = "删除样品信息", module = "样品管理")
	public Status delSamp(@RequestParam(value = "pointId") String pointId, @RequestParam(value = "type") String type, RedirectAttributes attr)
			throws GlobalException {
		try {
			boolean f = samplingService.delSamp(pointId, type);
			if (f) {
				status = new Status("删除成功", Status.STATUS_SUCCESS);
			} else {
				status = new Status("删除失败", Status.STATUS_ERROR);
			}
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "删除失败", e);
			status = new Status("删除失败", Status.STATUS_ERROR);
		}
		return status;
	}
}