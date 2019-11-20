package cn.demi.app.appCyd.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import cn.core.framework.utils.*;
import cn.demi.app.appCyd.vo.*;
import cn.demi.app.bus.vo.AppPstandard;
import cn.demi.init.std.service.ISampSourceService;
import cn.demi.init.std.vo.SampSourceVo;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.po.Appara;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EnumCyd;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.appCyd.service.AppTaskXcService;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.ICodeDao;
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Code;
import cn.demi.base.system.po.Files;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pjt.dao.ICustPointDao;
import cn.demi.bus.pjt.dao.ICustRoomDao;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.CustPoint;
import cn.demi.bus.pjt.po.CustRoom;
import cn.demi.bus.pjt.po.Im;
import cn.demi.bus.pjt.po.Scheme;
import cn.demi.bus.pjt.po.SchemePoint;
import cn.demi.bus.sample.dao.ISampCydDao;
import cn.demi.bus.sample.dao.ISampRecordDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampCyd;
import cn.demi.bus.sample.po.SampRecord;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.service.ITaskService;
import cn.demi.bus.task.service.ITaskZbService;
import cn.demi.bus.task.vo.CyrVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.bus.test.vo.TestResultVo;
import cn.demi.init.sp.vo.PcUnit;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.st.vo.SampTypeVo;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IItemMethodDao;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.dao.IPstandItemDao;
import cn.demi.init.std.dao.IPstandardDao;
import cn.demi.init.std.dao.ISampSourceDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.ItemMethod;
import cn.demi.init.std.po.Method;
import cn.demi.init.std.po.PstandItem;
import cn.demi.init.std.po.Pstandard;
import cn.demi.init.std.po.SampSource;
import cn.demi.init.std.service.IMethodService;
import cn.demi.init.std.vo.MethodVo;
import cn.demi.res.service.IApparaService;
import cn.demi.res.vo.ApparaVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("app.taskXcService")
public class AppTaskXcServiceImpl implements AppTaskXcService {
    protected String tablePrefix = PropertiesTools.getPropertiesValueByFileAndKey("resources/jdbc.properties",
            "namingStrategy.tablePrefix");// 数据库表名前缀
    @Autowired
    private IMethodDao methodDao;
    @Autowired
    private IPstandardDao pstandardDao;
    @Autowired
    private ICustPointDao custPointDao;
    @Autowired
    private ISchemePointDao schemePointDao;
    @Autowired
    private ISchemeDao schemeDao;
    @Autowired
    private ICustRoomDao custRoomDao;
    @Autowired
    private ITaskDao taskDao;

    @Autowired
    private ISamplingDao samplingDao;
    @Autowired
    private IItemMethodDao itemMethodDao;
    @Autowired
    private ISampCydDao sampCydDao;
    @Autowired
    private ITaskPointDao taskPointDao;
    @Autowired
    private ITaskItemDao taskItemDao;
    @Autowired
    private ITestItemDao testItemDao;
    @Autowired
    private ITestResultDao testResultDao;
    @Autowired
    private IAccountDao accountDao;
    @Autowired
    private ISampSourceDao sampSourceDao;

    @Autowired
    private ICodeDao codeDao;
    @Autowired
    private ITaskZbService taskZbService;
    @Autowired
    private IItemDao itemDao;
    @Autowired
    private ISampRecordDao sampRecordDao;
    @Autowired
    private IPstandItemDao pstandItemDao;
    @Autowired
    private IOrgDao orgDao;
    @Autowired
    private IProgressDao progressDao;
    @Autowired
    private IProgressLogDao progressLogDao;
    @Autowired
    private IMethodService methodService;
    @Autowired
    private IApparaService apparaService;
    @Autowired
    private IFilesDao filesDao;
    @Autowired
    private ITaskService taskService;

    @Autowired
    private ISampTypeService sampTypeService;
    @Autowired
    private ISampTypeDao sampTypeDao;
    @Autowired
    private IImDao imDao;
    @Autowired
    private IApparaDao apparaDao;
    @Autowired
    private ISampSourceService sampSourceService;


    @Override
    public List<AppTaskXcVo> listToCyd(ObjVo vo, String no, String custName, String sampName, String taskType,
                                       String cyName) throws GlobalException {
        StringBuffer hql = new StringBuffer(
                "SELECT task.id,task.`no`,cust.cust_name,task.samp_name,task.task_type,task.cy_name,task.cy_date,task.cy_end_date, task.cy_id,cust.cust_address ,task.samp_type FROM v_bus_task task");
        hql.append(" JOIN v_bus_cust cust on task.cust_id = cust.id");
        hql.append(" where task.`status`='" + EunmTask.TASK_XC.getStatus() + "'  and  task.cy_id like '%"
                + vo.getUserId() + "%'");
        if (no != null && !("").equals(no)) {
            hql.append("and task.no like '%" + no + "%'");
        }
        if (custName != null && !("").equals(custName)) {
            hql.append("and cust.cust_name like '%" + custName + "%'");
        }
        if (sampName != null && !("").equals(sampName)) {
            hql.append("and task.samp_name like '%" + sampName + "%'");
        }
        if (taskType != null && !("").equals(taskType)) {
            hql.append("and task.task_type like '%" + taskType + "%'");
        }
        if (cyName != null && !("").equals(cyName)) {
            hql.append("and task.cy_name like '%" + cyName + "%'");
        }
        hql.append("and task.is_del = 0  ORDER BY task.last_upd_time DESC");
        Query query = taskDao.queryBysql(hql.toString());
        int page = vo.getPage();
        if (page < 1) {
            page = 1;
        }
        List list = query.setFirstResult((page - 1) * vo.getRows()).setMaxResults(vo.getRows()).getResultList();
        List<AppTaskXcVo> outList = new ArrayList<>();
        if (null != list) {
            for (Object o : list) {
                Object[] objects = (Object[]) o;
                AppTaskXcVo appTaskXcVo = new AppTaskXcVo();
                appTaskXcVo.setId(objects[0] == null ? "" : objects[0].toString());
                appTaskXcVo.setNo(objects[1] == null ? "" : objects[1].toString());
                appTaskXcVo.setCustName(objects[2] == null ? "" : objects[2].toString());
                appTaskXcVo.setSampName(objects[3] == null ? "" : objects[3].toString());
                appTaskXcVo.setTaskType(objects[4] == null ? "" : objects[4].toString());
                appTaskXcVo.setCyName(objects[5] == null ? "" : objects[5].toString());
                appTaskXcVo.setCyDate(objects[6] == null ? "" : objects[6].toString());
                appTaskXcVo.setCyEndDate(objects[7] == null ? "" : objects[7].toString());
                appTaskXcVo.setCyId(objects[8] == null ? "" : objects[8].toString());
                appTaskXcVo.setCustAddress(objects[9] == null ? "" : objects[9].toString());
                appTaskXcVo.setSampType(objects[10] == null ? "" : objects[10].toString());
                if (taskZbService.findById(appTaskXcVo.getId()) != null) {
                    appTaskXcVo.setItemIds(taskZbService.findById(appTaskXcVo.getId()).getItemIds());
                }
                outList.add(appTaskXcVo);
            }
        }

        return outList;
    }

    @Override
    public List<AppTmVo> listByTaskId(ObjVo vo, String taskId) throws GlobalException {
        StringBuffer hql = new StringBuffer(
                "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel=" + Po.N);
        hql.append(" AND task.id ='" + taskId + "'");
        hql.append(" AND type <>'" + Sampling.SAMP_TYPE_XN + "'");
        hql.append(" ORDER BY sampCode asc");
        List<Sampling> sampList = samplingDao.list(hql.toString());
        List<AppTmVo> outList = new ArrayList<>();
        for (Sampling sampling : sampList) {
            AppTmVo appTmVo = new AppTmVo();
            appTmVo.setId(sampling.getId());
            appTmVo.setCyDate(sampling.getTask().getCyDate());
            appTmVo.setImgKey1(sampling.getSampCode());
            appTmVo.setImgKey2(sampling.getItemNames());
            appTmVo.setItemNames(sampling.getItemNames());
            appTmVo.setSampCode(sampling.getSampCode());
            appTmVo.setLy(sampling.getLy());
            appTmVo.setGdj(sampling.getTjj());
            outList.add(appTmVo);
        }
        return outList;
    }

    @Override
    public List<AppXcCyGfVo> listByItemIds(ObjVo vo, String itemIds) throws GlobalException {
        List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
        queryConditions.add(new QueryCondition("item.id in('" + itemIds.replace(",", "','") + "')"));
        queryConditions.add(new QueryCondition("method.isDel =" + Po.N + ""));
        List<OrderCondition> orderConditions = new ArrayList<OrderCondition>();
        orderConditions.add(new OrderCondition("sort", OrderCondition.ORDER_ASC));
        List<ItemMethod> imList = itemMethodDao.list(queryConditions, orderConditions);
        List<AppXcCyGfVo> outList = new ArrayList<>();
        if (null != imList) {
            for (ItemMethod im : imList) {
                AppXcCyGfVo appXcCyGfVo = new AppXcCyGfVo();
                appXcCyGfVo.setId(im.getId());
                appXcCyGfVo.setItemName(im.getItem().getName());
                appXcCyGfVo.setMethodCode(im.getMethod().getCode());
                appXcCyGfVo.setCyAppName(im.getCyAppName());
                appXcCyGfVo.setCtName(im.getCtName());
                appXcCyGfVo.setCyll(im.getCyll());
                appXcCyGfVo.setCysc(im.getCysc());
                appXcCyGfVo.setCytj(im.getCytj());
                appXcCyGfVo.setBcsj(String.valueOf(im.getBcsj()));
                appXcCyGfVo.setCctj(im.getCctj());
                appXcCyGfVo.setRemake(im.getRemark());
                if (StrUtils.isBlankOrNull(im.getCtId()) && StrUtils.isBlankOrNull(im.getCyAppId())
                        && StrUtils.isBlankOrNull(im.getCyll()) && StrUtils.isBlankOrNull(im.getCysc())
                        && StrUtils.isBlankOrNull(im.getCytj()) && StrUtils.isBlankOrNull(im.getCctj())) {
                    continue;
                }
                outList.add(appXcCyGfVo);
            }
        }
        return outList;
    }

    @Override
    public JSONObject edit(ObjVo vo, String taskId) throws GlobalException {
        TaskVo task = taskService.findById(taskId);
        AppXcEditTaskVo appXcEditTaskVo = new AppXcEditTaskVo();
        appXcEditTaskVo.setId(task.getId());
        appXcEditTaskVo.setNo(task.getNo());
        appXcEditTaskVo.setCustCustName(task.getCustVo().getCustName());
        appXcEditTaskVo.setTaskType(task.getTaskType());
        appXcEditTaskVo.setSampName(task.getSampName());
        appXcEditTaskVo.setCyDate(task.getCyDate());
        appXcEditTaskVo.setCyEndDate(task.getCyEndDate());
        appXcEditTaskVo.setXcSt(task.getXcSt());
        appXcEditTaskVo.setCyMsg(task.getCyMsg());
        appXcEditTaskVo.setCyId(task.getCyId());
        appXcEditTaskVo.setPj(task.getPj());
        // 更新最新 人信息 和 时间信息
        appXcEditTaskVo.setCyUserId(vo.getUserId());
        appXcEditTaskVo.setCyUserName(vo.getName());
        if (task.getCyTime() != null) {
            appXcEditTaskVo.setCyTime(task.getCyTime());
        } else {
            appXcEditTaskVo.setCyTime(DateUtils.getCurrDateTimeStr());
        }
        appXcEditTaskVo.setSampType(task.getSampType());
        appXcEditTaskVo.setCustTel(task.getCustVo().getCustTel());
        appXcEditTaskVo.setCustUser(task.getCustVo().getCustUser());
        appXcEditTaskVo.setCustAddress(task.getCustVo().getCustAddress());

        // 采样单列表
        List<SampCyd> cydList = sampCydDao.listByTaskId(taskId);
        List<AppXcEditCydVo> cydOutList = new ArrayList<>();
        if (null != cydList) {
            for (SampCyd cyd : cydList) {
                AppXcEditCydVo appXcEditCydVo = new AppXcEditCydVo();
                appXcEditCydVo.setIdCyd(cyd.getId());
                appXcEditCydVo.setCyDateCyd(cyd.getCyDate());
                appXcEditCydVo.setSortCyd(String.valueOf(cyd.getSort()));
                appXcEditCydVo.setPointNameCyd(cyd.getPointName());
                appXcEditCydVo.setItemNamesCyd(cyd.getItemNames());
                appXcEditCydVo.setSampNumCyd(String.valueOf(cyd.getSampNum()));
                appXcEditCydVo.setTypeCyd(cyd.getType());
                appXcEditCydVo.setCyStCyd(cyd.getCySt());
                cydOutList.add(appXcEditCydVo);
            }
        }
        // 点位情况
        List<TaskPointVo> tpList = task.getTpList();
        List<AppXcEditTpVo> tpOutList = new ArrayList<>();
        if (null != tpList) {
            for (TaskPointVo tp : tpList) {
                AppXcEditTpVo tpVo = new AppXcEditTpVo();
                tpVo.setIdTp(tp.getId());
                tpVo.setSampNameTp(tp.getSampName());
                tpVo.setPointNameTp(tp.getPointName());
                tpVo.setSampTypeIdTp(tp.getSampTypeId());
                tpVo.setPcTp(String.valueOf(tp.getPc()));
                tpVo.setPcUnitTp(tp.getPcUnit());
                tpVo.setSampNumTp(String.valueOf(tp.getSampNum()));
                tpVo.setTypeTp(tp.getType());
                tpVo.setZkNumTp(String.valueOf(tp.getZkNum()));
                tpVo.setPxNumTp(String.valueOf(tp.getPxNum()));
                tpVo.setItemIds(tp.getItemIds());
                tpVo.setItemNamesTp(tp.getItemNames());
                tpVo.setLatAndLng(tp.getLatAndLng());
                List<Files> fileList = filesDao.listByBusid(tp.getId());
                if (fileList.size() > 0) {
                    tpVo.setFilePath(fileList.get(0).getFilePath());
                }
                tpVo.setSampTypeName(tp.getSampTypeName());
                tpVo.setPointCode(tp.getPointCode());
                tpVo.setImId(tp.getImId());
                tpVo.setSort(String.valueOf(tp.getSort()));
                tpOutList.add(tpVo);
            }
        }

        // 转出去
        JSONObject obj = new JSONObject();
        obj.put("taskVo", appXcEditTaskVo);
        obj.put("CydList", cydOutList);
        obj.put("tpList", tpOutList);
        obj.put("cydEnum", EnumCyd.getList4Hj());

        List<SampTypeVo> sampListTemp = null;
        if (StrUtils.isNotBlankOrNull(task.getSampTypeId())) {
            sampListTemp = sampTypeService.listByIds(task.getSampTypeId());
        }
        List<AppSampListVo> sampList = new ArrayList<>();
        for (SampTypeVo stPo : sampListTemp) {
            AppSampListVo spVo = new AppSampListVo();
            spVo.setId(stPo.getId());
            spVo.setName(stPo.getName());
            sampList.add(spVo);
        }
        obj.put("sampList", sampList);
        return obj;
    }

    @Override
    public boolean update4Hb(String ids) throws GlobalException {
        List<SampCyd> cydList = sampCydDao.listByIds(ids, "sort", "asc");
        SampCyd cyd = cydList.get(0);
        String jpql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N
                + "' AND cyd.id in('" + ids.replace(",", "','") + "')";
        List<Sampling> sampList = samplingDao.list(jpql);
        try {
            for (Sampling sampling : sampList) {
                sampling.setCyd(cyd);
                samplingDao.update(sampling);
            }
            cydList.remove(cyd);
            for (SampCyd sampCyd : cydList) {
                sampCydDao.delete(sampCyd);
            }
            // 更新采样单
            uptCyd(cyd);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update4Cf(String id) throws GlobalException {
        SampCyd old = sampCydDao.findById(id);
        List<Sampling> sampList = samplingDao.listByCyd(old.getId());
        Task task = old.getTask();
        List<SampCyd> cydList = new ArrayList<>();
        cydList.add(old);
        for (Sampling samp : sampList) {
            EnumCyd cyd = null;
            if (samp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
                cyd = EnumCyd.getCyd4Hj(samp.getSampTypeName(), samp.getItemNames());
            } else {
                cyd = EnumCyd.getCyd4Zw(samp.getItemNames());
            }
            String type = null;
            if (cyd != null) {
                type = cyd.getCode();
            }
            ;
            if (samp.getPoint().getId().equals(old.getPointId()) && old.getType().equals(cyd.getCode())) {
                continue;//当前样品属于该采样单，不拆分
            }
            // 不属于时，查询是否有满足的采样单
            SampCyd sampCyd = sampCydDao.find4Auto(samp.getPoint().getId(), type, samp.getCyDate());
            //SampCyd sampCyd = sampCydDao.find4Task(samp.getTask().getId(), type, samp.getCyDate());
            if (sampCyd == null) {
                sampCyd = new SampCyd();
                sampCyd.setTask(task);
                sampCyd.setPointId(samp.getPoint().getId());
                sampCyd.setItemIds(samp.getItemIds());
                sampCyd.setItemNames(samp.getItemNames());
                sampCyd.setType(type);
                sampCyd.setCyDate(samp.getCyDate());
                sampCyd.setSampType(samp.getSampType());
                sampCyd.setSampName(samp.getSampName());
                sampCyd.setCySt(String.valueOf(Po.N));
                sampCyd.setSort(sampCydDao.getMaxSort(task.getId()));
                sampCydDao.add(sampCyd);
            }
            samp.setCyd(sampCyd);
            samplingDao.update(samp);
            cydList.add(sampCyd);
        }
        for (SampCyd cyd : cydList) {
            List<Sampling> newList = samplingDao.listByCyd(cyd.getId());
            if (newList == null || newList.size() <= 0) {
                sampCydDao.delete(cyd);
            } else {
                uptCyd(cyd);
            }
        }
        return true;
    }

    public void uptCyd(SampCyd cyd) {
        List<Sampling> sampList = samplingDao.listByCyd(cyd.getId());
        Set<String> nameSet = new HashSet<String>();
        Set<String> itemIdSet = new HashSet<String>();
        Set<String> itemNameSet = new HashSet<String>();
        Set<String> roomSet = new HashSet<String>();
        Set<String> pointSet = new HashSet<String>();
        Set<String> pointIdSet = new HashSet<String>();
        for (Sampling samp : sampList) {
            nameSet.add(samp.getSampName());
            itemIdSet.addAll(Arrays.asList(samp.getItemIds().split(",")));
            itemNameSet.addAll(Arrays.asList(samp.getItemNames().split(",")));
            roomSet.add(samp.getPoint().getRoom());
            pointSet.add(samp.getPointName());
            pointIdSet.add(samp.getPoint().getId());
        }
        cyd.setRoom(String.join(",", roomSet));
        cyd.setPointId(String.join(",", pointIdSet));
        cyd.setPointName(String.join(",", pointSet));
        cyd.setSampName(String.join(",", nameSet));
        cyd.setItemIds(String.join(",", itemIdSet));
        cyd.setItemNames(String.join(",", itemNameSet));
        cyd.setSampNum(sampList.size());
        sampCydDao.update(cyd);
    }

    @Override
    public boolean initAllCyd(String taskId) throws GlobalException {
        Task p = taskDao.findById(taskId);
        // 重置采样单
        List<Sampling> sampList = samplingDao.listAllByTaskId(p.getId());
        for (Sampling samp : sampList) {
            samp.setCyd(null);
            samplingDao.update(samp);
        }
        // 生成采样单
        initCyd(p);
        return true;
    }

    /**
     * 生成采样单 现场项目每个项目单独一个采样单 实验室项目根据编号 同编号一个采样单
     *
     * @param p
     */
    public void initCyd(Task p) {
        sampCydDao.deleteAll(sampCydDao.listByTaskId(p.getId()));
        // 处理现场项目
        String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND task.id ='"
                + p.getId() + "' ORDER BY point.sort,sort,cyDate,p ASC";
        List<Sampling> samplist = samplingDao.list(hql);
        for (Sampling samp : samplist) {
            EnumCyd cyd = null;
            if (samp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
                cyd = EnumCyd.getCyd4Hj(samp.getSampTypeName(), samp.getItemNames());
            } else {
                cyd = EnumCyd.getCyd4Zw(samp.getItemNames());
            }
            String type = null;
            if (cyd != null) {
                type = cyd.getCode();
            }
            ;
            SampCyd sampCyd = sampCydDao.find4Auto(samp.getPoint().getId(), type, samp.getCyDate());
            //SampCyd sampCyd = sampCydDao.find4Task(p.getId(), type, samp.getCyDate());
            if (sampCyd != null) {
                samp.setCyd(sampCyd);
            } else {
                sampCyd = new SampCyd();
                sampCyd.setTask(p);
                sampCyd.setPointId(samp.getPoint().getId());
                sampCyd.setItemIds(samp.getItemIds());
                sampCyd.setItemNames(samp.getItemNames());
                sampCyd.setType(type);
                sampCyd.setCyDate(samp.getCyDate());
                sampCyd.setSampType(samp.getSampType());
                sampCyd.setSampName(samp.getSampName());
                sampCyd.setCySt(String.valueOf(Po.N));
                sampCydDao.add(sampCyd);
                samp.setCyd(sampCyd);
            }
            samplingDao.update(samp);
        }
        List<SampCyd> cydList = sampCydDao.listByTaskId(p.getId());
        if (null != cydList) {
            int sort = 1;
            for (SampCyd cyd : cydList) {
                cyd.setSort(sort);
                uptCyd(cyd);
                sort++;
            }
        }
    }

    @Override
    public JSONObject edit4Item(String taskId, String cyId, ObjVo objVo) throws GlobalException {
        checkItem(taskId, objVo);
        Task task = taskDao.findById(taskId);
        AppXcEditTaskVo appXcEditTaskVo = new AppXcEditTaskVo();
        appXcEditTaskVo.setId(task.getId());
        appXcEditTaskVo.setPj(task.getPj());

        List<TaskItem> timList = taskItemDao.listByTaskId(taskId, String.valueOf(Po.Y));
        List<AppXcEditTimVo> timVoList = new ArrayList<>();
        if (null != timList) {
            for (TaskItem tim : timList) {
                AppXcEditTimVo timVo = new AppXcEditTimVo();
                timVo.setItemNameTim(tim.getItemName());
                timVo.setIdTim(tim.getId());
                timVo.setItemIdTim(tim.getItemId());
                timVo.setTestManIdTim(tim.getTestManId());
                timVo.setTestManTim(tim.getTestMan());
                timVo.setMethodNameTim(tim.getMethodName());
                timVo.setMethodIdTim(tim.getMethodId());
                timVo.setAppNameTim(tim.getAppName());
                timVo.setAppIdTim(tim.getAppId());
                timVo.setLimitLineTim(tim.getLimitLine());
                timVo.setWdTim(tim.getWd());
                timVo.setSdTim(tim.getSd());
                timVo.setStandNameTim(tim.getStandName());
                timVo.setStandIdTim(tim.getStandId());
                timVo.setSampTypeIdTim(tim.getSampTypeId());
                timVo.setLimitedTim(tim.getLimited());
                timVo.setUnitTim(tim.getUnit());
                timVo.setSampTypeNameTim(tim.getSampTypeName());
                timVo.setRemarkTim(tim.getRemark());
                timVo.setCheckManIdTim(tim.getCheckManId());
                timVo.setCheckManTim(tim.getCheckMan());
                timVo.setAppXcEditItemLIst(list4Item(tim.getId()));
                timVo.setTestTimeTim(tim.getTestTime());
                timVoList.add(timVo);
            }
        }

        List<Account> accList = accountDao.listByIds(cyId);
        List<AppXcEditUserVo> userOutList = new ArrayList<>();
        for (Account account : accList) {
            AppXcEditUserVo userVo = new AppXcEditUserVo();
            userVo.setIdUser(account.getId());
            userVo.setUserNameUser(account.getUser().getName());
            userOutList.add(userVo);
        }

        JSONObject object = new JSONObject();
        object.put("taskVo", appXcEditTaskVo);
        object.put("timList", timVoList);
        object.put("userList", userOutList);
        return object;
    }

    ;

    public List<AppXcEditItemVo> list4Item(String timId) throws GlobalException {
        List<TestItem> itList = testItemDao.listByTimId(timId);
        List<AppXcEditItemVo> itVoList = new ArrayList<>();
        if (null != itList) {
            for (TestItem it : itList) {
                AppXcEditItemVo itVo = new AppXcEditItemVo();
                TestResultVo trVo = findResult(it.getId());
                itVo.setPointVoPointName(it.getPoint().getPointName());
                itVo.setIdItem(it.getId());
                itVo.setTrVoIdItem(trVo.getId());
                itVo.setCyDateItem(it.getCyDate());
                itVo.setTrVoSampVoPItem(trVo.getSampVo().getP());
                itVo.setTrVoSampVoSampCodeItem(trVo.getSampVo().getSampCode());
                itVo.setTrVoValueItem(trVo.getValue());
                itVo.setResultItem(it.getResult());
                itVoList.add(itVo);
            }
        }
        return itVoList;
    }

    // 获取测试项目的结果信息
    public TestResultVo findResult(String itId) throws GlobalException {
        List<TestResult> rList = testResultDao.listByItId(itId);
        TestResultVo trVo = new TestResultVo();
        if (null != rList && rList.size() > 0) {

            trVo = trVo.toVo(rList.get(0));
        }
        return trVo;
    }

    @Override
    public JSONObject editCyd(String id) throws GlobalException {
        SampCyd cyd = sampCydDao.findById(id);
        AppCydVo appCydVo = new AppCydVo();
        appCydVo.setFx(cyd.getFx());
        appCydVo.setFxId(cyd.getFxId());
        appCydVo.setFxName(cyd.getFxName());
        appCydVo.setGnq(cyd.getGnq());
        appCydVo.setIdAppCyd(cyd.getId());
        appCydVo.setFs(cyd.getFs());
        appCydVo.setTx(cyd.getTx());
        appCydVo.setQw(cyd.getQw());
        appCydVo.setQy(cyd.getQy());
        appCydVo.setSd(cyd.getSd());
        appCydVo.setCyStandId(cyd.getCyStandId());
        if (StrUtils.isNotBlankOrNull(cyd.getCyStandId())) {
            String[] splitSampSourceId = cyd.getCyStandId().split(",");
            String cyStandName = new String();
            for (String s : splitSampSourceId) {
                String name = sampSourceService.findById(s).getName() + ",";
                cyStandName = name + cyStandName;
            }
            appCydVo.setCyStandName(cyStandName.substring(0, cyStandName.length() - 1));
        }
        appCydVo.setCyAppId(cyd.getCyAppId());
        if (StrUtils.isNotBlankOrNull(cyd.getCyAppId())) {
            String[] splitAppara = cyd.getCyAppId().split(",");
            String cyAppName = new String();
            for (String s : splitAppara) {
                String name = apparaService.findById(s).getName() + ",";
                cyAppName = name + cyAppName;
            }
            appCydVo.setCyAppName(cyAppName.substring(0, cyAppName.length() - 1));
        }

        appCydVo.setCyId(cyd.getCyId());
        appCydVo.setCyName(cyd.getCyName());
        appCydVo.setJhId(cyd.getJhId());
        appCydVo.setJhName(cyd.getJhName());
        if (StrUtils.isBlankOrNull(appCydVo.getJhId())) {
            appCydVo.setJhId(cyd.getTask().getFzId());
            appCydVo.setJhName(cyd.getTask().getFzName());
        }
        appCydVo.setDeme1(cyd.getDeme1());
        appCydVo.setDeme2(cyd.getDeme2());
        appCydVo.setDeme3(cyd.getDeme3());
        appCydVo.setDeme4(cyd.getDeme4());
        appCydVo.setDeme5(cyd.getDeme5());
        appCydVo.setDeme6(cyd.getDeme6());
        appCydVo.setDeme7(cyd.getDeme7());
        appCydVo.setDeme8(cyd.getDeme8());
        appCydVo.setDeme9(cyd.getDeme9());
        appCydVo.setDeme10(cyd.getDeme10());
        appCydVo.setDeme11(cyd.getDeme11());
        appCydVo.setDeme12(cyd.getDeme12());
        appCydVo.setDeme13(cyd.getDeme13());
        appCydVo.setDeme14(cyd.getDeme14());
        appCydVo.setDeme15(cyd.getDeme15());
        appCydVo.setDeme16(cyd.getDeme16());
        appCydVo.setDeme17(cyd.getDeme17());
        appCydVo.setDeme18(cyd.getDeme18());
        appCydVo.setDeme19(cyd.getDeme19());
        appCydVo.setDeme20(cyd.getDeme20());
        appCydVo.setDeme21(cyd.getDeme21());
        appCydVo.setDeme22(cyd.getDeme22());
        appCydVo.setDeme23(cyd.getDeme23());
        appCydVo.setDeme24(cyd.getDeme24());
        appCydVo.setXcDesc(cyd.getXcDesc());
        appCydVo.setPtUser(cyd.getPtUser());
        String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND cyd.id ='" + cyd.getId() + "'   ORDER BY zkType,itemIds,sort asc";
        List<Sampling> sampList = samplingDao.list(hql);
        List<AppCydSamp> sampVoList = new ArrayList<>();
        if (null != sampList) {
            for (Sampling samp : sampList) {
                AppCydSamp sampVo = new AppCydSamp();
                sampVo.setIdCydSamp(samp.getId());
                sampVo.setItemNames(samp.getItemNames());
                sampVo.setWorkHours(samp.getWorkHours());
                sampVo.setWorkPc(samp.getWorkPc());
                sampVo.setPointName(samp.getPointName());
                sampVo.setCyTime(samp.getCyTime());
                sampVo.setSampCode(samp.getSampCode());
                sampVo.setXz(samp.getXz());
                sampVo.setSampName(samp.getSampName());
                sampVo.setFcType(samp.getFcType());
                sampVo.setZkType(samp.getZkType());
                if (samp.getRecord() != null) {
                    sampVo.setRecordVoId(samp.getRecord().getId());
                    sampVo.setRecordVoV1(samp.getRecord().getV1());
                    sampVo.setRecordVoV2(samp.getRecord().getV2());
                    sampVo.setRecordVoV3(samp.getRecord().getV3());
                    sampVo.setRecordVoV4(samp.getRecord().getV4());
                    sampVo.setRecordVoAvg1(samp.getRecord().getAvg1());
                    sampVo.setRecordVoAvg2(samp.getRecord().getAvg2());
                    sampVo.setRecordVoDemo1(samp.getRecord().getDemo1());
                    sampVo.setRecordVoDemo2(samp.getRecord().getDemo2());
                    sampVo.setRecordVoDemo3(samp.getRecord().getDemo3());
                    sampVo.setRecordVoDemo4(samp.getRecord().getDemo4());
                    sampVo.setRecordVoDemo5(samp.getRecord().getDemo5());
                    sampVo.setRecordVoDemo6(samp.getRecord().getDemo6());
                    sampVo.setRecordVoDemo7(samp.getRecord().getDemo7());
                    sampVo.setRecordVoDemo8(samp.getRecord().getDemo8());
                    sampVo.setRecordVoDemo9(samp.getRecord().getDemo9());
                    sampVo.setRecordVoDemo10(samp.getRecord().getDemo10());
                    sampVo.setRecordVoDemo11(samp.getRecord().getDemo11());
                    sampVo.setRecordVoDemo12(samp.getRecord().getDemo12());
                    sampVo.setRecordVoDemo13(samp.getRecord().getDemo13());
                    sampVo.setRecordVoDemo14(samp.getRecord().getDemo14());
                    sampVo.setRecordVoDemo15(samp.getRecord().getDemo15());
                    sampVo.setRecordVoDemo16(samp.getRecord().getDemo16());
                    sampVo.setRecordVoDemo17(samp.getRecord().getDemo17());
                    sampVo.setRecordVoDemo18(samp.getRecord().getDemo18());
                    sampVo.setRecordVoDemo19(samp.getRecord().getDemo19());
                    sampVo.setRecordVoDemo20(samp.getRecord().getDemo20());
                    sampVo.setRecordVoDemo21(samp.getRecord().getDemo21());
                    sampVo.setRecordVoDemo22(samp.getRecord().getDemo22());
                    sampVo.setCyEndTime(samp.getCyEndTime());
                    sampVo.setNum(samp.getNum());
                }
                if (StrUtils.isBlankOrNull(sampVo.getWorkHours())) { // 采样单接触时间为空或null默认父页面现场情况里的接触时间
                    sampVo.setWorkHours(samp.getPoint().getWorkHours());
                }
                if (StrUtils.isBlankOrNull(sampVo.getWorkPc())) { // 采样单接触频次为空或null默认父页面现场情况里的接触频次
                    sampVo.setWorkPc(samp.getPoint().getWorkPc());
                }
                if (StrUtils.isBlankOrNull(sampVo.getSampCode())) {
                    int ptSort = taskPointDao.getSort4Code(cyd.getTask().getId(), samp.getPoint().getId(),
                            samp.getSampTypeId());
                    if (samp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
                        sampVo.setSampCode(samplingDao.createSampCodeHj(samp.getTask().getReportNo(),
                                samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP()));
                    } else {
                        sampVo.setSampCode(samplingDao.createSampCodeZw(samp.getType(), samp.getSampType(),
                                samp.getTask().getReportNo(), samp.getItemIds(), samp.getPoint().getSort(),
                                samp.getP()));
                    }
                }

                if (StrUtils.isBlankOrNull(sampVo.getWorkHours())) { // 采样单接触时间为空或null默认父页面现场情况里的接触时间
                    sampVo.setWorkHours(samp.getPoint().getWorkHours());
                }
                if (StrUtils.isBlankOrNull(sampVo.getWorkPc())) { // 采样单接触频次为空或null默认父页面现场情况里的接触频次
                    sampVo.setWorkPc(samp.getPoint().getWorkPc());
                }
                if (cyd.getType().equals(EnumCyd.HJ_G.getCode())) {//固体底泥采样单数据
                    if (StrUtils.isBlankOrNull(sampVo.getRecordVoDemo1())) {
                        sampVo.setRecordVoDemo1(samp.getPointCode());
                    }
                }
                if (StrUtils.isBlankOrNull(sampVo.getSampCode()) && !sampVo.getZkType().equals(Sampling.SAMP_TYPE_PT)) {
                    int ptSort = taskPointDao.getSort4Code(cyd.getTask().getId(), samp.getPoint().getId(), samp.getSampTypeId());
                    sampVo.setSampCode(samplingDao.createSampCodeZk(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP(),
                            samp.getZkType()));
                } else if (StrUtils.isBlankOrNull(sampVo.getSampCode()) && sampVo.getZkType().equals(Sampling.SAMP_TYPE_PT)) {
                    int ptSort = taskPointDao.getSort4Code(cyd.getTask().getId(), samp.getPoint().getId(), samp.getSampTypeId());
                    if (samp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
                        sampVo.setSampCode(samplingDao.createSampCodeHj(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP()));
                    } else {
                        sampVo.setSampCode(samplingDao.createSampCodeZw(samp.getType(), samp.getSampType(), samp.getTask().getReportNo(), samp.getItemIds(),
                                samp.getPoint().getSort(), samp.getP()));
                    }
                }
                sampVoList.add(sampVo);
            }
        }
        // 采样设备
        List<ApparaVo> apparaVoList = apparaService.listByIds(cyd.getTask().getCyAppIds());
        List<AppCydAppare> appvoList = new ArrayList<>();
        if (null != apparaVoList && apparaVoList.size() > 0) {
            for (ApparaVo app : apparaVoList) {
                AppCydAppare appVo = new AppCydAppare();
                appVo.setAppId(app.getId());
                appVo.setAppName(app.getName());
                appvoList.add(appVo);
            }
        }
// 检测方法
        List<SampSourceVo> sampSourceVoList = sampSourceService.listByIds(cyd.getTask().getCyStandIds());
        List<AppSampSource> sampSourcevoList = new ArrayList<>();
        if (null != apparaVoList && apparaVoList.size() > 0) {
            for (SampSourceVo sampSource : sampSourceVoList) {
                AppSampSource appVo = new AppSampSource();
                appVo.setId(sampSource.getId());
                appVo.setName(sampSource.getName());
                sampSourcevoList.add(appVo);
            }
        }

        // 分析人 校核人
        List<Account> users = accountDao.listByIds(cyd.getTask().getCyId());
        List<AppCydUser> cydusers = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            AppCydUser cyduser = new AppCydUser();
            cyduser.setIdCydUser(users.get(i).getId());
            cyduser.setUserName(users.get(i).getUser().getName());
            cydusers.add(cyduser);
        }
        // 采样人
        List<CyrVo> cyrs = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            CyrVo cyr = new CyrVo();
            cyr.setId(users.get(i).getId());
            cyr.setName(users.get(i).getUser().getName());
            cyrs.add(cyr);
        }
        // 天气
        Code tq = codeDao.findByCode("xc-tq");
        // 风向
        Code fx = codeDao.findByCode("xc-fx");
        // 排放类型
        Code pflx = codeDao.findByCode("xc-runType");
        // 样品保存条件
        Code ypbctj = codeDao.findByCode("save-request");
        // 功能区
        Code gnq = codeDao.findByCode("xc-gnq");
        JSONObject obj = new JSONObject();
        obj.put("CydVo", appCydVo);
        obj.put("sampList", sampVoList);
        //obj.put("zkList", zkVoList);
        obj.put("appList", appvoList);
        obj.put("cydUserList", cydusers);
        obj.put("cyrList", cyrs);
        obj.put("tq", toList(tq.getContent()));
        obj.put("fx", toList(fx.getContent()));
        obj.put("pflx", toList(pflx.getContent()));
        obj.put("ypbctj", toList(ypbctj.getContent()));
        obj.put("gnq", toList(gnq.getContent()));
        obj.put("sampSourceList", sampSourcevoList);
        return obj;
    }

    private List<String> toList(String content) {
        if (content == null) {
            content = "";
        }
        return StrUtils.splitList(content, ',');
    }

    @Override
    public List<AppSampSourceVo> sampSourceGridData(String code, String name) throws GlobalException {
        String hql = "FROM " + sampSourceDao.getEntityName(SampSource.class) + " WHERE isDel='" + Po.N + "'";
        if (code != null && !("").equals(code)) {
            hql += "and code like '%" + code + "%'";
        }
        if (name != null && !("").equals(name)) {
            hql += "and  name like '%" + name + "%'";
        }
        hql += " ORDER BY  sort  ASC";
        List<SampSource> list = sampSourceDao.list(hql);
        List<AppSampSourceVo> outList = new ArrayList<>();
        if (null != list) {
            for (SampSource sampSource : list) {
                AppSampSourceVo sampSourceVo = new AppSampSourceVo();
                sampSourceVo.setId(sampSource.getId());
                sampSourceVo.setCode(sampSource.getCode());
                sampSourceVo.setName(sampSource.getName());
                outList.add(sampSourceVo);
            }
        }
        return outList;
    }

    @Override
    public List<AppSampItem> sampitemSelect(String itemIds) throws GlobalException {
        List<Item> list = itemDao.listByIds(itemIds);
        List<AppSampItem> outList = new ArrayList<>();
        for (Item item : list) {
            AppSampItem appSampItem = new AppSampItem();
            appSampItem.setId(item.getId());
            appSampItem.setIsNow(item.getIsNow());
            appSampItem.setName(item.getName());
            appSampItem.setStandNames(item.getStandNames());
            appSampItem.setUnit(item.getUnit());
            outList.add(appSampItem);
        }
        return outList;
    }

    @Override
    public boolean updateCyd(AppCydVo v, List<AppCydSamp> sampList) throws GlobalException {
        try {
            SampCyd cyd = sampCydDao.findById(v.getIdAppCyd());
            cyd.setCyId(v.getCyId());
            cyd.setCyName(v.getCyName());
            cyd.setFxId(v.getFxId());
            cyd.setFxName(v.getFxName());
            cyd.setJhId(v.getJhId());
            cyd.setJhName(v.getJhName());
            cyd.setPtUser(v.getPtUser());
            cyd.setSampNum(v.getSampNum());
            if (!StrUtils.isBlankOrNull(v.getCyAppId())) {
                List<Appara> applist = apparaDao.listByIds(v.getCyAppId());
                List<String> idList = new ArrayList<>();
                List<String> nameList = new ArrayList<>();
                for (Appara appara : applist) {
                    idList.add(appara.getId());
                    nameList.add(appara.getName() + appara.getSpec() + "(" + appara.getNo() + ")");
                }
                cyd.setCyAppId(String.join(",", idList));
                cyd.setCyAppName(String.join(",", nameList));
            }
            if (!StrUtils.isBlankOrNull(v.getCyStandId())) {
                List<SampSource> ssList = sampSourceDao.listByIds(v.getCyStandId());
                List<String> idList = new ArrayList<>();
                List<String> nameList = new ArrayList<>();
                for (SampSource ss : ssList) {
                    idList.add(ss.getId());
                    nameList.add(ss.getCode() + ss.getName());
                }
                cyd.setCyStandId(String.join(",", idList));
                cyd.setCyStandName(String.join(",", nameList));
            }
            cyd.setGnq(v.getGnq());
            cyd.setRunType(v.getRunType());
            cyd.setItemType(v.getItemType());
            cyd.setQw(v.getQw());
            cyd.setQy(v.getQy());
            cyd.setFx(v.getFx());
            cyd.setFs(v.getFs());
            cyd.setSd(v.getSd());
            cyd.setTx(v.getTx());
            cyd.setXcDesc(v.getXcDesc());
            cyd.setDeme1(v.getDeme1());
            cyd.setDeme2(v.getDeme2());
            cyd.setDeme3(v.getDeme3());
            cyd.setDeme4(v.getDeme4());
            cyd.setDeme5(v.getDeme5());
            cyd.setDeme6(v.getDeme6());
            cyd.setDeme7(v.getDeme7());
            cyd.setDeme8(v.getDeme8());
            cyd.setDeme9(v.getDeme9());
            cyd.setDeme10(v.getDeme10());
            cyd.setDeme11(v.getDeme11());
            cyd.setDeme12(v.getDeme12());
            cyd.setDeme13(v.getDeme13());
            cyd.setDeme14(v.getDeme14());
            cyd.setDeme15(v.getDeme15());
            cyd.setDeme16(v.getDeme16());
            cyd.setDeme17(v.getDeme17());
            cyd.setDeme18(v.getDeme18());
            cyd.setDeme19(v.getDeme19());
            cyd.setDeme20(v.getDeme20());
            cyd.setDeme21(v.getDeme21());
            cyd.setDeme22(v.getDeme22());
            cyd.setDeme23(v.getDeme23());
            cyd.setDeme24(v.getDeme24());

            if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_S)) {
                cyd.setCySt(TaskPoint.ST_1);
            } else if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
                cyd.setCySt(TaskPoint.ST_2);
            }


            // 更新普通样


            int num = 0;
            if (sampList != null) {
                for (AppCydSamp sampVo : sampList) {
                    Sampling samp = samplingDao.findById(sampVo.getIdCydSamp());
                    samp.setCyTime(sampVo.getCyTime());
                    samp.setCyEndTime(sampVo.getCyEndTime());
                    if (!StrUtils.isBlankOrNull(sampVo.getSampCode())) {
                        samp.setSampCode(sampVo.getSampCode());
                    }
                    samp.setXz(sampVo.getXz());
                    samp.setTjj(samp.getTjj());
                    samp.setNum(sampVo.getNum());
                    samp.setWorkHours(sampVo.getWorkHours());
                    samp.setWorkPc(sampVo.getWorkPc());
                    samp.setTj(samp.getTj());
                    samp.setFcType(samp.getFcType());
                    samp.setV1(samp.getV1());
                    samp.setV2(samp.getV2());
                    samp.setRemark(samp.getRemark());
                    samp.setFcType(sampVo.getFcType());
                    SampRecord record;
                    if (StrUtils.isNotBlankOrNull(sampVo.getRecordVoId())) {
                        record = sampRecordDao.findById(sampVo.getRecordVoId());
                    } else {
                        record = new SampRecord();
                    }
                    record.setV1(sampVo.getRecordVoV1());
                    record.setV2(sampVo.getRecordVoV2());
                    record.setV3(sampVo.getRecordVoV3());
                    record.setV4(sampVo.getRecordVoV4());
                    record.setAvg1(sampVo.getRecordVoAvg1());
                    record.setAvg2(sampVo.getRecordVoAvg2());
                    record.setDemo1(sampVo.getRecordVoDemo1());
                    record.setDemo2(sampVo.getRecordVoDemo2());
                    record.setDemo3(sampVo.getRecordVoDemo3());
                    record.setDemo4(sampVo.getRecordVoDemo4());
                    record.setDemo5(sampVo.getRecordVoDemo5());
                    record.setDemo6(sampVo.getRecordVoDemo6());
                    record.setDemo7(sampVo.getRecordVoDemo7());
                    record.setDemo8(sampVo.getRecordVoDemo8());
                    record.setDemo9(sampVo.getRecordVoDemo9());
                    record.setDemo10(sampVo.getRecordVoDemo10());
                    record.setDemo11(sampVo.getRecordVoDemo11());
                    record.setDemo12(sampVo.getRecordVoDemo12());
                    record.setDemo13(sampVo.getRecordVoDemo13());
                    record.setDemo14(sampVo.getRecordVoDemo14());
                    record.setDemo15(sampVo.getRecordVoDemo15());
                    record.setDemo16(sampVo.getRecordVoDemo16());
                    record.setDemo17(sampVo.getRecordVoDemo17());
                    record.setDemo18(sampVo.getRecordVoDemo18());
                    record.setDemo19(sampVo.getRecordVoDemo19());
                    record.setDemo20(sampVo.getRecordVoDemo20());
                    record.setDemo21(sampVo.getRecordVoDemo21());
                    record.setDemo22(sampVo.getRecordVoDemo22());
                    sampRecordDao.saveOrUpdate(record);
                    samp.setRecord(record);
                    if (samp.getPoint().getType() != null && (samp.getPoint().getType().equals(Constants.SAMP_S) || samp.getPoint().getType().equals(Constants.SAMP_Q))) {
                        if (!StrUtils.isBlankOrNull(samp.getCyEndTime()) && !StrUtils.isBlankOrNull(samp.getCyTime())) {
                            long saveHour = DateUtils.getIntevalHours(samp.getCyDate() + " " + samp.getCyTime(), samp.getCyDate() + " " + samp.getCyEndTime());
                            samp.setCyHours(Long.valueOf(saveHour).intValue());//获取环境的气 声 采样时间长 用于计算小时 8 小时 日均值等数据
                        }
                    }
                    samplingDao.update(samp);
                    uptTaskItem(samp);
                }
                num += sampList.size();
            }
            cyd.setSampNum(num);
            sampCydDao.update(cyd);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    //已生成的现场项目任务的，更新样品信息 同步到项目任务表中
    public void uptTaskItem(Sampling samp) {
        List<Item> itemList = itemDao.listByIds(samp.getItemIds(), Constants.S);
        if (null != itemList) {
            for (Item item : itemList) {
                TestResult tr = testResultDao.findBySampAndItem(samp.getId(), item.getId());
                if (null != tr) {
                    TestItem it = tr.getIt();
                    if (null != samp.getRecord()) {
                        if ((item.getName().contains("一氧化碳") || item.getName().contains("二氧化碳"))) {
                            it.setStel(samp.getRecord().getAvg1());
                            it.setTwa(samp.getRecord().getAvg2());
                        } else if ((item.getName().contains("二氧化硅含量") || item.getName().contains("超高频辐射") || item.getName().contains("工频电场") || item.getName().contains("激光辐射")
                                || item.getName().contains("微波辐射"))) {
                            checkAvg(samp, item);
                            it.setValue(samp.getRecord().getAvg1());
                        } else if (item.getName().contains("高频电磁场")) {
                            checkAvg(samp, item);
                            it.setValue(samp.getRecord().getAvg1());// 电场
                            it.setValue2(samp.getRecord().getAvg2());// 磁场
                        } else if (item.getName().contains("紫外辐射")) {
                            it.setValue(samp.getRecord().getAvg1());
                        } else if ((item.getName().contains("手传振动") || item.getName().contains("噪声")) && null != samp.getRecord()) {
                            it.setValue(samp.getRecord().getAvg1());
                            it.setValue2(samp.getRecord().getAvg2());
                        } else if (item.getName().contains("照度")) {
                            if (null != samp.getCyd().getItemType() && samp.getCyd().getItemType().equals("照度平均值")) {
                                it.setValue(samp.getRecord().getAvg1());
                            } else {
                                it.setValue(samp.getRecord().getAvg2());
                            }
                        } else if (item.getName().contains("高温")) {
                            double d1 = 0;
                            try {
                                d1 = Double.valueOf(samp.getRecord().getAvg1());
                            } catch (Exception e) {
                                d1 = 0;
                            }
                            double d2 = 0;
                            try {
                                d2 = Double.valueOf(samp.getRecord().getAvg2());
                            } catch (Exception e) {
                                d2 = 0;
                            }
                            double d3 = 0;
                            try {
                                d3 = Double.valueOf(samp.getRecord().getAvg3());
                            } catch (Exception e) {
                                d3 = 0;
                            }
                            try {
                                NumberFormat ddf1 = NumberFormat.getNumberInstance();
                                ddf1.setMaximumFractionDigits(1);
                                it.setValue(ddf1.format((d1 + d2 + d3) / 3));
                            } catch (Exception e) {
                                it.setValue("");
                            }
                        } else if (item.getName().contains("空气比释动能率")) {
                            double d1 = 0;
                            try {
                                d1 = Double.valueOf(samp.getRecord().getV1());
                            } catch (Exception e) {
                                d1 = 0;
                            }
                            double d2 = 0;
                            try {
                                d2 = Double.valueOf(samp.getRecord().getV2());
                            } catch (Exception e) {
                                d2 = 0;
                            }
                            double d3 = 0;
                            try {
                                d3 = Double.valueOf(samp.getRecord().getV3());
                            } catch (Exception e) {
                                d3 = 0;
                            }
                            try {
                                NumberFormat ddf1 = NumberFormat.getNumberInstance();
                                ddf1.setMaximumFractionDigits(1);
                                it.setValue(ddf1.format((d1 + d2 + d3) / 3));
                            } catch (Exception e) {
                                it.setValue("");
                            }
                        }
                    }
                    it.setResult(TaskItem.RESULT_YES);
                    testItemDao.update(it);
                }
            }
        }
    }


    @Override
    public boolean update4Xc(TaskVo v, List<AppXcSb4Pad> timList) throws GlobalException {
        try {

            Task task = taskDao.findById(v.getId());
            if (v.getIsCommit() != null && v.getIsCommit().equals(Constants.PASS_Y)) {
                task.setXcSt(Task.ST_2);
            } else {
                task.setXcSt(Task.ST_1);
            }
            taskDao.update(task);
            int n = 0;
            Set<String> sampNameSet = new HashSet<String>();
            // 更新 项目任务表信息
            TaskItem tim = null;
            for (AppXcSb4Pad timVo : timList) {
                if (timVo.getType() == 1) {
                    tim = taskItemDao.findById(timVo.getIdTim());
                    uptLmt(tim, timVo);
                    // 更新评价标准
                    tim.setStandId(timVo.getStandIdTim());
                    tim.setStandName(timVo.getStandNameTim());
                    // 更新检查方法
                    tim.setMethodId(timVo.getMethodIdTim());
                    tim.setMethodName(timVo.getMethodNameTim());
                    tim.setLimitLine(timVo.getLimitLineTim());
                    // 更新检测仪器
                    tim.setAppId(timVo.getAppIdTim());
                    tim.setAppName(timVo.getAppNameTim());
                    tim.setUnit(timVo.getUnitTim());
                    tim.setSlUnit(tim.getSlUnit());
                    tim.setTestMan(timVo.getTestManTim());
                    tim.setTestManId(timVo.getTestManIdTim());
                    tim.setCheckMan(timVo.getCheckManTim());
                    tim.setCheckManId(timVo.getCheckManIdTim());
                    tim.setTestTime(timVo.getTestTimeTim());
                    tim.setTestEndTime(tim.getTestEndTime());
                    tim.setCheckTime(tim.getTestTime());
                    Account at = accountDao.findById(timVo.getTestManIdTim());
                    Org org = orgDao.findOrg(at.getOrg().getId());
                    tim.setOrgId(org.getId());
                    tim.setOrgName(org.getName());
                    tim.setDeptId(at.getOrg().getId());
                    tim.setDeptName(at.getOrg().getName());
                    tim.setWd(timVo.getWdTim());
                    tim.setSd(timVo.getSdTim());
                    n = 0;
                    sampNameSet.clear();
                } else {
                    // 更新 测试项目信息
                    TestItem it = testItemDao.findById(timVo.getIdItem());
                    it.setValue(it.getValue());
                    it.setSl(it.getSl());
                    it.setTwa(it.getTwa());
                    it.setMac(it.getMac());
                    it.setStel(it.getStel());
                    it.setLmt(it.getLmt());
                    it.setResult(timVo.getResultItem());
                    testItemDao.update(it);
                    TestResult tr = testResultDao.findById(timVo.getTrVoIdItem());
                    tr.setValue(timVo.getTrVoValueItem());
                    testResultDao.update(tr);
                    sampNameSet.add(tr.getSamp().getSampName());
                    n++;

                }
                if (timVo.getIsUpdate()) {
                    tim.setSampNum(n);
                    tim.setSampName(String.join(",", sampNameSet));
                    taskItemDao.update(tim);
                }

            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 检测标准更新，同步限值
    public void uptLmt(TaskItem tim, AppXcSb4Pad v) {
        if (StrUtils.isBlankOrNull(v.getStandIdTim())) {
            tim.setStandId(null);
            tim.setStandName(null);
            tim.setLimited(null);
            tim.setMac(null);
            tim.setStel(null);
            tim.setTwa(null);
            tim.setLmt(null);
        } else if (StrUtils.isBlankOrNull(tim.getStandId()) || !tim.getStandId().equals(v.getStandIdTim())) {
            tim.setStandId(v.getStandIdTim());
            tim.setStandName(v.getStandNameTim());
            if (tim.getTask().getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
                List<PstandItem> plist = pstandItemDao.listStand4Item(tim.getStandId(), tim.getItemId());
                if (null != plist && plist.size() > 0) {
                    PstandItem pit = plist.get(0);
                    tim.setStandId(pit.getStandId());
                    tim.setStandName(pit.getStandName());
                    tim.setLimited(pit.getValStr());
                }
            } else {
                List<PstandItem> plist = pstandItemDao.listStand(tim.getStandId(), tim.getItemId(), null,
                        tim.getItemType(), null);
                if (plist != null && plist.size() > 0) {
                    PstandItem pit = plist.get(0);
                    if (pit.getSampTypeName().contains("化学")) {
                        tim.setLmt(pit.getMaxValue());
                        tim.setMac(pit.getValue3());
                        tim.setTwa(pit.getValue());
                        tim.setStel(pit.getValue2());
                        if (!StrUtils.isBlankOrNull(tim.getMac())) {
                            tim.setLimited("a");
                        } else if (!StrUtils.isBlankOrNull(tim.getTwa()) && !StrUtils.isBlankOrNull(tim.getStel())) {
                            tim.setLimited("b");
                        } else if (!StrUtils.isBlankOrNull(tim.getTwa())) {
                            tim.setLimited("c");
                        }
                    } else {
                        // 物理等其他
                        tim.setLimited(pit.getValStr());
                    }
                }
            }
        }
    }

    /**
     * 文件流转文件上传
     */
    @Override
    public JSONArray uploadFile(MultipartHttpServletRequest multiRequest, Object temppath, String twoFilePath)
            throws GlobalException {
        String UPLOAD_DIR = ApplicationUtils.getValue("config.upload.path").toString().replace("\\", "/");
        JSONArray arr = new JSONArray();
        Iterator<String> iterator = multiRequest.getFileNames();
        while (iterator.hasNext()) {
            // 一次遍历所有文件
            MultipartFile file = multiRequest.getFile(iterator.next().toString());
            if (file != null) {
                String myFileName = file.getOriginalFilename(); // 取得当前上传文件的文件名称
                if (myFileName.trim() != "") { // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    String suffix = myFileName.substring(myFileName.lastIndexOf("."));
                    String fileName = DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay()
                            + new Date().getTime() + suffix;
                    String subPath = UPLOAD_DIR + twoFilePath + File.separator + fileName;
                    subPath = subPath.replace("\\", "/");
                    File targetFile = new File(temppath + UPLOAD_DIR + twoFilePath + File.separator);
                    if (!targetFile.exists()) // 检测目录
                        targetFile.mkdirs();
                    File tempFile = new File(targetFile.getPath() + File.separator + fileName);
                    try {
                        file.transferTo(tempFile);
                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                    }
                    FilesVo fileVo = new FilesVo();
                    fileVo.setBusType(twoFilePath);
                    fileVo.setFileName(myFileName);
                    fileVo.setFilePath(subPath);
                    fileVo.setFileType(suffix);
                    JSONObject obj = new JSONObject();
                    obj.put("filePath", subPath);
                    obj.put("fileName", myFileName);
                    obj.put("type", suffix);
                    arr.add(obj);

                }
            }
        }

        return arr;
    }

    @Override
    public boolean updateTask(AppXcEditTaskVo v, List<AppXcEditCydVo> cydList, ObjVo objVo,
                              List<AppXcEditTpVo> appXcEditTpVoList) throws GlobalException {
        Task p = taskDao.findById(v.getId());
        p.setCyUserId(v.getCyUserId());// 采样填报人
        p.setCyUserName(v.getCyUserName());
        p.setCyTime(v.getCyTime());
        p.setCyMsg(v.getCyMsg());

        // 更新采样单模板
        for (AppXcEditCydVo cydVo : cydList) {
            SampCyd cyd = sampCydDao.findById(cydVo.getIdCyd());
            cyd.setType(cydVo.getTypeCyd());
            cyd.setSort(Integer.parseInt(cydVo.getSortCyd()));
            sampCydDao.update(cyd);
        }
        // 更新现场情况 职卫
        List<AppXcEditTpVo> tpList = appXcEditTpVoList;
        if (null != tpList && !p.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {

        } else if (null != tpList) {
            for (AppXcEditTpVo tpVo : tpList) {
                TaskPoint tp = taskPointDao.findById(tpVo.getIdTp());
                SampType st = sampTypeDao.findById(tpVo.getSampTypeIdTp());
                tp.setSampTypeId(tpVo.getSampTypeIdTp());
                tp.setSampTypeName(tpVo.getSampTypeName());
                if (null != st) {
                    tp.setType(st.getType());
                }
                tp.setSampName(tpVo.getSampNameTp());
                tp.setPointName(tpVo.getPointNameTp());
                tp.setPointCode(tpVo.getPointCode());
                tp.setSort(Integer.parseInt(tpVo.getSort()));
                taskPointDao.update(tp);
            }
        }
        // 更新任务进度及日志
        if (!StrUtils.isNull(v.getIsCommit()) && v.getIsCommit().equals(EunmTask.PASS_Y)) {

            if (null != p.getXcSt() && p.getXcSt().equals("0")) {
                // 有现场项目但未现场上报的，先初始化现场项目，在已办处补录
                checkItem(p.getId(), objVo);
            }

            boolean flag = false;// 是否有实验室项目
            Progress pg = p.getProgress();
            List<Sampling> sampList = samplingDao.listAllByTaskId(p.getId());
            for (Sampling samp : sampList) {
                if (samp.getType().equals(Sampling.SAMP_TYPE_PT)) {
                    flag = true;// 是否有实验室项目
                    break;
                }
            }
            if (!flag) {// 声的直接到数据审核
                pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_HZ.getStatus(), null, null, null, null);
                // initItem4Now(p);//直接到汇总的初始现场项目 到汇总
            } else {// 其他到样品交接
                pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_JJ.getStatus(), null, null, null, null);
                // 到交接的项目 再交接提交时 初始化现场项目 到汇总
            }
            progressLogDao.addApp(p.getId(), p.getId(), EunmTask.TASK_XC.getStatus(), v.getIsCommit(), v.getCyMsg(),
                    objVo);
            p.setStatus(pg.getStatus());
            syncPoint(p);

        }
        try {
            taskDao.update(p);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void checkItem(String taskId, ObjVo objVo) throws GlobalException {
        Task task = taskDao.findById(taskId);
        List<Sampling> sampList = samplingDao.listAllByTaskId(taskId);
        for (Sampling samp : sampList) {
            // 获取项目
            List<Item> itemList = itemDao.listByIds(samp.getItemIds(), Constants.S);
            if (null != itemList) {
                for (Item item : itemList) {
                    // 检查项目任务信息是否存在
                    TaskItem tim = taskItemDao.find(taskId, item.getId());
                    if (tim == null) {
                        tim = saveTim(task, item, samp, objVo);
                    } else {
                        uptTim(tim, samp);
                    }
                    // 检查 项目的样品测试结果信息
                    TestResult tr = testResultDao.findBySampAndItem(samp.getId(), item.getId());
                    if (tr == null) {
                        TestItem it = null;
                        if (task.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
                            it = saveIt(tim, samp.getPoint(), samp, item);
                        } else {
                            // 职卫一个点位一个项目一个采样日期====一个测试信息（n个样品的测试结果）
                            // 检查是否已经有该测试信息
                            it = testItemDao.find(samp.getPoint().getId(), samp.getCyDate(), item.getId());
                            if (it == null) {
                                it = saveIt(tim, samp.getPoint(), samp, item);
                            }
                        }
                        tr = saveTr(it, samp);
                    }
                }
            }
        }
    }

    // 保存测试结果信息
    public TestResult saveTr(TestItem tim, Sampling samp) {
        TestResult tr = new TestResult();
        tr.setSamp(samp);
        tr.setIt(tim);
        String temp = samp.getCyd().getType();
        if (StrUtils.isBlankOrNull(temp)) {
            // 不处理
        }
        // else
        // if(temp.equals("cyd_hx_ht")||temp.equals("cyd_wl_cg")||temp.equals("cyd_wl_gp")
        // ||temp.equals("cyd_wl_wb")||temp.equals("cyd_wl_cp")||temp.equals("cyd_wl_jg"))
        // {
        // //一氧化碳采样单，照度，工频电场，微波，超高频，激光辐射
        // tr.setValue(samp.getV2());
        // }else if(temp.equals("cyd_wl_zf")) {
        // //紫外辐射
        // }
        else if (temp.equals("cyd_s_jt") || temp.equals("cyd_s_gsj") || temp.equals("cyd_s")) {
            // 环境 交通噪声，厂界 、通用模板
            tr.setValue(samp.getRecord().getV1());
        }
        tr.setSort(testResultDao.getMaxSort(tim.getId()) + 1);
        testResultDao.add(tr);
        return tr;
    }

    // 保存测试项目
    public TestItem saveIt(TaskItem tim, TaskPoint point, Sampling samp, Item item) {
        TestItem it = new TestItem();
        it.setTask(tim.getTask());
        it.setTim(tim);
        it.setCust(tim.getTask().getCust());
        it.setPoint(point);
        it.setCyDate(samp.getCyDate());
        it.setItemId(item.getId());
        it.setItemName(item.getName());
        if (item.getParent() != null) {
            it.setLevel(2);
        } else {
            it.setLevel(1);
        }
        if (null == samp.getRecord()) {
        } else if ((item.getName().contains("一氧化碳") || item.getName().contains("二氧化碳"))) {
            it.setStel(samp.getRecord().getAvg1());
            it.setTwa(samp.getRecord().getAvg2());
        } else if ((item.getName().contains("二氧化硅含量") || item.getName().contains("超高频辐射")
                || item.getName().contains("工频电场") || item.getName().contains("激光辐射")
                || item.getName().contains("微波辐射"))) {
            checkAvg(samp, item);
            it.setValue(samp.getRecord().getAvg1());
        } else if (item.getName().contains("高频电磁场")) {
            checkAvg(samp, item);
            it.setValue(samp.getRecord().getAvg1());// 电场
            it.setValue2(samp.getRecord().getAvg2());// 磁场
        } else if (item.getName().contains("紫外辐射")) {
            it.setValue(samp.getRecord().getAvg1());
        } else if ((item.getName().contains("手传振动") || item.getName().contains("噪声"))) {
            it.setValue(samp.getRecord().getAvg1());
            it.setValue2(samp.getRecord().getAvg2());
        } else if (item.getName().contains("照度")) {
            if (null != samp.getCyd().getItemType() && samp.getCyd().getItemType().equals("照度平均值")) {
                it.setValue(samp.getRecord().getAvg1());
            } else {
                it.setValue(samp.getRecord().getAvg2());
            }
        } else if (item.getName().contains("高温")) {
            double d1 = 0;
            try {
                d1 = Double.valueOf(samp.getRecord().getAvg1());
            } catch (Exception e) {
                d1 = 0;
            }
            double d2 = 0;
            try {
                d2 = Double.valueOf(samp.getRecord().getAvg2());
            } catch (Exception e) {
                d2 = 0;
            }
            double d3 = 0;
            try {
                d3 = Double.valueOf(samp.getRecord().getAvg3());
            } catch (Exception e) {
                d3 = 0;
            }
            try {
                NumberFormat ddf1 = NumberFormat.getNumberInstance();
                ddf1.setMaximumFractionDigits(1);
                it.setValue(ddf1.format((d1 + d2 + d3) / 3));
            } catch (Exception e) {
                it.setValue("");
            }
        } else if (item.getName().contains("空气比释动能率")) {
            double d1 = 0;
            try {
                d1 = Double.valueOf(samp.getRecord().getV1());
            } catch (Exception e) {
                d1 = 0;
            }
            double d2 = 0;
            try {
                d2 = Double.valueOf(samp.getRecord().getV2());
            } catch (Exception e) {
                d2 = 0;
            }
            double d3 = 0;
            try {
                d3 = Double.valueOf(samp.getRecord().getV3());
            } catch (Exception e) {
                d3 = 0;
            }
            try {
                NumberFormat ddf1 = NumberFormat.getNumberInstance();
                ddf1.setMaximumFractionDigits(1);
                it.setValue(ddf1.format((d1 + d2 + d3) / 3));
            } catch (Exception e) {
                it.setValue("");
            }
        }
        it.setResult(TaskItem.RESULT_YES);
        it.setType(TaskItem.ITEM_TYPE_XC);
        it.setIsBack(Constants.N);
        it.setSort(testItemDao.getMaxSort(tim.getId()) + 1);
        testItemDao.add(it);
        return it;
    }

    // 检查均值，为空 择自动计算
    public void checkAvg(Sampling samp, Item item) {
        SampRecord record = samp.getRecord();
        if (StrUtils.isBlankOrNull(record.getAvg1())) {
            double v1 = 0;
            if (item.getName().contains("二氧化硅含量") || item.getName().contains("超高频辐射") || item.getName().contains("工频电场")
                    || item.getName().contains("激光辐射") || item.getName().contains("高频电磁场")
                    || item.getName().contains("微波辐射")) {
                try {
                    v1 += Double.valueOf(record.getV4());
                } catch (Exception e) {
                    v1 += 0;
                }
                try {
                    v1 += Double.valueOf(record.getV5());
                } catch (Exception e) {
                    v1 += 0;
                }
                try {
                    v1 += Double.valueOf(record.getV6());
                } catch (Exception e) {
                    v1 += 0;
                }
                String v = String.valueOf(v1 / 3);
                if (v.indexOf(".") > 0) {
                    v = v.substring(0, v.indexOf("."));
                }
                record.setAvg1(v);
            }
        }
        if (StrUtils.isBlankOrNull(record.getAvg2())) {
            double v2 = 0;
            if (item.getName().contains("高频电磁场")) {
                try {
                    v2 += Double.valueOf(record.getV10());
                } catch (Exception e) {
                    v2 += 0;
                }
                try {
                    v2 += Double.valueOf(record.getV11());
                } catch (Exception e) {
                    v2 += 0;
                }
                try {
                    v2 += Double.valueOf(record.getV12());
                } catch (Exception e) {
                    v2 += 0;
                }
                String v = String.valueOf(v2 / 3);
                if (v.indexOf(".") > 0) {
                    v = v.substring(0, v.indexOf("."));
                }
                record.setAvg2(v);
            }
        }
        sampRecordDao.update(record);
    }

    // 更新项目任务信息
    public void uptTim(TaskItem tim, Sampling samp) {
        String st = null;
        if (!StrUtils.isBlankOrNull(samp.getCyTime())) {
            st = samp.getCyDate() + " " + samp.getCyTime();
        }
        String et = null;
        if (!StrUtils.isBlankOrNull(samp.getCyEndTime())) {
            et = samp.getCyDate() + " " + samp.getCyEndTime();
        } else {
            et = st;
        }
        String ct = tim.getTestTime();
        if (!StrUtils.isBlankOrNull(st) && !StrUtils.isBlankOrNull(ct)) {
            long l = DateUtils.getIntevalMinutes(st, ct);
            if (l > 0) {
                tim.setTestTime(st);
            }
        }
        String cet = tim.getTestEndTime();
        if (!StrUtils.isBlankOrNull(et) && !StrUtils.isBlankOrNull(cet)) {
            long l = DateUtils.getIntevalMinutes(et, cet);
            if (l < 0) {
                tim.setTestEndTime(et);
            }
        }
        taskItemDao.update(tim);
    }

    // 保存项目任务信息
    public TaskItem saveTim(Task task, Item item, Sampling samp, ObjVo objVo) {
        SampType st = sampTypeDao.findById(item.getSampTypeIds());
        TaskItem tim = new TaskItem();
        tim.setTask(task);
        tim.setItemId(item.getId());
        tim.setItemName(item.getName());
        tim.setUnit(item.getUnit());
        tim.setPrice(item.getPrice());
        tim.setType(TaskItem.ITEM_TYPE_XC);
        tim.setSampTypeId(item.getSampTypeIds());
        tim.setSampTypeName(item.getSampTypeNames());
        tim.setSt(st.getType());
        tim.setWd(samp.getCyd().getQw());
        tim.setSd(samp.getCyd().getSd());
        if (tim.getItemName().contains("粉尘")) {
            tim.setItemType(samp.getFcType());
        } else if (samp.getCyd().getType().equals("cyd_wl_jg") || samp.getCyd().getType().equals("cyd_wl_cp")
                || samp.getCyd().getType().equals("cyd_wl_cg")) {
            tim.setItemType(samp.getCyd().getItemType());
        }
        String sampTypeIds = sampTypeDao.findAllIds(tim.getSampTypeId());
        if (task.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
            List<PstandItem> plist = pstandItemDao.listStand4Item(task.getStandIds(), item.getId());
            if (null != plist && plist.size() > 0) {
                PstandItem pit = plist.get(0);
                tim.setStandId(pit.getStandId());
                tim.setStandName(pit.getStandName());
                tim.setLimited(pit.getValStr());
            }
        } else {
            List<Pstandard> standList = pstandardDao.listBySampTyle(sampTypeIds);
            if (null != standList && standList.size() > 0) {
                Pstandard stand = standList.get(0);
                tim.setStandId(stand.getId());
                tim.setStandName(stand.getName());
                List<PstandItem> plist = pstandItemDao.listStand(stand.getId(), item.getId(), null, tim.getItemType(),
                        null);
                if (plist != null && plist.size() > 0) {
                    PstandItem pit = plist.get(0);
                    // 化学有害因素标准特有
                    if (pit.getSampTypeName().contains("化学")) {
                        tim.setLmt(pit.getMaxValue());
                        tim.setMac(pit.getValue3());
                        tim.setTwa(pit.getValue());
                        tim.setStel(pit.getValue2());
                        if (!StrUtils.isBlankOrNull(tim.getMac())) {
                            tim.setLimited("a");
                        } else if (!StrUtils.isBlankOrNull(tim.getTwa()) && !StrUtils.isBlankOrNull(tim.getStel())) {
                            tim.setLimited("b");
                        } else if (!StrUtils.isBlankOrNull(tim.getTwa())) {
                            tim.setLimited("c");
                        }
                    } else {
                        // 物理等其他
                        tim.setLimited(pit.getValStr());
                    }
                }
            }
        }
        Im im = imDao.findByBusIdAndItemId(samp.getPoint().getId(), item.getId());
        if (null != im) {
            tim.setMethodId(im.getMethodId());
            tim.setMethodName(im.getMethodName());
            Method m = methodDao.findById(im.getMethodId());
            tim.setLimitLine(m.getLimitLine());
        }
        tim.setAppId(samp.getCyd().getCyAppId());
        tim.setAppName(samp.getCyd().getCyAppName());
        tim.setTestMan(objVo.getName());
        tim.setTestManId(objVo.getUserId());
        tim.setCheckMan(task.getFzName());
        tim.setCheckManId(task.getFzId());
        if (!StrUtils.isBlankOrNull(samp.getCyTime())) {
            tim.setTestTime(samp.getCyDate() + " " + samp.getCyTime());
        } else {
            tim.setTestTime(samp.getCyDate());
        }
        if (!StrUtils.isBlankOrNull(samp.getCyEndTime())) {
            tim.setTestEndTime(samp.getCyDate() + " " + samp.getCyEndTime());
        } else {
            tim.setTestEndTime(tim.getTestTime());
        }
        tim.setIsBack(Constants.N);
        tim.setSort(taskItemDao.getMaxSort(task.getId()) + 1);
        taskItemDao.add(tim);
        return tim;
    }

    public void syncPoint(Task task) {
        if (!StrUtils.isBlankOrNull(task.getSchemeId())) {
            // 同步更新方案信息
            Scheme scheme = schemeDao.findById(task.getSchemeId());
            scheme.setCyDate(task.getCyDate());
            scheme.setCyEndDate(task.getCyEndDate());
            scheme.setCyUserName(task.getCyName());
            scheme.setSampTypeId(task.getSampTypeId());
            scheme.setSampTypeName(task.getSampTypeName());
            scheme.setSampName(task.getSampName());
            scheme.setSampNum(task.getSampNum());
            scheme.setItemNames(task.getItemNames());
            scheme.setPointNames(task.getPointNames());
            schemeDao.update(scheme);
            List<TaskPoint> tpList = taskPointDao.listByTaskId(task.getId());
            if (null != tpList && tpList.size() > 0) {
                // 同步更新方案点位信息
                schemePointDao.deleteBySchId(scheme.getId());
                for (TaskPoint tp : tpList) {
                    SchemePoint sp = new SchemePoint();
                    sp.setScheme(scheme);
                    sp.setProjectId(scheme.getProject().getId());
                    sp.setSampTypeId(tp.getSampTypeId());
                    sp.setSampTypeName(tp.getSampTypeName());
                    sp.setSampName(tp.getSampName());
                    sp.setPc(tp.getPc());
                    sp.setPcUnit(tp.getPcUnit());
                    sp.setPointName(tp.getPointName());
                    sp.setPointCode(tp.getPointCode());
                    sp.setPointType(tp.getType());
                    sp.setItemId(tp.getItemIds());
                    sp.setItemName(tp.getItemNames());
                    sp.setRoom(tp.getRoom());
                    sp.setCyHours(tp.getCyHours());
                    sp.setCyType(tp.getCyType());
                    schemePointDao.add(sp);
                    // 同步更新踏勘相关表信息
                    if (!StrUtils.isBlankOrNull(tp.getRoom())) {
                        // 车间
                        CustRoom room = custRoomDao.findByName(scheme.getProject().getId(), tp.getRoom());
                        if (room == null) {
                            room = new CustRoom();
                            room.setProjectId(scheme.getProject().getId());
                            room.setCust(task.getCust());
                            room.setName(tp.getRoom());
                            custRoomDao.add(room);
                        }
                        // 同步监测点
                        CustPoint cp = custPointDao.findByName(room.getId(), tp.getPointName());
                        if (null == cp) {
                            cp = new CustPoint();
                            cp.setProjectId(scheme.getProject().getId());
                            cp.setRoom(room);
                            cp.setCust(task.getCust());
                            cp.setName(tp.getPointName());
                            cp.setSampTypeId(tp.getSampTypeId());
                            cp.setSampTypeName(tp.getSampTypeName());
                            try {
                                cp.setWorkTal(Integer.valueOf(tp.getWorkNum()));
                            } catch (Exception e) {
                                cp.setWorkTal(0);
                            }
                            cp.setItemIds(tp.getItemIds());
                            cp.setItemNames(tp.getItemNames());
                            cp.setWorkHours(tp.getWorkHours());
                            cp.setFhName(tp.getFh());
                            cp.setOthers(tp.getOthers());
                            custPointDao.add(cp);
                        }
                    }
                }
            }
        }
    }

    // 根据点位生成采样日期
    public void initSamp4Point(TaskPoint tp, String cyDate, String cyEndDate) throws GlobalException {
        samplingDao.deleteByPoint(tp.getId());
        if (tp.getPcUnit().equals(PcUnit.CT.getName())) {// 次/每天 生成样品信息
            int n = (int) (DateUtils.getIntevalDays(cyDate, cyEndDate) + 1);
            for (int i = 0; i < n; i++) {
                String date = DateUtils.getNextDate(cyDate, i);
                if (tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
                    initSamp4Hj(tp, date);
                }
            }
        } else {// 按次 生成样品信息
            if (tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
                initSamp4Hj(tp, cyDate);
            }
        }
        String sql = "select count(id) from " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N
                + "' AND zkType='" + Sampling.SAMP_TYPE_PT + "' AND point.id = '" + tp.getId() + "' ";
        Object num = samplingDao.query(sql).getSingleResult();
        tp.setSampNum(Integer.valueOf(num.toString()));
    }

    // 更新 已重置的样品里的采样单信息
    public void uptSamp(TaskPoint tp) {
        // 更新点位的样品采样单 属性
        String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id ='"
                + tp.getId() + "' ORDER BY sort,cyDate,p ASC";
        List<Sampling> samplist = samplingDao.list(hql);
        for (Sampling samp : samplist) {
            EnumCyd cyd = null;
            if (samp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
                cyd = EnumCyd.getCyd4Hj(samp.getSampTypeName(), samp.getItemNames());
            } else {
                cyd = EnumCyd.getCyd4Zw(samp.getItemNames());
            }
            String type = null;
            if (cyd != null) {
                type = cyd.getCode();
            }
            SampCyd sampCyd = sampCydDao.find4Auto(samp.getPoint().getId(), type, samp.getCyDate());
            //SampCyd sampCyd = sampCydDao.find4Task(tp.getTask().getId(), type, samp.getCyDate());
            if (sampCyd != null) {
                samp.setCyd(sampCyd);
            } else {
                sampCyd = new SampCyd();
                sampCyd.setTask(tp.getTask());
                sampCyd.setPointId(samp.getPoint().getId());
                sampCyd.setItemIds(samp.getItemIds());
                sampCyd.setItemNames(samp.getItemNames());
                sampCyd.setType(type);
                sampCyd.setCyDate(samp.getCyDate());
                sampCyd.setSampType(samp.getSampType());
                sampCyd.setSampName(samp.getSampName());
                sampCyd.setCySt(String.valueOf(Po.N));
                sampCyd.setSort(sampCydDao.getMaxSort(tp.getTask().getId()) + 1);
                sampCydDao.add(sampCyd);
                samp.setCyd(sampCyd);
            }
            samplingDao.update(samp);
        }
    }

    // 生成点位采样日的样品信息
    @SuppressWarnings("unchecked")
    public void initSamp4Hj(TaskPoint tp, String cyDate) {
        int n = 1;
        // 现场项目 每个占一个样品（采样单）
        String sql = "select id,name from " + tablePrefix + itemDao.getEntityName(Item.class) + " WHERE is_del='" + Po.N
                + "' AND id in('" + tp.getItemIds().replace(",", "','") + "') AND is_now='" + Constants.S
                + "' order by sort ASC";
        List<Object[]> object = itemDao.queryBySql(sql);
        if (null != object && object.size() > 0) {
            for (Object[] obj : object) {
                for (int i = 1; i <= tp.getPc(); i++) {
                    Sampling samp = new Sampling();
                    samp.setTask(tp.getTask());
                    samp.setCust(tp.getTask().getCust());
                    samp.setPoint(tp);
                    samp.setSampType(tp.getSampType());
                    samp.setPointName(tp.getPointName());
                    samp.setPointCode(tp.getPointCode());
                    samp.setSampTypeId(tp.getSampTypeId());
                    samp.setSampTypeName(tp.getSampTypeName());
                    samp.setSampName(tp.getSampName());
                    if (StrUtils.isBlankOrNull(tp.getSampName())) {
                        samp.setSampName(tp.getTask().getSampName());
                    }
                    samp.setCyDate(cyDate);
                    samp.setItemIds(obj[0].toString());
                    samp.setItemNames(obj[1].toString());
                    samp.setLy(Constants.F);
                    samp.setP(String.valueOf(i));// 批次
                    samp.setType(Sampling.SAMP_TYPE_XN);// 现场监测项目 采用虚拟样 样品交接不展示
                    samp.setZkType(Sampling.SAMP_TYPE_PT);
                    samp.setSort(n);
                    SampRecord record = new SampRecord();
                    sampRecordDao.add(record);
                    samp.setRecord(record);
                    samplingDao.add(samp);
                }
                n++;
            }
        }
        // 实验室项目 按样品编号归类
        sql = "select code,group_concat(id),group_concat(name) from " + tablePrefix + itemDao.getEntityName(Item.class)
                + " WHERE is_del='" + Po.N + "' AND id in('" + tp.getItemIds().replace(",", "','") + "') AND is_now='"
                + Constants.F + "' group by code order by sort ASC";
        object = itemDao.queryBySql(sql);
        if (null != object && object.size() > 0) {
            for (Object[] obj : object) {
                for (int i = 1; i <= tp.getPc(); i++) {
                    Sampling samp = new Sampling();
                    samp.setTask(tp.getTask());
                    samp.setCust(tp.getTask().getCust());
                    samp.setPoint(tp);
                    samp.setSampType(tp.getSampType());
                    samp.setPointName(tp.getPointName());
                    samp.setPointCode(tp.getPointCode());
                    samp.setSampTypeId(tp.getSampTypeId());
                    samp.setSampTypeName(tp.getSampTypeName());
                    samp.setSampName(tp.getSampName());
                    if (StrUtils.isBlankOrNull(tp.getSampName())) {
                        samp.setSampName(tp.getTask().getSampName());
                    }
                    samp.setCyDate(cyDate);
                    samp.setItemIds(obj[1].toString());
                    samp.setItemNames(obj[2].toString());
                    samp.setLy(Constants.F);
                    samp.setSort(n);
                    samp.setP(String.valueOf(i));
                    if (null != obj[0] && !String.valueOf(obj[0]).equals("null")) {
                        samp.setCode(String.valueOf(obj[0]));
                    }
                    samp.setType(Sampling.SAMP_TYPE_PT);
                    samp.setZkType(Sampling.SAMP_TYPE_PT);
                    SampRecord record = new SampRecord();
                    sampRecordDao.add(record);
                    samp.setRecord(record);
                    samplingDao.add(samp);
                }
                n++;
            }
        }
    }

    @Override
    public List<AppTaskXcVo> ListEd(ObjVo vo, String no, String custName, String sampName, String taskType,
                                    String cyName) throws GlobalException {
        StringBuffer hql = new StringBuffer(
                "SELECT task.id,task.`no`,cust.cust_name,task.samp_name,task.task_type,task.cy_name,task.cy_date,task.cy_end_date, task.cy_id ,task.samp_type FROM v_bus_task task");
        hql.append(" JOIN v_bus_cust cust on task.cust_id = cust.id");
        hql.append(" JOIN v_bus_progress_log log on task.id = log.bus_id");
        hql.append(" where log.`status`='" + EunmTask.TASK_XC.getStatus() + "'  and  log.user_id like '%"
                + vo.getUserId() + "%'");
        if (no != null && !("").equals(no)) {
            hql.append("and task.no like '%" + no + "%'");
        }
        if (custName != null && !("").equals(custName)) {
            hql.append("and cust.cust_name like '%" + custName + "%'");
        }
        if (sampName != null && !("").equals(sampName)) {
            hql.append("and task.samp_name like '%" + sampName + "%'");
        }
        if (taskType != null && !("").equals(taskType)) {
            hql.append("and task.task_type like '%" + taskType + "%'");
        }
        if (cyName != null && !("").equals(cyName)) {
            hql.append("and task.cy_name like '%" + cyName + "%'");
        }
        hql.append("and task.is_del = 0  GROUP BY task.id   ORDER BY task.last_upd_time DESC ");
        Query query = taskDao.queryBysql(hql.toString());
        int page = vo.getPage();
        if (page < 1) {
            page = 1;
        }
        List list = query.setFirstResult((page - 1) * vo.getRows()).setMaxResults(vo.getRows()).getResultList();
        List<AppTaskXcVo> outList = new ArrayList<>();
        if (null != list) {
            for (Object o : list) {
                Object[] objects = (Object[]) o;
                AppTaskXcVo appTaskXcVo = new AppTaskXcVo();
                appTaskXcVo.setId(objects[0] == null ? "" : objects[0].toString());
                appTaskXcVo.setNo(objects[1] == null ? "" : objects[1].toString());
                appTaskXcVo.setCustName(objects[2] == null ? "" : objects[2].toString());
                appTaskXcVo.setSampName(objects[3] == null ? "" : objects[3].toString());
                appTaskXcVo.setTaskType(objects[4] == null ? "" : objects[4].toString());
                appTaskXcVo.setCyName(objects[5] == null ? "" : objects[5].toString());
                appTaskXcVo.setCyDate(objects[6] == null ? "" : objects[6].toString());
                appTaskXcVo.setCyEndDate(objects[7] == null ? "" : objects[7].toString());
                appTaskXcVo.setSampType(objects[9] == null ? "" : objects[9].toString());
                /* appTaskXcVo.setCyId(objects[8] == null ? "" : objects[8].toString()); */
                /*
                 * appTaskXcVo.setItemIds(taskZbService.findById(appTaskXcVo.getId()).getItemIds
                 * ());
                 */
                outList.add(appTaskXcVo);
            }
        }
        return outList;
    }

    @Override
    public List<AppMethod> findMethodList(String itemId) throws GlobalException {
        Item item = itemDao.findById(itemId);
        if (item.getParent() != null) {
            itemId = item.getParent().getId();
        }
        String jpql = "FROM " + itemMethodDao.getEntityName(ItemMethod.class) + " WHERE isDel='" + Po.N
                + "' AND item.id='" + itemId + "'";
        jpql += " group by method.id";
        List<ItemMethod> imList = itemMethodDao.list(jpql);
        List<AppMethod> methodList = new ArrayList<>();
        if (null != imList) {
            for (ItemMethod im : imList) {
                AppMethod vo = new AppMethod();
                vo.setId(im.getMethod().getId());
                vo.setChapter(im.getMethod().getChapter());
                vo.setCode(im.getMethod().getCode());
                vo.setLimitLine(im.getMethod().getMinLine());
                vo.setName(im.getMethod().getName());
                methodList.add(vo);
            }
        }
        return methodList;

    }

    @Override
    public List<AppAppara> showApp(String id) throws GlobalException {
        List<MethodVo> mList = methodService.listByIds(id);
        Set<String> appSet = new HashSet<String>();
        if (null != mList) {
            for (MethodVo m : mList) {
                appSet.addAll(Arrays.asList(m.getAppIds().split(",")));
            }
        }
        List<AppAppara> outList = new ArrayList<>();
        List<ApparaVo> appList = apparaService.listByIds(String.join(",", appSet));
        for (ApparaVo apparaVo : appList) {
            AppAppara vo = new AppAppara();
            vo.setId(apparaVo.getId());
            vo.setNo(apparaVo.getNo());
            vo.setName(apparaVo.getName());
            vo.setSpec(apparaVo.getSpec());
            outList.add(vo);
        }
        return outList;
    }

    @Override
    public boolean updateBack(TaskVo v, ObjVo vo) throws GlobalException {
        Task p = taskDao.findById(v.getId());
        p.setCyUserId(v.getCyUserId());// 采样填报人
        p.setCyUserName(v.getCyUserName());
        p.setCyTime(v.getCyTime());
        p.setCyMsg(v.getCyMsg());
        // 更新任务进度及日志
        if (!StrUtils.isNull(v.getIsCommit()) && v.getIsCommit().equals(EunmTask.PASS_N)) {
            samplingDao.queryBysql("update v_bus_sampling set cyd_id=null where task_id ='" + p.getId() + "'")
                    .executeUpdate();
            sampCydDao.deleteByTask(p.getId());
            Progress pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_AP.getStatus(), null, null, null,
                    null);

            progressLogDao.addApp(p.getId(), p.getId(), EunmTask.TASK_XC.getStatus(), EunmTask.PASS_N, v.getCyMsg(),
                    vo);
            p.setStatus(pg.getStatus());
            p.setIsBack(Constants.Y);
        }
        try {
            taskDao.update(p);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean updateTp(String id, String latAndLng) throws GlobalException {
        TaskPoint tp = taskPointDao.findById(id);
        tp.setLatAndLng(latAndLng);
        try {
            taskPointDao.update(tp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteFile(String id) throws GlobalException {
        try {
            filesDao.delete(filesDao.findById(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String uploadPhoto(MultipartHttpServletRequest multiRequest, Object temppath, String twoFilePath)
            throws GlobalException {
        String UPLOAD_DIR = ApplicationUtils.getValue("config.upload.path").toString().replace("\\", "/");
        String filePath = "";
        Iterator<String> iterator = multiRequest.getFileNames();

        while (iterator.hasNext()) {
            // 一次遍历所有文件
            MultipartFile file = multiRequest.getFile(iterator.next().toString());
            if (file != null) {
                String myFileName = file.getOriginalFilename(); // 取得当前上传文件的文件名称
                if (myFileName.trim() != "") { // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    String suffix = myFileName.substring(myFileName.lastIndexOf("."));
                    String fileName = DateUtils.getYear() + DateUtils.getMonth() + DateUtils.getDay()
                            + new Date().getTime() + suffix;
                    String subPath = UPLOAD_DIR + twoFilePath + File.separator + fileName;
                    subPath = subPath.replace("\\", "/");
                    File targetFile = new File(temppath + UPLOAD_DIR + twoFilePath + File.separator);
                    if (!targetFile.exists()) // 检测目录
                        targetFile.mkdirs();
                    File tempFile = new File(targetFile.getPath() + File.separator + fileName);
                    try {
                        file.transferTo(tempFile);
                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                    }
                    FilesVo fileVo = new FilesVo();
                    fileVo.setBusType(twoFilePath);
                    fileVo.setFileName(myFileName);
                    fileVo.setFilePath(subPath);
                    fileVo.setFileType(suffix);
                    filePath = subPath;

                }
            }
        }
        return filePath;
    }

    @Override
    public boolean addPoint(String id) throws GlobalException {
        Task p = taskDao.findById(id);
        TaskPoint tp = new TaskPoint();
        tp.setTask(p);
        tp.setSampType(p.getSampType());
        tp.setSampName(p.getSampName());
        if (tp.getPc() == 0) {// 新增点位
            if (p.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
                tp.setPc(1);
                tp.setPcUnit(PcUnit.C.getName());
            } else {// 职业卫生默认采样3次
                tp.setPc(3);
                tp.setPcUnit(PcUnit.CT.getName());
            }
        }
        if (!p.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
            tp.setCyHours(15);
            tp.setCyType(Constants.CY_DD);
        }
        tp.setSort(taskPointDao.getMaxSort(p.getId()) + 1);
        try {
            taskPointDao.add(tp);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean deletePoint(String pointIds) throws GlobalException {
        try {
            List<TaskPoint> tpList = taskPointDao.listByIds(pointIds.replaceAll(" ", ""));
            if (null != tpList) {
                for (TaskPoint tp : tpList) {
                    List<Sampling> sampList = samplingDao.listAllByPointId(tp.getId());
                    if (null != sampList) {
                        for (Sampling samp : sampList) {
                            SampRecord record = samp.getRecord();
                            samplingDao.delete(samp);
                            sampRecordDao.delete(record);
                        }
                    }
                    taskPointDao.delete(tp);
                }
                uptCydNum(tpList.get(0).getTask());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 检查采样单的样品数量 ，若为空 删除采样单
    public void uptCydNum(Task task) {
        // 更新采样单
        List<SampCyd> cydList = sampCydDao.listByTaskId(task.getId());
        for (SampCyd cyd : cydList) {
            List<Sampling> sampList = samplingDao.listByCyd(cyd.getId());
            if (sampList != null && sampList.size() > 0) {
                cyd.setSampNum(sampList.size());
                sampCydDao.update(cyd);
            } else {
                // 采样单无样品，删除采样单

                sampCydDao.delete(cyd);
            }
        }
    }

    @Override
    public boolean uptPoint(AppXcEditTpVo v, ObjVo objVo) throws GlobalException {
        try {

            TaskPoint tp = taskPointDao.findById(v.getIdTp());
            if (StrUtils.isBlankOrNull(tp.getSampTypeId())) {
                List<Item> itemList = itemDao.listByIds(v.getItemIds());
                List<String> sampTypeIdList = new ArrayList<>();
                List<String> sampTypeList = new ArrayList<>();
                for (Item item : itemList) {
                    if (!sampTypeIdList.contains(item.getSampTypeIds())) {
                        sampTypeIdList.add(item.getSampTypeIds());
                        sampTypeList.add(item.getSampTypeNames());
                    }
                }
                tp.setSampTypeId(String.join(",", sampTypeIdList));
                tp.setSampTypeName(String.join(",", sampTypeList));
            }
            tp.setItemIds(v.getItemIds());
            tp.setItemNames(v.getItemNamesTp());
            taskPointDao.update(tp);
            imDao.uptIm4App(tp.getId(), v.getImId(), objVo);
            // 检测是否有现场项目 更新现场数据上报状态
            List<Item> itList = itemDao.listByIds(v.getItemIds(), Constants.S);
            if (itList != null && itList.size() > 0) {
                Task task = tp.getTask();
                task.setXcSt(Task.ST_0);
                taskDao.update(task);
            } // 删除历史样品
            List<Sampling> sampList = samplingDao.listAllByPointId(tp.getId());
            if (sampList != null) {
                for (Sampling samp : sampList) {
                    SampRecord record = samp.getRecord();
                    samp.setRecord(null);
                    samplingDao.delete(samp);
                    if (record != null) {
                        sampRecordDao.delete(record);
                    }
                }
            } // 重新生成项目信息
            initSamp4Point(tp, tp.getTask().getCyDate(), tp.getTask().getCyEndDate()); // 更新 已重置的样品里的采样单信息
            uptSamp(tp); // 更新采样单
            List<SampCyd> SampCydList = sampCydDao.listByTaskId(tp.getTask().getId());
            for (SampCyd cyd : SampCydList) {
                List<Sampling> thissampList = samplingDao.listByCyd(cyd.getId());
                if (thissampList != null && thissampList.size() > 0) {
                    cyd.setSampNum(thissampList.size());
                    uptCyd(cyd);
                } else {
                    // 采样单无样品，删除采样单
                    sampCydDao.delete(cyd);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<AppTaskApItemSelect> taskApItemSelectList(String ids) throws GlobalException {
        StringBuffer hql = new StringBuffer(
                "FROM " + itemDao.getEntityName(Item.class) + " WHERE isDel=" + Po.N);
        String[] split = ids.replaceAll(" ", "").split(",");
        String temp = "";
        for (String string : split) {
            temp += "'" + string + "',";
        }
        hql.append(" AND id in(" + temp.substring(0, temp.length() - 1) + ")");
//		hql.append(" AND id in('2c90a6ee66624e0b0166676704fd02e9','2c90a6ee66624e0b0166676f56f20341','2c90a6ee66624e0b0166677739e10398')");
        hql.append(" AND childNames is null or childNames=''");
        hql.append(" ORDER BY sort asc");
        List<Item> itemList = itemDao.list(hql.toString());
        List<AppTaskApItemSelect> outList = new ArrayList<>();
        for (Item po : itemList) {
            AppTaskApItemSelect vo = new AppTaskApItemSelect();
            vo.setId(po.getId());
            vo.setName(po.getName());
            vo.setUnit(po.getUnit());
            vo.setIsNow(po.getIsNow());
            vo.setStandNames(po.getStandNames());
            outList.add(vo);
        }
        return outList;
    }

    @Override
    public List<AppPstandard> pstandardList(String sampTypeId, String name) throws GlobalException {
        String hql = "FROM " + pstandardDao.getEntityName(Pstandard.class) + " WHERE isDel=" + Po.N;
        hql += " and status = '现行' ";
        if (StrUtils.isNotBlankOrNull(name)) {
            hql += " and (code like '%" + name + "%' or name like '%" + name + "%') ";
        }
        hql += " and  ";
        List<QueryCondition> returnList = new ArrayList<QueryCondition>();
        if (StrUtils.isNotBlankOrNull(sampTypeId)) {
            Set<String> idsSet = new HashSet<String>();
            String idArr[] = sampTypeId.replaceAll(" ", "").split(",");
            for (String idStr : idArr) {
                String ids = sampTypeDao.findAllIds(idStr);
                idsSet.addAll(Arrays.asList(ids.split(",")));
            }
            for (String id : idsSet) {
                if (id.trim().length() > 0) {
                    hql += " sampTypeId like '%" + id + "%' or";
                }
            }
            if (hql.length() > 2) {
                hql = hql.substring(0, hql.length() - 2);
                returnList.add(new QueryCondition(hql));
            }
        }
        hql += " ORDER BY lastUpdTime desc";
        Query query = pstandardDao.query(hql.toString());
        List<Pstandard> list = query.getResultList();
        List<AppPstandard> outList = new ArrayList<>();
        for (Pstandard po : list) {
            AppPstandard vo = new AppPstandard();
            vo.setId(po.getId());
            vo.setCode(po.getCode());
            vo.setSampTypeName(po.getSampTypeName());
            vo.setName(po.getName());
            outList.add(vo);
        }
        return outList;
    }

    @Override
    public List<AppPstandItemVo> pstandItemList(String itemId, String standId) throws GlobalException {
        String hql = "FROM " + pstandItemDao.getEntityName(PstandItem.class) + " WHERE isDel=" + Po.N;
        hql += " and item_id  = '" + itemId + "' ";
        hql += " and stand_id  = '" + standId + "' ";
        hql += " ORDER BY sort asc";
        Query query = pstandardDao.query(hql.toString());
        List<PstandItem> list = query.getResultList();
        List<AppPstandItemVo> outList = new ArrayList<>();
        for (PstandItem po : list) {
            AppPstandItemVo vo = new AppPstandItemVo();
            vo.setId(po.getId());
            vo.setCode(po.getCode());
            vo.setItemVoName(po.getItem().getName());
            vo.setItemType(po.getItemType());
            vo.setType(po.getType());
            vo.setOtherType(po.getOtherType());
            vo.setValue(po.getValStr());
            outList.add(vo);
        }
        return outList;
    }
}
