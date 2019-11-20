package cn.demi.cus.fee.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.cus.fee.dao.IFeeDao;
import cn.demi.cus.fee.po.Fee;

@Repository("cus.feeDao")
public class FeeDaoImpl extends BaseDaoImpl<Fee> implements IFeeDao {
}
