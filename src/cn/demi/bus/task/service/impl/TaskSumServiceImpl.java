package cn.demi.bus.task.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Files;
import cn.demi.base.system.vo.FilesVo;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.report.dao.IReportDetailDao;
import cn.demi.bus.report.dao.IReportDetailResultDao;
import cn.demi.bus.report.po.Report;
import cn.demi.bus.report.po.ReportDetail;
import cn.demi.bus.report.po.ReportDetailResult;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.service.ITaskSumService;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.bus.test.vo.TestItemVo;
import cn.demi.bus.test.vo.TestResultVo;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IPstandardDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.Pstandard;

@Service("bus.taskSumService")
public class TaskSumServiceImpl extends BaseServiceImpl<TaskVo, Task> implements ITaskSumService {
	@Autowired
	private IFilesDao filesDao;
	@Autowired
	private ITestResultDao testResultDao;
	@Autowired
	private ITestItemDao testItemDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IReportDao reportDao;
	@Autowired
	private IReportDetailDao reportDetailDao;
	@Autowired
	private IReportDetailResultDao reportDetailResultDao;
	@Autowired
	private ITaskPointDao taskPointDao;
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private IPstandardDao pstandardDao;
	@Autowired
	private IOrgDao orgDao;

	@Override
	public TaskVo findById(String id) throws GlobalException {
		Task task = taskDao.findById(id);
		TaskVo vo = po2Vo(task);
		List<TaskItem> pList = taskItemDao.listByTaskId(vo.getId());
		List<TaskItemVo> vList = new ArrayList<TaskItemVo>();
		for (TaskItem tim : pList) {
			TaskItemVo timVo = new TaskItemVo();
			timVo = timVo.toVo(tim);
			timVo.setItemList(list4Item(tim.getId()));
			if (null != timVo.getStatus() && timVo.getStatus().equals(EunmTask.ITEM_LR.getStatus())) {
				timVo.setStatus("待检测-" + timVo.getTestMan());
				timVo.setColor("waitTd");
			} else if (null != timVo.getStatus() && timVo.getStatus().equals(EunmTask.ITEM_JY.getStatus())) {
				timVo.setStatus("待复核-" + timVo.getCheckMan());
				timVo.setColor("waitTd");
			} else if (null != timVo.getStatus() && timVo.getStatus().equals(EunmTask.ITEM_FP.getStatus())) {
				timVo.setStatus("未分配");
				timVo.setColor("waitTd");
			} else {
				timVo.setColor("compTd");
			}
			// 附件
			List<Files> fList = filesDao.listByBusid(timVo.getId());
			List<FilesVo> fileList = new ArrayList<FilesVo>();
			if (null != fList) {
				for (Files f : fList) {
					FilesVo fVo = new FilesVo();
					fVo = fVo.toVo(f);
					fileList.add(fVo);
				}
			}
			timVo.setFileList(fileList);
			vList.add(timVo);
		}
		vo.setTimList(vList);
		return vo;
	}

	// 获取测试任务下的测试项目集合
	public List<TestItemVo> list4Item(String timId) throws GlobalException {
		List<TestItem> itList = testItemDao.listByTimId(timId);
		List<TestItemVo> itVoList = new ArrayList<>();
		if (null != itList) {
			for (TestItem it : itList) {
				TestItemVo itVo = new TestItemVo();
				itVo = itVo.toVo(it);
				if (it.getTask().getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
					itVo.setTrVo(findResult(it.getId()));
				} else if (it.getType().equals(TaskItem.ITEM_TYPE_XC) && !it.getItemName().contains("粉尘")) {
					itVo.setTrVo(findResult(it.getId()));
				} else {
					itVo.setTrList(list4Result(it.getId()));
				}
				itVoList.add(itVo);
			}
		}
		return itVoList;
	}

	// 获取测试项目的测试结果集合
	public List<TestResultVo> list4Result(String itId) throws GlobalException {
		List<TestResult> rList = testResultDao.listByItId(itId);
		List<TestResultVo> rVoList = new ArrayList<TestResultVo>();
		if (null != rList) {
			for (TestResult r : rList) {
				TestResultVo rVo = new TestResultVo();
				rVo = rVo.toVo(r);
				rVoList.add(rVo);
			}
		}
		return rVoList;
	}

	// 获取测试项目的结果信息
	public TestResultVo findResult(String itId) throws GlobalException {
		List<TestResult> rList = testResultDao.listByItId(itId);
		TestResultVo rVo = new TestResultVo();
		if (null != rList && rList.size() > 0) {
			rVo = rVo.toVo(rList.get(0));
		}
		return rVo;
	}

	@Override
	public void update(TaskVo v) throws GlobalException {
		Task task = taskDao.findById(v.getId());
		if (v.getIsCommit().equals(EunmTask.PASS_Y)) {
			task.setSumDate(v.getSumDate());
			task.setSumUser(v.getSumUser());
			task.setSumUserId(v.getSumUserId());
			task.setSumMsg(v.getSumMsg());
			// 插入任务进度日志
			Progress pg = progressDao.update(task.getProgress().getId(), EunmTask.TASK_BZ.getStatus(), null,null, null, null);
			task.setStatus(pg.getStatus());
			taskDao.update(task);
			uptItemSt(v);
			initReport(task, v);
			progressLogDao.add(task.getId(), task.getId(), EunmTask.TASK_HZ.getStatus(), v.getIsCommit(), v.getSumMsg());
		}
	}

	// 更新所有的项目任务为已完成
	public void uptItemSt(TaskVo v) {
		List<TaskItem> timList = taskItemDao.listByTaskId(v.getId());
		Progress pg = null;
		for (TaskItem tim : timList) {
			progressLogDao.add(v.getId(), tim.getId(), EunmTask.ITEM_HZ.getStatus(), EunmTask.PASS_Y, v.getSumMsg());
			pg = progressDao.update(tim.getProgress().getId(), EunmTask.TASK_END.getStatus(), null, null, null, null);
			tim.setStatus(pg.getStatus());
			taskItemDao.update(tim);
		}
	}

	public String createReportNo(String sampType, String type, String no) {
		if (sampType.equals(EnumBus.SAMP_TYPE_ZW)) {
			return no;
		} else if (sampType.equals(EnumBus.SAMP_TYPE_GW)) {
			return "（公）" + no;
		} else {
			return "中环（" + type + "）第" + no + "号";
		}
	}

	// 初始化报告
	public void initReport(Task task, TaskVo v) throws GlobalException {
		Report report = reportDao.findByTaskId(task.getId());
		if (report == null) {
			report = new Report();
		}
		report.setTask(task);
		report.setCust(task.getCust());
		report.setSampType(task.getSampType());
		report.setSampName(task.getSampName());
		report.setTaskType(task.getTaskType());
		report.setFinishDate(task.getFinishDate());
		report.setOrgId(task.getOrgId());
		report.setOrgName(task.getOrgName());
		report.setIsBack(Constants.N);
		report.setProgress(task.getProgress());
		report.setStatus(task.getStatus());
		// report.setMakeUser(v.getMakeUser());
		// report.setMakeUserId(v.getMakeUserId());
		if (task.getPj().equals(Constants.S))// 是否评价为是，才更新评价标准
		{
			uptStand(report, task);
		}
		if (task.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			initReport4Hj(report, task);
		} else {
			initReport4Zw(report, task);
		}
	}

	// 更新评价标准
	public void uptStand(Report report, Task task) {
		List<TaskItem> timList = taskItemDao.listByTaskId(task.getId());
		Set<String> standSet = new HashSet<String>();
		for (TaskItem tim : timList) {
			standSet.addAll(Arrays.asList(tim.getStandId().split(",")));
		}
		List<Pstandard> pstandList = pstandardDao.listByIds(String.join(",", standSet));
		List<String> idList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		if (null != pstandList) {
			for (Pstandard pstandard : pstandList) {
				idList.add(pstandard.getId());
				nameList.add(pstandard.getName() + " " + pstandard.getCode());
			}
		}
		report.setStandIds(String.join(",", idList));
		report.setStandNames(String.join(",", nameList));
	}

	// 初始化环节报告
	public void initReport4Hj(Report report, Task task) {
		report.setSampTypeId(task.getSampTypeId());
		report.setSampTypeName(task.getSampTypeName());
		String type = "综";
		if (!task.getSampTypeId().contains(",")) {
			SampType sampType = sampTypeDao.findById(task.getSampTypeId());
			type = sampType.getType();
		}
		report.setReportNo(createReportNo(task.getSampType(), type, task.getReportNo()));
		if (report.getTaskType().equals(EnumBus.HP.getName())) {
			report.setTemplate("bus-report-h-p.ftl");// 环评
		} else if (report.getTaskType().equals(EnumBus.BD.getName())) {
			SampType sampType;
			List<SampType> sampTypeList = sampTypeDao.listByIds(task.getSampTypeId());
			sampType = sampTypeList.get(0);
			if (sampType.getType().equals(Constants.SAMP_W)) {
				report.setTemplate("bus-report-h-bd-s.ftl");// 比对 水
			} else if (sampType.getType().equals(Constants.SAMP_Q)) {
				report.setTemplate("bus-report-h-bd-q.ftl");// 比对 气
			}
		} else {
			/**
			 * 验收 委托（送样和采样部分字段有差异， 有组织气 整个tab按日期循环， 无组织气 结果部分按日期纵向循环 水按日期 按点位 整个tab循环） 声
			 * 按日期循环 数据块
			 */
			report.setTemplate("bus-report-h.ftl");// 其他 综合
		}
		reportDao.saveOrUpdate(report);
		initReportDetail(report, task);
		reportDao.update(report);
	}

	// 初始化环境报告详情
	public void initReportDetail(Report report, Task task) {
		reportDetailDao.deleteByReportId(report.getId());
		List<TaskPoint> tpList = taskPointDao.listByTaskId(task.getId());
		int sort = 1;
		String startDate = null;// 测试日期
		String endDate = null;
		String cyDate = null;// 采样日期
		String cyEndDate = null;
		for (TaskPoint tp : tpList) {
			String hql = "FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel='" + Po.N + "' AND point.id ='" + tp.getId() + "' AND zkType='"
					+ Sampling.SAMP_TYPE_PT + "' ORDER BY cyDate,sort asc";
			List<Sampling> sampList = samplingDao.list(hql);
			for (Sampling samp : sampList) {
				hql = "FROM " + testResultDao.getEntityName(TestResult.class) + " WHERE isDel='" + Po.N + "' AND samp.id ='" + samp.getId()
						+ "' ORDER BY sort asc";
				List<TestResult> trList = testResultDao.list(hql);
				for (TestResult tr : trList) {
					ReportDetail detial = new ReportDetail();
					detial.setReportId(report.getId());
					detial.setPointId(tp.getId());
					detial.setPointName(tp.getPointName());
					detial.setType(tp.getType());
					detial.setSamplingId(samp.getId());
					detial.setCyDate(samp.getCyDate());
					if (StrUtils.isBlankOrNull(samp.getCyEndTime())) {
						detial.setCyTime(samp.getCyTime());
					} else {
						detial.setCyTime(samp.getCyTime() + "~" + samp.getCyEndTime());
					}
					detial.setCyHours(samp.getCyHours());
					detial.setSampTypeId(samp.getSampTypeId());
					detial.setSampTypeName(samp.getSampTypeName());
					if (samp.getSampTypeName().contains("噪声")) {
						// 噪声 名称存 昼间夜间
						detial.setSampName(samp.getFcType());
					} else {
						detial.setSampName(samp.getSampName());
					}
					detial.setSampCode(samp.getSampCode());
					detial.setP(samp.getP());
					detial.setXz(samp.getXz());
					detial.setStype(samp.getZkType());
					TestItem it = tr.getIt();
					TaskItem tim = it.getTim();
					detial.setTim(tim);
					detial.setItemId(it.getItemId());
					detial.setItemName(it.getItemName());
					detial.setLevel(it.getLevel());
					detial.setValue(tr.getValue());
					detial.setValue2(tr.getValue2());
					detial.setSl(tr.getSl());
					if (detial.getSampTypeName().contains("有组织废气") && StrUtils.isBlankOrNull(detial.getSl())) {
						// 计算有组织废气 速率
						try {
							float nd = Float.valueOf(detial.getValue());
							float ll = Float.valueOf(samp.getRecord().getDemo1());
							detial.setSl(String.valueOf(nd * ll / 1000000));
						} catch (Exception e) {
							detial.setSl(null);
						}
					}
					detial.setUnit(tim.getUnit());
					detial.setUnit2(tim.getSlUnit());
					detial.setLimitLine(tim.getLimitLine());
					detial.setLimited(tim.getLimited());
					detial.setStandId(tim.getStandId());
					detial.setStandName(tim.getStandName());
					detial.setResult(it.getResult());
					detial.setMethodId(tim.getMethodId());
					detial.setMethodName(tim.getMethodName());
					detial.setAppId(tim.getAppId());
					detial.setAppName(tim.getAppName());
					detial.setOrgId(tim.getDeptId());
					detial.setOrgName(tim.getDeptName());
					detial.setTestTime(tim.getTestTime());
					detial.setTestMan(tim.getTestMan());
					if (it.getLevel() > 1) {
						initParentDetail(detial, sort);
					} else {
						detial.setSort(sort);
						sort++;
					}
					reportDetailDao.add(detial);
					String testTime = tim.getTestTime().substring(0, 10);
					String endTime = tim.getTestEndTime();
					if (!StrUtils.isBlankOrNull(endTime)) {
						endTime = endTime.substring(0, 10);
					}
					if (StrUtils.isBlankOrNull(startDate) || DateUtils.getIntevalDays(testTime, startDate) > 0) {
						startDate = testTime;
					}
					if (StrUtils.isBlankOrNull(endDate)) {
						endDate = startDate;
					}
					if (!StrUtils.isBlankOrNull(endTime) && DateUtils.getIntevalDays(endDate, endTime) > 0) {
						endDate = endTime;
					} else if (DateUtils.getIntevalDays(endDate, testTime) > 0) {
						endDate = testTime;
					}

				}
				if (StrUtils.isBlankOrNull(cyDate) || DateUtils.getIntevalDays(samp.getCyDate(), cyDate) > 0) {
					cyDate = samp.getCyDate();
				}
				if (StrUtils.isBlankOrNull(cyEndDate) || DateUtils.getIntevalDays(cyEndDate, samp.getCyDate()) > 0) {
					cyEndDate = samp.getCyDate();
				}
			}
		}
		report.setTestDate(startDate);
		report.setTestEndDate(endDate);
		report.setCyDate(cyDate);
		report.setCyEndDate(cyEndDate);
	}

	// 对于子项添加父项详情
	public void initParentDetail(ReportDetail detial, int sort) {
		Item item = itemDao.findById(detial.getItemId());
		Item pitem = item.getParent();
		// 检查父项是否初始化
		String jpql = "FROM " + reportDetailDao.getEntityName(ReportDetail.class) + " WHERE isDel='" + Po.N + "' AND reportId='" + detial.getReportId()
				+ "' AND pointId='" + detial.getPointId() + "' AND cyDate='" + detial.getCyDate() + "'  AND itemId='" + pitem.getId() + "'";
		List<ReportDetail> pl = reportDetailDao.list(jpql);
		if (pl != null && pl.size() > 0) {
			ReportDetail p = pl.get(0);
			List<ReportDetail> subList = reportDetailDao.listByParent(p.getId());
			detial.setParent(p);
			detial.setSort(subList.size() + 1);
		} else {
			ReportDetail p = new ReportDetail();
			p.setReportId(detial.getReportId());
			p.setPointId(detial.getPointId());
			p.setPointName(detial.getPointName());
			p.setSampName(detial.getSampName());
			p.setSampCode(detial.getSampCode());
			p.setCyDate(detial.getCyDate());
			p.setTim(detial.getTim());
			p.setItemId(pitem.getId());
			p.setItemName(pitem.getName());
			p.setLevel(1);
			p.setSort(sort);
			reportDetailDao.add(p);
			detial.setParent(p);
			detial.setSort(1);
			sort++;
		}
	}

	// 初始化职卫报告
	public void initReport4Zw(Report report, Task task) {
		report.setReportNo(createReportNo(task.getSampType(), null, task.getReportNo()));
		report.setTemplate("bus-report-z.ftl");// 评价+定期2+委托 通用模板
		reportDao.saveOrUpdate(report);
		initReportDetailZw(report, task);
		reportDao.update(report);
	}

	// 初始化环境报告详情
	public void initReportDetailZw(Report report, Task task) {
		reportDetailResultDao.deleteByReportId(report.getId());
		reportDetailDao.deleteByReportId(report.getId());
		List<TaskItem> timList = taskItemDao.listByTaskId(task.getId());
		String startDate = null;
		String endDate = null;
		String cyDate = null;
		String cyEndDate = null;
		List<String> typeIdSet = new ArrayList<String>();
		List<String> typeNameSet = new ArrayList<String>();
		for (TaskItem tim : timList) {
			String hql = "FROM " + taskPointDao.getEntityName(TaskPoint.class) + " WHERE isDel='" + Po.N + "' AND task.id='" + task.getId()
					+ "' AND itemIds like '%" + tim.getItemId() + "%' ORDER BY sort asc";
			List<TaskPoint> tpList = taskPointDao.list(hql);
			int n = 1;
			if (null != tpList) {
				for (TaskPoint tp : tpList) {
					String jpql = "FROM " + testItemDao.getEntityName(TestItem.class) + " WHERE isDel=" + Po.N + " AND point.id='" + tp.getId()
							+ "' AND tim.id='" + tim.getId() + "' order by cyDate asc";
					List<TestItem> itList = testItemDao.list(jpql);
					if (null != itList) {
						for (TestItem it : itList) {
							ReportDetail detial = new ReportDetail();
							detial.setReportId(report.getId());
							detial.setPointId(tp.getId());
							detial.setPointName(tp.getPointName());
							detial.setTim(tim);
							detial.setRoom(tp.getRoom());
							detial.setWorkHours(tp.getWorkHours());
							detial.setCyDate(it.getCyDate());
							detial.setValue(it.getValue());
							detial.setSl(it.getSl());
							detial.setUnit(tim.getUnit());
							detial.setUnit2(tim.getSlUnit());
							detial.setMac(it.getMac());
							detial.setStel(it.getStel());
							detial.setTwa(it.getTwa());
							detial.setLmt(it.getLmt());
							detial.setResult(it.getResult());
							detial.setType(tp.getType());
							detial.setSampTypeId(tim.getSampTypeId());
							detial.setSampTypeName(tim.getSampTypeName());
							detial.setSampName(tim.getSampName());
							detial.setItemId(it.getItemId());
							detial.setItemName(it.getItemName());
							detial.setLevel(1);
							detial.setLimitLine(tim.getLimitLine());
							detial.setLimited(tim.getLimited());
							detial.setStandId(tim.getStandId());
							detial.setStandName(tim.getStandName());
							detial.setMethodId(tim.getMethodId());
							detial.setMethodName(tim.getMethodName());
							detial.setAppId(tim.getAppId());
							detial.setAppName(tim.getAppName());
							detial.setOrgId(tim.getDeptId());
							detial.setOrgName(tim.getDeptName());
							detial.setSort(n);
							reportDetailDao.add(detial);
							n++;
							hql = "FROM " + testResultDao.getEntityName(TestResult.class) + " WHERE isDel='" + TestResult.N + "' AND it.id ='" + it.getId()
									+ "' ORDER BY sort asc";
							List<TestResult> trList = testResultDao.list(hql);
							for (TestResult tr : trList) {
								if (tr.getSamp().getZkType().equals(Sampling.SAMP_TYPE_PT)) {
									ReportDetailResult result = new ReportDetailResult();
									result.setReportId(report.getId());
									result.setDetailId(detial.getId());
									result.setValue(tr.getValue());
									result.setValue2(tr.getValue2());
									result.setSl(tr.getSl());
									result.setItemId(tim.getItemId());
									result.setItemName(tim.getItemName());
									result.setSamplingId(tr.getSamp().getId());
									result.setSampCode(tr.getSamp().getSampCode());
									result.setSampName(tr.getSamp().getSampName());
									result.setP(tr.getSamp().getP());
									reportDetailResultDao.add(result);
								}
							}
							if (StrUtils.isBlankOrNull(cyDate) || DateUtils.getIntevalDays(it.getCyDate(), cyDate) > 0) {
								cyDate = it.getCyDate();
							}
							if (StrUtils.isBlankOrNull(cyEndDate) || DateUtils.getIntevalDays(cyEndDate, it.getCyDate()) > 0) {
								cyEndDate = it.getCyDate();
							}
						}
					}
				}
			}
			String testTime = tim.getTestTime().substring(0, 10);
			if (StrUtils.isBlankOrNull(startDate) || DateUtils.getIntevalDays(testTime, startDate) > 0) {
				startDate = testTime;
			}
			if (!StrUtils.isBlankOrNull(tim.getTestEndTime())) {
				String endTime = tim.getTestEndTime().substring(0, 10);
				if (StrUtils.isBlankOrNull(endDate) || DateUtils.getIntevalDays(endDate, endTime) > 0) {
					endDate = endTime;
				}
			} else {
				if (StrUtils.isBlankOrNull(testTime) || DateUtils.getIntevalDays(endDate, testTime) > 0) {
					endDate = testTime;
				}
			}
			if (!typeIdSet.contains(tim.getSampTypeId())) {
				typeIdSet.add(tim.getSampTypeId());
				typeNameSet.add(tim.getSampTypeName());
			}
		}
		report.setTestDate(startDate);
		report.setTestEndDate(endDate);
		report.setCyDate(cyDate);
		report.setCyEndDate(cyEndDate);
		report.setSampTypeId(String.join(",", typeIdSet));
		report.setSampTypeName(String.join(",", typeNameSet));
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}

		pageResult.addCondition(new QueryCondition("status='" + EunmTask.TASK_HZ.getStatus() + "' "));
//		pageResult.addCondition(new QueryCondition("sumUserId like '" + CurrentUtils.getCurrent().getAccountId() + "' "));
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
		StringBuffer hql = new StringBuffer("SELECT t.id,t.no,t.task_type,t.samp_type_name,t.samp_name,cust.cust_name,t.finish_date,t.sum_date,t.status");
		hql.append(" FROM " + tablePrefix + taskDao.getEntityName(Task.class) + " t");
		hql.append(" JOIN " + tablePrefix + taskDao.getEntityName(Cust.class) + " cust ON t.cust_id=cust.id ");
		hql.append(" JOIN " + tablePrefix + taskDao.getEntityName(ProgressLog.class) + " log ON t.id=log.bus_id AND log.status='" + EunmTask.TASK_HZ.getStatus()
				+ "' ");
		hql.append(" WHERE t.is_del ='" + Po.N + "'");
		if (QueryConditionList != null) {
			for (QueryCondition condition : QueryConditionList) {
				if (!StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) {
					hql.append(" AND t." + condition.getField().trim() + " like '%" + String.valueOf(condition.getValue()).trim() + "%' ");
				}
			}
		}
		hql.append(" group by t.id ");
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			hql.append(" ORDER BY log.last_upd_time " + OrderCondition.ORDER_DESC + "");
		} else {
			if (gridVo.getSidx().contains(".")) {
				hql.append(" ORDER BY " + gridVo.getSidx() + " " + gridVo.getSord() + "");
			} else {
				hql.append(" ORDER BY t." + gridVo.getSidx() + " " + gridVo.getSord() + "");
			}
		}
		Query query = taskDao.queryBysql(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Object[]> pList = query.setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();
		if (null != pList) {
			for (Object[] obj : pList) {
				Map<String, Object> map = new HashedMap();
				map.put("id", obj[0]);
				map.put("no", obj[1]);
				map.put("taskType", obj[2]);
				map.put("sampTypeName", obj[3]);
				map.put("sampName", obj[4]);
				map.put("custName", obj[5]);
				map.put("finishDate", obj[6]);
				map.put("sumDate", obj[7]);
				map.put("status", obj[8]);
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
	public List<Map<?, Object>> poList2mapList(List<Task> list) throws GlobalException {
		List tempList = new ArrayList();
		for (Task p : list) {
			Map<String, Object> map = po2map(p);
			Object timNum = map.get("timNum");
			Object timTal = map.get("timTal");
			map.put("timTal", (timNum == null ? "0" : timNum.toString()) + "/" + (timTal == null ? "0" : timTal.toString()));
			tempList.add(map);
		}
		return tempList;
	}

	// @Override
	// public void update2Stop(TaskVo v) throws GlobalException {
	// for(ItemTest it:itemTestDao.listByIds(v.getIds())){
	// Progress itpg=it.getProgress();
	// it.setStatus(EunmTask.TASK_STOP.getStatus());
	// it.setIsDel(Integer.valueOf(Constants.PASS_N));
	// it.setProgress(null);
	// it.setCheckMsg(v.getSumMsg());
	// itemTestDao.update(it);
	// if(null!=itpg) {
	// progressLogDao.add(it.getTask().getId(),it.getId(),itpg.getStatus(),EunmTask.TASK_STOP.getStatus(),v.getSumMsg());
	// progressDao.delete(itpg);
	// }
	// }
	// }
	@Override
	public void updateBack(TaskVo v) throws GlobalException {
		List<TaskItem> timList = taskItemDao.listByIds(v.getIds());
		for (TaskItem it : timList) {
			Progress pg = it.getProgress();
			pg = progressDao.update(pg.getId(), EunmTask.ITEM_LR.getStatus(), it.getDeptId(), it.getDeptName(), it.getTestManId(), it.getTestMan());
			it.setStatus(pg.getStatus());
			it.setIsBack(Constants.Y);
			taskItemDao.update(it);
			progressLogDao.add(it.getTask().getId(), it.getId(), EunmTask.ITEM_HZ.getStatus(), v.getIsCommit(), v.getSumMsg());
		}
	}
	// @Override
	// public void updateCc(TaskVo v) throws GlobalException {
	// List<ItemTest> itlist=itemTestDao.listByIds(v.getIds());
	// for (ItemTest it : itlist) {
	// ItemTest newIt=new ItemTest();
	// BeanUtils.copyProperties(it, newIt, new String[]
	// {"id","sort","isDel","progress","status","filePath","fileName","testTime","value","value2","wd","sd"});
	// newIt.setCheckMsg(v.getSumMsg());
	// newIt.setType(ItemTest.ITEM_TYPE_CC);
	// itemTestDao.add(newIt);
	// Progress pg=progressDao.add(newIt.getId(), EunmTask.ITEM_LR.getStatus(),
	// newIt.getDeptId(), newIt.getDeptName(), newIt.getTestManId(),
	// newIt.getTestMan());
	// newIt.setStatus(pg.getStatus());
	// newIt.setProgress(pg);
	// itemTestDao.update(newIt);
	// progressLogDao.add(newIt.getTask().getId(),newIt.getId(),EunmTask.ITEM_HZ.getStatus(),EunmTask.PASS_Y,"重测项目："+it.getItemName()+"，备注："+v.getSumMsg());
	// }
	// }
}
