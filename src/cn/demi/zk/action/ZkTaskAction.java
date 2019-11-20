package cn.demi.zk.action;

import java.util.List;

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
import cn.demi.zk.service.IZkProgressLogService;
import cn.demi.zk.service.IZkTaskService;
import cn.demi.zk.vo.ZkProgressLogVo;
import cn.demi.zk.vo.ZkTaskVo;
/**
 * 质控计划
 * @author QuJunLong
 */
@Controller("zk.taskAction")
@RequestMapping("/zk/task")
public class ZkTaskAction extends BaseAction<ZkTaskVo> {
	final String VIEW_PATH = "/zk/task/task";
	@Autowired 
	private IZkTaskService zkTaskService;
	@Autowired 
	private IZkProgressLogService zkProgressLogService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ZkTaskVo> baseService() {
		return zkTaskService;
	}
	@Override
	public ModelAndView edit(ZkTaskVo v) throws GlobalException {
		ModelAndView mav = new ModelAndView();
		if(null!=v && !StrUtils.isBlankOrNull(v.getId())){
			v=zkTaskService.findById(v.getId());
		}else {
			v.setAcceptDate(DateUtils.getCurrDateStr());
			v.setAcceptId(getCurrent().getAccountId());
			v.setAcceptName(getCurrent().getUserName());
			v.setYear(DateUtils.getYear());
			v.setMonth(DateUtils.getMonth());
		}
		//若为退回项目，获取退回日志记录
		List<ZkProgressLogVo> progressLogList=null;
		if(v.getIsBack()==1) {
			progressLogList=zkProgressLogService.findList(v.getId());
		}
		mav.addObject("progressLogList", progressLogList);
		
		mav.addObject(VO, v);
		mav.setViewName(getViewPath()+"_edit");
		return mav;
	}
	@Override
	@ResponseBody
	@RequestMapping(value ="gridData.do")
	public GridVo gridData(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		v.setStatus(EunmZkTask.TASK_00.getStatus());
		return zkTaskService.gridData(gridVo, v);
	}
	@ResponseBody
	@RequestMapping(value ="gridDatad.do")
	public GridVo gridDatad(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		v.setStatus(EunmZkTask.TASK_00.getStatus());
		return zkTaskService.gridDatad(gridVo, v);
	}
}