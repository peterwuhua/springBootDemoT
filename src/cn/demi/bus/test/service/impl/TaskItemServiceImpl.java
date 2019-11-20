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
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.sample.dao.ISampRecordDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampRecord;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.bus.test.service.ITaskItemService;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.bus.test.vo.TestItemVo;
import cn.demi.bus.test.vo.TestResultVo;
import cn.demi.doc.dao.IDocumentDao;
import cn.demi.doc.po.Document;
import cn.demi.doc.vo.DocumentVo;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.dao.IPstandItemDao;
import cn.demi.init.std.dao.IPstandardDao;
import cn.demi.init.std.po.Method;
import cn.demi.init.std.po.PstandItem;
import cn.demi.init.std.po.Pstandard;
import cn.demi.pfm.dao.ISRecordDao;

@Service("bus.taskItemService")
public class TaskItemServiceImpl extends BaseServiceImpl<TaskItemVo, TaskItem> implements ITaskItemService {

	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private ITestItemDao testItemDao;
	@Autowired
	private ITestResultDao testResultDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IPstandItemDao pstandItemDao;
	@Autowired
	private IPstandardDao pstandardDao;
	@Autowired
	private IDocumentDao documentDao;
	@Autowired
	private IMethodDao methodDao;
	@Autowired
	private ISRecordDao sRecordDao;
	@Autowired
	private ISampRecordDao sampRecordDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private ISamplingDao samplingDao;

	@Override
	public TaskItemVo find(String id) throws GlobalException {
		TaskItem po = taskItemDao.findById(id);
		TaskItemVo vo = po2Vo(po);
		vo.setItemList(list4Item(vo.getId()));
		if (StrUtils.isBlankOrNull(vo.getStandId())) {
			String sampTypeIds = sampTypeDao.findAllIds(po.getSampTypeId());
			List<Pstandard> pstandList = pstandardDao.listBySampTyle(po.getTask().getStandIds(), sampTypeIds);
			if (null != pstandList) {
				List<String> idsList = new ArrayList<>();
				List<String> nameList = new ArrayList<>();
				for (Pstandard pstandard : pstandList) {
					idsList.add(pstandard.getId());
					nameList.add(pstandard.getCode() + " " + pstandard.getName());
				}
				vo.setStandId(String.join(",", idsList));
				vo.setStandName(String.join(",", nameList));
			}
		}
		// 原始记录单模板
		List<DocumentVo> temVoList = new ArrayList<DocumentVo>();
		if (!StrUtils.isBlankOrNull(po.getMethodId())) {
			Method m = methodDao.findById(po.getMethodId());
			if (StrUtils.isBlankOrNull(m.getTempIds())) {
				List<Document> temList = documentDao.listByIds(m.getTempIds());
				for (Document doc : temList) {
					DocumentVo docVo = new DocumentVo();
					docVo = docVo.toVo(doc);
					temVoList.add(docVo);
				}
			}
		}
		DocumentVo docVo = new DocumentVo();
		docVo.setName("空白模板.xls");
		docVo.setRelativePath("static/upload/document/YSJLD/blank.xls");
		docVo.setType(".xls");
		temVoList.add(docVo);
		vo.setTempList(temVoList);
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
				} else if (it.getType().equals(TaskItem.ITEM_TYPE_XC)&&!it.getItemName().contains("粉尘")) {
					itVo.setTrVo(findResult(it.getId()));
				} else {
					itVo.setTrList(list4Result(it.getId()));
				}
				itVoList.add(itVo);
			}
		}
		return itVoList;
	}

	// 获取测试项目的测试结果集合 职卫
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

	// 获取测试项目的结果信息 环境
	public TestResultVo findResult(String itId) throws GlobalException {
		List<TestResult> rList = testResultDao.listByItId(itId);
		TestResultVo rVo = new TestResultVo();
		if (null != rList && rList.size() > 0) {
			rVo = rVo.toVo(rList.get(0));
			if (null != rVo.getSampVo().getZkType()
					&& (rVo.getSampVo().getZkType().equals(Sampling.SAMP_TYPE_JB) || rVo.getSampVo().getZkType().equals(Sampling.SAMP_TYPE_PX_S))) {
				rVo.setOper("1");
			} else {
				rVo.setOper("0");
			}
		}
		return rVo;
	}

	@Override
	public TaskItemVo findById(String id) throws GlobalException {
		TaskItem po = taskItemDao.findById(id);
		TaskItemVo vo = po2Vo(po);
		vo.setItemList(list4Item(vo.getId()));
		return vo;
	}

	@Override
	public void update(TaskItemVo v) throws GlobalException {
		TaskItem tim = taskItemDao.findById(v.getId());
		uptLmt(tim, v);
		tim.setStandId(v.getStandId());
		tim.setStandName(v.getStandName());
		tim.setMethodId(v.getMethodId());
		tim.setMethodName(v.getMethodName());
		tim.setLimitLine(v.getLimitLine());
		tim.setStId(v.getStId());
		tim.setStName(v.getStName());
		tim.setAppId(v.getAppId());
		tim.setAppName(v.getAppName());
		tim.setWd(v.getWd());
		tim.setSd(v.getSd());
		tim.setUnit(v.getUnit());
		tim.setSlUnit(v.getSlUnit());
		tim.setTestTime(v.getTestTime());
		tim.setTestEndTime(v.getTestEndTime());
		tim.setTemp(v.getTemp());
		tim.setTestMsg(v.getTestMsg());
		List<TestItemVo> itList = v.getItemList();
		for (TestItemVo itVo : itList) {
			TestItem it = testItemDao.findById(itVo.getId());
			it.setValue(itVo.getValue());
			it.setSl(itVo.getSl());
			it.setLmt(itVo.getLmt());
			it.setTwa(itVo.getTwa());
			it.setMac(itVo.getMac());
			it.setStel(itVo.getStel());
			it.setResult(itVo.getResult());
			testItemDao.update(it);
			List<TestResultVo> trList = itVo.getTrList();
			if (trList != null && trList.size() > 0) {
				for (TestResultVo trVo : trList) {
					TestResult tr = testResultDao.findById(trVo.getId());
					tr.setValue(trVo.getValue());
					tr.setValue2(trVo.getValue2());
					tr.setSl(trVo.getSl());
					testResultDao.update(tr);
					if (tr.getSamp() != null && trVo.getSampVo() != null) {
						Sampling samp = tr.getSamp();
						samp.setSampCode(trVo.getSampVo().getSampCode());// 样品编号
						samplingDao.update(samp);// 更新样品编号
						uptValue4Xc(tim, trVo.getSampVo(), samp.getRecord());
					}
				}
			} else {
				TestResultVo trVo = itVo.getTrVo();
				TestResult tr = testResultDao.findById(trVo.getId());
				tr.setValue(trVo.getValue());
				tr.setValue2(trVo.getValue2());
				tr.setSl(trVo.getSl());
				testResultDao.update(tr);
				if (tr.getSamp() != null && trVo.getSampVo() != null) {
					Sampling samp = tr.getSamp();
					samp.setSampCode(trVo.getSampVo().getSampCode());// 样品编号
					samplingDao.update(samp);// 更新样品编号
				}
				uptValue4Xc(tim, trVo.getSampVo(), tr.getSamp().getRecord());
			}
		}
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			tim.setIsBack(Constants.N);
			tim.setCheckMsg(null);
			Progress pg = null;
			if (tim.getType().equals(TaskItem.ITEM_TYPE_XC)) {
				pg = progressDao.update(tim.getProgress().getId(), EunmTask.ITEM_HZ.getStatus(), null, null, null, null);
			} else {
				pg = progressDao.update(tim.getProgress().getId(), EunmTask.ITEM_JY.getStatus(), tim.getDeptId(), tim.getDeptName(), tim.getCheckManId(),
						tim.getCheckMan());
			}
			tim.setStatus(pg.getStatus());
			progressLogDao.add(tim.getTask().getId(), tim.getId(), EunmTask.ITEM_LR.getStatus(), EunmTask.PASS_Y, v.getTestMsg());
			sRecordDao.insert(tim.getTask().getNo() + tim.getItemName(), tim.getAssignDate(), Constants.KH_RW_SHLR);
		}
		taskItemDao.update(tim);
	}

	// 现场项目 退回到数据录入 更新现场数据信息
	public void uptValue4Xc(TaskItem tim, SamplingVo sampVo, SampRecord record) {
		if (tim.getType().equals(TaskItem.ITEM_TYPE_XC) && null != sampVo && null != sampVo.getRecordVo()) {
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV1())) {
				record.setV1(sampVo.getRecordVo().getV1());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV2())) {
				record.setV2(sampVo.getRecordVo().getV2());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV3())) {
				record.setV3(sampVo.getRecordVo().getV3());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV4())) {
				record.setV4(sampVo.getRecordVo().getV4());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV5())) {
				record.setV5(sampVo.getRecordVo().getV5());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV6())) {
				record.setV6(sampVo.getRecordVo().getV6());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV7())) {
				record.setV7(sampVo.getRecordVo().getV7());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV8())) {
				record.setV8(sampVo.getRecordVo().getV8());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV9())) {
				record.setV9(sampVo.getRecordVo().getV9());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV10())) {
				record.setV10(sampVo.getRecordVo().getV10());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV11())) {
				record.setV11(sampVo.getRecordVo().getV11());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV12())) {
				record.setV12(sampVo.getRecordVo().getV12());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getAvg1())) {
				record.setAvg1(sampVo.getRecordVo().getAvg1());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getAvg2())) {
				record.setAvg2(sampVo.getRecordVo().getAvg2());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getAvg3())) {
				record.setAvg3(sampVo.getRecordVo().getAvg3());
			}
			if (!StrUtils.isBlankOrNull(sampVo.getRecordVo().getDemo2())) {
				record.setDemo2(sampVo.getRecordVo().getDemo2());
			}
			sampRecordDao.update(record);
		}
	}

	// 检测标准更新，同步限值
	public void uptLmt(TaskItem tim, TaskItemVo v) {
		if (StrUtils.isBlankOrNull(v.getStandId())) {
			tim.setStandId(null);
			tim.setStandName(null);
			tim.setLimited(null);
			tim.setMac(null);
			tim.setStel(null);
			tim.setTwa(null);
			tim.setLmt(null);
			tim.setPitId(null);
		} else if (StrUtils.isBlankOrNull(tim.getStandId()) || !tim.getStandId().equals(v.getStandId())) {
			tim.setStandId(v.getStandId());
			tim.setStandName(v.getStandName());
			if (tim.getTask().getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				List<PstandItem> plist = pstandItemDao.listStand4Item(tim.getStandId(), tim.getItemId());
				if (null != plist && plist.size() > 0) {
					PstandItem pit = plist.get(0);
					tim.setStandId(pit.getStandId());
					tim.setStandName(pit.getStandName());
					tim.setLimited(pit.getValStr());
					tim.setPitId(pit.getId());
				}
			} else {
				List<PstandItem> plist = pstandItemDao.listStand(tim.getStandId(), tim.getItemId(), null, tim.getItemType(), null);
				if (plist != null && plist.size() > 0) {
					PstandItem pit = plist.get(0);
					// 化学有害因素特有
					if (tim.getSampTypeName().contains("化学")) {
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
						// 环境、物理等其他
						tim.setLimited(pit.getValStr());
					}
					tim.setPitId(pit.getId());
				} else {
					tim.setLimited(null);
					tim.setMac(null);
					tim.setStel(null);
					tim.setTwa(null);
					tim.setLmt(null);
					tim.setPitId(null);
				}
			}
		}
	}

	@Override
	public void update4Zy(TaskItemVo v) throws GlobalException {
		TaskItem it = taskItemDao.findById(v.getId());
		it.setTestMan(v.getTestMan());
		it.setTestManId(v.getTestManId());
		it.setRemark(v.getRemark());
		taskItemDao.update(it);
		Progress pg = it.getProgress();
		pg.setUserId(v.getTestManId());
		pg.setUserName(v.getTestMan());
		progressDao.update(pg);
		progressLogDao.add(it.getTask().getId(), it.getId(), EunmTask.ITEM_LR.getStatus(), EunmTask.PASS_S,
				"任务转移 - 接收人：" + pg.getUserName() + " - 原因：" + v.getRemark());
	}

	@Override
	public void updateFile(TaskItemVo v) throws GlobalException {
		if (StrUtils.isNotBlankOrNull(v.getId())) {
			TaskItem it = taskItemDao.findById(v.getId());
			it.setFileName(v.getFileName());
			it.setFilePath(v.getFilePath());
			taskItemDao.update(it);
		}

	}

	@Override
	public TaskItemVo find4Temp(String id) throws GlobalException {
		TaskItem po = taskItemDao.findById(id);
		TaskItemVo vo = po2Vo(po);
		vo.setItemList(list4Item(vo.getId()));
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TaskItemVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.addOrder("lastUpdTime", OrderCondition.ORDER_DESC);
			pageResult.addOrder("task.id", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='" + EunmTask.ITEM_LR.getStatus() + "' "));
		pageResult.addCondition(new QueryCondition("testManId like '%" + CurrentUtils.getCurrent().getAccountId() + "%' "));
		pageResult = taskItemDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<TaskItem>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<?, Object>> poList2mapList(List<TaskItem> list) throws GlobalException {
		List tempList = new ArrayList();
		for (TaskItem p : list) {
			Map<String, Object> map = po2map(p);
			if (null != map.get("sbDate")) {
				String sbDate = String.valueOf(map.get("sbDate"));
				if (sbDate.length() == 10) {
					sbDate = sbDate + " 23:59:59";
				}
				long n = DateUtils.getIntevalHours(DateUtils.getCurrDateTimeStr(), sbDate);
				if (n < 2) {
					map.put("color", "red");
				} else if (n < 12) {
					map.put("color", "blue");
				}
			}
			String createUser = String.valueOf(map.get("createUser"));
			if (createUser != null && createUser.contains(getCurrent().getLoginName())) {
				map.put("isDelete", true);
			}
			tempList.add(map);
		}
		return tempList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, TaskItemVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql = new StringBuffer(
				"SELECT t.id AS A,t.item_name,task.id AS C,task.no,task.task_type,t.test_man,t.sb_date,t.progress_id,t.test_time,t.samp_name,t.samp_num");
		hql.append(" FROM " + tablePrefix + taskItemDao.getEntityName(TaskItem.class) + " t JOIN " + tablePrefix + taskItemDao.getEntityName(ProgressLog.class)
				+ " log ON t.id=log.bus_id AND log.status='" + EunmTask.ITEM_LR.getStatus() + "' ");
		hql.append(" JOIN " + tablePrefix + taskItemDao.getEntityName(Task.class) + " task ON t.task_id=task.id AND task.is_del='" + Po.N + "'");
		hql.append(" WHERE t.is_del !='" + Po.Y + "'");
		if (QueryConditionList != null) {
			for (QueryCondition condition : QueryConditionList) {
				if (!StrUtils.isBlankOrNull(String.valueOf(condition.getValue()))) {
					if (condition.getField().contains(".")) {
						hql.append(" AND " + condition.getField() + " like '%" + String.valueOf(condition.getValue()).trim() + "%' ");
					} else {
						hql.append(" AND t." + condition.getField() + " like '%" + String.valueOf(condition.getValue()).trim() + "%' ");
					}
				}
			}
		}
		hql.append(" GROUP BY t.id ");
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			hql.append(" ORDER BY t.last_upd_time " + OrderCondition.ORDER_DESC + "");
		} else {
			if (gridVo.getSidx().contains(".")) {
				hql.append(" ORDER BY " + gridVo.getSidx() + " " + gridVo.getSord() + "");
			} else {
				hql.append(" ORDER BY t." + gridVo.getSidx() + " " + gridVo.getSord() + "");
			}
		}
		Query query = taskItemDao.queryBysql(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Object[]> pList = query.setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();
		if (null != pList) {
			for (Object[] obj : pList) {
				Map<String, Object> map = new HashedMap();
				map.put("id", obj[0]);
				map.put("itemName", obj[1]);
				map.put("testMan", obj[5]);
				map.put("sbDate", obj[6]);
				map.put("testTime", obj[8]);
				map.put("sampName", obj[9]);
				map.put("sampNum", obj[10]);
				// 设置任务对象map
				Map<String, Object> taskVo = new HashedMap();
				taskVo.put("id", obj[2]);
				taskVo.put("no", obj[3]);
				String taskType = String.valueOf(obj[4]);
				taskVo.put("taskType", taskType);
				map.put("taskVo", taskVo);
				// 设置样品对象map
				Map<String, Object> progressVo = new HashedMap();
				progressVo.put("id", obj[7]);
				map.put("progressVo", progressVo);// 设置进度对象map
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
	public void update4Limited(TaskItemVo v) throws GlobalException {
		TaskItem tim = taskItemDao.findById(v.getId());
		PstandItem pit = pstandItemDao.findById(v.getUuid());
		// 化学有害因素特有
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
			// 环境、物理等其他
			tim.setLimited(pit.getValStr());
		}
		tim.setPitId(pit.getId());
		taskItemDao.update(tim);
	}
}
