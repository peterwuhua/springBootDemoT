package cn.demi.office.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.office.dao.IWorkPlanDao;
import cn.demi.office.po.WorkPlan;
import cn.demi.office.service.IWorkPlanService;
import cn.demi.office.vo.WorkPlanVo;

@Service("office.workPlanService")
public class WorkPlanServiceImpl extends BaseServiceImpl<WorkPlanVo,WorkPlan> implements
		IWorkPlanService {
	@Autowired
	private IWorkPlanDao workPlanDao;
	
	@Override
	public WorkPlanVo findById(String id) throws GlobalException {
		WorkPlan po = workPlanDao.findById(id);
		WorkPlanVo vo=new WorkPlanVo();
		vo=vo.toVo(po);
		return vo;
	}
	
	@Override
	public void add(WorkPlanVo v) throws GlobalException {
		WorkPlan p = vo2Po(v);
		int n=(int) DateUtils.getIntevalDays(v.getStartDate(), v.getEndDate());
		if(n<=1) {
			p.setDateStr(v.getStartDate());
		}else {
			String dateStr="";
			for (int i=1;i<n;i++) {
				dateStr+=DateUtils.getNextDate(v.getStartDate(), i)+",";
			}
			p.setDateStr(dateStr);
		}
		p.setStartTime(v.getStartDate()+" "+v.getStartTime());
		p.setEndTime(v.getEndDate()+" "+v.getEndTime());
		p.setOrgId(getCurrent().getDepId());
		p.setOrgName(getCurrent().getDepName());
		workPlanDao.add(p);
		v.setId(p.getId());
	}
	@Override
	public void update(WorkPlanVo v) throws GlobalException {
		WorkPlan p = workPlanDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		p.setStartTime(v.getStartDate()+" "+v.getStartTime());
		p.setEndTime(v.getEndDate()+" "+v.getEndTime());
		int n=(int) DateUtils.getIntevalDays(v.getStartDate(), v.getEndDate());
		if(n<=1) {
			p.setDateStr(v.getStartDate());
		}else {
			String dateStr="";
			for (int i=1;i<n;i++) {
				dateStr+=DateUtils.getNextDate(v.getStartDate(), i)+",";
			}
			p.setDateStr(dateStr);
		}
		workPlanDao.update(p);
	}
	@Override
	public List<WorkPlanVo> list(WorkPlanVo v) throws GlobalException {
		List<QueryCondition> conditions =new ArrayList<QueryCondition>();
		conditions.add(new QueryCondition("isDel",QueryCondition.EQ,Po.N));
		conditions.add(new QueryCondition("userId",QueryCondition.EQ,getCurrent().getAccountId()));
		 
		if(!StrUtils.isBlankOrNull(v.getSelectdate())) {
			conditions.add(new QueryCondition("dateStr",QueryCondition.AK,v.getSelectdate()));
		}
		List<WorkPlan> list = workPlanDao.list(conditions);
		List<WorkPlanVo> vList = new ArrayList<WorkPlanVo>();
		if(null!=list) {
			for (WorkPlan plan : list) {
				WorkPlanVo vo=new WorkPlanVo();
				vo=vo.toVo(plan);
				vList.add(vo);
			}
		}
		return vList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> listDate(WorkPlanVo v) {
		String hql="SELECT dateStr FROM "+workPlanDao.getEntityName(WorkPlan.class)+" WHERE isDel="+Po.N+" AND startTime like'"+v.getSelectdate()+"%' AND userId='"+getCurrent().getAccountId()+"'";
		List<String> lst=workPlanDao.query(hql).getResultList();
		Set<String> dateSet=new HashSet<String>();
		if(lst!=null) {
			for (String dateStr : lst) {
				if(dateStr.contains(",")) {
					String dates[]=dateStr.split(",");
					for (String date : dates) {
						if(!StrUtils.isBlankOrNull(date)) {
							dateSet.add(date);
						}
					}
				}else {
					dateSet.add(dateStr);
				}
			}
		}
		return new ArrayList<>(dateSet);
	}
	@Override
	public List<QueryCondition> toQueryList(WorkPlanVo v) throws GlobalException {
		List<QueryCondition> queryList = new ArrayList<QueryCondition>();
		queryList.add(new QueryCondition("userId",QueryCondition.EQ,getCurrent().getAccountId()));
		return queryList;
	}
}
