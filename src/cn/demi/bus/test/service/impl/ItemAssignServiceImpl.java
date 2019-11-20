package cn.demi.bus.test.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageBean;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.service.IItemAssignService;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.pfm.dao.ISRecordDao;

@Service("bus.itemAssignService")
public class ItemAssignServiceImpl extends BaseServiceImpl<TaskItemVo,TaskItem> implements
		IItemAssignService {
	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ISRecordDao sRecordDao;
	@Autowired
	private IOrgDao orgDao;
	@Override
	public List<TaskItemVo> listByIds(String ids) throws GlobalException {
		List<TaskItemVo> vList = new ArrayList<TaskItemVo>();
		String jpql="FROM "+taskItemDao.getEntityName(TaskItem.class)+" WHERE isDel='"+Po.N+"' AND id in('"+ids.replace(",", "','")+"') ORDER BY itemId,sort ASC";
		List<TaskItem> pList=taskItemDao.list(jpql);
		if(pList!=null) {
			for (TaskItem po : pList) {
				TaskItemVo vo=new TaskItemVo();
				vo=po2Vo(po);
				if(StrUtils.isBlankOrNull(vo.getSbDate())) {
					try {
						vo.setSbDate(DateUtils.getNextDate4Hours(DateUtils.parse(DateUtils.getCurrDateTimeStr(), DateUtils.formatStr_yyyyMMddHHmmss),24));
					} catch (ParseException e) {
						vo.setSbDate(null);
					}
				}
				vList.add(vo);
			}
		}
		return vList;
	}
	@Override
	public void update(TaskItemVo v) throws GlobalException {
		for (TaskItemVo itVo : v.getTimList()) {
			TaskItem it=taskItemDao.findById(itVo.getId());
			it.setTestMan(itVo.getTestMan());
			it.setTestManId(itVo.getTestManId());
			it.setCompDate(itVo.getCompDate());
			it.setSbDate(itVo.getSbDate());
			it.setCheckMan(itVo.getCheckMan());
			it.setCheckManId(itVo.getCheckManId());
			it.setAssignDate(v.getAssignDate());
			it.setAssignUser(v.getAssignUser());
			it.setAssignUserId(v.getAssignUserId());
			it.setAssignMsg(v.getAssignMsg());
			taskItemDao.update(it);
			//任务分配 初始化项目进度
			if(!StrUtils.isNull(v.getIsCommit()) && v.getIsCommit().equals(EunmTask.PASS_Y)) {
				String msg=it.getItemName()+" 分配给 "+it.getTestMan()+" 备注："+v.getAssignMsg();
				//插入任务进度日志
				progressLogDao.add(it.getTask().getId(), it.getId(), EunmTask.ITEM_FP.getStatus(), v.getIsCommit(), msg);
				Progress pg=progressDao.update(it.getProgress().getId(),EunmTask.ITEM_LR.getStatus(), it.getTestManId(),it.getTestMan());
				it.setStatus(pg.getStatus());
				taskItemDao.update(it);
				sRecordDao.insert(it.getTask().getNo()+it.getItemName(),it.getTask().getXdDate(),Constants.KH_RW_RWFP);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TaskItemVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='"+EunmTask.ITEM_FP.getStatus()+"' " ));
		List<String> orgIds=orgDao.listChild(CurrentUtils.getCurrent().getDepId());
		orgIds.add(CurrentUtils.getCurrent().getDepId());
		pageResult.addCondition(new QueryCondition("deptId in('"+String.join("','", orgIds)+"') " ));
		pageResult = taskItemDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<TaskItem>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(TaskItemVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		//queryList.add(new QueryCondition("tp='" + EnumBus.TYPE_ZJ + "' "));
		return queryList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, TaskItemVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("SELECT t.id AS A,t.item_name,task.id AS C,task.no,task.task_type,t.test_man,t.sb_date,t.assign_date,t.samp_name,t.samp_num");
		hql.append(" FROM "+tablePrefix+taskItemDao.getEntityName(TaskItem.class)+" t "
				+ "JOIN "+tablePrefix+taskItemDao.getEntityName(ProgressLog.class)+" log ON t.id=log.bus_id AND log.status='"+EunmTask.ITEM_FP.getStatus()+"' ");
		hql.append(" JOIN "+tablePrefix+taskItemDao.getEntityName(Task.class)+" task ON t.task_id=task.id AND task.is_del='"+Po.N+"'");
		hql.append(" WHERE t.is_del !='"+Po.Y+"'");
		if(QueryConditionList!=null) {
			for (QueryCondition condition : QueryConditionList) {
				if(!StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) {
					if(condition.getField().contains(".")) {
						hql.append(" AND "+condition.getField()+" like '%"+String.valueOf(condition.getValue()).trim()+"%' ");
					}else {
						hql.append(" AND t."+condition.getField()+" like '%"+String.valueOf(condition.getValue()).trim()+"%' ");
					}
				}
			}
		}
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			hql.append(" ORDER BY log.last_upd_time "+OrderCondition.ORDER_DESC+"");
		}else{
			if(gridVo.getSidx().contains(".")) {
				hql.append(" ORDER BY "+gridVo.getSidx()+" "+gridVo.getSord()+"");
			}else {
				hql.append(" ORDER BY t."+gridVo.getSidx()+" "+gridVo.getSord()+"");
			}
		}
		Query query=taskItemDao.queryBysql(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Object[]> pList = query.setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		List<Map<String, Object>> taskList =new ArrayList<Map<String, Object>>();
		if (null!=pList) {
			for (Object[] obj : pList) {
				Map<String, Object> map=new HashedMap();
				map.put("id", obj[0]);
				map.put("itemName", obj[1]);
				map.put("testMan", obj[5]);
				map.put("sbDate", obj[6]);
				map.put("assignDate", obj[7].toString());
				map.put("sampName", obj[8].toString());
				map.put("sampNum", obj[9].toString());
				//设置任务对象map
				Map<String, Object> taskVo =new HashedMap();
				taskVo.put("id", obj[2]);
				taskVo.put("no", obj[3]);
				taskVo.put("taskType", obj[4]);
				map.put("taskVo", taskVo);
				taskList.add(map);
			}
		}
		gridVo.setDatas(taskList);
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public void update2Stop(TaskItemVo v) throws GlobalException {
		for(TaskItem it:taskItemDao.listByIds(v.getIds())){
			it.setStatus(EunmTask.TASK_STOP.getStatus());
			Progress pg=it.getProgress();
			it.setProgress(null);
			taskItemDao.update(it);
			if(null!=pg) {
				//插入任务进度日志
				progressLogDao.add(it.getTask().getId(),it.getId(),pg.getStatus(),EunmTask.TASK_STOP.getStatus(),"项目终止,原因："+v.getRemark());
				progressDao.delete(pg);
			}
		}
	}
	 
}
