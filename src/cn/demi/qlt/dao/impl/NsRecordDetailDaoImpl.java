package cn.demi.qlt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.StrUtils;
import cn.demi.qlt.dao.INsRecordDetailDao;
import cn.demi.qlt.po.NsRecordDetail;

@Repository("qlt.nsRecordDetailDao")
public class NsRecordDetailDaoImpl extends BaseDaoImpl<NsRecordDetail> implements INsRecordDetailDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<NsRecordDetail> listByPid(String rId, String pId) {
		String hql =null;
		if(StrUtils.isBlankOrNull(pId)) {
			hql = "FROM "+getEntityName(NsRecordDetail.class)+" WHERE isDel='"+Po.N+"' AND recordId='"+rId+"' AND parent.id is null";
		}else {
			hql = "FROM "+getEntityName(NsRecordDetail.class)+" WHERE isDel='"+Po.N+"' AND recordId='"+rId+"' AND parent.id='"+pId+"'";
		}
		hql+=" ORDER BY code,sort ASC";
		List<NsRecordDetail> list=getEntityManager().createQuery(hql).getResultList();
		return list;
	}
}
