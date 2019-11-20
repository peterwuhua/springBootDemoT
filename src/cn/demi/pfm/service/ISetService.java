package cn.demi.pfm.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.pfm.vo.SetVo;

@Transactional
public interface ISetService extends IBaseService<SetVo> {
}
