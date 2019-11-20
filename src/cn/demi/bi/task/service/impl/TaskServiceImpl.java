package cn.demi.bi.task.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
import cn.demi.bi.task.service.ITaskService;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.vo.TestItemVo;

/**
 * 任务统计 业务逻辑层
 */
@Service("bi.taskService")
public class TaskServiceImpl extends BaseServiceImpl<TaskVo, Task> implements ITaskService {

	@Autowired
	private ITestItemDao testItemDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IOrgDao orgDao;

	/**
	 * 获取任务详细信息 包含项目、样品信息
	 */
	@Override
	public TaskVo findById(String id) throws GlobalException {
		Task task = taskDao.findById(id);
		TaskVo vo = po2Vo(task);
		// 样品信息
		// List<Sampling> sampList=samplingDao.findByProperty("task.id", task.getId());
		// List<SamplingVo> sampVoList=new ArrayList<SamplingVo>();
		// if(sampList!=null) {
		// for (Sampling samp : sampList) {
		// SamplingVo sampVo=new SamplingVo();
		// sampVo=sampVo.toVo(samp);
		// sampVoList.add(sampVo);
		// }
		// }
		// vo.setSampList(sampVoList);
		// 项目信息
		List<TestItem> itemList = testItemDao.findByProperty("task.id", task.getId());
		List<TestItemVo> itemVoList = new ArrayList<TestItemVo>();
		if (itemList != null) {
			for (TestItem item : itemList) {
				TestItemVo itemVo = new TestItemVo();
				itemVo = itemVo.toVo(item);
				itemVoList.add(itemVo);
			}
		}
		vo.setItemList(itemVoList);
		return vo;
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
		String currDeptId = CurrentUtils.getCurrent().getDepId();
		Org org = orgDao.findOrg(currDeptId);
		if (!getCurrent().getRoleNames().contains("总经理") && !getCurrent().getRoleNames().contains("副总") && !getCurrent().getRoleNames().contains("管理员")) {
			if (org.getName().contains("环境")) {
				pageResult.addCondition(new QueryCondition("samp_type like '%环境%'"));
			} else if (org.getName().contains("职业卫生")) {
				pageResult.addCondition(new QueryCondition("samp_type like '%职业卫生%'"));
			}
		}

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
		List<QueryCondition> QueryConditionList = new ArrayList<QueryCondition>();

		if (!StrUtils.isBlankOrNull(v.getNo())) {
			QueryConditionList.add(new QueryCondition("no", QueryCondition.AK, v.getNo()));
		}
		if (null != v.getCustVo() && !StrUtils.isBlankOrNull(v.getCustVo().getCustName())) {
			QueryConditionList.add(new QueryCondition("cust.custName", QueryCondition.AK, v.getCustVo().getCustName()));
		}
		if (!StrUtils.isBlankOrNull(v.getTaskType())) {
			QueryConditionList.add(new QueryCondition("taskType", QueryCondition.AK, v.getTaskType()));
		}
		if (!StrUtils.isBlankOrNull(v.getOrgName())) {
			QueryConditionList.add(new QueryCondition("orgName", QueryCondition.AK, v.getOrgName()));
		}
		if (!StrUtils.isBlankOrNull(v.getOrgId())) {
			List<String> ids = orgDao.listChild(v.getOrgId());
			ids.add(v.getOrgId());
			QueryConditionList.add(new QueryCondition("orgId in('" + String.join("','", ids) + "')"));
		}
		if (!StrUtils.isBlankOrNull(v.getStartDate())) {
			QueryConditionList.add(new QueryCondition("date>='" + v.getStartDate() + " 00:00:00'"));
		}
		if (!StrUtils.isBlankOrNull(v.getEndDate())) {
			QueryConditionList.add(new QueryCondition("date<='" + v.getEndDate() + " 23:59:59'"));
		}
		if (!StrUtils.isBlankOrNull(v.getSampName())) {
			QueryConditionList.add(new QueryCondition("sampName", QueryCondition.AK, v.getSampName()));
		}
		if (!StrUtils.isBlankOrNull(v.getUserName())) {
			QueryConditionList.add(new QueryCondition("userName", QueryCondition.AK, v.getUserName()));
		}
		QueryConditionList.add(new QueryCondition("status!='" + EunmTask.TASK_DJ.getStatus() + "' "));
		return QueryConditionList;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<?, Object>> poList2mapList(List<Task> list) throws GlobalException {
		List tempList = new ArrayList();
		for (Task p : list) {
			Map<String, Object> map = po2map(p);
			map.put("status", EunmTask.getName(map.get("status").toString()));
			tempList.add(map);
		}
		return tempList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int[] chart(TaskVo v) throws GlobalException {
		StringBuffer jpql = new StringBuffer("select substring(finishDate,6,2),count(id) from " + taskDao.getEntityName(Task.class) + " where isDel='" + Po.N
				+ "' and progress.status='" + EunmTask.TASK_END.getStatus() + "' ");
		jpql.append(" group by substring(finishDate,6,2)");
		List<Object[]> objList = taskDao.query(jpql.toString()).getResultList();
		int[] it = new int[12];
		for (int i = 0; i < it.length; i++) {
			for (Object[] objects : objList) {
				if (i == (Integer.valueOf(objects[0].toString()) - 1)) {
					it[i] = Integer.valueOf(objects[1].toString());
				}
			}
		}
		return it;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> chartPie(TaskVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<EnumBus> list = EnumBus.getALLList();
		List<String> itemNames = new ArrayList<String>();
		StringBuffer jpql = new StringBuffer("select taskType,count(id) from " + taskDao.getEntityName(Task.class) + " where isDel='" + Po.N + "' and status!='"
				+ EunmTask.TASK_DJ.getStatus() + "' AND status!='" + EunmTask.TASK_STOP.getStatus() + "' ");
		jpql.append(" group by taskType");
		List<Object[]> objList = taskDao.query(jpql.toString()).getResultList();
		List<Integer> n1 = new ArrayList<Integer>();
		for (EnumBus enumBus : list) {
			int n = 0;
			for (Object[] obj : objList) {
				if (obj[0].toString().equals(enumBus.getName())) {
					n = Integer.valueOf(obj[1].toString());
					break;
				}
			}
			n1.add(n);
			itemNames.add(enumBus.getName());
		}

		jpql = new StringBuffer("select taskType,count(id) from " + taskDao.getEntityName(Task.class) + " where isDel='" + Po.N + "' and status='"
				+ EunmTask.TASK_END.getStatus() + "'");
		jpql.append(" group by taskType");
		objList = taskDao.query(jpql.toString()).getResultList();
		List<Integer> n2 = new ArrayList<Integer>();
		for (EnumBus enumBus : list) {
			int n = 0;
			for (Object[] obj : objList) {
				if (obj[0].toString().equals(enumBus.getName())) {
					n = Integer.valueOf(obj[1].toString());
					break;
				}
			}
			n2.add(n);
		}

		map.put("itemNames", itemNames);
		map.put("total", n1);
		map.put("finish", n2);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> chartFy(TaskVo v) throws GlobalException {
		Map<String, Object> map = new HashMap<String, Object>();
		String title = "";
		StringBuffer jpql = new StringBuffer("select SUBSTR(date,6,2) as t,sum(price) from " + tablePrefix + taskDao.getEntityName(Task.class)
				+ " where is_del='" + Po.N + "' and status!='" + EunmTask.TASK_DJ.getStatus() + "' ");
		if (StrUtils.isBlankOrNull(v.getDate())) {
			jpql.append(" AND date like '" + DateUtils.getYear() + "%'");
			title += DateUtils.getYear() + "年度";
		} else {
			jpql.append(" AND date like '" + v.getDate() + "%'");
			title += v.getDate() + "年度";
		}
		// if(!StrUtils.isBlankOrNull(v.getOrgName())) {
		// jpql.append(" AND org_name like '"+v.getOrgName()+"'");
		// title+=v.getOrgName();
		// }
		if (!StrUtils.isBlankOrNull(v.getTaskType())) {
			jpql.append(" AND task_type like '" + v.getTaskType() + "'");
			title += v.getTaskType();
		}
		title += "费用总计";
		jpql.append("  group by t");
		List<Object[]> objList = taskDao.queryBySql(jpql.toString());
		double[] month = new double[12];
		double total = 0;
		if (null != objList && objList.size() > 0) {
			for (Object[] o : objList) {
				int m = Integer.valueOf(String.valueOf(o[0]));
				double d = Double.valueOf(String.valueOf(o[1]));
				month[m - 1] = d;
				total += d;
			}
		}
		title += total + "元";
		map.put("title", title);
		map.put("month", month);
		return map;
	}
}
