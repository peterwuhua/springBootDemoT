package cn.demi.bi.res.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bi.res.dao.IStandardDao;
import cn.demi.bi.res.po.Standard;
import cn.demi.bi.res.vo.StandardVo;

@Repository("bi.standardDao")
public class StandardDaoImpl extends BaseDaoImpl<Standard> implements IStandardDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		String jpql = " select Distinct ("+pageType+") from v_bi_standard where "+pageType+" IS NOT NULL AND is_del = 0 AND "+pageType+" != ''";
    return getEntityManager().createNativeQuery(jpql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> MaxPayTenStandard(StandardVo vo) throws GlobalException {
		String jpql = " SELECT name, safe_amount,amount FROM v_bi_standard WHERE is_del = 0 ";
		if(StrUtils.isNotBlankOrNull(vo.getUser()))
			jpql += "AND user IN ('"+vo.getUser().replace(",", "','")+"')";
		jpql += " GROUP BY name ORDER BY COUNT(id) DESC LIMIT 0,10";
		return getEntityManager().createNativeQuery(jpql).getResultList();
	}
}
