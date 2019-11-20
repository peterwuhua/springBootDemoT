package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.StanOutInVo;

@Transactional
public interface IStanOutInService extends IBaseService<StanOutInVo> {

	void addOutDepot(StanOutInVo v) throws GlobalException;

	void addInDepot(StanOutInVo v) throws GlobalException;

	List<StanOutInVo> getInList() throws GlobalException;

	List<StanOutInVo> getOutList() throws GlobalException;

}
