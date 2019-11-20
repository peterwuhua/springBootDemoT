package cn.demi.app.res.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.constant.ConstantApp;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.JsonUtil;
import cn.demi.app.res.service.ApparaService;
import cn.demi.app.res.vo.AppAgentia;
import cn.demi.app.res.vo.AppApparaout;
import cn.demi.app.res.vo.AppAppare;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
@Controller("app.appapparaAction")
@RequestMapping("/app_appara_web/")
public class AppapparaAction {
	
	@Autowired
	private ApparaService apparaService;
	/**
	 * app 获取仪器列表
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_appara_list.do")
	public String noticeList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppAppare> ntListVo = apparaService.apparalist(objVo);
		outVo.setData(ntListVo);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取仪器详情
	 */
	@ResponseBody
	@RequestMapping(value = "app_appara_show.do")
	public String showAgentia(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String id = (String) request.getParameter("id");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		AppAppare appAppare = apparaService.findappare(id);
		outVo.setData(appAppare);
		return JsonUtil.beanToJson(outVo);
	}
	
	/**
	 * app 获取检定校准仪器列表
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_appara_jdjclist.do")
	public String apparajdjcList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppAppare> ntListVo = apparaService.apparajdjclist(objVo);
		outVo.setData(ntListVo);
		return JsonUtil.beanToJson(outVo);
	}
	
	/**
	 * app 获取仪器出库列表
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_apparaout_list.do")
	public String apparaoutList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppApparaout> ntListVo = apparaService.apparaoutlist(objVo);
		outVo.setData(ntListVo);
		return JsonUtil.beanToJson(outVo);
	}	
}
