package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.RegOutInVo;

@Transactional
public interface IRegOutInService extends IBaseService<RegOutInVo> {

	void addOutDepot(RegOutInVo v) throws GlobalException;

	void addInDepot(RegOutInVo v) throws GlobalException;

	List<RegOutInVo> getOutList() throws GlobalException;

	List<RegOutInVo> getInList() throws GlobalException;

}
