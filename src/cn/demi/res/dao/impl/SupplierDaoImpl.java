package cn.demi.res.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.utils.StrUtils;
import cn.demi.res.dao.ISupplierDao;
import cn.demi.res.po.Supplier;

@Repository("res.supplierDao")
public class SupplierDaoImpl extends BaseDaoImpl<Supplier> implements ISupplierDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> countStandard(String name) {
		String jpql ="select name ,safe_amount,amount,unit,user  from v_res_standard where safe_amount>=amount and is_del='N' ";
		if(!StrUtils.isBlankOrNull(name)){
			jpql+=" AND name like '%"+name+"%' ";
		}
		return getEntityManager().createNativeQuery(jpql).getResultList();
	}
}
