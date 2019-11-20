package cn.demi.qlt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.qlt.dao.INsRecordDao;
import cn.demi.qlt.po.NsRecord;

@Repository("qlt.nsRecordDao")
public class NsRecordDaoImpl extends BaseDaoImpl<NsRecord> implements INsRecordDao {

	@SuppressWarnings("unchecked")
	@Override
	public NsRecord findByNsId(String nsId, int month) {
		String hql = "FROM "+getEntityName(NsRecord.class)+" WHERE isDel='"+Po.N+"' AND ns.id='"+nsId+"' AND month='"+month+"'";
		List<NsRecord> list=getEntityManager().createQuery(hql).getResultList();
		if(null!=list &&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NsRecord> findByNsId(String nsId) {
		String hql = "FROM "+getEntityName(NsRecord.class)+" WHERE isDel='"+Po.N+"' AND ns.id='"+nsId+"'  ORDER by month asc";
		return getEntityManager().createQuery(hql).getResultList();
	}
}
