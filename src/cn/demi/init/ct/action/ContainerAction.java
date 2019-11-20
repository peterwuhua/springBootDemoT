package cn.demi.init.ct.action;

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
import cn.demi.init.ct.service.IContainerService;
import cn.demi.init.ct.vo.ContainerVo;

@Controller("init.containerAction")
@RequestMapping("/init/container")
public class ContainerAction extends BaseAction<ContainerVo> {
	final String VIEW_PATH = "/init/container/container";
	@Autowired private IContainerService containerService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ContainerVo> baseService() {
		return containerService;
	}
	@ResponseBody
	@RequestMapping(value = "ajaxSave.do")
	@Log(operation=Log.Operation.SAVE,content="保存容器",module="容器管理")
	public Status ajaxSave(ContainerVo v, RedirectAttributes attr) throws GlobalException {
		try {
			containerService.save(v);
			status = new Status("添加成功",Status.STATUS_SUCCESS);
		}catch (GlobalException e) {
			status = new Status("添加失败",Status.STATUS_ERROR);
		}
		status.setObject(v);
		attr.addFlashAttribute(STATUS, status);
		return status;
	}
	@RequestMapping(value = "ajaxCheckCode.do")
	@ResponseBody
	public Status ajaxCheckCode(String id,String code, RedirectAttributes attr) throws GlobalException {
		try {
			ContainerVo v = containerService.findByCode(id,code);
			if(v!=null&&v.getCode()!=null){
				status = new Status("编号已存在",Status.STATUS_ERROR);
			}else{
				status = new Status("编号验证成功，可以使用",Status.STATUS_SUCCESS);
			}
		} catch (GlobalException e) {
			log.info("验证编号异常",e);
			status = new Status("验证编号异常",Status.STATUS_ERROR);
		}
		attr.addFlashAttribute(STATUS, status);
		return status;
	}
	@Override
	@Log(operation=Log.Operation.DELETE,content="删除容器",module="容器管理")
	public ModelAndView delete(ContainerVo v, RedirectAttributes attr) throws GlobalException {
		return super.delete(v, attr);
	}
}