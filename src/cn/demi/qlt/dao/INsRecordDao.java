package cn.demi.qlt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.qlt.po.NsRecord;

public interface INsRecordDao extends IBaseDao<NsRecord> {
	
	public NsRecord findByNsId(String nsId,int month);
	
	public List<NsRecord> findByNsId(String nsId);
}

