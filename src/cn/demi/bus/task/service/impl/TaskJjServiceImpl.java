package cn.demi.bus.task.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
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
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.po.Im;
import cn.demi.bus.sample.dao.ISampContainerDao;
import cn.demi.bus.sample.dao.ISampCydDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampContainer;
import cn.demi.bus.sample.po.SampCyd;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.vo.SampContainerVo;
import cn.demi.bus.sample.vo.SampCydVo;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.task.service.ITaskJjService;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.init.st.dao.ISampTypeDao;
import cn.demi.init.st.po.SampType;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IItemMethodDao;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.dao.IPstandItemDao;
import cn.demi.init.std.dao.IPstandardDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.Method;
import cn.demi.init.std.po.PstandItem;
import cn.demi.init.std.po.Pstandard;
import cn.demi.res.dao.IApparaDao;
import cn.demi.res.po.Appara;

@Service("bus.taskJjService")
public class TaskJjServiceImpl extends BaseServiceImpl<TaskVo,Task> implements
		ITaskJjService {
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IProgressDao progressDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private ITestItemDao testItemDao;
//	@Autowired
//	private IItemUserDao itemUserDao;
	@Autowired
	private IItemMethodDao itemMethodDao;
	@Autowired
	private ISampContainerDao sampContainerDao;
//	@Autowired
//    private IAccountDao accountDao;
//	@Autowired
//	private ISampSourceDao sampSourceDao;
	@Autowired
	private IPstandItemDao pstandItemDao;
	@Autowired
	private ISampCydDao sampCydDao;
	@Autowired
	private ITestResultDao testResultDao;
	@Autowired
	private ITaskItemDao  taskItemDao;
	@Autowired
    private ISampTypeDao sampTypeDao;
    @Autowired
	private IImDao imDao;
	@Autowired
	private IPstandardDao pstandardDao;
	@Autowired
	private IMethodDao methodDao;
	@Autowired
	private IApparaDao apparaDao;
	@Override
	public TaskVo find(String id) throws GlobalException {
		Task task=taskDao.findById(id);
		TaskVo vo=po2Vo(task);
		if(vo.getSource().equals(Constants.SAMP_XC)) {
			List<SampCyd> cydList=sampCydDao.listByTaskId(task.getId());
			List<SampCydVo> cydVoList=new ArrayList<SampCydVo>();
			for (SampCyd cyd : cydList) {
				SampCydVo cydVo=new SampCydVo();
				cydVo=cydVo.toVo(cyd);
				List<Sampling> sampList=samplingDao.listByCyd(cyd.getId(),Sampling.SAMP_TYPE_PT);
				List<SamplingVo> sampVoList=new ArrayList<SamplingVo>();
				if(sampList!=null&&sampList.size()>0) {
					for (Sampling samp : sampList) {
						SamplingVo sampVo=new SamplingVo();
						sampVo=sampVo.toVo(samp);
						if(sampVo.getSaveHour()==0&&!StrUtils.isBlankOrNull(samp.getItemIds())) {
							sampVo.setSaveHour(itemMethodDao.findMaxHours(samp.getItemIds()));
						}
						sampVo.setContainerList(getContainerList(samp.getId()));
						sampVoList.add(sampVo);
					}
					cydVo.setSampList(sampVoList);
					cydVoList.add(cydVo);
				}
			}
			vo.setCydList(cydVoList);
			if(StrUtils.isBlankOrNull(vo.getDealRequest())) {
				vo.setDealRequest(Constants.SAMP_XH);
			}
		}else {
			//样品信息
			List<Sampling> sampList=samplingDao.listByTaskId(id);
			List<SamplingVo> sampVoList=new ArrayList<SamplingVo>();
			if(sampList!=null) {
				for (Sampling samp : sampList) {
					SamplingVo sampVo=new SamplingVo();
					sampVo=sampVo.toVo(samp);
					if(sampVo.getSaveHour()==0) {
						sampVo.setSaveHour(itemDao.getMaxHours(sampVo.getItemIds()));
					}
					sampVoList.add(sampVo);
				}
			}
			vo.setSampList(sampVoList);
		}
		return vo;
	}
	/**
	 * 获取任务详细信息
	 * 包含项目、样品信息
	 */
	@Override
	public TaskVo findById(String id) throws GlobalException {
		Task task=taskDao.findById(id);
		TaskVo vo=po2Vo(task);
		if(vo.getSource().equals(Constants.SAMP_XC)) {
			List<SampCyd> cydList=sampCydDao.listByTaskId(task.getId());
			List<SampCydVo> cydVoList=new ArrayList<SampCydVo>();
			for (SampCyd cyd : cydList) {
				SampCydVo cydVo=new SampCydVo();
				cydVo=cydVo.toVo(cyd);
				List<Sampling> sampList=samplingDao.listByCyd(cyd.getId(),Sampling.SAMP_TYPE_PT);
				List<SamplingVo> sampVoList=new ArrayList<SamplingVo>();
				if(sampList!=null&&sampList.size()>0) {
					for (Sampling samp : sampList) {
						SamplingVo sampVo=new SamplingVo();
						sampVo=sampVo.toVo(samp);
						sampVo.setContainerList(getContainerList(samp.getId()));
						sampVoList.add(sampVo);
					}
					cydVo.setSampList(sampVoList);
					cydVoList.add(cydVo);
				}
			}
			vo.setCydList(cydVoList);
		}else {
			//样品信息
			List<Sampling> sampList=samplingDao.listByTaskId(id);
			List<SamplingVo> sampVoList=new ArrayList<SamplingVo>();
			if(sampList!=null) {
				for (Sampling samp : sampList) {
					SamplingVo sampVo=new SamplingVo();
					sampVo=sampVo.toVo(samp);
					if(sampVo.getSaveHour()==0) {
						sampVo.setSaveHour(itemDao.getMaxHours(sampVo.getItemIds()));
					}
					sampVoList.add(sampVo);
				}
			}
			vo.setSampList(sampVoList);
		}
		return vo;
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
		}
		return scList;
	}
	@Override
	public void update(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		//样品交接  信息值回填
		p.setReciveDate(v.getReciveDate());
		p.setReciveUser(v.getReciveUser());
		p.setReciveUserId(v.getReciveUserId());
		p.setDealRequest(v.getDealRequest());
		p.setReciveMsg(v.getReciveMsg());
		List<SamplingVo> sampList= v.getSampList();
		for (SamplingVo sampVo : sampList) {
			Sampling samp=samplingDao.findById(sampVo.getId());
			samp.setDeptId(getCurrent().getDepId());
			samp.setDeptName(getCurrent().getDepName());
			samp.setReciveDate(v.getReciveDate());
			samp.setReciveUser(v.getReciveUser());
			samp.setReciveUserId(v.getReciveUserId());
			samp.setXz(sampVo.getXz());
			samp.setLy(sampVo.getLy());
			samp.setSampStatus(sampVo.getSampStatus());
			samp.setTjj(sampVo.getTjj());
			samp.setCcfl(sampVo.getCcfl());
			samp.setSaveAddress(sampVo.getSaveAddress());
			samp.setSampCode(sampVo.getSampCode());
			if(p.getSource().equals(Constants.SAMP_ZS)&&StrUtils.isBlankOrNull(sampVo.getCyTime())) {
				if(v.getReciveDate().length()>15) {
					samp.setCyTime(v.getReciveDate().substring(11, 15));
					samp.setCyDate(v.getReciveDate().substring(0,10));
				}else {
					samp.setCyTime(DateUtils.getCurrDateTimeStr().substring(11, 15));
					samp.setCyDate(DateUtils.getCurrDateTimeStr().substring(0,10));
				}
			}else {
				samp.setCyTime(sampVo.getCyTime());
			}
			samp.setSaveHour(sampVo.getSaveHour());
			samp.setPyHour(sampVo.getPyHour());
			samp.setDealRequest(p.getDealRequest());
			samp.setSaveRequest(sampVo.getSaveRequest());
			if(samp.getLy().equals(Constants.S)) {
				samp.setStatus(Sampling.ST_10);
				samp.setLyDate(v.getReciveDate());
			}else {
				samp.setStatus(Sampling.ST_00);
			}
			samp.setSort(sampVo.getSort());
			samplingDao.update(samp);
		}
		p.setSampNum(sampList.size());
		//更新任务进度及日志
		if(!StrUtils.isBlankOrNull(v.getIsCommit())&&!v.getIsCommit().equals(EunmTask.PASS_S)) {
			Progress pg=progressDao.update(p.getProgress().getId(),EunmTask.TASK_XD.getStatus(),null,null,null,null);
			p.setStatus(pg.getStatus());
			progressLogDao.add(p.getId(), p.getId(), EunmTask.TASK_JJ.getStatus(),v.getIsCommit(),v.getReciveMsg());
			initItem(p);//初始化实验室项目
		}
		taskDao.update(p);
	}
	@Override
	public void updateBack(TaskVo v) throws GlobalException {
		Task p = taskDao.findById(v.getId());
		//样品交接  信息值回填
		p.setReciveDate(v.getReciveDate());
		p.setReciveUser(v.getReciveUser());
		p.setReciveUserId(v.getReciveUserId());
		p.setReciveMsg(v.getReciveMsg());
		//更新任务进度及日志
		if(!StrUtils.isBlankOrNull(v.getIsCommit())&&v.getIsCommit().equals(EunmTask.PASS_N)) {
			Progress pg=null;
			if(p.getSource().equals(Constants.SAMP_XC)) {//交接退回到现场采样
				pg=progressDao.update(p.getProgress().getId(),EunmTask.TASK_XC.getStatus(),null,null,p.getCyId(),p.getCyName());
			}else {//交接退回到任务登记
				pg=progressDao.update(p.getProgress().getId(),EunmTask.TASK_DJ.getStatus(),p.getOrgId(),p.getOrgName(),p.getUserId(),p.getUserName());
			}
			p.setStatus(pg.getStatus());
			p.setIsBack(Constants.Y);
			progressLogDao.add(p.getId(), p.getId(), EunmTask.TASK_JJ.getStatus(),v.getIsCommit(),v.getReciveMsg());
		}
		taskDao.update(p);
	}
	/**
	 * 初始化 实验室检测项目
	 * @param task
	 * @throws GlobalException
	 */
	public void initItem(Task task) throws GlobalException {
		List<Sampling> sampList =samplingDao.listByTaskId(task.getId(),Sampling.SAMP_TYPE_PT);
		for (Sampling samp : sampList) {
			if(samp.getLy().equals(Constants.S)) {
				continue;//现场留样的不初始化项目
			}
			List<Item> itemList=itemDao.listByIds(samp.getItemIds(),Constants.F);
			if(null!=itemList) {
				for (Item item : itemList) {
					//检查项目任务信息是否存在
					TaskItem tim=taskItemDao.find(task.getId(), item.getId());
					if(tim==null) {
						tim=saveTim(task, item,samp);
					} 
					float h=samp.getPyHour()+samp.getSaveHour();
					try {
						String comDate=DateUtils.getNextDate4Hours(DateUtils.parse(samp.getCyDate()+" "+samp.getCyTime(), DateUtils.formatStr_yyyyMMddHHmm),floatToInt(h));
						if(StrUtils.isBlankOrNull(tim.getCompDate())) {
							tim.setCompDate(comDate);	
						}else {
							long n=DateUtils.getIntevalHours(tim.getCompDate(), comDate);
							if(n>0) {
								tim.setCompDate(comDate);
							}
						}
					} catch (ParseException e) {
						log.error(e.getMessage(), e);
					}
					if(!StrUtils.isBlankOrNull(tim.getCompDate())) {
						tim.setSbDate(DateUtils.getNextWorkDate(tim.getCompDate(),1));
					}
					taskItemDao.update(tim);
					TestItem it=null;
					if(task.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
						//生成测试项目信息
						it=saveIt(tim,samp.getPoint(),samp,item);
					}else {
						//检查并生成测试项目信息
						it=testItemDao.find(samp.getPoint().getId(), samp.getCyDate(), item.getId());
						if(it==null) {
							it=saveIt(tim,samp.getPoint(),samp,item);
						}
					}
					//生成结果信息
					saveTr(it, samp);
				}
			}
		}
		List<TaskItem> timList=taskItemDao.listByTaskId(task.getId(), TaskItem.ITEM_TYPE_PT);
		for (TaskItem tim : timList) {
			//默认 检测最近一次分配的科室
			String jpql="select dept_id,dept_name from "+tablePrefix+testItemDao.getEntityName(TaskItem.class)+" where dept_id is not null and item_id = '"+tim.getItemId()+"' ORDER BY assign_date desc";
			@SuppressWarnings("unchecked")
			List<Object[]> objList=testItemDao.queryBySql(jpql);
			if(objList!=null&&objList.size()>0) {
				Object[] obj=objList.get(0);
				tim.setDeptId(String.valueOf(obj[0]));
				tim.setDeptName(String.valueOf(obj[1]));
			}
			String hql="FROM "+taskItemDao.getEntityName(TestResult.class)+" WHERE isDel='"+TestResult.N+"' AND it.tim.id ='"+tim.getId()+"'";
			List<TestResult> stlist=testResultDao.list(hql);
			Set<String> nameSet=new HashSet<String>();
			for (TestResult tr : stlist) {
				if(tr.getSamp().getZkType().equals(Sampling.SAMP_TYPE_PT)) {
					nameSet.add(tr.getSamp().getSampName());
				}
			}
			tim.setSampNum(stlist.size());
			tim.setSampName(String.join(",", nameSet));
			taskItemDao.update(tim);
		}
	}
	//保存项目任务信息
	public TaskItem saveTim(Task task,Item item,Sampling samp) {
		TaskItem tim=new TaskItem();
		SampType st=sampTypeDao.findById(item.getSampTypeIds());
		tim.setTask(task);
		tim.setOrgId(getCurrent().getOrgId());
		tim.setOrgName(getCurrent().getOrgName());
		tim.setSampTypeId(item.getSampTypeIds());
		tim.setSampTypeName(item.getSampTypeNames());
		tim.setSt(st.getType());
		tim.setItemId(item.getId());
		tim.setItemName(item.getName());
		tim.setUnit(item.getUnit());
		tim.setPrice(item.getPrice());
		tim.setType(TaskItem.ITEM_TYPE_PT);
		if(tim.getItemName().contains("粉尘")) {
			tim.setItemType(samp.getFcType());
		}else if(samp.getCyd() != null && (samp.getCyd().getType().equals("cyd_wl_jg")
				||samp.getCyd().getType().equals("cyd_wl_cp")
				||samp.getCyd().getType().equals("cyd_wl_cg"))) {
			tim.setItemType(samp.getCyd().getItemType());
		}
		String sampTypeIds=sampTypeDao.findAllIds(tim.getSampTypeId());
		if(task.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
			List<PstandItem> plist=pstandItemDao.listStand4Item(task.getStandIds(), item.getId());
			if(null!=plist&&plist.size()>0) {
				PstandItem pit=plist.get(0);
				tim.setStandId(pit.getStandId());
				tim.setStandName(pit.getStandName());
				tim.setLimited(pit.getValStr());
				tim.setPitId(pit.getId());
			}
		}else {
			List<Pstandard> standList=pstandardDao.listBySampTyle(sampTypeIds);
			if(null!=standList&&standList.size()>0) {
				Pstandard stand=standList.get(0);
				tim.setStandId(stand.getId());
				tim.setStandName(stand.getName());
				List<PstandItem> plist=pstandItemDao.listStand(stand.getId(), item.getId(), null,tim.getItemType(),null);
				if(plist!=null&&plist.size()>0) {
					PstandItem pit=plist.get(0);
					if(pit.getSampTypeName().contains("化学")) {
						tim.setLmt(pit.getMaxValue());
						tim.setMac(pit.getValue3());
						tim.setTwa(pit.getValue());
						tim.setStel(pit.getValue2());
						if(!StrUtils.isBlankOrNull(tim.getMac())) {
							tim.setLimited("a");
						}else if(!StrUtils.isBlankOrNull(tim.getTwa())&&!StrUtils.isBlankOrNull(tim.getStel())) {
							tim.setLimited("b");
						}else if(!StrUtils.isBlankOrNull(tim.getTwa())) {
							tim.setLimited("c");
						}
					}else {
						//物理等其他
						tim.setLimited(pit.getValStr());
					}
					tim.setPitId(pit.getId());
				}
			}
		}
		Im im=imDao.findByBusIdAndItemId(samp.getPoint().getId(), item.getId());
		if(null!=im) {
			tim.setMethodId(im.getMethodId());
			tim.setMethodName(im.getMethodName());
			Method m=methodDao.findById(im.getMethodId());
			tim.setLimitLine(m.getMinLine());
			if(!StrUtils.isBlankOrNull(m.getAppIds())) {
				List<Appara> appList=apparaDao.listByIds4Use(m.getAppIds());
				if(null!=appList&&appList.size()>0) {
					Appara app=appList.get(0);
					tim.setAppId(app.getId());
					tim.setAppName(app.getName()+app.getSpec()+"("+app.getNo()+")");
				}
			}
		}
		tim.setIsBack(Constants.N);
		tim.setSort(taskItemDao.getMaxSort(task.getId())+1);
		taskItemDao.add(tim);
		return tim;
	}
	//保存测试项目
	public TestItem saveIt(TaskItem tim,TaskPoint point,Sampling samp,Item item) {
		TestItem it=new TestItem();
		it.setTask(tim.getTask());
		it.setTim(tim);
		it.setCust(tim.getTask().getCust());
		it.setPoint(point);
		it.setCyDate(samp.getCyDate());
		it.setItemId(item.getId());
		it.setItemName(item.getName());
		if(item.getParent()!=null) {
			it.setLevel(2);
		}else {
			it.setLevel(1);
		}
		it.setResult(TaskItem.RESULT_YES);
		it.setType(TaskItem.ITEM_TYPE_PT);
		it.setIsBack(Constants.N);
		it.setSort(testItemDao.getMaxSort(tim.getId())+1);
		testItemDao.add(it);
		return it;
	}
	//保存测试结果信息
	public TestResult saveTr(TestItem tim,Sampling samp) {
		TestResult tr=new TestResult();
		tr.setSamp(samp);
		tr.setIt(tim);
		tr.setSort(testResultDao.getMaxSort(tim.getId())+1);
		testResultDao.add(tr);
		return tr;
	}
	public int floatToInt(float f){
	    int i = 0;
	    if(f>0) //正数
	      i = (int) ((f*10 + 5)/10);
	    else if(f<0) //负数
	      i = (int) ((f*10 - 5)/10);
	    else i = 0;
	    return i;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, TaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status='"+EunmTask.TASK_JJ.getStatus()+"' " ));
		//pageResult.addCondition(new QueryCondition("orgId like '"+CurrentUtils.getCurrent().getOrgId()+"%' "));
		pageResult = taskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Task>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(TaskVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		//queryList.add(new QueryCondition("type='" + EnumBus.TYPE_ZJ + "' "));
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
		hql.append(" AND log.status='"+EunmTask.TASK_JJ.getStatus()+"' ");
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
	public List<Map<?, Object>> poList2mapList(List<Task> list) throws GlobalException {
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for (Task p : list) {
			Map<String, Object> map = po2map(p);
			String cyDate=(String) map.get("cyDate");
			if(cyDate==null) {
				map.put("cyDate", DateUtils.getCurrDateStr());
			}
			tempList.add(map);
		}
		return tempList;
	}
	@Override
	public boolean checkCode(String sampCode) throws GlobalException {
		List<Sampling> lst=samplingDao.findByProperty("sampCode", sampCode);
		if(lst!=null) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public boolean update4File(TaskVo v) throws GlobalException {
		Task task=taskDao.findById(v.getId());
		task.setFileName(v.getFileName());
		task.setFilePath(v.getFilePath());
		taskDao.update(task);
		return true;
	}
}
