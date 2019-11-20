package cn.demi.init.std.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IItemMethodDao;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.ItemMethod;
import cn.demi.init.std.po.Method;
import cn.demi.init.std.service.IItemService;
import cn.demi.init.std.vo.ItemMethodVo;
import cn.demi.init.std.vo.ItemVo;
import cn.demi.init.std.vo.MethodVo;

@Service("init.itemService")
public class ItemServiceImpl extends BaseServiceImpl<ItemVo,Item> implements
		IItemService {
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private IMethodDao methodDao;
	@Autowired
	private IItemMethodDao itemMethodDao;
	@Override
	public void update(ItemVo v) throws GlobalException {
		Item p = itemDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.setName(StrUtils.getFmt4Stand(v.getName()));
		p.setUnit(StrUtils.getFmt4En(v.getUnit()));
		p.setSymbol(v.getSymbol());
		p.setRemark(v.getRemark());
		p.setHours(v.getHours());
		p.setPrice(v.getPrice());
		p.setSampTypeIds(v.getSampTypeIds());
		p.setSampTypeNames(v.getSampTypeNames());
		p.setSort(v.getSort());
		p.setType(v.getType());
		p.setCode(v.getCode());
		p.setIsNow(v.getIsNow());
		p.setFileId(v.getFileId());
		p.setFileName(v.getFileName());
		p.setFilePath(v.getFilePath());
		p.setRqIds(v.getRqIds());
		p.setRqNames(v.getRqNames());
		p.setSaveHour(v.getSaveHour());
		p.setMinVal(v.getMinVal());
		p.setMaxVal(v.getMaxVal());
		List<ItemVo> subList=v.getSubList();
		List<String> childName=new ArrayList<String>();
		if(null!=subList&&subList.size()>0) {
			for (ItemVo itemVo : subList) {
				if(null==itemVo||StrUtils.isBlankOrNull(itemVo.getName())) {
					continue;
				}
				Item sub=null;
				if(!StrUtils.isBlankOrNull(itemVo.getId())) {
					sub=itemDao.findById(itemVo.getId());
				}else {
					sub=new Item();
				}
				sub.setParent(p);
				sub.setType(p.getType());
				sub.setSampTypeIds(p.getSampTypeIds());
				sub.setSampTypeNames(p.getSampTypeNames());
				sub.setUnit(p.getUnit());
				sub.setSort(p.getSort());
				sub.setName(StrUtils.getFmt4Stand(itemVo.getName()));
				sub.setRemark(itemVo.getRemark());
				sub.setCode(itemVo.getCode());
				sub.setIsNow(v.getIsNow());
				sub.setRqIds(v.getRqIds());
				sub.setRqNames(v.getRqNames());
				sub.setSaveHour(v.getSaveHour());
				itemDao.saveOrUpdate(sub);
				childName.add(sub.getName());
			}
		}
		if(childName.size()>0) {
			p.setChildNames(String.join(",", childName));
		}else {
			p.setChildNames(null);
		}
		setMethod(p, v);
		itemDao.update(p);
	}
	@Override
	public void add(ItemVo v) throws GlobalException {
		v.setName(StrUtils.getFmt4Stand(v.getName()));
		v.setUnit(StrUtils.getFmt4En(v.getUnit()));
		Item p = vo2Po(v);
		itemDao.add(p);
		setMethod(p, v);
		itemDao.update(p);
		v.setId(p.getId());
	}
	public void setMethod(Item p,ItemVo v) {
		List<ItemMethodVo> imList=v.getMethodList();
		if(null!=imList&&imList.size()>0) {
			List<String> midList=new ArrayList<String>();
			List<String> mNameList=new ArrayList<String>();
			for (ItemMethodVo imVo : imList) {
				midList.add(imVo.getMethodVo().getId());
				Method m=methodDao.findById(imVo.getMethodVo().getId());
				if(!StrUtils.isBlankOrNull(m.getChapter())) {
					mNameList.add(m.getCode()+" "+m.getChapter());
				}else {
					mNameList.add(m.getCode());
				}
			}
			p.setStandIds(String.join(",", midList));
			p.setStandNames(String.join(",", mNameList));
		}else {
			p.setStandIds(null);
			p.setStandNames(null);
		}
	}
	@Override
	public ItemVo findById(String id) throws GlobalException {
		Item po= itemDao.findById(id);
		ItemVo vo=po2Vo(po);
		List<Item> subList=itemDao.listByPid(po.getId());
		vo.setSubList(toVoList(subList));
		return vo;
	}
	public String formatStr(String str) {
		str=str.replace("\"", "&quot;").replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
		return str;
	}
	
	
	@Override
	public List<QueryCondition> toQueryList(ItemVo v) throws GlobalException {
		List<QueryCondition> querys=new ArrayList<QueryCondition>();
		if(!StrUtils.isBlankOrNull(v.getSampTypeIds())) {
			String ids=sampTypeDao.findAllIds(v.getSampTypeIds());
			String idArr[]=ids.split(",");
			String hql="";
			for (String id : idArr) {
				if(id.trim().length()>0) {
					hql+=" sampTypeIds like '%"+id+"%' or";
				}
			}
			if(hql.length()>2) {
				hql=hql.substring(0, hql.length()-2);
				querys.add(new QueryCondition(hql));
			}
		}
		if(!StrUtils.isBlankOrNull(v.getIds())) {
			querys.add(new QueryCondition("id in('"+v.getIds().replace(",", "','")+"')"));
		}
		if(!StrUtils.isBlankOrNull(v.getIsNow())) {
			String isNow="是";
			if(v.getIsNow().equals("N")) {
				isNow="否";
			}
			querys.add(new QueryCondition("isNow='"+isNow+"'"));
		}
		if(null!=v.getParentVo()&&StrUtils.isNotBlankOrNull(v.getParentVo().getId())) {
			querys.add(new QueryCondition("parent.id ='"+v.getParentVo().getId()+"'"));
		}else {
			querys.add(new QueryCondition("parent.id is null or parent.id='' "));
		}
		return querys;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ItemVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_ASC);
			pageResult.setOrderColumn("sort");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = itemDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Item>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public void update2del(String... ids) throws GlobalException {
		List<Item> pList=itemDao.listByIds(ids);
		for(Item p:pList){
			p.setIsDel(Po.Y);
			itemDao.update(p);
			//删除方法项目关系
			List<ItemMethod> imList=itemMethodDao.listByItemId(p.getId());
			if(null!=imList) {
				for (ItemMethod im : imList) {
					im.setIsDel(Po.Y);
					itemMethodDao.update(im);
					//更新方法里的项目
					Method m=im.getMethod();
					List<String> midList = new ArrayList<String>();
					List<String> mNameList = new ArrayList<String>();
					List<ItemMethod> mList = itemMethodDao.listByMethodId(m.getId());
					if(null!=mList) {
						for (ItemMethod itemMethod : mList) {
							midList.add(itemMethod.getItem().getId());
							mNameList.add(itemMethod.getItem().getName());
						}
					}
					m.setItemIds(String.join(",", midList));
					m.setItemNames(String.join(",", mNameList));
					methodDao.update(m);
				}
			}
			//删除子项
			List<Item> itemList=itemDao.listByPid(p.getId());
			if(null!=itemList&&itemList.size()>0) {
				for (Item item : itemList) {
					item.setIsDel(Po.Y);
					itemDao.update(item);
				}
			}
		}
	}
	
	@Override
	public ItemVo findByName(String name) throws GlobalException {
		String jpql="FROM "+itemDao.getEntityName(Item.class)+" WHERE isDel='"+Po.N+"' AND name like '"+name+"'";
		List<Item> itList=itemDao.list(jpql);
		if(null!=itList&&itList.size()>0) {
			return po2Vo(itList.get(0));
		}else {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Select(GridVo gridVo, ItemVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		pageResult.addConditionList(toQueryList1(v));
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		pageResult = itemDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList1((List<Item>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4StandItem(GridVo gridVo, ItemVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.setOrder(gridVo.getSord());
		pageResult.setOrderColumn(gridVo.getSidx());
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		List<QueryCondition> querys=new ArrayList<QueryCondition>();
		//添加 vo查询项
		if(!StrUtils.isBlankOrNull(v.getSampTypeIds())) {
			List<String> pids=sampTypeDao.findAllPids(v.getSampTypeIds());
			pids.addAll(sampTypeDao.findAllSubids(v.getSampTypeIds()));
			String hql="";
			for (String id : pids) {
				if(id.trim().length()>0) {
					hql+=" sampTypeIds like '%"+id+"%' or";
				}
			}
			if(hql.length()>2) {
				hql=hql.substring(0, hql.length()-2);
				querys.add(new QueryCondition(hql));
			}
		}
		//querys.add(new QueryCondition("parent is null"));
		querys.add(new QueryCondition("childNames is null or childNames=''"));
		pageResult.addConditionList(querys);
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		pageResult = itemDao.getPageResult(pageResult);
		List<Item> plist=(List<Item>) pageResult.getResultList();
//		List<Item> rlist=new ArrayList<>();
//		for (Item item : plist) {
//			if(!StrUtils.isBlankOrNull(item.getChildNames())) {
//				rlist.addAll(getEndItem(item));
//			}else {
//				rlist.add(item);
//			}
//		}
		gridVo.setDatas(poList2mapList1(plist));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	//获取某个项目最底层子集
	public List<Item> getEndItem(Item item){
		List<Item> list=new ArrayList<Item>();
		List<Item> subList=itemDao.listByPid(item.getId());
		if(subList!=null&&subList.size()>0) {
			for (Item sub : subList) {
				if(!StrUtils.isBlankOrNull(sub.getChildNames())) {
					list.addAll(getEndItem(sub));
				}else {
					list.add(sub);
				}
			}
		}
		return list;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<?, Object>> poList2mapList1(List<Item> list) throws GlobalException{
		List tempList = new ArrayList();
		for(Item p:list){
			Map<String, Object> map = po2map(p);
			Map<String, Object> objMap =(Map<String, Object>) map.get("parentVo");
			String name =map.get("name").toString();
			if(null!=objMap) {
				map.put("name", objMap.get("name").toString()+"-"+name);
			}
			tempList.add(map);
		}
		return tempList;
	}
	public List<QueryCondition> toQueryList1(ItemVo v) throws GlobalException {
		List<QueryCondition> querys=new ArrayList<QueryCondition>();
		if(!StrUtils.isBlankOrNull(v.getSampTypeIds())) {
			String strId[]=v.getSampTypeIds().split(",");
			Set<String> idList=new HashSet<String>();
			for (String idstr : strId) {
				if(!StrUtils.isBlankOrNull(idstr)) {
					String ids=sampTypeDao.findAllIds(idstr);
					idList.addAll(Arrays.asList(ids.split(",")));
				}
			}
			String hql="";
			for (String id : idList) {
				if(id.trim().length()>0) {
					hql+=" sampTypeIds like '%"+id+"%' or";
				}
			}
			if(hql.length()>2) {
				hql=hql.substring(0, hql.length()-2);
				querys.add(new QueryCondition(hql));
			}
		}
		if(!StrUtils.isBlankOrNull(v.getIsNow())) {
			String isNow="是";
			if(v.getIsNow().equals("N")) {
				isNow="否";
			}
			querys.add(new QueryCondition("isNow='"+isNow+"'"));
		}
		if(!StrUtils.isBlankOrNull(v.getIds())) {
			querys.add(new QueryCondition("id in('"+v.getIds().replace(",", "','")+"')"));
		}
		querys.add(new QueryCondition("childNames is null or childNames=''"));
		return querys;
	}
	@Override
	public List<ItemVo> list(ItemVo v) throws GlobalException {
		String jpql="FROM "+itemDao.getEntityName(Item.class)+" WHERE isDel='"+Po.N+"' AND (parent.id is null or parent.id='') ";
		if(!StrUtils.isBlankOrNull(v.getName())) {
			jpql+=" AND name like '%"+v.getName()+"%'";		
		}
		jpql+=" order by sort asc";		
		List<Item> itList=itemDao.list(jpql);
		List<ItemVo> itVoList=new ArrayList<ItemVo>();
		if(null!=itList) {
			for (Item it : itList) {
				ItemVo itVo=new ItemVo();
				itVo=itVo.toVo(it);
				itVo.setSubList(listByPid(it.getId()));
				itVoList.add(itVo);
			}
		}
		return itVoList;
	}
	
	public List<ItemVo> listByPid(String pid) throws GlobalException {
		String jpql="FROM "+itemDao.getEntityName(Item.class)+" WHERE isDel='"+Po.N+"' AND parent.id='"+pid+"' ";
		jpql+=" order by sort asc";		
		List<Item> itList=itemDao.list(jpql);
		List<ItemVo> itVoList=new ArrayList<ItemVo>();
		if(null!=itList) {
			for (Item it : itList) {
				ItemVo itVo=new ItemVo();
				itVo=itVo.toVo(it);
				itVoList.add(itVo);
			}
		}
		return itVoList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Im(GridVo gridVo, ItemVo v) throws GlobalException {
		PageResult pageResult = new PageResult();
		pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		pageResult.addConditionList(getFilterRules(gridVo.getFilters()));
		List<QueryCondition> queryList=new ArrayList<QueryCondition>();
		//添加 vo查询项
		if(!StrUtils.isBlankOrNull(v.getSampTypeIds())) {
			String[] ids=v.getSampTypeIds().split(",");
			Set<String> idSet=new HashSet<String>();
			for (String idStr : ids) {
				if(!StrUtils.isBlankOrNull(idStr)) {
					idSet.addAll(sampTypeDao.findAllPids(idStr));
					idSet.addAll(sampTypeDao.findAllSubids(idStr));
				}
			}
			queryList.add(new QueryCondition(" sampTypeIds in('"+String.join(",", idSet).replace(",", "','")+"')"));
		}
		if(!StrUtils.isBlankOrNull(v.getSampType())) {
			String sampIds=sampTypeDao.find4Type(v.getSampType());
			queryList.add(new QueryCondition(" sampTypeIds in('"+String.join(",", sampIds).replace(",", "','")+"')"));
		}
		if(!StrUtils.isBlankOrNull(v.getIsNow())) {
			String isNow="是";
			if(v.getIsNow().equals("N")) {
				isNow="否";
			}
			queryList.add(new QueryCondition("isNow='"+isNow+"'"));
		}
		if(!StrUtils.isBlankOrNull(v.getIds())) {
			queryList.add(new QueryCondition("id not in('"+v.getIds().replace(",", "','")+"')"));
		}
		queryList.add(new QueryCondition("childNames is null or childNames=''"));
		pageResult.addConditionList(queryList);
		PageBean pageBean =	pageResult.getPageBean();
		pageBean.setCurrentPage(gridVo.getPage());
		if(0!=gridVo.getRows())
			pageBean.setPageSize(gridVo.getRows());
		pageResult.setPageBean(pageBean);
		pageResult = itemDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList1((List<Item>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<ItemVo> list4Im(String ids) throws GlobalException {
		List<Item> itList=itemDao.listByIds(ids);
		List<ItemVo> itVoList=new ArrayList<ItemVo>();
		if(null!=itList) {
			for (Item item : itList) {
				ItemVo itemVo=new ItemVo();
				itemVo=itemVo.toVo(item);
				List<ItemMethod> imList=itemMethodDao.listByItemId(item.getId());
				if(itemVo.getParentVo()!=null) {
					itemVo.setName(itemVo.getParentVo().getName()+"-"+itemVo.getName());
					imList=itemMethodDao.listByItemId(item.getParent().getId());
				}
				List<MethodVo> mlist=new ArrayList<>();
				if(null!=imList) {
					for (ItemMethod im : imList) {
						MethodVo mvo=new MethodVo();
						mvo=mvo.toVo(im.getMethod());
						mlist.add(mvo);
					}
				}
				itemVo.setMlist(mlist);
				itVoList.add(itemVo);
			}
		}
		return itVoList;
	}
	
}
