package cn.demi.res.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.utils.StrUtils;
import cn.demi.res.dao.IReagentDao;
import cn.demi.res.po.Reagent;

@Repository("res.regOutInDao")
public class ReagentDaoImpl extends BaseDaoImpl<Reagent> implements IReagentDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> countReagent(String name) {
		String jpql ="select name ,safe_amount,amount,unit,user  from v_res_reagent where safe_amount>amount and is_del='N'";
		if(!StrUtils.isBlankOrNull(name)){
			jpql+=" AND name like '%"+name+"%'";
		}
		return getEntityManager().createNativeQuery(jpql).getResultList();
	}
}
