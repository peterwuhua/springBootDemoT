package cn.demi.zk.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.zk.vo.ZkProgressLogVo;

@Transactional
public interface IZkProgressLogService extends IBaseService<ZkProgressLogVo> {
	
	public List<ZkProgressLogVo> findList(String taskId)throws GlobalException;
	
	public ZkProgressLogVo findById(ZkProgressLogVo v)throws GlobalException;
}
