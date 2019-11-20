package cn.demi.app.bus.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.PropertiesTools;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.bus.service.AppTaskService;
import cn.demi.app.bus.vo.AllTaskVo;
import cn.demi.app.bus.vo.AppCustPointSelect;
import cn.demi.app.bus.vo.AppTask;
import cn.demi.app.bus.vo.AppTaskEdit;
import cn.demi.app.bus.vo.AppTaskFb;
import cn.demi.app.bus.vo.AppTaskImSelect;
import cn.demi.app.bus.vo.AppTaskTp;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.dao.IRoleFunDao;
import cn.demi.base.system.dao.IUnitDao;
import cn.demi.base.system.po.RoleFun;
import cn.demi.base.system.po.Unit;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pjt.dao.ICustDao;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.dao.ITaskFbDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.dao.ITaskRoomDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskFb;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.service.ITaskService;
import cn.demi.bus.task.vo.TaskFbVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.cus.customer.dao.IClientDao;
import cn.demi.cus.customer.dao.IClientPointDao;
import cn.demi.cus.customer.dao.IContactsDao;
import cn.demi.cus.customer.dao.ICusClientDao;
import cn.demi.cus.customer.dao.ICustomerDao;
import cn.demi.cus.customer.po.Client;
import cn.demi.cus.customer.po.ClientPoint;
import cn.demi.cus.customer.po.Contacts;
import cn.demi.cus.customer.po.CusClient;
import cn.demi.cus.customer.po.Customer;
import cn.demi.cus.customer.vo.ClientVo;
import cn.demi.cus.customer.vo.CustVo;
import cn.demi.cus.customer.vo.CustomerVo;
import cn.demi.init.car.po.CarUse;
import cn.demi.init.sp.vo.PcUnit;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.st.service.ISampTypeService;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.vo.ItemVo;
import cn.demi.res.dao.ISupplierDao;
import cn.demi.res.po.Fb;
import cn.demi.res.po.Supplier;

@Service("app.TaskService")
public class AppTaskServiceImpl extends BaseServiceImpl<ItemVo,Item> implements AppTaskService  {
	private String tablePrefix = PropertiesTools.getPropertiesValueByFileAndKey("resources/jdbc.properties",
			"namingStrategy.tablePrefix");// 数据库表名前缀

	@Autowired
	private IRoleFunDao roleFunDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IOrgDao orgDao;
	@Autowired
	private ISampTypeService sampTypeService;
	@Autowired
	private ITaskService taskService;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private IClientDao clientDao;
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private IClientPointDao clientPointDao;
	@Autowired
	private ISupplierDao supplierDao;
	@Autowired
	private IProgressDao progressDao;

	@Autowired
	private ICustDao custDao;
	@Autowired
	private ITaskPointDao taskPointDao;

	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IContactsDao contactsDao;

	@Autowired
	private ICusClientDao cusClientDao;

	@Autowired
	private ITaskRoomDao taskRoomDao;
	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private IReportDao reportDao;
	@Autowired
	private ISamplingDao samplingDao;

	@Autowired
	private IUnitDao unitDao;
	@Autowired
	private ITaskFbDao taskFbDao;

	@Autowired
	private IImDao imDao;

	@Override
	public List<AllTaskVo> appAllTask(ObjVo v) throws GlobalException {
		List<AllTaskVo> outList = new ArrayList<>();
		// 任务
		StringBuffer hql = new StringBuffer(
				"SELECT task.id AS id, task.`no` AS NO, cust.cust_name, 	fun.`name` AS flag, task.date AS date FROM 	v_sys_function fun");
		hql.append(
				" JOIN v_bus_progress pg ON fun.wf_code = pg. STATUS JOIN v_bus_task task ON pg.bus_id = task.id LEFT JOIN v_bus_cust cust on  task.cust_id  = cust.id");
		hql.append(" AND pg.is_del = '0'");
		hql.append(" where fun.is_del='" + Po.N + "' ");
		hql.append(" and (pg.user_id like '%" + v.getUserId() + "%' or pg.user_id is null and pg.org_id in ('"
				+ String.join("','", v.getDepId()) + "') or pg.user_id is null and pg.org_id is null)");
		hql.append(" and fun.id  in (select fun_id from " + tablePrefix + roleFunDao.getEntityName(RoleFun.class)
				+ " where role_id in ('" + v.getRoleIds().replace(",", "','") + "'))");
		hql.append(" order by fun.sort asc");
		List list1 = taskDao.queryBySql(hql.toString());
		for (Object o : list1) {
			Object[] objects = (Object[]) o;
			AllTaskVo aTaskVo = new AllTaskVo();
			aTaskVo.setId(StrUtils.null2Str(objects[0]));
			aTaskVo.setNo(StrUtils.null2Str(objects[1]));
			aTaskVo.setCusName(StrUtils.null2Str(objects[2]));
			aTaskVo.setFlag(StrUtils.null2Str(objects[3]));
			aTaskVo.setDate(StrUtils.null2Str(objects[4]));
			outList.add(aTaskVo);
		}
		// 报告
		hql = new StringBuffer(
				"SELECT project.id AS id, project.`no` AS NO, cust.cust_name AS custname,  fun.`name` AS flag, project.rdate AS date FROM v_sys_function fun");
		hql.append(
				" JOIN v_bus_progress pg ON fun.wf_code = pg. STATUS JOIN v_bus_project project ON project.progress_id = pg.id LEFT JOIN v_bus_task task ON project.task_type = task.task_type LEFT JOIN v_bus_cust cust   ON task.cust_id = cust.id");
		hql.append(" AND pg.is_del = '0'");
		hql.append(" where fun.is_del='" + Po.N + "' ");
		hql.append(" and (pg.user_id like '%" + v.getUserId() + "%' or pg.user_id is null and pg.org_id in ('"
				+ String.join("','", v.getDepId()) + "') or pg.user_id is null and pg.org_id is null)");
		hql.append(" and fun.id  in (select fun_id from " + tablePrefix + roleFunDao.getEntityName(RoleFun.class)
				+ " where role_id in ('" + v.getRoleIds().replace(",", "','") + "'))");
		hql.append(" GROUP BY project.id order by fun.sort asc");
		List list2 = taskDao.queryBySql(hql.toString());
		for (Object o : list2) {
			Object[] objects = (Object[]) o;
			AllTaskVo aTaskVo = new AllTaskVo();
			aTaskVo.setId(StrUtils.null2Str(objects[0]));
			aTaskVo.setNo(StrUtils.null2Str(objects[1]));
			aTaskVo.setCusName(StrUtils.null2Str(objects[2]));
			aTaskVo.setFlag(StrUtils.null2Str(objects[3]));
			aTaskVo.setDate(StrUtils.null2Str(objects[4]));
			outList.add(aTaskVo);
		}
		// 车辆审核
		hql = new StringBuffer("SELECT id,no,code,date FROM v_init_car_use");
		hql.append(" where status='" + CarUse.ST_SH + "'  and  audit_id='" + v.getUserId() + "' or audit_id is null");
		List list3 = taskDao.queryBySql(hql.toString());
		for (Object o : list3) {
			Object[] objects = (Object[]) o;
			AllTaskVo aTaskVo = new AllTaskVo();
			aTaskVo.setId(StrUtils.null2Str(objects[0]));
			aTaskVo.setNo(StrUtils.null2Str(objects[1]));
			aTaskVo.setCusName(StrUtils.null2Str(objects[2]));
			aTaskVo.setFlag("车辆审核");
			aTaskVo.setDate(StrUtils.null2Str(objects[3]));
			outList.add(aTaskVo);
		}
		return outList;
	}

	@Override
	public List<AppTask> taskList(ObjVo v) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + taskDao.getEntityName(Task.class) + " WHERE isDel=" + Po.N);
		hql.append(" and  status='" + EunmTask.TASK_DJ.getStatus() + "'");
		hql.append(" ORDER BY lastUpdTime desc");
		Query query = taskDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		List<Task> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppTask> outList = new ArrayList<>();
		for (Task task : list) {
			AppTask appTask = new AppTask();
			appTask.setId(task.getId());
			appTask.setJj(task.getJj());
			appTask.setIsBack(task.getIsBack());
			appTask.setNo(task.getNo());
			appTask.setCustName(task.getCust().getCustName());
			appTask.setSampName(task.getSampName());
			appTask.setSource(task.getSource());
			appTask.setTaskType(task.getTaskType());
			appTask.setDate(task.getDate());
			appTask.setUserName(task.getUserName());
			appTask.setSampType(task.getSampType());
			outList.add(appTask);
		}
		return outList;
	}

	@Override
	public AppTaskEdit taskEdit(String id) throws GlobalException {
		if (!StrUtils.isBlankOrNull(id)) {
			TaskVo po = taskService.findById(id);
			AppTaskEdit vo = new AppTaskEdit();
			vo.setId(po.getId());
			vo.setSampType(po.getSampType());
			vo.setIsBack(po.getIsBack());
			vo.setCustName(po.getCustVo().getCustName());
			vo.setCustVoClientVoId(po.getCustVo().getClientVo().getId());
			vo.setCustVoCustomerVoId(po.getCustVo().getCustomerVo().getId());
			vo.setCustAddress(po.getCustVo().getCustAddress());
			vo.setCustUser(po.getCustVo().getCustUser());
			vo.setCustTel(po.getCustVo().getCustTel());
			vo.setIndustry(po.getCustVo().getIndustry());
			vo.setAttribute(po.getCustVo().getAttribute());
			vo.setProduct(po.getCustVo().getProduct());
			vo.setWtName(po.getCustVo().getWtName());
			vo.setWtAddress(po.getCustVo().getWtAddress());
			vo.setWtUser(po.getCustVo().getWtUser());
			vo.setWtTel(po.getCustVo().getTel());
			vo.setWtEmail(po.getCustVo().getWtEmail());
			vo.setWtZip(po.getCustVo().getWtZip());
			vo.setSampTypeName(po.getSampTypeName());
			vo.setSampTypeId(po.getSampTypeId());
			vo.setSampName(po.getSampName());
			vo.setReportNum(String.valueOf(po.getReportNum()));
			vo.setReportWay(po.getReportWay());
			vo.setJj(po.getJj());
			vo.setPj(po.getPj());
			vo.setStandNames(po.getStandNames());
			vo.setStandIds(po.getStandIds());
			vo.setUserName(po.getUserName());
			vo.setUserId(po.getUserId());
			vo.setRemark(po.getRemark());
			vo.setSource(po.getSource());
			vo.setDealRequest(po.getDealRequest());
			vo.setCyName(po.getCyName());
			vo.setCyDate(po.getCyDate());
			vo.setFinishDate(po.getFinishDate());
			vo.setFb(po.getFb());
			vo.setDate(po.getDate());
			vo.setSaveRequest(po.getSaveRequest());
			vo.setTaskType(po.getTaskType());
			// 检测点位信息
			if (po.getTpList() != null) {
				List<AppTaskTp> tpList = new ArrayList<>();
				for (TaskPointVo tpPo : po.getTpList()) {
					AppTaskTp tpVo = new AppTaskTp();
					tpVo.setId(tpPo.getId());
					tpVo.setSort(String.valueOf(tpPo.getSort()));
					tpVo.setSampName(tpPo.getSampName());
					tpVo.setPointName(tpPo.getPointName());
					tpVo.setPointCode(tpPo.getPointCode());
					tpVo.setItemNames(tpPo.getItemNames());
					tpVo.setImId(tpPo.getImId());
					tpVo.setSampTypeId(tpPo.getSampTypeId());
					tpVo.setSampTypeName(tpPo.getSampTypeName());
					tpVo.setRoom(tpPo.getRoom());
					tpVo.setItemIds(tpPo.getItemIds());
					tpList.add(tpVo);

				}
				vo.setTpList(tpList);
			}
			// 分包商
			if (po.getFbList() != null) {
				List<AppTaskFb> fbList = new ArrayList<>();
				String fbIds = "";
				for (TaskFbVo fbPo : po.getFbList()) {
					AppTaskFb fbVo = new AppTaskFb();
					fbVo.setFbVoName(fbPo.getFbVo().getName());
					fbVo.setFbVoId(fbPo.getFbVo().getId());
					fbVo.setFbUser(fbPo.getFbUser());
					fbVo.setFbMobile(fbPo.getFbMobile());
					fbVo.setItemNames(fbPo.getItemNames());
					fbVo.setItemIds(fbPo.getItemIds());
					fbVo.setNum(String.valueOf(fbPo.getNum()));
					/* fbIds += fbPo.getFbVo().getId()+","; */
					fbList.add(fbVo);
				}
				vo.setFbList(fbList);
				/* vo.setFbIds(fbIds.substring(0, fbIds.length()-1)); */
			}
			return vo;
		} else {
			return null;
		}

	}

	@Override
	public List<AppTaskImSelect> imSelectList(String sampTypeId, String sampType, String source, String imId)
			throws GlobalException {
		String hql = "SELECT item.id, item.`name`, GROUP_CONCAT(method. NAME) AS standNames, GROUP_CONCAT(method.id) AS standIds FROM v_init_item item LEFT JOIN v_init_method method ON item.stand_ids LIKE concat('%',method.id,'%') WHERE   item.is_del = 0";
		if (!StrUtils.isBlankOrNull(sampTypeId)) {
			String[] ids = sampTypeId.split(",");
			Set<String> idSet = new HashSet<String>();
			for (String idStr : ids) {
				if (!StrUtils.isBlankOrNull(idStr)) {
					idSet.addAll(sampTypeDao.findAllPids(idStr));
					idSet.addAll(sampTypeDao.findAllSubids(idStr));
				}
			}
			hql += " and item.samp_type_ids in('" + String.join(",", idSet).replace(",", "','") + "')";
		}
		if (!StrUtils.isBlankOrNull(sampType)) {
			String sampIds = sampTypeDao.find4Type(sampType);
			hql += " and item.samp_type_ids in('" + String.join(",", sampIds).replace(",", "','") + "')";
		}

		if (!StrUtils.isBlankOrNull(source)) {
			String isNow = "是";
			if (source.equals("送样")) {
				isNow = "否";
				hql += " and item.is_now ='" + isNow + "'";
			}
		}
		if (!StrUtils.isBlankOrNull(imId)) {
			hql += " and item.id not in('" + imId.replace(",", "','") + "')";
		}

		hql += " and item.child_names is null or item.child_names=''";
		hql += " GROUP BY item.id ORDER BY item.sort asc";
		List list = itemDao.queryBySql(hql.toString());
		List<AppTaskImSelect> outList = new ArrayList<>();
		
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			AppTaskImSelect vo = new AppTaskImSelect();
			vo.setId(StrUtils.null2Str(objects[0]));
			vo.setName(StrUtils.null2Str(objects[1]));
			vo.setStandIds(StrUtils.null2Str(objects[3]));
			vo.setStandNames(StrUtils.null2Str(objects[2]));
			outList.add(vo);
		}
		return outList;
		
	 
	}

	@Override
	public List<CustVo> list4Sim(ClientVo v) throws GlobalException {
		String hql = "FROM " + clientDao.getEntityName(Client.class) + " WHERE isDel='" + Po.N + "' ";
		if (StrUtils.isNotBlankOrNull(v.getName())) {
			hql += "AND name like '%" + v.getName().trim() + "%' or person like '%" + v.getName().trim() + "%'  ";
		}
		hql += "order by sort asc";
		List<Client> custList = clientDao.list(hql);
		List<CustVo> custVoList = new ArrayList<CustVo>();
		if (null != custList) {
			for (Client po : custList) {
				CustVo vo = new CustVo();
				vo.setId(po.getId());
				vo.setName(po.getName());
				vo.setAddress(po.getAddress());
				vo.setEmail(po.getEmail());
				vo.setFax(po.getFax());
				vo.setPerson(po.getPerson());
				vo.setPhone(po.getPhone());
				vo.setZip(po.getZip());
				vo.setCusType(po.getIndustry());
				vo.setAttribute(po.getAttribute());
				vo.setProduct(po.getProduct());
				custVoList.add(vo);
			}
		}
		return custVoList;
	}

	@Override
	public List<CustVo> list4Full(CustomerVo v) throws GlobalException {
		String sql = "select cust.id,cust.name,cust.code,cust.address,cust.cust_code,cust.fax,cust.zip,"
				+ "user.name as person,user.phone,cust.email,cust.telephone,cust.cust_address,cust.cust_tel,cust.cust_bank,cust.cust_account,cust.cus_type "
				+ " FROM " + tablePrefix + customerDao.getEntityName(Customer.class) + " cust LEFT JOIN " + tablePrefix
				+ customerDao.getEntityName(Contacts.class) + " user on cust.id=user.customer_id AND user.is_del='"
				+ Po.N + "' WHERE cust.is_del='" + Po.N + "' ";
		if (StrUtils.isNotBlankOrNull(v.getName())) {
			sql += "AND  user.name like '%" + v.getName().trim() + "%' or   cust.name like '%" + v.getName().trim()
					+ "%'";
		}
		if (StrUtils.isNotBlankOrNull(v.getCode())) {
			sql += "AND cust.code like '%" + v.getCode() + "%' ";
		}
		sql += "order by cust.sort asc";
		@SuppressWarnings("unchecked")
		List<Object[]> custList = customerDao.queryBySql(sql);
		List<CustVo> custVoList = new ArrayList<CustVo>();
		if (null != custList) {
			for (Object[] obj : custList) {
				CustVo vo = new CustVo();
				vo.setId(String.valueOf(obj[0]));
				vo.setName(String.valueOf(obj[1]));
				String code = String.valueOf(obj[2]);
				vo.setCode((code == null || code.equals("null")) ? "" : code);
				String address = String.valueOf(obj[3]);
				vo.setAddress((address == null || address.equals("null")) ? "" : address);
				String custCode = String.valueOf(obj[4]);
				vo.setCustCode((custCode == null || custCode.equals("null")) ? "" : custCode);
				String fax = String.valueOf(obj[5]);
				vo.setFax((fax == null || fax.equals("null")) ? "" : fax);
				String zip = String.valueOf(obj[6]);
				vo.setZip((zip == null || zip.equals("null")) ? "" : zip);
				String person = String.valueOf(obj[7]);
				vo.setPerson((person == null || person.equals("null")) ? "" : person);
				String phone = String.valueOf(obj[8]);
				vo.setPhone((phone == null || phone.equals("null")) ? "" : phone);
				String email = String.valueOf(obj[9]);
				vo.setEmail((email == null || email.equals("null")) ? "" : email);
				String telephone = String.valueOf(obj[10]);
				vo.setTelephone((telephone == null || telephone.equals("null")) ? "" : telephone);
				String custAddress = String.valueOf(obj[11]);
				vo.setCustAddress((custAddress == null || custAddress.equals("null")) ? "" : custAddress);
				String custTel = String.valueOf(obj[12]);
				vo.setCustTel((custTel == null || custTel.equals("null")) ? "" : custTel);
				String custBank = String.valueOf(obj[13]);
				vo.setCustBank((custBank == null || custBank.equals("null")) ? "" : custBank);
				String custAccount = String.valueOf(obj[14]);
				vo.setCustAccount((custAccount == null || custAccount.equals("null")) ? "" : custAccount);
				String custType = String.valueOf(obj[15]);
				vo.setCusType((custType == null || custType.equals("null")) ? "" : custType);
				custVoList.add(vo);
			}
		}
		return custVoList;
	}

	@Override
	public List<AppCustPointSelect> CustPointSelectlist(String custName, String sampTypeId) throws GlobalException {
		String hql = "SELECT point.id,im_id,samp_type_id,item_id,room,point.name,`code`,samp_name,item_name,samp_type_name  FROM v_cus_client_point  point  JOIN v_cus_client client on point.client_id = client.id WHERE client.is_del = '0'";
		if (StrUtils.isNotBlankOrNull(custName)) {
			hql += " AND client.name like '" + custName + "'";
		}
		if (StrUtils.isNotBlankOrNull(sampTypeId)) {
			String[] idArr = sampTypeId.split(",");
			String subHql = "";
			for (String idStr : idArr) {
				if (idStr.trim().length() > 0) {
					subHql += " samp_type_id like '%" + idStr.trim() + "%' OR";
				}
			}
			if (subHql.length() > 2) {
				subHql = subHql.substring(0, subHql.length() - 2);
			}
			hql += " AND (" + subHql + ")";
		}
		hql += " order by point.sort asc";
		List list = clientPointDao.queryBySql(hql.toString());
		List<AppCustPointSelect> outList = new ArrayList<>();
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			AppCustPointSelect vo = new AppCustPointSelect();
			vo.setId(StrUtils.null2Str(objects[0]));
			vo.setImId(StrUtils.null2Str(objects[1]));
			vo.setSampTypeId(StrUtils.null2Str(objects[2]));
			vo.setItemId(StrUtils.null2Str(objects[3]));
			vo.setRoom(StrUtils.null2Str(objects[4]));
			vo.setName(StrUtils.null2Str(objects[5]));
			vo.setCode(StrUtils.null2Str(objects[6]));
			vo.setSampName(StrUtils.null2Str(objects[7]));
			vo.setItemName(StrUtils.null2Str(objects[8]));
			vo.setSampTypeName(StrUtils.null2Str(objects[9]));
			outList.add(vo);
		}
		return outList;

	}

	@Override
	public List<AppTaskFb> fbList(String fbIds) throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"FROM " + supplierDao.getEntityName(Supplier.class) + " WHERE isDel=" + Po.N);
		if (StrUtils.isNotBlankOrNull(fbIds)) {
			String[] idArr = fbIds.replaceAll(" ", "").split(",");
			String subHql = "";
			for (String idStr : idArr) {
				if (idStr.trim().length() > 0) {
					subHql += " id !=  '" + idStr.trim() + "' and";
				}
			}
			if (subHql.length() > 2) {
				subHql = subHql.substring(0, subHql.length() - 3);
			}
			hql.append(" AND (" + subHql + ")");
		}
		hql.append(" ORDER BY lastUpdTime desc");
		List<AppTaskFb> outList = new ArrayList<>();
		List<Supplier> list = supplierDao.list(hql.toString());
		for (Supplier po : list) {
			AppTaskFb vo = new AppTaskFb();
			vo.setFbVoId(po.getId());
			vo.setFbVoName(po.getName());
			vo.setFbUser(po.getLinkman());
			vo.setFbMobile(po.getLinkmanTel());
			vo.setProductType(po.getProductType());
			outList.add(vo);
		}
		return outList;
	}

	@Override
	public Boolean taskAddOrUpDate(AppTaskEdit appTaskEdit, ObjVo objVo, String isCommit) throws GlobalException {
		// 判断是否有id
		if (StrUtils.isNotBlankOrNull(appTaskEdit.getId())) {
			// 有更新
			try {
				taskUpdate(appTaskEdit, isCommit, objVo);
				
				
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			// 无保存
			try {
				taskAdd(appTaskEdit, objVo, isCommit);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

	}

	public void taskUpdate(AppTaskEdit v, String isCommit, ObjVo objVo) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		if (StrUtils.isBlankOrNull(p.getProjectId())) {
			updateD(p, v, isCommit, objVo);
		} else {
			updateP(p, v, isCommit, objVo);
		}

	}

	public void taskAdd(AppTaskEdit v, ObjVo objVo, String isCommit) throws GlobalException {
		Task p = new Task();
		Cust cust = new Cust();
		cust.setCustName(v.getCustName());
		cust.setCustAddress(v.getCustAddress());
		cust.setCustUser(v.getCustUser());
		cust.setCustTel(v.getCustTel());
		cust.setIndustry(v.getIndustry());
		cust.setAttribute(v.getAttribute());
		cust.setProduct(v.getProduct());
		cust.setWtName(v.getWtName());
		Customer customer = new Customer();
		customer.setId(v.getCustVoCustomerVoId());
		cust.setCustomer(customer);
		cust.setWtAddress(v.getWtAddress());
		cust.setWtUser(v.getWtUser());
		cust.setWtTel(v.getWtTel());
		cust.setWtEmail(v.getWtEmail());
		cust.setWtZip(v.getWtZip());
		p.setId(v.getId());
		p.setSampType(v.getSampType());
		p.setIsBack(Constants.N);
		Client client = new Client();
		client.setId(v.getCustVoClientVoId());
		cust.setClient(client);
		p.setCust(cust);
		p.setSampTypeName(v.getSampTypeName());
		p.setSampTypeId(v.getSampTypeId());
		p.setSource(v.getSource());
		p.setSampName(v.getSampName());
		p.setDealRequest(v.getDealRequest());
		p.setCyName(v.getCyName());
		p.setCyDate(v.getCyDate());
		if(!StrUtils.isBlankOrNull(v.getReportNum())) {
			p.setReportNum(Integer.parseInt(v.getReportNum()));
		}
		
		p.setReportWay(v.getReportWay());
		p.setJj(v.getJj());
		p.setFinishDate(v.getFinishDate());
		p.setPj(v.getPj());
		p.setFb(v.getFb());
		p.setStandNames(v.getStandNames());
		p.setStandIds(v.getStandIds());
		p.setUserName(objVo.getName());
		p.setUserId(objVo.getUserId());
		p.setDate(v.getDate());
		p.setRemark(v.getRemark());
		p.setSaveRequest(v.getSaveRequest());
		p.setTaskType(v.getTaskType());
		p.setOrgId(objVo.getOrgId());
		p.setOrgName(objVo.getOrgName());
		p.setNo(checkCode(v.getTaskType()));
		p = setValue(v, p);
		taskDao.add(p);
		v.setId(p.getId());
		savePoint(v, p, isCommit,objVo);
		uptFb(v, p);
		v.setId(p.getId());
		Progress progress = progressDao.addApp(p.getId(), EunmTask.TASK_DJ.getStatus(), objVo);
		p.setProgress(progress);
		p.setStatus(progress.getStatus());
		commit(v, p, isCommit, objVo);
		taskDao.update(p);

	}

	// 登记 环节 新增的任务进行修改保存
	public void updateD(Task p, AppTaskEdit v, String isCommit, ObjVo objVo) throws GlobalException {	
		p = taskDao.findById(v.getId());
		Cust cust = p.getCust();
		cust.setCustName(v.getCustName());
		cust.setCustAddress(v.getCustAddress());
		cust.setCustUser(v.getCustUser());
		cust.setCustTel(v.getCustTel());
		cust.setIndustry(v.getIndustry());
		cust.setAttribute(v.getAttribute());
		cust.setProduct(v.getProduct());
		cust.setWtName(v.getWtName());
		cust.getCustomer().setId(v.getCustVoCustomerVoId());
		cust.setWtAddress(v.getWtAddress());
		cust.setWtUser(v.getWtUser());
		cust.setWtTel(v.getWtTel());
		cust.setWtEmail(v.getWtEmail());
		cust.setWtZip(v.getWtZip());
		p.setId(v.getId());
		p.setSampType(v.getSampType());
		p.setIsBack(Constants.N);
		p.getCust().getClient().setId(v.getCustVoClientVoId());
		p.setCust(cust);
		p.setSampTypeName(v.getSampTypeName());
		p.setSampTypeId(v.getSampTypeId());
		p.setSource(v.getSource());
		p.setSampName(v.getSampName());
		p.setDealRequest(v.getDealRequest());
		p.setCyName(v.getCyName());
		p.setCyDate(v.getCyDate());
		p.setReportNum(Integer.parseInt(v.getReportNum()));
		p.setReportWay(v.getReportWay());
		p.setJj(v.getJj());
		p.setFinishDate(v.getFinishDate());
		p.setPj(v.getPj());
		p.setFb(v.getFb());
		p.setStandNames(v.getStandNames());
		p.setStandIds(v.getStandIds());
		p.setUserName(objVo.getName());
		p.setUserId(objVo.getUserId());
		p.setDate(v.getDate());
		p.setRemark(v.getRemark());
		p.setSaveRequest(v.getSaveRequest());
		p.setTaskType(v.getTaskType());
		p.setOrgId(objVo.getOrgId());
		p.setOrgName(objVo.getOrgName());
		if (StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(checkCode(p.getTaskType()));
		}
		p = setValue(v, p);
		savePoint(v, p, isCommit,objVo);
		uptFb(v, p);
		commit(v, p, isCommit, objVo);
		taskDao.update(p);
	}

	// 项目环节提交的任务 进行修改保存
	public void updateP(Task p, AppTaskEdit v, String isCommit, ObjVo objVo) throws GlobalException {
		if (StrUtils.isBlankOrNull(p.getDate())) {
			p.setDate(v.getDate());
			p.setUserId(objVo.getUserId());
			p.setUserName(objVo.getName());
		}
		if (p.getSource().equals(Constants.SAMP_ZS)) {
			p.setDealRequest(v.getDealRequest());
			p.setSaveRequest(v.getSaveRequest());
			p.setCyName(v.getCyName());
			p.setCyDate(v.getCyDate());
		}
		p.setSampName(v.getSampName());
		p.setReportNum(Integer.parseInt(v.getReportNum()));
		p.setReportWay(v.getReportWay());
		p.setJj(v.getJj());
		p.setFinishDate(v.getFinishDate());
		p.setXcSt(Task.ST_N);
		p.setRemark(v.getRemark());
		savePoint(v, p, isCommit,objVo);
		uptFb(v, p);
		commit(v, p, isCommit, objVo);
		taskDao.update(p);
	}

	public String checkCode(String taskType) throws GlobalException {
		String prefix = "";
		prefix += DateUtils.getYear() + DateUtils.getMonth();
		String hql = "SELECT max(no) FROM " + taskDao.getEntityName(Task.class) + " WHERE isDel=" + Po.N
				+ " AND no like '" + prefix + "%'";
		String ls = (String) taskDao.query(hql).getSingleResult();
		String no = prefix;
		if (ls == null) {
			no += "001";
		} else {
			ls = ls.replace(prefix, "");
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

	public Task setValue(AppTaskEdit v, Task p) throws GlobalException {
		uptCustInfo(v, p);
		if (p.getSampType().equals(EnumBus.SAMP_TYPE_GW) || p.getSampType().equals(EnumBus.SAMP_TYPE_ZW)) {
			p.setSource(Constants.SAMP_XC);
			p.setPj(Constants.S);
			p.setSampTypeName("/");
		} else {
			p.setPj(v.getPj());
		}
		if (p.getSource().equals(Constants.SAMP_ZS)) {
			if (StrUtils.isBlankOrNull(v.getDealRequest())) {
				p.setDealRequest(Constants.SAMP_XH);
			} else {
				p.setDealRequest(v.getDealRequest());
			}
			p.setSaveRequest(v.getSaveRequest());
			p.setCyName(v.getCyName());
			p.setCyDate(v.getCyDate());
		}
		p.setXcSt(Task.ST_N);
		return p;
	}

	// 检查 及更新 受检单位 委托单位信息
	public void uptCustInfo(AppTaskEdit v, Task p) throws GlobalException {

		Cust cust = p.getCust();
		if (cust == null) {
			cust = new Cust();
		}
		
		cust.setCustName(v.getCustName());
		cust.setCustAddress(v.getCustAddress());
		cust.setCustUser(v.getCustUser());
		cust.setCustTel(v.getCustTel());
		cust.setIndustry(v.getIndustry());
		cust.setAttribute(v.getAttribute());
		cust.setProduct(v.getProduct());
		cust.setWtName(v.getWtName());
		Customer customer1 = new Customer();
		customer1.setId(v.getCustVoCustomerVoId());
		cust.setCustomer(customer1);
		cust.setWtAddress(v.getWtAddress());
		cust.setWtUser(v.getWtUser());
		cust.setWtTel(v.getWtTel());
		cust.setWtEmail(v.getWtEmail());
		cust.setWtZip(v.getWtZip());
		// 受检单位
		Client client = null;
		if (!StrUtils.isBlankOrNull(v.getCustName())) {
			client = clientDao.findByName(v.getCustName().replaceAll(" ", ""));
			
		}
		// 受检单位自维护
		if (null == client && !StrUtils.isBlankOrNull(v.getCustName())) {
			client = new Client();
			client.setName(v.getCustName());
			client.setAddress(v.getCustAddress());
			client.setPerson(v.getCustUser());
			client.setPhone(v.getCustTel());

			client.setIndustry(v.getIndustry());
			client.setAttribute(v.getAttribute());
			client.setProduct(v.getProduct());
			clientDao.add(client);
		} else if (null != client) {
			// 同步受检单位信息

			if (!StrUtils.isBlankOrNull(v.getCustAddress())) {
				client.setAddress(v.getCustAddress());
			}
			if (!StrUtils.isBlankOrNull(v.getCustUser())) {
				client.setPerson(v.getCustUser());
			}
			if (!StrUtils.isBlankOrNull(v.getCustTel())) {
				client.setPhone(v.getCustTel());
			}
			clientDao.update(client);
		}
		cust.setClient(client);
		// 委托单位
		Customer customer = null;
		if (!StrUtils.isBlankOrNull(v.getWtName())) {
		
			customer = customerDao.findByName(v.getWtName());
		}
		// 委托单位自维护
		if (null == customer && !StrUtils.isBlankOrNull(v.getWtName())) {
	
			customer = new Customer();
			customer.setName(v.getWtName());
			customer.setAddress(v.getWtAddress());
			customer.setPerson(v.getWtUser());
			customer.setPhone(v.getWtTel());
			customer.setTelephone(v.getWtTel());
			customer.setEmail(v.getWtEmail());

			customer.setZip(v.getWtZip());

			customer.setBuildDate(p.getDate());
			customer.setCode(customerDao.createCode());
			customerDao.add(customer);
		} else if (null == customer && !StrUtils.isBlankOrNull(v.getCustName())) {
			// 当委托单位为null时，委托单位=受检单位
			cust.setWtName(v.getCustName());
			cust.setWtAddress(v.getCustAddress());
			cust.setWtUser(v.getCustUser());
			cust.setWtTel(v.getCustTel());

			cust.setWtIndustry(v.getIndustry());
			cust.setWtAttribute(v.getAttribute());
			customer = customerDao.findByName(cust.getWtName());
			if (customer == null) {
				customer = new Customer();
				customer.setName(cust.getWtName());
				customer.setAddress(cust.getWtAddress());
				customer.setPerson(cust.getWtUser());
				customer.setPhone(cust.getWtTel());
				customer.setTelephone(cust.getWtTel());
				customer.setEmail(cust.getWtEmail());
				customer.setFax(cust.getWtFax());
				customer.setZip(cust.getWtZip());
				customer.setIndustry(cust.getWtIndustry());
				customer.setCusType(cust.getWtAttribute());
				customer.setBuildDate(p.getDate());
				customer.setCode(customerDao.createCode());
				customerDao.add(customer);
			} else {
				// 同步受检单位信息
				if (!StrUtils.isBlankOrNull(cust.getWtZip())) {
					customer.setZip(cust.getWtZip());
				}
				if (!StrUtils.isBlankOrNull(cust.getWtFax())) {
					customer.setFax(cust.getWtFax());
				}
				if (!StrUtils.isBlankOrNull(cust.getWtAddress())) {
					customer.setAddress(cust.getWtAddress());
				}
				if (!StrUtils.isBlankOrNull(cust.getWtUser())) {
					customer.setPerson(cust.getWtUser());
				}
				if (!StrUtils.isBlankOrNull(cust.getWtTel())) {
					if (cust.getWtTel().length() == 11 && !cust.getWtTel().contains("-")) {
						customer.setPhone(cust.getWtTel());
					} else {
						customer.setTelephone(cust.getWtTel());
					}
				}
				customerDao.update(customer);
			}
		} else if (null != customer) {
			// 同步受检单位信息
			if (!StrUtils.isBlankOrNull(v.getWtZip())) {
				customer.setZip(v.getWtZip());
			}

			if (!StrUtils.isBlankOrNull(v.getWtAddress())) {
				customer.setAddress(v.getWtAddress());
			}
			if (!StrUtils.isBlankOrNull(v.getWtUser())) {
				customer.setPerson(v.getWtUser());
			}
			if (!StrUtils.isBlankOrNull(v.getWtTel())) {
				if (v.getWtTel().length() == 11 && !v.getWtTel().contains("-")) {
					customer.setPhone(v.getWtTel());
				} else {
					customer.setTelephone(v.getWtTel());
				}
			}
			customerDao.update(customer);
		}
		cust.setCustomer(customer);
		// 委托单位联系人自维护
		Contacts ct = contactsDao.findBycusId(customer.getId(), customer.getPerson());
		if (null == ct) {
			ct = new Contacts();
			ct.setName(customer.getPerson());
			ct.setPhone(customer.getPhone());
			ct.setCustomer(customer);
			contactsDao.add(ct);
		}
		// 建立受检单位和委托单位关系
		CusClient cc = cusClientDao.findByClientIdAndCustId(client.getId(), customer.getId());
		if (cc == null) {
			cc = new CusClient();
			cc.setClient(client);
			cc.setCustomer(customer);
			cusClientDao.add(cc);
		}
		// 插入检测单位信息
		if (StrUtils.isBlankOrNull(cust.getUnit())) {
			Unit unit = unitDao.findById("0");
			if (null != unit) {
				cust.setUnit(unit.getName());
				cust.setAddress(unit.getAddress());
				cust.setUrl(unit.getWebsite());
				cust.setUser(unit.getLinkMan());
				cust.setTel(unit.getTelephone());
				cust.setZip(unit.getPost());
			}
		}
		custDao.saveOrUpdate(cust);
		p.setCust(cust);
	}

	// 项目过来的任务 保存点位
	public void savePoint(AppTaskEdit v, Task p, String isCommit,ObjVo objVo) throws GlobalException {
		List<AppTaskTp> tpList = v.getTpList();
		Set<String> itemSet = new HashSet<String>();
		Set<String> pointSet = new HashSet<String>();
		if (null != tpList && tpList.size() > 0) {
			for (AppTaskTp tpVo : tpList) {
				if (null != tpVo && !StrUtils.isBlankOrNull(tpVo.getItemNames())) {
					TaskPoint tp = null;
					if (!StrUtils.isBlankOrNull(tpVo.getId())) {
						tp = taskPointDao.findById(tpVo.getId());
					}
					if (tp == null) {
						tp = new TaskPoint();
					}
					tp.setTask(p);
					if (!StrUtils.isBlankOrNull(tpVo.getSampTypeId())) {
						SampType st = sampTypeDao.findById(tpVo.getSampTypeId());
						tp.setSampTypeId(st.getId());
						tp.setSampTypeName(st.getName());
						tp.setType(st.getType());
					} else if (!StrUtils.isBlankOrNull(tpVo.getItemIds())) {
						List<Item> itemList = itemDao.listByIds(tpVo.getItemIds());
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
					}
					tp.setSampType(p.getSampType());
					tp.setRoom(tpVo.getRoom());
					tp.setSampName(tpVo.getSampName());
					if (StrUtils.isBlankOrNull(tpVo.getSampName())) {
						tp.setSampName(p.getSampName());
					}
					tp.setItemIds(tpVo.getItemIds());
					tp.setItemNames(tpVo.getItemNames());
					tp.setPointName(tpVo.getPointName());
					tp.setPointCode(tpVo.getPointCode());
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
					}
					if(!StrUtils.isBlankOrNull(tpVo.getSort())) {
						tp.setSort(Integer.parseInt(tpVo.getSort()));
					}
					taskPointDao.saveOrUpdate(tp);
					imDao.uptIm4App(tp.getId(), tpVo.getImId(),objVo);
					pointSet.add(tpVo.getPointName());
					itemSet.addAll(Arrays.asList(tpVo.getItemNames().split(",")));
					// 点位自维护
					if (!StrUtils.isBlankOrNull(tp.getPointName()) && null != isCommit
							&& isCommit.equals(EunmTask.PASS_Y)) {
						synchPoint(p.getCust().getClient(), tp, tpVo.getImId());
					}
					List<Item> itList = itemDao.listByIds(tpVo.getItemIds(), Constants.S);
					if (itList != null && itList.size() > 0) {
						p.setXcSt(Task.ST_0);
					}
				}
			}
			p.setPointNames(String.join(",", pointSet));
			p.setItemNames(String.join(",", itemSet));
		}
	}

	// 同步受检单位的检测点位
	public void synchPoint(Client client, TaskPoint tp, String imId) throws GlobalException {
		ClientPoint cp = clientPointDao.findBycusId(client.getId(), tp.getSampTypeId(), tp.getSampType(),
				tp.getPointName().trim());
		if (!tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			cp = clientPointDao.findBycusId(client.getId(), tp.getPointName().trim());
		}
		if (null == cp) {
			cp = new ClientPoint();
			cp.setClient(client);
			cp.setRoom(tp.getRoom());
			cp.setName(tp.getPointName());
			cp.setCode(tp.getPointCode());
			cp.setSampType(tp.getSampType());
			if (tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				cp.setSampTypeId(tp.getSampTypeId());
				cp.setSampTypeName(tp.getSampTypeName());
			}
			cp.setSampName(tp.getSampName());
			cp.setType(tp.getType());
			cp.setItemId(tp.getItemIds());
			cp.setItemName(tp.getItemNames());
			cp.setImId(imId);
			clientPointDao.add(cp);
		} else {
			cp.setCode(tp.getPointCode());
			cp.setItemId(tp.getItemIds());
			cp.setItemName(tp.getItemNames());
			cp.setImId(imId);
			clientPointDao.update(cp);
		}
	}

	// 更新分包信息
	public void uptFb(AppTaskEdit vo, Task po) {
		po.setFb(vo.getFb());
		taskFbDao.deleteByTaskId(po.getId());
		List<String> fbItemId = new ArrayList<String>();
		List<String> fbItemName = new ArrayList<String>();
		List<String> fbUnit = new ArrayList<String>();
		if (null != vo.getFbList()) {
			for (AppTaskFb fbVo : vo.getFbList()) {
				if (fbVo == null || StrUtils.isBlankOrNull(fbVo.getFbVoId())) {
					continue;
				}
				TaskFb fb = new TaskFb();	
				Supplier fbTemp = new Supplier();
				fbTemp.setName(fbVo.getFbVoName());
				fbTemp.setId(fbVo.getFbVoId());
				fb.setFb(fbTemp);
				fb.setFbUser(fbVo.getFbUser());
				fb.setFbMobile(fbVo.getFbMobile());
				fb.setItemNames(fbVo.getItemNames());
				fb.setItemIds(fbVo.getItemIds());
				if(!StrUtils.isBlankOrNull(fbVo.getNum()) ) {
					fb.setNum(Integer.parseInt(fbVo.getNum()));
				}
				
				fb.setTask(po);
				fb.setFb(supplierDao.findById(fbVo.getFbVoId()));
				taskFbDao.add(fb);
				String ids[] = fb.getItemIds().split(",");
				String names[] = fb.getItemNames().split(",");
				int i = 0;
				for (String iid : ids) {
					if (!StrUtils.isBlankOrNull(iid) && !fbItemId.contains(iid)) {
						fbItemId.add(iid);
						fbItemName.add(names[i]);
					}
					i++;
				}
				if (!fbUnit.contains(fb.getFb().getName())) {
					fbUnit.add(fb.getFb().getName());
				}
			}
		}
		po.setFbItemId(String.join(",", fbItemId));
		po.setFbItemName(String.join(",", fbItemName));
		po.setFbUnit(String.join(",", fbUnit));
	}

	/**
	 * 普通任务 提交
	 * 
	 * @param v
	 * @param p
	 * @throws GlobalException
	 */
	public void commit(AppTaskEdit v, Task p, String isCommit, ObjVo objVo) throws GlobalException {
		if (null != isCommit && isCommit.equals(EunmTask.PASS_Y)) {
			Progress pg = p.getProgress();
			progressLogDao.addApp(p.getId(), p.getId(), EunmTask.TASK_DJ.getStatus(), isCommit, v.getRemark(), objVo);
			if (p.getSource().equals(Constants.SAMP_XC)) {// 现场采样到采样安排
				pg = progressDao.update(pg.getId(), EunmTask.TASK_AP.getStatus(), p.getApId(), p.getApName());
				// initRoom(p.getId(),p.getSampType());
			} else {// 自送样 直接到样品交接
				pg = progressDao.update(pg.getId(), EunmTask.TASK_JJ.getStatus(), null, null);
				p.setReportNo(createReportNo(p.getSampType()));
				initZsSamp(p);// 生成样品信息
			}
			p.setStatus(pg.getStatus());
			p.setIsBack(Constants.N);
		}
	}

	/**
	 * 生成合同编号
	 */
	public String createReportNo(String sampType) {
		String flag = DateUtils.getYear();
		String hql = "SELECT max(reportNo) FROM " + taskDao.getEntityName(Task.class) + " WHERE isDel=" + Po.N
				+ " AND sampType='" + sampType + "' AND reportNo like '" + flag + "%'";
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
	 * 自送 初始化样品信息
	 * 
	 * @param p
	 * @throws GlobalException
	 */
	@SuppressWarnings("unchecked")
	public void initZsSamp(Task p) throws GlobalException {
		List<TaskPoint> tpList = taskPointDao.listByTaskId(p.getId());
		int n = 1;
		for (TaskPoint tp : tpList) {
			String sql = "select code,group_concat(id),group_concat(name) from " + tablePrefix
					+ itemDao.getEntityName(Item.class) + " WHERE is_del='" + Po.N + "' AND id in('"
					+ tp.getItemIds().replace(",", "','") + "') group by code order by sort ASC";
			List<Object[]> object = itemDao.queryBySql(sql);
			int ptSort = taskPointDao.getSort4Code(p.getId(), tp.getId(), tp.getSampTypeId());
			if (null != object && object.size() > 0) {
				for (Object[] obj : object) {
					Sampling samp = new Sampling();
					samp.setTask(tp.getTask());
					samp.setCust(tp.getTask().getCust());
					samp.setPoint(tp);
					samp.setPointName(tp.getPointName());
					samp.setPointCode(tp.getPointCode());
					samp.setSampTypeId(tp.getSampTypeId());
					samp.setSampTypeName(tp.getSampTypeName());
					samp.setSampType(tp.getSampType());
					samp.setSampName(tp.getSampName());
					if (StrUtils.isBlankOrNull(tp.getSampName())) {
						samp.setSampName(p.getSampName());
					}
					samp.setItemIds(obj[1].toString());
					samp.setItemNames(obj[2].toString());
					samp.setLy(Constants.F);
					samp.setSort(n);
					samp.setSaveRequest(p.getSaveRequest());
					String cyDate = p.getCyDate();
					samp.setCyDate(cyDate.substring(0, 10));
					if (cyDate.length() > 16) {
						samp.setCyTime(cyDate.substring(11, 16));
						samp.setCyEndTime(cyDate.substring(11, 16));
					}
					samp.setSaveHour(itemDao.getMaxHours(tp.getItemIds()));
					samp.setType(Sampling.SAMP_TYPE_PT);
					samp.setZkType(Sampling.SAMP_TYPE_PT);
					if (null != obj[0] && !String.valueOf(obj[0]).equals("null")) {
						samp.setCode(String.valueOf(obj[0]));
					}
					samp.setP(String.valueOf(1));
					samp.setSampCode(samplingDao.createSampCodeHj(p.getReportNo(), samp.getSampTypeId(),
							samp.getItemIds(), ptSort, samp.getP()));
					samplingDao.add(samp);
					n++;
				}
				tp.setSampNum(object.size());
			}
			taskPointDao.update(tp);
		}
	}

}
