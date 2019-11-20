package cn.demi.init.car.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.DateUtils;
import cn.demi.init.car.dao.ICarUseDao;
import cn.demi.init.car.po.CarUse;

@Repository("init.carUseDao")
public class CarUseDaoImpl extends BaseDaoImpl<CarUse> implements ICarUseDao {
	@Override
	public String createCode() {
		String flag=DateUtils.getYear()+DateUtils.getMonth()+DateUtils.getDay();
		String hql="SELECT max(no) FROM "+getEntityName(CarUse.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
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
	public void deleteByBusId(String busId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(CarUse.class)+" WHERE busId ='"+busId+"'").executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarUse> findByBusId(String busId) {
		String hql="FROM  "+getEntityName(CarUse.class)+" WHERE isDel='"+Po.N+"' AND busId='"+busId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}
}
