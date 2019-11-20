package cn.demi.app.bus.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.common.action.Action;
import cn.core.framework.constant.ConstantApp;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.JsonUtil;
import cn.demi.app.bus.service.AppProjectService;
import cn.demi.app.bus.service.AppTaskService;
import cn.demi.app.bus.vo.AllTaskVo;
import cn.demi.app.bus.vo.AppPstandard;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.base.system.service.ICodeService;

@Controller("app.busWebAction")
@RequestMapping("/bus_web/")
public class BusWebAction extends Action {
	@Autowired
	private AppTaskService taskService;
	@Autowired
	private AppProjectService appProjectService;
	@Autowired
	private ICodeService codeService;

	/**
	 * app 所有待办任务 列表接口
	 */
	@ResponseBody
	@RequestMapping(value = "app_all_task_list.do")
	public String appAllTask(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		List<AllTaskVo> taskList = taskService.appAllTask(objVo);
		outVo.setData(taskList);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 评价依据列表
	 */
	@ResponseBody
	@RequestMapping(value = "app_pstandard_list.do")
	public String pstandardList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String sampTypeId = request.getParameter("sampTypeId");
		String name = request.getParameter("name");
		List<AppPstandard> list = appProjectService.pstandardList(sampTypeId, name);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 储存条件
	 */
	@ResponseBody
	@RequestMapping(value = "app_save_request_list.do")
	public String saveRequestList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		List<String> list = codeService.listByCode("save-request");
		list.add(0,"请选择");
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

}
