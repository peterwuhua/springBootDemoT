package cn.demi.bus.task.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.service.ITaskXdService;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.init.std.dao.IItemUserDao;
import cn.demi.init.std.po.ItemUser;
import cn.demi.pfm.dao.ISRecordDao;

@Service("bus.taskXdService")
public class TaskXdServiceImpl extends BaseServiceImpl<TaskVo,Task> implements
		ITaskXdService {
 
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private IItemUserDao itemUserDao;
	@Autowired
	private ISRecordDao sRecordDao;
	@Override
	public TaskVo find(String ids) throws GlobalException {
		TaskVo vo =new TaskVo();
		vo.setIds(ids);
		String taskIds[]=StrUtils.split(ids, ",");
		List<TaskItemVo> vList=new ArrayList<TaskItemVo>();
		for (String taskId : taskIds) {
			List<TaskItem> pList=taskItemDao.listByTaskId(taskId,TaskItem.ITEM_TYPE_PT);
			if(null!=pList) {
				for (TaskItem obj : pList) {
					TaskItemVo itVo=new TaskItemVo();
					itVo=itVo.toVo(obj);
					vList.add(itVo);
				}
			}
		}
		vo.setTimList(vList);
		return vo;
	}
	
	@Override
	public TaskVo findById(String id) throws GlobalException {
		Task task = taskDao.findById(id);
		TaskVo vo = po2Vo(task);
		List<TaskItem> pList=taskItemDao.listByTaskId(task.getId(),TaskItem.ITEM_TYPE_PT);
		if (pList != null && pList.size()>0) {
			List<TaskItemVo> timVoList = new ArrayList<TaskItemVo>();
			for (TaskItem tim : pList) {
				TaskItemVo timVo = new TaskItemVo();
				timVo = timVo.toVo(tim);
				timVoList.add(timVo);
			}
			 vo.setTimList(timVoList);
		}
		return vo;
	}
	
	@Override
	public void update(TaskVo v) throws GlobalException {
		List<Task> taskList=taskDao.listByIds(v.getIds());
		for (Task task : taskList) {
			task.setXdDate(v.getXdDate());
			task.setXdUser(v.getXdUser());
			task.setXdUserId(v.getXdUserId());
			task.setXdMsg(v.getXdMsg());
			Progress pg=progressDao.update(task.getProgress().getId(),EunmTask.TASK_HZ.getStatus(),null,null,null,null);
			task.setStatus(pg.getStatus());
			taskDao.update(task);
			progressLogDao.add(task.getId(),task.getId(),EunmTask.TASK_XD.getStatus(),v.getIsCommit(),v.getXdMsg());
			sRecordDao.insert(task.getNo(),task.getReciveDate(),Constants.KH_RW_RWXD);
			//更新现场测试项目状态到数据汇总
			uptItem2Hz(task.getId());
		}
		List<TaskItemVo> timList=v.getTimList();
		for (TaskItemVo timVo : timList) {
			TaskItem tim=taskItemDao.findById(timVo.getId());
			Org dept=orgDao.findById(timVo.getDeptId());
			//默认检测人 校验人
			ItemUser user=itemUserDao.findUser(tim.getItemId(), dept.getId());
			if(null!=user) {
				tim.setTestManId(user.getUserId());//默认检测人
				tim.setTestMan(user.getUserName());
				setCheckMan(tim,dept.getId());//默认校验人
			}else {
				setTestMan(tim,dept.getId());//默认检测人
				setCheckMan(tim,dept.getId());//默认校验人
			}
			tim.setDeptId(dept.getId());
			tim.setDeptName(dept.getName());
			tim.setCompDate(timVo.getCompDate());
			tim.setSbDate(DateUtils.getNextWorkDate(tim.getCompDate(),1));
			//任务下达 初始化项目进度
			Progress itpg=progressDao.add(tim.getId(), EunmTask.ITEM_FP.getStatus(), dept.getId(),dept.getName(),null,null);
			tim.setProgress(itpg);
			tim.setStatus(itpg.getStatus());
			taskItemDao.update(tim);
		}
	}
	//获取项目的最近检测的人
	public void setTestMan(TaskItem it,String orgId)throws GlobalException{
		String jpql="select test_man_id,test_man from "+tablePrefix+taskItemDao.getEntityName(TaskItem.class)+" where test_man_id is not null and item_name like '"+it.getItemName()+"' AND dept_id='"+orgId+"' ORDER BY assign_date desc";
		@SuppressWarnings("unchecked")
		List<Object[]> objList=taskItemDao.queryBySql(jpql);
		if(objList!=null&&objList.size()>0) {
			Object[] obj=objList.get(0);
			it.setTestManId(String.valueOf(obj[0]));
			it.setTestMan(String.valueOf(obj[1]));
		}
	}
	//获取项目的最近校验人
	public void setCheckMan(TaskItem it,String orgId)throws GlobalException{
		String jpql="select check_man_id,check_man from "+tablePrefix+taskItemDao.getEntityName(TaskItem.class)+" where check_man_id is not null and item_name like '"+it.getItemName()+"' AND dept_id='"+orgId+"' ORDER BY assign_date desc";
		@SuppressWarnings("unchecked")
		List<Object[]> objList=taskItemDao.queryBySql(jpql);
		if(objList!=null&&objList.size()>0) {
			Object[] obj=objList.get(0);
			it.setCheckManId(String.valueOf(obj[0]));
			it.setCheckMan(String.valueOf(obj[1]));
		}
	}
	//更新现场项目到数据汇总
	public void uptItem2Hz(String taskId) {
		List<TaskItem> timList=taskItemDao.listByTaskId(taskId,TaskItem.ITEM_TYPE_XC);
		for (TaskItem tim : timList) {
			Progress pg = progressDao.add(tim.getId(), EunmTask.ITEM_JY.getStatus(),getCurrent().getOrgId(), getCurrent().getOrgName(),null,null);
			tim.setProgress(pg);
			tim.setStatus(pg.getStatus());
			tim.setOrgId(getCurrent().getOrgId());
			tim.setOrgName(getCurrent().getOrgName());
			taskItemDao.update(tim);
            progressLogDao.add(tim.getTask().getId(), tim.getId(), EunmTask.TASK_XD.getStatus(), EunmTask.PASS_Y, "【系统】现场监测项目初始化");
		}
		taskDao.updateSt(taskId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status='"+EunmTask.TASK_XD.getStatus()+"' " ));
		pageResult = taskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Task>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(TaskVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
//		queryList.add(new QueryCondition("type='" + EnumBus.TYPE_ZJ + "' "));
		return queryList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("SELECT distinct t FROM "+taskDao.getEntityName(Task.class)+" t,"+taskDao.getEntityName(ProgressLog.class)+" log where log.busId=t.id and t.isDel ="+Po.N);
		if(QueryConditionList!=null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='"+EunmTask.TASK_XD.getStatus()+"' ");
		hql.append(" ORDER BY t."+pageResult.getOrderColumn()+" "+pageResult.getOrder()+"");
		pageResult =taskDao.getPageResult(hql.toString(), pageResult);
		gridVo.setDatas(poList2mapList((List<Task>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

}
