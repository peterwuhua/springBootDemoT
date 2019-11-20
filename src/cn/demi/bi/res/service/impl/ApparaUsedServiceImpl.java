package cn.demi.bi.res.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bi.res.service.IApparaUsedService;
import cn.demi.res.dao.IApparaUsedDao;
import cn.demi.res.po.ApparaUsed;
import cn.demi.res.vo.ApparaUsedVo;

@Service("bi.apparaUsedService")
public class ApparaUsedServiceImpl extends BaseServiceImpl<ApparaUsedVo,ApparaUsed> implements IApparaUsedService {
	
	@Autowired 
	private IApparaUsedDao apparaUsedDao;
	
	@Override
	public List<QueryCondition> toQueryList(ApparaUsedVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(v.getApparaVo()!=null) {
			if(!StrUtils.isBlankOrNull(v.getApparaVo().getNo())) {
				queryConditions.add(new QueryCondition("appara.no", QueryCondition.AK, v.getApparaVo().getNo()));
			}
			if(!StrUtils.isBlankOrNull(v.getApparaVo().getName())) {
				queryConditions.add(new QueryCondition("appara.name", QueryCondition.AK, v.getApparaVo().getName()));
			}
		}
		if(!StrUtils.isBlankOrNull(v.getUserName())) {
			queryConditions.add(new QueryCondition("userName", QueryCondition.AK, v.getUserName()));
		}
		if(!StrUtils.isBlankOrNull(v.getOrgName())) {
			queryConditions.add(new QueryCondition("orgName", QueryCondition.AK, v.getOrgName()));
		}
		if(!StrUtils.isBlankOrNull(v.getStartDate())) {
			queryConditions.add(new QueryCondition("endTime", QueryCondition.GE, v.getStartDate()));
		}
		if(!StrUtils.isBlankOrNull(v.getEndDate())) {
			queryConditions.add(new QueryCondition("startTime", QueryCondition.LE, v.getEndDate()));
		}
		return queryConditions;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ApparaUsedVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = apparaUsedDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ApparaUsed>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public Map<String, Object> po2map(ApparaUsed p) throws GlobalException {
		Map<String, Object> map=p.toMapProperty(p, p.getPropertyToMap());
		Object obj=map.get("duration");
		if(obj!=null) {
			Integer dt=Integer.valueOf(obj.toString());
			if(dt>60) {
				map.put("duration", dt/60+"小时"+dt%60+"分钟");
			}else {
				map.put("duration", dt+"分钟");
			}
		}
		return map;
	}
}
