package cn.demi.bus.sample.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.BeanUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.sample.dao.ISampCydDao;
import cn.demi.bus.sample.dao.ISampRecordDao;
import cn.demi.bus.sample.dao.ISamplingDao;
import cn.demi.bus.sample.po.SampCyd;
import cn.demi.bus.sample.po.SampRecord;
import cn.demi.bus.sample.po.Sampling;
import cn.demi.bus.sample.service.ISamplingService;
import cn.demi.bus.sample.vo.SamplingVo;
import cn.demi.bus.task.dao.ITaskPointDao;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;
import cn.demi.bus.test.dao.ITaskItemDao;
import cn.demi.bus.test.dao.ITestItemDao;
import cn.demi.bus.test.dao.ITestResultDao;
import cn.demi.bus.test.po.TaskItem;
import cn.demi.bus.test.po.TestItem;
import cn.demi.bus.test.po.TestResult;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.po.Item;

@Service("bus.samplingService")
public class SamplingServiceImpl extends BaseServiceImpl<SamplingVo, Sampling> implements ISamplingService {
	@Autowired
	private ISamplingDao samplingDao;
	@Autowired
	private ITestResultDao testResultDao;
	@Autowired
	private ITaskPointDao taskPointDao;
	@Autowired
	private ITestItemDao  testItemDao;
	@Autowired
	private ITaskItemDao  taskItemDao;
	@Autowired
	private ISampCydDao sampCydDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private ISampRecordDao sampRecordDao;
	@Override
	public SamplingVo findById(String id) throws GlobalException {
		Sampling po = samplingDao.findById(id);
		SamplingVo vo = po2Vo(po);
		return vo;
	}

	@Override
	public void update(SamplingVo v) throws GlobalException {
		Sampling po = samplingDao.findById(v.getId());
		po.setLyDate(v.getLyDate());
		po.setLyReason(v.getLyReason());
		po.setBcqx(v.getBcqx());
		samplingDao.update(po);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, SamplingVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addCondition(new QueryCondition("status='" + Sampling.ST_00 + "' or status='" + Sampling.ST_10 + "'"));
		pageResult.addCondition(new QueryCondition("type='" + Sampling.SAMP_TYPE_PT + "'"));
		pageResult = samplingDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Sampling>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<Map<?, Object>> poList2mapList(List<Sampling> list) throws GlobalException {
		List<Map<?, Object>> tempList = new ArrayList<Map<?, Object>>();
		for (Sampling p : list) {
			tempList.add(po2map(p));
		}
		return tempList;
	}

	@Override
	public List<SamplingVo> listByTaskId(String taskId) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel=" + Po.N);
		hql.append(" AND task.id ='" + taskId + "'");
		hql.append(" AND type <>'" + Sampling.SAMP_TYPE_XN + "'");
		hql.append(" ORDER BY sampCode asc");
		List<Sampling> sampList = samplingDao.list(hql.toString());
		List<SamplingVo> voList = toVoList(sampList);
		return voList;
	}

	@Override
	public List<SamplingVo> listByTaskId(String taskId, String type) throws GlobalException {
		StringBuffer hql = new StringBuffer("FROM " + samplingDao.getEntityName(Sampling.class) + " WHERE isDel=" + Po.N);
		hql.append(" AND task.id ='" + taskId + "'");
		hql.append(" AND type ='" + type + "'");
		hql.append(" ORDER BY sampCode asc");
		List<Sampling> sampList = samplingDao.list(hql.toString());
		List<SamplingVo> voList = toVoList(sampList);
		return voList;
	}

	/**
	 * 只能采样安排和现场采样环节删除使用
	 * 其他删除不能真删除
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean deleteSamp(String sampId) throws GlobalException {
		Sampling samp = samplingDao.findById(sampId);
		TaskPoint tp = samp.getPoint();
		delItem(samp);
		samplingDao.delete(samp);
		List l = samplingDao.queryBySql("select distinct(p) from " + tablePrefix + samplingDao.getEntityName(Sampling.class) + " WHERE is_del='" + Po.N
				+ "' AND id<>'" + samp.getId() + "' AND point_id='" + tp.getId() + "'");
		if (null != l) {
			tp.setPc(l.size());
		} else {
			tp.setPc(0);
		}
		//更新点位样品数量
		String sql="select count(id) from "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND zkType='"+Sampling.SAMP_TYPE_PT+"' AND point.id = '"+tp.getId()+"' ";
		Object num=samplingDao.query(sql).getSingleResult();
		tp.setSampNum(Integer.valueOf(num.toString()));
		 
		sql="select count(id) from "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND zkType='"+Sampling.SAMP_TYPE_PX_X+"' AND point.id = '"+tp.getId()+"' ";
		num=samplingDao.query(sql).getSingleResult();
		tp.setPxNum(Integer.valueOf(num.toString()));
			
		sql="select count(id) from "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND zkType='"+Sampling.SAMP_TYPE_KB+"' AND point.id = '"+tp.getId()+"' ";
		num=samplingDao.query(sql).getSingleResult();
		tp.setZkNum(Integer.valueOf(num.toString()));
		taskPointDao.update(tp);
		uptCydNum(tp.getTask());
		return true;
	}
	/**
	 * 删除现场项目结果信息
	 * @param cyd
	 */
	public void delItem(Sampling samp) {
		List<TestResult> trList=testResultDao.listBySampId(samp.getId());
		if(null!=trList) {
			for (TestResult tr : trList) {
				TestItem it=tr.getIt();
				testResultDao.delete(tr);
				//判断该项目下是否还有结果信息，没有删除
				List<TestResult> subList=testResultDao.listByItId(it.getId());
				if(subList==null||subList.size()==0) {
					TaskItem tim=it.getTim();
					testItemDao.delete(it);
					//判断该项目任务下是否有项目，没有删除该任务
					List<TestItem> itList=testItemDao.listByTimId(tim.getId());
					if(null==itList||itList.size()==0) {
						taskItemDao.delete(tim);
					}
				}
			}
		}
	}
	/**
	 * 新增样品
	 * type 为空 普通样 1 为 现场平行样 3为全程序空白
	 * 空白样 p 为空
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addSamp(String pointId, String type,String ids) throws GlobalException {
		TaskPoint tp= taskPointDao.findById(pointId);
		int ptSort = taskPointDao.getSort4Code(tp.getTask().getId(), tp.getId(), tp.getSampTypeId());
		if(StrUtils.isBlankOrNull(type)) {//普通样
			String p="";
			int maxp=1;
			tp.setPc(tp.getPc()+1);
			List<String> l=samplingDao.queryBySql("select max(p) from "+tablePrefix+samplingDao.getEntityName(Sampling.class)+" WHERE is_del='"+Po.N+"' AND point_id = '"+pointId+"' AND zk_type='"+Sampling.SAMP_TYPE_PT+"'");
			if(l!=null&&l.size()>0&&l.get(0)!=null) {
				try {
					maxp=Integer.valueOf(l.get(0));
					p=String.valueOf(maxp+1);
				} catch (NumberFormatException e) {
					p=String.valueOf(tp.getPc()+1);
				}
			}
			String jpql="from "+samplingDao.getEntityName(Sampling.class)+" where isDel='"+Po.N+"' and p='"+maxp+"' and point.id='"+pointId+"' AND zkType='"+Sampling.SAMP_TYPE_PT+"' order by sort asc";
			if(!tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
				//职卫 现场项目的样品只留一个批次，所以不复制
				jpql="from "+samplingDao.getEntityName(Sampling.class)+" where isDel='"+Po.N+"' and p='"+maxp+"' and type='"+Sampling.SAMP_TYPE_PT+"' AND zkType='"+Sampling.SAMP_TYPE_PT+"' and point.id='"+pointId+"' order by sort asc";
			}
			List<Sampling> sampList=samplingDao.list(jpql);
			for (Sampling sampling : sampList) {
				Sampling samp=new Sampling();
				samp.setTask(tp.getTask());
				samp.setCust(tp.getTask().getCust());
				samp.setPoint(tp);
				samp.setCyd(sampling.getCyd());
				samp.setSampType(tp.getSampType());
				samp.setPointName(tp.getPointName());
				samp.setPointCode(tp.getPointCode());
				samp.setSampTypeId(tp.getSampTypeId());
				samp.setSampTypeName(tp.getSampTypeName());
				samp.setCyDate(sampling.getCyDate());
				samp.setItemIds(sampling.getItemIds());
				samp.setItemNames(sampling.getItemNames());
				samp.setCode(sampling.getCode());
				samp.setLy(Constants.F);
				samp.setSort(sampling.getSort());
				samp.setP(p);
				samp.setSampName(sampling.getSampName());
				samp.setType(sampling.getType());
				samp.setZkType(Sampling.SAMP_TYPE_PT);
				SampRecord record=new SampRecord();
				sampRecordDao.add(record);
				samp.setRecord(record);
				if(tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
					samp.setSampCode(samplingDao.createSampCodeHj(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP()));
				}else {
					samp.setSampCode(samplingDao.createSampCodeZw(samp.getType(), samp.getSampType(), samp.getTask().getReportNo(), samp.getItemIds(),
							samp.getPoint().getSort(), samp.getP()));
				}
				samplingDao.add(samp);
			}
			tp.setSampNum(tp.getSampNum()+sampList.size());
		}else if(type.equals(Sampling.SAMP_TYPE_PX_X)) {
			List<Item> itemList=itemDao.listByIds(ids);
			int n=0;
			if(itemList!=null) {
				for (Item item : itemList) {
					Sampling samp=new Sampling();
					samp.setTask(tp.getTask());
					samp.setCust(tp.getTask().getCust());
					samp.setPoint(tp);
					samp.setSampType(tp.getSampType());
					samp.setPointName(tp.getPointName());
					samp.setPointCode(tp.getPointCode());
					samp.setSampTypeId(tp.getSampTypeId());
					samp.setSampTypeName(tp.getSampTypeName());
					samp.setCyDate(tp.getTask().getCyDate());
					samp.setItemIds(item.getId());
					samp.setItemNames(item.getName());
					samp.setCode(item.getCode());
					samp.setLy(Constants.F);
					samp.setSort(samplingDao.findMaxSort(tp.getTask().getId()));
					String jpql="select count(id) from "+tablePrefix+samplingDao.getEntityName(Sampling.class)+" where is_del='"+Po.N+"' and zk_type='"+Sampling.SAMP_TYPE_PX_X+"' AND item_ids like '%"+item.getId()+"%' and point_id='"+pointId+"' ";
					Object num=samplingDao.queryBysql(jpql).getSingleResult();
					if(num==null) {
						samp.setP(String.valueOf(1));
					}else {
						int i=Integer.valueOf(num.toString())+1;
						samp.setP(String.valueOf(i));
					}
					samp.setSampName("现场平行样");
					if(item.getIsNow().equals(Constants.S)) {
						samp.setType(Sampling.SAMP_TYPE_XN);
					}else {
						samp.setType(Sampling.SAMP_TYPE_PT);
					}
					samp.setZkType(Sampling.SAMP_TYPE_PX_X);
					samp.setCyd(getCyd(tp.getId(), item.getName(), samp.getSampTypeName(), samp.getCyDate()));
					SampRecord record=new SampRecord();
					sampRecordDao.add(record);
					samp.setRecord(record);
					if(tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
						samp.setSampCode(samplingDao.createSampCodeZk(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP(),
								samp.getZkType()));
						
					}
					samplingDao.add(samp);
					n++;
				}
			}
			tp.setPxNum(tp.getPxNum()+n);
		} else if(type.equals(Sampling.SAMP_TYPE_KB)){//全程序空白样
			List<Item> itemList=itemDao.listByIds(ids);
			int n=0;
			if(itemList!=null) {
				for (Item item : itemList) {
					Sampling samp=new Sampling();
					samp.setTask(tp.getTask());
					samp.setCust(tp.getTask().getCust());
					samp.setPoint(tp);
					samp.setSampType(tp.getSampType());
					samp.setPointName(tp.getPointName());
					samp.setPointCode(tp.getPointCode());
					samp.setSampTypeId(tp.getSampTypeId());
					samp.setSampTypeName(tp.getSampTypeName());
					samp.setCyDate(tp.getTask().getCyDate());
					samp.setItemIds(item.getId());
					samp.setItemNames(item.getName());
					samp.setCode(item.getCode());
					samp.setLy(Constants.F);
					samp.setSort(samplingDao.findMaxSort(tp.getTask().getId()));
					String jpql="select count(id) from "+tablePrefix+samplingDao.getEntityName(Sampling.class)+" where is_del='"+Po.N+"' and zk_type='"+Sampling.SAMP_TYPE_KB+"' AND item_ids like '%"+item.getId()+"%' and point_id='"+pointId+"' ";
					Object num=samplingDao.queryBysql(jpql).getSingleResult();
					if(num==null) {
						samp.setP(String.valueOf(1));
					}else {
						int i=Integer.valueOf(num.toString())+1;
						samp.setP(String.valueOf(i));
					}
					samp.setSampName("全程序空白样");
					if(item.getIsNow().equals(Constants.S)) {
						samp.setType(Sampling.SAMP_TYPE_XN);
					}else {
						samp.setType(Sampling.SAMP_TYPE_PT);
					}
					samp.setZkType(Sampling.SAMP_TYPE_KB);
					samp.setCyd(getCyd(tp.getId(), item.getName(), samp.getSampTypeName(), samp.getCyDate()));
					SampRecord record=new SampRecord();
					sampRecordDao.add(record);
					samp.setRecord(record);
					if(tp.getSampType().equals(EnumBus.SAMP_TYPE_HJ)) {
						samp.setSampCode(samplingDao.createSampCodeZk(samp.getTask().getReportNo(), samp.getSampTypeId(), samp.getItemIds(), ptSort, samp.getP(),
								samp.getZkType()));
						
					}
					samplingDao.add(samp);
					n++;
				}
			}
			tp.setZkNum(tp.getZkNum()+n);
		}else {
			return false;
		}
		taskPointDao.update(tp);
		uptCydNum(tp.getTask());
		return true;
	}
	public SampCyd getCyd(String pointId,String itemName,String sampTypeName,String cyDate) {
		EnumCyd	cyd=EnumCyd.getCyd4Hj(sampTypeName, itemName);
		//SampCyd sampCyd=sampCydDao.find4Task(taskId, cyd.getCode(),cyDate);
		SampCyd sampCyd=sampCydDao.find4Auto(pointId,cyd.getCode(),cyDate);
		return sampCyd;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean delSamp(String pointId, String type) throws GlobalException {
		TaskPoint tp= taskPointDao.findById(pointId);
		//删除样品
		if(StrUtils.isBlankOrNull(type)) {//删除普通样
			List<String> l=samplingDao.queryBySql("select max(p) from "+tablePrefix+samplingDao.getEntityName(Sampling.class)+" WHERE is_del='"+Po.N+"' AND zk_type='"+Sampling.SAMP_TYPE_PT+"' AND point_id = '"+pointId+"' ");
			if(l!=null&&l.size()>0&&l.get(0)!=null) {
				int maxp=Integer.valueOf(l.get(0));
				String hql="from "+samplingDao.getEntityName(Sampling.class)+" WHERE zkType='"+Sampling.SAMP_TYPE_PT+"' AND p='"+maxp+"' AND point.id = '"+pointId+"'  ";
				List<Sampling> samList=samplingDao.list(hql);
				if(null!=samList) {
					for (Sampling samp: samList) {
						delItem(samp);
						samplingDao.delete(samp);
					}
				}
			}else {
				return false;
			}
		}else if(type.equals(Sampling.SAMP_TYPE_KB)) {//删除空白样
			String hql="from "+samplingDao.getEntityName(Sampling.class)+" WHERE zkType='"+Sampling.SAMP_TYPE_KB+"' AND point.id = '"+pointId+"'  ";
			List<Sampling> samList=samplingDao.list(hql);
			if(null!=samList) {
				for (Sampling samp: samList) {
					delItem(samp);
					samplingDao.delete(samp);
				}
			}
		}else if(type.equals(Sampling.SAMP_TYPE_PX_X)) {//删除平行样
			List<String> l=samplingDao.queryBySql("select max(p) from "+tablePrefix+samplingDao.getEntityName(Sampling.class)+" WHERE is_del='"+Po.N+"' AND zk_type='"+Sampling.SAMP_TYPE_PX_X+"' AND point_id = '"+pointId+"' ");
			if(l!=null&&l.size()>0&&l.get(0)!=null) {
				int maxp=Integer.valueOf(l.get(0));
				String hql="from "+samplingDao.getEntityName(Sampling.class)+" WHERE zkType='"+Sampling.SAMP_TYPE_PX_X+"' AND p='"+maxp+"' AND point.id = '"+pointId+"'  ";
				List<Sampling> samList=samplingDao.list(hql);
				if(null!=samList) {
					for (Sampling samp: samList) {
						delItem(samp);
						samplingDao.delete(samp);
					}
				}
			}else {
				return false;
			}	
		}else {
			return false;
		}
		//更新点位样品数量
		String sql="select count(id) from "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND zkType='"+Sampling.SAMP_TYPE_PT+"' AND point.id = '"+pointId+"' ";
		if(type.equals(Sampling.SAMP_TYPE_PX_X)) {
			sql="select count(id) from "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND zkType='"+Sampling.SAMP_TYPE_PX_X+"' AND point.id = '"+pointId+"' ";
			Object num=samplingDao.query(sql).getSingleResult();
			tp.setPxNum(Integer.valueOf(num.toString()));
		}else if(type.equals(Sampling.SAMP_TYPE_KB)) {
			sql="select count(id) from "+samplingDao.getEntityName(Sampling.class)+" WHERE isDel='"+Po.N+"' AND zkType='"+Sampling.SAMP_TYPE_KB+"' AND point.id = '"+pointId+"' ";
			Object num=samplingDao.query(sql).getSingleResult();
			tp.setZkNum(Integer.valueOf(num.toString()));
		}else {
			Object num=samplingDao.query(sql).getSingleResult();
			tp.setSampNum(Integer.valueOf(num.toString()));
		}
		uptCydNum(tp.getTask());
		//更新频次
		@SuppressWarnings("rawtypes")
		List l = samplingDao.queryBySql("select distinct(p) from " + tablePrefix + samplingDao.getEntityName(Sampling.class) + " WHERE is_del='" + Po.N+ "' AND zk_type='"+Sampling.SAMP_TYPE_PT+"' AND point_id='" + tp.getId() + "'");
		if (null != l) {
			tp.setPc(l.size());
		} else {
			tp.setPc(0);
		}
		taskPointDao.update(tp);
		return true;
	}
	public void uptCydNum(Task task) {
		//更新采样单
		List<SampCyd> cydList=sampCydDao.listByTaskId(task.getId());
		if(null!=cydList) {
			for (SampCyd cyd : cydList) {
				List<Sampling> sampList=samplingDao.listByCyd(cyd.getId());
				if(sampList!=null&&sampList.size()>0) {
					cyd.setSampNum(sampList.size());
					sampCydDao.update(cyd);
				}else {
					//采样单无样品，删除采样单
					sampCydDao.delete(cyd);
				}
			}
		}
	}
	@Override
	public boolean copySamp(String sampId) throws GlobalException {
		Sampling samp = samplingDao.findById(sampId);
		Sampling newSamp = new Sampling();
		newSamp.setTask(samp.getTask());
		newSamp.setCust(samp.getCust());
		newSamp.setPoint(samp.getPoint());
		newSamp.setSampCode(null);
		newSamp.setSampTypeId(samp.getSampTypeId());
		newSamp.setSampTypeName(samp.getSampTypeName());
		newSamp.setSampType(samp.getSampType());
		newSamp.setSampName(samp.getSampName());
		newSamp.setType(samp.getType());
		newSamp.setZkType(samp.getZkType());
		newSamp.setPointName(samp.getPointName());
		newSamp.setPointCode(samp.getPointCode());
		newSamp.setItemIds(samp.getItemIds());
		newSamp.setItemNames(samp.getItemNames());
		newSamp.setCode(samp.getCode());
		newSamp.setContainer(samp.getContainer());
		newSamp.setContainerId(samp.getContainerId());
		newSamp.setOrgId(samp.getOrgId());
		newSamp.setOrgName(samp.getOrgName());
		newSamp.setDeptId(samp.getDeptId());
		newSamp.setDeptName(samp.getDeptName());
		newSamp.setReciveDate(samp.getReciveDate());
		newSamp.setReciveUser(samp.getReciveUser());
		newSamp.setReciveUserId(samp.getReciveUserId());
		newSamp.setStatus(null);
		newSamp.setCcfl(Constants.F);
		newSamp.onAdd();
		SampRecord record=new SampRecord();
		BeanUtils.copyProperties(samp.getRecord(), record, new String[] {"id"});
		sampRecordDao.add(record);
		samp.setRecord(record);
		samplingDao.add(newSamp);
		return true;
	}

	@Override
	public void update4Deal(SamplingVo v) throws GlobalException {
		List<Sampling> sampList = samplingDao.listByIds(v.getIds());
		for (Sampling sampling : sampList) {
			sampling.setDealUser(v.getDealUser());
			sampling.setDealDate(v.getDealDate());
			sampling.setDealRemark(v.getDealRemark());
			sampling.setDealRequest(v.getDealRequest());
			sampling.setStatus(Sampling.ST_30);
			samplingDao.update(sampling);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SamplingVo> listSampsByTaskId(String taskId) throws GlobalException {
		List<SamplingVo> samplist = new ArrayList<>();
		String sql = "select samp_code,item_names,num,container,status,tjj,ly ,save_request,bcqx from v_bus_sampling where task_id = '" + taskId + "'";
		List<Object[]> itemList = samplingDao.queryBySql(sql);
		if (null != itemList) {
			for (Object[] obj : itemList) {
				SamplingVo samp = new SamplingVo();
				samp.setSampCode(String.valueOf((obj[0] == null || obj[0] == "null") ? "" : obj[0]));
				samp.setItemNames(String.valueOf((obj[1] == null || obj[1] == "null") ? "" : obj[1]));
				samp.setNum(String.valueOf((obj[2] == null || obj[2] == "null") ? "" : obj[2]));
				samp.setContainer(String.valueOf((obj[3] == null || obj[3] == "null") ? "" : obj[3]));
				samp.setStatus(String.valueOf((obj[4] == null || obj[4] == "null") ? "" : obj[4]));
				samp.setTjj(String.valueOf((obj[5] == null || obj[5] == "null") ? "" : obj[5]));
				samp.setLy(String.valueOf((obj[6] == null || obj[6] == "null") ? "" : obj[6]));
				samp.setSaveRequest(String.valueOf((obj[7] == null || obj[7] == "null") ? "" : obj[7]));
				samp.setBcqx(String.valueOf((obj[8] == null || obj[8] == "null") ? "" : obj[8]));
				samplist.add(samp);
			}
		}
		return samplist;
	}
}
