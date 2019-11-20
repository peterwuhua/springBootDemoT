package cn.demi.bus.pjt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.po.Im;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.Method;

@Repository("bus.imDao")
public class ImDaoImpl extends BaseDaoImpl<Im> implements IImDao {

	@Override
	public void deleteByBusId(String busId) {
		String hql="DELETE FROM  "+getEntityName(Im.class)+" WHERE busId='"+busId+"' ";
		getEntityManager().createQuery(hql).executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Im> listByBusId(String busId) {
		String hql="FROM  "+getEntityName(Im.class)+" WHERE busId='"+busId+"' order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@Override
	public Im findByBusIdAndItemId(String busId, String itemId) {
		String hql="FROM  "+getEntityName(Im.class)+" WHERE busId='"+busId+"' AND itemId='"+itemId+"' ";
		@SuppressWarnings("unchecked")
		List<Im> l=getEntityManager().createQuery(hql).getResultList();
		if(null!=l&&l.size()>0) {
			return l.get(0);
		}else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findByBusId(String busId) {
		String sql="select concat(item_id,'-',method_id) from v_bus_im where bus_id='"+busId+"' order by sort asc";
		List<String> l=super.queryBySql(sql);
		if(l!=null&&l.size()>0) {
			return String.join(";", l);
		}else {
			return null;
		}
	}

	@Override
	public void uptIm(String busId, String imStr) {
		deleteByBusId(busId);
		if(!StrUtils.isBlankOrNull(imStr)) {
			String imArr[]=imStr.split(";");
			int n=0;
			for (String i : imArr) {
				if(!StrUtils.isBlankOrNull(i)&&i.contains("-")) {
					String m[]=i.split("-");
					if(m.length==2&&m[0].trim().length()>0) {
						Im im=new Im();
						im.setBusId(busId);
						Item item=(Item)super.getEntityManager().find(Item.class,m[0].trim());
						im.setItemId(item.getId());
						im.setItemName(item.getName());
						im.setPrice(item.getPrice());
						if(!StrUtils.isBlankOrNull(m[1].trim())&&!m[1].equals("null")) {
							Method method=(Method)super.getEntityManager().find(Method.class,m[1].trim());
							im.setMethodId(method.getId());
							String code=method.getCode();
							if(!StrUtils.isBlankOrNull(method.getChapter())) {
								code+=method.getChapter();
							}
							im.setMethodName(code+method.getName());
						}
						im.setUserId(getCurrent().getAccountId());
						im.setUserName(getCurrent().getUserName());
						im.setDate(DateUtils.getCurrDateTimeStr());
						im.setSort(n);
						add(im);
						n++;
					}
				}
			}
		}
	}

	@Override
	public void uptIm4App(String busId, String imStr,ObjVo objVo) {
		deleteByBusId(busId);
		if(!StrUtils.isBlankOrNull(imStr)) {
			String imArr[]=imStr.replaceAll(",", ";").split(";");
			int n=0;
			for (String i : imArr) {
				if(!StrUtils.isBlankOrNull(i)&&i.contains("-")) {
					String m[]=i.split("-");
					if(m.length==2&&m[0].trim().length()>0) {
						Im im=new Im();
						im.setBusId(busId);
						Item item=(Item)super.getEntityManager().find(Item.class,m[0].trim());
						im.setItemId(item.getId());
						im.setItemName(item.getName());
						im.setPrice(item.getPrice());
						if(!StrUtils.isBlankOrNull(m[1].trim())&&!m[1].equals("null")) {
							Method method=(Method)super.getEntityManager().find(Method.class,m[1].trim());
							im.setMethodId(method.getId());
							im.setMethodName(method.getName());
						}
						im.setUserId(objVo.getId());
						im.setUserName(objVo.getName());
						im.setDate(DateUtils.getCurrDateTimeStr());
						im.setSort(n);
						add(im);
						n++;
					}
				}
			}
		}
	}
}
