package cn.demi.qlt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.qlt.po.GsRecord;

public interface IGsRecordDao extends IBaseDao<GsRecord> {
	/**
	 * 获取记录详情集合
	 * @param nsId
	 * @return
	 */
	public List<GsRecord> listByGsId(String gsId);
}

