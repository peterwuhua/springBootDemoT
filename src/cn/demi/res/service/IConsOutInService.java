package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.ConsOutInVo;

@Transactional
public interface IConsOutInService extends IBaseService<ConsOutInVo> {

	void addOutDepot(ConsOutInVo v) throws GlobalException;

	void addInDepot(ConsOutInVo v) throws GlobalException;

	List<ConsOutInVo> getInList() throws GlobalException;

	List<ConsOutInVo> getOutList() throws GlobalException;

}
