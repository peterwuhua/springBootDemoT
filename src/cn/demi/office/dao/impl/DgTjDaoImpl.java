package cn.demi.office.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.demi.office.dao.IDgTjDao;
import cn.demi.office.po.DgTj;
import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;

@Repository("office.dgTjDao")
public class DgTjDaoImpl extends BaseDaoImpl<DgTj> implements IDgTjDao {

	@Override
	public void updDgTj(String userId, String date) {
		if(!StrUtils.isBlankOrNull(date)&&!StrUtils.isBlankOrNull(userId)) {
			String dt=date.substring(0, 10);
			StringBuffer jpql = new StringBuffer("FROM "+getEntityName(DgTj.class));
			jpql.append(" WHERE userId='"+userId+"'");
			jpql.append(" AND date='"+dt+"'");
			jpql.append(" AND isDel= "+Po.N);
			List<DgTj> lst=super.list(jpql.toString());
			if(lst!=null&&lst.size()>0) {
				DgTj tj=lst.get(0);
				String status=tj.getStatus();
				String stime=tj.getStime();
				String etime=tj.getEtime();
				if(status.equals(DgTj.ST_0)){
					tj.setStime(date);
					tj.setEtime(date);
					tj.setStatus(DgTj.ST_1);
				}else {
					if(DateUtils.getIntevalMinutes(date, stime)>0) {
						tj.setStime(date);
					}
					if(DateUtils.getIntevalMinutes(etime, date)>0) {
						tj.setEtime(date);
					}
				}
				getEntityManager().merge(tj);
			}
		}
	}
	@Override
	public void updDgTj(String userId, String startTime,String endTime,String state) {
		if(!StrUtils.isBlankOrNull(userId)) {
			StringBuffer jpql = new StringBuffer("FROM "+getEntityName(DgTj.class));
			jpql.append(" WHERE userId='"+userId+"'");
			jpql.append(" AND date>='"+startTime.substring(0, 10)+"' AND date<='"+endTime.substring(0, 10)+"'");
			jpql.append(" AND isDel= "+Po.N);
			List<DgTj> lst=super.list(jpql.toString());
			if(lst!=null&&lst.size()>0) {
				for (DgTj tj : lst) {
					String status=tj.getStatus();
					if(status.equals(DgTj.ST_0)) {
						tj.setStatus(state);
					}else if(!status.contains(state)){
						tj.setStatus(status+","+state);
					}
					getEntityManager().merge(tj);
				}
			}
		}
	}
	@Override
	public void insertDgTj(DgTj p) {
		StringBuffer jpql = new StringBuffer("FROM "+getEntityName(DgTj.class));
		jpql.append(" WHERE userId='"+p.getUserId()+"'");
		jpql.append(" AND date='"+p.getDate()+"'");
		jpql.append(" AND isDel= "+Po.N);
		List<DgTj> lst=super.list(jpql.toString());
		if(lst!=null&&lst.size()>0) {
			DgTj tj=lst.get(0);
			tj.setStime(p.getStime());
			tj.setEtime(p.getEtime());
			tj.setStatus(p.getStatus());
			getEntityManager().merge(tj);
		}else {
			getEntityManager().persist(p);
		}
	}
}
