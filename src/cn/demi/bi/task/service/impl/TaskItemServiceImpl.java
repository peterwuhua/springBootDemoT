package cn.demi.bi.task.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountRoleDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.AccountRole;
import cn.demi.bi.task.service.ITaskItemService;
import cn.demi.bi.task.vo.ObjVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.bus.test.vo.TaskItemVo;

/**
 * 项目统计 业务逻辑层
 * 
 * @author QuJunLong
 *
 */
@Service("bi.TestItemService")
public class TaskItemServiceImpl extends BaseServiceImpl<TaskItemVo, TaskItem> implements ITaskItemService {

	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private IAccountRoleDao accountRoleDao;
	@Autowired
	private IOrgDao orgDao;

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TaskItemVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		if (!getCurrent().getRoleNames().contains("总经理") && !getCurrent().getRoleNames().contains("副总") && !getCurrent().getRoleNames().contains("管理员")) {
			pageResult.addCondition(new QueryCondition("orgId='" + getCurrent().getOrgId() + "' "));
		}
		pageResult.addCondition(new QueryCondition("status is not null"));
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
			String status = String.valueOf(map.get("status"));
			map.put("status", EunmTask.getName(status));
			tempList.add(map);
		}
		return tempList;
	}

	@Override
	public List<QueryCondition> toQueryList(TaskItemVo v) throws GlobalException {
		List<QueryCondition> QueryConditionList = new ArrayList<QueryCondition>();
		if (v.getTaskVo() != null) {
			if (!StrUtils.isBlankOrNull(v.getTaskVo().getNo())) {
				QueryConditionList.add(new QueryCondition("task.no", QueryCondition.AK, v.getTaskVo().getNo()));
			}
			if (null != v.getTaskVo().getCustVo() && !StrUtils.isBlankOrNull(v.getTaskVo().getCustVo().getCustName())) {
				QueryConditionList.add(new QueryCondition("task.cust.custName", QueryCondition.AK, v.getTaskVo().getCustVo().getCustName()));
			}
			if (!StrUtils.isBlankOrNull(v.getTaskVo().getTaskType())) {
				QueryConditionList.add(new QueryCondition("task.taskType", QueryCondition.EQ, v.getTaskVo().getTaskType()));
			}
		}
		if (!StrUtils.isBlankOrNull(v.getTestMan())) {
			QueryConditionList.add(new QueryCondition("testMan", QueryCondition.AK, v.getTestMan()));
		}
		if (!StrUtils.isBlankOrNull(v.getItemName())) {
			QueryConditionList.add(new QueryCondition("itemName", QueryCondition.AK, v.getItemName()));
		}
		if (!StrUtils.isBlankOrNull(v.getStartDate())) {
			QueryConditionList.add(new QueryCondition("testTime>='" + v.getStartDate() + " 00:00:00'"));
		}
		if (!StrUtils.isBlankOrNull(v.getEndDate())) {
			QueryConditionList.add(new QueryCondition("testTime<='" + v.getEndDate() + " 23:59:59'"));
		}
		if (!StrUtils.isBlankOrNull(v.getDeptName())) {
			QueryConditionList.add(new QueryCondition("deptName", QueryCondition.AK, v.getDeptName()));
		}
		return QueryConditionList;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Map<String, Object> chartNum(TaskItemVo v) throws GlobalException {
		List<String> orgIds = orgDao.listChild(getCurrent().getOrgId());
		orgIds.add(getCurrent().getOrgId());
		StringBuffer sql = new StringBuffer("SELECT SUBSTR(t.test_time,6,2) as tt,count(it.id) FROM " + tablePrefix + taskItemDao.getEntityName(TestItem.class)
				+ " it join v_bus_task_item t on t.id=it.tim_id");
		sql.append(" WHERE t.org_id in('" + String.join("','", orgIds) + "') AND it.is_del='" + Po.N + "' ");
		sql.append(" AND t.test_time is not null ");
		sql.append(" AND t.test_time like '" + DateUtils.getYear() + "%'");
		sql.append(" group by tt");
		List<Object[]> objList = taskItemDao.queryBySql(sql.toString());
		int[] month = new int[12];
		if (null != objList && objList.size() > 0) {
			for (Object[] o : objList) {
				int m = Integer.valueOf(String.valueOf(o[0]));
				month[m - 1] = Integer.valueOf(String.valueOf(o[1]));
			}
		}
		sql = new StringBuffer("SELECT SUBSTR(t.test_time,6,2) as tt,count(it.id) FROM " + tablePrefix + taskItemDao.getEntityName(TestItem.class)
				+ " it join v_bus_task_item t on t.id=it.tim_id");
		sql.append(" WHERE t.org_id in('" + String.join("','", orgIds) + "') AND it.is_del='" + Po.N + "' ");
		sql.append(" AND t.test_time is not null ");
		sql.append(" AND t.test_time like '" + (Integer.valueOf(DateUtils.getYear()) - 1) + "%'");
		sql.append(" group by tt");
		objList = taskItemDao.queryBySql(sql.toString());
		int[] monthed = new int[12];
		if (null != objList && objList.size() > 0) {
			for (Object[] o : objList) {
				int m = Integer.valueOf(String.valueOf(o[0]));
				monthed[m - 1] = Integer.valueOf(String.valueOf(o[1]));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String[] m = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
		map.put("month", m);
		map.put("value", month);
		map.put("valued", monthed);
		return map;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Map<String, Object> chartValue(TaskItemVo v) throws GlobalException {
		StringBuffer jpql = new StringBuffer(
				"select itemName,max(value),min(value),avg(value) from " + taskItemDao.getEntityName(TaskItem.class) + " where isDel='" + TaskItem.N + "' ");
		if (!StrUtils.isBlankOrNull(v.getStartDate()) && v.getStartDate().equals("thisYear")) {
			jpql.append(" AND testTime>='" + DateUtils.getYear() + "-01-01" + "'");
		} else if (!StrUtils.isBlankOrNull(v.getStartDate()) && v.getStartDate().equals("oneYear")) {
			jpql.append(" AND testTime<='" + DateUtils.getCurrDateStr() + "'");
			jpql.append(" AND testTime>='" + ((Integer.parseInt(DateUtils.getYear()) - 1) + "-" + DateUtils.getMonth() + "-" + DateUtils.getDay()) + "'");
		}
		jpql.append(" group by itemId");
		List<Object[]> objList = taskItemDao.query(jpql.toString()).getResultList();
		Map<String, Object> map = new HashMap<String, Object>();
		List<TaskItemVo> itemList = new LinkedList<TaskItemVo>();
		String[] itemNames = new String[objList.size()];
		Float[] maxs = new Float[objList.size()];
		Float[] mins = new Float[objList.size()];
		Float[] averages = new Float[objList.size()];

		if (objList != null) {
			int n = 0;
			for (Object[] obj : objList) {
				TaskItemVo vo = new TaskItemVo();
				vo.setItemName(String.valueOf(obj[1]));
				itemList.add(vo);
				itemNames[n] = String.valueOf(obj[1]);
				float max = 0;
				if (!StrUtils.isBlankOrNull(String.valueOf(obj[2]))) {
					try {
						max = Float.valueOf(String.valueOf(obj[2]));
					} catch (NumberFormatException e) {
						max = 0;
					}
				}
				maxs[n] = max;
				float min = 0;
				if (!StrUtils.isBlankOrNull(String.valueOf(obj[3]))) {
					try {
						min = Float.valueOf(String.valueOf(obj[3]));
					} catch (NumberFormatException e) {
						min = 0;
					}
				}
				mins[n] = min;
				float average = 0;
				if (!StrUtils.isBlankOrNull(String.valueOf(obj[4]))) {
					try {
						average = Float.valueOf(String.valueOf(obj[4]));
					} catch (NumberFormatException e) {
						average = 0;
					}
				}
				averages[n] = average;
				n++;
			}
		}
		map.put("itemList", itemList);
		map.put("itemNames", itemNames);
		map.put("maxs", maxs);
		map.put("mins", mins);
		map.put("averages", averages);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ObjVo> list4Uw(TaskItemVo v) throws GlobalException {
		String orgId = getCurrent().getOrgId();
		if (!StrUtils.isBlankOrNull(v.getOrgId())) {
			orgId = v.getOrgId();
		}
		List<String> orgIds = orgDao.listChild(orgId);
		orgIds.add(orgId);
		String jpql = "FROM " + accountRoleDao.getEntityName(AccountRole.class) + " WHERE role.isDel=" + Po.N + " AND  account.isDel='" + Po.N + "'";
		jpql += " AND role.code like '" + Constants.ROLE_TESTER + "'";
		if (!StrUtils.isBlankOrNull(v.getTestMan())) {
			jpql += " AND account.user.name like '%" + v.getTestMan() + "%'";
		}
		if (!StrUtils.isBlankOrNull(v.getDeptName())) {
			jpql += " AND account.org.path like '%" + v.getDeptName() + "%' ";
		}
		jpql += " AND account.org.id in ('" + String.join("','", orgIds) + "')";
		List<AccountRole> acctList = accountRoleDao.list(jpql);
		Map<String, ObjVo> objMap = new HashMap<String, ObjVo>();
		for (AccountRole accountRole : acctList) {
			Account at = accountRole.getAccount();
			ObjVo obj = new ObjVo();
			obj.setObjId(at.getId());
			obj.setObjName(at.getUser().getName());
			obj.setDeptId(at.getOrg().getId());
			if (null != at.getOrg().getParent()) {
				obj.setDeptName(at.getOrg().getParent().getName() + "/" + at.getOrg().getName());
			} else {
				obj.setDeptName(at.getOrg().getName());
			}
			objMap.put(at.getId(), obj);
		}
		// 未完成项目数据
		StringBuffer sql = new StringBuffer("SELECT it.test_man_id,count(it.id) FROM " + tablePrefix + taskItemDao.getEntityName(TaskItem.class) + " it");
		sql.append(" WHERE it.org_id in('" + String.join("','", orgIds) + "') AND it.is_del='" + Po.N + "' ");
		sql.append(" AND it.status='" + EunmTask.ITEM_LR.getStatus() + "' ");
		if (!StrUtils.isBlankOrNull(v.getTestMan())) {
			sql.append(" AND it.test_man like '%" + v.getTestMan() + "%' ");
		}
		if (!StrUtils.isBlankOrNull(v.getDeptName())) {
			sql.append(" AND it.dept_name like '%" + v.getDeptName() + "%' ");
		}
		sql.append(" group by it.test_man_id");
		List<Object[]> objList = taskItemDao.queryBySql(sql.toString());
		if (null != objList && objList.size() > 0) {
			for (Object[] o : objList) {
				ObjVo obj = objMap.get(String.valueOf(o[0]));
				if (null != obj) {
					obj.setNum(Integer.valueOf(String.valueOf(o[1])));
					objMap.put(String.valueOf(o[0]), obj);
				}
			}
		}
		// 已完成项目数
		sql = new StringBuffer("SELECT it.test_man_id,count(it.id) FROM " + tablePrefix + taskItemDao.getEntityName(TaskItem.class) + " it");
		sql.append(" WHERE it.org_id in('" + String.join("','", orgIds) + "') AND it.is_del='" + Po.N + "' ");
		sql.append(" AND ( it.status='" + EunmTask.ITEM_JY.getStatus() + "' or  it.status='" + EunmTask.ITEM_HZ.getStatus() + "' or  it.status='"
				+ EunmTask.TASK_END.getStatus() + "')");
		if (!StrUtils.isBlankOrNull(v.getTestMan())) {
			sql.append(" AND it.test_man like '%" + v.getTestMan() + "%' ");
		}
		if (!StrUtils.isBlankOrNull(v.getDeptName())) {
			sql.append(" AND it.dept_name like '%" + v.getDeptName() + "%' ");
		}
		if (!StrUtils.isBlankOrNull(v.getStartDate())) {
			sql.append(" AND it.test_time>='" + v.getStartDate() + " 00:00：00'");
		}
		if (!StrUtils.isBlankOrNull(v.getEndDate())) {
			sql.append(" AND it.test_time<='" + v.getEndDate() + " 23:59：59'");
		}
		sql.append(" group by it.test_man_id");
		objList = taskItemDao.queryBySql(sql.toString());
		if (null != objList && objList.size() > 0) {
			for (Object[] o : objList) {
				ObjVo obj = objMap.get(String.valueOf(o[0]));
				if (obj == null) {
					continue;
				}
				obj.setComp(Integer.valueOf(String.valueOf(o[1])));
				objMap.put(String.valueOf(o[0]), obj);
			}
		}
		// 已完成样品数
		// 已完成项目数
		sql = new StringBuffer("SELECT tim.test_man_id,count(tr.id) FROM " + tablePrefix + taskItemDao.getEntityName(TestResult.class) + " tr");
		sql.append(" join " + tablePrefix + taskItemDao.getEntityName(TestItem.class) + " it on it.id=tr.it_id AND it.is_del='" + Po.N + "' ");
		sql.append(" join " + tablePrefix + taskItemDao.getEntityName(TaskItem.class) + " tim on tim.id=it.tim_id AND tim.is_del='" + Po.N
				+ "' and tim.org_id in('" + String.join("','", orgIds) + "') ");
		sql.append(" WHERE tr.is_del='" + Po.N + "' ");
		sql.append(" AND (tim.status='" + EunmTask.ITEM_JY.getStatus() + "' or  tim.status='" + EunmTask.ITEM_HZ.getStatus() + "' or  tim.status='"
				+ EunmTask.TASK_END.getStatus() + "')");
		if (!StrUtils.isBlankOrNull(v.getDeptName())) {
			sql.append(" AND tim.dept_name like '%" + v.getDeptName() + "%' ");
		}
		if (!StrUtils.isBlankOrNull(v.getTestMan())) {
			sql.append(" AND tim.test_man like '%" + v.getTestMan() + "%' ");
		}
		if (!StrUtils.isBlankOrNull(v.getStartDate())) {
			sql.append(" AND tim.test_time>='" + v.getStartDate() + " 00:00：00'");
		}
		if (!StrUtils.isBlankOrNull(v.getEndDate())) {
			sql.append(" AND tim.test_time<='" + v.getEndDate() + " 23:59：59'");
		}
		sql.append(" group by tim.test_man_id");
		objList = taskItemDao.queryBySql(sql.toString());
		if (null != objList && objList.size() > 0) {
			for (Object[] o : objList) {
				ObjVo obj = objMap.get(String.valueOf(o[0]));
				if (obj == null) {
					continue;
				}
				obj.setYpNum(Integer.valueOf(String.valueOf(o[1])));
				objMap.put(String.valueOf(o[0]), obj);
			}
		}
		List<ObjVo> objVoList = new ArrayList<ObjVo>(objMap.values());
		return objVoList;
	}
}
