package cn.demi.office.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IUserDao;
import cn.demi.base.system.po.User;
import cn.demi.office.dao.IQjglDao;
import cn.demi.office.po.Qjgl;
import cn.demi.office.service.IQjspService;
import cn.demi.office.vo.QjglVo;

@Service("office.qjspService")
public class QjspServiceImpl extends BaseServiceImpl<QjglVo, Qjgl> implements IQjspService {
	@Autowired
	private IQjglDao qjglDao;
	@Autowired
	private IUserDao userDao;
	/**
	 * 
	 * <p>
	 * Title: updateAuditInfo
	 * </p>
	 * <p>
	 * Description: 更新审批信息
	 * </p>
	 * 
	 * @param vo
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.office.service.IQjspService#updateAuditInfo(cn.demi.office.vo.QjglVo)
	 */
	@Override
	public void updateAuditInfo(QjglVo vo) throws GlobalException {
		Qjgl po = qjglDao.findById(vo.getId());
		po.setFstatus(vo.getFstatus());
		po.setSumDate(vo.getSumDate());
		po.setSumUserId(vo.getSumUserId());
		po.setSumUserName(vo.getSumUserName());
		po.setSumRemark(vo.getSumRemark());
		qjglDao.update(po);
	}

	
	
	private QjglVo next2Auditer(QjglVo v) {
		String qjlx = v.getType();// 请假类型
		double qjsc = v.getSumDay();// 请假时长
		double day = qjsc / 8;
		if (qjlx.equals("事假") || qjlx.equals("病假") || qjlx.equals("产假") || qjlx.equals("外出")) {
			User zg = new User();
			StringBuffer strBuf0 = new StringBuffer();
			strBuf0.append(
					"select t from " + userDao.getEntityName(User.class) + " t where 1=1 and t.orgId ='" + getCurrent().getDepId() + "' and t.duty='主管' ");
			zg = (User) userDao.query(strBuf0.toString()).getSingleResult();

			User fzjl = new User();
			StringBuffer strBuf1 = new StringBuffer();
			strBuf1.append(
					"select t from " + userDao.getEntityName(User.class) + " t where 1=1 and t.orgId ='" + getCurrent().getDepId() + "' and t.duty='副总经理' ");
			fzjl = (User) userDao.query(strBuf1.toString()).getSingleResult();

			User zjl = new User();
			StringBuffer strBuf2 = new StringBuffer();
			strBuf2.append(
					"select t from " + userDao.getEntityName(User.class) + " t where 1=1  and t.duty='董事长兼总经理' ");
			zjl = (User) userDao.query(strBuf2.toString()).getSingleResult();

			User user = new User();
			StringBuffer strBufd = new StringBuffer();
			strBufd.append("select t from  " + userDao.getEntityName(User.class) + " t where 1=1 and t.id ='" + getCurrent().getUserId() + "'");
			user = (User) userDao.query(strBufd.toString()).getSingleResult();
			if (null != zg && user.getDuty().equals("主管")) {
				if (day <= 0.5)// 主管审批
				{
					v.setSumUserId(zg.getId());
					v.setSumUserName(zg.getName());
				}
			} else if (null != fzjl && user.getDuty().equals("副总经理")) {
				if (day > 0.5 && day <= 2)// 分管副总审批
				{
					v.setSumUserId(fzjl.getId());
					v.setSumUserName(fzjl.getName());
				}
			} else if (null != zjl && user.getDuty().equals("董事长兼总经理")) {
				if (day > 2)// 总经理审批
				{
					v.setSumUserId(zjl.getId());
					v.setSumUserName(zjl.getName());
				}
			}
		}
		return v;
	}
	
	/**
	 * 
	 * <p>
	 * Title: gridData
	 * </p>
	 * <p>
	 * Description: 请假审批未提交
	 * </p>
	 * 
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.service.BaseServiceImpl#gridData(cn.core.framework.common.page.GridVo,
	 *      cn.core.framework.common.vo.Vo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, QjglVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			// 先根据状态排序desc,然后在根据编号排序desc
			pageResult.addOrder("no", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> condList = new ArrayList<>();
		QueryCondition cond = new QueryCondition(" fstatus = '" + Constants.QJGL_STATUS_WSP + "'");
		condList.add(cond);
		pageResult.addConditionList(condList);
		pageResult.addOrder("supportDate", "desc");
		pageResult = qjglDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Qjgl>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	/**
	 * 
	 * @Title: gridDatad @Description: 请假审批已提交 @param: @param gridVo @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         GridVo @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridDatad(GridVo gridVo, QjglVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
			// 先根据状态排序desc,然后在根据编号排序desc
			pageResult.addOrder("no", OrderCondition.ORDER_DESC);
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		List<QueryCondition> condList = new ArrayList<>();
		QueryCondition cond = new QueryCondition("  fstatus = '" + Constants.QJGL_STATUS_SP_BTG + "' or fstatus ='" + Constants.QJGL_STATUS_SP_TG + "'");
		condList.add(cond);
		pageResult.addConditionList(condList);
		pageResult.addOrder("supportDate", "desc");
		pageResult = qjglDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Qjgl>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	/**
	 * 
	 * <p>
	 * Title: toQueryList
	 * </p>
	 * <p>
	 * Description: 请假审批按审批人id进行权限过滤
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.core.framework.common.service.BaseServiceImpl#toQueryList(cn.core.framework.common.vo.Vo)
	 */
	@Override
	public List<QueryCondition> toQueryList(QjglVo v) throws GlobalException {
		List<QueryCondition> conditions = new ArrayList<>();
		String userId = getCurrent().getUserId();
		QueryCondition con = new QueryCondition("sumUserId='"+userId+"' or (sumUserId is null or sumUserId='' AND deptId='"+getCurrent().getDepId()+"')");
		conditions.add(con);
		return conditions;
	}

	
	
	@Override
	public void update(QjglVo v) throws GlobalException {
		v = next2Auditer(v);
		Qjgl p = qjglDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
//		commit(v, p);
		qjglDao.update(p);
	}
}
