package cn.demi.office.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IUserDao;
import cn.demi.base.system.po.User;
import cn.demi.office.dao.IQjglDao;
import cn.demi.office.po.Qjgl;
import cn.demi.office.service.IQjglService;
import cn.demi.office.vo.QjglVo;

@Service("office.qjglService")
public class QjglServiceImpl extends BaseServiceImpl<QjglVo, Qjgl> implements IQjglService {
	@Autowired
	private IQjglDao qjglDao;
	@Autowired
	private IUserDao userDao;

	/**
	 * 
	 * @Title: gridData @Description: 请假申请列表 @param: @param gridVo @param: @param
	 *         v @param: @return @param: @throws GlobalException @return:
	 *         GridVo @throws
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
	 * Title: organizeData
	 * </p>
	 * <p>
	 * Description: 编辑页面初始数据准备
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.office.service.IQjglService#organizeData(cn.demi.office.vo.QjglVo)
	 */
	@Override
	public QjglVo organizeData(QjglVo v) throws GlobalException {
		v.setPerson(getCurrent().getUserName()); // 申请人
		v.setPersonId(getCurrent().getAccountId()); // 申请人id
		v.setSupportDate(DateUtils.getCurrDateStr()); // 申请日期
		v.setDeptName(getCurrent().getDepName()); // 所在部门
		v.setDeptId(getCurrent().getDepId());
		// 生成编号 年月日+序号(四位小数)
		String jpql = " from " + qjglDao.getEntityName(Qjgl.class) + " where isDel='" + Po.N + "' order by no desc";
		List<Qjgl> list = qjglDao.list(jpql);
		if (StrUtils.isBlankOrNull(v.getNo())) {
			if (list.size() > 0) {
				String edNo = "";
				String suffix = list.get(0).getNo().substring(0, 8);
				String curr_suffix = DateUtils.format(new Date(), "yyyyMMdd");
				if (suffix.equals(curr_suffix)) // 相等
				{
					edNo = String.valueOf(Long.parseLong(list.get(0).getNo()) + 1);
					edNo = edNo.substring(8, edNo.length());
					v.setNo(suffix + edNo);// 生成编号
				} else { // 不相等
					v.setNo(curr_suffix + "0001");// 生成编号
				}
			} else {
				v.setNo(DateUtils.format(new Date(), "yyyyMMdd") + "0001");// 生成编号
			}
		}
		return v;
	}

	/**
	 * 
	 * <p>
	 * Title: next2Auditer
	 * </p>
	 * <p>
	 * Description: 流转下一个审批人
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.office.service.IQjglService#next2Auditer(cn.demi.office.vo.QjglVo)
	 */
	private QjglVo next2Auditer(QjglVo v) {
		String qjlx = v.getType();// 请假类型
		// double qjsc = v.getSumDay();// 请假时长
		// double day = qjsc / 8;
		if (qjlx.equals("事假") || qjlx.equals("病假") || qjlx.equals("产假") || qjlx.equals("外出")) {
			User zg = new User();
			StringBuffer strBuf0 = new StringBuffer();
			strBuf0.append(
					"select t from " + userDao.getEntityName(User.class) + " t where 1=1 and t.orgId ='" + getCurrent().getDepId() + "' and t.duty='主管' ");
			zg = (User) userDao.query(strBuf0.toString()).getSingleResult();

			if (null != zg) {
				v.setSumUserId(zg.getId());
				v.setSumUserName(zg.getName());
			}
		}
		return v;
	}

	@Override
	public void add(QjglVo v) throws GlobalException {
		v = next2Auditer(v);
		Qjgl p = vo2Po(v);
		qjglDao.add(p);
		v.setId(p.getId());
		commit(v, p);
		qjglDao.update(p);
	}

	@Override
	public void update(QjglVo v) throws GlobalException {
		v = next2Auditer(v);
		Qjgl p = qjglDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		commit(v, p);
		qjglDao.update(p);
	}

	private void commit(QjglVo v, Qjgl p) throws GlobalException {
		if (null != v.getIsCommit() && v.getIsCommit().equals(EunmTask.PASS_Y)) {
			p.setFstatus(Constants.QJGL_STATUS_WSP);
		} else {
			p.setFstatus(Constants.QJGL_STATUS_WSQ);
		}
	}

}
