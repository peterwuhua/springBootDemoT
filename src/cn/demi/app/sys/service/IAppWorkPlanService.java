package cn.demi.app.sys.service;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.vo.AppWorkPlanVo;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.office.vo.WorkPlanVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IAppWorkPlanService extends IBaseService<WorkPlanVo> {
	/**
	 * 工作计划
	 * @param v
	 * @return
	 */
	public List<AppWorkPlanVo> listDate(WorkPlanVo v);

	/**
	 * 新增工作计划
	 * @param v
	 * @param objVo
	 * @return
	 * @throws GlobalException
	 */
	public String addWorkPlan(WorkPlanVo v, ObjVo objVo) throws GlobalException;
}
