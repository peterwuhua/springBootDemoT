package cn.demi.bus.file.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.bus.file.vo.ArchiveVo;

@Transactional
public interface IArchiveAuditService extends IBaseService<ArchiveVo> {
}
