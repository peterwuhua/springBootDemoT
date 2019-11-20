package cn.demi.office.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.demi.office.vo.WorkPlanVo;

@Transactional
public interface IWorkPlanService extends IBaseService<WorkPlanVo> {
	
	public List<String> listDate(WorkPlanVo v);
}
