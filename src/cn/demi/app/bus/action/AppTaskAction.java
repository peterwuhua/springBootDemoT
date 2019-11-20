package cn.demi.app.bus.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.constant.ConstantApp;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.JsonUtil;
import cn.demi.app.bus.service.AppTaskService;
import cn.demi.app.bus.vo.AppCustPointSelect;
import cn.demi.app.bus.vo.AppTask;
import cn.demi.app.bus.vo.AppTaskEdit;
import cn.demi.app.bus.vo.AppTaskFb;
import cn.demi.app.bus.vo.AppTaskImSelect;
import cn.demi.app.bus.vo.AppTaskTp;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.bus.task.service.ITaskPointService;
import cn.demi.cus.customer.service.IClientService;
import cn.demi.cus.customer.service.ICustomerService;
import cn.demi.cus.customer.vo.ClientVo;
import cn.demi.cus.customer.vo.CustVo;
import cn.demi.cus.customer.vo.CustomerVo;

@Controller("app.taskAction")
@RequestMapping("/app_bus_task/")
public class AppTaskAction {
	@Autowired
	private AppTaskService taskService;
	@Autowired
	private IClientService clientService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private ITaskPointService taskPointService;

	/**
	 * app 任务登记列表
	 */
	@ResponseBody
	@RequestMapping(value = "app_task_list.do")
	public String appTaskList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		objVo.setPage(page);
		objVo.setRows(rows);
		List<AppTask> list = taskService.taskList(objVo);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 任务登记列表
	 */
	@ResponseBody
	@RequestMapping(value = "app_task_edit.do")
	public String appTaskEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String id = request.getParameter("id");
		AppTaskEdit taskEdit = taskService.taskEdit(id);
		outVo.setData(taskEdit);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 检测项目列表
	 */
	@ResponseBody
	@RequestMapping(value = "app_im_select.do")
	public String imSelectList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String sampTypeId = request.getParameter("sampTypeId");
		String sampType = request.getParameter("sampType");
		String source = request.getParameter("source");
		String imId = request.getParameter("imId");
		List<AppTaskImSelect> list = taskService.imSelectList(sampTypeId, sampType, source, imId);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 受检单位
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_sim_list.do")
	public String list4Sim(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		ClientVo v = new ClientVo();
		v.setName(request.getParameter("name"));
		List<CustVo> list = taskService.list4Sim(v);
		outVo.setData(list.toArray());
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 委托单位
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */

	@ResponseBody
	@RequestMapping(value = "app_full_list.do")
	public String list4Full(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		CustomerVo v = new CustomerVo();
		v.setName(request.getParameter("name"));
		List<CustVo> list = taskService.list4Full(v);
		outVo.setData(list.toArray());
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 获取客户的点位集合
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_cust_point_select_list.do")
	public String CustPointSelectlist(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String custName = request.getParameter("custName");
		String sampTypeId = request.getParameter("sampTypeId");
		List<AppCustPointSelect> list = taskService.CustPointSelectlist(custName, sampTypeId);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 删除点位信息
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_delete_point.do")
	public String deletePoint(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		try {
			outVo.setCode(ConstantApp.ST_1);
			taskPointService.delete(request.getParameterValues("id"));
		} catch (Exception e) {
			outVo.setCode(ConstantApp.ST_0);
			outVo.setData(e);

		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 分包商信息
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_fb_list.do")
	public String fbList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		List<AppTaskFb> list = taskService.fbList(request.getParameter("ids"));
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 任务 保存 或提交
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_task_update.do")
	public String taskUpdate(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		
		// 受检单位 委托单位 监测要求
		AppTaskEdit AppTaskEdit = (AppTaskEdit) JsonUtil.jsonToBean(request.getParameter("appTaskEdit"),
				AppTaskEdit.class);
		// 检测点位信息列表
		List<AppTaskFb> AppTaskFbList = JsonUtil.jsonToList(request.getParameter("appTaskFb"), AppTaskFb.class);
		// 分包单位列表
		List<AppTaskTp> AppTaskTpList = JsonUtil.jsonToList(request.getParameter("appTaskTp"), AppTaskTp.class);
		AppTaskEdit.setFbList(AppTaskFbList);
		AppTaskEdit.setTpList(AppTaskTpList);
		Boolean taskAddOrUpDate = taskService.taskAddOrUpDate(AppTaskEdit, objVo, request.getParameter("isCommit"));
		if(taskAddOrUpDate) {
			outVo.setCode(ConstantApp.ST_1);
		}else {
			outVo.setCode(ConstantApp.ST_0);
		}
		outVo.setData(taskAddOrUpDate);
		return JsonUtil.beanToJson(outVo);
	}

}
