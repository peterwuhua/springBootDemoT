package cn.demi.app.wenjian.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.core.framework.common.action.Action;
import cn.core.framework.constant.ConstantApp;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.JsonUtil;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.app.wenjian.service.CateService;
import cn.demi.app.wenjian.service.FileService;
import cn.demi.app.wenjian.vo.AppCate;
import cn.demi.app.wenjian.vo.AppFile;
import cn.demi.office.vo.DtVo;
import net.sf.json.JSONArray;

@Controller("app.appFileAction")
@RequestMapping("/app_file_web/")
public class AppFileAction extends Action {
	public static final String SERVER_UPLOAD_DIR = ApplicationUtils.getValue("config.upload.doc.document").toString()
			.replace("\\", "/");
	@Autowired
	private FileService fileService;

 
	@Autowired
	private CateService cateService;

	/**
	 * app获取文件查看列表
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */

	@ResponseBody
	@RequestMapping(value = "app_file_list.do")
	public String noticeList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		int type = Integer.parseInt(request.getParameter("type"));
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String searchValue = request.getParameter("searchValue");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppFile> ntListVo = fileService.list(objVo, type,searchValue);
		outVo.setData(ntListVo);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app文件详情
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_file_show.do")
	public String showfile(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String id = (String) request.getParameter("id");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		AppFile fileShow = fileService.findfile(id);
		outVo.setData(fileShow);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app文件签发功能
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_file_audit.do")
	public String updateAuditFile(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String id = request.getParameter("id");
		String AuditCt = request.getParameter("auditct");
		String AuditId = request.getParameter("auditid");
		String AuditName = request.getParameter("auditname");
		String AuditTime = request.getParameter("audittime");
		String SendId = request.getParameter("sendid");
		String SendName = request.getParameter("sendname");
		String IsCommit = request.getParameter("iscommit");
		DtVo v = new DtVo();
		v.setId(id);
		v.setAuditCt(AuditCt);
		v.setAuditId(AuditId);
		v.setAuditName(AuditName);
		v.setAuditTime(AuditTime);
		v.setSendId(SendId);
		v.setSendName(SendName);
		v.setIsCommit(IsCommit);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());

		String flag = fileService.updateAuditfile(v, objVo);
		if (flag == null) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
			outVo.setMessage(flag);
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 文件下发功能
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */

	@ResponseBody
	@RequestMapping(value = "app_file_send.do")
	public String updateSendFile(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String id = request.getParameter("id");
		String sendct = request.getParameter("sendct");
		String sendid = request.getParameter("sendid");
		String sendname = request.getParameter("sendname");
		String sendtime = request.getParameter("sendtime");
		String userids = request.getParameter("userids");
		String usernames = request.getParameter("usernames");
		DtVo v = new DtVo();
		v.setId(id);
		v.setSendCt(sendct);
		v.setSendId(sendid);
		v.setSendName(sendname);
		v.setSendTime(sendtime);
		v.setUserIds(userids);
		v.setUserNames(usernames);
		v.setStatus(EunmTask.TASK_END.getStatus());
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		String flag = fileService.updateSendfile(v, objVo);
		if (flag == null) {
			outVo.setCode(ConstantApp.ST_1);
		} else {
			outVo.setCode(ConstantApp.ST_0);
			outVo.setMessage(flag);
		}
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
	@RequestMapping(value = "app_file_userlist.do")
	public String userList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		JSONArray rows = fileService.userList();
		outVo.setData(rows);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 文件路径
	 */

	@ResponseBody
	@RequestMapping(value = "app_file_path_list.do")
	public String filePathList(HttpServletRequest request) throws GlobalException {
		String id = request.getParameter("id");
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		List<AppCate> list = cateService.list(id);
		OutVo outVo = new OutVo();
		outVo.setCode(ConstantApp.ST_1);
		outVo.setToken(objVo.getToken());
		outVo.setData(list);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 文件信息上传
	 *
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "app_file_message_upload.do")
	public String fileMessageUpload(HttpServletRequest request)
			throws IllegalStateException, IOException, GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();

		String appFileId = request.getParameter("id");
		String code = request.getParameter("code");
		String appFileName = request.getParameter("fileName");
		String title = request.getParameter("title");
		String source = request.getParameter("source");
		String dj = request.getParameter("dj");
		String path ="";
		if (request.getParameter("path") == null || "".equals(request.getParameter("path"))
				|| "null".equals(request.getParameter("path"))) {

		} else {
			path = request.getParameter("path") + "/" + title;
		}

		String auditId = request.getParameter("auditId");
		String auditName = request.getParameter("auditName");
		String date = request.getParameter("date");
		String content = request.getParameter("content");
		String remark = request.getParameter("remark");
		String status = request.getParameter("status");
		String userId = objVo.getUserId();
		String filePath = request.getParameter("filePath");
		String type = request.getParameter("type");
		String categoryId = request.getParameter("categoryId");
		fileService.saveorupdateFile(appFileId, title, code, appFileName, source, dj, path, auditId, auditName, date,
				content, remark, userId, status, filePath, type, categoryId, objVo);
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData("成功");

		return JsonUtil.beanToJson(outVo);

	}

	/**
	 * app 文件查看更新
	 *
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "app_file_view_upload.do")
	public String updatefileView(HttpServletRequest request)
			throws IllegalStateException, IOException, GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		DtVo v = new DtVo();
		v.setId(request.getParameter("id"));
		fileService.updatefileView(v, objVo);
		outVo.setCode(ConstantApp.ST_1);
		outVo.setData("成功");
		outVo.setToken(objVo.getToken());
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 文件流转流程列表
	 * 
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_file_select.do")
	public String selectFile(HttpServletRequest request) throws IllegalStateException, IOException, GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		int type = Integer.parseInt(request.getParameter("type"));
		String selectvalue = request.getParameter("selectvalue");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppFile> ntListVo = fileService.selectList(objVo, selectvalue, type);
		outVo.setData(ntListVo);
		return JsonUtil.beanToJson(outVo);

	}

	/**
	 * App 文件流转文件上传
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_upload_file.do")
	public String getFileItemList(HttpServletRequest request) throws Exception, GlobalException {
		OutVo outVo = new OutVo();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			String towFilePath = multiRequest.getParameter("towFilePath").replace("\"", "").trim();
			Object temppath = ApplicationUtils.getValue("config.server.base");
			JSONArray uploadFile = fileService.uploadFile(multiRequest, temppath, towFilePath);
			outVo.setData(uploadFile);
		}
		outVo.setCode(ConstantApp.ST_1);
		return JsonUtil.beanToJson(outVo);
	}

}
