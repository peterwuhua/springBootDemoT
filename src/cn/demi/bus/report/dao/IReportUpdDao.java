package cn.demi.bus.report.dao;
import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.report.po.ReportUpd;
/**
 * 报告详情持久层接口
 * @author QuJunLong
 *
 */
public interface IReportUpdDao extends IBaseDao<ReportUpd> {
	
	public void deleteByReportId(String reportId);
	
	public List<ReportUpd> listByReportId(String reportId);
	 
}

