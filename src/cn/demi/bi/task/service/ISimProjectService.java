package cn.demi.bi.task.service;


import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.bus.simpjt.vo.ProjectVo;

@Transactional
public interface ISimProjectService extends IBaseService<ProjectVo> {
 

}
