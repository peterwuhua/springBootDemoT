package cn.demi.init.sp.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.MapUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
import cn.demi.init.sp.dao.ISampPointDao;
import cn.demi.init.sp.po.SampPoint;
import cn.demi.init.sp.service.ISampPointService;
import cn.demi.init.sp.vo.SampPointVo;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;

@Service("init.samp_pointService")
public class SampPointServiceImpl extends BaseServiceImpl<SampPointVo,SampPoint> implements
		ISampPointService {
	@Autowired
	private ISampPointDao sampPointDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private IOrgDao orgDao;
	@Override
	public void add(SampPointVo v) throws GlobalException {
		SampPoint po = vo2Po(v);
		if(StrUtils.isNotBlankOrNull(v.getSampTypeId())) {
			SampType st=sampTypeDao.findById(v.getSampTypeId());
			po.setSampTypeName(st.getName());
		}
		if(StrUtils.isNotBlankOrNull(v.getSampId())) {
			SampType st=sampTypeDao.findById(v.getSampId());
			po.setSampName(st.getName());
		}
		if(!StrUtils.isBlankOrNull(v.getOrgId())) {
			Org org=orgDao.findById(v.getOrgId());
			po.setOrgId(org.getId());
			po.setOrgName(org.getName());
		}else {
			po.setOrgId(null);
			po.setOrgName(null);
		}
		sampPointDao.add(po);
		v.setId(po.getId());
	}
	@Override
	public void update(SampPointVo v) throws GlobalException {
		SampPoint sampPoint = sampPointDao.findById(v.getId());
		sampPoint=sampPoint.toPo(v, sampPoint);
		sampPoint.setType(v.getType());
		if(StrUtils.isNotBlankOrNull(v.getSampTypeId())) {
			SampType st=sampTypeDao.findById(v.getSampTypeId());
			sampPoint.setSampTypeName(st.getName());
		}
		if(StrUtils.isNotBlankOrNull(v.getSampId())) {
			SampType st=sampTypeDao.findById(v.getSampId());
			sampPoint.setSampName(st.getName());
		}
		if(!StrUtils.isBlankOrNull(v.getOrgId())) {
			Org org=orgDao.findById(v.getOrgId());
			sampPoint.setOrgId(org.getId());
			sampPoint.setOrgName(org.getName());
		}else {
			sampPoint.setOrgId(null);
			sampPoint.setOrgName(null);
		}
		sampPointDao.update(sampPoint);
		v.setId(sampPoint.getId());
	}
	@Override
	public void update2del(String... ids) throws GlobalException {
		for(SampPoint p:sampPointDao.listByIds(ids)){
			p.setIsDel(Po.Y);
			sampPointDao.update(p);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, SampPointVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult = sampPointDao.getPageResult(pageResult);
		List<SampPoint> pList = (List<SampPoint>) pageResult.getResultList();
		gridVo.setDatas(poList2mapList(pList));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<Map<?, Object>> poList2mapList(List<SampPoint> list) throws GlobalException{
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for(SampPoint p:list){
			tempList.add(po2map(p));
		}
		return tempList;
	}
	/**
	 * po to voMap
	 */
	@Override
	public Map<String,Object> po2map(SampPoint p) throws GlobalException{
		SampPointVo v=po2Vo(p);
		return MapUtils.convertBean2(v, p.getPropertyToMap());
	}
	@Override
	public List<SampPointVo> list(SampPointVo v,GridVo gridVo) throws GlobalException {
		List<QueryCondition> queryConditions = getFilterRules(gridVo.getFilters());
		if(queryConditions==null) {
			queryConditions=new ArrayList<QueryCondition>();
		}
		if(StrUtils.isBlankOrNull(v.getOrgId())) {
			queryConditions.add(new QueryCondition("orgId is null or orgId='"+getCurrent().getOrgId()+"'"));
		}else {
			queryConditions.add(new QueryCondition("orgId is null or orgId='"+v.getOrgId()+"'"));
		}
		if(v.getSampTypeId()!=null) {
			queryConditions.add(new QueryCondition("sampTypeId in('"+v.getSampTypeId().replace(",", "','")+"')"));
		}
		List<SampPointVo> list=super.list(queryConditions);
		return list;
	}
	@Override
	public boolean checkName(String name,String sampId) throws GlobalException{
		String jpql="FROM "+sampPointDao.getEntityName(SampPoint.class)+" WHERE isDel='"+Po.N
				+"' AND name like '"+name+"' AND sampId='"+sampId+"'";
		List<SampPoint> ls=sampPointDao.list(jpql);
		if(ls!=null&&ls.size()>0) {
			return false;
		}else {
			return true;
		}
	}
}
