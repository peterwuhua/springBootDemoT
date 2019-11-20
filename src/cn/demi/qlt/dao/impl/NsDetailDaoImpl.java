package cn.demi.qlt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.qlt.dao.INsDetailDao;
import cn.demi.qlt.po.NsDetail;

@Repository("qlt.nsDetailDao")
public class NsDetailDaoImpl extends BaseDaoImpl<NsDetail> implements INsDetailDao {

	@Override
	public List<NsDetail> listByNsId(String nsId) {
		String hql = "FROM "+getEntityName(NsDetail.class)+" WHERE isDel='"+Po.N+"' AND ns.id='"+nsId+"' order by code ASC";
		@SuppressWarnings("unchecked")
		List<NsDetail> imList=getEntityManager().createQuery(hql).getResultList();
		return imList;
	}
}
