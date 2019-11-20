package cn.demi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.demi.res.po.SupplierEvaluate;

public interface ISupplierEvaluateDao extends IBaseDao<SupplierEvaluate> {

	List<SupplierEvaluate> supplierEvaluateListBySupId(String id);
}

