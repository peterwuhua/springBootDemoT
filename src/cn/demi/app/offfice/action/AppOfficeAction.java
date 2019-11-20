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
import cn.demi.app.offfice.service.AppDgService;
import cn.demi.app.offfice.vo.AppDg;
import cn.demi.app.offfice.vo.AppDgEdit;
import cn.demi.app.offfice.vo.kqshrUser;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.office.service.IDgService;
import cn.demi.office.vo.DgCfgVo;

@Controller("app.officeAction")
@RequestMapping("/app_office/")
public class AppOfficeAction {
	@Autowired
	private AppDgService appDgService;
	@Autowired
	private IDgService dgService;

	/**
	 * 当前登录人 补卡 外勤列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_dg_list.do")
	public String appOfficeDgList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppDgEdit> list = appDgService.dgList(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 当前登录人 补卡审核列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_dg_audit_list.do")
	public String appOfficeDgAuditList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppDg> list = appDgService.dgAuditList(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 当前登录人 已办补卡审核列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_dg_audit_ed_list.do")
	public String appOfficeDgAuditEdList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppDg> list = appDgService.dgAuditEdList(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 获取上班时间
	 *
	 * @param request
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_dg_find.do")
	public String dgServiceFind(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		DgCfgVo v = dgService.find();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(v);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 验证打卡时间
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_dg_check_time.do")
	public String dgServiceCheckTime(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String msg = appDgService.checkTime(objVo, request.getParameter("check"));
		outVo.setToken(objVo.getToken());
		if (msg != null && msg != "") {
			outVo.setCode(ConstantApp.ST_1);
			outVo.setMessage(msg);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 补卡审核人
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_Kqshr_user.do")
	public String roleKoKqshr(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		List<kqshrUser> list = appDgService.kqshrUserList();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 申请 补卡 或 外勤
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_add_dg_edit.do")
	public String saveDgEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		// 受检单位 委托单位 监测要求
		AppDgEdit appDgEdit = (AppDgEdit) JsonUtil.jsonToBean(request.getParameter("appDgEdit"), AppDgEdit.class);
		boolean saveDgEdit = appDgService.saveDgEdit(appDgEdit, objVo);
		if (saveDgEdit) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		outVo.setToken(objVo.getToken());
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 当前登录人 当天打卡记录
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_today_dk_list.do")
	public String todayDk(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		objVo.setPage(Integer.parseInt("1"));
		objVo.setRows(Integer.parseInt("20"));
		List<String> list = appDgService.todayDk(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 当前登录人 动态列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_dg_type_list.do")
	public String dgTypeList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppDgEdit> list = appDgService.dgTypeList(objVo, request.getParameter("type"));
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 *补卡审核
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_dg_audit_update.do")
	public String updateAudit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();	 
		 boolean updateAudit = appDgService.updateAudit(request.getParameter("id"), request.getParameter("auditMsg") , request.getParameter("isCommit")  , objVo);
		outVo.setToken(objVo.getToken());
		if (updateAudit) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		outVo.setData(updateAudit);
		return JsonUtil.beanToJson(outVo);
	}

}
