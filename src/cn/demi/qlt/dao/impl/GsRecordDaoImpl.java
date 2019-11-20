package cn.demi.qlt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.qlt.dao.IGsRecordDao;
import cn.demi.qlt.po.GsRecord;

@Repository("qlt.gsRecordDao")
public class GsRecordDaoImpl extends BaseDaoImpl<GsRecord> implements IGsRecordDao {

	@Override
	public List<GsRecord> listByGsId(String gsId) {
		String hql = "FROM "+getEntityName(GsRecord.class)+" WHERE isDel='"+Po.N+"' AND gs.id='"+gsId+"' order by date ASC";
		@SuppressWarnings("unchecked")
		List<GsRecord> imList=getEntityManager().createQuery(hql).getResultList();
		return imList;
	}
}
