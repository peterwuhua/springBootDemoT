package cn.demi.qlt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.qlt.po.NsDetail;

public interface INsDetailDao extends IBaseDao<NsDetail> {
	/**
	 * 获取计划详情
	 * @param nsId
	 * @return
	 */
	public List<NsDetail> listByNsId(String nsId);
}

