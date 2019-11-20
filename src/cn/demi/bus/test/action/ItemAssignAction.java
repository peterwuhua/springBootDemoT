package cn.demi.bus.test.action;

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
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.vo.AccountVo;
import cn.demi.bus.test.service.IItemAssignService;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.init.std.service.IItemUserService;

/**
 * 项目测试
 * @author QuJunLong
 *
 */
@Controller("bus.itemAssignAction")
@RequestMapping("/bus/itemAssign")
public class ItemAssignAction extends BaseAction<TaskItemVo> {
	final String VIEW_PATH = "/bus/item/assign/item_assign";
	
	@Autowired
	private IItemUserService itemUserService;
	@Autowired 
	private IItemAssignService itemAssignService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TaskItemVo> baseService() {
		return itemAssignService;
	}
	@Override
	public ModelAndView edit(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<TaskItemVo> timList=null;
		if(null!=v && !StrUtils.isBlankOrNull(v.getIds())){
			timList=itemAssignService.listByIds(v.getIds());
		}
		v.setTimList(timList);
		v.setAssignUser(CurrentUtils.getCurrent().getUserName());
		v.setAssignUserId(CurrentUtils.getCurrent().getAccountId());
		v.setAssignDate(DateUtils.getCurrDateTimeStr());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="分配项目信息",module="任务分配")
	public Status updateData(TaskItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("修改成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"修改失败",e);
			status = new Status("修改失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, TaskItemVo v) throws GlobalException {
		return itemAssignService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, TaskItemVo v) throws GlobalException {
		return itemAssignService.gridDatad(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value = "listTesterSelect.do")
	public List<AccountVo> listTesterSelect(TaskItemVo v) throws GlobalException {
		//限量方法中配置的用户
		List<AccountVo> accountList=itemUserService.findTestUserList(v.getItemId());
		return accountList;
	}
	/**
	 * 项目终止
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value ="update2Stop.do")
	@Log(operation=Log.Operation.UPDATE,content="终止项目",module="任务分配")
	public ModelAndView update2Stop(TaskItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			itemAssignService.update2Stop(v);
			status = new Status("终止成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("终止失败",e);
			status = new Status("终止失败",Status.STATUS_ERROR);
		}  
		attr.addFlashAttribute(STATUS, status);
 		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_PAGE);
		return mav;
	}
	/**
	 * 项目终止
	 * @param v
	 * @param attr
	 * @return
	 * @throws GlobalException
	 */
	@RequestMapping(value ="update2Stoped.do")
	@Log(operation=Log.Operation.UPDATE,content="终止项目",module="任务分配")
	public ModelAndView update2Stoped(TaskItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			itemAssignService.update2Stop(v);
			status = new Status("终止成功",Status.STATUS_SUCCESS);
		} catch (GlobalException e) {
			log.info("终止失败",e);
			status = new Status("终止失败",Status.STATUS_ERROR);
		}  
		attr.addFlashAttribute(STATUS, status);
 		ModelAndView mav = new ModelAndView();
		mav.setViewName(REDIRECT_2_GRID_PAGED);
		return mav;
	}
	@Override
	public ModelAndView show(TaskItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<TaskItemVo> itemList=null;
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=itemAssignService.findById(v.getId());
			itemList=itemAssignService.listByIds(v.getId());
		}
		mav.addObject("itemList", itemList);
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_show");
		return mav;
	}
}