package cn.demi.app.bus.service;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.bus.vo.AppProject;
import cn.demi.app.bus.vo.AppProjectEdit;
import cn.demi.app.bus.vo.AppPstandard;
import cn.demi.app.sys.vo.ObjVo;

import java.util.List;

public interface AppProjectService {
    /**
     * 项目立项列表
     *
     * @return
     * @throws GlobalException
     */
    public List<AppProject> projectList(ObjVo v) throws GlobalException;

    /**
     * 项目立项详情
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    public AppProjectEdit projectEdit(String id) throws GlobalException;

    /**
     * 检测类型列表
     *
     * @param sampType
     * @return
     * @throws GlobalException
     */
    public List<String> appTaskTypeList(String sampType) throws GlobalException;

    /**
     * 方案编制列表
     *
     * @return
     * @throws GlobalException
     */

    public List<AppProject> projectFaList(ObjVo objVo) throws GlobalException;

    /**
     * 评价依据列表
     *
     * @param sampTypeId
     * @return
     * @throws GlobalException
     */

    public List<AppPstandard> pstandardList(String sampTypeId, String name) throws GlobalException;

    /**
     * 项目立项保存或提交
     *
     * @param appProjectEdit
     * @param objVo
     * @param isCommit
     * @return
     * @throws GlobalException
     */
    public Boolean projectAddOrUpDate(AppProjectEdit appProjectEdit, ObjVo objVo, String isCommit) throws GlobalException;


    /**
     * 更新下一页项目信息
     *
     * @param v
     * @throws GlobalException
     */
    public Boolean updateNext(AppProjectEdit v, String isCommit, ObjVo objVo) throws GlobalException;
}
