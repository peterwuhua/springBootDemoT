package cn.demi.bus.task.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.task.vo.SampAppVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;

@Transactional
public interface ITaskXcService extends IBaseService<TaskVo> {
	
	/**
	 * 现场采样 
	 * 采样单信息
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public TaskVo find(String id) throws GlobalException;
	/**
	 * 已办列表
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public GridVo gridDatad(GridVo gridVo,TaskVo v) throws GlobalException;
	/**
	 * 更新协议文件
	 * @param id
	 * @param sort
	 * @return
	 * @throws GlobalException
	 */
	boolean update4File(TaskVo v) throws GlobalException;
	/**
	 *退回到采样安排
	 * @param id
	 * @throws GlobalException
	 */
	void updateBack(TaskVo v) throws GlobalException;
	/**
	 * 删除点位
	 * @param pointId
	 * @throws GlobalException
	 */
	void deletePoint(String pointId) throws GlobalException;
	/**
	 * 新增点位
	 * @param taskId
	 * @throws GlobalException
	 */
	void addPoint(String taskId) throws GlobalException;
	/**
	 * 现场采样更新点位检测项目信息
	 * 根据新检测项目重置样品信息
	 * @param v
	 * @throws GlobalException
	 */
	public void uptPoint(TaskPointVo v) throws GlobalException;
	/**
	 * 合并采样单
	 * @param id
	 * @param sort
	 * @return
	 * @throws GlobalException
	 */
	boolean update4Hb(String ids) throws GlobalException;
	/**
	 * 拆分采样单
	 * @param id
	 * @param sort
	 * @return
	 * @throws GlobalException
	 */
	boolean update4Cf(String id) throws GlobalException;
	/**
	 *重置采样单
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	boolean initAllCyd(TaskVo v) throws GlobalException;
	/**
	 *录入现场项目结果前  检查现场项目信息
	 *若缺失自动补充
	 * @param taskId 任务Id
	 * @return
	 * @throws GlobalException
	 */
	void checkItem(String taskId) throws GlobalException;
	/**
	 * 根据点位获取采样单
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
//	public TaskPointVo findTaskPoint(String pointId) throws GlobalException;
	/**
	 *获取采样设备集合 
	 * @param busid 任务Id
	 * @return
	 * @throws GlobalException
	 */
	public List<SampAppVo> getAppList(String busId) throws GlobalException;
 
	
	public void updateApp(TaskVo v, ObjVo objVo) throws GlobalException;

}
