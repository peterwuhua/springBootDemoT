package cn.demi.res.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.res.dao.ICertificateDao;
import cn.demi.res.po.Certificate;

@Repository("res.certificateDao")
public class CertificateDaoImpl extends BaseDaoImpl<Certificate> implements ICertificateDao {

	@Override
	public Certificate findByItem(String userId, String itemId) {
		String hql="FROM "+getEntityName(Certificate.class)+" WHERE isDel = '"+Po.N+"' AND userId ='"+userId+"' AND itemId like '%"+itemId+"%' ORDER BY endTime DESC";
		@SuppressWarnings("unchecked")
		List<Certificate> rlist=query(hql).getResultList();
		if(null!=rlist&&rlist.size()>0) {
			return rlist.get(0);
		}else {
			return null;
		}
	}
}
