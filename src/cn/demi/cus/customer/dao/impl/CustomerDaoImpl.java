package cn.demi.cus.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.vo.CustomerVo;
/**
 * Create on : 2016年11月15日 下午2:45:38  <br>
 * Description :CustomerDaoImpl  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Repository("cus.customerDao")
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements ICustomerDao {

	@Override
	public Customer findByName(String cusName) {
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("name",QueryCondition.EQ, cusName));
		return super.query0(conditions, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectByPageType(String pageType) throws GlobalException {
		String jpql = " select "+pageType+" from v_bi_customer where is_del = 0 ";
	       jpql +=" group by " + pageType;
    return getEntityManager().createNativeQuery(jpql).getResultList();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> gradeList() throws GlobalException {
		String jpql = " select grade from v_bi_customer where is_del = 0 ";
	       jpql +=" group by grade";
	return getEntityManager().createNativeQuery(jpql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectByArea(String pageType, String data,String parentType) {
		String jpql = " select "+pageType+" from v_bi_customer where is_del = 0 ";
				if(null != parentType && !"".equals(parentType))
					 jpql +="and " +parentType+"=" + "'" +data + "'";
	       	   jpql +=" group by " + pageType;
	    return getEntityManager().createNativeQuery(jpql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> countBySalerAndGrade(String saler, String grade) {
		String jpql = " select count(id) from v_bi_customer where is_del = 0 ";
			   jpql +=" and grade = " + "'" +grade + "'";
			   jpql +=" and saler = " + "'" +saler + "'";
		return getEntityManager().createNativeQuery(jpql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> salerList(CustomerVo vo) throws GlobalException {
		String jpql = " select saler from v_bi_customer where is_del = 0 ";
		if(StrUtils.isNotBlankOrNull(vo.getSaler()))
			jpql += "AND saler IN ('"+vo.getSaler().replace(",", "','")+"')";
		if(StrUtils.isNotBlankOrNull(vo.getIndustry()))
			jpql += "AND industry IN ('"+vo.getIndustry().replace(",", "','")+"')";
		if(StrUtils.isNotBlankOrNull(vo.getCusType()))
			jpql += "AND cusType IN ('"+vo.getCusType().replace(",", "','")+"')";
		if(StrUtils.isNotBlankOrNull(vo.getBuildDate()))
			jpql += "AND buildDate IN ('"+vo.getBuildDate().replace(",", "','")+"')";
		if(StrUtils.isNotBlankOrNull(vo.getAreaPath()))
			jpql += "AND areaPath LIKE %'"+vo.getBuildDate()+"'%)";
	    jpql +=" group by saler";
	return getEntityManager().createNativeQuery(jpql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findCountGradesBySaler(CustomerVo vo,String saler) throws GlobalException {
		String jpql = "SELECT COUNT(grade) FROM v_bi_customer WHERE is_del = 0 "+"	AND saler = '"+saler+"'";
		if(StrUtils.isNotBlankOrNull(vo.getSaler()))
			jpql += "AND saler IN ('"+vo.getSaler().replace(",", "','")+"')";
		if(StrUtils.isNotBlankOrNull(vo.getIndustry()))
			jpql += "AND industry IN ('"+vo.getIndustry().replace(",", "','")+"')";
		if(StrUtils.isNotBlankOrNull(vo.getCusType()))
			jpql += "AND cusType IN ('"+vo.getCusType().replace(",", "','")+"')";
		if(StrUtils.isNotBlankOrNull(vo.getBuildDate()))
			jpql += "AND buildDate IN ('"+vo.getBuildDate().replace(",", "','")+"')";
		if(StrUtils.isNotBlankOrNull(vo.getAreaPath()))
			jpql += "AND areaPath LIKE %'"+vo.getBuildDate()+"'%)";
	return getEntityManager().createNativeQuery(jpql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String createCode() {
		String flag="";
		flag+=DateUtils.getYear()+DateUtils.getMonth();
		String hql="SELECT max(code) FROM "+getEntityName(Customer.class)+" WHERE isDel="+Po.N+" AND code like '"+flag+"%' ";
		List<String> ls=super.query(hql).getResultList();
		String no=null;
		if(ls!=null && ls.size()>0) {
			 no=ls.get(0);
		}
		if(no==null||no.equals("")) {
			no="01";
		}else {
			no=no.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(no);
			} catch (NumberFormatException e) {
				sort=0;
			}
			sort++;
			if(sort<10) {
				no="0"+sort;
			}else{
				no=""+sort;
			}
		}
		no=flag+no;
		return no;
	}
}
