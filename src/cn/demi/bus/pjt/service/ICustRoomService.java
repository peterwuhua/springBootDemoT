package cn.demi.bus.pjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.bus.pjt.vo.CustRoomVo;

@Transactional
public interface ICustRoomService extends IBaseService<CustRoomVo> {
	
}
