package cn.demi.res.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.StrUtils;
import cn.demi.res.dao.IStandardDao;
import cn.demi.res.po.Standard;

@Repository("res.standardDao")
public class StandardDaoImpl extends BaseDaoImpl<Standard> implements IStandardDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> countStandard(String name) {
		String jpql ="select name ,safe_amount,amount,unit,user  from v_res_standard where safe_amount>=amount and is_del='N' ";
		if(!StrUtils.isBlankOrNull(name)){
			jpql+=" AND name like '%"+name+"%' ";
		}
		return getEntityManager().createNativeQuery(jpql).getResultList();
	}

	@Override
	public Standard find(String name, String no) {
		String hql="FROM "+getEntityName(Standard.class)+" WHERE isDel="+Po.N+" AND name='"+name+"' AND no='"+no+"'";
		List<Standard> list=super.list(hql);
		if(null!=list&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
	}
}
