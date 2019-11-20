package cn.demi.qlt.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.qlt.po.NsItem;

public interface INsItemDao extends IBaseDao<NsItem> {
	/**
	 * 删除要素下所有内容
	 * @param ysId 要素Id
	 */
	public void delete4Ct(String ysId);
	/**
	 * 获取要素
	 * @param pId 父Id
	 * @return
	 */
	public List<NsItem> listByItemId(String pId);
}

