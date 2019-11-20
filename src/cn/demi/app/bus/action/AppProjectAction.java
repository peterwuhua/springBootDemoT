package cn.demi.app.bus.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.constant.ConstantApp;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.JsonUtil;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.bus.service.AppProjectService;
import cn.demi.app.bus.vo.AppProject;
import cn.demi.app.bus.vo.AppProjectEdit;
import cn.demi.app.bus.vo.AppProjectFb;
import cn.demi.app.bus.vo.AppProjectPoint;
import cn.demi.app.bus.vo.AppPstandard;
import cn.demi.app.bus.vo.AppTaskEdit;
import cn.demi.app.bus.vo.AppTaskFb;
import cn.demi.app.bus.vo.AppTaskTp;
import cn.demi.app.bus.vo.AppTaskType;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.base.system.service.ICodeService;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;

@Controller("app.projectAction")
@RequestMapping("/app_bus_project/")
public class AppProjectAction {
	@Autowired
	private AppProjectService appProjectService;
	@Autowired
	private ICodeService codeService;
	@Autowired
	private ISampTypeService sampTypeService;

	/**
	 * app 监测项目 列表
	 */
	@ResponseBody
	@RequestMapping(value = "app_bus_project_list.do")
	public String appBusProject(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		objVo.setPage(page);
		objVo.setRows(rows);
		List<AppProject> list = appProjectService.projectList(objVo);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 监测项目 详情
	 */
	@ResponseBody
	@RequestMapping(value = "app_bus_project_edit.do")
	public String appBusProjectEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String id = request.getParameter("id");
		AppProjectEdit appProjectEdit = appProjectService.projectEdit(id);
		outVo.setData(appProjectEdit);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 监测项目 样品类别
	 */
	@ResponseBody
	@RequestMapping(value = "app_task_type_list.do")
	public String appTaskTypeList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String sampType = request.getParameter("sampType");
		List<String> list = appProjectService.appTaskTypeList(sampType);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 行业分类和代码
	 */
	@ResponseBody
	@RequestMapping(value = "app_industry_select.do")
	public String industrySelect(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String content = codeService.findByCode("cus-sshy").getContent();
		outVo.setData(content);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 获取树形集合 全部展开 父节点不显示选择框
	 * 
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "treeData4NoCheck.do")
	public String treeData4NoCheck(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		String rootId = sampTypeService.findRoot().getId();
		SampTypeVo stVo = sampTypeService.findByName("环境");
		if (null != stVo) {
			rootId = stVo.getId();
		}
		List<SampTypeVo> list = sampTypeService.treeList(rootId);
		for (SampTypeVo vo : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId() : "");
			map.put("name", vo.getName());
			map.put("open", "true");
			if (vo.isHasChild()) {
				map.put("nocheck", "true");

			} else {
				map.put("nocheck", "false");
				mapList.add(map);
			}

		}
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(mapList);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 方案编制 列表
	 */
	@ResponseBody
	@RequestMapping(value = "app_bus_project_fa_list.do")
	public String appBusProjectFaList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		objVo.setPage(page);
		objVo.setRows(rows);
		List<AppProject> list = appProjectService.projectFaList(objVo);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 项目立项 保存 或提交
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
		AppProjectEdit appProjectEdit = (AppProjectEdit) JsonUtil.jsonToBean(request.getParameter("appProjectEdit"),
				AppProjectEdit.class);
		Boolean projectAddOrUpDate = appProjectService.projectAddOrUpDate(appProjectEdit, objVo,
				request.getParameter("isCommit"));
		if (projectAddOrUpDate) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 项目立项 送样 保存 或提交
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_task_update_next.do")
	public String updateNext(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		// 受检单位 委托单位 监测要求
		AppProjectEdit appProjectEdit = (AppProjectEdit) JsonUtil.jsonToBean(request.getParameter("appProjectEdit"),
				AppProjectEdit.class);
		List<AppProjectFb> appProjectFbList = JsonUtil.jsonToList(request.getParameter("appProjectFb"),
				AppProjectFb.class);
		List<AppProjectPoint> appProjectPointList = JsonUtil.jsonToList(request.getParameter("appProjectFb"),
				AppProjectPoint.class);
		appProjectEdit.setAppProjectPointList(appProjectPointList);
		appProjectEdit.setAppProjectFbList(appProjectFbList);
		appProjectService.updateNext(appProjectEdit, request.getParameter("isCommit"), objVo);
		return JsonUtil.beanToJson(outVo);
	}

}
