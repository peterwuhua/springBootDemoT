package cn.demi.bus.sample.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.sample.po.Sampling;

public interface ISamplingDao extends IBaseDao<Sampling> {
	
	/**
	 * 根据任务ID删除样品信息
	 * @param taskId
	 */
	public void deleteByTask(String taskId);
	/**
	 * 根据采样单ID删除样品信息
	 * @param taskId
	 */
	public void deleteByCyd(String cydId);
	/**
	 * 根据采样单ID删除样品信息
	 * @param pointId
	 */
	public void deleteByPoint(String pointId);
	/**
	 * 获取点位样品集合
	 * 不包含质控样
	 * @param pointId
	 * @return
	 */
	List<Sampling> listByPointId(String pointId);
	/**
	 * 获取点位样品集合
	 * 包含质控样
	 * @param pointId
	 * @return
	 */
	List<Sampling> listAllByPointId(String pointId);
	/**
	 * 获取样品集合
	 * @param cydId 采样单Id
	 * @return
	 */
	List<Sampling> listByCyd(String cydId);
	/**
	 * 获取样品集合
	 * @param cydId 采样单Id
	 * @param type 0 普通样 -1 虚拟样
	 * @return
	 */
	List<Sampling> listByCyd(String cydId,String type);
	/**
	 * 获取任务样品集合
	 * @param taskId
	 * @return
	 */
	List<Sampling> listByTaskId(String taskId);
	/**
	 * 获取任务样品集合
	 * @param taskId
	 * @param type 0 普通样 -1 虚拟样
	 * @return
	 */
	List<Sampling> listByTaskId(String taskId,String type);
	/**
	 * 获取任务样品集合
	 * @param taskId 任务id
	 *  @param p  批次
	 * @return
	 */
	List<Sampling> listByP(String taskId,String p);
	/**
	 * 获取任务样品集合
	 * @param pointId
	 * @return
	 */
	List<Sampling> listAllByTaskId(String taskId);
	
	int findMaxSort(String taskId);

	/**
	 * 或样品数量
	 * @param poinId 点位id
	 * @param zkType  0 普通样 1 现场平行样 2 室内平行样 3全程序空白样 4 加标回收样
	 * @return
	 */
	public int findNumByPoinId(String poinId,String zkType);
	/**
	 * 获取某个点位每天最多的样品数
	 * 环境 报告 水部分
	 * @param poinId
	 * @return
	 */
	public int maxNum4PointDay(String poinId);
	/**
	 * 生成样品编号 职卫
	 * @param type 样品类型 虚拟样 -1 普通样 0
	 * @param sampType 样品大类 环境、职业卫生、公共卫生
	 * @param reportNo 报告编号
	 * @param itemId 项目id
	 * @param pointSort 点位序号
	 * @param pc 批次 
	 * @return
	 */
	public String createSampCodeZw(String type,String sampType,String reportNo,String itemId,int pointSort,String pc);
	/**
	 * 生成样品编号 环境
	 * @param reportNo 报告编号
	 * @param sampTypeId 样品类型
	 * @param itemId 项目id
	 * @param pointSort 点位序号
	 * @param pc 批次 
	 * @return
	 */
	public String createSampCodeHj(String reportNo,String sampTypeId,String itemId,int pointSort,String pc);
	/**
	 * 生成样品编号 质控样
	 * @param reportNo 报告编号
	 * @param sampTypeId 样品类型
	 * @param itemId 项目id
	 * @param pointSort 点位序号
	 * @param pc 批次 
	 * @param zkType 质控类型
	 * @return
	 */
	public String createSampCodeZk(String reportNo,String sampTypeId,String itemId,int pointSort,String pc,String zkType);
}

