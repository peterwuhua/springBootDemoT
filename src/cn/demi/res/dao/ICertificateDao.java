package cn.demi.res.dao;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.Certificate;

public interface ICertificateDao extends IBaseDao<Certificate> {
	/**
	 * 或某资质证书 有效期
	 * @param userId
	 * @param itemId
	 * @return
	 */
	public Certificate findByItem(String userId, String itemId);
}

