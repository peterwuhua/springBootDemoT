package cn.demi.bus.test.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EnumBus;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Org;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.sample.dao.ISampRecordDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampRecord;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.bus.task.dao.ITaskDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.vo.TaskVo;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.bus.test.service.ITestItemService;
import cn.demi.bus.test.vo.TaskItemVo;
import cn.demi.bus.test.vo.TestItemVo;
import cn.demi.bus.test.vo.TestResultVo;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IPstandItemDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.PstandItem;

@Service("bus.testItemService")
public class TestItemServiceImpl extends BaseServiceImpl<TestItemVo,TestItem> implements
		ITestItemService {
	@Autowired
	private ITaskItemDao  taskItemDao;
	@Autowired
	private ITaskDao  taskDao;
	@Autowired
	private ITestItemDao  testItemDao;
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private ITestResultDao testResultDao;
	@Autowired
	private IProgressLogDao progressLogDao;
	@Autowired
	private IPstandItemDao pstandItemDao;
	@Autowired
	private IItemDao itemDao;
    @Autowired
	private ISampRecordDao sampRecordDao;
//	@Autowired
//	private IPstandardDao pstandardDao;
//	@Autowired
//	private IMethodDao methodDao;
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private IOrgDao orgDao;
//	@Autowired
//    private ISampTypeDao sampTypeDao;
	@Override
	public List<TestItemVo> listByTimId(String id) throws GlobalException {
		List<TestItem> itList=testItemDao.listByTimId(id);
		return toVoList(itList);
	}

	/**
	 * 环境类添加质控样
	 * 新增项目 质控样品
	 * 
	 */
	@Override
	public boolean add4Zk(TestItemVo v) throws GlobalException {
		TaskItem tim=taskItemDao.findById(v.getTimVo().getId());
		Item item=itemDao.findById(tim.getItemId());
		//测试项目信息
		TestItem it=new TestItem();
		it.setTask(tim.getTask());
		it.setTim(tim);
		it.setCust(tim.getTask().getCust());
		it.setPoint(null);
		it.setCyDate(DateUtils.getCurrDateStr());
		it.setItemId(tim.getItemId());
		it.setItemName(tim.getItemName());
		if(item.getParent()!=null) {
			it.setLevel(2);
		}else {
			it.setLevel(1);
		}
		it.setResult(TaskItem.RESULT_YES);
		it.setType(TaskItem.ITEM_TYPE_PT);
		it.setIsBack(Constants.N);
		it.setSort(testItemDao.getMaxSort(tim.getId()));
		testItemDao.add(it);
		//质控样信息
		Sampling newSamp=new Sampling();
		newSamp.setTask(tim.getTask());
		newSamp.setCust(tim.getTask().getCust());
		newSamp.setPoint(null);
		newSamp.setSampTypeId(item.getSampTypeIds());
		newSamp.setSampTypeName(item.getSampTypeNames());
		newSamp.setSampCode("/");
		newSamp.setSampType(tim.getTask().getSampType());
		newSamp.setType(Sampling.SAMP_TYPE_PT);
		if(v.getZkType().equals(Sampling.SAMP_TYPE_JB)) {
			newSamp.setZkType(Sampling.SAMP_TYPE_JB);
			newSamp.setSampName("加标回收样");
		}else if(v.getZkType().equals(Sampling.SAMP_TYPE_PX_S)) {
			newSamp.setZkType(Sampling.SAMP_TYPE_PX_S);
			newSamp.setSampName("室内平行样");
		}else {
			newSamp.setZkType(Sampling.SAMP_TYPE_PT);
		}
		newSamp.setItemIds(tim.getItemId());
		newSamp.setItemNames(tim.getItemName());
		newSamp.setOrgId(getCurrent().getOrgId());
		newSamp.setOrgName(getCurrent().getOrgName());
		newSamp.setDeptId(getCurrent().getDepId());
		newSamp.setDeptName(getCurrent().getDepName());
		newSamp.setLy(Constants.F);
		newSamp.setStatus(Sampling.ST_30);
		newSamp.setSort(samplingDao.findMaxSort(tim.getTask().getId()));
		samplingDao.add(newSamp);
		//质控样测试结果信息
		TestResult tr=new TestResult();
		tr.setSamp(newSamp);
		tr.setIt(it);
		tr.setSort(testResultDao.getMaxSort(tim.getId()));
		testResultDao.add(tr);
		progressLogDao.add(tim.getTask().getId(),tim.getId(),EunmTask.ITEM_LR.getStatus(),Constants.PASS_S,"添加质控样");
		return true;
	}

	@Override
	public boolean delete4Zk(String id) throws GlobalException {
		TestResult tr=testResultDao.findById(id);
		Sampling samp=tr.getSamp();
		TestItem it=tr.getIt();
		try {
			testResultDao.delete(tr);
			List<TestResult> l=testResultDao.listBySampId(samp.getId());
			if(l==null||l.size()==0) {
				samplingDao.delete(samp);
			}
			l=testResultDao.listByItId(it.getId());
			if(l==null||l.size()==0) {
				testItemDao.delete(it);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public boolean checkValue(TestItemVo v) throws GlobalException {
		boolean b=true;
		TaskItem tim=taskItemDao.findById(v.getId());
		if(!StrUtils.isBlankOrNull(tim.getPitId())&&!StrUtils.isBlankOrNull(v.getValue())) {
			PstandItem ptd=pstandItemDao.findById(tim.getPitId());
			if(ptd!=null) {
				if(!pstandItemDao.check(ptd,v.getValue())) {
					b=false;
				}
			}
		}
		return b;
	}

	@Override
	public List<TaskItemVo> list4Xc(String taskId) throws GlobalException {
		List<TaskItem> timList=taskItemDao.listByTaskId(taskId, String.valueOf(Po.Y));
		List<TaskItemVo> timVoList=new ArrayList<TaskItemVo>();
		if(null!=timList) {
			for (TaskItem tim : timList) {
				TaskItemVo timVo=new TaskItemVo();
				timVo=timVo.toVo(tim);
				timVo.setItemList(list4Item(tim.getId()));
				timVoList.add(timVo);
			}
		}
		return timVoList;
	}
	//获取测试任务下的测试项目集合
	public List<TestItemVo> list4Item(String timId) throws GlobalException{
		List<TestItem> itList=testItemDao.listByTimId(timId);
		List<TestItemVo> itVoList=new ArrayList<>();
		if(null!=itList) {
			for (TestItem it : itList) {
				TestItemVo itVo=new TestItemVo();
				itVo=itVo.toVo(it);
				if(it.getItemName().contains("粉尘")) {
					itVo.setTrList(list4Result(it.getId()));
				}else {
					itVo.setTrVo(findResult(it.getId()));
				}
				itVoList.add(itVo);
			}
		}
		return itVoList;
	}
	//获取测试项目的测试结果集合
	public List<TestResultVo> list4Result(String itId) throws GlobalException{
		List<TestResult> rList=testResultDao.listByItId(itId);
		List<TestResultVo> rVoList=new ArrayList<TestResultVo>();
		if(null!=rList) {
			for (TestResult r : rList) {
				TestResultVo rVo=new TestResultVo();
				rVo=rVo.toVo(r);
				rVoList.add(rVo);
			}
		}
		return rVoList;
	}
	//获取测试项目的结果信息
	public TestResultVo findResult(String itId) throws GlobalException{
		List<TestResult> rList=testResultDao.listByItId(itId);
		TestResultVo rVo=new TestResultVo();
		if(null!=rList&&rList.size()>0) {
			rVo=rVo.toVo(rList.get(0));
		}
		return rVo;
	}
	@Override
	public void update4Xc(TaskVo v) throws GlobalException {
		Task task=taskDao.findById(v.getId());
		if(v.getIsCommit()!=null&&v.getIsCommit().equals(Constants.PASS_Y)) {
			task.setXcSt(Task.ST_2);
		}else {
			task.setXcSt(Task.ST_1);
		}
		taskDao.update(task);
		List<TaskItemVo> timList=v.getTimList();
		for (TaskItemVo timVo : timList) {
			//更新 项目任务表信息
			TaskItem tim=taskItemDao.findById(timVo.getId());
			uptLmt(tim, timVo);
			//更新评价标准
			tim.setStandId(timVo.getStandId());
			tim.setStandName(timVo.getStandName());
			//更新检查方法
			tim.setMethodId(timVo.getMethodId());
			tim.setMethodName(timVo.getMethodName());
			tim.setLimitLine(timVo.getLimitLine());
			//更新检测仪器
			tim.setAppId(timVo.getAppId());
			tim.setAppName(timVo.getAppName());
			tim.setUnit(timVo.getUnit());
			tim.setSlUnit(timVo.getSlUnit());
			tim.setTestMan(timVo.getTestMan());
			tim.setTestManId(timVo.getTestManId());
			tim.setCheckMan(timVo.getCheckMan());
			tim.setCheckManId(timVo.getCheckManId());
			tim.setTestTime(timVo.getTestTime());
			tim.setTestEndTime(tim.getTestTime());
			tim.setCheckTime(tim.getTestTime());
			Account at=accountDao.findById(timVo.getTestManId());
			Org org=orgDao.findOrg(at.getOrg().getId());
			tim.setOrgId(org.getId());
			tim.setOrgName(org.getName());
			tim.setDeptId(at.getOrg().getId());
			tim.setDeptName(at.getOrg().getName());
			tim.setWd(timVo.getWd());
			tim.setSd(timVo.getSd());
			//更新 测试项目信息
			List<TestItemVo> itList=timVo.getItemList();
			int n=0;
			Set<String> sampNameSet=new HashSet<String>();
			for (TestItemVo itVo : itList) {
				TestItem it=testItemDao.findById(itVo.getId());
				it.setValue(itVo.getValue());
				it.setSl(itVo.getSl());
				it.setTwa(itVo.getTwa());
				it.setMac(itVo.getMac());
				it.setStel(itVo.getStel());
				it.setLmt(itVo.getLmt());
				it.setResult(itVo.getResult());
				testItemDao.update(it);
				//更新项目结果信息
				List<TestResultVo> trList=itVo.getTrList();
				if(trList!=null&&trList.size()>0) {
					for (TestResultVo trVo : trList) {
						TestResult tr=testResultDao.findById(trVo.getId());
						tr.setValue(trVo.getValue());
						tr.setValue2(trVo.getValue2());
						tr.setSl(trVo.getSl());
						testResultDao.update(tr);
						uptValue4Xc(tim, trVo.getSampVo(), tr.getSamp().getRecord());
						n++;
					}
				}else if(itVo.getTrVo()!=null) {
					TestResultVo trVo=itVo.getTrVo();
					TestResult tr=testResultDao.findById(trVo.getId());
					tr.setValue(trVo.getValue());
					tr.setValue2(trVo.getValue2());
					tr.setSl(trVo.getSl());
					testResultDao.update(tr);
					uptValue4Xc(tim, trVo.getSampVo(), tr.getSamp().getRecord());
					sampNameSet.add(tr.getSamp().getSampName());
					n++;
				}
			}
			tim.setSampNum(n);
			tim.setSampName(String.join(",", sampNameSet));
			taskItemDao.update(tim);
		}
	}
	//现场项目 退回到数据录入 更新现场数据信息
	public void uptValue4Xc(TaskItem tim,SamplingVo sampVo,SampRecord record) {
		if(tim.getType().equals(TaskItem.ITEM_TYPE_XC)&&null!=sampVo&&null!=sampVo.getRecordVo()){
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV1())) {
				record.setV1(sampVo.getRecordVo().getV1());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV2())) {
				record.setV2(sampVo.getRecordVo().getV2());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV3())) {
				record.setV3(sampVo.getRecordVo().getV3());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV4())) {
				record.setV4(sampVo.getRecordVo().getV4());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV5())) {
				record.setV5(sampVo.getRecordVo().getV5());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV6())) {
				record.setV6(sampVo.getRecordVo().getV6());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV7())) {
				record.setV7(sampVo.getRecordVo().getV7());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV8())) {
				record.setV8(sampVo.getRecordVo().getV8());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV9())) {
				record.setV9(sampVo.getRecordVo().getV9());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV10())) {
				record.setV10(sampVo.getRecordVo().getV10());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV11())) {
				record.setV11(sampVo.getRecordVo().getV11());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getV12())) {
				record.setV12(sampVo.getRecordVo().getV12());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getAvg1())) {
				record.setAvg1(sampVo.getRecordVo().getAvg1());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getAvg2())) {
				record.setAvg2(sampVo.getRecordVo().getAvg2());
			}
			if(!StrUtils.isBlankOrNull(sampVo.getRecordVo().getAvg3())) {
				record.setAvg3(sampVo.getRecordVo().getAvg3());
			}
			sampRecordDao.update(record);
		}
	}
	//检测标准更新，同步限值
	public void uptLmt(TaskItem tim,TaskItemVo v) {
		if(StrUtils.isBlankOrNull(v.getStandId())) {
			tim.setStandId(null);
			tim.setStandName(null);
			tim.setLimited(null);
			tim.setMac(null);
			tim.setStel(null);
			tim.setTwa(null);
			tim.setLmt(null);
		}else if(StrUtils.isBlankOrNull(tim.getStandId())
				||!tim.getStandId().equals(v.getStandId())) {
			tim.setStandId(v.getStandId());
			tim.setStandName(v.getStandName());
			if(tim.getTask().getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				List<PstandItem> plist=pstandItemDao.listStand4Item(tim.getStandId(), tim.getItemId());
				if(null!=plist&&plist.size()>0) {
					PstandItem pit=plist.get(0);
					tim.setStandId(pit.getStandId());
					tim.setStandName(pit.getStandName());
					tim.setLimited(pit.getValStr());
				}
			}else {
				List<PstandItem> plist=pstandItemDao.listStand(tim.getStandId(), tim.getItemId(),null, tim.getItemType(),null);
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
				}
				
			}
		}
	}

}
