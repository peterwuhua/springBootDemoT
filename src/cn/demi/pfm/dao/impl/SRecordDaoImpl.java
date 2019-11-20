package cn.demi.pfm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.utils.DateUtils;
import cn.demi.pfm.dao.ISRecordDao;
import cn.demi.pfm.po.SRecord;
import cn.demi.pfm.po.Set;

@Repository("pfm.recordDao")
public class SRecordDaoImpl extends BaseDaoImpl<SRecord> implements ISRecordDao {

	@SuppressWarnings("unchecked")
	@Override
	public void insert(String no,String startTime,String code) {
		String hql="FROM "+getEntityName(Set.class)+" WHERE isDel='"+Po.N+"' AND code ='"+code+"' ORDER BY sort DESC";
		List<Set> list=super.query(hql).getResultList();
		if(list!=null&&list.size()>0) {
			Set set=list.get(0);
			SRecord sr=new SRecord();
			sr.setOrgId(getCurrent().getDepId());
			sr.setOrgName(getCurrent().getDepName());
			sr.setUserId(getCurrent().getUserId());
			sr.setUserName(getCurrent().getUserName());
			sr.setRecordTime(DateUtils.getCurrDateTimeStr());
			sr.setCode(code);
			sr.setProject(set.getWorkName());
			sr.setValue(set.getValue());
			try {
				long t=DateUtils.getIntevalHours(startTime, DateUtils.getCurrDateTimeStr());
				if(t>set.getTime()) {
					String m="任务 "+no+" 在 "+set.getWorkName()+" 环节要求耗时不得超过"+set.getTime()+"小时，实际用时"+t+"小时";
					sr.setScoreExplain(m);
					super.save(sr);
				}
			} catch (Exception e) {
				log.error("插入考核记录传入开始日期异常",e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(String msg, String code) {
		String hql="FROM "+getEntityName(Set.class)+" WHERE isDel='"+Po.N+"' AND code ='"+code+"' ORDER BY sort DESC";
		List<Set> list=super.query(hql).getResultList();
		if(list!=null&&list.size()>0) {
			Set set=list.get(0);
			SRecord sr=new SRecord();
			sr.setOrgId(getCurrent().getDepId());
			sr.setOrgName(getCurrent().getDepName());
			sr.setUserId(getCurrent().getUserId());
			sr.setUserName(getCurrent().getUserName());
			sr.setRecordTime(DateUtils.getCurrDateTimeStr());
			sr.setCode(code);
			sr.setProject(set.getWorkName());
			sr.setValue(set.getValue());
			sr.setScoreExplain(msg);
			super.save(sr);
		}
	}
}
