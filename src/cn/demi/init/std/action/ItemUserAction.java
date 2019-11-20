package cn.demi.init.std.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IOrgService;
import cn.demi.base.system.vo.OrgVo;
import cn.demi.init.std.service.IItemUserService;
import cn.demi.init.std.vo.ItemUserVo;

@Controller("init.itemUserAction")
@RequestMapping("/init/itemUser")
public class ItemUserAction extends BaseAction<ItemUserVo> {
	final String VIEW_PATH = "/init/item_user/item_user";
	@Autowired 
	private IItemUserService itemUserService;	
	@Autowired
	private IOrgService orgService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ItemUserVo> baseService() {
		return itemUserService;
	}
	@Override
	public ModelAndView edit(ItemUserVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=itemUserService.findById(v.getId());
		}else {
			v.setItemId(v.getItemId());
			v.setOrgId(getCurrent().getDepId());
			v.setOrgName(getCurrent().getDepName());
		}
		mav.addObject(VO, v);
		String pid=orgService.findRoot().getId();
		if(!getCurrent().getRoleNames().contains(Constants.SUADMIN)) {
			pid=getCurrent().getOrgId();
		}
		List<OrgVo> orgList = orgService.listChild(pid);
		mav.addObject("orgList", orgList);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.DELETE,content="删除关系",module="项目检测人关系")
	public ModelAndView delete(ItemUserVo v, RedirectAttributes attr) throws GlobalException {
		try {
			itemUserService.delete(v.getIds());
			status = new Status("删除成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("删除失败",e);
			status = new Status("删除失败",Status.STATUS_ERROR);
		}  
		attr.addFlashAttribute(STATUS, status);
 		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		mav.addObject("itemId", v.getItemId());
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增关系",module="项目检测人关系")
	public ModelAndView add(ItemUserVo v, RedirectAttributes attr) throws GlobalException {
		try {
			itemUserService.add(v);
			status = new Status("新增成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		ModelAndView mav = new ModelAndView();
		attr.addFlashAttribute(STATUS, status);
		mav.addObject("itemId", v.getItemId());
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改关系",module="项目检测人关系")
	public ModelAndView update(ItemUserVo v, RedirectAttributes attr) throws GlobalException {
		try {
			itemUserService.update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);	
		ModelAndView mav = new ModelAndView();
		mav.addObject("itemId", v.getItemId());
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增关系",module="项目检测人关系")
	public Status addData(ItemUserVo v, RedirectAttributes attr) throws GlobalException {
		try {
			boolean flag=itemUserService.addData(v);
			if(!flag) {
				status = new Status("新增失败，数据已经存在",Status.STATUS_ERROR);
			}else {
				status = new Status("新增成功",Status.STATUS_SUCCESS);
			}
		} catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"新增失败",e);
			status = new Status("新增失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改关系",module="项目检测人关系")
	public Status updateData(ItemUserVo v, RedirectAttributes attr) throws GlobalException {
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