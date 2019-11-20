package cn.demi.init.std.action;

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
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.std.service.IItemMethodService;
import cn.demi.init.std.vo.ItemMethodVo;
import cn.demi.init.std.vo.MethodVo;

@Controller("init.item_methodAction")
@RequestMapping("/init/itemMethod")
public class ItemMethodAction extends BaseAction<ItemMethodVo> {
	final String VIEW_PATH = "/init/item_method/item_method";
	
	@Autowired 
	private IItemMethodService itemMethodService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ItemMethodVo> baseService() {
		return itemMethodService;
	}
	@Override
	public ModelAndView edit(ItemMethodVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=itemMethodService.findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@ResponseBody
	@RequestMapping(value="updateData.do")
	@Log(operation=Log.Operation.UPDATE,content="更新项目方法关系信息",module="项目方法关系")
	public Status updateData(ItemMethodVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@ResponseBody
	@RequestMapping(value = "addMethod.do")
	public Status addMethod(ItemMethodVo v, RedirectAttributes attr) throws GlobalException {
		try {
			boolean flag=itemMethodService.addMethod(v);
			if(flag) {
				status = new Status("新增成功",Status.STATUS_SUCCESS);
			}else {
				status = new Status("新增失败，数据已经存在",Status.STATUS_ERROR);
			}
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		return status;
	}
	@ResponseBody
	@RequestMapping(value = "deleteOne.do")
	public Status deleteOne(ItemMethodVo v, RedirectAttributes attr) throws GlobalException {
		try {
			itemMethodService.delete(v.getId());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		return status;
	}
	/**
	 * 根据项目Id获取关联方法
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxGetMethod.do")
	public List<ItemMethodVo> ajaxGetMethod(ItemMethodVo v) throws GlobalException {
		List<ItemMethodVo> methodList = itemMethodService.list(v);
		return methodList;
	}
	/**
	 * 根据项目Id获取关联方法
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajax4MethodList.do")
	public List<MethodVo> ajax4MethodList(ItemMethodVo v) throws GlobalException {
		List<MethodVo> methodList = itemMethodService.findMethodList(v.getItemVo().getId());
		return methodList;
	}
	//方法选择页面
	@RequestMapping(value="showMethod.do")
	public ModelAndView showMethod(ItemMethodVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<MethodVo> methodList=itemMethodService.findMethodList(v.getItemVo().getId());
		mav.addObject("methodList", methodList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_select2");
		return mav;
	}
}