package cn.demi.bus.test.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.bus.test.vo.TestItemVo;

@Transactional
public interface ITestItemService extends IBaseService<TestItemVo> {
	
	/**
	 * 根据项目任务获取其测试项目集合
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public List<TestItemVo> listByTimId(String id) throws GlobalException;
	/**
	 * 获取其测试项目集合
	 * 现场采样 现场项目数据源
	 * @param taskId 任务ID
	 * @return
	 * @throws GlobalException
	 */
	public List<TaskItemVo> list4Xc(String taskId) throws GlobalException;
	/**
	 * 更新现场项目结果
	 * @param v 采样单
	 * @throws GlobalException
	 */
	public void update4Xc(TaskVo v) throws GlobalException;
	/**
	 * 添加质控项目
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public boolean add4Zk(TestItemVo v) throws GlobalException;
	/**
	 * 删除质控项目
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public boolean delete4Zk(String id) throws GlobalException;
	/**
	 * 检查数据是否合格
	 * @param id
	 * @return
	 * @throws GlobalException
	 */
	public boolean checkValue(TestItemVo v) throws GlobalException;
}
