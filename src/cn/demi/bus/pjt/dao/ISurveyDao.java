package cn.demi.bus.pjt.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.bus.pjt.po.Survey;

public interface ISurveyDao extends IBaseDao<Survey> {
	
	Survey findByProjectId(String projectId);
}

