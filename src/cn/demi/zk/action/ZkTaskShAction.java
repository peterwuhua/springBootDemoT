package cn.demi.zk.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.constant.EunmZkTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.zk.service.IZkTaskShService;
import cn.demi.zk.vo.ZkTaskVo;
/**
 * 质控  任务审核
 * @author QuJunLong
 *
 */
@Controller("zk.taskShAction")
@RequestMapping("/zk/taskSh")
public class ZkTaskShAction extends BaseAction<ZkTaskVo> {
	final String VIEW_PATH = "/zk/task_sh/task_sh";
	@Autowired private IZkTaskShService zkTaskShService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ZkTaskVo> baseService() {
		return zkTaskShService;
	}
	@Override
	public ModelAndView edit(ZkTaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=zkTaskShService.findById(v.getId());
		}
		if(StrUtils.isBlankOrNull(v.getAuditId())) {
			v.setAuditId(getCurrent().getAccountId());
			v.setAuditName(getCurrent().getUserName());
			v.setAuditDate(DateUtils.getCurrDateStr());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		v.setStatus(EunmZkTask.TASK_10.getStatus());
		return zkTaskShService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		v.setStatus(EunmZkTask.TASK_10.getStatus());
		return zkTaskShService.gridDatad(gridVo, v);
	}
}