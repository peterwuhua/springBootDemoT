package cn.demi.office.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.DateUtils;
import cn.demi.base.system.po.Account;
import cn.demi.office.dao.IDgDao;
import cn.demi.office.po.Dg;

@Repository("office.dgDao")
public class DgDaoImpl extends BaseDaoImpl<Dg> implements IDgDao {

	@Override
	public Dg insert(String userId) {
		Account at = getEntityManager().find(Account.class, userId);
		Dg po=new Dg();
		po.setType(Dg.TP_DK);
		po.setDeptId(at.getOrg().getId());
		po.setDeptName(at.getOrg().getName());
		po.setUserId(at.getUser().getId());
		po.setUserName(at.getUser().getName());
		po.setDate(DateUtils.getCurrDateTimeStr());
		po.setCurTime(DateUtils.getCurrDateTimeStr());
		po.setContent("移动端考勤打卡");
		po.setStatus(Dg.ST_2);
		if(po.getSort()==null) {
			po.setSort(getMaxSort()+1);
		}
		getEntityManager().persist(po);
		return po;
	}

	@Override
	public List<Dg> find(String userId, String date) {
		StringBuffer jpql = new StringBuffer("FROM "+getEntityName(Dg.class));
		jpql.append(" WHERE userId='"+userId+"'");
		jpql.append(" AND date like '"+date+"%'");
		jpql.append(" AND isDel= "+Po.N);
		List<Dg> lst=super.list(jpql.toString());
		return lst;
	}
	@Override
	public List<Dg> findPm(String userId, String date) {
		StringBuffer jpql = new StringBuffer("FROM "+getEntityName(Dg.class));
		jpql.append(" WHERE userId='"+userId+"'");
		jpql.append(" AND date > '"+date+"'");
		jpql.append(" AND isDel= "+Po.N);
		List<Dg> lst=super.list(jpql.toString());
		return lst;
	}

	@Override
	public List<Dg> findApp(String userId, String date,String checkOrNoCkeck) {
		StringBuffer jpql = new StringBuffer("FROM "+getEntityName(Dg.class));
		jpql.append(" WHERE userId='"+userId+"'");
		jpql.append(" AND date like '"+date+"%'");
		jpql.append(" AND isDel= "+Po.N);
		jpql.append(" AND check_or_no_check='"+checkOrNoCkeck+"'");
		List<Dg> lst=super.list(jpql.toString());
		return lst;
	}
	@Override
	public List<Dg> findPmApp(String userId, String date,String checkOrNoCkeck) {
		StringBuffer jpql = new StringBuffer("FROM "+getEntityName(Dg.class));
		jpql.append(" WHERE userId='"+userId+"'");
		jpql.append(" AND date > '"+date+"'");
		jpql.append(" AND isDel= "+Po.N);
		jpql.append(" AND check_or_no_check='"+checkOrNoCkeck+"'");
		List<Dg> lst=super.list(jpql.toString());
		return lst;
	}
}
