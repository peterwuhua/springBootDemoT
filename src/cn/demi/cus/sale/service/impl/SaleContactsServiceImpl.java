package cn.demi.cus.sale.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.sale.dao.ISaleContactsDao;
import cn.demi.cus.sale.po.SaleContacts;
import cn.demi.cus.sale.service.ISaleContactsService;
import cn.demi.cus.sale.vo.SaleContactsVo;

@Service("cus.saleContactService")
public class SaleContactsServiceImpl extends BaseServiceImpl<SaleContactsVo, SaleContacts> implements ISaleContactsService {
	@Autowired
	private ISaleContactsDao saleContactsDao;

	/**
	 * 
	 * <p>
	 * Title: listByIds
	 * </p>
	 * <p>
	 * Description:用于销售查看客户跟踪内容
	 * </p>
	 * 
	 * @param cusId
	 * @param salerId
	 * @return 一条或者多条记录
	 * @throws GlobalException
	 * @see cn.demi.cus.sale.service.ISaleContactsService#listByIds(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<SaleContactsVo> listByIds(String cusId, String salerId) throws GlobalException {
		List<QueryCondition> condList = new ArrayList<>();
		if (!StrUtils.isBlankOrNull(cusId)) {
			QueryCondition con1 = new QueryCondition("customer_id", QueryCondition.EQ, cusId);
			condList.add(con1);
		}
		if (!StrUtils.isBlankOrNull(salerId)) {
			QueryCondition con2 = new QueryCondition("saler_id", QueryCondition.EQ, salerId);
			condList.add(con2);
		}
		QueryCondition con3 = new QueryCondition("  content is not null ");
		condList.add(con3);
		if (condList.size() == 0)
			condList = null;
		List<OrderCondition> orderList = new ArrayList<>();
		orderList.add(new OrderCondition("gz_date", OrderCondition.ORDER_DESC));
		return toVoList(saleContactsDao.list(condList, orderList));
	}

	/**
	 * 
	 * <p>
	 * Title: listBypubcId
	 * </p>
	 * <p>
	 * Description: 公共客户的客户跟踪
	 * </p>
	 * 
	 * @param cusId
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.cus.sale.service.ISaleContactsService#listBypubcId(java.lang.String)
	 */
	@Override
	public List<SaleContactsVo> listBypubcId(String cusId) throws GlobalException {
		List<QueryCondition> condList = new ArrayList<>();
		if (!StrUtils.isBlankOrNull(cusId)) {
			QueryCondition con1 = new QueryCondition("customer_id", QueryCondition.EQ, cusId);
			condList.add(con1);
		}
		QueryCondition con2 = new QueryCondition(" content is not null ");
		condList.add(con2);
		if (condList.size() == 0)
			condList = null;
		List<OrderCondition> orderList = new ArrayList<>();
		orderList.add(new OrderCondition("gz_date", OrderCondition.ORDER_DESC));
		return toVoList(saleContactsDao.list(condList, orderList));
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, SaleContactsVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		List<QueryCondition> list = new ArrayList<>();
		QueryCondition con0 = new QueryCondition(
				" (salerId='" + getCurrent().getAccountId() + "' or leaderId='" + getCurrent().getAccountId() + "') and content is null ");
		list.add(con0);
		pageResult.addConditionList(list);
		pageResult = saleContactsDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<SaleContacts>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public List<SaleContactsVo> listBySalerId(String salerId) throws GlobalException {
		String jpql = " from " + saleContactsDao.getEntityName(SaleContacts.class) + " where salerId= '" + salerId + "' and content is not null ";
		return toVoList(saleContactsDao.list(jpql));
	}

}
