package cn.demi.bus.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.report.dao.IReportDetailResultDao;
import cn.demi.bus.report.po.ReportDetailResult;

@Repository("bus.reportDetailResultDao")
public class ReportDetailResultDaoImpl extends BaseDaoImpl<ReportDetailResult> implements IReportDetailResultDao {

	@Override
	public void deleteByReportId(String reportId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(ReportDetailResult.class)+" WHERE reportId ='"+reportId+"' ").executeUpdate();
	}
	@Override
	public void deleteByDetailId(String detailId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(ReportDetailResult.class)+" WHERE detailId ='"+detailId+"' ").executeUpdate();
	}

	@Override
	public List<ReportDetailResult> listByDetailId(String detailId) {
		String hql="FROM "+getEntityName(ReportDetailResult.class)+" WHERE isDel='"+Po.N+"' AND detailId ='"+detailId+"' ORDER BY sort asc";
		return super.list(hql);
	}
	@Override
	public ReportDetailResult findOne(String sampId, String itemId) {
		String hql="FROM "+getEntityName(ReportDetailResult.class)+" WHERE isDel='"+Po.N+"' AND samplingId ='"+sampId+"' AND itemId='"+itemId+"'";
		@SuppressWarnings("unchecked")
		List<ReportDetailResult> list= getEntityManager().createQuery(hql).getResultList();
		if(null!=list&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

}
