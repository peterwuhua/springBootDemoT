package cn.demi.office.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.DateUtils;
import cn.demi.base.system.po.Account;
import cn.demi.office.dao.IKqDao;
import cn.demi.office.po.Kq;

@Repository("office.kqDao")
public class KqDaoImpl extends BaseDaoImpl<Kq> implements IKqDao {

	@Override
	public void insert(String atId,String startTime,String endTime, String msg, String type, String status, String auditId) {
		Account at = getEntityManager().find(Account.class, atId);
		Account audit = getEntityManager().find(Account.class, auditId);
		Kq kq=new Kq();
		kq.setNo(createCode());
		kq.setType(type);
		kq.setUserId(at.getUser().getId());
		kq.setUserName(at.getUser().getName());
		kq.setDeptId(at.getOrg().getId());
		kq.setDeptName(at.getOrg().getName());
		kq.setContent(msg);
		kq.setStatus(status);
		kq.setAuditId(audit.getId());
		kq.setAuditName(audit.getUser().getName());
		kq.setDate(startTime);
		kq.setEndTime(endTime);
		getEntityManager().persist(kq);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(String atIds,String startTime,String endTime, String msg,String busId) {
		StringBuffer jpql = new StringBuffer("FROM "+getEntityName(Account.class));
		jpql.append(" WHERE id IN ('"+toString(atIds).replace(",", "','")+"')");
		jpql.append(" AND isDel= "+Po.N);
		List<Account> lst=super.query(jpql.toString()).getResultList();
		for (Account at : lst) {
			Kq kq=new Kq();
			kq.setNo(createCode());
			kq.setBusId(busId);
			kq.setType(Kq.TP_WQ);
			kq.setUserId(at.getUser().getId());
			kq.setUserName(at.getUser().getName());
			kq.setDeptId(at.getOrg().getId());
			kq.setDeptName(at.getOrg().getName());
			kq.setContent(msg);
			kq.setStatus(Kq.ST_2);
			kq.setDate(startTime);
			kq.setEndTime(endTime);
			getEntityManager().persist(kq);
		}
	}

	@Override
	public void deleteByBusId(String busId) {
		String hql="DELETE FROM  "+getEntityName(Kq.class)+" WHERE busId='"+busId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
	}
	/**
	 * 生成编号
	 */
	public String createCode(){
		String flag=DateUtils.getYear()+DateUtils.getMonth()+DateUtils.getDay();
		String hql="SELECT max(no) FROM "+getEntityName(Kq.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
		String ls=(String) super.query(hql).getSingleResult();
		String no=flag;
		if(ls==null) {
			no+="01";
		}else {
			ls=ls.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort=0;
			}
			sort++;
			if(sort<10) {
				no+="0"+sort;
			}else {
				no+=sort;
			}
		}
		return no;
	}

	@Override
	public String findByUserId(String userId, String date) {
		StringBuffer jpql = new StringBuffer("FROM "+getEntityName(Kq.class));
		jpql.append(" WHERE userId='"+userId+"'");
		jpql.append(" AND (date like '"+date+"%' or endTime like '"+date+"%' or date<'"+date+" 23:59:59' AND  endTime>'"+date+" 00:00:00')");
		jpql.append(" AND isDel= "+Po.N);
		List<Kq> lst=super.list(jpql.toString());
		if(lst!=null&&lst.size()>0) {
			Set<String> st=new HashSet<String>();
			for (Kq kq : lst) {
				st.add(kq.getType());
			}
			return String.join(",", st);
		}else {
			return null;
		}
	}
}
