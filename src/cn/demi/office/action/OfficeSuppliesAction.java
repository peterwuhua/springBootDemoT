package cn.demi.office.action;

import java.util.List;

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
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.ICodeService;
import cn.demi.office.service.IOfficeSuppliesService;
import cn.demi.office.vo.OfficeSuppliesVo;
/**
 * 
 * @ClassName:  OfficeSuppliesAction   
 * @Description:  办公用品Action 
 * @author: 吴华 
 * @date:   2019年4月9日 下午4:23:25       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
@Controller("office.suppliesAction")
@RequestMapping("/office/supplies")
public class OfficeSuppliesAction extends BaseAction<OfficeSuppliesVo> {
	final String VIEW_PATH = "/office/office_supplies/office_supplies";
	@Autowired private IOfficeSuppliesService officeSuppliesService;	
	@Autowired
	private ICodeService codeService;	
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<OfficeSuppliesVo> baseService() {
		return officeSuppliesService;
	}
	
	
	@ResponseBody
	@RequestMapping(value = {GRID_DATA,GRID_DATA_JSON})
	public GridVo gridData(GridVo gridVo,OfficeSuppliesVo v) throws GlobalException{
		return baseService().gridData(gridVo,v);
	}
	
	@RequestMapping(value=GRID_PAGE)
	public ModelAndView gridPage(OfficeSuppliesVo v) throws GlobalException{
		ModelAndView mav = new ModelAndView();
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	
	/**
	 * 
	 * @Title: edit   
	 * @Description: 办公用品采购编辑页 
	 * @param: @param v
	 * @param: @return
	 * @param: @throws GlobalException      
	 * @return: ModelAndView      
	 * @throws
	 */
	@Override
	public ModelAndView edit(OfficeSuppliesVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if (null != v && !StrUtils.isBlankOrNull(v.getId())) {
			BeanUtils.copyProperties(baseService().findById(v.getId()), v);
		}else {
			v.setSqDate(DateUtils.getCurrDateStr());
		}
		List<String> priceList = codeService.listByCode("bgyp-zj"); // 总价格
		mav.addObject("priceList", priceList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath() + "_edit");
		return mav;
	}
	
	
	
	@Override
	@Log(operation = Log.Operation.ADD, content = "办公用品保存", module = "行政模块：办公用品申请")
	public ModelAndView save(OfficeSuppliesVo v, RedirectAttributes attr) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		try {
			officeSuppliesService.saveBgyp(v);
			status = new Status("保存成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("保存失败",e);
			status = new Status("保存失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		mav.addObject("id", v.getId());
		mav.setViewName(REDIRECT_2_EDIT);
		return mav;
	}

	
	@Override
	@Log(operation = Log.Operation.ADD, content = "办公用品新增/提交", module = "行政模块：办公用品申请")
	public Status addData(OfficeSuppliesVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().add(v);
			status = new Status("新增成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "新增失败", e);
			status = new Status("新增失败", Status.STATUS_ERROR);
		}
		return status;
	}

	@Override
	@Log(operation = Log.Operation.UPDATE, content = "办公用品修改/提交", module = "行政模块：办公用品申请")
	public Status updateData(OfficeSuppliesVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("修改成功", Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "修改失败", e);
			status = new Status("修改失败", Status.STATUS_ERROR);
		}
		return status;
	}
	
	
	
	
}