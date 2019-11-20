package cn.core.framework.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import cn.core.framework.constant.ConstantApp;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.JsonUtil;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.sys.service.AppUserService;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.app.sys.vo.UserOutVo;

/**
 * 移动 端访问拦截
 *
 * @author QuJunLong
 */
public class AppInterceptor extends HandlerInterceptorAdapter {

	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	public static Logger log = Logger.getLogger(AppInterceptor.class);
	@Autowired
	AppUserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = urlPathHelper.getLookupPathForRequest(request);
		if (url.contains("/app_web/") || url.contains("/bus_web/") || url.contains("/app_task_xc/")
				|| url.contains("/app_task_xc_zw/") || url.contains("/app_office/") || url.contains("/app_count_web/")
				|| url.contains("/app_init/") || url.contains("/app_file_web/") || url.contains("/app_appara_web/")
				|| url.contains("app_res_web") || url.contains("/app_bus_project/") || url.contains("/app_bus_task/")
				|| url.contains("/app_office_qj/") || url.contains("/app_office_car/")) { // 只拦截移动端过来的url
			OutVo outVo = new OutVo();
			if (url.contains("/app_web/login.do")) {
				UserOutVo userVo = userService.login(request.getParameter("name"), request.getParameter("password"));
				if (userVo == null) {
					outVo.setCode(ConstantApp.ST_0);
					outVo.setMessage("用户名或密码不正确！");
				} else {
					outVo.setCode(ConstantApp.ST_1);
					List<UserOutVo> objList = new ArrayList<UserOutVo>();
					objList.add(userVo);
					outVo.setData(objList);
				}
			} else if (url.contains("/bus_web/tv_all_task_list.do") || url.contains("/bus_web/appFileUpload.do")
					|| url.contains("/app_web/tv_nt_list.do") || url.contains("/app_web/tv_user_list.do")
					|| url.contains("/bus_web/tv_record_list.do") || url.contains("/app_web/tv_check_on_list.do")
					|| url.contains("/app_task_xc/app_upload_file.do")
					|| url.contains("/app_task_xc/app_photo_upload.do")
					|| url.contains("/app_file_web/app_upload_file.do")) {
				return super.preHandle(request, response, handler);
			} else {
				ObjVo objVo = userService.checkPerm(request.getParameter("token"));
				if (objVo == null) {
					outVo.setCode(ConstantApp.ST_99);
					outVo.setMessage("登陆超时！");
				} else if (StrUtils.isBlankOrNull(objVo.getRoleIds())) {
					outVo.setCode(ConstantApp.ST_98);
					outVo.setMessage("无权限！");
				} else {
					request.setAttribute("objVo", objVo);
					return super.preHandle(request, response, handler);
				}
			}
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(JsonUtil.beanToJson(outVo));
			out.close();
			return false;
		} else {
			return super.preHandle(request, response, handler);
		}
	}
}
