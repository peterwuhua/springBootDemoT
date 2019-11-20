package cn.demi.qlt.action;

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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.StrUtils;
import cn.demi.qlt.service.INsItemService;
import cn.demi.qlt.vo.NsItemVo;

@Controller("qlt.nsItemAction")
@RequestMapping("/qlt/nsItem")
public class NsItemAction extends BaseAction<NsItemVo> {
	final String VIEW_PATH = "/qlt/ns/item/ns_item";
	@Autowired private INsItemService nsItemService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<NsItemVo> baseService() {
		return nsItemService;
	}
	
	@Override
	public ModelAndView edit(NsItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=baseService().findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	public ModelAndView gridPage(NsItemVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		List<NsItemVo> itemList=nsItemService.treeList(null);
		mav.addObject("itemList", itemList);
		mav.setViewName(getViewPath()+"_page");
		return mav;
	}
	@Override
	@Log(operation=Log.Operation.ADD,content="新增要素",module="内审要素")
	public Status addData(NsItemVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新要素",module="内审要素")
	public Status updateData(NsItemVo v, RedirectAttributes attr) throws GlobalException {
		try {
			baseService().update(v);
			status = new Status("更新成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"更新失败",e);
			status = new Status("更新失败",Status.STATUS_ERROR);
		}
		return status;
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存要素",module="内审要素")
	public ModelAndView save(NsItemVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="删除要素",module="内审要素")
	public ModelAndView update2del(NsItemVo v, RedirectAttributes attr) throws GlobalException {
		return super.update2del(v, attr);
	}
	
}