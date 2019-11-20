package cn.demi.res.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.dao.IApparaRecordDao;
import cn.demi.res.po.ApparaRecord;
import cn.demi.res.service.IApparaRecordService;
import cn.demi.res.vo.ApparaRecordVo;

@Service("res.apparaRecordService")
public class ApparaRecordServiceImpl extends BaseServiceImpl<ApparaRecordVo,ApparaRecord> implements IApparaRecordService {

	@Autowired
	private IApparaRecordDao apparaRecordDao;
	@Autowired
	private IApparaDao apparaDao;
	
	@Override
	public List<ApparaRecordVo> apparaRecordListByAppId(String id, String type) throws GlobalException {
		List<ApparaRecordVo> vList = new ArrayList<ApparaRecordVo>();
		List<ApparaRecord> pList = null;
		ApparaRecordVo vo = null;
		pList = apparaRecordDao.apparaRecordListByAppId(id,type);
		if(null != pList && pList.size() > 0){
			for(ApparaRecord po : pList){
				vo = new ApparaRecordVo();
				vo = super.po2Vo(po);
				vList.add(vo);
			}
		}
		return vList;
	}

	@Override
	public List<ApparaRecordVo> listOrderByDate(String type,String appId) throws GlobalException {
		List<ApparaRecordVo> vList = new ArrayList<ApparaRecordVo>();
		List<ApparaRecord> pList = null;
		ApparaRecordVo vo = null;
		pList = apparaRecordDao.listOrderByDate(type,appId);
		if(null != pList && pList.size() > 0){
			for(ApparaRecord po : pList){
				vo = new ApparaRecordVo();
				vo = super.po2Vo(po);
				vList.add(vo);
			}
		}
		return vList;
	}

	@Override
	public List<ApparaRecordVo> listByTime(String startTime, String endTime,String type)
			throws GlobalException {
		List<ApparaRecordVo> vList = new ArrayList<ApparaRecordVo>();
		List<ApparaRecord> pList = null;
		ApparaRecordVo vo = null;
		pList = apparaRecordDao.listByTime(startTime,endTime,type);
		if(null != pList && pList.size() > 0){
			for(ApparaRecord po : pList){
				vo = new ApparaRecordVo();
				vo = super.po2Vo(po);
				vList.add(vo);
			}
		}
		return vList;
	}

	@Override
	public void add(ApparaRecordVo v) throws GlobalException {
		ApparaRecord p = vo2Po(v);
		p.setAppara(apparaDao.findById(v.getApparaVo().getId()));
		apparaRecordDao.add(p);
		v.setId(p.getId());
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ApparaRecordVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		pageResult.addConditionList(toQueryList(v));
		
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		List<QueryCondition> conditions = new ArrayList<>();
		conditions.add(new QueryCondition("type", QueryCondition.EQ,v.getType()));
		pageResult.addConditionList(conditions);
		pageResult.addOrder("date", OrderCondition.ORDER_DESC);
		pageResult = apparaRecordDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ApparaRecord>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());  
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ApparaRecordVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.addOrder("date", OrderCondition.ORDER_DESC);
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		if(!StrUtils.isBlankOrNull(v.getDate())) {
			pageResult.addCondition(new QueryCondition("date like '"+v.getDate()+"%'"));
		}
		if(!StrUtils.isNull(v.getApparaVo())) {
			if(!StrUtils.isBlankOrNull(v.getApparaVo().getNo())) {
				pageResult.addCondition(new QueryCondition("appara.no like '%"+v.getApparaVo().getNo()+"%'"));
			}
			if(!StrUtils.isBlankOrNull(v.getApparaVo().getName())) {
				pageResult.addCondition(new QueryCondition("appara.name like '%"+v.getApparaVo().getName()+"%'"));
			}
		}
		 
		pageResult = apparaRecordDao.getPageResult(pageResult);
		List<ApparaRecord> recordList=(List<ApparaRecord>)pageResult.getResultList();
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for(ApparaRecord record:recordList){
			Map<String, Object> map=po2map(record);
//			String type=String.valueOf(map.get("type"));
//			if(type.equals("test")) {
//				map.put("type", "检定");
//			}else if(type.equals("calibration")) {
//				map.put("type", "校验");
//			}
			tempList.add(map);
		}
		gridVo.setDatas(tempList);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
