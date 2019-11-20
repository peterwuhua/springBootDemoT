package cn.demi.bus.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.report.po.Report;

@Repository("bus.reportDao")
public class ReportDaoImpl extends BaseDaoImpl<Report> implements IReportDao {

	@Override
	public Report findByTaskId(String taskId) {
		String hql="FROM "+getEntityName(Report.class)+" WHERE isDel = '"+Po.N+"' AND task.id ='"+taskId+"' ";
		@SuppressWarnings("unchecked")
		List<Report> rlist=query(hql).getResultList();
		if(null!=rlist&&rlist.size()>0) {
			return rlist.get(0);
		}else {
			return null;
		}
	}

//	@Override
//	public Report findBySamp(String sampId) {
//		String hql="FROM "+getEntityName(Report.class)+" WHERE isDel = '"+Po.N+"' AND sampling.id ='"+sampId+"' ORDER BY sort asc";
//		List<Report> lst=super.list(hql);
//		if(null!=lst && lst.size()>0) {
//			return lst.get(0);
//		}else {
//			return null;
//		}
//	}

//	@Override
//	public boolean checkAllComp(String taskId) {
//		String hql="FROM "+getEntityName(Report.class)+" WHERE isDel = '"+Po.N+"' AND task.id ='"+taskId+"' AND status!='"+EunmTask.REPORT_46.getStatus()+"'";
//		List<Report> lst=super.list(hql);
//		if(null!=lst && lst.size()>0) {
//			return false;
//		}else {
//			return true;
//		}
//	}
//
//	@Override
//	public boolean checkAllComp4Jl(String taskId) {
//		String hql="FROM "+getEntityName(Report.class)+" WHERE isDel = '"+Po.N+"' AND task.id ='"+taskId+"' AND status!='"+EunmJl.SAMP_39.getStatus()+"'";
//		List<Report> lst=super.list(hql);
//		if(null!=lst && lst.size()>0) {
//			return false;
//		}else {
//			return true;
//		}
//	}
}
