package cn.demi.bus.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.report.dao.IReportDetailDao;
import cn.demi.bus.report.po.ReportDetail;
import cn.demi.bus.sample.po.Sampling;

@Repository("bus.reportDetailDao")
public class ReportDetailDaoImpl extends BaseDaoImpl<ReportDetail> implements IReportDetailDao {

	@Override
	public void deleteByReportId(String reportId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(ReportDetail.class)+" WHERE reportId ='"+reportId+"' AND parent.id is not null").executeUpdate();
		getEntityManager().createQuery("DELETE FROM "+getEntityName(ReportDetail.class)+" WHERE reportId ='"+reportId+"'").executeUpdate();
	}

	@Override
	public List<ReportDetail> listByReportId(String reportId) {
		String hql="FROM "+getEntityName(ReportDetail.class)+" WHERE isDel='"+Po.N+"' AND reportId ='"+reportId+"' AND parent.id is null ORDER BY sort asc";
		return super.list(hql);
	}
	@Override
	public List<ReportDetail> listByParent(String pid) {
		String hql="FROM "+getEntityName(ReportDetail.class)+" WHERE isDel='"+Po.N+"' AND parent.id ='"+pid+"' ORDER BY sort asc";
		return super.list(hql);
	}

	@Override
	public List<ReportDetail> listBySampId(String sampId) {
		String hql="FROM "+getEntityName(ReportDetail.class)+" WHERE isDel='"+Po.N+"' AND samplingId ='"+sampId+"' AND parent.id is null ORDER BY sort asc";
		return super.list(hql);
	}

	@Override
	public ReportDetail findOne(String sampId, String itemId) {
		String hql="FROM "+getEntityName(ReportDetail.class)+" WHERE isDel='"+Po.N+"' AND samplingId ='"+sampId+"' AND itemId='"+itemId+"'";
		@SuppressWarnings("unchecked")
		List<ReportDetail> list= getEntityManager().createQuery(hql).getResultList();
		if(null!=list&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<ReportDetail> listByPointId(String pointId) {
		String hql="FROM "+getEntityName(ReportDetail.class)+" WHERE isDel='"+Po.N+"' AND pointId ='"+pointId+"' AND parent.id is null ORDER BY sort asc";
		return super.list(hql);
	}

	@Override
	public List<ReportDetail> listByReportId(String reportId, String type) {
		String hql="FROM "+getEntityName(ReportDetail.class)+" WHERE isDel='"+Po.N+"' AND reportId ='"+reportId+"' AND parent.id is null";
		if(type.equals("0")) {
			hql+=" AND stype='"+Sampling.SAMP_TYPE_PT+"'";
		}
		if(type.equals("1")) {
			hql+=" AND stype<>'"+Sampling.SAMP_TYPE_PT+"'";
		}
		hql+=" ORDER BY sort asc";
		return super.list(hql);
	}

	@Override
	public ReportDetail findOne4PointAndSampCode(String pointId,String sampCode,String itemId) {
		String hql="FROM "+getEntityName(ReportDetail.class)+" WHERE isDel='"+Po.N+"' AND pointId ='"+pointId+"'AND sampCode='"+sampCode+"' AND itemId='"+itemId+"'";
		@SuppressWarnings("unchecked")
		List<ReportDetail> list= getEntityManager().createQuery(hql).getResultList();
		if(null!=list&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public ReportDetail findOne4Point(String pointId, String itemId, String cyTime) {
		String hql="FROM "+getEntityName(ReportDetail.class)+" WHERE isDel='"+Po.N+"' AND pointId ='"+pointId+"' AND itemId='"+itemId+"' AND cyTime='"+cyTime+"' ";
		@SuppressWarnings("unchecked")
		List<ReportDetail> list= getEntityManager().createQuery(hql).getResultList();
		if(null!=list&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public List<ReportDetail> listBySampTypeId(String reportId, String samTypeId) {
		String hql="FROM "+getEntityName(ReportDetail.class)+" WHERE isDel='"+Po.N+"' AND reportId ='"+reportId+"' AND parent.id is null";
		hql+=" AND sampTypeId='"+samTypeId+"'";
		hql+=" ORDER BY sort asc";
		return super.list(hql);
	}

	@Override
	public String findItems(String reportId, String sampType, String standId, String type) {
		String sql="select group_concat(DISTINCT(item_name)) from v_bus_report_detail where report_id='"+reportId
				+"' AND samp_type_id='"+sampType+"' AND stand_id='"+standId+"' AND result='"+type+"' ";
		Object itemNames=super.queryBysql(sql).getSingleResult();
		if(null!=itemNames) {
			return itemNames.toString();
		}else {
			return null;
		}
		
	}

	@Override
	public String findItems(String reportId, String standId, String type) {
		String sql="select group_concat(DISTINCT(concat(room,point_name,item_name))) from v_bus_report_detail where report_id='"+reportId
				+"' AND stand_id='"+standId+"' AND result='"+type+"' ";
		Object itemNames=super.queryBysql(sql).getSingleResult();
		if(null!=itemNames) {
			return itemNames.toString();
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkJcx(String reportId, String itemId) {
		boolean f=false;
		String hql="FROM "+getEntityName(ReportDetail.class)+" WHERE isDel='"+Po.N+"' AND reportId ='"+reportId+"' AND itemId='"+itemId+"'";
		List<ReportDetail> list= getEntityManager().createQuery(hql).getResultList();
		if(null!=list&&list.size()>0) {
			for (ReportDetail rd : list) {
				if(!StrUtils.isBlankOrNull(rd.getValue())&&!rd.getValue().equals("ND")&&!rd.getValue().contains("未检出")) {
					f=true;
				}
			}
		}
		return f;
	}
}
