package cn.demi.bus.pjt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.bus.pg.po.ProgressLog;
import cn.demi.bus.pjt.dao.ISurveyDao;
import cn.demi.bus.pjt.po.Survey;
/**
 * 现场踏勘 数据持久层
 * @author QuJunLong
 */
@Repository("bus.surveyDao")
public class SurveyDaoImpl extends BaseDaoImpl<Survey> implements ISurveyDao {

	@Override
	public Survey findByProjectId(String projectId) {
		StringBuffer hql=new StringBuffer("FROM "+getEntityName(Survey.class)+" WHERE isDel="+ProgressLog.N);
		hql.append(" AND project.id='"+projectId+"'");
		List<Survey> lst=super.list(hql.toString());
		if(lst!=null&&lst.size()>0) {
			return lst.get(0);
		}else {
			return null;
		}
	}
}
