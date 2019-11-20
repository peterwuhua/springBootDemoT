package cn.demi.qlt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.qlt.po.NsRecordDetail;

public interface INsRecordDetailDao extends IBaseDao<NsRecordDetail> {
	
	/**
	 * 获取记录单详情
	 * @param rId 记录单Id
	 * @param pId 详情父Id
	 * @return
	 */
	public List<NsRecordDetail> listByPid(String rId,String pId);
}

