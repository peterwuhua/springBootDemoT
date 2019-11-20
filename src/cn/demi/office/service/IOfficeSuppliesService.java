package cn.demi.office.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.office.vo.OfficeSuppliesVo;

@Transactional
public interface IOfficeSuppliesService extends IBaseService<OfficeSuppliesVo> {
	public void saveBgyp(OfficeSuppliesVo vo) throws GlobalException;
}
