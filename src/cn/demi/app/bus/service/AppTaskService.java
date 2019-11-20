package cn.demi.app.bus.service;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.bus.vo.*;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.cus.customer.vo.ClientVo;
import cn.demi.cus.customer.vo.CustVo;
import cn.demi.cus.customer.vo.CustomerVo;

import java.util.List;

public interface AppTaskService {

    /**
     * app 代办所有任务列表
     *
     * @param v
     * @return
     * @throws GlobalException
     */
    public List<AllTaskVo> appAllTask(ObjVo v) throws GlobalException;

    /**
     * 任务登记
     *
     * @param v
     * @return
     * @throws GlobalException
     */
    public List<AppTask> taskList(ObjVo v) throws GlobalException;

    /**
     * 任务登记详情
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    public AppTaskEdit taskEdit(String id) throws GlobalException;

    /**
     * 监测项目列表
     *
     * @return
     * @throws GlobalException
     */

    public List<AppTaskImSelect> imSelectList(String sampTypeId, String sampType, String source, String imId) throws GlobalException;


    /**
     * 自动补全方法
     */
    public List<CustVo> list4Sim(ClientVo v) throws GlobalException;

    /**
     * 自动补全
     *
     * @param v
     * @return
     * @throws GlobalException
     */
    public List<CustVo> list4Full(CustomerVo v) throws GlobalException;

    /**
     * 获取客户的点位集合
     *
     * @param custName
     * @param sampTypeId
     * @return
     * @throws GlobalException
     */
    public List<AppCustPointSelect> CustPointSelectlist(String custName, String sampTypeId) throws GlobalException;

    /**
     * 分包商列表
     *
     * @param fbIds
     * @return
     * @throws GlobalException
     */
    public List<AppTaskFb> fbList(String fbIds) throws GlobalException;

    /**
     * 任务登记保存或提交
     *
     * @param appTaskEdit
     * @return
     * @throws GlobalException
     */
    public Boolean taskAddOrUpDate(AppTaskEdit appTaskEdit, ObjVo objVo, String isCommit) throws GlobalException;

}
