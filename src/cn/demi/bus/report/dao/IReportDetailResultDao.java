package cn.demi.bus.report.dao;
import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.report.po.ReportDetailResult;
/**
 * 报告详情持久层接口
 * @author QuJunLong
 *
 */
public interface IReportDetailResultDao extends IBaseDao<ReportDetailResult> {
	
	public void deleteByReportId(String reportId);
	
	public void deleteByDetailId(String detailId);
	
	public List<ReportDetailResult> listByDetailId(String detailId);
	/**
	 * 获取结果信息
	 * @param sampId 样品id
	 * @param itemId 项目id
	 * @return
	 */
	public ReportDetailResult findOne(String sampId,String itemId);
}

