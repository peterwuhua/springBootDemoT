package cn.demi.bus.task.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EnumCyd;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.sample.dao.ISampContainerDao;
import cn.demi.bus.sample.dao.ISampCydDao;
import cn.demi.bus.sample.dao.ISampRecordDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampContainer;
import cn.demi.bus.sample.po.SampCyd;
import cn.demi.bus.sample.po.SampRecord;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.vo.SampContainerVo;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.bus.task.dao.ISampAppDao;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.SampApp;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.service.ITaskApService;
import cn.demi.bus.task.vo.CyObjVo;
import cn.demi.bus.task.vo.CyPlanVo;
import cn.demi.bus.task.vo.SampAppVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.init.car.dao.ICarDao;
import cn.demi.init.car.dao.ICarUseDao;
import cn.demi.init.car.po.Car;
import cn.demi.init.car.po.CarUse;
import cn.demi.init.car.vo.CarUseVo;
import cn.demi.init.sp.vo.PcUnit;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IItemMethodDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.ItemMethod;
import cn.demi.office.dao.IKqDao;
import cn.demi.pfm.dao.ISRecordDao;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.dao.IApparaOutDao;
import cn.demi.res.dao.IConsumableDao;
import cn.demi.res.po.Appara;
import cn.demi.res.po.ApparaOut;
import cn.demi.res.po.Consumable;

@Service("bus.taskApService")
public class TaskApServiceImpl extends BaseServiceImpl<TaskVo, Task> implements ITaskApService {
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
	private IApparaDao apparaDao;
	@Autowired
	private IApparaOutDao apparaOutDao;
	@Autowired
	private ICarDao carDao;
	@Autowired
	private ICarUseDao carUseDao;
	@Autowired
	private ISampContainerDao sampContainerDao;
	@Autowired
	private IItemMethodDao itemMethodDao;
	@Autowired
	private IConsumableDao consumableDao;
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private ISampRecordDao sampRecordDao;
	// @Autowired
	// private ISampTypeDao sampTypeDao;
	// @Autowired
	// private IAccountDao accountDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private ISRecordDao sRecordDao;
	@Autowired
	private IKqDao kqDao;
	@Autowired
	private ISampCydDao sampCydDao;

	/**
	 * 获取任务详细信息 包含项目、样品信息
	 */
	@Override
	public TaskVo find(String id) throws GlobalException {
		Task task = taskDao.findById(id);
		TaskVo vo = po2Vo(task);
		if (StrUtils.isBlankOrNull(vo.getReportNo())) {
			vo.setReportNo(createReportNo(task.getSampType()));
		}
		List<TaskPoint> tpList = taskPointDao.listByTaskId(task.getId());
		if (tpList != null && tpList.size() > 0) {
			List<TaskPointVo> tpVoList = new ArrayList<TaskPointVo>();
			for (TaskPoint tp : tpList) {
				TaskPointVo tpVo = new TaskPointVo();
				tpVo = tpVo.toVo(tp);
				tpVo.setSampList(getSampList(tp, vo.getReportNo()));
				tpVo.setZkList(getZkList(tp, vo.getReportNo()));
				tpVoList.add(tpVo);
			}
			vo.setTpList(tpVoList);
		}
		vo.setAppList(getAppList4Edit(id));
		vo.setCarList(getCarList(id));
		vo.setApDate(DateUtils.getCurrDateTimeStr());
		vo.setApName(getCurrent().getUserName());
		vo.setApId(getCurrent().getAccountId());
		return vo;
	}

	/**
	 * 生成合同编号
	 */
	public String createReportNo(String sampType) {
		String flag = DateUtils.getYear();
		String hql = "SELECT max(reportNo) FROM " + taskDao.getEntityName(Task.class) + " WHERE isDel=" + Po.N + " AND sampType='" + sampType
				+ "' AND reportNo like '" + flag + "%'";
		String ls = (String) taskDao.query(hql).getSingleResult();
		String no = flag;
		if (ls == null) {
			no += "001";
		} else {
			ls = ls.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort = 0;
			}
			sort++;
			if (sort < 10) {
				no += "00" + sort;
			} else if (sort < 100) {
				no += "0" + sort;
			} else {
				no += sort;
			}
		}
		return no;
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
				// tpVo.setContainerList(getContainerList(tp.getId()));
				// 获取普通样
				String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id ='" + tp.getId() + "' AND zkType='"
						+ Sampling.SAMP_TYPE_PT + "' ORDER BY sort ASC";
				List<Sampling> sampList = samplingDao.list(hql);
				List<SamplingVo> sampVoList = new ArrayList<SamplingVo>();
				for (Sampling samp : sampList) {
					SamplingVo sampVo = new SamplingVo();
					sampVo = sampVo.toVo(samp);
					sampVo.setContainerList(getContainerList(samp.getId()));
					sampVoList.add(sampVo);
				}
				tpVo.setSampList(sampVoList);
				// 获取质控样
				hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id ='" + tp.getId() + "' AND zkType='"
						+ Sampling.SAMP_TYPE_KB + "' ORDER BY sort ASC";
				List<Sampling> kbList = samplingDao.list(hql);
				// 检查平行样
				hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id ='" + tp.getId() + "' AND zkType='"
						+ Sampling.SAMP_TYPE_PX_X + "' ORDER BY sort ASC";
				List<Sampling> pxList = samplingDao.list(hql);
				kbList.addAll(pxList);
				List<SamplingVo> zkVoList = new ArrayList<SamplingVo>();
				for (Sampling samp : kbList) {
					SamplingVo sampVo = new SamplingVo();
					sampVo = sampVo.toVo(samp);
					sampVo.setContainerList(getContainerList(samp.getId()));
					zkVoList.add(sampVo);
				}
				tpVo.setZkList(zkVoList);
				tpVoList.add(tpVo);
			}
			vo.setTpList(tpVoList);
		}
		vo.setAppList(getAppList(id));
		vo.setCarList(getCarList(id));
		return vo;
	}

	// 获取普通样
	public List<SamplingVo> getSampList(TaskPoint tp, String reportNo) throws GlobalException {
		List<SamplingVo> sampVoList = new ArrayList<SamplingVo>();
		String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id ='" + tp.getId() + "' AND zkType='"
				+ Sampling.SAMP_TYPE_PT + "' ORDER BY sort,cyDate,p ASC";
		List<Sampling> sampList = samplingDao.list(hql);
		int ptSort = taskPointDao.getSort4Code(tp.getTask().getId(), tp.getId(), tp.getSampTypeId());
		for (Sampling samp : sampList) {
			SamplingVo sampVo = new SamplingVo();
			sampVo = sampVo.toVo(samp);
			if (StrUtils.isBlankOrNull(sampVo.getSampCode())) {
				if (tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
					sampVo.setSampCode(samplingDao.createSampCodeHj(reportNo, samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP()));
				} else {
					sampVo.setSampCode(samplingDao.createSampCodeZw(samp.getType(), samp.getSampType(), reportNo, samp.getItemIds(), samp.getPoint().getSort(),
							samp.getP()));
				}
			}
			sampVo.setContainerList(getContainerList(samp.getId()));
			sampVoList.add(sampVo);
		}
		return sampVoList;
	}

	// 获取质控样
	public List<SamplingVo> getZkList(TaskPoint tp, String reportNo) throws GlobalException {
		// 检查空白样
		String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id ='" + tp.getId() + "' AND zkType='"
				+ Sampling.SAMP_TYPE_KB + "' ORDER BY sort ASC";
		List<Sampling> kbList = samplingDao.list(hql);
		// 检查平行样
		hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id ='" + tp.getId() + "' AND zkType='"
				+ Sampling.SAMP_TYPE_PX_X + "' ORDER BY sort ASC";
		List<Sampling> pxList = samplingDao.list(hql);
		kbList.addAll(pxList);
		List<SamplingVo> sampVoList = new ArrayList<SamplingVo>();
		int ptSort = taskPointDao.getSort4Code(tp.getTask().getId(), tp.getId(), tp.getSampTypeId());
		for (Sampling samp : kbList) {
			SamplingVo sampVo = new SamplingVo();
			sampVo = sampVo.toVo(samp);
			if (StrUtils.isBlankOrNull(sampVo.getSampCode())) {
				sampVo.setSampCode(samplingDao.createSampCodeZk(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP(),
						samp.getZkType()));
			}
			sampVo.setContainerList(getContainerList(samp.getId()));
			sampVoList.add(sampVo);
		}
		return sampVoList;
	}

	// 采样容器信息
	public List<SampContainerVo> getContainerList(String sampId) throws GlobalException {
		List<SampContainer> cList = sampContainerDao.listBySampId(sampId);
		List<SampContainerVo> scList = new ArrayList<>();
		if (null != cList && cList.size() > 0) {
			for (SampContainer sc : cList) {
				SampContainerVo scVo = new SampContainerVo();
				scVo = scVo.toVo(sc);
				scList.add(scVo);
			}
		} else {
			Sampling samp = samplingDao.findById(sampId);
			List<ItemMethod> imList = itemMethodDao.listByItemIds(samp.getItemIds());
			Set<String> rqList = new HashSet<String>();
			for (ItemMethod item : imList) {
				if (!StrUtils.isBlankOrNull(item.getCtId())) {
					rqList.addAll(Arrays.asList(item.getCtId().split(",")));
				}
			}
			List<Consumable> clist = consumableDao.listByIds(String.join(",", rqList));
			if (null != clist) {
				for (Consumable c : clist) {
					SampContainerVo scVo = new SampContainerVo();
					scVo.setContainerId(c.getId());
					scVo.setContainer(c.getModel() + c.getName());
					scVo.setNum(1);
					scList.add(scVo);
				}
			}
		}
		return scList;
	}

	// 编辑页面 获取设备信息
	public List<SampAppVo> getAppList4Edit(String id) throws GlobalException {
		List<SampAppVo> appvoList = new ArrayList<SampAppVo>();
		List<SampApp> sctList = sampAppDao.findByBusId(id);
		if (null != sctList && sctList.size() > 0) {
			for (SampApp app : sctList) {
				SampAppVo appVo = new SampAppVo();
				appVo = appVo.toVo(app);
				appvoList.add(appVo);
			}
		} else {
			List<TaskPoint> tpList = taskPointDao.listByTaskId(id);
			String itemIds = "";
			for (TaskPoint tp : tpList) {
				itemIds += tp.getItemIds() + ",";
			}
			List<ItemMethod> imList = itemMethodDao.listByItemIds(itemIds);
			Set<String> appSet = new HashSet<String>();
			for (ItemMethod item : imList) {
				if (!StrUtils.isBlankOrNull(item.getCyAppId())) {
					appSet.addAll(Arrays.asList(item.getCyAppId().split(",")));
				}
			}
			String hql = "FROM " + apparaDao.getEntityName(Appara.class) + " WHERE isDel='" + Po.N + "' AND state='" + Appara.ST_0 + "' AND id in('"
					+ String.join("','", appSet) + "') group by name order by sort asc";
			List<Appara> appList = apparaDao.list(hql);
			for (Appara app : appList) {
				SampAppVo appVo = new SampAppVo();
				appVo.setAppId(app.getId());
				appVo.setAppName(app.getName());
				appVo.setAppCode(app.getNo());
				appVo.setBusId(id);
				appvoList.add(appVo);
			}
		}
		return appvoList;
	}

	// 设备信息
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

	// 车辆信息
	public List<CarUseVo> getCarList(String id) throws GlobalException {
		List<CarUseVo> carvoList = new ArrayList<CarUseVo>();
		List<CarUse> carList = carUseDao.findByBusId(id);
		if (null != carList && carList.size() > 0) {
			for (CarUse car : carList) {
				CarUseVo carVo = new CarUseVo();
				carVo = carVo.toVo(car);
				carvoList.add(carVo);
			}
		}
		return carvoList;
	}

	@Override
	public void update(TaskVo v) throws GlobalException {
		String cyDate = v.getCyDate();
		if (StrUtils.isNotBlankOrNull(cyDate)) {
			cyDate = cyDate.substring(0, 10);
		}
		Task p = taskDao.findById(v.getId());
		String oldDate = p.getCyDate();
		if (StrUtils.isNotBlankOrNull(oldDate)) {
			oldDate = oldDate.substring(0, 10);
		}
		p.setReportNo(v.getReportNo());
		p.setJcct(v.getJcct());
		p.setCyDate(v.getCyDate());
		p.setCyEndDate(v.getCyEndDate());
		p.setCyId(v.getCyId());
		p.setCyName(v.getCyName());
		p.setFzId(v.getFzId());
		p.setFzName(v.getFzName());
		p.setApDate(v.getApDate());
		p.setApId(v.getApId());
		p.setApName(v.getApName());
		p.setApMsg(v.getApMsg());
		//更新采样设备
		p.setCyAppIds(v.getCyAppIds());
		p.setCyAppNames(v.getCyAppNames());
		p.setCyStandIds(v.getCyStandIds());
		p.setCyStandNames(v.getCyStandNames());
		// 更新样品信息
		List<SamplingVo> sampList = v.getSampList();
		if (null != v.getZsampList()) {
			sampList.addAll(v.getZsampList());
		}
		if (null != sampList) {
			for (SamplingVo sampVo : sampList) {
				Sampling samp = samplingDao.findById(sampVo.getId());
				if (StrUtils.isNotBlankOrNull(v.getIsCommit())) {
					samp.setSampCode(sampVo.getSampCode());
				}
				samp.setSort(sampVo.getSort());
				samp.setItemIds(sampVo.getItemIds());
				samp.setItemNames(sampVo.getItemNames());
				samp.setCode(itemDao.findCode(samp.getItemIds()));// 更新样品的项目代号
				samplingDao.update(samp);
				updCt(sampVo, samp);// 更新采样容器
			}
			p.setSampNum(sampList.size());
		}
		// 更新采样设备
		updApp(v, p);
		// 更新车辆
		updCar(v, p);
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			Progress pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_ZB.getStatus(), null, null, p.getFzId(), p.getFzName());
			progressLogDao.add(p.getId(), p.getId(), EunmTask.TASK_AP.getStatus(), v.getIsCommit(), v.getApMsg());
			p.setStatus(pg.getStatus());
			p.setIsBack(Constants.N);
			// 考核超期检查
			sRecordDao.insert(p.getNo(), p.getDate(), Constants.KH_RW_CYAP);
			// 外勤单
			kqDao.insert(p.getCyId(), p.getCyDate(), p.getCyEndDate(), p.getCust().getCustName() + " 外出采样", p.getId());
			// 生成采样单
			initCyd(p);
		}
		taskDao.update(p);
	}

	/**
	 * 生成采样单 现场项目每个项目单独一个采样单 实验室项目根据编号 同编号一个采样单
	 * 
	 * @param p
	 */
	public void initCyd(Task p) {
		// 处理现场项目
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
			SampCyd sampCyd = sampCydDao.find4Auto(samp.getPoint().getId(), type, samp.getCyDate());
			// SampCyd sampCyd = sampCydDao.find4Task(samp.getTask().getId(), type,
			// samp.getCyDate());
			// SampCyd sampCyd=sampCydDao.find4Auto(samp.getPoint().getId(),
			// type,samp.getCyDate());
			// String type=null;
			// SampCyd sampCyd=sampCydDao.find4Task(samp.getTask().getId(),
			// type,samp.getCyDate());
			if (sampCyd != null) {
				samp.setCyd(sampCyd);
			} else {
				sampCyd = new SampCyd();
				sampCyd.setTask(p);
				sampCyd.setPointId(samp.getPoint().getId());
				sampCyd.setPointName(samp.getPointName());
				sampCyd.setRoom(samp.getPoint().getRoom());
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
			List<SampCyd> cydList = sampCydDao.listByTaskId(p.getId());
			if (null != cydList) {
				int sort = 1;
				for (SampCyd cyds : cydList) {
					cyds.setSort(sort);
					uptCyd(cyds);
					sort++;
				}
			}
		}
	}

	public void uptCyd(SampCyd cyd) {
		List<Sampling> sampList = samplingDao.listByCyd(cyd.getId());
		Set<String> nameSet = new HashSet<String>();
		Set<String> itemIdSet = new HashSet<String>();
		Set<String> itemNameSet = new HashSet<String>();
		Set<String> roomSet = new HashSet<String>();
		Set<String> pointSet = new HashSet<String>();
		for (Sampling samp : sampList) {
			nameSet.add(samp.getSampName());
			itemIdSet.addAll(Arrays.asList(samp.getItemIds().split(",")));
			itemNameSet.addAll(Arrays.asList(samp.getItemNames().split(",")));
			roomSet.add(samp.getPoint().getRoom());
			pointSet.add(samp.getPointName());
		}
		cyd.setRoom(String.join(",", roomSet));
		cyd.setPointName(String.join(",", pointSet));
		cyd.setSampName(String.join(",", nameSet));
		cyd.setItemIds(String.join(",", itemIdSet));
		cyd.setItemNames(String.join(",", itemNameSet));
		cyd.setSampNum(sampList.size());
		sampCydDao.update(cyd);
	}

	// 更新采样容器
	public void updCt(SamplingVo sampVo, Sampling samp) {
		sampContainerDao.deleteBySamp(samp.getId());
		List<SampContainerVo> scList = sampVo.getContainerList();
		if (null != scList) {
			for (SampContainerVo scVo : scList) {
				if (null != scVo && StrUtils.isNotBlankOrNull(scVo.getContainerId())) {
					SampContainer sc = new SampContainer();
					sc = sc.toPo(scVo, sc);
					sc.setTaskId(samp.getTask().getId());
					sc.setSampId(samp.getId());
					sc.setNo(samp.getTask().getNo());
					sc.setPointId(samp.getPoint().getId());
					sampContainerDao.add(sc);
				}
			}
		}
	}

	// 更新采样设备信息
	public void updApp(TaskVo v, Task p) {
		List<SampAppVo> appList = v.getAppList();
		sampAppDao.deleteByBusId(p.getId());
		if (null != appList) {
			for (SampAppVo appVo : appList) {
				if (null != appVo && !StrUtils.isBlankOrNull(appVo.getAppId())) {
					SampApp app = new SampApp();
					app.setBusId(p.getId());
					app.setAppId(appVo.getAppId());
					app.setAppName(appVo.getAppName());
					sampAppDao.add(app);
					// 初始化仪器待出库记录
					if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
						Appara appara = apparaDao.findById(appVo.getAppId());
						apparaOutDao.deleteByBusId(p.getId());
						if (null != app) {
							ApparaOut out = new ApparaOut();
							out.setBusId(p.getId());
							out.setAppara(appara);
							out.setCyIds(p.getCyId());
							out.setCyNames(p.getCyName());
							out.setOrgId(p.getOrgId());
							out.setOrgName(p.getOrgName());
							out.setUseTime(p.getCyDate());
							out.setBackTime(p.getCyEndDate());
							out.setStatus(ApparaOut.ST_0);
							apparaOutDao.add(out);
						}
					}
				}
			}
		}
	}

	// 更新车辆预约
	public void updCar(TaskVo v, Task p) {
		carUseDao.deleteByBusId(p.getId());
		List<CarUseVo> carList = v.getCarList();
		if (null != carList) {
			for (CarUseVo carUseVo : carList) {
				if (null == carUseVo || StrUtils.isBlankOrNull(carUseVo.getCarId())) {
					continue;
				}
				CarUse cu = new CarUse();
				cu.setOrgId(getCurrent().getDepId());
				cu.setOrgName(getCurrent().getDepName());
				cu.setUserId(p.getApId());
				cu.setUserName(p.getApName());
				cu.setDate(DateUtils.getCurrDateTimeStr());
				cu.setStartTime(p.getCyDate());
				cu.setEndTime(p.getCyEndDate());
				cu.setBusId(p.getId());
				cu.setBusNo(p.getNo());
				cu.setCarId(carUseVo.getCarId());
				Car car = carDao.findById(carUseVo.getCarId());
				cu.setCode(car.getCode());
				cu.setName(car.getName());
				cu.setRule(car.getRule());
				cu.setAuditId(car.getUserId());
				cu.setAuditName(car.getUserName());
				cu.setStatus(CarUse.ST_BC);
				cu.setNo(carUseDao.createCode());
				cu.setContent("现场采样车辆预约");
				try {
					cu.setDuration((int) DateUtils.getIntevalDays(DateUtils.parse(cu.getStartTime(), DateUtils.formatStr_yyyyMMdd),
							DateUtils.parse(cu.getEndTime(), DateUtils.formatStr_yyyyMMdd)));
				} catch (ParseException e) {
					cu.setDuration(0);
				}
				carUseDao.add(cu);
				if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
					progressDao.add(cu.getId(), EunmTask.CL_SH.getStatus(), null, null, cu.getAuditId(), cu.getAuditName());
					progressLogDao.add(cu.getId(), cu.getId(), EunmTask.CL_SQ.getStatus(), v.getIsCommit(), "现场采样车辆预约");
					cu.setStatus(CarUse.ST_SH);
					carUseDao.update(cu);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status='" + EunmTask.TASK_AP.getStatus() + "' "));
		//pageResult.addCondition(new QueryCondition("orgId like '%" +getCurrent().getOrgId() + "%' "));
		pageResult = taskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Task>) pageResult.getResultList()));
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
			@SuppressWarnings("unchecked")
			Map<String, Object> invoiceMap = (Map<String, Object>) map.get("invoiceVo");
			if (null != invoiceMap) {
				String hcj = invoiceMap.get("hcj") == null ? null : invoiceMap.get("hcj").toString();
				String payWay = invoiceMap.get("payWay") == null ? null : invoiceMap.get("payWay").toString();
				if (null != hcj && hcj.equals(Constants.S)) {
					payWay = payWay + "+优惠卷";
					invoiceMap.put("payWay", payWay);
					map.put("invoiceVo", invoiceMap);
				}
			}
			tempList.add(map);
		}
		return tempList;
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
		hql.append(" AND log.status='" + EunmTask.TASK_AP.getStatus() + "' AND log.userId like '" + CurrentUtils.getCurrent().getAccountId() + "%' ");
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
	public String findSampCode(String id, String itemIds) throws GlobalException {
		Sampling samp = samplingDao.findById(id);
		if (!samp.getZkType().equals(Sampling.SAMP_TYPE_PT)) {
			return samplingDao.createSampCodeZk(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), samp.getPoint().getSort(), samp.getP(),
					samp.getZkType());
		} else {
			if (samp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				return samplingDao.createSampCodeHj(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), samp.getPoint().getSort(),
						samp.getP());
			} else {
				return samplingDao.createSampCodeZw(samp.getType(), samp.getSampType(), samp.getTask().getReportNo(), samp.getItemIds(),
						samp.getPoint().getSort(), samp.getP());
			}
		}
	}

	@Override
	public List<CarUseVo> findDefinedCar() throws GlobalException {
		List<Car> list = getCarList(1);
		List<CarUseVo> cuList = new ArrayList<CarUseVo>();
		if (list != null && list.size() > 0) {
			Car car = list.get(0);
			CarUseVo cu = new CarUseVo();
			cu.setCarId(car.getId());
			cu.setCode(car.getCode());
			cu.setName(car.getName());
			cu.setRule(car.getRule());
			cuList.add(cu);
		}
		return cuList;
	}

	public List<Car> getCarList(int n) {
		String sd = DateUtils.getNextDate(DateUtils.getCurrDateStr(), n) + " 08:00:00";
		String ed = DateUtils.getNextDate(DateUtils.getCurrDateStr(), n) + " 18:00:00";
		// 未来 1 天内
		String jpql = "FROM " + carDao.getEntityName(Car.class) + " WHERE id not in " + "(select distinct(carId) from " + carUseDao.getEntityName(CarUse.class)
				+ " WHERE isDel='" + Po.N + "' " + "AND endTime>'" + sd + "' AND (endTime <'" + ed + "' OR startTime<'" + ed + "' AND endTime>'" + ed + "'))";
		List<Car> list = carDao.list(jpql);
		if (list == null || list.size() == 0) {
			list = getCarList(n + 1);
		}
		return list;
	}

	@Override
	public void updateYb(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		p.setCyId(v.getCyId());
		p.setCyName(v.getCyName());
		p.setApMsg(v.getApMsg());
		p.setFzId(v.getFzId());
		p.setFzName(v.getFzName());
		p.setCyStandIds(v.getCyStandIds());
		p.setCyStandNames(v.getCyStandNames());
		List<SamplingVo> sampList = v.getSampList();
		for (SamplingVo sampVo : sampList) {
			Sampling samp = samplingDao.findById(sampVo.getId());
			samp.setSampCode(sampVo.getSampCode());
			samp.setSort(sampVo.getSort());
			samplingDao.update(samp);
		}
		updApp(v, p);
		updCar(v, p);
		taskDao.update(p);
		progressDao.update(p.getProgress().getId(), EunmTask.TASK_XC.getStatus(), null, null, p.getCyId(), p.getCyName());
		progressLogDao.add(p.getId(), p.getId(), EunmTask.TASK_AP.getStatus(), Constants.PASS_S, "更新已办任务");
	}

	@Override
	public Map<String, Object> listApPlan(String taskId) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] dates = new String[7];
		for (int i = 0; i < dates.length; i++) {
			dates[i] = DateUtils.getNextDate(DateUtils.getCurrDateStr(), i);
		}
		map.put("date", dates);
		String jpql = "FROM " + taskDao.getEntityName(Task.class) + " where isDel='" + Po.N + "' AND source='" + Constants.SAMP_XC + "' AND cyEndDate>='"
				+ dates[0] + "' AND cyDate<='" + dates[6] + "'";
		if (StrUtils.isNotBlankOrNull(taskId)) {
			jpql += " AND id<>'" + taskId + "'";
		}
		jpql += " order by no asc";
		List<Task> planList = taskDao.list(jpql);
		List<CyPlanVo> planVoList = null;
		if (null != planList) {
			planVoList = new ArrayList<CyPlanVo>();
			int n = 0;
			for (Task task : planList) {
				CyPlanVo planVo = new CyPlanVo();
				planVo.setNo(task.getNo());
				planVo.setCustName(task.getCust().getCustName());
				planVo.setColor(getColor(n));
				planVo.setUser(task.getCyName());
				long startDate = DateUtils.string2Long(task.getCyDate(), DateUtils.YYYY_MM_DD);
				long endDate = DateUtils.string2Long(task.getCyEndDate(), DateUtils.YYYY_MM_DD);
				List<CyObjVo> objList = new LinkedList<CyObjVo>();
				int colSpan = 0;
				for (int i = 0; i < dates.length; i++) {
					CyObjVo obj = new CyObjVo();
					obj.setDate(dates[i]);
					long cDate = DateUtils.string2Long(dates[i], DateUtils.YYYY_MM_DD);
					if (cDate >= startDate && cDate <= endDate) {
						obj.setHas(true);
						colSpan++;
					} else {
						obj.setHas(false);
					}
					objList.add(obj);
				}
				planVo.setColSpan(colSpan);
				planVo.setTabList(objList);
				planVoList.add(planVo);
				n++;
			}
		}
		map.put("data", planVoList);
		return map;
	}

	public String getColor(int i) {
		String[] color = new String[] { "#FFFF66", "#FFCC66", "#CCFF99", "#FF3333", "#33CC33" };
		if (i < color.length) {
			return color[i];
		} else {
			i = i % color.length;
			return color[i];
		}
	}

	@Override
	public void updateBack(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		p.setApDate(v.getApDate());
		p.setApId(v.getApId());
		p.setApName(v.getApName());
		p.setApMsg(v.getApMsg());
		p.setIsBack(Constants.Y);
		p.setCyDate(null);
		p.setCyEndDate(null);
		p.setCyId(null);
		p.setCyName(null);
		p.setFzId(null);
		p.setFzName(null);
		Progress pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_DJ.getStatus(), null, null, p.getUserId(), p.getUserName());
		progressLogDao.add(p.getId(), p.getId(), EunmTask.TASK_AP.getStatus(), v.getIsCommit(), v.getApMsg());
		p.setStatus(pg.getStatus());
		taskDao.update(p);
		sRecordDao.insert(p.getNo(), p.getDate(), Constants.KH_RW_CYAP);
		// 退回清理样品记录
		samplingDao.deleteByTask(p.getId());
		// 退回清理采样单记录
		sampCydDao.deleteByTask(p.getId());
		// 退回清理仪器预约记录
		apparaOutDao.deleteByBusId(p.getId());
		sampAppDao.deleteByBusId(p.getId());
		// 退回清楚车辆预约记录
		List<CarUse> carulist = carUseDao.findByBusId(p.getId());
		if (null != carulist) {
			for (CarUse carUse : carulist) {
				progressDao.deleteByBusId(carUse.getId());
				carUseDao.delete(carUse);
			}
		}
	}

	/**
	 * 获取任务详细信息 包含项目、样品信息
	 */
	@Override
	public TaskVo find4Batch(String ids) throws GlobalException {
		TaskVo vo = new TaskVo();
		vo.setIds(ids);
		List<Task> tasklist = taskDao.listByIds(ids, "no", "asc");
		List<TaskPointVo> tpVoList = new ArrayList<TaskPointVo>();
		List<String> noList = new ArrayList<String>();
		int n = 0;
		for (Task task : tasklist) {
			if (StrUtils.isBlankOrNull(task.getReportNo())) {
				task.setReportNo(createReportNo(task.getSampType()));
				taskDao.update(task);
			}
			List<TaskPoint> tpList = taskPointDao.listByTaskId(task.getId());
			if (tpList != null && tpList.size() > 0) {
				for (TaskPoint tp : tpList) {
					TaskPointVo tpVo = new TaskPointVo();
					tpVo = tpVo.toVo(tp);
					tpVo.setSampList(getSampList(tp, task.getReportNo()));
					tpVo.setZkList(getZkList(tp, task.getReportNo()));
					tpVoList.add(tpVo);
				}
			}
			noList.add(task.getNo());
			if (n == 0) {
				vo.setAppList(getAppList4Edit(task.getId()));
				vo.setCarList(getCarList(task.getId()));
				vo.setCyDate(task.getCyDate());
				vo.setCyEndDate(task.getCyEndDate());
				vo.setFzId(task.getFzId());
				vo.setFzName(task.getFzName());
				vo.setCyId(task.getCyId());
				vo.setCyName(task.getCyName());
				vo.setJcct(task.getJcct());
			}
			n++;
		}
		vo.setNo(String.join(",", noList));
		vo.setTpList(tpVoList);
		return vo;
	}

	@Override
	public void updateBatch(TaskVo v) throws GlobalException {
		// 更新样品信息
		List<SamplingVo> sampList = v.getSampList();
		if (null != v.getZsampList()) {
			sampList.addAll(v.getZsampList());
		}
		if (null != sampList) {
			for (SamplingVo sampVo : sampList) {
				Sampling samp = samplingDao.findById(sampVo.getId());
				if (StrUtils.isNotBlankOrNull(v.getIsCommit())) {
					samp.setSampCode(sampVo.getSampCode());
				}
				samp.setP(sampVo.getP());
				samp.setSort(sampVo.getSort());
				samp.setItemIds(sampVo.getItemIds());
				samp.setItemNames(sampVo.getItemNames());
				samp.setCode(itemDao.findCode(samp.getItemIds()));
				samp.setSaveHour(itemDao.getMaxHours(sampVo.getItemIds()));
				samplingDao.update(samp);
				updCt(sampVo, samp);
			}
		}
		// 更新点位频次信息
		List<TaskPointVo> tpList = v.getTpList();
		for (TaskPointVo tpVo : tpList) {
			TaskPoint tp = taskPointDao.findById(tpVo.getId());
			// tp.setJhId(v.getFzId());
			// tp.setJhName(v.getFzName());
			List<Sampling> lt = samplingDao.listByPointId(tp.getId());
			tp.setSampNum(lt.size());
			tp.setZkNum(samplingDao.findNumByPoinId(tp.getId(), Sampling.SAMP_TYPE_KB));
			tp.setPxNum(samplingDao.findNumByPoinId(tp.getId(), Sampling.SAMP_TYPE_PX_X));
			taskPointDao.update(tp);
		}
		// 更新任务信息
		List<Task> taskList = taskDao.listByIds(v.getIds());
		for (Task p : taskList) {
			p.setJcct(v.getJcct());
			p.setCyDate(v.getCyDate());
			p.setCyEndDate(v.getCyEndDate());
			p.setCyId(v.getCyId());
			p.setCyName(v.getCyName());
			p.setFzId(v.getFzId());
			p.setFzName(v.getFzName());
			p.setApDate(v.getApDate());
			p.setApId(v.getApId());
			p.setApName(v.getApName());
			p.setApMsg(v.getApMsg());
			p.setSampNum(samplingDao.listByTaskId(p.getId()).size());
			updApp(v, p);
			updCar(v, p);
			if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
				Progress pg = progressDao.update(p.getProgress().getId(), EunmTask.TASK_ZB.getStatus(), null, null, p.getFzId(), p.getFzName());
				progressLogDao.add(p.getId(), p.getId(), EunmTask.TASK_AP.getStatus(), v.getIsCommit(), v.getApMsg());
				p.setStatus(pg.getStatus());
				// 考核超期检查
				sRecordDao.insert(p.getNo(), p.getDate(), Constants.KH_RW_CYAP);
				// 外勤单
				kqDao.insert(p.getCyId(), p.getCyDate(), p.getCyEndDate(), p.getCust().getCustName() + " 外出采样", p.getId());
			}
			taskDao.update(p);
		}
	}

	@Override
	public void update4Samp(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		String sd = p.getCyDate();
		String ed = p.getCyEndDate();
		String newSd = v.getCyDate();
		String newEd = v.getCyEndDate();
		p.setCyDate(v.getCyDate());
		p.setCyEndDate(v.getCyEndDate());
		p.setReportNo(v.getReportNo());
		taskDao.update(p);
		if (!StrUtils.isBlankOrNull(newSd) && !StrUtils.isBlankOrNull(newEd)
				&& (StrUtils.isBlankOrNull(sd) || StrUtils.isBlankOrNull(ed) || !newSd.equals(sd) || !newEd.equals(ed))) {
			// 采样日期更新 重置样品信息
			initSamp4Date(p, newSd, newEd);
		}else {
			List<TaskPointVo> tpList = v.getTpList();
			if (null != tpList) {
				for (TaskPointVo tpVo : tpList) {
					TaskPoint tp = taskPointDao.findById(tpVo.getId());
					if (!tp.getPcUnit().equals(tpVo.getPcUnit())) {
						tp.setPcUnit(tpVo.getPcUnit());
						// 更新该点位下样品
						initSamp4Point(tp, newSd, newEd);
						taskPointDao.update(tp);
					}
				}
			}
		}
	}

	// 根据此采样日期生成 外采 样品
	public void initSamp4Date(Task p, String cyDate, String cyEndDate) throws GlobalException {
		samplingDao.deleteByTask(p.getId());
		List<TaskPoint> tpList = taskPointDao.listByTaskId(p.getId());
		if (null != tpList) {
			for (TaskPoint tp : tpList) {
				initSamp4Point(tp, cyDate, cyEndDate);
				taskPointDao.update(tp);
			}
		}
	}

	// 根据点位生成采样日期
	public void initSamp4Point(TaskPoint tp, String cyDate, String cyEndDate) throws GlobalException {
		samplingDao.deleteByPoint(tp.getId());
		if (tp.getPcUnit().equals(PcUnit.CT.getName())) {// 次/每天 生成样品信息
			int n = (int) (DateUtils.getIntevalDays(cyDate, cyEndDate) + 1);
			for (int i = 0; i < n; i++) {
				String date = DateUtils.getNextDate(cyDate, i);
				if (tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
					initSamp4Hj(tp, date);
				} else {
					initSamp4Zw(tp, date);
				}
			}
		} else {// 按次 生成样品信息
			if (tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				initSamp4Hj(tp, cyDate);
			} else {
				initSamp4Zw(tp, cyDate);
			}
		}
		String sql = "select count(id) from " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND zkType='" + Sampling.SAMP_TYPE_PT
				+ "' AND point.id = '" + tp.getId() + "' ";
		Object num = samplingDao.query(sql).getSingleResult();
		tp.setSampNum(Integer.valueOf(num.toString()));
	}

	// 生成点位采样日的样品信息 职卫
	@SuppressWarnings("unchecked")
	public void initSamp4Zw(TaskPoint tp, String cyDate) {
		int n = 1;
		// 现场项目 每个点位每个项目生成一个样品 不分批次
		String sql = "select id,name,samp_type_ids,samp_type_names from " + tablePrefix + itemDao.getEntityName(Item.class) + " WHERE is_del='" + Po.N
				+ "' AND id in('" + tp.getItemIds().replace(",", "','") + "') and name not like '%粉尘%' AND is_now='" + Constants.S + "' order by sort ASC";
		List<Object[]> object = itemDao.queryBySql(sql);
		if (null != object && object.size() > 0) {
			for (Object[] obj : object) {
				// for(int i=1;i<=tp.getPc();i++) {
				Sampling samp = new Sampling();
				samp.setTask(tp.getTask());
				samp.setCust(tp.getTask().getCust());
				samp.setPoint(tp);
				samp.setSampType(tp.getSampType());
				samp.setPointName(tp.getPointName());
				samp.setPointCode(tp.getPointCode());
				samp.setSampTypeId(obj[2].toString());
				samp.setSampTypeName(obj[3].toString());
				samp.setSampName(tp.getSampName());
				if (StrUtils.isBlankOrNull(tp.getSampName())) {
					samp.setSampName(tp.getTask().getSampName());
				}
				samp.setCyDate(cyDate);
				samp.setItemIds(obj[0].toString());
				samp.setItemNames(obj[1].toString());
				samp.setLy(Constants.F);
				samp.setP(String.valueOf(1));// 批次
				samp.setType(Sampling.SAMP_TYPE_XN);// 现场监测项目 采用虚拟样 样品交接不展示
				samp.setZkType(Sampling.SAMP_TYPE_PT);
				samp.setSort(n);
				SampRecord record = new SampRecord();
				sampRecordDao.add(record);
				samp.setRecord(record);
				samp.setSampCode("/");// 职业卫生 所有现场项目样品编号打斜杠
				samplingDao.add(samp);
				// }
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
		// 实验室项目 按样品编号归类
		sql = "select code,group_concat(id),group_concat(name),group_concat(distinct samp_type_ids),group_concat(distinct samp_type_names) from " + tablePrefix
				+ itemDao.getEntityName(Item.class) + " WHERE is_del='" + Po.N + "' AND id in('" + tp.getItemIds().replace(",", "','") + "') AND is_now='"
				+ Constants.F + "'  AND  name not like '%粉尘%' group by code order by sort ASC";
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
					samp.setType(Sampling.SAMP_TYPE_PT);
					samp.setZkType(Sampling.SAMP_TYPE_PT);
					SampRecord record = new SampRecord();
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

	// 生成点位采样日的样品信息
	@SuppressWarnings("unchecked")
	public void initSamp4Hj(TaskPoint tp, String cyDate) {
		int ptSort = taskPointDao.getSort4Code(tp.getTask().getId(), tp.getId(), tp.getSampTypeId());
		int n = 1;
		// 现场项目 每个占一个样品（采样单）
		String sql = "select id,name from " + tablePrefix + itemDao.getEntityName(Item.class) + " WHERE is_del='" + Po.N + "' AND id in('"
				+ tp.getItemIds().replace(",", "','") + "') AND is_now='" + Constants.S + "' order by sort ASC";
		List<Object[]> object = itemDao.queryBySql(sql);
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
					samp.setSampTypeId(tp.getSampTypeId());
					samp.setSampTypeName(tp.getSampTypeName());
					samp.setSampName(tp.getSampName());
					if (StrUtils.isBlankOrNull(tp.getSampName())) {
						samp.setSampName(tp.getTask().getSampName());
					}
					samp.setCyDate(cyDate);
					samp.setItemIds(obj[0].toString());
					samp.setItemNames(obj[1].toString());
					samp.setLy(Constants.F);
					samp.setP(String.valueOf(i));// 批次
					samp.setType(Sampling.SAMP_TYPE_XN);// 现场监测项目 采用虚拟样 样品交接不展示
					samp.setZkType(Sampling.SAMP_TYPE_PT);
					samp.setSort(n);
					SampRecord record = new SampRecord();
					sampRecordDao.add(record);
					samp.setRecord(record);
//					if(samp.getTask().getReportNo() == null) {
//						samp.getTask().setReportNo(createReportNo(samp.getTask().getSampType()));
//					}
					samp.setSampCode(samplingDao.createSampCodeHj(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP()));
					samplingDao.add(samp);
				}
				n++;
			}
		}
		// 实验室项目 按样品编号归类
		sql = "select code,group_concat(id),group_concat(name) from " + tablePrefix + itemDao.getEntityName(Item.class) + " WHERE is_del='" + Po.N
				+ "' AND id in('" + tp.getItemIds().replace(",", "','") + "') AND is_now='" + Constants.F + "' group by code order by sort ASC";
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
					samp.setSampTypeId(tp.getSampTypeId());
					samp.setSampTypeName(tp.getSampTypeName());
					samp.setSampName(tp.getSampName());
					if (StrUtils.isBlankOrNull(tp.getSampName())) {
						samp.setSampName(tp.getTask().getSampName());
					}
					samp.setCyDate(cyDate);
					samp.setItemIds(obj[1].toString());
					samp.setItemNames(obj[2].toString());
					samp.setLy(Constants.F);
					samp.setSort(n);
					samp.setP(String.valueOf(i));
					if (null != obj[0] && !String.valueOf(obj[0]).equals("null")) {
						samp.setCode(String.valueOf(obj[0]));
					}
					samp.setType(Sampling.SAMP_TYPE_PT);
					samp.setZkType(Sampling.SAMP_TYPE_PT);
					SampRecord record = new SampRecord();
					sampRecordDao.add(record);
					samp.setRecord(record);
//					if(samp.getTask().getReportNo() == null) {
//						samp.getTask().setReportNo(createReportNo(samp.getTask().getSampType()));
//					}
					samp.setSampCode(samplingDao.createSampCodeHj(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP()));
					samplingDao.add(samp);
				}
				n++;
			}
		}
	}

	@Override
	public boolean update4SampAll(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		List<TaskPoint> tpList = taskPointDao.listByTaskId(p.getId());
		for (TaskPoint tp : tpList) {
			tp.setPc(1);
			tp.setPcUnit(PcUnit.C.getName());
			if (!p.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				tp.setPc(3);
				tp.setPcUnit(PcUnit.CT.getName());
			}
			tp.setSampNum(0);
			tp.setZkNum(0);
			tp.setPxNum(0);
			taskPointDao.update(tp);
		}
		if (!StrUtils.isBlankOrNull(p.getCyDate()) && !StrUtils.isBlankOrNull(p.getCyEndDate())) {
			initSamp4Date(p, p.getCyDate(), p.getCyEndDate());
			return true;
		} else {
			return false;
		}
	}
}
