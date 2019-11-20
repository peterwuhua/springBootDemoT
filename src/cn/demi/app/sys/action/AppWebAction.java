package cn.demi.app.sys.action;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.common.action.Action;
import cn.core.framework.constant.ConstantApp;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.JsonUtil;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.sys.service.AppUserService;
import cn.demi.app.sys.service.IAppSysMsgService;
import cn.demi.app.sys.service.IAppWorkPlanService;
import cn.demi.app.sys.service.IBillingInfoService;
import cn.demi.app.sys.service.IDgTjService;
import cn.demi.app.sys.service.INoticeService;
import cn.demi.app.sys.service.IOrgService;
import cn.demi.app.sys.service.IQuestionsService;
import cn.demi.app.sys.vo.AppBillingInfoVo;
import cn.demi.app.sys.vo.AppSysMsgVo;
import cn.demi.app.sys.vo.AppUser;
import cn.demi.app.sys.vo.AppWorkPlanVo;
import cn.demi.app.sys.vo.BillingInfoVo;
import cn.demi.app.sys.vo.NoticeOutVo;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.app.sys.vo.QuestionsVo;
import cn.demi.app.sys.vo.UserInfoVo;
import cn.demi.app.sys.vo.UserOutVo;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.service.IUserService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.base.system.vo.SysMsgVo;
import cn.demi.base.system.vo.UserVo;
import cn.demi.office.vo.WorkPlanVo;
import net.sf.json.JSONArray;

/**
 * 系统 view层
 *
 * @author QuJunLong
 */
@Controller("app.appWebAction")
@RequestMapping("/app_web/")
public class AppWebAction extends Action {

	@Autowired
	private AppUserService userService;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IOrgService orgService;
	@Autowired
	private IQuestionsService questionsService;
	@Autowired
	private IFilesService filesService;
	@Autowired
	private IDgTjService dgTjService;
	@Autowired
	private IAppWorkPlanService appWorkPlanService;
	@Autowired
	private IBillingInfoService billingInfoService;
	@Autowired
	private IAppSysMsgService appSysMsgService;
	@Autowired
	private IUserService userServicePc;

	/**
	 * 登录接口
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "login.do")
	public String login(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		UserOutVo userVo = userService.login(objVo.getName(), objVo.getPassword());
		List<UserOutVo> objList = new ArrayList<>();
		objList.add(userVo);
		outVo.setData(objList);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 获取个人信息
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_user_info.do")
	public String findUser(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		UserInfoVo userVo = userService.findUser(objVo.getUserId());
		List<UserInfoVo> objList = new ArrayList<>();
		objList.add(userVo);
		outVo.setData(objList);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 修改个人信息
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "update_user.do")
	public String updateUser(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		UserInfoVo infoVo = new UserInfoVo(request.getParameterMap());
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		boolean flag = userService.updateUser(infoVo, objVo.getUserId());
		if (flag) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
			outVo.setMessage("更新用户异常");
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 修改密码
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_update_pwd.do")
	public String update_pwd(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		String flag = userService.resetPwd(objVo.getUserId(), request.getParameter("oldPwd"),
				request.getParameter("newPwd"));
		if (flag == null) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
			outVo.setMessage(flag);
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 修改用户头像
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "update_avatar.do")
	public String update_avatar(HttpServletRequest request) throws GlobalException {
		String SERVER_TEMP_UPLOAD_DIR = ApplicationUtils.getValue("config.upload.sys.avatar").toString().replace("\\",
				"/");
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		String file = request.getParameter("file");
		if (file != null) {
			byte[] b = Base64.decode(file);
			String virtualPath = System.currentTimeMillis() + ".jpg";
			File f = new File(SERVER_BASE + SERVER_TEMP_UPLOAD_DIR);
			if (!f.exists())
				f.mkdirs();
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(f.getPath() + "/" + virtualPath.replace("/", "\\"));
				fos.write(b);
				fos.flush();
				fos.close();
				String path = "avatar/" + virtualPath;
				userService.updateAvatar(objVo.getUserId(), path);
				outVo.setCode(ConstantApp.ST_1);
			} catch (Exception e) {
				outVo.setCode(ConstantApp.ST_0);
				outVo.setMessage("获取图片异常");
			}
		} else {
			outVo.setCode(ConstantApp.ST_0);
			outVo.setMessage("获取图片异常");
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 部门列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "org_list.do")
	public String orgList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		JSONArray rows = orgService.orgList();
		outVo.setData(rows);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 所有用户信息
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_user_list.do")
	public String userList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		JSONArray rows = userService.userList();
		outVo.setData(rows);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 获取通知公告列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_notice_list.do")
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
		List<NoticeOutVo> ntListVo = noticeService.list(objVo);
		outVo.setData(ntListVo);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 意见反馈
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "question.do")
	public String question(HttpServletRequest request) throws GlobalException {
		String SERVER_TEMP_UPLOAD_DIR = ApplicationUtils.getValue("config.upload.sys.questions").toString()
				.replace("\\", "/");
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		QuestionsVo questionsVo = new QuestionsVo(request.getParameterMap());
		questionsVo.setUserId(objVo.getUserId());
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());

		questionsVo = questionsService.add(questionsVo);
		outVo.setCode(ConstantApp.ST_1);

		String[] files = request.getParameterMap().get("files");
		if (files != null) {
			for (String file : files) {
				byte[] b = Base64.decode(file);
				String virtualPath = System.currentTimeMillis() + ".jpg";
				File f = new File(SERVER_BASE + SERVER_TEMP_UPLOAD_DIR);
				if (!f.exists())
					f.mkdirs();
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(f.getPath() + "/" + virtualPath.replace("/", "\\"));
					fos.write(b);
					fos.flush();
					fos.close();

					FilesVo fileVo = new FilesVo();
					fileVo.setBusId(questionsVo.getId());
					fileVo.setBusType(Constants.FILE_TYPE_APP_QUEST);
					fileVo.setFileName(virtualPath);
					String filePath = (SERVER_TEMP_UPLOAD_DIR + "/" + virtualPath).replace("\\", "/");
					fileVo.setFilePath(filePath);
					fileVo.setFileType(".jpg");
					filesService.add(fileVo);
				} catch (Exception e) {
					outVo.setCode(ConstantApp.ST_0);
					outVo.setMessage("获取图片异常");
					return JsonUtil.beanToJson(outVo);
				}
			}
		} else {
			outVo.setCode(ConstantApp.ST_0);
			outVo.setMessage("获取图片异常");
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 通知公告列表
	 *
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "tv_nt_list.do")
	public String tvNoticeList(HttpServletRequest request) throws GlobalException {
		OutVo outVo = new OutVo();
		outVo.setCode(ConstantApp.ST_1);
		List<NoticeOutVo> ntListVo = noticeService.tvList();
		outVo.setData(ntListVo);
		return JsonUtil.beanToJson(outVo);
	}

	@ResponseBody
	@RequestMapping(value = "app_work_plan.do")
	public String appWorkPlan(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String startTime = request.getParameter("startTime");
		String status = request.getParameter("status");
		WorkPlanVo v = new WorkPlanVo();
		v.setUrgentLevel(status);
		v.setSelectdate(startTime);
		v.setUserId(objVo.getUserId());
		List<AppWorkPlanVo> list = appWorkPlanService.listDate(v);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	@ResponseBody
	@RequestMapping(value = "app_add_work_plan.do")
	public String appAddWorkPlan(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String status = request.getParameter("status");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		WorkPlanVo vo = new WorkPlanVo();
		vo.setType(type);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setUrgentLevel(status);
		vo.setStartDate(startTime);
		vo.setEndDate(endTime);
		String addWorkPlan = appWorkPlanService.addWorkPlan(vo, objVo);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		if (addWorkPlan == null) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		return JsonUtil.beanToJson(outVo);
	}

	@ResponseBody
	@RequestMapping(value = "app_billing_info_list")
	public String appBillingInfo(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		List<AppBillingInfoVo> list = billingInfoService.listByUserId(objVo.getUserId());
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	@ResponseBody
	@RequestMapping(value = "app_add_billing_info")
	public String AddAppBillingInfo(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		// 单位名称
		String companyName = request.getParameter("companyName");
		// 税号
		String tfn = request.getParameter("tfn");
		// 单位地址
		String companyAddress = request.getParameter("companyAddress");
		// 电话号码
		String tel = request.getParameter("tel");
		// 开户银行
		String depositBank = request.getParameter("depositBank");
		// 银行账号
		String depositNum = request.getParameter("depositNum");
		BillingInfoVo v = new BillingInfoVo();
		v.setCompanyName(companyName);
		v.setCompanyAddress(companyAddress);
		v.setTfn(tfn);
		v.setTel(tel);
		v.setDepositBank(depositBank);
		v.setDepositNum(depositNum);
		String addBillingInfo = billingInfoService.addBillingInfo(v, objVo);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		if (addBillingInfo == null) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		return JsonUtil.beanToJson(outVo);
	}

	@ResponseBody
	@RequestMapping(value = "app_update_billing_info")
	public String UpdateAppBillingInfo(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String id = request.getParameter("id");
		// 单位名称
		String companyName = request.getParameter("companyName");
		// 税号
		String tfn = request.getParameter("tfn");
		// 单位地址
		String companyAddress = request.getParameter("companyAddress");
		// 电话号码
		String tel = request.getParameter("tel");
		// 开户银行
		String depositBank = request.getParameter("depositBank");
		// 银行账号
		String depositNum = request.getParameter("depositNum");
		BillingInfoVo v = billingInfoService.findById(id);
		v.setCompanyName(companyName);
		v.setCompanyAddress(companyAddress);
		v.setTfn(tfn);
		v.setTel(tel);
		v.setDepositBank(depositBank);
		v.setDepositNum(depositNum);
		String updateBillingInfo = billingInfoService.updateBillingInfo(v);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		if (updateBillingInfo == null) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		return JsonUtil.beanToJson(outVo);
	}

	@ResponseBody
	@RequestMapping(value = "app_del_billing_info")
	public String DelAppBillingInfo(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String id = request.getParameter("id");
		BillingInfoVo v = billingInfoService.findById(id);
		v.setIsDel("1");
		String updateBillingInfo = billingInfoService.updateBillingInfo(v);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		if (updateBillingInfo == null) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		return JsonUtil.beanToJson(outVo);

	}

	/**
	 * app 人员档案
	 */
	@ResponseBody
	@RequestMapping(value = "app_sys_user_list.do")
	public String appUserList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		int page = Integer.parseInt(request.getParameter("page"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		String name = request.getParameter("name");
		objVo.setPage(page);
		objVo.setRows(rows);
		List<AppUser> list = userService.AppuserList(objVo, name);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 人员档案 详细
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_sys_user_edit.do")
	public String sysUserEdit(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String userId = "";
		if (!StrUtils.isBlankOrNull(request.getParameter("userId"))) {
			userId = request.getParameter("userId");
		} else {
			userId = objVo.getRealUserId();
		}
		UserVo vo = userServicePc.findById(userId);
		outVo.setData(vo);
		return JsonUtil.beanToJson(outVo);

	}

	@ResponseBody
	@RequestMapping(value = "app_num_msg")
	public String AppNumMsg(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		int numMsg = appSysMsgService.getCount4Wd(objVo);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(Integer.toString(numMsg));
		return JsonUtil.beanToJson(outVo);
	}

	@ResponseBody
	@RequestMapping(value = "app_msg_list")
	public String AppMsgList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppSysMsgVo> list = appSysMsgService.list4Wd(objVo);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	@ResponseBody
	@RequestMapping(value = "app_update_msg")
	public String AppUpdateMsg(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String id = request.getParameter("id");
		SysMsgVo sysMsgVo = appSysMsgService.findById(id);
		sysMsgVo.setStatus("1");
		String updateMsg = appSysMsgService.updateMsg(sysMsgVo);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		if (updateMsg == null) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
		}
		return JsonUtil.beanToJson(outVo);

	}
}