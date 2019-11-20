package cn.demi.init.std.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.std.dao.IPstandItemDao;
import cn.demi.init.std.po.PstandItem;

@Repository("init.pstand_itemDao")
public class PstandItemDaoImpl extends BaseDaoImpl<PstandItem> implements IPstandItemDao {

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<PstandItem> listStand4Item(String sampTypeIds, String standIds, String itemId) {
//		String hql="FROM "+getEntityName(PstandItem.class)+" WHERE isDel='"+Po.N
//				+"' AND sampTypeId in('"+sampTypeIds.replace(",", "','")+"') AND standId in('"+standIds.replace(",", "','")+"') AND item.id ='"+itemId+"'";
//		hql+=" ORDER by minValue desc,maxValue asc";
//		return getEntityManager().createQuery(hql).getResultList();
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PstandItem> listStand4Item(String standIds, String itemId, String itemType) {
		String hql="FROM "+getEntityName(PstandItem.class)+" WHERE isDel='"+Po.N
				+"'  AND standId in('"+standIds.replace(",", "','")+"') AND item.id ='"+itemId+"'";
		if(!StrUtils.isBlankOrNull(itemType)) {
			hql+=" AND itemType='"+itemType+"'";
		}
		hql+=" order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PstandItem> listStand4Item(String sampTypeIds, String standIds, String itemId, String itemType, String nd, String pqtgd) {
		String hql="FROM "+getEntityName(PstandItem.class)+" WHERE isDel='"+Po.N
				+"' AND sampTypeId in('"+sampTypeIds.replace(",", "','")+"') AND standId in('"+standIds.replace(",", "','")+"') AND item.id ='"+itemId+"'";
		if(!StrUtils.isBlankOrNull(itemType)) {
			hql+=" AND itemType='"+itemType+"'";
		}
		hql+=" AND limitLine='"+nd+"'";
		hql+=" AND limitLine3>='"+pqtgd+"'";
		hql+=" order by limitLine3 asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PstandItem> listStand4Item(String standIds, String itemId) {
		String hql="FROM "+getEntityName(PstandItem.class)+" WHERE isDel='"+Po.N
				+"' AND standId in('"+standIds.replace(",", "','")+"') AND item.id ='"+itemId+"'";
		hql+=" order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PstandItem findStand(String sampTypeIds, String standIds, String itemId, String limited) {
		String hql="FROM "+getEntityName(PstandItem.class)+" WHERE isDel='"+Po.N
				+"' AND sampTypeId in('"+sampTypeIds.replace(",", "','")+"') AND standId in('"+standIds.replace(",", "','")+"') AND item.id ='"+itemId+"'";
		hql+=" AND valStr like '%"+limited+"%'";
		hql+=" ORDER by minValue desc,maxValue asc";
		List<PstandItem> plist= getEntityManager().createQuery(hql).getResultList();
		if(plist!=null&&plist.size()>0) {
			return plist.get(0);
		}else {
			return null;
		}
	}
	@Override
	public boolean check(PstandItem pi, String value) {
		boolean b=true;
		if(pi.getXzType().equals(PstandItem.FLAG_MS)&&!StrUtils.isBlankOrNull(value)&&!value.equals(pi.getValue())) {
			b=false;
		}else if(pi.getXzType().equals(PstandItem.FLAG_SZ)&&!StrUtils.isBlankOrNull(value)&&!value.equals(pi.getValue())) {
			b=false;
		}else if(pi.getXzType().equals(PstandItem.FLAG_FW)) {
			try {
				float v=Float.valueOf(value);
				if(value.equals("NaN")) {
					b=false;
				}else if(!StrUtils.isBlankOrNull(pi.getMaxValue())&&!StrUtils.isBlankOrNull(pi.getMinValue())) {
					float maxVal=Float.valueOf(pi.getMaxValue());
					float minVal=Float.valueOf(pi.getMinValue());
					if(v>maxVal||v<minVal) {
						b=false;
					}
				}else if(!StrUtils.isBlankOrNull(pi.getMaxValue())) {
					float maxVal=Float.valueOf(pi.getMaxValue());
					if(v>maxVal) {
						b=false;
					}
				}else if(!StrUtils.isBlankOrNull(pi.getMinValue())) {
					float minVal=Float.valueOf(pi.getMinValue());
					if(v<minVal) {
						b=false;
					}
				}
			} catch (NumberFormatException e) {
				b=false;
			}
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PstandItem> listStand(String standIds, String itemId, String varStr) {
		String hql="FROM "+getEntityName(PstandItem.class)+" WHERE isDel='"+Po.N
				+"' AND standId in('"+standIds.replace(",", "','")+"') AND item.id ='"+itemId+"'";
		hql+=" AND valStr like '%"+varStr+"%'";
		hql+=" order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}

	@Override
	public boolean check(String varStr, String value) {
		boolean f=true;
		if(varStr.startsWith("<")) {
			double d1=StrUtils.findNum4Str(varStr);
			double d2=StrUtils.findNum4Str(value);
			if(d1<=d2) {
				f=false;
			}
		}else if(varStr.startsWith("≤")||varStr.startsWith("<=")) {
			double d1=StrUtils.findNum4Str(varStr);
			double d2=StrUtils.findNum4Str(value);
			if(d1<d2) {
				f=false;
			}
		}else if(varStr.startsWith(">")) {
			double d1=StrUtils.findNum4Str(varStr);
			double d2=StrUtils.findNum4Str(value);
			if(d1>=d2) {
				f=false;
			}
		}else if(varStr.startsWith("≥")||varStr.startsWith(">=")) {
			double d1=StrUtils.findNum4Str(varStr);
			double d2=StrUtils.findNum4Str(value);
			if(d1>d2) {
				f=false;
			}
		}else if(varStr.contains("~")) {
			String[] ss=varStr.split("~");
			if(ss.length==2) {
				if(!StrUtils.isBlankOrNull(ss[0])) {
					double d1=StrUtils.findNum4Str(ss[0]);
					double d2=StrUtils.findNum4Str(value);
					if(d1>d2) {
						f=false;
					}
				}
				if(!StrUtils.isBlankOrNull(ss[1])) {
					double d1=StrUtils.findNum4Str(ss[1]);
					double d2=StrUtils.findNum4Str(value);
					if(d1<d2) {
						f=false;
					}
				}
			}
		}
		return f;
	}

	@Override
	public int getMaxSort(String standId) {
		StringBuffer jpql = new StringBuffer("SELECT MAX(sort) FROM "
				+ getEntityName(PstandItem.class)+" WHERE isDel="+Po.N +" AND standId='"+standId+"' ");
		Object obj = query(jpql.toString()).getSingleResult();
		int maxSort = 0;
		if(null != obj){
			maxSort = (Integer)obj;
		}
		return maxSort;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PstandItem> listStand(String standIds, String itemId, String itemType, String type, String otherType) {
		String hql="FROM "+getEntityName(PstandItem.class)+" WHERE isDel='"+Po.N
				+"' AND standId in('"+standIds.replace(",", "','")+"') AND item.id ='"+itemId+"'";
		if(!StrUtils.isBlankOrNull(itemType)) {
			hql+=" AND (itemType like '%"+itemType+"%' or otherType like '%"+itemType+"%' or type like '%"+itemType+"%')";
		}
		if(!StrUtils.isBlankOrNull(type)) {
			hql+=" AND (itemType like '%"+type+"%' or otherType like '%"+type+"%' or type like '%"+type+"%')";
		}
		if(!StrUtils.isBlankOrNull(otherType)) {
			hql+=" AND (itemType like '%"+otherType+"%' or otherType like '%"+otherType+"%' or type like '%"+otherType+"%')";
		}
		hql+=" order by sort asc";
		return getEntityManager().createQuery(hql).getResultList();
	}
}
