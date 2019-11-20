package cn.demi.zk.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

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
import cn.core.framework.constant.EunmZkTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.zk.dao.IZkItemTestDao;
import cn.demi.zk.dao.IZkProgressLogDao;
import cn.demi.zk.dao.IZkSamplingDao;
import cn.demi.zk.dao.IZkTaskDao;
import cn.demi.zk.po.ZkItemTest;
import cn.demi.zk.po.ZkProgressLog;
import cn.demi.zk.po.ZkSampling;
import cn.demi.zk.po.ZkTask;
import cn.demi.zk.service.IZkTaskService;
import cn.demi.zk.vo.ZkItemTestVo;
import cn.demi.zk.vo.ZkSamplingVo;
import cn.demi.zk.vo.ZkTaskVo;

@Service("zk.taskService")
public class ZkTaskServiceImpl extends BaseServiceImpl<ZkTaskVo,ZkTask> implements
		IZkTaskService {
	@Autowired
	private IZkTaskDao zkTaskDao;
	@Autowired
	private IZkSamplingDao zkSamplingDao;
	@Autowired
	private IZkProgressLogDao progressLogDao;
	@Autowired
	private IZkItemTestDao itemTestDao;
	@Override
	public void add(ZkTaskVo v) throws GlobalException {
		ZkTask p = vo2Po(v);
		p.setNo(createCode());
		zkTaskDao.add(p);
		v.setId(p.getId());
		saveDetail(p,v);
	}
	@Override
	public void update(ZkTaskVo v) throws GlobalException {
		ZkTask p = zkTaskDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		zkTaskDao.update(p);
		saveDetail(p,v);
	}
	public void saveDetail(ZkTask p,ZkTaskVo v) {
		zkSamplingDao.deleteAll(zkSamplingDao.findByProperty("task.id", p.getId()));
		if(v.getSampList()!=null) {
			String objNames="";
			String objIds="";
			String itemIds="";
			String itemNames="";
			for (ZkSamplingVo sampVo : v.getSampList()) {
				ZkSampling samp=new ZkSampling();
				samp=samp.toPo(sampVo, samp);
				samp.setTask(p);
				samp.setSampCode(createSampCode());
				zkSamplingDao.add(samp);
				if(!objIds.contains(sampVo.getUserIds())) {
					objIds+=sampVo.getUserIds()+",";
					objNames+=sampVo.getUserNames()+",";
				}
				if(!StrUtils.isBlankOrNull(sampVo.getItemIds())) {
					String ids[]=sampVo.getItemIds().split(",");
					String names[]=sampVo.getItemNames().split(",");
					for (int i=0;i<ids.length;i++) {
						if(ids[i].trim().length()>0&&!itemIds.contains(ids[i])) {
							itemIds+=ids[i]+",";
							itemNames+=names[i]+",";
						}
					}
				}
			}
			if(objIds.endsWith(",")) {
				objIds=objIds.substring(0, objIds.length()-1);
				objNames=objNames.substring(0, objNames.length()-1);
			}
			if(itemIds.endsWith(",")) {
				itemIds=itemIds.substring(0, itemIds.length()-1);
				itemNames=itemNames.substring(0, itemNames.length()-1);
			}
			p.setObjNames(objNames);
			p.setObjIds(objIds);
			p.setItemIds(itemIds);
			p.setItemNames(itemNames);
			p.setSampNum(v.getSampList().size()+"");
		}else {
			p.setSampNum("0");
		}
		p.setOrgId(CurrentUtils.getCurrent().getOrgId());
		p.setTitle(v.getYear()+"年"+v.getMonth()+"月份内部质控计划表");
		if(null!=v.getIsCommit()&&v.getIsCommit().equals(Constants.PASS_Y)) {
			p.setStatus(EunmZkTask.TASK_10.getStatus());
			p.setIsBack(Po.N);
			//插入任务进度日志
			progressLogDao.add(p.getId(),EunmZkTask.TASK_00.getStatus(),"提交","计划制定");
		}else {
			p.setStatus(EunmZkTask.TASK_00.getStatus());
		}
		zkTaskDao.update(p);
	}
	public String createSampCode(){
		String flag="MY-";
		flag+=DateUtils.getYear().substring(2)+DateUtils.getMonth();
		String hql="SELECT max(sampCode) FROM "+zkTaskDao.getEntityName(ZkSampling.class)+" WHERE isDel="+Po.N+" AND sampCode like '"+flag+"%'";
		String ls=(String) zkTaskDao.query(hql).getSingleResult();
		String no=flag;
		if(ls==null) {
			no+="01";
		}else {
			ls=ls.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort=0;
			}
			sort++;
			if(sort<10) {
				no+="0"+sort;
			}else {
				no+=sort;
			}
		}
		return no;
	}
	public String createCode(){
		String flag="ZK";
		flag+=DateUtils.getYear()+DateUtils.getMonth();
		String hql="SELECT max(no) FROM "+zkTaskDao.getEntityName(ZkTask.class)+" WHERE isDel="+Po.N+" AND no like '"+flag+"%'";
		String ls=(String) zkTaskDao.query(hql).getSingleResult();
		String no=flag;
		if(ls==null) {
			no+="001";
		}else {
			ls=ls.replace(flag, "");
			int sort;
			try {
				sort = Integer.valueOf(ls);
			} catch (NumberFormatException e) {
				sort=0;
			}
			sort++;
			if(sort<10) {
				no+="00"+sort;
			}else if(sort<100){
				no+="0"+sort;
			}else {
				no+=sort;
			}
		}
		return no;
	}
	@Override
	public ZkTaskVo findById(String id) throws GlobalException {
		ZkTask po= zkTaskDao.findById(id);
		ZkTaskVo vo=po2Vo(po);
		List<ZkSampling> sampList=zkSamplingDao.findByProperty("task.id", po.getId());
		List<ZkSamplingVo> sampVoList= new ArrayList<ZkSamplingVo>();
		if(null!=sampList) {
			for (ZkSampling samp : sampList) {
				ZkSamplingVo sampVo=new ZkSamplingVo();
				sampVo=sampVo.toVo(samp);
				sampVoList.add(sampVo);
			}
		}
		vo.setSampList(sampVoList);
		List<ZkItemTest> pList=itemTestDao.findByProperty("task.id", po.getId());
		List<ZkItemTestVo> voList=new ArrayList<ZkItemTestVo>();
		for (ZkItemTest it : pList) {
			ZkItemTestVo itVo=new ZkItemTestVo();
			itVo=itVo.toVo(it);
			ZkSamplingVo sampVo=new ZkSamplingVo();
			sampVo=sampVo.toVo(it.getSampling());
			itVo.setSamplingVo(sampVo);
			voList.add(itVo);
		}
		vo.setItemList(voList);
		return vo;
	}
	 
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = zkTaskDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ZkTask>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	@Override
	public List<QueryCondition> toQueryList(ZkTaskVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("status='"+v.getStatus()+"'"));
		queryList.add(new QueryCondition("acceptId like '"+CurrentUtils.getCurrent().getAccountId()+"'"));
		return queryList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, ZkTaskVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if(StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx()) ){
			pageResult.setOrder(OrderCondition.ORDER_DESC);
			pageResult.setOrderColumn("lastUpdTime");
		}else{
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> QueryConditionList = getFilterRules(gridVo.getFilters());
		StringBuffer hql=new StringBuffer("FROM "+zkTaskDao.getEntityName(ZkTask.class)+" t,"+zkTaskDao.getEntityName(ZkProgressLog.class)+" log where log.taskId=t.id and t.isDel !="+Po.Y);
		if(QueryConditionList!=null) {
			for (QueryCondition queryCondition : QueryConditionList) {
				query(hql, queryCondition);
			}
		}
		hql.append(" AND log.status='"+v.getStatus()+"' ");
		hql.append(" ORDER BY t."+pageResult.getOrderColumn()+" "+pageResult.getOrder()+"");
		Query query=zkTaskDao.query(hql.toString());
		PageBean pager = new PageBean(query.getResultList().size(), pageResult.getPageBean().getPageSize());
		pager.refresh(pageResult.getPageBean().getCurrentPage());
		List<Object[]> list = zkTaskDao.query(hql.toString()).setFirstResult(pager.getStartRow()).setMaxResults(pager.getPageSize()).getResultList();
		List<ZkTask> taskList=new ArrayList<ZkTask>();
		if(list!=null) {
			for (Object[] objects : list) {
				try {
					Object obj[]=objects;
					ZkTask t=(ZkTask) obj[0];
					ZkProgressLog log=(ZkProgressLog) obj[1];
					t.setProgressLog(log);
					taskList.add(t);
				} catch (Exception e) {
				}
			}
		}
		gridVo.setDatas(poList2mapList(taskList));
		pageResult.setPageBean(pager);
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
	 
	@Override
	public void delete(String... ids) throws GlobalException {
		List<ZkTask> tkList=zkTaskDao.listByIds(ids);
		if(null!=tkList) {
			for (ZkTask task : tkList) {
				List<ZkSampling> sampList=zkSamplingDao.findByProperty("task.id", task.getId());
				if(null!=sampList) {
					zkSamplingDao.deleteAll(sampList);
				}
			}
		}
		zkTaskDao.deleteAll(tkList);
	}
}
