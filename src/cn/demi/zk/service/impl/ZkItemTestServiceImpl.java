package cn.demi.zk.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.constant.EunmZkTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.po.Method;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.po.Appara;
import cn.demi.res.vo.ApparaVo;
import cn.demi.zk.dao.IZkItemTestDao;
import cn.demi.zk.dao.IZkProgressLogDao;
import cn.demi.zk.dao.IZkTaskDao;
import cn.demi.zk.po.ZkItemTest;
import cn.demi.zk.po.ZkProgressLog;
import cn.demi.zk.service.IZkItemTestService;
import cn.demi.zk.vo.ZkItemTestVo;

@Service("zk.itemTestService")
public class ZkItemTestServiceImpl extends BaseServiceImpl<ZkItemTestVo,ZkItemTest> implements
		IZkItemTestService {
	@Autowired
	private IZkTaskDao zkTaskDao;
//	@Autowired
//	private IZkSamplingDao zkSamplingDao;
	@Autowired
	private IZkItemTestDao zkItemTestDao;
	@Autowired
	private IApparaDao apparaDao;
	@Autowired
	private IMethodDao methodDao;
	@Autowired
	private IZkProgressLogDao progressLogDao;
	@Override
	public void update(ZkItemTestVo v) throws GlobalException {
		//更新仪器使用信息
		if(null!=v.getItemVoList()) {
			for (ZkItemTestVo itVo : v.getItemVoList()) {
				ZkItemTest it = zkItemTestDao.findById(itVo.getId());
				if(!StrUtils.isBlankOrNull(itVo.getAppId())) {
					Appara app=apparaDao.findById(itVo.getAppId());
					it.setAppId(app.getId());
					it.setAppName(app.getName()+" "+app.getSpec());
					it.setAppStartTime(itVo.getAppStartTime());
					it.setAppEndTime(itVo.getAppEndTime());
					it.setAppStat(itVo.getAppStat());
				}
				it.setWd(itVo.getWd());
				it.setSd(itVo.getSd());
				zkItemTestDao.update(it);
			}
		}
		//更新检出结果
		for (ZkItemTestVo itVo : v.getItemList()) {
			ZkItemTest it=zkItemTestDao.findById(itVo.getId());
			it.setTestMan(v.getTestMan());
			it.setTestManId(v.getTestManId());
			it.setTestTime(v.getTestTime());
			it.setFileName(v.getFileName());
			it.setFilePath(v.getFilePath());
			it.setRemark(v.getRemark());
			it.setValue(itVo.getValue());
			if(!StrUtils.isBlankOrNull(itVo.getMethodId())) {
				Method method=methodDao.findById(itVo.getMethodId());
				it.setMethodId(method.getId());
				it.setMethodName(method.getName());
				it.setStandCode(method.getCode());
				it.setStandName(method.getStandName());
				it.setChapter(method.getChapter());
				it.setLimitLine(method.getLimitLine());
			}
			if(!StrUtils.isNull(v.getIsCommit())&&(v.getIsCommit().equals(EunmTask.PASS_Y))) {
				it.setStatus(ZkItemTest.STATUS_1);
				it.setIsBack(Po.N);
				progressLogDao.add(it.getId(),EunmZkTask.ITEM_1.getStatus(),"检测","检测项目："+it.getItemName()+" 本次检测结果为 "+it.getValue()+" "+it.getUnit()+" 备注："+v.getRemark());
			}
			zkItemTestDao.update(it);
		}	
	}
	@Override
	public List<ZkItemTestVo> listByIds(String ids) throws GlobalException {
		List<ZkItemTestVo> vList = new ArrayList<ZkItemTestVo>();
		List<ZkItemTest> pList=zkItemTestDao.listByIds(ids);
		if(pList!=null) {
			for (ZkItemTest po : pList) {
				ZkItemTestVo vo=new ZkItemTestVo();
				vo=po2Vo(po);
				if(!StrUtils.isBlankOrNull(po.getMethodId())) {
					Method method=methodDao.findById(po.getMethodId());
					String appIds=method.getAppIds();
					if(!StrUtils.isBlankOrNull(appIds)) {
						List<Appara> appList=apparaDao.listByIds(appIds);
						List<ApparaVo> appVoList=new ArrayList<ApparaVo>();
						if(null!=appList) {
							for (Appara app : appList) {
								ApparaVo appVo=new ApparaVo();
								appVo.setId(app.getId());
								appVo.setName(app.getName());
								appVo.setNo(app.getNo());
								appVo.setSpec(app.getSpec());
								appVo.setCode(app.getCode());
								appVo.setState(app.getState());
								appVoList.add(appVo);
							}
						}
						vo.setAppList(appVoList);
					}
				}
				vList.add(vo);
			}
		}
		return vList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ZkItemTestVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("itemId");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = zkItemTestDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ZkItemTest>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<Map<?, Object>> poList2mapList(List<ZkItemTest> list) throws GlobalException {
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for(ZkItemTest p:list){
			Map<String, Object> itemMap=po2map(p);
			long lastUpdTime=itemMap.get("lastUpdTime")==null?0:Long.valueOf(itemMap.get("lastUpdTime").toString());
			String time=DateUtils.parseToDateStr(lastUpdTime);
			if(null!=time&&time.length()>10) {
				time=time.substring(0, 10);
			}
			itemMap.put("lastUpdTime",time);
			tempList.add(itemMap);
		}
		return tempList;
	}
	@Override
	public List<QueryCondition> toQueryList(ZkItemTestVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("status='"+ZkItemTest.STATUS_0+"'"));
		return queryList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ZkItemTestVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("FROM "+zkTaskDao.getEntityName(ZkItemTest.class)+" t,"+zkTaskDao.getEntityName(ZkProgressLog.class)+" log where log.taskId=t.id and t.isDel !="+Po.Y);
		if(QueryConditionList!=null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='"+v.getStatus()+"' ");
		hql.append(" ORDER BY t."+pageResult.getOrderColumn()+" "+pageResult.getOrder()+"");
		Query query=zkTaskDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Object[]> list = zkTaskDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		List<ZkItemTest> taskList=new ArrayList<ZkItemTest>();
		if(list!=null) {
			for (Object[] objects : list) {
				try {
					Object obj[]=objects;
					ZkItemTest t=(ZkItemTest) obj[0];
					ZkProgressLog log=(ZkProgressLog) obj[1];
					t.setProgressLog(log);
					taskList.add(t);
				} catch (Exception e) {
				}
			}
		}
		gridVo.setDatas(poList2mapList(taskList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	 
	 
}
