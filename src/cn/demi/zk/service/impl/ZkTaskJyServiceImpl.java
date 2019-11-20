package cn.demi.zk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.po.Files;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.zk.dao.IZkItemTestDao;
import cn.demi.zk.dao.IZkProgressLogDao;
import cn.demi.zk.dao.IZkTaskDao;
import cn.demi.zk.po.ZkItemTest;
import cn.demi.zk.po.ZkProgressLog;
import cn.demi.zk.po.ZkTask;
import cn.demi.zk.service.IZkTaskJyService;
import cn.demi.zk.vo.ZkItemTestVo;
import cn.demi.zk.vo.ZkSamplingVo;
import cn.demi.zk.vo.ZkTaskVo;

@Service("zk.taskJyService")
public class ZkTaskJyServiceImpl extends BaseServiceImpl<ZkTaskVo,ZkTask> implements
		IZkTaskJyService {
	@Autowired
	private IZkTaskDao zkTaskDao;
	@Autowired
	private IZkItemTestDao itemTestDao;
	@Autowired
	private IZkProgressLogDao progressLogDao;
	@Autowired
	private IFilesDao filesDao;
	@Override
	public void update(ZkTaskVo v) throws GlobalException {
		ZkTask p = zkTaskDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		if(v.getIsCommit().equals(EunmTask.PASS_N)) {//任务退回
			String ids=v.getIds();
			List<ZkItemTest> itlist=itemTestDao.listByIds(ids);
			for (ZkItemTest it : itlist) {
				it.setIsBack(Po.Y);
				it.setCheckMsg(v.getRemark());
				it.setCheckMan(getCurrent().getUserName());
				it.setCheckManId(getCurrent().getAccountId());
				it.setStatus(ZkItemTest.STATUS_0);
				itemTestDao.update(it);
			}
		}else {
			p.setStatus(EunmZkTask.TASK_60.getStatus());
			zkTaskDao.update(p);
			progressLogDao.add(p.getId(),EunmZkTask.TASK_50.getStatus(),"校验",v.getRemark());
			String jpql ="FROM "+itemTestDao.getEntityName(ZkItemTest.class)+" WHERE isDel="+Po.N+" AND task.id='"+p.getId()+"'";
			List<ZkItemTest> itList=itemTestDao.list(jpql);
			for (ZkItemTest it : itList) {
				it.setCheckMsg(v.getRemark());
				it.setCheckMan(getCurrent().getUserName());
				it.setCheckManId(getCurrent().getAccountId());
				itemTestDao.update(it);
			}
		}
	}
	@Override
	public ZkTaskVo findById(String id) throws GlobalException {
		ZkTask task= zkTaskDao.findById(id);
		ZkTaskVo vo=po2Vo(task);
		List<ZkItemTest> pList=itemTestDao.findByProperty("task.id", task.getId());
		List<ZkItemTestVo> voList=new ArrayList<ZkItemTestVo>();
		for (ZkItemTest it : pList) {
			ZkItemTestVo itVo=new ZkItemTestVo();
			itVo=itVo.toVo(it);
			ZkSamplingVo sampVo=new ZkSamplingVo();
			sampVo=sampVo.toVo(it.getSampling());
			itVo.setSamplingVo(sampVo);
			
			String jpql="FROM "+filesDao.getEntityName(Files.class)+" WHERE busId='"+it.getId()+"'";
			List<Files> fList=filesDao.list(jpql);
			List<FilesVo> fileList=null;
			if(null!=fList) {
				fileList=new ArrayList<FilesVo>();
				for (Files f : fList) {
					FilesVo fVo=new FilesVo();
					fVo=fVo.toVo(f);
					fileList.add(fVo);
				}
			}
			itVo.setFileList(fileList);
			
			voList.add(itVo);
		}
		vo.setItemList(voList);
		//样品信息
//		List<ZkSampling> sampList=samplingDao.findByProperty("task.id", task.getId());
//		List<ZkSamplingVo> sampVoList=new ArrayList<ZkSamplingVo>();
//		Map<String,ZkItemTestVo> itemMap=new HashMap<String,ZkItemTestVo>();
//		if(sampList!=null) {
//			for (ZkSampling samp : sampList) {
//				ZkSamplingVo sampVo=new ZkSamplingVo();
//				sampVo=sampVo.toVo(samp);
//				sampVo.setItemMap(getItemTestVoList(samp.getId(),itemMap));
//				sampVoList.add(sampVo);
//			}
//		}
//		vo.setItemList(new ArrayList<ZkItemTestVo>(itemMap.values()));
//		vo.setSampList(sampVoList);
		return vo;
	}
	//获取现场测试项目
	public Map<String,ZkItemTestVo> getItemTestVoList(String sampid,Map<String,ZkItemTestVo> itemMap) throws GlobalException {
		Map<String,ZkItemTestVo> itemVoMap=new HashMap<>();
		//现场检测项目信息
		StringBuffer jpql = new StringBuffer("FROM "+itemTestDao.getEntityName(Po.class)+" WHERE isDel="+Po.N);
		jpql.append(" AND sampling.id='"+sampid+"'");
		jpql.append(" ORDER BY sort asc");
		List<ZkItemTest> itemList=itemTestDao.list(jpql.toString());
		if(itemList!=null) {
			for (ZkItemTest item : itemList) {
				ZkItemTestVo itemVo=new ZkItemTestVo();
				itemVo=itemVo.toVo(item);
				if(null==item.getStatus()||!item.getStatus().equals(ZkItemTest.STATUS_1)){
					itemVo.setValue("未完成");
				}
				itemVoMap.put(itemVo.getItemId(),itemVo);
				if(itemMap.get(itemVo.getItemId())==null) {
					itemMap.put(itemVo.getItemId(), itemVo);
				}
			}
		}
		return itemVoMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = zkTaskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ZkTask>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	 
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("FROM "+zkTaskDao.getEntityName(ZkTask.class)+" t,"+zkTaskDao.getEntityName(ZkProgressLog.class)+" log where log.taskId=t.id and t.isDel !="+Po.Y);
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
		List<ZkTask> taskList=new ArrayList<ZkTask>();
		if(list!=null) {
			for (Object[] objects : list) {
				try {
					Object obj[]=objects;
					ZkTask t=(ZkTask) obj[0];
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
	 
	@Override
	public List<QueryCondition> toQueryList(ZkTaskVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("status='"+v.getStatus()+"'"));
		return queryList;
	}
}
