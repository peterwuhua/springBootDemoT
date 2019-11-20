package cn.demi.bus.report.dao;
import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.report.po.ReportDetail;
/**
 * 报告详情持久层接口
 * @author QuJunLong
 *
 */
public interface IReportDetailDao extends IBaseDao<ReportDetail> {
	
	public void deleteByReportId(String reportId);
	
	public List<ReportDetail> listByReportId(String reportId);
	/**
	 * 获取报告结果详情，
	 * @param reportId
	 * @param type 0 普通样 1 质控样
	 * @return
	 */
	public List<ReportDetail> listByReportId(String reportId,String type);
	/**
	 * 获取报告结果详情，
	 * @param reportId
	 * @param samTypeId 样品类型
	 * @return
	 */
	public List<ReportDetail> listBySampTypeId(String reportId,String samTypeId);
	/**
	 * 获取报告结果详情，
	 * @param samplingId 样品Id
	 * @return
	 */
	public List<ReportDetail> listBySampId(String samplingId);
	/**
	 * 获取报告结果详情，
	 * @param pointId 点位Id
	 * @return
	 */
	public List<ReportDetail> listByPointId(String pointId);
	/**
	 * 获取样品的监测项目
	 * @param sampId 样品Id
	 * @param itemId  项目Id
	 * @return
	 */
	public ReportDetail findOne(String sampId,String itemId);
	/**
	 * 获取样品的监测项目
	 * 气
	 * 某点位 某时间某项目的检测信息
	 * @param sampId
	 * @param itemId
	 * @return
	 */
	public ReportDetail findOne4Point(String pointId,String itemId,String cyTime);
	/**
	 * 获取点位的 某批次的监测项目
	 * 只针对水
	 * @param pointId 点位
	 * @param sampCode 样品编号
	 * @param itemId 项目
	 * @return
	 */
	public ReportDetail findOne4PointAndSampCode(String pointId,String sampCode,String itemId);
	/**
	 * 根据任务编号获取项目集合
	 * @param taskId 任务编号
	 */
	public List<ReportDetail> listByParent(String pid);
	/**
	 * 获取 某标准 合格或不合格的项目名称集
	 * @param reportId 报告Id
	 * @param sampType 样品类型
	 * @param standId 标准Id
	 * @param  type 合格 不合格
	 * @return
	 */
	public String findItems(String reportId,String sampType,String standId,String type);
	/**
	 * 职业卫生
	 * 获取 某标准 合格或不合格的项目名称集
	 * @param reportId 报告Id
	 * @param sampType 样品类型
	 * @param standId 标准Id
	 * @param  type 合格 不合格
	 * @return
	 */
	public String findItems(String reportId,String standId,String type);
	/**
	 * 检查项目是否检出
	 * 检出线
	 */
	public boolean checkJcx(String reportId,String itemId);
}

