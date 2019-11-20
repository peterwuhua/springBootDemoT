package cn.demi.zk.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import cn.core.framework.constant.EunmZkTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.zk.dao.IZkItemTestDao;
import cn.demi.zk.dao.IZkProgressLogDao;
import cn.demi.zk.dao.IZkTaskDao;
import cn.demi.zk.po.ZkItemTest;
import cn.demi.zk.po.ZkProgressLog;
import cn.demi.zk.po.ZkTask;
import cn.demi.zk.service.IZkTaskPjService;
import cn.demi.zk.vo.ZkItemTestVo;
import cn.demi.zk.vo.ZkSamplingVo;
import cn.demi.zk.vo.ZkTaskVo;

@Service("zk.taskPjService")
public class ZkTaskPjServiceImpl extends BaseServiceImpl<ZkTaskVo,ZkTask> implements
		IZkTaskPjService {
	@Autowired
	private IZkTaskDao zkTaskDao;
	@Autowired
	private IZkItemTestDao itemTestDao;
	@Autowired
	private IZkProgressLogDao progressLogDao;
	@Override
	public void update(ZkTaskVo v) throws GlobalException {
		ZkTask p = zkTaskDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.setStatus(EunmZkTask.TASK_100.getStatus());
		p.setResult(v.getResult());
		progressLogDao.add(p.getId(),EunmZkTask.TASK_60.getStatus(),"评价",v.getRemark());
		zkTaskDao.update(p);
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
			voList.add(itVo);
		}
		vo.setItemList(voList);
		return vo;
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
