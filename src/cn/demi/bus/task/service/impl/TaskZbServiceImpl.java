package cn.demi.bus.task.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.sample.dao.ISampContainerDao;
import cn.demi.bus.sample.dao.ISampCydDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampContainer;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.vo.SampContainerVo;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.bus.task.dao.ISampAppDao;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.SampApp;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.service.ITaskZbService;
import cn.demi.bus.task.vo.SampAppVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.init.car.dao.ICarUseDao;
import cn.demi.init.car.po.CarUse;
import cn.demi.init.car.vo.CarUseVo;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IItemMethodDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.ItemMethod;
import cn.demi.init.std.vo.ItemMethodVo;
import cn.demi.pfm.dao.ISRecordDao;
import cn.demi.res.dao.IConsumableDao;
import cn.demi.res.po.Consumable;

@Service("bus.taskZbService")
public class TaskZbServiceImpl extends BaseServiceImpl<TaskVo,Task> implements
		ITaskZbService {
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ITaskPointDao taskPointDao;
	@Autowired
	private ISampAppDao sampAppDao;
	@Autowired
	private ICarUseDao carUseDao;
	@Autowired
	private ISampContainerDao sampContainerDao;
	@Autowired
	private IConsumableDao consumableDao;
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private ISRecordDao sRecordDao;
	@Autowired
	private IItemMethodDao itemMethodDao;
	@Autowired
	private ISampCydDao sampCydDao;
	/**
	 * 获取任务详细信息
	 * 包含项目、样品信息
	 */
	@Override
	public TaskVo findById(String id) throws GlobalException {
		Task task=taskDao.findById(id);
		TaskVo vo=po2Vo(task);
		Set<String> itemIdSet=new HashSet<String>();
		List<TaskPoint> tpList =taskPointDao.listByTaskId(task.getId());
		if (tpList != null && tpList.size()>0) {
			List<TaskPointVo> tpVoList = new ArrayList<TaskPointVo>();
			for (TaskPoint tp : tpList) {
				TaskPointVo tpVo = new TaskPointVo();
				tpVo = tpVo.toVo(tp);
				//获取普通样
				String hql="FROM "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND point.id ='"+tp.getId()+"'  ORDER BY sort ASC";
				List<Sampling> sampList=samplingDao.list(hql);
				List<SamplingVo> sampVoList=new ArrayList<SamplingVo>();
				for (Sampling samp : sampList) {
					SamplingVo sampVo=new SamplingVo();
					sampVo=sampVo.toVo(samp);
					sampVo.setContainerList(getContainerList(samp.getId()));
					sampVoList.add(sampVo);
				}
				tpVo.setSampList(sampVoList);
				tpVoList.add(tpVo);
				if(tpVo.getItemIds() != null) {
					itemIdSet.addAll(Arrays.asList(tpVo.getItemIds().split(",")));
				}
			}
			 vo.setTpList(tpVoList);
		}
		vo.setAppList(getAppList(id));
		vo.setCarList(getCarList(id));
		vo.setItemIds(String.join(",", itemIdSet));
		return vo;
	}
	//获取普通样
	public List<SamplingVo> getSampList(TaskPoint tp,String reportNo) throws GlobalException{
		List<SamplingVo> sampVoList=new ArrayList<SamplingVo>();
		String hql="FROM "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND point.id ='"+tp.getId()+"' AND (type='"+Sampling.SAMP_TYPE_PT+"' OR type='"+Sampling.SAMP_TYPE_XN+"') ORDER BY p,sort ASC";
		List<Sampling> sampList=samplingDao.list(hql);
		for (Sampling samp : sampList) {
			SamplingVo sampVo=new SamplingVo();
			sampVo=sampVo.toVo(samp);
			sampVo.setContainerList(getContainerList(samp.getId()));
			sampVoList.add(sampVo);
		}
		return sampVoList;
	}
	//获取质控样
	public List<SamplingVo> getZkList(TaskPoint tp,String reportNo) throws GlobalException{
		//检查空白样
		String hql="FROM "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND point.id ='"+tp.getId()+"' AND type='"+Sampling.SAMP_TYPE_KB+"' ORDER BY sort ASC";
		List<Sampling> kbList=samplingDao.list(hql);
		//检查平行样
		hql="FROM "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND point.id ='"+tp.getId()+"' AND type='"+Sampling.SAMP_TYPE_PX_X+"' ORDER BY sort ASC";
		List<Sampling> pxList=samplingDao.list(hql);
		kbList.addAll(pxList);
		List<SamplingVo> sampVoList=new ArrayList<SamplingVo>();
		for (Sampling samp : kbList) {
			SamplingVo sampVo=new SamplingVo();
			sampVo=sampVo.toVo(samp);
			sampVo.setContainerList(getContainerList(samp.getId()));
			sampVoList.add(sampVo);
		}
		return sampVoList;
	}
	//采样容器信息
	public List<SampContainerVo> getContainerList(String sampId) throws GlobalException{
		List<SampContainer> cList=sampContainerDao.listBySampId(sampId);
		List<SampContainerVo> scList=new ArrayList<>();
		if(null!=cList&&cList.size()>0) {
			for (SampContainer sc : cList) {
				SampContainerVo scVo=new SampContainerVo();
				scVo=scVo.toVo(sc);
				scList.add(scVo);
			}
		}else {
			Sampling samp=samplingDao.findById(sampId);
			List<Item> itList=itemDao.listByIds(samp.getItemIds());
			Set<String> rqList=new HashSet<String>();
			for (Item item : itList) {
				if(!StrUtils.isBlankOrNull(item.getRqIds())) {
					rqList.add(item.getRqIds());
				}
			}
			List<Consumable> clist=consumableDao.listByIds(String.join(",", rqList));
			if(null!=clist) {
				for (Consumable c : clist) {
					SampContainerVo scVo=new SampContainerVo();
					scVo.setContainerId(c.getId());
					scVo.setContainer(c.getModel()+c.getName());
					scVo.setNum(1);
					scList.add(scVo);
				}
			}
		}
		return scList;
	}
	//设备信息
	public List<SampAppVo> getAppList(String id) throws GlobalException {
		List<SampAppVo> appvoList=new ArrayList<SampAppVo>();
		List<SampApp> sctList=sampAppDao.findByBusId(id);
		if(null!=sctList&&sctList.size()>0) {
			for (SampApp app : sctList) {
				SampAppVo appVo=new SampAppVo();
				appVo=appVo.toVo(app);
				appvoList.add(appVo);
			}
		}
		return appvoList;
	}
	//车辆信息
	public List<CarUseVo> getCarList(String id) throws GlobalException {
		List<CarUseVo> carvoList=new ArrayList<CarUseVo>();
		List<CarUse> carList=carUseDao.findByBusId(id);
		if(null!=carList&&carList.size()>0) {
			for (CarUse car : carList) {
				CarUseVo carVo=new CarUseVo();
				carVo=carVo.toVo(car);
				carvoList.add(carVo);
			}
		}
		return carvoList;
	}
	@Override
	public void update(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		p.setZbId(v.getZbId());
		p.setZbName(v.getZbName());
		p.setZbDate(v.getZbDate());
		p.setZbMsg(v.getZbMsg());
		//更新样品信息
		List<SamplingVo> sampList=v.getSampList();
		if(null!=v.getZsampList()) {
			sampList.addAll(v.getZsampList());
		}
		if(null!=sampList) {
			for (SamplingVo sampVo : sampList) {
				Sampling samp=samplingDao.findById(sampVo.getId());
				samp.setSampCode(sampVo.getSampCode());
				samplingDao.update(samp);
			}
		}
		if(null!=v.getIsCommit()&&v.getIsCommit().equals(EunmTask.PASS_Y)) {
			Progress pg=progressDao.update(p.getProgress().getId(),EunmTask.TASK_XC.getStatus(),null,null,p.getCyId(),p.getCyName());
			progressLogDao.add(p.getId(), p.getId(),EunmTask.TASK_ZB.getStatus(),v.getIsCommit(),v.getZbMsg());
			p.setStatus(pg.getStatus());
			//考核超期检查
			sRecordDao.insert(p.getNo(),p.getDate(),Constants.KH_RW_CYZB);
		}
		taskDao.update(p);
	}
	 
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status='"+EunmTask.TASK_ZB.getStatus()+"' " ));
		pageResult = taskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Task>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<Map<?, Object>> poList2mapList(List<Task> list) throws GlobalException {
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for (Task p : list) {
			Map<String, Object> map = po2map(p);
			map.put("status", EunmTask.getName(map.get("status").toString()));
			tempList.add(map);
		}
		return tempList;
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
		hql.append(" AND log.status='"+EunmTask.TASK_ZB.getStatus()+"' AND log.userId like '"+CurrentUtils.getCurrent().getAccountId()+"%' ");
		hql.append(" ORDER BY t."+pageResult.getOrderColumn()+" "+pageResult.getOrder()+"");
		pageResult =taskDao.getPageResult(hql.toString(), pageResult);
		gridVo.setDatas(poList2mapList((List<Task>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<ItemMethodVo> list4Sop(TaskVo v) throws GlobalException {
		List<ItemMethod>  imList=itemMethodDao.listByItemIds(v.getItemIds());
		List<ItemMethodVo> imVolist=new ArrayList<>();
		List<SampApp> appList=sampAppDao.findByBusId(v.getId());
		if(null!=imList) {
			for (ItemMethod im : imList) {
				ItemMethodVo imVo=new ItemMethodVo();
				imVo=imVo.toVo(im);
				String appIds="";
				String appNames="";
				if(null!=imVo.getCyAppId()&&imVo.getCyAppId().contains(",")) {
					String appIdArr[]=imVo.getCyAppId().split(",");
					for (String appId : appIdArr) {
						for (SampApp app : appList) {
							if(app.getAppId().equals(appId)) {
								appIds+=app.getAppId()+",";
								if(!StrUtils.isBlankOrNull(app.getAppCode())) {
									appNames+=app.getAppName()+app.getAppCode()+",";
								}else {
									appNames+=app.getAppName()+",";
								}
							}
						}
					}
					if(appIds.endsWith(",")) {
						appIds=appIds.substring(0,appIds.length()-1);
						appNames=appNames.substring(0,appNames.length()-1);
					}
				}else {
					appIds=imVo.getCyAppId();
					appNames=imVo.getCyAppName();
				}
				imVo.setCyAppId(appIds);
				imVo.setCyAppName(appNames);
				imVolist.add(imVo);
			}
		}
		return imVolist;
	}
	@Override
	public void updateBack(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		p.setZbId(v.getZbId());
		p.setZbName(v.getZbName());
		p.setZbDate(v.getZbDate());
		p.setZbMsg(v.getZbMsg());
		// 更新任务进度及日志
		if (!StrUtils.isNull(v.getIsCommit()) && v.getIsCommit().equals(EunmTask.PASS_N)) {
			samplingDao.queryBysql("update v_bus_sampling set cyd_id=null where task_id ='" + p.getId() + "'").executeUpdate();
			sampCydDao.deleteByTask(p.getId());
			Progress pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_AP.getStatus(), null, null, null, null);
			progressLogDao.add(p.getId(), p.getId(), EunmTask.TASK_ZB.getStatus(), EunmTask.PASS_N, v.getZbMsg());
			p.setStatus(pg.getStatus());
			p.setIsBack(Constants.Y);
		}
		taskDao.update(p);
		
	}
	 
}
