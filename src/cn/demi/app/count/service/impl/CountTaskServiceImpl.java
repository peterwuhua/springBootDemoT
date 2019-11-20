package cn.demi.app.count.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.PropertiesTools;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.count.service.ICountTaskService;
import cn.demi.app.count.vo.AppObjvo;
import cn.demi.app.count.vo.CountItemList;
import cn.demi.app.count.vo.CountKhList;
import cn.demi.app.count.vo.CountKqList;
import cn.demi.app.count.vo.CountPriceList;
import cn.demi.app.count.vo.CountProject;
import cn.demi.app.count.vo.CountRept;
import cn.demi.app.count.vo.CountTaskInfo;
import cn.demi.app.count.vo.CountTaskList;
import cn.demi.app.count.vo.ItemPoint;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IAccountRoleDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.AccountRole;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.office.dao.IKqDao;
import cn.demi.office.po.Kq;
import cn.demi.pfm.dao.ISRecordDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("app.countTaskService")
public class CountTaskServiceImpl implements ICountTaskService {
	 private String tablePrefix = PropertiesTools.getPropertiesValueByFileAndKey("resources/jdbc.properties", "namingStrategy.tablePrefix");// 数据库表名前缀
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IItemDao iItemTestDao;
	@Autowired
	private ITaskPointDao taskPointDao;
	@Autowired
	private IKqDao kqDao;
	@Autowired
	ISRecordDao recordDao;
	@Autowired
	IReportDao reportDao;
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private IAccountRoleDao accountRoleDao;
	@Autowired
	private ITaskItemDao taskItemDao;

	/**
	 * 任务查询列表
	 *
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@Override
	public List<CountTaskList> taskList(ObjVo v, String no, String custname, String samptypename, String tasktype,
			String beginDate, String finishDate) throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"SELECT task.id,task.`no` as no,taskCus.cust_name as custname ,task.samp_type_name as samptypename,task.point_names,task.finish_date  , task.`status`,task.task_type as tasktype FROM v_bus_task task JOIN v_bus_cust AS taskCus ON task.cust_id = taskCus.id AND task.is_del = '0'");
		if (beginDate != null && !("").equals(beginDate) && !("null").equals(beginDate)) {
			hql.append("  and  task.finish_date >= '" + beginDate + "'");
		}
		if (finishDate != null && !("").equals(finishDate) && !("null").equals(finishDate)) {
			hql.append("  and  task.finish_date <= '" + finishDate + "'");
		}
		hql.append("  and no like '%" + no + "%' and taskCus.cust_name  like '%" + custname
				+ "%' and  task.samp_type_name like '%" + samptypename + "%' and  task.task_type like '%" + tasktype
				+ "%'  ");

		hql.append("ORDER BY task.last_upd_time DESC");

		Query query = taskDao.queryBysql(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}

		List list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<CountTaskList> outList = new ArrayList<>();
		if (null != list) {
			for (Object o : list) {
				Object[] objects = (Object[]) o;
				CountTaskList countTaskList = new CountTaskList();
				String id = "";
				if (objects[0] != null) {
					id = objects[0].toString();
				}
				String tempNo = "";
				if (objects[1] != null) {
					tempNo = objects[1].toString();
				}
				String tempCustName = "";
				if (objects[2] != null) {
					tempCustName = objects[2].toString();
				}
				String tempSamptypename = "";
				if (objects[3] != null) {
					tempSamptypename = objects[3].toString();
				}
				String pointName = "";
				if (objects[4] != null) {
					pointName = objects[4].toString();
				}
				String date = "";
				if (objects[5] != null) {
					date = objects[5].toString();
				}
				String status = "";
				if (objects[6] != null) {
					status = objects[6].toString();
				}
				String tempTaskType = "";
				if (objects[7] != null) {
					tempTaskType = objects[7].toString();
				}
				countTaskList.setId(id);
				countTaskList.setNo(tempNo);
				countTaskList.setCustName(tempCustName);
				countTaskList.setSampName(tempSamptypename);
				countTaskList.setPointName(pointName);
				countTaskList.setFinishDate(date);
				countTaskList.setStatus(EunmTask.getName(status));
				countTaskList.setTaskType(tempTaskType);
				outList.add(countTaskList);
			}
		}
		return outList;
	}

	/**
	 * 任务详情
	 *
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@Override
	public CountTaskInfo taskInfo(ObjVo v) throws GlobalException {
		Task task = taskDao.findById(v.getId());
		Cust cust = task.getCust();
		List<TaskPoint> tpList = taskPointDao.listByTaskId(task.getId());
		List<ItemPoint> itemPoints = new ArrayList<>();
		for (TaskPoint taskPoint : tpList) {
			ItemPoint itemPoint = new ItemPoint();
			itemPoint.setSampId(taskPoint.getSampTypeId());
			itemPoint.setSampName(taskPoint.getSampName());
			itemPoint.setSampNum(taskPoint.getSampNum());
			itemPoint.setPointName(taskPoint.getPointName());
			itemPoint.setPointCode(taskPoint.getPointCode());
			itemPoint.setItemNames(taskPoint.getItemNames());
			itemPoint.setItemIds(taskPoint.getItemIds());
			itemPoints.add(itemPoint);
		}
		CountTaskInfo countTaskInfo = new CountTaskInfo();
		countTaskInfo.setCustName(cust.getCustName());
		countTaskInfo.setCustTel(cust.getCustTel());
		countTaskInfo.setCustUser(cust.getCustUser());
		countTaskInfo.setCustEmail(cust.getCustEmail());
		countTaskInfo.setCustZip(cust.getCustZip());
		countTaskInfo.setFax(cust.getCustFax());
		countTaskInfo.setCustAddress(cust.getCustAddress());
		countTaskInfo.setTaskType(task.getTaskType());
		countTaskInfo.setSampTypeName(task.getSampTypeName());
		countTaskInfo.setSource(task.getSource());
		countTaskInfo.setTaskSource(task.getTaskSource());
		countTaskInfo.setReportNum(task.getReportNum());
		countTaskInfo.setReportWay(task.getReportWay());
		countTaskInfo.setJj(task.getJj());
		countTaskInfo.setFinishDate(task.getFinishDate());
		countTaskInfo.setPj(task.getPj());
		countTaskInfo.setPrice(task.getPrice());
		countTaskInfo.setFb(task.getFb());
		countTaskInfo.setUserName(task.getUserName());
		countTaskInfo.setDate(task.getDate());
		countTaskInfo.setFileName(task.getFileName());
		countTaskInfo.setJcct(task.getJcct());
		countTaskInfo.setRemark(task.getRemark());
		countTaskInfo.setPoints(itemPoints);
		return countTaskInfo;
	}

	/**
	 * 费用统计列表
	 *
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@Override
	public List<CountPriceList> priceList(ObjVo v, String no, String beginDate, String finishDate, String taskType)
			throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"FROM " + taskDao.getEntityName(Task.class) + " WHERE isDel='" + Po.N + "' ");
		if (beginDate != null && !("").equals(beginDate) && !("null").equals(beginDate)) {
			hql.append("  and  finish_date >= '" + beginDate + "'");
		}
		if (finishDate != null && !("").equals(finishDate) && !("null").equals(finishDate)) {
			hql.append("  and  finish_date <= '" + finishDate + "'");
		}
		if (no != null && !("").equals(no) && !("null").equals(no)) {
			hql.append("and no like'%" + no + "%' ");
		}
		if (taskType != null && !("").equals(taskType) && !("null").equals(taskType)) {
			hql.append("and task_type like'%" + taskType + "%' ");
		}

		hql.append(" ORDER BY lastUpdTime DESC");
		Query query = taskDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Task> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<CountPriceList> outList = new ArrayList<>();
		if (null != list) {
			for (Task task : list) {
				CountPriceList countPriceList = new CountPriceList();
				countPriceList.setId(task.getId());
				countPriceList.setNo(task.getNo());
				countPriceList.setCustName(task.getCust().getCustName());
				countPriceList.setData(task.getDate());
				countPriceList.setPrice(task.getPrice());
				countPriceList.setOrgName(task.getOrgName());
				countPriceList.setStatus(EunmTask.getName(task.getStatus()));
				countPriceList.setTaskType(task.getTaskType());
				outList.add(countPriceList);
			}
		}
		return outList;
	}

	/**
	 * 项目统计列表
	 *
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@Override
	public List<CountItemList> itemList(ObjVo v, String no, String itemName, String sampTypeName, String orgName,
			String userName, String beginDate, String finishDate, String taskType) throws GlobalException {
		// 项目
		StringBuffer hql = new StringBuffer(
				"SELECT itemTest.id AS id, itemTest.`status`, task.`no`, itemTest.test_man as userName, itemTest.check_man as checkName,itemTest.test_time,task.point_names,task.samp_name,"
						+ "task.org_name as orgName, task.task_type AS taskType, itemTest.item_name AS itemName,taskCus.cust_name, itemTest.samp_num,task.samp_name as sampName FROM v_bus_task_item itemTest"
						+ "    JOIN v_bus_task task ON itemTest.task_id = task.id 	  JOIN v_bus_cust taskCus ON task.cust_id = taskCus.id AND itemTest.is_del = '0'");
		if (beginDate != null && !("").equals(beginDate) && !("null").equals(beginDate)) {
			hql.append("  and  task.finish_date >= '" + beginDate + "'");
		}
		if (finishDate != null && !("").equals(finishDate) && !("null").equals(finishDate)) {
			hql.append("  and  task.finish_date <= '" + finishDate + "'");
		}

		hql.append("and no like'%" + no + "%' and itemTest.item_name like'%" + itemName
				+ "%'  and task.samp_name like'%" + sampTypeName + "%' " + "and task.org_name like'%" + orgName
				+ "%' and  itemTest.test_man like'%" + userName + "%' and task.task_type like'%" + taskType + "%' ");
		hql.append(" order BY itemTest.last_upd_time desc");
		Query query = iItemTestDao.queryBysql(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<CountItemList> outList = new ArrayList<>();
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			CountItemList countItemList = new CountItemList();
			String checkMan = "";
			if (objects[4] != null) {
				checkMan = objects[4].toString();
			}

			countItemList.setItemId(objects[0] == null ? "" : objects[0].toString());
			countItemList.setNo(objects[2] == null ? "" : objects[2].toString());
			countItemList.setTaskType(objects[9] == null ? "" : objects[9].toString());
			countItemList.setItemName(objects[10] == null ? "" : objects[10].toString());
			countItemList.setCustName(objects[11] == null ? "" : objects[11].toString());
			countItemList.setSampName(objects[7] == null ? "" : objects[7].toString());
			countItemList.setPointNames(objects[6] == null ? "" : objects[6].toString());
			countItemList.setTestMan(objects[3] == null ? "" : objects[3].toString());
			countItemList.setTestTime(objects[5] == null ? "" : objects[5].toString());
			countItemList.setValue(objects[12] == null ? "" : objects[12].toString());
			countItemList.setCheckMan(checkMan);

			outList.add(countItemList);
		}
		return outList;
	}

	/**
	 * 人员考核统计
	 *
	 * @return
	 * @throws GlobalException
	 */
	@Override
	public List<CountKhList> khList() throws GlobalException {
		List list = recordDao.queryBySql(
				"SELECT a.`name`,( 100- IFNULL( SUM( c.`value` ), 0 ) ) AS score ,a.org_name as org FROM v_sys_user a "
						+ "LEFT JOIN v_pfm_record b ON a.`id` = b.user_id LEFT JOIN v_pfm_rules c ON b.`code` = c.`code` GROUP BY a.`name`");
		List<CountKhList> tempList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			CountKhList countKhList = new CountKhList();
			Object[] o = (Object[]) list.get(i);
			countKhList.setName(o[0].toString());
			countKhList.setScore(o[1].toString());
			countKhList.setOrgName(o[2].toString());
			tempList.add(countKhList);
		}
		return tempList;
	}

	/**
	 * 考勤查询列表
	 *
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@Override
	public List<CountKqList> kqList(ObjVo v, String no, String name, String beginDate, String finishDate,
			String taskType) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + kqDao.getEntityName(Kq.class) + " WHERE isDel='" + Po.N + "' ");
		if (beginDate != null && !"".equals(beginDate) && !("null").equals(beginDate)) {
			hql.append("  and   date >= '" + beginDate + "'");
		}
		if (finishDate != null && !"".equals(finishDate) && !("null").equals(finishDate)) {
			hql.append("  and   date <= '" + finishDate + "'");
		}
		hql.append("and no like'%" + no + "%' and user_name like'%" + name + "%' and   type like '%" + taskType + "%'");
		hql.append(" ORDER BY lastUpdTime DESC");
		Query query = kqDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Kq> kqs = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<CountKqList> kqLists = new ArrayList<>();
		for (Kq kq : kqs) {
			CountKqList countKqList = new CountKqList();
			countKqList.setNo(kq.getNo());
			countKqList.setType(kq.getType());
			countKqList.setDeptName(kq.getDeptName());
			countKqList.setUserName(kq.getUserName());
			countKqList.setDate(kq.getDate());
			countKqList.setEndTime(kq.getEndTime());
			countKqList.setContent(kq.getContent());
			countKqList.setRemark(kq.getRemark());
			countKqList.setAuditName(kq.getAuditName());
			kqLists.add(countKqList);
		}
		return kqLists;
	}

	@Override
	public List<CountRept> appReportList(int page, int row, ObjVo v, String no, String taskNo, String cusname,
			String sampTypeName, String beginDate, String finishDate, String taskType) throws GlobalException {

		List<CountRept> outList = new ArrayList<>();

		// 报告列表
		StringBuffer hql = new StringBuffer(
				"SELECT report.id as id, report.`status` as status,  report.report_no as no,task.task_type as flag,  report.report_date as date,taskCus.cust_name as cusname,task.`no` as taskNo,task.samp_name as sampName FROM "
						+ " v_bus_report report    JOIN v_bus_task AS task ON report.task_id=task.id   JOIN v_bus_cust AS taskCus ON task.cust_id = taskCus.id AND report.is_del = '0' ");
		if (beginDate != null && !("").equals(beginDate) && !("null").equals(beginDate)) {
			hql.append("  and  task.finish_date >= '" + beginDate + "'");
		}
		if (finishDate != null && !("").equals(finishDate) && !("null").equals(finishDate)) {
			hql.append("  and  task.finish_date <= '" + finishDate + "'");
		}

		if (no != null && !no.equals("") && !no.equals("null")) {
			hql.append(" and rpt.report_no like'%" + no + "%'");
		}
		if (taskNo != null && !taskNo.equals("") && !taskNo.equals("null")) {
			hql.append(" and task.`no` like'%" + taskNo + "%'");
		}
		if (cusname != null && !cusname.equals("") && !cusname.equals("null")) {
			hql.append(" and taskCus.cust_name like'%" + cusname + "%'");
		}

		if (sampTypeName != null && !sampTypeName.equals("") && !sampTypeName.equals("null")) {
			hql.append(" and task.samp_name  like'%" + sampTypeName + "%'");
		}
		if (taskType != null && !taskType.equals("") && !taskType.equals("null")) {
			hql.append(" and task.task_type like'%" + taskType + "%'");
		}

		hql.append(" order BY report.last_upd_time desc");

		Query query = taskDao.queryBysql(hql.toString());
		List list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			CountRept tvAllTaskVo = new CountRept();
			tvAllTaskVo.setId((String) objects[0]);
			tvAllTaskVo.setState(EunmTask.getName((String) objects[1]));
			tvAllTaskVo.setNo((String) objects[2]);
			tvAllTaskVo.setTaskType((String) objects[3]);
			tvAllTaskVo.setFinish_date(objects[4] == null ? "" : (String) objects[4]);
			tvAllTaskVo.setDate(objects[4] == null ? "" : (String) objects[4]);
			tvAllTaskVo.setCusName(objects[5] == null ? "" : objects[5].toString());
			tvAllTaskVo.setTaskNo(objects[6] == null ? "" : (String) objects[6]);
			tvAllTaskVo.setSampleName(objects[7] == null ? "" : (String) objects[7]);
			tvAllTaskVo.setTitle(objects[5] == null ? "" : objects[5].toString());
			outList.add(tvAllTaskVo);
		}

		return outList;
	}

	@Override
	public List<CountProject> appProjectList(ObjVo v, String no, String custName, String wtName, String htPrice,
			String qdDate, String pay, String htSt) throws GlobalException {

		List<CountProject> outList = new ArrayList<>();

		// 报告列表
		StringBuffer hql = new StringBuffer(
				"SELECT project.id,project.`no`,cust.cust_name,cust.wt_name,project.ht_price,project.qd_date,project.pay,project.ht_st FROM v_bus_project project JOIN v_bus_cust cust on project.cust_id = cust.id");
		hql.append(" where project.is_del ='0' and project.qd_date is not NULL  ");

		if (no != null && !("").equals(no) && !("null").equals(no)) {
			hql.append(" and project.`no` like'%" + no + "%'");
		}
		if (custName != null && !("").equals(custName) && !("null").equals(custName)) {
			hql.append(" and cust.cust_name like'%" + custName + "%'");
		}
		if (wtName != null && !("").equals(wtName) && !("null").equals(wtName)) {
			hql.append(" and cust.wt_name` like'%" + wtName + "%'");
		}
		if (htPrice != null && !("").equals(htPrice) && !("null").equals(htPrice)) {
			hql.append(" and project.ht_price like'%" + htPrice + "%'");
		}
		if (qdDate != null && !("").equals(qdDate) && !("null").equals(qdDate)) {
			hql.append(" and  project.qd_date like'%" + qdDate + "%'");
		}
		if (pay != null && !("").equals(pay) && !("null").equals(pay)) {
			hql.append(" and project.pay like'%" + pay + "%'");
		}
		if (htSt != null && !("").equals(htSt) && !("null").equals(htSt)) {
			hql.append(" and project.ht_st like'%" + htSt + "%'");
		}

		hql.append(" ORDER BY project.last_upd_time DESC");
		Query query = taskDao.queryBysql(hql.toString());
		List list = query.setFirstResult((v.getPage() - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			CountProject tvAllTaskVo = new CountProject();
			tvAllTaskVo.setId(StrUtils.null2Str(objects[0]));
			tvAllTaskVo.setNo(StrUtils.null2Str(objects[1]));
			tvAllTaskVo.setCustName(StrUtils.null2Str(objects[2]));
			tvAllTaskVo.setWtName(StrUtils.null2Str(objects[3]));
			tvAllTaskVo.setHtPrice(StrUtils.null2Str(objects[4]));
			tvAllTaskVo.setQdDate(StrUtils.null2Str(objects[5]));
			tvAllTaskVo.setPay(StrUtils.null2Str(objects[6]));
			tvAllTaskVo.setHtSt(StrUtils.null2Str(objects[7]));
			outList.add(tvAllTaskVo);
		}

		return outList;
	}
 
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AppObjvo> list4Uw(TaskItemVo v,ObjVo objVo) throws GlobalException {
		String orgId=objVo.getOrgId();
		if(!StrUtils.isBlankOrNull(objVo.getOrgId())) {
			orgId=objVo.getOrgId();
		}
		List<String> orgIds=orgDao.listChild(orgId);
		orgIds.add(orgId);
		String jpql="FROM "+accountRoleDao.getEntityName(AccountRole.class)+" WHERE role.isDel="+Po.N+" AND  account.isDel='"+Po.N+"'";
		jpql+=" AND role.code like '"+Constants.ROLE_TESTER+"'";
		if(!StrUtils.isBlankOrNull(v.getTestMan())) {
			jpql+=" AND account.user.name like '%"+v.getTestMan()+"%'";
		}
		if(!StrUtils.isBlankOrNull(v.getDeptName())) {
			jpql+=" AND account.org.path like '%"+v.getDeptName()+"%' ";
		}
		jpql+=" AND account.org.id in ('"+String.join("','", orgIds)+"')";
		List<AccountRole>  acctList=accountRoleDao.list(jpql);
		Map<String,AppObjvo> objMap=new HashMap<String,AppObjvo>();
		for (AccountRole accountRole : acctList) {
			Account at=accountRole.getAccount();
			AppObjvo obj=new AppObjvo();
			obj.setObjId(at.getId());
			obj.setObjName(at.getUser().getName());
			obj.setDeptId(at.getOrg().getId());
			if(null!=at.getOrg().getParent()) {
				obj.setDeptName(at.getOrg().getParent().getName()+"/"+at.getOrg().getName());
			}else {
				obj.setDeptName(at.getOrg().getName());
			}
			objMap.put(at.getId(), obj);
		}
		//未完成项目数据
		StringBuffer sql=new StringBuffer("SELECT it.test_man_id,count(it.id) FROM "+tablePrefix+taskItemDao.getEntityName(TaskItem.class)+" it");
		sql.append(" WHERE it.org_id in('"+String.join("','", orgIds)+"') AND it.is_del='"+Po.N+"' ");
		sql.append(" AND it.status='"+EunmTask.ITEM_LR.getStatus()+"' ");
		if(!StrUtils.isBlankOrNull(v.getTestMan())) {
			sql.append(" AND it.test_man like '%"+v.getTestMan()+"%' ");
		}
		if(!StrUtils.isBlankOrNull(v.getDeptName())) {
			sql.append(" AND it.dept_name like '%"+v.getDeptName()+"%' ");
		}
		sql.append(" group by it.test_man_id");
		List<Object[]> objList=taskItemDao.queryBySql(sql.toString());
		if(null!=objList && objList.size()>0) {
			for (Object[] o : objList) {
				AppObjvo obj=objMap.get(String.valueOf(o[0]));
				if(null!=obj) {
					obj.setNum(Integer.valueOf(StrUtils.null2Str(o[1])));
					objMap.put(String.valueOf(o[0]), obj);
				}
			}
		}
		//已完成项目数
		sql=new StringBuffer("SELECT it.test_man_id,count(it.id) FROM "+tablePrefix+taskItemDao.getEntityName(TaskItem.class)+" it");
		sql.append(" WHERE it.org_id in('"+String.join("','", orgIds)+"') AND it.is_del='"+Po.N+"' ");
		sql.append(" AND ( it.status='"+EunmTask.ITEM_JY.getStatus()+"' or  it.status='"+EunmTask.ITEM_HZ.getStatus()+"' or  it.status='"+EunmTask.TASK_END.getStatus()+"')");
		if(!StrUtils.isBlankOrNull(v.getTestMan())) {
			sql.append(" AND it.test_man like '%"+v.getTestMan()+"%' ");
		}
		if(!StrUtils.isBlankOrNull(v.getDeptName())) {
			sql.append(" AND it.dept_name like '%"+v.getDeptName()+"%' ");
		}
		if(!StrUtils.isBlankOrNull(v.getStartDate())) {
			sql.append(" AND it.test_time>='"+v.getStartDate()+" 00:00：00'");
		}
		if(!StrUtils.isBlankOrNull(v.getEndDate())) {
			sql.append(" AND it.test_time<='"+v.getEndDate()+" 23:59：59'");
		}
		sql.append(" group by it.test_man_id");
		objList=taskItemDao.queryBySql(sql.toString());
		if(null!=objList && objList.size()>0) {
			for (Object[] o : objList) {
				AppObjvo obj=objMap.get(String.valueOf(o[0]));
				if(null!=obj) {
				obj.setComp(Integer.valueOf(StrUtils.null2Str(o[1])));
				objMap.put(String.valueOf(o[0]), obj);
				}
			}
		}
		//已完成样品数
		//已完成项目数
		sql=new StringBuffer("SELECT tim.test_man_id,count(tr.id) FROM "+tablePrefix+taskItemDao.getEntityName(TestResult.class)+" tr");
		sql.append(" join "+tablePrefix+taskItemDao.getEntityName(TestItem.class)+" it on it.id=tr.it_id AND it.is_del='"+Po.N+"' ");
		sql.append(" join "+tablePrefix+taskItemDao.getEntityName(TaskItem.class)+" tim on tim.id=it.tim_id AND tim.is_del='"+Po.N+"' and tim.org_id in('"+String.join("','", orgIds)+"') ");
		sql.append(" WHERE tr.is_del='"+Po.N+"' ");
		sql.append(" AND (tim.status='"+EunmTask.ITEM_JY.getStatus()+"' or  tim.status='"+EunmTask.ITEM_HZ.getStatus()+"' or  tim.status='"+EunmTask.TASK_END.getStatus()+"')");
		if(!StrUtils.isBlankOrNull(v.getDeptName())) {
			sql.append(" AND tim.dept_name like '%"+v.getDeptName()+"%' ");
		}
		if(!StrUtils.isBlankOrNull(v.getTestMan())) {
			sql.append(" AND tim.test_man like '%"+v.getTestMan()+"%' ");
		}
		if(!StrUtils.isBlankOrNull(v.getStartDate())) {
			sql.append(" AND tim.test_time>='"+v.getStartDate()+" 00:00：00'");
		}
		if(!StrUtils.isBlankOrNull(v.getEndDate())) {
			sql.append(" AND tim.test_time<='"+v.getEndDate()+" 23:59：59'");
		}
		sql.append(" group by tim.test_man_id");
		objList=taskItemDao.queryBySql(sql.toString());
		if(null!=objList && objList.size()>0) {
			for (Object[] o : objList) {
				AppObjvo obj=objMap.get(String.valueOf(o[0]));
				if(null!=obj) {
				obj.setYpNum(Integer.valueOf(StrUtils.null2Str(o[1])));
				objMap.put(String.valueOf(o[0]), obj);
				}
			}
		}
		List<AppObjvo> objVoList=new ArrayList<AppObjvo>(objMap.values());
		return objVoList;
	}
}
