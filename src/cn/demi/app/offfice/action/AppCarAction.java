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
import cn.demi.app.offfice.service.AppCarService;
import cn.demi.app.offfice.vo.AppCarAuditEdit;
import cn.demi.app.offfice.vo.AppCarAuditPage;
import cn.demi.app.offfice.vo.AppCarEdit;
import cn.demi.app.offfice.vo.AppCarPage;
import cn.demi.app.offfice.vo.AppCarUserEdit;
import cn.demi.app.offfice.vo.AppCarUserPage;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.init.car.service.ICarService;

@Controller("app.carAction")
@RequestMapping("/app_office_car/")
public class AppCarAction {

	@Autowired
	private AppCarService appCarService;
	@Autowired
	private ICarService carService;

	/**
	 * 当前车辆列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_car_list.do")
	public String appOfficeDgList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppCarPage> list = appCarService.appcarList(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 当前车辆详情
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_car_edit.do")
	public String findById(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppCarEdit carEdit = appCarService.appCarEdit(request.getParameter("id"));
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(carEdit);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 当前车辆新增修改
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_car_save.do")
	public String saveCar(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppCarEdit appCarEdit = (AppCarEdit) JsonUtil.jsonToBean(request.getParameter("appCarEdit"), AppCarEdit.class);
		boolean saveCar = appCarService.saveCar(appCarEdit);
		outVo.setToken(objVo.getToken());
		if (saveCar) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		outVo.setData(saveCar);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 车辆使用申请 列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_car_user_list.do")
	public String appCarUserList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppCarUserPage> list = appCarService.appCarUserList(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 车辆使用申请 编辑
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_car_user_edit.do")
	public String appCarUserEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppCarUserEdit appCarUserEdit = appCarService.appCarUserEdit(request.getParameter("id"));
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(appCarUserEdit);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 车辆使用申请新增修改
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_car_user_save.do")
	public String saveCarUser(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppCarUserEdit appCarUserEdit = (AppCarUserEdit) JsonUtil.jsonToBean(request.getParameter("appCarUserEdit"),
				AppCarUserEdit.class);
		boolean saveCarUser = appCarService.saveCarUser(appCarUserEdit, objVo);
		outVo.setToken(objVo.getToken());
		if (saveCarUser) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}

		outVo.setData(saveCarUser);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 车辆使用审核 列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_car_audit_list.do")
	public String appCarAuditList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppCarAuditPage> list = appCarService.appCarAuditList(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 车辆使用审核已办 列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_car_audit_list_ed.do")
	public String appCarAuditListEd(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.parseInt(page));
		objVo.setRows(Integer.parseInt(rows));
		List<AppCarAuditPage> list = appCarService.appCarAuditListEd(objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 车辆使用审核 编辑
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_car_audit_edit.do")
	public String appCarAuditEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppCarAuditEdit appCarAuditEdit = appCarService.appCarAuditEdit(request.getParameter("id"), objVo);
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(appCarAuditEdit);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 车辆使用审核修改
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_office_car_audit_save.do")
	public String saveCarAudit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		AppCarAuditEdit appCarAuditEdit = (AppCarAuditEdit) JsonUtil.jsonToBean(request.getParameter("appCarAuditEdit"),
				AppCarAuditEdit.class);
		boolean saveCarAudit = appCarService.saveCarAudit(appCarAuditEdit, objVo);
		outVo.setToken(objVo.getToken());
		if (saveCarAudit) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		outVo.setData(saveCarAudit);
		return JsonUtil.beanToJson(outVo);
	}

}
