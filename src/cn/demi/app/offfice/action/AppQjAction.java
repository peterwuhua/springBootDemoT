package cn.demi.app.offfice.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.constant.ConstantApp;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.JsonUtil;
import cn.demi.app.offfice.service.AppQjService;
import cn.demi.app.offfice.vo.AppDgEdit;
import cn.demi.app.offfice.vo.AppQjAuditEdit;
import cn.demi.app.offfice.vo.AppQjAuditPage;
import cn.demi.app.offfice.vo.AppQjShEdit;
import cn.demi.app.offfice.vo.AppQjShPage;
import cn.demi.app.offfice.vo.AppQjSpEdit;
import cn.demi.app.offfice.vo.AppQjSpPage;
import cn.demi.app.offfice.vo.AppQjSqPage;
import cn.demi.app.offfice.vo.AppQjSqEdit;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.base.system.service.ICodeService;
import net.sf.json.JSONObject;

@Controller("app.qjAction")
@RequestMapping("/app_office_qj/")
public class AppQjAction {
	@Autowired
	private AppQjService appQjService;
	@Autowired
	private ICodeService codeService;

	/**
	 * 请假申请列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sq_list.do")
	public String qjSqList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppQjSqPage> list = appQjService.qjSqList(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 请假申请详情查看
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sq_edit.do")
	public String appQjSqEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppQjSqEdit appQjSqEdit = appQjService.appQjSqEdit(request.getParameter("id").replaceAll(" ", ""));
		List<String> typeList = codeService.listByCode("qjgl-type"); // 查询请假类型
		JSONObject json = new JSONObject();
		json.put("appQjSqEdit", appQjSqEdit);
		json.put("typeList", typeList);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(json);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 请假申请增加修改
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sq_update.do")
	public String saveDgEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppQjSqEdit appQjSqEdit = (AppQjSqEdit) JsonUtil.jsonToBean(request.getParameter("appQjSqEdit"),
				AppQjSqEdit.class);
		boolean appQjSqSave = appQjService.appQjSqSave(appQjSqEdit, objVo);
		if (appQjSqSave) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		outVo.setToken(objVo.getToken());
		return JsonUtil.beanToJson(outVo);
	}

	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * 请假审核列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_audit_list.do")
	public String qjAuditList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppQjAuditPage> list = appQjService.qjAuditList(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 请假审核已办列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_audit_list_ed.do")
	public String qjAuditListEd(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppQjAuditPage> list = appQjService.qjAuditListEd(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 请假审核详情或查看
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_audit_edit.do")
	public String appQjAuditEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppQjAuditEdit appQjAuditEdit = appQjService.appQjAuditEdit(request.getParameter("id").replaceAll(" ", ""));
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(appQjAuditEdit);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 请假审核修改
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_audit_update.do")
	public String qjAuditUpdate(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppQjAuditEdit appQjAuditEdit = (AppQjAuditEdit) JsonUtil.jsonToBean(request.getParameter("appQjAuditEdit"),
				AppQjAuditEdit.class);
		boolean qjAuditUpdate = appQjService.qjAuditUpdate(appQjAuditEdit, objVo);
		if (qjAuditUpdate) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		outVo.setToken(objVo.getToken());
		return JsonUtil.beanToJson(outVo);
	}

	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * 副总审核列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sh_list.do")
	public String appQjShList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppQjShPage> list = appQjService.appQjShList(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 副总审核已办列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sh_list_ed.do")
	public String appQjShListEd(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppQjShPage> list = appQjService.appQjShListEd(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 副总审核详情或查看
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sh_edit.do")
	public String appQjShEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppQjShEdit appQjShEdit = appQjService.appQjShEdit(request.getParameter("id").replaceAll(" ", ""));
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(appQjShEdit);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 副总审核修改
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sh_update.do")
	public String qjShUpdate(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppQjShEdit appQjShEdit = (AppQjShEdit) JsonUtil.jsonToBean(request.getParameter("appQjShEdit"),
				AppQjShEdit.class);
		boolean qjShUpdate = appQjService.qjShUpdate(appQjShEdit, objVo);
		if (qjShUpdate) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		outVo.setToken(objVo.getToken());
		return JsonUtil.beanToJson(outVo);
	}

	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * 总经理审核列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sp_list.do")
	public String appQjSpList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppQjSpPage> list = appQjService.AppQjSpList(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 总经理审核已办列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sp_list_ed.do")
	public String AppQjSpListEd(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppQjSpPage> list = appQjService.AppQjSpListEd(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 总经理审核详情或查看
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sp_edit.do")
	public String appQjSpEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppQjSpEdit appQjSpEdit = appQjService.appQjSpEdit(request.getParameter("id").replaceAll(" ", ""));
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(appQjSpEdit);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 总经理审核修改
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_qj_sp_update.do")
	public String qjSpUpdate(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppQjSpEdit appQjSpEdit = (AppQjSpEdit) JsonUtil.jsonToBean(request.getParameter("appQjSpEdit"),
				AppQjSpEdit.class);
		boolean qjSpUpdate = appQjService.qjSpUpdate(appQjSpEdit, objVo);
		if (qjSpUpdate) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		outVo.setToken(objVo.getToken());
		return JsonUtil.beanToJson(outVo);
	}

	// ---------------------------------------------------------------------------------------------------------------------------//

}
