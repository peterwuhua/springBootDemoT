package cn.demi.bus.sample.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.init.st.po.SampType;
import cn.demi.init.std.po.Item;

@Repository("bus.samplingDao")
public class SamplingDaoImpl extends BaseDaoImpl<Sampling> implements ISamplingDao {

	@Override
	public List<Sampling> listByPointId(String pointId) {
		String hql="FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND point.id ='"+pointId+"'  ORDER BY sort asc";
		return super.list(hql);
	}
	@Override
	public List<Sampling> listAllByPointId(String pointId) {
		String hql="FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND point.id ='"+pointId+"' ORDER BY sampCode asc";
		return super.list(hql);
	}
	@Override
	public List<Sampling> listByTaskId(String taskId) {
		String hql="FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' AND type!='"+Sampling.SAMP_TYPE_XN+"' ORDER BY sort asc";
		return super.list(hql);
	}
	@Override
	public List<Sampling> listByP(String piontId,String p) {
		String hql="FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND point.id ='"+piontId+"' AND type!='"+Sampling.SAMP_TYPE_XN+"' AND p='"+p+"' ORDER BY sort asc";
		if(StrUtils.isBlankOrNull(p)) {
			hql="FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND point.id ='"+piontId+"' AND type!='"+Sampling.SAMP_TYPE_XN+"' AND (p=''  or p is null) ORDER BY sort asc";
		}
		return super.list(hql);
	}
	@Override
	public List<Sampling> listAllByTaskId(String taskId) {
		String hql="FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' ORDER BY sort asc";
		return super.list(hql);
	}

	@Override
	public int findMaxSort(String taskId) {
		String hql="SELECT max(sort) FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"'";
		try {
			return (int) getEntityManager().createQuery(hql).getSingleResult();
		} catch (Exception e) {
			return 0;
		}
	}
	@Override
	public void deleteByTask(String taskId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(Sampling.class)+" WHERE task.id ='"+taskId+"'").executeUpdate();
	}
	@Override
	public int findNumByPoinId(String poinId, String zkType) {
		String hql="FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND point.id ='"+poinId+"' AND zkType='"+zkType+"'";
		List<Sampling> lt=super.list(hql);
		return lt.size();
	}
	@Override
	public int maxNum4PointDay(String poinId) {
		String sql="SELECT max(c.ct) from ( select count(distinct samp_code) as ct FROM v_bus_sampling WHERE is_del='"+Po.N+"' AND point_id ='"+poinId+"' AND zk_type='"+Sampling.SAMP_TYPE_PT+"' group by cy_date) c ";
		Object n=super.queryBysql(sql).getSingleResult();
		if(null!=n) {
			return Integer.valueOf(n.toString());
		}else {
			return 0;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public String createSampCodeZw(String type,String sampType, String reportNo,String itemId, int pointSort, String pc) {
		String code=null;
		String ty="";
		String psort=pointSort<=9?"0"+pointSort:""+pointSort;
		if(sampType.equals(EnumBus.SAMP_TYPE_GW)) {
			if(type.equals(Sampling.SAMP_TYPE_XN)) {
				code=pc;
			}else {
				if(!StrUtils.isBlankOrNull(itemId)) {
					String hql="SELECT code FROM "+getEntityName(Item.class)+" WHERE isDel='"+Po.N+"' AND code is not null AND code!='' AND id in('"+itemId.replace(",", "','")+"') order by sort asc";
					List<String> list=(List<String>) super.query(hql).getResultList();
					if(null!=list && list.size()>0) {
						ty=list.get(0);
					}
				}
				if(StrUtils.isBlankOrNull(ty)||ty.equals("/")) {
					code="/";
				}else {
					code="G"+reportNo+"-"+ty+"-"+psort+"-"+pc;
				}
			}
		}else if(sampType.equals(EnumBus.SAMP_TYPE_ZW)) {
			if(type.equals(Sampling.SAMP_TYPE_XN)) {
				code=pc;
			}else {
				if(!StrUtils.isBlankOrNull(itemId)) {
					String hql="SELECT code FROM "+getEntityName(Item.class)+" WHERE isDel='"+Po.N+"' AND code is not null AND code!='' AND id in('"+itemId.replace(",", "','")+"') order by sort asc";
					List<String> list=(List<String>) super.query(hql).getResultList();
					if(null!=list && list.size()>0) {
						ty=list.get(0);
					}
				}
				if(StrUtils.isBlankOrNull(ty)||ty.equals("/")) {
					code="/";
				}else {
					code="Z"+reportNo+"-"+ty+"-"+psort+"-"+pc;
				}
			}
		}
		return code;
	}
	@Override
	public String createSampCodeHj(String reportNo, String sampTypeId, String itemId, int pointSort, String pc) {
		String code=null;
		String ty="";
		String type="";
		if(!StrUtils.isBlankOrNull(sampTypeId)) {
			SampType st=(SampType)getEntityManager().find(SampType.class,sampTypeId);
			ty=st.getTypeNo();
			type=st.getType();
		}
		if(null!=type && type.equals(Constants.SAMP_S)) {
			code=ty+pointSort;
		}else if(StrUtils.isBlankOrNull(ty)||ty.equals("/")) {
			code="/";
		}else {
			code="H"+reportNo+"-"+ty+pointSort+"-"+pc;
		}
		return code;
	}
	@Override
	public String createSampCodeZk(String reportNo, String sampTypeId, String itemId, int pointSort, String pc,String zkType) {
		String code=null;
		String ty="";
		if(!StrUtils.isBlankOrNull(sampTypeId)) {
			SampType st=(SampType)getEntityManager().find(SampType.class,sampTypeId);
			ty=st.getTypeNo();
		}
		if(StrUtils.isBlankOrNull(ty)||ty.equals("/")) {
			code="/";
		}else if(zkType.equals(Sampling.SAMP_TYPE_KB)){//空白样
			code="H"+reportNo+"-样空"+pc;
		}else if(zkType.equals(Sampling.SAMP_TYPE_PX_X)){//现场平行
			code="H"+reportNo+"-"+ty+pointSort+"-P"+pc;
		}else if(zkType.equals(Sampling.SAMP_TYPE_PX_S)){//室内平行样
			code="H"+reportNo+"-"+ty+pointSort+"-P"+pc;
		}
		return code;
	}
	@Override
	public void deleteByCyd(String cydId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(Sampling.class)+" WHERE cyd.id ='"+cydId+"'").executeUpdate();
	}
	@Override
	public List<Sampling> listByCyd(String cydId) {
		String hql="FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND cyd.id ='"+cydId+"' ORDER BY point.sort,p asc";
		return super.list(hql);
	}
	@Override
	public void deleteByPoint(String pointId) {
		getEntityManager().createQuery("DELETE FROM "+getEntityName(Sampling.class)+" WHERE point.id ='"+pointId+"'").executeUpdate();
	}
	@Override
	public List<Sampling> listByCyd(String cydId, String type) {
		String hql="FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND cyd.id ='"+cydId+"' AND type='"+type+"' ORDER BY sampCode asc";
		return super.list(hql);
	}
	@Override
	public List<Sampling> listByTaskId(String taskId, String type) {
		String hql="FROM "+getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND task.id ='"+taskId+"' AND type='"+type+"' ORDER BY cyDate,sampCode asc";
		return super.list(hql);
	}
}
