package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.SupplierEvaluateVo;

@Transactional
public interface ISupplierEvaluateService extends IBaseService<SupplierEvaluateVo> {

	List<SupplierEvaluateVo> supplierEvaluateListBySupId(String id) throws GlobalException;
}
