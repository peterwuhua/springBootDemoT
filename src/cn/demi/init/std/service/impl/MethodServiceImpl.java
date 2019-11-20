package cn.demi.init.std.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IItemMethodDao;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.ItemMethod;
import cn.demi.init.std.po.Method;
import cn.demi.init.std.service.IMethodService;
import cn.demi.init.std.vo.MethodVo;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.po.Appara;

@Service("init.methodService")
public class MethodServiceImpl extends BaseServiceImpl<MethodVo,Method> implements
		IMethodService {
	@Autowired
	private IMethodDao methodDao;
	@Autowired
	private IItemMethodDao itemMethodDao;
//	@Autowired
//	private IMstandardDao mstandardDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private IApparaDao apparaDao;
	@Override
	public List<QueryCondition> toQueryList(MethodVo v) throws GlobalException{
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
//		if(StrUtils.isNotBlankOrNull(v.getSampTypeIds())){
//			String idStr[]=v.getSampTypeIds().split(",");
//			String ids="";
//			for (String id : idStr) {
//				if(id.trim().length()==0) {
//					continue;
//				}
//				ids+=sampTypeDao.findAllIds(id)+",";
//			}
//			queryConditions.add(new QueryCondition("sampTypeIds in('"+ids.replace(",", "','")+"') or sampTypeIds is null"));
//		}
		return queryConditions;
	}
	
	@Override
	public void add(MethodVo v) throws GlobalException {
		Method p = vo2Po(v);
		p.setCode(StrUtils.getFmt4Stand(v.getCode()));
		p.setName(StrUtils.getFmt4Stand(v.getName()));
		String limtLine=null;
		if(!StrUtils.isBlankOrNull(v.getMinLine())&&!StrUtils.isBlankOrNull(v.getMaxLine())) {
			String ml=v.getMinLine().trim();
			String xl=v.getMaxLine().trim();
			if(ml.contains(">")) {
				ml=ml.replace(">", "");
			}else if(ml.contains(">=")) {
				ml=ml.replace(">=", "");
			}else if(ml.contains("≥")) {
				ml=ml.replace("≥", "");
			}
			if(xl.contains("<")) {
				xl=xl.replace("<", "");
			}else if(xl.contains("<=")) {
				xl=xl.replace("<=", "");
			}else if(xl.contains("≤")) {
				xl=xl.replace("≤", "");
			}
			limtLine=ml+" ~ "+xl;
		}else if(!StrUtils.isBlankOrNull(v.getMinLine())) {
			limtLine=v.getMinLine().trim();
		}else if(!StrUtils.isBlankOrNull(v.getMaxLine())) {
			limtLine=v.getMaxLine().trim();
		}
		p.setLimitLine(limtLine);
		methodDao.add(p);
		saveItemMethod(p,null);
		v.setId(p.getId());
	}
	@Override
	public void update(MethodVo v) throws GlobalException {
		Method p = methodDao.findById(v.getId());
		String itemIds=p.getItemIds();
		p.toPo(v, p);
		p.setCode(StrUtils.getFmt4Stand(v.getCode()));
		p.setName(StrUtils.getFmt4Stand(v.getName()));
		String limtLine=null;
		if(!StrUtils.isBlankOrNull(v.getMinLine())&&!StrUtils.isBlankOrNull(v.getMaxLine())) {
			String ml=v.getMinLine().trim();
			String xl=v.getMaxLine().trim();
			if(ml.contains(">")) {
				ml=ml.replace(">", "");
			}else if(ml.contains(">=")) {
				ml=ml.replace(">=", "");
			}else if(ml.contains("≥")) {
				ml=ml.replace("≥", "");
			}
			if(xl.contains("<")) {
				xl=xl.replace("<", "");
			}else if(xl.contains("<=")) {
				xl=xl.replace("<=", "");
			}else if(xl.contains("≤")) {
				xl=xl.replace("≤", "");
			}
			limtLine=ml+" ~ "+xl;
		}else if(!StrUtils.isBlankOrNull(v.getMinLine())) {
			limtLine=v.getMinLine().trim();
		}else if(!StrUtils.isBlankOrNull(v.getMaxLine())) {
			limtLine=v.getMaxLine().trim();
		}
		p.setLimitLine(limtLine);
		methodDao.update(p);
		saveItemMethod(p,itemIds);
	}
	public void saveItemMethod(Method p,String oldItems) {
		//若方法里未关联项目Id，根据方法Id清空项目方法关系表
		if(StrUtils.isBlankOrNull(p.getItemIds())) {
			itemMethodDao.deleteByMethodId(p.getId());
		}else if(!StrUtils.isBlankOrNull(oldItems)) {//清除已取消的项目Id 删除项目方法关系
			String itemIds[]=oldItems.split(",");
			List<String> itemList=new ArrayList<>();
			for (String itemId : itemIds) {
				if(StrUtils.isBlankOrNull(itemId)||p.getItemIds().contains(itemId)) {
					continue;
				}
				ItemMethod im=itemMethodDao.findByMethodIdAndItemId(p.getId(), itemId);
				itemMethodDao.delete(im);
				itemList.add(itemId);
			}
			//已清除的项目Id 更新项目里的方法属性
			for (String itemId : itemList) {
				Item item=itemDao.findById(itemId);
				//更新项目里的方法
				List<String> midList=new ArrayList<String>();
				List<String> mNameList=new ArrayList<String>();
				List<ItemMethod> imList=itemMethodDao.listByItemId(item.getId());
				for (ItemMethod itemMethod : imList) {
					midList.add(itemMethod.getMethod().getId());
					if(!StrUtils.isBlankOrNull(itemMethod.getMethod().getChapter())) {
						mNameList.add(itemMethod.getMethod().getCode()+" "+itemMethod.getMethod().getChapter());
					}else {
						mNameList.add(itemMethod.getMethod().getCode());
					}
				}
				item.setStandIds(String.join(",", midList));
				item.setStandNames(String.join(",", mNameList));
				itemDao.update(item);
			}
		}
		//更新添加的项目里方法信息
		if(!StrUtils.isBlankOrNull(p.getItemIds())) {
			List<String> itemList=Arrays.asList(p.getItemIds().split(","));
			for (String itemId : itemList) {
				if(!StrUtils.isBlankOrNull(itemId)) {
					Item item=itemDao.findById(itemId);
					ItemMethod im=itemMethodDao.findByMethodIdAndItemId(p.getId(), itemId);
					if(im==null) {
						im=new ItemMethod();
						im.setItem(item);
						im.setMethod(p);
						itemMethodDao.add(im);
					}
					//更新项目里的方法
					List<String> midList=new ArrayList<String>();
					List<String> mNameList=new ArrayList<String>();
					List<ItemMethod> imList=itemMethodDao.listByItemId(item.getId());
					for (ItemMethod itemMethod : imList) {
						midList.add(itemMethod.getMethod().getId());
						if(!StrUtils.isBlankOrNull(itemMethod.getMethod().getChapter())) {
							mNameList.add(itemMethod.getMethod().getCode()+" "+itemMethod.getMethod().getChapter());
						}else {
							mNameList.add(itemMethod.getMethod().getCode());
						}
					}
					item.setStandIds(String.join(",", midList));
					item.setStandNames(String.join(",", mNameList));
					itemDao.update(item);
				}
			}
		}
	}
	@Override
	public List<MethodVo> list(MethodVo v) throws GlobalException {
		String jpql="FROM "+methodDao.getEntityName(Method.class)+" WHERE isDel='"+Po.N+"'";
//		if(!StrUtils.isBlankOrNull(v.getStandId())) {
//			jpql+=" AND standId='"+v.getStandId()+"'";
//		}
		List<Method> plist=methodDao.list(jpql);
		
		return toVoList(plist);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, MethodVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("sort");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = methodDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Method>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public MethodVo findByCode(String code) throws GlobalException {
		code=code.replaceAll("　", " ").replaceAll("_", "-").replaceAll("－", "-").trim();
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("code LIKE '"+code+"'"));
		List<MethodVo> list = super.list(queryConditions);
		MethodVo vo = new MethodVo();
		if(list.size()>0){
			vo = list.get(0);
		}
		return vo;
	}

	@Override
	public String checkMethodApp(String ids) throws GlobalException {
		String msg="";
		List<Method> mlist=methodDao.listByIds(ids);
		if(mlist==null)return msg;
		for (Method m : mlist) {
			if(StrUtils.isNotBlankOrNull(m.getAppIds())) {
				List<Appara> appList=apparaDao.listByIds(m.getAppIds());
				if(null!=appList) {
					String appid="";
					for (Appara app : appList) {
						if(StrUtils.isBlankOrNull(app.getState())) {
							continue;
						}else if(app.getState().equals(Appara.ST_0)) {
							appid="";
							break;
						}else{
							appid+=m.getCode()+"所用的仪器被占用或损坏，不能进行实验！\r\n";
						}
					}
					if(!appid.equals("")) {
						msg+=appid;
					}
				}
			}
		}
		
		return msg;
	}
	@Override
	public void update2del(String... ids) throws GlobalException {
		List<Method> mlist=methodDao.listByIds(ids);
		for(Method m:mlist){
			m.setIsDel(Po.Y);
			methodDao.update(m);
			//删除方法项目关系
			List<ItemMethod> imList=itemMethodDao.listByMethodId(m.getId());
			if(null!=imList) {
				for (ItemMethod im : imList) {
					im.setIsDel(Po.Y);
					itemMethodDao.update(im);
					Item item=im.getItem();
					//更新项目里的方法
					List<String> midList=new ArrayList<String>();
					List<String> mNameList=new ArrayList<String>();
					List<ItemMethod> itList=itemMethodDao.listByItemId(item.getId());
					for (ItemMethod itemMethod : itList) {
						midList.add(itemMethod.getMethod().getId());
						if(!StrUtils.isBlankOrNull(itemMethod.getMethod().getChapter())) {
							mNameList.add(itemMethod.getMethod().getCode()+" "+itemMethod.getMethod().getChapter());
						}else {
							mNameList.add(itemMethod.getMethod().getCode());
						}
					}
					item.setStandIds(String.join(",", midList));
					item.setStandNames(String.join(",", mNameList));
					itemDao.update(item);
				}
			}
		}
	}
}
