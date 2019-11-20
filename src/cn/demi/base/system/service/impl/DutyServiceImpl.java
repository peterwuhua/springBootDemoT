package cn.demi.base.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IDutyDao;
import cn.demi.base.system.po.Duty;
import cn.demi.base.system.service.IDutyService;
import cn.demi.base.system.vo.DutyVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:11:25 </strong> <br>
 * <strong>Description : DutyServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.dutyService")
public class DutyServiceImpl extends BaseServiceImpl<DutyVo, Duty> implements
		IDutyService {
	@Autowired private IDutyDao dutyDao;
	@Override
	public void update4Grid(DutyVo v) throws GlobalException {
		Duty duty = dutyDao.findById(v.getId());
		duty.setCode(v.getCode());
		duty.setName(v.getName());
		duty.setSort(v.getSort());
		dutyDao.update(duty);
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, DutyVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = dutyDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Duty>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
