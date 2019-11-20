package cn.demi.init.sp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.demi.init.sp.dao.ISampPointDao;
import cn.demi.init.sp.po.SampPoint;

@Repository("init.samp_pointDao")
public class SampPointDaoImpl extends BaseDaoImpl<SampPoint> implements ISampPointDao {

	@SuppressWarnings("unchecked")
	@Override
	public SampPoint findByName(String sampId,String name) {
		String hql="FROM "+getEntityName(SampPoint.class)+" WHERE isDel='"+Po.N+"' AND  name like '"+name+"'";
		hql+=" AND sampId like '%"+sampId+"%'";
		List<SampPoint> ls=getEntityManager().createQuery(hql).getResultList();
		if(ls!=null&&ls.size()>0) {
			return ls.get(0);
		}else {
			return null;
		}
	}
}
