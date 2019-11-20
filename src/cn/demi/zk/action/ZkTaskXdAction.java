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
import cn.core.framework.utils.StrUtils;
import cn.demi.zk.service.IZkTaskXdService;
import cn.demi.zk.vo.ZkTaskVo;
/**
 * 质控  任务下达
 * @author QuJunLong
 */
@Controller("zk.taskXdAction")
@RequestMapping("/zk/taskXd")
public class ZkTaskXdAction extends BaseAction<ZkTaskVo> {
	final String VIEW_PATH = "/zk/task_xd/task_xd";
	@Autowired private IZkTaskXdService zkTaskXdService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ZkTaskVo> baseService() {
		return zkTaskXdService;
	}
	@Override
	public ModelAndView edit(ZkTaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=zkTaskXdService.findById(v.getId());
		}
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		v.setStatus(EunmZkTask.TASK_30.getStatus());
		return zkTaskXdService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		v.setStatus(EunmZkTask.TASK_30.getStatus());
		return zkTaskXdService.gridDatad(gridVo, v);
	}
}