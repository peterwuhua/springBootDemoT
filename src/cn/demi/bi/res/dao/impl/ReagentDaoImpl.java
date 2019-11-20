package cn.demi.bi.res.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bi.res.dao.IReagentDao;
import cn.demi.bi.res.po.Reagent;
import cn.demi.bi.res.vo.ReagentVo;

@Repository("bi.reagentDao")
public class ReagentDaoImpl extends BaseDaoImpl<Reagent> implements IReagentDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		String jpql = " select Distinct ("+pageType+") from v_bi_reagent where "+pageType+" IS NOT NULL AND is_del = 0";
    return getEntityManager().createNativeQuery(jpql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> MaxPayTenReagent(ReagentVo vo) throws GlobalException {
		String jpql = " SELECT name,safe_amount,amount FROM v_bi_reagent WHERE is_del = 0";
		
		if(null != vo.getGrade() && StringUtils.isNotBlank(vo.getGrade()))
			jpql += " AND grade IN ('"+vo.getGrade().replace(",", "','")+"')";
		
		jpql += " GROUP BY name ORDER BY COUNT(id) DESC LIMIT 0,10";
		return getEntityManager().createNativeQuery(jpql).getResultList();
	}	
}
