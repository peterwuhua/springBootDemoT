package cn.demi.bus.test.service.impl;

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
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Files;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.bus.test.service.IItemCheckService;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.bus.test.vo.TestItemVo;
import cn.demi.bus.test.vo.TestResultVo;
import cn.demi.pfm.dao.ISRecordDao;

@Service("bus.itemCheckService")
public class ItemCheckServiceImpl extends BaseServiceImpl<TaskItemVo,TaskItem> implements
		IItemCheckService {
	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IFilesDao filesDao;
	@Autowired
	private ISRecordDao sRecordDao;
	@Autowired
	private ITestItemDao  testItemDao;
	@Autowired
	private ITestResultDao testResultDao;
	@Autowired
	private IOrgDao orgDao;
	@Override
	public List<TaskItemVo> listByIds(String ids) throws GlobalException {
		String jpql="FROM "+taskItemDao.getEntityName(TaskItem.class)+" WHERE isDel='"+Po.N+"' AND id in('"+ids.replace(",", "','")+"') ORDER BY itemId,sort ASC";
		List<TaskItem>  itemList=taskItemDao.list(jpql);
		List<TaskItemVo>  itemVoList=null;
		if(null!=itemList) {
			itemVoList=new ArrayList<>();
			for (TaskItem itemTest : itemList) {
				TaskItemVo testVo=new TaskItemVo();
				testVo=testVo.toVo(itemTest);
				testVo.setItemList(list4Item(testVo.getId()));
				String hql="FROM "+filesDao.getEntityName(Files.class)+" WHERE isDel='"+Po.N+"' AND busId='"+itemTest.getId()+"' ";
				List<Files> fList=filesDao.list(hql);
				List<FilesVo> fileList=new ArrayList<FilesVo>();
				if(null!=fList) {
					for (Files f : fList) {
						FilesVo fVo=new FilesVo();
						fVo=fVo.toVo(f);
						fileList.add(fVo);
					}
				}
				testVo.setFileList(fileList);
				itemVoList.add(testVo);
			}
		}
		return itemVoList;
	}
	@Override
	public TaskItemVo findById(String id) throws GlobalException {
		TaskItem tim=taskItemDao.findById(id);
		TaskItemVo vo=po2Vo(tim);
		vo.setItemList(list4Item(vo.getId()));
		return vo;
	}
	//获取测试任务下的测试项目集合
	public List<TestItemVo> list4Item(String timId) throws GlobalException{
		List<TestItem> itList=testItemDao.listByTimId(timId);
		List<TestItemVo> itVoList=new ArrayList<>();
		if(null!=itList) {
			for (TestItem it : itList) {
				TestItemVo itVo=new TestItemVo();
				itVo=itVo.toVo(it);
				if(it.getTask().getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
					itVo.setTrVo(findResult(it.getId()));
				}else {
					itVo.setTrList(list4Result(it.getId()));
				}
				itVoList.add(itVo);
			}
		}
		return itVoList;
	}
	//获取测试项目的测试结果集合 职卫
	public List<TestResultVo> list4Result(String itId) throws GlobalException{
		List<TestResult> rList=testResultDao.listByItId(itId);
		List<TestResultVo> rVoList=new ArrayList<TestResultVo>();
		if(null!=rList) {
			for (TestResult r : rList) {
				TestResultVo rVo=new TestResultVo();
				rVo=rVo.toVo(r);
				rVoList.add(rVo);
			}
		}
		return rVoList;
	}
	//获取测试项目的结果信息 环境
	public TestResultVo findResult(String itId) throws GlobalException{
		List<TestResult> rList=testResultDao.listByItId(itId);
		TestResultVo rVo=new TestResultVo();
		if(null!=rList&&rList.size()>0) {
			rVo=rVo.toVo(rList.get(0));
			if(null!=rVo.getSampVo().getZkType()
					&&(rVo.getSampVo().getZkType().equals(Sampling.SAMP_TYPE_JB)
							||rVo.getSampVo().getZkType().equals(Sampling.SAMP_TYPE_PX_S))) {
				rVo.setOper("1");
			}else {
				rVo.setOper("0");
			}
		}
		return rVo;
	}
	@Override
	public void update(TaskItemVo v) throws GlobalException {
		List<TaskItem> list=taskItemDao.listByIds(v.getIds());
		for (TaskItem it :list) {
			it.setCheckMan(v.getCheckMan());
			it.setCheckManId(v.getCheckManId());
			it.setCheckTime(v.getCheckTime());
			it.setCheckMsg(v.getCheckMsg());
			Progress pg=null;
			if(!StrUtils.isNull(v.getIsCommit()) && v.getIsCommit().equals(EunmTask.PASS_Y)) {
				//提交至质量部审核
				//质量管控中心
				Org org = orgDao.findByName("质量管控中心");
				pg=progressDao.update(it.getProgress().getId(),EunmTask.ITEM_HZ.getStatus(),org.getId(),org.getName(),null,null);
				it.setOrgId(org.getId());
				it.setOrgName(org.getName());
			}else if(!StrUtils.isNull(v.getIsCommit())&&v.getIsCommit().equals(EunmTask.PASS_N)) {
				it.setIsBack(Constants.Y);//退回
				pg=progressDao.update(it.getProgress().getId(),EunmTask.ITEM_LR.getStatus(),it.getTestManId(),it.getTestMan());
			}
			progressLogDao.add(it.getTask().getId(),it.getId(),EunmTask.ITEM_JY.getStatus(),v.getIsCommit(),v.getCheckMsg());
			it.setStatus(pg.getStatus());
			taskItemDao.update(it);
			sRecordDao.insert(it.getTask().getNo()+it.getItemName(),DateUtils.parseToDateStr(it.getLastUpdTime()),Constants.KH_RW_SHJY);
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
//		pageResult.addCondition(new QueryCondition("orgId like '" + getCurrent().getOrgId() + "' "));
		pageResult.addCondition(new QueryCondition("status='"+EunmTask.ITEM_JY.getStatus()+"' " ));
		pageResult.addCondition(new QueryCondition("checkManId like '%"+CurrentUtils.getCurrent().getAccountId()+"%' or checkManId is null or checkManId='' "));
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
		return queryList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, TaskItemVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("SELECT t.id AS A,t.item_name,task.id AS C,task.no,task.task_type,t.check_man,t.sb_date,t.progress_id,t.check_time,t.samp_num,t.samp_name");
		hql.append(" FROM "+tablePrefix+taskItemDao.getEntityName(TaskItem.class)+" t JOIN "+tablePrefix+taskItemDao.getEntityName(ProgressLog.class)+" log ON t.id=log.bus_id AND log.status='"+EunmTask.ITEM_JY.getStatus()+"' ");
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
		hql.append(" group by t.id ");
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			hql.append(" ORDER BY t.last_upd_time "+OrderCondition.ORDER_DESC+"");
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
				map.put("checkMan", obj[5]);
				map.put("sbDate", obj[6]);
				map.put("checkTime", obj[8]);
				map.put("sampNum", obj[9]);
				map.put("sampName", obj[10]);
				//设置任务对象map
				Map<String, Object> taskVo =new HashedMap();
				taskVo.put("id", obj[2]);
				taskVo.put("no", obj[3]);
				String taskType=String.valueOf(obj[4]);
				taskVo.put("taskType", taskType);
				map.put("taskVo", taskVo);
				//设置样品对象map
				Map<String, Object> progressVo =new HashedMap();
				progressVo.put("id", obj[7]);
				map.put("progressVo", progressVo);//设置进度对象map
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<?, Object>> poList2mapList(List<TaskItem> list) throws GlobalException{
		List tempList = new ArrayList();
		for(TaskItem p:list){
			Map<String, Object> map=po2map(p);
			if (null != map.get("sbDate")) {
				String sbDate = String.valueOf(map.get("sbDate"));
				if(sbDate.length()==10) {
					sbDate=sbDate+" 23:59:59";
				}
				long n = DateUtils.getIntevalHours(DateUtils.getCurrDateTimeStr(), sbDate);
				if (n < 2) {
					map.put("color", "red");
				} else if (n < 12) {
					map.put("color", "blue");
				}
			}
			tempList.add(map);
		}
		return tempList;
	}
}
