package cn.demi.app.appCyd.service;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.appCyd.vo.*;
import cn.demi.app.bus.vo.AppPstandard;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.task.vo.TaskVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface AppTaskXcService {

    /**
     * 获取现场采样未办办列表
     *
     * @param vo
     * @param no
     * @param custName
     * @param sampName
     * @param taskType
     * @param cyName
     * @return
     * @throws GlobalException
     */
    public List<AppTaskXcVo> listToCyd(ObjVo vo, String no, String custName, String sampName, String taskType,
                                       String cyName) throws GlobalException;

    /**
     * 现场采样已办列表
     *
     * @param vo
     * @param no
     * @param custName
     * @param sampName
     * @param taskType
     * @param cyName
     * @return
     * @throws GlobalException
     */
    public List<AppTaskXcVo> ListEd(ObjVo vo, String no, String custName, String sampName, String taskType,
                                    String cyName) throws GlobalException;

    /**
     * 获取任务样品信息
     *
     * @param taskId
     * @return
     * @throws GlobalException
     */
    public List<AppTmVo> listByTaskId(ObjVo vo, String taskId) throws GlobalException;

    /**
     * 获取项目关系方法集合
     *
     * @param vo
     * @param itemIds 项目Ids
     * @return
     * @throws GlobalException
     */
    public List<AppXcCyGfVo> listByItemIds(ObjVo vo, String itemIds) throws GlobalException;

    /**
     * 采样单详情页面
     *
     * @param vo
     * @param taskId
     * @return
     * @throws GlobalException
     */
    public JSONObject edit(ObjVo vo, String taskId) throws GlobalException;

    /**
     * 合并采样单
     *
     * @param ids
     * @return
     * @throws GlobalException
     */
    public boolean update4Hb(String ids) throws GlobalException;

    /**
     * 拆分采样单
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    public boolean update4Cf(String id) throws GlobalException;

    /**
     * 重置采样单
     *
     * @param taskId
     * @return
     * @throws GlobalException
     */
    public boolean initAllCyd(String taskId) throws GlobalException;

    /**
     * 现场分析记录单
     *
     * @param taskId
     * @param cyId
     * @return
     * @throws GlobalException
     */
    public JSONObject edit4Item(String taskId, String cyId, ObjVo objVo) throws GlobalException;

    /**
     * 采样单 现场采样
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    public JSONObject editCyd(String id) throws GlobalException;

    /**
     * 监测方法列表
     *
     * @param code
     * @param name
     * @return
     * @throws GlobalException
     */
    public List<AppSampSourceVo> sampSourceGridData(String code, String name) throws GlobalException;

    /**
     * 空白样 平行样 选择列表
     *
     * @param itemIds
     * @return
     * @throws GlobalException
     */
    public List<AppSampItem> sampitemSelect(String itemIds) throws GlobalException;

    /**
     * 更新采样单
     *
     * @param v
     * @param sampList
     * @param zkList
     * @throws GlobalException
     */
    public boolean updateCyd(AppCydVo v, List<AppCydSamp> sampList) throws GlobalException;

    /**
     * 更新现场项目结果
     *
     * @param v 采样单
     * @throws GlobalException
     */
    public boolean update4Xc(TaskVo v, List<AppXcSb4Pad> timList) throws GlobalException;

    /**
     * 文件上传
     *
     * @param multiRequest
     * @param temppath
     * @param twoFilePath
     * @return
     * @throws GlobalException
     */
    public JSONArray uploadFile(MultipartHttpServletRequest multiRequest, Object temppath, String twoFilePath)
            throws GlobalException;

    /**
     * 照片上传
     *
     * @param multiRequest
     * @param temppath
     * @param twoFilePath
     * @return
     * @throws GlobalException
     */
    public String uploadPhoto(MultipartHttpServletRequest multiRequest, Object temppath, String twoFilePath)
            throws GlobalException;

    /**
     * 更新现场采样 页面
     *
     * @param v
     * @param cydList
     * @param objVo
     * @return
     */
    public boolean updateTask(AppXcEditTaskVo v, List<AppXcEditCydVo> cydList, ObjVo objVo,
                              List<AppXcEditTpVo> appXcEditTpVoList) throws GlobalException;

    /**
     * 现场分析记录单 检测方法
     *
     * @param itemId
     * @return
     * @throws GlobalException
     */
    public List<AppMethod> findMethodList(String itemId) throws GlobalException;

    /**
     * 现场分析记录单 检测仪器
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    public List<AppAppara> showApp(String id) throws GlobalException;

    /**
     * 退回到采样安排
     *
     * @param v
     * @param vo
     * @throws GlobalException
     */
    boolean updateBack(TaskVo v, ObjVo vo) throws GlobalException;

    /**
     * 更新 点位 经纬度
     *
     * @param id
     * @param latAndLng
     * @return
     * @throws GlobalException
     */
    boolean updateTp(String id, String latAndLng) throws GlobalException;

    /**
     * 根据id 删除文件
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    boolean deleteFile(String id) throws GlobalException;

    /**
     * 新增点位
     *
     * @param id
     * @throws GlobalException
     */
    boolean addPoint(String id) throws GlobalException;

    /**
     * 删除点位
     *
     * @param pointIds
     * @throws GlobalException
     */
    boolean deletePoint(String pointIds) throws GlobalException;

    /**
     * 录入现场项目结果前 检查现场项目信息 若缺失自动补充
     *
     * @param taskId 任务Id
     * @return
     * @throws GlobalException
     */
    void checkItem(String taskId, ObjVo objVo) throws GlobalException;

    /**
     * 现场采样更新点位检测项目信息 根据新检测项目重置样品信息
     *
     * @param v
     * @throws GlobalException
     */
    public boolean uptPoint(AppXcEditTpVo v, ObjVo objVo) throws GlobalException;

    /**
     * 选中要添加质控样的项目
     *
     * @param ids
     * @return
     * @throws GlobalException
     */
    public List<AppTaskApItemSelect> taskApItemSelectList(String ids) throws GlobalException;

    /**
     * 评价依据列表
     *
     * @param sampTypeId
     * @return
     * @throws GlobalException
     */

    public List<AppPstandard> pstandardList(String sampTypeId, String name) throws GlobalException;

    /**
     * 限值
     *
     * @param itemId
     * @param standId
     * @return
     * @throws GlobalException
     */

    public List<AppPstandItemVo> pstandItemList(String itemId, String standId) throws GlobalException;
}
