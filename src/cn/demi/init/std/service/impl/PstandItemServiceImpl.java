package cn.demi.init.std.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IPstandItemDao;
import cn.demi.init.std.dao.IPstandardDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.PstandItem;
import cn.demi.init.std.po.Pstandard;
import cn.demi.init.std.service.IPstandItemService;
import cn.demi.init.std.vo.PstandItemVo;

@Service("init.pstand_itemService")
public class PstandItemServiceImpl extends BaseServiceImpl<PstandItemVo,PstandItem> implements
		IPstandItemService {
	@Autowired
	private IPstandardDao pstandardDao;
	@Autowired
	private IPstandItemDao pstandItemDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private IItemDao itemDao;
	@Override
	public List<QueryCondition> toQueryList(PstandItemVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(v.getStandId()!=null){
			queryConditions.add(new QueryCondition("standId", QueryCondition.EQ,v.getStandId()));
		}
		return queryConditions;
	}
	@Override
	public List<PstandItemVo> listByStandId(PstandItemVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("standId", QueryCondition.EQ,v.getStandId()));
		if(null!=v.getSampTypeId()) {
			queryConditions.add(new QueryCondition("sampTypeId", QueryCondition.EQ,v.getSampTypeId()));
		}
		if(null!=v.getType()) {
			queryConditions.add(new QueryCondition("type", QueryCondition.EQ,v.getType()));
		}
		if(null!=v.getItemType()) {
			queryConditions.add(new QueryCondition("itemType", QueryCondition.EQ,v.getItemType()));
		}
		return super.list(queryConditions);
	}
	@Override
	public boolean savePstandItem(PstandItemVo v) throws GlobalException {
		List<Item> itemList=itemDao.listByIds(v.getIds());
		Pstandard s=pstandardDao.findById(v.getStandId());
		try {
			if(null!=itemList &&itemList.size()>0) {
				for (Item item : itemList) {
					PstandItem po=new PstandItem();
					po.setStandId(v.getStandId());
					po.setStandName(s.getName());
					po.setCode(s.getCode());
					po.setSampTypeId(v.getSampTypeId());
					po.setSampTypeName(v.getSampTypeName());
					po.setItem(item);
					po.setType(v.getType());
					po.setItemType(v.getItemType());
					po.setOtherType(v.getOtherType());
					po.setXzType(v.getXzType());
					String valStr=null;
					if(v.getXzType().equals(PstandItem.FLAG_FW)) {
						if(StrUtils.isNotBlankOrNull(v.getMinValue())&& StrUtils.isNotBlankOrNull(v.getMaxValue())) {
							valStr=v.getMinValue()+"~"+v.getMaxValue();
						}else if(StrUtils.isNotBlankOrNull(v.getMinValue())) {
							valStr=v.getMinFlag()+v.getMinValue();
						}else if(StrUtils.isNotBlankOrNull(v.getMaxValue())) {
							valStr=v.getMaxFlag()+v.getMaxValue();
						}
						po.setMaxFlag(v.getMaxFlag());
						po.setMaxValue(v.getMaxValue());
						po.setMinFlag(v.getMinFlag());
						po.setMinValue(v.getMinValue());
					}else if(v.getXzType().equals(PstandItem.FLAG_SZ)) {
						valStr=v.getValue();
						po.setFlag(v.getFlag());
						po.setValue(v.getValue());
					}else if(v.getXzType().equals(PstandItem.FLAG_MS)) {
						valStr=v.getValue();
						po.setValue(v.getValue());
					}
//					if(StrUtils.isNotBlankOrNull(v.getOtherType())) {
//						valStr+="("+v.getOtherType()+")";
//					}
					po.setValStr(valStr);
					//气
					po.setValue2(v.getValue2());
					po.setFlag2(v.getFlag2());
					if(!StrUtils.isBlankOrNull(v.getValue2())&&!StrUtils.isBlankOrNull(v.getFlag2())) {
						if(v.getFlag2().equals("描述")) {
							po.setValStr2(v.getValue2());
						}else {
							po.setValStr2(v.getFlag2()+v.getValue2());
						}
					}else {
						po.setValStr2(null);
					}
					po.setSort(pstandItemDao.getMaxSort(v.getStandId())+1);
					pstandItemDao.add(po);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	@Override
	public boolean checkPstandItem(PstandItemVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(!StrUtils.isBlankOrNull(v.getId())) {
			PstandItem p=pstandItemDao.findById(v.getId());
			queryConditions.add(new QueryCondition("id", QueryCondition.NE,p.getId()));
		}
		queryConditions.add(new QueryCondition("standId", QueryCondition.EQ,v.getStandId()));
		queryConditions.add(new QueryCondition("sampTypeId", QueryCondition.EQ,v.getSampTypeId()));
		queryConditions.add(new QueryCondition("item.id", QueryCondition.EQ,v.getItemVo().getId()));
		queryConditions.add(new QueryCondition("type", QueryCondition.EQ,v.getType()));
		List<PstandItem> pList = pstandItemDao.list(queryConditions,null,-1,-1);
		if(pList!=null&&pList.size()>0) {
			return false;
		}else {
			return true;
		}
	}
	public void recursiveSampType(SampType st,Set<String> idSet) {
		if(st!=null&&st.getParent()!=null) {
			idSet.add(st.getId());
			recursiveSampType(st.getParent(), idSet);
		}
	}
	@Override
	public List<PstandItemVo> list(PstandItemVo v) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if(!StrUtils.isBlankOrNull(v.getSampTypeId())) {
			String[] ids=v.getSampTypeId().split(",");
			String sql="";
			Set<String> idSet=new HashSet<String>();
			for (int i=0;i<ids.length;i++) {
				SampType st= sampTypeDao.findById(ids[i]);
				recursiveSampType(st, idSet);
			}
			sql+="sampTypeId in ('"+StringUtils.join(idSet.toArray(), "','")+"')";
			queryConditions.add(new QueryCondition(sql));
		}
		if(!StrUtils.isBlankOrNull(v.getType())) {
			queryConditions.add(new QueryCondition("type", QueryCondition.EQ,v.getType()));
		}
		if(!StrUtils.isBlankOrNull(v.getItemType())) {
			queryConditions.add(new QueryCondition("itemType", QueryCondition.EQ,v.getItemType()));
		}
		if(!StrUtils.isBlankOrNull(v.getStandId())){
			queryConditions.add(new QueryCondition("standId", QueryCondition.EQ,v.getStandId()));
		}
		if(!StrUtils.isBlankOrNull(v.getCode())){
			queryConditions.add(new QueryCondition("code", QueryCondition.EQ,v.getCode()));
		}
		return super.list(queryConditions);
	}
	@Override
	public List<PstandItemVo> listItem(GridVo gridVo, PstandItemVo v) throws GlobalException {
		List<QueryCondition> queryConditions =getFilterRules(gridVo.getFilters());
		if(null==queryConditions) {
			queryConditions=new ArrayList<QueryCondition>();
		}
		if(!StrUtils.isBlankOrNull(v.getSampTypeId())) {
			String[] ids=v.getSampTypeId().split(",");
			String sql="";
			Set<String> idSet=new HashSet<String>();
			for (int i=0;i<ids.length;i++) {
				SampType st= sampTypeDao.findById(ids[i]);
				recursiveSampType(st, idSet);
			}
			sql+="sampTypeId in ('"+StringUtils.join(idSet.toArray(), "','")+"')";
			queryConditions.add(new QueryCondition(sql));
		}
		if(!StrUtils.isBlankOrNull(v.getType())) {
			queryConditions.add(new QueryCondition("type", QueryCondition.EQ,v.getType()));
		}
		if(!StrUtils.isBlankOrNull(v.getItemType())) {
			queryConditions.add(new QueryCondition("itemType", QueryCondition.EQ,v.getItemType()));
		}
		if(!StrUtils.isBlankOrNull(v.getStandId())){
			queryConditions.add(new QueryCondition("standId", QueryCondition.EQ,v.getStandId()));
		}
		if(null!=v.getItemVo()&&!StrUtils.isBlankOrNull(v.getItemVo().getId())){
			queryConditions.add(new QueryCondition("item.id", QueryCondition.EQ,v.getItemVo().getId()));
		}
		if(!StrUtils.isBlankOrNull(v.getCode())){
			queryConditions.add(new QueryCondition("code", QueryCondition.EQ,v.getCode()));
		}
		return super.list(queryConditions);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, PstandItemVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
//			if(null!=v.getSampType()&&(v.getSampType().equals(EnumBus.SAMP_TYPE_ZW)
//					||v.getSampType().equals(EnumBus.SAMP_TYPE_GW))) {
				pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
//			}else{
//				pageResult.addOrder("type", OrderCondition.ORDER_ASC);
//				pageResult.addOrder("itemType", OrderCondition.ORDER_ASC);
//			}
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = pstandItemDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<PstandItem>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<?, Object>> poList2mapList(List<PstandItem> list) throws GlobalException{
		List tempList = new ArrayList();
		for(PstandItem p:list){
			Map<String, Object> map = po2map(p);
			Map<String, Object> itemMap =(Map<String, Object>) map.get("itemVo");
			Map<String, Object> objMap =(Map<String, Object>) itemMap.get("parentVo");
			String name =itemMap.get("name").toString();
			if(null!=objMap) {
				itemMap.put("name", objMap.get("name").toString()+"-"+name);
				map.put("itemVo", itemMap);
			}
			tempList.add(map);
		}
		return tempList;
	}
	@Override
	public List<PstandItemVo> listItem4Cy(PstandItemVo v) throws GlobalException {
		//String sampTypeIds=sampTypeDao.findAllIds(v.getSampTypeId());
		List<PstandItem> pitList=pstandItemDao.listStand4Item(v.getStandId(), v.getItemVo().getId(), v.getItemType());
		Set<String> valSet=new HashSet<>();
		List<PstandItemVo> psdVoList=new ArrayList<>();
		for (PstandItem pit : pitList) {
			if(!valSet.contains(pit.getValStr())) {
				PstandItemVo pitVo=new PstandItemVo();
				pitVo=pitVo.toVo(pit);
				psdVoList.add(pitVo);
				valSet.add(pit.getValStr());
			}
		}
		return psdVoList;
	}
	@Override
	public void update4Grid(PstandItemVo v) throws GlobalException {
		PstandItem po = pstandItemDao.findById(v.getId());
		po.setSampTypeId(v.getSampTypeId());
		po.setSampTypeName(v.getSampTypeName());
		po.setItemType(v.getItemType());
		po.setType(v.getType());
		po.setOtherType(v.getOtherType());
		po.setXzType(v.getXzType());
		String valStr=null;
		if(v.getXzType().equals(PstandItem.FLAG_FW)) {
			if(StrUtils.isNotBlankOrNull(v.getMinValue())&& StrUtils.isNotBlankOrNull(v.getMaxValue())) {
				valStr=v.getMinValue()+"~"+v.getMaxValue();
			}else if(StrUtils.isNotBlankOrNull(v.getMinValue())) {
				valStr=v.getMinFlag()+v.getMinValue();
			}else if(StrUtils.isNotBlankOrNull(v.getMaxValue())) {
				valStr=v.getMaxFlag()+v.getMaxValue();
			}
			po.setMaxFlag(v.getMaxFlag());
			po.setMaxValue(v.getMaxValue());
			po.setMinFlag(v.getMinFlag());
			po.setMinValue(v.getMinValue());
		}else if(v.getXzType().equals(PstandItem.FLAG_SZ)) {
			valStr=v.getValue();
			po.setFlag(v.getFlag());
			po.setValue(v.getValue());
		}else if(v.getXzType().equals(PstandItem.FLAG_MS)) {
			valStr=v.getValue();
			po.setValue(v.getValue());
		}
//		if(StrUtils.isNotBlankOrNull(v.getOtherType())) {
//			valStr+="("+v.getOtherType()+")";
//		}
		po.setValStr(valStr);
		//气
		po.setValue2(v.getValue2());
		po.setFlag2(v.getFlag2());
		if(!StrUtils.isBlankOrNull(v.getValue2())&&!StrUtils.isBlankOrNull(v.getFlag2())) {
			if(v.getFlag2().equals("描述")) {
				po.setValStr2(v.getValue2());
			}else {
				po.setValStr2(v.getFlag2()+v.getValue2());
			}
		}else {
			po.setValStr2(null);
		}
		po.setSort(v.getSort());
		pstandItemDao.update(po);
	}
	@Override
	public boolean savePstandItem4Zw(PstandItemVo v) throws GlobalException {
		List<Item> itemList=itemDao.listByIds(v.getIds());
		Pstandard s=pstandardDao.findById(v.getStandId());
		try {
			if(null!=itemList &&itemList.size()>0) {
				for (Item item : itemList) {
					PstandItem po=new PstandItem();
					po.setStandId(v.getStandId());
					po.setStandName(s.getName());
					po.setCode(s.getCode());
					po.setSampTypeId(v.getSampTypeId());
					po.setSampTypeName(v.getSampTypeName());
					po.setItem(item);
					po.setType(v.getType());
					po.setOtherType(v.getOtherType());
					po.setValue3(v.getValue3());
					po.setValue(v.getValue());
					po.setValue2(v.getValue2());
					po.setSort(pstandItemDao.getMaxSort(v.getStandId())+1);
					po.setMaxValue(getBs(item.getName(), v.getValue()));
					pstandItemDao.add(po);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	@Override
	public void update4Grid4Zw(PstandItemVo v) throws GlobalException {
		PstandItem po = pstandItemDao.findById(v.getId());
		po.setSampTypeId(v.getSampTypeId());
		po.setSampTypeName(v.getSampTypeName());
		po.setItemType(v.getItemType());
		po.setType(v.getType());
		po.setOtherType(v.getOtherType());
		po.setValue(v.getValue());
		po.setValue2(v.getValue2());
		po.setValue3(v.getValue3());
		po.setMaxValue(v.getMaxValue());
		po.setSort(v.getSort());
		po.setMaxValue(getBs(po.getItem().getName(), v.getValue()));
		pstandItemDao.update(po);
	}
	//计算超限倍数
	public String getBs(String itemName,String twa) {
		//粉尘              超限倍数：2
		//其他
		//PC-TWA＜1       2.5
		//1≤PC-TWA＜100   2.0
		//10≤PC-TWA＜100  1.5
		//PC-TWA≥100	  3
		String bs=null;
		try {
			if(itemName.contains("粉尘")) {
				bs=String.valueOf(2);
			}else if(!StrUtils.isBlankOrNull(twa)) {
				double d=Double.valueOf(twa);
				if(d<1) {
					bs=String.valueOf(3);
				}else if(d>=1&&d<10) {
					bs=String.valueOf(2.5);
				}else if(d>=10&&d<100) {
					bs=String.valueOf(2);
				}else if(d>=100) {
					bs=String.valueOf(1.5);
				}
			}
		} catch (NumberFormatException e) {
			bs=null;
		}
		return bs;
	}
}
