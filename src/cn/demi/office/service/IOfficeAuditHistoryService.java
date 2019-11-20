package cn.demi.office.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.demi.office.vo.OfficeAuditHistoryVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;

@Transactional
public interface IOfficeAuditHistoryService extends IBaseService<OfficeAuditHistoryVo> {
	public List<OfficeAuditHistoryVo> listByCmId(String id) throws GlobalException;
}
