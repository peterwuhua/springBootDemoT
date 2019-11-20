package cn.demi.app.appCyd.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.demi.app.appCyd.vo.*;
import cn.demi.app.bus.vo.AppPstandard;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.core.framework.constant.ConstantApp;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.JsonUtil;
import cn.demi.app.appCyd.service.AppTaskXcService;
import cn.demi.app.offfice.vo.AppQjSqEdit;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.sample.service.ISamplingService;
import cn.demi.bus.sample.service.impl.SamplingServiceImpl;
import cn.demi.bus.task.service.ITaskXcService;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.init.std.service.IItemService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("app.appTaskxcAction")
@RequestMapping("/app_task_xc/")
public class AppTaskXcAction {
    @Autowired
    AppTaskXcService appTaskXcService;
    @Autowired
    SamplingServiceImpl samplingservice;
    @Autowired
    IItemService itemService;
    @Autowired
    ISamplingService samplingService;
    @Autowired
    IFilesService filesService;
    @Autowired
    private ITaskXcService taskXcService;

    /**
     * 未办列表
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_task_xc_list.do")
    public String taskXcList(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        OutVo outVo = new OutVo();
        // 任务编号
        String no = request.getParameter("no");
        // 受检单位
        String custName = request.getParameter("custName");
        // 样品名称
        String sampName = request.getParameter("sampName");
        // 检测类型
        String taskType = request.getParameter("taskType");
        // 采样小组人员
        String cyName = request.getParameter("cyName");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        objVo.setPage(Integer.parseInt(page));
        objVo.setRows(Integer.parseInt(rows));
        List<AppTaskXcVo> list = appTaskXcService.listToCyd(objVo, no, custName, sampName, taskType, cyName);
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(list);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 已办列表
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_task_xc_list_ed.do")
    public String taskXcListEd(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        OutVo outVo = new OutVo();
        // 任务编号
        String no = request.getParameter("no");
        // 受检单位
        String custName = request.getParameter("custName");
        // 样品名称
        String sampName = request.getParameter("sampName");
        // 检测类型
        String taskType = request.getParameter("taskType");
        // 采样小组人员
        String cyName = request.getParameter("cyName");
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        objVo.setPage(Integer.parseInt(page));
        objVo.setRows(Integer.parseInt(rows));
        List<AppTaskXcVo> list = appTaskXcService.ListEd(objVo, no, custName, sampName, taskType, cyName);
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(list);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 条码清单
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_task_xc_tm_list.do")
    public String taskTmList(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String taskId = request.getParameter("taskId");
        List<AppTmVo> tmList = appTaskXcService.listByTaskId(objVo, taskId);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(tmList);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 规范清单
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_task_xc_gf_list.do")
    public String taskGfList(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String itemIds = request.getParameter("itemIds");
        List<AppXcCyGfVo> gfList = appTaskXcService.listByItemIds(objVo, itemIds);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(gfList);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * app 填写页面
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_task_xc_edit.do")
    public String taskXcEdit(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String taskId = request.getParameter("taskId");
        String taskSampType = request.getParameter("taskSampType");
        JSONObject jsonObject = appTaskXcService.edit(objVo, taskId);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(jsonObject);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 采样单合并
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_task_xc_cyd_hb.do")
    public String taskXcCydHb(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String ids = request.getParameter("ids").replaceAll(" ", "");
        boolean update4Hb = appTaskXcService.update4Hb(ids);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (update4Hb) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(update4Hb);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 采样单拆分
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_task_xc_cyd_cf.do")
    public String taskXcCydCf(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String id = request.getParameter("id").replaceAll(" ", "");
        boolean update4Cf = taskXcService.update4Cf(id);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (update4Cf) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(update4Cf);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 重置采样单
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_task_xc_cyd_cz.do")
    public String taskXcCydCz(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String taskId = request.getParameter("taskId");
        boolean initAllCyd = appTaskXcService.initAllCyd(taskId);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (initAllCyd == true) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(initAllCyd);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 现场项目结果录入 详情
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_task_xc_item.do")
    public String taskXcEdit4Item(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String taskId = request.getParameter("taskId");
        String cyId = request.getParameter("cyId");
        JSONObject jsonObject = appTaskXcService.edit4Item(taskId, cyId, objVo);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(jsonObject);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 采样单 详情
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_cyd.do")
    public String taskCyd(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String cydId = request.getParameter("cydId");
        JSONObject editCyd = appTaskXcService.editCyd(cydId);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(editCyd);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 监测方法列表
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_samp_source_grid_date.do")
    public String sampSourceGridDate(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        List<AppSampSourceVo> list = appTaskXcService.sampSourceGridData(code, name);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(list);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 添加样品
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_samp_add.do")
    public String addSampling(HttpServletRequest request) throws GlobalException {
        // type 空白样 3 平行样 1
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String pointId = request.getParameter("pointId");
        String type = request.getParameter("type");
        String itemIds = request.getParameter("itemIds");
        boolean addSamp = samplingservice.addSamp(pointId, type, itemIds);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (addSamp) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(addSamp);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 删除
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_samp_delete.do")
    public String deleteSampling(HttpServletRequest request) throws GlobalException {
        // type 空白样 3 平行样 1
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String pointId = request.getParameter("pointId");
        String type = request.getParameter("type");
        boolean delSamp = samplingservice.delSamp(pointId, type);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (delSamp) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(delSamp);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 采样单 内部删除
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_samp_cyd_delete.do")
    public String deleteSamplingCyd(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String id = request.getParameter("id");
        boolean deleteSamp = samplingService.deleteSamp(id);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (deleteSamp) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(deleteSamp);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 采样规范选择
     *
     * @param request
     * @return
     * @throws GlobalException ok
     */
    @ResponseBody
    @RequestMapping(value = "app_samp_select.do")
    public String selectSampling(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String itemIds = request.getParameter("itemIds");
        List<AppSampItem> list = appTaskXcService.sampitemSelect(itemIds);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(list);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 采样单 更新 保存 接口
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_update_cyd.do")
    public String updateCyd(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String appCyd = request.getParameter("appCyd");
        String appCydSampList = request.getParameter("appCydSamples");
        AppCydVo appCydVo = (AppCydVo) JsonUtil.jsonToBean(appCyd, AppCydVo.class);
        List<AppCydSamp> appCydSamps = JsonUtil.jsonToList(appCydSampList, AppCydSamp.class);
        boolean updateCyd = appTaskXcService.updateCyd(appCydVo, appCydSamps);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (updateCyd) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(updateCyd);
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
            JSONArray uploadFile = appTaskXcService.uploadFile(multiRequest, temppath, towFilePath);
            outVo.setData(uploadFile);
        }
        outVo.setCode(ConstantApp.ST_1);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 现场采样 编辑 更新 及提交
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_update_task_edit.do")
    public String updateTaskEdit(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        AppXcEditTaskVo v = new AppXcEditTaskVo();
        v.setId(request.getParameter("taskId"));
        v.setCyUserId(request.getParameter("cyUserId"));
        v.setCyUserName(request.getParameter("cyUserName"));
        v.setCyTime(request.getParameter("cyTime"));
        v.setCyMsg(request.getParameter("cyMsg"));
        v.setIsCommit(request.getParameter("isCommit"));
        String AppXcEditCydVoList = request.getParameter("appXcEditCydVoList");
        String AppXcEditTpVoList = request.getParameter("appXcEditTpVoList");
        List<AppXcEditCydVo> cydList = JsonUtil.jsonToList(AppXcEditCydVoList, AppXcEditCydVo.class);
        List<AppXcEditTpVo> tpList = JsonUtil.jsonToList(AppXcEditTpVoList, AppXcEditTpVo.class);
        boolean updateTask = appTaskXcService.updateTask(v, cydList, objVo, tpList);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (updateTask) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(updateTask);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 现场数据上报 接口
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_update_xc_sb.do")
    public String updateXcSb(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        TaskVo taskVo = new TaskVo();
        taskVo.setId(request.getParameter("taskId"));
        taskVo.setIsCommit(request.getParameter("isCommit"));
        String AppXcEditTimVoList = request.getParameter("appXcEditTimVoList");
        List<AppXcSb4Pad> timList = JsonUtil.jsonToList(AppXcEditTimVoList, AppXcSb4Pad.class);
        boolean update4Xc = appTaskXcService.update4Xc(taskVo, timList);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (update4Xc) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(update4Xc);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 现场分析记录单 检测方法
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_method_list.do")
    public String appMethodList(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String itemId = request.getParameter("itemId");
        List<AppMethod> list = appTaskXcService.findMethodList(itemId);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(list);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 现场分析记录单 检测仪器
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_appara_list.do")
    public String appApparaList(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String MethodId = request.getParameter("methodId");
        List<AppAppara> list = appTaskXcService.showApp(MethodId);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(list);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 退回
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_task_back.do")
    public String appTaskBack(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        TaskVo v = new TaskVo();
        v.setId(request.getParameter("taskId"));
        v.setCyUserId(request.getParameter("cyUserId"));
        v.setCyUserName(request.getParameter("cyUserName"));
        v.setCyTime(request.getParameter("cyTime"));
        v.setCyMsg(request.getParameter("cyMsg"));
        v.setIsCommit(request.getParameter("isCommit"));
        boolean updateBack = appTaskXcService.updateBack(v, objVo);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (updateBack) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(updateBack);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 更新 点位 经纬度
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_update_tp.do")
    public String updateTP(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String id = request.getParameter("id");
        String latAndLng = request.getParameter("latAndLng");
        Boolean updateTp = appTaskXcService.updateTp(id, latAndLng);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (updateTp) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(updateTp);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 删除文件
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_delete_file.do")
    public String deleteFile(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String id = request.getParameter("id");

        Boolean deleteFile = appTaskXcService.deleteFile(id);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (deleteFile) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(deleteFile);
        return JsonUtil.beanToJson(outVo);
    }

    @ResponseBody
    @RequestMapping(value = "app_photo_upload.do")
    public String padPhotoSave(HttpServletRequest request) throws GlobalException {
        String token = request.getParameter("token");
        OutVo outVo = new OutVo();
        outVo.setToken(token);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Object temppath = ApplicationUtils.getValue("config.server.base");
            String path = appTaskXcService.uploadPhoto(multiRequest, temppath, "task_point");
            String tpId = request.getParameter("tpId").replaceAll("\"", "");
            String name = request.getParameter("name").replaceAll("\"", "");
            try {
                List<FilesVo> filesList = new ArrayList<FilesVo>();
                filesService.deleteByBusId(tpId);
                FilesVo fileVo = new FilesVo();
                fileVo.setBusId(tpId);
                fileVo.setBusType(Constants.FILE_TYPE_TASK_POINT);
                fileVo.setFileName(path.substring(path.lastIndexOf("/") + 1));
                fileVo.setFilePath(path);
                fileVo.setFileType(path.substring(path.lastIndexOf(".")));
                fileVo.setName(name);
                filesList.add(fileVo);
                filesService.saveFiles(filesList);
                outVo.setCode(ConstantApp.ST_1);
            } catch (Exception e) {
                outVo.setCode(ConstantApp.ST_0);
                outVo.setData(e.toString());
            }
        }
        return JsonUtil.beanToJson(outVo);
    }

    @ResponseBody
    @RequestMapping(value = "app_photo_upload_cyd_xcptr.do")
    public String padPhotoSaveCydPtr(HttpServletRequest request) throws GlobalException {
        String token = request.getParameter("token");
        OutVo outVo = new OutVo();
        outVo.setToken(token);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Object temppath = ApplicationUtils.getValue("config.server.base");
            String path = appTaskXcService.uploadPhoto(multiRequest, temppath, "wtdwptr");
            String idCyd = request.getParameter("idCyd").replaceAll("\"", "");
            try {
                List<FilesVo> filesList = new ArrayList<FilesVo>();
                filesService.deleteByBusId(idCyd);
                FilesVo fileVo = new FilesVo();
                fileVo.setBusId(idCyd);
                fileVo.setBusType(Constants.FILE_TYPE_TASK_XC_WTDWPTR);
                fileVo.setFileName(path.substring(path.lastIndexOf("/") + 1));
                fileVo.setFilePath(path);
                fileVo.setFileType(path.substring(path.lastIndexOf(".")));
                fileVo.setName(idCyd);
                filesList.add(fileVo);
                filesService.saveFiles(filesList);
                outVo.setCode(ConstantApp.ST_1);
            } catch (Exception e) {
                outVo.setCode(ConstantApp.ST_0);
                outVo.setData(e.toString());
            }
        }
        return JsonUtil.beanToJson(outVo);
    }


    /**
     * 新增点位
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_add_point.do")
    public String addPoint(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String id = request.getParameter("id");

        Boolean addPoint = appTaskXcService.addPoint(id);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (addPoint) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(addPoint);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 删除点位
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_delete_point.do")
    public String deletePoint(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        String pointIds = request.getParameter("pointIds");
        Boolean deletePoint = appTaskXcService.deletePoint(pointIds);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (deletePoint) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(deletePoint);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 现场采样更新点位检测项目信息 根据新检测项目重置样品信息
     *
     * @param request
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_update_tp_item.do")
    public String uptPoint(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        AppXcEditTpVo appXcEditTpVo = (AppXcEditTpVo) JsonUtil.jsonToBean(request.getParameter("appXcEditTpVo"),
                AppXcEditTpVo.class);
        boolean uptPoint = appTaskXcService.uptPoint(appXcEditTpVo, objVo);
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        if (uptPoint) {
            outVo.setCode(ConstantApp.ST_1);
        } else {
            outVo.setCode(ConstantApp.ST_0);
        }
        outVo.setData(uptPoint);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * 选中要添加质控样的项目
     *
     * @param request
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_task_ap_item_select.do")
    public String taskApItemSelectList(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        List<AppTaskApItemSelect> list = appTaskXcService.taskApItemSelectList(request.getParameter("itemIds"));
        OutVo outVo = new OutVo();
        outVo.setCode(ConstantApp.ST_1);
        outVo.setData(list);
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
        List<AppPstandard> list = appTaskXcService.pstandardList(sampTypeId, name);
        outVo.setData(list);
        return JsonUtil.beanToJson(outVo);
    }

    /**
     * app 限值列表
     */
    @ResponseBody
    @RequestMapping(value = "app_pstand_item_list.do")
    public String pstandItemList(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        List<AppPstandItemVo> list = appTaskXcService.pstandItemList(request.getParameter("itemId"), request.getParameter("standId"));
        outVo.setData(list);
        return JsonUtil.beanToJson(outVo);
    }


}
