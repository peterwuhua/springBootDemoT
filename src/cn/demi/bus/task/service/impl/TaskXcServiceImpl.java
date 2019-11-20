package cn.demi.bus.task.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EnumCyd;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.ICustDao;
import cn.demi.bus.pjt.dao.ICustPointDao;
import cn.demi.bus.pjt.dao.ICustRoomDao;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.pjt.po.CustPoint;
import cn.demi.bus.pjt.po.CustRoom;
import cn.demi.bus.pjt.po.Im;
import cn.demi.bus.pjt.po.Scheme;
import cn.demi.bus.pjt.po.SchemePoint;
import cn.demi.bus.sample.dao.ISampCydDao;
import cn.demi.bus.sample.dao.ISampRecordDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampCyd;
import cn.demi.bus.sample.po.SampRecord;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.vo.SampCydVo;
import cn.demi.bus.task.dao.ISampAppDao;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.SampApp;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.service.ITaskXcService;
import cn.demi.bus.task.vo.SampAppVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.init.sp.vo.PcUnit;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.dao.IPstandItemDao;
import cn.demi.init.std.dao.IPstandardDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.Method;
import cn.demi.init.std.po.PstandItem;
import cn.demi.init.std.po.Pstandard;

@Service("bus.taskXcService")
public class TaskXcServiceImpl extends BaseServiceImpl<TaskVo, Task> implements ITaskXcService {

	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private ITaskPointDao taskPointDao;
	@Autowired
	private ICustDao taskCustDao;
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private ISampAppDao sampAppDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private ISampRecordDao sampRecordDao;
	@Autowired
	private IImDao imDao;
	@Autowired
	private IPstandardDao pstandardDao;
	@Autowired
	private IPstandItemDao pstandItemDao;
	@Autowired
	private IMethodDao methodDao;
	@Autowired
	private ISampCydDao sampCydDao;
	@Autowired
	private ITestResultDao testResultDao;
	@Autowired
	private ITestItemDao testItemDao;
	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private ICustPointDao custPointDao;
	@Autowired
	private ISchemePointDao schemePointDao;
	@Autowired
	private ISchemeDao schemeDao;
	@Autowired
	private ICustRoomDao custRoomDao;
	@Override
	public TaskVo find(String id) throws GlobalException {
		Task task = taskDao.findById(id);
		TaskVo vo = po2Vo(task);
		List<SampCyd> cydList = sampCydDao.listByTaskId(vo.getId());
		List<SampCydVo> cydVoList = new ArrayList<SampCydVo>();
		if (null != cydList) {
			for (SampCyd cyd : cydList) {
				SampCydVo cydVo = new SampCydVo();
				cydVo = cydVo.toVo(cyd);
				cydVoList.add(cydVo);
			}
		}
		vo.setCydList(cydVoList);
		List<TaskPoint> tpList = taskPointDao.listByTaskId(vo.getId());
		List<TaskPointVo> tpVoList = new ArrayList<TaskPointVo>();
		for (TaskPoint tp : tpList) {
			TaskPointVo tpVo = new TaskPointVo();
			tpVo = tpVo.toVo(tp);
			tpVo.setImId(imDao.findByBusId(tp.getId()));
			//若任务来自项目 则自动初始项目中的点位环境信息
			if (!task.getSampType().equals(EnumBus.SAMP_TYPE_HJ) && !StrUtils.isBlankOrNull(task.getProjectId())) {
				String jpql = "FROM " + custPointDao.getEntityName(CustPoint.class) + " WHERE isDel='" + Po.N + "' AND projectId='" + task.getProjectId()
						+ "' AND name like '" + tp.getPointName() + "' AND room.name like '" + tp.getRoom() + "'";
				CustPoint cp = null;
				if (custPointDao.query(jpql).getResultList().size() > 0) {//解决bug No entity found for query
					cp = (CustPoint) custPointDao.query(jpql).getSingleResult();
				}
				// query0(jpql);
				if (null != cp) {
					if (StrUtils.isBlankOrNull(tpVo.getWorkNum())) {
						tpVo.setWorkNum(String.valueOf(cp.getWorkNum()));
					}
					if (StrUtils.isBlankOrNull(tpVo.getWorkType())) {
						tpVo.setWorkType(String.valueOf(cp.getRoom().getWorkType()));
					}
					if (StrUtils.isBlankOrNull(tpVo.getWorkPc())) {
						tpVo.setWorkPc(String.valueOf(1));
					}
					if (StrUtils.isBlankOrNull(tpVo.getWorkHours())) {
						tpVo.setWorkHours(String.valueOf(cp.getWorkHours()));
					}
					if (StrUtils.isBlankOrNull(tpVo.getFh())) {
						tpVo.setFh(String.valueOf(cp.getFhName()));
					}
					if (StrUtils.isBlankOrNull(tpVo.getOthers())) {
						tpVo.setOthers(String.valueOf(cp.getOthers()));
					}
				}
			}
			tpVoList.add(tpVo);
		}
		vo.setTpList(tpVoList);
		return vo;
	}

	public List<TaskPointVo> getPointList(String taskId) throws GlobalException {
		List<TaskPoint> tpList = taskPointDao.listByTaskId(taskId);
		List<TaskPointVo> tpVoList = new ArrayList<TaskPointVo>();
		for (TaskPoint tp : tpList) {
			TaskPointVo tpVo = new TaskPointVo();
			tpVo = tpVo.toVo(tp);
			tpVoList.add(tpVo);
		}
		return tpVoList;
	}

	/**
	 * 获取任务详细信息 包含项目、样品信息
	 */
	@Override
	public TaskVo findById(String id) throws GlobalException {
		Task task = taskDao.findById(id);
		TaskVo vo = po2Vo(task);
		List<TaskPoint> tpList = taskPointDao.listByTaskId(task.getId());
		if (tpList != null && tpList.size() > 0) {
			List<TaskPointVo> tpVoList = new ArrayList<TaskPointVo>();
			for (TaskPoint tp : tpList) {
				TaskPointVo tpVo = new TaskPointVo();
				tpVo = tpVo.toVo(tp);
				tpVoList.add(tpVo);
			}
			vo.setTpList(tpVoList);
		}
		return vo;
	}

	// 设备信息
	@Override
	public List<SampAppVo> getAppList(String id) throws GlobalException {
		List<SampAppVo> appvoList = new ArrayList<SampAppVo>();
		List<SampApp> sctList = sampAppDao.findByBusId(id);
		if (null != sctList && sctList.size() > 0) {
			for (SampApp app : sctList) {
				SampAppVo appVo = new SampAppVo();
				appVo = appVo.toVo(app);
				appvoList.add(appVo);
			}
		}
		return appvoList;
	}

	@Override
	public void update(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		p.setCyUserId(v.getCyUserId());// 采样填报人
		p.setCyUserName(v.getCyUserName());
		p.setCyTime(v.getCyTime());
		p.setCyMsg(v.getCyMsg());
		p.setJcmd(v.getJcmd());
		// 更新采样单模板
		List<SampCydVo> cydList = v.getCydList();
		if(null!=cydList) {
			for (SampCydVo cydVo : cydList) {
				SampCyd cyd = sampCydDao.findById(cydVo.getId());
				cyd.setType(cydVo.getType());
				cyd.setSort(cydVo.getSort());
				sampCydDao.update(cyd);
			}
		}
		// 更新现场情况 职卫
		List<TaskPointVo> tpList = v.getTpList();
		if (null != tpList && !p.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			for (TaskPointVo tpVo : tpList) {
				TaskPoint tp = taskPointDao.findById(tpVo.getId());
				tp.setRoom(tpVo.getRoom());
				tp.setPointName(tpVo.getPointName());
				tp.setWorkNum(tpVo.getWorkNum());
				tp.setWorkHours(tpVo.getWorkHours());
				tp.setCyType(tpVo.getCyType());
				tp.setCyHours(tpVo.getCyHours());
				tp.setFh(tpVo.getFh());
				tp.setOthers(tpVo.getOthers());
				tp.setWorkType(tpVo.getWorkType());
				tp.setWorkPc(tpVo.getWorkPc());
				tp.setSort(tpVo.getSort());
				taskPointDao.update(tp);
				uptSampInfo(tp);
			}
		}else if(null != tpList){
			for (TaskPointVo tpVo : tpList) {
				TaskPoint tp = taskPointDao.findById(tpVo.getId());
				SampType st = sampTypeDao.findById(tpVo.getSampTypeId());
				tp.setSampTypeId(st.getId());
				tp.setSampTypeName(st.getName());
				tp.setType(st.getType());
				tp.setSampName(tp.getSampTypeName());
				tp.setPointName(tpVo.getPointName());
				tp.setPointCode(tpVo.getPointCode());
				tp.setSort(tpVo.getSort());
				taskPointDao.update(tp);
				uptSampInfo(tp);
			}
		}
		// 更新任务进度及日志
		if (!StrUtils.isNull(v.getIsCommit()) && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			if(null!=p.getXcSt()&&p.getXcSt().equals("0")) {
				//有现场项目但未现场上报的，先初始化现场项目，在已办处补录
				checkItem(p.getId());
			}
			
			boolean flag = false;// 是否有实验室项目
			Progress pg = p.getProgress();
			List<Sampling> sampList = samplingDao.listAllByTaskId(p.getId());
			for (Sampling samp : sampList) {
				if (samp.getType().equals(Sampling.SAMP_TYPE_PT)) {
					flag = true;// 是否有实验室项目
					break;
				}
			}
			if (!flag) {// 全现场项目的直接到数据审核
				pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_HZ.getStatus(), null, null, null, null);
				// 直接到汇总的初始现场项目 到汇总   到数据复核
				uptItem2Hz(p.getId());
			} else {
				// 有实验室项目的先到样品交接
				// 在交接后初始化实验室项目，任务下达后更新现场测试项目到数据汇总
				pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_JJ.getStatus(), null, null, null, null);
			}
			progressLogDao.add(p.getId(), p.getId(), EunmTask.TASK_XC.getStatus(), v.getIsCommit(), v.getCyMsg());
			p.setStatus(pg.getStatus());
			syncPoint(p);
		}
		taskDao.update(p);
	}
	public void uptSampInfo(TaskPoint tp) {
		List<Sampling> sampList=samplingDao.listAllByPointId(tp.getId());
		if(null!=sampList) {
			for (Sampling sampling : sampList) {
				sampling.setPointName(tp.getPointName());
				if(StrUtils.isBlankOrNull(sampling.getXz())) {
					sampling.setXz(sampling.getCyd().getXcDesc());
				}
				samplingDao.update(sampling);
			}
		}
	};
	//同步点位信息到立项下该任务对应的方案信息里
	public void syncPoint(Task task) {
		if(!StrUtils.isBlankOrNull(task.getSchemeId())) {
			//同步更新方案信息
			Scheme scheme=schemeDao.findById(task.getSchemeId());
			scheme.setCyDate(task.getCyDate());
			scheme.setCyEndDate(task.getCyEndDate());
			scheme.setCyUserName(task.getCyName());
			scheme.setSampTypeId(task.getSampTypeId());
			scheme.setSampTypeName(task.getSampTypeName());
			scheme.setSampName(task.getSampName());
			scheme.setSampNum(task.getSampNum());
			scheme.setItemNames(task.getItemNames());
			scheme.setPointNames(task.getPointNames());
			schemeDao.update(scheme);
			List<TaskPoint> tpList=taskPointDao.listByTaskId(task.getId());
			if(null!=tpList&&tpList.size()>0) {
				//同步更新方案点位信息
				schemePointDao.deleteBySchId(scheme.getId());
				for (TaskPoint tp : tpList) {
					SchemePoint sp=new SchemePoint();
					sp.setScheme(scheme);
					sp.setProjectId(scheme.getProject().getId());
					sp.setSampTypeId(tp.getSampTypeId());
					sp.setSampTypeName(tp.getSampTypeName());
					sp.setSampName(tp.getSampName());
					sp.setPc(tp.getPc());
					sp.setPcUnit(tp.getPcUnit());
					sp.setPointName(tp.getPointName());
					sp.setPointCode(tp.getPointCode());
					sp.setPointType(tp.getType());
					sp.setItemId(tp.getItemIds());
					sp.setItemName(tp.getItemNames());
					sp.setRoom(tp.getRoom());
					sp.setCyHours(tp.getCyHours());
					sp.setCyType(tp.getCyType());
					schemePointDao.add(sp);
					//同步更新踏勘相关表信息
					if(!StrUtils.isBlankOrNull(tp.getRoom())) {
						//车间
						CustRoom room=custRoomDao.findByName(scheme.getProject().getId(), tp.getRoom());
						if(room==null) {
							room=new CustRoom();
							room.setProjectId(scheme.getProject().getId());
							room.setCust(task.getCust());
							room.setName(tp.getRoom());
							custRoomDao.add(room);
						}
						//同步监测点
						CustPoint cp=custPointDao.findByName(room.getId(), tp.getPointName());
						if(null==cp) {
							cp=new CustPoint();
							cp.setProjectId(scheme.getProject().getId());
							cp.setRoom(room);
							cp.setCust(task.getCust());
							cp.setName(tp.getPointName());
							cp.setSampTypeId(tp.getSampTypeId());
							cp.setSampTypeName(tp.getSampTypeName());
							try {
								cp.setWorkTal(Integer.valueOf(tp.getWorkNum()));
							} catch (Exception e) {
								cp.setWorkTal(0);
							}
							cp.setItemIds(tp.getItemIds());
							cp.setItemNames(tp.getItemNames());
							cp.setWorkHours(tp.getWorkHours());
							cp.setFhName(tp.getFh());
							cp.setOthers(tp.getOthers());
							custPointDao.add(cp);
						}
					}
				}
			}
		}
	}
	// 更新现场项目到数据汇总
	public void uptItem2Hz(String taskId) {
		List<TaskItem> timList = taskItemDao.listByTaskId(taskId, TaskItem.ITEM_TYPE_XC);
		for (TaskItem tim : timList) {
			Progress pg = progressDao.add(tim.getId(), EunmTask.ITEM_JY.getStatus(), getCurrent().getOrgId(), getCurrent().getOrgName(), null, null);
			tim.setProgress(pg);
			tim.setStatus(pg.getStatus());
			tim.setOrgId(getCurrent().getOrgId());
			tim.setOrgName(getCurrent().getOrgName());
			taskItemDao.update(tim);
			progressLogDao.add(tim.getTask().getId(), tim.getId(), EunmTask.TASK_XC.getStatus(), EunmTask.PASS_Y, "【系统】现场监测项目初始化");
		}
		taskDao.updateSt(taskId);
	}

	@Override
	public void updateApp(TaskVo v, ObjVo objVo) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		if (null != v.getCustVo()) {
			Cust cust = p.getCust();
			cust.setCustJdr(v.getCustVo().getCustJdr());
			taskCustDao.update(cust);
		}
		p.setCyUserId(v.getCyUserId());
		p.setCyUserName(v.getCyUserName());
		p.setCyTime(v.getCyTime());
		p.setCyMsg(v.getCyMsg());
		p.setCyDate(v.getCyDate());
		p.setCyEndDate(v.getCyEndDate());
		p.setJcmd(v.getJcmd());
		// 更新任务进度及日志
		if (!StrUtils.isNull(v.getIsCommit()) && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			SampType sampType = sampTypeDao.findById(p.getSampTypeId());
			String type = "";
			if (sampType != null) {
				type = sampType.getType();
			}
			Progress pg = p.getProgress();
			List<Sampling> sampList = samplingDao.listAllByTaskId(p.getId());
			boolean flag = false;
			for (Sampling samp : sampList) {
				List<Item> itemList = itemDao.listByIds(samp.getItemIds(), Constants.F);
				if (null != itemList && itemList.size() > 0) {
					flag = true;
				}
			}
			if (type.equals(Constants.SAMP_S) || !flag) {// 声的直接到数据审核
				pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_HZ.getStatus(), null, null, null, null);
			} else {// 其他到样品交接
				pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_JJ.getStatus(), null, null, null, null);
			}
			progressLogDao.addApp(p.getId(), p.getId(), EunmTask.TASK_XC.getStatus(), v.getIsCommit(), v.getCyMsg(), objVo);
			p.setStatus(pg.getStatus());
			// initItem4Now(p, objVo);
		}
		taskDao.update(p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status='" + EunmTask.TASK_XC.getStatus() + "' "));
		pageResult.addCondition(new QueryCondition(" cyId like '%" + getCurrent().getAccountId() + "%'"));
		pageResult = taskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Task>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<QueryCondition> toQueryList(TaskVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		// queryList.add(new QueryCondition("type='" + EnumBus.TYPE_ZJ + "' "));
		return queryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql = new StringBuffer("SELECT distinct t FROM " + taskDao.getEntityName(Task.class) + " t," + taskDao.getEntityName(ProgressLog.class)
				+ " log where log.busId=t.id and t.isDel =" + Po.N);
		if (QueryConditionList != null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='" + EunmTask.TASK_XC.getStatus() + "' AND log.userId like '" + CurrentUtils.getCurrent().getAccountId() + "%' ");
		hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " + pageResult.getOrder() + "");
		pageResult = taskDao.getPageResult(hql.toString(), pageResult);
		gridVo.setDatas(poList2mapList((List<Task>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public boolean update4File(TaskVo v) throws GlobalException {
		Task task = taskDao.findById(v.getId());
		task.setFileName(v.getFileName());
		task.setFilePath(v.getFilePath());
		taskDao.update(task);
		return true;
	}

	// @Override
	// public TaskPointVo findTaskPoint(String pointId) throws GlobalException {
	// TaskPoint tp = taskPointDao.findById(pointId);
	// TaskPointVo tpVo = new TaskPointVo();
	// tpVo = tpVo.toVo(tp);
	// List<SamplingVo> sampVoList = new ArrayList<SamplingVo>();
	// String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE
	// isDel='" + Po.N + "' AND point.id ='" + pointId + "' AND (type='" +
	// Sampling.SAMP_TYPE_PT + "' OR type='" + Sampling.SAMP_TYPE_XN + "') ORDER BY
	// sort asc";
	// List<Sampling> sampList = samplingDao.list(hql);
	// if (null != sampList) {
	// for (Sampling samp : sampList) {
	// SamplingVo sampVo = new SamplingVo();
	// sampVo = sampVo.toVo(samp);
	// if(StrUtils.isBlankOrNull(sampVo.getSampCode())) {
	// sampVo.setSampCode(samplingDao.createSampCode(samp.getType(),samp.getSampType(),
	// tp.getTask().getReportNo(),
	// samp.getSampTypeId(),samp.getItemIds(),samp.getPoint().getSort(),
	// samp.getP()));
	// }
	// sampVoList.add(sampVo);
	// }
	// }
	// tpVo.setSampList(sampVoList);
	// List<SamplingVo> zkVoList = new ArrayList<SamplingVo>();
	// hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='"
	// + Po.N + "' AND point.id ='" + pointId + "' AND (type='" +
	// Sampling.SAMP_TYPE_PX_X + "' OR type='" + Sampling.SAMP_TYPE_KB + "') ORDER
	// BY sort asc";
	// List<Sampling> zkList = samplingDao.list(hql);
	// if (null != zkList) {
	// for (Sampling samp : zkList) {
	// SamplingVo sampVo = new SamplingVo();
	// sampVo = sampVo.toVo(samp);
	// if(StrUtils.isBlankOrNull(sampVo.getSampCode())) {
	// sampVo.setSampCode(samplingDao.createSampCode(samp.getType(),samp.getSampType(),
	// tp.getTask().getReportNo(),
	// samp.getSampTypeId(),samp.getItemIds(),samp.getPoint().getSort(),
	// samp.getP()));
	// }
	// zkVoList.add(sampVo);
	// }
	// }
	// tpVo.setZkList(zkVoList);
	// return tpVo;
	// }

	// 水 采样单数据来源处理
	// public TaskPointVo find4w(TaskPointVo tpVo,TaskPoint tp) throws
	// GlobalException {
	// List<SamplingVo> sampVoList = new ArrayList<SamplingVo>();
	// String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE
	// isDel='" + Po.N + "' AND point.id ='" + tpVo.getId() + "' ORDER BY sort asc";
	// List<Sampling> sampList = samplingDao.list(hql);
	// String startTime=null;
	// String endTime=null;
	// float sl=0;
	// float msl=0;
	// float ss=0;
	// float mss=0;
	// if (null != sampList) {
	// for (Sampling samp : sampList) {
	// SamplingVo sampVo = new SamplingVo();
	// sampVo = sampVo.toVo(samp);
	// if (sampVo.getType().equals(Sampling.SAMP_TYPE_XN)) {
	// sampVo.setSampCode("/");
	// }
	// if(StrUtils.isBlankOrNull(startTime)) {
	// startTime=sampVo.getCyTime();
	// }else if(DateUtils.getIntevalMinutes(startTime, sampVo.getCyTime())<0) {
	// startTime=sampVo.getCyTime();
	// }
	// if(StrUtils.isBlankOrNull(endTime)&&StrUtils.isNotBlankOrNull(sampVo.getCyEndTime()))
	// {
	// endTime=sampVo.getCyEndTime();
	// }else
	// if(StrUtils.isNotBlankOrNull(sampVo.getCyEndTime())&&DateUtils.getIntevalMinutes(endTime,
	// sampVo.getCyEndTime())>0) {
	// endTime=sampVo.getCyEndTime();
	// }else
	// if(StrUtils.isBlankOrNull(endTime)&&StrUtils.isNotBlankOrNull(sampVo.getCyTime()))
	// {
	// endTime=sampVo.getCyTime();
	// }else
	// if(StrUtils.isNotBlankOrNull(sampVo.getCyTime())&&DateUtils.getIntevalMinutes(endTime,
	// sampVo.getCyTime())>0) {
	// endTime=sampVo.getCyTime();
	// }
	// String cyTime=sampVo.getCyTime();
	// sampVo.setCyDate(DateUtils.getChineseDate(cyTime).substring(5));
	// String cyStr=DateUtils.getChineseDateTime(sampVo.getCyTime());
	// if(cyStr.length()>20) {
	// cyStr=cyStr.substring(0, 20).replace("上午", "").replace("下午", "");
	// }
	// if(!StrUtils.isBlankOrNull(sampVo.getCyEndTime())) {
	// String cyEndStr=DateUtils.getChineseDateTime(sampVo.getCyEndTime());
	// if(cyEndStr.length()>20) {
	// cyEndStr=cyEndStr.substring(0, 20).replace("上午", "").replace("下午", "");
	// }
	// sampVo.setCyTimeStr(cyStr+"-"+cyEndStr);
	// }else {
	// sampVo.setCyTimeStr(cyStr);
	// }
	// if(!StrUtils.isBlankOrNull(cyTime)&&cyTime.length()>=16) {
	// cyTime=cyTime.substring(11, 16);
	// }
	// sampVo.setCyTime(cyTime);
	// if(!StrUtils.isBlankOrNull(sampVo.getCyEndTime())) {
	// sampVo.setCyEndTime(sampVo.getCyEndTime().substring(11, 16));
	// }
	// sampVoList.add(sampVo);
	// if(null!=sampVo.getRecordVo()) {
	// String l=sampVo.getRecordVo().getDemo9();
	// if(StrUtils.isNotBlankOrNull(l)) {
	// try {
	// float ll=Float.valueOf(l);
	// if(ll>msl) {
	// msl=ll;
	// }
	// sl=sl+ll;
	// } catch (NumberFormatException e) {
	//
	// }
	// }
	// String s=sampVo.getRecordVo().getDemo2();
	// if(StrUtils.isNotBlankOrNull(s)) {
	// try {
	// float sss=Float.valueOf(s);
	// if(sss>mss) {
	// mss=sss;
	// }
	// ss=ss+sss;
	// } catch (NumberFormatException e) {
	//
	// }
	// }
	// }
	// }
	// }
	// tpVo.setSampList(sampVoList);
	// String cyDate=tp.getTask().getCyDate();
	// if(!StrUtils.isBlankOrNull(cyDate)) {
	// tpVo.setCyDate(DateUtils.getChineseDate(cyDate));
	// tpVo.setCyDateCode(cyDate.substring(2, 10).replace("-", ""));
	// }
	// String fzrId= tp.getTask().getFzId();
	// if(!StrUtils.isBlankOrNull(fzrId)) {
	// Account fzr = accountDao.findById(fzrId);
	// tpVo.setCyUserCode(fzr.getUser().getNo());
	// }
	// String cyTime="";
	// if(!startTime.substring(11,16).equals(endTime.substring(11,16))) {
	// cyTime=startTime.substring(11,16)+"~"+endTime.substring(11,16);
	// }else {
	// cyTime=startTime.substring(11,16);
	// }
	// tpVo.setCyTime(cyTime);
	// startTime=DateUtils.getChineseDateTime(startTime);
	// if(startTime.length()>20) {
	// startTime=startTime.substring(0, 20).replace("上午", "").replace("下午", "");
	// }
	// endTime=DateUtils.getChineseDateTime(endTime);
	// if(endTime.length()>20) {
	// endTime=endTime.substring(0, 20).replace("上午", "").replace("下午", "");
	// }
	//// tpVo.setStartTime(startTime);
	//
	// String reciveDate=tpVo.getTaskVo().getReciveDate();
	// if(!StrUtils.isBlankOrNull(reciveDate)) {
	// tpVo.getTaskVo().setReciveDate(DateUtils.getChineseDate(reciveDate));
	// }
	// return tpVo;
	// }
	// //气 采样单数据来源处理
	// public TaskPointVo find4q(TaskPointVo tpVo,TaskPoint tp) throws
	// GlobalException {
	// List<SamplingVo> sampVoList = new ArrayList<SamplingVo>();
	// String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE
	// isDel='" + Po.N + "' AND point.id ='" + tp.getId() + "' ORDER BY sort asc";
	// List<Sampling> sampList = samplingDao.list(hql);
	// String wd="";
	// String ll="";
	// String startTime=null;
	// String endTime=null;
	// String tq="";
	// String fx="";
	// String fs="";
	// if (null != sampList) {
	// for (Sampling samp : sampList) {
	// SamplingVo sampVo = new SamplingVo();
	// sampVo = sampVo.toVo(samp);
	// if (sampVo.getType().equals(Sampling.SAMP_TYPE_XN)) {
	// sampVo.setSampCode("/");
	// }
	// if(StrUtils.isBlankOrNull(startTime)) {
	// startTime=sampVo.getCyTime();
	// }else if(DateUtils.getIntevalMinutes(startTime, sampVo.getCyTime())<0) {
	// startTime=sampVo.getCyTime();
	// }
	// if(StrUtils.isBlankOrNull(endTime)&&StrUtils.isNotBlankOrNull(sampVo.getCyEndTime()))
	// {
	// endTime=sampVo.getCyEndTime();
	// }else
	// if(StrUtils.isNotBlankOrNull(sampVo.getCyEndTime())&&DateUtils.getIntevalMinutes(endTime,
	// sampVo.getCyEndTime())>0) {
	// endTime=sampVo.getCyEndTime();
	// }else
	// if(StrUtils.isBlankOrNull(endTime)&&StrUtils.isNotBlankOrNull(sampVo.getCyTime()))
	// {
	// endTime=sampVo.getCyTime();
	// }else
	// if(StrUtils.isNotBlankOrNull(sampVo.getCyTime())&&DateUtils.getIntevalMinutes(endTime,
	// sampVo.getCyTime())>0) {
	// endTime=sampVo.getCyTime();
	// }
	// String cyTime=sampVo.getCyTime();
	// sampVo.setCyDate(DateUtils.getChineseDate(cyTime).substring(5));
	// //降尘
	// if(sampVo.getSampName().contains("降尘")) {
	// try {
	// String cTime=DateUtils.getChineseDateTime(samp.getCyTime()).substring(0,
	// 20).replace("上午", "").replace("下午", "");
	// sampVo.setCyTime(cTime);
	// String eTime=DateUtils.getChineseDateTime(samp.getCyEndTime()).substring(0,
	// 20).replace("上午", "").replace("下午", "");
	// sampVo.setCyEndTime(eTime);
	// sampVo.setNum(""+DateUtils.getIntevalDays(samp.getCyTime(),
	// samp.getCyEndTime()));
	// } catch (Exception e) {
	// log.error(e.getMessage(), e);
	// }
	// }else {
	// if(!StrUtils.isBlankOrNull(cyTime)&&cyTime.length()>=16) {
	// sampVo.setCyTime(cyTime.substring(11, 16));
	// }
	// if(!StrUtils.isBlankOrNull(sampVo.getCyEndTime())) {
	// sampVo.setCyEndTime(sampVo.getCyEndTime().substring(11, 16));
	// }
	// }
	// //颗粒物
	// ItemTest it= itemTestDao.findBySampAndItem(samp.getId(),"二氧化硫");
	// if(null!=it) {
	// sampVo.setV1(it.getValue());//SO2
	// }
	// it= itemTestDao.findBySampAndItem(samp.getId(),"二氧化氮");
	// if(null!=it) {
	// sampVo.setV2(it.getValue());//NO2
	// }
	// sampVoList.add(sampVo);
	// //有组织排放废气采样 废气温度 废气流量
	// if(null!=sampVo.getRecordVo()) {
	// if(StrUtils.isBlankOrNull(wd)
	// &&StrUtils.isNotBlankOrNull(sampVo.getRecordVo().getDemo10())) {
	// wd=sampVo.getRecordVo().getDemo10();
	// }
	// if(StrUtils.isBlankOrNull(ll)
	// &&StrUtils.isNotBlankOrNull(sampVo.getRecordVo().getDemo3())) {
	// ll=sampVo.getRecordVo().getDemo3();
	// }
	// }
	// //林格曼黑度 天气、风向、风速
	// if(null!=sampVo.getRecordVo()) {
	// if(StrUtils.isBlankOrNull(tq)
	// &&StrUtils.isNotBlankOrNull(sampVo.getRecordVo().getDemo6())) {
	// tq=sampVo.getRecordVo().getDemo6();
	// }
	// if(StrUtils.isBlankOrNull(fx)
	// &&StrUtils.isNotBlankOrNull(sampVo.getRecordVo().getDemo7())) {
	// fx=sampVo.getRecordVo().getDemo7();
	// }
	// if(StrUtils.isBlankOrNull(fs)
	// &&StrUtils.isNotBlankOrNull(sampVo.getRecordVo().getDemo8())) {
	// fs=sampVo.getRecordVo().getDemo8();
	// }
	// }
	// }
	// }
	// tpVo.setSampList(sampVoList);
	//
	// String cyDate=tp.getTask().getCyDate();
	// if(!StrUtils.isBlankOrNull(cyDate)) {
	// tpVo.setCyDate(DateUtils.getChineseDate(cyDate));
	// tpVo.setCyDateCode(cyDate.substring(2, 10).replace("-", ""));
	// }
	// startTime=DateUtils.getChineseDateTime(startTime);
	// if(startTime.length()>20) {
	// startTime=startTime.substring(14, 20);
	// }
	// endTime=DateUtils.getChineseDateTime(endTime);
	// if(endTime.length()>20) {
	// endTime=endTime.substring(14, 20);
	// }
	//
	// String fzrId= tp.getTask().getFzId();
	// if(!StrUtils.isBlankOrNull(fzrId)) {
	// Account fzr = accountDao.findById(fzrId);
	// tpVo.setCyUserCode(fzr.getUser().getNo());
	// }
	// String reciveDate=tpVo.getTaskVo().getReciveDate();
	// if(!StrUtils.isBlankOrNull(reciveDate)) {
	// tpVo.getTaskVo().setReciveDate(DateUtils.getChineseDate(reciveDate));
	// }
	// return tpVo;
	// }
	// 声 采样单数据来源处理
	// public TaskPointVo find4s(TaskPointVo tpVo,TaskPoint tp) throws
	// GlobalException {
	// List<SamplingVo> sampVoList = new ArrayList<SamplingVo>();
	// String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE
	// isDel='" + Po.N + "' AND point.id ='" + tpVo.getId() + "' ORDER BY sort asc";
	// List<Sampling> sampList = samplingDao.list(hql);
	// String startTime=null;
	// String endTime=null;
	// float v=0;
	// if (null != sampList) {
	// for (Sampling samp : sampList) {
	// SamplingVo sampVo = new SamplingVo();
	// sampVo = sampVo.toVo(samp);
	// if (sampVo.getType().equals(Sampling.SAMP_TYPE_XN)) {
	// sampVo.setSampCode("/");
	// }
	// if(StrUtils.isBlankOrNull(startTime)) {
	// startTime=sampVo.getCyTime();
	// }else if(DateUtils.getIntevalMinutes(startTime, sampVo.getCyTime())<0) {
	// startTime=sampVo.getCyTime();
	// }
	// if(StrUtils.isBlankOrNull(endTime)&&StrUtils.isNotBlankOrNull(sampVo.getCyEndTime()))
	// {
	// endTime=sampVo.getCyEndTime();
	// }else
	// if(StrUtils.isNotBlankOrNull(sampVo.getCyEndTime())&&DateUtils.getIntevalMinutes(endTime,
	// sampVo.getCyEndTime())>0) {
	// endTime=sampVo.getCyEndTime();
	// }else
	// if(StrUtils.isBlankOrNull(endTime)&&StrUtils.isNotBlankOrNull(sampVo.getCyTime()))
	// {
	// endTime=sampVo.getCyTime();
	// }else
	// if(StrUtils.isNotBlankOrNull(sampVo.getCyTime())&&DateUtils.getIntevalMinutes(endTime,
	// sampVo.getCyTime())>0) {
	// endTime=sampVo.getCyTime();
	// }
	// String cyTime=sampVo.getCyTime();
	// try {
	// String standDate=cyTime.substring(0, 10)+" 20:00:00";
	// if(DateUtils.getIntevalHours(standDate, cyTime)>=0) {
	// sampVo.setV1("/");
	// sampVo.setV2(sampVo.getRecordVo().getDemo4());
	// }else {
	// sampVo.setV1(sampVo.getRecordVo().getDemo4());
	// sampVo.setV2("/");
	// }
	// } catch (Exception e1) {
	// sampVo.setV1(sampVo.getRecordVo().getDemo4());
	// sampVo.setV2("/");
	// }
	// sampVo.setCyDate(DateUtils.getChineseDate(cyTime).substring(5));
	// String cyStr=DateUtils.getChineseDateTime(sampVo.getCyTime());
	// if(cyStr.length()>20) {
	// cyStr=cyStr.substring(0, 20).replace("上午", "").replace("下午", "");
	// }
	// String cyEndStr=DateUtils.getChineseDateTime(sampVo.getCyEndTime());
	// if(cyEndStr.length()>20) {
	// cyEndStr=cyEndStr.substring(0, 20).replace("上午", "").replace("下午", "");
	// }
	// sampVo.setCyTimeStr(cyStr+"-"+cyEndStr);
	// if(!StrUtils.isBlankOrNull(cyTime)&&cyTime.length()>=16) {
	// cyTime=cyTime.substring(11, 16);
	// }
	// sampVo.setCyTime(cyTime);
	// if(!StrUtils.isBlankOrNull(sampVo.getCyEndTime())) {
	// sampVo.setCyEndTime(sampVo.getCyEndTime().substring(11, 16));
	// }
	// sampVoList.add(sampVo);
	// if(null!=sampVo.getRecordVo()) {
	// String val=sampVo.getRecordVo().getDemo4();
	// if(!StrUtils.isBlankOrNull(val)) {
	// try {
	// v+=Float.valueOf(val);
	// } catch (NumberFormatException e) {
	//
	// }
	// }
	//
	// }
	// }
	// }
	// tpVo.setSampList(sampVoList);
	// String cyDate=tp.getTask().getCyDate();
	// if(!StrUtils.isBlankOrNull(cyDate)) {
	// tpVo.setCyDate(DateUtils.getChineseDate(cyDate));
	// tpVo.setCyDateCode(cyDate.substring(2, 10).replace("-", ""));
	// }
	// String fzrId= tp.getTask().getFzId();
	// if(!StrUtils.isBlankOrNull(fzrId)) {
	// Account fzr = accountDao.findById(fzrId);
	// tpVo.setCyUserCode(fzr.getUser().getNo());
	// }
	// String cyTime="";
	// if(!startTime.substring(11,16).equals(endTime.substring(11,16))) {
	// cyTime=startTime.substring(11,16)+"~"+endTime.substring(11,16);
	// }else {
	// cyTime=startTime.substring(11,16);
	// }
	// tpVo.setCyTime(cyTime);
	// startTime=DateUtils.getChineseDateTime(startTime);
	// if(startTime.length()>20) {
	// startTime=startTime.substring(0, 20).replace("上午", "").replace("下午", "");
	// }
	// endTime=DateUtils.getChineseDateTime(endTime);
	// if(endTime.length()>20) {
	// endTime=endTime.substring(0, 20).replace("上午", "").replace("下午", "");
	// }
	// String reciveDate=tpVo.getTaskVo().getReciveDate();
	// if(!StrUtils.isBlankOrNull(reciveDate)) {
	// tpVo.getTaskVo().setReciveDate(DateUtils.getChineseDate(reciveDate));
	// }
	// return tpVo;
	// }
	public static String formatDouble(double d) {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(d);
	}

	public static String formatDouble2(double d) {
		String v = String.valueOf(d);
		String xs = v.substring(v.indexOf(".") + 1);
		DecimalFormat df = new DecimalFormat("#.#");
		if (xs.length() == 0 || Integer.valueOf(xs) == 0) {
			df = new DecimalFormat("#");
		} else if (xs.length() == 1) {
			df = new DecimalFormat("#.#");
		} else if (xs.length() == 2) {
			df = new DecimalFormat("#.##");
		} else if (xs.length() == 3) {
			df = new DecimalFormat("#.###");
		} else {
			df = new DecimalFormat("#.#");
		}
		return df.format(d);
	}

	@Override
	public boolean update4Hb(String ids) throws GlobalException {
		List<SampCyd> cydList = sampCydDao.listByIds(ids, "sort", "asc");
		SampCyd cyd = cydList.get(0);
		//获取要合并的采样单所有样品
		String jpql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND cyd.id in('" + ids.replace(",", "','") + "')";
		List<Sampling> sampList = samplingDao.list(jpql);
		try {
			//将获取的样品全部默认到第一个采样单
			for (Sampling sampling : sampList) {
				sampling.setCyd(cyd);
				samplingDao.update(sampling);
			}
			cydList.remove(cyd);
			//除过默认采样单外，删除其他要合并的采样单
			for (SampCyd sampCyd : cydList) {
				sampCydDao.delete(sampCyd);
			}
			// 更新采样单样品数量信息
			uptCyd(cyd);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update4Cf(String id) throws GlobalException {
		SampCyd old = sampCydDao.findById(id);
		//获取样品信息
		List<Sampling> sampList = samplingDao.listByCyd(old.getId());
		Task task = old.getTask();
		List<SampCyd> cydList = new ArrayList<>();
		cydList.add(old);
		for (Sampling samp : sampList) {
			EnumCyd cyd = null;
			//根据样品项查询采样单类型
			if (samp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				cyd = EnumCyd.getCyd4Hj(samp.getSampTypeName(), samp.getItemNames());
			} else {
				cyd = EnumCyd.getCyd4Zw(samp.getItemNames());
			}
			String type = null;
			if (cyd != null) {
				type = cyd.getCode();
			}
			;
			if( cyd == null) {
				continue;// 当前样品无所属模板，不拆分
			}
			if(samp.getPoint().getId().equals(old.getPointId())&&old.getType().equals(cyd.getCode())) {
				continue;//当前样品属于该采样单，不拆分
			}
			// 获取的采样单模板不属于原采样单时时，查询该任务是否有满足的采样单
			 SampCyd sampCyd=sampCydDao.find4Auto(samp.getPoint().getId(),type,samp.getCyDate());
			//SampCyd sampCyd = sampCydDao.find4Task(samp.getTask().getId(), type, samp.getCyDate());
			if (sampCyd == null) {//无符合采样单则新生成采样单
				sampCyd = new SampCyd();
				sampCyd.setTask(task);
				sampCyd.setPointId(samp.getPoint().getId());
				sampCyd.setPointName(samp.getPoint().getPointName());
				sampCyd.setItemIds(samp.getItemIds());
				sampCyd.setItemNames(samp.getItemNames());
				sampCyd.setType(type);
				sampCyd.setCyDate(samp.getCyDate());
				sampCyd.setSampType(samp.getSampType());
				sampCyd.setSampName(samp.getSampName());
				sampCyd.setCySt(String.valueOf(Po.N));
				sampCyd.setSort(sampCydDao.getMaxSort(task.getId()));
				sampCydDao.add(sampCyd);
			}
			samp.setCyd(sampCyd);//更新样品的采样单
			samplingDao.update(samp);
			cydList.add(sampCyd);
		}
		for (SampCyd cyd : cydList) {
			//检查并更新采样单样品信息
			List<Sampling> newList = samplingDao.listByCyd(cyd.getId());
			if (newList == null || newList.size() <= 0) {
				sampCydDao.delete(cyd);
			} else {
				uptCyd(cyd);
			}
		}
		return true;
	}

	public void uptCyd(SampCyd cyd) {
		List<Sampling> sampList = samplingDao.listByCyd(cyd.getId());
		Set<String> nameSet = new HashSet<String>();
		Set<String> itemIdSet = new HashSet<String>();
		Set<String> itemNameSet = new HashSet<String>();
		Set<String> roomSet = new HashSet<String>();
		Set<String> pointSet = new HashSet<String>();
		Set<String> pointIdSet = new HashSet<String>();
		for (Sampling samp : sampList) {
			nameSet.add(samp.getSampName());
			itemIdSet.addAll(Arrays.asList(samp.getItemIds().split(",")));
			itemNameSet.addAll(Arrays.asList(samp.getItemNames().split(",")));
			roomSet.add(samp.getPoint().getRoom());
			pointSet.add(samp.getPointName());
			pointIdSet.add(samp.getPoint().getId());
		}
		cyd.setRoom(String.join(",", roomSet));
		cyd.setPointId(String.join(",", pointIdSet));
		cyd.setPointName(String.join(",", pointSet));
		cyd.setSampName(String.join(",", nameSet));
		cyd.setItemIds(String.join(",", itemIdSet));
		cyd.setItemNames(String.join(",", itemNameSet));
		cyd.setSampNum(sampList.size());
		sampCydDao.update(cyd);
	}
	//检查采样单的样品数量 ，若为空 删除采样单
	public void uptCydNum(Task task) {
		// 更新采样单
		List<SampCyd> cydList = sampCydDao.listByTaskId(task.getId());
		for (SampCyd cyd : cydList) {
			List<Sampling> sampList = samplingDao.listByCyd(cyd.getId());
			if (sampList != null && sampList.size() > 0) {
				cyd.setSampNum(sampList.size());
				sampCydDao.update(cyd);
			} else {
				// 采样单无样品，删除采样单
				sampCydDao.delete(cyd);
			}
		}
	}

	@Override
	public boolean initAllCyd(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		// 更新现场情况 职卫
		List<TaskPointVo> tpVoList = v.getTpList();
		if (null != tpVoList && !p.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			for (TaskPointVo tpVo : tpVoList) {
				TaskPoint tp = taskPointDao.findById(tpVo.getId());
				tp.setWorkNum(tpVo.getWorkNum());
				tp.setWorkType(tpVo.getWorkType());
				tp.setWorkPc(tpVo.getWorkPc());
				tp.setWorkHours(tpVo.getWorkHours());
				tp.setAllItemId(tpVo.getAllItemId());
				tp.setAllItemName(tpVo.getAllItemName());
				tp.setCyType(tpVo.getCyType());
				tp.setCyHours(tpVo.getCyHours());
				tp.setFh(tpVo.getFh());
				tp.setOthers(tpVo.getOthers());
				taskPointDao.update(tp);
				uptSampInfo(tp);
			}
		}else if(null != tpVoList){
			for (TaskPointVo tpVo : tpVoList) {
				TaskPoint tp = taskPointDao.findById(tpVo.getId());
				SampType st = sampTypeDao.findById(tpVo.getSampTypeId());
				tp.setSampTypeId(st.getId());
				tp.setSampTypeName(st.getName());
				tp.setType(st.getType());
				tp.setSampName(tp.getSampTypeName());
				tp.setPointName(tpVo.getPointName());
				tp.setPointCode(tpVo.getPointCode());
				tp.setSort(tpVo.getSort());
				taskPointDao.update(tp);
				uptSampInfo(tp);
			}
		}
		// 生成采样单
		initCyd(p);
		return true;
	}

	/**
	 * 生成采样单 现场项目每个项目单独一个采样单 实验室项目根据编号 同编号一个采样单
	 * 
	 * @param p
	 */
	public void initCyd(Task p) {
		//取消所有样品的采样单属性
		List<Sampling> sampList = samplingDao.listAllByTaskId(p.getId());
		for (Sampling samp : sampList) {
			samp.setCyd(null);
			samplingDao.update(samp);
		}
		//删除原有采样单信息
		sampCydDao.deleteAll(sampCydDao.listByTaskId(p.getId()));
		//重新根据样品项目生成采样单
		String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND task.id ='" + p.getId()
				+ "' ORDER BY point.sort,sort,cyDate,p ASC";
		List<Sampling> samplist = samplingDao.list(hql);
		for (Sampling samp : samplist) {
			EnumCyd cyd = null;
			if (samp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				cyd = EnumCyd.getCyd4Hj(samp.getSampTypeName(), samp.getItemNames());
			} else {
				cyd = EnumCyd.getCyd4Zw(samp.getItemNames());
			}
			String type = null;
			if (cyd != null) {
				type = cyd.getCode();
			}
			SampCyd sampCyd=sampCydDao.find4Auto(samp.getPoint().getId(),type,samp.getCyDate());
			//SampCyd sampCyd = sampCydDao.find4Task(p.getId(), type, samp.getCyDate());
			if (sampCyd != null) {
				samp.setCyd(sampCyd);
			} else {
				sampCyd = new SampCyd();
				sampCyd.setTask(p);
				sampCyd.setPointId(samp.getPoint().getId());
				sampCyd.setPointName(samp.getPoint().getPointName());
				sampCyd.setItemIds(samp.getItemIds());
				sampCyd.setItemNames(samp.getItemNames());
				sampCyd.setType(type);
				sampCyd.setCyDate(samp.getCyDate());
				sampCyd.setSampType(samp.getSampType());
				sampCyd.setSampName(samp.getSampName());
				sampCyd.setCySt(String.valueOf(Po.N));
				sampCydDao.add(sampCyd);
				samp.setCyd(sampCyd);
			}
			samplingDao.update(samp);
		}
		List<SampCyd> cydList = sampCydDao.listByTaskId(p.getId());
		if (null != cydList) {
			int sort = 1;
			for (SampCyd cyd : cydList) {
				cyd.setSort(sort);
				uptCyd(cyd);
				sort++;
			}
		}
	};

	@Override
	public void checkItem(String taskId) throws GlobalException {
		Task task = taskDao.findById(taskId);
		List<Sampling> sampList = samplingDao.listAllByTaskId(taskId);
		for (Sampling samp : sampList) {
			// 获取项目
			List<Item> itemList = itemDao.listByIds(samp.getItemIds(), Constants.S);
			if (null != itemList) {
				for (Item item : itemList) {
					// 检查项目任务信息是否存在
					TaskItem tim = taskItemDao.find(taskId, item.getId());
					if (tim == null) {
						tim = saveTim(task, item, samp);
					} else {
						uptTim(tim, samp);
					}
					// 检查 项目的样品测试结果信息
					TestResult tr = testResultDao.findBySampAndItem(samp.getId(), item.getId());
					if (tr == null) {
						TestItem it = null;
						if (task.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
							it = saveIt(tim, samp.getPoint(), samp, item);
						} else {
							// 职卫一个点位一个项目一个采样日期====一个测试信息（n个样品的测试结果）
							// 检查是否已经有该测试信息
							it = testItemDao.find(samp.getPoint().getId(), samp.getCyDate(), item.getId());
							if (it == null) {
								it = saveIt(tim, samp.getPoint(), samp, item);
							}
						}
						tr = saveTr(it, samp);
					}
				}
			}
		}
	}

	// 保存项目任务信息
	public TaskItem saveTim(Task task, Item item, Sampling samp) {
		SampType st = sampTypeDao.findById(item.getSampTypeIds());
		TaskItem tim = new TaskItem();
		tim.setTask(task);
		tim.setItemId(item.getId());
		tim.setItemName(item.getName());
		tim.setUnit(item.getUnit());
		tim.setPrice(item.getPrice());
		tim.setType(TaskItem.ITEM_TYPE_XC);
		tim.setSampTypeId(item.getSampTypeIds());
		tim.setSampTypeName(item.getSampTypeNames());
		tim.setSt(st.getType());
		tim.setWd(samp.getCyd().getQw());
		tim.setSd(samp.getCyd().getSd());
		if (tim.getItemName().contains("粉尘")) {
			tim.setItemType(samp.getFcType());
		} else if (samp.getCyd().getType().equals("cyd_wl_jg") || samp.getCyd().getType().equals("cyd_wl_cp") || samp.getCyd().getType().equals("cyd_wl_cg")) {
			tim.setItemType(samp.getCyd().getItemType());
		}
		String sampTypeIds = sampTypeDao.findAllIds(tim.getSampTypeId());
		if (task.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			List<PstandItem> plist = pstandItemDao.listStand4Item(task.getStandIds(), item.getId());
			if (null != plist && plist.size() > 0) {
				PstandItem pit = plist.get(0);
				tim.setStandId(pit.getStandId());
				tim.setStandName(pit.getStandName());
				tim.setLimited(pit.getValStr());
			}
		} else {
			List<Pstandard> standList = pstandardDao.listBySampTyle(sampTypeIds);
			if (null != standList && standList.size() > 0) {
				Pstandard stand = standList.get(0);
				tim.setStandId(stand.getId());
				tim.setStandName(stand.getName());
				List<PstandItem> plist = pstandItemDao.listStand(stand.getId(), item.getId(), null, tim.getItemType(), null);
				if (plist != null && plist.size() > 0) {
					PstandItem pit = plist.get(0);
					// 化学有害因素标准特有
					if (pit.getSampTypeName().contains("化学")) {
						tim.setLmt(pit.getMaxValue());
						tim.setMac(pit.getValue3());
						tim.setTwa(pit.getValue());
						tim.setStel(pit.getValue2());
						if (!StrUtils.isBlankOrNull(tim.getMac())) {
							tim.setLimited("a");
						} else if (!StrUtils.isBlankOrNull(tim.getTwa()) && !StrUtils.isBlankOrNull(tim.getStel())) {
							tim.setLimited("b");
						} else if (!StrUtils.isBlankOrNull(tim.getTwa())) {
							tim.setLimited("c");
						}
					} else {
						// 物理等其他
						tim.setLimited(pit.getValStr());
					}
				}
			}
		}
		Im im = imDao.findByBusIdAndItemId(samp.getPoint().getId(), item.getId());
		if (null != im) {
			tim.setMethodId(im.getMethodId());
			tim.setMethodName(im.getMethodName());
			Method m = methodDao.findById(im.getMethodId());
			tim.setLimitLine(m.getMinLine());
		}
		tim.setAppId(samp.getCyd().getCyAppId());
		tim.setAppName(samp.getCyd().getCyAppName());
		tim.setTestMan(getCurrent().getUserName());
		tim.setTestManId(getCurrent().getAccountId());
		tim.setCheckMan(task.getFzName());
		tim.setCheckManId(task.getFzId());
		if (!StrUtils.isBlankOrNull(samp.getCyTime())) {
			tim.setTestTime(samp.getCyDate() + " " + samp.getCyTime());
		} else {
			tim.setTestTime(samp.getCyDate()+" 00:00");
		}
		if (!StrUtils.isBlankOrNull(samp.getCyEndTime())) {
			tim.setTestEndTime(samp.getCyDate() + " " + samp.getCyEndTime());
		} else {
			tim.setTestEndTime(tim.getTestTime());
		}
		tim.setIsBack(Constants.N);
		tim.setSort(taskItemDao.getMaxSort(task.getId()) + 1);
		taskItemDao.add(tim);
		return tim;
	}

	// 更新项目任务信息
	public void uptTim(TaskItem tim, Sampling samp) {
		String st = null;
		if (!StrUtils.isBlankOrNull(samp.getCyTime())) {
			st = samp.getCyDate() + " " + samp.getCyTime();
		}
		String et = null;
		if (!StrUtils.isBlankOrNull(samp.getCyEndTime())) {
			et = samp.getCyDate() + " " + samp.getCyEndTime();
		} else {
			et = st;
		}
		String ct = tim.getTestTime();
		if (!StrUtils.isBlankOrNull(st) && !StrUtils.isBlankOrNull(ct)) {
			long l = DateUtils.getIntevalMinutes(st, ct);
			if (l > 0) {
				tim.setTestTime(st);
			}
		}
		String cet = tim.getTestEndTime();
		if (!StrUtils.isBlankOrNull(et) && !StrUtils.isBlankOrNull(cet)) {
			long l = DateUtils.getIntevalMinutes(et, cet);
			if (l < 0) {
				tim.setTestEndTime(et);
			}
		}
		taskItemDao.update(tim);
	}

	// 保存测试项目
	public TestItem saveIt(TaskItem tim, TaskPoint point, Sampling samp, Item item) {
		TestItem it = new TestItem();
		it.setTask(tim.getTask());
		it.setTim(tim);
		it.setCust(tim.getTask().getCust());
		it.setPoint(point);
		it.setCyDate(samp.getCyDate());
		it.setItemId(item.getId());
		it.setItemName(item.getName());
		if (item.getParent() != null) {
			it.setLevel(2);
		} else {
			it.setLevel(1);
		}
		if( null == samp.getRecord()) {
		}else if ((item.getName().contains("一氧化碳") || item.getName().contains("二氧化碳"))) {
			it.setStel(samp.getRecord().getAvg1());
			it.setTwa(samp.getRecord().getAvg2());
		} else if ((item.getName().contains("二氧化硅含量") || item.getName().contains("超高频辐射") || item.getName().contains("工频电场") || item.getName().contains("激光辐射")
				|| item.getName().contains("微波辐射"))) {
			checkAvg(samp, item);
			it.setValue(samp.getRecord().getAvg1());
		} else if (item.getName().contains("高频电磁场")) {
			checkAvg(samp, item);
			it.setValue(samp.getRecord().getAvg1());// 电场
			it.setValue2(samp.getRecord().getAvg2());// 磁场
		} else if (item.getName().contains("紫外辐射")) {
			it.setValue(samp.getRecord().getAvg1());
		} else if ((item.getName().contains("手传振动") || item.getName().contains("噪声"))) {
			it.setValue(samp.getRecord().getAvg1());
			it.setValue2(samp.getRecord().getAvg2());
		} else if (item.getName().contains("照度")) {
			if (null != samp.getCyd().getItemType() && samp.getCyd().getItemType().equals("照度平均值")) {
				it.setValue(samp.getRecord().getAvg1());
			} else {
				it.setValue(samp.getRecord().getAvg2());
			}
		} else if (item.getName().contains("高温")) {
			double d1 = 0;
			try {
				d1 = Double.valueOf(samp.getRecord().getAvg1());
			} catch (Exception e) {
				d1 = 0;
			}
			double d2 = 0;
			try {
				d2 = Double.valueOf(samp.getRecord().getAvg2());
			} catch (Exception e) {
				d2 = 0;
			}
			double d3 = 0;
			try {
				d3 = Double.valueOf(samp.getRecord().getAvg3());
			} catch (Exception e) {
				d3 = 0;
			}
			try {
				NumberFormat ddf1 = NumberFormat.getNumberInstance();
				ddf1.setMaximumFractionDigits(1);
				it.setValue(ddf1.format((d1 + d2 + d3) / 3));
			} catch (Exception e) {
				it.setValue("");
			}
		} else if (item.getName().contains("空气比释动能率")) {
			double d1 = 0;
			try {
				d1 = Double.valueOf(samp.getRecord().getV1());
			} catch (Exception e) {
				d1 = 0;
			}
			double d2 = 0;
			try {
				d2 = Double.valueOf(samp.getRecord().getV2());
			} catch (Exception e) {
				d2 = 0;
			}
			double d3 = 0;
			try {
				d3 = Double.valueOf(samp.getRecord().getV3());
			} catch (Exception e) {
				d3 = 0;
			}
			try {
				NumberFormat ddf1 = NumberFormat.getNumberInstance();
				ddf1.setMaximumFractionDigits(1);
				it.setValue(ddf1.format((d1 + d2 + d3) / 3));
			} catch (Exception e) {
				it.setValue("");
			}
		}
		it.setResult(TaskItem.RESULT_YES);
		it.setType(TaskItem.ITEM_TYPE_XC);
		it.setIsBack(Constants.N);
		it.setSort(testItemDao.getMaxSort(tim.getId()) + 1);
		testItemDao.add(it);
		return it;
	}

	// 检查均值，为空 择自动计算
	public void checkAvg(Sampling samp, Item item) {
		SampRecord record = samp.getRecord();
		if (StrUtils.isBlankOrNull(record.getAvg1())) {
			double v1 = 0;
			if (item.getName().contains("二氧化硅含量") || item.getName().contains("超高频辐射") || item.getName().contains("工频电场") || item.getName().contains("激光辐射")
					|| item.getName().contains("高频电磁场") || item.getName().contains("微波辐射")) {
				try {
					v1 += Double.valueOf(record.getV4());
				} catch (Exception e) {
					v1 += 0;
				}
				try {
					v1 += Double.valueOf(record.getV5());
				} catch (Exception e) {
					v1 += 0;
				}
				try {
					v1 += Double.valueOf(record.getV6());
				} catch (Exception e) {
					v1 += 0;
				}
				String v = String.valueOf(v1 / 3);
				if (v.indexOf(".") > 0) {
					v = v.substring(0, v.indexOf("."));
				}
				record.setAvg1(v);
			}
		}
		if (StrUtils.isBlankOrNull(record.getAvg2())) {
			double v2 = 0;
			if (item.getName().contains("高频电磁场")) {
				try {
					v2 += Double.valueOf(record.getV10());
				} catch (Exception e) {
					v2 += 0;
				}
				try {
					v2 += Double.valueOf(record.getV11());
				} catch (Exception e) {
					v2 += 0;
				}
				try {
					v2 += Double.valueOf(record.getV12());
				} catch (Exception e) {
					v2 += 0;
				}
				String v = String.valueOf(v2 / 3);
				if (v.indexOf(".") > 0) {
					v = v.substring(0, v.indexOf("."));
				}
				record.setAvg2(v);
			}
		}
		sampRecordDao.update(record);
	}

	// 保存测试结果信息
	public TestResult saveTr(TestItem tim, Sampling samp) {
		TestResult tr = new TestResult();
		tr.setSamp(samp);
		tr.setIt(tim);
		String temp = samp.getCyd().getType();
		if (StrUtils.isBlankOrNull(temp)) {
			// 不处理
		}
		// else
		// if(temp.equals("cyd_hx_ht")||temp.equals("cyd_wl_cg")||temp.equals("cyd_wl_gp")
		// ||temp.equals("cyd_wl_wb")||temp.equals("cyd_wl_cp")||temp.equals("cyd_wl_jg"))
		// {
		// //一氧化碳采样单，照度，工频电场，微波，超高频，激光辐射
		// tr.setValue(samp.getV2());
		// }else if(temp.equals("cyd_wl_zf")) {
		// //紫外辐射
		// }
		else if (temp.equals("cyd_s_jt") || temp.equals("cyd_s_gsj") || temp.equals("cyd_s")) {
			// 环境 交通噪声，厂界 、通用模板
			tr.setValue(samp.getRecord().getV1());
		}
		tr.setSort(testResultDao.getMaxSort(tim.getId()) + 1);
		testResultDao.add(tr);
		return tr;
	}

	@Override
	public void updateBack(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		p.setCyUserId(v.getCyUserId());// 采样填报人
		p.setCyUserName(v.getCyUserName());
		p.setCyTime(v.getCyTime());
		p.setCyMsg(v.getCyMsg());
		// 更新任务进度及日志
		if (!StrUtils.isNull(v.getIsCommit()) && v.getIsCommit().equals(EunmTask.PASS_N)) {
			samplingDao.queryBysql("update v_bus_sampling set cyd_id=null where task_id ='" + p.getId() + "'").executeUpdate();
			sampCydDao.deleteByTask(p.getId());
			Progress pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_AP.getStatus(), null, null, null, null);
			progressLogDao.add(p.getId(), p.getId(), EunmTask.TASK_XC.getStatus(), EunmTask.PASS_N, v.getCyMsg());
			p.setStatus(pg.getStatus());
			p.setIsBack(Constants.Y);
		}
		taskDao.update(p);
	}

	@Override
	public void deletePoint(String pointIds) throws GlobalException {
		List<TaskPoint> tpList=taskPointDao.listByIds(pointIds);
		if(null!=tpList) {
			for (TaskPoint tp : tpList) {
				List<Sampling> sampList=samplingDao.listAllByPointId(tp.getId());
				if(null!=sampList) {
					for (Sampling samp : sampList) {
						SampRecord record=samp.getRecord();
						samplingDao.delete(samp);
						sampRecordDao.delete(record);
					}
				}
				taskPointDao.delete(tp);
			}
			uptCydNum(tpList.get(0).getTask());
		}
	}

	@Override
	public void addPoint(String taskId) throws GlobalException {
		Task p=taskDao.findById(taskId);
		TaskPoint tp = new TaskPoint();
		tp.setTask(p);
		tp.setSampType(p.getSampType());
		tp.setSampName(p.getSampName());
		if (tp.getPc() == 0) {// 新增点位
			if (p.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				tp.setPc(1);
				tp.setPcUnit(PcUnit.C.getName());
			} else {// 职业卫生默认采样3次
				tp.setPc(3);
				tp.setPcUnit(PcUnit.CT.getName());
			}
		}
		if (!p.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			tp.setCyHours(15);
			tp.setCyType(Constants.CY_DD);
		}else {
			tp.setSampName(tp.getSampTypeName());
		}
		tp.setSort(taskPointDao.getMaxSort(p.getId())+1);
		taskPointDao.add(tp);
	}

	@Override
	public void uptPoint(TaskPointVo v) throws GlobalException {
		TaskPoint	tp = taskPointDao.findById(v.getId());
		if (StrUtils.isBlankOrNull(tp.getSampTypeId())) {
			List<Item> itemList = itemDao.listByIds(v.getItemIds());
			List<String> sampTypeIdList = new ArrayList<>();
			List<String> sampTypeList = new ArrayList<>();
			for (Item item : itemList) {
				if (!sampTypeIdList.contains(item.getSampTypeIds())) {
					sampTypeIdList.add(item.getSampTypeIds());
					sampTypeList.add(item.getSampTypeNames());
				}
			}
			tp.setSampTypeId(String.join(",", sampTypeIdList));
			tp.setSampTypeName(String.join(",", sampTypeList));
		}else {
			//环境  样品名称=样品类型
			tp.setSampName(tp.getSampTypeName());
		}
		tp.setItemIds(v.getItemIds());
		tp.setItemNames(v.getItemNames());
		taskPointDao.update(tp);
		imDao.uptIm(tp.getId(), v.getImId());
		//检测是否有现场项目 更新现场数据上报状态
		List<Item> itList = itemDao.listByIds(v.getItemIds(), Constants.S);
		if (itList != null && itList.size() > 0) {
			Task task=tp.getTask();
			task.setXcSt(Task.ST_0);
			taskDao.update(task);
		}
		//删除历史样品
		List<Sampling>  sampList=samplingDao.listAllByPointId(tp.getId());
		if(sampList!=null) {
			for (Sampling samp : sampList) {
				SampRecord record=samp.getRecord();
				samp.setRecord(null);
				samplingDao.delete(samp);
				sampRecordDao.delete(record);
			}
		}
		//重新生成项目信息
		initSamp4Point(tp, tp.getTask().getCyDate(), tp.getTask().getCyEndDate());
		//更新已重置的样品里的采样单信息
		uptSamp(tp);
		// 更新采样单
		List<SampCyd> cydList = sampCydDao.listByTaskId(tp.getTask().getId());
		for (SampCyd cyd : cydList) {
			List<Sampling> thissampList = samplingDao.listByCyd(cyd.getId());
			if (thissampList != null && thissampList.size() > 0) {
				cyd.setSampNum(thissampList.size());
				uptCyd(cyd);
			} else {
				// 采样单无样品，删除采样单
				sampCydDao.delete(cyd);
			}
		}
	}
	//更新已重置的样品里的采样单信息
	public void uptSamp(TaskPoint tp) {
		//更新点位的样品采样单 属性
		String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id ='" + tp.getId()
				+ "' ORDER BY sort,cyDate,p ASC";
		List<Sampling> samplist = samplingDao.list(hql);
		for (Sampling samp : samplist) {
			EnumCyd cyd = null;
			if (samp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				cyd = EnumCyd.getCyd4Hj(samp.getSampTypeName(), samp.getItemNames());
			} else {
				cyd = EnumCyd.getCyd4Zw(samp.getItemNames());
			}
			String type = null;
			if (cyd != null) {
				type = cyd.getCode();
			}
			 SampCyd sampCyd=sampCydDao.find4Auto(samp.getPoint().getId(),type,samp.getCyDate());
			//SampCyd sampCyd = sampCydDao.find4Task(tp.getTask().getId(), type, samp.getCyDate());
			if (sampCyd != null) {
				samp.setCyd(sampCyd);
			} else {
				sampCyd = new SampCyd();
				sampCyd.setTask(tp.getTask());
				sampCyd.setPointId(samp.getPoint().getId());
				sampCyd.setPointName(samp.getPoint().getPointName());
				sampCyd.setItemIds(samp.getItemIds());
				sampCyd.setItemNames(samp.getItemNames());
				sampCyd.setType(type);
				sampCyd.setCyDate(samp.getCyDate());
				sampCyd.setSampType(samp.getSampType());
				sampCyd.setSampName(samp.getSampName());
				sampCyd.setCySt(String.valueOf(Po.N));
				sampCyd.setSort(sampCydDao.getMaxSort(tp.getTask().getId())+1);
				sampCydDao.add(sampCyd);
				samp.setCyd(sampCyd);
			}
			samplingDao.update(samp);
		}
	}
	//根据点位生成采样日期
	public void initSamp4Point(TaskPoint tp,String cyDate,String cyEndDate) throws GlobalException {
		samplingDao.deleteByPoint(tp.getId());
		if(tp.getPcUnit().equals(PcUnit.CT.getName())) {//次/每天  生成样品信息
			int n=(int) (DateUtils.getIntevalDays(cyDate, cyEndDate)+1);
			for(int i=0;i<n;i++) {
				String date=DateUtils.getNextDate(cyDate, i);
				if(tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
					initSamp4Hj(tp, date);
				}else {
					initSamp4Zw(tp, date);
				}
			}
		}else {//按次 生成样品信息
			if(tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				initSamp4Hj(tp, cyDate);
			}else {
				initSamp4Zw(tp, cyDate);
			}
		}
		String sql="select count(id) from "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND zkType='"+Sampling.SAMP_TYPE_PT+"' AND point.id = '"+tp.getId()+"' ";
		Object num=samplingDao.query(sql).getSingleResult();
		tp.setSampNum(Integer.valueOf(num.toString()));
	}
	//生成点位采样日的样品信息
	@SuppressWarnings("unchecked")
	public void initSamp4Hj(TaskPoint tp,String cyDate) {
		int ptSort = taskPointDao.getSort4Code(tp.getTask().getId(), tp.getId(), tp.getSampTypeId());
		int n=1;
		//现场项目 每个占一个样品（采样单）
		String sql="select id,name from "+tablePrefix+itemDao.getEntityName(Item.class)+" WHERE is_del='"+Po.N+"' AND id in('"+tp.getItemIds().replace(",", "','")+"') AND is_now='"+Constants.S+"' order by sort ASC";
		List<Object[]> object=itemDao.queryBySql(sql);
		if(null!=object &&object.size()>0) {
			for (Object[] obj: object) {
				for(int i=1;i<=tp.getPc();i++) {
					Sampling samp=new Sampling();
					samp.setTask(tp.getTask());
					samp.setCust(tp.getTask().getCust());
					samp.setPoint(tp);
					samp.setSampType(tp.getSampType());
					samp.setPointName(tp.getPointName());
					samp.setPointCode(tp.getPointCode());
					samp.setSampTypeId(tp.getSampTypeId());
					samp.setSampTypeName(tp.getSampTypeName());
					samp.setSampName(samp.getSampTypeName());
					samp.setCyDate(cyDate);
					samp.setItemIds(obj[0].toString());
					samp.setItemNames(obj[1].toString());
					samp.setLy(Constants.F);
					samp.setP(String.valueOf(i));//批次
					samp.setType(Sampling.SAMP_TYPE_XN);//现场监测项目 采用虚拟样 样品交接不展示
					samp.setZkType(Sampling.SAMP_TYPE_PT);
					samp.setSort(n);
					SampRecord record=new SampRecord();
					sampRecordDao.add(record);
					samp.setRecord(record);
					samp.setSampCode(samplingDao.createSampCodeHj(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP()));
					samplingDao.add(samp);
				}
				n++;
			}
		}
		//实验室项目 按样品编号归类
		sql="select code,group_concat(id),group_concat(name) from "+tablePrefix+itemDao.getEntityName(Item.class)+" WHERE is_del='"+Po.N+"' AND id in('"+tp.getItemIds().replace(",", "','")+"') AND is_now='"+Constants.F+"' group by code order by sort ASC";
		object=itemDao.queryBySql(sql);
		if(null!=object &&object.size()>0) {
			for (Object[] obj: object) {
				for(int i=1;i<=tp.getPc();i++) {
					Sampling samp=new Sampling();
					samp.setTask(tp.getTask());
					samp.setCust(tp.getTask().getCust());
					samp.setPoint(tp);
					samp.setSampType(tp.getSampType());
					samp.setPointName(tp.getPointName());
					samp.setPointCode(tp.getPointCode());
					samp.setSampTypeId(tp.getSampTypeId());
					samp.setSampTypeName(tp.getSampTypeName());
					samp.setSampName(samp.getSampTypeName());
					samp.setCyDate(cyDate);
					samp.setItemIds(obj[1].toString());
					samp.setItemNames(obj[2].toString());
					samp.setLy(Constants.F);
					samp.setSort(n);
					samp.setP(String.valueOf(i));
					if(null!=obj[0]&&!String.valueOf(obj[0]).equals("null")) {
						samp.setCode(String.valueOf(obj[0]));
					}
					samp.setType(Sampling.SAMP_TYPE_PT);
					samp.setZkType(Sampling.SAMP_TYPE_PT);
					SampRecord record=new SampRecord();
					sampRecordDao.add(record);
					samp.setRecord(record);
					samp.setSampCode(samplingDao.createSampCodeHj(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP()));
					samplingDao.add(samp);
				}
				n++;
			}
		}
	}
	//生成点位采样日的样品信息 职卫  
	@SuppressWarnings("unchecked")
	public void initSamp4Zw(TaskPoint tp,String cyDate) {
		int n=1;
		//现场项目 每个点位每个项目生成一个样品 不分批次
		String sql="select id,name,samp_type_ids,samp_type_names from "+tablePrefix+itemDao.getEntityName(Item.class)+" WHERE is_del='"+Po.N+"' AND id in('"+tp.getItemIds().replace(",", "','")+"') and name not like '%粉尘%' AND is_now='"+Constants.S+"' order by sort ASC";
		List<Object[]> object=itemDao.queryBySql(sql);
		if(null!=object &&object.size()>0) {
			for (Object[] obj: object) {
				//for(int i=1;i<=tp.getPc();i++) {
					Sampling samp=new Sampling();
					samp.setTask(tp.getTask());
					samp.setCust(tp.getTask().getCust());
					samp.setPoint(tp);
					samp.setSampType(tp.getSampType());
					samp.setPointName(tp.getPointName());
					samp.setPointCode(tp.getPointCode());
					samp.setSampTypeId(obj[2].toString());
					samp.setSampTypeName(obj[3].toString());
					samp.setSampName(tp.getSampName());
					if(StrUtils.isBlankOrNull(tp.getSampName())) {
						samp.setSampName(tp.getTask().getSampName());
					}
					samp.setCyDate(cyDate);
					samp.setItemIds(obj[0].toString());
					samp.setItemNames(obj[1].toString());
					samp.setLy(Constants.F);
					samp.setP(String.valueOf(1));//批次
					samp.setType(Sampling.SAMP_TYPE_XN);//现场监测项目 采用虚拟样 样品交接不展示
					samp.setZkType(Sampling.SAMP_TYPE_PT);
					samp.setSort(n);
					SampRecord record=new SampRecord();
					sampRecordDao.add(record);
					samp.setRecord(record);
					samp.setSampCode("/");// 职业卫生 所有现场项目样品编号打斜杠
					samplingDao.add(samp);
				//}
				n++;
			}
		}
		//粉尘 类处理
		// 实验室项目 按样品编号归类
		sql = "select code,group_concat(id),group_concat(name),group_concat(distinct samp_type_ids),group_concat(distinct samp_type_names) from " + tablePrefix
				+ itemDao.getEntityName(Item.class) + " WHERE is_del='" + Po.N + "' AND id in('" + tp.getItemIds().replace(",", "','") + "') AND  name like '%粉尘%' group by code order by sort ASC";
		object = itemDao.queryBySql(sql);
		if (null != object && object.size() > 0) {
			for (Object[] obj : object) {
				for (int i = 1; i <= tp.getPc(); i++) {
					Sampling samp = new Sampling();
					samp.setTask(tp.getTask());
					samp.setCust(tp.getTask().getCust());
					samp.setPoint(tp);
					samp.setSampType(tp.getSampType());
					samp.setPointName(tp.getPointName());
					samp.setPointCode(tp.getPointCode());
					samp.setItemIds(obj[1].toString());
					samp.setItemNames(obj[2].toString());
					samp.setSampTypeId(obj[3].toString());
					samp.setSampTypeName(obj[4].toString());
					samp.setSampName(tp.getSampName());
					if (StrUtils.isBlankOrNull(tp.getSampName())) {
						samp.setSampName(tp.getTask().getSampName());
					}
					samp.setCyDate(cyDate);
					samp.setLy(Constants.F);
					samp.setSort(n);
					samp.setP(String.valueOf(i));
					if (null != obj[0] && !String.valueOf(obj[0]).equals("null")) {
						samp.setCode(String.valueOf(obj[0]));
					}
					samp.setType(Sampling.SAMP_TYPE_XN);// 现场监测项目 采用虚拟样 样品交接不展示
					samp.setZkType(Sampling.SAMP_TYPE_PT);
					SampRecord record = new SampRecord();
					sampRecordDao.add(record);
					samp.setRecord(record);
					samp.setSampCode(samplingDao.createSampCodeZw(Sampling.SAMP_TYPE_PT, samp.getSampType(), samp.getTask().getReportNo(), samp.getItemIds(),
								samp.getPoint().getSort(), samp.getP()));
					samplingDao.add(samp);
				}
				n++;
			}
		}
		//实验室项目 按样品编号归类
		sql="select code,group_concat(id),group_concat(name),group_concat(distinct samp_type_ids),group_concat(distinct samp_type_names) from "+tablePrefix+itemDao.getEntityName(Item.class)+" WHERE is_del='"+Po.N+"' AND id in('"+tp.getItemIds().replace(",", "','")+"') and name not like '%粉尘%' AND is_now='"+Constants.F+"' group by code order by sort ASC";
		object=itemDao.queryBySql(sql);
		if(null!=object &&object.size()>0) {
			for (Object[] obj: object) {
				for(int i=1;i<=tp.getPc();i++) {
					Sampling samp=new Sampling();
					samp.setTask(tp.getTask());
					samp.setCust(tp.getTask().getCust());
					samp.setPoint(tp);
					samp.setSampType(tp.getSampType());
					samp.setPointName(tp.getPointName());
					samp.setPointCode(tp.getPointCode());
					samp.setItemIds(obj[1].toString());
					samp.setItemNames(obj[2].toString());
					samp.setSampTypeId(obj[3].toString());
					samp.setSampTypeName(obj[4].toString());
					samp.setSampName(tp.getSampName());
					if(StrUtils.isBlankOrNull(tp.getSampName())) {
						samp.setSampName(tp.getTask().getSampName());
					}
					samp.setCyDate(cyDate);
					
					samp.setLy(Constants.F);
					samp.setSort(n);
					samp.setP(String.valueOf(i));
					if(null!=obj[0]&&!String.valueOf(obj[0]).equals("null")) {
						samp.setCode(String.valueOf(obj[0]));
					}
					samp.setType(Sampling.SAMP_TYPE_PT);
					samp.setZkType(Sampling.SAMP_TYPE_PT);
					SampRecord record=new SampRecord();
					sampRecordDao.add(record);
					samp.setRecord(record);
					samp.setSampCode(samplingDao.createSampCodeZw(samp.getType(), samp.getSampType(), samp.getTask().getReportNo(), samp.getItemIds(),
							samp.getPoint().getSort(), samp.getP()));
					samplingDao.add(samp);
				}
				n++;
			}
		}
	}
}
