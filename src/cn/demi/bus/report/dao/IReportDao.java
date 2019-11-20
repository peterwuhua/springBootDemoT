package cn.demi.bus.report.dao;
import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.report.po.Report;
/**
 * 报告持久层接口
 * @author QuJunLong
 *
 */
public interface IReportDao extends IBaseDao<Report> {
	/**
	 * 根据任务编号获取报告集合
	 * @param taskId 任务编号
	 */
	public Report findByTaskId(String taskId);
	
//	/**
//	 * 根据样品获取报告信息
//	 * @param sampId
//	 * @return
//	 * @throws GlobalException
//	 */
//	public Report findBySamp(String sampId);
//	/**
//	 * 检查任务下所有报告是否完成
//	 * 质检
//	 * @param sampId
//	 * @return
//	 * @throws GlobalException
//	 */
//	public boolean checkAllComp(String taskId);
//	/**
//	 * 检查任务下所有报告是否完成
//	 * 计量
//	 * @param sampId
//	 * @return
//	 * @throws GlobalException
//	 */
//	public boolean checkAllComp4Jl(String taskId);
}

