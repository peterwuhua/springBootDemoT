package cn.demi.app.sys.dao.impl;

import org.springframework.stereotype.Repository;
import cn.demi.app.sys.dao.IBillingInfoDao;
import cn.demi.app.sys.po.BillingInfo;
import cn.core.framework.common.dao.BaseDaoImpl;

@Repository("office.billingInfoDao")
public class BillingInfoDaoImpl extends BaseDaoImpl<BillingInfo> implements IBillingInfoDao {
}
