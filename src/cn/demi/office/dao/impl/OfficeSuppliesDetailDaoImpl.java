package cn.demi.office.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.office.dao.IOfficeSuppliesDetailDao;
import cn.demi.office.po.OfficeSuppliesDetail;

@Repository("office.supplies_detailDao")
public class OfficeSuppliesDetailDaoImpl extends BaseDaoImpl<OfficeSuppliesDetail> implements IOfficeSuppliesDetailDao {
}
