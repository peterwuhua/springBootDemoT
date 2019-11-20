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
import cn.core.framework.common.page.OrderCondition;
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
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.dao.IUnitDao;
import cn.demi.base.system.po.Org;
import cn.demi.base.system.po.Unit;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.ICustDao;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.dao.ISchemeDao;
import cn.demi.bus.pjt.dao.ISchemePointDao;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.pjt.po.Im;
import cn.demi.bus.pjt.po.Scheme;
import cn.demi.bus.pjt.po.SchemePoint;
import cn.demi.bus.pjt.vo.CustVo;
import cn.demi.bus.report.dao.IReportDao;
import cn.demi.bus.report.po.Report;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.dao.ITaskFbDao;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.dao.ITaskRoomDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskFb;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.po.TaskRoom;
import cn.demi.bus.task.service.ITaskService;
import cn.demi.bus.task.vo.TaskFbVo;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.po.TaskItem;
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
import cn.demi.init.sp.vo.PcUnit;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.po.Item;
import cn.demi.res.dao.ISupplierDao;
import cn.demi.res.vo.ApparaVo;

@Service("bus.taskService")
public class TaskServiceImpl extends BaseServiceImpl<TaskVo, Task> implements ITaskService {

	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private ICustDao custDao;
	@Autowired
	private ITaskPointDao taskPointDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IContactsDao contactsDao;
	@Autowired
	private IClientDao clientDao;
	@Autowired
	private IClientPointDao clientPointDao;
	@Autowired
	private ICusClientDao cusClientDao;
	@Autowired
	private ISampTypeDao sampTypeDao;
	@Autowired
	private ITaskRoomDao taskRoomDao;
	@Autowired
	private ITaskItemDao taskItemDao;
	@Autowired
	private IReportDao reportDao;
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private IUnitDao unitDao;
	@Autowired
	private ITaskFbDao taskFbDao;
	@Autowired
	private ISupplierDao supplierDao;
	@Autowired
	private IImDao imDao;
	@Autowired
	private ISchemeDao schemeDao;
	@Autowired
	private ISchemePointDao schemePointDao;

	@Autowired
	private IOrgDao orgDao;

	/**
	 * 获取任务详细信息 点位信息
	 */
	@Override
	public TaskVo findById(String id) throws GlobalException {
		Task task = taskDao.findById(id);
		TaskVo vo = po2Vo(task);
		List<TaskPoint> tpList = taskPointDao.listByTaskId(task.getId());
		if (tpList != null && tpList.size() > 0) {
			List<TaskPointVo> tpVoList = new ArrayList<TaskPointVo>();
			int sort = 1;
			for (TaskPoint tp : tpList) {
				TaskPointVo tpVo = new TaskPointVo();
				tpVo = tpVo.toVo(tp);
				tpVo.setImId(imDao.findByBusId(tp.getId()));
				tpVo.setSort(sort);
				tpVoList.add(tpVo);
				sort++;
			}
			vo.setTpList(tpVoList);
		}
		// 分包信息
		List<TaskFb> fbList = taskFbDao.listByTaskId(task.getId());
		if (null != fbList) {
			List<TaskFbVo> fbVoList = new ArrayList<>();
			for (TaskFb fb : fbList) {
				TaskFbVo fbVo = new TaskFbVo();
				fbVo = fbVo.toVo(fb);
				fbVoList.add(fbVo);
			}
			vo.setFbList(fbVoList);
		}
		if (StrUtils.isBlankOrNull(vo.getDate())) {
			vo.setDate(DateUtils.getCurrDateTimeStr());
			vo.setUserId(getCurrent().getAccountId());
			vo.setUserName(getCurrent().getUserName());
		}
		return vo;
	}

	@Override
	public void delete(String... ids) throws GlobalException {
		for (String id : ids) {
			if (!StrUtils.isBlankOrNull(id)) {
				// 删除方案时 同步删除点位表和项目方法关系表
				List<TaskPoint> spList = taskPointDao.listByTaskId(id);
				if (null != spList) {
					for (TaskPoint tp : spList) {
						imDao.deleteByBusId(tp.getId());
						taskPointDao.delete(tp);
					}
				}
				Task p = taskDao.findById(id);
				Progress pg = p.getProgress();
				taskDao.delete(p);
				progressDao.delete(pg);
			}
		}
	}

	public Task setValue(TaskVo v, Task p) throws GlobalException {
		uptCustInfo(v, p);
		if (p.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			p.setPj(v.getPj());
			p.setSampName(p.getSampTypeName());
		} else {
			p.setSource(Constants.SAMP_XC);
			p.setPj(Constants.S);
			p.setSampTypeName("/");
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

	// 项目过来的任务 保存点位
	public void savePoint(TaskVo v, Task p, String isCommit) throws GlobalException {
		List<TaskPointVo> tpList = v.getTpList();
		Set<String> itemSet = new HashSet<String>();
		Set<String> pointSet = new HashSet<String>();
		if (null != tpList && tpList.size() > 0) {
			for (TaskPointVo tpVo : tpList) {
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
						tp.setSampName(tp.getSampTypeName());
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
						tp.setSampName(p.getSampTypeName());
					}
					tp.setSampType(p.getSampType());
					tp.setRoom(tpVo.getRoom());
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
					tp.setSort(tpVo.getSort());
					taskPointDao.saveOrUpdate(tp);
					imDao.uptIm(tp.getId(), tpVo.getImId());
					pointSet.add(tpVo.getPointName());
					itemSet.addAll(Arrays.asList(tpVo.getItemNames().split(",")));
					// 点位自维护
					if (!StrUtils.isBlankOrNull(tp.getPointName()) && null != isCommit && isCommit.equals(EunmTask.PASS_Y)) {
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
		ClientPoint cp = clientPointDao.findBycusId(client.getId(), tp.getSampTypeId(), tp.getSampType(), tp.getPointName().trim());
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
	public void uptFb(TaskVo vo, Task po) {
		po.setFb(vo.getFb());
		taskFbDao.deleteByTaskId(po.getId());
		List<String> fbItemId = new ArrayList<String>();
		List<String> fbItemName = new ArrayList<String>();
		List<String> fbUnit = new ArrayList<String>();
		if (null != vo.getFbList()) {
			for (TaskFbVo fbVo : vo.getFbList()) {
				if (fbVo == null || fbVo.getFbVo() == null || StrUtils.isBlankOrNull(fbVo.getFbVo().getId())) {
					continue;
				}
				TaskFb fb = new TaskFb();
				fb = fb.toPo(fbVo, fb);
				fb.setTask(po);
				fb.setFb(supplierDao.findById(fbVo.getFbVo().getId()));
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

	@Override
	public void add(TaskVo v) throws GlobalException {
		Task p = vo2Po(v);
		p.setSampType(v.getSampType());
		p.setIsBack(Constants.N);
		p.setOrgId(getCurrent().getOrgId());
		p.setOrgName(getCurrent().getOrgName());
		p.setNo(checkCode(v.getTaskType()));
		p = setValue(v, p);
		taskDao.add(p);
		v.setId(p.getId());
		savePoint(v, p, v.getIsCommit());
		uptFb(v, p);
		v.setId(p.getId());
		Progress progress = progressDao.add(p.getId(), EunmTask.TASK_DJ.getStatus(), p.getOrgId(), p.getOrgName(), p.getUserId(), p.getUserName());
		p.setProgress(progress);
		p.setStatus(progress.getStatus());
		commit(v, p);
		taskDao.update(p);
	}

	@Override
	public void update(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		if (StrUtils.isBlankOrNull(p.getProjectId())) {
			updateD(p, v);
		} else {
			updateP(p, v);
		}
	}

	// 登记 环节 新增的任务进行修改保存
	public void updateD(Task p, TaskVo v) throws GlobalException {
		p.toPo(v, p);
		if (StrUtils.isBlankOrNull(p.getNo())) {
			p.setNo(checkCode(p.getTaskType()));
		}
		p = setValue(v, p);
		savePoint(v, p, v.getIsCommit());
		uptFb(v, p);
		commit(v, p);
		taskDao.update(p);
	}

	// 项目环节提交的任务 进行修改保存
	public void updateP(Task p, TaskVo v) throws GlobalException {
		if (StrUtils.isBlankOrNull(p.getDate())) {
			p.setDate(v.getDate());
			p.setUserId(v.getUserId());
			p.setUserName(v.getUserName());
		}
		if (p.getSource().equals(Constants.SAMP_ZS)) {
			p.setDealRequest(v.getDealRequest());
			p.setSaveRequest(v.getSaveRequest());
			p.setCyName(v.getCyName());
			p.setCyDate(v.getCyDate());
		}
		p.setReportNum(v.getReportNum());
		p.setReportWay(v.getReportWay());
		p.setJj(v.getJj());
		p.setFinishDate(v.getFinishDate());
		p.setXcSt(Task.ST_N);
		p.setRemark(v.getRemark());
		savePoint(v, p, v.getIsCommit());
		uptFb(v, p);
		commit(v, p);
		taskDao.update(p);
	}

	// 检查 及更新 受检单位 委托单位信息
	public void uptCustInfo(TaskVo v, Task p) throws GlobalException {
		CustVo custVo = v.getCustVo();
		Cust cust = p.getCust();
		if (cust == null)
			cust = new Cust();
		cust = cust.toPo(custVo, cust);
		// 受检单位
		Client client = null;
		if (null != custVo && !StrUtils.isBlankOrNull(custVo.getCustName())) {
			client = clientDao.findByName(custVo.getCustName());
		}
		// 受检单位自维护
		if (null == client && !StrUtils.isBlankOrNull(custVo.getCustName())) {
			client = new Client();
			client.setName(custVo.getCustName());
			client.setAddress(custVo.getCustAddress());
			client.setPerson(custVo.getCustUser());
			client.setPhone(custVo.getCustTel());
			client.setEmail(custVo.getCustEmail());
			client.setFax(custVo.getCustFax());
			client.setZip(custVo.getCustZip());
			client.setIndustry(custVo.getIndustry());
			client.setAttribute(custVo.getAttribute());
			client.setProduct(custVo.getProduct());
			clientDao.add(client);
		} else if (null != client) {
			// 同步受检单位信息
			if (!StrUtils.isBlankOrNull(custVo.getCustZip())) {
				client.setZip(custVo.getCustZip());
			}
			if (!StrUtils.isBlankOrNull(custVo.getCustFax())) {
				client.setFax(custVo.getCustFax());
			}
			if (!StrUtils.isBlankOrNull(custVo.getCustAddress())) {
				client.setAddress(custVo.getCustAddress());
			}
			if (!StrUtils.isBlankOrNull(custVo.getCustUser())) {
				client.setPerson(custVo.getCustUser());
			}
			if (!StrUtils.isBlankOrNull(custVo.getCustTel())) {
				client.setPhone(custVo.getCustTel());
			}
			clientDao.update(client);
		}
		cust.setClient(client);
		// 委托单位
		Customer customer = null;
		if (null != custVo && !StrUtils.isBlankOrNull(custVo.getWtName())) {
			customer = customerDao.findByName(custVo.getWtName());
		}
		// 委托单位自维护
		if (null == customer && !StrUtils.isBlankOrNull(custVo.getWtName())) {
			customer = new Customer();
			customer.setName(custVo.getWtName());
			customer.setAddress(custVo.getWtAddress());
			customer.setPerson(custVo.getWtUser());
			customer.setPhone(custVo.getWtTel());
			customer.setTelephone(custVo.getWtTel());
			customer.setEmail(custVo.getWtEmail());
			customer.setFax(custVo.getWtFax());
			customer.setZip(custVo.getWtZip());
			customer.setIndustry(custVo.getWtIndustry());
			customer.setCusType(custVo.getWtAttribute());
			customer.setBuildDate(p.getDate());
			customer.setCode(customerDao.createCode());
			customerDao.add(customer);
		} else if (null == customer && !StrUtils.isBlankOrNull(custVo.getCustName())) {
			// 当委托单位为null时，委托单位=受检单位
			cust.setWtName(custVo.getCustName());
			cust.setWtAddress(custVo.getCustAddress());
			cust.setWtUser(custVo.getCustUser());
			cust.setWtTel(custVo.getCustTel());
			cust.setWtEmail(custVo.getCustEmail());
			cust.setWtFax(custVo.getCustFax());
			cust.setWtZip(custVo.getCustZip());
			cust.setWtIndustry(custVo.getIndustry());
			cust.setWtAttribute(custVo.getAttribute());
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
			if (!StrUtils.isBlankOrNull(custVo.getWtZip())) {
				customer.setZip(custVo.getWtZip());
			}
			if (!StrUtils.isBlankOrNull(custVo.getWtFax())) {
				customer.setFax(custVo.getWtFax());
			}
			if (!StrUtils.isBlankOrNull(custVo.getWtAddress())) {
				customer.setAddress(custVo.getWtAddress());
			}
			if (!StrUtils.isBlankOrNull(custVo.getWtUser())) {
				customer.setPerson(custVo.getWtUser());
			}
			if (!StrUtils.isBlankOrNull(custVo.getWtTel())) {
				if (custVo.getWtTel().length() == 11 && !custVo.getWtTel().contains("-")) {
					customer.setPhone(custVo.getWtTel());
				} else {
					customer.setTelephone(custVo.getWtTel());
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

	public String checkCode(String taskType) throws GlobalException {
		String prefix = "";
		prefix += DateUtils.getYear() + DateUtils.getMonth();
		String hql = "SELECT max(no) FROM " + taskDao.getEntityName(Task.class) + " WHERE isDel=" + Po.N + " AND no like '" + prefix + "%'";
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

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		// pageResult.addCondition(" orgId ='"+getCurrent().getDepId()+"'");
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("status", OrderCondition.ORDER_ASC);
			pageResult.addOrder("lastUpdTime", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult.addCondition(new QueryCondition("status='" + EunmTask.TASK_DJ.getStatus() + "'"));
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
		hql.append(" AND log.status='" + EunmTask.TASK_DJ.getStatus() + "' AND log.userId like '" + CurrentUtils.getCurrent().getAccountId() + "' ");
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
	public List<Map<?, Object>> poList2mapList(List<Task> list) throws GlobalException {
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for (Task p : list) {
			Map<String, Object> map = po2map(p);
			String status = map.get("status").toString();
			map.put("status", EunmTask.getName(status));
			tempList.add(map);
		}
		return tempList;
	}

	/**
	 * 普通任务 提交
	 * 
	 * @param v
	 * @param p
	 * @throws GlobalException
	 */
	public void commit(TaskVo v, Task p) throws GlobalException {
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			Progress pg = p.getProgress();
			Org org = null;
			if (p.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				org = orgDao.findByName("环境中心");
			} else {
				org = orgDao.findByName("职业卫生中心");
			}
			p.setOrgId(org.getId());
			p.setOrgName(org.getName());
			progressLogDao.add(p.getId(), p.getId(), EunmTask.TASK_DJ.getStatus(), v.getIsCommit(), v.getRemark());
			if (p.getSource().equals(Constants.SAMP_XC)) {// 现场采样到采样安排
				pg = progressDao.update(pg.getId(), EunmTask.TASK_AP.getStatus(),null, null, null, null);
			} else {// 自送样 直接到样品交接
				pg = progressDao.update(pg.getId(), EunmTask.TASK_JJ.getStatus(), null, null, null, null);
				p.setReportNo(createReportNo(p.getSampType()));
				initZsSamp(p);// 生成样品信息
			}
			p.setStatus(pg.getStatus());
			p.setIsBack(Constants.N);
		}
	}

	// 初始化任务的车间数据
	@SuppressWarnings("unchecked")
	public void initRoom(String taskId, String sampType) {
		if (!sampType.equals(EnumBus.SAMP_TYPE_HJ)) {
			String sql = "select room from v_bus_task_point where is_del=" + Po.N + "  and task_id='" + taskId + "' group by room order by sort asc";
			List<String> roomList = taskPointDao.queryBySql(sql);
			if (null != roomList) {
				int n = 1;
				for (String room : roomList) {
					TaskRoom rm = new TaskRoom();
					rm.setTaskId(taskId);
					rm.setName(room);
					rm.setSort(n);
					taskRoomDao.add(rm);
				}
			}
		}
	}

	/**
	 * 自送 初始化样品信息
	 * 
	 * @param p
	 * @throws GlobalException
	 */
	@SuppressWarnings("unchecked")
	public void initZsSamp(Task p) throws GlobalException {
		samplingDao.deleteByTask(p.getId());// 清空之前数据，在重新生成
		List<TaskPoint> tpList = taskPointDao.listByTaskId(p.getId());
		Scheme schpo = null;
		List<SchemePoint> schPoints = null;
		if (p.getSchemeId() != null) {
			schPoints = schemePointDao.findByProperty("schemeId", p.getSchemeId());
			schemePointDao.deleteBySchId(p.getSchemeId());// 删除方案持久所有相关点位数据
			// 查询方案持久信息
			schpo = schemeDao.findById(p.getSchemeId());
			String itemNames = "";
			int m = 0;
			for (TaskPoint tp : tpList) {
				itemNames += tp.getItemNames();
				m++;
				if (m < tpList.size()) {
					itemNames += ",";
				}
			}
			schpo.setItemNames(itemNames);
		}

		int n = 1;
		for (TaskPoint tp : tpList) {
			String sql = "select code,group_concat(id),group_concat(name) from " + tablePrefix + itemDao.getEntityName(Item.class) + " WHERE is_del='" + Po.N
					+ "' AND id in('" + tp.getItemIds().replace(",", "','") + "') group by code order by sort ASC";
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
					samp.setSampCode(samplingDao.createSampCodeHj(p.getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP()));
					samplingDao.add(samp);

					// 更新项目中方案持久表中的点位信息
					if (p.getSchemeId() != null) {
						SchemePoint scpo = new SchemePoint();
						scpo.setSampTypeId(samp.getSampTypeId());
						scpo.setSampTypeName(samp.getSampTypeName());
						scpo.setSampName(samp.getSampName());
						scpo.setXz(samp.getXz());
						scpo.setItemId(samp.getItemIds());
						scpo.setItemName(samp.getItemNames());
						scpo.setFxPrice(0);
						scpo.setRemark(samp.getRemark());

						for (SchemePoint sp : schPoints) {
							if (sp.getSampName().contains(samp.getSampName())) {
								scpo.setPacking(sp.getPacking());// 包装情况
							}
						}
						scpo.setScheme(schpo);
						schemePointDao.add(scpo);
					}
					n++;
				}
				tp.setSampNum(object.size());
			}
			taskPointDao.update(tp);
		}
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

	@Override
	public void update4Yb(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		// 更新客户信息
		CustVo custVo = v.getCustVo();
		Cust cust = p.getCust();
		cust = cust.toPo(custVo, cust);
		custDao.update(cust);
		// 更新要求信息
		p.setSampName(v.getSampName());
		p.setStandIds(v.getStandIds());
		p.setStandNames(v.getStandNames());
		p.setRemark(v.getRemark());
		p.setReportNum(v.getReportNum());
		p.setReportWay(v.getReportWay());
		p.setFinishDate(v.getFinishDate());
		p.setJj(v.getJj());
		if (p.getSource().equals(Constants.SAMP_ZS)) {
			p.setDealRequest(v.getDealRequest());
			p.setSaveRequest(v.getSaveRequest());
			p.setCyName(v.getCyName());
			p.setCyDate(v.getCyDate());
		}
		taskDao.update(p);
		List<TaskPointVo> tpList = v.getTpList();
		if (null != tpList) {
			for (TaskPointVo tpVo : tpList) {
				TaskPoint tp = taskPointDao.findById(tpVo.getId());
				tp.setSampName(tpVo.getSampName());
				tp.setPointName(tpVo.getPointName());
				tp.setPointCode(tpVo.getPointCode());
				taskPointDao.update(tp);
			}
		}
		List<TaskFbVo> fbList = v.getFbList();
		if (null != fbList) {
			for (TaskFbVo tfbVo : fbList) {
				TaskFb tfb = taskFbDao.findById(tfbVo.getId());
				tfb.setFbMobile(tfbVo.getFbMobile());
				tfb.setFbUser(tfbVo.getFbUser());
				tfb.setNum(tfbVo.getNum());
				taskFbDao.update(tfb);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Other(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status ='" + EunmTask.TASK_DJ.getStatus() + "' AND acceptId is null"));
		pageResult = taskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Task>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public void update4File(TaskVo v) throws GlobalException {
		Task task = taskDao.findById(v.getId());
		task.setFileName(v.getFileName());
		task.setFilePath(v.getFilePath());
		taskDao.update(task);
	}

	@Override
	public TaskVo find4Rwd(String id) throws GlobalException {
		Task task = taskDao.findById(id);
		TaskVo vo = po2Vo(task);
		return vo;
	}

	@Override
	public TaskVo find4Dj(TaskVo v) throws GlobalException {
		v.setReportWay(Constants.RP_ZQ);
		v.setReportNum(2);
		v.setSource(Constants.SAMP_XC);
		v.setPj(Constants.F);
		v.setJj(Constants.F);
		v.setFb(Constants.F);
		v.setDate(DateUtils.getCurrDateTimeStr());
		v.setUserId(getCurrent().getAccountId());
		v.setUserName(getCurrent().getUserName());
		v.setFinishDate(DateUtils.getNextDate(v.getDate(), 5));
		return v;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Old(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		// pageResult.addCondition(new QueryCondition("taskType !='" +
		// EnumBus.LX.getName() + "' "));
		pageResult = taskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Task>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Lx(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		// pageResult.addCondition(new QueryCondition("taskType ='" +
		// EnumBus.LX.getName() + "' OR taskType ='" + EnumBus.LY.getName() + "' "));
		pageResult.addCondition(new QueryCondition("status='" + EunmTask.TASK_END.getStatus() + "'"));
		if (StrUtils.isBlankOrNull(v.getStatus()) || v.getStatus().equals("0")) {
			pageResult.addCondition(new QueryCondition("finishDate>'" + DateUtils.getBeforeDate4M(DateUtils.getCurrDateStr(), 1) + " 23:59:59'"));
		}
		pageResult = taskDao.getPageResult(pageResult);
		List<Task> list = (List<Task>) pageResult.getResultList();
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for (Task p : list) {
			Map<String, Object> map = po2map(p);
			Map<String, Object> dtMap = (Map<String, Object>) map.get("lxDtVo");
			String pc = dtMap.get("pc").toString();
			String pcUnit = dtMap.get("pcUnit").toString();
			dtMap.put("pc", pc + pcUnit);
			map.put("lxDtVo", dtMap);
			tempList.add(map);
		}
		gridVo.setDatas(tempList);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public String copy(TaskVo v) throws GlobalException {
		v = findById(v.getId());
		v.setId(null);
		Task p = vo2Po(v);
		Cust cust = new Cust();
		cust = cust.toPo(v.getCustVo(), cust);
		cust.setId(null);
		Client client = clientDao.findById(v.getCustVo().getClientVo().getId());
		cust.setClient(client);
		Customer customer = customerDao.findById(v.getCustVo().getCustomerVo().getId());
		cust.setCustomer(customer);
		custDao.add(cust);
		p.setCust(cust);
		p.setDate(DateUtils.getCurrDateTimeStr());
		p.setFinishDate(DateUtils.getNextDate(p.getDate(), 5));
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		p.setOrgId(getCurrent().getOrgId());
		p.setOrgName(getCurrent().getOrgName());
		p.setNo(checkCode(v.getTaskType()));
		p.setIsBack(Constants.N);
		p.setSampType(v.getSampType());
		p.setCyDate(null);
		p.setCyEndDate(null);
		p.setApDate(null);
		p.setZbDate(null);
		p.setCyTime(null);
		p.setXdDate(null);
		p.setSumDate(null);
		p.setReciveDate(null);
		p.setCyId(null);
		p.setCyName(null);
		p.setFzId(null);
		p.setFzName(null);
		taskDao.add(p);
		v.setId(p.getId());
		Progress progress = progressDao.add(p.getId(), EunmTask.TASK_DJ.getStatus(), p.getOrgId(), p.getOrgName(), p.getUserId(), p.getUserName());
		p.setProgress(progress);
		p.setStatus(progress.getStatus());
		// 复制项目
		List<TaskPointVo> tpList = v.getTpList();
		if (null != tpList) {
			for (TaskPointVo tpVo : tpList) {
				TaskPoint tp = new TaskPoint();
				tp.setTask(p);
				tp.setSampTypeId(tpVo.getSampTypeId());
				tp.setSampTypeName(tpVo.getSampTypeName());
				tp.setType(tpVo.getType());
				tp.setSampType(p.getSampType());
				tp.setSampName(tpVo.getSampName());
				tp.setSampNum(tpVo.getSampNum());
				tp.setItemIds(tpVo.getItemIds());
				tp.setItemNames(tpVo.getItemNames());
				tp.setRoom(tpVo.getRoom());
				tp.setPointName(tpVo.getPointName());
				tp.setPointCode(tpVo.getPointCode());
				tp.setPc(tpVo.getPc());
				tp.setPcUnit(tpVo.getPcUnit());
				taskPointDao.add(tp);
				// 复制项目 方法关系
				List<Im> imList = imDao.listByBusId(tpVo.getId());
				for (Im im : imList) {
					Im nem_im = new Im();
					nem_im.setBusId(tp.getId());
					nem_im.setItemId(im.getItemId());
					nem_im.setItemName(im.getItemName());
					nem_im.setMethodId(im.getMethodId());
					nem_im.setMethodName(im.getMethodName());
					nem_im.setPrice(im.getPrice());
					nem_im.setUserId(getCurrent().getAccountId());
					nem_im.setUserName(getCurrent().getUserName());
					nem_im.setDate(DateUtils.getCurrDateTimeStr());
					imDao.add(nem_im);
				}
			}

		}
		taskDao.update(p);
		return p.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4Select(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status >='" + EunmTask.TASK_DJ.getStatus() + "' "));
		pageResult = taskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Task>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public void save4Other(TaskVo v) throws GlobalException {
		Task p = vo2Po(v);
		p = setValue(v, p);
		// 受理人信息
		taskDao.saveOrUpdate(p);
		v.setId(p.getId());
		Progress progress = progressDao.add(p.getId(), EunmTask.TASK_DJ.getStatus(), null, null, null, null);
		p.setProgress(progress);
		p.setStatus(progress.getStatus());
		taskDao.update(p);
	}

	@Override
	public void update2Stop(TaskVo v) throws GlobalException {
		for (Task p : taskDao.listByIds(v.getIds())) {
			// 若有报告，报告终止
			List<Report> reportList = reportDao.findByProperty("task.id", p.getId());
			if (null != reportList) {
				for (Report report : reportList) {
					report.setStatus(EunmTask.TASK_STOP.getStatus());
					report.setProgress(null);
					reportDao.update(report);
				}
			}
			Progress pg = p.getProgress();
			p.setStatus(EunmTask.TASK_STOP.getStatus());
			p.setProgress(null);
			taskDao.update(p);
			// 插入任务进度日志
			progressLogDao.add(p.getId(), p.getId(), pg.getStatus(), EunmTask.TASK_STOP.getStatus(), "任务终止");
			// 清除流程实例，记录日志记录
			if (null != pg) {
				progressDao.delete(pg);
			}
			List<TaskItem> itList = taskItemDao.listByTaskId(p.getId());
			if (null != itList) {
				for (TaskItem it : itList) {
					Progress itempg = it.getProgress();
					it.setStatus(EunmTask.TASK_STOP.getStatus());
					it.setProgress(null);
					taskItemDao.update(it);
					if (null != itempg) {
						// 插入任务进度日志
						progressLogDao.add(it.getTask().getId(), it.getId(), itempg.getStatus(), EunmTask.TASK_STOP.getStatus(), "项目终止");
						progressDao.delete(itempg);
					}
				}
			}
		}
	}

	@Override
	public void update2St(String taskIds, String timIds) throws GlobalException {
		if (!StrUtils.isBlankOrNull(taskIds)) {
			List<Task> tlist = taskDao.listByIds(taskIds);
			if (null != tlist) {
				for (Task task : tlist) {
					taskDao.updateSt(task.getId());
				}
			}
		}
		if (!StrUtils.isBlankOrNull(timIds)) {
			List<TaskItem> mlist = taskItemDao.listByIds(timIds);
			if (null != mlist) {
				for (TaskItem tim : mlist) {
					taskDao.updateSt(tim.getTask().getId());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData4MySelect(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status >='" + EunmTask.TASK_DJ.getStatus() + "' "));
		String hql = " select t from " + taskDao.getEntityName(Task.class) + " t where  t.status <> '" + EunmTask.TASK_DJ.getStatus()
				+ "' and t.cust.customer.id='" + v.getCustomerId() + "' and t.isDel =" + Po.N;
		// hql.append(" ORDER BY t." + pageResult.getOrderColumn() + " " +
		// pageResult.getOrder() + "");
		pageResult = taskDao.getPageResult(hql.toString(), pageResult);
		gridVo.setDatas(poList2mapList((List<Task>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public void importData(TaskVo v, String type, String param, String[][] dataArrays) throws GlobalException {
		// 匹配增加
		if (CLEAR_ADD.equals(type)) {
			// 删除点位信息
			taskPointDao.deleteByTaskId(v.getId());
		}
		TaskPoint tpo = null;
		Task task = vo2Po(v);
		task.setId(v.getId());
		for (int i = 1, j = dataArrays.length; i < j; i++) {
			if (dataArrays[i].length == 0)
				continue;

			String[] values = dataArrays[i];
			if (null == values[0] || "".equals(values[0].trim())) {
				log.info("第 " + i + "行无效数据不做导入");
				continue;
			}
			if (null == values[1] || "".equals(values[1].trim())) {
				log.info("第 " + i + "行无效数据不做导入");
				continue;
			}
			if (null == values[2] || "".equals(values[2].trim())) {
				log.info("第 " + i + "行无效数据不做导入");
				continue;
			}
			tpo = new TaskPoint();
			tpo.setTask(task);
			// 数组数据对应至对象
			data2Vo(values, tpo, param);
			taskPointDao.add(tpo);
		}
	}

	public void data2Vo(String[] values, TaskPoint p, String param) {
		p.setRoom(values[0]);
		p.setPointName(values[1]);
		p.setItemNames(values[2]);
	}

}
