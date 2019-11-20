package cn.demi.app.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.regexp.internal.recompile;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.demi.app.sys.service.IAppWorkPlanService;
import cn.demi.app.sys.vo.AppWorkPlanVo;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.office.dao.IWorkPlanDao;
import cn.demi.office.po.WorkPlan;
import cn.demi.office.vo.WorkPlanVo;

@Service("app.workPlanService")
public class AppWorkPlanServiceImpl extends BaseServiceImpl<WorkPlanVo, WorkPlan> implements IAppWorkPlanService {
	@Autowired
	private IWorkPlanDao workPlanDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<AppWorkPlanVo> listDate(WorkPlanVo v) {
		String hql = "SELECT id,type,title,content,urgentLevel,startTime,endTime FROM "
				+ workPlanDao.getEntityName(WorkPlan.class) + " WHERE isDel=" + Po.N + " AND startTime like'%"
				+ v.getSelectdate() + "%' AND userId='" + v.getUserId() + "' and urgentLevel like '%"
				+ v.getUrgentLevel() + "%'";
		List list = workPlanDao.query(hql).getResultList();
		List<AppWorkPlanVo> workPlans = new ArrayList<>();
		for (Object o : list) {
			Object[] objects = (Object[]) o;
			AppWorkPlanVo workPlan = new AppWorkPlanVo();
			workPlan.setId(objects[0] == null ? "" : objects[0].toString());
			workPlan.setType(objects[1] == null ? "" : objects[1].toString());
			workPlan.setTitle(objects[2] == null ? "" : objects[2].toString());
			workPlan.setContent(objects[3] == null ? "" : objects[3].toString());
			workPlan.setUrgentLevel(objects[4] == null ? "" : objects[4].toString());
			workPlan.setStartTime(objects[5] == null ? "" : objects[5].toString());
			workPlan.setEndTime(objects[6] == null ? "" : objects[6].toString());
			workPlans.add(workPlan);
		}
		return workPlans;
	}
	@Override
	public String addWorkPlan(WorkPlanVo v,ObjVo objVo) throws GlobalException {
		WorkPlan p = new WorkPlan();
		int n=(int) DateUtils.getIntevalDays(v.getStartDate(), v.getEndDate());
		if(n<=1) {
			p.setDateStr(v.getStartDate().substring(0, 10));
		}else {
			String dateStr="";
			for (int i=1;i<n;i++) {
				dateStr+=DateUtils.getNextDate(v.getStartDate(), i)+",";
			}
			p.setDateStr(dateStr);
		}
		p.setContent(v.getContent());	  
		p.setStartTime(v.getStartDate());
		p.setEndTime(v.getEndDate());
		p.setOrgId(objVo.getDepId());
		p.setOrgName(objVo.getOrgName());
		p.setTitle(v.getTitle());
		p.setType(v.getType());
		p.setUrgentLevel(v.getUrgentLevel());
		p.setUserId(objVo.getUserId());
		p.setUserName(objVo.getName());
		try {
			workPlanDao.add(p);
			v.setId(p.getId());
			return null;
		} catch (Exception e) {
			return e.toString();
		}
		
		
	}
	
}
