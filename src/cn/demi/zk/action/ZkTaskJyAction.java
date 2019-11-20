package cn.demi.zk.action;

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
import cn.core.framework.constant.EunmZkTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.zk.service.IZkTaskJyService;
import cn.demi.zk.vo.ZkTaskVo;
/**
 * 质控  数据复核
 * @author QuJunLong
 *
 */
@Controller("zk.taskJyAction")
@RequestMapping("/zk/taskJy")
public class ZkTaskJyAction extends BaseAction<ZkTaskVo> {
	final String VIEW_PATH = "/zk/task_jy/task_jy";
	@Autowired private IZkTaskJyService zkTaskJyService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ZkTaskVo> baseService() {
		return zkTaskJyService;
	}
	@Override
	public ModelAndView edit(ZkTaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=zkTaskJyService.findById(v.getId());
		}
		v.setAuditDate(DateUtils.getCurrDateStr());
		v.setAuditId(getCurrent().getAccountId());
		v.setAuditName(getCurrent().getUserName());
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
//	@ResponseBody
//	@RequestMapping(value ="updateBack.do")
//	public Status updateBack(ZkTaskVo v,RedirectAttributes attr) throws GlobalException {
//		try {
//			zkTaskJyService.update(v);
//			status = new Status("退回成功",Status.STATUS_SUCCESS);
//		}catch (GlobalException e) {
//			log.info(StringUtils.isNotBlank(e.getMessage())?e.getMessage():"退回失败",e);
//			status = new Status("退回失败",Status.STATUS_ERROR);
//		}
//		return status;
//	}
//	
	@Override
	@Log(operation=Log.Operation.UPDATE,content="更新校验结果",module="质控数据复核")
	public Status updateData(ZkTaskVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		v.setStatus(EunmZkTask.TASK_50.getStatus());
		return zkTaskJyService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		v.setStatus(EunmZkTask.TASK_50.getStatus());
		return zkTaskJyService.gridDatad(gridVo, v);
	}
}